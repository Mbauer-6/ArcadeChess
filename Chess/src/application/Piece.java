package application;

public abstract class Piece {
	private String name = "";
	
	public Piece() {
		this.name = "No Name";
	}
	
	public Piece(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public abstract String getImg();
	public abstract int getColumn();
	public abstract int getRow();
	public abstract String getColor();
	public abstract int getValue();
	public abstract int getRank();

	
	@Override
	public String toString() {
		return "Piece Info: Name: " + this.name;
	}
	
	public abstract void move(int row, int col);
}
