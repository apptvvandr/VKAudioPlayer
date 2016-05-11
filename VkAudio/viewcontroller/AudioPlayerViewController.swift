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
    
    @IBOutlet weak var labelArtist: UILabel!
    @IBOutlet weak var labelName: UILabel!
    @IBOutlet weak var imageCover: UIImageView! //todo: download cover
    @IBOutlet weak var btnPrevious: UIButton!
    @IBOutlet weak var btnPlay: UIButton!
    @IBOutlet weak var btnNext: UIButton!
    
    @IBOutlet weak var progressAudioStream: UISlider!
        
    var player = AVPlayer()
    var audios: [Audio]!
    private var currentAudio: (index: Int, audio: Audio)!
    
    var selectedAudioIndex: Int!
    var timer: NSTimer!
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        player.addObserver(self, forKeyPath: "rate", options: NSKeyValueObservingOptions.New, context: nil)
        timer = NSTimer.scheduledTimerWithTimeInterval(1.0, target: self, selector: #selector(AudioPlayerViewController.onAudioSliderUpdated), userInfo: nil, repeats: true)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        currentAudio = (selectedAudioIndex, audios[selectedAudioIndex])
        progressAudioStream.maximumValue = currentAudio.audio.duration ?? 0
        
        updateUi()
        playAudio(currentAudio.audio)
    }
    
    @IBAction func onPreviousButtonClicked(sender: AnyObject) {
        if currentAudio.index - 1 >= 0 {
            currentAudio.index = currentAudio.index - 1
            currentAudio.audio = audios[currentAudio.index]
            updateUi()
            playAudio(currentAudio.audio)
        }
    }
    
    @IBAction func onPlayButtonClicked(sender: AnyObject) {
        if player.rate == 0.0 {
            player.play()
        }
        else {
            player.pause()
        }
    }
    
    @IBAction func onNextButtonClicked(sender: AnyObject) {
        if currentAudio.index + 1 <= audios.count {
            currentAudio.index = currentAudio.index + 1
            currentAudio.audio = audios[currentAudio.index]
            updateUi()
            playAudio(currentAudio.audio)
        }
    }
    
    @IBAction func onAudioSliderDragged(sender: UISlider) {
        let time = CMTimeMake(Int64(progressAudioStream.value), 1)
        player.seekToTime(time) { (result) in
            self.player.play()
        }
    }
    
    func onAudioSliderUpdated(){
        let playerTime = player.currentTime()
        let time = Float(Int64(playerTime.value) / Int64(playerTime.timescale))
        if time == currentAudio.audio.duration!{
            self.onNextButtonClicked(self)
            return
        }
        progressAudioStream.value = Float(time)
    }
    
    private func playAudio(audio: Audio){
        if let path = audio.url, url = NSURL(string: path){
            player.replaceCurrentItemWithPlayerItem(AVPlayerItem(URL: url))
            player.play()
        }
    }
    
    private func updateUi(){
        labelArtist.text = currentAudio.audio.artist
        labelName.text = currentAudio.audio.name
        
        btnPrevious.hidden = currentAudio.index == 0
        btnNext.hidden = currentAudio.index == audios.count
    }
    
    override func observeValueForKeyPath(keyPath: String?, ofObject object: AnyObject?, change: [String : AnyObject]?, context: UnsafeMutablePointer<Void>) {
        if keyPath == "rate" {
            if let rate = change?[NSKeyValueChangeNewKey] as? Float {
                let imageSrc = rate == 0.0 ? "ic_play_arrow" : "ic_pause"
                btnPlay.setImage(UIImage(named: imageSrc), forState: .Normal)
            }
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
        player.pause() //todo: implement continious playing in background
        
        player.removeObserver(self, forKeyPath: "rate")
        timer.invalidate()
    }
}