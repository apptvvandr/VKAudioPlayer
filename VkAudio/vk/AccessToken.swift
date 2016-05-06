//
//  AccessToken.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class AccessToken {

    var token: String
    var expiredIn: Int64
    var userId: String
    var wasRequested: Int64

    init(token: String, expiredIn: Int64, userId: String, wasRequested: Int64) {
        self.token = token
        self.expiredIn = expiredIn
        self.userId = userId
        self.wasRequested = wasRequested
    }

    convenience init?(url: String!) {
        if !url.containsString("access_token") {
            return nil
        }

        let tokenSubstring = url.componentsSeparatedByString("access_token")[1]
        let accessValuesStrings: [String] = tokenSubstring.componentsSeparatedByString("&")

        var accessValues = [String]()
        for (index, value) in accessValuesStrings.enumerate() {
            let accessValue = value.componentsSeparatedByString("=")[1]
            accessValues.insert(accessValue, atIndex: index)
        }

        let preferences = NSUserDefaults.standardUserDefaults();
        preferences.setObject(url, forKey: VkSDK.PREFS_KEY_TOKEN_URL)

        self.init(token: accessValues[0], expiredIn: Int64(accessValues[1])!, userId: accessValues[2], wasRequested: NSDate.currentTimeMillis())
    }
}

extension NSDate {
    static func currentTimeMillis() -> Int64 {
        return Int64(NSDate().timeIntervalSince1970 * 1000)
    }
}
