package application;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Loader 
{
	private Board boardToLoad;
	private Player player1ToLoad;
	private Player player2ToLoad;
	private String gameModeToLoad;
	
	public Loader(String fileName)
	{
		load(fileName);
	}
	
	
	public Board getBoardToLoad()
	{
		return this.boardToLoad;
	}
	
	public void setBoardToLoad(Board boardToLoad)
	{
		this.boardToLoad = boardToLoad;
	}
	
	
	public Player getPlayer1ToLoad()
	{
		return this.player1ToLoad;
	}
	
	public void setPlayer1ToLoad(Player player1ToLoad)
	{
		this.player1ToLoad = player1ToLoad;
	}
	
	
	public Player getPlayer2ToLoad()
	{
		return this.player2ToLoad;
	}
	
	public void setPlayer2ToLoad(Player player2ToLoad)
	{
		this.player2ToLoad = player2ToLoad;
	}
	
	public String getGameModeToLoad()
	{
		return this.gameModeToLoad;
	}
	
	public void setGameModeToLoad(String gameModeToLoad)
	{
		this.gameModeToLoad = gameModeToLoad;
	}
	
	
	public void load(String fileName)
	{
		File fileToLoad;
		Scanner fileReader = null;
		
		//Information to get
		String gameMode;
		
		//Player info
		String player1Name;
		int player1Points;
		String player1Color;
		int player1Type;
		int player1Wins;
		int player1Loses;
		String player2Name;
		int player2Points;
		String player2Color;
		int player2Type;
		int player2Wins;
		int player2Loses;
		
		//Piece info
		String pieceName;
		String pieceColor;
		int pieceRow;
		int pieceCol;
		int pieceRank;
		
		//For knights
		String pieceFacing = "left"; //default to left facing
		
		try
		{
			fileToLoad = new File(fileName);
			fileReader = new Scanner(fileToLoad);
			fileReader.useDelimiter(",|\n");				//ignore commas and new line characters
			
			gameMode = fileReader.next();
			setGameModeToLoad(gameMode);
			
			player1Name = fileReader.next();
			player1Points = fileReader.nextInt();
			player1Color = fileReader.next();
			player1Type = fileReader.nextInt();
			player1Wins = fileReader.nextInt();
			player1Loses = fileReader.nextInt();
				
			Player player1ToLoad = new Player(player1Name, player1Points, player1Color, 1);
			player1ToLoad.setPlayerType(player1Type);
			
			//adding wins
			for(int i = 0; i < player1Wins; i++)
			{
				player1ToLoad.addToWins();
			}
			
			//adding losses
			for(int i = 0; i < player1Loses; i++)
			{
				player1ToLoad.addToLoses();
			}
			
			if(gameMode.equals("classic"))
			{
				player1ToLoad.fillArrayClassic();
			}
			else
			{
				player1ToLoad.fillArrayArcade();
			}
			
			setPlayer1ToLoad(player1ToLoad);
			
			player2Name = fileReader.next();
			player2Points = fileReader.nextInt();
			player2Color = fileReader.next();
			player2Type = fileReader.nextInt();
			player2Wins = fileReader.nextInt();
			player2Loses = fileReader.nextInt();
			
			Player player2ToLoad = new Player(player2Name, player2Points, player2Color, 2);
			player2ToLoad.setPlayerType(player2Type);
	
			//now player 2's wins
			for(int i = 0; i < player2Wins; i++)
			{
				player2ToLoad.addToWins();
			}
			
			//losses
			for(int i = 0; i < player2Loses; i++)
			{
				player2ToLoad.addToLoses();
			}
			
			if(gameMode.equals("classic"))
			{
				player2ToLoad.fillArrayClassic();
			}
			else
			{
				player2ToLoad.fillArrayArcade();
			}
			
			setPlayer2ToLoad(player2ToLoad);
		
			Board boardToLoad = new Board();
			
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j < 8; j++)
				{
					pieceName = fileReader.next();
					pieceColor = fileReader.next();
					pieceRow = fileReader.nextInt();
					pieceCol = fileReader.nextInt();
					
					if(pieceName.equals("knight"))
					{
						pieceFacing = fileReader.next();
					}
					
					pieceRank = fileReader.nextInt();
					switch(pieceName)
					{
						case "bishop":
							Bishop newBishop = new Bishop(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
							boardToLoad.getPieceGrid()[i][j] = newBishop;
							break;
						case "king":
							King newKing = new King(pieceName, pieceColor, pieceRow, pieceCol);
							boardToLoad.getPieceGrid()[i][j] = newKing;
							break;
						case "knight":
							Knight newKnight = new Knight(pieceName, pieceColor, pieceRow, pieceCol, pieceRank, pieceFacing);
							boardToLoad.getPieceGrid()[i][j] = newKnight;
							break;
						case "pawn":
							if(pieceColor.equals(getPlayer1ToLoad().getColor()))
							{
								Pawn newPawn = new Pawn(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
								newPawn.setOriginalRow(6);
								boardToLoad.getPieceGrid()[i][j] = newPawn;
							}
							else if(pieceColor.equals(getPlayer2ToLoad().getColor()))
							{
								Pawn newPawn = new Pawn(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
								newPawn.setOriginalRow(1);
								boardToLoad.getPieceGrid()[i][j] = newPawn;
							}
							break;
						case "queen":
							Queen newQueen = new Queen(pieceName, pieceColor, pieceRow, pieceCol);
							boardToLoad.getPieceGrid()[i][j] = newQueen;
							break;
						case "rook":
							Rook newRook = new Rook(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
							boardToLoad.getPieceGrid()[i][j] = newRook;
							break;
						case "emptySpace":
							boardToLoad.getPieceGrid()[i][j] = null;
							break;
					}
				}
			}
			
			while(fileReader.hasNext())
			{
				pieceName = fileReader.next();
				pieceColor = fileReader.next();
				pieceRow = fileReader.nextInt();
				pieceCol = fileReader.nextInt();
				
				if(pieceName.equals("knight"))
				{
					pieceFacing = fileReader.next();
				}
				
				pieceRank = fileReader.nextInt();
				switch(pieceName)
				{
					case "bishop":
						Bishop newBishop = new Bishop(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
						boardToLoad.addToCapturedPieces(newBishop);
						break;
					case "king":
						King newKing = new King(pieceName, pieceColor, pieceRow, pieceCol);
						boardToLoad.addToCapturedPieces(newKing);
						break;
					case "knight":
						Knight newKnight = new Knight(pieceName, pieceColor, pieceRow, pieceCol, pieceRank, pieceFacing);
						boardToLoad.addToCapturedPieces(newKnight);
						break;
					case "pawn":
						if(pieceColor.equals(getPlayer1ToLoad().getColor()))
						{
							Pawn newPawn = new Pawn(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
							newPawn.setOriginalRow(6);
							boardToLoad.addToCapturedPieces(newPawn);
						}
						else if(pieceColor.equals(getPlayer2ToLoad().getColor()))
						{
							Pawn newPawn = new Pawn(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
							newPawn.setOriginalRow(1);
							boardToLoad.addToCapturedPieces(newPawn);
						}
						break;
					case "queen":
						Queen newQueen = new Queen(pieceName, pieceColor, pieceRow, pieceCol);
						boardToLoad.addToCapturedPieces(newQueen);
						break;
					case "rook":
						Rook newRook = new Rook(pieceName, pieceColor, pieceRow, pieceCol, pieceRank);
						boardToLoad.addToCapturedPieces(newRook);
						break;
				}
			}
			
			setBoardToLoad(boardToLoad);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			fileReader.close();
		}
	}
}
