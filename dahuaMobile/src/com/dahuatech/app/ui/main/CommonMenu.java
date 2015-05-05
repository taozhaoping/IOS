package com.dahuatech.app.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppManager;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.attendance.AdCheckInActivity;
import com.dahuatech.app.ui.contacts.ContactsMainActivity;
import com.dahuatech.app.ui.develop.hour.DHMainActivity;
import com.dahuatech.app.ui.expense.main.ExpenseMainActivity;
import com.dahuatech.app.ui.expense.taxi.ExpenseTaxiMainActivity;
import com.dahuatech.app.ui.market.MarketMainActivity;
import com.dahuatech.app.ui.meeting.MeetingListActivity;
import com.dahuatech.app.ui.task.TaskListActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

/**
 * @ClassName CommonMenu
 * @Description 父类菜单共同类
 * @author 21291
 * @date 2014年10月16日 下午5:24:24
 */
public class CommonMenu { 

	private String sideMenuType;			//侧边栏菜单类型  "main"-主页类型,"other"-不是主页类型
	private SharedPreferences sp;  			//首选项文件
	private SlidingMenu menu; 				//侧边导航菜单类
	private Context context;  				//上下文环境
	private int leftDimen;					//左边默认显示宽度
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/** 
	* @Name: CommonMenu 
	* @Description: 内部类单例模式 
	*/
	private CommonMenu(){}
	private static class SingletonHolder {  
        private static CommonMenu instance = new CommonMenu();  
    }  
	public static CommonMenu getCommonMenu(Context context,SharedPreferences sp,SlidingMenu menu,String sideMenuType,int leftDimen) {
		SingletonHolder.instance.context=context;
		SingletonHolder.instance.sp=sp;
		SingletonHolder.instance.menu=menu;
		SingletonHolder.instance.sideMenuType=sideMenuType;
		SingletonHolder.instance.leftDimen=leftDimen;
		return SingletonHolder.instance;
	}
	
	/** 
	* @Title: initSlidingMenu 
	* @Description: 初始化侧边导航菜单类
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月16日 下午4:45:07
	*/
	public void initSlidingMenu(){	
		menu.setMenu(R.layout.drawer_left_menu);				  		//加载左边菜单视图
		menu.setShadowDrawable(R.drawable.shadow); 	   					//设置左边视图阴影
		
		if("other".equals(sideMenuType)){  //说明是其他侧边栏类型
			menu.setSecondaryMenu(R.layout.drawer_right_menu);			//加载右边菜单视图
			menu.setSecondaryShadowDrawable(R.drawable.shadowright);	//设置右边视图阴影
			menu.setMode(SlidingMenu.LEFT_RIGHT);						//左右边滑动
		}
		else{
			menu.setMode(SlidingMenu.LEFT);								//左边滑动
		}
		
		//设置SlidingMenu 的手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在整个content页面中，滑动，可以打开SlidingMenu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开SlidingMenu,你需要在屏幕边缘滑动才可以打开SlidingMenu
        //TOUCHMODE_NONE 不能通过手势打开SlidingMenu
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);  					//设置阴影图片的宽度  
		menu.setFadeEnabled(true);										//设置滑动时菜单的是否渐变
		menu.setFadeDegree(0.35f);										//设置滑动时的渐变程度 
		menu.setBehindScrollScale(0.0f);								//设置缩放比例
		menu.setBehindWidth(leftDimen);									//默认侧边栏宽度
		
		menu.setBehindCanvasTransformer(new CanvasTransformer(){    	//定义拉伸动画

			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(percentOpen, 1, 0, 0);
			}
		});
		menu.attachToActivity((Activity)context, SlidingMenu.SLIDING_WINDOW);  	//占全屏
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
	public int getPixelsWidth(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: setMarginTouchMode 
	* @Description: 设置边缘手势模式
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月19日 下午2:25:44
	*/
	public void setMarginTouchMode(){
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
	/** 
	* @Title: initLeftButton 
	* @Description: 初始化左边侧边栏菜单控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月16日 下午4:50:18
	*/
	@SuppressLint("InflateParams")
	public void initLeftButton(){
		final Button btnSetting=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_setting);
		final Button btnBack=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_back);
		final Button btnInvite=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_invite);
		final Button btnExit=(Button)((Activity)context).findViewById(R.id.drawer_left_menu_exit);
		
		//系统设置
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if(SettingActivity.getInstance()==null){  //不是从设置页点击
					UIHelper.showSetting(context);
					if("other".equals(sideMenuType)){  //说明是其他侧边栏类型
						((Activity)context).finish();
					}
				}
			}
		});
				
		//回到首页
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("other".equals(sideMenuType)){  //说明是其他侧边栏类型
					Intent intent = new Intent();
					intent.setClass(context, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					((Activity)context).startActivity(intent);
					((Activity)context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
					((Activity)context).finish();
				}
			}
		});
				
		//邀请好友
		btnInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(SmsNoticeActivity.getInstance()==null){  //不是从短信邀请页点击
					UIHelper.showSmsInvite(context);
					if("other".equals(sideMenuType)){  //说明是其他侧边栏类型
						((Activity)context).finish();
					}
				}
			}
		});
				
		//退出
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appExit();
			}
		});			
	}
	
	/** 
	* @Title: initRightButton 
	* @Description: 初始化右边侧边栏菜单控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月1日 上午10:55:46
	*/
	public void initRightButton(){
		final String fItemNumber= sp.getString(AppContext.USER_NAME_KEY, "");
		final Button btnMyTask=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_myTask);
		final Button btnExpense=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_expense);
		final Button btnContacts=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_contacts);
		final Button btnMeeting=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_meeting);
		final Button btnDh=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_develop_hour);
		final Button btnAttendance=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_attendance);
		final Button btnMarket=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_market);
		final Button btnRich=(Button)((Activity)context).findViewById(R.id.drawer_right_menu_richscan);
	
		//我的任务
		btnMyTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=TaskListActivity.getInstance())
				{
					if(TaskListActivity.getInstance()!=null){
						TaskListActivity.getInstance().finish();
					}
					UIHelper.showMyTask(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//我的报销
		btnExpense.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=ExpenseMainActivity.getInstance())
				{
					if(ExpenseMainActivity.getInstance()!=null){
						ExpenseMainActivity.getInstance().finish();
					}
					UIHelper.showExpenseMain(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//通讯录
		btnContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=ContactsMainActivity.getInstance())
				{
					if(ContactsMainActivity.getInstance()!=null){
						ContactsMainActivity.getInstance().finish();
					}	
					UIHelper.showContacts(context,fItemNumber,"main_search");
					((Activity)context).finish();
				}
			}
		});
		
		//我的会议
		btnMeeting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=MeetingListActivity.getInstance())
				{
					if(MeetingListActivity.getInstance()!=null){
						MeetingListActivity.getInstance().finish();
					}	
					UIHelper.showMeetingList(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//研发工时
		btnDh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=DHMainActivity.getInstance())
				{
					if(DHMainActivity.getInstance()!=null){
						DHMainActivity.getInstance().finish();
					}	
					UIHelper.showDH(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//我的考勤
		btnAttendance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=AdCheckInActivity.getInstance())
				{
					if(AdCheckInActivity.getInstance()!=null){
						AdCheckInActivity.getInstance().finish();
					}	
					UIHelper.showAttendanceCheck(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//我的销售
		btnMarket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				if((Activity)context!=MarketMainActivity.getInstance())
				{
					if(MarketMainActivity.getInstance()!=null){
						MarketMainActivity.getInstance().finish();
					}	
					UIHelper.showMarketMain(context,fItemNumber);
					((Activity)context).finish();
				}
			}
		});
		
		//扫一扫
		btnRich.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				UIHelper.showCapture(context);
				((Activity)context).finish();
			}
		});
	}
	
	/** 
	* @Title: toggle 
	* @Description: 侧边菜单栏和内容切换
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月14日 下午5:33:24
	*/
	public void toggle() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			NavUtils.navigateUpFromSameTask(((Activity)context));
			((Activity)context).finish();
		}
	}
	
	/** 
	* @Title: appExit 
	* @Description: 退出
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月16日 下午3:49:14
	*/
	public void appExit(){
		SharedPreferences settingSp = ((Activity)context).getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, Context.MODE_PRIVATE);
		//判断已经设置打车按钮
		if(settingSp.getBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, false)){
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle(R.string.expense_taxi_exit);
			builder.setMessage(R.string.expense_taxi_gps_exit);
			builder.setPositiveButton(R.string.sure,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();					
							exit();
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
		else {
			exit();
		}
	}
	
	/** 
	* @Title: exit 
	* @Description: 退出
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月20日 上午8:54:44
	*/
	private void exit(){
		AppContext appContext=(AppContext)((Activity)context).getApplication();
		appContext.Logout();
		if(ExpenseTaxiMainActivity.getInstance()!=null){
			ExpenseTaxiMainActivity.getInstance().finish();
		}
		((Activity)context).finish();
		AppManager.getAppManager().AppExit(context);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
