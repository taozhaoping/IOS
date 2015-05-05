package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExAttendanceTHeaderInfo
 * @Description 异常考勤调整申请单据表头实体
 * @author 21291
 * @date 2014年7月23日 下午2:39:42
 */
public class ExAttendanceTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//单据编号
	private String FApplyName;			//申请人
	private String FApplyDate;			//申请时间
	private String FApplyDept;			//申请部门
	private String FSubEntrys;			//子集集合
	
	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFApplyDept() {
		return FApplyDept;
	}

	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ExAttendanceTHeaderInfo instance = new ExAttendanceTHeaderInfo();  
    }
	private ExAttendanceTHeaderInfo() {}
	public static ExAttendanceTHeaderInfo getExAttendanceTHeaderInfo() {
		return singletonHolder.instance;
	}
}
