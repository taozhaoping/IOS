package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHConfirmListChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListRootInfo;

/**
 * @ClassName DHComfirmListPersonAdapter
 * @Description 研发工时确认列表具体人员项适配器类
 * @author 21291
 * @date 2014年11月5日 下午3:10:34
 */
public class DHComfirmListPersonAdapter extends BaseExpandableListAdapter {

	private List<DHConfirmListRootInfo> listData;  		//数据源
	private final LayoutInflater inflater ;  			//视图容器
	private int pResource,tResource;         			//子集视图
	
	public DHComfirmListPersonAdapter(Context context,List<DHConfirmListRootInfo> listData,int pResource,int tResource) {
		this.listData=listData;
		this.inflater = LayoutInflater.from(context);
		this.pResource=pResource;	
		this.tResource=tResource;
	}

	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<DHConfirmListRootInfo> listItems) {
		this.listData = listItems;
	    this.notifyDataSetChanged();
	}

	// 自定义根级控件集合
	static class DHListPersonRoot {
		public TextView dr_fTypeName;
		public TextView dr_fHours;
	}	
	private DHListPersonRoot rootHoder = null;
	
	// 自定义子级控件集合
	static class DHListPersonChild {
		public TextView dc_fWeekDay;
		public TextView dc_fHours;	
	}	
	private DHListPersonChild childHoder = null;
	
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
			// 获取item项布局文件的视图
			convertView = inflater.inflate(this.tResource, null);
			childHoder = new DHListPersonChild();

			// 获取控件对象
			childHoder.dc_fWeekDay = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_child_FWeekDay);
			childHoder.dc_fHours = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_child_FHours);
			// 设置控件集到convertView
			convertView.setTag(childHoder);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			childHoder = (DHListPersonChild) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHConfirmListChildInfo dChildInfo = (DHConfirmListChildInfo) getChild(groupPosition,childPosition);
		childHoder.dc_fWeekDay.setText(dChildInfo.getFWeekDay());		
		childHoder.dc_fHours.setText(dChildInfo.getFHours()+"小时");
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
		if (convertView == null) {
			// 获取item项布局文件的视图
			convertView = inflater.inflate(this.pResource, parent, false);
			rootHoder = new DHListPersonRoot();

			// 获取控件对象
			rootHoder.dr_fTypeName = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_root_FTypeName);
			rootHoder.dr_fHours = (TextView) convertView.findViewById(R.id.dh_confirm_list_person_root_FHours);
			
			// 设置控件集到convertView
			convertView.setTag(rootHoder);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			rootHoder = (DHListPersonRoot) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHConfirmListRootInfo dRootInfo = (DHConfirmListRootInfo)getGroup(groupPosition);
		rootHoder.dr_fTypeName.setText(dRootInfo.getFTypeName());		
		rootHoder.dr_fHours.setText(dRootInfo.getFHours()+"小时");	
		
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
