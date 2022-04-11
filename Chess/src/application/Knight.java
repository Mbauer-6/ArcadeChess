package application;

public class Knight extends Piece {
	private int rank = 0;
	private int value = 400;
	private String img = "NULL";
	private String color = "NULL";
	private int rowLocation = 0;
	private int colLocation = 0;
	
	public Knight() {
		super("knight");
		this.value = 400;
	}
//Constructor for regular chess where rank is defaulted to 0.
	public Knight(String name, String color, int row, int col, String face) {
		super(name);
		this.value = 400;
		this.color = color;
		this.rowLocation = row;
		this.colLocation = col;
		this.img = color+"_"+name+"_"+face+"_r0.png";
	}
	
//Constructor for arcade mode where the Knight's rank matters.	
	public Knight(String name, String color, int row, int col, int rank, String face) {
		super(name);
		this.rank = rank;
		this.value = determineValue(this.rank);
		this.color = color;
		this.rowLocation = row;
		this.colLocation = col;
		
		this.img = color+"_"+name+"_"+face+"_r"+rank+".png";
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
	
	public void setRank(int rank) {
		this.rank = rank;
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
		this.colLocation = col;
		this.rowLocation = row;
	}
	
	public int determineValue(int rank) {
		switch(rank) {
		case 0:
			this.value = 400;
			break;
		case 1:
			this.value = (int)(this.value * 1.20);
			break;
		case 2:
			this.value = (int)(this.value * 1.40);
			break;
		case 3:
			this.value = (int)(this.value * 1.60);
			break;
		default:
			this.value = 400;
		}
		
		return this.value;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Color: "+ this.color+ " Rank: "+ this.rank+" Value: "+this.value
				+" Image: "+this.img;
	}
}
