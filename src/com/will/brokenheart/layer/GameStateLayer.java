package com.will.brokenheart.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.util.Log;

import com.will.brokenheart.R;
import com.will.brokenheart.util.WindowUtil;

public class GameStateLayer extends CCLayer {
	private static List<CCSprite> hp;

	private CCSprite ready_0;
	private CCSprite ready_1;
	private CCSprite ready_2;
	private CCSprite ready_3;
	private CCSprite ready_4;
	private CCSprite ready_5;
	private CCSprite hp1;
	private CCSprite hp2;
	private CCSprite hp3;
	private CCSprite title;
	private static CCLabelAtlas sorce;

	private static float scale;
	private CGSize center;
	private Context app;
	private CGSize winSize;

	public GameStateLayer(Context app) {
		hp = new ArrayList<CCSprite>();
		winSize = WindowUtil.winSize();
		scale = WindowUtil.winScale();
		SoundEngine.sharedEngine().preloadSound(app, R.raw.ost20);
		SoundEngine.sharedEngine().preloadEffect(app, R.raw.s_39);
		SoundEngine.sharedEngine().preloadEffect(app, R.raw.s_28);
		setIsTouchEnabled(true);

		this.app = app;
		center = WindowUtil.winCenter();
		ready_0 = CCSprite.sprite("mainUI/MK/ready_0.png");
		ready_0.setScale(scale);
		ready_0.setAnchorPoint(0.5F, 0);
		
		ready_0.setPosition(center.width - 120 * scale, center.height - 50
				* scale);

		ready_1 = CCSprite.sprite("mainUI/MK/ready_1.png");
		ready_1.setScale(scale);
		ready_1.setAnchorPoint(0.5F, 0);
		ready_1.setPosition(center.width - 60 * scale, center.height - 50
				* scale);

		ready_2 = CCSprite.sprite("mainUI/MK/ready_2.png");
		ready_2.setScale(scale);
		ready_2.setAnchorPoint(0.5F, 0);
		ready_2.setPosition(center.width, center.height - 50 * scale);

		ready_3 = CCSprite.sprite("mainUI/MK/ready_3.png");
		ready_3.setScale(scale);
		ready_3.setAnchorPoint(0.5F, 0);
		ready_3.setPosition(center.width + 60 * scale, center.height - 50
				* scale);

		ready_4 = CCSprite.sprite("mainUI/MK/ready_4.png");
		ready_4.setScale(scale);
		ready_4.setAnchorPoint(0.5F, 0);
		ready_4.setPosition(center.width + 120 * scale, center.height - 50
				* scale);
		ready_4.setScale(scale);

		ready_5 = CCSprite.sprite("mainUI/MK/ready_5.png");
		ready_5.setPosition(center.width + 180 * scale, center.height);
		ready_5.setScale(scale);

		hp1 = CCSprite.sprite("mainUI/heart.png");
		hp1.setAnchorPoint(0, 1);
		hp1.setScale(scale + 0.4F);
		hp1.setPosition(1, WindowUtil.winSize().height);
		hp.add(hp1);

		hp2 = CCSprite.sprite("mainUI/heart.png");
		hp2.setAnchorPoint(0, 1);
		hp2.setScale(scale + 0.4F);
		hp2.setPosition(50 * scale, WindowUtil.winSize().height);
		hp.add(hp2);

		hp3 = CCSprite.sprite("mainUI/heart.png");
		hp3.setAnchorPoint(0, 1);
		hp3.setScale(scale + 0.4F);
		hp3.setPosition(100 * scale, WindowUtil.winSize().height);
		hp.add(hp3);

		title = CCSprite.sprite("mainUI/up_title.png");
		title.setAnchorPoint(1, 1);
		title.setScale(scale);
		title.setPosition(winSize.width, winSize.height);

		sorce = CCLabelAtlas.label("000", "mainUI/font/num_combo_0.png", 77,
				112, '0');
		sorce.setScale(scale);
		sorce.setPosition(center.width - (sorce.getWidth() * scale / 2),
				winSize.height - sorce.getHeight() * scale);

	}

	@Override
	public void onEnter() {
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.ost20, true);
		new Thread() {
			public void run() {
				try {
					//......- -!
					addAction(ready_0);
					GameStateLayer.this.addChild(ready_0);
					TimeUnit.MILLISECONDS.sleep(100);

					GameStateLayer.this.addChild(ready_1);
					addAction(ready_1);
					TimeUnit.MILLISECONDS.sleep(100);

					addAction(ready_2);
					GameStateLayer.this.addChild(ready_2);
					TimeUnit.MILLISECONDS.sleep(100);

					addAction(ready_3);
					GameStateLayer.this.addChild(ready_3);
					TimeUnit.MILLISECONDS.sleep(100);

					addAction(ready_4);
					GameStateLayer.this.addChild(ready_4);
					TimeUnit.MILLISECONDS.sleep(700);

					addAction(ready_5, ready_0, ready_1, ready_2, ready_3,
							ready_4);
					GameStateLayer.this.addChild(ready_5);
					TimeUnit.MILLISECONDS.sleep(500);
					ready_5.runAction(CCFadeOut.action(0.5F));
					TimeUnit.MILLISECONDS.sleep(500);
					ready_5.removeSelf();

					GameStateLayer.this.addChild(hp1);
					GameStateLayer.this.addChild(hp2);
					GameStateLayer.this.addChild(hp3);
					GameStateLayer.this.addChild(title);
					GameStateLayer.this.addChild(sorce);
				} catch (InterruptedException e) {
					Log.e(this.getName(), e.getMessage());
				}
			}
		}.start();
	}

	private void addAction(CCSprite sprite) {
		SoundEngine.sharedEngine().playEffect(app, R.raw.s_28);
		CGPoint pot = CGPoint.ccp(sprite.getPosition().x,
				WindowUtil.CenterPoint().y);
		sprite.runAction(CCSequence.actions(CCRotateBy.action(0, 45),
				CCMoveTo.action(0.2F, pot), CCRotateBy.action(0.2F, -90),
				CCRotateBy.action(0.2F, 45)));
	}

	private void addAction(CCSprite sprite2, CCSprite... sprite) {
		SoundEngine.sharedEngine().playEffect(app, R.raw.s_39);
		sprite2.runAction(CCMoveTo.action(0.1F, WindowUtil.CenterPoint()));
		sprite[0].runAction(CCMoveTo.action(0.5F,
				CGPoint.ccp(-1000, WindowUtil.winCenter().height - 500)));
		sprite[1].runAction(CCMoveTo.action(0.5F,
				CGPoint.ccp(-1000, WindowUtil.winCenter().height - 300)));
		sprite[2].runAction(CCMoveTo.action(0.5F,
				CGPoint.ccp(-1000, WindowUtil.winCenter().height)));
		sprite[3].runAction(CCMoveTo.action(0.5F,
				CGPoint.ccp(-1000, WindowUtil.winCenter().height + 300)));
		sprite[4].runAction(CCMoveTo.action(0.5F,
				CGPoint.ccp(-1000, WindowUtil.winCenter().height + 500)));
	}

	@Override
	public void onExit() {
		super.onExit();
		SoundEngine.sharedEngine().pauseSound();
	}

	/**
	 * 修改游戏分数
	 * 
	 * @param sorceStr
	 *            分数
	 * @param reset
	 *            是否调整位置
	 */
	public static void addSorce(String sorceStr, boolean reset) {
		sorce.setString(sorceStr);
		if (reset) {
			sorce.setPosition(WindowUtil.winCenter().width
					- (sorce.getWidth() * scale / 2),
					WindowUtil.winSize().height - sorce.getHeight() * scale);

		}
	}

	public static boolean minumHP() {
		if (hp.size() > 0) {
			CCSprite sprite = hp.get(hp.size() - 1);
			hp.remove(sprite);
			sprite.removeSelf();
			return true;
		}
		return false;
	}

	public static void pause() {
		SoundEngine.sharedEngine().pauseSound();
	}
}
