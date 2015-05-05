package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTBodyInfo
 * @Description  文件审批流表体单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:27:12
 */
public class DocumentApproveTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FDocumentCode;   	//文件编号
	private String FDocumentName;		//文件名称
	private String FDocumentVersion;	//文件版本
	private String FDocumentBelong;		//所属业务
	
	public String getFDocumentCode() {
		return FDocumentCode;
	}
	public void setFDocumentCode(String fDocumentCode) {
		FDocumentCode = fDocumentCode;
	}
	public String getFDocumentName() {
		return FDocumentName;
	}
	public void setFDocumentName(String fDocumentName) {
		FDocumentName = fDocumentName;
	}
	public String getFDocumentVersion() {
		return FDocumentVersion;
	}
	public void setFDocumentVersion(String fDocumentVersion) {
		FDocumentVersion = fDocumentVersion;
	}
	public String getFDocumentBelong() {
		return FDocumentBelong;
	}
	public void setFDocumentBelong(String fDocumentBelong) {
		FDocumentBelong = fDocumentBelong;
	}
}
