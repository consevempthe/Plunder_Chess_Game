package client;

import java.util.ArrayList;

import client.ChessPiece.Color;

public class PieceMovement {

	MoveHistory history;
	ChessBoard board;
	ChessPiece piece;
	
	public PieceMovement(MoveHistory history, ChessBoard board, ChessPiece piece) {
		this.history = history;
		this.board = board;
		this.piece = piece;
	}
	
	public String pawnPlusOne() {
		String to = changePosition(piece.getPosition(), adjustForColor(piece.color, 1), 0);
		try {
			if(board.getPiece(to) == null) {
				return to;
			}
		} catch (IllegalPositionException e) {
			return null;
		}
		return null;
	}
	
	public String pawnPlusTwo(){
		String to = changePosition(piece.getPosition(), adjustForColor(piece.color, 2), 0);
		boolean isStart = isPawnStartMove();
		try {
			if(isStart && pawnPlusOne() != null && board.getPiece(to) == null)
				return to;
			
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> pawnCapture(){
		ArrayList<String> moves = new ArrayList<String>();
		for(int i = -1; i < 2; i++) {
			String to = changePosition(piece.getPosition(), adjustForColor(piece.color, 1), i);  
			try {
				if(i != 0 && piece.isPositionTakable(to) && board.getPiece(to) != null)
					moves.add(to);
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}
		}
		return moves;
	}

	public ArrayList<String> kingCircleOfMoves(){
		ArrayList<String> moves = new ArrayList<String>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(i != 0 || j != 0) {
					String to = changePosition(piece.getPosition(), i, j);
					if(piece.isPositionTakable(to)) {
						moves.add(to);
					}
				}
			}
		}
		return moves;
	}

	private String changePosition(String position, int row, int col) {
		char c = position.charAt(0);
		char r = position.charAt(1);
		return (char)(c + col) + "" + (char)(r+row);
	}
	
	private int adjustForColor(Color pieceColor, int numToAdjust) {
		if(pieceColor == Color.BLACK)
			return -numToAdjust;
		else 
			return numToAdjust;
	}
	
	private boolean isPawnStartMove() {
		if((piece.color.equals(Color.WHITE) && piece.row == 1) || (piece.color.equals(Color.BLACK) && piece.row == 6))
			return true;
		return false;
	}
	
}
