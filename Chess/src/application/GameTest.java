package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//Tests Game class, and implicitly tests Player and Board class

class GameTest {

	@Test
	void test() {
		
		//Basic classic game
		Game gameTest = new Game(0,"white","bob", 1, "black", "computer", false);
		
		assertEquals(false, gameTest.check("white"));
		assertEquals(false, gameTest.checkmate(0,0,"white"));
		
		
		//TODO: Tests not running while JavaFX implemented in Game. 
		//Works when commented out

			}

}
