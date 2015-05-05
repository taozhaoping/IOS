package com.dahuatech.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.CacheManager;

import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.common.MethodsCompat;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName AppContext
 * @Description 局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author 21291
 * @date 2014年4月16日 下午1:43:29
 */
@SuppressLint("DefaultLocale")
public class AppContext extends Application {
	//测试员工
	public static final String TEMPTEST_FITEMNUMBER="27060";  //临时测试的员工号
	public static final String TEMPTEST_FITEMNAME="27060";  //临时测试的员工名称

	//引导页和欢迎页首选项配置文件
	public static final String GUIDEANDWELCOME_CONFIG_FILE = "GUIDE_WELCOME_CONFIG_FILE"; 
	public static final String IS_FIRST_COUNT_KEY="Is_First_Count_Key"; //引导页登陆次数
	
	/**************登陆部分开始*****************/
	
	//LoginActivity首选项配置文件
	public static final long LOGINACTIVITY_SESSION = 5;	 								//登陆过期时间设置 分钟为单位 默认5分钟
	public static final String LOGINACTIVITY_CONFIG_FILE = "LOGINACTIVITY_CONFIG_FILE"; 
	public static final String USER_NAME_KEY="userName_Key"; 							//员工号key
	public static final String PASSWORD_KEY="password_Key"; 							//密码key
	public static final String FITEMNAME_KEY="fItemName_Key"; 							//员工名称key
	public static final String ACCURATE_EXPIRES_TIME = "accurate_expires_time";  		//session key理论上的过期时间
	public static final String FNOTICE_ALARM_KEY="fNotice_Alarm_Key"; 			 		//通知栏信息
	public static final String FNOTICE_LOGIN_IS_SHOW_KEY="fNotice_Login_Is_show_Key"; 	//登陆是否有通知栏信息
	public static final String FLOGIN_IS_FIRST_KEY="fLogin_Is_First_Key"; 				//是否首次登陆
	
	/**************登陆部分结束*****************/
	
	/**************首页部分开始*****************/
	
	//MainActivity首选项配置文件 
	public static final String FITEMNUMBER_KEY="fItemNumber_Key"; 						//员工号
	/**************首页部分结束*****************/

	/**************设置部分开始*****************/
	
	//SettingActivity首选项配置文件
	public static final String SETTINGACTIVITY_CONFIG_FILE = "SETTINGACTIVITY_CONFIG_FILE"; 
	public static final String IS_NOTICE_KEY="isNotice_Key"; 						//是否启用通知栏
	public static final String IS_GESTURES_KEY="is_Gestures_key"; 					//是否启用手势密码
	
	public static final String IS_EXPENSE_JURNEY_KEY="is_expense_Jurney_Key"; 	  	//打车报销首页开关按钮
	public static final String IS_EXPENSE_ADDRESS_KEY="is_expense_address_Key"; 	//开始地址
	public static final String IS_EXPENSE_TIME_KEY="is_expense_time_Key"; 			//开始时间
	
	/**************设置部分结束*****************/
	
	/**************手势密码部分结束*****************/
	
	public static final String GESTURES_PASSWORD_SOURCE_KEY="gestures_password_source_key"; //手势密码来源
	
	/**************手势密码部分结束*****************/
	
	/**************任务列表部分开始***************/
	
	//TaskListActivity首选项配置文件
	public static final String TASKLISTACTIVITY_CONFIG_FILE = "TASKLISTACTIVITY_CONFIG_FILE"; 
	public static final String FSTATUS_KEY="fStatus_Key"; 							//默认保存记录状态 0-待审批 1-已审批
	public static final String FTOTALCOUNT_KEY="fTotalCount_key";  					//分页获取的总记录数
	public static final String PAGE_SIZE = "4";										//默认分页大小
	
	/**************任务列表部分结束****************/
	
	/**************打卡部分开始***************/
	
	public static final String ADCHECKINACTIVITY_CONFIG_FILE = "ADCHECKINACTIVITY_CONFIG_FILE";	  //打卡存储文件
	public static final String AD_AMAP_ADDRESS_KEY="ad_aMap_address_Key"; 						  //打卡中心地址
	public static final String AD_AMAP_ADDRESS_CACHE_KEY="ad_aMap_address_cache_Key"; 			  //缓存版本
	
	/**************打卡部分结束****************/
	
	/**************单据详细页部分开始****************/
	
	//单据详细页首选项配置文件
	public static final String FMENUID_KEY="FMenuID_Key";  
	public static final String FSYSTEMTYPE_KEY="FSystemType_Key";  
	public static final String FBILLID_KEY="FBillID_Key";  
	public static final String FCLASSTYPEID_KEY="FClassTypeId_Key";  
	
	//对私单据 费用类型ID值
	public static final String FEXPENSEPRIVATE_COSTTYPE_KEY="FExpensePrivate_CostType_key";
	
	/**************单据详细页部分结束****************/
	
	/**************工作流页面部分开始****************/
	
	public static final String TA_APPROVE_FSTATUS = "TA_Approve_FStatus";//审批操作后的状态
	public static final int ACTIVITY_WORKFLOW = 0x11;  		//未审批过记录
	public static final int ACTIVITY_WORKFLOWBEEN = 0x12; 	//已审批过记录
	
	//WorkFlowActivity首选项配置文件
	public static final String WORKFLOW_FSYSTEMTYPE_KEY="Workflow_FSystemType_Key";  
	public static final String WORKFLOW_FCLASSTYPEID_KEY="Workflow_fClassTypeId_Key";  
	public static final String WORKFLOW_FBILLID_KEY="Workflow_fBillId_Key";  
	public static final String WORKFLOW_FBILLNAME_KEY="Workflow_fBillName_Key"; 
	//工程商审批key
	public static final String WORKFLOW_ENGINEERING_KEY="B3-F0-C7-F6-BB-76-3A-F1-BE-91-D9-E7-4E-AB-FE-B1-99-DC-1F-1F";
	//报销系统单据审批key
	public static final String WORKFLOW_EXPENSEPRIVATE_KEY="FE-5D-BB-CE-A5-CE-7E-29-88-B8-C6-9B-CF-DF-DE-89-04-AA-BC-1F";
	//新版工作流平台审批key
	public static final String WORKFLOW_NEWOFFICE_KEY="9E-6A-55-B6-B4-56-3E-65-2A-23-BE-9D-62-3C-A5-05-5C-35-69-40";
	//考勤单据审批key(新版)
	public static final String WORKFLOW_ATTENDANCE_HR_KEY="5B-38-4C-E3-2D-8C-DE-F0-2B-C3-A1-39-D4-CA-C0-A2-2B-B0-29-E8";
	
	//PlusCopyActivity配置字段
	public static final String FPLUSCOPY_TYPE_KEY="fPlusCopy_Type_Key"; //加签/抄送 类型
	public static final int FPLUSCOPY_PERSON_KEY = 0x20;   				//跳转到人员列表
	public static final String PLUSCOPY_PERSON_LIST="pluscopy_person_list";    //人员列表传递选中的人员
	
	/**************工作流页面部分结束****************/
	
	/**************通知栏服务部分开始****************/
	
	public static final String BROADCAST_WAITTASKCOUNT_KEY="BroadCast_WaitTaskCount_Key"; //BroadCast首选项配置文件  
	
	/**************通知栏服务部分结束****************/
	
	/**************我的流水模块开始****************/
	
	public static final String EXPENSE_FLOW_DETAIL_STATUS="expense_flow_detail_status_Key";  //我的流水详情状态 0-新增,1-修改
	public static final String EXPENSE_FLOW_DETAIL_EXPENDTIME="expense_flow_detail_expendtime_Key";  //是否有传递过来的消费时间
	public static final String EXPENSE_FLOW_DETAIL_SKIP_SOURCE="expense_flow_detail_skip_source_Key";  //跳转来源
	
	public static final String EXPENSE_FLOW_DETAIL_BACK_FID="back_fId"; 		//回传的主键
	public static final String EXPENSE_FLOW_DETAIL_BACK_FNAME="back_fName"; 	//回传的名称
	
	public static final int EXPENSE_FLOW_DETAIL_CLIENT = 0x14; 		//跳转到客户列表
	public static final int EXPENSE_FLOW_DETAIL_PROJECT = 0x15; 	//跳转到项目列表
	
	/**************我的流水模块结束****************/
	
	/**************我的会议模块开始****************/	
	
	public static final String MEETING_DETAIL_FSTATUS="meeting_detail_fStatus";     //会议详情状态
	public static final String MEETING_DETAIL_FORDERID="meeting_detail_fOrderId";    //会议主键内码
	
	public static final String MEETING_DETAIL_SELECTEDDATE="meeting_detail_selecteddate";    //会议详情页面已经选中的日期
	public static final String MEETING_DETAIL_SELECTEDSTART="meeting_detail_selectedstart";  //会议详情页面已经选中的开始时间
	public static final String MEETING_DETAIL_SELECTEDEND="meeting_detail_selectedend";      //会议详情页面已经选中的结束时间
	
	public static final String MEETING_DETAIL_ROOM_ID="meeting_detail_room_id";    	 //会议室ID
	public static final String MEETING_DETAIL_ROOM_NAME="meeting_detail_room_name";  //会议室名称
	public static final String MEETING_DETAIL_ROOM_IP="meeting_detail_room_ip";      //会议室IP
	
	public static final String MEETING_DETAIL_PERSON_LIST="meeting_detail_person_list";    //会议详情页面传递已经选中参与的人员
	public static final String MEETING_DETAIL_PERSON_FLAG="meeting_detail_person_flag";    //人员搜索列表页返回类型标志
	
	public static final int MEETING_DETAIL_ROOM = 0x16;   //跳转到会议室列表
	public static final int MEETING_DETAIL_PERSON = 0x17; //跳转到人员搜索列表
	public static final int MEETING_DETAIL_MASTER = 0x18; //跳转到人员搜索列表
	public static final int MEETING_DETAIL_INFO = 0x19;   //跳转到会议详情页
	
	/**************我的会议模块结束****************/
	
	/**************研发工时模块开始****************/	
	
	public static final String DEVELOP_HOURS_WEEK_BILLID="develop_hours_fBillId";    					//周单据ID
	public static final String DEVELOP_HOURS_WEEK_VALUE="develop_hours_fWeekValue";    					//每周值
	public static final String DEVELOP_HOURS_WEEK_DATE="develop_hours_fWeekDate";      					//每周时间值
	public static final String DEVELOP_HOURS_LIST_PROJECT_TYPE="develop_hours_list_project_type";   	//显示类型
	
	public static final String DEVELOP_HOURS_DETAIL_ACTION_TYPE="dh_detail_action_type";     			//详情操作类型
	public static final String DEVELOP_HOURS_DETAIL_ACCESS="dh_detail_access";      		 			//新增操作入口
	
	public static final String DEVELOP_HOURS_DETAIL_PASS_WEEKDATE="dh_detail_pass_WeekDate";  			//传递过来的工作日时间
	public static final String DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE="dh_detail_pass_ProjectCode";  	//传递过来的项目编号
	public static final String DEVELOP_HOURS_DETAIL_PASS_PROJECTNAME="dh_detail_pass_ProjectName";  	//传递过来的项目名称
	public static final String DEVELOP_HOURS_DETAIL_PASS_TYPEID="dh_detail_pass_TypeId";  				//传递过来的任务类型ID
	
	public static final int DEVELOP_HOURS_DETAIL_PROJECT = 0x31;   	//跳转到项目搜索页面
	public static final int DEVELOP_HOURS_DETAIL_TYPE = 0x32;   	//跳转到任务类别页面
	public static final int DEVELOP_HOURS_DETAIL = 0x33;   			//跳转到工时新增页面
	
	public static final String DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE="dh_detail_back_fProjectCode"; 	//项目搜索列表回传的项目编号
	public static final String DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME="dh_detail_back_fProjectName"; 	//项目搜索列表回传的项目名称
	
	public static final String DEVELOP_HOURS_DETAIL_BACK_FTYPEID="dh_detail_back_fTypeId"; 				//任务类别列表回传的任务ID
	public static final String DEVELOP_HOURS_DETAIL_BACK_FTYPENAME="dh_detail_back_fTypeName"; 			//任务类别列表回传的任务名称
	
	public static final String DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER="dh_confirm_pass_ProjectNumber";  	//确认列表传递过来的项目经理工号
	public static final String DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX="dh_confirm_pass_WeekIndex";  			//确认列表传递过来的周次
	public static final String DEVELOP_HOURS_CONFIRM_PASS_YEAR="dh_confirm_pass_Year";  					//确认列表传递过来的年份
	public static final String DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE="dh_confirm_pass_ProjectCode";  		//确认列表传递过来的项目编号
	public static final String DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER="dh_confirm_pass_ConfrimNumber";  	//确认列表传递过来的确认人员员工号

	/**************研发工时模块结束****************/	
	
	/**************通讯录模块开始****************/	
	
	public static final String CONTACTS_SOURCE_TYPE="contacts_source_type";     //通讯录来源类型
	public static final String CONTACTS_RETURN_VALUE="contacts_return_value";    //通讯录返回值
	public static final int CONTACTS_SMS_SEARCH = 0x30; 	  					//邀请同事页面跳转到通讯录
	
	/**************通讯录模块结束****************/	
	
	/**************我的销售模块开始****************/	
	
	public static final String MARKET_WORKFLOW_TYPE="market_workflow_type";     //我的销售流程记录类型
	
	/**************我的销售模块结束****************/	
	

	/**************网络连接类型开始****************/	
	
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	
	/**************网络连接类型结束****************/	
	
	/**************公共信息模块开始****************/	
	
	private static final int CACHE_TIME = 60*60000;//缓存失效时间 1个小时
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	private boolean login = false;	// 登录状态
	private int loginUid =0;	// 登陆的员工号

	public boolean containsProperty(String key){
		Properties props = getProperties();
		return props.containsKey(key);
	}
	
	public void setProperties(Properties ps){
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties(){
		return AppConfig.getAppConfig(this).get();
	}
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	public void removeProperty(String...key){
		AppConfig.getAppConfig(this).remove(key);
	}
	
	/**************公共信息模块结束****************/	
	
	/**************百度地图管理部分开始****************/
	private static AppContext mInstance;
	private static Context mContext;
	static{
		mInstance=null;
		mContext=null;
	}
	
	//获取自身实例
	public static AppContext getInstance() {
		return mInstance;
	}
	
	//获取全局上下文实例
	public static Context getContext() {
		return mContext;
	}
	
	/**************百度地图管理部分结束****************/

	@Override
	public void onCreate() {
		super.onCreate();
		try {
	        Class.forName("android.os.AsyncTask");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		if(mInstance==null){   //实例化自身
			mInstance = this;
		}
		if(mContext==null){
			mContext=getApplicationContext();
		}
		//注册App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());     
	}

	//建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	/** 
	* @Title: getPackageInfo 
	* @Description: 获取App安装包信息
	* @param @return     
	* @return PackageInfo    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:47:53
	*/
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try { 
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
		if(info == null) info = new PackageInfo();
		return info;
	}
	
	/** 
	* @Title: getAppId 
	* @Description: 获取App唯一标识
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月23日 下午7:13:50
	*/
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if(StringUtils.isEmpty(uniqueID)){
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}
	
	/** 
	* @Title: isLogin 
	* @Description: 用户是否登录
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午11:00:18
	*/
	public boolean isLogin() {
		return this.login;
	}
	
	/** 
	* @Title: setLogin 
	* @Description: 设置登陆状态
	* @param @param login     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月23日 下午7:17:32
	*/
	public void setLogin(boolean login){
		this.login=login;
	}
	
	/** 
	* @Title: getLoginUid 
	* @Description: 获取登录用户id
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:32:10
	*/
	public int getLoginUid() {
		return this.loginUid;
	}
	
	/** 
	* @Title: Logout 
	* @Description:  用户退出
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午11:00:10
	*/
	public void Logout() {
		ApiClient.cleanCookie();
		this.cleanCookie();
		clearLoginPwd();    //清除登陆密码
		
		clearLoginSP(getSharedPreferences(LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE)); 			 //清除登陆配置文件
		clearSettingSP(getSharedPreferences(SETTINGACTIVITY_CONFIG_FILE,MODE_PRIVATE));          //清除设置配置文件
		clearSharedPreferences(getSharedPreferences(TASKLISTACTIVITY_CONFIG_FILE,MODE_PRIVATE)); //清除我的任务配置文件
	}
	
	/** 
	* @Title: clearLoginSP 
	* @Description: 清除登陆页的首选项信息
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 下午4:49:49
	*/
	private void clearLoginSP(SharedPreferences sp){
		Editor editor=sp.edit();
		editor.remove(AppContext.PASSWORD_KEY); //清除密码
		//editor.remove(AppContext.FITEMNAME_KEY); //清除员工名称
		editor.remove(AppContext.ACCURATE_EXPIRES_TIME); //清除过期时间
		editor.remove(AppContext.FNOTICE_ALARM_KEY); //清除通知栏信息
		editor.commit();
	}
	
	/** 
	* @Title: clearSettingSP 
	* @Description: 清除设置页的首选项信息
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月3日 下午4:49:29
	*/
	private void clearSettingSP(SharedPreferences sp){
		Editor editor=sp.edit();
		editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY); //清除打车报销主页下开关按钮
		editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY); //清除已经按下产生的地址
		editor.remove(AppContext.IS_EXPENSE_TIME_KEY); //清除已经按下产生的时间
		editor.commit();
	}
	
	/** 
	* @Title: getLoginInfo 
	* @Description: 获取登录信息
	* @param @return     
	* @return UserInfo    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午12:59:35
	*/
	public UserInfo getLoginInfo() {		
		UserInfo user = UserInfo.getUserInfo();	
		user.setFItemNumber(getProperty("UserInfo.FItemNumber"));
		user.setFItemName(getProperty("UserInfo.FItemName"));
		user.setFPassword(getProperty("UserInfo.FPassword"));
		user.setIsRememberMe(StringUtils.toBool(getProperty("UserInfo.IsRememberMe")));
		return user;
	}
	
	/** 
	* @Title: saveLoginInfo 
	* @Description: 保存登录信息
	* @param @param user     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:36:19
	*/
	@SuppressWarnings("serial")
	public void saveLoginInfo(final UserInfo user) {
		this.loginUid = Integer.valueOf(user.getFItemNumber()); 
		this.login = true;
		setProperties(new Properties(){{
			setProperty("UserInfo.FItemNumber", user.getFItemNumber());
			setProperty("UserInfo.FItemName", user.getFItemName());
			setProperty("UserInfo.FPassword",user.getFPassword());
			setProperty("UserInfo.IsRememberMe", String.valueOf(user.isIsRememberMe()));//是否记住我的信息
		}});		
	}

	/** 
	* @Title: clearLoginInfo 
	* @Description: 清除登录密码
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月10日 上午9:15:40
	*/
	public void clearLoginPwd() {
		this.loginUid = 0;
		this.login = false;
		removeProperty("UserInfo.FPassword");
	}
	
	/** 
	* @Title: clearSharedPreferences 
	* @Description: 删除首选项配置文件
	* @param @param sp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 下午2:53:49
	*/
	public void clearSharedPreferences(SharedPreferences sp){
		if(login==true){
			this.setLogin(false);
		}
		sp.edit().clear().commit();
	}

	/** 
	* @Title: isAudioNormal 
	* @Description: 检测当前系统声音是否为正常模式
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:55:48
	*/
	public boolean isAudioNormal() {
		AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE); 
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}
	
	/** 
	* @Title: isAppSound 
	* @Description: 应用程序是否发出提示音
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:56:21
	*/
	public boolean isAppSound() {
		return isAudioNormal() && isVoice();
	}
	
	/** 
	* @Title: isVoice 
	* @Description: 是否发出提示音
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:30:17
	*/
	public boolean isVoice(){
		String perf_voice = getProperty(AppConfig.CONF_VOICE);
		//默认是开启提示声音
		if(StringUtils.isEmpty(perf_voice))
			return true;
		else
			return StringUtils.toBool(perf_voice);
	}
	
	/** 
	* @Title: setConfigVoice 
	* @Description: 设置是否发出提示音
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:31:47
	*/
	public void setConfigVoice(boolean b){
		setProperty(AppConfig.CONF_VOICE, String.valueOf(b));
	}
	
	/** 
	* @Title: isNetworkConnected 
	* @Description: 检测网络是否可用
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:56:31
	*/
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){ // 当前网络是连接的
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	* @Title: getNetworkType 
	* @Description: 获取当前网络类型
	* @param @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络    
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:57:21
	*/
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtils.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	/** 
	* @Title: isMethodsCompat 
	* @Description: 判断当前版本是否兼容目标版本的方法
	* @param @param VersionCode
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:59:21
	*/
	public boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}
	
	/** 
	* @Title: isLoadImage 
	* @Description: 是否加载显示文章图片
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:27:59
	*/
	public boolean isLoadImage(){
		String perf_loadimage = getProperty(AppConfig.CONF_LOAD_IMAGE);
		//默认是加载的
		if(StringUtils.isEmpty(perf_loadimage))
			return true;
		else
			return StringUtils.toBool(perf_loadimage);
	}
	
	/** 
	* @Title: setConfigLoadimage 
	* @Description: 设置是否加载文章图片
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:28:12
	*/
	public void setConfigLoadimage(boolean b){
		setProperty(AppConfig.CONF_LOAD_IMAGE, String.valueOf(b));
	}
	
	/** 
	* @Title: isCheckUp 
	* @Description: 是否启动检查更新
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:33:07
	*/
	public boolean isCheckUp(){
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
		//默认是开启
		if(StringUtils.isEmpty(perf_checkup))
			return true;
		else
			return StringUtils.toBool(perf_checkup);
	}
	
	/** 
	* @Title: setConfigCheckUp 
	* @Description: 设置启动检查更新
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:33:26
	*/
	public void setConfigCheckUp(boolean b){
		setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}
	
	/** 
	* @Title: isScroll 
	* @Description: 是否左右滑动
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:33:51
	*/
	public boolean isScroll(){
		String perf_scroll = getProperty(AppConfig.CONF_SCROLL);
		//默认是关闭左右滑动
		if(StringUtils.isEmpty(perf_scroll))
			return false;
		else
			return StringUtils.toBool(perf_scroll);
	}
	
	/** 
	* @Title: setConfigScroll 
	* @Description: 设置是否左右滑动
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:34:21
	*/
	public void setConfigScroll(boolean b){
		setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
	}
	
	/** 
	* @Title: isHttpsLogin 
	* @Description: 是否Https登录
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:39:29
	*/
	public boolean isHttpsLogin(){
		String perf_httpslogin = getProperty(AppConfig.CONF_HTTPS_LOGIN);
		//默认是http
		if(StringUtils.isEmpty(perf_httpslogin))
			return false;
		else
			return StringUtils.toBool(perf_httpslogin);
	}
	
	/** 
	* @Title: setConfigHttpsLogin 
	* @Description: 设置是是否Https登录
	* @param @param b     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:39:38
	*/
	public void setConfigHttpsLogin(boolean b){
		setProperty(AppConfig.CONF_HTTPS_LOGIN, String.valueOf(b));
	}

	/** 
	* @Title: cleanCookie 
	* @Description: 清除保存的缓存
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:40:16
	*/
	public void cleanCookie(){
		removeProperty(AppConfig.CONF_COOKIE);
	}
	
	/** 
	* @Title: isReadDataCache 
	* @Description: 判断缓存数据是否可读
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:40:26
	*/
	@SuppressWarnings("unused")
	private boolean isReadDataCache(String cachefile){
		return readObject(cachefile) != null;
	}
	
	/** 
	* @Title: isExistDataCache 
	* @Description: 判断缓存数据是否可读
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:40:35
	*/
	private boolean isExistDataCache(String cachefile){
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	/** 
	* @Title: isCacheDataFailure 
	* @Description: 判断缓存是否失效
	* @param @param cachefile
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:40:55
	*/
	public boolean isCacheDataFailure(String cachefile){
		boolean failure = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if(!data.exists())
			failure = true;
		return failure;
	}
	
	/**
	 * 清除app缓存
	 */
	@SuppressWarnings("deprecation")
	public void clearAppCache(){
		//清除webview缓存
		File file = CacheManager.getCacheFileBaseDir();  
		if (file != null && file.exists() && file.isDirectory()) {  
		    for (File item : file.listFiles()) {  
		    	item.delete();  
		    }  
		    file.delete();  
		}  		  
		deleteDatabase("webview.db");  
		deleteDatabase("webview.db-shm");  
		deleteDatabase("webview.db-wal");  
		deleteDatabase("webviewCache.db");  
		deleteDatabase("webviewCache.db-shm");  
		deleteDatabase("webviewCache.db-wal");  
		//清除数据缓存
		clearCacheFolder(getFilesDir(),System.currentTimeMillis());
		clearCacheFolder(getCacheDir(),System.currentTimeMillis());
		//2.2版本才有将应用缓存转移到sd卡的功能
		if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),System.currentTimeMillis());
		}
		//清除编辑器保存的临时内容
		Properties props = getProperties();
		for(Object key : props.keySet()) {
			String _key = key.toString();
			if(_key.startsWith("temp"))
				removeProperty(_key);
		}
	}	
	
	/** 
	* @Title: clearCacheFolder 
	* @Description:  清除缓存目录
	* @param @param dir 目录
	* @param @param curTime 当前系统时间
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:42:32
	*/
	private int clearCacheFolder(File dir, long curTime) {          
	    int deletedFiles = 0;         
	    if (dir!= null && dir.isDirectory()) {             
	        try {                
	            for (File child:dir.listFiles()) {    
	                if (child.isDirectory()) {              
	                    deletedFiles += clearCacheFolder(child, curTime);          
	                }  
	                if (child.lastModified() < curTime) {     
	                    if (child.delete()) {                   
	                        deletedFiles++;           
	                    }    
	                }    
	            }             
	        } catch(Exception e) {       
	            e.printStackTrace();    
	        }     
	    }       
	    return deletedFiles;     
	}
	
	/** 
	* @Title: setMemCache 
	* @Description: 将对象保存到内存缓存中
	* @param @param key
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:47:45
	*/
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}
	
	/** 
	* @Title: getMemCache 
	* @Description: 从内存缓存中获取对象
	* @param @param key
	* @param @return     
	* @return Object    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:47:57
	*/
	public Object getMemCache(String key){
		return memCacheRegion.get(key);
	}
	
	/** 
	* @Title: setDiskCache 
	* @Description: 保存磁盘缓存
	* @param @param key
	* @param @param value
	* @param @throws IOException     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:48:05
	*/
	public void setDiskCache(String key, String value) throws IOException {
		FileOutputStream fos = null;
		try{
			fos = openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		}finally{
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: getDiskCache 
	* @Description: 获取磁盘缓存数据
	* @param @param key
	* @param @return
	* @param @throws IOException     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:48:14
	*/
	public String getDiskCache(String key) throws IOException {
		FileInputStream fis = null;
		try{
			fis = openFileInput("cache_"+key+".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		}finally{
			try {
				fis.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: saveObject 
	* @Description: 保存对象
	* @param @param ser
	* @param @param file
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:48:24
	*/
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	/** 
	* @Title: readObject 
	* @Description: 读取对象
	* @param @param file
	* @param @return     
	* @return     
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午2:48:34
	*/
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/** 
	* @Title: isHaveUploadCount 
	* @Description: 判断是否存在未上传记录的乘车信息
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年5月19日 下午3:28:24
	*/
	public boolean isHaveUploadCount(){
		boolean flag=false;
		GPSDbAdapter mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		if(mDbHelper.getGpsdbByUploadFlagCount("0") > 0){
			mDbHelper.closeSqlLite();
			flag=true;
		}
		mDbHelper.closeSqlLite();
		return flag;
	}
}
