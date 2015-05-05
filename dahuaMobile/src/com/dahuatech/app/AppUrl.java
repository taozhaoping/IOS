package com.dahuatech.app;

/**
 * @ClassName AppUrl
 * @Description URL地址集合
 * @author 21291
 * @date 2014年8月8日 上午10:16:23
 */
public class AppUrl {

	public final static String HOST = "m.dahuatech.com:8010";//10.18.106.83:8010 
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	private final static String URL_SPLITTER = "/";
	private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	private final static String ANDROID_SERVICE="GetAndroidDataService.svc";
	private final static String URL_API_HOST_ANDROID = URL_API_HOST+ANDROID_SERVICE+URL_SPLITTER;
	
	//LYNC2010下载地址
	public final static String URL_API_HOST_ANDROID_LYNC_DOWNLOAD = URL_API_HOST+URL_SPLITTER+"Lync2010.apk";
	
	//新版-日志统计服务地址LogsRecord
	public final static String URL_API_HOST_ANDROID_LOGSRECORD = URL_API_HOST_ANDROID+"NewSetLogsRecord";
	
	//LoginActivity 用户登陆
	public final static String URL_API_HOST_ANDROID_LOGINACTIVITY = URL_API_HOST_ANDROID+"LoginVerify";
	
	//LoginLockActivity 手势秘密用户验证
	public final static String URL_API_HOST_ANDROID_VERIFYVALIDACTIVITY = URL_API_HOST_ANDROID+"VerifyValid";
	
	//NoticeService 通知服务
	public final static String URL_API_HOST_ANDROID_NOTICESERVICE = URL_API_HOST_ANDROID+"GetLoginTaskCount";
	
	//SettingActivity 配置信息
	public final static String URL_API_HOST_ANDROID_SETTINGACTIVITY = URL_API_HOST_ANDROID+"SoftCheckUpdate";
	
	//TaskListActivity 我的任务列表
	public final static String URL_API_HOST_ANDROID_TASKLISTACTIVITY = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//EngineeringActivity 工程商单据
	public final static String URL_API_HOST_ANDROID_ENGINEERINGACTIVITY = URL_API_HOST_ANDROID+"GetEngineeringData";
	
	//ExpensePrivateTHeaderActivity 报销对私单据表头
	public final static String URL_API_HOST_ANDROID_EXPENSEPRIVATETHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpensePrivateTHeaderData";
	
	//ExpensePrivateTBodyActivity 报销对私单据表体
	public final static String URL_API_HOST_ANDROID_EXPENSEPRIVATETBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpensePrivateTBodyData";
	
	//ExpensePublicTHeaderActivity 报销对公单据
	public final static String URL_API_HOST_ANDROID_EXPENSEPUBLICTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpensePublicTHeaderData";
	
	//ExpenseCostTHeaderActivity 报销费用申请单
	public final static String URL_API_HOST_ANDROID_EXPENSECOSTTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseCostTHeaderData";
	
	//ExpenseSpecialTHeaderActivity 报销特殊事务单据表头
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialTHeaderData";
	
	//ExpenseSpecialTBodyActivity 报销特殊事务单据表体
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialTBodyData";
	
	//ExpenseSpecialThingActivity 报销特批事务单据表头
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHINGHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialAuditTHeaderData";
		
	//ExpenseSpecialThingActivity 报销特批事务单据表体
	public final static String URL_API_HOST_ANDROID_EXPENSESPECIALTHINGBODYACTIVITY = URL_API_HOST_ANDROID+"GetExpenseSpecialAuditTBodyData";
		
	//ExpenseMarketPayTHeaderActivity 报销市场投标支付单据
	public final static String URL_API_HOST_ANDROID_EXPENSEMARKETPAYTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseMarketPayTHeaderData";
	
	//ExpenseMarketBidTHeaderActivity 报销市场投标报销单据
	public final static String URL_API_HOST_ANDROID_EXPENSEMARKETBIDTHEADERACTIVITY = URL_API_HOST_ANDROID+"GetExpenseMarketBidTHeaderData";
	
	//NetworkPermissionActivity 网络权限申请单据
	public final static String URL_API_HOST_ANDROID_NETWORKPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetNetworkPermissionData";
	
	//DevelopTestNetworkActivity 研发项目测试权限申请单据
	public final static String URL_API_HOST_ANDROID_DEVELOPTESTNETWORKACTIVITY = URL_API_HOST_ANDROID+"GetDevelopTestNetworkData";
	
	//DaHuaAssumeCostActivity 大华承担费用申请单据
	public final static String URL_API_HOST_ANDROID_DAHUAASSUMECOSTACTIVITY = URL_API_HOST_ANDROID+"GetDaHuaAssumeCostData";
	
	//DevelopInquiryActivity 研发中心询价申请单据
	public final static String URL_API_HOST_ANDROID_DEVELOPINQUIRYACTIVITY = URL_API_HOST_ANDROID+"GetDevelopInquiryData";
	
	//MemRequreActivity MEM流程申请单据
	public final static String URL_API_HOST_ANDROID_MEMREQUREACTIVITY = URL_API_HOST_ANDROID+"GetMemRequreData";
	
	//ApplyOverTimeActivity 加班申请单据
	public final static String URL_API_HOST_ANDROID_APPLYOVERTIMEACTIVITY = URL_API_HOST_ANDROID+"GetApplyOverTimeData";
	
	//ApplyLeaveActivity 请假申请单据
	public final static String URL_API_HOST_ANDROID_APPLYLEAVEACTIVITY = URL_API_HOST_ANDROID+"GetApplyLeaveData";
	
	//ApplyResumeActivity 销假申请单据
	public final static String URL_API_HOST_ANDROID_APPLYRESUMEACTIVITY = URL_API_HOST_ANDROID+"GetApplyResumeData";
	
	//ExAttendanceActivity 异常考勤调整申请单据
	public final static String URL_API_HOST_ANDROID_EXATTENDANCEACTIVITY = URL_API_HOST_ANDROID+"GetExAttendanceData";
	
	//ApplyDaysOffActivity 普通部门调休申请单据
	public final static String URL_API_HOST_ANDROID_APPLYDAYSOFFACTIVITY = URL_API_HOST_ANDROID+"GetApplyDaysOffData";
	
	//ApplyDaysOffDevelopActivity 研发部门调休申请单据
	public final static String URL_API_HOST_ANDROID_APPLYDAYSOFFDEVELOPACTIVITY = URL_API_HOST_ANDROID+"GetApplyDaysOffDevelopData";
	
	//DocumentApproveActivity 文件审批流申请单据
	public final static String URL_API_HOST_ANDROID_DOCUMENTAPPROVEACTIVITY = URL_API_HOST_ANDROID+"GetDocumentApproveData";
	
	//NewProductLibActivity 新产品转库申请单据
	public final static String URL_API_HOST_ANDROID_NEWPRODUCTLIBACTIVITY = URL_API_HOST_ANDROID+"GetNewProductLibData";
	
	//SvnPermissionActivity SVN权限申请单据
	public final static String URL_API_HOST_ANDROID_SVNPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetSvnPermissionData";
	
	//DevelopTravelActivity 研发出差派遣申请单据
    public final static String URL_API_HOST_ANDROID_DEVELOPTRAVELACTIVITY = URL_API_HOST_ANDROID+"GetDevelopTravelData";
    
    //PurchaseStockActivity 采购备料申请单据
    public final static String URL_API_HOST_ANDROID_PURCHASESTOCKACTIVITY = URL_API_HOST_ANDROID+"GetPurchaseStockData";
    
	//EmailOpenActivity 邮箱开通申请单据
    public final static String URL_API_HOST_ANDROID_EMAILOPENACTIVITY = URL_API_HOST_ANDROID+"GetEmailOpenData";
    
    //FixedAssetsSpecialActivity 固定资产特殊紧急采购需求单据
    public final static String URL_API_HOST_ANDROID_FIXEDASSETSSPECIALACTIVITY = URL_API_HOST_ANDROID+"GetFixedAssetsSpecialData";
    
    //LowConsumableActivity 低值易耗物料代码单据
    public final static String URL_API_HOST_ANDROID_LOWCONSUMABLEACTIVITY = URL_API_HOST_ANDROID+"GetLowConsumableData";
    
    //TrainComputerActivity 培训电算化教室单据
    public final static String URL_API_HOST_ANDROID_TRAINCOMPUTERACTIVITY = URL_API_HOST_ANDROID+"GetTrainComputerData";
    
    //TravelApprovalActivity 出差审批单据
    public final static String URL_API_HOST_ANDROID_TRAVELAPPROVALACTIVITY = URL_API_HOST_ANDROID+"GetTravelApprovalData";
    
    //DoorPermissionActivity 门禁权限单据
    public final static String URL_API_HOST_ANDROID_DOORPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetDoorPermissionData";
	
    //NewProductReworkActivity 新产品返工申请单据
  	public final static String URL_API_HOST_ANDROID_NEWPRODUCTREWORKACTIVITY = URL_API_HOST_ANDROID+"GetNewProductReworkData";
  	
  	//TdBorrowActivity 技术文件借阅申请单据
  	public final static String URL_API_HOST_ANDROID_TDBORROWACTIVITY = URL_API_HOST_ANDROID+"GetTdBorrowData";
  	
	//TdPermissionActivity TD权限申请单据
  	public final static String URL_API_HOST_ANDROID_TDPERMISSIONACTIVITY = URL_API_HOST_ANDROID+"GetTdPermissionData";
  	
	//ProjectReadActivity 项目阅读申请单据
  	public final static String URL_API_HOST_ANDROID_PROJECTREADACTIVITY = URL_API_HOST_ANDROID+"GetProjectReadData";
  	
  	//FeDestroyActivity 印鉴销毁申请单据
  	public final static String URL_API_HOST_ANDROID_FEDESTROYACTIVITY = URL_API_HOST_ANDROID+"GetFeDestroyData";
  	
 	//FeEngravingActivity 印鉴刻制申请单据
  	public final static String URL_API_HOST_ANDROID_FEENGRAVINGACTIVITY = URL_API_HOST_ANDROID+"GetFeEngravingData";
    
  	//FeTakeOutActivity 印鉴外带申请单据
  	public final static String URL_API_HOST_ANDROID_FETAKEOUTACTIVITY = URL_API_HOST_ANDROID+"GetFeTakeOutData";
  	
  	//FeTransferActivity 印鉴移交申请单据
  	public final static String URL_API_HOST_ANDROID_FETRANSFERACTIVITY = URL_API_HOST_ANDROID+"GetFeTransferData";
  	
  	//FeUpdateActivity 印鉴更换申请单据
  	public final static String URL_API_HOST_ANDROID_FEUPDATEACTIVITY = URL_API_HOST_ANDROID+"GetFeUpdateData";
  	
	//WorkFlowActivity 工作流详细信息
	public final static String URL_API_HOST_ANDROID_WORKFLOWACTIVITY = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//OldWorkFlowAppServiceUrl 旧版-工作流审批服务地址
	public final static String URL_API_HOST_ANDROID_OLDWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"GetDataByPost";
	
	//NewWorkFlowAppServiceUrl 新版-工作流审批服务地址
	public final static String URL_API_HOST_ANDROID_NEWWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"WorkFlowApp";
	
	//HrWorkFlowAppServiceUrl HR版-工作流审批服务地址
	public final static String URL_API_HOST_ANDROID_HRWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"HrWorkFlowApp";
	
	//EpWorkFlowAppServiceUrl 报销版-工作流审批服务地址
	public final static String URL_API_HOST_ANDROID_EPWORKFLOWAPPSERVICEURL = URL_API_HOST_ANDROID+"EpWorkFlowApp";
	
	//RejectNodeRepository 新版-驳回节点或加签/抄送附加节点配置信息
	public final static String URL_API_HOST_ANDROID_REJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetRejectNodeData";
	
	//HrRejectNodeRepository HR版-驳回节点或加签/抄送附加节点配置信息
	public final static String URL_API_HOST_ANDROID_HRREJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetHrRejectNodeData";
	
	//EpRejectNodeRepository 报销版-驳回节点或加签/抄送附加节点配置信息
	public final static String URL_API_HOST_ANDROID_EPREJECTNODEREPOSITORY = URL_API_HOST_ANDROID+"GetEpRejectNodeData";
	
	//PlusCopyActivity 新版-加签/抄送审批服务地址
    public final static String URL_API_HOST_ANDROID_PLUSCOPYAPPURL = URL_API_HOST_ANDROID+"GetPlusCopyAppData";
    
    //PlusCopyActivity 新版-加签/抄送查询人员地址
    public final static String URL_API_HOST_ANDROID_PLUSCOPYPERSONURL = URL_API_HOST_ANDROID+"GetPlusCopyPersonData";
    
    //LowerNodeApproveActivity 新版-下级节点审批人服务地址
    public final static String URL_API_HOST_ANDROID_LOWERNODEAPPROVEURL = URL_API_HOST_ANDROID+"GetLowerNodeAppStatus";
    
    //LowerNodeApproveActivity 新版-下级节点审批操作
    public final static String URL_API_HOST_ANDROID_PASSLOWERNODEHANDLEURL = URL_API_HOST_ANDROID+"PassLowerNodeHandle";
	
	//ExpenseMainActivity 乘车记录上传报销
	public final static String URL_API_HOST_ANDROID_EXPENSEMAINACTIVITY = URL_API_HOST_ANDROID+"ExpenseTaxi";
	
	//ExpenseTaxiListActivity 乘车记录列表批量上传报销
	public final static String URL_API_HOST_ANDROID_EXPENSETAXILISTACTIVITY = URL_API_HOST_ANDROID+"ExpenseTaxiBatch";
	
	//ContactsMainActivity 通讯录列表信息
	public final static String URL_API_HOST_ANDROID_CONTACTSMAINACTIVITY = URL_API_HOST_ANDROID+"GetContactsData";
	
	//ExpenseFlowListActivity 获取我的流水列表服务器地址
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWLISTACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowListData";
	
	//ExpenseClientSearchListActivity/ExpenseProjectSearchListActivity 获取我的流水客户/项目列表服务器地址
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWSEARCHACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowSearchData";
	
	//ExpenseFlowLocalListActivity/ExpenseFlowDetailActivity 本地流水记录列表/流水详情记录 上传服务器地址
	public final static String URL_API_HOST_ANDROID_EXPENSEFLOWUPLOADACTIVITY = URL_API_HOST_ANDROID+"GetExpenseFlowUploadData";

	//MeetingListActivity 获取我的会议列表
	public final static String URL_API_HOST_ANDROID_MEETINGLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingListData";
	
	//MeetingListActivity 取消会议
    public final static String URL_API_HOST_ANDROID_CANCLEMEETINGDATA = URL_API_HOST_ANDROID+"CancleMeetingData";
	
	//MeetingDetailActivity 获取我的会议详情
	public final static String URL_API_HOST_ANDROID_MEETINGDETAILACTIVITY = URL_API_HOST_ANDROID+"GetMeetingDetailData";
	
	//MeetingDetailActivity 获取我的会议默认初始化信息
    public final static String URL_API_HOST_ANDROID_MEETINGINITACTIVITY = URL_API_HOST_ANDROID+"GetMeetingInitData";
	
	//MeetingDetailActivity 上传会议详情信息到服务器
    public final static String URL_API_HOST_ANDROID_UPLOADMEETINGDETAIL = URL_API_HOST_ANDROID+"UploadMeetingDetailData";
	
	//MeetingPersonListActivity 获取人员搜索列表
	public final static String URL_API_HOST_ANDROID_MEETINGPERSONLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingPersonListData";
	
	//MeetingRoomListActivity 获取会议室搜索列表
	public final static String URL_API_HOST_ANDROID_MEETINGROOMLISTACTIVITY = URL_API_HOST_ANDROID+"GetMeetingRoomListData";
	
	//MeetingRoomListActivity 获取过滤会议室历史记录列表
	public final static String URL_API_HOST_ANDROID_INITMEETINGROOMLISTACTIVITY = URL_API_HOST_ANDROID+"GetInitMeetingRoomListData";
	
	//DHListActivity 获取研发工时列表
    public final static String URL_API_HOST_ANDROID_DHLISTACTIVITY = URL_API_HOST_ANDROID+"GetDhListData";
    
    //DHListProjectActivity 获取研发工时具体项目列表
    public final static String URL_API_HOST_ANDROID_DHLISTPROJECTACTIVITY = URL_API_HOST_ANDROID+"GetDhListProjectData";
   
    //DHDetailActivity 获取研发工时详情信息
    public final static String URL_API_HOST_ANDROID_DHDETAILACTIVITY = URL_API_HOST_ANDROID+"GetDhDetailData";
    
	//DHDetailActivity 上传研发工时详情信息
    public final static String URL_API_HOST_ANDROID_UPLOADDHDETAILACTIVITY = URL_API_HOST_ANDROID+"UploadDhDetailData";
    
    //DHProjectSearchActivity 获取研发工时项目搜索列表
    public final static String URL_API_HOST_ANDROID_DHPROJECTSEARCHACTIVITY = URL_API_HOST_ANDROID+"GetDhProjectSearchData";
   
    //DHTypeListActivity 获取研发工时任务类型列表
    public final static String URL_API_HOST_ANDROID_DHTYPELISTACTIVITY = URL_API_HOST_ANDROID+"GetDhTypeListData";
    
    //DHConfirmListActivity 获取研发工时确认列表
    public final static String URL_API_HOST_ANDROID_DHCONFIRMLISTACTIVITY = URL_API_HOST_ANDROID+"GetDhConfirmListData";
    
    //DHConfirmListActivity 获取研发工时人员确认
    public final static String URL_API_HOST_ANDROID_UPLOADDHCONFIRMACTIVITY = URL_API_HOST_ANDROID+"UploadDhConfirmData";
    
    //DHConfirmListPersonActivity 获取研发工时确认列表具体人员信息
    public final static String URL_API_HOST_ANDROID_DHCONFIRMLISTPERSONACTIVITY = URL_API_HOST_ANDROID+"GetDhConfirmListPersonData";
    
    //AdListActivity 获取考勤模块考勤记录
    public final static String URL_API_HOST_ANDROID_ATTENDANCELISTACTIVITY = URL_API_HOST_ANDROID+"GetAttendanceListData";
    
    //AdCheckInActivity 获取是否已经签入或签出
    public final static String URL_API_HOST_ANDROID_GETCHECKSTATUSACTIVITY = URL_API_HOST_ANDROID+"GetCheckStatusData";
    
    //AdCheckInActivity 确认签入/签出
    public final static String URL_API_HOST_ANDROID_UPLOADCHECKACTIVITY = URL_API_HOST_ANDROID+"UploadCheckData";
    
    //MainActivity 打卡中心地点服务地址
    public final static String URL_API_HOST_ANDROID_GETNEWAMAPLISTACTIVITY = URL_API_HOST_ANDROID+"GetNewAmapListData";
    
    //MarketBidSearchActivity 报价服务地址
    public final static String URL_API_HOST_ANDROID_GETMARKETBIDACTIVITY = URL_API_HOST_ANDROID+"GetMarketBidData";
    
    //MarketContractSearchActivity 合同服务地址
    public final static String URL_API_HOST_ANDROID_GETMARKETCONTRACTACTIVITY = URL_API_HOST_ANDROID+"GetMarketContractData";
    
    //MarketProductSearchActivity 产品服务地址
    public final static String URL_API_HOST_ANDROID_GETMARKETPRODUCTACTIVITY = URL_API_HOST_ANDROID+"GetMarketProductData";
    
    //MarketBidSearchActivity 报价查询历史记录服务地址
    public final static String URL_API_HOST_ANDROID_GETMARKETBIDHISTORYACTIVITY = URL_API_HOST_ANDROID+"GetMarketBidHistroyData";
    
    //MarketContractSearchActivity 合同查询历史记录服务地址
    public final static String URL_API_HOST_ANDROID_GETMARKETCONTRACTHISTORYACTIVITY = URL_API_HOST_ANDROID+"GetMarketContractHistroyData";
    
  //ContributionAwardActivity HR贡献奖申请单
  	public final static String URL_API_HOST_ANDROID_CONTRIBUTIONAWARDACTIVITY = URL_API_HOST_ANDROID+"GetContributionAwardData";

}
