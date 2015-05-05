package com.dahuatech.app.widget.utli;

/**
 * @ClassName MathUtil
 * @Description 算术工具类
 * @author 21291
 * @date 2014年12月5日 下午1:15:50
 */
public class MathUtil {
	/** 
	* @Title: distance 
	* @Description: 计算两点间的距离  绝对值和的平方根
	* @param @param x1
	* @param @param y1
	* @param @param x2
	* @param @param y2
	* @param @return     
	* @return double    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 下午1:16:39
	*/
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
	}
	
	/** 
	* @Title: pointTotoDegrees 
	* @Description: 计算点a(x,y)的角度
	* @param @param x
	* @param @param y
	* @param @return     
	* @return double    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 下午1:20:43
	*/
	public static double pointTotoDegrees(double x, double y) {
		return Math.toDegrees(Math.atan2(x, y));
	}

}
