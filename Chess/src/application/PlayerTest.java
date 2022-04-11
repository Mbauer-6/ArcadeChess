package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void test() {
		Player d = new Player("Dean", 0 , "white", 1);
		d.addPoints(100);
		assertEquals(100, d.getPoints());
		d.deductPoints(50);
		assertEquals(50, d.getPoints());
		d.fillArrayClassic();
		Piece[] piece = d.getArray();
		assertEquals("pawn",piece[0].getName());
		assertEquals(16, piece.length);
		d.fillArrayArcade();
		Piece[] e = d.getArray();
		assertEquals("queen",e[15].getName());
		assertEquals("white", d.getColor());
		assertEquals("Dean", d.getName());
		
	}

}
