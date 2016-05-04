//
//  Users.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

extension VkSDK {
    
    struct Users {
        
        static func getUserInfo(params: [String: AnyObject]? = nil, onResult: (result: [String: AnyObject]) -> Void, onError: ((error: NSError) -> Void)? = {error in print(error)}){
            VkSDK.instance?.get("users.get", parameters: params, onResult: { (result) in
                onResult(result: result[0] as! [String : AnyObject])
                }, onError: onError)
        }
        
    }
}