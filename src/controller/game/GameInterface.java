package controller.game;

import model.Board;

public interface GameInterface {
	public void update();
	public Board getBoard();
	public int numberOfCapturedStones(int playerID);
}
