package view;

import controller.Main;

public class ViewHandler {

	public static void selectAction(String selection) {
		Main.getController().menuSelection(selection);
	}

	public static void spStart(String difficulty, String color) {
		Main.getController().spStart(difficulty, color);
	}
	
}
