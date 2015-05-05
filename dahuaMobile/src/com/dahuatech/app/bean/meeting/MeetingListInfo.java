package com.dahuatech.app.bean.meeting;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingListInfo
 * @Description 我的会议列表实体类
 * @author 21291
 * @date 2014年9月11日 下午2:14:57
 */
public class MeetingListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FId;  			//会议主键内码
	private String FStatus;  		//会议状态 0-代表自身创建 可以修改和取消 1-他人创建 只能查看
	private String FCreate;  		//申请人
	private String FMeetingName;	//会议名称
	private String FMeetingDate;	//会议日期
	private String FMeetingStart;	//会议起始时间
	private String FMeetingEnd;		//会议结束时间
	private int FRecordCount;		//总的会议记录
	
	public String getFId() {
		return FId;
	}
	public void setFId(String fId) {
		FId = fId;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	public String getFCreate() {
		return FCreate;
	}
	public void setFCreate(String fCreate) {
		FCreate = fCreate;
	}
	public String getFMeetingName() {
		return FMeetingName;
	}
	public void setFMeetingName(String fMeetingName) {
		FMeetingName = fMeetingName;
	}
	public String getFMeetingDate() {
		return FMeetingDate;
	}
	public void setFMeetingDate(String fMeetingDate) {
		FMeetingDate = fMeetingDate;
	}
	public String getFMeetingStart() {
		return FMeetingStart;
	}
	public void setFMeetingStart(String fMeetingStart) {
		FMeetingStart = fMeetingStart;
	}
	public String getFMeetingEnd() {
		return FMeetingEnd;
	}
	public void setFMeetingEnd(String fMeetingEnd) {
		FMeetingEnd = fMeetingEnd;
	}
	public int getFRecordCount() {
		return FRecordCount;
	}
	public void setFRecordCount(int fRecordCount) {
		FRecordCount = fRecordCount;
	}
	
}
