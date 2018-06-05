package persistence;

import java.io.Serializable;

import model.Board;


public class PersistenceObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8077621001254127763L;
	private int status;
	private Board board;
		
		public PersistenceObject(int status, Board board) {
			this.status = status;
			this.board = board;
		}
		public int getStatus() {
			return status;
		}
		public Board getBoard() {
			return board;
		}
}
