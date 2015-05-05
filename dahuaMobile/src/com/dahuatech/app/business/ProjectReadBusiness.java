package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ProjectReadTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ProjectReadBusiness
 * @Description 项目信息阅读权限单据业务逻辑类
 * @author 21291
 * @date 2014年9月23日 下午2:49:55
 */
public class ProjectReadBusiness extends BaseBusiness<ProjectReadTHeaderInfo> {

	/** 
	* @Name: ProjectReadBusiness 
	* @Description:  
	*/
	public ProjectReadBusiness(Context context) {
		super(context,ProjectReadTHeaderInfo.getProjectReadTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ProjectReadBusiness projectReadBusiness;
	public static ProjectReadBusiness getProjectReadBusiness(Context context,String serviceUrl) {
		if (projectReadBusiness == null) {
			projectReadBusiness = new ProjectReadBusiness(context);
		}
		projectReadBusiness.setServiceUrl(serviceUrl);
		return projectReadBusiness;
	}
	
	/** 
	* @Title: getProjectReadTHeaderInfo 
	* @Description: 项目信息阅读权限单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ProjectReadTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 下午2:50:46
	*/
	public ProjectReadTHeaderInfo getProjectReadTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
