//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import Kingfisher

class UserAudiosViewController: UITableViewController, UISearchResultsUpdating {

    var ownerId: Int?
    var ownerName: String?
    var userAudios = [Audio]()
    
    let searchController = UISearchController(searchResultsController: nil)
    var filteredAudios = [Audio]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.tableView.backgroundView = blurView
        }
      
        searchController.searchResultsUpdater = self
        searchController.dimsBackgroundDuringPresentation = false
        definesPresentationContext = true
        tableView.tableHeaderView = searchController.searchBar
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        self.title = ownerName != nil ? "\(ownerName!)'s audios" : "Audios"
        let params: [String: AnyObject] = ownerId != nil ? ["owner_id": ownerId!] : [String: AnyObject]()

        VkSDK.Audios.getAudios(params,
                onResult: {
                    result in
                    self.userAudios = result
                    self.tableView.reloadData()
                })
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = isSearchPrepared() ? filteredAudios[indexPath.row] : userAudios[indexPath.row]

        cell.setData(audio.artist, name: audio.name)
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
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        super.prepareForSegue(segue, sender: sender)

        if let identifier = segue.identifier where identifier == "audioToAudioPlayer" {
            let cell = sender as! AudioCell
            let index = self.tableView.indexPathForCell(cell)!.row
            
            let destinationController = segue.destinationViewController as! AudioPlayerViewController
            let searchPrepared = isSearchPrepared()
            destinationController.audios = searchPrepared ? filteredAudios : userAudios
            destinationController.selectedAudioIndex = index
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        searchController.active = false
    }
}