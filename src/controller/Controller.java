package controller;

import model.Game;
import model.Model;
import view.MenuView;
import view.GameView;
import view.Window;

public class Controller {
	
	private Model model;
	private GameController gamecontroller;
	
	public Controller(Model model) {
		this.model = model;
	}
	
	public void menuSelection(String selection) {
		
		switch (selection) {
			case MenuView.SPGAME:
				//TODO
				Window spWindow = new GameView();
				spWindow.displayWindow("SP");
				break;
			case MenuView.MPGAME: 
				//TODO
				Window mpWindow = new GameView();
				mpWindow.displayWindow("MP");
				break;
			case MenuView.LEADERBOARD: 
				//TODO
				break;
		}
		
	}

	public void spStart(String difficulty, String color) {
		
		this.model = new Game(difficulty, color);
		this.gamecontroller = new GameController((Game)this.model);
	}

	public Model getModel() { return this.model; }

	public GameController getGameController() { return this.gamecontroller; }
}
