//
//  GroupModel.swift
//  VkAudio
//
//  Created by mac-224 on 23.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class GroupModel: VkModel {
    
    dynamic var name: String = ""
    
    override var fullName: String {get{return name}}
}
