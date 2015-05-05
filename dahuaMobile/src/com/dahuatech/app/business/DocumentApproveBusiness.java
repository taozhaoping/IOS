package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DocumentApproveTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DocumentApproveBusiness
 * @Description 文件审批流单据业务逻辑类
 * @author 21291
 * @date 2014年8月12日 上午10:52:07
 */
public class DocumentApproveBusiness extends BaseBusiness<DocumentApproveTHeaderInfo> {

	/** 
	* @Name: DocumentApproveBusiness 
	* @Description: 默认构造函数  
	*/
	public DocumentApproveBusiness(Context context) {
		super(context,DocumentApproveTHeaderInfo.getDocumentApproveTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DocumentApproveBusiness documentApproveBusiness;
	public static DocumentApproveBusiness getDocumentApproveBusiness(Context context,String serviceUrl) {
		if (documentApproveBusiness == null) {
			documentApproveBusiness = new DocumentApproveBusiness(context);
		}
		documentApproveBusiness.setServiceUrl(serviceUrl);
		return documentApproveBusiness;
	}
	
	/** 
	* @Title: getDocumentApproveTHeaderInfo 
	* @Description: 获取文件审批流单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return DocumentApproveTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月12日 上午10:53:36
	*/
	public DocumentApproveTHeaderInfo getDocumentApproveTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
