package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.market.MarketWorkflowInfo;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName MarketWorkFlowAdapter
 * @Description 销售工作流审批记录适配器类
 * @author 21291
 * @date 2015年1月29日 下午2:14:04
 */
public class MarketWorkFlowAdapter extends MyBaseAdapter<MarketWorkflowInfo> {

	private MarketWorkFlowItemView listItemView=null; // 自定义视图
	
	public MarketWorkFlowAdapter(Context context, List<MarketWorkflowInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//自定义控件集合
	static class MarketWorkFlowItemView{
		public TextView mw_fStepFlagName;
		public TextView mw_fUpdateTime;
		public TextView mw_fItemName;
		public TextView mw_fShortTel;
		public TextView mw_fStatusResult;
		public TextView mw_fComment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取workflowlayout布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new MarketWorkFlowItemView();
			
			//获取控件对象
			listItemView.mw_fStepFlagName=(TextView) convertView.findViewById(R.id.market_workflowlayout_FStepFlagName);
			listItemView.mw_fUpdateTime=(TextView) convertView.findViewById(R.id.market_workflowlayout_FUpdateTime);
			listItemView.mw_fItemName=(TextView) convertView.findViewById(R.id.market_workflowlayout_FItemName);
			listItemView.mw_fShortTel=(TextView) convertView.findViewById(R.id.market_workflowlayout_FCornet);
			listItemView.mw_fStatusResult=(TextView)convertView.findViewById(R.id.market_workflowlayout_FStatusResult);
			listItemView.mw_fComment=(TextView)convertView.findViewById(R.id.market_workflowlayout_FComment);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			listItemView=(MarketWorkFlowItemView) convertView.getTag();
		}
		// 根据position,从集合获取一条数据
		MarketWorkflowInfo marketWorkflow=listItems.get(position);
		// 把数据绑定到item界面上
		listItemView.mw_fStepFlagName.setText(marketWorkflow.getFStepFlagName());
		listItemView.mw_fUpdateTime.setText(marketWorkflow.getFUpdateTime());
		listItemView.mw_fItemName.setText(marketWorkflow.getFItemName());
		listItemView.mw_fStatusResult.setText(marketWorkflow.getFStatusResult());
		listItemView.mw_fComment.setText(marketWorkflow.getFComment());
		if(!StringUtils.isEmpty(marketWorkflow.getFShortTel())){
			listItemView.mw_fShortTel.setTag(marketWorkflow);
			String udata=marketWorkflow.getFShortTel();
			SpannableString content = new SpannableString("("+udata+")");
			content.setSpan(new UnderlineSpan(), 1, udata.length()+1, 0);
			listItemView.mw_fShortTel.setText(content);
			listItemView.mw_fShortTel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MarketWorkflowInfo market=(MarketWorkflowInfo)v.getTag(); 
					//拨打电话
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+market.getFShortTel()));
					context.startActivity(intent);	
				}
			});
		}
		return convertView;
	}

	//禁用点击事件
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
