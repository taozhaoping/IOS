package com.dahuatech.app.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.inter.ISpinnerListener;

/**
 * @ClassName DHWeekSpinnerDialog
 * @Description 周次 自定义弹出下拉框
 * @author 21291
 * @date 2014年10月24日 上午11:41:02
 */
public class DHWeekSpinnerDialog extends SpinnerDialog<DHWeekInfo> {

	private int fCurrentYear;					  //当前年份
	private int fCurrentWeekIndex;				  //当前周次
	private Calendar cal;						  //日期实例
	
	private List<DHWeekInfo>  weekInfoList;       //周信息集合
	private Spinner mYearSpinner;  				  //年份控件
	
	public Spinner getmYearSpinner() {
		return mYearSpinner;
	}

	public void setmYearSpinner(Spinner mYearSpinner) {
		this.mYearSpinner = mYearSpinner;
	}

	public DHWeekSpinnerDialog(Context context) {
		super(context);
	}

	public DHWeekSpinnerDialog(Context context, int theme) {
		super(context, theme);
	}

	public DHWeekSpinnerDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public DHWeekSpinnerDialog(Context context,Integer fCurrentWeekIndex, ISpinnerListener readyListener) {
		super(context, readyListener);
		this.fCurrentWeekIndex=fCurrentWeekIndex;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_spinner_dialog);
		mYearSpinner=(Spinner) findViewById (R.id.week_spinner_dialog_year);	 //年份控件   
	    mSpinner = (Spinner) findViewById (R.id.week_spinner_dialog_week);	     //周次控件
	   
	    buttonOK = (Button) findViewById(R.id.week_spinner_dialog_OK);
        buttonOK.setText(spinnerOk);
        buttonCancel = (Button) findViewById(R.id.week_spinner_dialog_Cancle);
        buttonCancel.setText(spinnerCancle);
        
        cal=new GregorianCalendar();    								//初始化日期实例
        fCurrentYear=cal.get(Calendar.YEAR);  							//获取当前年份
        yearFillData(mYearSpinner,fCurrentYear);
        
        //确定按钮点击事件
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
        	
        	@Override
            public void onClick(View v) {
                int position = mSpinner.getSelectedItemPosition();
                String itemText=(String)mSpinner.getItemAtPosition(position);
                DHWeekInfo dInfo=weekInfoList.get(position);
                mReadyListener.dHWeekOk(position,itemText, dInfo);
                DHWeekSpinnerDialog.this.dismiss();
            }
        });
        
        //取消按钮点击事件
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                DHWeekSpinnerDialog.this.dismiss();
            }
        });
	}
	
	/** 
	* @Title: yearFillData 
	* @Description: 填充年份数据源
	* @param @param spinner
	* @param @param cal
	* @param @param currentYear     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月20日 上午11:20:43
	*/
	private void yearFillData(final Spinner spinner,int currentYear){
        List<String> showYearList=new ArrayList<String>();
        showYearList.add(String.valueOf(currentYear));  	//设置当前年份
        showYearList.add(String.valueOf(currentYear-1));	//设置上一年年份
        
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,showYearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(yearAdapter);
        
        //年份下拉框监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			// 动态改变地级适配器的绑定值
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				int fYear= Integer.valueOf(spinner.getSelectedItem().toString());
				int fWeekIndex=1;
				if(fYear==fCurrentYear){
					fWeekIndex= fCurrentWeekIndex;
				}
				else{
					fWeekIndex=DateHelper.getNumWeeksForYear(cal, fYear);
				}
				weekInfoList=DateHelper.getDHWeekList(cal,fYear,fWeekIndex);  //初始化周信息集合
				weekFillData(mSpinner,weekInfoList);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
	}
	
	/** 
	* @Title: weekFillData 
	* @Description: 填充周次数据源
	* @param @param spinner
	* @param @param list      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月20日 上午11:19:00
	*/
	private void weekFillData(final Spinner spinner,final List<DHWeekInfo> list){
        showList=new ArrayList<String>();
        String showWeekItem="";
        //添加数据源集合
        for (DHWeekInfo item : list) {
        	showWeekItem=String.valueOf(item.FIndex)+"周"+" "+"["+item.FStartDate+"-"+item.FEndDate+"]";
        	showList.add(showWeekItem);
		}  
       
        ArrayAdapter<String> weekAdapter = new ArrayAdapter<String> (mContext,android.R.layout.simple_spinner_item,showList);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(weekAdapter);
	}
}
