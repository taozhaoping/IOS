package com.dahuatech.app.bean.meeting;

import com.dahuatech.app.bean.ContactInfo;

/**
 * 
* @Title: MeetingSearchContactInfo.java 
* @Package com.dahuatech.app.bean.meeting 
* @tags 通讯录查询实体类
* @Description: 通讯录查询实体类
* @date 2015-3-12 上午9:15:35 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class MeetingSearchContactInfo extends ContactInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
