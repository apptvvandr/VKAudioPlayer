//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

private let reuseIdentifier = "Cell"

class UserGroupsViewController2: UICollectionViewController {

    var groups = [Group]()

    override func viewWillAppear(animated: Bool) {
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
        cell.update(group.name!, photoUrl: group.photoUrl!)
        return cell
    }

    override func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return groups.count
    }
}
