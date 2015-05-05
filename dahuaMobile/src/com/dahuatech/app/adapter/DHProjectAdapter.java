package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHProjectInfo;

/**
 * @ClassName DHProjectAdapter
 * @Description 研发工时模块 项目搜索列表适配器类
 * @author 21291
 * @date 2014年10月30日 下午4:37:58
 */
public class DHProjectAdapter extends MyBaseAdapter<DHProjectInfo> {
	
	public DHProjectAdapter(Context context,List<DHProjectInfo> data, int resource) {
		super(context, data, resource);
	}

	// 刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<DHProjectInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//自定义控件集合
	static class DHProjectViewHolder{
		public RelativeLayout dv_fRelativeLayout;
		public TextView dv_fProjectCode;
		public TextView dv_fProjectName;
	}
	private DHProjectViewHolder dProjectViewHolder=null;  //自定义视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取item项布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			dProjectViewHolder=new DHProjectViewHolder();
			
			//获取控件对象
			dProjectViewHolder.dv_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.dh_project_search_item);
			dProjectViewHolder.dv_fProjectCode=(TextView)convertView.findViewById(R.id.dh_project_search_item_FProjectCode);
			dProjectViewHolder.dv_fProjectName=(TextView)convertView.findViewById(R.id.dh_project_search_item_FProjectName);
			
			//设置控件集到convertView
			convertView.setTag(dProjectViewHolder);	
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			dProjectViewHolder=(DHProjectViewHolder) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHProjectInfo dInfo=listItems.get(position);
		dProjectViewHolder.dv_fRelativeLayout.setTag(dInfo);
		if("-1".equals(dInfo.getFProjectCode())){
			dProjectViewHolder.dv_fProjectCode.setVisibility(View.GONE);
		}
		else {
			dProjectViewHolder.dv_fProjectCode.setVisibility(View.VISIBLE);
		}
		
		dProjectViewHolder.dv_fProjectCode.setText(dInfo.getFProjectCode());
		dProjectViewHolder.dv_fProjectName.setText(dInfo.getFProjectName());
		return convertView;
	}

}
