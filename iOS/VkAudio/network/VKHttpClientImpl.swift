//
//  VKHttpClientImpl.swift
//  VkAudio
//
//  Created by mac-224 on 28.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import Alamofire

class VKHttpClientImpl: VKHttpClient {
    
    static let sharedInstance = VKHttpClientImpl()
    
    private init() {}
    
    var baseUrl: String = "https://api.vk.com/method/"
    
    func get<T>(method: String, params: [String : AnyObject]?, callback: VKApiCallback<T>?) {
        var requestParams: [String: AnyObject] = ["access_token": VKApi.token!]
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
}