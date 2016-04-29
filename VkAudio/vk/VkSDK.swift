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
    
    var token: AccessToken?
    private var requestManager: RequestManager?
    
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
    
    static func authUrl(id: String, scope: String, version: String = "3.0") -> String {
        return "http://oauth.vk.com/authorize"
            + "?client_id=\(id)"
            + "&scope=\(scope)"
            + "&display=touch"
            + "&v=\(version)"
            + "&response_type=token"
    }
    
    func getAudios(onResult: (result: [Audio]) -> Void, onError: ((error: NSError) -> Void)? = nil){
        let apiMethod = "audio.get"
        let params = ["owner_id" : token!.userId, "access_token" : token!.token]
        
        requestManager?.get(apiMethod, parameters: params, onResult: { (result) in
            var audios = [Audio]()
            for response in result {
                let audio = Audio(apiResponse: response as! [String: AnyObject])
                audios.append(audio)
            }
            onResult(result: audios)
        }, onError: onError)
    }
}