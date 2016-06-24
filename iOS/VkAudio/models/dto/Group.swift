//
//  Group.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Group: VkItem {

    var name: String?
    var photoUrl: String?

    override init(apiResponse: [String:AnyObject]) {
        super.init(apiResponse: apiResponse)

        id = apiResponse["gid"] as? Int
        name = apiResponse["name"] as? String
        photoUrl = apiResponse["photo_max_orig"] as? String
    }
}