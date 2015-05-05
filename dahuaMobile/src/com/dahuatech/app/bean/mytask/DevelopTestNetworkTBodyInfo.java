package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTestNetworkTBodyInfo
 * @Description 研发项目测试网络权限表体单据实体
 * @author 21291
 * @date 2014年7月15日 下午4:35:41
 */
public class DevelopTestNetworkTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyForIp;     //申请权限设备IP地址
	private String FPurposeIp;		//目的IP地址/域名
	private String FStartTime;		//起始日期
	private String FEndTime;		//结束日期
	
	public String getFApplyForIp() {
		return FApplyForIp;
	}
	
	public void setFApplyForIp(String fApplyForIp) {
		FApplyForIp = fApplyForIp;
	}
	
	public String getFPurposeIp() {
		return FPurposeIp;
	}
	
	public void setFPurposeIp(String fPurposeIp) {
		FPurposeIp = fPurposeIp;
	}
	
	public String getFStartTime() {
		return FStartTime;
	}
	
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	
	public String getFEndTime() {
		return FEndTime;
	}
	
	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}
}
