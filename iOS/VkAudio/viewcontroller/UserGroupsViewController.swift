//
//  UserGroupsViewController.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import MBProgressHUD

class UserGroupsViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    @IBOutlet weak var barToggle: UIBarButtonItem!
    
    let api = VKAPServiceImpl.sharedInstance
    var dataTag = "groups"
    
    var groups = [GroupModel]() {
        didSet {
            if self.groups.count > 0 {
                VKAPUserDefaults.setLastDataUpdate(NSDate.currentTimeMillis(), dataTag: dataTag)
            }
        }
    }
    
    var progressHudHidden: Bool = true
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.collectionView!.backgroundView = blurView
        }
        
        if let revealViewController = self.revealViewController() {
            barToggle.target = self.revealViewController()
            barToggle.action = #selector(SWRevealViewController.revealToggle(_:))
            self.view.addGestureRecognizer(revealViewController.panGestureRecognizer())
        }
    }

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        if groups.count == 0 || VKAPUtils.lastRequestIsOlder(dataTag, seconds: VKAPUtils.REQUSET_DELAY_USER_GROUPS) {
            onPerformDataRequest()
        }
    }
    
    private func onPerformDataRequest() {
        if groups.count == 0 {
            MBProgressHUD.showHUDAddedTo(self.view, animated: true)
            progressHudHidden = false
        }
        
        api.getGroups(VKApiCallback(
            onResult: { (result: [GroupModel]) in
                self.groups = result
                self.collectionView?.reloadData()
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
            
                VkModelDB.sharedInstance?.update(result)
                SyncItemDB.sharedInstance?.update()
                
                if !VKAPUserDefaults.isAskedSync(self.dataTag) {
                    self.showSyncAlert(result)
                }
            },
            onError: { (error) in
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
        }))
    }
    
    private func showSyncAlert(items: [GroupModel]) {
        let alertView = SimpleAlertView(title: "Synchronization", message: "Would you like to sync groups music automatically?",
            negativeButtonTitle: "NO", onNegativeButtonClick: {
                //todo: onNegativeClick
            },
            positiveButtonTitle: "YES", onPositiveButtonClick: {
                let pickDialog = SyncModelPickDialog(items: items.map{$0 as VkModel})
                pickDialog.show()
        })
        VKAPUserDefaults.setAskedSync(true, dataTag: self.dataTag)
        alertView.show()
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        let screenWidth = self.view.frame.width
        let width = screenWidth / 2 <= 160 ? screenWidth / 2 : screenWidth / 3 //wow, ipad compatibility
        return CGSize(width: width - 12, height: width - 12)
    }

    override func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell: GroupCell = collectionView.dequeueReusableCellWithReuseIdentifier(GroupCell.STORYBOARD_ID, forIndexPath: indexPath) as! GroupCell
        let group = groups[indexPath.row]
        cell.setData(group.id, groupName: group.name, photoUrl: group.avatarUrl)
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
