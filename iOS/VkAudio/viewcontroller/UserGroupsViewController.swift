//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class UserGroupsViewController: UICollectionViewController {

    var groups = [Group]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.collectionView!.backgroundView = blurView
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        //todo: api-related stuff on UI. should be encapsulated into service level
        VkSDK.Groups.getGroups(["extended": 1],
                onResult: {
                    result in
                    self.groups = result
                    self.collectionView?.reloadData()
                })
    }

    override func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell: GroupCell = collectionView.dequeueReusableCellWithReuseIdentifier(GroupCell.STORYBOARD_ID, forIndexPath: indexPath) as! GroupCell
        let group = groups[indexPath.row]
        cell.setData(group.id, groupName: group.name, photoUrl: group.photoUrl)
        return cell
    }

    override func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return groups.count
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let identifier = segue.identifier where identifier == "groupsToFriendAudios" {
            let cell = sender as! GroupCell

            if let groupId = cell.groupId {
                let destinationController = segue.destinationViewController as! UserAudiosViewController
                destinationController.ownerId = -groupId
                destinationController.ownerName = cell.groupName
            }
        }
    }
}
