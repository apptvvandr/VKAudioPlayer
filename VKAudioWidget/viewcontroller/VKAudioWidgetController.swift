//
//  TodayViewController.swift
//  VKAudioWidget
//
//  Created by mac-224 on 16.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import UIKit
import NotificationCenter

class VKAudioWidgetController: UIViewController, NCWidgetProviding {
    
    @IBOutlet weak var labelCurrentAudio: UILabel!
    @IBOutlet weak var btnPlay: UIButton!
    @IBOutlet weak var btnNext: UIButton!

    var isPlaying = false
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        AudioPlayerEventHandler.subscribeForPlayerEvent(.STATE_CHANGED, sender: .CONTROLLER) { (message: Bool) in
            self.isPlaying = message
            self.updatePlayButtonWithData(message)
        }
        
        AudioPlayerEventHandler.subscribeForPlayerEvent(.AUDIO_CHANGED, sender: .CONTROLLER) { (message: [String: AnyObject]) in
            let artist = message["artist"] as? String
            let name = message["name"] as? String
            self.updateAudioInfoWithData(artist, name: name)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(VKAudioWidgetController.onWidgetClicked(_:))))
    }
    
    func onWidgetClicked(sender: UITapGestureRecognizer) {
        let appUrl = NSURL(string: "vkaudio://context-widget")!
        self.extensionContext?.openURL(appUrl, completionHandler: nil)
    }
    
    @IBAction func onPlayButtonClicked(sender: AnyObject) {
        AudioPlayerEventHandler.sendPlayerStateChangedEvent(!isPlaying, sender: .WIDGET)
        
        isPlaying = !isPlaying
        updatePlayButtonWithData(!isPlaying)
    }
    
    @IBAction func onNextButtonClicked(sender: AnyObject) {
        AudioPlayerEventHandler.sendNextAudioEvent(.WIDGET)
    }
    
    private func updateAudioInfoWithData(artist: String?, name: String?) {
        labelCurrentAudio.text = "\(artist ?? "Unknown") - \(name ?? "Unnamed")"
    }
    
    private func updatePlayButtonWithData(isPlaying: Bool) {
        let playButtonImage = isPlaying ? "ic_pause" : "ic_play"
        btnPlay.setImage(UIImage(named: playButtonImage), forState: .Normal)
    }
}
