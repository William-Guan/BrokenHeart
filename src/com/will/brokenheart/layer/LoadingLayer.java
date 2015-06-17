package com.will.brokenheart.layer;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Context;

import com.will.brokenheart.util.WindowUtil;

public class LoadingLayer extends CCLayer {

	private CCSprite loding;
	private CCSprite dot1;
	private CCSprite dot2;
	private CCSprite dot3;
	private CCSprite heart;
	private CGSize winSize;
	private Context app;
	private float scaleRate;

	public LoadingLayer(Context app) {
		this.app = app;
		scaleRate = WindowUtil.winScale();
		winSize = WindowUtil.winCenter();

		loding = CCSprite.sprite("now_loading.png");
		loding.setScale(scaleRate);
		CGRect lodingrect = loding.getBoundingBox();
		loding.setAnchorPoint(0.6F, 0);
		loding.setPosition(winSize.width, winSize.height
				- lodingrect.size.height);
		CGPoint lodingPoint = loding.getPosition();

		dot1 = CCSprite.sprite("dot.png");
		dot1.setScale(scaleRate);
		CGRect dotRect = dot1.getBoundingBox();
		dot1.setPosition(lodingPoint.x + dotRect.size.width + 85 * scaleRate,
				lodingPoint.y + 10);

		dot2 = CCSprite.sprite("dot.png");
		dot2.setVisible(false);
		dot2.setScale(scaleRate);
		dot2.setPosition(lodingPoint.x + dotRect.size.width * 2 + 85
				* scaleRate, lodingPoint.y + 10);

		dot3 = CCSprite.sprite("dot.png");
		dot3.setVisible(false);
		dot3.setPosition(lodingPoint.x + dotRect.size.width * 3 + 85
				* scaleRate, lodingPoint.y + 10);
		dot3.setScale(scaleRate);

		heart = CCSprite.sprite("mainUI/heart.png");
		heart.setPosition(winSize.width, winSize.height + 35 * scaleRate);
		heart.setScale(scaleRate + 0.6F);
	}

	private void init() {
		CCScaleTo scale = CCScaleBy.action(0.8F, 1.5F);
		CCSequence sequ = CCSequence.actions(scale, scale.reverse());
		CCRepeatForever forever = CCRepeatForever.action(sequ);
		heart.runAction(forever);

		CCRepeatForever repeat1 = CCRepeatForever.action(CCSequence.actions(
				CCDelayTime.action(1), CCHide.action(), CCDelayTime.action(2),
				CCShow.action()));
		CCRepeatForever repeat2 = CCRepeatForever.action(CCSequence.actions(
				CCDelayTime.action(1), CCShow.action(), CCDelayTime.action(1),
				CCHide.action(), CCDelayTime.action(1)));
		CCRepeatForever repeat3 = CCRepeatForever.action(CCSequence.actions(
				CCDelayTime.action(2), CCShow.action(), CCDelayTime.action(1),
				CCHide.action()));

		dot1.runAction(repeat1);
		dot2.runAction(repeat2);
		dot3.runAction(repeat3);
		this.addChild(heart);
		this.addChild(loding);
		this.addChild(dot1);
		this.addChild(dot2);
		this.addChild(dot3);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		init();
		this.runAction(CCSequence.actions(CCDelayTime.action(3), CCCallFuncN.action(this, "lodingGame")));
	}

	public void lodingGame(Object sender) {
		CCDirector.sharedDirector().pushScene(GameMainLayer.scene(app));
	}

	public static CCScene scene(Context app) {
		CCScene lodingScene = CCScene.node();
		lodingScene.addChild(new LoadingLayer(app));
		return lodingScene;
	}
}
