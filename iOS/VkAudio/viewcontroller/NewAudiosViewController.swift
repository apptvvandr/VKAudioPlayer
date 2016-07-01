//
//  NewAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 01.07.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import MBProgressHUD

class NewAudiosViewController: UITableViewController {
    
    @IBOutlet weak var barToggle: UIBarButtonItem!
    
    var items = [VkModel: [AudioModel]]() {
        didSet {
            if self.items.count > 0 {
                VKAPUserDefaults.setLastDataUpdate(NSDate.currentTimeMillis(), dataTag: self.dataTag)
            }
        }
    }
    let dataTag = "new_audio"
    
    var progressHudHidden: Bool = true

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in self.tableView.backgroundView = blurView }
        if let revealViewController = self.revealViewController() {
            barToggle.target = self.revealViewController()
            barToggle.action = #selector(SWRevealViewController.revealToggle(_:))
            self.view.addGestureRecognizer(revealViewController.panGestureRecognizer())
        }
        
        let headerNib = UINib(nibName: "AudioOwnerHeader", bundle: nil)
        tableView.registerNib(headerNib, forHeaderFooterViewReuseIdentifier: "header_audio_owner")
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        if items.count == 0 || SyncItemDB.sharedInstance!.getAllIds() != nil && VKAPUtils.lastRequestIsOlder(dataTag, seconds: VKAPUtils.REQUEST_DELAY_NEW_AUDIOS) {
            self.onPerformDataRequest()
        }
    }
    
    private func onPerformDataRequest() {
        if items.count == 0 {
            MBProgressHUD.showHUDAddedTo(self.view, animated: true)
            progressHudHidden = false
        }
        
        VKAPServiceImpl.sharedInstance.getNewAudios(SyncItemDB.sharedInstance!.getAllIds()!, callback: VKApiCallback(
            onResult: { (result: [VkModel: [AudioModel]]) in
                self.items = result
                self.tableView.reloadData()
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
            },
            onError: { (error: NSError) in
                self.progressHudHidden = MBProgressHUD.hideHUDForView(self.view, animated: true)
        }))

    }
    
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return items.count
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let index = items.startIndex.advancedBy(section)
        let values = items.values[index]
        
        return values.count
    }
    
    override func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let item = items.keys[items.startIndex.advancedBy(section)]

        let header = tableView.dequeueReusableHeaderFooterViewWithIdentifier("header_audio_owner") as! AudioOwnerHeader        
        header.imageAvatar.kf_setImageWithURL(NSURL(string: item.avatarUrl)!)
        header.labelName.text = item.fullName
        
        return header
    }
    
    override func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        let sectionValues = items.values[items.startIndex.advancedBy(section)]
        return sectionValues.count > 0 ? 48.0 : 0.0
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("cell_audio") as! AudioCell
        let sectionValues = items.values[items.startIndex.advancedBy(indexPath.section)]
        let rowValue = sectionValues[indexPath.row]
        
        cell.setData(rowValue)
        
        return cell
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let playerController = segue.destinationViewController as! AudioPlayerViewController
        let indexPath = self.tableView.indexPathForCell(sender as! AudioCell)
        let section = items.startIndex.advancedBy(indexPath!.section)
        let sectionValues = items.values[section]
        
        playerController.audios = sectionValues
        playerController.selectedAudioIndex = indexPath?.row
    }
}

class AudioOwnerHeader: UITableViewHeaderFooterView {
    @IBOutlet weak var imageAvatar: UIImageView!
    @IBOutlet weak var labelName: UILabel!
    
    override func layoutSubviews() {
        super.layoutSubviews()
        if imageAvatar != nil {
            imageAvatar.layer.cornerRadius = CGRectGetWidth(imageAvatar.frame) / 2
        }
    }
}