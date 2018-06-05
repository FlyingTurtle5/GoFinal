package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScenePane extends BorderPane{
	
	private PlayerPane playerPane;
	private int id;
	private Color playerColor;
	private ProgressBar progressBar;
	private Label label;
	private String labelDisable;
	private String labelEnable;
	private String labelWin;
	private String labelLose;
	private String labelDraw;
	private MenuBar menueBar;
	
	private Button quitButton;
	private Button patButton;
	
	public ScenePane(PlayerPane playerPane, int id, MenuBar menueBar) {
		super();
		this.playerPane = playerPane;
		this.menueBar = menueBar;
		this.id = id;
		this.quitButton = new Button("Quit");
		this.patButton = new Button("Pat");
		
		if(id==1) {
			playerColor = Color.AZURE;
		}else {
			playerColor = Color.DIMGREY;
		}
		progressBar = new ProgressBar();
		progressBar.setPrefWidth(120);
		label = new Label("");
		int id1 = id%2 +1;
		labelDisable = "Waiting for player " + id1;
		labelEnable = "It's your turn!";
		labelWin = "You won!";
		labelLose = "You lose!";
		labelDraw = "Draw";

		init();		
		this.getChildren().add(menueBar);
		
		
	}
	private void init(){
		AnchorPane center = new AnchorPane();
		center.getChildren().add(playerPane);
		AnchorPane.setTopAnchor(playerPane, 60.0);
		AnchorPane.setLeftAnchor(playerPane, 80.0);
		this.setCenter(center);
		
		VBox rightBox = new VBox(20);
	    
	    rightBox.getChildren().addAll(quitButton,patButton);
	    this.setRight(rightBox);
	    
	    
		
	//	BorderPane.setAlignment(playerPane, Pos.CENTER);
		HBox bottomBox = new HBox(30);
		
		Text text = new Text("Spieler " + id);
		text.setFill(playerColor);
		text.setEffect(new Lighting());
	    text.setFont(Font.font(Font.getDefault().getFamily(), 50));
	    
	    VBox bottomRightBox = new VBox(10);
	    
	    bottomRightBox.getChildren().addAll(progressBar, label);
	    bottomBox.getChildren().addAll(text,bottomRightBox);
	    this.setBottom(bottomBox);	    
	    
	    
	}
	
	public Button getPatButton() {
		return patButton;
	}
	public void setPatButton(Button pat) {
		this.patButton = pat;
	}
	/**
	 * Setting the label to make-your-choice and sets the ProgressBar to "ready"
	 */
	public void enable(){
		progressBar.setProgress(1);
		label.setText(labelEnable);
	}
	
	/**
	 * Setting the label to waiting and sets the ProgressBar to "in-Progress"
	 */
	public void disable(){
		progressBar.setProgress(-1);
		label.setText(labelDisable);
	}
	
	/**
	 * Setting the label to win-message
	 */
	public void win(){
		progressBar.setProgress(1);
		label.setText(labelWin);
	}
	
	/**
	 * Setting the label to lose-message
	 */
	public void lose(){
		progressBar.setProgress(1);
		label.setText(labelLose);
	}
	
	/**
	 * Setting the label to draw-message
	 */
	public void draw(){
		progressBar.setProgress(1);
		label.setText(labelDraw);
	}
	
}
