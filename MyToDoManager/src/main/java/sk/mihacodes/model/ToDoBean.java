package sk.mihacodes.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import sk.mihacodes.control.TaskPriorityControl;
import sk.mihacodes.model.util.ConnectionToDatabase;
import tornadofx.control.DateTimePicker;

public class ToDoBean {

	private String toDoTitle;
	private String toDoDescription;
	private String toDoPriority;
	private DateTimePicker targetDate;

	public String getToDoTitle() {
		return toDoTitle;
	}

	public void setToDoTitle(String toDoTitle) {
		this.toDoTitle = toDoTitle;
	}

	public String getToDoDescription() {
		return toDoDescription;
	}

	public void setToDoDescription(String toDoDescription) {
		this.toDoDescription = toDoDescription;
	}

	public String getToDoPriority() {
		return toDoPriority;
	}

	public void setToDoPriority(String toDoPriority) {
		this.toDoPriority = toDoPriority;
	}

	public DateTimePicker getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(DateTimePicker targetDate) {
		this.targetDate = targetDate;
	}

	public void addToDoToDatabase(String username) {

		ConnectionToDatabase ctd = new ConnectionToDatabase();
		Connection con = ctd.createConnection();
		Statement stmt = null;
		PreparedStatement stat;
		ResultSet rs;

		try {

			String sql = "SELECT MAX(id) AS max_id FROM TODOlist";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			int id = 0;

			if (rs.next()) {

				id = rs.getInt("max_id") + 1;
				stmt = con.createStatement();
				sql = "INSERT INTO TODOlist " + "VALUES (" + id + ", '" + username + "', '" + getToDoTitle() + "', '"
						+ getToDoDescription() + "', '" + getToDoPriority() + "', '"
						+ getTargetDate().getDateTimeValue() + "')";
				stmt.executeUpdate(sql);

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

	public ObservableList<Node> readTasksFromList (String username){
		
		ConnectionToDatabase ctd = new ConnectionToDatabase();
		Connection con = ctd.createConnection();
		Statement stmt = null;
		PreparedStatement stat;
		ResultSet rs;
		
		ObservableList<Node> taskObservList = FXCollections.observableArrayList();
		Label taskLabel = new Label();
		TaskPriorityControl taskColor = new TaskPriorityControl();
		
		try {

			String sql = "SELECT id, username, " + 
					"tasktitle, " + 
					"taskdescription, " + 
					"taskpriority, " + 
					"targetdate " + 
					"FROM TODOlist WHERE username = '" + username + "';";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();

			while (rs.next()) {
//				rs.getInt(1);
//				rs.getString(2);
				
				String toDoTitle = new String(rs.getString(3));
				String toDoDescription = new String(rs.getString(4));
				String toDoPriority = new String(rs.getString(5));
				String targetDate = new String(rs.getTimestamp(6).toString());
				
				
				
				taskLabel = new Label(toDoTitle + " - " + targetDate);
				taskLabel.setBackground(new Background(new BackgroundFill(taskColor.taskPriorityColor(toDoPriority), CornerRadii.EMPTY, Insets.EMPTY)));
				taskObservList.add(taskLabel);
				
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
		return taskObservList;
	}
}
