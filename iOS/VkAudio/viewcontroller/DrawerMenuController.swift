//
//  DrawerMenuController.swift
//  VkAudio
//
//  Created by mac-224 on 27.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation

class DrawerMenuController: UITableViewController {
 
    private let icons = ["ic_download", "ic_settings", "ic_info", "ic_exit_to_app"]
    private let titles = ["Downloads", "Settings", "About", "Logout"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let headerNib = UINib(nibName: "DrawerHeader", bundle: nil)
        tableView.registerNib(headerNib, forHeaderFooterViewReuseIdentifier: "DrawerHeaderSection")
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return titles.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let index = indexPath.row
        let cell = tableView.dequeueReusableCellWithIdentifier("drawer_cell_menu_item") as! MenuItemCell
        
        cell.imageIcon.image = UIImage(named: icons[index])
        cell.labelTitle.text = titles[index]
        
        return cell
    }
    
    override func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = tableView.dequeueReusableHeaderFooterViewWithIdentifier("DrawerHeaderSection") as! HeaderCell
        let user = VkModelDB.sharedInstance!.getWithType(UserModel.self, id: VKApi.userId!)
        
        let avatar = UIImage(contentsOfFile: LocalStorage.buildFilePath(.DocumentDirectory, fileName: "bg_image.jpg"))
        header.imageAvatar.image = avatar
        self.setupBlurView(.Light, bounds: header.imageAvatar.bounds) { header.imageAvatar.addSubview($0) }
        header.labelUsername.text = user?.fullName
        
        return header
    }
    
    override func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 128.0
    }
    
     override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        VKAPUtils.logout(self)
    }
    
    override func shouldPerformSegueWithIdentifier(identifier: String, sender: AnyObject?) -> Bool {
        let senderCell = sender as! MenuItemCell
        return senderCell.labelTitle.text == titles[3]
    }
}


public class HeaderCell: UITableViewHeaderFooterView {
    @IBOutlet weak var imageAvatar: UIImageView!
    @IBOutlet weak var labelUsername: UILabel!
}

public class MenuItemCell: UITableViewCell {
    @IBOutlet weak var imageIcon: UIImageView!
    @IBOutlet weak var labelTitle: UILabel!
}