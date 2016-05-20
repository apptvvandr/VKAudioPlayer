//
//  GroupsCollectionView.swift
//  VkAudio
//
//  Created by mac-224 on 04.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class GroupsCollectionView: UICollectionView{
    
    let columns: Int = 3
    let spacing: CGFloat = 8
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        let layout: UICollectionViewFlowLayout = UICollectionViewFlowLayout()
        let containerWidth = UIScreen.mainScreen().bounds.width - (CGFloat(columns) * spacing)
        
        layout.sectionInset = UIEdgeInsets(top: spacing, left: spacing, bottom: spacing, right: spacing)
        layout.itemSize = CGSize(width: containerWidth/3, height: containerWidth/3)
        layout.minimumInteritemSpacing = spacing/2
        layout.minimumLineSpacing = spacing/2
        
        self.collectionViewLayout = layout
    }
}