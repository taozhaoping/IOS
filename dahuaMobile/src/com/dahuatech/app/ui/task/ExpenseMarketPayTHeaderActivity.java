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
import com.dahuatech.app.bean.mytask.ExpenseMarketPayTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExpenseMarketPayTHeaderBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseMarketPayTHeaderActivity
 * @Description 市场投标支付单据Activity
 * @author 21291
 * @date 2014年6月25日 下午2:34:35
 */
public class ExpenseMarketPayTHeaderActivity extends MenuActivity implements ITaskContext{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fConSmName,fCommitDate,fAmountAll,fPubPayNo,fRecAccName,fProjectName,fConSmTypeName;
	private TaskParamInfo taskParam;  //参数类
	private Button btnApprove;	//审批按钮
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expensemarketpaytheader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEMARKETPAYTHEADERACTIVITY;
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.expensemarketpaytheader_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExpenseMarketPayTHeaderActivity.this, ExpenseMarketPayTHeaderActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExpenseMarketPayTHeaderActivity.this, R.string.expensemarketpaytheader_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseMarketPayTHeaderActivity.this);
		ExpenseMarketPayTHeaderBusiness eBusiness= (ExpenseMarketPayTHeaderBusiness)factoryBusiness.getInstance("ExpenseMarketPayTHeaderBusiness",serviceUrl);
		return eBusiness.getExpenseMarketPayTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ExpenseMarketPayTHeaderInfo exMarketPay=(ExpenseMarketPayTHeaderInfo)base;
		if(!StringUtils.isEmpty(exMarketPay.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.expensemarketpaytheader_FBillNo);
			fBillNo.setText(exMarketPay.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFConSmName())){
			fConSmName=(TextView)findViewById(R.id.expensemarketpaytheader_FConSmName);
			fConSmName.setText(exMarketPay.getFConSmName());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFCommitDate())){
			fCommitDate=(TextView)findViewById(R.id.expensemarketpaytheader_FCommitDate);
			fCommitDate.setText(exMarketPay.getFCommitDate());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFAmountAll())){
			fAmountAll=(TextView)findViewById(R.id.expensemarketpaytheader_FAmountAll);
			fAmountAll.setText(exMarketPay.getFAmountAll());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFPubPayNo())){
			fPubPayNo=(TextView)findViewById(R.id.expensemarketpaytheader_FPubPayNo);
			fPubPayNo.setText(exMarketPay.getFPubPayNo());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFRecAccName())){
			fRecAccName=(TextView)findViewById(R.id.expensemarketpaytheader_FRecAccName);
			fRecAccName.setText(exMarketPay.getFRecAccName());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFProjectName())){
			fProjectName=(TextView)findViewById(R.id.expensemarketpaytheader_FProjectName);
			fProjectName.setText(exMarketPay.getFProjectName());
		}
		
		if(!StringUtils.isEmpty(exMarketPay.getFConSmTypeName())){
			fConSmTypeName=(TextView)findViewById(R.id.expensemarketpaytheader_FConSmTypeName);
			fConSmTypeName.setText(exMarketPay.getFConSmTypeName());
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
	* @date 2014年8月13日 下午5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		 //审批按钮
		 View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		 handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		 handleLayout.setVisibility(View.GONE);
		 btnApprove=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);

		 if("1".equals(fStatus)){  //说明是已审批进来
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
		 }
		 btnApprove.setOnClickListener(new OnClickListener() {
			 
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(ExpenseMarketPayTHeaderActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.expensemarketpaytheader_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(ExpenseMarketPayTHeaderActivity.this, WorkFlowBeenActivity.class);
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
					btnApprove.setText(R.string.approve_imgbtnApprove_record);
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
		commonMenu.setContext(ExpenseMarketPayTHeaderActivity.this);
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
