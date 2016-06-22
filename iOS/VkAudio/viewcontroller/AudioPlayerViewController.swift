//
//  AudioPlayerViewController.swift
//  VkAudio
//
//  Created by mac-224 on 05.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import MediaPlayer
import JLToast

class AudioPlayerViewController: UIViewController, AudioPlayerDelegate {
    
    @IBOutlet weak var labelArtist: UILabel!
    @IBOutlet weak var labelName: UILabel!
    @IBOutlet weak var imageCover: UIImageView! //todo: download cover
    @IBOutlet weak var btnPrevious: UIButton!
    @IBOutlet weak var btnPlay: UIButton!
    @IBOutlet weak var btnNext: UIButton!
    @IBOutlet weak var btnShuffle: UIButton!
    @IBOutlet weak var btnRepeat: UIButton!
    @IBOutlet weak var btnPlaylistEdit: UIButton!
    @IBOutlet weak var progressAudioStream: BufferingSlider!
    @IBOutlet weak var labelAudioCurrentDuration: UILabel!
    @IBOutlet weak var labelAudioDuration: UILabel!
    
    let api: VKAPService = VKAPService.sharedInstance!
    let player = AudioPlayer.sharedInstance
    var playlistOwnerId: Int? //nil means current user
    var audios: [Audio]!
    var selectedAudioIndex: Int!
    
    var managedAudios = [Int: ManagedAudio]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setupBlurView { (blurView) in
            self.view.addSubview(blurView)
            self.view.sendSubviewToBack(blurView)
        }
        
        player.delegate = self
        player.playlist = audios.map({$0 as Audio}) //[?] stackoverflow.com/questions/30100787
        
        let currentAudio = player.currentAudio?.audio
        if currentAudio == nil || (currentAudio as! Audio).id != audios[selectedAudioIndex].id {
            player.play(selectedAudioIndex)
        }
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        let audio = player.currentAudio?.audio as! Audio
        
        let playButtonImageRes = player.isPlaying() ? "ic_pause" : "ic_play"
        btnPlay.setImage(UIImage(named: playButtonImageRes), forState: .Normal)
        labelArtist.text = audio.artist
        labelName.text = audio.name
        progressAudioStream.maximumValue = Float(audio.duration!)
        
        updateEditPlaylistIcon(audio, playlistPosition: player.currentAudio!.playlistPosition)
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
    
    @IBAction func onShuffleButtonClicked(sender: AnyObject) {
        var newPlaylist = [Audio]()
        let shuffleEnabled = VKAPUserDefaults.isShuffleEnabled()
        VKAPUserDefaults.setShuffleEnabled(!shuffleEnabled)
        if !shuffleEnabled {
            newPlaylist = audios.shuffle()
            btnShuffle.tintColor = progressAudioStream.progressLayerColor
        }
        else {
            newPlaylist = self.audios
            btnShuffle.tintColor = UIColor.blackColor()
        }
        player.playlist = newPlaylist.map({$0 as Audio})
    }
    
    @IBAction func onRepeatButtonClicked(sender: AnyObject) {
        let repeatEnabled = VKAPUserDefaults.isRepeatEnabled()
        VKAPUserDefaults.setRepeatEnabled(!repeatEnabled)
        
        let repeatTint = !repeatEnabled ? progressAudioStream.progressLayerColor : UIColor.blackColor()
        btnRepeat.tintColor = repeatTint
    }
    
    @IBAction func onPlaylistEditClicked(sender: AnyObject) {
        let currentAudio = player.currentAudio?.audio as! Audio
        let managedAudio = managedAudios[player.currentAudio!.playlistPosition]
        
        if currentAudio.ownerId != Int(VkApi.sharedInstance!.userId) && managedAudio == nil {
            addAudioRequest(currentAudio)
            return
        }
        if currentAudio.ownerId == Int(VkApi.sharedInstance!.userId) && managedAudio == nil {
            removeAudioRequest(currentAudio, id: currentAudio.id!)
            return
        }
        if !managedAudio!.wasRemoved {
            removeAudioRequest(currentAudio, id: managedAudio!.id)
            return
        }
        if managedAudio!.wasRemoved {
            restoreAudioRequest(currentAudio, id: managedAudio!.id)
            return
        }
    }
    
    private func addAudioRequest(audio: Audio) {
        api.addAudio(audio.id!, ownerId: audio.ownerId!, callback: VkApiCallback(onResult: { (result: Int) in
            let message = "\(audio.artist ?? "Unknown") - \(audio.name ?? "Unnamed") was added to your page"
            JLToast.makeText(message, duration: JLToastDelay.LongDelay).show()
            
            self.managedAudios[self.player.currentAudio!.playlistPosition] = ManagedAudio(id: result)
            self.btnPlaylistEdit.setImage(UIImage(named: "ic_remove"), forState: .Normal)
        }))
    }
    
    private func removeAudioRequest(audio: Audio, id: Int) {
        api.removeAudio(id, ownerId: Int(VkApi.sharedInstance!.userId)!, callback: VkApiCallback(onResult: { (result: Int) in
            let message = "\(audio.artist ?? "Unknown") - \(audio.name ?? "Unnamed") was removed from your page"
            JLToast.makeText(message, duration: JLToastDelay.LongDelay).show()
            
            var managedAudio = self.managedAudios[self.player.currentAudio!.playlistPosition]
            if managedAudio == nil {
                managedAudio = ManagedAudio(id: audio.id!)
                if audio.ownerId! == Int(VkApi.sharedInstance!.userId) {
                    managedAudio!.wasRemoved = true
                }
            }
            else {
                managedAudio!.wasRemoved = true
            }
            self.managedAudios[self.player.currentAudio!.playlistPosition] = managedAudio
            self.btnPlaylistEdit.setImage(UIImage(named: "ic_add"), forState: .Normal)
        }))
    }
    
    private func restoreAudioRequest(audio: Audio, id: Int) {
        api.restoreAudio(id, ownerId: Int(VkApi.sharedInstance!.userId)!, callback: VkApiCallback(onResult: { (result: Audio) in
            let message = "\(audio.artist ?? "Unknown") - \(audio.name ?? "Unnamed") was added to your page"
            JLToast.makeText(message, duration: JLToastDelay.LongDelay).show()
            
            self.managedAudios[self.player.currentAudio!.playlistPosition] = ManagedAudio(id: result.id!)
            self.btnPlaylistEdit.setImage(UIImage(named: "ic_remove"), forState: .Normal)
        }))
    }
    
    @IBAction func onAudioSliderDragged(sender: UISlider) {
        player.seekToTime(Int64(progressAudioStream.value)) {
            let currentAudio = self.player.currentAudio?.audio as! Audio
            let elapsedTime = Int64(self.progressAudioStream.value)
            
            self.updateMediaCenterInfo(currentAudio, elapsedTime: elapsedTime)
        }
    }
    
    //MARK: - AudioPlayerDelegate
    
    func onStopPlaying(audio: AudioPlayerItem, playlistPosition: Int, stopSeconds: Int64) {
        btnPlay.setImage(UIImage(named: "ic_play"), forState: .Normal)
        if stopSeconds == Int64(audio.duration!) {
            if VKAPUserDefaults.isRepeatEnabled() {
                player.play(playlistPosition)
            }
            else {
                player.playNext()
            }
        }
    }
    
    func onStartPlaying(audio: AudioPlayerItem, playlistPosition: Int, startSeconds: Int64) {
        let audio = audio as! Audio
        
        btnPlay.setImage(UIImage(named: "ic_pause"), forState: .Normal)
        labelArtist.text = audio.artist
        labelName.text = audio.name
        progressAudioStream.maximumValue = Float(audio.duration!)
        labelAudioDuration.text = VKAPUtils.formatProgress(audio.duration ?? 0)
        
        updateEditPlaylistIcon(audio, playlistPosition: playlistPosition)
        updateMediaCenterInfo(audio, elapsedTime: startSeconds)
    }
    
    func onTimeChanged(seconds: Int64, cachedSeconds: Int64) {
        progressAudioStream.value = Float(seconds)
        labelAudioCurrentDuration.text = VKAPUtils.formatProgress(Int(seconds))
        progressAudioStream.bufferValue = Float(cachedSeconds)
    }
    
    private func updateEditPlaylistIcon(currentAudio: Audio, playlistPosition: Int) {
        let managedAudio = managedAudios[playlistPosition]
        let isOwner = currentAudio.ownerId == Int(VkApi.sharedInstance!.userId)
        let canBeRestored = managedAudio != nil && managedAudio!.wasRemoved
        let icon = isOwner && !canBeRestored ? "ic_remove" : "ic_add"
        btnPlaylistEdit.setImage(UIImage(named: icon), forState: .Normal)
    }
    
    private func updateMediaCenterInfo(currentAudio: Audio, elapsedTime: Int64? = 0) {
        let currentAudioInfo = [
            MPMediaItemPropertyArtist: currentAudio.artist!,
            MPMediaItemPropertyTitle: currentAudio.name!,
            MPNowPlayingInfoPropertyElapsedPlaybackTime: NSNumber(longLong: elapsedTime!),
            MPMediaItemPropertyPlaybackDuration: NSNumber(integer: currentAudio.duration!)
        ]
        MPNowPlayingInfoCenter.defaultCenter().nowPlayingInfo = currentAudioInfo
    }
}