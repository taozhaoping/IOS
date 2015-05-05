package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ContributionAwardInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * 贡献奖申请
* @Title: ContributionAwardBusiness.java 
* @Package com.dahuatech.app.business 
* @Description: TODO
* @date 2015-3-17 下午2:42:11 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class ContributionAwardBusiness extends
		BaseBusiness<ContributionAwardInfo> {

	public ContributionAwardBusiness(Context context) {
		super(context, ContributionAwardInfo.getContributionAwardInfo());
	}

	// 单例模式(线程不安全写法)
	private static ContributionAwardBusiness contributionAwardBusiness;

	public static ContributionAwardBusiness getContributionAwardBusiness(
			Context context, String serviceUrl) {
		if (contributionAwardBusiness == null) {
			contributionAwardBusiness = new ContributionAwardBusiness(context);
		}
		contributionAwardBusiness.setServiceUrl(serviceUrl);
		return contributionAwardBusiness;
	}
	
	/**
	 * 
	* @Title: getContributionAwardInfo 
	* @Description: 获取实例信息
	* @param  @param taskParam
	* @param  @return   参数 
	* @return ContributionAwardInfo    返回类型 
	* @throws 
	* @author taozhaoping 26078
	* @author mail taozhaoping@gmail.com
	 */
	public ContributionAwardInfo getContributionAwardInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}

}
