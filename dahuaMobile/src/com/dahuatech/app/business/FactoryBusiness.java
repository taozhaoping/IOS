package com.dahuatech.app.business;

import android.content.Context;

/**
 * @ClassName FactoryBusiness
 * @Description 业务逻辑的简单工厂类
 * @author 21291
 * @date 2014年6月4日 下午2:27:05
 */
public class FactoryBusiness<T> {
	
	private T t;  //私有变量
	private Context context;
	private static FactoryBusiness<?> mInstance;
	static{
		mInstance=null;
	}
	
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	private FactoryBusiness(Context context){
		this.context=context;
	}

	//获取实例
	@SuppressWarnings("rawtypes")
	public static FactoryBusiness<?> getFactoryBusiness(Context context) {
		if(mInstance==null){
			mInstance=new FactoryBusiness(context);
		}
		return mInstance;
	}
	
	/** 
	* @Title: getInstance 
	* @Description: 获取业务逻辑实例方法
	* @param @param type 类型
	* @param @param url  服务地址
	* @param @return     
	* @return T    
	* @throws 
	* @author 21291
	* @date 2014年7月16日 上午10:50:08
	*/
	@SuppressWarnings("unchecked")
	public T getInstance(String type,String url) {	
		if("NoticeBussiness".equals(type)){   //通知栏的业务逻辑类
			t=(T) NoticeBussiness.getNoticeBussiness(context,url);
		}	
		if("SettingBusiness".equals(type)){   //系统参数业务逻辑类
			t=(T) SettingBusiness.getSettingBusiness(context,url);
		}
		if("UserLoginBussiness".equals(type)){  //登陆验证业务逻辑类
			t=(T) UserLoginBussiness.getUserLoginBussiness(context,url);
		}
		if("WorkFlowBusiness".equals(type)){  //工作流业务逻辑类
			t=(T) WorkFlowBusiness.getWorkFlowBusiness(context);
		}
		if("RejectNodeBusiness".equals(type)){  //驳回节点业务逻辑类
			t=(T) RejectNodeBusiness.getRejectNodeBusiness(context,url);
		}
		if("LowerNodeAppBusiness".equals(type)){  //下级节点审批人业务逻辑类
			t=(T) LowerNodeAppBusiness.getLowerNodeAppBusiness(context,url);
		}
		if("ContactsBusiness".equals(type)){  //通讯录业务逻辑类
			t=(T) ContactsBusiness.getContactsBusiness(context,url);
		}
		if("ExpenseBusiness".equals(type)){  //报销打车业务逻辑类 
			t=(T) ExpenseBusiness.getExpenseBusiness(context,url);
		}
		if("ExpandableBusiness".equals(type)){ //我的流水主列表页业务逻辑类  
			t=(T) ExpandableBusiness.getExpandableBusiness(context,url);
		}	
		if("ExpenseFlowItemBusiness".equals(type)){ //我的流水客户/项目搜索列表页业务逻辑类
			t=(T) ExpenseFlowItemBusiness.getExpenseFlowItemBusiness(context,url);
		}	
		if("MeetingBusiness".equals(type)){ //我的会议业务逻辑类
			t=(T) MeetingBusiness.getMeetingBusiness(context,url);
		}	
		if("DevelopHourBusiness".equals(type)){  //研发工时业务逻辑类 
			t=(T) DevelopHourBusiness.getDevelopHourBusiness(context,url);
		}
		if("AttendanceBusiness".equals(type)){  //考勤模块业务逻辑类
			t=(T) AttendanceBusiness.getAttendanceBusiness(context,url);
		}
		if("MarketBusiness".equals(type)){  //我的销售模块业务逻辑类
			t=(T) MarketBusiness.getMarketBusiness(context,url);
		}
		if("TaskListBusiness".equals(type)){  //任务列表业务逻辑类
			t=(T) TaskListBusiness.getTaskListBusiness(context,url);
		}
		//具体单据内容业务
		if("EngineeringBusiness".equals(type)){  //工程商单据实体业务逻辑类
			t=(T) EngineeringBusiness.getEngineeringBusiness(context,url);
		}	
		if("ExpensePrivateTBodyBusiness".equals(type)){  //报销对私单据表体业务逻辑类
			t=(T) ExpensePrivateTBodyBusiness.getExpensePrivateTBodyBusiness(context,url);
		}
		if("ExpensePrivateTHeaderBusiness".equals(type)){  //报销对私单据表头业务逻辑类
			t=(T) ExpensePrivateTHeaderBusiness.getExpensePrivateTHeaderBusiness(context,url);
		}
		if("ExpensePublicTHeaderBusiness".equals(type)){  //对公支付报销单据业务逻辑类
			t=(T) ExpensePublicTHeaderBusiness.getExpensePublicTHeaderBusiness(context,url);
		}
		if("ExpenseCostTHeaderBusiness".equals(type)){  //费用申请报销单据业务逻辑类
			t=(T) ExpenseCostTHeaderBusiness.getExpenseCostTHeaderBusiness(context,url);
		}
		if("ExpenseSpecialTBodyBusiness".equals(type)){  //报销特殊事务单据表体业务逻辑类
			t=(T) ExpenseSpecialTBodyBusiness.getExpenseSpecialTBodyBusiness(context,url);
		}
		if("ExpenseSpecialTHeaderBusiness".equals(type)){  //报销特殊事务单据表头业务逻辑类
			t=(T) ExpenseSpecialTHeaderBusiness.getExpenseSpecialTHeaderBusiness(context,url);
		}
		if("ExpenseMarketPayTHeaderBusiness".equals(type)){  //报销市场支付单据表头业务逻辑类
			t=(T) ExpenseMarketPayTHeaderBusiness.getExpenseMarketPayTHeaderBusiness(context,url);
		}
		if("ExpenseMarketBidTHeaderBusiness".equals(type)){  //报销市场投标单据表头业务逻辑类
			t=(T) ExpenseMarketBidTHeaderBusiness.getExpenseMarketBidTHeaderBusiness(context,url);
		}
		if("NetworkPermissionBusiness".equals(type)){  //网络权限单据业务逻辑类
			t=(T) NetworkPermissionBusiness.getNetworkPermissionBusiness(context,url);
		}
		if("DevelopTestNetworkBusiness".equals(type)){  //研发项目测试网络权限单据业务逻辑类
			t=(T) DevelopTestNetworkBusiness.getDevelopTestNetworkBusiness(context,url);
		}
		if("DaHuaAssumeCostBusiness".equals(type)){  //大华承担费用单据业务逻辑类
			t=(T) DaHuaAssumeCostBusiness.getDaHuaAssumeCostBusiness(context,url);
		}
		if("DevelopInquiryBusiness".equals(type)){  //研发中心询价单据业务逻辑类
			t=(T) DevelopInquiryBusiness.getDevelopInquiryBusiness(context,url);
		}
		if("MemRequreBusiness".equals(type)){  //MEM流程单据业务逻辑类
			t=(T) MemRequreBusiness.getMemRequreBusiness(context,url);
		}
		if("ApplyOverTimeBusiness".equals(type)){  //加班单据业务逻辑类
			t=(T) ApplyOverTimeBusiness.getApplyOverTimeBusiness(context,url);
		}
		if("ExAttendanceBusiness".equals(type)){  //异常考勤调整单据业务逻辑类
			t=(T) ExAttendanceBusiness.getExAttendanceBusiness(context,url);
		}
		if("ApplyDaysOffBusiness".equals(type)){  //普通部门调休单据业务逻辑类
			t=(T) ApplyDaysOffBusiness.getApplyDaysOffBusiness(context,url);
		}
		if("ApplyDaysOffDevelopBusiness".equals(type)){  //研发部门调休单据业务逻辑类
			t=(T) ApplyDaysOffDevelopBusiness.getApplyDaysOffDevelopBusiness(context,url);
		}
		if("DocumentApproveBusiness".equals(type)){  //文件审批流单据业务逻辑类
			t=(T) DocumentApproveBusiness.getDocumentApproveBusiness(context,url);
		}
		if("NewProductLibBusiness".equals(type)){  //新产品转库单据业务逻辑类
			t=(T) NewProductLibBusiness.getNewProductLibBusiness(context,url);
		}
		if("SvnPermissionBusiness".equals(type)){  //SVN权限单据业务逻辑类
			t=(T) SvnPermissionBusiness.getSvnPermissionBusiness(context,url);
		}
		if("DevelopTravelBusiness".equals(type)){  //研发出差派遣单据业务逻辑类
			t=(T) DevelopTravelBusiness.getDevelopTravelBusiness(context,url);
		}
		if("PurchaseStockBusiness".equals(type)){  //采购备料单据业务逻辑类
			t=(T) PurchaseStockBusiness.getPurchaseStockBusiness(context,url);
		}
		if("EmailOpenBusiness".equals(type)){  //邮箱开通单据业务逻辑类
			t=(T) EmailOpenBusiness.getEmailOpenBusiness(context,url);
		}
		if("FixedAssetsSpecialBusiness".equals(type)){  //固定资产特殊紧急采购需求单据业务逻辑类
			t=(T) FixedAssetsSpecialBusiness.getFixedAssetsSpecialBusiness(context,url);
		}
		if("LowConsumableBusiness".equals(type)){  //低值易耗物料代码单据业务逻辑类
			t=(T) LowConsumableBusiness.getLowConsumableBusiness(context,url);
		}
		if("TrainComputerBusiness".equals(type)){  //培训电算化教室单据业务逻辑类
			t=(T) TrainComputerBusiness.getTrainComputerBusiness(context,url);
		}
		if("TravelApprovalBusiness".equals(type)){  //出差审批单据业务逻辑类
			t=(T) TravelApprovalBusiness.getTravelApprovalBusiness(context,url);
		}
		if("DoorPermissionBusiness".equals(type)){  //门禁权限单据业务逻辑类
			t=(T) DoorPermissionBusiness.getDoorPermissionBusiness(context,url);
		}
		if("NewProductReworkBusiness".equals(type)){  //新产品返工单据业务逻辑类
			t=(T) NewProductReworkBusiness.getNewProductReworkBusiness(context,url);
		}
		if("TdBorrowBusiness".equals(type)){  //技术文件借阅申请单据业务逻辑类
			t=(T) TdBorrowBusiness.getTdBorrowBusiness(context,url);
		}
		if("TdPermissionBusiness".equals(type)){  //TD权限申请单据业务逻辑类
			t=(T) TdPermissionBusiness.getTdPermissionBusiness(context,url);
		}
		if("ProjectReadBusiness".equals(type)){  //项目信息阅读权限申请单据业务逻辑类
			t=(T) ProjectReadBusiness.getProjectReadBusiness(context,url);
		}
		if("FeDestroyBusiness".equals(type)){  //印鉴销毁业务逻辑类
			t=(T) FeDestroyBusiness.getFeDestroyBusiness(context,url);
		}
		if("FeEngravingBusiness".equals(type)){  //印鉴刻制业务逻辑类
			t=(T) FeEngravingBusiness.getFeEngravingBusiness(context,url);
		}
		if("FeTakeOutBusiness".equals(type)){  //印鉴外带业务逻辑类
			t=(T) FeTakeOutBusiness.getFeTakeOutBusiness(context,url);
		}
		if("FeTransferBusiness".equals(type)){  //印鉴移交业务逻辑类
			t=(T) FeTransferBusiness.getFeTransferBusiness(context,url);
		}
		if("FeUpdateBusiness".equals(type)){  //印鉴更换业务逻辑类
			t=(T) FeUpdateBusiness.getFeUpdateBusiness(context,url);
		}	
		if("ApplyLeaveBusiness".equals(type)){  //请假申请业务逻辑类
			t=(T) ApplyLeaveBusiness.getApplyLeaveBusiness(context,url);
		}	
		if("ApplyResumeBusiness".equals(type)){  //销假申请业务逻辑类
			t=(T) ApplyResumeBusiness.getApplyResumeBusiness(context,url);
		}	
		if("ContributionAwardBusiness".equals(type)) //贡献奖金申请单
		{
			t=(T) ContributionAwardBusiness.getContributionAwardBusiness(context, url);
		}
		if("ExpenseSpecialThingHeaderBusiness".equals(type)) //特批事物处理表头
		{
			t=(T) ExpenseSpecialThingHeaderBusiness.getExpenseSpecialTHeaderBusiness(context, url);
		}
		if("ExpenseSpecialThingBodyBusiness".equals(type)) //特批事物处理表体
		{
			t=(T) ExpenseSpecialThingBodyBusiness.getExpenseSpecialTBodyBusiness(context, url);
		}
		
		return t;
	}
}
