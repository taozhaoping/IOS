package com.dahuatech.app.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName ExpandableListAdapter
 * @Description 我的流水列表适配器
 * @author 21291
 * @date 2014年8月27日 下午1:45:21
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private String[] fTravelLabelArray;			//差旅费标签值数组
	private String[] fSocializeLabelArray;		//交际应酬费标签值数组
	private String[] fTravelValueArray;			//差旅费实际值数组
	private String[] fSocializeValueArray;		//交际应酬费实际值数组
	
	private Context context;
	private List<String> listDataHeader; //头部标题
	private HashMap<String, List<ExpenseFlowDetailInfo>> listDataChild; //子集集合
	private String fItemNumber;			 //员工号
	private int parentItem = -1;		 		//当前选中的父类
	private int childItem=-1;					//当前选中的子类

	private ChildViewHolder cViewHolder=null;   //自定义子类视图
	private GroupViewHolder gviewHolder=null;	//自定义标题视图

	public ExpandableListAdapter(Context context, List<String> listDataHeader,HashMap<String, List<ExpenseFlowDetailInfo>> listChildData,String fItemNumber) {
		fTravelLabelArray=context.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=context.getResources().getStringArray(R.array.socialize_labels_array);
		fTravelValueArray=context.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=context.getResources().getStringArray(R.array.socialize_value_array);
		
		this.context = context;
		this.listDataHeader = listDataHeader;
		this.listDataChild = listChildData;
		this.fItemNumber=fItemNumber;
	}
	
	public void setParentItem(int parentItem) {
		this.parentItem = parentItem;
	}

	public void setChildItem(int childItem) {
		this.childItem = childItem;
	}
	
	//刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}
		
	//刷新视图
	public void refreshView(HashMap<String, List<ExpenseFlowDetailInfo>> listItems) {
		this.listDataChild = listItems;
		setParentItem(-1);
		setChildItem(-1);
	    this.notifyDataSetChanged();
	}
	
	//标题自定义控件
	static class GroupViewHolder{
		TextView gv_title;
		Button gv_btnAdd;
	}
	
	//子类自定义控件集合
	static class ChildViewHolder{
		TextView cv_fExpendType;
		TextView cv_fExpendAddress;
		TextView cv_fExpendAmount;
		TextView cv_fCause;
	}

	public Object getChild(int groupPosition, int childPosititon) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
	}

	//获取与在给定组给予孩子相关的数据
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	//设置子选项样式  
	@SuppressLint("InflateParams")
	public View getChildView(int groupPosition, int childPosititon,boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expense_flowlist_item, null);
			cViewHolder=new ChildViewHolder();
			
			//获取控件对象
			cViewHolder.cv_fExpendType=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendType);
			cViewHolder.cv_fExpendAddress=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendAddress);
			cViewHolder.cv_fExpendAmount=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FExpendAmount);
			cViewHolder.cv_fCause=(TextView) convertView.findViewById(R.id.expense_flowlist_item_FCause);
			
			//设置控件集到convertView
			convertView.setTag(cViewHolder);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			cViewHolder=(ChildViewHolder) convertView.getTag();
		}
		
		if(parentItem==groupPosition){  //选中状态
			if(childItem==childPosititon){  //选中状态
				convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
			}
			else{
				convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			}
		}
		else{
			convertView.findViewById(R.id.expense_flowlist_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// 根据position,从集合获取一条数据
		final ExpenseFlowDetailInfo childInfo = (ExpenseFlowDetailInfo) getChild(groupPosition, childPosititon);
		//初始化数据
		cViewHolder.cv_fExpendType.setText(getExpendType(childInfo.getFExpendTypeParent(),childInfo.getFExpendTypeChild()));
		cViewHolder.cv_fExpendAddress.setText(childInfo.getFExpendAddress());
		cViewHolder.cv_fExpendAmount.setText(childInfo.getFExpendAmount());
		cViewHolder.cv_fCause.setText(childInfo.getFCause());
		
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

	//返回在指定Group的Child数目
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
	}

	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}

	public int getGroupCount() {
		return this.listDataHeader.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expense_flowlist_group, null);
            gviewHolder=new GroupViewHolder();
        	
            //获取控件对象
            gviewHolder.gv_title=(TextView) convertView.findViewById(R.id.expense_flowlist_group_title);
            gviewHolder.gv_btnAdd=(Button) convertView.findViewById(R.id.expense_flowlist_group_btnAdd);
            convertView.setTag(gviewHolder);  //设置控件集到convertView
        }
        else {
        	//取出以前保存在tag中的自定义视图对象
        	gviewHolder=(GroupViewHolder)convertView.getTag();
		}
        //初始化数据
        String headerTitle = (String) getGroup(groupPosition);
        gviewHolder.gv_title.setTypeface(null, Typeface.BOLD);
        gviewHolder.gv_title.setText(headerTitle);
        gviewHolder.gv_btnAdd.setTag(headerTitle);
        gviewHolder.gv_btnAdd.setOnClickListener(new GroupBtnListener());
        return convertView;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	//标题按钮点击监听类
	private class GroupBtnListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			if(v.getId()==gviewHolder.gv_btnAdd.getId()){  //说明发生点击事件
				String title = (String) v.getTag();
				UIHelper.showExpenseFlowDetail(context,fItemNumber,title);
			}
		}
	}

}
