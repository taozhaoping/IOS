package com.dahuatech.app.ui.main;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdAmapListInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NoticeBussiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @ClassName MainActivity
 * @Description 应用程序首页
 * @author 21291
 * @date 2014年4月17日 下午3:10:14
 * 
 */
public class MainActivity extends BaseActivity {

	private TextView txtFItemName,txtWelcome,txtWaitTaskCount;
	private String fItemNumber,fItemName;     	//员工号,员工名称
	private NoticeBussiness noticeBussiness;  	//待审批数业务逻辑类
	private AttendanceBusiness aBusiness;	  	//打卡中心地点业务逻辑类
	private Button btnMyTask,btnExpense,btnMeeting,btnContacts,btnDevelopHour;
	private Button btnAttendance,btnMarket,btnLync;
	private boolean fNotice=false;
	private String aMapCacheKey;				//缓存版本号
	private AppContext appContext;  		  	//全局Context
	
	private SharedPreferences loginSp,aMapSp;	//配置文件
	private SlidingMenu menu;					//侧边栏
	private CommonMenu commonMenu;				//公共菜单
	private Calendar cal;						//日期类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		if(savedInstanceState!=null){  //如果是重新初始化，则恢复回收之前的值
			fItemNumber=savedInstanceState.getString(AppContext.USER_NAME_KEY);
			fItemName=savedInstanceState.getString(AppContext.FITEMNAME_KEY);	
		}
		
		//传递的信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.USER_NAME_KEY);
			fItemName=extras.getString(AppContext.FITEMNAME_KEY);	
		}
		
		loginSp=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aMapSp=getSharedPreferences(AppContext.ADCHECKINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aMapCacheKey=aMapSp.getString(AppContext.AD_AMAP_ADDRESS_CACHE_KEY, "0");  //获取缓存版本号，默认为0
		//配置侧边菜单类
		menu=new SlidingMenu(this);	
		int left=(int)(getPixelsWidth() * 2 / 3);					//左边显示宽度
		commonMenu=CommonMenu.getCommonMenu(MainActivity.this,loginSp,menu,"main",left);
		commonMenu.initSlidingMenu();
		commonMenu.initLeftButton();
		txtWaitTaskCount=(TextView)findViewById(R.id.main_msg_MyTask);
		setBtnEvent();
		initBusiness();
		getAmapAddress();	//获取打卡中心地址
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {  //活动被回收之前 先保存员工号和员工名称
		super.onSaveInstanceState(outState);
		outState.putString(AppContext.USER_NAME_KEY, fItemNumber);
		outState.putString(AppContext.FITEMNAME_KEY, fItemName);
	}

	/** 
	* @Title: initBusiness 
	* @Description: 初始化信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 下午4:08:19
	*/
	private void initBusiness(){
		 FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MainActivity.this);
		 noticeBussiness=(NoticeBussiness)factoryBusiness.getInstance("NoticeBussiness","");   
		 aBusiness=(AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness","");   
	}
	
	/** 
	* @Title: setBtnEvent 
	* @Description: 初始化按钮和事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 下午4:00:24
	*/
	private void setBtnEvent(){
		txtFItemName=(TextView) findViewById(R.id.main_txtFItemName);
		txtFItemName.setText(fItemName);
		
		txtWelcome=(TextView) findViewById(R.id.main_txtWelcome);
		//txtWelcome.setText(showWelcome());
		
		//我的任务
		btnMyTask=(Button) findViewById(R.id.main_btnMyTask);
		btnMyTask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMyTask(MainActivity.this,fItemNumber);
			}
		});
		
		//报销流水	
		btnExpense=(Button) findViewById(R.id.main_btnExpense);
		btnExpense.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseMain(MainActivity.this,fItemNumber);
			}
		});
		
		//通讯录
		btnContacts=(Button) findViewById(R.id.main_btnContacts);
		btnContacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showContacts(MainActivity.this,fItemNumber,"main_search");
			}
		});
		
		//我的会议
		btnMeeting=(Button) findViewById(R.id.main_btnMeeting);
		btnMeeting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMeetingList(MainActivity.this,fItemNumber);
			}
		});
		
		//研发工时
		btnDevelopHour = (Button) findViewById(R.id.main_btnDevelopHour);
		btnDevelopHour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showDH(MainActivity.this,fItemNumber);
			}
		});
		
		//我的考勤
		btnAttendance=(Button) findViewById(R.id.main_btnAttendance);
		btnAttendance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showAttendanceCheck(MainActivity.this,fItemNumber);
			}
		});
		
		//我的销售
		btnMarket=(Button) findViewById(R.id.main_btnMarket);
		btnMarket.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketMain(MainActivity.this,fItemNumber);
			}
		});
		
		//启动LYNC
		btnLync=(Button) findViewById(R.id.main_btnlync);
		btnLync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.microsoft.office.lync");
				if(launchIntent!=null){
					startActivity(launchIntent);
					sendLyncLogs("start");
				}
				else{
					alertLync();
				}
			}
		});
	}	
	
	/** 
	* @Title: showWelcome 
	* @Description: 根据当前时间，显示欢迎词
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2015年1月16日 上午10:09:12
	*/
	private String showWelcome(){
		String welCome="";
		cal = new GregorianCalendar();
		String currentWeekDay=DateHelper.getWeekOfDate(cal,cal.getTime());
		if("FSaturday".equals(currentWeekDay) || "FSunday".equals(currentWeekDay)){  //说明周末上班
			welCome=getResources().getString(R.string.main_weekend_hint);
		}
		else{  //说明是工作日
			String currentYear=String.valueOf(cal.get(Calendar.YEAR)); 			//当前年份
			String summerTime=currentYear+"-05-01";					   			//夏令时
			String winterTime=currentYear+"-10-01";					   			//冬令时
			String currentDate=StringUtils.toShortDateString(cal.getTime()); 	//当前日期
			String nowTime=StringUtils.toDateString(cal.getTime());  			//当前时间
			String amSixTime=currentDate+" 06:00:00";				 			//早上6点
			String amEightTime=currentDate+" 08:30:00";				 			//早上8点30分
			String amTwelveTime=currentDate+" 12:00:00";		     			//中午12点
			String pmThirteenTime="";		     					 			//下午1点
			if(DateHelper.dateCompare(cal, currentDate, summerTime) < 0){
				 pmThirteenTime=currentDate+" 13:00:00";
			}
			else if(DateHelper.dateCompare(cal, currentDate, winterTime) <0){
				pmThirteenTime=currentDate+" 13:30:00";
			}
			else{
				pmThirteenTime=currentDate+" 13:00:00";
			}
			
			String pmSeventeenTime=currentDate+" 17:30:00";						//下午17点30分				
			String pmEighteenTime=currentDate+" 18:00:00";						//下午18点					
		
			if(DateHelper.dateCompare(cal, nowTime, amSixTime) < 0){
				welCome=getResources().getString(R.string.main_overtime_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, amEightTime) < 0){
				welCome=getResources().getString(R.string.main_morning_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, amTwelveTime) < 0){
				welCome="";
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmThirteenTime) < 0){
				welCome=getResources().getString(R.string.main_noon_hint);
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmSeventeenTime) < 0){
				welCome="";
			}
			else if(DateHelper.dateCompare(cal, nowTime, pmEighteenTime) < 0){
				welCome=getResources().getString(R.string.main_off_hint);
			}
			else{
				welCome=getResources().getString(R.string.main_overtime_hint);
			}
		}
		return welCome;
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: 获取屏幕像素宽度
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年12月2日 上午11:21:35
	*/
	private int getPixelsWidth(){
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: alertLync 
	* @Description: 弹出LYNC客户端下载地址
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 下午2:12:47
	*/
	@SuppressLint("InlinedApi")
	private void alertLync(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setTitle(R.string.lync_title);
		builder.setMessage(R.string.lync_message);
		builder.setPositiveButton(R.string.download,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						downLoadLync();
						sendLyncLogs("download");
						dialog.dismiss();
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
	* @Title: downLoadLync 
	* @Description: 下载Lync客户端
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 下午2:51:14
	*/
	private void downLoadLync(){
		Uri uri = Uri.parse(AppUrl.URL_API_HOST_ANDROID_LYNC_DOWNLOAD);  
		Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);  
		startActivity(downloadIntent); 
	}
	
	/** 
	* @Title: sendLyncLogs 
	* @Description: 发送LYNC日志
	* @param @param typeName     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月3日 上午11:50:53
	*/
	private void sendLyncLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_lync));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(MainActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fNotice =loginSp.getBoolean(AppContext.FNOTICE_ALARM_KEY, false);		
		//说明是通知栏过来的
		if(fNotice){
			loginSp.edit().remove(AppContext.FNOTICE_ALARM_KEY).commit();
			String fItemNumber=loginSp.getString(AppContext.USER_NAME_KEY, "");
			if(StringUtils.isEmpty(fItemNumber))
				return;
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, TaskListActivity.class);
			intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
			intent.putExtra(AppContext.FSTATUS_KEY, "0");
			startActivity(intent);
		}	
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getTaskCount();  	//实时获取待办任务数量
	}

	/** 
	* @Title: getTaskCount 
	* @Description: 实时获取登陆人待办任务数量
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月30日 下午12:18:33
	*/
	private void getTaskCount(){
		new taskCountAsync().execute();
	}
	
	/**
	 * @ClassName taskCountAsync
	 * @Description 异步获取待办任务数量
	 * @author 21291
	 * @date 2014年7月30日 下午2:08:00
	 */
	private class taskCountAsync extends AsyncTask<Void , Void, String > {
		
	   @Override
	   protected String doInBackground(Void... params) {
		   return getPostByTaskCount();
	   }

	   @Override
	   protected void onPostExecute(String result) {
		    //显示我的任务数图标
			if(!result.equals("0")){
				txtWaitTaskCount.setVisibility(View.VISIBLE);
				txtWaitTaskCount.setText(result);
			}else{
				txtWaitTaskCount.setVisibility(View.GONE);
			}
	   }
	}
	
	/** 
	* @Title: getPostByTaskCount 
	* @Description: 异步获取可审批任务数量
	* @param @param param
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午10:06:29
	*/
	private String getPostByTaskCount(){
	   noticeBussiness.setServiceUrl(AppUrl.URL_API_HOST_ANDROID_NOTICESERVICE);
	   return noticeBussiness.getTaskCount(fItemNumber);       
	}
	
	/** 
	* @Title: getAmapAddress 
	* @Description: 获取
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 下午3:58:32
	*/
	private void getAmapAddress(){
		new AmapAddressAsync().execute();
	}

	/**
	 * @ClassName AmapAddressAsync
	 * @Description 异步获取打卡中心地址
	 * @author 21291
	 * @date 2014年12月31日 下午4:13:02
	 */
	private class AmapAddressAsync extends AsyncTask<Void , Void, AdAmapListInfo > {
		
	   @Override
	   protected AdAmapListInfo doInBackground(Void... params) {
		   return getPostByAmap();
	   }

	   @Override
	   protected void onPostExecute(AdAmapListInfo result) {
		   if(!StringUtils.isEmpty(result.getFCacheKey())){
			   if(!aMapCacheKey.equals(result.getFCacheKey())){  //说明缓存修改过，需要修改
				   if(!StringUtils.isEmpty(result.getFAmapList())){  //存储打卡中心地址
						aMapSp.edit().putString(AppContext.AD_AMAP_ADDRESS_CACHE_KEY, result.getFCacheKey()).commit();
						aMapSp.edit().putString(AppContext.AD_AMAP_ADDRESS_KEY, result.getFAmapList()).commit();
					}
			   }
		   }
	   }
	}
	
	/** 
	* @Title: getPostByTaskCount 
	* @Description: 异步获取可审批任务数量
	* @param @param param
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午10:06:29
	*/
	private AdAmapListInfo getPostByAmap(){
	   aBusiness.setServiceUrl(AppUrl.URL_API_HOST_ANDROID_GETNEWAMAPLISTACTIVITY);
	   return aBusiness.getAmapList(aMapCacheKey);       
	}
	
	@Override
	protected void onDestroy() {		
		appContext.Logout();   //全部退出
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();	
	}

	//监听返回 是否退出
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag=true;
    	if(keyCode == KeyEvent.KEYCODE_BACK) { //返回键
    		if (menu.isMenuShowing()) {
    			menu.showContent();
    		} else {
    			UIHelper.Exit(MainActivity.this);  //退出应用		
    		}
    	}
    	else {
    		flag=super.onKeyDown(keyCode, event);
		}  	
    	return flag;
    }
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
