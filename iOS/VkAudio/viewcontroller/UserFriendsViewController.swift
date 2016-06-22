//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 02.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import MBProgressHUD

class UserFriendsViewController: UITableViewController {

    let api = VKAPService.sharedInstance!
    let dataTag = "friends"
    
    var friends = [User]() {
        didSet {
            if friends.count > 0 {
                VKAPUserDefaults.setLastDataUpdate(NSDate.currentTimeMillis(), dataTag: dataTag)
            }
        }
    }
    
    var progressHudHidden: Bool = true
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.tableView.backgroundView = blurView
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        if friends.count == 0 || VKAPUtils.lastRequestIsOlder(dataTag, seconds: VKAPUtils.REQUEST_DELAY_USER_FRIENDS) {
            onPerformDataRequest()
        }
    }

    private func onPerformDataRequest() {
        if friends.count == 0 {
            MBProgressHUD.showHUDAddedTo(self.view, animated: true)
            progressHudHidden = false
        }
        
        api.getFriends(VkApiCallback(
            onResult: { (result) in
                self.friends = result
                self.tableView.reloadData()
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
            }, onError: {(error) in
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
        }))
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