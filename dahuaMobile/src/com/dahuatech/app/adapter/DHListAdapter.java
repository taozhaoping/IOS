package com.dahuatech.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHListInfo;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName DHListAdapter
 * @Description 研发工时列表适配器类
 * @author 21291
 * @date 2014年10月23日 上午9:58:36
 */
public class DHListAdapter extends MyBaseAdapter<DHListInfo> {
	
	private String fItemNumber;			//员工号
	
	public DHListAdapter(String fItemNumber,Context context, List<DHListInfo> data,int resource) {
		super(context, data, resource);
		this.fItemNumber=fItemNumber;
	}
	
	public void refreshView() {
		this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<DHListInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}

	// 自定义控件集合
	static class DHListViewHoder {
		public TextView dv_fWeekTitle;
		public TextView dv_fWeekDate;
		public TextView dv_fWeekHours;
		
		public RelativeLayout dv_fMonLayout;
		public TextView dv_fMonItem;
		public TextView dv_fMonHours;
		public ImageButton dv_fMonImage;
		
		public RelativeLayout dv_fTueLayout;
		public TextView dv_fTueItem;
		public TextView dv_fTueHours;
		public ImageButton dv_fTueImage;
		
		public RelativeLayout dv_fWedLayout;
		public TextView dv_fWedItem;
		public TextView dv_fWedHours;
		public ImageButton dv_fWedImage;
		
		public RelativeLayout dv_fThurLayout;
		public TextView dv_fThurItem;
		public TextView dv_fThurHours;
		public ImageButton dv_fThurImage;
		
		public RelativeLayout dv_fFriLayout;
		public TextView dv_fFriItem;
		public TextView dv_fFriHours;
		public ImageButton dv_fFriImage;
		
		public RelativeLayout dv_fSatLayout;
		public TextView dv_fSatItem;
		public TextView dv_fSatHours;
		public ImageButton dv_fSatImage;
		
		public RelativeLayout dv_fSunLayout;
		public TextView dv_fSunItem;
		public TextView dv_fSunHours;
		public ImageButton dv_fSunImage;
	}
	
	private DHListViewHoder dHoder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取item项布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			dHoder = new DHListViewHoder();

			// 获取控件对象
			dHoder.dv_fWeekTitle=(TextView)convertView.findViewById(R.id.dh_list_item_FWeekTitle);
			dHoder.dv_fWeekDate=(TextView)convertView.findViewById(R.id.dh_list_item_FWeekDate);
			dHoder.dv_fWeekHours=(TextView)convertView.findViewById(R.id.dh_list_item_FWeekHours);
			
			dHoder.dv_fMonLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_monday);
			dHoder.dv_fMonItem=(TextView)convertView.findViewById(R.id.dh_list_item_monday_hours);
			dHoder.dv_fMonHours=(TextView)convertView.findViewById(R.id.dh_list_item_FMonHours);
			dHoder.dv_fMonImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_monday_ImageButton);
			
			dHoder.dv_fTueLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_tuesday);
			dHoder.dv_fTueItem=(TextView)convertView.findViewById(R.id.dh_list_item_tuesday_hours);
			dHoder.dv_fTueHours=(TextView)convertView.findViewById(R.id.dh_list_item_FTueHours);
			dHoder.dv_fTueImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_tuesday_ImageButton);
			
			dHoder.dv_fWedLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_wendnesday);
			dHoder.dv_fWedItem=(TextView)convertView.findViewById(R.id.dh_list_item_wendnesday_hours);
			dHoder.dv_fWedHours=(TextView)convertView.findViewById(R.id.dh_list_item_FWedHours);
			dHoder.dv_fWedImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_wendnesday_ImageButton);
			
			dHoder.dv_fThurLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_thursday);
			dHoder.dv_fThurItem=(TextView)convertView.findViewById(R.id.dh_list_item_thursday_hours);
			dHoder.dv_fThurHours=(TextView)convertView.findViewById(R.id.dh_list_item_FThurHours);
			dHoder.dv_fThurImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_thursday_ImageButton);
			
			dHoder.dv_fFriLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_friday);
			dHoder.dv_fFriItem=(TextView)convertView.findViewById(R.id.dh_list_item_friday_hours);
			dHoder.dv_fFriHours=(TextView)convertView.findViewById(R.id.dh_list_item_FFriHours);
			dHoder.dv_fFriImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_friday_ImageButton);
			
			dHoder.dv_fSatLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_saturday);
			dHoder.dv_fSatItem=(TextView)convertView.findViewById(R.id.dh_list_item_saturday_hours);
			dHoder.dv_fSatHours=(TextView)convertView.findViewById(R.id.dh_list_item_FSatHours);
			dHoder.dv_fSatImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_saturday_ImageButton);
			
			dHoder.dv_fSunLayout=(RelativeLayout)convertView.findViewById(R.id.dh_list_item_sunday);
			dHoder.dv_fSunItem=(TextView)convertView.findViewById(R.id.dh_list_item_sunday_hours);
			dHoder.dv_fSunHours=(TextView)convertView.findViewById(R.id.dh_list_item_FSunHours);
			dHoder.dv_fSunImage=(ImageButton)convertView.findViewById(R.id.dh_list_item_sunday_ImageButton);
			
			// 设置控件集到convertView
			convertView.setTag(dHoder);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			dHoder = (DHListViewHoder) convertView.getTag();
		}

		// 根据position,从集合获取一条数据
	    final DHListInfo dhListInfo = listItems.get(position);
	    dHoder.dv_fWeekTitle.setText("第"+dhListInfo.getFWeekIndex()+"周");
	    dHoder.dv_fWeekDate.setText(dhListInfo.getFWeekDate());
	    dHoder.dv_fWeekHours.setText(dhListInfo.getFWeekHours());
	    
	    Double dMon=Double.parseDouble(dhListInfo.getFMonHours());
	    Double dTue=Double.parseDouble(dhListInfo.getFTueHours());
	    Double dWed=Double.parseDouble(dhListInfo.getFWedHours());
	    Double dThur=Double.parseDouble(dhListInfo.getFThurHours());
	    Double dFri=Double.parseDouble(dhListInfo.getFFriHours());
	    Double dSat=Double.parseDouble(dhListInfo.getFSatHours());
	    Double dSun=Double.parseDouble(dhListInfo.getFSunHours());
	    
	    if(dMon > 0){  //周一样式
	    	dHoder.dv_fMonLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fMonImage.setVisibility(View.GONE);
	    	dHoder.dv_fMonItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fMonHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fMonHours.setText(dhListInfo.getFMonHours());	
	    	dHoder.dv_fMonLayout.setClickable(true);
	    	dHoder.dv_fMonLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fMonLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fMonItem.setVisibility(View.GONE);
	    	dHoder.dv_fMonHours.setVisibility(View.GONE);
	    	dHoder.dv_fMonImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fMonImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
	    if(dTue > 0){  //周二样式
	    	dHoder.dv_fTueLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fTueImage.setVisibility(View.GONE);
	    	dHoder.dv_fTueItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fTueHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fTueHours.setText(dhListInfo.getFTueHours());	
	    	dHoder.dv_fTueLayout.setClickable(true);
	    	dHoder.dv_fTueLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	      	dHoder.dv_fTueLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fTueItem.setVisibility(View.GONE);
	    	dHoder.dv_fTueHours.setVisibility(View.GONE);
	    	dHoder.dv_fTueImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fTueImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
	    if(dWed > 0){  //周三样式
	    	dHoder.dv_fWedLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fWedImage.setVisibility(View.GONE);
	    	dHoder.dv_fWedItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fWedHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fWedHours.setText(dhListInfo.getFWedHours());	
	    	dHoder.dv_fWedLayout.setClickable(true);
	    	dHoder.dv_fWedLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fWedLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fWedItem.setVisibility(View.GONE);
	    	dHoder.dv_fWedHours.setVisibility(View.GONE);
	    	dHoder.dv_fWedImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fWedImage.setOnClickListener(new ImageBtnListener(dhListInfo));	
		}
	    
	    if(dThur > 0){  //周四样式
	    	dHoder.dv_fThurLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fThurImage.setVisibility(View.GONE);
	    	dHoder.dv_fThurItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fThurHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fThurHours.setText(dhListInfo.getFThurHours());	
	    	dHoder.dv_fThurLayout.setClickable(true);
	    	dHoder.dv_fThurLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fThurLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fThurItem.setVisibility(View.GONE);
	    	dHoder.dv_fThurHours.setVisibility(View.GONE);
	    	dHoder.dv_fThurImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fThurImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
	    if(dFri > 0){  //周五样式
	    	dHoder.dv_fFriLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fFriImage.setVisibility(View.GONE);
	     	dHoder.dv_fFriItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fFriHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fFriHours.setText(dhListInfo.getFFriHours());	
	    	dHoder.dv_fFriLayout.setClickable(true);
	    	dHoder.dv_fFriLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fFriLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fFriItem.setVisibility(View.GONE);
	    	dHoder.dv_fFriHours.setVisibility(View.GONE);
	    	dHoder.dv_fFriImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fFriImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
	    if(dSat > 0){  //周六样式
	    	dHoder.dv_fSatLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fSatImage.setVisibility(View.GONE);
	    	dHoder.dv_fSatItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSatHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSatHours.setText(dhListInfo.getFSatHours());	
	    	dHoder.dv_fSatLayout.setClickable(true);
	    	dHoder.dv_fSatLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fSatLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fSatItem.setVisibility(View.GONE);
	    	dHoder.dv_fSatHours.setVisibility(View.GONE);
	    	dHoder.dv_fSatImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSatImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
	    if(dSun > 0){  //周日样式
	     	dHoder.dv_fSunLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.calendar_bk));
	    	dHoder.dv_fSunImage.setVisibility(View.GONE);
	    	dHoder.dv_fSunItem.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSunHours.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSunHours.setText(dhListInfo.getFSunHours());	
	    	dHoder.dv_fSunLayout.setClickable(true);
	    	dHoder.dv_fSunLayout.setOnClickListener(new LayoutListener(dhListInfo));
	    }
	    else {
	    	dHoder.dv_fSunLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	dHoder.dv_fSunItem.setVisibility(View.GONE);
	    	dHoder.dv_fSunHours.setVisibility(View.GONE);
	      	dHoder.dv_fSunImage.setVisibility(View.VISIBLE);
	    	dHoder.dv_fSunImage.setOnClickListener(new ImageBtnListener(dhListInfo));
		}
	    
		return convertView;
	}
	
	/**
	 * @ClassName LayoutListener
	 * @Description RelativeLayout点击监听类
	 * @author 21291
	 * @date 2014年10月23日 下午2:50:43
	 */
	private class LayoutListener implements OnClickListener {
		private DHListInfo dListInfo;
		public LayoutListener(DHListInfo dListInfo) {
			this.dListInfo=dListInfo;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==dHoder.dv_fMonLayout.getId()){  //周一布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFMonValue(),dListInfo.getFMonDate(),"show");	
			}
			
			if(v.getId()==dHoder.dv_fTueLayout.getId()){  //周二布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFTueValue(),dListInfo.getFTueDate(),"show");	
			}
			
			if(v.getId()==dHoder.dv_fWedLayout.getId()){  //周三布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFWedValue(),dListInfo.getFWedDate(),"show");
			}
			
			if(v.getId()==dHoder.dv_fThurLayout.getId()){  //周四布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFThurValue(),dListInfo.getFThurDate(),"show");
			}
			
			if(v.getId()==dHoder.dv_fFriLayout.getId()){  //周五布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFFriValue(),dListInfo.getFFriDate(),"show");
			}
			
			if(v.getId()==dHoder.dv_fSatLayout.getId()){  //周六布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFSatValue(),dListInfo.getFSatDate(),"show");
			}
			
			if(v.getId()==dHoder.dv_fSunLayout.getId()){  //周日布局点击		
				UIHelper.showDHProjectList(context,fItemNumber,dListInfo.getFBillId(),dListInfo.getFSunValue(),dListInfo.getFSunDate(),"show");
			}
		}
	}
	
	/**
	 * @ClassName ImageBtnListener
	 * @Description 添加按钮点击监听类
	 * @author 21291
	 * @date 2014年10月23日 下午2:50:49
	 */
	private class ImageBtnListener implements OnClickListener {
		private DHListInfo dListInfo;
		public ImageBtnListener(DHListInfo dListInfo) {
			this.dListInfo=dListInfo;
		}

		@Override
		public void onClick(View v) {
			if(v.getId()==dHoder.dv_fMonImage.getId()){  //周一按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFMonDate());	
			}
			
			if(v.getId()==dHoder.dv_fTueImage.getId()){  //周二按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFTueDate());
			}
			
			if(v.getId()==dHoder.dv_fWedImage.getId()){  //周三按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFWedDate());
			}
			
			if(v.getId()==dHoder.dv_fThurImage.getId()){  //周四按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFThurDate());
			}
			
			if(v.getId()==dHoder.dv_fFriImage.getId()){  //周五按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFFriDate());
			}
			
			if(v.getId()==dHoder.dv_fSatImage.getId()){  //周六按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFSatDate());
			}
			
			if(v.getId()==dHoder.dv_fSunImage.getId()){  //周日按钮点击
				UIHelper.showDHListDetail(context,dListInfo.getFBillId(),dListInfo.getFWedValue(),fItemNumber,"Add","DhList",dListInfo.getFSunDate());
			}
		}
	}
}
