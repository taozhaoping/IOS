package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyDaysOffDevelopInfo
 * @Description 研发部门调休申请单据实体
 * @author 21291
 * @date 2014年7月23日 下午2:53:29
 */
public class ApplyDaysOffDevelopInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FBillNo;			//单据编号
	private String FApplyName;		//申请人
	private String FApplyDate;		//申请时间
	private String FApplyDept;		//申请部门
	private String FTypeName;		//调休类型
	private String FSumDays;		//调休天数
	private String FStartTime;		//上午时间
	private String FEndTime;		//下午时间
	private String FReason;			//调休原因
	
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

	public String getFTypeName() {
		return FTypeName;
	}

	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}

	public String getFSumDays() {
		return FSumDays;
	}

	public void setFSumDays(String fSumDays) {
		FSumDays = fSumDays;
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

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}

	//私有构造函数，防止被实例化 
	private ApplyDaysOffDevelopInfo() {}
	//内部类实现单例模式  延迟加载,线程安全（java中class加载时互斥的）,减少内存开销
	private static class singletonHolder {  
        private static ApplyDaysOffDevelopInfo instance = new ApplyDaysOffDevelopInfo();  
    }  
	public static ApplyDaysOffDevelopInfo getApplyDaysOffDevelopInfo() {
		return singletonHolder.instance;
	}
}
