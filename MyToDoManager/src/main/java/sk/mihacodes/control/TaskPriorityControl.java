package sk.mihacodes.control;

import javafx.scene.paint.Color;

public class TaskPriorityControl {
	
	public Color taskPriorityColor(String priority) {
		Color color = null;
		
//				if(priority.equals("High"))
//					color = "-fx-control-inner-background-color: RED";
//				else if(priority.equals("Medium"))
//					color = "-fx-control-inner-background-color: ORANGE";
//				else if(priority.equals("Low"))
//					color =  "-fx-control-inner-background-color: GREEN";

		if(priority.equals("High"))
			color = Color.RED;
		else if(priority.equals("Medium"))
			color = Color.ORANGE;
		else if(priority.equals("Low"))
			color =  Color.GREEN;

		return color;
	}
}
