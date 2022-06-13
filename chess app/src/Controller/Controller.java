package Controller;

import java.util.Random;

import Model.Model;
import Utilities.Piece;
import Utilities.Piece.PIECE_TYPE;

/**
 * This implements the game logic of chess. 
 * 
 * @author West Laos, Mason Mariani
 */
public class Controller {
	private Model 	model;		// used to hold all the information related to the game
	private int 	playerNum;	// used to track which player is which
	
	public Controller(Model model, int playerNum) {
		this.model =  model;
		this.playerNum = playerNum;
	}
	
	/**
	 * This function returns the board currently being used.
	 * 
	 * @return An Piece[][] representing the positions of all the pieces in the game.  
	 */
	public Piece[][] getBoard() {
		return this.model.getBoard();
		
	}
	
	/**
	 * 
	 * calls the model to make this move and update the board
	 * 
	 * @param selectedPiece - the piece to be moved
	 * @param newRow - the row to be moved to
	 * @param newCol - the column to be moved to 
	 * 
	 */
	public void makeMove(Piece selectedPiece, int newRow, int newCol) {
		selectedPiece.setMoved(true);
		model.makeMove(selectedPiece, newRow, newCol);
	}
	
	/**
	 * given a model, updates this controller's current model
	 * 
	 * @param model
	 */
	public void updateModel(Model model) {
		this.model = model;
	}
	
	/**
	 * returns this controller's model
	 * 
	 * @return - this controller's model
	 */
	public Model getModel() {
		return this.model;
	}
	
	/**
	 * called by view to make the cpu's randomly decided move
	 */
	public void cpuMakeMove() {
		//generate random number
		//get list of all cpu (black)(P2) pieces
		Piece[] cpu_pieces = model.getPlayer1Pieces();
		Random random = new Random();
		Integer[] used_index = new Integer[16];
		Integer index;
		int counter = 0;
		while (counter < 16) {
			index = random.nextInt(cpu_pieces.length);
			if (used_index[index] == null) {
				used_index[index] = index;
				
				//given this random index, see if this piece can move
				//attempt to move it as far as it can go or capture a piece if it can
				
				//check pawn
				if (cpu_pieces[index].getType() == PIECE_TYPE.PAWN) {
					if (cpuPawn(cpu_pieces[index])) {
						return;
					}
				}
				
				//check rook
				if (cpu_pieces[index].getType() == PIECE_TYPE.ROOK) {
					if (cpuRook(cpu_pieces[index])) {
						return;
					}
				}
				
				//check bishop
				if (cpu_pieces[index].getType() == PIECE_TYPE.BISHOP) {
					if (cpuBishop(cpu_pieces[index])) {
						return;
					}
				}
				
				//check queen
				if (cpu_pieces[index].getType() == PIECE_TYPE.QUEEN) {
					if (cpuRook(cpu_pieces[index]) || cpuBishop(cpu_pieces[index])) {
						return;
					}
				}
				
				//check king
				if (cpu_pieces[index].getType() == PIECE_TYPE.KING) {
					if (cpuKing(cpu_pieces[index])) {
						return;
					}
				}
				
				//check knight
				if (cpu_pieces[index].getType() == PIECE_TYPE.KNIGHT) {
					if (cpuKnight(cpu_pieces[index])) {
						return;
					}
				}	
				counter++;
			}
		}
	}
	
	/**
	 * 
	 * @param piece - the piece that the cpu is trying to move
	 * @return - true if the move is valid, false if not
	 */
	private boolean cpuKnight(Piece piece) {
		//down 2 right 1
		if (isValidMove(piece, piece.getRow()+2, piece.getCol()+1)) {
			makeMove(piece, piece.getRow()+2, piece.getCol()+1);
			return true;
		}
		//down 2 left 1
		if (isValidMove(piece, piece.getRow()+2, piece.getCol()-1)) {
			makeMove(piece, piece.getRow()+2, piece.getCol()-1);
			return true;
		}
		//left 2 up 1
		if (isValidMove(piece, piece.getRow()-1, piece.getCol()-2)) {
			makeMove(piece, piece.getRow()-1, piece.getCol()-2);
			return true;
		}
		//left 2 down 1
		if (isValidMove(piece, piece.getRow()+1, piece.getCol()-2)) {
			makeMove(piece, piece.getRow()+1, piece.getCol()-2);
			return true;
		}
		//right 2 up 1
		if (isValidMove(piece, piece.getRow()-1, piece.getCol()+2)) {
			makeMove(piece, piece.getRow()-1, piece.getCol()+2);
			return true;
		}
		//right 2 down 1
		if (isValidMove(piece, piece.getRow()+1, piece.getCol()+2)) {
			makeMove(piece, piece.getRow()+1, piece.getCol()+2);
			return true;
		}
		//up 2 left 1
		if (isValidMove(piece, piece.getRow()-2, piece.getCol()-1)) {
			makeMove(piece, piece.getRow()-2, piece.getCol()-1);
			return true;
		}
		//up 2 right 1
		if (isValidMove(piece, piece.getRow()-2, piece.getCol()+1)) {
			makeMove(piece, piece.getRow()-2, piece.getCol()+1);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - the piece that the cpu is trying to move
	 * @return - true if the move is valid, false if not
	 */
	private boolean cpuKing(Piece piece) {
		int cur_x = piece.getRow();
		int cur_y = piece.getCol();
		if (isValidMove(piece, cur_x, cur_y+1)) {
			makeMove(piece, cur_x, cur_y+1);
			return true;
		}
		if (isValidMove(piece, cur_x-1, cur_y+1)) {
			makeMove(piece, cur_x-1, cur_y+1);
			return true;
		}
		if (isValidMove(piece, cur_x+1, cur_y+1)) {
			makeMove(piece, cur_x+1, cur_y+1);
			return true;
		}
		if (isValidMove(piece, cur_x+1, cur_y)) {
			makeMove(piece, cur_x+1, cur_y);
			return true;
		}
		if (isValidMove(piece, cur_x-1, cur_y)) {
			makeMove(piece, cur_x-1, cur_y);
			return true;
		}
		if (isValidMove(piece, cur_x+1, cur_y-1)) {
			makeMove(piece, cur_x+1, cur_y-1);
			return true;
		}
		if (isValidMove(piece, cur_x-1, cur_y-1)) {
			makeMove(piece, cur_x-1, cur_y-1);
			return true;
		}
		if (isValidMove(piece, cur_x, cur_y-1)) {
			makeMove(piece, cur_x, cur_y-1);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - the piece that the cpu is trying to move
	 * @return - true if the move is valid, false if not
	 */
	private boolean cpuBishop(Piece piece) {
		//check each vertical
		int temp_y = 7;
		while (temp_y > piece.getCol()) {
			if(isValidMove(piece, piece.getRow()+(temp_y-piece.getCol()), temp_y)) {
				makeMove(piece, piece.getRow()+(temp_y-piece.getCol()), temp_y);
				return true;
			}
			if(isValidMove(piece, piece.getRow()-(temp_y-piece.getCol()), temp_y)) {
				makeMove(piece, piece.getRow()-(temp_y-piece.getCol()), temp_y);
				return true;
			}
			temp_y--;
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - the piece that the cpu is trying to move
	 * @return - true if the move is valid, false if not
	 */
	private boolean cpuRook(Piece piece) {
		//for every vertical space, check if its a valid move, same with horizontal
		for (int temp_val = 7; temp_val >= 0; temp_val--) {
			if (isValidMove(piece, piece.getRow(), temp_val)) {
				makeMove(piece, piece.getRow(), temp_val);
				return true;
			}
			if (isValidMove(piece, temp_val, piece.getCol())) {
				makeMove(piece, temp_val, piece.getCol());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - the piece that the cpu is trying to move
	 * @return - true if the move is valid, false if not
	 */
	private boolean cpuPawn(Piece piece) {
		//if true, make the move
		//try moving diagonal
		if (isValidMove(piece, piece.getRow()+1, piece.getCol()-1)) {
			makeMove(piece, piece.getRow()+1, piece.getCol()-1);
			return true;
		}
		if (isValidMove(piece, piece.getRow()+1, piece.getCol()+1)) {
			makeMove(piece, piece.getRow()+1, piece.getCol()+1);
			return true;
		}
		
		//try moving forward 2
		if (isValidMove(piece, piece.getRow()+2, piece.getCol())) {
			makeMove(piece, piece.getRow()+2, piece.getCol());
			return true;
		}
		
		//try moving forward 1
		if (isValidMove(piece, piece.getRow()+1, piece.getCol())) {
			makeMove(piece, piece.getRow()+1, piece.getCol());
			return true;
		}
		return false;
	}
	
	//----------------EVERYTHING BELOW HERE DEALS WITH PIECE MOVEMENT VALIDITY---------------------
	
	/**
	 * main movement logic method, calls upon other methods for piece specific logic
	 * given a piece and a destination location, check if that move is valid
	 * 
	 * @param piece - the selected piece
	 * @param x - the x component of the board
	 * @param y - the y component of the board
	 * @return - returns true if the movement is valid, false if not
	 */
	public boolean isValidMove(Piece piece, int x, int y) {		
		//if the positions are the same
		if (piece.getRow() == x && piece.getCol() == y) {
			return false;
		}
		
		if (playerNum != piece.getPlayer()) {
			return false;
		}
		
		//if the location is off the board
		if (x < 0 || y < 0 || x > 7 || y > 7) {
			return false;
		}
		
		//if the destination contains a friendly piece
		//loop through this player number's pieces, and if that list contains the piece at [x][y], then it isnt valid
		Piece[][] current_board = model.getBoard();
		Piece[] piece_list_friend;
		Piece[] piece_list_foe;
		if (playerNum == 1) {
			piece_list_friend = model.getPlayer1Pieces();
			piece_list_foe = model.getPlayer2Pieces();
		} else {
			piece_list_friend = model.getPlayer2Pieces();
			piece_list_foe = model.getPlayer1Pieces();
		}
		
		// checks if there is a piece at designated location
		if (current_board[x][y] != null) {
			// checks if it is a friendly piece
			if (current_board[x][y].getPlayer() == piece.getPlayer()) {
				return false;
			}
		}
		
		//if there is a piece (friend or foe) that is in the way of the destination (excluding knights) the move isnt valid
		//check rook
		if (piece.getType() == PIECE_TYPE.ROOK) {
			return checkRook(piece, x, y, current_board);
		}
		
		//check bishop
		if (piece.getType() == PIECE_TYPE.BISHOP) {
			return checkBishop(piece, x, y, current_board);
		}
		
		//check king
		if (piece.getType() == PIECE_TYPE.KING) {
			return checkKing(piece, x, y, current_board);
		}
		
		//check queen
		if (piece.getType() == PIECE_TYPE.QUEEN) {
			if (piece.getCol() == y || piece.getRow() == x) {
				//moving like a rook
				return checkRook(piece, x, y, current_board);
			}
			else {
				return checkBishop(piece, x, y, current_board);
			}
		}
		
		//check pawn
		if (piece.getType() == PIECE_TYPE.PAWN) {
			return checkPawn(piece, x, y, current_board, piece_list_foe);
		}
		
		//check knight
		if (piece.getType() == PIECE_TYPE.KNIGHT) {
			return checkKnight(piece, x, y, current_board);
		}		
		
		return false;
		
	}
	
	/**
	 * 
	 * @param piece - piece to be moved
	 * @param x - the x position the piece is trying to move to
	 * @param y - the y position the piece is trying to move to
	 * @param board - the current state of the board
	 * @return true if the move is valid, false if not
	 */
	private boolean checkKnight(Piece piece, int x, int y, Piece[][] current_board) {
		//up 2 left 1
		if (piece.getRow()-2 == x && piece.getCol()-1 == y) {
			return true;
		}
		//up 2 right 1
		if (piece.getRow()-2 == x && piece.getCol()+1 == y) {
			return true;
		}
		//right 2 up 1
		if (piece.getRow()-1 == x && piece.getCol()+2 == y) {
			return true;
		}
		//right 2 down 1
		if (piece.getRow()+1 == x && piece.getCol()+2 == y) {
			return true;
		}
		//down 2 right 1
		if (piece.getRow()+2 == x && piece.getCol()+1 == y) {
			return true;
		}
		//down 2 left 1
		if (piece.getRow()+2 == x && piece.getCol()-1 == y) {
			return true;
		}
		//left 2 up 1
		if (piece.getRow()-1 == x && piece.getCol()-2 == y) {
			return true;
		}
		//left 2 down 1
		if (piece.getRow()+1 == x && piece.getCol()-2 == y) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param piece - piece to be moved
	 * @param x - the x position the piece is trying to move to
	 * @param y - the y position the piece is trying to move to
	 * @param board - the current state of the board
	 * @return true if the move is valid, false if not
	 */
	private boolean checkPawn(Piece piece, int x, int y, Piece[][] current_board, Piece[] piece_list) {
		//this seems a bit excessive and kind of redundant, could probably trim this down quite a bit
		//maybe instead of checking which player this is in every if statement, could just define the direction when calling this method
		
		//if this pawn hasn't moved yet and is trying to move 2 spaces straight forward
		if ((piece.getRow()==6 && piece.getPlayer()==2) ||
				(piece.getRow()==1 && piece.getPlayer()==1)) {
			if (piece.getCol() == y && (piece.getRow()+2 == x || piece.getRow()-2 == x)) {
				// checks the space the pawn is attempting to move to is empty
				if (current_board[x][y] == null) {
					return true;
				}
			}
		}
		
		//if moving straight forward 1 space
		if (piece.getCol() == y) {
			if (piece.getPlayer() == 1 && piece.getRow()+1 == x) {
				// checks the space the pawn is attempting to move to is emtpy
				if (current_board[x][y] == null) {
					return true;
				}
			}
			//if player 2 is moving their pawn forward
			if (piece.getPlayer() == 2 && piece.getRow()-1 == x) {
				// checks the space the pawn is attempting to move to is emtpy
				if (current_board[x][y] == null) {
					return true;
				}
			}
		}
		
		//if moving diagonally
		if (y == piece.getCol()+1 || y == piece.getCol()-1) {
			//player 1 condition
			if (piece.getPlayer() == 1 && piece.getRow()+1 == x) {
				if (current_board[x][y] != null && current_board[x][y].getPlayer() != piece.getPlayer()) {
					return true;
				}
			}
			//player 2 condition
			if (piece.getPlayer() == 2 && piece.getRow()-1 == x) {
				if (current_board[x][y] != null && current_board[x][y].getPlayer() != piece.getPlayer()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param piece - piece to be moved
	 * @param x - the x position the piece is trying to move to
	 * @param y - the y position the piece is trying to move to
	 * @param board - the current state of the board
	 * @return true if the move is valid, false if not
	 */
	private boolean checkKing(Piece piece, int x, int y, Piece[][] current_board) {
		int cur_x = piece.getRow();
		int cur_y = piece.getCol();
		//if the king is moving 1 space in any direction
		if ((x == cur_x || x == cur_x+1 || x == cur_x-1) &&
				(y == cur_y || y == cur_y+1 || y == cur_y-1)) {
			//if the space the king is moving to is empty or has an enemy on it (probably not necessary already checked before; here for clarity right now)
			if (current_board[x][y] == null || current_board[x][y].getPlayer() != piece.getPlayer()) {
				return true;
			}
		}
		//castling
		//if the king hasnt moved, and is moving 2 spaces right
		if (!piece.hasMoved() && y == cur_y+2 && cur_x == x && 
				current_board[x][7].getType() == PIECE_TYPE.ROOK && !current_board[x][7].hasMoved()) {
			makeMove(current_board[x][7], x, y-1);
			return true;
		}
		//left
		if (!piece.hasMoved() && y == cur_y-2 && cur_x == x && 
				current_board[x][0].getType() == PIECE_TYPE.ROOK && !current_board[x][0].hasMoved()) {
			makeMove(current_board[x][0], x, y+1);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - piece to be moved
	 * @param x - the x position the piece is trying to move to
	 * @param y - the y position the piece is trying to move to
	 * @param board - the current state of the board
	 * @return true if the move is valid, false if not
	 */
	private boolean checkBishop(Piece piece, int x, int y, Piece[][] current_board) {		
		int temp_x = -1;
		int temp_y = -1;

		//up left
		if (piece.getRow() > x && piece.getCol() > y) {
			//from current position to x y if there is a friendly piece the move is invalid
			temp_x = piece.getRow()-1;
			temp_y = piece.getCol()-1;
			while (temp_x > x && temp_y > y) {
				if (current_board[temp_x][temp_y] != null) {
					return false;
				}
				temp_x--;
				temp_y--;
			}
		}
		//down left
		if (piece.getRow() < x && piece.getCol() > y) {
			temp_x = piece.getRow()+1;
			temp_y = piece.getCol()-1;
			while (temp_x < x && temp_y > y) {
				if (current_board[temp_x][temp_y] != null) {
					return false;
				}
				temp_x++;
				temp_y--;
			}
		}
		//down right
		if (piece.getRow() < x && piece.getCol() < y) {
			temp_x = piece.getRow()+1;
			temp_y = piece.getCol()+1;
			while (temp_x < x && temp_y < y) {
				if (current_board[temp_x][temp_y] != null) {
					return false;
				}
				temp_x++;
				temp_y++;
			}
		}
		//up right
		if (piece.getRow() > x && piece.getCol() < y) {
			temp_x = piece.getRow()-1;
			temp_y = piece.getCol()+1;
			while (temp_x > x && temp_y < y) {
				if (current_board[temp_x][temp_y] != null) {
					return false;
				}
				temp_x--;
				temp_y++;
			}
		}
		if (temp_y == y && temp_x == x) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - piece to be moved
	 * @param x - the x position the piece is trying to move to
	 * @param y - the y position the piece is trying to move to
	 * @param board - the current state of the board
	 * @return true if the move is valid, false if not
	 */
	private boolean checkRook(Piece piece, int x, int y, Piece[][] board) {
		//also seems a bit redundant, maybe theres a better solution
		//which direction is it moving?
		int temp_y = -1;
		int temp_x = -1;
		if (piece.getRow() == x) {
			//must be moving vertically
			//up or down?
			if (piece.getCol() < y) {
				//moving right
				temp_y = piece.getCol()+1;
				while (temp_y < y) {
					//if there is a piece at this location, return false
					if (board[x][temp_y] != null) {
						return false;
					}
					temp_y++;
				}
			} else {
				//moving left
				temp_y = piece.getCol()-1;
				while (temp_y > y) {
					//if there is a piece at this location, return false
					if (board[x][temp_y] != null) {
						return false;
					}
					temp_y--;
				}
			}
			if (temp_y == y && piece.getRow() == x) {
				return true;
			}
			
		} else {
			if (piece.getRow() < x) {
				//must be moving up
				temp_x = piece.getRow()+1;
				while (temp_x < x) {
					if (board[temp_x][y] != null) {
						return false;
					}
					temp_x++;
				}
			} else {
				//moving down
				temp_x = piece.getRow()-1;
				while (temp_x > x) {
					if (board[temp_x][y] != null) {
						return false;
					}
					temp_x--;
				}
			}
			if (temp_x == x && piece.getCol() == y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * called by the view to see if the game should continue 
	 * @return true if game is over, false if not
	 */
	public boolean isGameOver() {
		if (this.model.isGameOver()) {
			return true;
		}
		boolean player1King = false;
		for (int i = 0; i < this.model.getPlayer1Pieces().length & !player1King; i++) {
			if (this.model.getPlayer1Pieces()[i].getType() == PIECE_TYPE.KING) {
				player1King = true;
			}
		}
		boolean player2King = false;
		for (int i = 0; i < this.model.getPlayer2Pieces().length & !player2King; i++) {
			if (this.model.getPlayer2Pieces()[i].getType() == PIECE_TYPE.KING) {
				player2King = true;
			}
		}
		return !(player1King & player2King);
	}
}
