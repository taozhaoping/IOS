package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DoorPermissionTBodyInfo
 * @Description 门禁权限申请单据表体实体
 * @author 21291
 * @date 2014年8月21日 下午2:19:22
 */
public class DoorPermissionTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FPosition;   	//工作位置
	private String FPermission;		//申请开通权限
	private String FNote;	    	//备注
	
	public String getFPosition() {
		return FPosition;
	}
	public void setFPosition(String fPosition) {
		FPosition = fPosition;
	}
	public String getFPermission() {
		return FPermission;
	}
	public void setFPermission(String fPermission) {
		FPermission = fPermission;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
