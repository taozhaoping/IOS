package com.dahuatech.app.bean.develophour;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName DHConfirmChildInfo
 * @Description 研发工时确认列表子级项实体类  (用到了观察者模式-既是观察者类又是被观察者类)
 * @author 21291
 * @date 2014年10月20日 上午11:43:03
 */
public class DHConfirmChildInfo extends Observable implements Observer {

	private String FItemNumber;		//确认人员员工号
	private String FItemName;		//确认人员员工名称
	private boolean isChecked;		//是否选中
   
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}

	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public DHConfirmChildInfo(){
		this.isChecked=false;
	}
	
	public void changeChecked(){  //改变状态
		isChecked = !isChecked;
		super.setChanged();
		super.notifyObservers();
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof Boolean) {
			this.isChecked = (Boolean) data;
		}
	}
}
