//
//  ViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UIWebViewDelegate {

    @IBOutlet weak var webView: UIWebView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.delegate = self

        let plistPath = NSBundle.mainBundle().pathForResource("VKAPApplication", ofType: "plist")
        let appValues = NSDictionary(contentsOfFile: plistPath!)!

        let appId = appValues.objectForKey("APP_ID") as! String
        let appScope = appValues.objectForKey("APP_SCOPE") as! String

        let authUrl = VKApi.authUrl(appId, scope: appScope)
        webView.loadRequest(NSURLRequest(URL: NSURL(string: authUrl)!))
    }

    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        if let url = request.URL?.absoluteString where url.containsString("access_token=") {
            //MARK: - api setup
            //TODO: create VKLoginController for this
            let tokenSubstring = url.componentsSeparatedByString("access_token")[1]
            let accessValuesStrings: [String] = tokenSubstring.componentsSeparatedByString("&")
            
            var accessValues = [String]()
            for (index, value) in accessValuesStrings.enumerate() {
                let accessValue = value.componentsSeparatedByString("=")[1]
                accessValues.insert(accessValue, atIndex: index)
            }
            VKApi.setup(accessValues[0], tokenExpireIn: accessValues[1], userId: accessValues[2])
            
            //MARK: - VKAP setup
            VkModelDB.setup(VKApi.userId!)
            SyncItemDB.setup(VKApi.userId!)
            VKAPUserDefaults.setLastLoginMillis(NSDate.currentTimeMillis())

            VKAPServiceImpl.sharedInstance.getUserInfo(VKApi.userId!, callback: VKApiCallback(
                onResult: { (result: UserModel) in
                    result.syncEnabled = true
                    VkModelDB.sharedInstance?.put(result)
                    self.performSegueWithIdentifier("segue_tabs", sender: self)
            }))
            return false
        }
        return true
    }
}