package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.EmailOpenTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName EmailOpenBusiness
 * @Description 邮箱开通单据业务逻辑类
 * @author 21291
 * @date 2014年8月18日 上午10:45:53
 */
public class EmailOpenBusiness extends BaseBusiness<EmailOpenTHeaderInfo> {

	/** 
	* @Name: DocumentApproveBusiness 
	* @Description: 默认构造函数  
	*/
	public EmailOpenBusiness(Context context) {
		super(context,EmailOpenTHeaderInfo.getEmailOpenTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static EmailOpenBusiness emailOpenBusiness;
	public static EmailOpenBusiness getEmailOpenBusiness(Context context,String serviceUrl) {
		if (emailOpenBusiness == null) {
			emailOpenBusiness = new EmailOpenBusiness(context);
		}
		emailOpenBusiness.setServiceUrl(serviceUrl);
		return emailOpenBusiness;
	}
	
	/** 
	* @Title: getEmailOpenTHeaderInfo 
	* @Description: 获取邮箱开通单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return EmailOpenTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月18日 上午10:47:00
	*/
	public EmailOpenTHeaderInfo getEmailOpenTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
