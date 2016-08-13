package org.javastudy.hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

// http://www.tuicool.com/articles/r6ZZBjU  可以使用
public class BaseTest {

	private static final String TABLE_NAME = "demo_table";

	public static Configuration conf = null;
	public HTable table = null;
	public HBaseAdmin admin = null;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.zookeeper.quorum", "192.168.189.201");
		conf.set("hbase.master", "192.168.189.201:16010");
		System.out.println(conf.get("hbase.zookeeper.quorum"));
	}

	/**
	 * 创建一张表
	 */
	public static void creatTable(String tableName, String[] familys) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table already exists!");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(familys[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table " + tableName + " ok.");
		}
	}

	/**
	 * 删除表
	 */
	public static void deleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("delete table " + tableName + " ok.");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入一行记录
	 */
	public static void addRecord(String tableName, String rowKey, String family, String qualifier, String value)
			throws Exception {
		try {
			HTable table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table " + tableName + " ok.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一行记录
	 */
	public static void delRecord(String tableName, String rowKey) throws IOException {
		HTable table = new HTable(conf, tableName);
		List list = new ArrayList();
		Delete del = new Delete(rowKey.getBytes());
		list.add(del);
		table.delete(list);
		System.out.println("del recored " + rowKey + " ok.");
	}

	/**
	 * 查找一行记录
	 */
	public static void getOneRecord(String tableName, String rowKey) throws IOException {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " ");
			System.out.print(kv.getTimestamp() + " ");
			System.out.println(new String(kv.getValue()));
		}
	}

	/**
	 * 显示所有数据
	 */
	public static void getAllRecord(String tableName) {
		try {
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for (Result r : ss) {
				for (KeyValue kv : r.raw()) {
					System.out.print(new String(kv.getRow()) + " ");
					System.out.print(new String(kv.getFamily()) + ":");
					System.out.print(new String(kv.getQualifier()) + " ");
					System.out.print(kv.getTimestamp() + " ");
					System.out.println(new String(kv.getValue()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] agrs) {
		try {
			String tablename = "scores";
			String[] familys = { "grade", "course" };
//			BaseTest.creatTable(tablename, familys);

			// add record zkb
			BaseTest.addRecord(tablename, "zkb", "grade", "", "5");
			BaseTest.addRecord(tablename, "zkb", "course", "", "90");
			BaseTest.addRecord(tablename, "zkb", "course", "math", "97");
			BaseTest.addRecord(tablename, "zkb", "course", "art", "87");
			// add record baoniu
			BaseTest.addRecord(tablename, "baoniu", "grade", "", "4");
			BaseTest.addRecord(tablename, "baoniu", "course", "math", "89");

			System.out.println("===========get one record========");
			BaseTest.getOneRecord(tablename, "zkb");

			System.out.println("===========show all record========");
			BaseTest.getAllRecord(tablename);

			System.out.println("===========del one record========");
			BaseTest.delRecord(tablename, "baoniu");
			BaseTest.getAllRecord(tablename);

			System.out.println("===========show all record========");
			BaseTest.getAllRecord(tablename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}