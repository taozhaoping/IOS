package com.dahuatech.app.widget.utli;

/**
 * @ClassName RoundUtil
 * @Description 圆工具类
 * @author 21291
 * @date 2014年12月5日 下午1:56:55
 */
public class RoundUtil {

	/** 
	* @Title: checkInRound 
	* @Description: 检查两点的距离是否在圆的半径内
	* @param @param sx
	* @param @param sy
	* @param @param r
	* @param @param x
	* @param @param y
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 下午1:57:20
	*/
	public static boolean checkInRound(float sx, float sy, float r, float x,float y) {
		return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
	}

}
