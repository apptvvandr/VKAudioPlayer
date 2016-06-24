//
//  VkItemSyncModelDB.swift
//  VkAudio
//
//  Created by mac-224 on 23.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class VkModelDB {
    
    private static let BASE_NAME = "vkap_vk_items"
    
    static var sharedInstance: VkModelDB?
    private var realm: Realm
    
    private init(userId: Int) {
        var realmConfig = Realm.Configuration()
        realmConfig.deleteRealmIfMigrationNeeded = true
        
        let fileUrl = NSURL.fileURLWithPath(realmConfig.fileURL!.path!)
            .URLByDeletingLastPathComponent!
            .URLByAppendingPathComponent("\(VkModelDB.BASE_NAME)_\(userId)")
            .URLByAppendingPathExtension("realm")

        realmConfig.fileURL = fileUrl
        realm = try! Realm(configuration: realmConfig)
    }
    
    static func setup(userId: Int) {
        sharedInstance = VkModelDB(userId: userId)
    }
    
    func put <T: VkModel> (object: T) {
        try! realm.write {
            realm.add(object, update: true)
        }
    }
    
    func update <T: VkModel> (objects: [T]) {
        try! realm.write {
            realm.delete(realm.objects(T))
            realm.add(objects, update: true)
        }
    }
    
    func get(id: Int) -> VkModel? {
        if let friend = getWithType(FriendModel.self, id: id) {
            return friend
        }
        if let group = getWithType(GroupModel.self, id: id) {
            return group
        }
        return nil
    }
    
    func getWithType <T: VkModel> (type: T.Type, id: Int) -> T? {
        return realm.objectForPrimaryKey(T.self, key: id)
    }
    
    func getAll() -> [VkModel] {
        var objects = [VkModel]()
        
        let groups: [GroupModel] = getAllWithType(GroupModel.self)
        let friends: [FriendModel] = getAllWithType(FriendModel.self)
        
        objects.appendContentsOf(groups.map{$0 as VkModel})
        objects.appendContentsOf(friends.map{$0 as VkModel})
        
        return objects
    }
    
    func getAllWithType <T: VkModel> (type: T.Type) -> [T] {
        return Array(realm.objects(T.self))
    }
    
    func contains(id: Int) -> Bool {
        return containsWithType(GroupModel.self, id: id) || containsWithType(FriendModel.self, id: id)
    }
    
    func containsWithType <T: VkModel> (type: T.Type, id: Int) -> Bool {
        return getWithType(type, id: id) != nil
    }
    
    func path() -> String {
        return realm.configuration.fileURL!.path!
    }
}