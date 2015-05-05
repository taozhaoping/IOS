package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName SvnPermissionTBodyInfo
 * @Description SVN权限表体单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:47:49
 */
public class SvnPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FAddress;   			//权限访问地址
	private String FReadOrWrite;		//读或写
	private String FResponsible;		//负责人
	private String FReason;				//原因
	
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}
	public String getFReadOrWrite() {
		return FReadOrWrite;
	}
	public void setFReadOrWrite(String fReadOrWrite) {
		FReadOrWrite = fReadOrWrite;
	}
	public String getFResponsible() {
		return FResponsible;
	}
	public void setFResponsible(String fResponsible) {
		FResponsible = fResponsible;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
