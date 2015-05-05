package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTBodyInfo
 * @Description  研发出差派遣表体1单据实体 
 * @author 21291
 * @date 2014年8月12日 上午10:27:12
 */

public class DevelopTravelTBodyOneInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FSchedule; 		//日程安排
	private String FStartTime; 		//开始时间
	private String FEndTime; 		//结束时间
	private String FNote; 			//备注
	
	public String getFSchedule() {
		return FSchedule;
	}
	public void setFSchedule(String fSchedule) {
		FSchedule = fSchedule;
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
