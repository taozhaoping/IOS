package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHTypeInfo;

/**
 * @ClassName DHTypeAdapter
 * @Description 研发工时模块 任务类型列表适配器类
 * @author 21291
 * @date 2014年11月4日 上午9:55:02
 */
public class DHTypeAdapter extends MyBaseAdapter<DHTypeInfo> {
	
	public DHTypeAdapter(Context context,List<DHTypeInfo> data, int resource) {
		super(context, data, resource);
	}

	// 刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<DHTypeInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	//自定义控件集合
	static class DHTypeViewHolder{
		public RelativeLayout dv_fRelativeLayout;
		public TextView dv_fTypeId;
		public TextView dv_fTypeName;
	}
	private DHTypeViewHolder dTypeViewHolder=null;  //自定义视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取item项布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			dTypeViewHolder=new DHTypeViewHolder();
			
			//获取控件对象
			dTypeViewHolder.dv_fRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.dh_type_list_item);
			dTypeViewHolder.dv_fTypeId=(TextView)convertView.findViewById(R.id.dh_type_list_item_FTypeId);
			dTypeViewHolder.dv_fTypeName=(TextView)convertView.findViewById(R.id.dh_type_list_item_FTypeName);
			
			//设置控件集到convertView
			convertView.setTag(dTypeViewHolder);	
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			dTypeViewHolder=(DHTypeViewHolder) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		final DHTypeInfo dInfo=listItems.get(position);
		dTypeViewHolder.dv_fRelativeLayout.setTag(dInfo);
		dTypeViewHolder.dv_fTypeId.setText(dInfo.getFTypeId());
		dTypeViewHolder.dv_fTypeName.setText(dInfo.getFTypeName());
		return convertView;
	}

}
