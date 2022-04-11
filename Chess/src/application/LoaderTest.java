package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

public class LoaderTest
{
	@Test
	public void playersLoadCorrectlyTest()
	{
		//First need to save a file to load
		Player player1LoadTest = new Player("Frank", 50, "white", 1);
		player1LoadTest.setPlayerType(1);
		player1LoadTest.addToWins();
		player1LoadTest.addToLoses();
		Player player2LoadTest = new Player("Bob", 75, "black", 2);
		player2LoadTest.setPlayerType(1);
		player2LoadTest.addToLoses();
					
		player1LoadTest.fillArrayClassic();
		player2LoadTest.fillArrayClassic();
					
		Board boardTest = new Board(player1LoadTest.getArray(), player2LoadTest.getArray());
					
		Saver fileSaver = new Saver(boardTest, player1LoadTest, player2LoadTest, "classic");
		fileSaver.save();
			
		Loader fileLoader = new Loader("FrankBobclassic.txt");	//now to load the file that was created
			
		Player loadedPlayer1 = fileLoader.getPlayer1ToLoad();
		Player loadedPlayer2 = fileLoader.getPlayer2ToLoad();
		
		//testing player 1's information
		assertEquals("Frank", loadedPlayer1.getName());
		assertEquals(50, loadedPlayer1.getPoints());
		assertEquals("white", loadedPlayer1.getColor());
		assertEquals(1, loadedPlayer1.getPlayerType());
		assertEquals(1, loadedPlayer1.getWins());
		assertEquals(1, loadedPlayer1.getLoses());
		
		//now player 2
		assertEquals("Bob", loadedPlayer2.getName());
		assertEquals(75, loadedPlayer2.getPoints());
		assertEquals("black", loadedPlayer2.getColor());
		assertEquals(1, loadedPlayer2.getPlayerType());
		assertEquals(0, loadedPlayer2.getWins());
		assertEquals(1, loadedPlayer2.getLoses());
		
		//File fileToDelete = new File("FrankBobclassic.txt");
		//fileToDelete.delete();
	}
	
	@Test
	public void boardLoadsCorrectlyTest()
	{
		// First need to save a file to load
		Player player1BoardTest = new Player("John", 0, "white", 1);
		Player player2BoardTest = new Player("Charlie", 0, "black", 2);

		player1BoardTest.fillArrayClassic();
		player2BoardTest.fillArrayClassic();

		Board boardTest = new Board(player1BoardTest.getArray(), player2BoardTest.getArray());

		// moving some pieces
		boardTest.move(6, 0, 4, 0); // moving player1 pawn forward
		boardTest.move(0, 1, 2, 2); // moving player2 knight

		Saver fileSaver = new Saver(boardTest, player1BoardTest, player2BoardTest, "classic");
		fileSaver.save();

		Loader fileLoader = new Loader("JohnCharlieclassic.txt");
		Board loadedBoard = fileLoader.getBoardToLoad(); // retrieve board

		// now to test if the moved pieces are in their correct spots
		assertEquals("pawn", loadedBoard.getPieceGrid()[4][0].getName());
		assertEquals("white", loadedBoard.getPieceGrid()[4][0].getColor());
		assertEquals(0, loadedBoard.getPieceGrid()[4][0].getRank());
		assertEquals("knight", loadedBoard.getPieceGrid()[2][2].getName());
		assertEquals("black", loadedBoard.getPieceGrid()[2][2].getColor());
		assertEquals(0, loadedBoard.getPieceGrid()[4][0].getRank());

		// now to test if the other pieces are there. if pawns are all there, then the
		// others are there as well
		for (int i = 0; i < 8; i++)
		{
			assertEquals("pawn", loadedBoard.getPieceGrid()[1][i].getName());
			assertEquals("black", loadedBoard.getPieceGrid()[1][i].getColor());
			assertEquals(0, loadedBoard.getPieceGrid()[1][i].getRank());

			if (i != 0) // first player's pawn was moved forward to spaces, leaving behind a blank space
			{
				assertEquals("pawn", loadedBoard.getPieceGrid()[6][i].getName());
				assertEquals("white", loadedBoard.getPieceGrid()[6][i].getColor());
				assertEquals(0, loadedBoard.getPieceGrid()[6][i].getRank());
			}
			else // blank space is at [6][0]
			{
				assertEquals(null, loadedBoard.getPieceGrid()[6][i]);
			}
		}
		
		File fileToDelete = new File("JohnCharlieclassic.txt");
		fileToDelete.delete();
	}
	
	@Test
	public void gameModeLoadsCorrectlyTest()
	{
		Player player1GameModeTest = new Player("Jim", 0, "white", 1);
		Player player2GameModeTest = new Player("Matt", 0, "black", 2);
		
		player1GameModeTest.fillArrayArcade();
		player2GameModeTest.fillArrayArcade();
		
		Board boardTest = new Board(player1GameModeTest.getArray(), player2GameModeTest.getArray());
		
		Saver fileSaver = new Saver(boardTest, player1GameModeTest, player2GameModeTest, "arcade");
		fileSaver.save();
		
		Loader fileLoader = new Loader("JimMattarcade.txt");
		
		assertEquals("arcade", fileLoader.getGameModeToLoad());
		
		File fileToDelete = new File("JimMattarcade.txt");
		fileToDelete.delete();
	}
	
	@Test
	public void capturedPiecesLoadCorrectlyTest()
	{
		//First need to save a file to load
		Player player1BoardTest = new Player("Jack", 0, "white", 1);
		Player player2BoardTest = new Player("Mark", 0, "black", 2);

		player1BoardTest.fillArrayClassic();
		player2BoardTest.fillArrayClassic();

		Board boardTest = new Board(player1BoardTest.getArray(), player2BoardTest.getArray());

		//moving some pieces
		boardTest.move(6, 0, 4, 0); //moving player1 pawn forward
		boardTest.move(4, 0, 3, 0); //moving player1 pawn forward another spot
		boardTest.move(3, 0, 2, 0);
		boardTest.move(2, 0, 1, 1); //capture opponent's pawn
		boardTest.move(1, 1, 0, 0); //capture opponent's rook

		Saver fileSaver = new Saver(boardTest, player1BoardTest, player2BoardTest, "classic");
		fileSaver.save();

		Loader fileLoader = new Loader("JackMarkclassic.txt");
		Board loadedBoard = fileLoader.getBoardToLoad(); //retrieve board
		
		assertEquals("pawn", loadedBoard.getCapturedPieces().get(0).getName());
		assertEquals("black", loadedBoard.getCapturedPieces().get(0).getColor());
	}
}
