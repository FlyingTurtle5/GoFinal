package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for the position of the Board
 * 
 * @author Christin Meichsner & Rebecca Schümann
 * 
 */
public class Position implements Serializable {

	private static final long serialVersionUID=0;
	private int row;
	private int column;
	
	/**Constructor of Position with a given row and column
	 * @param row: the row of the position
	 * @param column: the column of the position
	 */
	public Position(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
	}
	
	/**get-method for the row
	 *  @return the row of the position
	 */
	public int getRow() {
		return row;
	}

	/**set-method for the row
	 * sets the attribute for the row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**get-method for the column
	 *  @return the column of the position
	 */
	public int getColumn() {
		return column;
	}

	/**set-Method for the column
	 * sets the attribute for the column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
		
	public Position[] neighbors() {
		return new Position[]{new Position(row-1, column),
							  new Position(row,column+1),
							  new Position(row+1,column),
							  new Position (row,column-1)};
	}
	@Override
	public boolean equals (Object o) {
	if(o==this) {
	return true;
	}
	if(!(o instanceof Position)) {
	return false;
	}
	Position p= (Position) o;
	return this.row==p.row && this.column==p.column; 
	}

	@Override
	public int hashCode() {
	return Objects.hash(row, column);
	}

}
