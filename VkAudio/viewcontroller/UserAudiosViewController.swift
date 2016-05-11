//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import Kingfisher

class UserAudiosViewController: UITableViewController {

    var ownerId: Int?
    var ownerName: String?
    
    var userAudios = [Audio]()

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
        let audio = userAudios[indexPath.row]

        cell.setData(audio.artist, name: audio.name)
        return cell
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return userAudios.count
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        super.prepareForSegue(segue, sender: sender)

        if let identifier = segue.identifier where identifier == "audioToAudioPlayer" {
            let cell = sender as! AudioCell
            let index = self.tableView.indexPathForCell(cell)
            
            let destinationController = segue.destinationViewController as! AudioPlayerViewController
            destinationController.audios = self.userAudios
            destinationController.selectedAudioIndex = index!.row
        }
        
    }
}