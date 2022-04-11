package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoardTest {
	
	@Test
	void boardFillTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		Board testBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		
		//First testing fillBoard() and constructor
		
		/* secondPlayer array */
		//Names
		assertEquals("rook", testBoard.getPieceGrid()[0][0].getName());
		assertEquals("knight", testBoard.getPieceGrid()[0][1].getName());
		assertEquals("bishop", testBoard.getPieceGrid()[0][2].getName());
		assertEquals("king", testBoard.getPieceGrid()[0][3].getName());
		assertEquals("queen", testBoard.getPieceGrid()[0][4].getName());
		assertEquals("bishop", testBoard.getPieceGrid()[0][5].getName());
		assertEquals("knight", testBoard.getPieceGrid()[0][6].getName());
		assertEquals("rook", testBoard.getPieceGrid()[0][7].getName());
		for(int i = 1; i < 2; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				assertEquals("pawn", testBoard.getPieceGrid()[i][j].getName());
			}
		}
		
		//Color
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				assertEquals("white", testBoard.getPieceGrid()[i][j].getColor());
			}
		}
		
		/* empty spaces */
		for(int i = 2; i < 6; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				assertEquals(null, testBoard.getPieceGrid()[i][j]);
			}
		}
		
		/* firstPlayer array */
		assertEquals("rook", testBoard.getPieceGrid()[7][0].getName());
		assertEquals("knight", testBoard.getPieceGrid()[7][1].getName());
		assertEquals("bishop", testBoard.getPieceGrid()[7][2].getName());
		assertEquals("king", testBoard.getPieceGrid()[7][3].getName());
		assertEquals("queen", testBoard.getPieceGrid()[7][4].getName());
		assertEquals("bishop", testBoard.getPieceGrid()[7][5].getName());
		assertEquals("knight", testBoard.getPieceGrid()[7][6].getName());
		assertEquals("rook", testBoard.getPieceGrid()[7][7].getName());
		for(int i = 6; i < 7; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				assertEquals("pawn", testBoard.getPieceGrid()[i][j].getName());
			}
		}
		
		//Color
		for(int i = 6; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				assertEquals("black", testBoard.getPieceGrid()[i][j].getColor());
			}
		}
	}
		
	@Test
	void bishopLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//now for moving pieces
		//Bishops
		Board bishopBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		assertEquals(false, bishopBoard.isLegalMove(0, 2, 2, 4)); //cannot jump over pawns in front of bishop
		assertEquals(false, bishopBoard.isLegalMove(7, 2, 5, 0)); //now for diagonally up and to the left
		bishopBoard.move(1, 2, 3, 2); //moving pawn out of the way
		assertEquals(false, bishopBoard.isLegalMove(0, 2, 0, 3)); //cannot move in straight lines
		bishopBoard.move(1, 3, 3, 3); //moving diagonal pawn out of the way
		assertEquals(true, bishopBoard.isLegalMove(0, 2, 2, 4)); //can move diagonally	
	}
	
	@Test
	void kingLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//King
		Board kingBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		kingBoard.move(1, 3, 3, 3); //moving pawn out of the way
		assertEquals(true, kingBoard.isLegalMove(0, 3, 1, 3)); //moving king one space down
		kingBoard.move(0, 3, 1, 3);
		assertEquals(true, kingBoard.isLegalMove(1, 3, 2, 4)); //legal move diagonally
		assertEquals(false, kingBoard.isLegalMove(1, 3, 2, 5)); //cannot move more than one space in any direction
	}
	
	@Test
	void knightLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//Knight
		Board knightBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		knightBoard.move(1, 1, 3, 1); //moving pawn out of the way
		assertEquals(true, knightBoard.isLegalMove(0, 1, 2, 2)); //legal move
		knightBoard.move(0, 1, 2, 2);
		assertEquals(false, knightBoard.isLegalMove(2, 2, 2, 3)); //cannot move in straight lines
	}
	
	@Test
	void pawnLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//Pawns
		Board pawnBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		
		pawnBoard.move(7, 1, 5, 2); //using a knight to test that a pawn can't jump over it for its first move
		pawnBoard.move(5, 2, 3, 3);
		pawnBoard.move(3, 3, 2, 1); //now the knight is right in front of a pawn
		
		assertEquals(false, pawnBoard.isLegalMove(1, 1, 3, 1)); //pawn cannot jump over knight
		
		assertEquals(true, pawnBoard.isLegalMove(1, 0, 3, 0)); //first move
		pawnBoard.move(1, 0, 3, 0);
		assertEquals(false, pawnBoard.isLegalMove(3, 0, 5, 0)); //second move
		assertEquals(true, pawnBoard.isLegalMove(3, 0, 4, 0)); //legal second move
		pawnBoard.move(3, 0, 4, 0);
		pawnBoard.move(4, 0, 5, 0);
		assertEquals(false, pawnBoard.isLegalMove(5, 0, 6, 0)); //cannot capture in front
		assertEquals(true, pawnBoard.isLegalMove(5, 0, 6, 1)); //can capture diagonally
		assertEquals(false, pawnBoard.isLegalMove(5, 0, 4, 0)); //cannot move backwards
	}
	
	@Test
	void queenLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//Queen
		Board queenBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		
		assertEquals(false, queenBoard.isLegalMove(0, 4, 4, 4)); //queen cannot jump over pawn in front
		assertEquals(false, queenBoard.isLegalMove(0, 4, 2, 6)); //queen cannot jump diagonally over
		
		queenBoard.move(1, 4, 3, 4); //moving pawn out of the way
		assertEquals(true, queenBoard.isLegalMove(0, 4, 2, 4)); //moving queen forward
		queenBoard.move(0, 4, 2, 4);
		assertEquals(true, queenBoard.isLegalMove(2, 4, 2, 0)); //moving queen all the way to the left
		queenBoard.move(2, 4, 2, 0);
		assertEquals(true, queenBoard.isLegalMove(2, 0, 5, 3)); //moving queen diagonally
		assertEquals(false, queenBoard.isLegalMove(2, 0, 3, 5)); //cannot move queen outside of diagonally/up,down,left,right
	}
	
	@Test
	void rookLegalMoveTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//Rook
		Board rookBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		
		assertEquals(false, rookBoard.isLegalMove(0, 0, 7, 0)); //cannot capture opponent's rook immediately
		
		rookBoard.move(1, 0, 3, 0); //moving pawn out of the way
		assertEquals(true, rookBoard.isLegalMove(0, 0, 2, 0)); //moving rook forward
		rookBoard.move(0, 0, 2, 0);
		assertEquals(true, rookBoard.isLegalMove(2, 0, 2, 7)); //moving rook all the way to the right
		rookBoard.move(2, 0, 2, 7);
		assertEquals(false, rookBoard.isLegalMove(2, 7, 4, 5)); //rook cannot move diagonally
		assertEquals(false, rookBoard.isLegalMove(2, 7, 7, 7)); //cannot capture opponent's rook if it is behind a piece
	}
	
	@Test
	void captureTest()
	{
		Player firstPlayerTest = new Player("firstPlayer", 0, "black", 1);
		firstPlayerTest.fillArrayClassic();
		Player secondPlayerTest = new Player("secondPlayer", 0, "white", 2);
		secondPlayerTest.fillArrayClassic();
		
		//Testing capturing pieces and adding to captured pieces array
		Board captureBoard = new Board(firstPlayerTest.getArray(), secondPlayerTest.getArray());
		captureBoard.move(1, 0, 3, 0);
		captureBoard.move(3, 0, 4, 0);
		captureBoard.move(4, 0, 5, 0);
		captureBoard.move(5, 0, 6, 1); //now captured enemy pawn diagonally
		assertEquals("pawn", captureBoard.getCapturedPieces().get(0).getName()); //capture piece name is pawn
		assertEquals("black", captureBoard.getCapturedPieces().get(0).getColor()); //captured piece color is black
	}
}
