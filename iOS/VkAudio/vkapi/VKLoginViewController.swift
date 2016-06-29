//
//  VKLoginViewController.swift
//  VkAudio
//
//  Created by mac-224 on 29.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class VKLoginViewController: UIViewController, UIWebViewDelegate {

    @IBOutlet weak var webView: UIWebView!
    
    var appId: String?
    var appScope: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.delegate = self
        
        let authUrl = VKApi.authUrl(appId!, scope: appScope!)
        webView.loadRequest(NSURLRequest(URL: NSURL(string: authUrl)!))
    }
    
    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        if let url = request.URL?.absoluteString where url.containsString("access_token=") {
            let tokenSubstring = url.componentsSeparatedByString("access_token")[1]
            let accessValuesStrings: [String] = tokenSubstring.componentsSeparatedByString("&")
            
            var accessValues = [String]()
            for (index, value) in accessValuesStrings.enumerate() {
                let accessValue = value.componentsSeparatedByString("=")[1]
                accessValues.insert(accessValue, atIndex: index)
            }
            VKApi.setup(accessValues[0], tokenExpireIn: accessValues[1], userId: accessValues[2])
            VKApi.setup();
            VKApiDefaults.setLastLoginMillis(Double(NSDate().timeIntervalSince1970 * 1000))
            onLoginResult(accessValues[0], tokenExpiersIn: Double(accessValues[1])!, userId: Int(accessValues[2])!)
            
            return false
        }
        return true
    }
    
    func onLoginResult(token: String, tokenExpiersIn: Double, userId: Int) {
    }
    
    func onLoginError() {
    }
}
