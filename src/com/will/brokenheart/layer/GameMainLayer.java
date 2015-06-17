package com.will.brokenheart.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.instant.CCCallFuncND;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.will.brokenheart.R;
import com.will.brokenheart.util.StringUtil;
import com.will.brokenheart.util.WindowUtil;

/**
 * 游戏操作层
 * 
 * @author William
 * 
 */
public class GameMainLayer extends CCLayer {

	private List<CCSprite> sprites;
	private CGSize winSize;
	private Context app;
	private static CCScene mainScene;
	private boolean first;
	private static int cc;
	private int combo;
	private long currentTime;
	private long preTime;
	private float scale; // 缩放比例
	private int sorce;
	private int count;
	private Thread t1;
	private CCSprite preCombo;
	/* 难度变量 */
	private int delayTime = 3; // 红心保持显示时间
	// 红心出现频率
	private int speed = 2000;
	private int speedMin = 1000;
	private EndLayer end;

	public GameMainLayer(Context app) {
		setIsTouchEnabled(true);
		SoundEngine.sharedEngine().preloadEffect(app, R.raw.s_39);
		first = true;
		end = new EndLayer(app);
		this.app = app;
		scale = WindowUtil.winScale();
		sprites = new LinkedList<CCSprite>();
		sprites = Collections.synchronizedList(sprites);
		winSize = WindowUtil.winSize();

		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(
				"mainUI/heartbreakertexture.plist");

		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(
				"effect_effectB.plist");
	}

	@Override
	public void onEnter() {
		this.runAction(CCTintBy.action(1));
		super.onEnter();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
				}
				// 开始游戏
				makeHeart();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (cc >= 3) {
						mainScene.addChild(CCColorLayer.node(
								ccColor4B.ccc4(21, 18, 18, 100), winSize.width,
								winSize.height), 2);
						mainScene.addChild(end, 3);
						pause();
						pauseSchedulerAndActions();
						cc = 0;
						return;
					}
				}
			}
		}).start();

	}

	private void makeHeart() {
		final float minX = winSize.width * 0.1F;
		final float maxX = winSize.width - minX;
		final float minY = winSize.height * 0.2F;
		final float maxY = winSize.height - minY;

		t1 = new Thread() {
			public void run() {
				while (!Thread.interrupted()) {
					CCSprite sprite = CCSprite.sprite("rozpad_01.png", true);
					sprite.setScale(scale + 0.4F);

					// 两个数值之间的随机数
					float spriteX = (float) (minX + Math.random()
							* (maxX - minX + 1F));
					float spriteY = (float) (minY + Math.random()
							* (maxY - minY + 1F));

					// 生成❤精灵
					sprite.setPosition(spriteX, spriteY);
					heartSpriteAction(sprite);

					try {
						TimeUnit.MILLISECONDS.sleep((long) (speedMin + Math
								.random() * (speed - speed + 1F)));
						GameMainLayer.this.addChild(sprite);
						sprites.add(sprite);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		};
		t1.start();
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// 鼠标位置
		final CGPoint location = CCDirector.sharedDirector().convertToGL(
				CGPoint.ccp(event.getX(), event.getY()));

		for (int i = sprites.size() - 1; i >= 0; --i) {
			final CCSprite sprite = sprites.get(i);
			float K = 2.5F;
			CGRect smallbox = CGRect
					.make(sprite.getPosition().x
							- sprite.getContentSize().width / K,
							sprite.getPosition().y
									- sprite.getContentSize().height / K,
							(sprite.getContentSize().width / K) * 2,
							(sprite.getContentSize().height / K) * 2);
			if (CGRect.containsPoint(smallbox, location)) {
				SoundEngine.sharedEngine().playEffect(app, R.raw.s_39);
				if (sorce >= 19) {
					attackAction("effect_attack2_1", "effect_attack2_",
							location.x - 50, location.y, 9);
				} else {
					attackAction("effect_attack3_1", "effect_attack3_",
							location.x, location.y, 5);
				}
				if (first) {
					first = false;
					preTime = System.currentTimeMillis();
					currentTime = System.currentTimeMillis();
				} else {
					currentTime = System.currentTimeMillis();
				}

				if (currentTime - preTime < 2300) {
					preTime = System.currentTimeMillis();
					currentTime = 0;
					combo++;
				} else {
					currentTime = 0;
					preTime = 0;
					combo = 0;
					first = true;
				}

				if (combo == 10) {
					CCSprite comb = CCSprite.sprite("mainUI/combo.png");
					comb.setAnchorPoint(0, 0);
					comb.setScale(scale);
					comb.setPosition(0, winSize.height - winSize.height * 0.2F);
					comb.runAction(CCSequence.actions(CCMoveTo.action(
							0.2F,
							CGPoint.ccp(winSize.width * 0.2F, winSize.height
									- winSize.height * 0.2F)), CCDelayTime
							.action(2), CCCallFuncN.action(this, "removeSelf")));

					this.addChild(comb, 2);
					preCombo = comb;
				} else if (combo > 10) {
					preCombo.removeSelf();
					preCombo = null;
					CCSprite comb2 = CCSprite.sprite("mainUI/combo.png");
					Log.d("123", comb2.hashCode() + " HHHH");
					comb2.setAnchorPoint(0, 0);
					comb2.setScale(scale);
					comb2.setPosition(winSize.width * 0.2F, winSize.height
							- winSize.height * 0.2F);
					CCScaleBy scaleAction = CCScaleBy.action(0.1F, 0.8F);
					comb2.runAction(CCSequence.actions(scaleAction,
							scaleAction.reverse(), CCDelayTime.action(2),
							CCCallFuncN.action(this, "removeSelf")));
					this.addChild(comb2, 2);
					preCombo = comb2;
				}

				sprites.remove(i);
				sprite.stopAllActions();

				boolean reset = false;
				if (sorce == 999 || sorce == 9999) {
					reset = true;
				}

				// 自动补零
				String strNum = StringUtil.autoPushZero(sorce += 1, null);
				addComplex();

				GameStateLayer.addSorce(strNum, reset);
				sprite.runAction(CCSequence.actions(CCFadeOut.action(0.5F),
						CCCallFuncN.action(this, "removeSelf")));
				attackAction("effect_tonado_1", "effect_tonado_", location.x,
						location.y, 13);
				break;
			}
		}
		return CCTouchDispatcher.kEventHandled;
	}


	/**
	 * 缩放动画 ,延迟，删除，回调
	 * 
	 * @param heart
	 */
	private void heartSpriteAction(CCSprite heart) {
		CCScaleBy scaleBy = CCScaleBy.action(0.7F, 1.1F);
		CCTintBy tint = CCTintBy.action(0.7F, ccColor3B.ccc3(64, 17, 65));
		CCRepeat repeat = CCRepeat.action(
				CCSequence.actions(scaleBy, scaleBy.reverse(), tint.reverse()),
				delayTime);
		CCCallFuncND callback = CCCallFuncND.action(this, "callbackND", "");
		CCSequence sequence = CCSequence.actions(repeat, callback);
		heart.runAction(sequence);
	}

	public void callbackND(Object sender, Object data) {
		final CCSprite heart = (CCSprite) sender;
		sprites.remove(heart);
		heart.runAction(CCFadeOut.action(0.5F));
		new Thread() {
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
				}
				heart.removeFromParentAndCleanup(true);
			};
		}.start();
		if (count++ >= 3) {
			count = 0;
			GameStateLayer.minumHP();
			cc++;
		}

	}

	public static CCScene scene(Context app) {
		mainScene = CCScene.node();
		mainScene.addChild(new BackgroundLayer("map_5.png"), -1, 1);
		mainScene.addChild(new GameMainLayer(app), 1);
		mainScene.addChild(new GameStateLayer(app), 2);
		return mainScene;
	}

	private void addComplex() {
		if (sorce == 10) {
			speed = 1000;
		} else if (sorce == 20) {
			mainScene.removeChildByTag(1, false);
			mainScene.addChild(new BackgroundLayer("map_3.png"), -1, 2);
			speedMin = 750;
		} else if (sorce == 50) {
			speed = 800;
		} else if (sorce == 100) {
			mainScene.removeChildByTag(2, false);
			mainScene.addChild(new BackgroundLayer("map_2.png"), -1, 3);
			speed = 1800;
			speedMin = 1000;
			makeHeart();
		} else if (sorce == 150) {
			delayTime = 2;
		} else if (sorce == 200) {
			mainScene.removeChildByTag(3, false);
			speed = 1500;
		} else if (sorce == 300) {
			speedMin = 900;
		} else if (sorce == 600) {
			mainScene.removeChildByTag(4, false);
			mainScene.addChild(new BackgroundLayer("map_1.png"), -1, 5);
		}
	}

	private void attackAction(String name2, String name, float x, float y,
			int size) {
		CCSprite sprite1 = CCSprite.sprite(name2, true);
		sprite1.setScale(scale);
		sprite1.setPosition(x, y);

		ArrayList<CCSpriteFrame> frames1 = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i <= size; i++) {
			String pngName = name + i;
			CCSpriteFrame f = CCSpriteFrameCache.sharedSpriteFrameCache()
					.spriteFrameByName(pngName);
			frames1.add(f);
		}
		CCAnimation animation1 = CCAnimation.animation("guai", 0.1F, frames1);
		CCAnimate animate1 = CCAnimate.action(animation1, false);
		sprite1.runAction(CCSequence.actions(animate1,
				CCCallFuncN.action(this, "removeSelf")));
		this.addChild(sprite1);
	}

	public void removeSelf(Object sender) {
		CCSprite sp = (CCSprite) sender;
		sp.removeFromParentAndCleanup(true);
	}

	public void goneSelf(Object sender) {
		CCSprite sp = (CCSprite) sender;
		sp.setVisible(false);
	}

	public void pause() {
		GameStateLayer.pause();
		this.pauseSchedulerAndActions();
		t1.interrupt();
	}

	@Override
	public void onExit() {
		super.onExit();
		t1.interrupt();
		stopAllActions();
	}
}
