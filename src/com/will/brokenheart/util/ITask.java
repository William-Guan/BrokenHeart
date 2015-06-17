package com.will.brokenheart.util;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class ITask {

	public static void showNumber(String strNum,CCLayer layer) {
		CGSize s = CCDirector.sharedDirector().winSize();
		float x = s.width / 2;
		float y = s.height / 2;
		int width = 25;
		int height = 35;
		CGSize size = CGSize.make(width, height);
		char[] c = strNum.toCharArray();
		for (int i =0 ; i < c.length; i++) {
			CGRect rect = new CGRect(CGPoint.ccp(width*(c[i]-48), 0),size);
			CCSprite sprite = CCSprite.sprite("ui_shop_font.png",rect);
			sprite.setPosition(x+width*i, y);
			layer.addChild(sprite);
		}
	}
}
