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

class VKAPService: VKAPServiceDelegate {
    
    static var sharedInstance: VKAPService?
    var api: VKApiDelegate
    
    static func setup(api: VKApiDelegate) -> VKAPService! {
        sharedInstance = VKAPService(api: api)
        
        return sharedInstance!
    }
    
    private init(api: VKApiDelegate) {
        self.api = api
    }
    
    func getAudios(ownerId: Int?, callback: VkApiCallback<[Audio]>?) {
        let requestParams: [String: AnyObject]? = ownerId != nil ? ["owner_id": ownerId!] : nil
        
        api.getRequest("audio.get", params: requestParams, callback: VkApiCallback(
            onResult: { (result: [AnyObject]) in
                let audios = result.flatMap { $0 as? [String: AnyObject] }.map { Audio(apiResponse: $0) }
                callback?.onResult(result: audios)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getGroups(callback: VkApiCallback<[Group]>?) {
        let requestParams: [String: AnyObject] = ["extended": 1, "fields": "photo_max_orig"]
        
        api.getRequest("groups.get", params: requestParams, callback: VkApiCallback(
            onResult: { (result: [AnyObject]) in
                let groups = result.flatMap { $0 as? [String: AnyObject] }.map { Group(apiResponse: $0) }
                callback?.onResult(result: groups)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getFriends(callback: VkApiCallback<[Friend]>?) {
        let requestParams: [String: AnyObject] = ["fields": "name, photo_max_orig"]
        
        api.getRequest("friends.get", params: requestParams, callback: VkApiCallback(
            onResult: { (result: [AnyObject]) in
                let users = result.flatMap { $0 as? [String: AnyObject] }.map { Friend(apiResponse: $0) }
                callback?.onResult(result: users)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func getUserPhoto(callback: VkApiCallback<UIImage>?) {
        let requestParams: [String: AnyObject] = ["fields": "photo_max_orig"]
        
        api.getRequest("users.get", params: requestParams, callback: VkApiCallback(
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

    func addAudio(audioId: Int, ownerId: Int, callback: VkApiCallback<Int>?) {
        let params: [String: AnyObject] = ["audio_id": audioId, "owner_id": ownerId]
        
        api.getRequest("audio.add", params: params, callback: VkApiCallback(onResult: { (result: AnyObject) in
                callback?.onResult(result: result as! Int)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }

    func removeAudio(audioId: Int, ownerId: Int, callback: VkApiCallback<Int>?) {
        let params: [String: AnyObject] = ["audio_id": audioId, "owner_id": ownerId]

        api.getRequest("audio.delete", params: params, callback: VkApiCallback(
            onResult: { (result: AnyObject) in
                callback?.onResult(result: result as! Int)
            },
            onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
    
    func restoreAudio(id: Int, ownerId: Int, callback: VkApiCallback<Audio>?) {
        let params: [String: AnyObject] = ["audio_id": id, "owner_id": ownerId]
        
        api.getRequest("audio.restore", params: params, callback: VkApiCallback(onResult: { (result: AnyObject) in
            let audio = Audio(apiResponse: result as! [String: AnyObject])
            callback?.onResult(result: audio)
            }, onError: { (error) in
                callback?.onError?(error: error)
        }))
    }
}