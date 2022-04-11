package application;

import java.util.ArrayList;

public class Board
{
	private final int MAX_HEIGHT_AND_WIDTH = 8;
	
	private Piece[][] pieceGrid = new Piece[8][8];
	private ArrayList<Piece> capturedPieces;
	
	public Board()
	{
		capturedPieces = new ArrayList<Piece>();
	}
	
	public Board(Piece[] firstPlayerArray, Piece[] secondPlayerArray)
	{
		fillBoard(firstPlayerArray, secondPlayerArray);
		capturedPieces = new ArrayList<Piece>();
	}
	
	public void fillBoard(Piece[] firstPlayerArray, Piece[] secondPlayerArray)
	{
		for(int i = 0; i < MAX_HEIGHT_AND_WIDTH; i++)
		{
			getPieceGrid()[6][i] = firstPlayerArray[i];		//Starting with pawns on 7th row from the top
			
			getPieceGrid()[1][i] = secondPlayerArray[i];		//Now the second player pawns on 2nd row
		}
		//Very bottom row
		getPieceGrid()[7][0] = firstPlayerArray[10];
		getPieceGrid()[7][7] = firstPlayerArray[11];
		getPieceGrid()[7][1] = firstPlayerArray[12];
		getPieceGrid()[7][6] = firstPlayerArray[13];
		getPieceGrid()[7][2] = firstPlayerArray[8];
		getPieceGrid()[7][5] = firstPlayerArray[9];
		getPieceGrid()[7][3] = firstPlayerArray[14];
		getPieceGrid()[7][4] = firstPlayerArray[15];
		
		//Very top row
		getPieceGrid()[0][0] = secondPlayerArray[10];
		getPieceGrid()[0][7] = secondPlayerArray[11];
		getPieceGrid()[0][1] = secondPlayerArray[12];
		getPieceGrid()[0][6] = secondPlayerArray[13];
		getPieceGrid()[0][2] = secondPlayerArray[8];
		getPieceGrid()[0][5] = secondPlayerArray[9];
		getPieceGrid()[0][3] = secondPlayerArray[14];
		getPieceGrid()[0][4] = secondPlayerArray[15];
	}
	
	public void setPieceGrid(Piece[][] pieceGrid)
	{
		this.pieceGrid = pieceGrid;
	}
	
	public Piece[][] getPieceGrid()
	{
		return this.pieceGrid;
	}
	
	public void setCapturedPieces(ArrayList<Piece> capturedPieces)
	{
		this.capturedPieces = capturedPieces;
	}
	
	public ArrayList<Piece> getCapturedPieces()
	{
		return this.capturedPieces;
	}
	
	public void addToCapturedPieces(Piece capturedPiece)
	{
		this.capturedPieces.add(capturedPiece);
	}
	
	public Piece popCapturedPieces()
	{
		Piece mostRecentCapturedPiece = this.capturedPieces.get(this.capturedPieces.size() - 1); //most recent piece is the last index
		
		this.capturedPieces.remove(this.capturedPieces.size() - 1);
		
		return mostRecentCapturedPiece;
	}
	
	public boolean isMatchingColor(String color, int row, int col)
	{
		Piece pieceToTest = getPieceGrid()[row][col];
	
		if(pieceToTest.getColor().equals(color))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void move(int startRow, int startCol, int endRow, int endCol)
	{
		if(isLegalMove(startRow, startCol, endRow, endCol))
		{
			if(this.pieceGrid[endRow][endCol] != null)
			{
				addToCapturedPieces(this.pieceGrid[endRow][endCol]);
			}
			this.pieceGrid[startRow][startCol].move(endRow, endCol);
			this.pieceGrid[endRow][endCol] = this.pieceGrid[startRow][startCol];	//"move" piece to new space
			this.pieceGrid[startRow][startCol] = null;								//make old space empty
		
		}
	}
	
	public boolean isLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		if(startRow == endRow && startCol == endCol)
		{
			return false;
		}
		
		Piece pieceToTest = getPieceGrid()[startRow][startCol];
		
		if(pieceToTest == null)
		{
			return false;
		}
		
		String pieceName = pieceToTest.getName();
		
		if(pieceName.equals("bishop"))
		{
			return bishopLegalMove(startRow, startCol, endRow, endCol);
		}
		else if(pieceName.equals("king"))
		{
			return kingLegalMove(startRow, startCol, endRow, endCol);
		}
		else if(pieceName.equals("knight"))
		{
			return knightLegalMove(startRow, startCol, endRow, endCol);
		}
		else if(pieceName.equals("pawn"))
		{
			return pawnLegalMove(startRow, startCol, endRow, endCol);
		}
		else if(pieceName.equals("queen"))
		{
			return queenLegalMove(startRow, startCol, endRow, endCol);
		}
		else if(pieceName.equals("rook"))
		{
			return rookLegalMove(startRow, startCol, endRow, endCol);
		}
		else
		{
			return false;
		}
	}
	
	/********** helper functions for isLegalMove **********/
	private boolean bishopLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		boolean pieceInTheWay = false;
		
		if(startRow == endRow || startCol == endCol)
		{
			return false;
		}
		
		else if(Math.abs(endRow - startRow) == Math.abs(endCol - startCol))
		{
			if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if the ending piece is an opposite color and there is no piece in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
			else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return false; //disallow a move if the colors are the same
			}
			else
			{
				for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if there are no pieces in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean kingLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		if((Math.abs(endRow - startRow) == 1 || Math.abs(endCol - startCol) == 1) && (Math.abs(endRow - startRow) + Math.abs(endCol - startCol) == 1 || Math.abs(endRow - startRow) + Math.abs(endCol - startCol) == 2))
		{
			if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return true; //allow a move if the ending piece is an opposite color
			}
			else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return false; //disallow a move if the colors are the same
			}
			else
			{
				return true; //allow a move if ending cell is null
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean knightLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		if((Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 2) || (Math.abs(endCol - startCol) == 1 && Math.abs(endRow - startRow) == 2))
		{
			if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return true; //allow a move if the ending piece is an opposite color
			}
			else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return false; //disallow a move if the colors are the same
			}
			else
			{
				return true; //allow a move if ending cell is null
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean pawnLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		//first move for each pawn can be two spaces, subsequent moves are one space; can capture diagonally; can only move forward
		
		if(((Pawn)getPieceGrid()[startRow][startCol]).getOriginalRow() == 1)
		{
			if(startRow == 1) //if the pawn is still in its original row
			{
				//then allow a move forward two spaces
				if(endCol == startCol)
				{
					if((Math.abs(endRow - startRow) == 2 || Math.abs(endRow - startRow) == 1) && endRow > startRow) //if the ending row is 1 or 2 away from the original
					{
						if(Math.abs(endRow - startRow) == 1 && getPieceGrid()[startRow + 1][startCol] == null) //if the space in front of the pawn is empty
						{
							return true; //then allow the pawn to move forward 1 or 2 spaces
						}
						else if(Math.abs(endRow - startRow) == 2 && getPieceGrid()[startRow + 2][startCol] == null && getPieceGrid()[startRow + 1][startCol] == null) //if the space two ahead and one before is empty
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else if(endCol == startCol && Math.abs(endRow - startRow) == 1 && endRow > startRow) //if the columns between the starting and ending position are the same
				{
					if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //cannot capture in front of the pawn
					}
					else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //disallow a move if the colors are the same
					}
					else
					{
						return true; //allow a move if ending cell is null
					}
				}
				else if(Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1 && endRow > startRow) //if the ending position is a diagonal capture
				{
					if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return true; //can capture diagonally
					}
					else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //disallow a move if the colors are the same
					}
					else
					{
						return false; //disallow a move if ending cell diagonally is null
					}
				}
				else
				{
					return false;
				}
			}
			
			else if(endCol == startCol && Math.abs(endRow - startRow) == 1 && endRow > startRow) //if the columns between the starting and ending position are the same
			{
				if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //cannot capture in front of the pawn
				}
				else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //disallow a move if the colors are the same
				}
				else
				{
					return true; //allow a move if ending cell is null
				}
			}
			else if(Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1 && endRow > startRow) //if the ending position is a diagonal capture
			{
				if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return true; //can capture diagonally
				}
				else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //disallow a move if the colors are the same
				}
				else
				{
					return false; //disallow a move if ending cell diagonally is null
				}
			}
			else
			{
				return false;
			}
		}
		else if(((Pawn)getPieceGrid()[startRow][startCol]).getOriginalRow() == 6)
		{
			if(startRow == 6) //if the pawn is still in its original row
			{
				//then allow a move forward two spaces
				if(endCol == startCol)
				{
					if((Math.abs(endRow - startRow) == 2 || Math.abs(endRow - startRow) == 1) && endRow < startRow) //if the ending row is 1 or 2 away from the original
					{
						if(Math.abs(endRow - startRow) == 1 && getPieceGrid()[startRow - 1][startCol] == null) //if the space in front of the pawn is empty
						{
							return true; //then allow the pawn to move forward 1 or 2 spaces
						}
						else if(Math.abs(endRow - startRow) == 2 && getPieceGrid()[startRow - 2][startCol] == null && getPieceGrid()[startRow - 1][startCol] == null) //IF the space two ahead and one before is empty
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else if(endCol == startCol && Math.abs(endRow - startRow) == 1 && endRow < startRow) //if the columns between the starting and ending position are the same
				{
					if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //cannot capture in front of the pawn
					}
					else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //disallow a move if the colors are the same
					}
					else
					{
						return true; //allow a move if ending cell is null
					}
				}
				else if(Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1 && endRow < startRow) //if the ending position is a diagonal capture
				{
					if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return true; //can capture diagonally
					}
					else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
					{
						return false; //disallow a move if the colors are the same
					}
					else
					{
						return false; //disallow a move if ending cell diagonally is null
					}
				}
				else
				{
					return false;
				}
			}
			
			else if(endCol == startCol && Math.abs(endRow - startRow) == 1 && endRow < startRow) //if the columns between the starting and ending position are the same
			{
				if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //cannot capture in front of the pawn
				}
				else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //disallow a move if the colors are the same
				}
				else
				{
					return true; //allow a move if ending cell is null
				}
			}
			else if(Math.abs(endRow - startRow) == 1 && Math.abs(endCol - startCol) == 1 && endRow < startRow) //if the ending position is a diagonal capture
			{
				if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return true; //can capture diagonally
				}
				else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
				{
					return false; //disallow a move if the colors are the same
				}
				else
				{
					return false; //disallow a move if ending cell diagonally is null
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean queenLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		boolean pieceInTheWay = false;
		
		if((Math.abs(endRow - startRow) == Math.abs(endCol - startCol)) || ((endCol == startCol) || (endRow == startRow)))
		{
			if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				for(int i = 1; i < Math.abs(endCol - startCol); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if the ending piece is an opposite color and no piece is in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
			else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return false; //disallow a move if the colors are the same
			}
			else
			{
				for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				for(int i = 1; i < Math.abs(endCol - startCol); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol > startCol) //diagonally down and to the right
					{
						if(getPieceGrid()[startRow + i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow > startRow && endCol < startCol) //diagonally down and to the left
					{
						if(getPieceGrid()[startRow + i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol > startCol) //diagonally up and to the right
					{
						if(getPieceGrid()[startRow - i][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol < startCol) //diagonally up and to the left
					{
						if(getPieceGrid()[startRow - i][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if the ending cell is null and no piece is in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean rookLegalMove(int startRow, int startCol, int endRow, int endCol)
	{
		boolean pieceInTheWay = false;
		
		if((endCol == startCol) || (endRow == startRow))
		{
			if(getPieceGrid()[endRow][endCol] != null && !getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				for(int i = 1; i < Math.abs(endCol - startCol); i++) // if there are any pieces between starting and ending positions
				{
					if(endRow > startRow && endCol == startCol) //down
					{
						if(getPieceGrid()[startRow + i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol < startCol) //left
					{
						if(getPieceGrid()[startRow][startCol - i] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow < startRow && endCol == startCol) //up
					{
						if(getPieceGrid()[startRow - i][startCol] != null)
						{
							pieceInTheWay = true;
						}
					}
					else if(endRow == startRow && endCol > startCol) //right
					{
						if(getPieceGrid()[startRow][startCol + i] != null)
						{
							pieceInTheWay = true;
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if the ending piece is an opposite color and no piece is in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
			else if(getPieceGrid()[endRow][endCol] != null && getPieceGrid()[endRow][endCol].getColor().equals(getPieceGrid()[startRow][startCol].getColor()))
			{
				return false; //disallow a move if the colors are the same
			}
			else
			{
				if(endCol == startCol)
				{
					for(int i = 1; i < Math.abs(endRow - startRow); i++) // if there are any pieces between starting and ending positions
					{
						if(endRow > startRow && endCol == startCol) //down
						{
							if(getPieceGrid()[startRow + i][startCol] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow == startRow && endCol < startCol) //left
						{
							if(getPieceGrid()[startRow][startCol - i] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow < startRow && endCol == startCol) //up
						{
							if(getPieceGrid()[startRow - i][startCol] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow == startRow && endCol > startCol) //right
						{
							if(getPieceGrid()[startRow][startCol + i] != null)
							{
								pieceInTheWay = true;
							}
						}
					}
				}
				else if(endRow == startRow)
				{
					for(int i = 1; i < Math.abs(endCol - startCol); i++) // if there are any pieces between starting and ending positions
					{
						if(endRow > startRow && endCol == startCol) //down
						{
							if(getPieceGrid()[startRow + i][startCol] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow == startRow && endCol < startCol) //left
						{
							if(getPieceGrid()[startRow][startCol - i] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow < startRow && endCol == startCol) //up
						{
							if(getPieceGrid()[startRow - i][startCol] != null)
							{
								pieceInTheWay = true;
							}
						}
						else if(endRow == startRow && endCol > startCol) //right
						{
							if(getPieceGrid()[startRow][startCol + i] != null)
							{
								pieceInTheWay = true;
							}
						}
					}
				}
				if(!pieceInTheWay)
				{
					return true; //allow a move if the ending cell is null and no piece is in the way
				}
				else
				{
					return false; //disallow a move if the above loop found a piece in the way
				}
			}
		}
		else
		{
			return false;
		}
	}
}
