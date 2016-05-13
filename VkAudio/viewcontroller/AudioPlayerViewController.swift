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
        
    var audios: [Audio]!
    var selectedAudioIndex: Int!
    
    let audioPlayer = AudioPlayer.sharedInstance
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.view.addSubview(blurView)
            self.view.sendSubviewToBack(blurView)
        }
        
        audioPlayer.delegate = self
        audioPlayer.playlist = audios
        audioPlayer.play(selectedAudioIndex)
    }
    
    @IBAction func onPreviousButtonClicked(sender: AnyObject) {
        audioPlayer.playPrevious()
    }
    
    @IBAction func onPlayButtonClicked(sender: AnyObject) {
        if audioPlayer.isPlaying() {
            audioPlayer.pause()
        }
        else {
            audioPlayer.continuePlaying()
        }
    }
    
    @IBAction func onNextButtonClicked(sender: AnyObject) {
        audioPlayer.playNext()
    }
    
    @IBAction func onAudioSliderDragged(sender: UISlider) {
        audioPlayer.seekToTime(Int64(progressAudioStream.value))
    }
    
    //MARK: - AudioPlayerDelegate
    
    func onStopPlaying(audio: Audio, playlistPosition: Int, stopSeconds: Int64) {        
        btnPlay.setImage(UIImage(named: "ic_play_arrow"), forState: .Normal)
        if stopSeconds == Int64(audio.duration!) {
            audioPlayer.playNext()
        }
    }
    
    func onStartPlaying(audio: Audio, playlistPosition: Int, startSeconds: Int64) {
        btnPlay.setImage(UIImage(named: "ic_pause"), forState: .Normal)
        labelArtist.text = audio.artist
        labelName.text = audio.name
        progressAudioStream.maximumValue = audio.duration!
    }
    
    func onTimeChanged(seconds: Int64) {
        progressAudioStream.value = Float(seconds)
    }
}