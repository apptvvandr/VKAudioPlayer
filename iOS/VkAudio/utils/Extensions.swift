//
//  Extensions.swift
//  VkAudio
//
//  Created by mac-224 on 19.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

extension UIViewController {
    
    func setupBlurView(style: UIBlurEffectStyle = .ExtraLight, onBlurCreated: (blurView: UIVisualEffectView) -> Void){
        let blurEffect = UIBlurEffect(style: style)
        let blurEffectView = UIVisualEffectView(effect: blurEffect)
        blurEffectView.frame = view.bounds
        blurEffectView.autoresizingMask = [.FlexibleWidth, .FlexibleHeight]
        
        onBlurCreated(blurView: blurEffectView)
    }
}

extension CollectionType {
    /// Return a copy of `self` with its elements shuffled
    func shuffle() -> [Generator.Element] {
        var list = Array(self)
        list.shuffleInPlace()
        return list
    }
}

extension MutableCollectionType where Index == Int {
    /// Shuffle the elements of `self` in-place.
    mutating func shuffleInPlace() {
        // empty and single-element collections don't shuffle
        if count < 2 { return }
        
        for i in 0..<count - 1 {
            let j = Int(arc4random_uniform(UInt32(count - i))) + i
            guard i != j else { continue }
            swap(&self[i], &self[j])
        }
    }
}

extension NSDate {
    static func currentTimeMillis() -> Double {
        return Double(NSDate().timeIntervalSince1970 * 1000)
    }
}

func +=<Key, Value>(inout left: [Key:Value], right: [Key:Value]) {
    for (key, value) in right {
        left[key] = value
    }
}