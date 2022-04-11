package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KingTest {

	@Test
	void test() {
		King k = new King();
		King k2 = new King("king", "black", 4,9);
		k.setRow(4);
		k.setColumn(9);
		k.setColor("white");
		k2.move(5, 5);
		assertEquals(4, k.getRow());
		assertEquals(9, k.getColumn());
		assertEquals("white", k.getColor());
		assertEquals("king", k2.getName());
		assertEquals("black_king.png", k2.getImg());
		assertEquals(10000, k2.getValue());
		assertEquals(6, k2.getRank());
		assertEquals(5, k2.getRow());
		assertEquals(5, k2.getColumn());
		assertEquals("Piece Info: Name: king Color: black Rank: 6 Value: 10000 Image: black_king.png", k2.toString());
	}

}
