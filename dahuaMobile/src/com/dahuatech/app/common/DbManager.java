package com.dahuatech.app.common;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.develophour.DHProjectInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchContactInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;

/**
 * @ClassName DbManager
 * @Description 数据库管理类
 * @author 21291
 * @date 2014年9月2日 上午11:22:09
 */
public class DbManager {

	public static final String KEY_ROWID = "_id"; // 主键内码
	// 报销模块
	public static final String KEY_CLIENTID = "clientId"; // 客户ID
	public static final String KEY_CLIENTNAME = "clientName"; // 客户名称
	public static final String KEY_PROJECTID = "projectId"; // 项目ID
	public static final String KEY_PROJECTNAME = "projectName"; // 项目名称

	public static final String KEY_DETAIL_FSERVERID = "fServerId"; // 服务器主机内码
	public static final String KEY_DETAIL_FPAYTYPE = "fPayType"; // 支付类型
	public static final String KEY_DETAIL_FITEMNUMBER = "fItemNumber"; // 员工工号
	public static final String KEY_DETAIL_FEXPENDTIME = "fExpendTime"; // 消费时间
	public static final String KEY_DETAIL_FEXPENDTYPEPARENT = "fExpendTypeParent"; // 消费类型父类
	public static final String KEY_DETAIL_FEXPENDTYCHILD = "fExpendTypeChild"; // 消费类型子类
	public static final String KEY_DETAIL_FEXPENDADDRESS = "fExpendAddress"; // 消费地点
	public static final String KEY_DETAIL_FEXPENDAMOUNT = "fExpendAmount"; // 消费金额
	public static final String KEY_DETAIL_FCAUSE = "fCause"; // 事由
	public static final String KEY_DETAIL_FCLIENTID = "fClientId"; // 客户
	public static final String KEY_DETAIL_FPROJECTID = "fProjectId"; // 项目
	public static final String KEY_DETAIL_FCLIENT = "fClient"; // 客户
	public static final String KEY_DETAIL_FPROJECT = "fProject"; // 项目
	public static final String KEY_DETAIL_FACCOMPANY = "fAccompany"; // 有无陪同
	public static final String KEY_DETAIL_FACCOMPANYREASON = "fAccompanyReason"; // 无陪同原因或陪同人员
	public static final String KEY_DETAIL_FSTART = "fStart"; // 出发地
	public static final String KEY_DETAIL_FDESTINATION = "fDestination"; // 目的地
	public static final String KEY_DETAIL_FSTARTTIME = "fStartTime"; // 出发时间
	public static final String KEY_DETAIL_FENDTIME = "fEndTime"; // 到达时间
	public static final String KEY_DETAIL_FBUSINESSLEVEL = "fBusinessLevel"; // 出差级别
	public static final String KEY_DETAIL_FREASON = "fReason"; // 未刷卡原因
	public static final String KEY_DETAIL_FDESCRIPTION = "fDescription"; // 说明
	public static final String KEY_DETAIL_FUPLOADFLAG = "fUploadFlag"; // 本地或已经上传标志

	// 我的会议模块
	public static final String KEY_MEETING_ROOMID = "roomId"; // 会议室主键内码
	public static final String KEY_MEETING_ROOMNAME = "roomName"; // 会议室名称
	public static final String KEY_MEETING_ROOMIP = "roomIp"; // 会议室IP

	public static final String KEY_MEETING_FITEMNUMBER = "fItemNumber"; // 员工号
	public static final String KEY_MEETING_FITEMNAME = "fItemName"; // 员工名称
	public static final String KEY_MEETING_FDEPTNAME = "fDeptName"; // 部门名称

	// 加签/抄送模块
	public static final String KEY_PLUSCOPY_FITEMNUMBER = "fItemNumber"; // 员工号
	public static final String KEY_PLUSCOPY_FITEMNAME = "fItemName"; // 员工名称

	// 研发工时模块
	public static final String KEY_DEVELOP_HOURS_PROJECTCODE = "projectCode"; // 项目编号
	public static final String KEY_DEVELOP_HOURS_PROJECTNAME = "projectName"; // 项目名称

	// 我的销售模块-报价查询
	public static final String KEY_MARKET_BID_FCUSTOMERNAME = "fcustomername"; // 客户名称
	public static final String KEY_MARKET_BID_FBIDCODE = "fbidcode"; // 报价单号

	// 我的销售模块-合同查询
	public static final String KEY_MARKET_CONTRACT_FCUSTOMERNAME = "fcustomername"; // 客户名称
	public static final String KEY_MARKET_CONTRACT_FCONTRACTCODE = "fcontractcode"; // 合同单号

	// 通讯录模块
	public static final String KEY_MARKET_CONTACT_FCORNET = "fCornet"; // 员工短号
	public static final String KEY_MARKET_CONTACT_MAIL = "fEmail"; // 员工短号

	private DBUtils mDbHelper;
	private SQLiteDatabase mDb;
	private Context mContext;

	public DbManager(Context context) {
		this.mContext = context;
	}

	/**
	 * @Title: openSqlLite
	 * @Description: 打开数据库
	 * @param @return
	 * @param @throws SQLException
	 * @return DbManager
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 上午11:29:10
	 */
	public DbManager openSqlLite() throws SQLException {
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化ExpenseFlowAdapter的步骤放在Activity的onCreate里
		mDbHelper = new DBUtils(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	/**
	 * @Title: closeSqlLite
	 * @Description: 关闭数据库
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 上午11:29:42
	 */
	public void closeSqlLite() {
		mDbHelper.close();
		mDb.close();
	}

	/**
	 * @Title: insertClientSearch
	 * @Description: 插入客户列表搜索记录
	 * @param @param eInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月17日 下午5:09:01
	 */
	public void insertClientSearch(ExpenseFlowItemInfo eInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_CLIENT_SEARCH + " WHERE 1=1 AND "
					+ KEY_CLIENTNAME + " = " + "\"" + eInfo.getFItemName()
					+ "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_CLIENTID, eInfo.getFId());
				cv.put(KEY_CLIENTNAME, eInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: insertProjectSearch
	 * @Description: 插入项目列表搜索记录
	 * @param @param eInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月17日 下午5:09:58
	 */
	public void insertProjectSearch(ExpenseFlowItemInfo eInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_PROJECT_SEARCH + " WHERE 1=1 AND "
					+ KEY_PROJECTNAME + " = " + "\"" + eInfo.getFItemName()
					+ "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_PROJECTID, eInfo.getFId());
				cv.put(KEY_PROJECTNAME, eInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deleteClientSearchAll
	 * @Description: 清除全部客户历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午2:12:21
	 */
	public void deleteClientSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, null, null);
	}

	/**
	 * @Title: deleteProjectSearchAll
	 * @Description: 清除全部项目历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午2:12:04
	 */
	public void deleteProjectSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, null, null);
	}

	/**
	 * @Title: queryLocalList
	 * @Description: 获取本地客户/项目搜索记录数据源
	 * @param @param type 获取类型
	 * @param @return
	 * @return List<ExpenseFlowItemInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午7:28:18
	 */
	public List<ExpenseFlowItemInfo> queryLocalList(String type) {
		List<ExpenseFlowItemInfo> list = new ArrayList<ExpenseFlowItemInfo>();
		Cursor c = null;
		if ("client".equals(type)) {
			c = queryHistory(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, "",
					KEY_CLIENTNAME, "30");
			while (c.moveToNext()) {
				ExpenseFlowItemInfo eItemInfo = new ExpenseFlowItemInfo();
				eItemInfo.setFId(c.getString(c.getColumnIndex(KEY_CLIENTID)));
				eItemInfo.setFItemName(c.getString(c
						.getColumnIndex(KEY_CLIENTNAME)));
				list.add(eItemInfo);
			}
		} else {
			c = queryHistory(DBUtils.DATABASE_TABLE_PROJECT_SEARCH, "",
					KEY_PROJECTNAME, "30");
			while (c.moveToNext()) {
				ExpenseFlowItemInfo eItemInfo = new ExpenseFlowItemInfo();
				eItemInfo.setFId(c.getString(c.getColumnIndex(KEY_PROJECTID)));
				eItemInfo.setFItemName(c.getString(c
						.getColumnIndex(KEY_PROJECTNAME)));
				list.add(eItemInfo);
			}
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryClientList
	 * @Description: 获取客户搜索历史记录集合
	 * @param @param queryStr 查询条件
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午2:58:05
	 */
	public List<String> queryClientList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_CLIENT_SEARCH, queryStr,
				KEY_CLIENTNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_CLIENTNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryProjectList
	 * @Description: 获取项目搜索历史记录集合
	 * @param @param queryStr 查询条件
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午3:00:45
	 */
	public List<String> queryProjectList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_PROJECT_SEARCH,
				queryStr, KEY_PROJECTNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_PROJECTNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: insertFlowDetail
	 * @Description: 插入我的流水详情记录
	 * @param @param exDetailInfo
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014年9月5日 上午11:29:51
	 */
	public boolean insertFlowDetail(ExpenseFlowDetailInfo exDetailInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DETAIL_FSERVERID, exDetailInfo.getFServerId());
			cv.put(KEY_DETAIL_FPAYTYPE, exDetailInfo.getFPayType());
			cv.put(KEY_DETAIL_FITEMNUMBER, exDetailInfo.getFItemNumber());
			cv.put(KEY_DETAIL_FEXPENDTIME, exDetailInfo.getFExpendTime());
			cv.put(KEY_DETAIL_FEXPENDTYPEPARENT,
					exDetailInfo.getFExpendTypeParent());
			cv.put(KEY_DETAIL_FEXPENDTYCHILD,
					exDetailInfo.getFExpendTypeChild());
			cv.put(KEY_DETAIL_FEXPENDADDRESS, exDetailInfo.getFExpendAddress());
			cv.put(KEY_DETAIL_FEXPENDAMOUNT, exDetailInfo.getFExpendAmount());
			cv.put(KEY_DETAIL_FCAUSE, exDetailInfo.getFCause());
			cv.put(KEY_DETAIL_FCLIENTID, exDetailInfo.getFClientId());
			cv.put(KEY_DETAIL_FPROJECTID, exDetailInfo.getFProjectId());
			cv.put(KEY_DETAIL_FCLIENT, exDetailInfo.getFClient());
			cv.put(KEY_DETAIL_FPROJECT, exDetailInfo.getFProject());
			cv.put(KEY_DETAIL_FACCOMPANY, exDetailInfo.getFAccompany());
			cv.put(KEY_DETAIL_FACCOMPANYREASON,
					exDetailInfo.getFAccompanyReason());
			cv.put(KEY_DETAIL_FSTART, exDetailInfo.getFStart());
			cv.put(KEY_DETAIL_FDESTINATION, exDetailInfo.getFDestination());
			cv.put(KEY_DETAIL_FSTARTTIME, exDetailInfo.getFStartTime());
			cv.put(KEY_DETAIL_FENDTIME, exDetailInfo.getFEndTime());
			cv.put(KEY_DETAIL_FBUSINESSLEVEL, exDetailInfo.getFBusinessLevel());
			cv.put(KEY_DETAIL_FREASON, exDetailInfo.getFReason());
			cv.put(KEY_DETAIL_FDESCRIPTION, exDetailInfo.getFDescription());
			cv.put(KEY_DETAIL_FUPLOADFLAG, exDetailInfo.getFUploadFlag());
			long ret = mDb.insert(DBUtils.DATABASE_TABLE_FLOW_DETAIL, null, cv);
			mDb.setTransactionSuccessful();
			if (ret > 0) {
				return true;
			}
			return false;
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: updateFlowDetail
	 * @Description: 修改已存在的流水详情记录
	 * @param @param exDetailInfo
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014年9月5日 上午11:27:41
	 */
	public boolean updateFlowDetail(ExpenseFlowDetailInfo exDetailInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DETAIL_FSERVERID, exDetailInfo.getFServerId());
			cv.put(KEY_DETAIL_FEXPENDTIME, exDetailInfo.getFExpendTime());
			cv.put(KEY_DETAIL_FEXPENDTYPEPARENT,
					exDetailInfo.getFExpendTypeParent());
			cv.put(KEY_DETAIL_FEXPENDTYCHILD,
					exDetailInfo.getFExpendTypeChild());
			cv.put(KEY_DETAIL_FEXPENDADDRESS, exDetailInfo.getFExpendAddress());
			cv.put(KEY_DETAIL_FEXPENDAMOUNT, exDetailInfo.getFExpendAmount());
			cv.put(KEY_DETAIL_FCAUSE, exDetailInfo.getFCause());
			cv.put(KEY_DETAIL_FCLIENTID, exDetailInfo.getFClientId());
			cv.put(KEY_DETAIL_FPROJECTID, exDetailInfo.getFProjectId());
			cv.put(KEY_DETAIL_FCLIENT, exDetailInfo.getFClient());
			cv.put(KEY_DETAIL_FPROJECT, exDetailInfo.getFProject());
			cv.put(KEY_DETAIL_FACCOMPANY, exDetailInfo.getFAccompany());
			cv.put(KEY_DETAIL_FACCOMPANYREASON,
					exDetailInfo.getFAccompanyReason());
			cv.put(KEY_DETAIL_FSTART, exDetailInfo.getFStart());
			cv.put(KEY_DETAIL_FDESTINATION, exDetailInfo.getFDestination());
			cv.put(KEY_DETAIL_FSTARTTIME, exDetailInfo.getFStartTime());
			cv.put(KEY_DETAIL_FENDTIME, exDetailInfo.getFEndTime());
			cv.put(KEY_DETAIL_FBUSINESSLEVEL, exDetailInfo.getFBusinessLevel());
			cv.put(KEY_DETAIL_FREASON, exDetailInfo.getFReason());
			cv.put(KEY_DETAIL_FDESCRIPTION, exDetailInfo.getFDescription());
			cv.put(KEY_DETAIL_FUPLOADFLAG, exDetailInfo.getFUploadFlag());
			long ret = mDb.update(DBUtils.DATABASE_TABLE_FLOW_DETAIL, cv,
					KEY_ROWID + "=" + exDetailInfo.getFLocalId(), null);
			mDb.setTransactionSuccessful();
			if (ret > 0) {
				return true;
			}
			return false;
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: queryFlowDetailList
	 * @Description: 获取暂存我的流水详情列表
	 * @param @return
	 * @return List<ExpenseFlowDetailInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月5日 上午11:38:23
	 */
	public List<ExpenseFlowDetailInfo> queryFlowDetailList() {
		ArrayList<ExpenseFlowDetailInfo> list = new ArrayList<ExpenseFlowDetailInfo>();
		Cursor c = queryFlowDetailAll();
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					ExpenseFlowDetailInfo eInfo = new ExpenseFlowDetailInfo();
					eInfo.setFLocalId(c.getInt(c.getColumnIndex(KEY_ROWID)));
					eInfo.setFServerId(c.getInt(c
							.getColumnIndex(KEY_DETAIL_FSERVERID)));
					eInfo.setFPayType(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPAYTYPE)));
					eInfo.setFItemNumber(c.getString(c
							.getColumnIndex(KEY_DETAIL_FITEMNUMBER)));
					eInfo.setFExpendTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTIME)));
					eInfo.setFExpendTypeParent(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTYPEPARENT)));
					eInfo.setFExpendTypeChild(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDTYCHILD)));
					eInfo.setFExpendAddress(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDADDRESS)));
					eInfo.setFExpendAmount(c.getString(c
							.getColumnIndex(KEY_DETAIL_FEXPENDAMOUNT)));
					eInfo.setFCause(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCAUSE)));
					eInfo.setFClientId(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCLIENTID)));
					eInfo.setFProjectId(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPROJECTID)));
					eInfo.setFClient(c.getString(c
							.getColumnIndex(KEY_DETAIL_FCLIENT)));
					eInfo.setFProject(c.getString(c
							.getColumnIndex(KEY_DETAIL_FPROJECT)));
					eInfo.setFAccompany(c.getString(c
							.getColumnIndex(KEY_DETAIL_FACCOMPANY)));
					eInfo.setFAccompanyReason(c.getString(c
							.getColumnIndex(KEY_DETAIL_FACCOMPANYREASON)));
					eInfo.setFStart(c.getString(c
							.getColumnIndex(KEY_DETAIL_FSTART)));
					eInfo.setFDestination(c.getString(c
							.getColumnIndex(KEY_DETAIL_FDESTINATION)));
					eInfo.setFStartTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FSTARTTIME)));
					eInfo.setFEndTime(c.getString(c
							.getColumnIndex(KEY_DETAIL_FENDTIME)));
					eInfo.setFBusinessLevel(c.getString(c
							.getColumnIndex(KEY_DETAIL_FBUSINESSLEVEL)));
					eInfo.setFReason(c.getString(c
							.getColumnIndex(KEY_DETAIL_FREASON)));
					eInfo.setFDescription(c.getString(c
							.getColumnIndex(KEY_DETAIL_FDESCRIPTION)));
					eInfo.setFUploadFlag(c.getString(c
							.getColumnIndex(KEY_DETAIL_FUPLOADFLAG)));
					list.add(eInfo);
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryFlowDetailAll
	 * @Description: 查询本地我的流水详情所有记录
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014年9月5日 上午11:40:06
	 */
	public Cursor queryFlowDetailAll() {
		String selectStr = "SELECT * FROM "
				+ DBUtils.DATABASE_TABLE_FLOW_DETAIL + " WHERE 1=1 AND "
				+ KEY_DETAIL_FUPLOADFLAG + " ='0' " + " ORDER BY " + KEY_ROWID
				+ " DESC";
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: deleteFlowDetail
	 * @Description: 删除我的流水详情记录
	 * @param @param rowId
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014年9月5日 上午11:35:06
	 */
	public boolean deleteFlowDetail(int rowId) {
		return mDb.delete(DBUtils.DATABASE_TABLE_FLOW_DETAIL, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * @Title: batchDeleteFlowDetail
	 * @Description: 批量删除流水详情记录
	 * @param @param dList
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 21291
	 * @date 2014年9月10日 下午12:00:32
	 */
	public boolean batchDeleteFlowDetail(List<Integer> dList) {
		mDb.beginTransaction(); // 开始事务
		try {
			boolean result = false;
			for (Integer item : dList) {
				result = false;
				if (deleteFlowDetail(item)) {
					result = true;
				}
			}
			mDb.setTransactionSuccessful();
			return result;
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: updateFlag
	 * @Description: 修改流水详情记录上传服务状态标志
	 * @param @param rowId
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月10日 下午3:44:58
	 */
	public void updateFlag(int rowId) {
		ContentValues args = new ContentValues();
		args.put(KEY_DETAIL_FUPLOADFLAG, "1"); // 表示已经上传成功的标志
		mDb.update(DBUtils.DATABASE_TABLE_FLOW_DETAIL, args, KEY_ROWID + "="
				+ rowId, null);
	}

	/**
	 * @Title: batchUpdateFlag
	 * @Description: 批量修改流水详情记录的上传状态标志
	 * @param @param uList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月10日 下午3:43:18
	 */
	public void batchUpdateFlag(List<Integer> uList) {
		mDb.beginTransaction(); // 开始事务
		try {
			for (Integer item : uList) {
				updateFlag(item);
			}
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: insertRoomSearch
	 * @Description: 插入会议室列表搜索记录
	 * @param @param roomName
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月11日 下午8:16:38
	 */
	public void insertRoomSearch(MeetingRoomInfo meRoomInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH
					+ " WHERE 1=1 AND " + KEY_MEETING_ROOMNAME + " = " + "\""
					+ meRoomInfo.getFRoomName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MEETING_ROOMID, meRoomInfo.getFRoomId());
				cv.put(KEY_MEETING_ROOMNAME, meRoomInfo.getFRoomName());
				cv.put(KEY_MEETING_ROOMIP, meRoomInfo.getFRoomIp());
				mDb.insert(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: insertPersonSearch
	 * @Description: 插入人员列表搜索记录
	 * @param @param mPersonInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:02:37
	 */
	public void insertPersonSearch(MeetingPersonInfo mPersonInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH
					+ " WHERE 1=1 AND " + KEY_MEETING_FITEMNUMBER + " = "
					+ "\"" + mPersonInfo.getFItemNumber() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MEETING_FITEMNUMBER, mPersonInfo.getFItemNumber());
				cv.put(KEY_MEETING_FITEMNAME, mPersonInfo.getFItemName());
				cv.put(KEY_MEETING_FDEPTNAME, mPersonInfo.getFDeptName());
				mDb.insert(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deleteRoomSearchAll
	 * @Description: 清除全部会议室历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:07:51
	 */
	public void deleteRoomSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, null, null);
	}

	/**
	 * @Title: deletePersonSearchAll
	 * @Description: 清除全部人员历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:08:40
	 */
	public void deletePersonSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, null, null);
	}

	/**
	 * @Title: queryRoomAllList
	 * @Description: 获取本地会议室搜索记录数据源
	 * @param @return
	 * @return List<MeetingRoomInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:22:49
	 */
	public List<MeetingRoomInfo> queryRoomAllList() {
		List<MeetingRoomInfo> list = new ArrayList<MeetingRoomInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH, "", "",
				"30");
		while (c.moveToNext()) {
			MeetingRoomInfo mRoomInfo = new MeetingRoomInfo();
			mRoomInfo.setFRoomId(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMID)));
			mRoomInfo.setFRoomName(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMNAME)));
			mRoomInfo.setFRoomIp(c.getString(c
					.getColumnIndex(KEY_MEETING_ROOMIP)));
			list.add(mRoomInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryRoomList
	 * @Description: 获取会议室搜索历史记录集合
	 * @param @param queryStr 查询条件
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:23:30
	 */
	public List<String> queryRoomList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_ROOM_SEARCH,
				queryStr, KEY_MEETING_ROOMNAME, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c.getColumnIndex(KEY_MEETING_ROOMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryPersonAllList
	 * @Description: 获取本地人员搜索记录数据源
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:24:48
	 */
	public List<MeetingPersonInfo> queryPersonAllList() {
		List<MeetingPersonInfo> list = new ArrayList<MeetingPersonInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, "", "",
				"30");
		while (c.moveToNext()) {
			MeetingPersonInfo mPersonInfo = new MeetingPersonInfo();
			mPersonInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNUMBER)));
			mPersonInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNAME)));
			mPersonInfo.setFDeptName(c.getString(c
					.getColumnIndex(KEY_MEETING_FDEPTNAME)));
			mPersonInfo.setFRecordCount(0);
			list.add(mPersonInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryPersonList
	 * @Description: 获取人员姓名搜索历史记录集合
	 * @param @param queryStr
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:27:35
	 */
	public List<String> queryPersonList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryPersonHistory(
				DBUtils.DATABASE_TABLE_MEETING_PERSON_SEARCH, queryStr, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c
							.getColumnIndex(KEY_MEETING_FITEMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}

		return list;
	}

	/**
	 * @Title: queryHistory
	 * @Description: 根据条件，查询搜索框历史记录
	 * @param @param dataBase 数据库
	 * @param @param queryStr 查询条件
	 * @param @param fieldStr 字段名称
	 * @param @param topCount 返回数量
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014年9月2日 下午7:51:18
	 */
	public Cursor queryHistory(String dataBase, String queryStr,
			String fieldStr, String topCount) {
		String whereStr = ""; // 查询条件
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + fieldStr + " LIKE '%" + queryStr
					+ "%' ";
		}

		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: queryPersonHistory
	 * @Description: 根据条件，查询人员搜索框历史记录
	 * @param @param dataBase 数据库
	 * @param @param queryStr 查询条件
	 * @param @param topCount 返回数量
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:31:43
	 */
	public Cursor queryPersonHistory(String dataBase, String queryStr,
			String topCount) {
		String whereStr = ""; // 查询条件
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + KEY_MEETING_FITEMNUMBER + " LIKE '%"
					+ queryStr + "%' OR " + KEY_MEETING_FITEMNAME + " LIKE '%"
					+ queryStr + "%'";
		}

		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: insertPlusCopyPersonSearch
	 * @Description: 加签/抄送模块插入人员列表搜索记录
	 * @param @param mPersonInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月25日 上午10:44:58
	 */
	public void insertPlusCopyPersonSearch(PlusCopyPersonInfo mPersonInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH
					+ " WHERE 1=1 AND " + KEY_PLUSCOPY_FITEMNUMBER + " = "
					+ "\"" + mPersonInfo.getFItemNumber() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_PLUSCOPY_FITEMNUMBER, mPersonInfo.getFItemNumber());
				cv.put(KEY_PLUSCOPY_FITEMNAME, mPersonInfo.getFItemName());
				mDb.insert(DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deletePlusCopyPersonSearchAll
	 * @Description: 加签/抄送模块清除全部人员历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月25日 上午10:46:36
	 */
	public void deletePlusCopyPersonSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, null, null);
	}

	/**
	 * @Title: queryPlusCopyPersonAllList
	 * @Description: 加签/抄送模块获取本地人员搜索记录数据源
	 * @param @return
	 * @return List<PlusCopyPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月25日 上午10:47:35
	 */
	public List<PlusCopyPersonInfo> queryPlusCopyPersonAllList() {
		List<PlusCopyPersonInfo> list = new ArrayList<PlusCopyPersonInfo>();
		Cursor c = null;
		c = queryPlusCopyPersonHistory(
				DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, "", "30");
		while (c.moveToNext()) {
			PlusCopyPersonInfo mPersonInfo = new PlusCopyPersonInfo();
			mPersonInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_PLUSCOPY_FITEMNUMBER)));
			mPersonInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_PLUSCOPY_FITEMNAME)));
			list.add(mPersonInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: queryPlusCopyPersonList
	 * @Description: 加签/抄送模块获取人员姓名搜索历史记录集合
	 * @param @param queryStr
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author 21291
	 * @date 2014年9月25日 上午10:49:00
	 */
	public List<String> queryPlusCopyPersonList(String queryStr) {
		List<String> list = new ArrayList<String>();
		Cursor c = queryPlusCopyPersonHistory(
				DBUtils.DATABASE_TABLE_PLUSCOPY_PERSON_SEARCH, queryStr, "10");
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					list.add(c.getString(c
							.getColumnIndex(KEY_PLUSCOPY_FITEMNAME)));
				} while (c.moveToNext());
			}
			c.close();
		}
		return list;
	}

	/**
	 * @Title: queryPlusCopyPersonHistory
	 * @Description: 根据条件，查询人员搜索框历史记录
	 * @param @param dataBase 数据库
	 * @param @param queryStr 查询条件
	 * @param @param topCount 返回数量
	 * @param @return
	 * @return Cursor
	 * @throws
	 * @author 21291
	 * @date 2014年9月25日 上午10:50:19
	 */
	public Cursor queryPlusCopyPersonHistory(String dataBase, String queryStr,
			String topCount) {
		String whereStr = ""; // 查询条件
		if (queryStr.length() != 0) {
			whereStr = " WHERE 1=1 AND " + KEY_PLUSCOPY_FITEMNUMBER
					+ " LIKE '%" + queryStr + "%' OR " + KEY_PLUSCOPY_FITEMNAME
					+ " LIKE '%" + queryStr + "%'";
		}
		String selectStr = "SELECT * FROM " + dataBase + whereStr
				+ " ORDER BY " + KEY_ROWID + " DESC LIMIT " + topCount;
		Cursor c = mDb.rawQuery(selectStr, null);
		return c;
	}

	/**
	 * @Title: insertDHProjectSearch
	 * @Description: 研发工时模块_插入项目列表搜索记录
	 * @param @param dhProjectInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年10月30日 下午4:03:13
	 */
	public void insertDHProjectSearch(DHProjectInfo dhProjectInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH
					+ " WHERE 1=1 AND " + KEY_DEVELOP_HOURS_PROJECTNAME + " = "
					+ "\"" + dhProjectInfo.getFProjectName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_DEVELOP_HOURS_PROJECTCODE,
						dhProjectInfo.getFProjectCode());
				cv.put(KEY_DEVELOP_HOURS_PROJECTNAME,
						dhProjectInfo.getFProjectName());
				mDb.insert(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH,
						null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deleteDHProjectSearchAll
	 * @Description: 研发工时模块_清除全部项目历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年10月30日 下午4:04:36
	 */
	public void deleteDHProjectSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH, null,
				null);
	}

	/**
	 * @Title: queryDHProjectAllList
	 * @Description: 研发工时模块_获取本地项目列表搜索记录
	 * @param @return
	 * @return List<DHProjectInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年10月30日 下午4:29:28
	 */
	public List<DHProjectInfo> queryDHProjectAllList() {
		List<DHProjectInfo> list = new ArrayList<DHProjectInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_DEVELOP_HOURS_PROJECT_SEARCH,
				"", "", "5");
		while (c.moveToNext()) {
			DHProjectInfo dhProjectInfo = new DHProjectInfo();
			dhProjectInfo.setFProjectCode(c.getString(c
					.getColumnIndex(KEY_DEVELOP_HOURS_PROJECTCODE)));
			dhProjectInfo.setFProjectName(c.getString(c
					.getColumnIndex(KEY_DEVELOP_HOURS_PROJECTNAME)));
			list.add(dhProjectInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertMarketBidSearch
	 * @Description: 我的销售模块_插入报价搜索记录
	 * @param @param mBidHistoryInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015年1月29日 下午5:10:16
	 */
	public void insertMarketBidSearch(MarketBidHistoryInfo mBidHistoryInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH
					+ " WHERE 1=1 AND " + KEY_MARKET_BID_FCUSTOMERNAME + " = "
					+ "\"" + mBidHistoryInfo.getFCustomerName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MARKET_BID_FCUSTOMERNAME,
						mBidHistoryInfo.getFCustomerName());
				cv.put(KEY_MARKET_BID_FBIDCODE, mBidHistoryInfo.getFBidCode());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, null, cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deleteMarketBidSearchAll
	 * @Description:我的销售模块_清除全部报价历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015年1月27日 下午6:05:55
	 */
	public void deleteMarketBidSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, null, null);
	}

	/**
	 * @Title: queryMarketBidAllList
	 * @Description: 我的销售模块_获取本地报价列表搜索记录
	 * @param @return
	 * @return List<MarketBidHistoryInfo>
	 * @throws
	 * @author 21291
	 * @date 2015年1月29日 下午5:10:48
	 */
	public List<MarketBidHistoryInfo> queryMarketBidAllList() {
		List<MarketBidHistoryInfo> list = new ArrayList<MarketBidHistoryInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_BID_SEARCH, "", "", "5");
		while (c.moveToNext()) {
			MarketBidHistoryInfo mBidInfo = new MarketBidHistoryInfo();
			mBidInfo.setFCustomerName(c.getString(c
					.getColumnIndex(KEY_MARKET_BID_FCUSTOMERNAME)));
			mBidInfo.setFBidCode(c.getString(c
					.getColumnIndex(KEY_MARKET_BID_FBIDCODE)));
			list.add(mBidInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertMarketContractSearch
	 * @Description: 我的销售模块_插入合同搜索记录
	 * @param @param marketContractHistoryInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015年1月29日 下午5:11:32
	 */
	public void insertMarketContractSearch(
			MarketContractHistoryInfo marketContractHistoryInfo) {
		mDb.beginTransaction(); // 开始事务
		try {
			// 先判断是否已经存在
			String sqlQuery = "SELECT * FROM "
					+ DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH
					+ " WHERE 1=1 AND " + KEY_MARKET_CONTRACT_FCUSTOMERNAME
					+ " = " + "\""
					+ marketContractHistoryInfo.getFCustomerName() + "\"";
			Cursor c = mDb.rawQuery(sqlQuery, null);
			if (c == null || c.getCount() == 0) {
				ContentValues cv = new ContentValues();
				cv.put(KEY_MARKET_CONTRACT_FCUSTOMERNAME,
						marketContractHistoryInfo.getFCustomerName());
				cv.put(KEY_MARKET_CONTRACT_FCONTRACTCODE,
						marketContractHistoryInfo.getFContractCode());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, null,
						cv);
				mDb.setTransactionSuccessful();
			}
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * @Title: deleteMarketContractSearchAll
	 * @Description: 我的销售模块_清除全部合同历史记录
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2015年1月27日 下午6:13:54
	 */
	public void deleteMarketContractSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, null, null);
	}

	/**
	 * @Title: queryMarketContractAllList
	 * @Description: 我的销售模块_获取本地合同列表搜索记录
	 * @param @return
	 * @return List<marketContractHistoryInfo>
	 * @throws
	 * @author 21291
	 * @date 2015年1月29日 下午5:12:08
	 */
	public List<MarketContractHistoryInfo> queryMarketContractAllList() {
		List<MarketContractHistoryInfo> list = new ArrayList<MarketContractHistoryInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_CONTRACT_SEARCH, "", "",
				"5");
		while (c.moveToNext()) {
			MarketContractHistoryInfo mContractInfo = new MarketContractHistoryInfo();
			mContractInfo.setFCustomerName(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTRACT_FCUSTOMERNAME)));
			mContractInfo.setFContractCode(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTRACT_FCONTRACTCODE)));
			list.add(mContractInfo);
		}
		c.close();
		return list;
	}

	/**
	 * @Title: insertContactSearch
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param mSearchContactInfo 参数
	 * @return void 返回类型
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void insertContactSearch(ContactInfo mContactInfo) {
		mDb.beginTransaction();
		String itemNumber = mContactInfo.getFItemNumber();
		String sql = "select _id from "
				+ DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH
				+ " where FItemNumber=?";
		Cursor c = mDb.rawQuery(sql, new String[] {itemNumber });
		try {
			if (c != null && c.getCount() == 0) {
				ContentValues vs = new ContentValues();
				vs.put(KEY_MEETING_FITEMNUMBER,
						mContactInfo.getFItemNumber());
				vs.put(KEY_MEETING_FITEMNAME, mContactInfo.getFItemName());
				vs.put(KEY_MEETING_FDEPTNAME,
						mContactInfo.getFDepartment());
				vs.put(KEY_MARKET_CONTACT_FCORNET,
						mContactInfo.getFCornet());
				vs.put(KEY_MARKET_CONTACT_MAIL, mContactInfo.getFEmail());
				mDb.insert(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, null,
						vs);
				mDb.setTransactionSuccessful();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			mDb.endTransaction(); // 结束事务
		}
	}

	/**
	 * 
	* @Title: queryPersonAllList 
	* @Description: 获取本地的通讯录信息
	* @param  @return   参数 
	* @return List<MeetingPersonInfo>    返回类型 
	* @throws 
	* @author taozhaoping 26078
	* @author mail taozhaoping@gmail.com
	 */
	public List<ContactInfo> queryContactAllList() {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		Cursor c = null;
		c = queryHistory(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, "", "",
				"10");
		while (c.moveToNext()) {
			ContactInfo mContactInfo = new ContactInfo();
			mContactInfo.setFItemNumber(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNUMBER)));
			mContactInfo.setFItemName(c.getString(c
					.getColumnIndex(KEY_MEETING_FITEMNAME)));
			mContactInfo.setFDepartment(c.getString(c
					.getColumnIndex(KEY_MEETING_FDEPTNAME)));
			mContactInfo.setFCornet(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTACT_FCORNET)));
			mContactInfo.setFEmail(c.getString(c
					.getColumnIndex(KEY_MARKET_CONTACT_MAIL)));
			list.add(mContactInfo);
		}
		c.close();
		return list;
	}
	
	/**
	 * 
	 * @Title: deleteContactSearchAll
	 * @Description: 清空通讯录搜索记录
	 * @param 参数
	 * @return void 返回类型
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void deleteContactSearchAll() {
		mDb.delete(DBUtils.DATABASE_TABLE_MARKET_CONTACT_SEARCH, null, null);
	}
}
