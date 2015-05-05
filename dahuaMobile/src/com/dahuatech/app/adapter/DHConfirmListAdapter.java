package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHConfirmChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmRootInfo;

/**
 * @ClassName DHConfrimAdapter
 * @Description 研发工时列表适配器
 * @author 21291
 * @date 2014年10月17日 下午3:38:34
 */
public class DHConfirmListAdapter extends BaseExpandableListAdapter {
	
	private List<DHConfirmRootInfo> listData;  				//数据源
	private final LayoutInflater inflater ;  				//视图容器
	private int viewRootResource;			 				//自定义根级项视图
	private int viewChildResource;			 				//自定义子级项视图  

	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	//根级item项
	static class RootGroupHolder {  
		CheckBox root_fCheckBox;
        TextView root_fProjectName;
    }
	private RootGroupHolder rootGroupHolder=null;
	
	//根级item项
	static class ChildViewHolder {
		RelativeLayout  child_fRelativeLayout;
		CheckBox child_fCheckBox;
        TextView child_fItemName;
    }
	private ChildViewHolder childViewHolder=null;
	
	//构造函数
	public DHConfirmListAdapter(Context context, List<DHConfirmRootInfo> listData,int rootResource,int childResource){
		this.listData = listData;
	    this.inflater = LayoutInflater.from(context);
	    this.viewRootResource=rootResource;
	    this.viewChildResource=childResource;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listData.get(groupPosition).getFChildren().get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(viewChildResource, null);
			childViewHolder=new ChildViewHolder();
			
			//获取控件对象
			childViewHolder.child_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.dh_confirm_list_child);
			childViewHolder.child_fCheckBox=(CheckBox) convertView.findViewById(R.id.dh_confirm_list_child_checkBox);
			childViewHolder.child_fItemName=(TextView) convertView.findViewById(R.id.dh_confirm_list_child_FItemName);
			
			
			//设置控件集到convertView
			convertView.setTag(childViewHolder);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			childViewHolder=(ChildViewHolder) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHConfirmChildInfo childInfo = (DHConfirmChildInfo) getChild(groupPosition, childPosition);
		//初始化数据
		childViewHolder.child_fRelativeLayout.setTag(childInfo);
		childViewHolder.child_fItemName.setText(childInfo.getFItemName());
		
		childViewHolder.child_fCheckBox.setChecked(childInfo.isChecked());
		childViewHolder.child_fCheckBox.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				childInfo.changeChecked();
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listData.get(groupPosition).getFChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.listData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(viewRootResource, parent, false);
			rootGroupHolder=new RootGroupHolder();
			
			rootGroupHolder.root_fCheckBox = (CheckBox) convertView.findViewById(R.id.dh_confirm_item_root_checkBox);
			rootGroupHolder.root_fProjectName = (TextView) convertView.findViewById(R.id.dh_confirm_item_root_FProjectName);
			
			// 设置控件集到convertView
			convertView.setTag(rootGroupHolder);
		}
		else{
			rootGroupHolder=(RootGroupHolder)convertView.getTag();
		}
		
		final DHConfirmRootInfo dRootInfo = (DHConfirmRootInfo) getGroup(groupPosition);	
		rootGroupHolder.root_fProjectName.setText(dRootInfo.getFProjectName());
		
		rootGroupHolder.root_fCheckBox.setChecked(dRootInfo.isChecked());
		rootGroupHolder.root_fCheckBox.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				dRootInfo.changeChecked();
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
	 
	
