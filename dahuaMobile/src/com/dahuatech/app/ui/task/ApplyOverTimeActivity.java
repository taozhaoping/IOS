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
import com.dahuatech.app.bean.mytask.ApplyOverTimeTBodyInfo;
import com.dahuatech.app.bean.mytask.ApplyOverTimeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyOverTimeBusiness;
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
 * @ClassName ApplyOverTimeActivity
 * @Description 加班申请单据Activity
 * @author 21291
 * @date 2014年7月23日 下午3:20:10
 */
public class ApplyOverTimeActivity extends MenuActivity implements ITaskContext {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fOverTimeCount,fDaysOffCount;
	private Button appButton;	//审批按钮
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	private float dip; //像素  相差1.5倍
	private int fixedHeight;	//高度固定值
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//像素宽度信息
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applyovertime);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYOVERTIMEACTIVITY;
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
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applyovertime_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ApplyOverTimeActivity.this, ApplyOverTimeActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyOverTimeActivity.this, R.string.applyovertime_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyOverTimeActivity.this);
		ApplyOverTimeBusiness eBusiness= (ApplyOverTimeBusiness)factoryBusiness.getInstance("ApplyOverTimeBusiness",serviceUrl);
		return eBusiness.getApplyOverTimeTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ApplyOverTimeTHeaderInfo applyInfo=(ApplyOverTimeTHeaderInfo)base;
		if(!StringUtils.isEmpty(applyInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applyovertime_FBillNo);
			fBillNo.setText(applyInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applyovertime_FApplyName);
			fApplyName.setText(applyInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applyovertime_FApplyDate);
			fApplyDate.setText(applyInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFOverTimeCount())){
			fOverTimeCount=(TextView)findViewById(R.id.applyovertime_FOverTimeCount);
			fOverTimeCount.setText(applyInfo.getFOverTimeCount());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFDaysOffCount())){
			fDaysOffCount=(TextView)findViewById(R.id.applyovertime_FDaysOffCount);
			fDaysOffCount.setText(applyInfo.getFDaysOffCount());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFSubEntrys())){
			showSubEntrys(applyInfo.getFSubEntrys());
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
	* @date 2014年7月23日 下午4:26:16
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<ApplyOverTimeTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ApplyOverTimeTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ApplyOverTimeTBodyInfo item : tBodyInfos) {
					attachRelativeLayout(item,i);
					i++;
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
	* @param @param i 行号   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月30日 下午3:05:05
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final ApplyOverTimeTBodyInfo tBodyInfo,final int i){
		
		paddingLeft = 25; // margin in dips
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
        
        //外面包围的相对布局
        RelativeLayout reLayoutTopOut=new RelativeLayout(this); 
        //配置相对布局实例的高度和宽度
        RelativeLayout.LayoutParams reLPTopOut=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);	
        reLPTopOut.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        reLayoutTopOut.setLayoutParams(reLPTopOut);
    	reLayoutTopOut.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegraye5_bottom));
        
        //相对布局1
        RelativeLayout reLayoutTop=new RelativeLayout(this);   //创建一个子类底部相对布局实例
        //配置相对布局实例的高度和宽度
        RelativeLayout.LayoutParams reLPTop=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
        reLPTop.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
  		reLayoutTop.setLayoutParams(reLPTop);
  		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  		reLayoutTop.setBackgroundDrawable(getResources().getDrawable(R.drawable.linewhitee5_bottom));
  		
  		//加班日期
		TextView tv1 = new TextView(this);
		RelativeLayout.LayoutParams tvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tv1.setLayoutParams(tvParam1);
		tv1.setPadding(paddingLeft, 0, 0, 0);
		tv1.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		tv1.setText(tBodyInfo.getFDate());
		reLayoutTop.addView(tv1);
		
		//用于
		int tvTop3Id=getNewId();
		TextView tv2 = new TextView(this);
		RelativeLayout.LayoutParams tvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvParam2.addRule(RelativeLayout.LEFT_OF, tvTop3Id);
		tvParam2.setMargins(-50, 0, 50, 0);
		tv2.setLayoutParams(tvParam2);
		tv2.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		tv2.setText(R.string.applyovertime_Purpose);
		reLayoutTop.addView(tv2);
		
		//用途
		TextView tv3 = new TextView(this);
		RelativeLayout.LayoutParams tvParam3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam3.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		tvParam3.setMargins(-50, 0, 50, 0);
		tv3.setLayoutParams(tvParam3);
		tv3.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		tv3.setText(tBodyInfo.getFUse());
		tv3.setId(tvTop3Id);
		reLayoutTop.addView(tv3);
		reLayoutTopOut.addView(reLayoutTop);
		
		//行号
		TextView tvTopOut = new TextView(this);
		RelativeLayout.LayoutParams tvTopOutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopOutParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTopOut.setLayoutParams(tvTopOutParam);
		tvTopOut.setPadding(8, 0, 0, 0);
		tvTopOut.setTextColor(getResources().getColor(R.color.line_color));
		tvTopOut.setTextSize(14);
		tvTopOut.setText(String.valueOf(i)+":");
		
		reLayoutTopOut.addView(tvTopOut);
		fSubLinearLayout.addView(reLayoutTopOut);
		
		//相对布局2
		RelativeLayout reLayout1=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLP1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayout1.setLayoutParams(reLP1);
		reLayout1.setPadding(paddingLeft, 5, 10, 5);
		
		//上午
		TextView reTv1 = new TextView(this);
		RelativeLayout.LayoutParams reParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		reTv1.setLayoutParams(reParam1);
		reTv1.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		reTv1.setText(R.string.applyovertime_am);
		int reTv1Id=getNewId();
		reTv1.setId(reTv1Id);
		reLayout1.addView(reTv1);
		
		//上午值
		TextView reTv2 = new TextView(this);
		RelativeLayout.LayoutParams reParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		reParam2.addRule(RelativeLayout.RIGHT_OF, reTv1Id);
		reTv2.setLayoutParams(reParam2);
		reTv2.setPadding(10, 0, 0, 0);
		reTv2.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		reTv2.setText(tBodyInfo.getFStartTime());
		reLayout1.addView(reTv2);
		fSubLinearLayout.addView(reLayout1);
		
		//相对布局3
		RelativeLayout reLayout2=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLP2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayout2.setLayoutParams(reLP2);
		reLayout2.setPadding(paddingLeft, 5, 10, 5);
		
		//下午
		TextView reTv3 = new TextView(this);
		RelativeLayout.LayoutParams reParam3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		reTv3.setLayoutParams(reParam3);
		reTv3.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		reTv3.setText(R.string.applyovertime_pm);
		int reTv3Id=getNewId();
		reTv3.setId(reTv3Id);
		reLayout2.addView(reTv3);
		
		//下午值
		TextView reTv4 = new TextView(this);
		RelativeLayout.LayoutParams reParam4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		reParam4.addRule(RelativeLayout.RIGHT_OF, reTv3Id);
		reTv4.setLayoutParams(reParam4);
		reTv4.setPadding(10, 0, 0, 0);
		reTv4.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		reTv4.setText(tBodyInfo.getFEndTime());
		reLayout2.addView(reTv4);
		fSubLinearLayout.addView(reLayout2);
				
		//相对布局4
		RelativeLayout reLayoutBottom1=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom1.setLayoutParams(reLPBottom1);
		reLayoutBottom1.setPadding(paddingLeft, 5, 10, 5);
		
		//额外出勤
		TextView rb1Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1Tv1.setLayoutParams(rb1TvParam1);
		rb1Tv1.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		rb1Tv1.setText(R.string.applyovertime_attendance);
		int rb1Tv1Id=getNewId();
		rb1Tv1.setId(rb1Tv1Id);
		reLayoutBottom1.addView(rb1Tv1);
		
		//额外出勤值
		TextView rb1Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb1TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb1TvParam2.addRule(RelativeLayout.RIGHT_OF, rb1Tv1Id);
		rb1Tv2.setLayoutParams(rb1TvParam2);
		rb1Tv2.setPadding(10, 0, 50, 0);
		rb1Tv2.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitlered);
		rb1Tv2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.amount_hour,0);
		rb1Tv2.setText(tBodyInfo.getFAttendance());
		reLayoutBottom1.addView(rb1Tv2);
		fSubLinearLayout.addView(reLayoutBottom1);
		
		//相对布局5
		RelativeLayout reLayoutBottom2=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom2.setLayoutParams(reLPBottom2);
		reLayoutBottom2.setPadding(paddingLeft, 5, 10, 5);
		
		//当日考勤
		TextView rb2Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2Tv1.setLayoutParams(rb2TvParam1);
		rb2Tv1.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		rb2Tv1.setText(R.string.applyovertime_attenTime);
		int rb2Tv1Id=getNewId();
		rb2Tv1.setId(rb2Tv1Id);
		reLayoutBottom2.addView(rb2Tv1);
		
		//当日考勤值
		TextView rb2Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb2TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb2TvParam2.addRule(RelativeLayout.RIGHT_OF, rb2Tv1Id);
		rb2Tv2.setLayoutParams(rb2TvParam2);
		rb2Tv2.setPadding(10, 0, 0, 0);
		rb2Tv2.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		rb2Tv2.setText(tBodyInfo.getFAttenTime());
		reLayoutBottom2.addView(rb2Tv2);
		fSubLinearLayout.addView(reLayoutBottom2);
		
		//相对布局6 
		RelativeLayout reLayoutBottom3=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		RelativeLayout.LayoutParams reLPBottom3=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom3.setLayoutParams(reLPBottom3);
		reLayoutBottom3.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom3.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//加班原因
		TextView rb3Tv1 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3Tv1.setLayoutParams(rb3TvParam1);
		rb3Tv1.setPadding(paddingLeft, 0, 0, 0);
		rb3Tv1.setTextAppearance(ApplyOverTimeActivity.this,R.style.icons);
		rb3Tv1.setText(R.string.applyovertime_reason);
		int rb3Tv1Id=getNewId();
		rb3Tv1.setId(rb3Tv1Id);
		reLayoutBottom3.addView(rb3Tv1);
		
		//加班原因值
		TextView rb3Tv2 = new TextView(this);
		RelativeLayout.LayoutParams rb3TvParam2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		rb3TvParam2.addRule(RelativeLayout.RIGHT_OF, rb3Tv1Id);
		rb3Tv2.setLayoutParams(rb3TvParam2);
		rb3Tv2.setPadding(10, 0, 0, 0);
		rb3Tv2.setTextAppearance(ApplyOverTimeActivity.this,R.style.iconstitle);
		rb3Tv2.setText(tBodyInfo.getFReason());
		reLayoutBottom3.addView(rb3Tv2);
		fSubLinearLayout.addView(reLayoutBottom3);
	}
	
	/** 
	* @Title: getNewId 
	* @Description:  获取一个新的ID值
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年7月9日 下午4:27:38
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
	* @date 2014年7月25日 上午11:27:37
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
					Intent intent = new Intent(ApplyOverTimeActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applyovertime_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//显示已审批页面
					Intent intent = new Intent(ApplyOverTimeActivity.this, WorkFlowBeenActivity.class);
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
		commonMenu.setContext(ApplyOverTimeActivity.this);
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
