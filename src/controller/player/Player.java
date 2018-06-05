package controller.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import controller.game.Game;
import model.Content;
import model.Position;

public class Player implements PlayerInterface{
	
	private Content content;
	private Game game;
	private int id;
	private boolean pat;
	
		
	/**
	 * Constructor of the Class Player
	 * @param content
	 * @param game
	 * @param id
	 */
		public Player(Content content, Game game, int id) {
			this.content=content;
			this.game=game;
			this.id=id;
			this.pat=false;
		}
	
		public void usePat() {
			int pat = -1;
			System.out.println("Do you like to pass? no (0), yes (1)");
			pat = readInt();
			if(pat == 1) {
				this.pat=true;
			} else {
				this.pat=false;
			}
		}
		
		public boolean getPat() {
			return pat;
		}
		
		public void setPat(boolean pat) {
			this.pat=pat;
		}

	/**
	 * 	
	 * @return 
	 */
	private int readInt() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String eingabe ="";
			Integer string_to_int;
			eingabe = input.readLine();
			string_to_int = new Integer(eingabe);
			
			return string_to_int.intValue();
			
		}catch(Exception e) {
			return -1;
		}
	}
	
	/**
	 * changes to the next player, updates the game and prints out the board.
	 */
	public void enable() {
		System.out.println("Player "+ id);
		System.out.println(game.getBoard());
		System.out.println("Do you like to save (1), load (2) or continue (0) ?");
		int answere = readInt();
		if(answere == 1) {			
			game.safe();
		}
		if(answere == 2) {
			game.load();
			System.out.println(game.getBoard());
			game.update();
		}else if(answere == 0){
			System.out.println("Game continues.");
		}	
		game.update();		
	}
	
	/**
	 * disables the turn of player
	 */
	public void disable() {
			
	}

	/**
	 * 
	 */
	public Position placeContent() {
		int row;
		int column;
		Position position;
					
			do {
				System.out.println("Which row?");
				row = readInt();
				System.out.println("which column?");
				column = readInt();
				position = new Position(row,column);
			}
			while(!game.getBoard().checkPosition(position));
					
			return position;		
	}

	/**
	 * 
	 */
	public void win(int ownPoints, int opponentPoints) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void loose(int ownPoints, int opponentPoints) {
		// TODO Auto-generated method stub
		
	}
	
}
