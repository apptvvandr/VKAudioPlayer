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
    
    let api = VKAPService.sharedInstance!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let userPhoto = UIImage(contentsOfFile: "bg_image.jpg") else {
            api.getUserPhoto(VkApiCallback(onResult: { (result) in
                self.setBackgroundImage(result)
            }))
            return
        }
        setBackgroundImage(userPhoto)
    }
    
    private func setBackgroundImage(image: UIImage) {
        UIGraphicsBeginImageContext(self.view.frame.size)
        image.drawInRect(self.view.bounds)
        let contextImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        //todo: set blur once here instead of embedded conrollers

        self.view.backgroundColor = UIColor(patternImage: contextImage)
    }
}