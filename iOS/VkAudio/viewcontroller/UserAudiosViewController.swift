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

    @IBOutlet weak var barToggle: UIBarButtonItem!
    
    let api = VKAPServiceImpl.sharedInstance
    var dataTag = "main_audio"
    
    var ownerId: Int? {
        didSet {
            let isCurrentUser = ownerId == nil || ownerId! == VKApi.userId!
            self.dataTag = isCurrentUser ? "main_audio" : "audio"
        }
    }
    var ownerName: String?
    var audios = [AudioModel]() {
        didSet {
            if self.dataTag.hasPrefix("main_") && self.audios.count > 0 {
                VKAPUserDefaults.setLastDataUpdate(NSDate.currentTimeMillis(), dataTag: self.dataTag)
            }
        }
    }
    
    let searchController = UISearchController(searchResultsController: nil)
    var filteredAudios = [AudioModel]()
    
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
        
        self.navigationItem.title = ownerName == nil ? "Audio" : ownerName!
        
        if let revealViewController = self.revealViewController() {
            barToggle.target = self.revealViewController()
            barToggle.action = #selector(SWRevealViewController.revealToggle(_:))
            self.view.addGestureRecognizer(revealViewController.panGestureRecognizer())
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        if audios.count == 0 || VKAPUtils.lastRequestIsOlder(dataTag, seconds: VKAPUtils.REQUEST_DELAY_USER_AUDIOS) {
            onPerformDataRequest()
        }
    }
    
    func onPerformDataRequest() {
        if audios.count == 0 {
            MBProgressHUD.showHUDAddedTo(self.view, animated: true)
            progressHudHidden = false
        }
        
        api.getAudios(ownerId, callback: VKApiCallback(
            onResult: { (result: [AudioModel]) in
                self.audios = result
                self.tableView.reloadData()
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
            },
            onError: { (error) in
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
                self.tableView.reloadEmptyDataSet()
        }))
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = isSearchPrepared() ? filteredAudios[indexPath.row] : audios[indexPath.row]

        cell.setData(audio)
        return cell
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return isSearchPrepared() ? filteredAudios.count : audios.count
    }
    
    //MARK: -Search
    
    private func isSearchPrepared() -> Bool{
        return searchController.active && searchController.searchBar.text != ""

    }
    
    private func onSearchPrepared(filter: String){
        let lowercaseFilter = filter.lowercaseString
        filteredAudios = audios.filter{ audio in
            let fullName = "\(audio.artist)-\(audio.name)"
            return fullName.lowercaseString.containsString(lowercaseFilter)
        }
        tableView.reloadData()
    }
    
    func updateSearchResultsForSearchController(sechController: UISearchController) {
        onSearchPrepared(searchController.searchBar.text!)
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        super.prepareForSegue(segue, sender: sender)
        let audioPlayerVC = segue.destinationViewController as! AudioPlayerViewController

        if sender is AudioCell {
            let cell = sender as! AudioCell
            let index = self.tableView.indexPathForCell(cell)!.row
            
            audioPlayerVC.playlistOwnerId = ownerId
            audioPlayerVC.audios = isSearchPrepared() ? filteredAudios : audios
            audioPlayerVC.selectedAudioIndex = index
        }
        else {
            let player = AudioPlayer.sharedInstance
            audioPlayerVC.audios = player.playlist.map({$0 as! AudioModel})
            audioPlayerVC.selectedAudioIndex = player.currentAudio?.playlistPosition
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        searchController.active = false
    }
    
    //MARK: -DZNEmptyDataSet
    
    func emptyDataSetShouldDisplay(scrollView: UIScrollView!) -> Bool {
        return progressHudHidden && audios.count == 0
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