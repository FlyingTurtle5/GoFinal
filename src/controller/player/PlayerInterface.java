package controller.player;

import model.Position;

public interface PlayerInterface {
	public void enable();
	public void disable();
	public Position placeContent();
	public void win (int ownPoints, int opponentPoints);
	public void loose(int ownPoints, int opponentPoints);
}
