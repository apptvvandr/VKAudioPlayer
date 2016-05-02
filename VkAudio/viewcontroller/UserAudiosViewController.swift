//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class UserAudiosViewController: UITableViewController {
        
    var userAudios = [Audio]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        VkSDK.Audios.getAudios(
            onResult: { result in
                self.userAudios = result
                self.tableView.reloadData()
            }
        )
        
        VkSDK.Users.getUserInfo(
            ["fields" : "photo_big"],
            onResult: { result in
                let url = NSURL(string: result["photo_big"] as! String)
                
                dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)) {
                    let data = NSData(contentsOfURL: url!)
                    dispatch_async(dispatch_get_main_queue(), {
                       let uiImage = UIImage(data: data!)
                        
                        let imageView = UIImageView(image: uiImage)
                        self.tableView.backgroundView = imageView
                    });
                }
            }
        )
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(AudioCell.STORYBOARD_ID) as! AudioCell
        let audio = userAudios[indexPath.row]
        
        cell.backgroundColor = UIColor(white: 1, alpha: 0.5)
        cell.update(audio.artist!, name: audio.name!)
        return cell
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return userAudios.count
    }
}