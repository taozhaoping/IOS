package com.dahuatech.app.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @ClassName ParseXmlService
 * @Description XML解析类
 * @author 21291
 * @date 2014年3月14日 上午11:19:29
 */
public class ParseXmlService
{
	public static HashMap<String, String> parseXml(InputStream inStream) throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		// 实例化一个文档构建器工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 通过文档构建器工厂获取一个文档构建器
		DocumentBuilder builder = factory.newDocumentBuilder();
		// 通过文档通过文档构建器构建一个文档实例
		Document document = builder.parse(inStream);
		//获取XML文件根节点
		Element root = document.getDocumentElement();
		//获得所有子节点
		NodeList childNodes = root.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++)
		{
			//遍历子节点
			Node childNode = (Node) childNodes.item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) childNode;
				//版本号
				if ("version".equals(childElement.getNodeName()))
				{
					hashMap.put("version",childElement.getFirstChild().getNodeValue());
				}
				//软件名称
				else if (("name".equals(childElement.getNodeName())))
				{
					hashMap.put("name",childElement.getFirstChild().getNodeValue());
				}
				//下载地址
				else if (("url".equals(childElement.getNodeName())))
				{
					hashMap.put("url",childElement.getFirstChild().getNodeValue());
				}
			}
		}
		
		return hashMap;
	}

	/** 
	* @Title: xmlPullParserTest 
	* @Description: xmlPullParser文件解析
	* @param @param xpp
	* @param @param tagId
	* @param @return     
	* @return HashMap<String,String>    
	* @throws 
	* @author 21291
	* @date 2014年4月10日 下午3:12:35
	*/
	/* 1. XmlPullParser通过next()，依次向下检查 
	 * 2. XmlPullParser是事件驱动，如果检测到 END_DOCUMENT，应当停止继续检测 
	 * 3. XmlPullParser是事件驱动，当检测到START_TAG，可以读出<AAAA bbbb=xxxx c=yyyyy>的内容，其中AAAA可通过getName()来获取，属性的个数，可通过getAttributeCount()获取，属性的名词和内容可分别通过getAttributeName(index)和getAttributeValue(index)来获取。 
	 * 4. XmlPullParser 是事件驱动，如果是END_TAG，则为</AAAA>，可以通过getName()来获得AAAA的内容 
	 * 5. 对于<entry>Hello</entry>这种方式，如果要获取中间的数值，则事件为XmlPullParser.TEXT, 可通过getText()来获取内容。 
	 * 6. 由于xml文件可能书写不正确，或者我们在解释的过程中处理不正确，应当使用异常捕获的方式来处理 
	*/  
	public static HashMap<String, String> xmlPullParser(XmlPullParser xpp,String tagName) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		//步骤1：进行特定xml文件的解析，对应第6点，应采用异常捕获防止程序出错  
        try{  
        	//步骤2：通过循环，逐步解析XML，直至xml文件结束，对应第1点和第2点 
            while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT){  
                //步骤3：获取目标tagName的解析  
                if(xpp.getEventType() == XmlPullParser.START_TAG){  
                    if(xpp.getName().equals(tagName)){  
                    	//步骤4：单独处理
                        getItems(xpp,tagName,hashMap);  
                    }  
                }  
                xpp.next();  
            }  
        } catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
		
	}
	
    /** 
    * @Title: getItems 
    * @Description: 解析tagName的内容，获取每个Item的name的值，注意对异常的处理
    * @param @param xpp
    * @param @param tagName
    * @param @param hashMap
    * @param @throws Exception     
    * @return void    
    * @throws IOException 
    * @throws XmlPullParserException 
    * @author 21291
    * @date 2014年4月10日 下午3:17:28
    */
    private static void getItems(XmlPullParser xpp,String tagName,HashMap<String, String> hashMap) throws XmlPullParserException, IOException{  
    	String strName="";
    	String strValue="";
        while(true){  
            xpp.next();  
            //<tagName> ...</tagName>的内容已经检索完毕，或者文件结束，都退出处理
            if((xpp.getEventType() == XmlPullParser.END_TAG && xpp.getName().equals(tagName))|| xpp.getEventType() == XmlPullParser.END_DOCUMENT)  
                break;  
  
            if(xpp.getEventType()==XmlPullParser.START_TAG) {  
            	//读出属性的名字和数值  
                if(xpp.getName().equals("Item")){  
                    for(int i = 0; i < xpp.getAttributeCount() ; i ++){  
                        if(xpp.getAttributeName(i).equals("name")){  
                        	strName=xpp.getAttributeValue(i);
                        	//处理<name>value</name>值
                        	xpp.next();  
                        	if(xpp.getEventType()==XmlPullParser.TEXT)  
                        		strValue=xpp.getText();  
                        	hashMap.put(strName, strValue);  
                        }  
                    }  
                }   
            }  
        }  
    }  
}
