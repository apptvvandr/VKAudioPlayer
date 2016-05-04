//
//  VkSDK.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import Alamofire

private var _instance: VkSDK?

class VkSDK {

    internal static let PREFS_KEY_TOKEN_URL = "token_url"
    let URL_REQUEST_BASE = "https://api.vk.com/method/"

    var token: AccessToken?

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

    private init?(urlWithToken: String) {
        if let token = AccessToken(url: urlWithToken) {
            self.token = token
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

    func get(apiMethod: String, parameters: [String:AnyObject]? = nil, onResult: (result:[AnyObject]) -> Void, onError: ((error:NSError) -> Void)? = {
        error in print(error)
    }) {
        var requestParams: [String:AnyObject] = ["access_token": token!.token]
        if let params = parameters {
            requestParams += params
        }

        Alamofire.request(.GET, URL_REQUEST_BASE + apiMethod, parameters: requestParams)
        .responseJSON {
            apiResponse in
            if let response = apiResponse.result.value {
                let responseDict = response as! [String:AnyObject]
                if let responseDictValues = responseDict["response"] as? [AnyObject] {
                    onResult(result: Array(responseDictValues))
                } else if let errorDictValues = responseDict["error"] {
                    let code = errorDictValues["error_code"] as? Int
                    let message = errorDictValues["error_msg"] as? String

                    if code != nil && message != nil {
                        onError?(error: NSError(domain: message!, code: code!, userInfo: nil))
                    }
                }
            } else {
                onError?(error: apiResponse.result.error!)
            }
        }
    }
}

func +=<Key, Value>(inout left: [Key:Value], right: [Key:Value]) {
    for (key, value) in right {
        left[key] = value
    }
}