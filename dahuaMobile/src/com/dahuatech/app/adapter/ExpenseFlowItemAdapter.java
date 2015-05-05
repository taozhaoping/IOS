package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;

/**
 * @ClassName ExpenseFlowItemAdapter
 * @Description 客户/项目列表适配器类
 * @author 21291
 * @date 2014年9月1日 下午7:18:50
 */
public class ExpenseFlowItemAdapter extends MyBaseAdapter<ExpenseFlowItemInfo> {

	private int selectItem = -1;					// 当前选中的位置
	private ExpenseFlowItemView eFlowItemView=null;  //自定义视图
	
	public ExpenseFlowItemAdapter(Context context,List<ExpenseFlowItemInfo> data, int resource) {
		super(context, data, resource);
	}
	
	public void setSelectItem(int selectItem) {  
	    this.selectItem = selectItem;  
	} 
	
	// 刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<ExpenseFlowItemInfo> listItems) {
		this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}
	
	//自定义控件集合
	static class ExpenseFlowItemView{
		public RelativeLayout ef_fRelativeLayout;
		public TextView ef_fId;
		public TextView ef_fItemName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取item项布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			eFlowItemView=new ExpenseFlowItemView();
			
			//获取控件对象
			eFlowItemView.ef_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.expense_flowsearch_layout);
			eFlowItemView.ef_fId=(TextView)convertView.findViewById(R.id.expense_flowsearch_layout_FId);
			eFlowItemView.ef_fItemName=(TextView)convertView.findViewById(R.id.expense_flowsearch_layout_FItemName);
			
			//设置控件集到convertView
			convertView.setTag(eFlowItemView);	
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			eFlowItemView=(ExpenseFlowItemView) convertView.getTag();
		}
		
		if(selectItem==position){  //选中状态
			convertView.findViewById(R.id.expense_flowsearch_layout).setBackgroundColor(context.getResources().getColor(R.color.background_color));
		}
		else{
			convertView.findViewById(R.id.expense_flowsearch_layout).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// 根据position,从集合获取一条数据
		ExpenseFlowItemInfo eInfo=listItems.get(position);
		eFlowItemView.ef_fId.setText(String.valueOf(eInfo.getFId()));
		eFlowItemView.ef_fId.setVisibility(View.GONE);
		eFlowItemView.ef_fItemName.setText(eInfo.getFItemName());
		eFlowItemView.ef_fRelativeLayout.setTag(eInfo);
		
		return convertView;
	}

}
