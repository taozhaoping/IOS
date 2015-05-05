package com.dahuatech.app.widget;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.mytask.LowerNodeAppResultInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppSpinnerInfo;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.ui.task.LowerNodeApproveActivity;

/**
 * @ClassName MultiSelectionSpinner
 * @Description 下拉框多选控件
 * @author 21291
 * @date 2014年11月6日 上午11:37:34
 */
@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
public class MultiSelectionSpinner extends Spinner implements OnMultiChoiceClickListener{
	
	private String[] _items = null;  					//item元素数组
	private boolean[] mSelection = null;  				//选中布尔值数组
	private ArrayAdapter<String> simple_adapter;		//简单适配器
	
	private LowerNodeApproveActivity lNodeApprove;		//调用实体类
	private String spinnerType;							//下拉框类型 
	private int spinnerIndex,roleSpinnerCount;			//下拉框索引,角色下拉框总数
	
	private String FNodeName;							//该节点名称
	private TextView resultView;						//总的结果文本控件
	
	public LowerNodeApproveActivity getlNodeApprove() {
		return lNodeApprove;
	}

	public void setlNodeApprove(LowerNodeApproveActivity lNodeApprove) {
		this.lNodeApprove = lNodeApprove;
	}
	
	public String getSpinnerType() {
		return spinnerType;
	}

	public void setSpinnerType(String spinnerType) {
		this.spinnerType = spinnerType;
	}

	public int getSpinnerIndex() {
		return spinnerIndex;
	}

	public void setSpinnerIndex(int spinnerIndex) {
		this.spinnerIndex = spinnerIndex;
	}

	public int getRoleSpinnerCount() {
		return roleSpinnerCount;
	}

	public void setRoleSpinnerCount(int roleSpinnerCount) {
		this.roleSpinnerCount = roleSpinnerCount;
	}
	
	public String getFNodeName() {
		return FNodeName;
	}

	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}

	public TextView getResultView() {
		return resultView;
	}

	public void setResultView(TextView resultView) {
		this.resultView = resultView;
	}

	public MultiSelectionSpinner(Context context) {
		super(context);
		simple_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);
		super.setAdapter(simple_adapter);  
	}

	public MultiSelectionSpinner(Context context, int mode) {
		super(context, mode);
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		simple_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);  
		super.setAdapter(simple_adapter); 
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
	
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs,int defStyle, int mode) {
		super(context, attrs, defStyle, mode);
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (mSelection != null && which < mSelection.length) {  
			mSelection[which] = isChecked;  
			  
			simple_adapter.clear();  
			simple_adapter.add(buildSelectedItemString());  
			
	    } else {  
			 throw new IllegalArgumentException("索引超出界限");  
		}  
	}
	
	@Override  
	public boolean performClick() {  
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());  
		builder.setMultiChoiceItems(_items, mSelection, this);  
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result=getSelectedItemsAsString();
				Map<String,LowerNodeAppSpinnerInfo> spinnerMap=lNodeApprove.getSpinnerMap();
				
				if("User".equals(spinnerType)){  //说明节点人员下拉框
					LowerNodeAppSpinnerInfo lUserSpinnerInfo =spinnerMap.get(spinnerType+"_"+"0");
					if(lUserSpinnerInfo!=null){
						lUserSpinnerInfo.setFSpinnerValue(result);
					}
				}
	        	
				if("Role".equals(spinnerType)){  //说明角色人员下拉框
					for (int i = 0; i < roleSpinnerCount; i++) {
						if(i==spinnerIndex){
							LowerNodeAppSpinnerInfo lRoleSpinnerInfo =spinnerMap.get(spinnerType+"_"+String.valueOf(spinnerIndex));
							if(lRoleSpinnerInfo!=null){
								lRoleSpinnerInfo.setFSpinnerValue(result);
							}
						}
					}		
				}
				showResultView(getCurrentNodeResult(spinnerMap));
				dialog.dismiss();			
			}
		});               
		builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();  
		return true;  
	}  
	
	/** 
	* @Title: getCurrentNodeResult 
	* @Description: 排序并获取当前节点所选择的值
	* @param @param map
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 上午11:50:29
	*/
	@SuppressWarnings("rawtypes")
	private String getCurrentNodeResult(Map<String,LowerNodeAppSpinnerInfo> map){
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;  
		
		//采用Iterator遍历HashMap  效率高
		Iterator it = map.entrySet().iterator();
        Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppSpinnerInfo lSpinnerInfo =map.get(key);
        	if(!StringUtils.isEmpty(lSpinnerInfo.getFSpinnerValue())){
        		if(foundOne){
            		sb.append(",");  
            	}
            	foundOne=true;
            	sb.append(lSpinnerInfo.getFSpinnerValue()); 
        	}	 
        }  
        return sb.toString();
	}
	
	/** 
	* @Title: showResultView 
	* @Description: 显示最终文本控件值
	* @param @param currentNodeResult     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 上午11:49:51
	*/
	private void showResultView(String currentNodeResult){
		Map<String,LowerNodeAppResultInfo> nodeValueMap=lNodeApprove.getNodeValueMap();
		LowerNodeAppResultInfo nodeResult=nodeValueMap.get(FNodeName);
		nodeResult.setFSelectResult(currentNodeResult);
		nodeResult.setFShowResult(FNodeName+":"+currentNodeResult);
		resultView.setText(getNodeValueMap(nodeValueMap)); //结果值
	}
	
	 @Override  
	 public void setAdapter(SpinnerAdapter adapter) {  
		 throw new RuntimeException("setAdapter不被支持");  
	 }
	 
	/** 
	* @Title: setItems 
	* @Description: 设置元素集合(数组参数)
	* @param @param items     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:26:36
	*/
	public void setItems(String[] items) {  
		_items = items;  
		mSelection = new boolean[_items.length];  
		simple_adapter.clear();  
		simple_adapter.add(_items[0]);  
		Arrays.fill(mSelection, false);  
	}  
		  
	/** 
	* @Title: setItems 
	* @Description: 重载-设置元素集合(集合参数)
	* @param @param items     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:27:23
	*/
	public void setItems(List<String> items) { 
		_items = items.toArray(new String[items.size()]);  
		mSelection = new boolean[_items.length];  
        simple_adapter.clear();  
		simple_adapter.add(_items[0]);  
		Arrays.fill(mSelection, false);  
	}  
	
	/** 
	* @Title: setSelection 
	* @Description: 设置选中元素(数组参数)
	* @param @param selection     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:28:40
	*/
	public void setSelection(String[] selection) {  
	    for (String cell : selection) {  
			for (int j = 0; j < _items.length; ++j) {  
				if (_items[j].equals(cell)) {  
					mSelection[j] = true;  
				}  
			}  
		}  
	} 
	
	/** 
	* @Title: setSelection 
	* @Description: 重载-选中元素集合(集合参数)
	* @param @param selection     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:30:30
	*/
	public void setSelection(List<String> selection) {  
	    for (int i = 0; i < mSelection.length; i++) {  
		    mSelection[i] = false;  
		}  
		for (String sel : selection) {  
		    for (int j = 0; j < _items.length; ++j) {  
		    	if (_items[j].equals(sel)) {  
		    		mSelection[j] = true;  
		    	}  
		    }  
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
    }  
	
	/** (非 Javadoc) 
	* <p>Title: setSelection</p> 
	* <p>Description: 设置选中项</p> 
	* @param index 索引值
	* @see android.widget.AbsSpinner#setSelection(int) 
	*/
	public void setSelection(int index) {  
		for (int i = 0; i < mSelection.length; i++) {  
	        mSelection[i] = false;  
		}  
		if (index >= 0 && index < mSelection.length) {  
		    mSelection[index] = true;  
		} else {  
		    throw new IllegalArgumentException("索引值 " + index + " 超出界限");  
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
	}  
	
	/** 
	* @Title: setSelection 
	* @Description: 设置选中项
	* @param @param selectedIndicies  数组元素    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:34:11
	*/
	public void setSelection(int[] selectedIndicies) {  
		for (int i = 0; i < mSelection.length; i++) {  
			mSelection[i] = false;  
		}  
		for (int index : selectedIndicies) {  
			if (index >= 0 && index < mSelection.length) {  
				mSelection[index] = true;  
			} else {  
				throw new IllegalArgumentException("索引值 " + index+ " 超出界限");  
			}     
		}  
		simple_adapter.clear();  
		simple_adapter.add(buildSelectedItemString());  
    }  
	
	/** 
	* @Title: getSelectedStrings 
	* @Description: 获取选中的元素值集合
	* @param @return     
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:37:10
	*/
	public List<String> getSelectedStrings() {  
		List<String> selection = new LinkedList<String>();  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				selection.add(_items[i]);  
			}  
		}  
		return selection;  
	}  
	
	/** 
	* @Title: getSelectedIndicies 
	* @Description: 获取选中的元素索引集合
	* @param @return     
	* @return List<Integer>    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:39:02
	*/
	public List<Integer> getSelectedIndicies() {  
		List<Integer> selection = new LinkedList<Integer>();  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				selection.add(i);  
			}  
		}  
		return selection;  
	}  
	
	/** 
	* @Title: buildSelectedItemString 
	* @Description: 构建选中元素字符串
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:39:38
	*/
	private String buildSelectedItemString() {  
		StringBuilder sb = new StringBuilder();  
		boolean foundOne = false;    
		
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				if (foundOne) {  
					sb.append(",");  
				}  
				foundOne = true;  	  
				sb.append(_items[i]);  
			}  
		}  
		return sb.toString();  
	} 
	
	/** 
	* @Title: getSelectedItemsAsString 
	* @Description: 获取选中元素字符串
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年11月6日 上午11:42:06
	*/
	public String getSelectedItemsAsString() {  
		StringBuilder sb = new StringBuilder();  
		boolean foundOne = false;  
		  
		for (int i = 0; i < _items.length; ++i) {  
			if (mSelection[i]) {  
				if (foundOne) {  
					sb.append(", ");  
				}  
				foundOne = true;  
				sb.append(_items[i]);  
		    }  
		}  
		return sb.toString();  
	} 
	
	@SuppressWarnings("rawtypes")
	public static String getNodeValueMap(Map<String,LowerNodeAppResultInfo> nodeValueMap){
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;  
		//采用Iterator遍历HashMap  效率高
        Iterator it = nodeValueMap.entrySet().iterator(); 
        Map.Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppResultInfo lResultInfo =nodeValueMap.get(key);
        	if(!StringUtils.isEmpty(lResultInfo.getFShowResult())){
        		if(foundOne){
            		sb.append(";");  
            	}
            	foundOne=true;
            	sb.append(lResultInfo.getFShowResult()); 
        	}	 
        }  
        return sb.toString();
	}
}
