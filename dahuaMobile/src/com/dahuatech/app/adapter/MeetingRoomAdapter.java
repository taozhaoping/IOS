package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;

/**
 * @ClassName MeetingRoomAdapter
 * @Description 选择会议室适配器类
 * @author 21291
 * @date 2014年9月11日 下午7:28:50
 */
public class MeetingRoomAdapter extends MyBaseAdapter<MeetingRoomInfo> {

	private MeetingRoomView meetingRoomView = null;

	public MeetingRoomAdapter(Context context, List<MeetingRoomInfo> data,int resource) {
		super(context, data, resource);
	}
	
	// 刷新视图
	public void refreshView() {
		this.notifyDataSetChanged();
	}

	// 自定义控件集合
	static class MeetingRoomView {
		public TextView mr_fRoomId;
		public TextView mr_fRoomName;
		public TextView mr_fRoomIp;
		public TextView mr_fType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取item项布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			meetingRoomView = new MeetingRoomView();

			// 获取控件对象
			meetingRoomView.mr_fRoomId = (TextView) convertView.findViewById(R.id.meeting_room_list_item_FRoomId);
			meetingRoomView.mr_fRoomName = (TextView) convertView.findViewById(R.id.meeting_room_list_item_FRoomName);	
			meetingRoomView.mr_fRoomIp = (TextView) convertView.findViewById(R.id.meeting_room_list_item_FRoomIp);
			meetingRoomView.mr_fType = (TextView) convertView.findViewById(R.id.meeting_room_list_item_FType);
			
			// 设置控件集到convertView
			convertView.setTag(meetingRoomView);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			meetingRoomView = (MeetingRoomView) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		MeetingRoomInfo mInfo = listItems.get(position);
		meetingRoomView.mr_fRoomId.setText(mInfo.getFRoomId());
		meetingRoomView.mr_fRoomName.setText(mInfo.getFRoomName());
		meetingRoomView.mr_fRoomIp.setText(mInfo.getFRoomIp());
		meetingRoomView.mr_fType.setTag(mInfo);
		if("0".equals(mInfo.getFType())){  //可用
			meetingRoomView.mr_fType.setText(context.getResources().getString(R.string.meeting_room_list_item_enable));
			meetingRoomView.mr_fType.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.imgbtn_blue));
		}
		else {//冲突
			meetingRoomView.mr_fType.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.imgbtn_red));
			if("-1".equals(mInfo.getFRoomId())){
				meetingRoomView.mr_fType.setText(context.getResources().getString(R.string.meeting_room_list_item_history));
			}
			else {	
				meetingRoomView.mr_fType.setText(context.getResources().getString(R.string.meeting_room_list_item_disable));
				meetingRoomView.mr_fType.setEnabled(false);
			}
		}
		
		return convertView;
	}
}
