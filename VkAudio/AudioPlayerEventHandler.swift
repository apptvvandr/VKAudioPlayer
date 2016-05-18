//
//  AudioDataStreamer.swift
//  VkAudio
//
//  Created by mac-224 on 17.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import MMWormhole

class AudioPlayerEventHandler {
    
    private static let wormhole = MMWormhole(applicationGroupIdentifier: "group.github.y0rrrsh.vkswiftyplayer", optionalDirectory: "wormhole_event_handler")
    
    enum PlayerEvent: String {
        case AUDIO_CHANGED = "audio_changed"
        case PLAYER_STATE_CHANGED = "player_state_changed"
        case NEXT_AUDIO = "audio_next"
    }
    
    static func sendCurrentAudioChangedEvent(audioArtist: String?, audioName: String?) {
        let eventData = ["artist" : audioArtist ?? "Unknown", "name": audioName ?? "Unnamed"]
        wormhole.passMessageObject(eventData, identifier: PlayerEvent.AUDIO_CHANGED.rawValue)
    }
    
    static func sendPlayerStateChangedEvent(playerIsPlaying: Bool) {
        wormhole.passMessageObject(playerIsPlaying, identifier: PlayerEvent.PLAYER_STATE_CHANGED.rawValue)
    }
    
    static func sendNextAudioEvent() {
        wormhole.passMessageObject(true, identifier: PlayerEvent.NEXT_AUDIO.rawValue)
    }
    
    static func subscribeForPlayerEvent<T>(event: PlayerEvent, eventCallback: (message: T) -> Void) {
        wormhole.listenForMessageWithIdentifier(event.rawValue) { (wormholeMessage: AnyObject?) in
            if let message = wormholeMessage as? T {
                eventCallback(message: message)
            }
        }
    }
}