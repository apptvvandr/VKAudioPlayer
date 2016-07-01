//
//  User.swift
//  VkAudio
//
//  Created by mac-224 on 28.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class User: VKItem {
    
    var id: Int
    var firstName: String?
    var lastName: String?
    var isOnline: Bool?
    var photoUrl: String?
    
    init(apiResponse: [String:AnyObject]) {
        id = apiResponse["id"] as! Int
        firstName = apiResponse["first_name"] as? String
        lastName = apiResponse["last_name"] as? String
        photoUrl = apiResponse["photo_max_orig"] as? String
    }
}