//
//  Audio.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Audio: VkItem, AudioPlayerItem {

    var url: String?
    var artist: String?
    var name: String?
    var duration: Int?
    var ownerId: Int?

    override init(apiResponse: [String:AnyObject]) {
        super.init(apiResponse: apiResponse)

        id = apiResponse["aid"] as? Int
        url = apiResponse["url"] as? String
        artist = apiResponse["artist"] as? String
        name = apiResponse["title"] as? String
        duration = apiResponse["duration"] as? Int
        ownerId = apiResponse["owner_id"] as? Int
    }
}