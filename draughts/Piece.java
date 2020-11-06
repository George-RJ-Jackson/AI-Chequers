
package draughts;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Piece extends Pane {	
	ArrayList<Moves> moves = new ArrayList<Moves>();
	PieceValue value;
	int x;
	int y;
	boolean isKing;

	/** Constructor for the piece object, requires value of piece and location
	 * @param Value of the piece
	 * @param X location
	 * @param Y location
	 * **/
	public Piece(PieceValue value, int x, int y) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	/** Returns piece X coordinate 
	 * @return X coordinate
	 * **/
	public int getX() {
		return x;
	}
	/** Set piece X coordinate **/
	public void setX(int x) {
		this.x = x;
	}
	/** Returns piece Y coordinate 
	 * @return Y coordinate
	 * **/
	public int getY() {
		return y;
	}
	/** set piece Y coordinate 
	 * **/
	public void setY(int y) {
		this.y = y;
		if (y == 0 && value == PieceValue.WHITE) {
			this.isKing = true;
		}
		if (y == 7 && value == PieceValue.RED) {
			this.isKing = true;
		}
	}
	/** Obtains the apppropriate draught image 
	 * @return Image
	 * **/
	@SuppressWarnings("exports")
	public ImageView getImage() {
		ImageView image = new ImageView();
		if (value == PieceValue.RED) {
			if (isKing == true) {
				
				image = new ImageView("whiteKing.png");
				
			} else
				image = new ImageView("white.png");
		}
		if (value == PieceValue.WHITE) {
			if (isKing == true) {
				image = new ImageView("blackking.png");
			} else
				image = new ImageView("red.png");
		}
		return image;
	}
	/** Get the value of the piece
	 * @return Piece value
	 */
	public PieceValue getValue() {
		return value;
	}
	/** Set the value of the piece */
	public void setValue(PieceValue value) {
		this.value = value;
	}
	/** Get the moves of a piece 
	 * @return The moves 
	 * */
	public ArrayList<Moves> getMoves() {
		this.moves.clear();
		switch (value) {
		case RED:
			if (isKing == false) {
				moves.addAll(getMovesDown());
				moves.addAll(getJumpsDown());
			} else {
				moves.addAll(getMovesUp());
				moves.addAll(getJumpsUp());
				moves.addAll(getMovesDown());
				moves.addAll(getJumpsDown());
			}
			break;
		case WHITE:
			if (isKing == false) {
				moves.addAll(getMovesUp());
				moves.addAll(getJumpsUp());
			} else {
				moves.addAll(getMovesUp());
				moves.addAll(getJumpsUp());
				moves.addAll(getMovesDown());
				moves.addAll(getJumpsDown());
			}
			break;
		}
		return moves;
	}
	
	/** Get the moves of a white piece 
	 * @return The moves 
	 * */
	public ArrayList<Moves> getMovesUp() {
		ArrayList<Moves> possibleMoves = new ArrayList<Moves>();
		/* Left side check */
		if (App.board.getPiece(x - 1, y - 1) == null && isLegal(x - 1, y - 1)) {
			Moves m = new Moves();
			m.newx = x - 1;
			m.newy = y - 1;
			m.originalx = x;
			m.originaly = y;
			m.piece = this;
			possibleMoves.add(m);

		}
		/* Right side check */
		if (App.board.getPiece(x + 1, y - 1) == null && isLegal(x + 1, y - 1)) {
			Moves m = new Moves();
			m.newx = x + 1;
			m.newy = y - 1;
			m.originalx = x;
			m.originaly = y;
			m.piece = this;
			possibleMoves.add(m);
		}
		return possibleMoves;
	}
	/** Get the jumps of a white piece 
	 * @return The moves 
	 * */
	public ArrayList<Moves> getJumpsUp() {
		/* Left side jump check */
		ArrayList<Moves> possibleMoves = new ArrayList<Moves>();
		if (App.board.getPiece(x - 1, y - 1) != null) {
			if (App.board.getPiece(x - 1, y - 1).getValue() != value && App.board.getPiece(x - 2, y - 2) == null
					&& isLegal(x - 2, y - 2)) {
				Moves m = new Moves();
				m.newx = x - 2;
				m.newy = y - 2;
				m.originalx = x;
				m.originaly = y;
				m.piece = this;
				m.killedPiece = App.board.getPiece(x - 1, y - 1);
				possibleMoves.add(m);
			}
		}
		/* Right side jump check */
		if (App.board.getPiece(x + 1, y - 1) != null) {
			if (App.board.getPiece(x + 1, y - 1).getValue() != value && App.board.getPiece(x + 2, y - 2) == null
					&& isLegal(x + 2, y - 2)) {
				Moves m = new Moves();
				m.newx = x + 2;
				m.newy = y - 2;
				m.originalx = x;
				m.originaly = y;
				m.piece = this;
				m.killedPiece = App.board.getPiece(x + 1, y - 1);
				possibleMoves.add(m);
			}
		}
		return possibleMoves;
	}
	/** Get the moves of a black piece 
	 * @return The moves 
	 * */
	public ArrayList<Moves> getMovesDown() {
		ArrayList<Moves> possibleMoves = new ArrayList<Moves>();
		/* Left side check */
		if (App.board.getPiece(x - 1, y + 1) == null && isLegal(x - 1, y + 1)) {
			Moves m = new Moves();
			m.newx = x - 1;
			m.newy = y + 1;
			m.originalx = x;
			m.originaly = y;
			m.piece = this;
			possibleMoves.add(m);
		}
		/* Right side check */
		if (App.board.getPiece(x + 1, y + 1) == null && isLegal(x + 1, y + 1)) {
			Moves m = new Moves();
			m.newx = x + 1;
			m.newy = y + 1;
			m.originalx = x;
			m.originaly = y;
			m.piece = this;
			possibleMoves.add(m);
		}
		return possibleMoves;
	}
	/** Get the jumps of a black piece 
	 * @return The moves 
	 * */
	public ArrayList<Moves> getJumpsDown() {
		/* Left side jump check */
		ArrayList<Moves> possibleMoves = new ArrayList<Moves>();
		if (isLegal(x - 1, y + 1) && App.board.getPiece(x - 1, y + 1) != null) {
			if (App.board.getPiece(x - 1, y + 1).getValue() != value && App.board.getPiece(x - 2, y + 2) == null
					&& isLegal(x - 2, y + 2)) {
				Moves m = new Moves();
				m.newx = x - 2;
				m.newy = y + 2;
				m.killedPiece = App.board.getPiece(x - 1, y + 1);
				m.originalx = x;
				m.originaly = y;
				m.piece = this;
				possibleMoves.add(m);
			}
		}
		/* Right side jump check */
		if (isLegal(x + 1, y + 1) && App.board.getPiece(x + 1, y + 1) != null) {
			if (App.board.getPiece(x + 1, y + 1).getValue() != value && App.board.getPiece(x + 2, y + 2) == null
					&& isLegal(x + 2, y + 2)) {

				Moves m = new Moves();
				m.newx = x + 2;
				m.newy = y + 2;
				m.killedPiece = App.board.getPiece(x + 1, y + 1);
				m.originalx = x;
				m.originaly = y;
				m.piece = this;
				possibleMoves.add(m);
			}
		}
		return possibleMoves;
	}
	/** Check if a coordinate is on the board
	 * @return True or false 
	 * */
	public boolean isLegal(int x, int y) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
			return true;
		}
		return false;
	}

}
