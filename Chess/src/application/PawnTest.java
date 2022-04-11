package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PawnTest {

	@Test
	void test() {
		Pawn p = new Pawn();
		Pawn p2 = new Pawn("pawn", "black", 1, 6);
		Pawn p3 = new Pawn("pawn", "white", 1 ,5 , 2);
		p.setRow(4);
		p.setColumn(9);
		p.setColor("white");
		p2.move(5, 5);
		
		assertEquals(4, p.getRow());
		assertEquals(9, p.getColumn());
		assertEquals("white", p.getColor());
		assertEquals("pawn", p2.getName());
		assertEquals("black_pawn_r0.png", p2.getImg());
		assertEquals(100, p2.getValue());
		assertEquals(0, p2.getRank());
		assertEquals(5, p2.getRow());
		assertEquals(5, p2.getColumn());
		assertEquals(140, p3.getValue());
		assertEquals("Piece Info: Name: pawn Color: white Rank: 2 Value: 140 Image: white_pawn_r2.png", p3.toString());
	}

}
