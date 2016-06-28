//
//  VkApi.swift
//  VkAudio
//
//  Created by mac-224 on 23.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class VKApi {
    
    static var sharedInstance: VKApi?
    
    static var token: String?
    static var userId: Int?
    
    private init(token: String, userId: String) {
        VKApi.token = token
        VKApi.userId = Int(userId)
    }
    
    static func setup() {
        if sharedInstance != nil {
            return
        }
        let tokenValues = VKApiDefaults.getTokenValues()
        if let token = tokenValues[0], let userId = tokenValues[2] {
            sharedInstance = VKApi(token: token, userId: userId)
        }
    }
    
    static func setup(token: String, tokenExpireIn: String, userId: String) {
        VKApiDefaults.saveTokenValues(token, expireIn: tokenExpireIn, userId: userId)
        sharedInstance = VKApi(token: token, userId: userId)
    }
    
    static func logout() {
        VKApiDefaults.clearValues()
        let cookies = NSHTTPCookieStorage.sharedHTTPCookieStorage()
        //todo: clear cache since last login
        //cookies.removeCookiesSinceDate(date: NSDate)
        for cookie in cookies.cookies! {
            cookies.deleteCookie(cookie)
        }
    }
    
    static func authUrl(id: String, scope: String) -> String {
        return "http://oauth.vk.com/authorize"
            + "?client_id=\(id)"
            + "&scope=\(scope)"
            + "&display=touch"
            + "&response_type=token"
    }
}