//
//  SyncModelPeekDialog.swift
//  VkAudio
//
//  Created by mac-224 on 29.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit
import BEMCheckBox

class SyncModelPickDialog: SimpleAlertView, UITableViewDelegate, UITableViewDataSource {
    
    private var tableView: UITableView?
    private var items = [VkModel]()
    
    init(items: [VkModel]) {
        super.init(title: "Pick items to sync", positiveButtonTitle: "Apply")
        self.items = items
        
        tableView = UITableView(frame: frame)
        tableView!.delegate = self
        tableView!.dataSource = self
        tableView!.backgroundColor = UIColor.clearColor()
        tableView!.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell_sync_item")
        self.setValue(tableView, forKey: "accessoryView")
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        return SyncItemCell(item: items[indexPath.row])
    }
}

class SyncItemCell: UITableViewCell, BEMCheckBoxDelegate {
    
    private var itemSize: CGSize!
    private var imageRect: CGRect!
    private var checkBox: BEMCheckBox!
    
    private var item: VkModel!
    
    init(item: VkModel) {
        super.init(style: .Default, reuseIdentifier: "cell_sync_item")
        self.item = item
        
        self.backgroundColor = UIColor.clearColor()
        itemSize = CGSizeMake(32, 32)
        imageRect = CGRectMake(0, 0, itemSize.width, itemSize.height)
        self.imageView?.layer.cornerRadius = imageRect.width / 2
        
        checkBox = BEMCheckBox(frame: CGRectMake(0, 0, itemSize.width / 2, itemSize.height / 2))
        checkBox.delegate = self
        checkBox.boxType = .Square
        checkBox.onTintColor = UIColor.brownColor() //todo: use accentColor
        checkBox.onFillColor = UIColor.brownColor()
        checkBox.onCheckColor = UIColor.whiteColor()
        checkBox.onAnimationType = .Fill
        checkBox.offAnimationType = .Fill
        self.accessoryView = checkBox
        
        UIGraphicsBeginImageContextWithOptions(itemSize, false, UIScreen.mainScreen().scale)
        self.imageView?.kf_setImageWithURL(NSURL(string: item.avatarUrl)!, completionHandler: { (image, error, cacheType, imageURL) in
            image!.drawInRect(self.imageRect)
            self.imageView?.image! = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext();
        })
        self.textLabel!.text = item.fullName
        checkBox.on = item.syncEnabled
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func didTapCheckBox(checkBox: BEMCheckBox) {
        self.item.syncEnabled = checkBox.on
    }
}