//
//  VkApiImpl.swift
//  VkAudio
//
//  Created by mac-224 on 23.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

protocol VKApiDelegate {
    
    var baseUrl: String { get }
    var token: String { get }
    var tokenExpireIn: String { get }
    var userId: String { get }

    func getRequest<T>(method: String, params: [String: AnyObject]?, callback: VkApiCallback<T>?)
    
    static func authUrl(id: String, scope: String) -> String
}

class VkApiCallback<T> {
    var onResult: (result: T) -> Void
    var onError: ((error: NSError) -> Void)?
    
    init(onResult: (result: T) -> Void, onError: ((error: NSError) -> Void)? = nil) {
        self.onResult = onResult
        self.onError = onError
    }
}

protocol VKAPServiceDelegate {
    var api: VKApiDelegate { get }
    
    func getAudios(ownerId: Int?, callback: VkApiCallback<[Audio]>?)
    func getGroups(callback: VkApiCallback<[Group]>?)
    func getFriends(callback: VkApiCallback<[Friend]>?)
    func getUserPhoto(callback: VkApiCallback<UIImage>?)
    
    func addAudio(audioId: Int, ownerId: Int, callback: VkApiCallback<Int>?)
    func removeAudio(audioId: Int, ownerId: Int, callback: VkApiCallback<Int>?)
    func restoreAudio(id: Int, ownerId: Int, callback: VkApiCallback<Audio>?)
}