package application;

import java.util.ArrayList;
import java.util.Random;

public class Player{
	private String playerName;
	private int playerPoints;
	private String color;
	private final int NUM_CHESS_PIECES = 16;
	private Piece pieces[];
	private int playerChoice; //to signify player should be 1 for player 1 and 2 for player 2
	private int playerType;
	private int wins;
	private int loses;
	
	public Player() {
		playerName = "Noname";
		playerPoints = 0;
		color = "white";
		pieces =  new Piece[NUM_CHESS_PIECES];
		
		wins = 0;
		loses = 0;
	}
	public Player(String name, int points, String color, int playerChoice) {
		playerName = name;
		playerPoints = points;
		this.color = color;
		pieces =  new Piece[NUM_CHESS_PIECES];
		this.playerChoice = playerChoice;
		wins = 0;
		loses = 0;
	}
	public void setPoints(int points) {
		playerPoints = points;
	}
	public void setName(String name) {
		playerName = name;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void addPoints(int points) {
		playerPoints = playerPoints + points;
	}
	public void deductPoints(int points) {
		playerPoints = playerPoints - points;
	}
	public void addToWins() {
		this.wins++;
	}
	public int getWins() {
		return this.wins;
	}
	public void addToLoses() {
		this.loses++;
	}
	public int getLoses() {
		return loses;
	}
	public void fillArrayClassic() {
		if(playerChoice == 2) {
			for(int i = 0; i < 8; i++) {
			Pawn p = new Pawn("pawn",this.color , 1, i);
			pieces[i] = p;
			}
			pieces[8] = new Bishop("bishop",this.color , 0, 2);
			pieces[9] = new Bishop("bishop",this.color , 0, 5);
			pieces[10] = new Rook("rook", this.color, 0, 0);
			pieces[11] = new Rook("rook", this.color, 0, 7);
			pieces[12] = new Knight("knight", this.color,0,1, "left");
		    pieces[13] = new Knight("knight", this.color,0,6, "right");
			pieces[14] = new King("king", this.color, 0,3);
			pieces[15] = new Queen("queen", this.color, 0,4);
		}
		else {
			for(int i = 0; i < 8; i++) {
			Pawn p = new Pawn("pawn",this.color , 6, i);
			pieces[i] = p;
			}
			pieces[8] = new Bishop("bishop",this.color , 7, 2);
			pieces[9] = new Bishop("bishop",this.color , 7, 5);
			pieces[10] = new Rook("rook", this.color, 7, 0);
			pieces[11] = new Rook("rook", this.color, 7, 7);
			pieces[12] = new Knight("knight", this.color,7,1, "left");
		    pieces[13] = new Knight("knight", this.color,7,6, "right");
			pieces[14] = new King("king", this.color, 7, 3);
			pieces[15] = new Queen("queen", this.color, 7,4);
			
		}
	}
		
	public void fillArrayArcade() {
		if(playerChoice == 2) {
			
			for(int i = 0; i < 8; i++) {
				Random r2 = new Random();
		        int rank  =  r2.nextInt(4);
		        Pawn p = new Pawn("pawn",this.color , 1, i, rank);
		        pieces[i] = p;
			}
			Random r2 = new Random();
			int rank = r2.nextInt(4); //every other to re gen num
			pieces[8] = new Bishop("bishop",this.color , 0, 2,rank);
		    rank = r2.nextInt(4);
			pieces[9] = new Bishop("bishop",this.color , 0, 5,rank);
			rank = r2.nextInt(4);
			pieces[10] = new Rook("rook", this.color, 0, 0,rank);
			rank = r2.nextInt(4);
			pieces[11] = new Rook("rook", this.color, 0, 7,rank);
			rank = r2.nextInt(4);
			pieces[12] = new Knight("knight", this.color,0,1,rank, "left");
			rank = r2.nextInt(4);
		    pieces[13] = new Knight("knight", this.color,0,6,rank, "right");

			pieces[14] = new King("king", this.color, 0, 3);
			pieces[15] = new Queen("queen", this.color, 0,4);

		}
		else {
			for(int i = 0; i < 8; i++) {
				Random r2 = new Random();
				int rank  =  r2.nextInt(4);
				Pawn p = new Pawn("pawn",this.color , 6, i,rank);
				pieces[i] = p;
			}
			Random r2 = new Random();
			int rank  =  r2.nextInt(4);
			pieces[8] = new Bishop("bishop",this.color , 7, 2,rank);
		    rank  =  r2.nextInt(4);
			pieces[9] = new Bishop("bishop",this.color , 7, 5,rank);
			rank  =  r2.nextInt(4);
			pieces[10] = new Rook("rook", this.color, 7, 0);
			rank  =  r2.nextInt(4);
			pieces[11] = new Rook("rook", this.color, 7, 7,rank);
			rank  =  r2.nextInt(4);
			pieces[12] = new Knight("knight", this.color,7,1,rank, "left");
			rank  =  r2.nextInt(4);
		    pieces[13] = new Knight("knight", this.color,7,6,rank, "right");
			pieces[14] = new King("king", this.color, 7, 3);
			pieces[15] = new Queen("queen", this.color, 7,4);
			
		}
	}

	public int getPoints() {
		return playerPoints;
	}
	public String getName() {
		return playerName;
	}
	public String getColor() {
		return color;
	}
	public Piece[] getArray() {
		return this.pieces;
	}
	public void setPlayerType(int playerType) {
		this.playerType = playerType;
	}
	public int getPlayerType() {
		return playerType;
	}
	public void printArray() {
		for(int i = 0; i < NUM_CHESS_PIECES; i++)
		System.out.println(pieces[i].getColor());
	}
	
}
