//
//  AudioModel.swift
//  VkAudio
//
//  Created by mac-224 on 24.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class AudioModel: AudioPlayerItem {
    
    let id: Int
    let ownerId: Int
    var url: String
    var duration: Int
    let artist: String
    let name: String
    let date: Double
    
    init(id: Int, ownerId: Int, url: String, duration: Int, artist: String, name: String, date: Double) {
        self.id = id
        self.ownerId = ownerId
        self.url = url
        self.duration = duration
        self.artist = artist
        self.name = name
        self.date = date
    }
}