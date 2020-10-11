package client;

import java.util.ArrayList;

import client.ChessPiece.Color;

public class ChessBoard {

	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();

	public ChessBoard() {
		board = new ChessPiece[8][8];
	}

	public void initialize() {
		for (int i = 0; i < 8; i++) {
			placePiece(new Pawn(this, Color.WHITE), (char) ('a' + i) + "" + (char) ('1' + 1));
			placePiece(new Pawn(this, Color.BLACK), (char) ('a' + i) + "" + (char) ('1' + 6));
		}
		placePiece(new Rook(this, Color.WHITE), "a1");
		placePiece(new Rook(this, Color.BLACK), "a8");
		placePiece(new Rook(this, Color.WHITE), "h1");
		placePiece(new Rook(this, Color.BLACK), "h8");
		placePiece(new Knight(this, Color.WHITE), "b1");
		placePiece(new Knight(this, Color.BLACK), "b8");
		placePiece(new Knight(this, Color.WHITE), "g1");
		placePiece(new Knight(this, Color.BLACK), "g8");
		placePiece(new Bishop(this, Color.WHITE), "c1");
		placePiece(new Bishop(this, Color.BLACK), "c8");
		placePiece(new Bishop(this, Color.WHITE), "f1");
		placePiece(new Bishop(this, Color.BLACK), "f8");
		placePiece(new Queen(this, Color.WHITE), "d1");
		placePiece(new Queen(this, Color.BLACK), "d8");
		placePiece(new King(this, Color.WHITE), "e1");
		placePiece(new King(this, Color.BLACK), "e8");
	}

	public MoveHistory getHistory() {
		return history;
	}

	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		return board[i2][i1];
	}

	public boolean placePiece(ChessPiece piece, String position) {
		try {
			if (getPiece(position) != null && getPiece(position).getColor().equals(piece.getColor()))
				return false;
			else if (getPiece(position) != null && !getPiece(position).getColor().equals(piece.getColor()))
				this.captureAndReplace(piece, position); // capture the piece does this need to be added to some sort of
															// list
			piece.setPosition(position);
		} catch (IllegalPositionException e) {
			return false;
		}
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		board[i2][i1] = piece;
		return true;
	}

	public void move(String from, String to) throws IllegalMoveException, IllegalPositionException {
		ChessPiece pieceToMove = null;
		try {
			pieceToMove = getPiece(from);
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException();
		}
		if (pieceToMove != null && pieceToMove.legalMoves(true).contains(to)) {
			// TODO add a scanner to ask if the user wants to use the vest as part of the
			// move
			if (pieceToMove.getVest() != null) {
				// if the move is in vest and not the parent piece it's a vest move
				if (pieceToMove.getVest().getPiece().legalMoves(false).contains(to)
						&& !pieceToMove.legalMoves(false).contains(to)) {
					pieceToMove.setVest(null);
				}
			}

			placePiece(pieceToMove, to);
			removePiece(from);

			history.addMoveToMoveHistory(new Move(pieceToMove, from, to));
			tryPawnPromote(to);
		} else
			throw new IllegalMoveException();
	}

	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}

	public void replacePiece(ChessPiece newPiece, String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		this.board[row][col] = null;
		placePiece(newPiece, position);
	}

	public void captureAndReplace(ChessPiece piece, String position) throws IllegalPositionException {
		// TODO check if the player wants to plunder and set the vest
		if (true) // swap this to a scanner that asks if they want to plunder
		{
			ChessPiece vest = this.getPiece(position);
			piece.setVest(vest);
		}

		this.replacePiece(piece, position);
	}

	// this function is called by move();
	private void tryPawnPromote(String position) {

		// TODO: Note that pawn just gets promoted to QUEEN right now
		// However, it can also get promoted to Bishop, Knight, and Rook,
		// depending on the user.
		ChessPiece piece = null;
		try {
			piece = getPiece(position);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		if (piece instanceof Pawn && ((position.charAt(1) == '1' && piece.color == Color.BLACK)
				|| (position.charAt(1) == '8' && piece.color == Color.WHITE))) {
			Pawn pawn = (Pawn) piece;
			pawn.promote("QUEEN");
		}

	}

	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h'
				&& position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}

	// This method is just for testing, remove when UI is implemented
	public String toString() {
		String chess = "";
		String upperLeft = "\u250C";
		String upperRight = "\u2510";
		String horizontalLine = "\u2500";
		String horizontal3 = horizontalLine + "\u3000" + horizontalLine;
		String verticalLine = "\u2502";
		String upperT = "\u252C";
		String bottomLeft = "\u2514";
		String bottomRight = "\u2518";
		String bottomT = "\u2534";
		String plus = "\u253C";
		String leftT = "\u251C";
		String rightT = "\u2524";

		String topLine = upperLeft;
		for (int i = 0; i < 7; i++) {
			topLine += horizontal3 + upperT;
		}
		topLine += horizontal3 + upperRight;

		String bottomLine = bottomLeft;
		for (int i = 0; i < 7; i++) {
			bottomLine += horizontal3 + bottomT;
		}
		bottomLine += horizontal3 + bottomRight;
		chess += topLine + "\n";

		for (int row = 7; row >= 0; row--) {
			String midLine = "";
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == null) {
					midLine += verticalLine + " \u3000 ";
				} else {
					midLine += verticalLine + " " + board[row][col] + " ";
				}
			}
			midLine += verticalLine;
			String midLine2 = leftT;
			for (int i = 0; i < 7; i++) {
				midLine2 += horizontal3 + plus;
			}
			midLine2 += horizontal3 + rightT;
			chess += midLine + "\n";
			if (row >= 1)
				chess += midLine2 + "\n";
		}

		chess += bottomLine;
		return chess;
	}

}
