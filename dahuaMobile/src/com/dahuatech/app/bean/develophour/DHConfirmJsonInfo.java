package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmJsonInfo
 * @Description 研发工时确认列表项JSON实体类
 * @author 21291
 * @date 2014年11月5日 下午2:56:28
 */
public class DHConfirmJsonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FProjectCode;      //项目编码
	public String FProjectName;      //项目名称
    public String FSubChilds; 		 //子集集合
    
	public String getFProjectCode() {
		return FProjectCode;
	}

	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFSubChilds() {
		return FSubChilds;
	}

	public void setFSubChilds(String fSubChilds) {
		FSubChilds = fSubChilds;
	}	
}
