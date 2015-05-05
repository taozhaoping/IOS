package com.dahuatech.app.ui.market;

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
 * @ClassName MarketMainActivity
 * @Description 我的销售主页面
 * @author 21291
 * @date 2015年1月26日 下午1:29:36
 */
public class MarketMainActivity extends MenuActivity {
	private static MarketMainActivity mInstance;
	
	private Button btnMarketBid,btnMarketContract,btnMarketProduct;
	private String fItemNumber; 		//员工号
	private AppContext appContext; 		//全局Context

	public static MarketMainActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mInstance=this;
		setContentView(R.layout.market_main);
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
		
		//报价查询
		btnMarketBid=(Button) findViewById(R.id.market_main_bid_search);
		btnMarketBid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketBidSearch(MarketMainActivity.this, fItemNumber);
			}
		});
		
		//合同查询
		btnMarketContract=(Button) findViewById(R.id.market_main_contract_search);
		btnMarketContract.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketContractSearch(MarketMainActivity.this, fItemNumber);
			}
		});
		
		//产品查询
		btnMarketProduct=(Button) findViewById(R.id.market_main_product_search);
		btnMarketProduct.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showMarketProductSearch(MarketMainActivity.this, fItemNumber);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketMainActivity.this);
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
