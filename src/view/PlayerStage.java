package view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlayerStage extends Stage{

private ScenePane pane;
	
	/**
	 * Constructor gets the parent node of the Scene-Graph
	 * @param The parent node of the Scene-Graph
	 */
	public PlayerStage(ScenePane pane){
		super();
		this.pane = pane;
		init();
	}
	
	/**
	 * private Method for setting the title and a scene to the stage 
	 */
	private void init(){
		setTitle("Go");
		//size of windows
		Scene scene = new Scene(pane,600,600, Color.WHITESMOKE);
		//scene.getStylesheets().add("style.css");
		setScene(scene);
	
	}
}
