package com.dahuatech.app.ui.task;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.EngineeringInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.EngineeringBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName EngineeringActivity
 * @Description 工程商圈定单据Activity
 * @author 21291
 * @date 2014年5月20日 下午2:06:19
 */
public class EngineeringActivity extends MenuActivity implements ITaskContext{	
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fEngineereName,fAddress,fAmount,fContact,fTel,fComboBox,fText,fComboBox1,fBase1,fNote;
	private TaskParamInfo taskParam;  //参数类
	private Button btnApprove;    //审批按钮
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.engineering);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_NOTICESERVICE;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
			
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(EngineeringActivity.this, EngineeringActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(EngineeringActivity.this, R.string.engineering_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(EngineeringActivity.this);
		EngineeringBusiness eBusiness=(EngineeringBusiness)factoryBusiness.getInstance("EngineeringBusiness",serviceUrl);
		return eBusiness.getEngineeringInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		EngineeringInfo engineering=(EngineeringInfo)base;
		if(!StringUtils.isEmpty(engineering.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.engineering_FBillNo);
			fBillNo.setText(engineering.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(engineering.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.engineering_FApplyName);
			fApplyName.setText(engineering.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(engineering.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.engineering_FApplyDate);
			fApplyDate.setText(engineering.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(engineering.getFEngineereName())){
			fEngineereName=(TextView)findViewById(R.id.engineering_FEngineereName);
			fEngineereName.setText(engineering.getFEngineereName());
		}
		
		if(!StringUtils.isEmpty(engineering.getFAddress())){
			fAddress=(TextView)findViewById(R.id.engineering_FAddress);
			fAddress.setText(engineering.getFAddress());
		}
		
		if(!StringUtils.isEmpty(engineering.getFAmount())){
			fAmount=(TextView)findViewById(R.id.engineering_FAmount);
			fAmount.setText(engineering.getFAmount());
		}
		
		if(!StringUtils.isEmpty(engineering.getFContact())){
			fContact=(TextView)findViewById(R.id.engineering_FContact);
			fContact.setText(engineering.getFContact());
		}
		
		if(!StringUtils.isEmpty(engineering.getFTel())){
			fTel=(TextView)findViewById(R.id.engineering_FTel);
			fTel.setText(engineering.getFTel());
			fTel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+fTel.getText().toString()));
					EngineeringActivity.this.startActivity(intent);	
				}
			});	
		}
		
		if(!StringUtils.isEmpty(engineering.getFComboBox())){
			fComboBox=(TextView)findViewById(R.id.engineering_FComboBox);
			fComboBox.setText(engineering.getFComboBox());
		}
		
		if(!StringUtils.isEmpty(engineering.getFText())){
			fText=(TextView)findViewById(R.id.engineering_FText);
			fText.setText(engineering.getFText());
		}
			
		if(!StringUtils.isEmpty(engineering.getFComboBox1())){
			fComboBox1=(TextView)findViewById(R.id.engineering_FComboBox1);
			fComboBox1.setText(engineering.getFComboBox1());
		}
		
		if(!StringUtils.isEmpty(engineering.getFBase1())){
			fBase1=(TextView)findViewById(R.id.engineering_FBase1);
			fBase1.setText(engineering.getFBase1());
		}
		
		if(!StringUtils.isEmpty(engineering.getFNote())){
			fNote=(TextView)findViewById(R.id.engineering_FNote);
			fNote.setText(engineering.getFNote());
		}	
		
		//审批按钮
		btnApprove=(Button)findViewById(R.id.approve_imgbtnApprove);
		if("1".equals(fStatus)){  //说明是已审批进来
			btnApprove.setText(R.string.approve_imgbtnApprove_record);
		}
		btnApprove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(EngineeringActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.engineering_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//显示已审批页面
					Intent intent = new Intent(EngineeringActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
			}
		});	
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
		commonMenu.setContext(EngineeringActivity.this);
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
