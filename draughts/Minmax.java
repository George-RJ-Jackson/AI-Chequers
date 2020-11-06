package draughts;

import java.util.ArrayList;

public class Minmax {
	ArrayList<Moves> successorEvaluations;
	int difficulty;

	/** Start minmax evaluation **/
	public void start() {
		startEvaluation();
		Moves move = getBestMove();
		makeMove(move);
		App.board.switchTurn();
	}
	/** Evaluate the returned moves **/
	private Moves getBestMove() {
		int max = -1000;
		int best = -1;
		for (int i = 0; i < this.successorEvaluations.size(); i++) {
			if (max < ((Moves) this.successorEvaluations.get(i)).score) {
				max = ((Moves) this.successorEvaluations.get(i)).score;
				best = i;
			}
		}
		return this.successorEvaluations.get(best);
	}
	/** Start evaluation **/
	public void startEvaluation() {
		this.successorEvaluations = new ArrayList<>();
		minmax(0, 1, -1000, 1000);
	}
	/** Perform the move **/
	public void makeMove(Moves m) {
		Piece piece = App.board.getPiece(m.originalx, m.originaly);
		if (piece.isKing) {
			m.wasking = true;
		}
		if (m.killedPiece != null && m.killedPiece.isKing == true) {
			m.piece.isKing = true;
		}
		piece.setX(m.newx);
		piece.setY(m.newy);
		if (m.killedPiece != null) {
			App.board.pieces.remove(m.killedPiece);
		}		 

	}
	/** Get moves for the piece **/
	public ArrayList<Moves> getMoves(PieceValue value) {
		ArrayList<Moves> moves = new ArrayList<>();
		App.board.forcedMoves.clear();
		App.board.hasToTake(value);
		if (App.board.forcedMoves.isEmpty()) {
			for (Piece piece : App.board.pieces) {
				if (piece.getValue() == value) {
					piece.getMoves();
					for (Moves m : piece.moves) {
						moves.add(m);
					}
				}
			}
		} else {
			moves.addAll(App.board.forcedMoves);
		}
		return moves;
	}
	/** Minmax with Alpha-Beta pruning **/
	public int minmax(int depth, int player, int alpha, int beta) {
		int bestScore = 0;
		if (player == 0) {
			bestScore = -26;
		} else if (player == 1) {
			bestScore = 26;
		}
		if (App.board.hasWon(PieceValue.RED)) {
			return bestScore = 26;
		}
		if (App.board.hasWon(PieceValue.WHITE)) {
			return bestScore = -26;
		}
		if (depth == App.board.difficulty) {
			int redScore = 0;
			int whiteScore = 0;
			int kingRedScore = 0;
			int kingWhiteScore = 0;
			for (Piece p : App.board.pieces) {
				if (p.getValue() == PieceValue.RED) {
					redScore++;
					if (p.isKing) {
						kingRedScore++;
					}
				} else {
					whiteScore++;
					if (p.isKing) {
						kingWhiteScore++;
					}
				}
			}
			redScore = redScore + kingRedScore;
			whiteScore = whiteScore + kingWhiteScore;
			return redScore - whiteScore;
		}
		ArrayList<Moves> positions;
		if (player == 1) {
			positions = getMoves(PieceValue.RED);

		} else {
			positions = getMoves(PieceValue.WHITE);
		}
		for (Moves m : positions) {
			if (player == 1) {
				makeMove(m);
				int currentScore = minmax(depth + 1, 0, alpha, beta);
				if (currentScore > alpha)
					alpha = currentScore;
				if (currentScore > bestScore)
					bestScore = currentScore;
				if (depth == 0) {
					m.score = currentScore;
					this.successorEvaluations.add(m);
				}
			} else if (player == 0) {
				makeMove(m);
				int currentScore = minmax(depth + 1, 0, alpha, beta);
				if (currentScore < bestScore)
					bestScore = currentScore;
				if (currentScore < beta)
					beta = currentScore;
			}
			Piece piece = App.board.getPiece(m.newx, m.newy);
			piece.setX(m.originalx);
			piece.setY(m.originaly);
			if (m.killedPiece != null) {
				App.board.pieces.add(m.killedPiece);
			}
			if (m.wasking == false) {
				piece.isKing = false;
			}
		}
		return bestScore;
	}

}
