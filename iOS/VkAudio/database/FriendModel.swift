//
//  FriendModel.swift
//  VkAudio
//
//  Created by mac-224 on 23.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class FriendModel: VkItemSyncModel {
    
    var firstName: String = ""
    var lastName: String = ""

    override var fullName: String {get {return "\(self.firstName) \(self.lastName)"}}

    override class func ignoredProperties() -> [String] { return ["firstName, lastName"] }
}
