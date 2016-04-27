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
    
    private func sendApiGetRequest(apiMethod: String, params: [String: AnyObject]?,
                                   onResult: (result: [AnyObject]) -> Void, onError: ((error: NSError) -> Void)?){
        
        Alamofire.request(.GET, URL_REQUEST_BASE + apiMethod, parameters: params)
            .responseJSON {
                response in
                if let responseValues = response.result.value {
                    let responseDict = responseValues as! [String: AnyObject]
                    let responseData = responseDict["response"] as! [AnyObject]
                    onResult(result: Array(responseData[1..<responseData.count]))
                }
                else if onError != nil {
                    onError!(error: NSError(coder: NSCoder())!)
                }
            }
    }
    
    func getAudios(onResult: (result: [AnyObject]) -> Void) {
        let params = ["owner_id" : token.userId, "access_token" : token.token]
        sendApiGetRequest("audio.get", params: params, onResult: onResult) { (error) in
            //todo: handle error
        }
    }
}