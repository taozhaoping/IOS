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
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.EmailOpenTBodyInfo;
import com.dahuatech.app.bean.mytask.EmailOpenTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.EmailOpenBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName EmailOpenActivity
 * @Description 邮箱开通申请Activity
 * @author 21291
 * @date 2014年8月18日 上午10:36:59
 */
public class EmailOpenActivity extends MenuActivity implements ITaskContext,ICheckNextNode{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fEmailType,fApplyDept,fReason,fApplyPermission,fInfoSafe;
	private Button appButton,lowerButton;	//审批按钮,下级节点
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
		setContentView(R.layout.emial_open_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EMAILOPENACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.email_open_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(EmailOpenActivity.this, EmailOpenActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(EmailOpenActivity.this, R.string.emailopen_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(EmailOpenActivity.this);
		EmailOpenBusiness eBusiness= (EmailOpenBusiness)factoryBusiness.getInstance("EmailOpenBusiness",serviceUrl);
		return eBusiness.getEmailOpenTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		EmailOpenTHeaderInfo eInfo=(EmailOpenTHeaderInfo)base;
		if(!StringUtils.isEmpty(eInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.email_open_FBillNo);
			fBillNo.setText(eInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.email_open_FApplyName);
			fApplyName.setText(eInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.email_open_FApplyDate);
			fApplyDate.setText(eInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.email_open_FApplyDept);
			fApplyDept.setText(eInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFEmailType())){
			fEmailType=(TextView)findViewById(R.id.email_open_FEmailType);
			fEmailType.setText(eInfo.getFEmailType());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFReason())){
			fReason=(TextView)findViewById(R.id.email_open_FReason);
			fReason.setText(eInfo.getFReason());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFApplyPermission())){
			fApplyPermission=(TextView)findViewById(R.id.email_open_FApplyPermission);
			fApplyPermission.setText(eInfo.getFApplyPermission());
		}
		
		if(!StringUtils.isEmpty(eInfo.getFInfoSafe())){
			fInfoSafe=(TextView)findViewById(R.id.email_open_FInfoSafe);
			fInfoSafe.setText(eInfo.getFInfoSafe());
		}

		if(!StringUtils.isEmpty(eInfo.getFSubEntrys())){
			showSubEntrys(eInfo.getFSubEntrys());
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
	* @date 2014年8月18日 上午10:50:12
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<EmailOpenTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<EmailOpenTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (EmailOpenTBodyInfo item : tBodyInfos) {
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
	* @date 2014年8月18日 上午10:51:15
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final EmailOpenTBodyInfo tBodyInfo,final int i){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.email_open_tbody, null);
		 
		TextView fLine=(TextView)linearLayout.findViewById(R.id.email_open_tbody_FLine);
		fLine.setText(String.valueOf(i));
		
		TextView fEmailAddress=(TextView)linearLayout.findViewById(R.id.email_open_tbody_FEmailAddress);
		fEmailAddress.setText(tBodyInfo.getFEmailAddress());
		
		TextView fStartTime=(TextView)linearLayout.findViewById(R.id.email_open_tbody_FStartTime);
		fStartTime.setText(tBodyInfo.getFStartTime());
			
		TextView fApplyDeadLine=(TextView)linearLayout.findViewById(R.id.email_open_tbody_FApplyDeadLine);
		fApplyDeadLine.setText(tBodyInfo.getFApplyDeadLine());
		 
		fSubLinearLayout.addView(linearLayout);
	}
	
	/** 
	* @Title: initApprove 
	* @Description: 加载审批按钮视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月13日 上午11:08:30
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		 if("1".equals(fStatus)){  //说明是已审批进来
			 appButton.setText(R.string.approve_imgbtnApprove_record);
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
					UIHelper.showPlusCopy(EmailOpenActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.emailopen_title));
				}
			 });
			 
			 //抄送操作
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(EmailOpenActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.emailopen_title));
				}
			 });
			 
			 //下级节点操作
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(EmailOpenActivity.this,EmailOpenActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //审批操作
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(EmailOpenActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.emailopen_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(EmailOpenActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, EmailOpenActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.emailopen_title));
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
		commonMenu.setContext(EmailOpenActivity.this);
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
