//
//  OutsideController.swift
//  VkAudio
//
//  Created by mac-224 on 30.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class OutsideController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let backButton = UIButton(type: .Custom)
        backButton.frame = CGRectMake(0, 0, 40, 40)
        backButton.setTitle("Back", forState: .Normal)
        backButton.addTarget(self, action: #selector(OutsideController.onBackPressed), forControlEvents: .TouchUpInside)
        
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(customView: backButton)
    }
    
    @objc func onBackPressed() {
        let storyboard = UIStoryboard(name: "Main", bundle: NSBundle.mainBundle())
        let settingsController = storyboard.instantiateViewControllerWithIdentifier("controller_main")
        self.presentViewController(settingsController, animated: true, completion: nil)
    }
    
    static func present(fromController parent: UIViewController, identified id: String) {
        let storyboard = UIStoryboard(name: "Main", bundle: NSBundle.mainBundle())
        let controller = storyboard.instantiateViewControllerWithIdentifier(id)
        parent.presentViewController(controller, animated: true, completion: nil)
    }
}