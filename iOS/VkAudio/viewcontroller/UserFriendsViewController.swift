//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 02.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class UserFriendsViewController: UITableViewController {

    var friends = [User]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.tableView.backgroundView = blurView
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)

        //todo: api-related stuff on UI. should be encapsulated into service level
        VkSDK.Frinds.getFriends(["fields": "name, photo_100"], onResult: {
            (result) in
            self.friends = result
            self.tableView.reloadData()
        })
    }


    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return friends.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(UserCell.STORYBOARD_ID) as! UserCell
        let friend: User = friends[indexPath.row]

        cell.setData(friend.id, firstName: friend.firstName, lastName: friend.lastName, photoUrl: friend.photoUrl)
        return cell
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let identifier = segue.identifier where identifier == "friendsToFriendAudios" {
            let cell = sender as! UserCell

            let destinationController = segue.destinationViewController as! UserAudiosViewController
            destinationController.ownerId = cell.userId
            destinationController.ownerName = cell.firstName
        }
    }
}