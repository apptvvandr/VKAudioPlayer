//
//  VKApiDefaults.swift
//  VkAudio
//
//  Created by mac-224 on 28.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class VKApiDefaults {
    
    private static let DEFAULTS_NAME_VK = "defaults_vk"
    private static let KEY_VK_TOKEN = "vk_token"
    private static let KEY_VK_TOKEN_EXPIRE = "vk_token_expire"
    private static let KEY_VK_USER_ID = "vk_user_id"
    private static let KEY_VK_LAST_LOGIN = "vk_last_login"
    
    static func getTokenValues() -> [String?] {
        let defaults = getDefaults()
        
        return [defaults.stringForKey(KEY_VK_TOKEN),
                defaults.stringForKey(KEY_VK_TOKEN_EXPIRE),
                defaults.stringForKey(KEY_VK_USER_ID)]
    }
    
    static func saveTokenValues(token: String, expireIn: String, userId: String) {
        let defaults = getDefaults()
        defaults.setObject(token, forKey: KEY_VK_TOKEN)
        defaults.setObject(expireIn, forKey: KEY_VK_TOKEN_EXPIRE)
        defaults.setObject(userId, forKey: KEY_VK_USER_ID)
    }
    
    static func setLastLoginMillis(millis: Double) {
        getDefaults().setDouble(millis, forKey: KEY_VK_LAST_LOGIN)
    }
    
    static func getLastLoginMillis() -> Double {
        return getDefaults().doubleForKey(KEY_VK_LAST_LOGIN)
    }
    
    static func clearValues() {
        let appDomain = NSBundle.mainBundle().bundleIdentifier
        getDefaults().removePersistentDomainForName(appDomain!)
    }
    
    private static func getDefaults() -> NSUserDefaults! {
        return NSUserDefaults(suiteName: DEFAULTS_NAME_VK)
    }
}