//
//  VkTabsController.swift
//  VkAudio
//
//  Created by mac-224 on 04.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class VkTabsController: UITabBarController {

    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)

        //todo: api-related stuff on UI. should be encapsulated into service level
        VkSDK.Users.getUserInfo(["fields": "photo_big"], onResult: {
            (result) in
            if let url = NSURL(string: result["photo_big"] as! String) {

                UIImageView().kf_setImageWithURL(url, completionHandler: {
                    (image, error, cacheType, imageURL) in

                    UIGraphicsBeginImageContext(self.view.frame.size)
                    image?.drawInRect(self.view.bounds)
                    let contextImage = UIGraphicsGetImageFromCurrentImageContext()
                    UIGraphicsEndImageContext()

                    self.view.backgroundColor = UIColor(patternImage: contextImage)
                })
            }
        })
    }
}