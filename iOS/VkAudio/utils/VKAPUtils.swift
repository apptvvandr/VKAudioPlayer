//
//  VKAPUtils.swift
//  VkAudio
//
//  Created by mac-224 on 22.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class VKAPUtils {

    static let REQUEST_DELAY_USER_AUDIOS = 15
    static let REQUSET_DELAY_USER_GROUPS = 5 * 60
    static let REQUEST_DELAY_USER_FRIENDS = 5 * 60
    
    static func formatProgress(seconds: Int) -> String {
        let minutes = seconds / 60
        let seconds = seconds % 60
        
        return String(format: "%0.2d:%0.2d", minutes, seconds)
    }
    
    static func lastRequestIsOlder(dataTag: String, seconds: Int) -> Bool {
        let lastDataUpdate = VKAPUserDefaults.getLastDataUpdate(dataTag)
        let now = NSDate.currentTimeMillis()
        
        return now - lastDataUpdate > Double(seconds) * 1000
    }
    
    static func resetSettings() {
        VKAPUserDefaults.clear();
        VkModelDB.sharedInstance!.setSyncForAllEnabled(false);
    }
    
    static func login(starter: UIViewController) {
        let plistPath = NSBundle.mainBundle().pathForResource("VKAPApplication", ofType: "plist")
        let appValues = NSDictionary(contentsOfFile: plistPath!)!
        
        let appId = appValues.objectForKey("APP_ID") as! String
        let appScope = appValues.objectForKey("APP_SCOPE") as! String
        
        VKApi.login(starter, appId: appId, appScope: appScope)
    }
    
    static func logout(holder: UIViewController) {
        VKApi.logout(holder)
        let avatarName = "bg_image_\(VKApi.userId!).jpg"
        LocalStorage.removeFile(.DocumentDirectory, fileName: avatarName)
        
        login(holder)
    }
}