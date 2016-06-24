//
//  VkItemSyncModel.swift
//  VkAudio
//
//  Created by mac-224 on 23.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class VkModel: Object {
    
    dynamic var id: Int = 0
    dynamic var avatarUrl: String = ""
    
    var fullName: String{get {return ""}}
    var syncEnabled: Bool {
        get{if let db = SyncItemDB.sharedInstance {
            return db.isSyncEnabledForId(id)
            }
            return false
        }
        set{if let db = SyncItemDB.sharedInstance {
            db.setSyncEnabledForId(id, enabled: newValue)
            }}
    }
    var syncSeconds: NSDate {
        get {if let db = SyncItemDB.sharedInstance {
            return db.getSyncMillisForId(id)
            }
            return NSDate(timeIntervalSince1970: 0)
        }
        set {
            if let db = SyncItemDB.sharedInstance {
                db.setSyncMillisForId(id, millis: newValue)
            }
        }
    }
        
    override class func primaryKey() -> String? {
        return "id"
    }
    
    override class func ignoredProperties() -> [String] {
        return ["syncEnabled", "syncSeconds"]
    }
}