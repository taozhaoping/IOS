package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppResultInfo
 * @Description 节点选取的结果实体类
 * @author 21291
 * @date 2014年11月12日 下午1:53:01
 */
public class LowerNodeAppResultInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int FIsMust;					//当前节点流程是否必须要走   0-代表不必走，1-代表必须要走
	private String FNodeId;					//当前节点ID
	private String FNodeName;				//当前节点名称
	private String FSelectResult;			//每个节点选取的结果值
	private String FShowResult;				//每个节点显示结果值
	
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
	public String getFSelectResult() {
		return FSelectResult;
	}
	public void setFSelectResult(String fSelectResult) {
		FSelectResult = fSelectResult;
	}
	public LowerNodeAppResultInfo() {
		this.FSelectResult="";
	}
	public String getFShowResult() {
		return FShowResult;
	}
	public void setFShowResult(String fShowResult) {
		FShowResult = fShowResult;
	}
	
}
