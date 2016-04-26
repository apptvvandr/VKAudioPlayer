//
//  Preferences.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class Preferences{
    
    private static let KEY_TOKEN_URL = "token_url"
    
    
    
    private static func getUserDefaults() -> NSUserDefaults{
        return NSUserDefaults.standardUserDefaults()
    }
}