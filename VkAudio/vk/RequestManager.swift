//
//  VkRequestManager.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import Alamofire

class RequestManager {
    
    let URL_REQUEST_BASE = "https://api.vk.com/method/"
    
    private let token: AccessToken
 
    init(token:AccessToken){
        self.token = token
    }
    
    func getAudios(onResult: (result: [AnyObject]) -> Void, onError: ((error: NSError) -> Void)? = nil) {
        let params = ["owner_id" : token.userId, "access_token" : token.token]
        let url = URL_REQUEST_BASE + "audio.get"
        
        Alamofire.request(.GET, url, parameters: params)
            .responseJSON{apiResponse in
                if let response = apiResponse.result.value {
                    let responseDict = response as! [String : AnyObject]
                    if let responseDictValues = responseDict["response"] as? [AnyObject]{
                        let responseValues = Array(responseDictValues[1..<responseDictValues.count])
                        onResult(result: responseValues)
                    }
                    else if let errorDictValues = responseDict["error"] {
                        let code = errorDictValues["error_code"] as! Int
                        let message = errorDictValues["error_msg"] as! String
                        onError?(error: NSError(domain: message, code: code, userInfo: nil))
                    }
                }
                else {
                    onError?(error: apiResponse.result.error!)
                }
        }
    }
}
