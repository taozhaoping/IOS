package com.dahuatech.app.ui.expense.taxi;

import java.util.Calendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.common.AmapConstants;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.CustomDialog;


/**
 * @ClassName ExpenseMainActivity
 * @Description 打车报销主页面
 * @author 21291
 * @date 2014年5月13日 下午2:08:31
 */
public class ExpenseTaxiMainActivity extends MenuActivity implements AMapLocationListener {
	private TextView mLocationMeter,mLocationAddress,mLocationStatus;
	private String locationMeter,locationAddress;
	private ImageButton imgbtnJurney;							  //乘车开关
	
	private String fItemNumber,startTime,endTime,startAddress,endAddress;
	private String amount="0";
	
	private SharedPreferences sp; 
	private Editor editor;
	private AppContext appContext;  							  //定义全局变量和首选项变量

	private boolean isLocation=false;							  //判断是否定位成功
	private boolean isJurney;								 	  //是否已经开启打车开关
	
	private static ExpenseTaxiMainActivity mInstance;
	public static ExpenseTaxiMainActivity getInstance() {
		return mInstance;
	}
	
	private LocationManagerProxy mLocationManagerProxy;          //高德地图定位
	private Random mRandom=new Random();						 //随机数
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);	
		mInstance = this;
		setContentView(R.layout.expense_taximain);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		appContext = (AppContext)this.getApplication(); //初始化全局变量
		if(!appContext.isNetworkConnected()){ 	//判断是否有网络连接
			UIHelper.ToastMessage(ExpenseTaxiMainActivity.this,getResources().getString(R.string.network_not_connected));
			return;
		}
		Bundle extras = getIntent().getExtras(); //获取传递信息
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		sp = getSharedPreferences(AppContext.SETTINGACTIVITY_CONFIG_FILE, MODE_PRIVATE);
		editor=sp.edit();
		locationMeter=locationAddress="";
		imgbtnJurney = (ImageButton) this.findViewById(R.id.imgbtnJurney);
		mLocationMeter=(TextView)findViewById(R.id.GPS_Meter);
		mLocationAddress=(TextView)findViewById(R.id.Addr_Desc);
		mLocationStatus=(TextView)findViewById(R.id.GPS_Status);
		mLocationStatus.setText(getResources().getString(R.string.expense_taxi_addr_querying));
	
		isJurney=sp.getBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, false);
		if(isJurney){ //判断是否已经设置打车按钮
			imgbtnJurney.setBackgroundResource(R.drawable.btn_on);
			startAddress=sp.getString(AppContext.IS_EXPENSE_ADDRESS_KEY, "");
			startTime=sp.getString(AppContext.IS_EXPENSE_TIME_KEY, "");
		}
		imgbtnJurney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//判断是否定位
				if (!isLocation && StringUtils.isEmpty(locationAddress)){
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_query_addr_failed));
					return;
				}
				if(imgbtnJurney.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.btn_off).getConstantState()))
				{
					//打开开关开始打车
					imgbtnJurney.setBackgroundResource(R.drawable.btn_on);
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_start_taxi));
					isJurney=true;
					
					//保存开始数据到数据库
					startAddress = locationAddress;
					startTime = DateFormat.format("yyyy-MM-dd",Calendar.getInstance().getTime()).toString();
					editor.putBoolean(AppContext.IS_EXPENSE_JURNEY_KEY, true);
					editor.putString(AppContext.IS_EXPENSE_ADDRESS_KEY, startAddress);
					editor.putString(AppContext.IS_EXPENSE_TIME_KEY, startTime);
					editor.commit();
				}
				else {
					//关闭开关结束打车
					imgbtnJurney.setBackgroundResource(R.drawable.btn_off);
					UIHelper.ToastMessage(ExpenseTaxiMainActivity.this, getResources().getString(R.string.expense_taxi_end_taxi));
					isJurney=false;
					new Handler().postDelayed(new Runnable() { // 延迟1秒执行
			            @Override
			            public void run() {
			            	showAlertDialog();	
			            }
			        },1000);	
				}	
			}
		});	
		initLocation();
		startRequestLocation();
		sendLogs("access");	//发送日志信息进行统计
	}
	
	/** 
	* @Title: initLocation 
	* @Description: 初始化定位信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 上午10:43:42
	*/
	private void initLocation(){
		// 初始化定位，采用混合定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(true);	
	}
	
	/** 
	* @Title: startRequestLocation 
	* @Description: 开始请求定位
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 上午10:46:07
	*/
	private void startRequestLocation(){
		if(mLocationManagerProxy!=null){
			//此方法为每隔固定时间会发起一次定位请求，每隔10秒重新请求定位，当距离超过15米时，再重新请求定位
			mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND, AmapConstants.FIXMETER, this);
		}
	}
	
	/** 
	* @Title: stopRequestLocation 
	* @Description: 停止定位
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 上午10:48:55
	*/
	private void stopRequestLocation(){
		if(mLocationManagerProxy!=null){	
			mLocationManagerProxy.removeUpdates(this); //移除定位请求	
			mLocationManagerProxy.destroy(); // 销毁定位
		}
		mLocationManagerProxy=null;
	}
	
	@Override
	public void onLocationChanged(Location location) {
	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息
			locationMeter=amapLocation.getLatitude() + "," + amapLocation.getLongitude();
			locationAddress=amapLocation.getAddress();
			isLocation=true;
		}
		initLocationView();
	}
	
	/** 
	* @Title: initLocationView 
	* @Description: 初始定位视图值
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 上午10:56:15
	*/
	private void initLocationView(){  
		mLocationMeter.setText(locationMeter);
		mLocationAddress.setText(getResources().getString(R.string.expense_taxi_addr_descs)+locationAddress);
		if(isLocation){
			mLocationStatus.setText(getResources().getString(R.string.expense_taxi_query_addr_success));
		}
		else{
			mLocationStatus.setText(getResources().getString(R.string.expense_taxi_addr_querying));
		}
	}
	
	/** 
	* @Title: showAlertDialog 
	* @Description: 显示提示框
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月24日 上午11:39:00
	*/
	public void showAlertDialog(){
		final CustomDialog.Builder builder = new CustomDialog.Builder(ExpenseTaxiMainActivity.this);
		builder.setTitle(getResources().getString(R.string.expense_taxi_amount));
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//设置你的操作事项
				if(!StringUtils.isEmpty(builder.getEditTextStr()))
					amount =builder.getEditTextStr();
				newOffBtnJurney();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton(R.string.cancle,new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				amount="0";
				editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY);
				editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY);
				editor.remove(AppContext.IS_EXPENSE_TIME_KEY);
				editor.commit();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/** 
	* @Title: newOffBtnJurney 
	* @Description: 打车记录跳转到流水填写页面
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月13日 下午5:24:27
	*/
	private void newOffBtnJurney(){
		endAddress = locationAddress;
		endTime = DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
		editor.remove(AppContext.IS_EXPENSE_JURNEY_KEY);
		editor.remove(AppContext.IS_EXPENSE_ADDRESS_KEY);
		editor.remove(AppContext.IS_EXPENSE_TIME_KEY);
		editor.commit();
		redirectToFlow(amount,startAddress,endAddress,startTime,endTime);
	}
    
    /** 
    * @Title: redirectToFlow 
    * @Description: 跳转到流水页面
    * @param @param amount
    * @param @param fStartAddress
    * @param @param fEndAddress
    * @param @param fStartTime
    * @param @param fEndTime     
    * @return void    
    * @throws 
    * @author 21291
    * @date 2014年10月13日 下午4:58:12
    */
    private void redirectToFlow(String amount,String fStartAddress,String fEndAddress,String fStartTime,String fEndTime){
    	UIHelper.redirectToExpenseFlow(ExpenseTaxiMainActivity.this,"Taxi",fItemNumber,"",amount,fStartAddress,fEndAddress,fStartTime,fEndTime);
    }
    
	/** 
	* @Title: alertExit 
	* @Description: 退出打车页面的提醒
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月15日 上午11:31:05
	*/
	@SuppressLint("InlinedApi")
	private void alertExit(){
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);
		AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseTaxiMainActivity.this);
		builder.setIcon(typedValue.resourceId);
		builder.setMessage(getResources().getString(R.string.expense_taxi_gps_exit));
		builder.setPositiveButton(R.string.sure,
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
					dialog.dismiss();
				}
			});
		
		builder.setNegativeButton(R.string.cancle,new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击打车报销时，发送日志记录到服务器
	* @param @param typeName 操作类型     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 下午2:45:02
	*/
	private void sendLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_taxi));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseTaxiMainActivity.this,logInfo);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if(isJurney){ //只有正在打车的时候，退出提醒
					alertExit();
				}
				else{
					commonMenu.toggle();
				}
				return true;				
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		//发送广播,判断是否网络状态发生改变
		Intent intent = new Intent("com.dahuatech.app.action.APPWIFI_CHANGE");  
		sendBroadcast(intent);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		stopRequestLocation();
		super.onPause();
	}

	@Override
	protected void onRestart() {
		initLocation();
		mLocationManagerProxy.removeUpdates(this);
		int randomTime=mRandom.nextInt(1000);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND+randomTime, AmapConstants.FIXMETER, this);
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		if(mInstance!=null){
			mInstance=null;
		}
		super.onDestroy();	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) { //返回键
			if(isJurney){ //只有正在打车的时候，退出提醒
				alertExit();
			}	
    	}
		return super.onKeyDown(keyCode, event);
	}
}
