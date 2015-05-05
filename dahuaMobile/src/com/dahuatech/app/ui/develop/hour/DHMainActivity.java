package com.dahuatech.app.ui.develop.hour;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName DHMainActivity
 * @Description 研发工时主页面
 * @author 21291
 * @date 2014年10月21日 下午5:24:10
 */
public class DHMainActivity extends MenuActivity {
	private static DHMainActivity mInstance;
	
	private Button btnDhDetail,btnDhList,btnDhConfirmList;
	private String fItemNumber; //员工号
	private AppContext appContext; //全局Context

	public static DHMainActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mInstance=this;
		setContentView(R.layout.dh_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		//新增工时
		btnDhDetail=(Button) findViewById(R.id.dh_main_detail);
		btnDhDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showDHDetail(DHMainActivity.this,fItemNumber,"Add","DhAdd");
			}
		});
		
		//工时列表
		btnDhList=(Button) findViewById(R.id.dh_main_list);
		btnDhList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showDHList(DHMainActivity.this,fItemNumber);
			}
		});
		
		//工时确认列表
		btnDhConfirmList=(Button) findViewById(R.id.dh_main_confirm_list);
		btnDhConfirmList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showDHConfirmList(DHMainActivity.this,fItemNumber); 
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHMainActivity.this);
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
