//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class UserAudiosViewController: UITableViewController {
    
    static let STORYBOARD_ID = "controller_audios"
    static let SEGUE_ID = "login_to_user_audios"
    
    private var userAudios = [Audio]()
    var audios: [Audio] {
        return userAudios
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        VkSDK.instance?.getAudios({ (result) in
            self.userAudios = result
            self.tableView.reloadData()
        })
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = userAudios[indexPath.row]
        
        cell.update(audio.artist!, name: audio.name!)
        return cell
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return userAudios.count
    }
}