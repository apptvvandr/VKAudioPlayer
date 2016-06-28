//
//  UserModel.swift
//  VkAudio
//
//  Created by mac-224 on 28.06.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import RealmSwift

class UserModel: VkModel {
    
    dynamic var firstName: String = ""
    dynamic var lastName: String = ""
    
    override var fullName: String {get{return "\(firstName) \(lastName)"}}
}