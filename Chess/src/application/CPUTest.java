package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CPUTest {

	@Test
	void testSetPlayer() {
		//Arrange
		CPU sut = new CPU();
		
		//Act 
		sut.setPlayer(new Player());
		
		//Assert
		assertEquals(0, sut.getCpuPlayer().getPoints());
		assertEquals("Noname", sut.getCpuPlayer().getName());
	
		
	}

	@Test
	void testFindAvailablePieces() {
		//Arrange
		CPU sut = new CPU();
		Game test = new Game(1,"white","name1", 2, "black", "name2", false);
		
		sut.setPlayer(test.getPlayer2());
		
		//Act 
		sut.findAvailablePieces(test.getBoard());
		
		//Assert
		assertEquals("rook",sut.getAvailablePieces().get(0).getName());
	}

	@Test
	void testDetermineMove() {
		//Arrange
		CPU sut = new CPU();
		Game test = new Game(1,"white","name1", 2, "black", "name2", false);
		
		sut.setPlayer(test.getPlayer2());
		sut.findAvailablePieces(test.getBoard());
		
		//Act 
		sut.determineMove(test.getBoard());
		
		//Assert
		assertEquals(true, sut.didMove());
		
	}
	
	@Test
	void testDetermineIfCanTakePiece() {
		//Arrange
		CPU sut = new CPU();
		Game test = new Game(1,"black","name1", 2, "white", "name2", false);
		Pawn testPiece;
		
		sut.setPlayer(test.getPlayer2());
		
		//Act
		testPiece = (Pawn)test.getBoard().getPieceGrid()[6][0];
		test.getBoard().getPieceGrid()[2][0] = testPiece;
		sut.findAvailablePieces(test.getBoard());
		
		//Assert
		assertEquals(true, sut.determineIfCanTakePiece(test.getBoard()));
	}

}
