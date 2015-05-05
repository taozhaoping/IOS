package com.dahuatech.app.bean.mytask;

import java.io.Serializable;

/**
 * @ClassName WorkFlowInfo
 * @Description 工作流实体类-基础类
 * @author 21291
 * @date 2014年4月25日 上午9:35:01
 */
public class WorkFlowInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String FTaskId;       //T_WorkflowTaskers主键内码
	private String FID;			  //T_WorkflowDetails主键内码
	private String FStepFlagName; //流转节点
	private String FItemName;     //审批人
	private String FComment;      //最终意见
	private String FStatusResult; //处理结果
	private String FUpdateTime;   //处理时间
	
	public String getFTaskId() {
		return FTaskId;
	}

	public void setFTaskId(String fTaskId) {
		FTaskId = fTaskId;
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFStepFlagName() {
		return FStepFlagName;
	}
	
	public void setFStepFlagName(String fStepFlagName) {
		FStepFlagName = fStepFlagName;
	}
	
	public String getFItemName() {
		return FItemName;
	}
	
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	public String getFComment() {
		return FComment;
	}

	public void setFComment(String fComment) {
		FComment = fComment;
	}

	public String getFStatusResult() {
		return FStatusResult;
	}
	
	public void setFStatusResult(String fStatusResult) {
		FStatusResult = fStatusResult;
	}
	
	public String getFUpdateTime() {
		if(FUpdateTime==null || FUpdateTime.length()<=0)
			return "";
		else 
		{
			int index=FUpdateTime.indexOf('.');
			if(index > 0)
				return FUpdateTime.substring(0,index).replace("T"," ");	
			else 
				return FUpdateTime.replace("T"," ");	
		}	    
	}
	
	public void setFUpdateTime(String fUpdateTime) {
		FUpdateTime = fUpdateTime;
	}
	
	protected WorkFlowInfo(){}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static WorkFlowInfo instance = new WorkFlowInfo();  
    }
	private static WorkFlowInfo workFlowInfo;	
	public static WorkFlowInfo getWorkFlowInfo() {
		return singletonHolder.instance;
	}
	
	public static WorkFlowInfo getWorkFlowInfo(String fStepFlagName, String fItemName,
			String fComment, String fStatusResult, String fUpdateTime) {
		if(workFlowInfo==null){
			workFlowInfo=new WorkFlowInfo();
		}
		workFlowInfo.setFStepFlagName(fStepFlagName);
		workFlowInfo.setFItemName(fItemName);
		workFlowInfo.setFComment(fComment);
		workFlowInfo.setFStatusResult(fStatusResult);
		workFlowInfo.setFUpdateTime(fUpdateTime);
		
		return workFlowInfo;
	}
}
