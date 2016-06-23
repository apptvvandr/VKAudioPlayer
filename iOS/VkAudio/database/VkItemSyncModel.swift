//
//  VkItemSyncModel.swift
//  VkAudio
//
//  Created by mac-224 on 23.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class VkItemSyncModel: Object {
    
    dynamic var id: Int = 0
    dynamic var name: String = ""
    dynamic var avatarUrl: String = ""
    dynamic var isSyncEnabled: Bool = false
    dynamic var syncSeconds: NSDate = NSDate(timeIntervalSinceNow: 0)
    
    var fullName: String {get{return self.name}}
        
    override class func primaryKey() -> String? {
        return "id"
    }
    
    override class func ignoredProperties() -> [String] { return ["fullName"] }
}