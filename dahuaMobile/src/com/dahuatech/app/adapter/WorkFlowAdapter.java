package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;

/**
 * @ClassName WorkFlowAdapter
 * @Description 工作流待审批记录适配器类
 * @author 21291
 * @date 2014年4月25日 下午1:41:39
 */
public class WorkFlowAdapter extends MyBaseAdapter<WorkFlowInfo> {

	private WorkFlowItemView listItemView=null; // 自定义视图
	
	public WorkFlowAdapter(Context context, List<WorkFlowInfo> data,int resource) {
		super(context, data, resource);
	}
	
	//自定义控件集合
	static class WorkFlowItemView{
		public TextView wf_fStepFlagName;
		public TextView wf_fUpdateTime;
		public TextView wf_fItemName;
		public TextView wf_fStatusResult;
		public TextView wf_fComment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			// 获取workflowlayout布局文件的视图
			convertView=listContainer.inflate(this.itemViewResource, null);
			listItemView=new WorkFlowItemView();
			
			//获取控件对象
			listItemView.wf_fStepFlagName=(TextView) convertView.findViewById(R.id.workflowlayout_FStepFlagName);
			listItemView.wf_fUpdateTime=(TextView) convertView.findViewById(R.id.workflowlayout_FUpdateTime);
			listItemView.wf_fItemName=(TextView) convertView.findViewById(R.id.workflowlayout_FItemName);
			listItemView.wf_fStatusResult=(TextView) convertView.findViewById(R.id.workflowlayout_FStatusResult);
			listItemView.wf_fComment=(TextView)convertView.findViewById(R.id.workflowlayout_FComment);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}
		else {
			//取出以前保存在tag中的自定义视图对象
			listItemView=(WorkFlowItemView) convertView.getTag();
		}
		// 根据position,从集合获取一条数据
		WorkFlowInfo work=listItems.get(position);
		// 把数据绑定到item界面上
		listItemView.wf_fStepFlagName.setText(work.getFStepFlagName());
		listItemView.wf_fUpdateTime.setText(work.getFUpdateTime());
		listItemView.wf_fItemName.setText(work.getFItemName());
		listItemView.wf_fStatusResult.setText(work.getFStatusResult());
		listItemView.wf_fComment.setText(work.getFComment());
		
		return convertView;
	}

	//禁用点击事件
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
