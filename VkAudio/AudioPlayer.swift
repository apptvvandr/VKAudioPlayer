//
//  AudioPlayer.swift
//  VkAudio
//
//  Created by mac-224 on 12.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import AVFoundation

public class AudioPlayer {
    
    public static let sharedInstance = AudioPlayer()
    public var delegate: AudioPlayerDelegate?
    
    public var playlist: [AudioPlayerItem] = [AudioPlayerItem]()
    public var currentAudio: (playlistPosition: Int, audio: AudioPlayerItem)?
    
    private var player = AVPlayer()
    private var currentAudioTimer: NSTimer?
    private var playing: Bool = false
    
    private init() {}
    
    public func play(playlistPosition: Int = 0) {
        assert(playlistPosition >= 0 && playlistPosition < playlist.count)
        
        if playlist.isEmpty {
            print("AudioPlayer: play: playlist is empty")
        }
        self.playAudio(playlistPosition)
    }
    
    public func playNext() {
        if playlist.isEmpty {
            print("AudioPlayer: playNext: playlist is empty")
            return
        }
        let index = currentAudio?.playlistPosition ?? 0
        let nextIndex = index == playlist.endIndex - 1 ? 0 : index + 1
        self.playAudio(nextIndex)
    }
    
    public func playPrevious() {
        if playlist.isEmpty {
            print("AudioPlayer: playPrevious: playlist is empty")
            return
        }
        
        let index = currentAudio?.playlistPosition ?? playlist.endIndex
        let previousIndex = index == 0 ? playlist.endIndex - 1 : index - 1
        self.playAudio(previousIndex)
    }
    
    public func continuePlaying() {
        if currentAudio == nil {
            print("AudioPlayer: continuePlaying: current audio is not initialized")
            return
        }
        player.play()
        playing = true
        
        delegate?.onStartPlaying(currentAudio!.audio, playlistPosition: currentAudio!.playlistPosition, startSeconds: playerSecondsToInt())
    }
    
    public func pause() {
        if playing {
            player.pause()
            playing = false
            
            delegate?.onStopPlaying(currentAudio!.audio, playlistPosition: currentAudio!.playlistPosition, stopSeconds: self.playerSecondsToInt())
        }
    }
    
    public func seekToTime(seconds: Int64){
        currentAudioTimer?.invalidate()
        player.seekToTime(CMTimeMake(seconds, 1)) { (finished: Bool) in
            if finished {
                self.currentAudioTimer = NSTimer.scheduledTimerWithTimeInterval(1.0, target: self, selector: #selector(AudioPlayer.onTimeChanged), userInfo: nil, repeats: true)
            }
        }
    }
    
    public func isPlaying() -> Bool {
        return self.playing
    }
    
    private func playAudio(position: Int) {
        currentAudio = (position, playlist[position])
        
        if let path = currentAudio!.audio.url, url = NSURL(string: path){
            self.resetTimer()
            
            player.replaceCurrentItemWithPlayerItem(AVPlayerItem(URL: url))
            player.play()
            playing = true
            
            delegate?.onStartPlaying(currentAudio!.audio, playlistPosition: currentAudio!.playlistPosition, startSeconds: self.playerSecondsToInt())
        }
    }
    
    @objc private func onTimeChanged() {
        let seconds = self.playerSecondsToInt()
        
        if seconds == Int64(currentAudio!.audio.duration!) {
            self.currentAudioTimer?.invalidate()
            
            delegate?.onStopPlaying(currentAudio!.audio, playlistPosition: currentAudio!.playlistPosition, stopSeconds: seconds)
            
            return
        }
        
        delegate?.onTimeChanged(seconds)
    }
    
    private func resetTimer() {
        self.currentAudioTimer?.invalidate()
        self.currentAudioTimer = NSTimer.scheduledTimerWithTimeInterval(1.0, target: self, selector: #selector(AudioPlayer.onTimeChanged), userInfo: nil, repeats: true)
    }
    
    private func playerSecondsToInt() -> Int64 {
        let playerTime = player.currentTime()
        return Int64(playerTime.value) / Int64(playerTime.timescale)
    }
}

public protocol AudioPlayerDelegate {
    func onTimeChanged(seconds: Int64)
    func onStartPlaying(audio: AudioPlayerItem, playlistPosition: Int, startSeconds: Int64)
    func onStopPlaying(audio: AudioPlayerItem, playlistPosition: Int, stopSeconds: Int64)
}

public protocol AudioPlayerItem : class {
    var url: String? {get set}
    var duration: Int? {get set}
}