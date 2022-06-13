package TestCase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Controller.Controller;
import Model.Model;

class TestCase {
	Model model = new Model();
	@Test
	void testModel() {
		assertTrue(model.getBoard()[0][0].getValue() == 5);
		assertTrue(model.getBoard()[0][4].getValue() == 10);
		assertTrue(model.getBoard()[7][3].getValue() == 9);
		assertTrue(model.getPlayer1Pieces().length == 16);
		assertTrue(model.getPlayer2Pieces().length == 16);
		model.makeMove(model.getBoard()[1][0], 2, 0);
		model.makeMove(model.getBoard()[6][0], 5, 0);
		assertFalse(model.isGameOver());
		model.save("test.txt");
		model.makeMove(model.getBoard()[2][0], 7, 4);
		assertTrue(model.getPlayer2Pieces().length == 15);
		assertTrue(model.isGameOver());
		assertTrue(model.getWinner() == 1);
		model.load("test.txt");
		model.makeMove(model.getBoard()[5][0], 0, 4);
		assertTrue(model.getPlayer2Pieces().length == 16);
		assertTrue(model.isGameOver());
		assertTrue(model.getWinner() == 2);
	}
	
	Model model2 = new Model();
	Controller player1 = new Controller(model2, 1);
	Controller player2 = new Controller(model2, 2);
	@Test
	void testController() {
		assertTrue(player1.getBoard().equals(player2.getBoard()));
		// check basic valid move and move for pawn
		assertFalse(player1.isValidMove(model2.getBoard()[0][0], 0, 0));
		assertFalse(player1.isValidMove(model2.getBoard()[6][0], 5, 0));
		assertFalse(player1.isValidMove(model2.getBoard()[0][0], -1, 0));
		assertFalse(player1.isValidMove(model2.getBoard()[0][0], 1, 0));
		assertFalse(player2.isValidMove(model2.getBoard()[6][0], 7, 0));
		assertTrue(player1.isValidMove(model2.getBoard()[1][0], 2, 0));
		assertTrue(player1.isValidMove(model2.getBoard()[1][0], 3, 0));
		assertTrue(player2.isValidMove(model2.getBoard()[6][0], 5, 0));
		assertTrue(player2.isValidMove(model2.getBoard()[6][0], 4, 0));
		assertFalse(player1.isValidMove(model2.getBoard()[1][0], 2, 1));
		assertFalse(player2.isValidMove(model2.getBoard()[6][0], 5, 1));
		player1.makeMove(model2.getBoard()[1][0], 3, 0);
		player2.makeMove(model2.getBoard()[6][1], 4, 1);
		assertTrue(player1.isValidMove(model2.getBoard()[3][0], 4, 1));
		assertTrue(player2.isValidMove(model2.getBoard()[4][1], 3, 0));
		assertTrue(player1.isValidMove(model2.getBoard()[0][0], 1, 0));
		//check visVlidMove for rook
		player1.makeMove(model2.getBoard()[0][0], 2, 0);
		player1.makeMove(model2.getBoard()[2][0], 2, 3);
		player1.makeMove(model2.getBoard()[2][3], 3, 3);
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 5, 3));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 2, 3));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 3, 4));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 3, 1));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 6, 3));
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 3, 0));
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 7, 3));
		player1.makeMove(model2.getBoard()[1][3], 2, 3);
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 1, 3));
		player1.makeMove(model2.getBoard()[2][3], 2, 2);
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 1, 3));
		player1.makeMove(model2.getBoard()[2][2], 3, 1);
		player1.makeMove(model2.getBoard()[3][0], 1, 0);
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 3, 0));
		player1.makeMove(model2.getBoard()[3][1], 3, 5);
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 3, 7));
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 4, 4));
		player1.makeMove(model2.getBoard()[3][5], 1, 3);
		player2.makeMove(model2.getBoard()[4][1], 6, 1);
		player1.makeMove(model2.getBoard()[3][3], 0, 0);
		//Check isValidMove for knight
		assertFalse(player1.isValidMove(model2.getBoard()[0][1], 3, 7));
		assertTrue(player1.isValidMove(model2.getBoard()[0][1], 2, 0));
		assertTrue(player1.isValidMove(model2.getBoard()[0][1], 2, 2));
		assertTrue(player2.isValidMove(model2.getBoard()[7][1], 5, 0));
		assertTrue(player2.isValidMove(model2.getBoard()[7][1], 5, 2));
		player1.makeMove(model2.getBoard()[0][1], 3, 3);
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 2, 1));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 2, 5));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 4, 1));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 4, 5));
		player1.makeMove(model2.getBoard()[3][3], 0, 1);
		// check isValidMove for bishop
		assertFalse(player1.isValidMove(model2.getBoard()[0][2], 2, 4));
		assertFalse(player1.isValidMove(model2.getBoard()[0][2], 2, 0));
		assertFalse(player2.isValidMove(model2.getBoard()[7][2], 5, 4));
		assertFalse(player2.isValidMove(model2.getBoard()[7][2], 5, 0));
		player1.makeMove(model2.getBoard()[0][2], 3, 3);
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 3, 0));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 5, 5));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 5, 1));
		player1.makeMove(model2.getBoard()[3][3], 4, 3);
		assertTrue(player1.isValidMove(model2.getBoard()[4][3], 2, 1));
		assertTrue(player1.isValidMove(model2.getBoard()[4][3], 2, 5));
		player1.makeMove(model2.getBoard()[4][3], 0, 2);
		//check isValidMove for queen
		player1.makeMove(model2.getBoard()[0][3], 3, 3);
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 2, 2));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 4, 3));
		//check isValidMove for king
		player1.makeMove(model2.getBoard()[0][4], 3, 3);
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 2, 2));
		assertTrue(player1.isValidMove(model2.getBoard()[3][3], 4, 3));
		assertFalse(player1.isValidMove(model2.getBoard()[3][3], 5, 3));
		Model newModel = new Model();
		player1.updateModel(newModel);
		assertFalse(player1.getModel().equals(player2.getModel()));
	}
	

}
