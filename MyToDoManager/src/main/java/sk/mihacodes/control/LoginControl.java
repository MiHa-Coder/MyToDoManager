package sk.mihacodes.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sk.mihacodes.model.util.ConnectionToDatabase;

public class LoginControl {

	private String username;
	private String password;
	private Statement stmt;
	private PreparedStatement stat;
	private ResultSet rs;
	private ConnectionToDatabase ctd;

	public LoginControl(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public boolean loginTheUser() {
		ctd = new ConnectionToDatabase();
		Connection con = ctd.createConnection();
		boolean b = false;
		
		try {

			String pass = String.valueOf(password.hashCode());

			String sql = "SELECT username, password FROM TODOlogin";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();

			while (rs.next()) {
				if (username.equals(rs.getString(1)) && pass.equals(rs.getString(2))) {
					b = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (stmt != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return b;
	}
}
