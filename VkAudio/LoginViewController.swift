//
//  ViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import AFNetworking

class LoginViewController: UIViewController, UIWebViewDelegate {
    
    internal static let SROTYBOARD_ID: String = "controller_login"
  
    @IBOutlet weak var webView: UIWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.delegate = self
        webView.loadRequest(NSURLRequest(URL: NSURL(string: VkSDK.URL_OAUTH)!))
    }
    
    func webViewDidFinishLoad(webView: UIWebView) {
        if let currentUrl = webView.request?.URL?.absoluteString where currentUrl.containsString("access_token"){
            VkSDK.setup(currentUrl)
            self.performSegueWithIdentifier("login_to_user_audios", sender: self)
        }
    }
}