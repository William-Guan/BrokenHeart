package com.will.brokenheart.layer;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.view.MotionEvent;

import com.will.brokenheart.R;
import com.will.brokenheart.util.WindowUtil;

/**
 * 动画层
 * 
 * @author William
 * 
 */
public class StartUpAnimationLayer extends CCLayer {

	private CCSprite brokenFont;
	private CCSprite heart;
	private CCSprite heartFont;
	private CCSprite start;
	private CCSprite line;

	private Context app;
	private float scaleRate;

	public StartUpAnimationLayer(Context context) {
		this.app = context;
		scaleRate = WindowUtil.winScale();
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.suc);
		SoundEngine.sharedEngine().preloadSound(context, R.raw.ost10);
		CGSize winSize = CCDirector.sharedDirector().winSize();
		CGSize winCenter = CGSize.make(winSize.getWidth() / 2,
				winSize.getHeight() / 2);
		brokenFont = CCSprite.sprite("mainUI/title/1_title.png");
		brokenFont.setAnchorPoint(0.8F, 0);
		brokenFont.setPosition(winCenter.getWidth(), winCenter.getHeight());
		brokenFont.setScale(scaleRate);

		heartFont = CCSprite.sprite("mainUI/title/1_title_B.png");
		heartFont.setPosition(winCenter.getWidth() + 100 * scaleRate,
				winCenter.getHeight() - 40 * scaleRate);
		heartFont.setScale(WindowUtil.winScale());

		heart = CCSprite.sprite("mainUI/title/title_heart.png");
		heart.setAnchorPoint(0, 0);
		heart.setPosition(winCenter.getWidth() - 30 * scaleRate,
				winCenter.getHeight() + 50 * scaleRate);
		heart.setScale(WindowUtil.winScale());

		start = CCSprite.sprite("mainUI/title/bt_start.png");
		start.setAnchorPoint(0.5F, 1);
		start.setPosition(winCenter.getWidth(), winCenter.getHeight() - 200
				* scaleRate);
		start.setScale(WindowUtil.winScale());

		line = CCSprite.sprite("mainUI/title/title_line.png");
		line.setPosition(winCenter.getWidth(), winCenter.getHeight() - 230
				* scaleRate);
		line.setScale(WindowUtil.winScale());

		this.addChild(start, 1);
		this.addChild(line, 0);
	}

	@Override
	public void onExit() {
		SoundEngine.sharedEngine().pauseSound();
		super.onExit();
	}

	@Override
	public void onEnter() {
		SoundEngine.sharedEngine().playSound(app, R.raw.ost10, true);
		super.onEnter();
		new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				brokenFont.runAction(CCFadeIn.action(0.5F));
				StartUpAnimationLayer.this.addChild(brokenFont, 1);
				try {
					sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				heart.runAction(CCFadeIn.action(0.5F));
				heartFont.runAction(CCFadeIn.action(0.5F));
				StartUpAnimationLayer.this.addChild(heart, 0);
				StartUpAnimationLayer.this.addChild(heartFont, 1);
				CCSequence sequence = CCSequence.actions(CCFadeIn.action(0.5F),
						CCFadeOut.action(0.8F));
				CCRepeatForever forever = CCRepeatForever.action(sequence);
				start.runAction(forever);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setIsTouchEnabled(true);
			};
		}.start();
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		SoundEngine.sharedEngine().pauseSound();
		SoundEngine.sharedEngine().playEffect(app, R.raw.suc);
		CCDirector director = CCDirector.sharedDirector();
		director.pushScene(LoadingLayer.scene(app));
		return CCTouchDispatcher.kEventHandled;
	}

	public static CCScene scene(Context app) {
		CCScene startUpScene = CCScene.node();
		startUpScene.addChild(new BackgroundLayer("mainUI/title/title_bg.png"),
				-1);
		startUpScene.addChild(new StartUpAnimationLayer(app), 0);
		return startUpScene;
	}
}
