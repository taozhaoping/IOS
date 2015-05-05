package com.dahuatech.app.bean.develophour;

import java.util.List;

/**
 * @ClassName DHListProjectInfo
 * @Description 研发工时列表具体项项目实体类
 * @author 21291
 * @date 2014年10月24日 下午5:29:51
 */
public class DHListProjectInfo extends DHProjectInfo {
	private static final long serialVersionUID = 1L;

	private String FHours;		    				//工作小时
	private List<DHListTypeInfo> dListTypeInfo;		//子集集合
	
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public List<DHListTypeInfo> getdListTypeInfo() {
		return dListTypeInfo;
	}
	public void setdListTypeInfo(List<DHListTypeInfo> dListTypeInfo) {
		this.dListTypeInfo = dListTypeInfo;
	}
}
