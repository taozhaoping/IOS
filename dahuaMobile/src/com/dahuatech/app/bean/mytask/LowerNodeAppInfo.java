package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName LowerNodeAppInfo
 * @Description 下级节点审批状态实体
 * @author 21291
 * @date 2014年11月10日 下午4:06:36
 */
public class LowerNodeAppInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FNodeId;			//当前节点ID
	private String FNodeName;		//当前节点名称
	private int FIsMust;			//当前节点流程是否必须要走   0-代表不必走，1-代表必须要走
	
	//当前节点状态  	
	// 0-代表该审批单据没有下级节点审批这个按钮，也就是没有这个功能
	// 1-代表审批人，通过搜索框来获取人员信息  
	// 2-代表审批人，从附带出的流程节点配置的审批人信息来获取
	// 3-代表审批人，既不通过搜索来获取，也不通过附带出的人员来获取，自动生成审批人
	private int FNodeStatus;		
	private String FSubEntrys;			//子集集合  针对当前节点状态值为2的情况，其他情况为空
	
	public String getFNodeId() {
		return FNodeId;
	}
	public void setFNodeId(String fNodeId) {
		FNodeId = fNodeId;
	}
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public int getFIsMust() {
		return FIsMust;
	}
	public void setFIsMust(int fIsMust) {
		FIsMust = fIsMust;
	}
	public int getFNodeStatus() {
		return FNodeStatus;
	}
	public void setFNodeStatus(int fNodeStatus) {
		FNodeStatus = fNodeStatus;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}

	public LowerNodeAppInfo() {
		this.FSubEntrys="";
	}
}
