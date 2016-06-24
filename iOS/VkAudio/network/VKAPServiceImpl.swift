//
//  VKAPApi.swift
//  VkAudio
//
//  Created by mac-224 on 23.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class VKAPServiceImpl: VKAPService {
    
    static var sharedInstance: VKAPServiceImpl?
    var api: VKApi
    
    static func setup(api: VKApi) -> VKAPService! {
        sharedInstance = VKAPServiceImpl(api: api)
        return sharedInstance!
    }
    
    private init(api: VKApi) {
        self.api = api
    }
    
    func getAudios(ownerId: Int?, callback: VKApiCallback<[AudioModel]>?) {
        let requestParams: [String: AnyObject]? = ownerId != nil ? ["owner_id": ownerId!] : nil
        
        api.get("audio.get", params: requestParams, callback: VKApiCallback(
            onResult: { (result: [AnyObject]) in
                let audioDtos = result.flatMap { $0 as? [String: AnyObject] }.map { Audio(apiResponse: $0) }
                let audioModels = audioDtos.map{dto in
                    AudioModel(id: dto.id, ownerId: dto.ownerId!, url: dto.url!, duration: dto.duration!, artist: dto.artist!, name: dto.name!)}
                callback?.onResult(result: audioModels)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getGroups(callback: VKApiCallback<[GroupModel]>?) {
        let requestParams: [String: AnyObject] = ["extended": 1, "fields": "photo_max_orig"]
        
        api.get("groups.get", params: requestParams, callback: VKApiCallback(
            onResult: { (result: [AnyObject]) in
                let groupsDtos = result.flatMap { $0 as? [String: AnyObject] }.map { Group(apiResponse: $0) }
                let groupModels = groupsDtos.map{dto in
                    GroupModel(value: ["id": dto.id, "name": dto.name!, "avatarUrl": dto.photoUrl!])}
                callback?.onResult(result: groupModels)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getFriends(callback: VKApiCallback<[FriendModel]>?) {
        let requestParams: [String: AnyObject] = ["fields": "name, photo_max_orig"]
        
        api.get("friends.get", params: requestParams, callback: VKApiCallback(
            onResult: { (result: [AnyObject]) in
                let friendDtos = result.flatMap { $0 as? [String: AnyObject] }.map { Friend(apiResponse: $0) }
                let friendModels = friendDtos.map{dto in
                    FriendModel(value: ["id": dto.id, "firstName": dto.firstName!, "lastName": dto.lastName!, "avatarUrl": dto.photoUrl!])}
                callback?.onResult(result: friendModels)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getUserPhoto(callback: VKApiCallback<UIImage>?) {
        let requestParams: [String: AnyObject] = ["fields": "photo_max_orig"]
        
        api.get("users.get", params: requestParams, callback: VKApiCallback(
            onResult: { (result: [AnyObject]) in
                guard let resultDict  = result[0] as? [String : AnyObject], let url = NSURL(string: resultDict["photo_max_orig"] as! String) else {
                    return
                }
            
                let localPath = LocalStorage.buildFilePath(.DocumentDirectory, fileName: "bg_image.jpg")
                Alamofire.download(.GET, url, destination: { (temporaryURL, response) in
                    return NSURL(string: "file://\(localPath)")!
                }).response(completionHandler: { (request, response, data, error) in
                    let image = UIImage(contentsOfFile: localPath)
                    callback?.onResult(result: image!)
                })
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }

    func addAudio(audioId: Int, ownerId: Int, callback: VKApiCallback<Int>?) {
        let params: [String: AnyObject] = ["audio_id": audioId, "owner_id": ownerId]
        
        api.get("audio.add", params: params, callback: VKApiCallback(onResult: { (result: AnyObject) in
                callback?.onResult(result: result as! Int)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }

    func removeAudio(audioId: Int, ownerId: Int, callback: VKApiCallback<Int>?) {
        let params: [String: AnyObject] = ["audio_id": audioId, "owner_id": ownerId]

        api.get("audio.delete", params: params, callback: VKApiCallback(
            onResult: { (result: AnyObject) in
                callback?.onResult(result: result as! Int)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func restoreAudio(id: Int, ownerId: Int, callback: VKApiCallback<AudioModel>?) {
        let params: [String: AnyObject] = ["audio_id": id, "owner_id": ownerId]
        
        api.get("audio.restore", params: params, callback: VKApiCallback(onResult: { (result: AnyObject) in
            let dto = Audio(apiResponse: result as! [String: AnyObject])
            let audioModel = AudioModel(id: dto.id, ownerId: dto.ownerId!, url: dto.url!, duration: dto.duration!, artist: dto.artist!, name: dto.name!)
            callback?.onResult(result: audioModel)
            }, onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
}