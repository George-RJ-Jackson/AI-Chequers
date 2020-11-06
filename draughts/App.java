package draughts;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {

	GridPane grid;
	static Board board;
	boolean clicked = false;

	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) {
		stage.setScene(startMenu());
		stage.show();
	}

	public void handleGui() {
		board = new Board();
		grid = new GridPane();
		displayPieces();
		events();
	}
	/** Display startMenu **/
	@SuppressWarnings("exports")
	public Scene startMenu() {
		Pane pane = new Pane();
		ComboBox<String> comboBoxPlayer = new ComboBox<String>();
		comboBoxPlayer.getItems().addAll("Player vs Computer", "Player vs Player");
		comboBoxPlayer.setValue("Set Mode");
		comboBoxPlayer.setLayoutX(300);
		comboBoxPlayer.setLayoutY(300);
		comboBoxPlayer.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-background-color: white; -fx-max-width:200;");

		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll("1 - Easy", "2", "3", "4", "5 - Difficult");
		comboBox.setValue("Set Difficulty");
		comboBox.setLayoutX(300);
		comboBox.setLayoutY(250);
		comboBox.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-background-color: white; -fx-min-width:200;");

		CheckBox cb = new CheckBox("Disable move assist");
		cb.setLayoutX(301);
		cb.setLayoutY(350);
		cb.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-size: 17.5px; ");

		Button button = new Button("Start Game");
		button.setLayoutX(300);
		button.setLayoutY(390);
		button.setMinWidth(200);
		button.setStyle("-fx-background-color: #303030; -fx-text-fill: white; -fx-font-size: 18px; ");

        String rules = new String("http://www.indepthinfo.com/checkers/play.shtml");
		Button helpButton = new Button("Rules");
		helpButton.setLayoutX(300);
		helpButton.setLayoutY(440);
		helpButton.setMinWidth(200);
		helpButton.setStyle("-fx-background-color: #303030; -fx-text-fill: white; -fx-font-size: 18px; ");
		
		Rectangle r = new Rectangle();
		r.setX(200);
		r.setY(200);
		r.setWidth(400);
		r.setHeight(300);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.web("404040"));

		pane.getChildren().add(new ImageView("bluurredbg.jpg"));
		pane.getChildren().add(r);
		pane.getChildren().add(button);
		pane.getChildren().add(helpButton);
		pane.getChildren().add(cb);
		pane.getChildren().add(comboBox);
		pane.getChildren().add(comboBoxPlayer);

		Scene scene = new Scene(pane, 800, 800);
		handleGui();
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("Checkers");
				stage.setScene(new Scene(grid, 800, 800));
				stage.show();
				
			}
		});
		comboBox.setOnAction((e) -> {
			if (comboBox.getValue() == "1 - Easy") {
				App.board.difficulty = 2;
			}
			if (comboBox.getValue() == "2") {
				App.board.difficulty = 3;
			}
			if (comboBox.getValue() == "3") {
				App.board.difficulty = 4;
			}
			if (comboBox.getValue() == "4") {
				App.board.difficulty = 5;
			}
			if (comboBox.getValue() == "5 - Difficult") {
				App.board.difficulty = 7;
			}

		});
		
		helpButton.setOnAction((e) -> {
            getHostServices().showDocument(rules);

		});
		comboBoxPlayer.setOnAction((e) -> {
			if (comboBoxPlayer.getValue() == "Player vs Computer") {
				App.board.aiIsPlaying = true;
			}
			if (comboBoxPlayer.getValue() == "Player vs Player") {
				App.board.aiIsPlaying = false;
			}
		});
		cb.setOnAction((e) -> {
			if (cb.isSelected()) {
				App.board.showMoves = false;
			}
		});
		return scene;

	}
	/** Display pieces and board **/
	public void displayPieces() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				Pane tile = new Pane();
				if ((j + i) % 2 == 0) {
					tile.getChildren().add(new ImageView("whitebg.png"));
				}
				if ((j + i) % 2 != 0) {
					tile.getChildren().add(new ImageView("blackbg.png"));
				}
				tile.setMinSize(100, 100);
				grid.add(tile, i, j);
			}
		}
		for (Piece piece : board.pieces) {
			grid.add(piece.getImage(), piece.getX(), piece.getY());
			grid.add(piece.getImage(), piece.getX(), piece.getY());
		}
	}
	/** Display available moves **/
	public void displayMoves(Piece piece) {
		for (Moves move : piece.moves) {
			if (piece.getValue() == PieceValue.RED) {
				if (piece.isKing == false) {
					if (piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueWhite.png");
						grid.add(image, move.newx, move.newy);
					}
				} else {
					if (piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueWhiteKing.png");
						grid.add(image, move.newx, move.newy);
					}
				}

			}
			if (piece.getValue() == PieceValue.WHITE) {
				if (piece.isKing == false) {
					if (piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("blackOpaque.png");

						grid.add(image, move.newx, move.newy);
					}
				} else {
					if (piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueBlackKing.png");
						grid.add(image, move.newx, move.newy);
					}
				}
			}
		}
	}
	/** Display forced captures **/
	public void displayForcedMoves() {
		for (Moves move : board.forcedMoves) {
			if (move.piece.getValue() == PieceValue.RED) {
				if (move.piece.isKing == false) {
					if (move.piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueWhite.png");

						grid.add(image, move.newx, move.newy);
					}
				} else {
					if (move.piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueWhiteKing.png");
						grid.add(image, move.newx, move.newy);
					}
				}

			}
			if (move.piece.getValue() == PieceValue.WHITE) {
				if (move.piece.isKing == false) {
					ImageView image = new ImageView("blackOpaque.png");
					if (move.piece.isLegal(move.newx, move.newy)) {
						grid.add(image, move.newx, move.newy);
					}
				} else {
					if (move.piece.isLegal(move.newx, move.newy)) {
						ImageView image = new ImageView("opaqueBlackKing.png");
						grid.add(image, move.newx, move.newy);
					}
				}
			}
		}
	}
	/** Reset display **/
	public void clearDisplay() {
		grid.getChildren().clear();
	}
	/** Handle mouse click of board **/
	public void events() {
		grid.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			int x = (int) Math.floor(e.getX() / 100);
			int y = (int) Math.floor(e.getY() / 100);
			Piece piece = board.getPiece(x, y);
			if (piece != null) {
				if (board.aiIsPlaying && piece.getValue() == PieceValue.RED) {
				} else {
					piece.moves.clear();
					board.forcedMoves.clear();
					board.hasToTake(piece.getValue());
					if (board.forcedMoves.isEmpty()) {
						clearDisplay();
						displayPieces();
						piece.getMoves();
						if (board.showMoves == true) {
							displayMoves(piece);
						}
						board.forcedMoves.clear();
					} else {
						clearDisplay();
						piece.getMoves();
						displayPieces();
						if (board.showMoves == true) {
							displayForcedMoves();
						}
						board.forcedMoves.clear();
					}
					board.badMove = false;
					board.activePiece = piece;
					ImageView activeImage = new ImageView("activePiece.png");
					grid.add(activeImage, board.activePiece.getX(), board.activePiece.getY());
				}
			} else {
				board.badMove = true;
			}
			/* Looping to see if click matches up with a currently displayed move */
			if(board.activePiece != null) {
			for (int i = 0; i < board.activePiece.moves.size(); i++) {
				Moves move = board.activePiece.moves.get(i);
				board.hasToTake(board.activePiece.getValue());
				if (x == move.newx && y == move.newy) {
					if (move.killedPiece != null || board.forcedMoves.isEmpty()) {
						board.activePiece.x = x;
						board.activePiece.y = y;
						board.forcedMoves.clear();
						App.board.badMove = false;
						if (move.killedPiece != null && move.killedPiece.isKing == true) {
							board.activePiece.isKing = true;
							board.pieces.remove(move.killedPiece);
						}
						if(move.killedPiece != null) {
							board.pieces.remove(move.killedPiece);
						}
						if (board.activePiece.getValue() == PieceValue.WHITE && board.activePiece.y == 0) {
							board.activePiece.isKing = true;
							move.wasking = true;
						}
						if (board.activePiece.getValue() == PieceValue.RED && board.activePiece.y == 7) {
							board.activePiece.isKing = true;
							move.wasking = true;
						}
						board.activePiece.moves.clear();
						if (App.board.hasWon(PieceValue.RED)) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Game over");
							alert.setHeaderText("The game is over");
							alert.showAndWait();
							App.board.gameOver = true;
						}
						if (App.board.hasWon(PieceValue.WHITE)) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Game over");
							alert.setHeaderText("The game is over");
							alert.showAndWait();
							App.board.gameOver = true;
						}
						board.switchTurn();
						clearDisplay();
						displayPieces();
						board.forcedMoves.clear();
						board.forceTake = false;
					}
				}
			}
			if (board.badMove == true) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Move");
				if(board.forceTake == true) {
				alert.setHeaderText("There is a forced capture available.");
				}else {
					alert.setHeaderText("This is an invalid move.");	
				}
				alert.showAndWait();
			}	
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
