package client;

import client.ChessPiece.Color;

public class ChessBoard {

	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();

	public ChessBoard() {
		board = new ChessPiece[8][8];
	}

	public void initialize() {
		for(int i = 0; i < 8; i++) {
			placePiece(new Pawn(this, Color.WHITE), (char) ('a' + i) + "" + (char)('1' + 1));
			placePiece(new Pawn(this, Color.BLACK), (char) ('a' + i) + "" + (char)('1' + 6));
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
		if(!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		return board[i2][i1];
	}

	public boolean placePiece(ChessPiece piece, String position) {
		try {
			if(getPiece(position) != null && getPiece(position).getColor().equals(piece.getColor()))
				return false;
			piece.setPosition(position);
		} catch (IllegalPositionException e) {
			return false;
		}
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		board[i2][i1] = piece;
		return true;
	}

	public void move(String from, String to) throws IllegalMoveException {
		ChessPiece pieceToMove = null;
		try {
			pieceToMove = getPiece(from);
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException();
		}
		if(pieceToMove != null && pieceToMove.legalMoves().contains(to)) {
			placePiece(pieceToMove, to);
			removePiece(from);
			history.addMoveToMoveHistory(new Move(pieceToMove, from, to));
		}
		else
			throw new IllegalMoveException();
	}
	
	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}

	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h' && position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}

}
