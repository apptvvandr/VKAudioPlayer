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

        let plistPath = NSBundle.mainBundle().pathForResource("VkApp", ofType: "plist")
        let appValues = NSDictionary(contentsOfFile: plistPath!)!

        let appId = appValues.objectForKey("APP_ID") as! String
        let appScope = appValues.objectForKey("APP_SCOPE") as! String
        let appApiVersion = appValues.objectForKey("API_VERSION") as! String

        let authUrl = VkSDK.authUrl(appId, scope: appScope, version: appApiVersion)
        webView.loadRequest(NSURLRequest(URL: NSURL(string: authUrl)!))
    }

    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        if let url = request.URL?.absoluteString where url.containsString("access_token=") {
            VkSDK.setup(url)

            performSegueWithIdentifier("segue_tabs", sender: self)

            return false
        }
        return true
    }
}