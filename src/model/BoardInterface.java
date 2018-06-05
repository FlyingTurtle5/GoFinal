package model;

import model.Content;
import model.Position;

public interface BoardInterface{	
	public void placeContent(Content c, Position p);
	//public int capture(Position p, Content content);
	public void calculatePoints();
}
