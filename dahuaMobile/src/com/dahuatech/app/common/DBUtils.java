package com.dahuatech.app.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ClassName DBUtils
 * @Description 数据库帮助类
 * @author 21291
 * @date 2014年5月19日 下午12:47:22
 */
public class DBUtils extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "expense"; 		// 数据库名称
	public static final String SD_DATABASE_NAME = FileUtils.getSDRoot()+ "/dahuaMobile/database/"+DATABASE_NAME; // SD卡数据库名
	public static final int DATABASE_VERSION = 5; // V1.0.4数据库版本号是1,V1.0.5数据库版本号是2,V1.0.6数据库版本号是3,V2.2.1数据库版本号是4 ,V2.4.1数据库版本号是5
	
	//报销模块
	public static final String DATABASE_TABLE = "expense_gps"; 								// 打车上传_数据库表名
	public static final String DATABASE_TABLE_CLIENT_SEARCH = "expense_client_search"; 		// 报销模块_客户列表搜索记录_数据库表名
	public static final String DATABASE_TABLE_PROJECT_SEARCH = "expense_project_search"; 	// 报销模块_项目列表搜索记录_数据库表名
	public static final String DATABASE_TABLE_FLOW_DETAIL = "expense_flow_table_detail"; 	// 报销模块_流水详情_数据库表名
	
	//会议模块
	public static final String DATABASE_TABLE_MEETING_ROOM_SEARCH = "meeting_room_search"; 		// 会议模块_会议室列表搜索记录_数据库表名
	public static final String DATABASE_TABLE_MEETING_PERSON_SEARCH = "meeting_person_search"; 	// 会议模块_人员列表搜索记录_数据库表名
	
	//加签/抄送模块
	public static final String DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH = "pluscopy_person_search"; 	// 加签/抄送模块_人员列表搜索记录_数据库表名
	
	//研发工时模块
	public static final String DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH = "dh_project_search"; 	// 研发工时模块_项目列表搜索记录_数据库表名
	
	//我的销售模块
	public static final String DATABASE_TABLE_MARKET_BID_SEARCH = "market_bid_search"; 				// 我的销售模块_报价查询记录_数据库表名
	public static final String DATABASE_TABLE_MARKET_CONTRACT_SEARCH = "market_contract_search"; 	// 我的销售模块_合同查询记录_数据库表名
	
	//通讯录模块
	public static final String DATABASE_TABLE_MARKET_CONTACT_SEARCH = "Contact_search";             //通讯录_通讯录搜索记录_数据库表名
	
	//创建_报销模块_打车上传表
	public static final String DATABASE_CREATE = "create table IF NOT EXISTS "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
			+ " ime text not null, path text, startTime text, endTime text, "
			+ " startLocation text,endLocation text,startAddress text,endAddress text,"
			+ " uploadFlag text, autoFlag text, cause text, amount text );";
	
	//创建_报销模块_客户列表搜索记录表
	public static final String DATABASE_CREATE_CLIENT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_CLIENT_SEARCH+" (_id integer primary key autoincrement, clientId varchar,clientName varchar );";
	//创建_报销模块_项目列表搜索记录表
	public static final String DATABASE_CREATE_PROJECT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_PROJECT_SEARCH+" (_id integer primary key autoincrement, projectId varchar,projectName varchar );";	
	//创建_报销模块_我的流水详情表
	public static final String DATABASE_CREATE_FLOW_DETAIL = "create table IF NOT EXISTS "+DATABASE_TABLE_FLOW_DETAIL+" (_id integer primary key autoincrement, "
			+ " fServerId integer,fPayType varchar,fItemNumber varchar, fExpendTime varchar, fExpendTypeParent varchar, fExpendTypeChild varchar, fExpendAddress text, "
			+ " fExpendAmount varchar,fCause text,fClientId varchar,fProjectId varchar,fClient varchar,fProject varchar,"
			+ " fAccompany varchar,fAccompanyReason text,"
			+ " fStart text,fDestination text,fStartTime varchar,fEndTime varchar,fBusinessLevel varchar,"
			+ " fReason varchar, fDescription text,fUploadFlag varchar );";
	
	//创建_会议模块_会议室列表搜索记录表
	public static final String DATABASE_CREATE_ROOM_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MEETING_ROOM_SEARCH+" (_id integer primary key autoincrement, roomId varchar,roomName varchar,roomIp varchar );";
	//创建_会议模块_会议人员列表搜索记录表
	public static final String DATABASE_CREATE_PERSON_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MEETING_PERSON_SEARCH+" (_id integer primary key autoincrement, fItemNumber varchar,fItemName varchar,fDeptName text );";
	
	//创建_我的任务_加签/抄送人员列表搜索记录表
	public static final String DATABASE_CREATE_PLUSCOPY_PERSON_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH+" (_id integer primary key autoincrement, fItemNumber varchar,fItemName varchar );";
	
	//创建_研发工时模块_项目列表搜索记录表
	public static final String DATABASE_CREATE_DEVELOP_HOURS_PROJECT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH+" (_id integer primary key autoincrement, projectCode varchar,projectName varchar );";
	
	//创建_我的销售模块_报价查询记录表
	public static final String DATABASE_CREATE_MARKET_BID_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MARKET_BID_SEARCH+" (_id integer primary key autoincrement, fcustomername varchar,fbidcode varchar );";
	//创建_我的销售模块_合同查询记录表
	public static final String DATABASE_CREATE_MARKET_CONTRACT_SEARCH = "create table IF NOT EXISTS "+DATABASE_TABLE_MARKET_CONTRACT_SEARCH+" (_id integer primary key autoincrement, fcustomername varchar,fcontractcode varchar );";
		
	//通讯录记录
	public static final String DATABASE_CREATE_MARKET_CONTACT_SEARCH = "CREATE TABLE " + DATABASE_TABLE_MARKET_CONTACT_SEARCH + " (_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,fItemNumber TEXT,fItemName   TEXT,fEmail      TEXT, fCornet     TEXT,fDeptName TEXT )";

	
	//删除_报销模块_打车上传
	public static final String DROP_TEMP_DATABASE_TABLE = "DROP TABLE IF EXISTS "+DATABASE_TABLE;
	//删除_报销模块_客户列表搜索记录
	public static final String DROP_TEMP_DATABASE_TABLE_CLIENT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_CLIENT_SEARCH;
	//删除_报销模块_项目列表搜索记录
	public static final String DROP_TEMP_DATABASE_TABLE_PROJECT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_PROJECT_SEARCH;
	//删除_报销模块_我的流水详情
	public static final String DROP_TEMP_DATABASE_TABLE_FLOW_DETAIL = "DROP TABLE IF EXISTS "+DATABASE_TABLE_FLOW_DETAIL;
	
	//删除_会议模块_会议室列表搜索记录表
	public static final String DROP_TEMP_DATABASE_TABLE_ROOM_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MEETING_ROOM_SEARCH;
	//删除_会议模块_会议人员列表搜索记录表
	public static final String DROP_TEMP_DATABASE_TABLE_PERSON_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MEETING_PERSON_SEARCH;
	
	//删除_我的任务_加签/抄送人员列表搜索记录表
	public static final String DROP_TEMP_DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH;

	//删除_研发工时模块_项目列表搜索记录
	public static final String DROP_TEMP_DATABASE_DEVELOP_HOURS_TABLE_CLIENT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH;
	
	//删除_我的销售模块_报价查询记录
	public static final String DROP_TEMP_DATABASE_MARKET_BID_TABLE_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_BID_SEARCH;
	//删除_我的销售模块_合同查询记录
	public static final String DROP_TEMP_DATABASE_MARKET_CONTRACT_TABLE_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_CONTRACT_SEARCH;
	
	//删除_通讯录模块_查询记录上传
	public static final String DROP_TEMP_DATABASE_TABLE_CONTACT_SEARCH = "DROP TABLE IF EXISTS "+DATABASE_TABLE_MARKET_CONTACT_SEARCH;
	
	public DBUtils(Context context) {
		//第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//SQLiteDatabase.openOrCreateDatabase(SD_DATABASE_NAME,null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);  								//创建_报销模块_打车上传表	
		db.execSQL(DATABASE_CREATE_CLIENT_SEARCH);  				//创建_报销模块_客户列表搜索记录表
		db.execSQL(DATABASE_CREATE_PROJECT_SEARCH);  				//创建_报销模块_项目列表搜索记录表
		db.execSQL(DATABASE_CREATE_FLOW_DETAIL);  					//创建_报销模块_我的流水详情表
		db.execSQL(DATABASE_CREATE_ROOM_SEARCH);  					//创建_会议模块_会议室列表搜索记录表
		db.execSQL(DATABASE_CREATE_PERSON_SEARCH);  				//创建_会议模块_会议人员列表搜索记录表
		db.execSQL(DATABASE_CREATE_PLUSCOPY_PERSON_SEARCH); 		//创建_我的任务_加签/抄送人员列表搜索记录表
		db.execSQL(DATABASE_CREATE_DEVELOP_HOURS_PROJECT_SEARCH); 	//创建_研发工时模块_项目列表搜索记录表
		db.execSQL(DATABASE_CREATE_MARKET_BID_SEARCH); 				//创建_我的销售模块_报价查询记录表
		db.execSQL(DATABASE_CREATE_MARKET_CONTRACT_SEARCH); 		//创建_我的销售模块_合同查询记录表
		db.execSQL(DATABASE_CREATE_MARKET_CONTACT_SEARCH);      //创建通讯录搜索记录表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i <= newVersion; i++) {
			switch (i) {
				case 1:
					db.execSQL(DROP_TEMP_DATABASE_TABLE);  						 		//删除_报销模块_打车上传表		
					break;	
				case 2:
					db.execSQL(DROP_TEMP_DATABASE_TABLE_CLIENT_SEARCH);  			 	//删除_报销模块_客户列表搜索记录表
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PROJECT_SEARCH);  		 		//删除_报销模块_项目列表搜索记录表
					db.execSQL(DROP_TEMP_DATABASE_TABLE_FLOW_DETAIL);  			 		//删除_报销模块_我的流水详情表
					db.execSQL(DROP_TEMP_DATABASE_TABLE_ROOM_SEARCH);  			 		//删除_会议模块_会议室列表搜索记录表
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PERSON_SEARCH);  		 		//删除_会议模块_会议人员列表搜索记录表
					db.execSQL(DROP_TEMP_DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH);	 	//删除_我的任务_加签/抄送人员列表搜索记录表
					break;
				case 3:
					db.execSQL(DROP_TEMP_DATABASE_DEVELOP_HOURS_TABLE_CLIENT_SEARCH);  	//删除_研发工时模块_项目列表搜索记录表	
					break;	
				case 4:
					db.execSQL(DROP_TEMP_DATABASE_MARKET_BID_TABLE_SEARCH); 			//删除_我的销售模块_报价查询记录表
					db.execSQL(DROP_TEMP_DATABASE_MARKET_CONTRACT_TABLE_SEARCH); 		//删除_我的销售模块_合同查询记录表
					break;
				case 5:
					db.execSQL(DROP_TEMP_DATABASE_TABLE_CONTACT_SEARCH);
					break;
				default:
					break;
				}
		}
		onCreate(db);
	}
	
	/** 
	* @Title: GetColumns 
	* @Description: 获取列名称集合
	* @param @param db
	* @param @param tableName
	* @param @return     
	* @return List<String>    
	* @throws 
	* @author 21291
	* @date 2014年5月19日 下午12:55:15
	*/
	public static List<String> GetColumns(SQLiteDatabase db, String tableName) {
	    List<String> ar = null;
	    Cursor c = null;
	    try {
	        c = db.rawQuery("select * from " + tableName + " limit 1", null);
	        if (c != null) {
	            ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (c != null)
	            c.close();
	    }
	    return ar;
	}
}
