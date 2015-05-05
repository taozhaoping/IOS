package com.dahuatech.app.widget.utli;

/**
 * @ClassName LockPoint
 * @Description 点位置实体
 * @author 21291
 * @date 2014年12月5日 上午10:31:49
 */
public class LockPoint {
	//点的状态
	public static int STATE_NORMAL = 0; 			// 未选中
	public static int STATE_CHECK = 1; 				// 选中 
	public static int STATE_CHECK_ERROR = 2; 		// 已经选中,但是输错误
	
	public float x;							//横坐标
	public float y;							//纵坐标
	public int state = 0;					//默认状态
	public int index = 0;					//下标
	
	public LockPoint() {
		
	}
	
	public LockPoint(float x,float y){
		this.x=x;
		this.y=y;
	}
}
