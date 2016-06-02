package org.javastudy.db.berkeleydb;

import java.io.File;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;
/*
 * 数据库操作类
 */
public class BdbTest {

	private static Database bdb; // 数据源
	private static Environment exampleEnv;// 环境对象
	private static boolean isrunning = false;// 判断是否运行

	public static void main(String[] args) throws DatabaseException {
		start("I:\\test\\bdb");
		String key = "This is a key";
		String value = "This is a value";
		String key1 = "user100";
		String value1 = "my user 100";
		set(key.getBytes(), value.getBytes());
		set(key1.getBytes(), value1.getBytes());
		delete(key1);

//		for (int i = 0; i < 500000; i++) {
//			String mykey = "key" + i;
//			String myvalue = "value" + i;
//			set(mykey.getBytes(), myvalue.getBytes());
//		}
		selectByKey("key300");
	}

	/**
	 * 打开数据库方法
	 * 
	 * @throws DatabaseException
	 */
	public static void start(String path) throws DatabaseException {
		if (isrunning) {
			return;
		}
		/******************** 文件处理 ***********************/
		File envDir = new File(path);// 操作文件
		if (!envDir.exists())// 判断文件路径是否存在，不存在则创建
		{
			envDir.mkdir();// 创建
		}

		/******************** 环境配置 ***********************/
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(false); // 不进行事务处理
		envConfig.setAllowCreate(true); // 如果不存在则创建一个
		exampleEnv = new Environment(envDir, envConfig);// 通过路径，设置属性进行创建

		/******************* 创建适配器对象 ******************/
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(false); // 不进行事务处理
		dbConfig.setAllowCreate(true);// 如果不存在则创建一个
		dbConfig.setSortedDuplicates(true);// 数据分类
		bdb = exampleEnv.openDatabase(null, "simpleDb", dbConfig); // 使用适配器打开数据库
		isrunning = true; // 设定是否运行
	}

	/**
	 * 关闭数据库方法
	 * 
	 * @throws DatabaseException
	 */
	public static void stop() throws DatabaseException {
		if (isrunning) {
			isrunning = false;
			bdb.close();
			exampleEnv.close();
		}
	}

	public static boolean isrunning() {
		return isrunning;
	}

	/**
	 * 数据存储方法 set(Here describes this method function with a few words)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * @param key
	 * @param data
	 * 
	 *            void
	 * @throws DatabaseException
	 */
	public static void set(byte[] key, byte[] data) throws DatabaseException {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		keyEntry.setData(key); // 存储数据
		dataEntry.setData(data);
		OperationStatus status = bdb.put(null, keyEntry, dataEntry);// 持久化数据
		if (status != OperationStatus.SUCCESS) {
			throw new RuntimeException("Data insertion got status " + status);
		}
	}

	/*
	 * 执行获取,根据key值获取
	 */
	public static void selectByKey(String aKey) throws DatabaseException {
		DatabaseEntry theKey = null;
		DatabaseEntry theData = new DatabaseEntry();
		try {
			theKey = new DatabaseEntry(aKey.getBytes("utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bdb.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) { // 根据key值，进行数据查询
			// Recreate the data String.
			byte[] retData = theData.getData();
			String foundData = new String(retData);
			System.out.println("For key: '" + aKey + "' found data: '" + foundData + "'.");
		}
	}

	/**
	 * 查询所有，可遍历数据 selectAll(Here describes this method function with a few
	 * words)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * 
	 * void
	 * 
	 * @throws DatabaseException
	 */
	public static void selectAll() throws DatabaseException {
		Cursor cursor = null;
		cursor = bdb.openCursor(null, null);
		DatabaseEntry theKey = null;
		DatabaseEntry theData = null;
		theKey = new DatabaseEntry();
		theData = new DatabaseEntry();
		while (cursor.getNext(theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			System.out.println(new String(theData.getData()));
		}
		cursor.close();
	}

	/**
	 * 删除方法 delete(Here describes this method function with a few words)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * @param key
	 * 
	 *            void
	 * @throws DatabaseException
	 */
	public static void delete(String key) throws DatabaseException {
		DatabaseEntry keyEntry = null;
		try {
			keyEntry = new DatabaseEntry(key.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		bdb.delete(null, keyEntry);
	}
}
