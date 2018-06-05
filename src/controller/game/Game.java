package controller.game;

import controller.player.Player;
import controller.player.PlayerGUI;
import model.Board;
import model.Content;
import persistence.PersistenceObject;
import persistence.SaveLoad;

public class Game {
	
	private PlayerGUI player1;
	private PlayerGUI player2;
	
	private Board board;
	private int status;
	private int boardsize;
	
	// the different states of the game
	private final int PLAYER1 = 1;
	private final int PLAYER2 = 2;
	private final int winPLAYER1 = 3;
	private final int winPLAYER2 = 4;
	private final int draw = 5;
	
	/**
	 * Constructor of the Class Game. A board is set and two players are initialized.
	 * @param groesse size of the matrix of the board.
	 */
	public Game(int size) {
		this.boardsize = size;
		this.board = new Board(size);
		this.player1 = new PlayerGUI(Content.WHITE, this, 1);
		this.player2 = new PlayerGUI(Content.BLACK, this, 2);
		this.status = PLAYER1;		
		
		player1.enable();
		player2.disable();
	}	
	//Game over		
	public void endOfGame() {
		player1.disable();
		player2.disable();
		System.out.println("The Game is over!");
		System.out.println("Player 1 has "+ board.getWhitePoints()+" points.");
		System.out.println("Player 2 has "+ board.getBlackPoints()+" points.");
		
	}
	
	/**
	 * If the enabled player places a content on the board, player will be disabled and status changes to the other player.
	 */
	public void update() {
		
		if(player1.getPat() && player2.getPat()) {
			endOfGame();
		}else {
		
			switch(status) {		
				case PLAYER1:
					//player1.setPat(false);
					//player1.usePat();
					if(player1.getPat() || board.checkWhite(player1.placeContent())) {
						status=PLAYER2;
						player1.disable();				
					}
					break;	
		
				case PLAYER2:
					//player2.setPat(false);
					//player2.usePat();
					if(player2.getPat() || board.checkBlack(player2.placeContent())) {
						status=PLAYER1;
						player2.disable();			
					}
					break;
				}		
		
			if(status == PLAYER1) {
				player1.enable();
			}else if(status == PLAYER2){
				player2.enable();
			}
		}		
	}
	
	public void safe() {
		PersistenceObject po = new PersistenceObject(status,board);
		SaveLoad.safe(po);
	}
	
	public void load() {
		PersistenceObject po = SaveLoad.load();
		
		status = po.getStatus();
		board = po.getBoard();
		
		switch(status) {		
			case PLAYER1:
				player1.enable();
				player2.disable();
				break;
				
			case PLAYER2:
				player2.enable();
				player2.disable();
				break;
		}
	}
	
	public PlayerGUI getPlayer1() {
		return player1;
	}
	public void setPlayer1(PlayerGUI player1) {
		this.player1 = player1;
	}
	public PlayerGUI getPlayer2() {
		return player2;
	}
	public void setPlayer2(PlayerGUI player2) {
		this.player2 = player2;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	/**get-method of the board 
	 * @return
	 */
	public Board getBoard() {
		return board;
	}
	public int getBoardsize() {
		return boardsize;
	}
}
