package com.will.brokenheart.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

/**
 * 背景层
 * 
 * @author William
 * 
 */
public class BackgroundLayer extends CCLayer {

	private CCSprite background;

	public BackgroundLayer(String file) {
		// 将背景精灵填充屏幕
		CGSize size = CCDirector.sharedDirector().winSize();
		background = CCSprite.sprite(file);
		background.setPosition(size.getWidth()/2, size.getHeight()/2);
		CGRect rect = background.getTextureRect();
		background.setScaleX(size.getWidth()/rect.size.width);
		background.setScaleY(size.getHeight()/rect.size.height);
		this.addChild(background, 0);
	}
	
}
