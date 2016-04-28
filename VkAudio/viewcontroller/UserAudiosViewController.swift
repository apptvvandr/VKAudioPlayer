//
//  UserAudiosViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class UserAudiosViewController: UIViewController {
    
    internal static let STORYBOARD_ID = "controller_user_audios"
    internal static let SEGUE_ID = "login_to_user_audios"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        VkSDK.instance?.getAudios({ (result) in
            print(result)
        })
    }
}