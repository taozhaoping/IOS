package com.dahuatech.app.bean.mytask;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppBackInfo
 * @Description 选取的下级节点人员实体
 * @author 21291
 * @date 2014年11月12日 下午4:11:11
 */
public class LowerNodeAppBackInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FNodeId;			//当前节点ID
	private String FNodeName;		//当前节点名称
	private String FSelectItem;		//选中的项
	private int FIsMust;			//当前节点流程是否必须要走   0-代表不必走，1-代表必须要走
	
	public String getFNodeId() {
		return FNodeId;
	}
	public void setFNodeId(String fNodeId) {
		FNodeId = fNodeId;
	}
	
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public String getFSelectItem() {
		return FSelectItem;
	}
	public void setFSelectItem(String fSelectItem) {
		FSelectItem = fSelectItem;
	}
	public int getFIsMust() {
		return FIsMust;
	}
	public void setFIsMust(int fIsMust) {
		FIsMust = fIsMust;
	}
	// 将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<LowerNodeAppBackInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (LowerNodeAppBackInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FNodeId", item.FNodeId);
					jsonObject.put("FSelectItem", item.FSelectItem);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
