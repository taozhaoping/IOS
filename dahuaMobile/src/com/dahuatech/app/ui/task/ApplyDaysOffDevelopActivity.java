package com.dahuatech.app.ui.task;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.dahuatech.app.bean.mytask.ApplyDaysOffDevelopInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ApplyDaysOffDevelopBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ApplyDaysOffDevelopActivity
 * @Description 研发部门调休申请单据Activity
 * @author 21291
 * @date 2014年7月23日 下午3:23:08
 */
public class ApplyDaysOffDevelopActivity extends MenuActivity {

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fApplyName,fApplyDate,fApplyDept,fTypeName,fSumDays,fStartTime,fEndTime,fReason;
	private ProgressDialog dialog;    //弹出框
	private TaskParamInfo taskParam;  //参数类
	private Button btnApprove; 	 	  //审批按钮
	private TableLayout handleLayout;
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	
	private String serviceUrl;  	  //服务地址
	private AppContext appContext;	  // 全局Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applydaysoff_develop);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_APPLYDAYSOFFDEVELOPACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		fSubLinearLayout=(LinearLayout)findViewById(R.id.applydaysoffdevelop_LinearLayout);
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);			
			new ApplyDaysOffAsync().execute(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ApplyDaysOffDevelopActivity.this, R.string.applydaysoffdevelop_netparseerror);
			return;
		}
	}
	
	/**
	 * @ClassName ApplyDaysOffAsync
	 * @Description 异步执行任务,获取数据实体
	 * @author 21291
	 * @date 2014年7月25日 下午2:58:35
	 */
	private class ApplyDaysOffAsync extends AsyncTask<String, integer, ApplyDaysOffDevelopInfo> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 显示等待框
			dialog.show();
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(ApplyDaysOffDevelopInfo result) {
			super.onPostExecute(result);
			initApplyDaysOffDevelopInfo(result);
			// 销毁等待框
			dialog.dismiss();
		}

		// 主要是完成耗时操作
		@Override
		protected ApplyDaysOffDevelopInfo doInBackground(String... params) {
			return getDataByPost(params[0]);
		}
	}
	
	/** 
	* @Title: getDataByPost 
	* @Description: 异步获取实体信息
	* @param @param serviceUrl
	* @param @return     
	* @return ApplyDaysOffDevelopInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 下午2:58:56
	*/
	private ApplyDaysOffDevelopInfo getDataByPost(String serviceUrl){
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// 发送这个消息到消息队列中
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ApplyDaysOffDevelopActivity.this);
		ApplyDaysOffDevelopBusiness eBusiness= (ApplyDaysOffDevelopBusiness)factoryBusiness.getInstance("ApplyDaysOffDevelopBusiness",serviceUrl);
		return eBusiness.getApplyDaysOffDevelopInfo(taskParam);
	}
	
	/** 
	* @Title: initApplyDaysOffDevelopInfo 
	* @Description: 研发部门调休申请单据初始化
	* @param @param adeInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 下午3:00:02
	*/
	private void initApplyDaysOffDevelopInfo(ApplyDaysOffDevelopInfo adInfo){
		if(!StringUtils.isEmpty(adInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.applydaysoffdevelop_FBillNo);
			fBillNo.setText(adInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyName);
			fApplyName.setText(adInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDate())){
			fApplyDate=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyDate);
			fApplyDate.setText(adInfo.getFApplyDate());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFApplyDept())){
			fApplyDept=(TextView)findViewById(R.id.applydaysoffdevelop_FApplyDept);
			fApplyDept.setText(adInfo.getFApplyDept());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFTypeName())){
			fTypeName=(TextView)findViewById(R.id.applydaysoffdevelop_FTypeName);
			fTypeName.setText(adInfo.getFTypeName());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFStartTime())){
			fStartTime=(TextView)findViewById(R.id.applydaysoffdevelop_FStartTime);
			fStartTime.setText(adInfo.getFStartTime());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFEndTime())){
			fEndTime=(TextView)findViewById(R.id.applydaysoffdevelop_FEndTime);
			fEndTime.setText(adInfo.getFEndTime());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFSumDays())){
			fSumDays=(TextView)findViewById(R.id.applydaysoffdevelop_FSumDays);
			fSumDays.setText(adInfo.getFSumDays());
		}
		
		if(!StringUtils.isEmpty(adInfo.getFReason())){
			fReason=(TextView)findViewById(R.id.applydaysoffdevelop_FReason);
			fReason.setText(adInfo.getFReason());
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
		 btnApprove=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 handleLayout.setVisibility(View.GONE);
		 if("1".equals(fStatus)){  //说明是已审批进来
			 btnApprove.setText(R.string.approve_imgbtnApprove_record);
		 }
		 
		 btnApprove.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(ApplyDaysOffDevelopActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.applydaysoffdevelop_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else {//显示已审批页面
					Intent intent = new Intent(ApplyDaysOffDevelopActivity.this, WorkFlowBeenActivity.class);
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
		commonMenu.setContext(ApplyDaysOffDevelopActivity.this);
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
