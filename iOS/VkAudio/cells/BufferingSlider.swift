//  CachingSlider.swift
//  VkAudio
//
//  Created by mac-224 on 24.05.16.
//  Copyright © 2016 y0rrrsh. All rights reserved.
//

import Foundation
import UIKit

class BufferingSlider: UISlider {
    
    override var value: Float {
        didSet {
            updateLayers()
        }
    }
    
    var bufferValue : Float = 0.0 {
        didSet {
            updateLayers()
        }
    }
    
    var lineHeight = CGFloat(2) {
        didSet {
            frameCenter = self.frame.size.height / 2 - lineHeight / 2
        }
    }
    private var frameCenter: CGFloat?
    
    var backgroundLayerColor : UIColor = UIColor.lightGrayColor()
    var progressLayerColor : UIColor = UIColor.redColor()
    var bufferLayerColor : UIColor = UIColor.darkGrayColor()
    
    private var backgroundLayer = CAShapeLayer()
    private var progressLayer = CAShapeLayer()
    private var bufferLayer = CAShapeLayer()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setup()
    }
    
    override func drawRect(rect: CGRect) {
        super.drawRect(rect)
        self.frameCenter = self.frame.size.height / 2 - lineHeight / 2
        
        backgroundLayer.path = UIBezierPath(rect: CGRect(x: 0, y: frameCenter!, width: self.frame.size.width, height: lineHeight)).CGPath
        backgroundLayer.fillColor = backgroundLayerColor.CGColor
    }
    
    private func setup() {
        self.minimumTrackTintColor = UIColor.clearColor()
        self.maximumTrackTintColor = UIColor.clearColor()
        
        self.layer.addSublayer(backgroundLayer)
        self.layer.addSublayer(bufferLayer)
        self.layer.addSublayer(progressLayer)
    }
    
    private func updateLayers() {
        let progressWidth = self.frame.size.width * CGFloat(value) / CGFloat(maximumValue)
        progressLayer.path = UIBezierPath(rect: CGRect(x: 0, y: frameCenter!, width: progressWidth, height: lineHeight)).CGPath
        progressLayer.fillColor = progressLayerColor.CGColor
        
        let bufferWidth = self.frame.size.width * CGFloat(bufferValue) / CGFloat(maximumValue)
        bufferLayer.path = UIBezierPath(rect: CGRect(x: 0, y: frameCenter!, width: bufferWidth, height: lineHeight)).CGPath
        bufferLayer.fillColor = bufferLayerColor.CGColor
    }
}