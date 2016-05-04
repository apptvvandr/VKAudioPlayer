//
//  GroupCell.swift
//  VkAudio
//
//  Created by mac-224 on 03.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import Kingfisher

class GroupCell: UICollectionViewCell {

    static let STORYBOARD_ID = "cell_group"
    var groupId: Int?

    @IBOutlet weak var imagePhoto: UIImageView!
    @IBOutlet weak var labelName: UILabel!

    func update(groupId: Int, groupName: String, photoUrl: String) {
        self.groupId = groupId
        labelName.text = groupName
        if let url = NSURL(string: photoUrl) {
            imagePhoto.kf_setImageWithURL(url)
        }
    }
}