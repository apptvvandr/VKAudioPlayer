//
//  SyncItemDB.swift
//  VkAudio
//
//  Created by mac-224 on 24.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class SyncItemDB {
    
    private static let BASE_NAME = "vkap_sync_list"
    static var sharedInstance: SyncItemDB?
    
    private var realm: Realm
    
    private init(userId: Int) {
        var realmConfig = Realm.Configuration()
        realmConfig.deleteRealmIfMigrationNeeded = true
        
        let fileUrl = NSURL.fileURLWithPath(realmConfig.fileURL!.path!)
            .URLByDeletingLastPathComponent!
            .URLByAppendingPathComponent("\(SyncItemDB.BASE_NAME)_\(userId)")
            .URLByAppendingPathExtension("realm")
        
        realmConfig.fileURL = fileUrl
        realm = try! Realm(configuration: realmConfig)
    }
    
    static func setup(userId: Int) {
        sharedInstance = SyncItemDB(userId: userId)
    }
    
    func getAllIds() -> [Int]? {
        let syncItems = realm.objects(SyncItem.self)
        return syncItems.map{syncItem in syncItem.id}
    }
    
    func isSyncEnabledForId(id: Int) -> Bool {
        return realm.objectForPrimaryKey(SyncItem.self, key: id) != nil
    }
    
    func setSyncEnabledForId(id: Int, enabled: Bool) {
        try! realm.write {
            if enabled {
                realm.add(SyncItem(value: ["id": id]), update: true)
            }
            else if let item = realm.objectForPrimaryKey(SyncItem.self, key: id) {
                realm.delete(item)
            }
        }
    }
    
    func getSyncMillisForId(id: Int) -> NSDate {
        if let item = realm.objectForPrimaryKey(SyncItem.self, key: id) {
            return item.lastSyncMillis
        }
        return NSDate(timeIntervalSince1970: 0)
    }
    
    func setSyncMillisForId(id: Int, millis: NSDate) {
        if let item = realm.objectForPrimaryKey(SyncItem.self, key: id) {
            try! realm.write({ 
                item.lastSyncMillis = millis
            })
        }
    }
    
    func update() {
        let items = realm.objects(SyncItem.self)
        for item in items {
            if !VkModelDB.sharedInstance!.contains(item.id) {
                setSyncEnabledForId(item.id, enabled: false)
            }
        }
    }
}