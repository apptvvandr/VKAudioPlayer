//
//  Audios.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

extension VkSDK {

    struct Audios {

        static func getAudios(params: [String:AnyObject]? = nil, onResult: (result:[Audio]) -> Void, onError: ((error:NSError) -> Void)? = {
            error in print(error)
        }) {
            VkSDK.instance?.get("audio.get", parameters: params, onResult: {
                (result) in
                let audios = result.flatMap { $0 as? [String: AnyObject] }.map { Audio(apiResponse: $0) }
                onResult(result: audios)
            }, onError: onError)
        }
    }

}