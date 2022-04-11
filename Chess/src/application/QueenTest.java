package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueenTest {

	@Test
	void test() {
		
		Queen q = new Queen();
		Queen q2 = new Queen("queen", "black", 4 , 9);
		q.setRow(4);
		q.setColumn(9);
		q.setColor("white");
		q2.move(5, 5);
		
		assertEquals(4, q.getRow());
		assertEquals(9, q.getColumn());
		assertEquals("white", q.getColor());
		assertEquals("queen", q2.getName());
		assertEquals("black_queen.png", q2.getImg());
		assertEquals(500, q2.getValue());
		assertEquals(5, q2.getRank());
		assertEquals(5, q2.getRow());
		assertEquals(5, q2.getColumn());
		assertEquals("Piece Info: Name: queen Color: black Rank: 5 Value: 500 Image: black_queen.png", q2.toString());
	}

}
