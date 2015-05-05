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
import com.dahuatech.app.adapter.ExpensePrivateTBodyAdapter;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyInfo;
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.business.ExpensePrivateTBodyBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class ExpensePrivateTBodyActivity extends MenuActivity {

	private String fSystemType,fBillID,fCostCode;
	private ListView mListView; 	// 列表控件对象
	private ProgressDialog dialog;	// 进程框
	
	private List<ExpensePrivateTBodyInfo> eArrayList;  // 数据源集合
	private ExpensePrivateTBodyAdapter epAdapter;      // 适配器类
	
	private String serviceUrl;  //服务地址
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenseprivatetbody);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEPRIVATETBODYACTIVITY;	
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
		eArrayList=new ArrayList<ExpensePrivateTBodyInfo>();
		new ExpensePrivateTBodyListAsync().execute(serviceUrl);		
	}

	/**
	 * @ClassName ExpensePrivateTBodyListAsync
	 * @Description 异步执行任务,获取数据结果集
	 * @author 21291
	 * @date 2014年5月27日 上午9:30:28
	 */
	private class ExpensePrivateTBodyListAsync extends AsyncTask<String, integer, List<ExpensePrivateTBodyInfo>> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpensePrivateTBodyInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<ExpensePrivateTBodyInfo> doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 加载界面数据并初始化
	* @param @param arrayList 程序集     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月27日 上午9:31:48
	*/
	private void renderListView(List<ExpensePrivateTBodyInfo> arrayList) {
		// 实例化XListView
		mListView = (ListView) findViewById(R.id.expenseprivatetbody_xListView);
		if(arrayList.size()==0){ //如果获取不到数据
			UIHelper.ToastMessageLong(ExpensePrivateTBodyActivity.this, R.string.expensePrivateTBody_netparseerror);
			mListView.setAdapter(null);
			return;
		}
		eArrayList.addAll(arrayList);	
		// 使用数据集构造adapter对象
		epAdapter = new ExpensePrivateTBodyAdapter(ExpensePrivateTBodyActivity.this, eArrayList,R.layout.expenseprivatetbody_layout);
		// 设置XListView的adapter
		mListView.setAdapter(epAdapter);
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 通过POST方式获取任务数据源
	* @param @param serviceUrl 服务地址
	* @param @return     
	* @return List<ExpensePrivateTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014年6月20日 下午4:19:25
	*/
	private List<ExpensePrivateTBodyInfo> getListByPost(String serviceUrl) {
		// 参数实体
		ExpensePrivateTBodyParam eParam = ExpensePrivateTBodyParam.getExpensePrivateTBodyParam();
		eParam.setFBillID(fBillID);
		eParam.setFSystemType(fSystemType);
		eParam.setFCostCode(fCostCode);
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpensePrivateTBodyActivity.this);
		ExpensePrivateTBodyBusiness eBodyBusiness= (ExpensePrivateTBodyBusiness)factoryBusiness.getInstance("ExpensePrivateTBodyBusiness",serviceUrl);
		return eBodyBusiness.getExpensePrivateTBodyList(eParam);
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
