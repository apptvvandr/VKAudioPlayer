//
//  Group.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Group: VKItem {

    var id: Int
    var name: String?
    var photoUrl: String?

    init(apiResponse: [String:AnyObject]) {
        id = apiResponse["gid"] as! Int
        name = apiResponse["name"] as? String
        photoUrl = apiResponse["photo_max_orig"] as? String
    }
}