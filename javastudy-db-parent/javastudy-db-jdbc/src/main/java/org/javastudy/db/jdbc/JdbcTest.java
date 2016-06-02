package org.javastudy.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTest {
	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springboot", "root", "123456");
		// 关闭自动提交事务
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		try {
			conn.setAutoCommit(false); // 关闭自动提交事务
			// String sql = "insert into employee(id,name,sharding_id)
			// VALUES(161616,'66',10000)";
			// stmt.executeUpdate(sql);
			for (int i = 1; i < 10; i++) {
				String sql2 = "insert into news(id,title,content) VALUE(" + (i + 5000) + ",'22','6')";
				stmt.executeUpdate(sql2);
			}
			conn.commit(); // 提交事务
			System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback(); // 回滚事务
			System.out.println("no");
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}