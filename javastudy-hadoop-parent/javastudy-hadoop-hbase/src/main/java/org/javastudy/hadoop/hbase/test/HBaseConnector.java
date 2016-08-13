package org.javastudy.hadoop.hbase.test;

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
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public class HBaseConnector {

	private static Configuration configuration = null;
	private static HTable hTable;
	private static HBaseAdmin admin;
	private static Logger logger = Logger.getLogger(HBaseConnector.class);

	static {
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("hbase.zookeeper.quorum", "192.168.189.201");
		configuration.set("hbase.master", "192.168.189.201:16010");
	}

	public void creatTable(String tableName, String[] familys) {

		try {
			admin = new HBaseAdmin(configuration);

			if (admin.tableExists(tableName)) {

				logger.debug(tableName + "table already exists !!");

			} else {

				HTableDescriptor tableDesc = new HTableDescriptor(tableName);
				for (int i = 0; i < familys.length; i++) {
					tableDesc.addFamily(new HColumnDescriptor(familys[i]));
				}
				admin.createTable(tableDesc);
				logger.debug(tableName + " table created successfully !! ");

			}
			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	public void listTable() throws Exception {

		try {
			admin = new HBaseAdmin(configuration);
			HTableDescriptor[] tableDescriptor = admin.listTables();

			for (int i = 0; i < tableDescriptor.length; i++) {
				logger.debug(tableDescriptor[i].getNameAsString());
			}

			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	public void disableTable(String tableName) throws Exception {

		try {

			admin = new HBaseAdmin(configuration);
			Boolean isEnabled = admin.isTableDisabled(tableName);

			if (!isEnabled) {
				admin.disableTable(tableName);
				logger.debug(tableName + " table disabled successfully");
			}

			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	public void enableTable(String tableName) throws Exception {

		try {

			admin = new HBaseAdmin(configuration);
			Boolean isEnabled = admin.isTableEnabled(tableName);

			if (!isEnabled) {
				admin.enableTable(tableName);
				logger.debug(tableName + " table enabled successfully");
			}

			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteTable(String tableName) {

		try {

			admin = new HBaseAdmin(configuration);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);

			logger.debug(tableName + " table deleteed successfully");
			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void addColumn(String tableName, String column) {

		try {
			admin = new HBaseAdmin(configuration);

			HColumnDescriptor columnDescriptor = new HColumnDescriptor(column);
			admin.addColumn("employee", columnDescriptor);
			logger.debug(tableName + " column added successfully");
			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	public void deleteColumn(String tableName, String column) throws Exception {

		try {

			admin = new HBaseAdmin(configuration);
			admin.deleteColumn("employee", "contactDetails");
			logger.debug(tableName + " column deleteed successfully");
			admin.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void addRecord(String tableName, String rowKey, String family,
			String qualifier, String value) {
		try {
			hTable = new HTable(configuration, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			hTable.put(put);
			logger.debug("insert recored " + rowKey + " to table " + tableName
					+ " successfully");

			hTable.close();
		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void delRecord(String tableName, String rowKey) {

		try {
			hTable = new HTable(configuration, tableName);
			List<Delete> list = new ArrayList<Delete>();
			Delete del = new Delete(rowKey.getBytes());
			list.add(del);
			hTable.delete(list);
			logger.debug(rowKey + " record deleteed successfully");
			hTable.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void getOneRecord(String tableName, String rowKey) {

		try {
			hTable = new HTable(configuration, tableName);
			Get get = new Get(rowKey.getBytes());
			Result result = hTable.get(get);
			for (KeyValue keyValue : result.raw()) {

				StringBuffer buffer = new StringBuffer();
				buffer.append(new String(keyValue.getRow()) + " ");
				buffer.append(new String(keyValue.getFamily()) + ":");
				buffer.append(new String(keyValue.getQualifier()) + " ");
				buffer.append(keyValue.getTimestamp() + " ");
				logger.debug(buffer);
				logger.debug(new String(keyValue.getValue()));
			}
			hTable.close();

		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void getAllRecord(String tableName) {
		try {
			hTable = new HTable(configuration, tableName);
			Scan s = new Scan();
			ResultScanner resultScanner = hTable.getScanner(s);
			for (Result result : resultScanner) {
				for (KeyValue keyValue : result.raw()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(new String(keyValue.getRow()) + " ");
					buffer.append(new String(keyValue.getFamily()) + ":");
					buffer.append(new String(keyValue.getQualifier()) + " ");
					buffer.append(keyValue.getTimestamp() + " ");
					logger.debug(buffer);
					logger.debug(new String(keyValue.getValue()));
				}
			}
			hTable.close();
		} catch (MasterNotRunningException e) {
			logger.error(e.getMessage());
		} catch (ZooKeeperConnectionException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
