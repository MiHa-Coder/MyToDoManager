package sk.mihacodes.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sk.mihacodes.control.LoginControl;
import sk.mihacodes.control.RegistrationControl;
import sk.mihacodes.control.TaskListControl;
import sk.mihacodes.control.TaskPriorityControl;
import sk.mihacodes.model.LoginBean;
import sk.mihacodes.model.ToDoBean;
import tornadofx.control.DateTimePicker;


public class ToDoMainFrame extends Application implements IButtons {

	LoginBean lb = new LoginBean();
	ToDoBean todoBean = new ToDoBean();
	TaskListControl tlc = new TaskListControl();

	TaskPriorityControl tpc = new TaskPriorityControl();
	
	@Override
	public void start(Stage stage) throws Exception {
		initUI(stage);

	}

	private void initUI(Stage stage) {

		var root = new VBox();

		var scene = new Scene(root, 300, 800);

		root.getChildren().add(login());

		stage.setTitle("MyToDoManager");
		stage.setScene(scene);
		stage.show();
	}

	private VBox addToDoTask() {
		ToDoBean todoBean = new ToDoBean();
		Label addNewTaskLabel = new Label("Add New Task");
		addNewTaskLabel.setFont(Font.font("Sans Serif", FontWeight.BOLD, 30));

		Label taskTitleLabel = new Label("Task Title");

		TextField taskTitleField = new TextField();

		Label taskDescriptionLabel = new Label("Task Description");

		TextField taskDescriptionField = new TextField();

		Label taskPriorityLabel = new Label("Task Priority");

		ObservableList<String> taskPriorityList = FXCollections.observableArrayList("High", "Medium", "Low");

		
		ChoiceBox<String> taskPriorityChoice = new ChoiceBox<String>(taskPriorityList);

		Label taskTargetDateLabel = new Label("Target Date");

		DateTimePicker taskTargetDatePicker = new DateTimePicker();
		taskTargetDatePicker.setOnAction(event -> {
			LocalDateTime taskTargetDate = taskTargetDatePicker.getDateTimeValue();

		});

		VBox vbox;
		Button taskSaveButton = new Button("Save");

		vbox = new VBox(addNewTaskLabel, taskTitleLabel, taskTitleField, taskDescriptionLabel, taskDescriptionField,
				taskPriorityLabel, taskPriorityChoice, taskTargetDateLabel, taskTargetDatePicker, taskSaveButton);

		taskSaveButton.setOnAction(event -> {
			
			todoBean.setToDoTitle(taskTitleField.getText());
			todoBean.setToDoDescription(taskDescriptionField.getText());
			todoBean.setToDoPriority(taskPriorityChoice.getValue());
			todoBean.setTargetDate(taskTargetDatePicker);
			
			todoBean.addToDoToDatabase(lb.getUsername());
			
			vbox.getChildren().clear();

			vbox.getChildren().add(new HBox(new Label(taskTitleField.getText().concat(" | ")), new Label(
					taskTargetDatePicker.getDateTimeValue().format(DateTimeFormatter.ofPattern("HH:mm\ndd.MM.yyyy")))));

		});
		return vbox;
	}

	private VBox login() {

		Label username = new Label("Username: ");
		TextField usernameField = new TextField();
		HBox usernameHBox = new HBox(username, usernameField);
		usernameHBox.setAlignment(Pos.CENTER);

		Label password = new Label("Password: ");
		PasswordField passwordField = new PasswordField();
		HBox passwordHBox = new HBox(password, passwordField);
		passwordHBox.setAlignment(Pos.CENTER);

		HBox loginButtons = new HBox(registerButton, loginButton);
		loginButtons.setAlignment(Pos.CENTER);

		Label registerWarningMessage = new Label();
		VBox vbox = new VBox(usernameHBox, passwordHBox, loginButtons, registerWarningMessage);
		vbox.setAlignment(Pos.CENTER);

		ListView listView = new ListView();
		listView.setEditable(true);
		listView.setPrefHeight(500);

		btn.setOnAction(event -> {

			listView.getItems().add(addToDoTask());
		});

		registerButton.setOnAction(event -> {
			RegistrationControl registrationControl = new RegistrationControl(usernameField.getText(),
					passwordField.getText());
			
			registrationControl.addUserToDatabase();
			registerWarningMessage.setText(registrationControl.getRegisterWarningMessage());

			usernameField.clear();
			passwordField.clear();
		});

		loginButton.setOnAction(event -> {
			
			LoginControl lc = new LoginControl(usernameField.getText(), passwordField.getText());

			if (lc.loginTheUser() == true) {
				
				lb.setUsername(usernameField.getText());

				vbox.getChildren().clear();
				var hbox = new HBox(btn);

				vbox.getChildren().add(hbox);
				
//				listView.getItems().addAll(todoBean.readTasksFromList(lb.getUsername()));
//				listView.setStyle(tpc.taskPriorityColor(lb.getUsername()));
			
//				tlc.taskListView(lb.getUsername());
				
				vbox.getChildren().add(tlc.taskListView(lb.getUsername()));
				vbox.getChildren().add(listView);
				
			} else {
				registerWarningMessage.setText("*Username or Password is incorrect");

				usernameField.clear();
				passwordField.clear();
			}

		});

		return vbox;
	}
}
