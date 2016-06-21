//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class UserGroupsViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    let api = VKAPService.sharedInstance!
    var groups = [Group]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.collectionView!.backgroundView = blurView
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        api.getGroups(VkApiCallback(onResult: { (result) in
            self.groups = result
            self.collectionView?.reloadData()
        }))
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        let screenWidth = self.view.frame.width
        let width = screenWidth / 2 <= 160 ? screenWidth / 2 : screenWidth / 3 //wow, ipad compatibility
        return CGSize(width: width - 12, height: width - 12)
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
