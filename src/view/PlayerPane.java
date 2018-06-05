package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.Board;
import model.Content;
import model.Position;

public class PlayerPane extends Pane{
	
	private int unit;
	private double offset;
	private Board board;
	private int boardsize;
	
	public PlayerPane(int boardsize){
		super();
		this.board = new Board(boardsize);
		this.unit = 50;
		this.offset = unit / 8;
		this.setPrefSize(3*unit,3*unit);
		setGrid(boardsize);
		this.boardsize = boardsize;
	}
	
	private void setGrid(int boardsize){				
		for(int i = 0; i < boardsize;i++) {
			//waagerecht
			getChildren().addAll(new Line(unit*i,0,unit*i,unit*(boardsize-1)));
			//senkrecht
			getChildren().addAll(new Line(0,unit*i,unit*(boardsize-1),unit*i));
		}
	}	
	
	private void addBlack(Position position) {
		double centerX = position.getColumn() * unit;
		double centerY = position.getRow() * unit;
		double radius = unit/2 - offset;
		Circle circle = new Circle(centerX, centerY,radius, Color.BLACK);
		getChildren().add(circle);
	}
	
	private void addWhite(Position position) {
		double centerX = position.getColumn() * unit;
		double centerY = position.getRow() * unit;
		double radius = unit/2 - offset;
		Circle circle = new Circle(centerX, centerY,radius, Color.AZURE);
		getChildren().add(circle);
	}
	
	public void update(Board board, int boardsize){
		resetBoardByInconsistency(board, boardsize);
		for(int row = 0; row < boardsize; row++){
			for(int column = 0; column < boardsize; column++){
				Position position = new Position(row,column);
				Content c = board.getPosition(position);
				if(this.board.getPosition(position) != c){
					if(c == Content.BLACK){
						this.board.placeContent(Content.BLACK, position);
						addBlack(position);
					} else  if(c == Content.WHITE){
						this.board.placeContent(Content.WHITE, position);
						addWhite(position);
					}
				}
			}
		}		
	}
	
	private void resetBoardByInconsistency(Board board, int boardsize){
		for(int row = 0; row < boardsize; row++){
			for(int column = 0; column < boardsize; column++){
				Position position = new Position(row,column);
				if(this.board.getPosition(position) != board.getPosition(position) && this.board.getPosition(position) != Content.EMPTY){
					this.board = new Board(boardsize);
					this.getChildren().clear();
					this.setGrid(boardsize);
				}
			}
		}
	}
	
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}	
	public int getBoardsize() {
		return boardsize;
	}
}
