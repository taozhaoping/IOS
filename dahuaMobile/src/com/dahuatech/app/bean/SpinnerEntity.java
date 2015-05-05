package com.dahuatech.app.bean;

/**
 * @ClassName SpinnerEntity
 * @Description Spinner控件实体类
 * @author 21291
 * @date 2014年10月24日 下午1:32:18
 */
public class SpinnerEntity extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FKey;
	private String FValue;
	
	public String getFKey() {
		return FKey;
	}

	public void setFKey(String fKey) {
		FKey = fKey;
	}

	public String getFValue() {
		return FValue;
	}

	public void setFValue(String fValue) {
		FValue = fValue;
	}

	public SpinnerEntity(){
		this.FKey="";
		this.FValue="";
	}
	
	public SpinnerEntity(String fKey,String fValue){
		this.FKey=fKey;
		this.FValue=fValue;
	}
}
