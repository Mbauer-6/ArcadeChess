package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Saver
{
	private Board boardToSave;
	private Player player1ToSave;
	private Player player2ToSave;
	private String gameMode;
	
	public Saver(Board boardToSave, Player player1ToSave, Player player2ToSave, String gameMode)
	{
		setBoardToSave(boardToSave);
		setPlayer1ToSave(player1ToSave);
		setPlayer2ToSave(player2ToSave);
		setGameMode(gameMode);
	}
	
	
	public Board getBoardToSave()
	{
		return this.boardToSave;
	}
	
	public void setBoardToSave(Board boardToSave)
	{
		this.boardToSave = boardToSave;
	}
	
	
	public Player getPlayer1ToSave()
	{
		return this.player1ToSave;
	}
	
	public void setPlayer1ToSave(Player player1ToSave)
	{
		this.player1ToSave = player1ToSave;
	}
	
	
	public Player getPlayer2ToSave()
	{
		return this.player2ToSave;
	}
	
	public void setPlayer2ToSave(Player player2ToSave)
	{
		this.player2ToSave = player2ToSave;
	}
	
	
	public String getGameMode()
	{
		return this.gameMode;
	}
	
	public void setGameMode(String gameMode)
	{
		this.gameMode = gameMode;
	}
	
	
	public void save()
	{
		//save player1 and player2's name, points, color, pieces array, and their choice, alongside the board's pieces and their information
		
		//filename will be a concatenation of player 1's name, player 2's name, and the gamemode
		String fileName = getPlayer1ToSave().getName() + getPlayer2ToSave().getName() + getGameMode();
		try
		{
			File newGameSave = new File(fileName + ".txt"); //text file
			FileWriter fileWriter = new FileWriter(newGameSave, false); //overwrite
			String textToWriteToFile = "";
			
			//first, the gamemode
			textToWriteToFile += getGameMode() + "\n";
			
			//then, player1's information
			textToWriteToFile += getPlayer1ToSave().getName() + "," + 
								 getPlayer1ToSave().getPoints() + "," + 
								 getPlayer1ToSave().getColor() + "," +
								 getPlayer1ToSave().getPlayerType() + "," + 
								 getPlayer1ToSave().getWins() + "," + 
								 getPlayer1ToSave().getLoses() + "\n";
			
			//now for player2's information
			textToWriteToFile += getPlayer2ToSave().getName() + "," + 
					 			 getPlayer2ToSave().getPoints() + "," + 
					 			 getPlayer2ToSave().getColor() + "," +
					 			 getPlayer2ToSave().getPlayerType() + "," + 
					 			 getPlayer2ToSave().getWins() + "," + 
					 			 getPlayer2ToSave().getLoses() + "\n";
			
			//now for board's pieces
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j < 8; j ++)
				{
					if(getBoardToSave().getPieceGrid()[i][j] != null) //write this to file for each piece
					{
						textToWriteToFile += getBoardToSave().getPieceGrid()[i][j].getName() + "," + 
											 getBoardToSave().getPieceGrid()[i][j].getColor() + "," + 
											 getBoardToSave().getPieceGrid()[i][j].getRow() + "," + 
											 getBoardToSave().getPieceGrid()[i][j].getColumn() + ",";
						
						if(getBoardToSave().getPieceGrid()[i][j].getName() == "knight")			//to save the facing of knights
						{
							if(getBoardToSave().getPieceGrid()[i][j].getImg().equals(getBoardToSave().getPieceGrid()[i][j].getColor()+"_"+
																					 getBoardToSave().getPieceGrid()[i][j].getName()+"_"+"left"+"_r"+
																					 getBoardToSave().getPieceGrid()[i][j].getRank()+".png"))	//left facing
							{
								textToWriteToFile += "left,";
							}
							else if(getBoardToSave().getPieceGrid()[i][j].getImg().equals(getBoardToSave().getPieceGrid()[i][j].getColor()+"_"+
									 													  getBoardToSave().getPieceGrid()[i][j].getName()+"_"+"right"+"_r"+
									 													  getBoardToSave().getPieceGrid()[i][j].getRank()+".png"))	//right facing)
							{
								textToWriteToFile += "right,";
							}
						}
						
						textToWriteToFile += getBoardToSave().getPieceGrid()[i][j].getRank(); //then rank, as knight has facing before rank
						
						if(i != 7 || j != 7) //this makes sure the last space doesn't put a comma at the end
						{
							textToWriteToFile += ",";
						}
					}
					else //write this to file if space is empty
					{
						textToWriteToFile += "emptySpace,noColor," + i + "," + j + "," + 0;	//empty spaces will default to rank 0
						
						if(i != 7 || j != 7)
						{
							textToWriteToFile += ",";
						}
					}
				}
			}
			
			textToWriteToFile += "\n";
			
			for(int i = 0; i < boardToSave.getCapturedPieces().size(); i++)
			{
				if(getBoardToSave().getCapturedPieces().get(i) != null) //write this to file for each captured piece
				{
					textToWriteToFile += getBoardToSave().getCapturedPieces().get(i).getName() + "," + 
										 getBoardToSave().getCapturedPieces().get(i).getColor() + "," + 
										 getBoardToSave().getCapturedPieces().get(i).getRow() + "," + 
										 getBoardToSave().getCapturedPieces().get(i).getColumn() + ",";
					
					if(getBoardToSave().getCapturedPieces().get(i).getName() == "knight")			//to save the facing of knights
					{
						if(getBoardToSave().getCapturedPieces().get(i).getImg().equals(getBoardToSave().getCapturedPieces().get(i).getColor()+"_"+
																				 getBoardToSave().getCapturedPieces().get(i).getName()+"_"+"left"+"_r"+
																				 getBoardToSave().getCapturedPieces().get(i).getRank()+".png"))	//left facing
						{
							textToWriteToFile += "left,";
						}
						else if(getBoardToSave().getCapturedPieces().get(i).getImg().equals(getBoardToSave().getCapturedPieces().get(i).getColor()+"_"+
								 													  getBoardToSave().getCapturedPieces().get(i).getName()+"_"+"right"+"_r"+
								 													  getBoardToSave().getCapturedPieces().get(i).getRank()+".png"))	//right facing)
						{
							textToWriteToFile += "right,";
						}
					}
					
					textToWriteToFile += getBoardToSave().getCapturedPieces().get(i).getRank() + "\n"; //then rank, as knight has facing before rank
				}
			}
			
			fileWriter.write(textToWriteToFile);
			fileWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
