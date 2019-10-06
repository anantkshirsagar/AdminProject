package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		// ------------------Mysql Database Configuration------------------
		String PORT = "3306";
		String DRIVER = "com.mysql.jdbc.Driver";
		String USERNAME = "root";
		String PASSWORD = "root";
		String DB_NAME = "photo_competition";
		String URL = "jdbc:mysql://localhost:" + PORT + "/" + DB_NAME;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from theme");
			System.out.println(rs.next());
		} catch (Exception e) {
		}
	}
}
