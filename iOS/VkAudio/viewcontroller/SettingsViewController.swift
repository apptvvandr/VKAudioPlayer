//
//  SettingsTableViewController.swift
//  VkAudio
//
//  Created by mac-224 on 30.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class SettingsViewController: OutsideController {
    
    @IBOutlet weak var btnAutoSync: UIButton!
    @IBOutlet weak var switchAutoSync: UISwitch!
    @IBOutlet weak var btnSyncObjects: UIButton!
    @IBOutlet weak var btnEditSyncObjects: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        switchAutoSync.on = VKAPUserDefaults.isAutoSyncEnabled()
        btnSyncObjects.enabled = switchAutoSync.on
        btnEditSyncObjects.enabled = switchAutoSync.on
    }
    
    @IBAction func onAutoSyncClicked(sender: AnyObject) {
        let autoSyncEnabled = VKAPUserDefaults.isAutoSyncEnabled()
        
        VKAPUserDefaults.setAutoSyncEnabled(!autoSyncEnabled);
        switchAutoSync.setOn(!autoSyncEnabled, animated: true)
        
        btnSyncObjects.enabled = switchAutoSync.on
        btnEditSyncObjects.enabled = switchAutoSync.on
    }
    
    @IBAction func onSyncObjectsClicked(sender: AnyObject) {
        let syncItems = VkModelDB.sharedInstance!.getAll()
        let syncItemPickDialog = SyncModelPickDialog(items: syncItems)
        syncItemPickDialog.show()
    }
    
    @IBAction func onResetSettingsClicked(sender: AnyObject) {
        let confirmResetDialog = SimpleAlertView(title: "Warning", message: "This will clear all your account settings.\nAre you sure?",
                                                 negativeButtonTitle: "No",
                                                 positiveButtonTitle: "Yes") { VKAPUtils.resetSettings() }
        confirmResetDialog.show()
    }
}
