package sk.mihacodes.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDatabase {

	private final String url = "jdbc:postgresql://localhost/tododb";
	private final String user = "postgres";
	private final String password ="sa";
	
	public Connection createConnection() {
		
		Connection con = null;
		
		try {
			
			con = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		
		return con;
	}
	
	public void closeConnection(Connection con) {
		
			try {
				
				if (con != null)
				con.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
