package client;

import client.ChessPiece.Color;

import java.util.ArrayList;

public class ChessBoard {

	private ChessPiece[][] board;

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
		if(piece instanceof Pawn) {
			// TODO: Note that pawn just gets promoted to QUEEN right now
			// However, it can also get promoted to Bishop, Knight, and Rook,
			// depending on the user.
			((Pawn) piece).promote("QUEEN");
		}
		return true;
	}

	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}
	
	public boolean replacePiece(String position, ChessPiece newPiece) {
		
		try {
			if(getPiece(position) != null) {
				int row = position.charAt(1) - '1';
				int col = position.charAt(0) - 'a';
				this.board[row][col] = newPiece;
				this.board[row][col].setPosition(position);
				return true;
			} else {
				return false;
			}
		} catch (IllegalPositionException e) {
			return false;
		}
		
	}

	public void move(String from, String to) throws Exception {
		if(this.getPiece(from) == null) {
			throw new IllegalMoveException();
		}
		ArrayList<String> possible_moves = this.getPiece(from).legalMoves();
		if(possible_moves.size() > 0) {
			if(possible_moves.contains(to)) {
				if(placePiece(getPiece(from), to)) {
					removePiece(from);
				}
			} else {
				throw new IllegalMoveException();
			}
		} else {
			throw new IllegalMoveException();
		}
	}

	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h' && position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}

}
