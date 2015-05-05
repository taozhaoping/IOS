package com.dahuatech.app.ui.attendance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.attendance.AdCheckInfo;
import com.dahuatech.app.bean.attendance.AdCheckStatusInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.AmapConstants;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.AdAmapSpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AdCheckInActivity
 * @Description 签入签出设置
 * @author 21291
 * @date 2014年12月16日 下午2:11:39
 */
public class AdCheckInActivity extends MenuActivity implements LocationSource,AMapLocationListener {
	private static AdCheckInActivity mInstance;
	public static AdCheckInActivity getInstance(){
		return mInstance;
	}
	private static final String GEOFENCE_BROADCAST_ACTION = "geo_fence.broadcast_action";
	
	private AMap mAMap;										//地图显示类
	private LocationManagerProxy mLocationManagerProxy;		//定位实例
	private PendingIntent mpIntent;							//包装的发送意图
	private Circle mCircle;									//圆圈				
	private OnLocationChangedListener mListener;			//激活事件
	private Random mRandom=new Random();					//随机数
	private List<AdAmapInfo> adAmapList;					//打卡中心地点集合
	
	private String fAddress;								//定位地址
	private double fLatitude,fLongitude;					//定位经纬度
	private int fAttendId,fAttendStatus;					//考勤ID,考勤状态	
	private String fCheckInTime,fCheckOutTime;				//签入/签出时间
	
	private AttendanceBusiness aBusiness;					//业务逻辑类
	private AdCheckInfo aCheckInfo;							//上传实体类
	
	private String fItemNumber;  							//员工号
	private String serviceUrl,uploadUrl;  					//签入/签出地址,上传地址
	private ProgressDialog uploadDialog,amapDialog;     	//上传弹出框,地图加载图
	
	private Button checkInBtn,btnAdList;					//打卡按钮,明细界面
	private TextView checkText;								//签入/签出结果
	private boolean isMockLocation;							//模拟位置是否开启 默认不开启
	
	private SharedPreferences aMapSp;						//配置文件
	private AppContext appContext; 							//全局Context
	private int checkInCount=0;								//打卡次数， 默认为0
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.attendance_check_in);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_GETCHECKSTATUSACTIVITY;	//获取签入/签出地址
		uploadUrl=AppUrl.URL_API_HOST_ANDROID_UPLOADCHECKACTIVITY;		//设置上传地址
		aMapSp=getSharedPreferences(AppContext.ADCHECKINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_verifying));
		uploadDialog.setCancelable(false);
		
		amapDialog = new ProgressDialog(this);
		amapDialog.setMessage(getResources().getString(R.string.dialog_amaploading));
		amapDialog.setCancelable(false);
		
		initAdAmap();				//初始化打卡地点集合
		initView();					//初始化视图控件
		initCheckStatus();			//初始化考勤状态	
		initAMap();					//初始化AMap对象
		initMarker();				//初始化定位标志
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:37:30
	*/
	private void initView(){
		checkInBtn=(Button)findViewById(R.id.attendance_check_btn);
		checkInBtn.setEnabled(false);
		btnAdList=(Button)findViewById(R.id.attendance_check_list);
		checkText=(TextView)findViewById(R.id.attendance_check_text);
		setViewEvent();
		
		aCheckInfo=AdCheckInfo.getAdCheckInfo();
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(AdCheckInActivity.this);
		aBusiness= (AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness","");
	}
	
	/** 
	* @Title: initAdAmap 
	* @Description: 初始化打卡地点集合
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 上午11:33:03
	*/
	private void initAdAmap(){
		adAmapList=new ArrayList<AdAmapInfo>();
		String aMapAddress=aMapSp.getString(AppContext.AD_AMAP_ADDRESS_KEY, "");
		if(!StringUtils.isEmpty(aMapAddress)){  //不为空
			try {
				Type listType = new TypeToken<ArrayList<AdAmapInfo>>(){}.getType();
				Gson gson = GsonHelper.getInstance();
				JSONArray jsonArray= new JSONArray(aMapAddress);
				adAmapList=gson.fromJson(jsonArray.toString(), listType);
						
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	/** 
	* @Title: setViewEvent 
	* @Description: 设置视图控件事件处理方法
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 下午4:56:13
	*/
	private void setViewEvent(){
		//打卡签入/签出
		checkInBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isMockLocation){   //说明模拟位置没打开
					final AdAmapSpinnerDialog mSpinnerDialog=new AdAmapSpinnerDialog(AdCheckInActivity.this,adAmapList,new ISpinnerListener(){

						@Override
						public void rejectOk(int n, RejectNodeInfo reInfo) {}

						@Override
						public void cancelled() {}

						@Override
						public void dHWeekOk(int n, String itemText,DHWeekInfo dhWeekInfo) {}

						@Override
						public void adAmapOk(int n, AdAmapInfo adAmapInfo) {
							uploadDialog.show();
							checkInCount=0;
							float radius=Float.valueOf(adAmapInfo.getFRadius()); 
							verifyGeo(getLatLng(adAmapInfo.getFLatitude(),adAmapInfo.getFLongitude()),radius);
						}	
					});
					mSpinnerDialog.setTitle(getResources().getString(R.string.ad_check_title));
					mSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
					mSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
					mSpinnerDialog.show();	
				}
				else{
					UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_open_mock_location));
				}
			}
		});
		
		//明细
		btnAdList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showAttendanceList(AdCheckInActivity.this,fItemNumber);
			}
		});
	}
	
	/** 
	* @Title: getLatLng 
	* @Description: 获取经纬度坐标类
	* @param @param fLatitude 纬度坐标
	* @param @param fLongitude 经度坐标
	* @param @return     
	* @return LatLng    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 下午2:58:53
	*/
	private LatLng getLatLng(String fLatitude,String fLongitude){
		return new LatLng(StringUtils.toDouble(fLatitude, 0), StringUtils.toDouble(fLongitude, 0));
	}

	/** 
	* @Title: initCheckStatus 
	* @Description: 判断签入/签出状态
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月31日 上午11:50:37
	*/
	private void initCheckStatus(){
		new getCheckAsync().execute();
	}
	
	/**
	 * @ClassName getCheckAsync
	 * @Description 获取是否已经签入/签出
	 * @author 21291
	 * @date 2014年12月22日 上午11:44:40
	 */
	private class getCheckAsync extends AsyncTask<Void, Void, AdCheckStatusInfo>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}

		// 主要是完成耗时操作
		@Override
		protected AdCheckStatusInfo doInBackground(Void... params) {
			return getCheckByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(AdCheckStatusInfo result) {
			super.onPostExecute(result);
			renderCheckView(result);	
		}	
	}
	
	/** 
	* @Title: getCheckByPost 
	* @Description: 获取实体信息
	* @param @return     
	* @return AdCheckStatusInfo    
	* @throws 
	* @author 21291
	* @date 2014年12月30日 上午9:38:43
	*/
	private  AdCheckStatusInfo getCheckByPost(){
		aBusiness.setServiceUrl(serviceUrl);
		return aBusiness.getCheckStausData(fItemNumber);
	}
	
	/** 
	* @Title: renderCheckView 
	* @Description: 获取并设置签入/签出状态
	* @param @param aStatusInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月30日 上午9:39:18
	*/
	private void renderCheckView(AdCheckStatusInfo aStatusInfo){
		fAttendId=aStatusInfo.getFAttendId();
		fAttendStatus=aStatusInfo.getFStatus();
		fCheckInTime=aStatusInfo.getFCheckInTime();
		fCheckOutTime=aStatusInfo.getFCheckOutTime();
		setAttendStatus();
	}
	
	/** 
	* @Title: setAttendStatus 
	* @Description: 设置考勤状态
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月30日 下午2:08:39
	*/
	private void setAttendStatus(){
		switch (fAttendStatus) {
			case 0:	//代表未打卡 
				checkInBtn.setText(getResources().getString(R.string.attendance_check_in));
				checkText.setText("上班未签入,下班未签出");
				break;
				
			case 1: //代表上午未签入 下午已签出
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText("上班未签入,"+fCheckOutTime+"签出");
				break;
	
			case 2:	//代表打卡未签出
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText(fCheckInTime+"签入,下班未签出");
				break;

			case 3:	//代表已打卡已签出
				checkInBtn.setText(getResources().getString(R.string.attendance_check_out));
				checkText.setText(fCheckInTime+"签入,"+fCheckOutTime+"签出");
				break;
	
			default:
				break;
		}
	}
	
	/** 
	* @Title: initAmap 
	* @Description: 初始化AMap对象信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 下午2:32:18
	*/
	private void initAMap() {
		if (mAMap == null) {
			mAMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.attendance_check_map)).getMap();			
		}
		MyLocationStyle myLocationStyle = new MyLocationStyle();		//自定义系统定位小蓝点
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);						//设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180)); 	//设置圆形的填充颜色
		myLocationStyle.strokeWidth(0.1f);								//设置圆形的边框粗细
		mAMap.setMyLocationStyle(myLocationStyle);
		mAMap.setLocationSource(this);									//设置定位监听
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);			//设置默认定位按钮是否显示
		mAMap.setMyLocationEnabled(true);								//设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);   			//设置定位类型，只在第一次定位移动到地图中心	
	}
	
	/** 
	* @Title: initMarker 
	* @Description: 初始化标记对象
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 下午4:29:35
	*/
	private void initMarker() {
		if(adAmapList.size() > 0){  //说明有中心点
			MarkerOptions markOptions = new MarkerOptions(); //创建定位位置标志
			BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location_point1));
			markOptions.icon(bitmapDescriptor);
			markOptions.anchor(0.5f, 0.5f);
			
			for (AdAmapInfo item : adAmapList) {
				Marker marker=mAMap.addMarker(markOptions);
				marker.setPosition(getLatLng(item.getFLatitude(),item.getFLongitude()));
			}
		}
	}
	
	/** 
	* @Title: initBroadcast 
	* @Description: 初始化广播事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月17日 上午10:02:12
	*/
	private void initBroadcast(){
		IntentFilter fliter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(GEOFENCE_BROADCAST_ACTION);
		registerReceiver(mReceiver, fliter);
		Intent mIntent = new Intent(GEOFENCE_BROADCAST_ACTION);
		mpIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0,mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/** 
	* @Title: verifyGeo 
	* @Description: 验证打卡地点围栏并显示
	* @param @param latLng
	* @param @param radius 半径    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月5日 下午4:24:13
	*/
	private void verifyGeo(LatLng latLng,float radius){
		mLocationManagerProxy.removeGeoFenceAlert(mpIntent);  
		//设置地理围栏，位置、半径、超时时间、处理事件
		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,latLng.longitude, radius,AmapConstants.EXPIRATION_TIME, mpIntent);
		//创建一个圆,将地理围栏添加到地图上显示
		if (mCircle != null) {
			mCircle.remove();
		}
		CircleOptions circle = new CircleOptions();
		circle.center(latLng);								//设置中心点
		circle.radius(radius);				//设置半径
		circle.fillColor(Color.argb(180, 224, 171, 10));	//设置圆形的填充颜色
		circle.strokeColor(Color.RED);	 					//设置圆形的边框颜色
		mCircle = mAMap.addCircle(circle); 					//显示在地图
	}

	/** 
	* @Fields mReceiver : 打卡处理事件
	*/ 
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 接受广播
			if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
				Bundle bundle = intent.getExtras();
				// 根据广播的status来确定是在区域内还是在区域外
				int status = bundle.getInt("status");
				if (status == 1) {
					if(checkInCount==0){  //说明是首次接受到广播信息
						uploadServer();
					}
					else{
						uploadDialog.dismiss();
					}
				}
				else{
					uploadDialog.dismiss();
					UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_not_check_in));
				}
				checkInCount++;
			}
		}
	};
	
	/** 
	* @Title: uploadServer 
	* @Description: 上传服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月19日 上午10:04:06
	*/
	private void uploadServer(){
		setModel();
		new uploadAsync().execute(aCheckInfo);
	}
	
	/** 
	* @Title: setModel 
	* @Description: 设置打卡上传实体值
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:49:31
	*/
	private void setModel(){
		SharedPreferences sp=getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		aCheckInfo.setFAttendId(fAttendId);
		aCheckInfo.setFAttendStatus(fAttendStatus);
		aCheckInfo.setFItemNumber(fItemNumber);
		aCheckInfo.setFItemName(sp.getString(AppContext.FITEMNAME_KEY, ""));
		aCheckInfo.setFLatitude(String.valueOf(fLatitude));
		aCheckInfo.setFLongitude(String.valueOf(fLongitude));
		aCheckInfo.setFAddress(fAddress); 
	}
	
	/**
	 * @ClassName uploadDetailAsync
	 * @Description 上传数据到服务器
	 * @author 21291
	 * @date 2014年10月30日 下午1:58:01
	 */
	private class uploadAsync extends AsyncTask<AdCheckInfo,Void,ResultMessage>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		// 主要是完成耗时操作
		@Override
		protected ResultMessage doInBackground(AdCheckInfo... params) {
			return upload(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);	
			showResult(result);	
			uploadDialog.dismiss();
		}	
	}
	
	/** 
	* @Title: upload 
	* @Description: 上传动作
	* @param @param adCheckInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年12月19日 上午10:36:58
	*/
	private ResultMessage upload(AdCheckInfo adCheckInfo){
		aBusiness.setServiceUrl(uploadUrl);
		return aBusiness.checkHandle(adCheckInfo);
	}
	
	/** 
	* @Title: showResult 
	* @Description: 更新上传结果
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月19日 上午10:37:49
	*/
	private void showResult(ResultMessage result){
		if(result.isIsSuccess()){  //说明上传成功
			UIHelper.ToastMessage(AdCheckInActivity.this, getResources().getString(R.string.attendance_upload_success));
			Gson gson=GsonHelper.getInstance();
			AdCheckStatusInfo adCheckStatusInfo = gson.fromJson(result.getResult(), AdCheckStatusInfo.class);
			fAttendId=adCheckStatusInfo.getFAttendId();
			fAttendStatus=adCheckStatusInfo.getFStatus();
			fCheckInTime=adCheckStatusInfo.getFCheckInTime();
			fCheckOutTime=adCheckStatusInfo.getFCheckOutTime();
			setAttendStatus();
			sendLogs();
		}
		else{
			UIHelper.ToastMessage(AdCheckInActivity.this,getResources().getString(R.string.attendance_upload_failure));
		}
	}
	
	//激活定位
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		amapDialog.show();
		startLocation();
	}

	@Override
	public void deactivate() {
		mListener = null;
		destroyLocation();
	}
	
	/** 
	* @Title: initLocation 
	* @Description: 初始化定位实例
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午12:22:49
	*/
	private void initLocation(){
		if(mLocationManagerProxy==null){
			mLocationManagerProxy = LocationManagerProxy.getInstance(AdCheckInActivity.this);     //创建定位实例
			mLocationManagerProxy.setGpsEnable(true);	
		}
	}
	
	/** 
	* @Title: startLocation 
	* @Description: 开始请求定位
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 上午10:46:07
	*/
	private void startLocation(){
		initLocation();
		//此方法为每隔固定时间会发起一次定位请求，每隔5秒重新请求定位，当距离超过15米时，再重新请求定位
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND, AmapConstants.FIXMETER, this);
	}
	
	/** 
	* @Title: destroyLocation 
	* @Description: 销毁请求定位
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月16日 下午4:44:24
	*/
	private void destroyLocation(){
		if(mLocationManagerProxy!=null){ // 销毁定位
			mLocationManagerProxy.removeGeoFenceAlert(mpIntent);
			mLocationManagerProxy.removeUpdates(this);
			mLocationManagerProxy.destroy();
			mLocationManagerProxy=null;
		}
		if(mReceiver!=null){
			unregisterReceiver(mReceiver);  
		}
		
		if(amapDialog.isShowing()){
			amapDialog.dismiss();
		}
	} 
	
	//定位接口实现

	/**
	 * 此方法已经废弃
	 */
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
		if (mListener != null && amapLocation!=null && amapLocation.getAMapException().getErrorCode() == 0) {  //说明定位成功 
			amapDialog.dismiss();
			checkInBtn.setEnabled(true);
			mListener.onLocationChanged(amapLocation);//显示系统小蓝点
			fLatitude=amapLocation.getLatitude();
			fLongitude=amapLocation.getLongitude();
			fAddress=amapLocation.getAddress();
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击我的考勤时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午3:34:49
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_attendance));
		logInfo.setFActionName("check");
		logInfo.setFNote("note");
		UIHelper.sendLogs(AdCheckInActivity.this,logInfo);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		initLocation();
		mLocationManagerProxy.removeUpdates(this);
		int randomTime=mRandom.nextInt(1000);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, AmapConstants.INTERVALFIVESECOND+randomTime, AmapConstants.FIXMETER, this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		commonMenu.setContext(AdCheckInActivity.this);
		commonMenu.setMarginTouchMode();
		isMockLocation=Settings.Secure.getInt(this.getContentResolver(),Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
	}
	
	//地图方法重载
	@Override
	protected void onResume() {
		super.onResume();
		initBroadcast();
	}

	@Override
	protected void onPause() {
		super.onPause();
		destroyLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
