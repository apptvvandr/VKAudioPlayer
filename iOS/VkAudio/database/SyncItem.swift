//
//  SyncItem.swift
//  VkAudio
//
//  Created by mac-224 on 24.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class SyncItem: Object {
    
    dynamic var id: Int = 0
    dynamic var lastSyncMillis: NSDate = NSDate(timeIntervalSince1970: 0)
    
    override class func primaryKey() -> String? { return "id" }
}
