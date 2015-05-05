package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName RepositoryInfo
 * @Description 仓储类
 * @author 21291
 * @date 2014年4月23日 下午2:44:11
 */
public class RepositoryInfo extends Base {

	private static final long	serialVersionUID	= 1L;
	
	private String ClassType;   //系统类型ID
	private String IsTest;      //是否测试 0 测试 1 正式
	private String ServiceName; // 服务名称
		
	//服务类型 1 数据服务  2 字段权限服务 3 菜单权限服务 4 数据权限服务 5.工作流服务
	private String ServiceType; 
	private boolean SqlType;  	// 是否存储过程
	private boolean IsCahce; 	// 是否需要缓存
	private String  FItemNumber;// 统计日志信息时候用到的员工号

	public String getClassType() {
		return ClassType;
	}

	public void setClassType(String classType) {
		ClassType = classType;
	}

	public String getIsTest() {
		return IsTest;
	}

	public void setIsTest(String isTest) {
		IsTest = isTest;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public boolean isSqlType() {
		return SqlType;
	}

	public void setSqlType(boolean sqlType) {
		SqlType = sqlType;
	}

	public boolean isIsCahce() {
		return IsCahce;
	}

	public void setIsCahce(boolean isCahce) {
		IsCahce = isCahce;
	}

	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	private RepositoryInfo() {
		SqlType = false;
		IsCahce = false;
		FItemNumber="";
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static RepositoryInfo instance = new RepositoryInfo();  
    }
	public static RepositoryInfo getRepositoryInfo() {
		return singletonHolder.instance;
	}
    
    // 将实体对象转化成json以便于在网络中传输
    public static String ConvertToJson(RepositoryInfo item) {
        String jsonString = "";
        JSONObject jsonObject = new JSONObject();
        try {
	    	  jsonObject.put("ClassType", item.ClassType);  
	          jsonObject.put("IsTest", item.IsTest);  
	          jsonObject.put("ServiceName", item.ServiceName);  
	          jsonObject.put("ServiceType", item.ServiceType);  
	          jsonObject.put("SqlType", item.SqlType);  
	          jsonObject.put("IsCahce", item.IsCahce);  
	          jsonObject.put("FItemNumber", item.FItemNumber);  
	          jsonString = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }	
}
