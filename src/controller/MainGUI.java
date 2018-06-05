package controller;

import controller.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application{
	
	public void start(Stage primarySTage) throws Exception{
		Game game = new Game(9);
		
	}
	public static void main (String[] args) {
		launch(args);
	}
}
