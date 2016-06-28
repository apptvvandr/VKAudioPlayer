//
//  VKAP.swift
//  VkAudio
//
//  Created by mac-224 on 24.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

protocol VKAPService {    
    func getAudios(ownerId: Int?, callback: VKApiCallback<[AudioModel]>?)
    func getGroups(callback: VKApiCallback<[GroupModel]>?)
    func getFriends(callback: VKApiCallback<[FriendModel]>?)
    func getUserInfo(userId: Int, callback: VKApiCallback<UserModel>?)
    
    func addAudio(audioId: Int, ownerId: Int, callback: VKApiCallback<Int>?)
    func removeAudio(audioId: Int, ownerId: Int, callback: VKApiCallback<Int>?)
    func restoreAudio(id: Int, ownerId: Int, callback: VKApiCallback<AudioModel>?)
}