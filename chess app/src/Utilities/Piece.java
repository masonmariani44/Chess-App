package Utilities;

import java.io.FileNotFoundException;

/**
 * This class represents pieces in a game of chess. 
 * 
 * @author West Laos
 */
public class Piece {
	/**
	 * This class stores all the names of the pieces in chess.
	 * 
	 * @author West Laos
	 */
	public enum PIECE_TYPE {
		KING("king"),
		QUEEN("queen"),
		BISHOP("bishop"),
		ROOK("rook"),
		KNIGHT("knight"),
		PAWN("pawn");
		
		private String description;
		
		private PIECE_TYPE(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
	}
	
	private PIECE_TYPE type; 	// Stores type of the piece
	private int player; 		// Stores which player the piece belongs to. 
	private int row;		// Stores the piece's x-location on the board 
	private int col;		// Stores the piece's y-location on the board
	private boolean moved;
	
	public Piece(PIECE_TYPE type, int player, int row, int col) {
		this.type = type;
		this.player = player;
		this.row = row;
		this.col = col;
		this.moved = false;
	}
	
	/**
	 * Returns what type of piece the instance its called on is.
	 * 
	 * @return An ENUM representing the type of piece this instance of piece is.
	 */
	public PIECE_TYPE getType() {
		return this.type;
	}
	
	/**
	 * Return which player the piece belongs to.
	 * 
	 * @return An int representing which player a piece belongs to. 
	 */
	public int getPlayer() {
		return this.player;
	}
	
	/**
	 * Return the value of this piece. 
	 * 
	 * @return An int representing the value of this piece.  
	 */
	public int getValue() {
		if (this.type == PIECE_TYPE.KING) {
			return 10;
		} else if (this.type == PIECE_TYPE.QUEEN) {
			return 9;
		} else if (this.type == PIECE_TYPE.BISHOP) {
			return 3;
		} else if (this.type == PIECE_TYPE.ROOK) {
			return 5;
		} else if (this.type == PIECE_TYPE.KNIGHT) {
			return 3;
		}  else {
			return 1;
		}
	}
	
	public void setMoved(boolean status) {
		this.moved = status;
	}
	public boolean hasMoved() {
		return this.moved;
	}
	
	/**
	 * change the x-location of this piece
	 * 
	 * @param An int representing the new x-location of this piece
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * change the y-location of this piece
	 * 
	 * @param An int representing the new y-location of this piece
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * Return the x-location of this piece 
	 * 
	 * @return An int representing the x-location of this piece
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Return the y-location of this piece
	 * 
	 * @return An int representing the y-location of this piece
	 */
	public int getCol() {
		return this.col;
	}
}
