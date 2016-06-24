//
//  ManagedAudio.swift
//  VkAudio
//
//  Created by mac-224 on 22.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class ManagedAudio {
    
    var wasRemoved: Bool
    let id: Int
    
    init(id: Int) {
        self.id = id
        self.wasRemoved = false
    }
}