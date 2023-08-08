package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;

public class UserDAO {
	public int saveUser(User user) {
		int i = 0;
		Connection connection = MysqlConnection.getConnection();
		PreparedStatement preStmt = null;
		String sql = "insert into user(username, password) values(?,?)";
		try {
			preStmt = connection.prepareStatement(sql);
			preStmt.setString(1, user.getName());
			preStmt.setString(2, user.getPassword());
			i =  preStmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			MysqlConnection.closeDB(null, preStmt, connection);
		}
		return i;
	}
	
	public int deleteUser(String username) {
		int i = 0;
		Connection conn = MysqlConnection.getConnection();
		PreparedStatement preStmt = null;
		String sql = "delete from user where username = ?";
		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, username);
			i = preStmt.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			MysqlConnection.closeDB(null, preStmt, conn);
		}
		return i;
	}
	
	public int modifyUser(User user) {
		int i = 0;
		Connection conn = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		String sql = "upload user set password = ?  where username = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			i = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			MysqlConnection.closeDB(null, ps, conn);
		}
		return i;
	}
	
	public String queryUser(String username) {
		//User user = null;
		Connection conn = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user where username = ?";
		String password = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//user = new User(rs.getString("username"), rs.getString("password"));
				password = rs.getString("password");
				break;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			MysqlConnection.closeDB(null, ps, conn);
		}
		return password;
	}
	
	public List<User> queryAllUsers(){
		List<User> user = new ArrayList<User>();
		Connection conn = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user";
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				user.add(new User(rs.getString("username"), rs.getString("password")));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			MysqlConnection.closeDB(null, ps, conn);
		}
		return user;
	}
}
