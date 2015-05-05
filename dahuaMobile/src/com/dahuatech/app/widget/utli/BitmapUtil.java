package com.dahuatech.app.widget.utli;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @ClassName BitmapUtil
 * @Description 图片处理工具类
 * @author 21291
 * @date 2014年12月5日 上午11:22:34
 */
public class BitmapUtil {

	/** 
	* @Title: zoom 
	* @Description: 设置图片比例缩放
	* @param @param bitmap
	* @param @param zf
	* @param @return     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014年12月5日 上午11:23:44
	*/
	public static Bitmap zoom(Bitmap bitmap, float zf) {
		Matrix matrix = new Matrix();		//实例化矩阵
		matrix.postScale(zf, zf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
	}
	
	/** 
	* @Title: zoom 
	* @Description: 设置图片比例缩放
	* @param @param bitmap
	* @param @param wf
	* @param @param hf
	* @param @return     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014年12月8日 上午9:53:34
	*/
	public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
	}

}
