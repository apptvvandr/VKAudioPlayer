//
//  VkApiImpl.swift
//  VkAudio
//
//  Created by mac-224 on 23.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

protocol VKHttpClient {

    var baseUrl: String { get }
    
    func get<T>(method: String, params: [String : AnyObject]?, callback: VKApiCallback<T>?)
    
}

protocol VKItem {
    var id: Int {get}
}

class VKApiCallback<T> {
    var onResult: (result: T) -> Void
    var onError: ((error: NSError) -> Void)?
    
    init(onResult: (result: T) -> Void, onError: ((error: NSError) -> Void)? = nil) {
        self.onResult = onResult
        self.onError = onError
    }
}