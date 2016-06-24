//
//  VkApi.swift
//  VkAudio
//
//  Created by mac-224 on 23.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class VkApi: VKApiDelegate {
    
    static var sharedInstance: VkApi?
    
    var baseUrl: String = "https://api.vk.com/method/"
    
    var token: String
    var tokenExpireIn: String
    var userId: String
    
    static func setup(urlWithToken: String) -> VkApi! {
        let tokenSubstring = urlWithToken.componentsSeparatedByString("access_token")[1]
        let accessValuesStrings: [String] = tokenSubstring.componentsSeparatedByString("&")
        
        var accessValues = [String]()
        for (index, value) in accessValuesStrings.enumerate() {
            let accessValue = value.componentsSeparatedByString("=")[1]
            accessValues.insert(accessValue, atIndex: index)
        }
        sharedInstance = VkApi(token: accessValues[0], tokenExpiresIn: accessValues[1], userId: accessValues[2])
        
        return sharedInstance!
    }
    
    private init(token: String, tokenExpiresIn: String, userId: String) {
        self.token = token
        self.tokenExpireIn = tokenExpiresIn
        self.userId = userId
    }
    
    func getRequest<T>(method: String, params: [String : AnyObject]?, callback: VkApiCallback<T>?) {
        var requestParams: [String: AnyObject] = ["access_token": token]
        if let params = params {
           requestParams += params
        }
        
        Alamofire.request(.GET, baseUrl + method, parameters: requestParams)
            .responseJSON { (response: Response<AnyObject, NSError>) in
                switch response.result {
                case .Success(let result):
                    guard let responseDictionary = result as? [String: AnyObject] else {
                        return
                    }
                    if let responseValues = responseDictionary["response"] as? T {
                        callback?.onResult(result: responseValues)
                    }
                    else if let errorValues = responseDictionary["error"] {
                        guard let code = errorValues["error_code"] as? Int, let message = errorValues["error_msg"] as? String else {
                            return
                        }
                        callback?.onError?(error: NSError(domain: message, code: code, userInfo: nil))
                    }
                case .Failure(let error):
                    callback?.onError?(error: error)
                }
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