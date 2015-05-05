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
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdPermissionAfterTBodyInfo;
import com.dahuatech.app.bean.mytask.TdPermissionBeforeTBodyInfo;
import com.dahuatech.app.bean.mytask.TdPermissionTHeaderInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.TdPermissionBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName TdPermissionActivity
 * @Description TD权限申请单据Activity
 * @author 21291
 * @date 2014年9月23日 上午10:25:09
 */
@SuppressLint("InflateParams")
public class TdPermissionActivity extends MenuActivity implements ITaskContext,ICheckNextNode{
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fTel,fPersonType,fBeforeAmount,fAfterAmount;
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
		setContentView(R.layout.td_permission_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_TDPERMISSIONACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.td_permission_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(TdPermissionActivity.this, TdPermissionActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(TdPermissionActivity.this, R.string.tdpermission_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(TdPermissionActivity.this);
		TdPermissionBusiness eBusiness= (TdPermissionBusiness)factoryBusiness.getInstance("TdPermissionBusiness",serviceUrl);
		return eBusiness.getTdPermissionTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		TdPermissionTHeaderInfo tInfo=(TdPermissionTHeaderInfo)base;
		if(!StringUtils.isEmpty(tInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.td_permission_FBillNo);
			fBillNo.setText(tInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.td_permission_FApplyName);
			fApplyName.setText(tInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(tInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.td_permission_FApplyDate);
			fApplyDate.setText(tInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.td_permission_FApplyDept);
			fApplyDept.setText(tInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFTel())){
			fTel=(TextView)findViewById(R.id.td_permission_FTel);
			fTel.setText(tInfo.getFTel());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFPersonType())){
			fPersonType=(TextView)findViewById(R.id.td_permission_FPersonType);
			fPersonType.setText(tInfo.getFPersonType());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFBeforeAmount())){
			fBeforeAmount=(TextView)findViewById(R.id.td_permission_FBeforeAmount);
			fBeforeAmount.setText(tInfo.getFBeforeAmount());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFAfterAmount())){
			fAfterAmount=(TextView)findViewById(R.id.td_permission_FAfterAmount);
			fAfterAmount.setText(tInfo.getFAfterAmount());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFBeforeSubEntrys())){
			showBeforeTitle();
			showBefore(tInfo.getFBeforeSubEntrys());
		}
		
		if(!StringUtils.isEmpty(tInfo.getFAfterSubEntrys())){
			showAfterTitle();
			showAfter(tInfo.getFAfterSubEntrys());
		}
		initApprove();
	}
	
	/** 
	* @Title: showBeforeTitle 
	* @Description: 显示发布前子类明细头部
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 下午12:00:41
	*/
	private void showBeforeTitle(){
		 View beforeTitle = LayoutInflater.from(this).inflate(R.layout.td_permission_before_title_tbody, null);
		 fSubLinearLayout.addView(beforeTitle);	
	}
	
	//构造发布前子类
	private void showBefore(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<TdPermissionBeforeTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<TdPermissionBeforeTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (TdPermissionBeforeTBodyInfo item : tBodyInfos) {
					attachBeforeSubEntry(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: showAfterTitle 
	* @Description: 显示发布后子类明细头部
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 下午12:01:28
	*/
	private void showAfterTitle(){
		 View beforeTitle = LayoutInflater.from(this).inflate(R.layout.td_permission_after_title_tbody, null);
		 fSubLinearLayout.addView(beforeTitle);	
	}
	
	//构造发布后子类集合
	private void showAfter(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<TdPermissionAfterTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<TdPermissionAfterTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (TdPermissionAfterTBodyInfo item : tBodyInfos) {
					attachAfterSubEntry(item,i);
					i++;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachBeforeSubEntry 
	* @Description: 动态附加发布前子类视图
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 下午12:03:09
	*/
	private void attachBeforeSubEntry(final TdPermissionBeforeTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.td_permission_before_tbody, null);
		
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView fApplyNumber=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FApplyNumber);
		 fApplyNumber.setText(tBodyInfo.getFApplyNumber());
		 
		 TextView fApplyName=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FApplyName);
		 fApplyName.setText(tBodyInfo.getFApplyName());
		 
		 TextView fApplyDept=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FApplyDept);
		 fApplyDept.setText(tBodyInfo.getFApplyDept());
		 
		 TextView fEmail=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FEmail);
		 fEmail.setText(tBodyInfo.getFEmail());
		 
		 TextView fTdDomain=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FTdDomain);
		 fTdDomain.setText(tBodyInfo.getFTdDomain());
		 
		 TextView fTdProject=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FTdProject);
		 fTdProject.setText(tBodyInfo.getFTdProject());
		 
		 TextView fCodeAndName=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FCodeAndName);
		 fCodeAndName.setText(tBodyInfo.getFCodeAndName());
		 
		 TextView fManager=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FManager);
		 fManager.setText(tBodyInfo.getFManager());
		 
		 TextView fProjectPermission=(TextView)linearLayout.findViewById(R.id.td_permission_before_tbody_FProjectPermission);
		 fProjectPermission.setText(tBodyInfo.getFProjectPermission());
		 
		 fSubLinearLayout.addView(linearLayout);
	}
	
	/** 
	* @Title: attachAfterSubEntry 
	* @Description: 动态附加发布后子类视图
	* @param @param tBodyInfo
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 下午12:04:16
	*/
	private void attachAfterSubEntry(final TdPermissionAfterTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.td_permission_after_tbody, null);
		
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView fApplyNumber=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FApplyNumber);
		 fApplyNumber.setText(tBodyInfo.getFApplyNumber());
		 
		 TextView fApplyName=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FApplyName);
		 fApplyName.setText(tBodyInfo.getFApplyName());
		 
		 TextView fApplyDept=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FApplyDept);
		 fApplyDept.setText(tBodyInfo.getFApplyDept());
		 
		 TextView fEmail=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FEmail);
		 fEmail.setText(tBodyInfo.getFEmail());
		 
		 TextView fProductName=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FProductName);
		 fProductName.setText(tBodyInfo.getFProductName());
		 
		 TextView fProductType=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FProductType);
		 fProductType.setText(tBodyInfo.getFProductType());
		 
		 TextView fCodeAndName=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FCodeAndName);
		 fCodeAndName.setText(tBodyInfo.getFCodeAndName());
		 
		 TextView fTestPermission=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FTestPermission);
		 fTestPermission.setText(tBodyInfo.getFTestPermission());
		 
		 TextView fDevelopPermission=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FDevelopPermission);
		 fDevelopPermission.setText(tBodyInfo.getFDevelopPermission());
		 
		 TextView fManagerPermission=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FManagerPermission);
		 fManagerPermission.setText(tBodyInfo.getFManagerPermission());
		 
		 TextView fReadOnly=(TextView)linearLayout.findViewById(R.id.td_permission_after_tbody_FReadOnly);
		 fReadOnly.setText(tBodyInfo.getFReadOnly());
		 
		 fSubLinearLayout.addView(linearLayout);
	}
	
	/** 
	* @Title: initApprove 
	* @Description: 加载审批按钮视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 上午10:39:56
	*/
	private void initApprove(){
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		 if("1".equals(fStatus)){  //说明是已审批进来
			 appButton.setText(R.string.approve_imgbtnApprove_record);
			 handleLayout.setVisibility(View.GONE);
		 }
		 else {
			 handleLayout.setVisibility(View.VISIBLE);
			 Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			 Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			 lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
			 
			 //加签操作
			 plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(TdPermissionActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.tdpermission_title));
				}
			 });
			 
			 //抄送操作
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(TdPermissionActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.tdpermission_title));
				}
			 });
			 
			  //下级节点操作
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(TdPermissionActivity.this,TdPermissionActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
			 
		 }
		 
		 //审批操作
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(TdPermissionActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.tdpermission_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(TdPermissionActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, TdPermissionActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.tdpermission_title));
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
		commonMenu.setContext(TdPermissionActivity.this);
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
