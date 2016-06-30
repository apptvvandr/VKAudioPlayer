//
//  SimpleAlertView.swift
//  VkAudio
//
//  Created by mac-224 on 29.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class SimpleAlertView: UIAlertView, UIAlertViewDelegate {
    
    private var negativeButtonIndex: Int?
    private var positiveButtonIndex: Int?
    
    var onNegativeButtonClick: (() -> ())?
    var onPositiveButtonClick: (() -> ())?
    
    init(title: String, message: String? = nil,
         negativeButtonTitle: String? = nil, onNegativeButtonClick: (() -> ())? = nil,
         positiveButtonTitle: String? = nil, onPositiveButtonClick: (() -> ())? = nil) {
        
        super.init(frame: CGRectMake(0, 0, UIScreen.mainScreen().bounds.size.width, UIScreen.mainScreen().bounds.size.height))
        super.delegate = self
        
        self.title = title
        self.message = message
        if let negativeTitle = negativeButtonTitle {
            self.negativeButtonIndex = addButtonWithTitle(negativeTitle)
            self.onNegativeButtonClick = onNegativeButtonClick
        }
        if let positiveTitle = positiveButtonTitle {
            self.positiveButtonIndex = addButtonWithTitle(positiveTitle)
            self.onPositiveButtonClick = onPositiveButtonClick
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        if buttonIndex == negativeButtonIndex {
            onNegativeButtonClick?()
            return
        }
        if buttonIndex == positiveButtonIndex {
            onPositiveButtonClick?()
        }
    }
}