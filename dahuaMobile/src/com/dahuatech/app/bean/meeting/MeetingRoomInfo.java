package com.dahuatech.app.bean.meeting;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName MeetingRoomInfo
 * @Description 会议室实体类
 * @author 21291
 * @date 2014年9月11日 上午9:20:46
 */
public class MeetingRoomInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FRoomId; 	//主键内码
	private String FRoomName; 	//会议室名称
	private String FRoomIp; 	//会议室IP
	private String FType; 		//会议室冲突状态 0 未冲突  1是冲突
	private int FRecordCount; 	//总的会议室记录

	public String getFRoomId() {
		return FRoomId;
	}
	
	public void setFRoomId(String fRoomId) {
		FRoomId = fRoomId;
	}

	public String getFRoomName() {
		return FRoomName;
	}

	public void setFRoomName(String fRoomName) {
		FRoomName = fRoomName;
	}

	public String getFRoomIp() {
		return FRoomIp;
	}

	public void setFRoomIp(String fRoomIp) {
		FRoomIp = fRoomIp;
	}
	
	public String getFType() {
		return FType;
	}

	public void setFType(String fType) {
		FType = fType;
	}

	public int getFRecordCount() {
		return FRecordCount;
	}

	public void setFRecordCount(int fRecordCount) {
		FRecordCount = fRecordCount;
	}
	
	//默认构造函数
	public MeetingRoomInfo() {}
}
