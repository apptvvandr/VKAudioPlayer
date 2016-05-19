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
    
    enum Event: String {
        case AUDIO_CHANGED = "audio_changed"
        case STATE_CHANGED = "player_state_changed"
        case NEXT_AUDIO = "audio_next"
    }
    
    enum EventSender: String {
        case CONTROLLER = "controller"
        case WIDGET = "widget"
    }
    
    static func sendCurrentAudioChangedEvent(audioArtist: String?, audioName: String?, sender: EventSender) {
        let eventData = ["artist" : audioArtist ?? "Unknown", "name": audioName ?? "Unnamed"]
        wormhole.passMessageObject(eventData, identifier: Event.AUDIO_CHANGED.rawValue + "_" + sender.rawValue)
    }
    
    static func sendPlayerStateChangedEvent(playerIsPlaying: Bool, sender: EventSender) {
        wormhole.passMessageObject(playerIsPlaying, identifier: Event.STATE_CHANGED.rawValue + "_" + sender.rawValue)
    }
    
    static func sendNextAudioEvent(sender: EventSender) {
        wormhole.passMessageObject(true, identifier: Event.NEXT_AUDIO.rawValue + "_" + sender.rawValue)
    }
    
    static func subscribeForPlayerEvent<T>(event: Event, sender: EventSender, eventCallback: (message: T) -> Void) {
        wormhole.listenForMessageWithIdentifier(event.rawValue  + "_" + sender.rawValue) { (wormholeMessage: AnyObject?) in
            if let message = wormholeMessage as? T {
                eventCallback(message: message)
            }
        }
    }
}