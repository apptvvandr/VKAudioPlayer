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
    
    func getAudios(ownerId: String?) -> String {
        let requestUrl: String = URL_REQUEST_BASE + "audio.get" + "?uid=\(token.userId)" + "&access_token=\(token.token)"
        
        
        return requestUrl
    }
}