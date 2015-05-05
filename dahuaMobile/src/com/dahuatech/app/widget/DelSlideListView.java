package com.dahuatech.app.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.dahuatech.app.R;
import com.dahuatech.app.inter.IListViewonSingleTapUpListenner;
import com.dahuatech.app.inter.IOnDeleteListioner;

@SuppressLint("ClickableViewAccessibility")
public class DelSlideListView extends ListView implements GestureDetector.OnGestureListener, View.OnTouchListener {

	private GestureDetector mDetector;  //手势实现类
	private IOnDeleteListioner mOnDeleteListioner; //删除事件接口实现类
	private IListViewonSingleTapUpListenner thisonSingleTapUpListenner; //手势触发事件类
	private int position;					//下标
	@SuppressWarnings("unused")
	private float velocityX, velocityY;		//X轴  Y轴 
	
	public IOnDeleteListioner getmOnDeleteListioner() {
		return mOnDeleteListioner;
	}

	public void setmOnDeleteListioner(IOnDeleteListioner mOnDeleteListioner) {
		this.mOnDeleteListioner = mOnDeleteListioner;
	}

	public IListViewonSingleTapUpListenner getThisonSingleTapUpListenner() {
		return thisonSingleTapUpListenner;
	}

	public void setThisonSingleTapUpListenner(
			IListViewonSingleTapUpListenner thisonSingleTapUpListenner) {
		this.thisonSingleTapUpListenner = thisonSingleTapUpListenner;
	}
	
	private int standard_touch_target_size = 0;
	private float mLastMotionX;
	public boolean deleteView = false;
	private DelSlideScrollLinerLayout mScrollLinerLayout;
	private boolean scroll = false;
	private int pointToPosition;
	private boolean listViewMoving;
	private boolean delAll = false;
	public boolean isLongPress = false;
	
	public DelSlideListView(Context context) {
		super(context);
		init(context);
	}

	public DelSlideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public boolean isDelAll() {
		return delAll;
	}

	public void setDelAll(boolean delAll) {
		this.delAll = delAll;
	}
	
	public void setScroll(boolean b) {
		listViewMoving = b;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	private void init(Context mContext) {
		mDetector = new GestureDetector(mContext, this);
		mDetector.setIsLongpressEnabled(false);
		standard_touch_target_size = (int) getResources().getDimension(R.dimen.delete_action_len);
		this.setOnTouchListener(this);
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (isDelAll()) {
			return false;
		} else {
			if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
				int deltaX2 = (int) (mLastMotionX - event.getX());
				if (scroll) {
					if (!deleteView && deltaX2 >= standard_touch_target_size / 2) {
						mScrollLinerLayout.snapToScreen(standard_touch_target_size);
						position = pointToPosition - this.getFirstVisiblePosition();
						deleteView = true;
					} else {
						position = -1;
						deleteView = false;
						mScrollLinerLayout.snapToScreen(0);
					}
					scroll = false;
					return true;
				}
			}
			return mDetector.onTouchEvent(event);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (scroll || deleteView) {
			return true;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (thisonSingleTapUpListenner != null) {
			thisonSingleTapUpListenner.onSingleTapUp();
		}
		mLastMotionX = e.getX();
		pointToPosition = this.pointToPosition((int) e.getX(), (int) e.getY());
		final int p = pointToPosition - this.getFirstVisiblePosition();
		if (mScrollLinerLayout != null) {
			mScrollLinerLayout.onDown();
			mScrollLinerLayout.setSingleTapUp(true);
		}
		if (deleteView && p != position) {
			deleteView = false;
			if (mScrollLinerLayout != null) {
				mScrollLinerLayout.snapToScreen(0);
				mScrollLinerLayout.setSingleTapUp(false);
			}
			position = p;
			scroll = false;
			return true;
		}
		isLongPress = false;
		position = p;
		scroll = false;
		listViewMoving = false;
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {}
	
	@Override
	public void onLongPress(MotionEvent e) {}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if (deleteView) {  //若发生删除事件
			position = -1;
			deleteView = false;
			mScrollLinerLayout.snapToScreen(0);
			scroll = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (listViewMoving && !scroll) {
			if (mScrollLinerLayout != null)
				mScrollLinerLayout.snapToScreen(0);
			return false;
		} else if (scroll) {
			if (mScrollLinerLayout != null) {
				int deltaX = (int) (mLastMotionX - e2.getX());
				if (deleteView) {
					deltaX += standard_touch_target_size;
				}
				if (deltaX >= 0 && deltaX <= standard_touch_target_size) {
					mScrollLinerLayout.scrollBy(deltaX - mScrollLinerLayout.getScrollX(), 0);
				}
			}
		} else {
			if (Math.abs(distanceX) > Math.abs(distanceY)) {
				final int pointToPosition1 = this.pointToPosition((int) e2.getX(), (int) e2.getY());
				final int p1 = pointToPosition1 - this.getFirstVisiblePosition();
				if (p1 == position && mOnDeleteListioner.isCandelete(p1)) {
					mScrollLinerLayout = (DelSlideScrollLinerLayout) this.getChildAt(p1);
					if (mScrollLinerLayout != null) {
						int deltaX = (int) (mLastMotionX - e2.getX());
						if (deleteView) {
							deltaX += standard_touch_target_size;
						}
						if (deltaX >= 0 && deltaX <= standard_touch_target_size && Math.abs(distanceY) < 5) {
							isLongPress = true;
							scroll = true;
							listViewMoving = false;
							mScrollLinerLayout.setSingleTapUp(false);
							mScrollLinerLayout.scrollBy((int) (e1.getX() - e2.getX()), 0);

						}
					}
				}
			}
		}
		if (scroll) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		return false;
	}
	
	public void deleteItem() {
		position = -1;
		deleteView = false;
		scroll = false;
		if (mScrollLinerLayout != null) {
			mScrollLinerLayout.snapToScreen(0);
		}
	}

}
