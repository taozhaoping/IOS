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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.DevelopTestNetworkTBodyInfo;
import com.dahuatech.app.bean.mytask.DevelopTestNetworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.DevelopTestNetworkBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.ViewIdGenerator;
import com.dahuatech.app.inter.ICheckNextNode;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DevelopTestNetworkActivity
 * @Description 研发项目测试权限申请单据Activity
 * @author 21291
 * @date 2014年7月15日 下午5:02:26
 */
public class DevelopTestNetworkActivity extends MenuActivity implements ITaskContext,ICheckNextNode {
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus,fItemNumber;
	private TextView fBillNo,fApplyName,fDate,fApplyerDeptName,fTelphone,fApplyerPermisson,fPermissionRequre;
	private Button appButton,lowerButton;	//审批按钮,下级节点
	private TableLayout handleLayout;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fSubLinearLayout; //子类布局全局变量
	
	private float dip; //像素  相差1.5倍
	private int fixedHeight;	//高度固定值
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width,layoutHeight; // margin in dips
	private	DisplayMetrics displaymetrics; 	//像素宽度信息
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private SharedPreferences sp;  //获取登陆信息
	private AsyncTaskContext aTaskContext;		//异步请求策略类
	private LowerNodeAppCheck lowerNodeAppCheck;		//异步请求是否有下级节点策略类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developtestnetwork);
		
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DEVELOPTESTNETWORKACTIVITY;	
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

		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density;
		fSubLinearLayout=(LinearLayout)findViewById(R.id.developtestnetwork_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(DevelopTestNetworkActivity.this, DevelopTestNetworkActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(DevelopTestNetworkActivity.this, R.string.developtestnetwork_netparseerror);
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
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DevelopTestNetworkActivity.this);
		DevelopTestNetworkBusiness eBusiness= (DevelopTestNetworkBusiness)factoryBusiness.getInstance("DevelopTestNetworkBusiness",serviceUrl);
		return eBusiness.getDevelopTestNetworkTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		DevelopTestNetworkTHeaderInfo deInfo=(DevelopTestNetworkTHeaderInfo)base;
		if(!StringUtils.isEmpty(deInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.developtestnetwork_FBillNo);
			fBillNo.setText(deInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyName())){
			fApplyName=(TextView)findViewById(R.id.developtestnetwork_FApplyName);
			fApplyName.setText(deInfo.getFApplyName());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFDate())){
			fDate=(TextView)findViewById(R.id.developtestnetwork_FDate);
			fDate.setText(deInfo.getFDate());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyerDeptName())){
			fApplyerDeptName=(TextView)findViewById(R.id.developtestnetwork_FApplyerDeptName);
			fApplyerDeptName.setText(deInfo.getFApplyerDeptName());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFTelphone())){
			fTelphone=(TextView)findViewById(R.id.developtestnetwork_FTelphone);
			fTelphone.setText(deInfo.getFTelphone());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFApplyerPermisson())){
			fApplyerPermisson=(TextView)findViewById(R.id.developtestnetwork_FApplyerPermisson);
			fApplyerPermisson.setText(deInfo.getFApplyerPermisson());
		}
		
		if(!StringUtils.isEmpty(deInfo.getFPermissionRequre())){
			fPermissionRequre=(TextView)findViewById(R.id.developtestnetwork_FPermissionRequre);
			fPermissionRequre.setText(deInfo.getFPermissionRequre());
		}
	
		if(!StringUtils.isEmpty(deInfo.getFSubEntrys())){
			showSubEntrys(deInfo.getFSubEntrys());
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
	* @date 2014年7月15日 下午5:06:33
	*/
	private void showSubEntrys(final String fSubEntrys){
		try {
			int i=1;
			Type listType = new TypeToken<ArrayList<DevelopTestNetworkTBodyInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DevelopTestNetworkTBodyInfo> tBodyInfos=gson.fromJson(jsonArray.toString(), listType);
			if(tBodyInfos.size() > 0){
				for (DevelopTestNetworkTBodyInfo item : tBodyInfos) {
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
	* @param @param i     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月17日 下午5:15:24
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final DevelopTestNetworkTBodyInfo tBodyInfo,final int i){
		paddingLeft = 20; // margin in dips
		paddingRight = 10; 
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
		
		RelativeLayout reLayoutTop=new RelativeLayout(this);  //创建一个子类头部相对布局实例
		//配置相对布局实例的高度和宽度
		LayoutParams reLPTop=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,layoutHeight);	
		reLayoutTop.setLayoutParams(reLPTop);
		reLayoutTop.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		//行号
		TextView tvTop1 = new TextView(this);
		LayoutParams tvTopParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTop1.setLayoutParams(tvTopParam1);
		tvTop1.setTextAppearance(DevelopTestNetworkActivity.this,R.style.icons);
		tvTop1.setText(R.string.developtestnetwork_line);
		int tvTop1Id=getNewId();
		tvTop1.setId(tvTop1Id);		
		reLayoutTop.addView(tvTop1);
		
		//行号值
		TextView tvTop2 = new TextView(this);
		LayoutParams tvTopParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvTopParam2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tvTopParam2.addRule(RelativeLayout.RIGHT_OF, tvTop1Id);
		tvTop2.setLayoutParams(tvTopParam2);
		tvTop2.setTextAppearance(DevelopTestNetworkActivity.this,R.style.icons);
		tvTop2.setText(String.valueOf(i));
		reLayoutTop.addView(tvTop2);		
		fSubLinearLayout.addView(reLayoutTop);
		
		RelativeLayout reLayoutBottom=new RelativeLayout(this);  //创建一个子类底部相对布局实例
		//配置相对布局实例的高度和宽度
		LayoutParams reLPBottom=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayoutBottom.setLayoutParams(reLPBottom);
		reLayoutBottom.setPadding(paddingLeft, 5, 10, 5);
		reLayoutBottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.linegray_bottom));
		
		//申请权限设备IP地址名称
		TextView tvBottom1 = new TextView(this);
		LayoutParams tvBottomParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottom1.setLayoutParams(tvBottomParam1);
		tvBottom1.setPadding(paddingLeft, 0, 0, 0);
		tvBottom1.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom1.setText(R.string.developtestnetwork_ApplyForIp);
		tvBottom1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.address, 0, 0, 0);
		int tvBottom1Id=getNewId();
		tvBottom1.setId(tvBottom1Id);
		reLayoutBottom.addView(tvBottom1);
		
		//申请权限设备IP地址值
		TextView tvBottom2 = new TextView(this);
		LayoutParams tvBottomParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam2.setMargins(0, 0, 0, 5);
		tvBottomParam2.addRule(RelativeLayout.RIGHT_OF, tvBottom1Id);
		tvBottom2.setLayoutParams(tvBottomParam2);
		tvBottom2.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom2.setText(tBodyInfo.getFApplyForIp());
		reLayoutBottom.addView(tvBottom2);
		
		//目的IP地址/域名名称
		TextView tvBottom3 = new TextView(this);
		LayoutParams tvBottomParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam3.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottom3.setLayoutParams(tvBottomParam3);
		tvBottom3.setPadding(paddingLeft, 0, 0, 0);
		tvBottom3.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom3.setText(R.string.developtestnetwork_PurposeIp);
		tvBottom3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.address, 0, 0, 0);
		int tvBottom3Id=getNewId();
		tvBottom3.setId(tvBottom3Id);
		reLayoutBottom.addView(tvBottom3);
		
		//目的IP地址/域名值
		TextView tvBottom4 = new TextView(this);
		LayoutParams tvBottomParam4 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam4.setMargins(0, 0, 0, 5);
		tvBottomParam4.addRule(RelativeLayout.BELOW, tvBottom1Id);
		tvBottomParam4.addRule(RelativeLayout.RIGHT_OF, tvBottom3Id);
		tvBottom4.setLayoutParams(tvBottomParam4);
		tvBottom4.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom4.setText(tBodyInfo.getFPurposeIp());
		reLayoutBottom.addView(tvBottom4);
		
		//起始日期名称
		TextView tvBottom5 = new TextView(this);
		LayoutParams tvBottomParam5 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam5.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottom5.setLayoutParams(tvBottomParam5);
		tvBottom5.setPadding(paddingLeft, 0, 0, 0);
		tvBottom5.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		tvBottom5.setText(R.string.developtestnetwork_StartTime);
		int tvBottom5Id=getNewId();
		tvBottom5.setId(tvBottom5Id);
		reLayoutBottom.addView(tvBottom5);
		
		//起始日期值
		TextView tvBottom6 = new TextView(this);
		LayoutParams tvBottomParam6 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam6.setMargins(0, 0, 0, 5);
		tvBottomParam6.addRule(RelativeLayout.BELOW, tvBottom3Id);
		tvBottomParam6.addRule(RelativeLayout.RIGHT_OF, tvBottom5Id);
		tvBottom6.setLayoutParams(tvBottomParam6);
		tvBottom6.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom6.setText(tBodyInfo.getFStartTime());
		reLayoutBottom.addView(tvBottom6);
		
		//结束日期名称
		TextView tvBottom7 = new TextView(this);
		LayoutParams tvBottomParam7 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam7.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottom7.setLayoutParams(tvBottomParam7);
		tvBottom7.setPadding(paddingLeft, 0, 0, 0);
		tvBottom7.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconstext);
		tvBottom7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0);
		tvBottom7.setText(R.string.developtestnetwork_EndTime);
		int tvBottom7Id=getNewId();
		tvBottom7.setId(tvBottom7Id);
		reLayoutBottom.addView(tvBottom7);
		
		//结束日期值
		TextView tvBottom8 = new TextView(this);
		LayoutParams tvBottomParam8 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvBottomParam8.setMargins(0, 0, 0, 5);
		tvBottomParam8.addRule(RelativeLayout.BELOW, tvBottom5Id);
		tvBottomParam8.addRule(RelativeLayout.RIGHT_OF, tvBottom7Id);
		tvBottom8.setLayoutParams(tvBottomParam8);
		tvBottom8.setTextAppearance(DevelopTestNetworkActivity.this,R.style.iconscontent);
		tvBottom8.setText(tBodyInfo.getFEndTime());
		reLayoutBottom.addView(tvBottom8);
		
		fSubLinearLayout.addView(reLayoutBottom);
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
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
		 
		if("1".equals(fStatus)){  //说明是已审批进来
			appButton.setText(R.string.approve_imgbtnApprove_record);
			handleLayout.setVisibility(View.GONE);
		}
		else { //说明是未审批进来
			handleLayout.setVisibility(View.VISIBLE);
			Button plusButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnPlus);
			Button copyButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnCopy);
			lowerButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnNext);
		 
			//加签操作
			plusButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopTestNetworkActivity.this,"0",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developtestnetwork_title));
				}
			});
		 
			//抄送操作
			copyButton.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					UIHelper.showPlusCopy(DevelopTestNetworkActivity.this,"1",fSystemType,fClassTypeId,fBillID,fItemNumber,getResources().getString(R.string.developtestnetwork_title));
				}
			});
			
			  //下级节点操作
			  lowerNodeAppCheck=LowerNodeAppCheck.getLowerNodeAppCheck(DevelopTestNetworkActivity.this,DevelopTestNetworkActivity.this);
			  lowerNodeAppCheck.checkStatusAsync(fSystemType, fClassTypeId, fBillID, fItemNumber);
		 }
		 
		 //审批操作
		 appButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 if("0".equals(fStatus)){ //显示待审批页面
					 Intent intent = new Intent(DevelopTestNetworkActivity.this, WorkFlowActivity.class);
					 intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					 intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					 intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					 intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.developtestnetwork_title));
					 startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					 overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				 } 
				 else 
				 {//显示已审批页面
					 Intent intent = new Intent(DevelopTestNetworkActivity.this, WorkFlowBeenActivity.class);
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
		lowerNodeAppCheck.showNextNode(resultMessage, lowerButton, DevelopTestNetworkActivity.this, fSystemType, fClassTypeId, fBillID, fItemNumber, getResources().getString(R.string.developtestnetwork_title));
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
		commonMenu.setContext(DevelopTestNetworkActivity.this);
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
