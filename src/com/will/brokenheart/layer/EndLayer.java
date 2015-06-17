package com.will.brokenheart.layer;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.view.MotionEvent;

import com.will.brokenheart.util.WindowUtil;

public class EndLayer extends CCLayer {

	private CCSprite char_il;
	private float scale;
	private CGSize winCenSize;
	private Context app;
	
	public EndLayer(Context app) {
		this.app = app;
		setIsTouchEnabled(true);
		scale = WindowUtil.winScale();
		winCenSize = WindowUtil.winCenter();
		char_il = CCSprite.sprite("mainUI/map_1_name.png");
		char_il.setScale(scale);
		char_il.setAnchorPoint(0.5F, 0);
		char_il.setPosition(winCenSize.width, winCenSize.height);
		char_il.runAction(CCMoveTo.action(winCenSize.height+30*scale));
		this.addChild(char_il);
		CCLabel gameover = CCLabel.makeLabel("GAME OVER", "font/FTLTLT_0.TTF", 60);
		gameover.setPosition(winCenSize.width, winCenSize.height-50);
		this.addChild(gameover);
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CCDirector.sharedDirector().pushScene(LoadingLayer.scene(app));
		return super.ccTouchesBegan(event);
	}
	
	
}
