//
//  AudioCell.swift
//  VkAudio
//
//  Created by mac-224 on 29.04.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class AudioCell: UITableViewCell {

    static let STORYBOARD_ID = "cell_audio"
    
    @IBOutlet weak var labelName: UILabel!
    @IBOutlet weak var labelArtist: UILabel!
    @IBOutlet weak var labelDuration: UILabel!
    
    override func layoutSubviews() {
        super.layoutSubviews()
        self.backgroundColor = UIColor.clearColor()
    }

    func setData(audio: AudioModel) {
        labelName.text = audio.name
        labelArtist.text = audio.artist
        labelDuration.text = VKAPUtils.formatProgress(audio.duration)
    }
}