package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NewProductLibTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NewProductLibBusiness
 * @Description 新产品转库单据业务逻辑类
 * @author 21291
 * @date 2014年8月12日 上午10:54:55
 */
public class NewProductLibBusiness extends BaseBusiness<NewProductLibTHeaderInfo> {

	/** 
	* @Name: NewProductLibBusiness 
	* @Description: 默认构造函数  
	*/
	public NewProductLibBusiness(Context context) {
		super(context,NewProductLibTHeaderInfo.getNewProductLibTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static NewProductLibBusiness newProductLibBusiness;
	public static NewProductLibBusiness getNewProductLibBusiness(Context context,String serviceUrl) {
		if (newProductLibBusiness == null) {
			newProductLibBusiness = new NewProductLibBusiness(context);
		}
		newProductLibBusiness.setServiceUrl(serviceUrl);
		return newProductLibBusiness;
	}
	
	/** 
	* @Title: getNewProductLibTHeaderInfo 
	* @Description:  获取新产品转库单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return NewProductLibTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月12日 上午10:55:36
	*/
	public NewProductLibTHeaderInfo getNewProductLibTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
