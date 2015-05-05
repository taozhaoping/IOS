package com.dahuatech.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dahuatech.app.R;

/**
 * @ClassName CustomDialog
 * @Description 自定义提示框
 * @author 21291
 * @date 2014年6月23日 上午11:51:27
 */
public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public static class Builder{
		private Context context;
		private String title;
		private EditText editText;
		private View contentView;
		private String positiveButtonText;
		private String negativeButtonText;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		
		public Builder(Context context) {
			this.context = context;
		}
		
		/** 
		* @Title: setTitle 
		* @Description:设置标题
		* @param @param title
		* @param @return     
		* @return Builder    
		* @throws 
		* @author 21291
		* @date 2014年6月23日 下午1:38:12
		*/
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/** 
		* @Title: setTitle 
		* @Description: 从资源ID设置标题
		* @param @param title
		* @param @return     
		* @return Builder    
		* @throws 
		* @author 21291
		* @date 2014年6月23日 下午1:38:57
		*/
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		/** 
		* @Title: getEditTextStr 
		* @Description: 获取编辑文本框值
		* @param @return     
		* @return String    
		* @throws 
		* @author 21291
		* @date 2014年6月23日 下午1:39:56
		*/
		public String getEditTextStr(){
			return editText.getText().toString();
		}
		
		/** 
		* @Title: setConteView 
		* @Description: 动态设置提示框文本内容
		* @param @param v
		* @param @return     
		* @return Builder    
		* @throws 
		* @author 21291
		* @date 2014年6月23日 下午4:46:06
		*/
		public Builder setConteView(View v){
			this.contentView = v;
			return this;
		}
		
		public Builder setPositiveButton(int positiveButtonText,DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		
		/** 
		* @Title: create 
		* @Description: 创建自定义提示框
		* @param @return     
		* @return CustomDialog    
		* @throws 
		* @author 21291
		* @date 2014年6月23日 下午1:44:52
		*/
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.customdialoglayout, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			//初始化控件
			TextView titleView=(TextView) layout.findViewById(R.id.customdialog_title);
			titleView.setText(title);
			editText= (EditText) layout.findViewById(R.id.customdialog_edittext);
			Button btnPositiveButton=(Button) layout.findViewById(R.id.customdialog_positiveButton);
			Button btnNegativeButton=(Button) layout.findViewById(R.id.customdialog_negativeButton);
			// set the confirm button
			if (positiveButtonText != null) {
				btnPositiveButton.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					btnPositiveButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
						}
					});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				btnPositiveButton.setVisibility(View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				btnNegativeButton.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					btnNegativeButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
						}
					});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				btnNegativeButton.setVisibility(View.GONE);
			}
			
			if(contentView != null){
				((LinearLayout) layout.findViewById(R.id.customdialog_contentView)).removeAllViews();
				((LinearLayout) layout.findViewById(R.id.customdialog_contentView)).addView(contentView);
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
