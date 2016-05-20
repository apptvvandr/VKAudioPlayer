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
        
        static func addAudio(audioId: Int, ownerId: Int, onResult: ((result: Int?) -> Void)? = nil) {
            let params = ["audio_id": audioId, "owner_id": ownerId]
            VkSDK.instance?.get("audio.add", parameters: params, onResult: { (result) in
                print(result)
//                onResult?(result: result)
            })
        }
        
        static func removeAudio(audioId: Int, ownerId: Int, onResult: ((result: Int?) -> Void)? = nil) {
            let params = ["audio_id": audioId, "owner_id": ownerId]
            VkSDK.instance?.get("audio.delete", parameters: params, onResult: { (result) in
                print(result)
//                onResult?(result: result)
            })
        }
    }
}