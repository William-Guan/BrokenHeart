package com.will.brokenheart.util;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.Log;

/**
 * 窗口工具类
 * 
 * @author William
 * 
 */
public class WindowUtil {
	private static CGSize winSize = CCDirector.sharedDirector().winSize();

	public static CGSize winSize() {
		return CGSize.make(winSize.width, winSize.height);
	}

	public static CGSize winCenter() {
		return CGSize.make(winSize.width / 2, winSize.height / 2);
	}

	public static CGPoint CenterPoint() {
		CGSize size = winCenter();
		return CGPoint.ccp(size.width, size.height);
	}

	public static float winScale() {
		Log.d(WindowUtil.class.getName(), winSize.width+"");
		if(winSize.width >=2048){
			return 1.5F;
		}
		if(winSize.width >= 900){
			return 1F;
		}
		if(winSize.width >= 600){
			return 0.7F;
		}
		if(winSize.width>= 480){
			return 0.5F;
		}
		return 0;
	}
}
