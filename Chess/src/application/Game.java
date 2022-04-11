package application;

public class Game {
	private Player player1;
	private Player player2;
	private Board board;
	private Board testBoard;
	private String gameMode = "";
	int kingsValidMoves;
	boolean isCheckmated = false;
	
	
	public Game(int playerType1, String player1Color, String name1, int playerType2, String player2Color, String name2,
			boolean isArcade) {
		if (isArcade) {
			if (playerType1 == 1) { // player type signifies AI or player
				player1 = new Player(name1, 0, player1Color, 1);
				player1.fillArrayArcade();
				player1.setPlayerType(1);
			} else {
				player1 = new Player("Computer", 0, player1Color, 1); // will be a computer
				player1.fillArrayArcade();
				player1.setPlayerType(2);
			}
			if (playerType2 == 1) {
				player2 = new Player(name2, 0, player2Color, 2);
				player2.fillArrayArcade();
				player2.setPlayerType(1);
			} else {
				player2 = new Player("Computer", 0, player2Color, 2);// will be a computer
				player2.fillArrayArcade();
				player2.setPlayerType(2);
			}
		} else {// classic
			if (playerType1 == 1) {
				player1 = new Player(name1, 0, player1Color, 1);
				player1.fillArrayClassic();
				player1.setPlayerType(1);
			} else {
				player1 = new Player(name1, 0, player1Color, 1); // will be a computer
				player1.fillArrayClassic();
				player1.setPlayerType(2);
			}
			if (playerType2 == 1) {
				player2 = new Player(name2, 0, player2Color, 2);
				player2.fillArrayClassic();
				player2.setPlayerType(1);
			} else {
				player2 = new Player(name2, 0, player2Color, 2);// will be a computer
				player2.fillArrayClassic();
				player2.setPlayerType(2);
			}
		}
		board = new Board(player1.getArray(), player2.getArray());
		testBoard = new Board();
		updateTestBoard();

	}

	public Game(Player loadedPlayer1, Player loadedPlayer2, Board loadedBoard)	//use this constructor when loading a game
	{
		this.player1 = loadedPlayer1;
		this.player2 = loadedPlayer2;
		this.board = loadedBoard;
		testBoard = new Board();
		updateTestBoard();
	}

	public boolean check(String playerColor) {
		updateTestBoard();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (testBoard.getPieceGrid()[i][j] != null && testBoard.getPieceGrid()[i][j].getName().equals("king")
						&& !testBoard.getPieceGrid()[i][j].getColor().equals(playerColor)) {
					if (isInCheck(i, j, playerColor)) {
						return true;
					}

				}
			}
		}
		return false;
	}
	public boolean check(String playerColor, boolean checkCheckMate) { //when you want to also check if checkmate
		updateTestBoard();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (testBoard.getPieceGrid()[i][j] != null && testBoard.getPieceGrid()[i][j].getName().equals("king")
						&& !testBoard.getPieceGrid()[i][j].getColor().equals(playerColor)) {
					if (isInCheck(i, j, playerColor)) {
						checkmate(i, j, playerColor);
						return true;
					}

				}
			}
		}
		return false;
	}

	public boolean checkmate(int i, int j, String playerColor)// made public for GameTest() class
	{
		
		updateTestBoard();
		
		Piece temp = null;		
		kingsValidMoves = 0;
		int tempi = i + 1;
		int tempj = j + 1;
		int tempNum = 0;

		// Figuring out the number of valid moves for the king
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (testBoard.isLegalMove(i, j, a, b)) {
					kingsValidMoves++;
				}
			}
		}
		/// up
		if (tempi < 8 && testBoard.isLegalMove(i,j,tempi,j))// needs to run for all possibilities around space
		{
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][j] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(tempi, j, playerColor)) {
				
				tempNum++;
			}

			testBoard.getPieceGrid()[i][j] = temp;// and
			testBoard.getPieceGrid()[tempi][j] = null;// these
		}
		
		updateTestBoard();
		// down
		tempi = i - 1;
		if (tempi >= 0 && testBoard.isLegalMove(i, j, tempi, j)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][j] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck( tempi, j, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[tempi][j] = null;
		}
		
		
		updateTestBoard();
		// left
		tempj = j - 1;
		if (tempj >= 0 && testBoard.isLegalMove(i,j, i, tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[i][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(i, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[i][tempj] = null;
		}
		
		
		updateTestBoard();
		// right
		tempj = j + 1;
		if (tempj < 8 && testBoard.isLegalMove(i,j,i,tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[i][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(i, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[i][tempj] = null;

		}
		
		
		updateTestBoard();
		// diagonal topright
		tempi = i + 1;
		tempj = j + 1;
		if (tempi < 8 && tempj < 8 && testBoard.isLegalMove(i,j,tempi,tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(tempi, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[tempi][tempj] = null;
		}
		
		
		updateTestBoard();
		// diagonal bottomleft
		tempi = i + 1;
		tempj = j - 1;
		if (tempi < 8 && tempj >= 0 && testBoard.isLegalMove(i,j,tempi,tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(tempi, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[tempi][tempj] = null;
		}
		
		
		updateTestBoard();
		// diagonal topleft
		tempi = i - 1;
		tempj = j - 1;
		if (tempi >= 0 && tempj >= 0 && testBoard.isLegalMove(i, j, tempi, tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(tempi, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[tempi][tempj] = null;
		}
		
		
		updateTestBoard();
		// diagonal bottomright
		tempi = i - 1;
		tempj = j + 1;
		if (tempi >= 0 && tempj < 8 && testBoard.isLegalMove(i,j,tempi,tempj)) {
			temp = null;
			temp = testBoard.getPieceGrid()[i][j];
			testBoard.getPieceGrid()[tempi][tempj] = temp;
			testBoard.getPieceGrid()[i][j] = null;
			if (isInCheck(tempi, tempj, playerColor)) {

				tempNum++;
			}
			testBoard.getPieceGrid()[i][j] = temp;
			testBoard.getPieceGrid()[tempi][tempj] = null;
		}
		
		
	
		if (tempNum == kingsValidMoves) {
			System.out.println("CHECKMATE!!");
			isCheckmated = true;
			return true;
		}else {
			System.out.println("Kings valid moves: "+kingsValidMoves + " TempNum: "+ tempNum);
		}
		return false;

	}

	public boolean getIsCheckmated()
	{
		return this.isCheckmated;
	}
	
	public void setIsCheckmated(boolean val)
	{
		this.isCheckmated = val;
	}
		
	private boolean isInCheck(int xKing, int yKing, String playerColor) {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) // loops through players pieces to see if they have a legal path to the opposing
										// king
			{
				if (testBoard.getPieceGrid()[i][j] != null && testBoard.getPieceGrid()[i][j].getColor().equals(playerColor)) {
					if (testBoard.isLegalMove(i, j, xKing, yKing)) {
						return true;
					}
				}
			}
		}
		return false;

	}
	
	public Piece[][] updatedGrid(Piece[][] temp) {
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				temp[a][b] = board.getPieceGrid()[a][b];
			}
		}
		return temp;
	}
	
	public void updateTestBoard() {
		for(int a = 0; a < 8; a++) {
			for(int b = 0; b < 8; b++){
				testBoard.getPieceGrid()[a][b] = board.getPieceGrid()[a][b];
			}
		}
	}

	public Board getBoard() {
		return this.board;
	}

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

	public void setGameMode(String gameModeToLoad) {

		this.gameMode = gameModeToLoad;
	}
	public String getGameMode() {

		return this.gameMode;
	}
	
}
