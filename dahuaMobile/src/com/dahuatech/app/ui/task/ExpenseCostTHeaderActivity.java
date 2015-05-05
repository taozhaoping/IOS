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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ExpenseCostTBodyInfo;
import com.dahuatech.app.bean.mytask.ExpenseCostTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExpenseCostTHeaderBusiness;
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
 * @ClassName ExpenseCostTHeaderActivity
 * @Description 费用申请报销单据Activity
 * @author 21291
 * @date 2014年7月9日 下午4:00:21
 */
public class ExpenseCostTHeaderActivity extends MenuActivity implements ITaskContext{
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fGeneralCode;
	private TextView fBillNo,fConSmName,fCommitDate,fGeneralName,fAppAmt,fLDAmt;
	private TableLayout tb1,tb2;
	private Button appButton;	//审批按钮
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private int showFlag;         //根据不同申请类型，明细子类显示值也不同 0-费用预申请，1-费用借款，2-费用预申请+借款
	private LinearLayout fCostTypeLinearLayout; //费用类型布局全局变量
	
	private float dip; //像素  相差1.5倍
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width; // margin in dips
	private	DisplayMetrics displaymetrics; 	//像素宽度信息
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expensecosttheader);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSECOSTTHEADERACTIVITY;			
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
		//初始化帧布局实例 
		fCostTypeLinearLayout=(LinearLayout)findViewById(R.id.expensecosttheader_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExpenseCostTHeaderActivity.this, ExpenseCostTHeaderActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExpenseCostTHeaderActivity.this, R.string.expensecosttheader_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseCostTHeaderActivity.this);
		ExpenseCostTHeaderBusiness eBusiness= (ExpenseCostTHeaderBusiness)factoryBusiness.getInstance("ExpenseCostTHeaderBusiness",serviceUrl);
		return eBusiness.getExpenseCostTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ExpenseCostTHeaderInfo exCost=(ExpenseCostTHeaderInfo)base;
		if(!StringUtils.isEmpty(exCost.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.expensecosttheader_FBillNo);
			fBillNo.setText(exCost.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(exCost.getFConSmName())){
			fConSmName=(TextView)findViewById(R.id.expensecosttheader_FConSmName);
			fConSmName.setText(exCost.getFConSmName());
		}
		
		if(!StringUtils.isEmpty(exCost.getFCommitDate())){
			fCommitDate=(TextView)findViewById(R.id.expensecosttheader_FCommitDate);
			fCommitDate.setText(exCost.getFCommitDate());
		}
		
		if(!StringUtils.isEmpty(exCost.getFGeneralName())){
			fGeneralName=(TextView)findViewById(R.id.expensecosttheader_FGeneralName);
			fGeneralName.setText(exCost.getFGeneralName());
		}
		
		if(!StringUtils.isEmpty(exCost.getFAppAmt())){
			fAppAmt=(TextView)findViewById(R.id.expensecosttheader_FAppAmt);
			fAppAmt.setText(exCost.getFAppAmt());
		}
		
		if(!StringUtils.isEmpty(exCost.getFLDAmt())){
			fLDAmt=(TextView)findViewById(R.id.expensecosttheader_FLDAmt);
			fLDAmt.setText(exCost.getFLDAmt());
		}
		
		fGeneralCode=exCost.getFGeneralCode();
		if("AF01.04.01".equals(fGeneralCode)){  //费用预申请
			tb2=(TableLayout)findViewById(R.id.expensecosttheader_tb2);
			tb2.setVisibility(View.GONE);
			showFlag=0;
		}
		
		if("AF01.04.02".equals(fGeneralCode)){  //费用借款
			tb1=(TableLayout)findViewById(R.id.expensecosttheader_tb1);
			tb1.setVisibility(View.GONE);
			showFlag=1; 
		}
		
		if("AF01.04.03".equals(fGeneralCode)){   //费用预申请+借款
			showFlag=2;
		}
		
		if(!StringUtils.isEmpty(exCost.getFCostType())){
			showCostType(exCost.getFCostType());
		}
		initApprove();
	}
	
	/** 
	* @Title: showCostType 
	* @Description: 构造子类集合
	* @param @param fCostType     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月17日 上午11:08:11
	*/
	private void showCostType(final String fCostType){
		try {
			Type listType = new TypeToken<ArrayList<ExpenseCostTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fCostType);
			List<ExpenseCostTBodyInfo> costTBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(costTBodyInfos.size() > 0){
				for (ExpenseCostTBodyInfo expenseCostTBodyInfo : costTBodyInfos) {
					attachRelativeLayout(expenseCostTBodyInfo);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: 动态拼接 显示费用类型
	* @param @param costTBodyInfo
	* @param @param id     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月17日 上午11:50:25
	*/
	@SuppressLint({ "InlinedApi" })
	private void attachRelativeLayout(final ExpenseCostTBodyInfo costTBodyInfo){
		paddingLeft = 20; // margin in dips
		paddingRight = 10; 
		paddingTop = 5;
		paddingBottom = 5; 
	    width = getPixelsWidth(); 
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
        }
		
		RelativeLayout reLayoutTop=new RelativeLayout(this);  //创建一个费用类型头部相对布局实例
		//配置相对布局实例的高度和宽度
		LayoutParams reLPTop=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutTop.setLayoutParams(reLPTop);
		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		
		//费用类型名称
		TextView tvTop1 = new TextView(this);
		LayoutParams tvTopParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTop1.setLayoutParams(tvTopParam1);
		tvTop1.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.icons);
		tvTop1.setText(R.string.expensecosttheader_FConSmType);
		int tvTop1Id=getNewId();
		tvTop1.setId(tvTop1Id);		
		reLayoutTop.addView(tvTop1);
		
		//费用类型值
		TextView tvTop2 = new TextView(this);
		LayoutParams tvTopParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTopParam2.addRule(RelativeLayout.RIGHT_OF, tvTop1Id);
		tvTop2.setLayoutParams(tvTopParam2);
		tvTop2.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.icons);
		tvTop2.setText(costTBodyInfo.getFConSmType());
		reLayoutTop.addView(tvTop2);		
		fCostTypeLinearLayout.addView(reLayoutTop);
		
		RelativeLayout reLayoutBottom=new RelativeLayout(this);  //创建一个费用类型底部相对布局实例
		//配置相对布局实例的高度和宽度
		LayoutParams reLPBottom=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom.setLayoutParams(reLPBottom);
		reLayoutBottom.setPadding(paddingLeft, 5, 10, 5);
		
		//申请日期名称
		TextView tvBottom1 = new TextView(this);
		LayoutParams tvBottomParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottom1.setLayoutParams(tvBottomParam1);
		tvBottom1.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom1.setText(R.string.expensecosttheader_FConSmDate);
		tvBottom1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		int tvBottom1Id=getNewId();
		tvBottom1.setId(tvBottom1Id);
		reLayoutBottom.addView(tvBottom1);
		
		//申请日期值
		TextView tvBottom2 = new TextView(this);
		LayoutParams tvBottomParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam2.setMargins(0, 0, 0, 5);
		tvBottomParam2.addRule(RelativeLayout.RIGHT_OF, tvBottom1Id);
		tvBottom2.setLayoutParams(tvBottomParam2);
		tvBottom2.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom2.setText(costTBodyInfo.getFConSmDate());
		reLayoutBottom.addView(tvBottom2);
		
		//费用预申请金额名称
		TextView tvBottom3 = new TextView(this);
		LayoutParams tvBottomParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam3.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottom3.setLayoutParams(tvBottomParam3);
		tvBottom3.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount_2014, 0, 0, 0);
		tvBottom3.setText(R.string.expensecosttheader_subClassFAppAmt);
		int tvBottom3Id=getNewId();
		tvBottom3.setId(tvBottom3Id);
		reLayoutBottom.addView(tvBottom3);
		
		//费用预申请金额符号
		TextView tvBottom4 = new TextView(this);
		LayoutParams tvBottomParam4 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam4.setMargins(0, 0, 0, 5);
		tvBottomParam4.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottomParam4.addRule(RelativeLayout.RIGHT_OF, tvBottom3Id);
		tvBottom4.setLayoutParams(tvBottomParam4);
		tvBottom4.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom4.setText(R.string.engineering_165);	
		int tvBottom4Id=getNewId();
		tvBottom4.setId(tvBottom4Id);	
		reLayoutBottom.addView(tvBottom4);
		
		//费用预申请金额值
		TextView tvBottom5 = new TextView(this);
		LayoutParams tvBottomParam5 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam5.setMargins(0, 0, 0, 5);
		tvBottomParam5.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottomParam5.addRule(RelativeLayout.RIGHT_OF, tvBottom4Id);
		tvBottom5.setLayoutParams(tvBottomParam5);
		tvBottom5.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom5.setText(costTBodyInfo.getFAppAmt());	
		reLayoutBottom.addView(tvBottom5);
		
		//费用借款金额名称
		TextView tvBottom6 = new TextView(this);
		LayoutParams tvBottomParam6 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam6.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottom6.setLayoutParams(tvBottomParam6);
		tvBottom6.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount_2014, 0, 0, 0);
		tvBottom6.setText(R.string.expensecosttheader_subClassFLDAmt);
		int tvBottom6Id=getNewId();
		tvBottom6.setId(tvBottom6Id);
		reLayoutBottom.addView(tvBottom6);
		
		//费用借款金额符号
		TextView tvBottom7 = new TextView(this);
		LayoutParams tvBottomParam7 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam7.setMargins(0, 0, 0, 0);
		tvBottomParam7.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottomParam7.addRule(RelativeLayout.RIGHT_OF, tvBottom6Id);
		tvBottom7.setLayoutParams(tvBottomParam7);
		tvBottom7.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom7.setText(R.string.engineering_165);	
		int tvBottom7Id=getNewId();
		tvBottom7.setId(tvBottom7Id);	
		reLayoutBottom.addView(tvBottom7);
		
		//费用借款金额值
		TextView tvBottom8 = new TextView(this);
		LayoutParams tvBottomParam8 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam8.setMargins(0, 0, 0, 5);
		tvBottomParam8.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottomParam8.addRule(RelativeLayout.RIGHT_OF, tvBottom7Id);
		tvBottom8.setLayoutParams(tvBottomParam8);
		tvBottom8.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom8.setText(costTBodyInfo.getFLDAmt());
		reLayoutBottom.addView(tvBottom8);

		//借款类型名称
		TextView tvBottom9 = new TextView(this);
		LayoutParams tvBottomParam9 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam9.addRule(RelativeLayout.BELOW, tvBottom6Id);
		tvBottom9.setLayoutParams(tvBottomParam9);
		tvBottom9.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount_2001, 0, 0, 0);
		tvBottom9.setText(R.string.expensecosttheader_FLendType);
		int tvBottom9Id=getNewId();
		tvBottom9.setId(tvBottom9Id);
		reLayoutBottom.addView(tvBottom9);
		
		//借款类型值
		TextView tvBottom10 = new TextView(this);
		LayoutParams tvBottomParam10 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam10.setMargins(0, 0, 0, 5);
		tvBottomParam10.addRule(RelativeLayout.BELOW, tvBottom6Id);
		tvBottomParam10.addRule(RelativeLayout.RIGHT_OF, tvBottom9Id);
		tvBottom10.setLayoutParams(tvBottomParam10);
		tvBottom10.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom10.setText(costTBodyInfo.getFLendType());
		reLayoutBottom.addView(tvBottom10);
		
		//项目名称
		TextView tvBottom11 = new TextView(this);
		LayoutParams tvBottomParam11 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam11.addRule(RelativeLayout.BELOW, tvBottom9Id);
		tvBottom11.setLayoutParams(tvBottomParam11);
		tvBottom11.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom11.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount_3002, 0, 0, 0);
		tvBottom11.setText(R.string.expensePrivateTBodyInfo_ProjectName);
		int tvBottom11Id=getNewId();
		tvBottom11.setId(tvBottom11Id);
		reLayoutBottom.addView(tvBottom11);
		
		//项目值
		TextView tvBottom12 = new TextView(this);
		LayoutParams tvBottomParam12 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam12.setMargins(0, 0, 0, 5);
		tvBottomParam12.addRule(RelativeLayout.BELOW, tvBottom9Id);
		tvBottomParam12.addRule(RelativeLayout.RIGHT_OF, tvBottom11Id);
		tvBottom12.setLayoutParams(tvBottomParam12);
		tvBottom12.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom12.setText(costTBodyInfo.getFProjectName());
		reLayoutBottom.addView(tvBottom12);
		
		//事由名称
		TextView tvBottom13 = new TextView(this);
		LayoutParams tvBottomParam13 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam13.addRule(RelativeLayout.BELOW, tvBottom11Id);
		tvBottom13.setLayoutParams(tvBottomParam13);
		tvBottom13.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconstext);
		tvBottom13.setCompoundDrawablesWithIntrinsicBounds(R.drawable.amount, 0, 0, 0);
		tvBottom13.setText(R.string.expensePrivateTBodyInfo_Use);
		int tvBottom13Id=getNewId();
		tvBottom13.setId(tvBottom13Id);
		reLayoutBottom.addView(tvBottom13);
		
		//事由值
		TextView tvBottom14 = new TextView(this);
		LayoutParams tvBottomParam14 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam14.setMargins(0, 0, 0, 5);
		tvBottomParam14.addRule(RelativeLayout.BELOW, tvBottom11Id);
		tvBottomParam14.addRule(RelativeLayout.RIGHT_OF, tvBottom13Id);
		tvBottom14.setLayoutParams(tvBottomParam14);
		tvBottom14.setTextAppearance(ExpenseCostTHeaderActivity.this,R.style.iconscontent);
		tvBottom14.setText(costTBodyInfo.getFUse());
		reLayoutBottom.addView(tvBottom14);
		
		fCostTypeLinearLayout.addView(reLayoutBottom);
		if(showFlag==0){  //说明是费用预申请类型
			tvBottom6.setVisibility(View.GONE);
			tvBottom7.setVisibility(View.GONE);
			tvBottom8.setVisibility(View.GONE);
			tvBottom9.setVisibility(View.GONE);
			tvBottom10.setVisibility(View.GONE);
			tvBottom11.setVisibility(View.GONE);	
		}
		
		if(showFlag==1){  //说明是费用借款类型
			tvBottom3.setVisibility(View.GONE);
			tvBottom4.setVisibility(View.GONE);
			tvBottom5.setVisibility(View.GONE);
		}
	}
	
	/** 
	* @Title: getNewId 
	* @Description: 获取一个新的ID值
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年6月17日 下午2:45:08
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
	* @date 2014年7月25日 下午2:00:31
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
		handleLayout.setVisibility(View.GONE); 
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		
		if("1".equals(fStatus)){  //说明是已审批进来
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		
		appButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(ExpenseCostTHeaderActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.expensecosttheader_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(ExpenseCostTHeaderActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
		 	}
		 });
		 fCostTypeLinearLayout.addView(buttonLayout);
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
		commonMenu.setContext(ExpenseCostTHeaderActivity.this);
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
