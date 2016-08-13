package org.javastudy.hadoop.hbase.test;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author karthy
 *
 */
public class HBaseTest {

	public static void main(String[] agrs) {
		
		try {
			
			String tablename = "employee";
			String[] familys = { "personal", "professional" };
			
			HBaseConnector connector = new HBaseConnector();
			connector.creatTable(tablename, familys);

			Put p = new Put(Bytes.toBytes("employee1"));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("name"),
					Bytes.toBytes("karthy"));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("city"),
					Bytes.toBytes("atlanta"));
			p.add(Bytes.toBytes("professional"), Bytes.toBytes("designation"),
					Bytes.toBytes("developer"));
			p.add(Bytes.toBytes("professional"), Bytes.toBytes("salary"),
					Bytes.toBytes("100000"));

			p = new Put(Bytes.toBytes("employee1"));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("name"),
					Bytes.toBytes("vinodh"));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("city"),
					Bytes.toBytes("chicago"));
			p.add(Bytes.toBytes("professional"), Bytes.toBytes("designation"),
					Bytes.toBytes("developer"));
			p.add(Bytes.toBytes("professional"), Bytes.toBytes("salary"),
					Bytes.toBytes("100000"));

			connector.getOneRecord(tablename, "employee1");

			connector.getAllRecord(tablename);

			connector.delRecord(tablename, "employee2");

			connector.getAllRecord(tablename);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
