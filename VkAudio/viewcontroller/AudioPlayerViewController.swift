//
//  AudioPlayerViewController.swift
//  VkAudio
//
//  Created by mac-224 on 05.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import AVFoundation

class AudioPlayerViewController: UIViewController{
    
    var audio: Audio!
    var player: AVPlayer!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "\(audio.artist ?? "Unknown") - \(audio.name ?? "Unknown")"
        if let path = audio.url, url = NSURL(string: path){
            player = AVPlayer(URL: url)
            player.play()
        }
    }
}