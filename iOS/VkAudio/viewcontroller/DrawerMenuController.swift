//
//  DrawerMenuController.swift
//  VkAudio
//
//  Created by mac-224 on 27.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class DrawerMenuController: UITableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let headerNib = UINib(nibName: "DrawerHeader", bundle: nil)
        tableView.registerNib(headerNib, forHeaderFooterViewReuseIdentifier: "DrawerHeaderSection")
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let position = (indexPath.section, indexPath.row)
        switch position {
        case (0, 0):
            break //todo: present downloads
        case (1, 0):
            OutsideController.present(fromController: self, identified: "controller_settings")
        case (1, 1):
            break //todo: present app info
        case (2, 0):
            VKAPUtils.logout(self)
        default: break
        }
        self.revealViewController().revealToggleAnimated(true)
    }
    
    override func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if section == 0 {
            let header = tableView.dequeueReusableHeaderFooterViewWithIdentifier("DrawerHeaderSection") as! HeaderCell
            let user = VkModelDB.sharedInstance!.getWithType(UserModel.self, id: VKApi.userId!)
        
            let avatarName = "bg_image_\(VKApi.userId!).jpg"
            let avatar = UIImage(contentsOfFile: LocalStorage.buildFilePath(.DocumentDirectory, fileName: avatarName))
            header.imageAvatar.image = avatar
            self.setupBlurView(.Light, bounds: header.imageAvatar.bounds) { header.imageAvatar.addSubview($0) }
            header.labelUsername.text = user?.fullName
        
            return header
        }
        return nil
    }
    
    override func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return section == 0 ? 160.0 : 3.0
    }
}


public class HeaderCell: UITableViewHeaderFooterView {
    @IBOutlet weak var imageAvatar: UIImageView!
    @IBOutlet weak var labelUsername: UILabel!
}