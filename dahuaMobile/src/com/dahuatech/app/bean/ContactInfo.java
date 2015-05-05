package com.dahuatech.app.bean;

import java.util.List;

/**
 * @ClassName ContactInfo
 * @Description 通讯录实体
 * @author 21291
 * @date 2014年6月26日 上午9:20:36
 */
public class ContactInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;  //工号
	private String FItemName;	 //姓名
	private String FEmail;		 //邮箱
	private String FCornet;		 //短号
	private String FDepartment;  //部门
	
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

	public String getFEmail() {
		return FEmail;
	}

	public void setFEmail(String fEmail) {
		FEmail = fEmail;
	}

	public String getFCornet() {
		return FCornet;
	}

	public void setFCornet(String fCornet) {
		FCornet = fCornet;
	}

	public String getFDepartment() {
		return FDepartment;
	}

	public void setFDepartment(String fDepartment) {
		FDepartment = fDepartment;
	}
		
	public ContactInfo(){};	
	public ContactInfo(String fItemNumber,String fItemName,String fEmail,String fCornet,String fDepartment){
		this.FItemNumber=fItemNumber;
		this.FItemName=fItemName;
		this.FEmail=fEmail;
		this.FCornet=fCornet;
		this.FDepartment=fDepartment;
	}
	
	//服务端返回结果类型
	public static class ContactResultInfo{
		 public List<ContactInfo> contactList;
		 public String resultStr;
	}
}

