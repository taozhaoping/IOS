package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.ExpenseSpecialTBodyInfo;

/**
 * @ClassName ExpenseSpecialTBodyAdapter
 * @Description 特殊事务单据费用类型明细适配器类
 * @author 21291
 * @date 2014年6月20日 下午3:12:02
 */
public class ExpenseSpecialTBodyAdapter extends MyBaseAdapter<ExpenseSpecialTBodyInfo> {

	private ExpenseSpecialTBodyView listItemView=null; // 自定义视图
	
	public ExpenseSpecialTBodyAdapter(Context context, List<ExpenseSpecialTBodyInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//自定义控件集合
	static class ExpenseSpecialTBodyView{
		public TextView ep_fConSmDate;
		public TextView ep_fConSmType;
		public TextView ep_fAmount;
		public TextView ep_fProjectName;
		public TextView ep_fClientName;
		public TextView ep_fUse;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		if(convertView==null){
			// 获取expensespecialtbody布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new ExpenseSpecialTBodyView();
			
			//获取控件对象
			listItemView.ep_fConSmDate=(TextView) convertView.findViewById(R.id.expensespecialtbody_FConSmDate);
			listItemView.ep_fConSmType=(TextView) convertView.findViewById(R.id.expensespecialtbody_FConSmType);		
			listItemView.ep_fAmount=(TextView) convertView.findViewById(R.id.expensespecialtbody_FAmount);
			listItemView.ep_fProjectName=(TextView) convertView.findViewById(R.id.expensespecialtbody_FProjectName);
			listItemView.ep_fClientName=(TextView) convertView.findViewById(R.id.expensespecialtbody_FClientName);
			listItemView.ep_fUse=(TextView) convertView.findViewById(R.id.expensespecialtbody_FUse);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			listItemView=(ExpenseSpecialTBodyView) convertView.getTag();
		}
		// 根据position,从集合获取一条数据
		ExpenseSpecialTBodyInfo eTBodyInfo=listItems.get(position);
		// 把数据绑定到item界面上
		listItemView.ep_fConSmDate.setText(eTBodyInfo.getFConSmDate());
		listItemView.ep_fConSmType.setText(eTBodyInfo.getFConSmType());
		listItemView.ep_fAmount.setText(eTBodyInfo.getFAmount());
		listItemView.ep_fProjectName.setText(eTBodyInfo.getFProjectName());
		listItemView.ep_fClientName.setText(eTBodyInfo.getFClientName());
		listItemView.ep_fUse.setText(eTBodyInfo.getFUse());
		
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
