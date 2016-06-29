//
//  LocalStorage.swift
//  VkAudio
//
//  Created by mac-224 on 11.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class LocalStorage {
    
    static func buildFilePath(dir: NSSearchPathDirectory, fileName: String) -> String {
        let url = NSFileManager.defaultManager().URLsForDirectory(dir, inDomains: .UserDomainMask)[0]
        let path = url.URLByAppendingPathComponent(fileName)
        
        return path.path!
    }
    
    static func removeFile(dir: NSSearchPathDirectory, fileName: String) {
        let path = buildFilePath(dir, fileName: fileName)
        try! NSFileManager.defaultManager().removeItemAtURL(NSURL(string: "file://\(path)")!)
    }
    
    static func fileExists(dir: NSSearchPathDirectory, fileName: String) -> Bool {
        let path = buildFilePath(dir, fileName: fileName)
        
        return NSFileManager.defaultManager().fileExistsAtPath(path)
    }
}