package org.javastudy.hadoop.cassandra;

import java.util.Iterator;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

// http://zhaoyanblog.com/archives/180.html

public class CassandraMain {
	public static void main(String[] args) {
		QueryOptions options = new QueryOptions();
		// options.setConsistencyLevel(ConsistencyLevel.QUORUM); // 添加后会报错，把它去掉
		Cluster cluster = null;
		Session session = null;
		try {
			cluster = Cluster.builder().addContactPoint("192.168.189.201").addContactPoint("192.168.189.129")
					.addContactPoint("192.168.189.202").withCredentials("cassandra", "cassandra")
					.withQueryOptions(options).build();

			session = cluster.connect();
		} catch (Exception e) {
			// e.printStackTrace();
		}

//		 session.execute(
//		 "CREATE KEYSPACE hello WITH replication = {'class':'SimpleStrategy','replication_factor': 3} AND durable_writes = true;");

		// 针对keyspace的session，后面表名前面不用加keyspace
		Session kpSession = cluster.connect("hello");
//		 kpSession.execute("CREATE TABLE kp(a INT, b INT, c INT, PRIMARY KEY(a));");
		// for (int i = 0; i < 1000000; i++) {
		// RegularStatement insert = QueryBuilder.insertInto("kp",
		// "tbl").values(new String[] { "a", "b", "c" },
		// new Object[] { i, 999, 999 });
		// kpSession.execute(insert);
		// }
		//
		try {
			 RegularStatement insert2 = QueryBuilder.insertInto("hello", "kp")
			 .values(new String[] { "a", "b", "c" }, new Object[] { 77777, 2,
			 100 });
			 kpSession.execute(insert2);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
		}

		//
		// RegularStatement insert3 = QueryBuilder.insertInto("kp",
		// "tbl").values(new String[] { "a", "b", "c" },
		// new Object[] { 3111, 2111, 100111 });
		// kpSession.execute(insert3);
		//
		// RegularStatement delete = QueryBuilder.delete().from("kp",
		// "tbl").where(QueryBuilder.eq("a", 1));
		// kpSession.execute(delete);

		// RegularStatement update = QueryBuilder.update("kp",
		// "tbl").with(QueryBuilder.set("b", 96))
		// .where(QueryBuilder.eq("a", 3));
		// kpSession.execute(update);

		// RegularStatement select = QueryBuilder.select().from("kp",
		// "tbl").where(QueryBuilder.eq("a", 3));

		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		RegularStatement select = QueryBuilder.select().from("hello", "kp");
		ResultSet rs = kpSession.execute(select);
		Iterator<Row> iterator = rs.iterator();
		while (iterator.hasNext()) {
			Row row = iterator.next();
			System.out.println("a=" + row.getInt("a"));
			System.out.println("b=" + row.getInt("b"));
			System.out.println("c=" + row.getInt("c"));
		}
		kpSession.close();
		session.close();
		cluster.close();
	}
}