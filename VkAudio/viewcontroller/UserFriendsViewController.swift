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
    
    var friends = [Friend]()

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        VkSDK.Frinds.getFriends(["fields": "name, photo_100"], onResult: { (result) in
            self.friends = result
            self.tableView.reloadData()
        })
    }


    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return friends.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(FriendCell.STORYBOARD_ID) as! FriendCell
        let friend: Friend = friends[indexPath.row]
        
        cell.setData(friend.firstName!, lastName: friend.lastName!, photoUrl: friend.photoUrl!)
        return cell
    }
}