package com.dahuatech.app.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;

/**
 * @ClassName PlusCopyPersonAdapter
 * @Description 加签/抄送人员查询适配器类
 * @author 21291
 * @date 2014年9月25日 上午9:53:30
 */
@SuppressLint("UseSparseArrays")
public class PlusCopyPersonAdapter extends MyBaseAdapter<PlusCopyPersonInfo> {
    private SparseArray<Boolean> isSelected;  		//用来控制CheckBox的选中状况  	
	private PlusCopyPersonView plusCopyPersonView = null;  //自定义视图
	
	public PlusCopyPersonAdapter(Context context, List<PlusCopyPersonInfo> data,int resource,Boolean chkFlag) {
		super(context, data, resource);
		isSelected=new SparseArray<Boolean>();
		initDate(chkFlag);
	}
	
	// 初始化isSelected的数据  
    public void initDate(Boolean chkFlag){  
        for(int i=0; i< getCount();i++) {  
            getIsSelected().put(i,chkFlag);  
        }  
    } 
    
	public SparseArray<Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(SparseArray<Boolean> isSelected) {
		this.isSelected = isSelected;
	}
	
	// 刷新视图
	public void refreshView() {
	    this.notifyDataSetChanged();
	}
	
	// 刷新视图
	public void refreshView(List<PlusCopyPersonInfo> listItems) {
		this.listItems = listItems;
	    this.notifyDataSetChanged();
	}
	
	// 自定义控件集合
	static class PlusCopyPersonView {
		public TextView pc_fItemNumber;
		public TextView pc_fItemName;
		public CheckBox pc_fChkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取item项布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			plusCopyPersonView = new PlusCopyPersonView();

			// 获取控件对象
			plusCopyPersonView.pc_fItemNumber = (TextView) convertView.findViewById(R.id.pluscopy_person_item_FItemNumber);
			plusCopyPersonView.pc_fItemName = (TextView) convertView.findViewById(R.id.pluscopy_person_item_FItemName);
			plusCopyPersonView.pc_fChkBox = (CheckBox) convertView.findViewById(R.id.pluscopy_person_item_chkBox);
			
			// 设置控件集到convertView
			convertView.setTag(plusCopyPersonView);
		} else {
			// 取出以前保存在tag中的自定义视图对象
			plusCopyPersonView = (PlusCopyPersonView) convertView.getTag();
		}
		
		// 根据position,从集合获取一条数据
		PlusCopyPersonInfo pInfo = listItems.get(position);
		if("-1".equals(pInfo.getFItemNumber())){  //表示不显示复选框
			plusCopyPersonView.pc_fItemNumber.setVisibility(View.GONE);
			plusCopyPersonView.pc_fChkBox.setVisibility(View.GONE);
		}
		else{
			plusCopyPersonView.pc_fItemNumber.setVisibility(View.VISIBLE);
			plusCopyPersonView.pc_fChkBox.setVisibility(View.VISIBLE);
		}
		plusCopyPersonView.pc_fItemNumber.setText(pInfo.getFItemNumber());
		plusCopyPersonView.pc_fItemName.setText(pInfo.getFItemName());
		plusCopyPersonView.pc_fItemName.setTag(pInfo);
		
		plusCopyPersonView.pc_fChkBox.setChecked(getIsSelected().get(position));
		plusCopyPersonView.pc_fChkBox.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v ;  	
				if(getIsSelected().get(position)){
					getIsSelected().put(position,false);  
					cb.setChecked(false); 	
				}
				else {
					getIsSelected().put(position,true);  
					cb.setChecked(true); 
				}
			}
		});
		
		return convertView;
	}
}
