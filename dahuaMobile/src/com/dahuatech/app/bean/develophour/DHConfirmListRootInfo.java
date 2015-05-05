package com.dahuatech.app.bean.develophour;

import java.util.ArrayList;
import java.util.List;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListRootInfo
 * @Description 研发工时确认列表项具体子项根级类
 * @author 21291
 * @date 2014年11月5日 下午2:49:47
 */
public class DHConfirmListRootInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FTypeId; 							//任务ID
	private String FTypeName;  							//任务类型
	private String FHours;								//工时	
	private List<DHConfirmListChildInfo> FChildren;		//子集集合
		
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
	public List<DHConfirmListChildInfo> getFChildren() {
		return FChildren;
	}
	public void setFChildren(List<DHConfirmListChildInfo> fChildren) {
		FChildren = fChildren;
	}
	
	public DHConfirmListRootInfo(){
		FChildren=new ArrayList<DHConfirmListChildInfo>();
	}
}
