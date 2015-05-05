package com.dahuatech.app.common;

import com.amap.api.maps.model.LatLng;

public class AmapConstants {

	public static final int ERROR = 1001;	//网络异常

	public static final LatLng COMPANY_1199 = new LatLng(30.183744, 120.173553);//公司1199号经纬度
	public static final LatLng COMPANY_1187 = new LatLng(30.184021, 120.179272);//公司1187号经纬度
	
	public static final long INTERVALFIVESECOND=5000;	   //定时请求间隔5秒
	public static final float FIXMETER=15;				   //固定距离15米
	
	public static final float RADIUS=100;				   //围栏半径
	public static final long EXPIRATION_TIME=-1;		   //围栏过期时间 设置为-1时表示没有时间限制 ,毫秒为单位
}
