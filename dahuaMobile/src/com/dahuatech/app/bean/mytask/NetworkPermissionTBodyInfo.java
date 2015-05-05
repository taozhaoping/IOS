package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NetworkPermissionTBodyInfo
 * @Description 网络权限申请单据相关子类实体
 * @author 21291
 * @date 2014年7月9日 下午3:49:47
 */
public class NetworkPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FIpAddress; 		//所在楼层
	private String FComboBox1;  	//开通有效期限
	private String FComboBox3;		//申请权限类别
	private String FDate1;			//起始时间
	
	public String getFIpAddress() {
		return FIpAddress;
	}
	public void setFIpAddress(String fIpAddress) {
		FIpAddress = fIpAddress;
	}
	public String getFComboBox1() {
		return FComboBox1;
	}
	public void setFComboBox1(String fComboBox1) {
		FComboBox1 = fComboBox1;
	}
	public String getFComboBox3() {
		return FComboBox3;
	}
	public void setFComboBox3(String fComboBox3) {
		FComboBox3 = fComboBox3;
	}
	public String getFDate1() {
		return FDate1;
	}
	public void setFDate1(String fDate1) {
		FDate1 = fDate1;
	}
}
