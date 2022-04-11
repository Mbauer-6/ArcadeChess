package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RookTest {

	@Test
	void test() {
		Rook r = new Rook();
		Rook r2 = new Rook("rook", "white", 0, 0);
		Rook r3 = new Rook("rook", "black", 0, 7, 3);
		
		r.setRow(4);
		r.setColumn(9);
		r.setColor("white");
		r2.move(5, 5);
		
		assertEquals(4, r.getRow());
		assertEquals(9, r.getColumn());
		assertEquals("white", r.getColor());
		assertEquals("rook", r2.getName());
		assertEquals("white_rook_r0.png", r2.getImg());
		assertEquals(200, r2.getValue());
		assertEquals(0, r2.getRank());
		assertEquals(5, r2.getRow());
		assertEquals(5, r2.getColumn());
		assertEquals("Piece Info: Name: rook Color: black Rank: 3 Value: 320 Image: black_rook_r3.png", r3.toString());
		assertEquals(320, r3.getValue());
	}

}
