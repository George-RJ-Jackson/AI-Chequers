package draughts;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Board {

	ArrayList<Piece> pieces = new ArrayList<Piece>();
	Piece activePiece;
	Minmax ai;
	int turn;
	int difficulty = 1;
	boolean forceTake = false;
	boolean showMoves = true;
	boolean aiIsPlaying = true;
	boolean badMove = false;
	boolean gameOver = false;
	/** Board constructor **/
	public Board() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if ((j + i) % 2 != 0) {
					if (j < 3) {
						Piece piece = new Piece(PieceValue.RED, i, j);
						pieces.add(piece);
					} else if (j > 4) {
						Piece piece = new Piece(PieceValue.WHITE, i, j);
						pieces.add(piece);
					}
				}
			}
		}
		this.ai = new Minmax();
	}
	/** Evaluate any forced captures **/
	ArrayList<Moves> forcedMoves = new ArrayList<Moves>();
	public void hasToTake(PieceValue value) {
		for (Piece piece : pieces) {
			piece.getMoves();
			if (piece.getValue() == value) {
				for (Moves move : piece.moves) {
					if (move.killedPiece != null) {
						forceTake = true;
						forcedMoves.add(move);
					}
				}
			}
		}
	}
	/** Switch turn of player **/
	public void switchTurn() {
		if (turn == 0) {
			turn = 1;
			if (aiIsPlaying == true) {
				if (gameOver == false) {
					ai.start();
				}
			}
		}else {
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
			turn = 0;
		}
	}
	/** Get piece at X, Y
	 * @return pieice
	 *  **/
	public Piece getPiece(int x, int y) {
		for (Piece piece : pieces) {
			if (piece.getX() == x && piece.getY() == y) {
				return piece;
			}
		}
		return null;
	}
	/** Determine if there is a winner
	 * @return true or false
	 *  **/
	public boolean hasWon(PieceValue value) {
		for (Piece piece : App.board.pieces) {
			if (piece.value == value) {
				return false;
			}
		}
		return true;
	}
}
