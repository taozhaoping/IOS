package com.dahuatech.app.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppExpiration;
import com.dahuatech.app.AppGuide;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.AppWelcome;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.SettingBusiness;
import com.dahuatech.app.ui.attendance.AdCheckInActivity;
import com.dahuatech.app.ui.attendance.AdListActivity;
import com.dahuatech.app.ui.barcode.CaptureActivity;
import com.dahuatech.app.ui.contacts.ContactsMainActivity;
import com.dahuatech.app.ui.develop.hour.DHConfirmListActivity;
import com.dahuatech.app.ui.develop.hour.DHConfirmListPersonActivity;
import com.dahuatech.app.ui.develop.hour.DHDetailActivity;
import com.dahuatech.app.ui.develop.hour.DHListActivity;
import com.dahuatech.app.ui.develop.hour.DHListProjectActivity;
import com.dahuatech.app.ui.develop.hour.DHMainActivity;
import com.dahuatech.app.ui.develop.hour.DHProjectSearchActivity;
import com.dahuatech.app.ui.develop.hour.DHTypeListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseClientSearchListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowDetailActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseFlowLocalListActivity;
import com.dahuatech.app.ui.expense.flow.ExpenseProjectSearchListActivity;
import com.dahuatech.app.ui.expense.main.ExpenseMainActivity;
import com.dahuatech.app.ui.expense.taxi.ExpenseTaxiMainActivity;
import com.dahuatech.app.ui.main.LoginActivity;
import com.dahuatech.app.ui.main.LoginLockActivity;
import com.dahuatech.app.ui.main.LoginLockSetPwdActivity;
import com.dahuatech.app.ui.main.MainActivity;
import com.dahuatech.app.ui.main.SettingActivity;
import com.dahuatech.app.ui.main.SmsNoticeActivity;
import com.dahuatech.app.ui.market.MarketBidSearchActivity;
import com.dahuatech.app.ui.market.MarketContractSearchActivity;
import com.dahuatech.app.ui.market.MarketMainActivity;
import com.dahuatech.app.ui.market.MarketProductSearchActivity;
import com.dahuatech.app.ui.market.MarketWorkflowActivity;
import com.dahuatech.app.ui.meeting.MeetingDetailActivity;
import com.dahuatech.app.ui.meeting.MeetingListActivity;
import com.dahuatech.app.ui.meeting.MeetingPersonListActivity;
import com.dahuatech.app.ui.meeting.MeetingRoomListActivity;
import com.dahuatech.app.ui.task.ApplyDaysOffActivity;
import com.dahuatech.app.ui.task.ApplyDaysOffDevelopActivity;
import com.dahuatech.app.ui.task.ApplyLeaveActivity;
import com.dahuatech.app.ui.task.ApplyOverTimeActivity;
import com.dahuatech.app.ui.task.ApplyResumeActivity;
import com.dahuatech.app.ui.task.ContributionAwardActivity;
import com.dahuatech.app.ui.task.DaHuaAssumeCostActivity;
import com.dahuatech.app.ui.task.DevelopInquiryActivity;
import com.dahuatech.app.ui.task.DevelopTestNetworkActivity;
import com.dahuatech.app.ui.task.DevelopTravelActivity;
import com.dahuatech.app.ui.task.DocumentApproveActivity;
import com.dahuatech.app.ui.task.DoorPermissionActivity;
import com.dahuatech.app.ui.task.EmailOpenActivity;
import com.dahuatech.app.ui.task.EngineeringActivity;
import com.dahuatech.app.ui.task.ExAttendanceActivity;
import com.dahuatech.app.ui.task.ExpenseCostTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseMarketBidTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseMarketPayTHeaderActivity;
import com.dahuatech.app.ui.task.ExpensePrivateTHeaderActivity;
import com.dahuatech.app.ui.task.ExpensePublicTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseSpecialTHeaderActivity;
import com.dahuatech.app.ui.task.ExpenseSpecialThingHeaderActivity;
import com.dahuatech.app.ui.task.FeDestroyActivity;
import com.dahuatech.app.ui.task.FeEngravingActivity;
import com.dahuatech.app.ui.task.FeTakeOutActivity;
import com.dahuatech.app.ui.task.FeTransferActivity;
import com.dahuatech.app.ui.task.FeUpdateActivity;
import com.dahuatech.app.ui.task.FixedAssetsSpecialActivity;
import com.dahuatech.app.ui.task.LowConsumableActivity;
import com.dahuatech.app.ui.task.LowerNodeApproveActivity;
import com.dahuatech.app.ui.task.MemRequreActivity;
import com.dahuatech.app.ui.task.NetworkPermissionActivity;
import com.dahuatech.app.ui.task.NewProductLibActivity;
import com.dahuatech.app.ui.task.NewProductReworkActivity;
import com.dahuatech.app.ui.task.PlusCopyActivity;
import com.dahuatech.app.ui.task.PlusCopyPersonActivity;
import com.dahuatech.app.ui.task.ProjectReadActivity;
import com.dahuatech.app.ui.task.PurchaseStockActivity;
import com.dahuatech.app.ui.task.SvnPermissionActivity;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.dahuatech.app.ui.task.TdBorrowActivity;
import com.dahuatech.app.ui.task.TdPermissionActivity;
import com.dahuatech.app.ui.task.TrainComputerActivity;
import com.dahuatech.app.ui.task.TravelApprovalActivity;

public class UIHelper {
	
	public static final int ACTIVITY_ENGINEERING = 0x00;
	public static final int ACTIVITY_EXPENSEPRIVATE = 0x01;
	public static final int ACTIVITY_EXPENSEPUBLIC = 0x02;
	
	//我的流水 不同列表跳转到流水详情页面
	public static final int ACTIVITY_EXPENSEDETAIL_LOCAL = 0x03;			 //待上传列表
	public static final int ACTIVITY_EXPENSEDETAIL_SERVER = 0x04;			 //服务器列表
	
	private static UIHelper mInstance;
	static{
		mInstance=new UIHelper();
	}
	
	public UIHelper(){}
	
	public static UIHelper getInstance(){
		return mInstance;
	}
	
	public static Context mContext;

	/** 
	* @Title: showGuide 
	* @Description: 显示引导动画页
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 上午11:05:34
	*/
	public static void showGuide(Context context) {
		Intent intent = new Intent(context, AppGuide.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showWelcome 
	* @Description: 显示开始动画欢迎界面
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 上午11:03:19
	*/
	public static void showWelcome(Context context) {
		Intent intent = new Intent(context, AppWelcome.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpiration 
	* @Description: 显示过期时间判断页面
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午2:23:05
	*/
	public static void showExpiration(Context context) {
		Intent intent = new Intent(context, AppExpiration.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLogin 
	* @Description: 显示登录界面
	* @param @param context 上下文
	* @param @param fLoginShowNotice 是否有通知栏信息  
	* @param @param fFirstLogin 是否首次登陆     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午3:03:35
	*/
	public static void showLogin(Context context,boolean fLoginShowNotice,boolean fFirstLogin) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	
		intent.putExtra(AppContext.FNOTICE_LOGIN_IS_SHOW_KEY, fLoginShowNotice);	
		intent.putExtra(AppContext.FLOGIN_IS_FIRST_KEY, fFirstLogin);	
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLock 
	* @Description: 显示登陆密码页面
	* @param @param context 上下文
	* @param @param fItemNumber 员工号
	* @param @param fItemName 员工名称    
	* @param @param fFirstLogin 是否首次登陆         
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 下午4:04:29
	*/
	public static void showLock(Context context,String fItemNumber,String fItemName,boolean fFirstLogin) {
		Intent intent = new Intent(context, LoginLockActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);
		intent.putExtra(AppContext.FLOGIN_IS_FIRST_KEY, fFirstLogin);
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLockSetPwd 
	* @Description: 设置手势密码登陆页面
	* @param @param context 上下文
	* @param @param fItemNumber 员工号
	* @param @param fItemName 员工名称        
	* @param @param source 来源
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 上午10:40:58
	*/
	public static void showLockSetPwd(Context context,String fItemNumber,String fItemName,String source) {
		Intent intent = new Intent(context, LoginLockSetPwdActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);	
		intent.putExtra(AppContext.GESTURES_PASSWORD_SOURCE_KEY, source);	
		context.startActivity(intent);
	}

	/** 
	* @Title: showHome 
	* @Description: 显示首页
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午3:01:46
	*/
	public static void showHome(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
		((Activity)context).finish();
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showHome 
	* @Description: 显示首页
	* @param @param context
	* @param @param fItemNumber 员工号
	* @param @param fItemName 员工名称    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月9日 上午10:14:23
	*/
	public static void showHome(Context context,String fItemNumber,String fItemName) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(AppContext.USER_NAME_KEY, fItemNumber);
		intent.putExtra(AppContext.FITEMNAME_KEY, fItemName);	
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		((Activity)context).finish();
	}
	
	/** 
	* @Title: showSetting 
	* @Description: 显示系统设置界面
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午3:18:33
	*/
	public static void showSetting(Context context) {
		Intent intent = new Intent(context, SettingActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showSmsInvite 
	* @Description: 发送短信邀请页面
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月16日 下午3:54:55
	*/
	public static void showSmsInvite(Context context) {
		Intent intent = new Intent(context, SmsNoticeActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMyTask 
	* @Description: 显示我的任务界面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月10日 下午4:38:02
	*/
	public static void showMyTask(Context context,String fItemNumber) {
		Intent intent = new Intent(context, TaskListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.FSTATUS_KEY, "0");
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseMain 
	* @Description: 显示报销流水界面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月21日 下午4:24:40
	*/
	public static void showExpenseMain(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseTaxi 
	* @Description: 显示报销打车界面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月10日 下午4:40:25
	*/
	public static void showExpenseTaxi(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseTaxiMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowList 
	* @Description: 显示我的流水列表页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月27日 下午2:10:32
	*/
	public static void showExpenseFlowList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseFlowListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowLocalList 
	* @Description: 显示我的流水待上传列表页面
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 下午3:08:11
	*/
	public static void showExpenseFlowLocalList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseFlowLocalListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowDetail 
	* @Description: 跳转到流水详情页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号
	* @param @param listType 列表类型
	* @param @param eDetailInfo 详情实体         
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 上午11:58:24
	*/
	public static void showExpenseFlowDetail(Context context,String fItemNumber,String listType,ExpenseFlowDetailInfo eDetailInfo){
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, eDetailInfo.getFPayType());
		//主键内码
		intent.putExtra(DbManager.KEY_ROWID, eDetailInfo.getFLocalId());
		intent.putExtra(DbManager.KEY_DETAIL_FSERVERID, eDetailInfo.getFServerId());
		//流水父类详情信息
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTIME, eDetailInfo.getFExpendTime());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTYPEPARENT, eDetailInfo.getFExpendTypeParent());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDTYCHILD, eDetailInfo.getFExpendTypeChild());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDADDRESS, eDetailInfo.getFExpendAddress());
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDAMOUNT, eDetailInfo.getFExpendAmount());
		intent.putExtra(DbManager.KEY_DETAIL_FCAUSE, eDetailInfo.getFCause());	
		intent.putExtra(DbManager.KEY_DETAIL_FCLIENTID, eDetailInfo.getFClientId());
		intent.putExtra(DbManager.KEY_DETAIL_FPROJECTID, eDetailInfo.getFProjectId());
		intent.putExtra(DbManager.KEY_DETAIL_FCLIENT, eDetailInfo.getFClient());
		intent.putExtra(DbManager.KEY_DETAIL_FPROJECT, eDetailInfo.getFProject());
		intent.putExtra(DbManager.KEY_DETAIL_FACCOMPANY, eDetailInfo.getFAccompany());
		intent.putExtra(DbManager.KEY_DETAIL_FACCOMPANYREASON, eDetailInfo.getFAccompanyReason());
		//流水子类详情信息
		intent.putExtra(DbManager.KEY_DETAIL_FSTART, eDetailInfo.getFStart());
		intent.putExtra(DbManager.KEY_DETAIL_FDESTINATION, eDetailInfo.getFDestination());
		intent.putExtra(DbManager.KEY_DETAIL_FSTARTTIME, eDetailInfo.getFStartTime());
		intent.putExtra(DbManager.KEY_DETAIL_FENDTIME, eDetailInfo.getFEndTime());
		intent.putExtra(DbManager.KEY_DETAIL_FBUSINESSLEVEL, eDetailInfo.getFBusinessLevel());
		intent.putExtra(DbManager.KEY_DETAIL_FREASON, eDetailInfo.getFReason());
		intent.putExtra(DbManager.KEY_DETAIL_FDESCRIPTION, eDetailInfo.getFDescription());
		
		if("local".equals(listType)){  //本地列表跳转
			((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEDETAIL_LOCAL);
		}
		else{  //服务器列表跳转
			((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEDETAIL_SERVER);
		}
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowDetail 
	* @Description: 显示我的流水详情页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号 
	* @param @param defaultExpendTime 默认时间     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月15日 下午3:48:51
	*/
	public static void showExpenseFlowDetail(Context context,String fItemNumber,String defaultExpendTime) {
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, "现金");
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME, defaultExpendTime);
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: redirectToExpenseFlow 
	* @Description: 打车报销跳转到流水填写页面
	* @param @param context 上下文环境
	* @param @param source 跳转来源
	* @param @param fItemNumber 员工号  
	* @param @param defaultExpendTime  默认时间     
	* @param @param fExpendAmount 打车金额
	* @param @param fStartAddress 打车起始地址
	* @param @param fEndAddress 打车结束地址
	* @param @param fStartTime 打车开始时间
	* @param @param fEndTime 打车结束时间     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月13日 下午5:30:11
	*/
	public static void redirectToExpenseFlow(Context context,String source,String fItemNumber,String defaultExpendTime,String fExpendAmount,String fStartAddress,String fEndAddress,String fStartTime,String fEndTime) {
		Intent intent = new Intent(context, ExpenseFlowDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME, defaultExpendTime);
		intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_SKIP_SOURCE, source);
		intent.putExtra(DbManager.KEY_DETAIL_FPAYTYPE, "现金");
		intent.putExtra(DbManager.KEY_DETAIL_FEXPENDAMOUNT, fExpendAmount);
		intent.putExtra(DbManager.KEY_DETAIL_FSTART, fStartAddress);
		intent.putExtra(DbManager.KEY_DETAIL_FDESTINATION, fEndAddress);
		intent.putExtra(DbManager.KEY_DETAIL_FSTARTTIME, fStartTime);
		intent.putExtra(DbManager.KEY_DETAIL_FENDTIME, fEndTime);		
		((Activity)context).startActivityForResult(intent,ACTIVITY_EXPENSEDETAIL_SERVER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		((Activity)context).finish();
	}
	
	/** 
	* @Title: showExpenseFlowClientList 
	* @Description: 
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 下午2:50:03
	*/
	public static void showExpenseFlowClientList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseClientSearchListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.EXPENSE_FLOW_DETAIL_CLIENT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showExpenseFlowProjectList 
	* @Description: 跳转到流水项目搜索页面
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 下午2:50:57
	*/
	public static void showExpenseFlowProjectList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, ExpenseProjectSearchListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.EXPENSE_FLOW_DETAIL_PROJECT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: showContacts 
	* @Description: 显示通讯录界面
	* @param @param context 上下文环境  
	* @param @param fItemNumber 员工号   
	* @param @param fSourceType 来源类型     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月15日 下午2:23:39
	*/
	public static void showContacts(Context context,String fItemNumber,String fSourceType){
		Intent intent = new Intent(context, ContactsMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.CONTACTS_SOURCE_TYPE, fSourceType);
		/*邀请同事功能，需要回传搜索到的人员*/
		((Activity)context).startActivityForResult(intent,AppContext.CONTACTS_SMS_SEARCH);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeeting 
	* @Description: 显示我的会议列表界面
	* @param @param context 上下文环境     
	* @param @param fItemNumber  员工号       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 上午11:37:13
	*/
	public static void showMeetingList(Context context,String fItemNumber){
		Intent intent = new Intent(context, MeetingListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingDetail 
	* @Description: 显示我的会议详情界面
	* @param @param context 上下文环境  
	* @param @param fItemNumber 员工号
	* @param @param fOrderId 会议主键内码    
	* @param @param fStatus 状态    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 下午1:39:41
	*/
	public static void showMeetingDetail(Context context,String fItemNumber,String fOrderId,String fStatus){
		Intent intent = new Intent(context, MeetingDetailActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_FSTATUS, fStatus);
		intent.putExtra(AppContext.MEETING_DETAIL_FORDERID, fOrderId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_INFO);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingRoomSearch 
	* @Description: 显示会议室搜索列表界面
	* @param @param context 上下文环境     
	* @param @param fItemNumber 员工号
	* @param @param fSelectedDate 选中的日期
	* @param @param fSelectedStart 选中的开始日期
	* @param @param fSelectedEnd  选中的结束日期        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 下午5:10:25
	*/
	public static void showMeetingRoomSearch(Context context,String fItemNumber,String fSelectedDate,String fSelectedStart,String fSelectedEnd){
		Intent intent = new Intent(context, MeetingRoomListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDDATE, fSelectedDate);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDSTART, fSelectedStart);
		intent.putExtra(AppContext.MEETING_DETAIL_SELECTEDEND, fSelectedEnd);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_ROOM);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: showMeetingPersonSearch 
	* @Description: 显示人员搜索列表界面
	* @param @param context 上下文环境  
	* @param @param personList 人员集合字符串
	* @param @param fLag 传递的状态标志        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月18日 下午5:46:31
	*/
	public static void showMeetingPersonSearch(Context context,String personList,String fLag){
		Intent intent = new Intent(context, MeetingPersonListActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST, personList);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG, fLag);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_PERSON);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMeetingMasterSearch 
	* @Description: 获取主持人搜索列表界面
	* @param @param context 上下文环境     
	* @param @param personList 人员集合
	* @param @param fLag 传递的状态标志       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午9:01:40
	*/
	public static void showMeetingMasterSearch(Context context,String personList,String fLag){
		Intent intent = new Intent(context, MeetingPersonListActivity.class);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST, personList);
		intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG, fLag);
		((Activity)context).startActivityForResult(intent,AppContext.MEETING_DETAIL_MASTER);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showCapture 
	* @Description: 显示扫一扫界面
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月10日 下午4:33:56
	*/
	public static void showCapture(Context context) {
		Intent intent = new Intent(context, CaptureActivity.class);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showTaskDetail 
	* @Description: 做个判断，根据系统ID和单据类型 ，显示不同单据详细信息
	* @param @param context 运行上下文
	* @param @param task 任务实体
	* @param @param fStatus 审批状态    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月7日 下午7:58:44
	*/
	public static void showTaskDetail(Context context, TaskInfo task,String fStatus){
		String fMenuId=String.valueOf(task.getFMenuID());
		String fSystemType=String.valueOf(task.getFSystemType());
		String fClassTypeId=String.valueOf(task.getFClassTypeID());	
		String fBillId=String.valueOf(task.getFBillID());
		Intent intent=null;
		
		if("19".equals(fSystemType) && "280002156".equals(fClassTypeId)){
			//工程商圈定单据实体
			intent = new Intent(context, EngineeringActivity.class);	
		} 
		
		if("8".equals(fSystemType) && "20140001".equals(fClassTypeId)){
			//报销对私单据实体
			intent = new Intent(context, ExpensePrivateTHeaderActivity.class);	
		}
		 
		if("8".equals(fSystemType) && "20140002".equals(fClassTypeId)){
			//报销对公单据实体
			intent = new Intent(context, ExpensePublicTHeaderActivity.class);
		}
		
		if("8".equals(fSystemType) && "20140003".equals(fClassTypeId)){
			//特殊事务单据实体
			intent = new Intent(context, ExpenseSpecialTHeaderActivity.class);
		}
		
		if("8".equals(fSystemType) && "20140008".equals(fClassTypeId)){
			//特批事务单据实体
			intent = new Intent(context, ExpenseSpecialThingHeaderActivity.class);
		}
		 
		if("8".equals(fSystemType) && "20140004".equals(fClassTypeId)){
			//费用申请单据实体
		    intent = new Intent(context, ExpenseCostTHeaderActivity.class);
		}
		 
		if("8".equals(fSystemType) && "20140006".equals(fClassTypeId)){
			//市场投标支付单据实体
			intent = new Intent(context, ExpenseMarketPayTHeaderActivity.class);
		}
	   
		if("8".equals(fSystemType) && "20140007".equals(fClassTypeId)){
			//市场投标报销单据实体
			intent = new Intent(context, ExpenseMarketBidTHeaderActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000001".equals(fClassTypeId)){
			//请假申请单据实体
			intent = new Intent(context, ApplyLeaveActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000002".equals(fClassTypeId)){
			//销假申请单据实体
			intent = new Intent(context, ApplyResumeActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000003".equals(fClassTypeId)){
			//加班申请单据实体
			intent = new Intent(context, ApplyOverTimeActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000004".equals(fClassTypeId)){
			//普通部门调休申请单据实体
			intent = new Intent(context, ApplyDaysOffActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000006".equals(fClassTypeId)){
			//研发部门调休申请单据实体
			intent = new Intent(context, ApplyDaysOffDevelopActivity.class);
		}
		
		if("38".equals(fSystemType) && "40000007".equals(fClassTypeId)){
			//异常考勤调整申请单据实体
			intent = new Intent(context, ExAttendanceActivity.class);
		}
		
		if ("38".equals(fSystemType) && "290002186".equals(fClassTypeId))
		{
			//长期贡献奖金申请
			intent = new Intent(context, ContributionAwardActivity.class);
		}
	    
		if("18".equals(fSystemType) && "200001053".equals(fClassTypeId)){
			//网络权限申请单据实体
			intent = new Intent(context, NetworkPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002216".equals(fClassTypeId)){
			//研发项目测试权限申请单据实体
			intent = new Intent(context, DevelopTestNetworkActivity.class);
		}
		
		if("18".equals(fSystemType) && "100000018".equals(fClassTypeId)){
			//大华承担费用申请单据实体
			intent = new Intent(context, DaHuaAssumeCostActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002160".equals(fClassTypeId)){
			//研发中心询价申请单据实体
			intent = new Intent(context, DevelopInquiryActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002238".equals(fClassTypeId)){
			//MEM流程申请单据实体
			intent = new Intent(context, MemRequreActivity.class);
		}
		
		if("18".equals(fSystemType) && "230001132".equals(fClassTypeId)){
			//文件审批流申请单据实体
			intent = new Intent(context, DocumentApproveActivity.class);
		}
		
		if("18".equals(fSystemType) && "230001092".equals(fClassTypeId)){
			//SVN权限申请单据实体
			intent = new Intent(context, SvnPermissionActivity.class);
		}
		 
		if("18".equals(fSystemType) && "200001021".equals(fClassTypeId)){
			//新产品转库申请单据实体
			intent = new Intent(context, NewProductLibActivity.class);
		}
		
		if("18".equals(fSystemType) && "100000011".equals(fClassTypeId)){
			//研发出差派遣申请单据实体
			intent = new Intent(context, DevelopTravelActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001081".equals(fClassTypeId)){
			//采购备料申请单据实体
			intent = new Intent(context, PurchaseStockActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001040".equals(fClassTypeId)){
			//邮箱开通申请单据实体
			intent = new Intent(context, EmailOpenActivity.class);
		}
		
		if("18".equals(fSystemType) && "230002046".equals(fClassTypeId)){
			//固定资产特殊紧急采购需求申请单据实体
			intent = new Intent(context, FixedAssetsSpecialActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001035".equals(fClassTypeId)){
			//低值易耗物料代码申请单据实体
			intent = new Intent(context, LowConsumableActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001054".equals(fClassTypeId)){
			//培训电算化教室申请单据实体
			intent = new Intent(context, TrainComputerActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002154".equals(fClassTypeId)){
			//出差审批申请单据实体
			intent = new Intent(context, TravelApprovalActivity.class);
		}
		
		if("18".equals(fSystemType) && "280002152".equals(fClassTypeId)){
			//新产品返工申请单据实体
			intent = new Intent(context, NewProductReworkActivity.class);
		}
		
		if("18".equals(fSystemType) && "200001052".equals(fClassTypeId)){
			//门禁权限申请单据实体
			intent = new Intent(context, DoorPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "290002167".equals(fClassTypeId)){
			//技术文件借阅申请单据实体
			intent = new Intent(context, TdBorrowActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001081".equals(fClassTypeId)){
			//TD权限申请单据实体
			intent = new Intent(context, TdPermissionActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001083".equals(fClassTypeId)){
			//项目信息阅读权限申请单据实体
			intent = new Intent(context, ProjectReadActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001068".equals(fClassTypeId)){
			//印鉴销毁申请单据实体
			intent = new Intent(context, FeDestroyActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001059".equals(fClassTypeId)){
			//印鉴刻制申请单据实体
			intent = new Intent(context, FeEngravingActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001060".equals(fClassTypeId)){
			//印鉴外带申请单据实体
			intent = new Intent(context, FeTakeOutActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001070".equals(fClassTypeId)){
			//印鉴移交申请单据实体
			intent = new Intent(context, FeTransferActivity.class);
		}
		
		if("18".equals(fSystemType) && "280001069".equals(fClassTypeId)){
			//印鉴更换申请单据实体
			intent = new Intent(context, FeUpdateActivity.class);
		}
		if(intent!=null){
			showIntent(context,intent,fMenuId,fSystemType,fClassTypeId,fBillId,fStatus);
		}
		else {
			ToastMessageLong(context,R.string.tasklist_notfind);
		}
	}
	
	/** 
	* @Title: showIntent 
	* @Description: 显示意图跳转
	* @param @param context
	* @param @param intent
	* @param @param fMenuId
	* @param @param fSystemType
	* @param @param fClassTypeId
	* @param @param fBillId
	* @param @param fStatus     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月13日 下午4:55:41
	*/
	private static void showIntent(final Context context,final Intent intent,final String fMenuId,final String fSystemType,final String fClassTypeId,final String fBillId,final String fStatus){
		intent.putExtra(AppContext.FSTATUS_KEY, fStatus);
		intent.putExtra(AppContext.FMENUID_KEY, fMenuId);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemType);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		((Activity)context).startActivityForResult(intent, ACTIVITY_EXPENSEPUBLIC);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showPlusCopy 
	* @Description: 显示加签/抄送页面
	* @param @param context 上下文环境
	* @param @param fType 加签或者抄送 "0"-代表加签  "1"-代表抄送
	* @param @param fSystemId 系统ID
	* @param @param fClassTypeId 单据类型ID
	* @param @param fBillId 单据ID 
	* @param @param fItemNumber 员工号    
	* @param @param fBillName 单据名称     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午5:22:41
	*/
	public static void showPlusCopy(Context context,String fType,String fSystemId,String fClassTypeId,String fBillId,String fItemNumber,String fBillName){
		Intent intent = new Intent(context, PlusCopyActivity.class);
		intent.putExtra(AppContext.FPLUSCOPY_TYPE_KEY, fType);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, fBillName);
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showPlusCopyPersonSearch 
	* @Description: 显示加签/抄送模块人员搜索列表界面
	* @param @param context 上下文环境  
	* @param @param personList  已经选择的人员集合字符串    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 下午1:44:18
	*/
	public static void showPlusCopyPersonSearch(Context context,String personList){
		Intent intent = new Intent(context, PlusCopyPersonActivity.class);
		intent.putExtra(AppContext.PLUSCOPY_PERSON_LIST, personList);
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showLowerNodeApp 
	* @Description: 显示下级节点页面
	* @param @param context  	上下文环境
	* @param @param fSystemId 	系统ID
	* @param @param fClassTypeId 单据类型ID
	* @param @param fBillId 单据ID 
	* @param @param fItemNumber 员工号  
	* @param @param fBillName 单据名称     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月13日 下午2:35:13
	*/
	public static void showLowerNodeApp(Context context,String fSystemId,String fClassTypeId,String fBillId,String fItemNumber,String fBillName){
		Intent intent = new Intent(context, LowerNodeApproveActivity.class);
		intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemId);
		intent.putExtra(AppContext.FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.FBILLID_KEY, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, fBillName);
		
		((Activity)context).startActivityForResult(intent,AppContext.FPLUSCOPY_PERSON_KEY);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDH 
	* @Description: 显示研发工时主页面
	* @param @param context  上下文环境
	* @param @param fItemNumber 员工号   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月19日 上午11:49:50
	*/
	public static void showDH(Context context,String fItemNumber){
		Intent intent = new Intent(context, DHMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHList 
	* @Description: 显示工时列表页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月22日 上午9:26:44
	*/
	public static void showDHList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, DHListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHProjectList 
	* @Description: 显示每周项具体项目列表页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号    
	* @param @param fBillId 周单据ID
	* @param @param fWeekValue 每周值
	* @param @param fWeekDate 每周时间值        
	* @param @param type 类型   show类型和edit类型    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 上午10:15:14
	*/
	public static void showDHProjectList(Context context,String fItemNumber,Integer fBillId,String fWeekValue,String fWeekDate,String type) {
		Intent intent = new Intent(context, DHListProjectActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_DATE, fWeekDate);
		intent.putExtra(AppContext.DEVELOP_HOURS_LIST_PROJECT_TYPE, type);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHDetail 
	* @Description: 显示工时详情页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号
	* @param @param fActionType 操作类型  "Add"或"Edit"
	* @param @param fAccess 新增操作入口,有按钮入口,工时列表入口,项目列表入口,任务类型列表入口       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月18日 下午4:53:29
	*/
	public static void showDHDetail(Context context,String fItemNumber,String fActionType,String fAccess ) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHListDetail 
	* @Description: 显示工时详情页面
	* @param @param context 上下文环境
	* @param @param fBillId 上下文环境
	* @param @param fWeekValue 每周值  
	* @param @param fItemNumber 员工号 
	* @param @param fActionType 操作类型  "Add"或"Edit"
	* @param @param fAccess  新增操作入口,有按钮入口,工时列表入口,项目列表入口,任务类型列表入口  
	* @param @param fWeekDate  传递过来的工时日期    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月18日 下午4:49:45
	*/
	public static void showDHListDetail(Context context,Integer fBillId,String fWeekValue,String fItemNumber,String fActionType,String fAccess,String fWeekDate) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_WEEKDATE, fWeekDate);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHListProjectDetail 
	* @Description: 显示工时详情页面
	* @param @param context 上下文环境
	* @param @param fBillId 周单据ID
	* @param @param fWeekValue 每周值  
	* @param @param fItemNumber 员工号 
	* @param @param fActionType操作类型  "Add"或"Edit"
	* @param @param fAccess 新增操作入口,有按钮入口,工时列表入口,项目列表入口,任务类型列表入口  
	* @param @param fWeekDate 传递过来的工时日期
	* @param @param fProjectCode 传递过来的项目编号
	* @param @param fProjectName 传递过来的项目名称
	* @param @param fTypeId 传递过来的任务类型ID       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月18日 下午3:50:58
	*/
	public static void showDHListProjectDetail(Context context,Integer fBillId,String fWeekValue,String fItemNumber,String fActionType,String fAccess,String fWeekDate,String fProjectCode,String fProjectName,String fTypeId) {
		Intent intent = new Intent(context, DHDetailActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_BILLID, fBillId);
		intent.putExtra(AppContext.DEVELOP_HOURS_WEEK_VALUE, fWeekValue);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACTION_TYPE, fActionType);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_ACCESS, fAccess);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_WEEKDATE, fWeekDate);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE, fProjectCode);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTNAME, fProjectName);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_TYPEID, fTypeId);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHProjectSearch 
	* @Description: 显示项目搜索页面
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月4日 上午11:20:59
	*/
	public static void showDHProjectSearch(Context context) {
		Intent intent = new Intent(context, DHProjectSearchActivity.class);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL_PROJECT);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHTypeList 
	* @Description: 显示任务类别列表
	* @param @param context 上下文环境 
	* @param @param fProjectCode 项目编码    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月4日 上午11:23:11
	*/
	public static void showDHTypeList(Context context,String fProjectCode) {
		Intent intent = new Intent(context, DHTypeListActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE, fProjectCode);
		((Activity)context).startActivityForResult(intent,AppContext.DEVELOP_HOURS_DETAIL_TYPE);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHConfirmList 
	* @Description: 显示工时确认列表页面
	* @param @param context 上下文环境
	* @param @param fItemNumber 员工号    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月19日 上午11:48:20
	*/
	public static void showDHConfirmList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, DHConfirmListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showDHConfirmListPerson 
	* @Description: 显示工时确认列表具体人员信息
	* @param @param context 上下文环境
	* @param @param fProjectNumber 项目经理员工号
	* @param @param fWeekIndex 周次
	* @param @param fYear 年份
	* @param @param fProjectCode 项目编码
	* @param @param fConfrimNumber 确认人员员工号     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月19日 上午9:50:48
	*/
	public static void showDHConfirmListPerson(Context context,String fProjectNumber,Integer fWeekIndex,Integer fYear,String fProjectCode,String fConfrimNumber) {
		Intent intent = new Intent(context, DHConfirmListPersonActivity.class);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER, fProjectNumber);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX, fWeekIndex);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_YEAR, fYear);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE, fProjectCode);
		intent.putExtra(AppContext.DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER, fConfrimNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showAttendanceList 
	* @Description: 显示我的考勤列表界面
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月17日 下午4:25:10
	*/
	public static void showAttendanceList(Context context,String fItemNumber) {
		Intent intent = new Intent(context, AdListActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showAttendanceCheck 
	* @Description: 显示签入/签出打卡界面
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:27:57
	*/
	public static void showAttendanceCheck(Context context,String fItemNumber) {
		Intent intent = new Intent(context, AdCheckInActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketMain 
	* @Description: 显示我的销售主页
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月26日 下午1:59:45
	*/
	public static void showMarketMain(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketMainActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	

	/** 
	* @Title: showMarketBidSearch 
	* @Description: 显示报价查询
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月26日 下午2:01:09
	*/
	public static void showMarketBidSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketBidSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketContractSearch 
	* @Description: 显示合同查询
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月26日 下午2:01:53
	*/
	public static void showMarketContractSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketContractSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketProductSearch 
	* @Description: 显示产品查询
	* @param @param context
	* @param @param fItemNumber     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月30日 上午9:30:52
	*/
	public static void showMarketProductSearch(Context context,String fItemNumber) {
		Intent intent = new Intent(context, MarketProductSearchActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
	
	/** 
	* @Title: showMarketWorkflow 
	* @Description: 显示销售模块工作流信息
	* @param @param context
	* @param @param fItemNumber
	* @param @param fSystemType
	* @param @param fClassTypeId
	* @param @param fBillID
	* @param @param type     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月2日 上午11:42:09
	*/
	public static void showMarketWorkflow(Context context,String fItemNumber,String fSystemType,String fClassTypeId,String fBillID,String type) {
		Intent intent = new Intent(context, MarketWorkflowActivity.class);
		intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
		intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
		intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
		intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
		intent.putExtra(AppContext.MARKET_WORKFLOW_TYPE, type);
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	/** 
	* @Title: sendBroadCast 
	* @Description: 发送通知广播
	* @param @param context
	* @param @param notice 通知数    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月8日 上午9:32:14
	*/
	public static void sendBroadCast(Context context, int notice) {
		if (notice ==0)
			return;
		Intent intent = new Intent("com.dahuatech.app.action.APPWIDGET_UPDATE");
		intent.putExtra(AppContext.BROADCAST_WAITTASKCOUNT_KEY, notice);
		context.sendBroadcast(intent);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 发送日志统计信息
	* @param @param context 调用上下文
	* @param @param logInfo 日志信息      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午11:32:42
	*/
	public static void sendLogs(Context context,LogsRecordInfo logInfo){
		mContext=context;
		mInstance.new logRecordAsync().execute(logInfo);
	}
	
	/**
	 * @ClassName logRecordAsync
	 * @Description 日志统计异步操作
	 * @author 21291
	 * @date 2014年7月31日 上午10:46:26
	 */
	private class logRecordAsync extends AsyncTask<LogsRecordInfo , Void, Void> {
		
	   @Override
	   protected Void doInBackground(LogsRecordInfo... params) {
		   String serviceUrl=AppUrl.URL_API_HOST_ANDROID_LOGSRECORD;  
		   FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(mContext);
		   SettingBusiness sBusiness =(SettingBusiness)factoryBusiness.getInstance("SettingBusiness",serviceUrl);   
		   sBusiness.SendLogRecord(params[0]);
		   return null;   
	   }

	   @Override
	   protected void onPostExecute(Void result) { }
	}

	/** 
	* @Title: Exit 
	* @Description: 退出程序
	* @param @param cont     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:40:47
	*/
	public static void Exit(final Context cont) {
		TypedValue typedValue = new TypedValue();
		cont.getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(typedValue.resourceId);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						AppManager.getAppManager().AppExit(cont); // 退出
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/** 
	* @Title: sendAppCrashReport 
	* @Description: 发送App异常崩溃报告
	* @param @param cont 上下文
	* @param @param crashReport 具体错误信息 
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:40:00
	*/
	@SuppressLint("InlinedApi")
	public static void sendAppCrashReport(final Context cont,final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						Intent i = new Intent(Intent.ACTION_SEND);
						//i.setType("text/plain"); //模拟器
						i.setType("message/rfc822"); // 真机
						i.putExtra(Intent.EXTRA_EMAIL,new String[] { "22292436@qq.com" });
						i.putExtra(Intent.EXTRA_SUBJECT,"大华移动办公Android客户端 - 错误报告");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "发送错误报告"));
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}
	
	/**
	 * 弹出Toast消息
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void ToastMessageLong(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	
	/** 
	* @Title: finish 
	* @Description: 点击返回监听事件
	* @param @param activity
	* @param @return     
	* @return View.OnClickListener    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午3:37:33
	*/
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/** 
	* @Title: loginOrLogout 
	* @Description: 用户登录或注销
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午3:26:58
	*/
	public static void loginOrLogout(Activity activity) {
		AppContext ac = (AppContext) activity.getApplication();
		if (ac.isLogin()) {
			ac.Logout();
			ToastMessage(activity, "已退出登录");
		} else {
			showLogin(activity,false,true);
		}
	}
	
	/** 
	* @Title: clearAppCache 
	* @Description: 清除app缓存
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午3:27:06
	*/
	public static void clearAppCache(Activity activity) {
		final AppContext ac = (AppContext) activity.getApplication();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					ToastMessage(ac, "缓存清除成功");
				} else {
					ToastMessage(ac, "缓存清除失败");
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					ac.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
}
