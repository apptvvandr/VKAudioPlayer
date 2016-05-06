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
    
    var player = AVPlayer()
    var audios: [Audio]!
    var currentAudioIndex: Int!
    
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        player.addObserver(self, forKeyPath: "rate", options: NSKeyValueObservingOptions.New, context: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let audio = audios[currentAudioIndex]
        updateUi()
        playAudio(audio)
    }
    
    @IBAction func onPreviousButtonClicked(sender: AnyObject) {
        if currentAudioIndex - 1 >= 0 {
            currentAudioIndex = currentAudioIndex - 1
            let audio = audios[currentAudioIndex]
            updateUi()
            playAudio(audio)
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
        if currentAudioIndex + 1 <= audios.count {
            currentAudioIndex = currentAudioIndex + 1
            let audio = audios[currentAudioIndex]
            updateUi()
            playAudio(audio)
        }
    }
    
    private func playAudio(audio: Audio){
        if let path = audio.url, url = NSURL(string: path){
            player.replaceCurrentItemWithPlayerItem(AVPlayerItem(URL: url))
            player.play()
        }
    }
    
    private func updateUi(){
        let audio = audios[currentAudioIndex]
        
        labelArtist.text = audio.artist
        labelName.text = audio.name
        
        btnPrevious.hidden = currentAudioIndex == 0
        btnNext.hidden = currentAudioIndex == audios.count
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
        player.removeObserver(self, forKeyPath: "rate")
    }
}