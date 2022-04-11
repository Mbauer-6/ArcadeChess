package application;

import java.util.ArrayList;
import java.util.Random;

public class CPU {

	private Player cpuPlayer;
	private ArrayList<Piece> computerPiecesArray;
	private Piece selectedPiece;
	private Random randomNumber;
	private boolean moved;
	private int nextRow;
	private int nextCol;
	private int currentRow;
	private int currentCol;

	public CPU() {
		computerPiecesArray = new ArrayList<Piece>();
		randomNumber = new Random();
	}

	public void setPlayer(Player cpuPlayer) {
		this.cpuPlayer = cpuPlayer;
	}

	// error where moves opponents piece needs fixed
	public void findAvailablePieces(Board gameBoard) { // finds active pieces belonging to cpu then stores them into
														// array list
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getPieceGrid()[i][j] != null && gameBoard.getPieceGrid()[i][j].getColor().equals(cpuPlayer.getColor())) {
					
						computerPiecesArray.add(gameBoard.getPieceGrid()[i][j]);
						
						
				}
			}
		}

	}

	public void determineMove(Board gameBoard) {

		if (determineIfCanTakePiece(gameBoard)) {
			gameBoard.move(selectedPiece.getRow(), selectedPiece.getColumn(), nextRow, nextCol);
			System.out.println("CPU moved " + selectedPiece.getName() + " to " + nextRow + " " + nextCol + " from "
					+ currentRow + " " + currentCol);

		} else {
			int rand = randomNumber.nextInt(computerPiecesArray.size());
			moved = false;
			selectedPiece = computerPiecesArray.get(rand);
			do {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (gameBoard.isLegalMove(selectedPiece.getRow(), selectedPiece.getColumn(), i, j)
								&& selectedPiece.getColor().equals(getCpuPiece().getColor())) {
							currentRow = selectedPiece.getRow();
							currentCol = selectedPiece.getColumn();
							nextRow = i;
							nextCol = j;
							gameBoard.move(selectedPiece.getRow(), selectedPiece.getColumn(), i, j);
							System.out.println("CPU moved " + selectedPiece.getName() + " to " + nextRow + " " + nextCol
									+ " from " + currentRow + " " + currentCol);
							moved = true;
							break;
						}
					}

					if (moved)
						break;
				}
				if (computerPiecesArray.size() != 1) {
					computerPiecesArray.remove(rand);
				}

				rand = randomNumber.nextInt(computerPiecesArray.size());
				selectedPiece = computerPiecesArray.get(rand);
			} while (!moved);
			computerPiecesArray.clear();
		}
	}

	public boolean determineIfCanTakePiece(Board gameBoard) {

		for (int i = 0; i < computerPiecesArray.size(); i++) {

			selectedPiece = computerPiecesArray.get(i);
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					if (gameBoard.isLegalMove(selectedPiece.getRow(), selectedPiece.getColumn(), j, k)
							&& gameBoard.getPieceGrid()[j][k] != null && selectedPiece.getColor().equals(cpuPlayer.getColor())) {
						nextRow = j;
						nextCol = k;
						computerPiecesArray.clear();
						return true;
					}
				}
			}
		}
		return false;
	}

	public Piece getCpuPiece() {
		return selectedPiece;
	}

	public int getNextRow() {
		return nextRow;
	}

	public int getNextCol() {
		return nextCol;
	}

	public Player getCpuPlayer() {
		return cpuPlayer;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	
	public int getCurrentCol() {
		return currentCol;
	}

	public ArrayList<Piece> getAvailablePieces() {
		return computerPiecesArray;
	}
	public boolean didMove() {
		return moved;
	}
}
