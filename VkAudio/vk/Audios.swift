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
                var audios = [Audio]()
                for i in 1 ..< result.count {
                    let audio = Audio(apiResponse: result[i] as! [String:AnyObject])
                    audios.append(audio)
                }
                onResult(result: audios)
            }, onError: onError)
        }
    }

}