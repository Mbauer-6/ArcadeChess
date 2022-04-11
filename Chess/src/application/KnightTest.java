package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KnightTest {

	@Test
	void test() {
		Knight k = new Knight();
		Knight k2 = new Knight("knight", "black", 0, 2, "left");
		Knight k3 = new Knight("knight", "white", 7,2, 3,"right");
		
		k.setRow(4);
		k.setColumn(9);
		k.setColor("white");
		k2.move(5, 5);
		
		assertEquals(4, k.getRow());
		assertEquals(9, k.getColumn());
		assertEquals("white", k.getColor());
		assertEquals("knight", k2.getName());
		assertEquals("black_knight_left_r0.png", k2.getImg());
		assertEquals(400, k2.getValue());
		assertEquals(0, k2.getRank());
		assertEquals(5, k2.getRow());
		assertEquals(5, k2.getColumn());
		assertEquals(640, k3.getValue());
		assertEquals("Piece Info: Name: knight Color: white Rank: 3 Value: 640 Image: white_knight_right_r3.png", k3.toString());
	}

}
