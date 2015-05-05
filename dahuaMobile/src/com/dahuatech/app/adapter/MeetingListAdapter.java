package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.meeting.MeetingListInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName MeetingListAdapter
 * @Description 会议列表适配器类
 * @author 21291
 * @date 2014年9月11日 上午11:50:56
 */
public class MeetingListAdapter extends MyBaseAdapter<MeetingListInfo> {

	private int selectItem = -1;	// 当前选中的位置
	private MeetingListView meetingListView = null;

	public MeetingListAdapter(Context context, List<MeetingListInfo> data,int resource) {
		super(context, data, resource);
	}
	
	public void setSelectItem(int selectItem) {  
	    this.selectItem = selectItem;  
	} 
	
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<MeetingListInfo> listItems) {
		this.listItems = listItems;
	    setSelectItem(-1);
	    this.notifyDataSetChanged();
	}

	// 自定义控件集合
	static class MeetingListView {
		public RelativeLayout ml_fRelativeLayout;
		public TextView ml_fMeetingDate;
		public TextView ml_fMeetingStart;
		public TextView ml_fMeetingEnd;
		public TextView ml_fMeetingName;
		public TextView ml_fCreate;
		public ImageButton ml_btnCancle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取item项布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			meetingListView = new MeetingListView();

			// 获取控件对象
			meetingListView.ml_fRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.meeting_list_item);
			meetingListView.ml_fMeetingDate = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingDate);
			meetingListView.ml_fMeetingStart = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingStart);
			meetingListView.ml_fMeetingEnd = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingEnd);
			meetingListView.ml_fMeetingName = (TextView) convertView.findViewById(R.id.meeting_list_item_fMeetingName);
			meetingListView.ml_fCreate = (TextView) convertView.findViewById(R.id.meeting_list_item_fCreate);
			meetingListView.ml_btnCancle = (ImageButton) convertView.findViewById(R.id.meeting_list_item_btnCancle);
			// 设置控件集到convertView
			convertView.setTag(meetingListView);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			meetingListView = (MeetingListView) convertView.getTag();
		}
		
		if(selectItem==position){  //选中状态
			convertView.findViewById(R.id.meeting_list_item).setBackgroundColor(context.getResources().getColor(R.color.background_color));
		}
		else{
			convertView.findViewById(R.id.meeting_list_item).setBackgroundColor(context.getResources().getColor(R.color.white));
		}

		// 根据position,从集合获取一条数据
		MeetingListInfo mInfo = listItems.get(position);
		meetingListView.ml_fRelativeLayout.setTag(mInfo);
		meetingListView.ml_fMeetingDate.setText(mInfo.getFMeetingDate());
		meetingListView.ml_fMeetingStart.setText(mInfo.getFMeetingStart());
		meetingListView.ml_fMeetingEnd.setText(mInfo.getFMeetingEnd());
		meetingListView.ml_fMeetingName.setText(mInfo.getFMeetingName());
		meetingListView.ml_fCreate.setText(mInfo.getFCreate());

		meetingListView.ml_btnCancle.setTag(mInfo);
		if ("0".equals(mInfo.getFStatus())) { // 说明不是自己创建的会议 只有只读 取消按钮不显示
			meetingListView.ml_btnCancle.setVisibility(View.GONE);
		} else { // 取消按钮显示
			meetingListView.ml_btnCancle.setVisibility(View.VISIBLE);
			meetingListView.ml_btnCancle.setOnClickListener(new ButtonListener(position));
		}
		return convertView;
	}

	// 按钮点击监听类
	private class ButtonListener implements OnClickListener {
		private int bPos;
		private MeetingBusiness mBusiness; //业务逻辑类
		
		public ButtonListener(int pos) {
			bPos = pos;
			//初始化业务逻辑类
			FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(context);
			mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness",AppUrl.URL_API_HOST_ANDROID_CANCLEMEETINGDATA);
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==meetingListView.ml_btnCancle.getId()){  //说明发生点击事件
				MeetingListInfo meetingList = (MeetingListInfo) v.getTag();
				new cancleMeetingAsync().execute(meetingList.getFId());		
			}
		}
		
		/**
		 * @ClassName pullUpRefreshAsync
		 * @Description 上拉异步加载更多
		 * @author 21291
		 * @date 2014年9月11日 下午4:51:08
		 */
		private class cancleMeetingAsync extends AsyncTask<String, Void, ResultMessage>{

			@Override
			protected ResultMessage doInBackground(String... params) {
				return cancleByPost(params[0]); // 主要是完成耗时操作
			}

			// 完成更新UI操作
			@Override
			protected void onPostExecute(ResultMessage result) {
				if(result.isIsSuccess()){  //会议取消成功
					listItems.remove(bPos);
					UIHelper.ToastMessage(context, context.getResources().getString(R.string.meeting_cancle_success));
					
					// 延迟2秒刷新页面
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	refreshView();
			            }
			        }, 2000);	
				}
				else{
					UIHelper.ToastMessage(context, context.getResources().getString(R.string.meeting_cancle_failure));
				}
			}
		}
		
		/** 
		* @Title: cancleByPost 
		* @Description: 取消会议
		* @param @param fOrderId 会议主键内码
		* @param @return     
		* @return ResultMessage    
		* @throws 
		* @author 21291
		* @date 2014年9月19日 下午5:51:13
		*/
		private ResultMessage cancleByPost(final String fOrderId){
			return mBusiness.removeMeetingListItem(fOrderId);
		}
	}
}
