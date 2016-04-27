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
    
    func getAudios(onResult: (result: [AnyObject]) -> Void) {
        let params = ["owner_id" : token.userId, "access_token" : token.token]
        
        Alamofire.request(.GET, URL_REQUEST_BASE + "audio.get", parameters: params)
            .responseJSON{ response in
                let dataDict = response.result.value as! [String : AnyObject]
                if let serverData = dataDict["response"] as? [AnyObject] {
                    let data = Array(serverData[1..<serverData.count])
                    onResult(result: data)
                }
        }
    }
}
