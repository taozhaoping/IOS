package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHTypeInfo
 * @Description 研发工时任务类型实体
 * @author 21291
 * @date 2014年10月28日 下午5:33:10
 */
public class DHTypeInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FTypeId;			//类型Id
	public String FTypeName;		//类型名称
	
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}
	public String getFTypeName() {
		return FTypeName;
	}
	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}
}
