//
//  AudioPlayerViewController.swift
//  VkAudio
//
//  Created by mac-224 on 05.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class AudioPlayerViewController: UIViewController{
    
    var audio: Audio!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "\(audio.artist ?? "Unknown") - \(audio.name ?? "Unknown")"
    }
}