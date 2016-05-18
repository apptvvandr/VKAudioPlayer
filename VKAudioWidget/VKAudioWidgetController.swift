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
    
    private var artist: String?
    private var name: String?
    private var isPlaying: Bool! = false
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        AudioPlayerEventHandler.subscribeForPlayerEvent(AudioPlayerEventHandler.PlayerEvent.PLAYER_STATE_CHANGED) { (message: Bool) in
            self.setPlaying(message)
        }
        
        AudioPlayerEventHandler.subscribeForPlayerEvent(AudioPlayerEventHandler.PlayerEvent.AUDIO_CHANGED) { (message: [String: AnyObject]) in
            let artist = message["artist"] as? String
            let name = message["name"] as? String
            
            self.setPlaying(true)
            self.updateAudioInfoWithData(artist, name: name)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(VKAudioWidgetController.onContainerClicked(_:))))
    }
    
    func onContainerClicked(sender: UITapGestureRecognizer) {
        self.extensionContext?.openURL(NSURL(string: "vkaudio://context-widget")!, completionHandler: nil)
    }
    
    @IBAction func onPlayButtonClicked(sender: AnyObject) {
        setPlaying(!isPlaying)
        AudioPlayerEventHandler.sendPlayerStateChangedEvent(isPlaying)
    }
    
    @IBAction func onNextButtonClicked(sender: AnyObject) {
        AudioPlayerEventHandler.sendNextAudioEvent()
    }
    
    private func updateAudioInfoWithData(artist: String?, name: String?) {
        labelCurrentAudio.text = "\(artist ?? "Unknown") - \(name ?? "Unnamed")"
        self.artist = artist
        self.name = name
    }
    
    private func setPlaying(playing: Bool) {
        let playButtonImage = playing ? "ic_pause" : "ic_play_arrow"
        btnPlay.setImage(UIImage(named: playButtonImage), forState: .Normal)
        self.isPlaying = playing
    }
}
