package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListChildInfo
 * @Description 研发工时确认列表项具体子项子级类
 * @author 21291
 * @date 2014年11月5日 下午2:53:47
 */
public class DHConfirmListChildInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FWeekDay;       		//工作日期
	private String FHours;				//工时
	
	public String getFWeekDay() {
		return FWeekDay;
	}
	public void setFWeekDay(String fWeekDay) {
		FWeekDay = fWeekDay;
	}
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	} 
}
