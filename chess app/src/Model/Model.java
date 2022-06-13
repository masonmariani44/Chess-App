package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Utilities.Piece;
import Utilities.Piece.PIECE_TYPE;

/**
 * This stores all the information for a game of chess
 * 
 * @author West Laos and Roger Liu
 */
public class Model {
	private Piece[][] board;			// this represents the game board
	private Piece[] player1Pieces;		// this represents player 1's pieces that still remain on the board
	private Piece[] player2Pieces;		// this represents player 2's pieces that still remain on the board
	private int player1PiecesNum;		// this represents the number of player 1's pieces that still remain on the board
	private int player2PiecesNum;		// this represents the number of player 2's pieces that still remain on the board
	private int playerWon;				// this represents the number of player who won the game
	
	public Model() {
		player1PiecesNum = 16;
		player2PiecesNum = 16;
		playerWon = 0;
		player1Pieces = new Piece[player1PiecesNum];
		player2Pieces = new Piece[player2PiecesNum];
		setUpBoard();
	}
	
	/**
	 * This function sets up the board for a new game.
	 * 	 
	 * It creates a new instance of board and places pieces in the 
	 * correct starting positions.
	 */
	private void setUpBoard() {
		this.board = new Piece[8][8];
		// sets up pawns
		for (int col = 0; col < 8; col++) {
			board[1][col] = new Piece(PIECE_TYPE.PAWN, 1, 1, col);	// P1
			board[6][col] = new Piece(PIECE_TYPE.PAWN, 2, 6, col);  // P2
		}
		// set up rooks
		board[0][0] = new Piece(PIECE_TYPE.ROOK, 	1, 0, 0);		// P1
		board[0][7] = new Piece(PIECE_TYPE.ROOK, 	1, 0, 7);		// P1
		board[7][0] = new Piece(PIECE_TYPE.ROOK, 	2, 7, 0);		// P2
		board[7][7] = new Piece(PIECE_TYPE.ROOK, 	2, 7, 7);		// P2
		// sets up knights
		board[0][1] = new Piece(PIECE_TYPE.KNIGHT, 	1, 0, 1);		// P1
		board[0][6] = new Piece(PIECE_TYPE.KNIGHT, 	1, 0, 6);		// P1
		board[7][1] = new Piece(PIECE_TYPE.KNIGHT, 	2, 7, 1);		// P2
		board[7][6] = new Piece(PIECE_TYPE.KNIGHT, 	2, 7, 6);		// P2
		// sets up bishops
		board[0][2] = new Piece(PIECE_TYPE.BISHOP, 	1, 0, 2);		// P1
		board[0][5] = new Piece(PIECE_TYPE.BISHOP, 	1, 0, 5);		// P1
		board[7][2] = new Piece(PIECE_TYPE.BISHOP, 	2, 7, 2);		// P2
		board[7][5] = new Piece(PIECE_TYPE.BISHOP, 	2, 7, 5);		// P2
		// sets up bishops
		board[0][3] = new Piece(PIECE_TYPE.QUEEN, 	1, 0, 3);		// P1
		board[0][4] = new Piece(PIECE_TYPE.KING, 	1, 0, 4);		// P1
		board[7][3] = new Piece(PIECE_TYPE.QUEEN, 	2, 7, 3);		// P2
		board[7][4] = new Piece(PIECE_TYPE.KING, 	2, 7, 4);		// P2
		updatePieceStatus();
	}
	
	/**
	 * This function returns the board currently being used.
	 * 
	 * @return An Piece[][] representing the positions of all the pieces in the game.  
	 */
	public Piece[][] getBoard() {
		return this.board;
	}
	
	/**
	 * This function returns the array that contains all the player 1's pieces on board
	 * 
	 * @return A Piece[] representing the array contains all the pieces remain in the game
	 */
	public Piece[] getPlayer1Pieces() {
		return player1Pieces;
	}
	
	/**
	 * This function returns the array that contains all the player 2's pieces on board
	 * 
	 * @return A Piece[] representing the array contains all the pieces remain in the game
	 */
	public Piece[] getPlayer2Pieces() {
		return player2Pieces;
	}
	
	/**
	 * This function updates the array that contains all the pieces of the given player remains on the board
	 */
	private void updatePieceStatus() {
		int piecesNum1 = 0; 
		int piecesNum2 = 0; 
		for (int i = 0; i < 8; i++) {							// loop through the board to find all the pieces
			for(int j = 0; j < 8; j++) {
				if (this.board[i][j] != null) {
					if (this.board[i][j].getPlayer() == 1) {
							player1Pieces[piecesNum1] = this.board[i][j];
							piecesNum1++;
					}else {
							player2Pieces[piecesNum2] = this.board[i][j];
							piecesNum2++;
					}
				}
			}
		}
			player1PiecesNum = piecesNum1;
			Piece[] temp = new Piece[piecesNum1];				// Used to resize the array for player 1's pieces
			for (int i = 0; i < piecesNum1; i++) {
				temp[i] = player1Pieces[i];
			}
			player1Pieces = temp;
			player2PiecesNum = piecesNum2;
			temp = new Piece[piecesNum2];						// Used to resize the array for player 2's pieces
			for (int i = 0; i < piecesNum2; i++) {
				temp[i] = player2Pieces[i];
			}
			player2Pieces = temp;
	}
	
	/**
	 * Move the piece to given location
	 * 
	 * @param piece: Given piece that is wanted to move
	 * @param row: Given row that move the piece to
	 * @param col: Given column that move the piece to
	 */
	public void makeMove(Piece piece, int row, int col) {
		boolean cond = false;
		if (board[row][col] != null) {						// determine if it need to update the pieces' status later
			cond = true;
		}
		this.board[row][col] = piece;
		this.board[piece.getRow()][piece.getCol()] = null;
		piece.setRow(row);
		piece.setCol(col);
		if (cond = true) {
			updatePieceStatus();
		}
	}
	
	/**
	 * Determine if the game is over by checking if both players' kings are on the board
	 * 
	 * @return A boolean representing if the game is over
	 */
	public boolean isGameOver() {
		boolean cond = true;
		for (int i = 0; i < this.player1PiecesNum; i ++) {
			if (player1Pieces[i].getValue() == 10) {
				cond = false;
			}
		}
		if (cond == true) {
			this.playerWon = 2;
			return cond;
		}
		cond = true;
		for (int i = 0; i < this.player2PiecesNum; i ++) {
			if (player2Pieces[i].getValue() == 10) {
				cond = false;
			}
		}
		if (cond == true) {
			this.playerWon = 1;
			return cond;
		}
		return cond;
	}
	
	/**
	 * This function return the player won the game.
	 * 
	 * @return An int representing the player won the game
	 */
	public int getWinner() {
		return this.playerWon;
	}
	
	/**
	 * This function save both players' pieces' information including name and location via writing into a text file
	 * 
	 * @param A string that represents the name of the saving file
	 */
	public void save(String fileName) {
		try {
		      File myObj = new File(fileName);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		try {
		      FileWriter myWriter = new FileWriter(fileName);
		      myWriter.write("Player1\n");
		      for (int i = 0; i < player1PiecesNum; i++) {
		    	  String piece = player1Pieces[i].getType().getDescription();
		    	  int row = player1Pieces[i].getRow();
		    	  int col = player1Pieces[i].getCol();
		    	  myWriter.write(piece+" "+Integer.toString(row)+" "+Integer.toString(col)+"\n");
		      }
		      myWriter.write("Player2\n");
		      for (int i = 0; i < player2PiecesNum; i++) {
		    	  String piece = player2Pieces[i].getType().getDescription();
		    	  int row = player2Pieces[i].getRow();
		    	  int col = player2Pieces[i].getCol();
		    	  myWriter.write(piece+" "+Integer.toString(row)+" "+Integer.toString(col)+"\n");
		      }
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	
	/**
	 * This function read the saved file and set up the board according to the file
	 * 
	 * @param A string that represents the name of the saving file
	 */
	public void load(String fileName) {
		this.board = new Piece[8][8];
		this.player1Pieces = new Piece[16];
		this.player2Pieces = new Piece[16];
		int player = 1;
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String line = myReader.nextLine();
		        if (line.equals("Player1")){
		        	player = 1;
		        }else if (line.equals("Player2")) {
		        	player = 2;
		        }else {
		        	String[] piece = line.split(" ", 3);
		        	int row = Integer.parseInt(piece[1]);
		        	int col = Integer.parseInt(piece[2]);
		        	if (piece[0].equals("king")) {
		        		board[row][col] = new Piece(PIECE_TYPE.KING, player, row, col);
		        	} else if (piece[0].equals("queen")) {
		        		board[row][col] = new Piece(PIECE_TYPE.QUEEN, player, row, col);
		        	} else if (piece[0].equals("bishop")) {
		        		board[row][col] = new Piece(PIECE_TYPE.BISHOP, player, row, col);
		        	} else if (piece[0].equals("rook")) {
		        		board[row][col] = new Piece(PIECE_TYPE.ROOK, player, row, col);
		        	} else if (piece[0].equals("knight")) {
		        		board[row][col] = new Piece(PIECE_TYPE.KNIGHT, player, row, col);
		        	} else {
		        		board[row][col] = new Piece(PIECE_TYPE.PAWN, player, row, col);
		        	}
		        }
		      }
		      myReader.close();
		      this.updatePieceStatus();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
