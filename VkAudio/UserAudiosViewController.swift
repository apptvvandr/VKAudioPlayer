//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class UserAudiosViewController: UIViewController{
    
    internal static let STORYBOARD_ID: String = "controller_user_audios"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let audios: String = VkSDK.instance!.requestManager!.getAudios("124259152")
        print(audios)
    }
}