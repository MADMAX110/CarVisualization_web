package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnection {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carvisualization?characterEncoding=utf8&useUnicode=true&sessionVariables=storage_engine%3DInnoDB&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
		}catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		}catch(SQLException e) {
			System.out.println("JDBC connection error");
		}
		return connection;
	}
	
	public static void closeDB(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				
			}
		}
		if (st != null) {
			try {
				st.close();
			}
			catch(SQLException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				
			}
			
		}
	}
}
