package com.dahuatech.app.bean.develophour;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName DHConfirmRootInfo
 * @Description 研发工时确认列表根级项实体类  (用到了观察者模式-既是观察者类又是被观察者类)
 * @author 21291
 * @date 2014年10月20日 上午11:29:12
 */
public class DHConfirmRootInfo extends Observable implements Observer {  //表示可以被观察
	
	public String FProjectCode;      				//项目编码
	public String FProjectName; 					//项目名称
    public List<DHConfirmChildInfo> FChildren; 		//子集集合
	private boolean isChecked;						//是否选中

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

	public List<DHConfirmChildInfo> getFChildren() {
		return FChildren;
	}

	public void setFChildren(List<DHConfirmChildInfo> fChildren) {
		FChildren = fChildren;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public DHConfirmRootInfo(){
		this.isChecked=false;
		FChildren=new ArrayList<DHConfirmChildInfo>();
	}
	
	public void changeChecked(){
		isChecked = !isChecked;
		super.setChanged();  //设置变化点 
		super.notifyObservers(isChecked);  //通知有变化了
	}

	@Override
	public void update(Observable observable, Object data) {  //具体变化操作
		boolean flag = true;
		for (DHConfirmChildInfo dChildInfo : FChildren) {
			if (dChildInfo.isChecked() == false) {
				flag = false;
			}
		}
		this.isChecked = flag;	
	}
	
}
