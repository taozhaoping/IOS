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
import com.dahuatech.app.bean.mytask.NewProductLibTBodyInfo;
import com.dahuatech.app.bean.mytask.NewProductLibTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.NewProductLibBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName NewProductLib
 * @Description 新产品转库单据Activity
 * @author 21291
 * @date 2014年8月12日 上午9:48:46
 */
public class NewProductLibActivity extends MenuActivity implements ITaskContext,ICheckNextNode{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fMaterialType,fProductLine,fDescribe,fReason;
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
		setContentView(R.layout.new_product_lib_theader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_NEWPRODUCTLIBACTIVITY;	
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
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.new_product_lib_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(NewProductLibActivity.this, NewProductLibActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(NewProductLibActivity.this, R.string.newproductlib_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(NewProductLibActivity.this);
		NewProductLibBusiness eBusiness= (NewProductLibBusiness)factoryBusiness.getInstance("NewProductLibBusiness",serviceUrl);
		return eBusiness.getNewProductLibTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		NewProductLibTHeaderInfo nInfo=(NewProductLibTHeaderInfo)base;
		if(!StringUtils.isEmpty(nInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.new_product_lib_FBillNo);
			fBillNo.setText(nInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.new_product_lib_FApplyName);
			fApplyName.setText(nInfo.getFApplyName());
		}

		if(!StringUtils.isEmpty(nInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.new_product_lib_FApplyDate);
			fApplyDate.setText(nInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.new_product_lib_FApplyDept);
			fApplyDept.setText(nInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFMaterialType())){
			fMaterialType=(TextView)findViewById(R.id.new_product_lib_FMaterialType);
			fMaterialType.setText(nInfo.getFMaterialType());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFProductLine())){
			fProductLine=(TextView)findViewById(R.id.new_product_lib_FProductLine);
			fProductLine.setText(nInfo.getFProductLine());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFDescribe())){
			fDescribe=(TextView)findViewById(R.id.new_product_lib_FDescribe);
			fDescribe.setText(nInfo.getFDescribe());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFReason())){
			fReason=(TextView)findViewById(R.id.new_product_lib_FReason);
			fReason.setText(nInfo.getFReason());
		}
		
		if(!StringUtils.isEmpty(nInfo.getFSubEntrys())){
			showSubEntrys(nInfo.getFSubEntrys());
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
	* @date 2014年8月13日 下午4:07:01
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<NewProductLibTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<NewProductLibTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (NewProductLibTBodyInfo item : tBodyInfos) {
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
	* @date 2014年8月13日 下午4:07:37
	*/
	@SuppressLint("InflateParams")
	private void attachSubEntry(final NewProductLibTBodyInfo tBodyInfo,final int i){
		 View linearLayout = LayoutInflater.from(this).inflate(R.layout.new_product_lib_tbody, null);
		
		 TextView fLine=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FLine);
		 fLine.setText(String.valueOf(i));
		 
		 TextView fProductName=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FProductName);
		 fProductName.setText(tBodyInfo.getFProductName());
		 
		 TextView fModel=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FModel);
		 fModel.setText(tBodyInfo.getFModel());
		 
		 TextView fUnit=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FUnit);
		 fUnit.setText(tBodyInfo.getFUnit());
		 
		 TextView fAmount=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FAmount);
		 fAmount.setText(tBodyInfo.getFAmount());
		 
		 TextView fOutLocation=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FOutLocation);
		 fOutLocation.setText(tBodyInfo.getFOutLocation());
		 
		 TextView fInLocation=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FInLocation);
		 fInLocation.setText(tBodyInfo.getFInLocation());
		 
		 TextView fNote=(TextView)linearLayout.findViewById(R.id.new_product_lib_tbody_FNote);
		 fNote.setText(tBodyInfo.getFNote());
		 
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
					UIHelper.showPlusCopy(NewProductLibActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.newproductlib_title));
				}
			 });
			 
			 //抄送操作
			 copyButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(NewProductLibActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.newproductlib_title));
				}
			 });
			 
			  //下级节点操作
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(NewProductLibActivity.this,NewProductLibActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //审批操作
		 appButton.setOnClickListener(new OnClickListener() {
			@Override
		 	public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(NewProductLibActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.newproductlib_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(NewProductLibActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, NewProductLibActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.newproductlib_title));
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
		commonMenu.setContext(NewProductLibActivity.this);
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
