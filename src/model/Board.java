package model;

import model.Content;
import model.Position;
import java.io.Serializable;
import java.util.LinkedList;

	/**
	 * 
	 * @author Christin Meichsner and Rebecca Schümann
	 *
	 */

public class Board implements Serializable {
		
	/**
	 * @param board: array of the playing field
	 * @param whitePoints: integer for the points from content WHITE
	 * @param blackPoints: integer for the points from content BLACK
	 * @param BlackLast: last Position for content BLACK
	 * @param WhiteLast: last Position for content WHITE
	 */
	private static final long serialVersionUID = 1L;
	private Content[][] board;
	private int whitePoints;
	private int blackPoints;
	private Position BlackLast;
	private Position WhiteLast;

	/**
	 * Constructor of the Class Board. Constructs a matrix of type Content.
	 * @param i choice the size of matrix.
	 *  0 for 9x9
	 *  1 for 11x11
	 *  default is 19x19
	 * 	 */
	public Board(int i) {
		this.board = new Content[i][i];
		for(int row = 0; row < board.length; row++) {
			for(int column = 0; column < board[row].length; column++){
				board[row][column] = Content.EMPTY;
			}
		}
		this.whitePoints=0;
		this.blackPoints=0;
		this.BlackLast=null;
		this.WhiteLast=null;
	}	
	

	/**
	 * Creates a list of Content to capture
	 * Starts boolean checkAttack for adding stones to the list
	 */
	public LinkedList <Position> checkAttackList(Position start, Content toCapture, Content surrounder, Content ignore){
		
		LinkedList<Position> captured = new LinkedList<Position>();
		checkAttack(captured, new LinkedList<Position>(), start, toCapture, surrounder, ignore);
		return captured;
	}
	
	/**
	 * Takes the list of neighbors and add them to the list 'captured' and 'visited'
	 * if stone is surrounder -> restarts method checkAttack
	 * if stone is Content Empty -> stone to capture is removed from list
	 * if stone is to capture BUT a stone later in the list will return false -> clear all stones from the list
	 * @param captured: list of surrounded position
	 * @param visited: list of checked position
	 * @param current: current position to check
	 * @param toCapture: converse ENUM
	 * @param surrounder: own ENUM
	 * @return
	 */
	private boolean checkAttack(LinkedList<Position> captured, LinkedList<Position> visited, Position current,
			Content toCapture, Content surrounder, Content ignore) {
		
		LinkedList<Position> neighbors = getValidNeighbors(current);
		
		if(getPosition(current)==surrounder) {
			for(Position position:neighbors) {
				if (getPosition(position)==toCapture) {
					checkAttack(captured, visited, position, toCapture, surrounder, ignore);
				}
			}
		}else {
			captured.add(current);
			visited.add(current);
			
			for(Position position:neighbors) {
				if (getPosition(position)==ignore) {
					captured.remove(current);
					return false;
				}
			}
			for(Position position:neighbors) {
				if(!visited.contains(position)&& getPosition(position)==toCapture) {
					if(!checkAttack(captured, visited, position, toCapture, surrounder, ignore)) {
						captured.remove(current);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * fetches the List for content to capture and will place EMPTY on the board
	 * @param surrounded
	 * @return points for either BLACK or WHITE captured content
	 */
	public int toCapture (LinkedList <Position> surrounded) {
		int points=0;
		for (Position p: surrounded) {
			placeContent(Content.EMPTY,p);
			points++;
		}
		return points;
	}
	
	/**
	 * creates a list of Content commiting suicide
	 * @param start
	 * @param toCapture
	 * @param surrounder
	 * @return list of probably dead own content
	 */
	public LinkedList <Position> checkSuicideList(Position start, Content toCapture, Content surrounder){

		LinkedList<Position> suicide = new LinkedList<Position>();
		checkSuicide(suicide, new LinkedList<Position>(), start, toCapture, surrounder);
		return suicide;
		}

	/**
	 * checks if placed content will commit suicide	
	 * @param suicide: list of surrounded own content
	 * @param visited: list of visited content
	 * @param current: current position
	 * @param toCapture: own ENUM 
	 * @param surrounder: converse ENUM
	 * @return
	 */
	private boolean checkSuicide(LinkedList<Position> suicide, LinkedList<Position> visited, Position current,
		Content toCapture, Content surrounder) {

		LinkedList<Position> neighbors = getValidNeighbors(current);

		if(getPosition(current)==toCapture) {
			for(Position position:neighbors) {
				if(getPosition(position)==surrounder) {
						checkSuicide(suicide, visited, position, toCapture, surrounder);
				}
			}
		}else {
			suicide.add(current);
			visited.add(current);

		for(Position position:neighbors) {
			if (getPosition(position)==Content.EMPTY) {
				suicide.remove(current);
				return false;
			}
		}
		for(Position position:neighbors) {
				if((!visited.contains(position) && getPosition(position)==surrounder) && 
				!checkSuicide(suicide, visited, position, toCapture, surrounder)) {
					suicide.remove(current);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * checks if the turn would be a suicide
	 * @param suicide
	 * @param content
	 * @param start
	 * @return if true the position will be EMPTY again and the player is forced to another turn
	 */
	public boolean suicideCheck (LinkedList <Position> suicide, Content content, Position start) {
		if(suicide.isEmpty()){
		return false;
		}else {
		System.out.println("Suicide. Choose again.");
		placeContent(Content.EMPTY, start);
		return true;
		}
	}

	
	/**
	 * Creates a list of neighbors and check if they are in Bounds
	 * @param current
	 * @return list of valid neighbors
	 */
	public LinkedList<Position> getValidNeighbors(Position current){
		
		LinkedList<Position> neighbors = new LinkedList<Position>();
		
		Position north = new Position(current.getRow()-1,current.getColumn());
		Position east = new Position(current.getRow(),current.getColumn()+1);
		Position south = new Position(current.getRow()+1,current.getColumn());
		Position west = new Position(current.getRow(),current.getColumn()-1);
		
		if (inBounds(north))
			neighbors.add(north);
		if (inBounds(east))
			neighbors.add(east);
		if (inBounds(south))
			neighbors.add(south);
		if (inBounds(west))
			neighbors.add(west);
		
		return neighbors;
	}
	
	/**
	* will count Points at the end of the game, either for black or white, depending on the given content
    * @param own 
	*/
	public void calculatePoints(Content own) {
		LinkedList<Position> surrounded = new LinkedList<Position>(); 
		Content ignore = own == Content.WHITE ? Content.BLACK : Content.WHITE;
		
		for(int i=0; i<board.length;i++) {
			for (int j=0; j<board[i].length;j++){
				if(board[i][j]==Content.EMPTY) {
					Position position = new Position (i,j);
					if (surrounded.contains(position)) {
						continue;
					}
					surrounded.addAll(checkAttackList(position,Content.EMPTY,own,ignore));
				}
			}
		}
		
		if (own == Content.WHITE)
			countPointsWhite(surrounded);
		else if (own == Content.BLACK)
			countPointsBlack(surrounded);
	}	
	
	/**
	 * counts the white points
	 */
	public void countPointsWhite(LinkedList<Position> surrounded){
		whitePoints += surrounded.size();
	}
	
	/**
	 * counts the black points
	 */	
	public void countPointsBlack(LinkedList<Position> surrounded){
			blackPoints += surrounded.size();
	}	

	/**
	 * changes the content of the position
	 * @param content
	 * @param position
	 */
	public void placeContent(Content content, Position position ){
		board[position.getRow()][position.getColumn()] = content ;
	}
	
	/**
	 * places WHITE at the position and checks if BLACK is surrounded, checks if the turn would be a suicide
	 * and will count points for surrounded BLACK
	 * @param position
	 * @return true if the position is WHITE
	 */
	public boolean checkWhite(Position position) {
		if(checkLastWhitePosition(position)) {
			board[position.getRow()][position.getColumn()]=Content.WHITE;
			WhiteLast=position;
			blackPoints -= toCapture(checkAttackList(position, Content.BLACK, Content.WHITE, Content.EMPTY));
			if(suicideCheck(checkSuicideList(position, Content.BLACK, Content.WHITE), Content.WHITE, position)) {
				return false;
			}
			return true;
			}
		return false;
	}
	
	/**
	 * places BLACK at the position and checks if WHITE is surrounded, checks if the turn would be a suicide
	 * and will count points for surrounded WHITE
	 * @param position
	 * @return true if the position is BLACK
	 */
	public boolean checkBlack(Position position) {
		if(checkLastBlackPosition(position)) {
			board[position.getRow()][position.getColumn()]=Content.BLACK;
			BlackLast=position;
			whitePoints -= toCapture(checkAttackList(position, Content.WHITE, Content.BLACK, Content.EMPTY));
			if(suicideCheck(checkSuicideList(position, Content.WHITE, Content.BLACK), Content.BLACK, position)) {
				return false;
			}
			return true;
			}
		return false;
	}
	
	/**
	 * @param position
	 * @return true if the position is EMPTY
	 */
	public boolean checkEmpty(Position position) {
		if(checkPosition(position)) {
			board[position.getRow()][position.getColumn()]=Content.EMPTY;
			return true;
			}
		return false;
	}
	
	/**
	 * @param position
	 * @return true if the position is within the range of the board and EMPTY
	 */
	public boolean checkPosition(Position position) {
		return inBounds(position) && getPosition(position) == Content.EMPTY;
	}
	
	/**
	 * checks the position for black and compares it with the last turn
	 * @param position
	 * @return if true the position is within the range of the board, empty and no repeated action
	 */
	public boolean checkLastBlackPosition(Position position) {
		if (inBounds(position) && getPosition(position) == Content.EMPTY){
				if (BlackLast == null || !position.equals(BlackLast)) {
					return true;
				}
		}
	System.out.println("You can't place the stone here.");
	return false;
	}
	
	/**
	 * checks the position for white and compares it with the last turn
	 * @param position
	 * @return if true the position is within the range of the board, empty and no repeated action
	 */
	public boolean checkLastWhitePosition(Position position) {
		if (inBounds(position) && getPosition(position) == Content.EMPTY){
				if (WhiteLast == null || !position.equals(WhiteLast)) {
					return true;
				}
		}
	System.out.println("You can't place the stone here.");
	return false;
	}

	/**
	 * @param position
	 * @return true if the position is within the range of the board
	 */
	private boolean inBounds(Position position) {
		return (position.getRow() >= 0 && position.getRow() < board.length && 
				position.getColumn() >= 0 && position.getColumn()< board[position.getRow()].length);	
	}
	
	/**
	 * @param position
	 * @return the row and column of the position
	 */
	public Content getPosition(Position position) {
		return board[position.getRow()][position.getColumn()];
	}
	
	/**
	 * @return Points for player White
	 */
	public int getWhitePoints() {
		return whitePoints;
	}
	
	/**
	 * @return Points for player Black
	 */
	public int getBlackPoints() {
		return blackPoints;
	}
	
	/**
	 * toString method for the board
	 */
	public String toString(){
		String s = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				s += board[i][j].toString() + "\t";
			}
			s += "\n";
		}
		return s;
		}
	
	}
