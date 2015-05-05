package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TrainComputerTBodyInfo
 * @Description 培训电算化教室申请单据表体实体
 * @author 21291
 * @date 2014年8月21日 上午9:29:11
 */
public class TrainComputerTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FApplyDesc;   	//申请说明
	private String FStartTime;		//开始时间
	private String FEndTime;	    //结束时间
	private String FNote;	    	//备注
	
	public String getFApplyDesc() {
		return FApplyDesc;
	}
	public void setFApplyDesc(String fApplyDesc) {
		FApplyDesc = fApplyDesc;
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
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
