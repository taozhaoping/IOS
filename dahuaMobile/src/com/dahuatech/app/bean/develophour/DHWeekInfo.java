package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHWeekInfo
 * @Description 研发工时周实体类
 * @author 21291
 * @date 2014年10月24日 上午9:27:29
 */
public class DHWeekInfo extends Base {
	private static final long serialVersionUID = 1L;

	public int FYear;			//年份
	public int FIndex;			//周次
	public String FStartDate;	//开始时间
	public String FEndDate;		//结束时间
	
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}
	public int getFIndex() {
		return FIndex;
	}
	public void setFIndex(int fIndex) {
		FIndex = fIndex;
	}
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFEndDate() {
		return FEndDate;
	}
	public void setFEndDate(String fEndDate) {
		FEndDate = fEndDate;
	}
	
}
