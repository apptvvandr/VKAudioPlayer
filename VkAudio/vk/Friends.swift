//
//  Friends.swift
//  VkAudio
//
//  Created by mac-224 on 02.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

extension VkSDK {

    struct Frinds {

        static func getFriends(params: [String:AnyObject]? = nil, onResult: (result:[Friend]) -> Void, onError: ((error:NSError) -> Void)? = {
            error in print(error)
        }) {
            VkSDK.instance?.get("friends.get", parameters: params, onResult: {
                (result) in
                var friends = [Friend]()
                for apiResponse in result {
                    let audio = Friend(apiResponse: apiResponse as! [String:AnyObject])
                    friends.append(audio)
                }
                onResult(result: friends)
            }, onError: onError)
        }

    }

}