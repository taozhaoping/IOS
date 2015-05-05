package com.dahuatech.app.bean;


/**
 * @ClassName ValidLogin
 * @Description 手势密码登陆验证类
 * @author 21291
 * @date 2015年2月5日 下午4:31:40
 */
public class ValidLogin extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private String FItemName; 		//员工名称
	private int FIsValid;			//工号是否有效  0-无效，1-有效
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

	public int getFIsValid() {
		return FIsValid;
	}

	public void setFIsValid(int fIsValid) {
		FIsValid = fIsValid;
	}
}
