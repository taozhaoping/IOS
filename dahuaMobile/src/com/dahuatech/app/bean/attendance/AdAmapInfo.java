package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdAmapInfo
 * @Description 我的考勤中心地点实体
 * @author 21291
 * @date 2014年12月31日 上午10:47:43
 */
public class AdAmapInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAddressType;	//具体地址类型
	private String FAddress;		//具体地址
	private String FLatitude;  		//地址坐标纬度
	private String FLongitude;  	//地址坐标经度
	private String FRadius;			//半径
	
	public String getFAddressType() {
		return FAddressType;
	}
	public void setFAddressType(String fAddressType) {
		FAddressType = fAddressType;
	}
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}
	public String getFLatitude() {
		return FLatitude;
	}
	public void setFLatitude(String fLatitude) {
		FLatitude = fLatitude;
	}
	public String getFLongitude() {
		return FLongitude;
	}
	public void setFLongitude(String fLongitude) {
		FLongitude = fLongitude;
	}
	public String getFRadius() {
		return FRadius;
	}
	public void setFRadius(String fRadius) {
		FRadius = fRadius;
	}
}
