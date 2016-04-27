//
//  VkSDK.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

private var _instance: VkSDK?
class VkSDK {
    
    internal static let PREFS_KEY_TOKEN_URL = "token_url"
    
    private static let APP_ID = 5424227
    private static let APP_SECURE = "xnzb7tqjzyyFou43nUQ3"
    private static let APP_SCOPE = "audio"
    
    static let URL_OAUTH: String = "http://oauth.vk.com/authorize"
        + "?client_id=" + "\(APP_ID)"
        + "&scope=" + "\(APP_SCOPE)"
        + "&display=touch"
        + "&response_type=token"
    
    var token: AccessToken?
    var requestManager: RequestManager?
    
    class var instance: VkSDK? {
        if _instance == nil {
            if let urlWithToken = NSUserDefaults.standardUserDefaults().stringForKey(PREFS_KEY_TOKEN_URL) {
                return VkSDK.setup(urlWithToken)
            }
        }
        return _instance
    }
    
    class func setup(urlWithToken: String) -> VkSDK? {
        struct Static {
            static var onceToken: dispatch_once_t = 0
        }
        dispatch_once(&Static.onceToken) {
            _instance = VkSDK(urlWithToken: urlWithToken)
        }
        return _instance!
    }
    
    private init?(urlWithToken: String){
        if let token = AccessToken(url: urlWithToken){
            self.token = token
            self.requestManager = RequestManager(token: token)
            return
        }
        return nil
    }
}