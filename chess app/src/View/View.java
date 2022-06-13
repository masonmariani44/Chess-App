package View;

import java.io.FileNotFoundException;
import java.util.Optional;

import Controller.Controller;
import Model.Model;
import Utilities.Piece;
import Utilities.Piece.PIECE_TYPE;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * This class implement the view of the chess game created by Controller and Model
 * 
 * @author West Laos
 */
public class View extends Application{
	
	public static void main(String[] args) {
		launch();
    }
	
	private static final Border DEFAULT_BORDER = new Border(new BorderStroke(Color.rgb(0, 0, 0, 1), 
			BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(.5)));
	
	private static final Border SELECTED_BORDER = new Border(new BorderStroke(Color.rgb(255, 255, 255, 1), 
			BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(.5)));
	
	private Label[][] labels; // gives easy access to all the labels in the grid
	private GridPane grid;
	
	private Model model;
	
	private Controller player1;
	private Controller player2;
	
	private int turn;
	
	private Piece selectedPiece;
	private int newRow;
	private int newCol;
	
	private Timeline timer;
	private Label p1Timer;
	private Label p2Timer;
	
	private Stage stage;
	
	private boolean twoPlayerGame;
	
	@Override
	public void start(Stage arg0) throws Exception {
		// creates classes for the game
		this.model = new Model();
		this.player1 = new Controller(model, 1);
		this.player2 = new Controller(model, 2);
		
		this.stage = new Stage();
		
		// create vBox
		VBox vbox = new VBox();
		Label title =  new Label("CSC 335: CHESS");
		title.setPrefHeight(50);	
		title.setPrefWidth(500);
		title.setFont(new Font(30));
		title.setAlignment(Pos.TOP_CENTER);
		Label creators =  new Label("Created by Roger Liu, Mason Mariani and West Laos");
		creators.setPrefHeight(50);	
		creators.setPrefWidth(500);
		creators.setFont(new Font(20));
		creators.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().add(title);
		vbox.getChildren().add(creators);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setLayoutY(vbox.getLayoutY() + 50);
		vbox.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250, 0), new 
				CornerRadii(5.0), new Insets(0))));
		
		// create hBoxes
		HBox hbox = new HBox();				
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.setSpacing(hbox.getSpacing() + 10);
		Button newGame = new Button("New Game");
		newGame.setOnAction(e -> {
			gameModeSelction();
		});
		newGame.setPrefHeight(50);	
		newGame.setPrefWidth(150);
		newGame.setFont(new Font(20));
		Button reloadGame = new Button("Saved Game");
		reloadGame.setOnAction(e -> {
			this.model.load("chess.txt");
			gameModeSelction();
		});
		reloadGame.setPrefHeight(50);	
		reloadGame.setPrefWidth(150);
		reloadGame.setFont(new Font(20));
		hbox.getChildren().add(newGame);
		hbox.getChildren().add(reloadGame);		
		vbox.getChildren().add(new Label());
		vbox.getChildren().add(hbox);
		
		
		Scene scene = new Scene(vbox, 500, 350);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	 * Set up the correct screen for a two player match.
	 * 
	 * Sets up all buttons and value for a two player game and sets the current scene to display them.
	 */
	private void twoPlayerLocal() {
		this.twoPlayerGame = true;
		
		// setup grid
		createGridPane(this.player1);
		grid.setAlignment(Pos.CENTER);
		
		// create vBox
		VBox vbox = new VBox();
		
		
		
		// create hBoxes
		HBox hbox_main = new HBox();
		HBox hbox_player1 = new HBox();
		HBox hbox_player2 = new HBox();
		HBox hbox_controls =  new HBox();
		
		
		vbox.getChildren().add(hbox_main);
		
		hbox_main.getChildren().add(hbox_player1);
		hbox_main.getChildren().add(hbox_player2);
		hbox_main.getChildren().add(hbox_controls);
		
		// P1
		Label p1 = new Label("P1");
		p1.setPrefHeight(50);	
		p1.setPrefWidth(50);
		p1.setFont(new Font(30));
		p1Timer = new Label("10:00");
		p1Timer.setPrefHeight(50);	
		p1Timer.setPrefWidth(100);
		p1Timer.setFont(new Font(30));
		hbox_player1.getChildren().add(p1);
		hbox_player1.getChildren().add(p1Timer);
		hbox_player1.setAlignment(Pos.BASELINE_CENTER);
		
		// P2
		Label p2 = new Label("P2");
		p2.setPrefHeight(50);	
		p2.setPrefWidth(50);
		p2.setFont(new Font(30));
		p2Timer = new Label("10:00");
		p2Timer.setPrefHeight(50);	
		p2Timer.setPrefWidth(100);
		p2Timer.setFont(new Font(30));
		hbox_player2.getChildren().add(p2);
		hbox_player2.getChildren().add(p2Timer);
		hbox_player2.setAlignment(Pos.BASELINE_CENTER);
		
		// Controls
		Button saveGame = new Button("Menu");
		saveGame.setOnAction(e -> {
			gameControles();
		});
		
		saveGame.setPrefHeight(30);	
		saveGame.setPrefWidth(50);
		saveGame.setFont(new Font(12));
		saveGame.setAlignment(Pos.CENTER);
		
		hbox_controls.getChildren().add(saveGame);
		hbox_controls.setAlignment(Pos.CENTER);
		
		
		hbox_main.setAlignment(Pos.CENTER);
		
		// setup vBox
		vbox.getChildren().add(grid); 				
		vbox.getChildren().add(new Label()); 		
		vbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vbox, 500, 500);
        this.stage.setScene(scene);
        this.stage.setTitle("Chess");
        this.stage.show();
		
		int turn = 0;
		
		this.timer = new Timeline(new KeyFrame (Duration.seconds(1), new Clock()));
		this.timer.setCycleCount(Timeline.INDEFINITE);
		this.timer.play();
	}

	/**
	 * Set up the correct screen for a single player match.
	 * 
	 * Sets up all buttons and value for a single player game and sets the current scene to display them.
	 */
	private void singlePlayerAI() { 
		this.twoPlayerGame = false;
		// setup grid
		createGridPane(this.player1);
		grid.setAlignment(Pos.CENTER);
		
		// create vBox
		VBox vbox = new VBox();
		
		// create hBoxes
		HBox hbox_main = new HBox();
		HBox hbox_controls =  new HBox();
		
		vbox.getChildren().add(hbox_main);
		
		hbox_main.getChildren().add(hbox_controls);
		
		
		// Controls
		Button saveGame = new Button("Menu");
		saveGame.setOnAction(e -> {
			gameControles();
		});
		
		saveGame.setPrefHeight(30);	
		saveGame.setPrefWidth(50);
		saveGame.setFont(new Font(12));
		saveGame.setAlignment(Pos.CENTER);
		
		hbox_controls.getChildren().add(saveGame);
		hbox_controls.setAlignment(Pos.CENTER);
		
		
		hbox_main.setAlignment(Pos.CENTER);
		
		// setup vBox
		vbox.getChildren().add(grid); 				
		vbox.getChildren().add(new Label()); 		
		vbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vbox, 500, 500);
        this.stage.setScene(scene);
        this.stage.setTitle("Chess");
        this.stage.show();
		
		int turn = 0;
	}
	
	/**
	 * Initializes the global variable grid.
	 * 
	 * @param Controller is the one of the controllers used in the game that has been initialized.
	 */
	private void createGridPane(Controller controller) {
		grid = new GridPane();
		labels =  new Label[8][8];
		boolean toggle = true;
		// creates all the buttons on the board
		for (int row = 0; row < 8; row++) {
			toggle = !toggle;
		    for (int col = 0; col < 8; col++) { 
		    	Label label;
		    	// checks if their is a piece in this location
		    	if (controller.getBoard()[row][col] != null) {
		    		// create label
		    		label = new Label();
		    		// controller.getBoard()[row][col].getType().getDescription();
		    		// create image
		    	    Image img = new Image("Chess_Pieces_Sprite.png");
		    	   
		    	    ImageView view = new ImageView(img);
		    	    
		    	    Rectangle2D viewportRect = selectImage(row, col);
		    	    
		    	    view.setViewport(viewportRect);
		    	    view.setFitHeight(40);
		    	    view.setFitWidth(40);
		    	    
		    	    label.setGraphic(view); 
		    	    img.cancel();
		    	} else {
		    		label = new Label("");
		    	}
		    	labels[row][col] = label;
		    	grid.add(labels[row][col], col, row);
		    	
		    	label.setPrefHeight(50);	
		    	label.setPrefWidth(50);
		    	label.setFont(new Font(15));
		    	label.setBorder(DEFAULT_BORDER);
		    	label.setAlignment(Pos.CENTER);
		    	
		    	// added actions on click
		    	label.setOnMouseClicked(event -> {
		    		int r = grid.getRowIndex(label);
		    		int c = grid.getColumnIndex(label);
		    		
		    		if (controller.getBoard()[r][c] != null) {
		    			System.out.println("clicked "+ controller.getBoard()[r][c].getType() + " at "
				    			+ r + ", " + c);
		    			// checks if a piece was already slected if not selects this one
		    			if (this.selectedPiece == null) {
			    			this.selectedPiece = controller.getBoard()[r][c];
			    			this.labels[r][c].setBorder(SELECTED_BORDER);
			    		// checks if current selected piece is this one if so deslects it 
			    		} else if ((this.selectedPiece.getRow() == r) & 
			    				(this.selectedPiece.getCol() == c)) {
			    			
			    			this.selectedPiece = null;
			    			this.labels[r][c].setBorder(DEFAULT_BORDER);
			    		// sets coordinates for selected piece to move to
			    		} else {
			    			this.newRow = r;
			    			this.newCol = c;
			    			attemptToMovePiece();
			    			
			    		}
			    	} else {
			    		System.out.println("clicked space at "
				    			+ r + ", " + c);
			    		// checks if a piece has been selected yet and if one has 
			    		// it sets coordinates for it to move to
			    		if (this.selectedPiece != null) {
			    			this.newRow = r;
			    			this.newCol = c;
			    			attemptToMovePiece();
			    		}
			    }});
		    	 
		    	// checks which color this label is
		    	if (toggle) {
		    		label.setStyle("-fx-background-color: #B88B4A");
		    	} else {
		    		label.setStyle("-fx-background-color: #E3C16F");
		    	}
		    	toggle = !toggle;
		    }
		}
	}
	
	/**
	 * Updates the global variable grid to match the controller given
	 * 
	 * @param Controller is the one of the controllers used in the game.
	 */
	private void updateGridPane(Controller controller) {
		boolean toggle = true;
		// creates all the buttons on the board
		for (int row = 0; row < 8; row++) {
			toggle = !toggle;
		    for (int col = 0; col < 8; col++) { 
		    	Label label = this.labels[row][col];
		    	// checks if their is a piece in this location
		    	if (controller.getBoard()[row][col] != null) {
		    		
		    	    Image img = new Image("Chess_Pieces_Sprite.png");
		    	    ImageView view = new ImageView(img);
		    	    
		    	    Rectangle2D viewportRect = selectImage(row, col);
		    	    
		    	    view.setViewport(viewportRect);
		    	    view.setFitHeight(40);
		    	    view.setFitWidth(40);
		    	    
		    	    label.setGraphic(view); 
		    	} else {
		    		label.setText("");
		    		label.setGraphic(null);
		    	}
		    	label.setBorder(DEFAULT_BORDER);
		    }
		}
	}
	
	/**
	 * Creates a Rectangle 2D of the ./Chess_Pieces_Sprite.png and the given location
	 * 
	 * @param Row represants the row the piece this is being made for is in.
	 * @param Col represants the col the piece this is being made for is in.
	 */
	private Rectangle2D selectImage(int row, int col) {
		int width = 210;
		int height = 200;
		int x = 0;
		int y = 0;
		
		Piece selectedPiece = this.player1.getBoard()[row][col];
		
		if (selectedPiece.getPlayer() == 1) {
			y = 200;
		} else {
			y = 0;
		}
		
		if (selectedPiece.getType().equals(PIECE_TYPE.KING)) {
			x = 0;
		} else if (selectedPiece.getType().equals(PIECE_TYPE.QUEEN)) {
			x = 200;
		} else if (selectedPiece.getType().equals(PIECE_TYPE.BISHOP)) {
			x = 400;
		} else if (selectedPiece.getType().equals(PIECE_TYPE.KNIGHT)) {
			x = 600;
		} else if (selectedPiece.getType().equals(PIECE_TYPE.ROOK)) {
			x = 800;
		} else if (selectedPiece.getType().equals(PIECE_TYPE.PAWN)) {
			x = 1000;
		}
		
		return (new Rectangle2D(x, y, width, height));
	}
	
	/**
	 * Checks if a piece and new position have been selected and if it is valid it moves the piece
	 * 
	 */
	private void attemptToMovePiece() {
		if (this.selectedPiece != null & this.newRow != -1 & this.newCol != -1) {
			// player ones turn
			if (turn % 2 == 0) {
				if (this.player2.isValidMove(this.selectedPiece, this.newRow, this.newCol)) {
					this.player2.makeMove(this.selectedPiece, this.newRow, this.newCol);
					this.player1.updateModel(this.player2.getModel());
					this.model = player1.getModel();
					turn += 1;
					if (this.twoPlayerGame) {
						// restart timer so it counts down for other player
						this.timer.stop();
						this.timer = new Timeline(new KeyFrame (Duration.seconds(1), new Clock()));
						this.timer.setCycleCount(Timeline.INDEFINITE);
						this.timer.play();
					}
					this.labels[this.selectedPiece.getRow()][this.selectedPiece.getCol()]
							.setBorder(DEFAULT_BORDER);
					this.selectedPiece = null;
					this.newRow = -1;
					this.newCol = -1;
					updateGridPane(this.player1);
					if (this.player1.isGameOver()) {
						if (this.twoPlayerGame) {
							this.timer.stop();
						}
						gameOver();
					}
					if (!this.twoPlayerGame) {
						this.player1.cpuMakeMove();
						this.player2.updateModel(this.player1.getModel());
						this.model = player1.getModel();
						this.turn += 1;
						updateGridPane(this.player1);
						if (this.player1.isGameOver()) {
							if (this.twoPlayerGame) {
								this.timer.stop();
							}
							gameOver();
						}
					}
				}
			// player twos turn
			}  else {
				if (this.player1.isValidMove(this.selectedPiece, this.newRow, this.newCol)) {
					this.player1.makeMove(this.selectedPiece, this.newRow, this.newCol);
					this.player2.updateModel(this.player1.getModel());
					this.model = player1.getModel();
					turn += 1;
					if (this.twoPlayerGame) {
						// restart timer so it counts down for other player
						this.timer.stop();
						this.timer = new Timeline(new KeyFrame (Duration.seconds(1), new Clock()));
						this.timer.setCycleCount(Timeline.INDEFINITE);
						this.timer.play();
						
					}
					this.labels[this.selectedPiece.getRow()][this.selectedPiece.getCol()]
							.setBorder(DEFAULT_BORDER);
					updateGridPane(this.player1);
					if (this.player1.isGameOver()) {
						if (this.twoPlayerGame) {
							this.timer.stop();
						}
						gameOver();
					}
				}
			}
			this.selectedPiece = null;
			this.newRow = -1;
			this.newCol = -1;
		}
	}
	 
	/**
	 * This class represents a chess timer
	 */
	public class Clock implements EventHandler{
		@Override
		public void handle(Event arg0) {
			Label curPlayer;
			if (turn % 2 == 0) {
				curPlayer = p1Timer;
			} else {
				curPlayer = p2Timer;
			}
			
			if (curPlayer.getText().split(":")[1].equals("00")) {
				if (curPlayer.getText().split(":")[0].equals("0")) {
					gameOver();
				} else {
					curPlayer.setText(Integer.parseInt(curPlayer.getText().split(":")[0]) - 1 
							+ "" + ":59");
				}
			} else {
				if (Integer.parseInt(curPlayer.getText().split(":")[1]) <= 10) {
					curPlayer.setText(curPlayer.getText().split(":")[0] + ":0" +
							(Integer.parseInt(curPlayer.getText().split(":")[1]) - 1));
				} else {
					curPlayer.setText(curPlayer.getText().split(":")[0] + ":" +
							(Integer.parseInt(curPlayer.getText().split(":")[1]) - 1));
				}
			}
			stage.show();
		}
		
	}
	
	/**
	 * Creates an alert asking the player which game mode they would like to play.
	 */
	public void gameModeSelction() {
		// creates alert to check if game is versus a player or ai
		Alert gameModeSelection = new Alert(Alert.AlertType.CONFIRMATION);
		gameModeSelection.setTitle("Chess");
		gameModeSelection.getDialogPane().getChildren().remove(1);
		gameModeSelection.setHeaderText("Welcome to our chess app!");
		gameModeSelection.setContentText("To begin please select a game mode.");
		
		gameModeSelection.getDialogPane().setMinHeight(100);
		gameModeSelection.getDialogPane().setMaxWidth(450);
		
		
		ButtonType twoPlayerLocal = new ButtonType("Two player local.");
		ButtonType singlePlayerAI = new ButtonType("Single player vs A.I.");
		
		
		gameModeSelection.getButtonTypes().set(0, twoPlayerLocal);
		gameModeSelection.getButtonTypes().add(singlePlayerAI);
		
		gameModeSelection.setGraphic(null);
		
		Optional<ButtonType> result = gameModeSelection.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);

		if (button == twoPlayerLocal) {
			twoPlayerLocal();
		} else if (button == singlePlayerAI){
			singlePlayerAI();
		}
	}
	
	/**
	 * Creates a menu letting the player save and close the game
	 */
	public void gameControles() {
		// creates alert to check if game is versus a player or ai
		Alert gameControles = new Alert(Alert.AlertType.CONFIRMATION);
		gameControles.setTitle("Chess");
		gameControles.setHeaderText("");
		gameControles.setContentText("");
		
		gameControles.getDialogPane().setMaxWidth(90);
		gameControles.getDialogPane().getChildren().remove(1);
		
		
		ButtonType save = new ButtonType("Save Game");
		ButtonType close = new ButtonType("Close Game");
		
		gameControles.getButtonTypes().set(0, save);
		gameControles.getButtonTypes().add(close);
		
		gameControles.setGraphic(null);
		
		Optional<ButtonType> result = gameControles.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);

		if (button == save) {
			this.model.save("chess.txt");
		} else if (button == close){
			System.exit(0);
		} else {
			gameControles.close();
		}
	}
	
	/**
	 * Creates a game over screen and lets you start a new game or end the program
	 */
	private void gameOver() {
		// creates alert to check if game is versus a player or ai
		Alert gameOver = new Alert(Alert.AlertType.CONFIRMATION);
		gameOver.setTitle("Chess");
		if (this.model.getWinner() == 0) {
			if (this.p1Timer.getText().equals("0:00")) {
				gameOver.setHeaderText("Congratulations Player 2 is the WINNER!!!");
			} else {
				gameOver.setHeaderText("Congratulations Player 1 is the WINNER!!!");
			}
		} else {
			if (turn % 2 == 0 ) {
				gameOver.setHeaderText("Congratulations Player 2 is the WINNER!!!");
			} else {
				gameOver.setHeaderText("Congratulations Player 1 is the WINNER!!!");
			}
		}
		
		gameOver.getDialogPane().setMinHeight(100);
		gameOver.getDialogPane().setMaxWidth(450);
		
		
		ButtonType playAgain = new ButtonType("Play Again");
		ButtonType endGame = new ButtonType("End Game");
		
		
		gameOver.getButtonTypes().set(0, playAgain);
		gameOver.getButtonTypes().add(endGame);
		
		gameOver.setGraphic(null);
		
		Optional<ButtonType> result = gameOver.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);

		if (button == playAgain) {
			try {
				this.stage.close();
				start(this.stage);
			} catch (Exception e) {
			}
		} else if (button == endGame){
			System.exit(0);
		} else {
			System.exit(0);
		}
	}
}
