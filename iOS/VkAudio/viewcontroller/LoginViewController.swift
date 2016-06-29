//
//  ViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import JLToast

class LoginViewController: VKLoginViewController {
    
    override func onLoginResult(token: String, tokenExpiersIn: Double, userId: Int) {
        VkModelDB.setup(userId)
        SyncItemDB.setup(userId)
        
        VKAPServiceImpl.sharedInstance.getUserInfo(VKApi.userId!, callback: VKApiCallback(
            onResult: { (result: UserModel) in
                result.syncEnabled = true
                VkModelDB.sharedInstance?.put(result)
                
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                let mainViewController = storyboard.instantiateViewControllerWithIdentifier("controller_main")
                self.presentViewController(mainViewController, animated: true, completion: nil)
        }))
    }
    
    override func onLoginError() {
        JLToast.makeText("To use this app you should be logged in!", duration: JLToastDelay.ShortDelay).show()
        webView.reload()
    }
}