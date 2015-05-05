package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingDetailInfo
 * @Description 会议详情实体类
 * @author 21291
 * @date 2014年9月11日 上午9:04:41
 */
public class MeetingDetailInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FId;  				//主键内码
	private String FApplyNumber;  		//申请人员工号
	private String FApplyName;  		//申请人名称
	private String FApplyDept;  		//申请部门
	private String FMeetingName;		//会议名称
	private String FMeetingDate;		//会议日期
	private String FMeetingStart;		//会议起始时间
	private String FMeetingEnd;			//会议结束时间
	private String FMeetingMasterId;	//会议主持人ID
	private String FMeetingMasterName;	//会议主持人姓名
	private String FMeetingRoom;		//会议地点
	private String FMeetingRoomId;		//会议地点ID
	private String FMeetingRoomIp;		//会议地点IP
	private String FSubEntrys;			//子集集合 (会议人员集合)

	public String getFId() {
		return FId;
	}

	public void setFId(String fId) {
		FId = fId;
	}

	public String getFApplyNumber() {
		return FApplyNumber;
	}

	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDept() {
		return FApplyDept;
	}

	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
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
	
	public String getFMeetingMasterId() {
		return FMeetingMasterId;
	}

	public void setFMeetingMasterId(String fMeetingMasterId) {
		FMeetingMasterId = fMeetingMasterId;
	}

	public String getFMeetingMasterName() {
		return FMeetingMasterName;
	}

	public void setFMeetingMasterName(String fMeetingMasterName) {
		FMeetingMasterName = fMeetingMasterName;
	}
	
	public String getFMeetingRoom() {
		return FMeetingRoom;
	}

	public void setFMeetingRoom(String fMeetingRoom) {
		FMeetingRoom = fMeetingRoom;
	}

	public String getFMeetingRoomId() {
		return FMeetingRoomId;
	}

	public void setFMeetingRoomId(String fMeetingRoomId) {
		FMeetingRoomId = fMeetingRoomId;
	}

	public String getFMeetingRoomIp() {
		return FMeetingRoomIp;
	}

	public void setFMeetingRoomIp(String fMeetingRoomIp) {
		FMeetingRoomIp = fMeetingRoomIp;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static MeetingDetailInfo instance = new MeetingDetailInfo();  
    }
	public MeetingDetailInfo() {}
	public static MeetingDetailInfo getMeetingDetailInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(MeetingDetailInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FId", item.FId);
			jsonObject.put("FApplyNumber", item.FApplyNumber);
			jsonObject.put("FApplyName", item.FApplyName);
			jsonObject.put("FApplyDept", item.FApplyDept);
			jsonObject.put("FMeetingName", item.FMeetingName);
			jsonObject.put("FMeetingDate", item.FMeetingDate);
			jsonObject.put("FMeetingStart", item.FMeetingStart);
			jsonObject.put("FMeetingEnd", item.FMeetingEnd);	
			jsonObject.put("FMeetingMasterId", item.FMeetingMasterId);
			jsonObject.put("FMeetingMasterName", item.FMeetingMasterName);
			jsonObject.put("FMeetingRoom", item.FMeetingRoom);
			jsonObject.put("FMeetingRoomId", item.FMeetingRoomId);
			jsonObject.put("FMeetingRoomIp", item.FMeetingRoomIp);
			jsonObject.put("FSubEntrys", item.FSubEntrys);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
