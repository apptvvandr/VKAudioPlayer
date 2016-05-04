//
//  Friend.swift
//  VkAudio
//
//  Created by mac-224 on 02.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Friend: VkItem {
    
    var firstName: String?
    var lastName: String?
    var isOnline: Bool?
    var photoUrl: String?
 
    override init(apiResponse: [String: AnyObject]){
        super.init(apiResponse: apiResponse)

        id = apiResponse["uid"] as? Int
        firstName = apiResponse["first_name"] as? String
        lastName = apiResponse["last_name"] as? String
        isOnline = apiResponse["online"] as? Int == 1
        photoUrl = apiResponse["photo_100"] as? String
    }
}