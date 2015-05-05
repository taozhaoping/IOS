package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTravelTBodyTwoInfo
 * @Description  研发出差派遣表体2单据实体 
 * @author 21291
 * @date 2014年8月15日 上午9:48:25
 */
public class DevelopTravelTBodyTwoInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FTravelName; 	//出差人姓名
	private String FTravelDept; 	//出差人部门名称
	private String FDeptManager; 	//部门经理
	private String FStartTime; 		//实际开始时间
	private String FEndTime; 		//实际结束时间
	
	public String getFTravelName() {
		return FTravelName;
	}
	public void setFTravelName(String fTravelName) {
		FTravelName = fTravelName;
	}
	public String getFTravelDept() {
		return FTravelDept;
	}
	public void setFTravelDept(String fTravelDept) {
		FTravelDept = fTravelDept;
	}
	public String getFDeptManager() {
		return FDeptManager;
	}
	public void setFDeptManager(String fDeptManager) {
		FDeptManager = fDeptManager;
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
