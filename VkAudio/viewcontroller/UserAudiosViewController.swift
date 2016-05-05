//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import Kingfisher

class UserAudiosViewController: UITableViewController {

    var ownerId: Int?
    var userAudios = [Audio]()

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)

        var params = [String: AnyObject]()
        if let id = ownerId {
            params = ["owner_id": id]
        }

        VkSDK.Audios.getAudios(params,
                onResult: {
                    result in
                    self.userAudios = result
                    self.tableView.reloadData()
                })
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = userAudios[indexPath.row]

        cell.backgroundColor = UIColor(white: 1, alpha: 0.5)
        cell.setData(audio.artist, name: audio.name)
        return cell
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return userAudios.count
    }
}