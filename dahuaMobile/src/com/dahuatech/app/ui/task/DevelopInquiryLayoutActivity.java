package com.dahuatech.app.ui.task;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.business.DevelopInquiryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName DevelopInquiryLayoutActivity
 * @Description 研发中心询价单据子类信息Activity
 * @author 21291
 * @date 2014年7月18日 上午11:28:57
 */
public class DevelopInquiryLayoutActivity extends MenuActivity {

	private String fMasterialName,fSupplier,fManufacturer,fOffer,fCurrency,fOrderQuantityFrom,fOrderQuantityTo,fUnit,fOrderForward,fMini,fMiniOrder,fPayment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developinquiry_layout);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMasterialName=extras.getString(DevelopInquiryBusiness.KEY_FMASTERIALNAME);
			fSupplier=extras.getString(DevelopInquiryBusiness.KEY_FSUPPLIER);
			fManufacturer=extras.getString(DevelopInquiryBusiness.KEY_FMANUFACTURER);
			fOffer=extras.getString(DevelopInquiryBusiness.KEY_FOFFER);
			fCurrency=extras.getString(DevelopInquiryBusiness.KEY_FCURRENCY);
			fOrderQuantityFrom=extras.getString(DevelopInquiryBusiness.KEY_FORDERQUANTITYFROM);
			fOrderQuantityTo=extras.getString(DevelopInquiryBusiness.KEY_FORDERQUANTITYTO);
			fUnit=extras.getString(DevelopInquiryBusiness.KEY_FUNIT);
			fOrderForward=extras.getString(DevelopInquiryBusiness.KEY_FORDERFORWARD);
			fMini=extras.getString(DevelopInquiryBusiness.KEY_FMINI);
			fMiniOrder=extras.getString(DevelopInquiryBusiness.KEY_FMINIORDER);
			fPayment=extras.getString(DevelopInquiryBusiness.KEY_FPAYMENT);
			init();
		}
	}
	
	/** 
	* @Title: init 
	* @Description: 初始化信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月18日 下午1:35:26
	*/
	private void init(){
		if(!StringUtils.isEmpty(fMasterialName)){
			TextView fMasterialNameText=(TextView)findViewById(R.id.developinquiry_layout_FMasterialName);
			fMasterialNameText.setText(fMasterialName);
		}
		
		if(!StringUtils.isEmpty(fSupplier)){
			TextView fSupplierText=(TextView)findViewById(R.id.developinquiry_layout_FSupplier);
			fSupplierText.setText(fSupplier);
		}
		
		if(!StringUtils.isEmpty(fManufacturer)){
			TextView fManufacturerText=(TextView)findViewById(R.id.developinquiry_layout_FManufacturer);
			fManufacturerText.setText(fManufacturer);
		}
		
		if(!StringUtils.isEmpty(fOffer)){
			TextView fOfferText=(TextView)findViewById(R.id.developinquiry_layout_FOffer);
			fOfferText.setText(fOffer);
		}
		
		if(!StringUtils.isEmpty(fCurrency)){
			TextView fCurrencyText=(TextView)findViewById(R.id.developinquiry_layout_FCurrency);
			fCurrencyText.setText(fCurrency);
		}
		
		if(!StringUtils.isEmpty(fUnit)){
			TextView fUnitText=(TextView)findViewById(R.id.developinquiry_layout_FUnit);
			fUnitText.setText(fUnit);
		}
		
		if(!StringUtils.isEmpty(fOrderQuantityFrom)){
			TextView fOrderQuantityFromText=(TextView)findViewById(R.id.developinquiry_layout_FOrderQuantityFrom);
			fOrderQuantityFromText.setText(fOrderQuantityFrom);
		}
		
		if(!StringUtils.isEmpty(fOrderQuantityTo)){
			TextView fOrderQuantityToText=(TextView)findViewById(R.id.developinquiry_layout_FOrderQuantityTo);
			fOrderQuantityToText.setText(fOrderQuantityTo);
		}
		
		if(!StringUtils.isEmpty(fMini)){
			TextView fMiniText=(TextView)findViewById(R.id.developinquiry_layout_FMini);
			fMiniText.setText(fMini);
		}
		
		if(!StringUtils.isEmpty(fMiniOrder)){
			TextView fMiniOrderText=(TextView)findViewById(R.id.developinquiry_layout_FMiniOrder);
			fMiniOrderText.setText(fMiniOrder);
		}
		
		if(!StringUtils.isEmpty(fOrderForward)){
			TextView fOrderForwardText=(TextView)findViewById(R.id.developinquiry_layout_FOrderForward);
			fOrderForwardText.setText(fOrderForward);
		}
		
		if(!StringUtils.isEmpty(fPayment)){
			TextView fPaymentText=(TextView)findViewById(R.id.developinquiry_layout_FPayment);
			fPaymentText.setText(fPayment);
		}
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
