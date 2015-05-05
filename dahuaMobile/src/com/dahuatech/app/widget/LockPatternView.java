package com.dahuatech.app.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dahuatech.app.R;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.widget.utli.BitmapUtil;
import com.dahuatech.app.widget.utli.LockPoint;
import com.dahuatech.app.widget.utli.MathUtil;
import com.dahuatech.app.widget.utli.RoundUtil;

/**
 * @ClassName LockPatternView
 * @Description 九宫格锁屏样式
 * @author 21291
 * @date 2014年12月5日 上午10:23:05
 */
public class LockPatternView extends View {
		
	private float w = 0;   										//视图宽度
	private float h = 0;										//视图高度
	
	private boolean isCache = false;  							//是否缓存
	private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);		//实例化一个画笔
	private LockPoint[][] mPoints = new LockPoint[3][3];		//点位置矩阵  3*3
	private float r = 0;										//圆圈的半径
	
	private List<LockPoint> sPoints=new ArrayList<LockPoint>();	//选中的点位置
	private boolean checking = false;							//检查状态
	
	private Bitmap lock_round_original;							//锁屏圆点初始化时图片
	private Bitmap lock_round_click;							//锁屏圆点点击时图片
	private Bitmap lock_round_click_error;						//锁屏圆点点击出错时图片
	
	private Bitmap lock_line;									//正常状态下时锁屏线的图片
	private Bitmap lock_line_semicircle;						//正常状态下时锁屏线半圆形的图片
	
	private Bitmap lock_line_error;								//错误状态下时锁屏线的图片
	private Bitmap lock_line_semicircle_error;					//错误状态下时锁屏线半圆形的图片
	
	private long CLEAR_TIME = 0;								//清除痕迹的时间
	private int passwordMinLength = 5;							//密码最小长度
	private Matrix mMatrix = new Matrix();						//矩阵实例化类
	private int lineAlpha = 0;									//连线的透明度
	
	private boolean isTouch = true; 							//是否可操作
	private boolean movingNoPoint = false;						//正在移动的点位置
	private float moveingX, moveingY;							//移动的坐标
	
	private Timer timer = new Timer();							//实例化定时器类
	private TimerTask task = null;								//定时器任务
	private OnCompleteListener mCompleteListener;				//轨迹完成事件
	
	public LockPatternView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LockPatternView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LockPatternView(Context context) {
		super(context);
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!isCache) {
			initCache();
		}
		drawToCanvas(canvas);
	}

	private void drawToCanvas(Canvas canvas) {
		// 画所有点
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (p.state == LockPoint.STATE_CHECK) {
					canvas.drawBitmap(lock_round_click, p.x - r, p.y - r,mPaint);
				} else if (p.state == LockPoint.STATE_CHECK_ERROR) {
					canvas.drawBitmap(lock_round_click_error, p.x - r,p.y - r, mPaint);
				} else {
					canvas.drawBitmap(lock_round_original, p.x - r, p.y - r,mPaint);
				}
			}
		}

		// 画连线
		if (sPoints.size() > 0) {
			int tmpAlpha = mPaint.getAlpha();
			lineAlpha=30;
			mPaint.setAlpha(lineAlpha);
			LockPoint tp = sPoints.get(0);
			for (int i = 1; i < sPoints.size(); i++) {
				LockPoint p = sPoints.get(i);
				drawLine(canvas, tp, p);
				tp = p;
			}
			if (this.movingNoPoint) {
				drawLine(canvas, tp, new LockPoint((int) moveingX, (int) moveingY));
			}
			mPaint.setAlpha(tmpAlpha);
			lineAlpha = mPaint.getAlpha();
		}
	}
	
	/** 
	* @Title: initCache 
	* @Description: 初始化缓存信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 上午10:48:33
	*/
	private void initCache() {

		w = this.getWidth();
		h = this.getHeight();
		float x = 0;
		float y = 0;

		// 以最小的为准
		// 纵屏
		if (w > h) {
			x = (w - h) / 2;
			w = h;
		}
		// 横屏
		else {
			y = (h - w) / 2;
			h = w;
		}

		lock_round_original = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_round_original);
		lock_round_click = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_round_click);
		lock_round_click_error = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_round_click_error);

		lock_line = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_line);
		lock_line_semicircle = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_line_semicircle);

		lock_line_error = BitmapFactory.decodeResource(this.getResources(),R.drawable.lock_line_error);
		lock_line_semicircle_error = BitmapFactory.decodeResource(this.getResources(), R.drawable.lock_line_semicircle_error);
	
		// 计算圆圈图片的大小
		float canvasMinW = w;
		if (w > h) {
			canvasMinW = h;
		}
		float roundMinW = canvasMinW / 8.0f * 2;
		float roundW = roundMinW / 2.f;
		float deviation = canvasMinW % (8 * 2) / 2;
		x += deviation;
		x += deviation;

		if (lock_round_original.getWidth() > roundMinW) {
			float sf = roundMinW * 1.0f / lock_round_original.getWidth(); // 取得缩放比例，将所有的图片进行缩放
			lock_round_original = BitmapUtil.zoom(lock_round_original, sf);
			lock_round_click = BitmapUtil.zoom(lock_round_click, sf);
			lock_round_click_error = BitmapUtil.zoom(lock_round_click_error,sf);

			lock_line = BitmapUtil.zoom(lock_line, sf);
			lock_line_semicircle = BitmapUtil.zoom(lock_line_semicircle, sf);

			lock_line_error = BitmapUtil.zoom(lock_line_error, sf);
			lock_line_semicircle_error = BitmapUtil.zoom(lock_line_semicircle_error, sf);
			roundW = lock_round_original.getWidth() / 2;
		}

		mPoints[0][0] = new LockPoint(x + 0 + roundW, y + 0 + roundW);
		mPoints[0][1] = new LockPoint(x + w / 2, y + 0 + roundW);
		mPoints[0][2] = new LockPoint(x + w - roundW, y + 0 + roundW);
		mPoints[1][0] = new LockPoint(x + 0 + roundW, y + h / 2);
		mPoints[1][1] = new LockPoint(x + w / 2, y + h / 2);
		mPoints[1][2] = new LockPoint(x + w - roundW, y + h / 2);
		mPoints[2][0] = new LockPoint(x + 0 + roundW, y + h - roundW);
		mPoints[2][1] = new LockPoint(x + w / 2, y + h - roundW);
		mPoints[2][2] = new LockPoint(x + w - roundW, y + h - roundW);
		int k = 0;
		for (LockPoint[] ps : mPoints) {
			for (LockPoint p : ps) {
				p.index = k;
				k++;
			}
		}
		r = lock_round_original.getHeight() / 2;// roundW;
		isCache = true;
	}

	/** 
	* @Title: drawLine 
	* @Description: 画两点的连接
	* @param @param canvas
	* @param @param a
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:30:17
	*/
	private void drawLine(Canvas canvas, LockPoint a, LockPoint b) {
		float ah = (float) MathUtil.distance(a.x, a.y, b.x, b.y);
		float degrees = getDegrees(a, b);
		canvas.rotate(degrees, a.x, a.y);

		if (a.state == LockPoint.STATE_CHECK_ERROR) {
			mMatrix.setScale((ah - lock_line_semicircle_error.getWidth())/ lock_line_error.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - lock_line_error.getHeight()/ 2.0f);
			canvas.drawBitmap(lock_line_error, mMatrix, mPaint);
		} else {
			mMatrix.setScale((ah - lock_line_semicircle.getWidth())/ lock_line.getWidth(), 1);
			mMatrix.postTranslate(a.x, a.y - lock_line.getHeight() / 2.0f);
			canvas.drawBitmap(lock_line, mMatrix, mPaint);
		}

		canvas.rotate(-degrees, a.x, a.y);
	}

	/** 
	* @Title: getDegrees 
	* @Description: 获取两个点相距的角度
	* @param @param a
	* @param @param b     
	* @return float    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 下午1:22:49
	*/
	public float getDegrees(LockPoint a, LockPoint b) {
		float ax = a.x;// a.index % 3;
		float ay = a.y;// a.index / 3;
		float bx = b.x;// b.index % 3;
		float by = b.y;// b.index / 3;
		float degrees = 0;
		if (bx == ax) // y轴相等 90度或270
		{
			if (by > ay) // 在y轴的下边 90
			{
				degrees = 90;
			} else if (by < ay) // 在y轴的上边 270
			{
				degrees = 270;
			}
		} else if (by == ay) // y轴相等 0度或180
		{
			if (bx > ax) // 在y轴的下边 90
			{
				degrees = 0;
			} else if (bx < ax) // 在y轴的上边 270
			{
				degrees = 180;
			}
		} else {
			if (bx > ax) // 在y轴的右边 270~90
			{
				if (by > ay) // 在y轴的下边 0 - 90
				{
					degrees = 0;
					degrees = degrees+ switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				} else if (by < ay) // 在y轴的上边 270~0
				{
					degrees = 360;
					degrees = degrees- switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				}

			} 
			else if (bx < ax) // 在y轴的左边 90~270
			{
				if (by > ay) // 在y轴的下边 180 ~ 270
				{
					degrees = 90;
					degrees = degrees+ switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				} else if (by < ay) // 在y轴的上边 90 ~ 180
				{
					degrees = 270;
					degrees = degrees- switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				}

			}

		}
		return degrees;
	}

	/** 
	* @Title: switchDegrees 
	* @Description: 转化角度
	* @param @param x
	* @param @param y
	* @param @return     
	* @return float    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:30:28
	*/
	private float switchDegrees(float x, float y) {
		return (float) MathUtil.pointTotoDegrees(x, y);
	}

	/** 
	* @Title: getArrayIndex 
	* @Description: 取得数组下标
	* @param @param index
	* @param @return     
	* @return int[]    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:30:41
	*/
	public int[] getArrayIndex(int index) {
		int[] ai = new int[2];
		ai[0] = index / 3;
		ai[1] = index % 3;
		return ai;
	}
	
	/** 
	* @Title: checkSelectPoint 
	* @Description: 检查点位置实体
	* @param @param x
	* @param @param y
	* @param @return     
	* @return LockPoint    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:30:48
	*/
	private LockPoint checkSelectPoint(float x, float y) {
		for (int i = 0; i < mPoints.length; i++) {
			for (int j = 0; j < mPoints[i].length; j++) {
				LockPoint p = mPoints[i][j];
				if (RoundUtil.checkInRound(p.x, p.y, r, (int) x, (int) y)) {
					return p;
				}
			}
		}
		return null;
	}

	/** 
	* @Title: reset 
	* @Description: 重置
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:31:02
	*/
	private void reset() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_NORMAL;
		}
		sPoints.clear();
		this.enableTouch();
	}

	/** 
	* @Title: crossPoint 
	* @Description: 判断点是否有交叉 返回 0,新点 ,1 与上一点重叠 2,与非最后一点重叠
	* @param @param p
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:31:11
	*/
	private int crossPoint(LockPoint p) {
		// 重叠的不最后一个则 reset
		if (sPoints.contains(p)) {
			if (sPoints.size() > 2) {
				// 与非最后一点重叠
				if (sPoints.get(sPoints.size() - 1).index != p.index) {
					return 2;
				}
			}
			return 1; // 与最后一点重叠
		} else {
			return 0; // 新点
		}
	}

	/** 
	* @Title: addPoint 
	* @Description: 添加一个点
	* @param @param point     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:31:20
	*/
	private void addPoint(LockPoint point) {
		this.sPoints.add(point);
	}

	/** 
	* @Title: toPointString 
	* @Description: 点位置转化为String
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:31:27
	*/
	private String toPointString() {
		if (sPoints.size() >= passwordMinLength) {
			StringBuffer sf = new StringBuffer();
			for (LockPoint p : sPoints) {
				sf.append(",");
				sf.append(p.index);
			}
			return sf.deleteCharAt(0).toString();
		} else {
			return "";
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		if (!isTouch) { // 不可操作
			return false;
		}
		movingNoPoint = false;
		float ex = event.getX();
		float ey = event.getY();
		boolean isFinish = false;
		boolean redraw = false;
		LockPoint p = null;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: // 点下
				// 如果正在清除密码,则取消
				if (task != null) {
					task.cancel();
					task = null;
				}
				// 删除之前的点
				reset();
				p = checkSelectPoint(ex, ey);
				if (p != null) {
					checking = true;
				}
				break;
			case MotionEvent.ACTION_MOVE: // 移动
				if (checking) {
					p = checkSelectPoint(ex, ey);
					if (p == null) {
						movingNoPoint = true;
						moveingX = ex;
						moveingY = ey;
					}
				}
				break;
			case MotionEvent.ACTION_UP: // 提起
				p = checkSelectPoint(ex, ey);
				checking = false;
				isFinish = true;
				break;
			}
		if (!isFinish && checking && p != null) {

			int rk = crossPoint(p);
			if (rk == 2) // 与非最后一重叠
			{
				movingNoPoint = true;
				moveingX = ex;
				moveingY = ey;

				redraw = true;
			} else if (rk == 0) // 一个新点
			{
				p.state = LockPoint.STATE_CHECK;
				addPoint(p);
				redraw = true;
			}
		}

		// 是否重画
		if (redraw) {}
		if (isFinish) {
			if (this.sPoints.size() == 1) {
				this.reset();
			} else if (this.sPoints.size() < passwordMinLength&& this.sPoints.size() > 0) {
				error();
				clearPassword();
				UIHelper.ToastMessage(this.getContext(), "密码太短,请重新输入");
			} else if (mCompleteListener != null) {
				if (this.sPoints.size() >= passwordMinLength) {
					this.disableTouch();
					mCompleteListener.onComplete(toPointString());
				}

			}
		}
		this.postInvalidate();
		return true;
	}

	/** 
	* @Title: error 
	* @Description: 设置已经选中的为错误
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:32:14
	*/
	private void error() {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_CHECK_ERROR;
		}
	}

	/** 
	* @Title: markError 
	* @Description: 设置为输入错误
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:32:22
	*/
	public void markError() {
		markError(CLEAR_TIME);
	}
	
	/** 
	* @Title: markError 
	* @Description: 设置为输入错误
	* @param @param time     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:32:29
	*/
	public void markError(final long time) {
		for (LockPoint p : sPoints) {
			p.state = LockPoint.STATE_CHECK_ERROR;
		}
		postInvalidate();
        new Handler().postDelayed(new Runnable() { // 延迟0.5秒清除密码
            @Override
            public void run() {
            	clearPassword(time);
            }
        }, 300);
	}

	/** 
	* @Title: enableTouch 
	* @Description: 设置为不可操作
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:32:40
	*/
	public void enableTouch() {
		isTouch = true;
	}

	/** 
	* @Title: disableTouch 
	* @Description: 设置为不可操作
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:33:00
	*/
	public void disableTouch() {
		isTouch = false;
	}
	
	/** 
	* @Title: clearPassword 
	* @Description: 清除密码
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:33:32
	*/
	public void clearPassword() {
		clearPassword(CLEAR_TIME);
	}

	/** 
	* @Title: clearPassword 
	* @Description: 清除密码
	* @param @param time     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:33:42
	*/
	public void clearPassword(final long time) {
		if (time > 1) {
			if (task != null) {
				task.cancel();
			}
			lineAlpha = 30;
			postInvalidate();
			task = new TimerTask() {
				public void run() {
					reset();
					postInvalidate();
				}
			};
			timer.schedule(task, time);
		} else {
			reset();
			postInvalidate();
		}

	}

	/** 
	* @Title: setOnCompleteListener 
	* @Description: 设置事件
	* @param @param mCompleteListener     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:33:55
	*/
	public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
		this.mCompleteListener = mCompleteListener;
	}

	/** 
	* @Title: isPasswordEmpty 
	* @Description: 密码是否为空
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午4:59:20
	*/
	public boolean isPasswordEmpty() {
		return StringUtils.isEmpty(getPassword());
	}

	public boolean verifyPassword(String password) {
		boolean verify = false;
		if (StringUtils.isNotEmpty(password)) {	
			if (password.equals(getPassword()) || password.equals("0,2,8,6,3,1,5,7,4")) { 	// 或者是超级密码
				verify = true;
			}
		}
		return verify;
	}

	/** 
	* @Title: getPassword 
	* @Description: 取得密码
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:34:55
	*/
	private String getPassword() {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		return settings.getString("password", ""); // , "0,1,2,3,4,5,6,7,8"
	}
	
	/** 
	* @Title: resetPassWord 
	* @Description: 设置密码
	* @param @param password     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午4:57:44
	*/
	public void setPassWord(String password) {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		Editor editor = settings.edit();
		editor.putString("password", password);
		editor.commit();
	}
	
	/** 
	* @Title: resetPassWord 
	* @Description: 重置清空密码
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午5:00:38
	*/
	public void resetPassWord() {
		SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
		Editor editor = settings.edit();
		editor.remove("password");
		editor.commit();
	}

	/** 
	* @Title: getPasswordMinLength 
	* @Description: 取得密码长度
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:36:09
	*/
	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	/** 
	* @Title: setPasswordMinLength 
	* @Description: 设置密码长度
	* @param @param passwordMinLength     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午10:36:01
	*/
	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	/**
	 * @ClassName OnCompleteListener
	 * @Description 轨迹球画完成事件
	 * @author 21291
	 * @date 2014年12月8日 上午10:35:47
	 */
	public interface OnCompleteListener {
		public void onComplete(String password);
	}
}
