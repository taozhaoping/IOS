package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName NodeInfo
 * @Description 驳回节点实体类
 * @author 21291
 * @date 2014年4月29日 上午10:04:58
 */
public class RejectNodeInfo extends Base  {
	private static final long serialVersionUID = 1L;
	
	private String FNodeKey;  //驳回节点名称
	private String FNodeValue; //驳回节点值
	
	public String getFNodeKey() {
		return FNodeKey;
	}

	public void setFNodeKey(String fNodeKey) {
		FNodeKey = fNodeKey;
	}

	public String getFNodeValue() {
		return FNodeValue;
	}

	public void setFNodeValue(String fNodeValue) {
		FNodeValue = fNodeValue;
	}

	public RejectNodeInfo() {
		super();
	}
	
	public RejectNodeInfo(String fNodeKey,String fNodeValue){
		super();
		this.FNodeKey=fNodeKey;
		this.FNodeValue=fNodeValue;
	}
}
