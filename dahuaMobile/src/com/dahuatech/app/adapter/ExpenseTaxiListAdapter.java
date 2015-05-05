package com.dahuatech.app.adapter;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.inter.IOnDeleteListioner;


/**
 * @ClassName ExpenseTaxiListAdapter
 * @Description 报销打车乘车记录信息适配器类
 * @author 21291
 * @date 2014年5月26日 下午2:43:24
 */

@SuppressLint("UseSparseArrays")
public class ExpenseTaxiListAdapter extends MyBaseAdapter<Map<String, Object>> {

	private IOnDeleteListioner mOnDeleteListioner;       // 触发删除按钮事件
	private int selectItem = -1;						// 当前选中的位置
	private SparseArray<Boolean> isSelected;  			// 用来控制CheckBox的选中状况  

    private ExpenseTaxiListView expenseTaxiListView=null;  // 自定义视图
    
    public ExpenseTaxiListAdapter(Context context, List<Map<String, Object>> data,int resource) {
		super(context, data, resource);
		isSelected=new SparseArray<Boolean>();
		initDate();
	}
    
    // 初始化isSelected的数据  
    public void initDate(){  
        for(int i=0; i< getCount();i++) {  
            getIsSelected().put(i,false);  
        }  
    } 
    
	public void setOnDeleteListioner(IOnDeleteListioner mOnDeleteListioner) {
		this.mOnDeleteListioner = mOnDeleteListioner;
	}
	
	public void setSelectItem(int selectItem) {  
	    this.selectItem = selectItem;  
	} 
	
	public SparseArray<Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(SparseArray<Boolean> isSelected) {
		this.isSelected = isSelected;
	}
	
	// 通知数据集合刷新  
	public void swapItems(List<Map<String, Object>> listItems) {
	    this.listItems = listItems;
	    notifyDataSetChanged();
	}
	
    static class ExpenseTaxiListView{
    	  public TextView endAddress;
		  public TextView startTime;
		  public TextView endTime;
		  public TextView deleteAction;
		  public CheckBox  chkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=listContainer.inflate(this.itemViewResource, null);
			expenseTaxiListView=new ExpenseTaxiListView();
			
			expenseTaxiListView.endAddress=(TextView)convertView.findViewById(R.id.expense_taxilist_layout_endAddress);
			expenseTaxiListView.startTime = (TextView)convertView.findViewById(R.id.expense_taxilist_layout_startTime);
			expenseTaxiListView.endTime =   (TextView)convertView.findViewById(R.id.expense_taxilist_layout_endTime);
			expenseTaxiListView.deleteAction=(TextView)convertView.findViewById(R.id.expense_taxilist_layout_deleteAction);
			expenseTaxiListView.chkBox = (CheckBox)convertView.findViewById(R.id.expense_taxilist_layout_chkBox);
			convertView.setTag(expenseTaxiListView); 	//设置控件集到convertView
		}
		else{ //取出以前保存在tag中的自定义视图对象		
			expenseTaxiListView=(ExpenseTaxiListView) convertView.getTag();
		}

		if(selectItem==position){  //选中状态
			convertView.findViewById(R.id.expense_taxilistlayout).setBackgroundColor(context.getResources().getColor(R.color.background_color));
			expenseTaxiListView.endAddress.setTextColor(context.getResources().getColor(R.color.active_font));
			expenseTaxiListView.startTime.setTextColor(context.getResources().getColor(R.color.active_font));
			expenseTaxiListView.endTime.setTextColor(context.getResources().getColor(R.color.active_font));
		}
		else{
			expenseTaxiListView.endAddress.setTextColor(context.getResources().getColor(R.color.default_font));
			expenseTaxiListView.startTime.setTextColor(context.getResources().getColor(R.color.default_font));
			expenseTaxiListView.endTime.setTextColor(context.getResources().getColor(R.color.default_font));
		}
			
		Map<String, Object> map=listItems.get(position);
		expenseTaxiListView.endAddress.setText(context.getString(R.string.expense_adapte_endAddress)+(String)map.get("endAddress"));
		expenseTaxiListView.startTime.setText(context.getString(R.string.expense_adapter_begin)+(String)map.get("startTime"));
		expenseTaxiListView.endTime.setText(context.getString(R.string.expense_adapter_to)+(String)map.get("endTime"));
		final OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mOnDeleteListioner != null)
					mOnDeleteListioner.onDelete(position);
			}
		};
		expenseTaxiListView.deleteAction.setOnClickListener(mOnClickListener);	
		// 根据isSelected来设置checkbox的选中状况  
		expenseTaxiListView.chkBox.setChecked(getIsSelected().get(position)); 
		expenseTaxiListView.chkBox.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v ;  	
				if(getIsSelected().get(position)){
					getIsSelected().put(position,false);  
					cb.setChecked(false); 	
				}
				else {
					getIsSelected().put(position,true);  
					cb.setChecked(true); 
				}
			}
		});
			
		return convertView;
	}
}
