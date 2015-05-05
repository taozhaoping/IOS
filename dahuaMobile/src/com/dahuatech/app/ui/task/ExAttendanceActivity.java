package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ExAttendanceTBodyInfo;
import com.dahuatech.app.bean.mytask.ExAttendanceTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExAttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.ViewIdGenerator;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExAttendanceActivity
 * @Description 异常考勤调整申请单据Activity
 * @author 21291
 * @date 2014年7月23日 下午3:20:37
 */
public class ExAttendanceActivity extends MenuActivity implements ITaskContext{
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept;
	private Button appButton;		//审批按钮
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	private float dip; //像素  相差1.5倍
	private int fixedHeight=30;	//高度固定值
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//像素宽度信息
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exattendance);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化全局变量
		appContext = (AppContext)getApplication();
		//网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXATTENDANCEACTIVITY;		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}

		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density;  
		fSubLinearLayout=(LinearLayout)findViewById(R.id.exattendance_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExAttendanceActivity.this, ExAttendanceActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExAttendanceActivity.this, R.string.exattendance_netparseerror);
			return;
		}
	}
	
	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// 发送这个消息到消息队列中
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExAttendanceActivity.this);
		ExAttendanceBusiness eBusiness= (ExAttendanceBusiness)factoryBusiness.getInstance("ExAttendanceBusiness",serviceUrl);
		return eBusiness.getExAttendanceTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ExAttendanceTHeaderInfo exInfo=(ExAttendanceTHeaderInfo)base;
		if(!StringUtils.isEmpty(exInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.exattendance_FBillNo);
			fBillNo.setText(exInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(exInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.exattendance_FApplyName);
			fApplyName.setText(exInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(exInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.exattendance_FApplyDate);
			fApplyDate.setText(exInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(exInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.exattendance_FApplyDept);
			fApplyDept.setText(exInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(exInfo.getFSubEntrys())){
			showSubEntrys(exInfo.getFSubEntrys());
		}
		initApprove();
	}
	
	/** 
	* @Title: showSubEntrys 
	* @Description: 构造子类集合
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 上午10:21:57
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			Type listType = new TypeToken<ArrayList<ExAttendanceTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ExAttendanceTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ExAttendanceTBodyInfo item : tBodyInfos) {
					attachRelativeLayout(item);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: 动态拼接 显示子类视图，可能有多个
	* @param @param tBodyInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 上午10:22:59
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final ExAttendanceTBodyInfo tBodyInfo){
	    paddingLeft = 20; // margin in dips
		paddingRight = 20; 
		paddingTop = 5;
		paddingBottom = 5; 
		fixedHeight=33;	//高度固定值
	    width = getPixelsWidth(); 
		layoutHeight=RelativeLayout.LayoutParams.WRAP_CONTENT;
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
	        layoutHeight=(int)(fixedHeight * dip);
        }
        
        //相对布局头部
        RelativeLayout reLayoutTop=new RelativeLayout(this);   //创建一个子类底部相对布局实例
        //配置相对布局实例的高度和宽度
        RelativeLayout.LayoutParams reLPTop=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
        reLPTop.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
  		reLayoutTop.setLayoutParams(reLPTop);
  		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  		reLayoutTop.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegraye5_bottom));
  		
  		//加班日期
		TextView tv1 = new TextView(this);
		RelativeLayout.LayoutParams tvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tv1.setLayoutParams(tvParam1);
		tv1.setPadding(paddingLeft, 0, 0, 0);
		tv1.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		tv1.setText(tBodyInfo.getFDate());
		reLayoutTop.addView(tv1);
		fSubLinearLayout.addView(reLayoutTop);
		
		//相对布局底部1
		RelativeLayout reLayoutBottom1=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLPBottom1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		reLayoutBottom1.setLayoutParams(reLPBottom1);
		reLayoutBottom1.setPadding(paddingLeft, 5, 10, 5);
		
		//上午
		TextView rb1Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1Tv1.setLayoutParams(rb1TvParam1);
		rb1Tv1.setTextAppearance(ExAttendanceActivity.this,R.style.icons);
		rb1Tv1.setText(R.string.exattendance_am);
		int rb1Tv1Id=getNewId();
		rb1Tv1.setId(rb1Tv1Id);
		reLayoutBottom1.addView(rb1Tv1);
		
		//上午值
		TextView rb1Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1TvParam2.addRule(RelativeLayout.RIGHT_OF, rb1Tv1Id);
		rb1Tv2.setLayoutParams(rb1TvParam2);
		rb1Tv2.setPadding(10, 0, 0, 0);
		rb1Tv2.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		rb1Tv2.setText(tBodyInfo.getFStartTime());
		reLayoutBottom1.addView(rb1Tv2);
		
		//上午结果值
		TextView rb1Tv3 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1TvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rb1TvParam3.setMargins(0, 0, 20, 0);
		rb1Tv3.setLayoutParams(rb1TvParam3);
		rb1Tv3.setPadding(paddingLeft, 0, 0, 0);
		rb1Tv3.setTextAppearance(ExAttendanceActivity.this,R.style.iconsred);
		rb1Tv3.setText(tBodyInfo.getFOldStartResult());
		reLayoutBottom1.addView(rb1Tv3);
		fSubLinearLayout.addView(reLayoutBottom1);
		
		//相对布局底部2
		RelativeLayout reLayoutBottom2=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLPBottom2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		reLayoutBottom2.setLayoutParams(reLPBottom2);
		reLayoutBottom2.setPadding(paddingLeft, 5, 10, 5);
		
		//上午改签
		TextView rb2Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2Tv1.setLayoutParams(rb2TvParam1);
		rb2Tv1.setTextAppearance(ExAttendanceActivity.this,R.style.icons);
		rb2Tv1.setText(R.string.exattendance_change);
		int rb2Tv1Id=getNewId();
		rb2Tv1.setId(rb2Tv1Id);
		reLayoutBottom2.addView(rb2Tv1);
		
		//上午改签后值
		TextView rb2Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2TvParam2.addRule(RelativeLayout.RIGHT_OF, rb2Tv1Id);
		rb2Tv2.setLayoutParams(rb2TvParam2);
		rb2Tv2.setPadding(10, 0, 0, 0);
		rb2Tv2.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		rb2Tv2.setText(tBodyInfo.getFChangeStartTime());
		reLayoutBottom2.addView(rb2Tv2);
		fSubLinearLayout.addView(reLayoutBottom2);
		
		//相对布局底部3
		RelativeLayout reLayoutBottom3=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom3=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLPBottom3.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		reLayoutBottom3.setLayoutParams(reLPBottom3);
		reLayoutBottom3.setPadding(paddingLeft, 5, 10, 5);
		
		//下午
		TextView rb3Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3Tv1.setLayoutParams(rb3TvParam1);
		rb3Tv1.setTextAppearance(ExAttendanceActivity.this,R.style.icons);
		rb3Tv1.setText(R.string.exattendance_pm);
		int rb3Tv1Id=getNewId();
		rb3Tv1.setId(rb3Tv1Id);
		reLayoutBottom3.addView(rb3Tv1);
		
		//下午值
		TextView rb3Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3TvParam2.addRule(RelativeLayout.RIGHT_OF, rb3Tv1Id);
		rb3Tv2.setLayoutParams(rb3TvParam2);
		rb3Tv2.setPadding(10, 0, 0, 0);
		rb3Tv2.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		rb3Tv2.setText(tBodyInfo.getFEndTime());
		reLayoutBottom3.addView(rb3Tv2);
		
		//下午结果值
		TextView rb3Tv3 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3TvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rb3TvParam3.setMargins(0, 0, 20, 0);
		rb3Tv3.setLayoutParams(rb3TvParam3);
		rb3Tv3.setPadding(10, 0, 0, 0);
		rb3Tv3.setTextAppearance(ExAttendanceActivity.this,R.style.iconsred);
		rb3Tv3.setText(tBodyInfo.getFOldEndResult());
		reLayoutBottom3.addView(rb3Tv3);
		fSubLinearLayout.addView(reLayoutBottom3);
		
		//相对布局底部4
		RelativeLayout reLayoutBottom4=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom4=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLPBottom4.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		reLayoutBottom4.setLayoutParams(reLPBottom4);
		reLayoutBottom4.setPadding(paddingLeft, 5, 10, 5);
		
		//下午改签
		TextView rb4Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb4TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb4Tv1.setLayoutParams(rb4TvParam1);
		rb4Tv1.setTextAppearance(ExAttendanceActivity.this,R.style.icons);
		rb4Tv1.setText(R.string.exattendance_change);
		int rb4Tv1Id=getNewId();
		rb4Tv1.setId(rb4Tv1Id);
		reLayoutBottom4.addView(rb4Tv1);
		
		//下午改签后值
		TextView rb4Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb4TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb4TvParam2.addRule(RelativeLayout.RIGHT_OF, rb4Tv1Id);
		rb4Tv2.setLayoutParams(rb4TvParam2);
		rb4Tv2.setPadding(10, 0, 0, 0);
		rb4Tv2.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		rb4Tv2.setText(tBodyInfo.getFChangeEndTime());
		reLayoutBottom4.addView(rb4Tv2);
		fSubLinearLayout.addView(reLayoutBottom4);
		
		//相对布局底部5
		RelativeLayout reLayoutBottom5=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom5=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLPBottom5.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		reLayoutBottom5.setLayoutParams(reLPBottom5);
		reLayoutBottom5.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom5.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//调整原因
		TextView rb5Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb5TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb5Tv1.setLayoutParams(rb5TvParam1);
		rb5Tv1.setPadding(paddingLeft, 0, 0, 0);
		rb5Tv1.setTextAppearance(ExAttendanceActivity.this,R.style.icons);
		rb5Tv1.setText(R.string.exattendance_reason);
		int rb5Tv1Id=getNewId();
		rb5Tv1.setId(rb5Tv1Id);
		reLayoutBottom5.addView(rb5Tv1);
		
		//调整原因值
		TextView rb5Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb5TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb5TvParam2.addRule(RelativeLayout.RIGHT_OF, rb5Tv1Id);
		rb5Tv2.setLayoutParams(rb5TvParam2);
		rb5Tv2.setPadding(10, 0, 0, 0);
		rb5Tv2.setTextAppearance(ExAttendanceActivity.this,R.style.iconstitle);
		rb5Tv2.setText(tBodyInfo.getFReason());
		reLayoutBottom5.addView(rb5Tv2);
		fSubLinearLayout.addView(reLayoutBottom5);
	}
	
	/** 
	* @Title: getNewId 
	* @Description: 获取一个新的ID值
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 上午10:23:21
	*/
	private int getNewId(){
		int fId;
		do {
			fId = ViewIdGenerator.generateViewId();
		} while (findViewById(fId) != null);
		return fId;
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: 获取屏幕像素宽度
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 上午11:22:20
	*/
	private int getPixelsWidth(){
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: initApprove 
	* @Description: 加载审批按钮视图
	* @param    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月13日 下午5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		
		handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
			
		handleLayout.setVisibility(View.GONE);
		if("1".equals(fStatus)){  //说明是已审批进来
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		
		appButton.setOnClickListener(new OnClickListener() {
			@Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(ExAttendanceActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.exattendance_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(ExAttendanceActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
		 	}
		 });
		
		 fSubLinearLayout.addView(buttonLayout);
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.ACTIVITY_WORKFLOW:
				if(resultCode==RESULT_OK){
					fStatus="1";  //从待审批页面过来 说明是审批或驳回通过
					SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);					
					sp.edit().putString(AppContext.TA_APPROVE_FSTATUS, fStatus).commit();
					appButton.setText(R.string.approve_imgbtnApprove_record);
				}			
				break;
			case AppContext.ACTIVITY_WORKFLOWBEEN:
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExAttendanceActivity.this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
