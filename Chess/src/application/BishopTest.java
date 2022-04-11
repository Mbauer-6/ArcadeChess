package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BishopTest {

	@Test
	void test() {
		Bishop b = new Bishop();
		Bishop b2 = new Bishop("bishop", "black", 0,1);
		Bishop b3 = new Bishop("bishop", "white", 0, 6, 1);
		
		b.setRow(4);
		b.setColumn(9);
		b.setColor("white");
		b2.move(5, 5);
		
		assertEquals(4, b.getRow());
		assertEquals(9, b.getColumn());
		assertEquals("white", b.getColor());
		assertEquals("bishop", b2.getName());
		assertEquals("black_bishop_r0.png", b2.getImg());
		assertEquals(300, b2.getValue());
		assertEquals(0, b2.getRank());
		assertEquals(5, b2.getRow());
		assertEquals(5, b2.getColumn());
		assertEquals(360, b3.getValue());
		assertEquals("Piece Info: Name: bishop Color: white Rank: 1 Value: 360 Image: white_bishop_r1.png", b3.toString());
	}

}
