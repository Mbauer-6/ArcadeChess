package application;

public class King extends Piece {
	private int rank = 6;
	private int value = 10000;
	private String img = "NULL";
	private String color = "NULL";
	private int rowLocation = 0;
	private int colLocation = 0;
	
	public King() {
		super("king");
		this.rank = 6;
		
	}
	
	public King(String name, String color, int row, int col) {
		super(name);
		this.rank = 6;
		this.value = 10000;
		this.color = color;
		this.img = this.color +"_"+ this.getName() +".png";
		this.rowLocation = row;
		this.colLocation = col;
	}
	
	public void setRow(int row) {
		this.rowLocation = row;
	}
	
	public int getRow() { 
		return this.rowLocation; 
	}
	
	public void setColumn(int column) {
		this.colLocation = column; 
	}
	
	public int getColumn() {
		return this.colLocation; 
	}

	public int getValue() {
		return this.value; 
	}
	
	public String getImg() {
		return this.img; 
	}
	
	public int getRank() {
		return this.rank; 
	}
	
	public void setColor(String color) {
		this.color = color; 
	}
	
	public String getColor() {
		return this.color; 
	}
	
	public void move(int row, int col) { 
		this.rowLocation = row;
		this.colLocation = col;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Color: "+ this.color+ " Rank: "+ this.rank+" Value: "+this.value
				+" Image: "+this.img;
	}
}
