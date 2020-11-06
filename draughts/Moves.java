package draughts;

public class Moves {
	/* Piece being moved */
	Piece piece;
	/* Piece killed */
	Piece killedPiece;	
	/* Original position */
	int originalx;
	int originaly;
	/* New position */
	int newx;
	int newy;
	/* Move score */
	int score;
	/* Was piece a king */
	boolean wasking = false;
}
