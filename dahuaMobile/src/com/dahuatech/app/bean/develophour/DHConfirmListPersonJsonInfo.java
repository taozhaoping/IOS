package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListPersonJsonInfo
 * @Description 研发工时确认列表项具体实体JSON类
 * @author 21291
 * @date 2014年11月5日 下午2:45:39
 */
public class DHConfirmListPersonJsonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FTypeId; 			//任务ID
	private String FTypeName; 			//任务类型
	private String FHours;				//工时
	private String FSubChilds; 			//子集集合

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

	public String getFHours() {
		return FHours;
	}

	public void setFHours(String fHours) {
		FHours = fHours;
	}

	public String getFSubChilds() {
		return FSubChilds;
	}

	public void setFSubChilds(String fSubChilds) {
		FSubChilds = fSubChilds;
	}	
}
