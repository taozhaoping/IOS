package com.dahuatech.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * @ClassName DelSlideScrollLinerLayout
 * @Description 手势布局类
 * @author 21291
 * @date 2014年5月15日 上午11:30:08
 */
public class DelSlideScrollLinerLayout extends LinearLayout {

	private Scroller mScroller;
	private boolean pressed = true;
	
	public DelSlideScrollLinerLayout(Context context,AttributeSet attrs) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		mScroller = new Scroller(context);
	}
	
	public void onDown() {
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}
	}
	
	@Override
	public void setPressed(boolean pressed) {
		if (this.pressed)
			super.setPressed(pressed);
		else{
			super.setPressed(this.pressed);
		}
	}

	public void setSingleTapUp(boolean pressed) {
		this.pressed = pressed;
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	public int getToX() {
		return mScroller.getCurrX();
	}

	public void snapToScreen(int whichScreen) {
		mScroller.startScroll(whichScreen, 0, 0, 0, 50);
		invalidate();
	}
}
