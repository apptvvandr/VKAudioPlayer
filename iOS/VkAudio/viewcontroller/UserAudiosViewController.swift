//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import Kingfisher
import DZNEmptyDataSet
import MBProgressHUD

class UserAudiosViewController: UITableViewController, UISearchResultsUpdating, DZNEmptyDataSetSource, DZNEmptyDataSetDelegate {

    let api = VKAPService.sharedInstance!
    
    var ownerId: Int?
    var ownerName: String?
    var userAudios = [Audio]()
    
    let searchController = UISearchController(searchResultsController: nil)
    var filteredAudios = [Audio]()
    
    var progressHudHidden: Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.tableView.backgroundView = blurView
        }
      
        searchController.searchResultsUpdater = self
        searchController.dimsBackgroundDuringPresentation = false
        definesPresentationContext = true
        tableView.tableHeaderView = searchController.searchBar
        
        tableView.emptyDataSetSource = self
        tableView.emptyDataSetDelegate = self
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        onRefresh()
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = isSearchPrepared() ? filteredAudios[indexPath.row] : userAudios[indexPath.row]

        cell.setData(audio)
        return cell
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return isSearchPrepared() ? filteredAudios.count : userAudios.count
    }
    
    //MARK: -Search
    
    private func isSearchPrepared() -> Bool{
        return searchController.active && searchController.searchBar.text != ""

    }
    
    private func onSearchPrepared(filter: String){
        let lowercaseFilter = filter.lowercaseString
        filteredAudios = userAudios.filter({ (audio: Audio) -> Bool in
            let fullName = "\(audio.artist ?? "")-\(audio.name ?? "")"
            return fullName.lowercaseString.containsString(lowercaseFilter)
        })
        tableView.reloadData()
    }
    
    func updateSearchResultsForSearchController(sechController: UISearchController) {
        onSearchPrepared(searchController.searchBar.text!)
    }
    
    //MARK: -Refresh
    
    func onRefresh() {
        MBProgressHUD.showHUDAddedTo(self.view, animated: true)
        progressHudHidden = false
        api.getAudios(ownerId, callback: VkApiCallback(
            onResult: { (result) in
                self.userAudios = result
                self.tableView.reloadData()
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
            },
            onError: { (error) in
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
                self.tableView.reloadEmptyDataSet()
        }))
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        super.prepareForSegue(segue, sender: sender)
        let audioPlayerVC = segue.destinationViewController as! AudioPlayerViewController

        if sender is AudioCell {
            let cell = sender as! AudioCell
            let index = self.tableView.indexPathForCell(cell)!.row
            
            let searchPrepared = isSearchPrepared()
            audioPlayerVC.playlistOwnerId = ownerId
            audioPlayerVC.audios = searchPrepared ? filteredAudios : userAudios
            audioPlayerVC.selectedAudioIndex = index
        }
        else {
            let player = AudioPlayer.sharedInstance
            audioPlayerVC.audios = player.playlist.map({$0 as! Audio})
            audioPlayerVC.selectedAudioIndex = player.currentAudio?.playlistPosition
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        searchController.active = false
    }
    
    //MARK: -DZNEmptyDataSet
    
    func emptyDataSetShouldDisplay(scrollView: UIScrollView!) -> Bool {
        return progressHudHidden && userAudios.count == 0
    }
    
    func titleForEmptyDataSet(scrollView: UIScrollView!) -> NSAttributedString! {
        let str = "Oops..."
        let attrs = [NSFontAttributeName: UIFont.preferredFontForTextStyle(UIFontTextStyleHeadline)]
        return NSAttributedString(string: str, attributes: attrs)
    }
    
    func descriptionForEmptyDataSet(scrollView: UIScrollView!) -> NSAttributedString! {
        let str = "Seems, \(ownerName ?? "your") playlist is empty, or something went wrong"
        let attrs = [NSFontAttributeName: UIFont.preferredFontForTextStyle(UIFontTextStyleBody)]
        return NSAttributedString(string: str, attributes: attrs)
    }
    
    func imageForEmptyDataSet(scrollView: UIScrollView!) -> UIImage! {
        return UIImage(named: "sad")
    }
}