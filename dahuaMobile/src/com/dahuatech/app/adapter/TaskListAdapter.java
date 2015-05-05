package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName TaskListAdapter
 * @Description 任务列表适配器类
 * @author 21291
 * @date 2014年4月23日 上午11:08:42
 */
public class TaskListAdapter extends MyBaseAdapter<TaskInfo> {

	private TaskListItemView listItemView=null; // 自定义视图
	 
	private String fStatus;					// 默认保存记录状态 0-待审批 1-已审批
	public TaskListAdapter(Context context, List<TaskInfo> data,int resource,String fStatus) {
		super(context, data, resource);
		this.fStatus=fStatus;
	}

	//自定义控件集合
	static class TaskListItemView{
		public ImageButton tl_fitemsIcon;
		public TextView tl_fClassTypeName;
		public TextView tl_fTitle;
		public TextView tl_fSendTime;
		public TextView tl_fSender;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取tasklistlayout布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new TaskListItemView();
			
			//获取控件对象
			listItemView.tl_fitemsIcon=(ImageButton) convertView.findViewById(R.id.tasklistlayout_itemsIcon);
			listItemView.tl_fClassTypeName=(TextView) convertView.findViewById(R.id.tasklistlayout_FClassTypeName);
			listItemView.tl_fTitle=(TextView) convertView.findViewById(R.id.tasklistlayout_FTitle);
			listItemView.tl_fSendTime=(TextView) convertView.findViewById(R.id.tasklistlayout_FSendTime);
			listItemView.tl_fSender=(TextView) convertView.findViewById(R.id.tasklistlayout_FSender);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			listItemView=(TaskListItemView) convertView.getTag();
		}
		// 根据position,从集合获取一条数据
		TaskInfo task=listItems.get(position);
		// 设置隐藏参数(实体类)
		listItemView.tl_fitemsIcon.setTag(task);
		listItemView.tl_fClassTypeName.setText(task.getFClassTypeName());
		listItemView.tl_fTitle.setText(task.getFTitle());
		listItemView.tl_fSendTime.setText(task.getFSendTime());
		listItemView.tl_fSender.setText(task.getFSender());
		
		listItemView.tl_fitemsIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TaskInfo newTask = (TaskInfo) v.getTag(); //Works fine here	
				UIHelper.showTaskDetail(context, newTask,fStatus);
			}
		});
		
		return convertView;
	}

}
