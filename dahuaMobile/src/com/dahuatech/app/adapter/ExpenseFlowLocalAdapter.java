package com.dahuatech.app.adapter;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;

/**
 * @ClassName ExpenseFlowLocalAdapter
 * @Description 本地待上传列表适配器类
 * @author 21291
 * @date 2014年9月9日 上午9:55:51
 */
@SuppressLint("UseSparseArrays")
public class ExpenseFlowLocalAdapter extends MyBaseAdapter<ExpenseFlowDetailInfo> {

	private String[] fTravelLabelArray;					//差旅费标签值数组
	private String[] fSocializeLabelArray;				//交际应酬费标签值数组
	private String[] fTravelValueArray;					//差旅费实际值数组
	private String[] fSocializeValueArray;				//交际应酬费实际值数组
	
	private int selectItem = -1;						//当前选中的位置
    private SparseArray<Boolean> isSelected;  			//用来控制CheckBox的选中状况  
    
    private ExpenseFlowLocalView eFlowLocalView=null;	//自定义视图

	public ExpenseFlowLocalAdapter(Context context,List<ExpenseFlowDetailInfo> data, int resource) {
		super(context, data, resource);
		fTravelLabelArray=context.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=context.getResources().getStringArray(R.array.socialize_labels_array);
		fTravelValueArray=context.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=context.getResources().getStringArray(R.array.socialize_value_array);
		isSelected=new SparseArray<Boolean>();
		initDate();
	}

    public void initDate(){  
        for(int i=0; i< getCount();i++) {  
            getIsSelected().put(i,false);
        }  
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

	//刷新视图
	public void refreshView(List<ExpenseFlowDetailInfo> listItems) {
	    this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}

	//自定义控件集合
	static class ExpenseFlowLocalView{
		public RelativeLayout ef_fRelativeLayout;
		public TextView ef_fExpendTime;
		public TextView ef_fExpendType;
		public TextView ef_fCause;
		public TextView ef_fExpendAddress;
		public TextView ef_fExpendAmount;
		public CheckBox ef_fChkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取item项布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			eFlowLocalView=new ExpenseFlowLocalView();
			
			//获取控件对象
			eFlowLocalView.ef_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.expense_flowlocal_list_item);
			eFlowLocalView.ef_fExpendTime=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendTime);
			eFlowLocalView.ef_fExpendType=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendType);
			eFlowLocalView.ef_fCause=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FCause);
			eFlowLocalView.ef_fExpendAddress=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendAddress);
			eFlowLocalView.ef_fExpendAmount=(TextView)convertView.findViewById(R.id.expense_flowlocal_list_item_FExpendAmount);
			eFlowLocalView.ef_fChkBox=(CheckBox)convertView.findViewById(R.id.expense_flowlocal_list_item_chkBox);
			
			//设置控件集到convertView
			convertView.setTag(eFlowLocalView);	
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			eFlowLocalView=(ExpenseFlowLocalView) convertView.getTag();
		}
		
		if(selectItem==position){  //选中状态
			convertView.findViewById(R.id.expense_flowlocal_list_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
			eFlowLocalView.ef_fExpendTime.setTextColor(context.getResources().getColor(R.color.active_font));
			eFlowLocalView.ef_fExpendType.setTextColor(context.getResources().getColor(R.color.active_font));
			eFlowLocalView.ef_fCause.setTextColor(context.getResources().getColor(R.color.active_font));
		}
		else{ //未选中状态
			convertView.findViewById(R.id.expense_flowlocal_list_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			eFlowLocalView.ef_fExpendTime.setTextColor(context.getResources().getColor(R.color.default_font));
			eFlowLocalView.ef_fExpendType.setTextColor(context.getResources().getColor(R.color.default_font));
			eFlowLocalView.ef_fCause.setTextColor(context.getResources().getColor(R.color.default_font));
			getIsSelected().put(position,false);  
		}
		
		// 根据position,从集合获取一条数据
		ExpenseFlowDetailInfo eInfo=listItems.get(position);
		eFlowLocalView.ef_fRelativeLayout.setTag(eInfo);
		eFlowLocalView.ef_fExpendTime.setText(eInfo.getFExpendTime());
		eFlowLocalView.ef_fExpendType.setText(getExpendType(eInfo.getFExpendTypeParent(),eInfo.getFExpendTypeChild()));
		eFlowLocalView.ef_fCause.setText(eInfo.getFCause());
		eFlowLocalView.ef_fExpendAddress.setText(eInfo.getFExpendAddress());
		eFlowLocalView.ef_fExpendAmount.setText(eInfo.getFExpendAmount());
		
		eFlowLocalView.ef_fChkBox.setChecked(getIsSelected().get(position));
		eFlowLocalView.ef_fChkBox.setOnClickListener(new View.OnClickListener() {	
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
	
	/** 
	* @Title: getExpendType 
	* @Description: 根据父类和子类ID，获取消费类型
	* @param @param fExpendTypeParent
	* @param @param fExpendTypeChild
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 上午10:14:28
	*/
	private String getExpendType(String fExpendTypeParent,String fExpendTypeChild){
		String expendType="";
		int fChildPosition;
		if("2006".equals(fExpendTypeParent)){  //差旅费
			fChildPosition=Arrays.asList(fTravelValueArray).indexOf(fExpendTypeChild);
			expendType=fTravelLabelArray[fChildPosition];
		}
		else  //交际应酬费
		{
			fChildPosition=Arrays.asList(fSocializeValueArray).indexOf(fExpendTypeChild);
			expendType=fSocializeLabelArray[fChildPosition];
		}	
		return expendType;
	}
}
