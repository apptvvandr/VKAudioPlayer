//
//  Groups.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

extension VkSDK {

    struct Groups {

        static func getGroups(params: [String:AnyObject]? = nil, onResult: (result:[Group]) -> Void, onError: ((error:NSError) -> Void)? = {
            error in print(error)
        }) {
            VkSDK.instance?.get("groups.get", parameters: params, onResult: {
                (result) in
                let groups = result.flatMap { $0 as? [String: AnyObject] }.map { Group(apiResponse: $0) }
                onResult(result: groups)
            }, onError: onError)
        }
    }

}