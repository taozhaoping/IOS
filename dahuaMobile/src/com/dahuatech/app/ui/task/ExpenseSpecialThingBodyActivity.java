package com.dahuatech.app.ui.task;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseSpecialTBodyAdapter;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.bean.mytask.ExpenseSpecialTBodyInfo;
import com.dahuatech.app.business.ExpenseSpecialThingBodyBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;


public class ExpenseSpecialThingBodyActivity extends MenuActivity {

	private String fSystemType,fBillID,fCostCode;
	private ListView mListView; 	// 列表控件对象
	private ProgressDialog dialog;	// 进程框
	
	private List<ExpenseSpecialTBodyInfo> eArrayList;  // 数据源集合
	private ExpenseSpecialTBodyAdapter epAdapter;      // 适配器类
	
	private String serviceUrl;  //服务地址
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expensespecialtbody);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSESPECIALTHINGBODYACTIVITY;	
		//获取传递参数信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fCostCode=extras.getString(AppContext.FEXPENSEPRIVATE_COSTTYPE_KEY);
		}
			
		//声明一个等待框以提示用户等待
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//初始化数据集合信息
		eArrayList=new ArrayList<ExpenseSpecialTBodyInfo>();
		new ExpenseSpecialTBodyListAsync().execute(serviceUrl);	
	}
	
	/**
	 * @ClassName ExpenseSpecialTBodyListAsync
	 * @Description 异步执行任务,获取数据结果集
	 * @author 21291
	 * @date 2014年6月20日 下午4:17:22
	 */
	private class ExpenseSpecialTBodyListAsync extends AsyncTask<String, integer, List<ExpenseSpecialTBodyInfo>> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpenseSpecialTBodyInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<ExpenseSpecialTBodyInfo> doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 加载界面数据并初始化
	* @param @param arrayList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年6月20日 下午4:18:55
	*/
	private void renderListView(List<ExpenseSpecialTBodyInfo> arrayList) {
		// 实例化XListView
		mListView = (ListView) findViewById(R.id.expensespecialtbody_ListView);
		if(arrayList.size()==0){ //如果获取不到数据
			UIHelper.ToastMessageLong(ExpenseSpecialThingBodyActivity.this, R.string.expensespecialthingbody_netparseerror);
			mListView.setAdapter(null);
			return;
		}
		eArrayList.addAll(arrayList);	
		// 使用数据集构造adapter对象
		epAdapter = new ExpenseSpecialTBodyAdapter(ExpenseSpecialThingBodyActivity.this, eArrayList,R.layout.expensespecialtbody_layout);
		// 设置XListView的adapter
		mListView.setAdapter(epAdapter);
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 过POST方式获取任务数据源
	* @param @param serviceUrl 服务地址
	* @param @return     
	* @return List<ExpenseSpecialTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014年6月20日 下午4:19:17
	*/
	private List<ExpenseSpecialTBodyInfo> getListByPost(String serviceUrl) {
		// 参数实体
		ExpensePrivateTBodyParam eParam = ExpensePrivateTBodyParam.getExpensePrivateTBodyParam();
		eParam.setFBillID(fBillID);
		eParam.setFSystemType(fSystemType);
		eParam.setFCostCode(fCostCode);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseSpecialThingBodyActivity.this);
		ExpenseSpecialThingBodyBusiness eBodyBusiness= (ExpenseSpecialThingBodyBusiness)factoryBusiness.getInstance("ExpenseSpecialThingBodyBusiness",serviceUrl);
		return eBodyBusiness.getExpenseSpecialTBodyList(eParam);
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
