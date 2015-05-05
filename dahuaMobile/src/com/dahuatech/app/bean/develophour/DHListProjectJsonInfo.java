package com.dahuatech.app.bean.develophour;

/**
 * @ClassName DHListProjectJsonInfo
 * @Description 研发工时列表具体项项目实体Json类
 * @author 21291
 * @date 2014年10月24日 下午5:24:27
 */
public class DHListProjectJsonInfo extends DHProjectInfo {
	private static final long serialVersionUID = 1L;

	private String FHours;		    //工作小时
	private String FSubEntrys;		//子集集合
	
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
}
