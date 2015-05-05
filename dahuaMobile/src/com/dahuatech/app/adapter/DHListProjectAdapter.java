package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHListProjectInfo;
import com.dahuatech.app.bean.develophour.DHListTypeInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName DHListProjectAdapter
 * @Description 研发工时列表具体项项目适配器类
 * @author 21291
 * @date 2014年10月23日 上午10:09:42
 */
public class DHListProjectAdapter extends BaseExpandableListAdapter {
	private Context context;		   				//上下文环境
	private Integer fBillId;						//周单据ID
	private String fItemNumber;						//员工号
	private String fWeekDate,fWeekValue;			//工时日期,每周值
	private List<DHListProjectInfo> listData;  		//数据源
	private final LayoutInflater inflater ;  		//视图容器
	
	private int pResource,tResource;         		//子集视图
	private int parentItem = -1;		 			//当前选中的父类
	private int childItem=-1;						//当前选中的子类
	
	public DHListProjectAdapter(Context context,int fBillId, String fWeekValue,String fItemNumber,String fWeekDate,List<DHListProjectInfo> listData,int pResource,int tResource) {
		this.context=context;
		this.fBillId=fBillId;
		this.fWeekValue=fWeekValue;
		this.fItemNumber=fItemNumber;
		this.fWeekDate=fWeekDate;
		this.listData=listData;
		this.inflater = LayoutInflater.from(context);
		this.pResource=pResource;	
		this.tResource=tResource;
	}

	public void setParentItem(int parentItem) {
		this.parentItem = parentItem;
	}

	public void setChildItem(int childItem) {
		this.childItem = childItem;
	}

	public void refreshView() {
		setParentItem(-1);
		setChildItem(-1);
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<DHListProjectInfo> listItems) {
		this.listData = listItems;
		setParentItem(-1);
		setChildItem(-1);
	    this.notifyDataSetChanged();
	}

	// 自定义控件集合
	static class DHProjectViewHoder {
		public TextView dv_fProjectName;
		public TextView dv_fHours;
	}	
	private DHProjectViewHoder pViewHoder = null;
	
	static class DHTypeViewHoder {
		public TextView dv_fTypeName;
		public TextView dv_fHours;	
		public RelativeLayout dv_fTypeReLayout;
		public Button dv_fTypeAdd;	
	}
		
	private DHTypeViewHoder tViewHoder = null;
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listData.get(groupPosition).getdListTypeInfo().get(childPosition);
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
			tViewHoder = new DHTypeViewHoder();

			// 获取控件对象
			tViewHoder.dv_fTypeName = (TextView) convertView.findViewById(R.id.dh_list_type_item_FTypeName);
			tViewHoder.dv_fHours = (TextView) convertView.findViewById(R.id.dh_list_type_item_FHours);
					
			tViewHoder.dv_fTypeReLayout = (RelativeLayout) convertView.findViewById(R.id.dh_list_type_item_FLayout);
			tViewHoder.dv_fTypeAdd=(Button)convertView.findViewById(R.id.dh_list_type_item_FAdd);
			// 设置控件集到convertView
			convertView.setTag(tViewHoder);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			tViewHoder = (DHTypeViewHoder) convertView.getTag();
		}
		
		if(parentItem==groupPosition){  //选中状态
			if(childItem==childPosition){
				convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.dh_type_background_color));
			}
			else
			{
				convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.white));
			}
		}
		else{
			convertView.findViewById(R.id.dh_list_type_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// 根据position,从集合获取一条数据
		final DHListTypeInfo dhListTypeInfo = (DHListTypeInfo) getChild(groupPosition,childPosition);
		tViewHoder.dv_fTypeName.setText(dhListTypeInfo.getFTypeName());		
		tViewHoder.dv_fHours.setText(dhListTypeInfo.getFHours()+"小时");
		
		if(isLastChild){  //如果是最后一个子项
			tViewHoder.dv_fTypeReLayout.setVisibility(View.VISIBLE);
			tViewHoder.dv_fTypeAdd.setOnClickListener(new ButtonListener(groupPosition));
		}
		else {
			tViewHoder.dv_fTypeReLayout.setVisibility(View.GONE);
		}
		return convertView;
	}	
	
	/**
	 * @ClassName ButtonListener
	 * @Description 添加按钮点击监听类
	 * @author 21291
	 * @date 2014年11月4日 下午1:30:31
	 */
	private class ButtonListener implements OnClickListener {
		private int bPosition;
		
		public ButtonListener(int position) {
			this.bPosition = position;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==tViewHoder.dv_fTypeAdd.getId()){  //点击按钮
				final DHListProjectInfo dhProjectInfo = (DHListProjectInfo)getGroup(bPosition);
				UIHelper.showDHListProjectDetail(context,fBillId,fWeekValue,fItemNumber,"Add","DhListType",fWeekDate,dhProjectInfo.getFProjectCode(),dhProjectInfo.getFProjectName(),"");
			}
		}
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listData.get(groupPosition).getdListTypeInfo().size();
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
			pViewHoder = new DHProjectViewHoder();

			// 获取控件对象
			pViewHoder.dv_fProjectName = (TextView) convertView.findViewById(R.id.dh_list_project_item_FProjectName);
			pViewHoder.dv_fHours = (TextView) convertView.findViewById(R.id.dh_list_project_item_FHours);
			
			// 设置控件集到convertView
			convertView.setTag(pViewHoder);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			pViewHoder = (DHProjectViewHoder) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHListProjectInfo dhProjectInfo = (DHListProjectInfo)getGroup(groupPosition);
		pViewHoder.dv_fProjectName.setText(dhProjectInfo.getFProjectName());		
		pViewHoder.dv_fHours.setText(dhProjectInfo.getFHours()+"小时");	
		
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
