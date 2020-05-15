package sk.mihacodes.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sk.mihacodes.model.util.ConnectionToDatabase;

public class RegistrationControl {

	private String username;
	private String password;
	private Statement stmt;
	private PreparedStatement stat;
	private ResultSet rs;
	private ConnectionToDatabase ctd;
	private String registerWarningMessage;

	public RegistrationControl(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getRegisterWarningMessage() {
		return registerWarningMessage;
	}

	public void setRegisterWarningMessage(String message) {
		this.registerWarningMessage = message;
	}

	public void addUserToDatabase() {

		ctd = new ConnectionToDatabase();
		Connection con = ctd.createConnection();

		try {

			String sql = "SELECT username FROM TODOlogin";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			boolean b = false;
			
			while (rs.next()) {
				if (username.equals(rs.getString(1))) {
					b = true;
					setRegisterWarningMessage("*User already exists. Please try again!");

				}

			}
			if (b == false) {
				sql = "SELECT MAX(id) AS max_id FROM TODOlogin";
				stat = con.prepareStatement(sql);
				rs = stat.executeQuery();
				int id = 0;
				
				if (rs.next()) {
					
					id = rs.getInt("max_id") + 1;
					stmt = con.createStatement();
					sql = "INSERT INTO TODOlogin (id, username, password)" + "VALUES (" + id + ", '" + username + "', '"
							+ password.hashCode() + "')";
					stmt.executeUpdate(sql);
					setRegisterWarningMessage("*User is Registered successfully");
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
	}
}
