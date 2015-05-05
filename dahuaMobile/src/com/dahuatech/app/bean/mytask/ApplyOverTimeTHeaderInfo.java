package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyOverTimeTHeaderInfo
 * @Description 加班申请单据表头实体
 * @author 21291
 * @date 2014年7月23日 下午2:26:07
 */
public class ApplyOverTimeTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//单据编号
	private String FApplyName;			//申请人
	private String FApplyDate;			//申请时间
	private String FOverTimeCount;		//申请加班数
	private String FDaysOffCount;		//申请调休数
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

	public String getFOverTimeCount() {
		return FOverTimeCount;
	}

	public void setFOverTimeCount(String fOverTimeCount) {
		FOverTimeCount = fOverTimeCount;
	}

	public String getFDaysOffCount() {
		return FDaysOffCount;
	}

	public void setFDaysOffCount(String fDaysOffCount) {
		FDaysOffCount = fDaysOffCount;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ApplyOverTimeTHeaderInfo instance = new ApplyOverTimeTHeaderInfo();  
    }  
	private ApplyOverTimeTHeaderInfo() {}
	public static ApplyOverTimeTHeaderInfo getApplyOverTimeTHeaderInfo() {
		return singletonHolder.instance;
	}
}
