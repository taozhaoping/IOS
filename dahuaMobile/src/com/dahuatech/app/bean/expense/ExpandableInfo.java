package com.dahuatech.app.bean.expense;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpandableInfo
 * @Description 我的流水可展开实体信息
 * @author 21291
 * @date 2014年8月25日 下午5:30:09
 */
public class ExpandableInfo extends Base {

	private static final long serialVersionUID = 1L;
	private String FGroupTitle;		    //群组标题
	private String FSubEntrys;			//子集集合
	
	public String getFGroupTitle() {
		return FGroupTitle;
	}
	public void setFGroupTitle(String fGroupTitle) {
		FGroupTitle = fGroupTitle;
	}
	
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
}
