package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Entity;

/**
 * @ClassName TaskInfo
 * @Description 任务实体类
 * @author 21291
 * @date 2014年4月22日 下午5:26:45
 */
public class TaskInfo extends Entity {

	private static final long serialVersionUID = 1L;
	
	private int FMenuID;	  //菜单内码
	private String FBillID;   //单据内码
	private String FTitle;	  //单据标题
	private String FSender;	  //申请人
	private String FSendTime; //申请时间
	
	private int FClassTypeID;   	//单据类型ID
	private String FClassTypeName;  //单据类型名称
	
	private int FSystemType;		//系统ID
	private int FTotalCount;		//总的记录数

	public int getFMenuID() {
		return FMenuID;
	}

	public void setFMenuID(int fMenuID) {
		FMenuID = fMenuID;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	public String getFTitle() {
		return FTitle;
	}

	public void setFTitle(String fTitle) {
		FTitle = fTitle;
	}

	public String getFSender() {
		return FSender;
	}

	public void setFSender(String fSender) {
		FSender = fSender;
	}

	public String getFSendTime() {
		if(FSendTime==null || FSendTime.length()<=0)
			return "";
		else 
		{
			int index=FSendTime.indexOf('.');
			if(index > 0)
				return FSendTime.substring(0,index).replace("T"," ");	
			else 
				return FSendTime.replace("T"," ");	
		}	 
	}

	public void setFSendTime(String fSendTime) {
		FSendTime = fSendTime;
	}

	public int getFClassTypeID() {
		return FClassTypeID;
	}

	public void setFClassTypeID(int fClassTypeID) {
		FClassTypeID = fClassTypeID;
	}

	public String getFClassTypeName() {
		return FClassTypeName;
	}

	public void setFClassTypeName(String fClassTypeName) {
		FClassTypeName = fClassTypeName;
	}
	
	public int getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(int fSystemType) {
		FSystemType = fSystemType;
	}
	
	public int getFTotalCount() {
		return FTotalCount;
	}

	public void setFTotalCount(int fTotalCount) {
		FTotalCount = fTotalCount;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static TaskInfo instance = new TaskInfo();  
    }
	public TaskInfo() {}
	public static TaskInfo getTaskInfo() {
		return singletonHolder.instance;
	}
}
