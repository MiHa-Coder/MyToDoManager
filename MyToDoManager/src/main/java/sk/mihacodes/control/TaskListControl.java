package sk.mihacodes.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import sk.mihacodes.model.ToDoBean;

public class TaskListControl {

	public ListView<Node> taskListView(String username) {
		ListView<Node> taskList = new ListView<Node>();
		taskList.setEditable(true);
		taskList.setPrefHeight(500);
		ToDoBean tdb = new ToDoBean();
		
		ObservableList<Node> obsList = tdb.readTasksFromList(username);

		
		for (Node node : obsList) {
			taskList.getItems().add(node);
		}

		return taskList;
	}
}
