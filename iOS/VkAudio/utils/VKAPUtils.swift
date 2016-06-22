//
//  VKAPUtils.swift
//  VkAudio
//
//  Created by mac-224 on 22.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class VKAPUtils {
    
    static func formatProgress(seconds: Int) -> String {
        let minutes = seconds / 60
        let seconds = seconds % 60
        
        return String(format: "%0.2d:%0.2d", minutes, seconds)
    }
}