package com.dahuatech.app.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;

/**
 * @ClassName ListHelper
 * @Description 集合帮助类
 * @author 21291
 * @date 2014年9月19日 上午10:10:40
 */
public class ListHelper {

	/** 
	* @Title: removeDuplicatesHs 
	* @Description: 通过HashSet来去重
	* @param @param list     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午10:28:45
	*/
	public static <T> void removeDuplicates(List<T> list) {
	    int size = list.size();
	    int out = 0;
	    {
	        final Set<T> encountered = new HashSet<T>();
	        for (int in = 0; in < size; in++) {
	            final T t = list.get(in);
	            final boolean first = encountered.add(t);
	            if (first) {
	                list.set(out++, t);
	            }
	        }
	    }
	    while (out < size) {
	        list.remove(--size);
	    }
	}
	
	/** 
	* @Title: rDMeetingPerson 
	* @Description: List<MeetingPersonInfo>集合去重
	* @param @param list
	* @param @return     
	* @return List<MeetingPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:32:27
	*/
	public static List<MeetingPersonInfo> rDMeetingPerson(List<MeetingPersonInfo> list) {
		Map<String, MeetingPersonInfo> cleanMap = new LinkedHashMap<String, MeetingPersonInfo>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			cleanMap.put(list.get(i).getFItemNumber(), list.get(i));
        }
		List<MeetingPersonInfo> returnList = new ArrayList<MeetingPersonInfo>(cleanMap.values());
	    return returnList;
	}
	
	/** 
	* @Title: rDPlusCopyPerson 
	* @Description: List<PlusCopyPersonInfo>集合去重  
	* @param @param list
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:32:48
	*/
	public static List<PlusCopyPersonInfo> rDPlusCopyPerson(List<PlusCopyPersonInfo> list) {
		Map<String, PlusCopyPersonInfo> cleanMap = new LinkedHashMap<String, PlusCopyPersonInfo>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			cleanMap.put(list.get(i).getFItemNumber(), list.get(i));
        }
		List<PlusCopyPersonInfo> returnList = new ArrayList<PlusCopyPersonInfo>(cleanMap.values());
	    return returnList;
	}
}
