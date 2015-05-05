package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHProjectInfo
 * @Description 研发工时项目实体类
 * @author 21291
 * @date 2014年10月28日 下午5:34:53
 */
public class DHProjectInfo extends Base {
	private static final long serialVersionUID = 1L;

	public String FProjectCode;					//项目编号
	public String FProjectName;					//项目名称
	
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

	public DHProjectInfo(){}
}
