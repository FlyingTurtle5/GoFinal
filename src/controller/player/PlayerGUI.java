package controller.player;

import java.io.EOFException;

import controller.game.Game;
import view.PlayerPane;
import view.PlayerStage;
import view.ScenePane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import model.Content;
import model.Position;

public class PlayerGUI implements PlayerInterface{
	
	private Position position;
	private PlayerPane playerPane;
	private ScenePane scenePane;
	private PlayerStage stage;
	private Content content;
	private Game game;
	private int id;
	private boolean enable;
	private boolean pat;
	
	
	public PlayerGUI(Content content, Game game, int id) {
		this.content = content;
		this.game = game;
		this.id = id;
		playerPane = new PlayerPane(game.getBoardsize());
		this.pat = false;
		
		playerPane.setOnMouseClicked((MouseEvent me) -> {
            if (enable){
            	int column =correctPosition((int) me.getX())/playerPane.getUnit();
            	int row= correctPosition((int) me.getY())/playerPane.getUnit();
            	
            	position = new Position(row,column);
            	
            	System.out.println(position);
            	if(game.getBoard().checkPosition(position)){
            		game.update();
            		playerPane.update(game.getBoard(),game.getBoardsize());
            	}
            }
            setPat(false);
        });
	
		MenuBar menuBar = setMenu();
		scenePane = new ScenePane(playerPane,id,menuBar);
		stage = new PlayerStage(scenePane);
		stage.show();
		
		scenePane.getPatButton().setOnAction(new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent event) {
				try{ 
					setPat(true);
					System.out.println("Pat was used.");
					game.update();}
				catch (Exception e) {
				}
			}
		});		
		
	}
		
	public int correctPosition(int location) {
		int unit = playerPane.getUnit();
		if(location%unit >= unit/2) {
			return (location+unit - location%unit);
		}else {
			return (location - location%unit);
		}
	}
	
	
	
	/**
	 * Generates a Menu for Saving and Loading a game
	 * @return the MenuBar with the items Speichern, Laden
	 */
	private MenuBar setMenu(){
		MenuBar menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(true);
		MenuItem menusafe = new MenuItem("Speichern");
		menusafe.setOnAction((ActionEvent t)->	{game.safe();});	
		MenuItem menuload = new MenuItem("Laden");
		menuload.setOnAction((ActionEvent t)-> {game.load();playerPane.update(game.getBoard(),game.getBoardsize());});
		Menu safeload = new Menu("Speichern/Laden");
		safeload.getItems().addAll(menusafe,menuload);
		menuBar.getMenus().add(safeload);
		return menuBar;
	}
	
	
	
	/**
	 * Enables the player, the playerPane gets an updated board.
	 * @see controller.player.Player#enable()
	 */
	public void enable(){
		enable = true;
		playerPane.update(game.getBoard(),game.getBoardsize());
		scenePane.enable();
	}
	
	/**
	 * Disables the player, the playerPane gets an updated board
	 * @see controller.player.Player#disable()
	 */
	public void disable(){
		enable = false;
		playerPane.update(game.getBoard(), game.getBoardsize());
		scenePane.disable();
	}
	
	/**
	 * Gets the message that the player looses
	 * @see controller.player.Player#lose()
	 */
	public void lose(){
		scenePane.lose();
	}
	
	/**
	 * Gets the message that the player wins
	 * @see controller.player.Player#win()
	 */
	public void win() {
		scenePane.win();
	}

	/**
	 * Gets the message that the game is drawn
	 * @see controller.player.Player#draw()
	 */
	public void draw() {
		scenePane.draw();
	}
	
	
	
	/**
	 * returns the selected Position of the player and setting the current position to null
	 * @see controller.player.Player#placeContent()
	 */
	public Position placeContent() {
			Position p = position;
			position = null;
			return p;
	}



	@Override
	public void win(int ownPoints, int opponentPoints) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void loose(int ownPoints, int opponentPoints) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean getPat() {
		
		return pat;
	}



	public void setPat(boolean b) {
		this.pat = b;
		
	}


	
	public void usePat() {
		/*int status = game.getStatus();
		if(status ==1) {
			game.getPlayer1().setPat(true);
		}else {
			game.getPlayer2().setPat(true);					
		}
		*/
		
	}
	
}
