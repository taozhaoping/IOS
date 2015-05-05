package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdBorrowTBodyInfo
 * @Description 技术文件借阅申请单表体实体
 * @author 21291
 * @date 2014年8月28日 下午4:26:19
 */
public class TdBorrowTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDocumentName;   //文件名称
	private String FDocumentUse;	//文件用途
	private String FSupportType;	//载体类别
	private String FVersion;		//版本
	private String FNote;			//备注
	
	public String getFDocumentName() {
		return FDocumentName;
	}
	public void setFDocumentName(String fDocumentName) {
		FDocumentName = fDocumentName;
	}
	public String getFDocumentUse() {
		return FDocumentUse;
	}
	public void setFDocumentUse(String fDocumentUse) {
		FDocumentUse = fDocumentUse;
	}
	public String getFSupportType() {
		return FSupportType;
	}
	public void setFSupportType(String fSupportType) {
		FSupportType = fSupportType;
	}
	public String getFVersion() {
		return FVersion;
	}
	public void setFVersion(String fVersion) {
		FVersion = fVersion;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
}
