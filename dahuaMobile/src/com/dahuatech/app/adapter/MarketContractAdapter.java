package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractInfo;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName MarketContractAdapter
 * @Description 合同搜索列表适配器类
 * @author 21291
 * @date 2015年1月28日 下午1:54:48
 */
public class MarketContractAdapter extends MyBaseAdapter<MarketContractInfo> {
	private DbManager mDbHelper;
	private MarketContractHistoryInfo marketContractHistory;    	//查询历史类
	private String fItemNumber;										//员工号	
	
	public MarketContractAdapter(Context context,List<MarketContractInfo> data, int resource,DbManager mDbHelper,MarketContractHistoryInfo marketContractHistory,String fItemNumber) {
		super(context, data, resource);
		this.mDbHelper=mDbHelper;
		this.marketContractHistory=marketContractHistory;
		this.fItemNumber=fItemNumber;
	}

	// 刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<MarketContractInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//自定义控件集合
	static class MarketContractViewHolder{
		public RelativeLayout mc_FRelativeLayout;
		public TextView mc_FCustomerName;
		public TextView mc_FContractCode;
		public TextView mc_FNodeName_Label;
		public TextView mc_FNodeName;
		public TextView mc_FTasker_Label;
		public TextView mc_FTasker;
		public ImageButton mc_FImageButton;
	}
	private MarketContractViewHolder mContractViewHolder=null;  //自定义视图
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取item项布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			mContractViewHolder=new MarketContractViewHolder();
			
			//获取控件对象
			mContractViewHolder.mc_FRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.market_contract_search_item);
			mContractViewHolder.mc_FCustomerName=(TextView)convertView.findViewById(R.id.market_contract_FCustomerName);
			mContractViewHolder.mc_FContractCode=(TextView)convertView.findViewById(R.id.market_contract_FContractCode);
			mContractViewHolder.mc_FNodeName_Label=(TextView)convertView.findViewById(R.id.market_contract_NodeName_Label);
			mContractViewHolder.mc_FNodeName=(TextView)convertView.findViewById(R.id.market_contract_FNodeName);
			mContractViewHolder.mc_FTasker_Label=(TextView)convertView.findViewById(R.id.market_contract_Tasker_Label);
			mContractViewHolder.mc_FTasker=(TextView)convertView.findViewById(R.id.market_contract_FTasker);
			mContractViewHolder.mc_FImageButton=(ImageButton)convertView.findViewById(R.id.market_contract_FImageButton);
			
			//设置控件集到convertView
			convertView.setTag(mContractViewHolder);	
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			mContractViewHolder=(MarketContractViewHolder) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final MarketContractInfo mInfo=listItems.get(position);
		mContractViewHolder.mc_FRelativeLayout.setTag(mInfo);
		if("-1".equals(mInfo.getFContractCode())){
			mContractViewHolder.mc_FContractCode.setVisibility(View.GONE);
			mContractViewHolder.mc_FNodeName_Label.setVisibility(View.GONE);
			mContractViewHolder.mc_FNodeName.setVisibility(View.GONE);
			mContractViewHolder.mc_FTasker_Label.setVisibility(View.GONE);
			mContractViewHolder.mc_FTasker.setVisibility(View.GONE);
			mContractViewHolder.mc_FImageButton.setVisibility(View.GONE);
		}
		else {
			mContractViewHolder.mc_FContractCode.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FNodeName_Label.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FNodeName.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FTasker_Label.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FTasker.setVisibility(View.VISIBLE);
			mContractViewHolder.mc_FImageButton.setVisibility(View.VISIBLE);
		}
		
		mContractViewHolder.mc_FCustomerName.setText(mInfo.getFCustomerName());
		mContractViewHolder.mc_FContractCode.setText(mInfo.getFContractCode());
		mContractViewHolder.mc_FNodeName.setText(mInfo.getFNodeName());
		mContractViewHolder.mc_FTasker.setText(mInfo.getFTasker());
		mContractViewHolder.mc_FImageButton.setOnClickListener(new ImageBtnListener(mInfo));
		return convertView;
	}
	
	/**
	 * @ClassName ImageBtnListener
	 * @Description 添加按钮点击监听类
	 * @author 21291
	 * @date 2014年10月23日 下午2:50:49
	 */
	private class ImageBtnListener implements OnClickListener {
		private MarketContractInfo contractInfo;
		public ImageBtnListener(MarketContractInfo contractInfo) {
			this.contractInfo=contractInfo;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==mContractViewHolder.mc_FImageButton.getId()){  //按钮点击
				marketContractHistory.setFContractCode(contractInfo.getFContractCode());
				marketContractHistory.setFCustomerName(contractInfo.getFCustomerName());
				mDbHelper.insertMarketContractSearch(marketContractHistory); //添加搜索记录到本地数据库中		
				UIHelper.showMarketWorkflow(context, fItemNumber, contractInfo.getFSystemType(), contractInfo.getFClassTypeID(), contractInfo.getFBillID(),"contractflowquery");
			}
		}
	}

}
