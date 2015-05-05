package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName EmailOpenTBodyInfo
 * @Description 邮箱开通表体单据实体
 * @author 21291
 * @date 2014年8月18日 上午10:44:29
 */
public class EmailOpenTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FEmailAddress; 		//邮箱地址
	private String FStartTime; 			//起始日期
	private String FApplyDeadLine; 	    //申请权限期限
	
	public String getFEmailAddress() {
		return FEmailAddress;
	}
	public void setFEmailAddress(String fEmailAddress) {
		FEmailAddress = fEmailAddress;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFApplyDeadLine() {
		return FApplyDeadLine;
	}
	public void setFApplyDeadLine(String fApplyDeadLine) {
		FApplyDeadLine = fApplyDeadLine;
	}
}
