//
//  AudioPlayerViewController.swift
//  VkAudio
//
//  Created by mac-224 on 05.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class AudioPlayerViewController: UIViewController, AudioPlayerDelegate {
    
    @IBOutlet weak var labelArtist: UILabel!
    @IBOutlet weak var labelName: UILabel!
    @IBOutlet weak var imageCover: UIImageView! //todo: download cover
    @IBOutlet weak var btnPrevious: UIButton!
    @IBOutlet weak var btnPlay: UIButton!
    @IBOutlet weak var btnNext: UIButton!
    @IBOutlet weak var progressAudioStream: UISlider!
    
    let player = AudioPlayer.sharedInstance
    var audios: [Audio]!
    var selectedAudioIndex: Int!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.view.addSubview(blurView)
            self.view.sendSubviewToBack(blurView)
        }
        
        player.delegate = self
        player.playlist = audios.map({$0 as Audio}) //[?] stackoverflow.com/questions/30100787
        if player.currentAudio?.playlistPosition != selectedAudioIndex {
            player.play(selectedAudioIndex)
        }
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        let audio = player.currentAudio?.audio as! Audio
        
        let playButtonImageRes = player.isPlaying() ? "ic_pause" : "ic_play_arrow"
        btnPlay.setImage(UIImage(named: playButtonImageRes), forState: .Normal)
        labelArtist.text = audio.artist
        labelName.text = audio.name
        progressAudioStream.maximumValue = Float(audio.duration!)
    }
    
    @IBAction func onPreviousButtonClicked(sender: AnyObject) {
        player.playPrevious()
    }
    
    @IBAction func onPlayButtonClicked(sender: AnyObject) {        
        if player.isPlaying() {
            player.pause()
        }
        else {
            player.continuePlaying()
        }
    }
    
    @IBAction func onNextButtonClicked(sender: AnyObject) {
        player.playNext()
    }
    
    @IBAction func onAudioSliderDragged(sender: UISlider) {
        player.seekToTime(Int64(progressAudioStream.value))
    }
    
    //MARK: - AudioPlayerDelegate
    
    func onStopPlaying(audio: AudioPlayerItem, playlistPosition: Int, stopSeconds: Int64) {
        btnPlay.setImage(UIImage(named: "ic_play_arrow"), forState: .Normal)
        if stopSeconds == Int64(audio.duration!) {
            player.playNext()
        }
        AudioPlayerEventHandler.sendPlayerStateChangedEvent(false, sender: .CONTROLLER)
    }
    
    func onStartPlaying(audio: AudioPlayerItem, playlistPosition: Int, startSeconds: Int64) {
        let audio = audio as! Audio
        
        btnPlay.setImage(UIImage(named: "ic_pause"), forState: .Normal)
        labelArtist.text = audio.artist
        labelName.text = audio.name
        progressAudioStream.maximumValue = Float(audio.duration!)
        
        AudioPlayerEventHandler.sendPlayerStateChangedEvent(true, sender: .CONTROLLER)
        AudioPlayerEventHandler.sendCurrentAudioChangedEvent(audio.artist, audioName: audio.name, sender: .CONTROLLER)
    }
    
    func onTimeChanged(seconds: Int64) {
        progressAudioStream.value = Float(seconds)
    }
}