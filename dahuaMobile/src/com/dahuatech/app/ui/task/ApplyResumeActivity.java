package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ApplyResumeTBodyInfo;
import com.dahuatech.app.bean.mytask.ApplyResumeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyResumeBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ApplyLeaveActivity
 * @Description 请假申请单据
 * @author 21291
 * @date 2015年1月12日 上午9:44:33
 */
public class ApplyResumeActivity extends MenuActivity implements ITaskContext {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept;
	private Button appButton;	//审批按钮
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_resume_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYRESUMEACTIVITY;
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
				
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applyresumetheader_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ApplyResumeActivity.this, ApplyResumeActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyResumeActivity.this, R.string.applyresume_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyResumeActivity.this);
		ApplyResumeBusiness eBusiness= (ApplyResumeBusiness)factoryBusiness.getInstance("ApplyResumeBusiness",serviceUrl);
		return eBusiness.getApplyResumeTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ApplyResumeTHeaderInfo applyInfo=(ApplyResumeTHeaderInfo)base;
		if(!StringUtils.isEmpty(applyInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applyresumetheader_FBillNo);
			fBillNo.setText(applyInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applyresumetheader_FApplyName);
			fApplyName.setText(applyInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applyresumetheader_FApplyDate);
			fApplyDate.setText(applyInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(applyInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.applyresumetheader_FApplyDept);
			fApplyDept.setText(applyInfo.getFApplyDept());
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
	* @date 2015年1月12日 上午9:52:37
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<ApplyResumeTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<ApplyResumeTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (ApplyResumeTBodyInfo item : tBodyInfos) {
					attachSubEntry(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachSubEntry 
	* @Description: 动态附加子类视图
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月12日 上午9:54:18
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final ApplyResumeTBodyInfo tBodyInfo,final int i){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.apply_resume_tbody, null);
	
		TextView fLine=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FLine);
		fLine.setText(String.valueOf(i));
 
		TextView fStartDate=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FStartDate);
		fStartDate.setText(tBodyInfo.getFStartDate());
 
		TextView fAmCheckTime=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FAmCheckTime);
		fAmCheckTime.setText(tBodyInfo.getFAmCheckTime());
 
		TextView fAmCheckResult=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FAmCheckResult);
		fAmCheckResult.setText(tBodyInfo.getFAmCheckResult());
 
		TextView fAmResume=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FAmResume);
		fAmResume.setText(tBodyInfo.getFAmResume());
		
		TextView fPmCheckTime=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FPmCheckTime);
		fPmCheckTime.setText(tBodyInfo.getFPmCheckTime());
	
		TextView fPmCheckResult=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FPmCheckResult);
		fPmCheckResult.setText(tBodyInfo.getFPmCheckResult());
		
		TextView fPmResume=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FPmResume);
		fPmResume.setText(tBodyInfo.getFPmResume());
		
		TextView fReason=(TextView)linearLayout.findViewById(R.id.apply_resume_tbody_FReason);
		fReason.setText(tBodyInfo.getFReason());
		
		fSubLinearLayout.addView(linearLayout);
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
					Intent intent = new Intent(ApplyResumeActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applyresume_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//显示已审批页面
					Intent intent = new Intent(ApplyResumeActivity.this, WorkFlowBeenActivity.class);
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
		commonMenu.setContext(ApplyResumeActivity.this);
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
