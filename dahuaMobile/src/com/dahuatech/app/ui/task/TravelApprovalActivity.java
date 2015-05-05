package com.dahuatech.app.ui.task;

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
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TravelApprovalInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.TravelApprovalBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName TravelApprovalActivity
 * @Description 出差审批单据Activity
 * @author 21291
 * @date 2014年8月21日 上午9:59:17
 */
public class TravelApprovalActivity extends MenuActivity implements ITaskContext,ICheckNextNode{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fClientName,fStartTime,fBackTime,fTravelAddress,fTravelDays,fTravelCause,fArrangement,fTravelReport;
	private Button btnApprove,lowerButton;	//审批按钮,下级节点
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private SharedPreferences sp;  //获取登陆信息
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	private LowerNodeAppCheck lowerNodeAppCheck;		//异步请求是否有下级节点策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_approval_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_TRAVELAPPROVALACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, ""); 	//获取员工号
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.travel_approval_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(TravelApprovalActivity.this, TravelApprovalActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(TravelApprovalActivity.this, R.string.travelapproval_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(TravelApprovalActivity.this);
		TravelApprovalBusiness eBusiness= (TravelApprovalBusiness)factoryBusiness.getInstance("TravelApprovalBusiness",serviceUrl);
		return eBusiness.getTravelApprovalInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		TravelApprovalInfo tInfo=(TravelApprovalInfo)base;
		if(!StringUtils.isEmpty(tInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.travel_approval_FBillNo);
			fBillNo.setText(tInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.travel_approval_FApplyName);
			fApplyName.setText(tInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.travel_approval_FApplyDate);
			fApplyDate.setText(tInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.travel_approval_FApplyDept);
			fApplyDept.setText(tInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFClientName())){
			fClientName=(TextView)findViewById(R.id.travel_approval_FClientName);
			fClientName.setText(tInfo.getFClientName());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFStartTime())){
			fStartTime=(TextView)findViewById(R.id.travel_approval_FStartTime);
			fStartTime.setText(tInfo.getFStartTime());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFBackTime())){
			fBackTime=(TextView)findViewById(R.id.travel_approval_FBackTime);
			fBackTime.setText(tInfo.getFBackTime());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFTravelAddress())){
			fTravelAddress=(TextView)findViewById(R.id.travel_approval_FTravelAddress);
			fTravelAddress.setText(tInfo.getFTravelAddress());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFTravelDays())){
			fTravelDays=(TextView)findViewById(R.id.travel_approval_FTravelDays);
			fTravelDays.setText(tInfo.getFTravelDays());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFTravelCause())){
			fTravelCause=(TextView)findViewById(R.id.travel_approval_FTravelCause);
			fTravelCause.setText(tInfo.getFTravelCause());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFArrangement())){
			fArrangement=(TextView)findViewById(R.id.travel_approval_FArrangement);
			fArrangement.setText(tInfo.getFArrangement());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFTravelReport())){
			fTravelReport=(TextView)findViewById(R.id.travel_approval_FTravelReport);
			fTravelReport.setText(tInfo.getFTravelReport());
		}	
		initApprove();
	}
	
	/** 
	* @Title: initApprove 
	* @Description: 加载审批按钮视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 下午2:46:38
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 btnApprove=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		 if("1".equals(fStatus)){  //说明是已审批进来
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
			 handleLayout.setVisibility(View.GONE);
		 }
		 else{
			 handleLayout.setVisibility(View.VISIBLE);
			 Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			 Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			 lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			
			 //加签操作
			 plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(TravelApprovalActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.travelapproval_title));
				}
			 });
			 
			 //抄送操作
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(TravelApprovalActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.travelapproval_title));
				}
			 });
			 
			  //下级节点操作
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(TravelApprovalActivity.this,TravelApprovalActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //审批操作
		 btnApprove.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(TravelApprovalActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.travelapproval_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(TravelApprovalActivity.this, WorkFlowBeenActivity.class);
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
	
	@Override
	public void setCheckResult(ResultMessage resultMessage) {
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, TravelApprovalActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.travelapproval_title));
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
					btnApprove.setText(R.string.approve_imgbtnApprove_record);
					handleLayout.setVisibility(View.GONE);
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
		commonMenu.setContext(TravelApprovalActivity.this);
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
