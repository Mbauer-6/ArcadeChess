package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Scanner;

public class SaverTest
{
	@Test
	public void FileExistsAfterSavingTest()
	{
		//setting up for testing
		Player player1Test = new Player("Frank", 0, "white", 1);
		Player player2Test = new Player("Bob", 0, "black", 2);
		
		player1Test.setPlayerType(1);
		player2Test.setPlayerType(1);
		
		player1Test.fillArrayClassic();
		player2Test.fillArrayClassic();
		
		Board boardTest = new Board(player1Test.getArray(), player2Test.getArray());
		
		Saver fileSaver = new Saver(boardTest, player1Test, player2Test, "Classic");
		fileSaver.save();
		
		//now to see if the file exists
		File newFile = new File(player1Test.getName() + player2Test.getName() + "Classic.txt");
			
		assertEquals(true, newFile.exists());
		newFile.delete();
	}
	
	@Test
	public void FileSavesProperlyTest()
	{
		//setting up for testing
		Player player1Test = new Player("Frank", 0, "white", 1);
		Player player2Test = new Player("Bob", 0, "black", 2);
		
		//Testing to see player type is saved
		player1Test.setPlayerType(1);
		player2Test.setPlayerType(1);
				
		player1Test.fillArrayClassic();
		player2Test.fillArrayClassic();
				
		Board boardTest = new Board(player1Test.getArray(), player2Test.getArray());
				
		//moving some pieces
		boardTest.move(6, 0, 4, 0); //moving player1 pawn forward
		boardTest.move(0, 1, 2, 2); //moving player2 knight
				
		Saver fileSaver = new Saver(boardTest, player1Test, player2Test, "Classic");
		fileSaver.save();
		
		String fileName = player1Test.getName() + player2Test.getName() + "Classic.txt";
		File fileToRead = new File(fileName);
		Scanner fileInput = null;
				
		try
		{
			fileInput = new Scanner(fileToRead);

			if (fileInput.hasNext())
			{
				assertEquals(fileSaver.getGameMode(), fileInput.nextLine());
				
				//player 1
				String player1Info = fileSaver.getPlayer1ToSave().getName() + "," + 
									 fileSaver.getPlayer1ToSave().getPoints() + "," + 
									 fileSaver.getPlayer1ToSave().getColor() + "," + 
									 fileSaver.getPlayer1ToSave().getPlayerType() + "," + 
									 fileSaver.getPlayer1ToSave().getWins() + "," + 
									 fileSaver.getPlayer1ToSave().getLoses();
				
				assertEquals(player1Info, fileInput.nextLine());
				
				//now for player 2
				String player2Info = fileSaver.getPlayer2ToSave().getName() + "," + 
						 			 fileSaver.getPlayer2ToSave().getPoints() + "," + 
						 			 fileSaver.getPlayer2ToSave().getColor() + "," + 
						 			 fileSaver.getPlayer2ToSave().getPlayerType() + "," + 
									 fileSaver.getPlayer2ToSave().getWins() + "," + 
									 fileSaver.getPlayer2ToSave().getLoses();
				
				assertEquals(player2Info, fileInput.nextLine());
				
				String boardInfo = "";
				
				//finally, the board
				for(int i = 0; i < 8; i++)
				{
					for(int j = 0; j < 8; j ++)
					{
						if(fileSaver.getBoardToSave().getPieceGrid()[i][j] != null) //piece functions throw error on null
						{
							boardInfo += fileSaver.getBoardToSave().getPieceGrid()[i][j].getName() + "," + 
										 fileSaver.getBoardToSave().getPieceGrid()[i][j].getColor() + "," + 
										 fileSaver.getBoardToSave().getPieceGrid()[i][j].getRow() + "," + 
										 fileSaver.getBoardToSave().getPieceGrid()[i][j].getColumn() + ",";
							
							if(fileSaver.getBoardToSave().getPieceGrid()[i][j].getName() == "knight")
							{
								if(fileSaver.getBoardToSave().getPieceGrid()[i][j].getImg().equals(fileSaver.getBoardToSave().getPieceGrid()[i][j].getColor()+"_"+
																								   fileSaver.getBoardToSave().getPieceGrid()[i][j].getName()+"_"+"left"+"_r"+
																								   fileSaver.getBoardToSave().getPieceGrid()[i][j].getRank()+".png"))	//left facing
								{
									boardInfo += "left,";
								}
								else if(fileSaver.getBoardToSave().getPieceGrid()[i][j].getImg().equals(fileSaver.getBoardToSave().getPieceGrid()[i][j].getColor()+"_"+
																										fileSaver.getBoardToSave().getPieceGrid()[i][j].getName()+"_"+"right"+"_r"+
																										fileSaver.getBoardToSave().getPieceGrid()[i][j].getRank()+".png"))	//right facing)
								{
									boardInfo += "right,";
								}
							}
							
							boardInfo += fileSaver.getBoardToSave().getPieceGrid()[i][j].getRank();
							
							if(i != 7 || j != 7) //this makes sure the last space doesn't put a comma at the end
							{
								boardInfo += ",";
							}
						}
						else //write this to file if space is empty
						{
							boardInfo += "emptySpace,noColor," + i + "," + j + "," + 0;
							
							if(i != 7 || j != 7)
							{
								boardInfo += ",";
							}
						}
					}
				}
				
				assertEquals(boardInfo, fileInput.nextLine());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			fileInput.close();
			fileToRead.delete();
		}
	}
}
