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

    @IBOutlet weak var labelTitle: UILabel!

    func setData(artist: String?, name: String?) -> Void {
        labelTitle.text = "\(artist ?? "Unknown") - \(name ?? "Unknown")"
    }
}