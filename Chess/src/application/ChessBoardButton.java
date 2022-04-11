package application;

import javafx.scene.control.Button;

public class ChessBoardButton extends Button {
	private Piece piece = null;
	private boolean selected;
	private int row;
	private int col;
	private String color;

	public ChessBoardButton(Piece piece) {
		this.selected = false;
		this.piece = piece;
		this.color = "white";
	}

	public void setPiece(Piece piece) {
		piece = this.piece;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setRow(int row) {
		this.row = row;
	}
	public int getRow() {
		return this.row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getCol() {
		return this.col;
	}
	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	@Override
	public String toString() {
		return this.piece.getName();
	}

	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getColor() 
	{
		return color;
	}
}
