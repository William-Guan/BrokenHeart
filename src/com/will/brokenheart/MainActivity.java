package com.will.brokenheart;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.will.brokenheart.layer.StartUpAnimationLayer;

public class MainActivity extends Activity {

	// 渲染应用程序实例
	private CCGLSurfaceView mainView;
	private CCDirector director;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		mainView = new CCGLSurfaceView(this);
		director = CCDirector.sharedDirector();
		setContentView(mainView);
		// attach the OpenGL view to a window
		director.attachInView(mainView);
		// Make the Scene active
		director.runWithScene(StartUpAnimationLayer.scene(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CCDirector.sharedDirector().end();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		CCDirector.sharedDirector().onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CCDirector.sharedDirector().onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
