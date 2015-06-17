package com.will.brokenheart.layer;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.ccColor4B;

public class ColorLayer extends CCLayer {
	
	public ColorLayer() {
		CCColorLayer.node(ccColor4B.ccc4(21, 18, 18, 100));
	}
}
