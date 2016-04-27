//
//  ViewController.swift
//  VkAudio
//
//  Created by mac-224 on 26.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import Alamofire

class LoginViewController: UIViewController, UIWebViewDelegate {
    
    internal static let SROTYBOARD_ID: String = "controller_login"
  
    @IBOutlet weak var webView: UIWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.delegate = self
        webView.loadRequest(NSURLRequest(URL: NSURL(string: VkSDK.URL_OAUTH)!))
    }
    
    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        if let url = request.URL?.absoluteString where url.containsString("access_token="){
            VkSDK.setup(url)
            self.performSegueWithIdentifier(UserAudiosViewController.SEGUE_ID, sender: self)
            return false
        }
        return true
    }
}