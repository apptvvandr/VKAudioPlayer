//
//  AppDelegate.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import CoreData

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        let starterControllerId = VkSDK.instance == nil ? LoginViewController.SROTYBOARD_ID : UserAudiosViewController.STORYBOARD_ID;
        let storyboard = UIStoryboard(name: "Main", bundle: NSBundle.mainBundle())
        let startVC = storyboard.instantiateViewControllerWithIdentifier(starterControllerId)

        UIApplication.sharedApplication().delegate?.window??.rootViewController = UINavigationController(rootViewController: startVC)
        
        return true
    }

}

