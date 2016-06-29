//
//  VkTabsController.swift
//  VkAudio
//
//  Created by mac-224 on 04.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class VkTabsController: UITabBarController {
    
    let api = VKAPServiceImpl.sharedInstance
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let user = VkModelDB.sharedInstance!.get(VKApi.userId!) as! UserModel
        
        let avatarName = "bg_image_\(VKApi.userId!).jpg"
        let localPath = LocalStorage.buildFilePath(.DocumentDirectory, fileName: avatarName)
        if LocalStorage.fileExists(.DocumentDirectory, fileName: avatarName) {
           self.setBackgroundImage(UIImage(contentsOfFile: localPath)!)
            return
        }
        
        Alamofire.download(.GET, user.avatarUrl, destination: { (temporaryURL, response) in NSURL(string: "file://\(localPath)")!})
            .response(completionHandler: { (request, response, data, error) in
                let avatar = UIImage(contentsOfFile: localPath)
                self.setBackgroundImage(avatar!)
            })
    }
    
    private func setBackgroundImage(image: UIImage) {
        UIGraphicsBeginImageContext(self.view.frame.size)
        image.drawInRect(self.view.bounds)
        let contextImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        self.view.backgroundColor = UIColor(patternImage: contextImage)
    }
}