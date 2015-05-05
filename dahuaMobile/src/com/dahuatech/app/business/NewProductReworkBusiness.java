package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NewProductReworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NewProductReworkBusiness
 * @Description 新产品返工单据业务逻辑类
 * @author 21291
 * @date 2014年8月27日 上午9:44:34
 */
public class NewProductReworkBusiness extends BaseBusiness<NewProductReworkTHeaderInfo> {

	/** 
	* @Name: NewProductReworkBusiness 
	* @Description: 默认构造函数  
	*/
	public NewProductReworkBusiness(Context context) {
		super(context,NewProductReworkTHeaderInfo.getNewProductReworkTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static NewProductReworkBusiness newProductReworkBusiness;
	public static NewProductReworkBusiness getNewProductReworkBusiness(Context context,String serviceUrl) {
		if (newProductReworkBusiness == null) {
			newProductReworkBusiness = new NewProductReworkBusiness(context);
		}
		newProductReworkBusiness.setServiceUrl(serviceUrl);
		return newProductReworkBusiness;
	}
	
	/** 
	* @Title: getNewProductReworkTHeaderInfo 
	* @Description: 获取新产品返工单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return NewProductReworkTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月27日 上午9:45:41
	*/
	public NewProductReworkTHeaderInfo getNewProductReworkTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
