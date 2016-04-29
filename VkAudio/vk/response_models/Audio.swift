//
//  Audio.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Audio {
    
    var id: Int?
    var url: String?
    var artist: String?
    var name: String?
    
    init(apiResponse: [String: AnyObject]){
        id = apiResponse["aid"] as? Int
        url = apiResponse["url"] as? String
        artist = apiResponse["artist"] as? String
        name = apiResponse["title"] as? String
    }
}