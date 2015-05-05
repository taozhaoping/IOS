package com.dahuatech.app.bean.develophour;

/**
 * @ClassName DHListTypeInfo
 * @Description 研发工时列表具体项目任务类型实体类
 * @author 21291
 * @date 2014年10月24日 下午5:04:43
 */
public class DHListTypeInfo extends DHTypeInfo {
	private static final long serialVersionUID = 1L;
	
	private String FHours;		    //工作小时
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
}
