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
	
	public ArrayList<String> knightJumpMove(){
		ArrayList<String> moves = new ArrayList<String>();
	    int[][] moveSet = { {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2} };
	    for (int[] move : moveSet) {
	        String to = changePosition(piece.getPosition(), move[0], move[1]);
	        if(piece.isPositionTakable(to)) {
	        	moves.add(to);
			}
		}
	    return moves;
	}

	public ArrayList<String> longRangeMoves(String type){
		ArrayList<String> moves = new ArrayList<String>();
		int colA = 1, colB = 0, rowA = 1, rowB = 0;
		if (type.equals("Diagonal")) {
			colB = 1;
			rowB = 1;
		}
		boolean c1 = true, c2 = true, r1 = true, r2 = true;
		while (c1 || c2 || r1 || r2) {
			if (c1)
				c1 = performMoveAddition(moves, changePosition(piece.getPosition(), -rowB, colA));
			if (c2)
				c2 = performMoveAddition(moves, changePosition(piece.getPosition(), rowB, -colA));
			if (r1)
				r1 = performMoveAddition(moves, changePosition(piece.getPosition(), rowA, colB));
			if (r2)
				r2 = performMoveAddition(moves, changePosition(piece.getPosition(), - rowA, -colB));
			colA++;
			rowA++;
			if (type.equals("Diagonal")) {
				colB++;
				rowB++;
			}
		}
		return moves;
	}
	
	public String enPassantMove() {
		if(history.getMoveHistory().size() == 0)
			return null;
		Move lastMove = history.getMoveHistory().get(history.getMoveHistory().size() - 1);
		boolean isPawn = lastMove.getPieceMoved().getClass() == Pawn.class;
		boolean oppositeColor = piece.color != lastMove.getPieceMoved().color;
		boolean isDoubleMove = Math.abs(lastMove.getFrom().charAt(1) - lastMove.getTo().charAt(1)) == 2; 
		if(isPawn && oppositeColor && isDoubleMove) {
			if(Math.abs(lastMove.getTo().charAt(0)-piece.getPosition().charAt(0)) == 1) {
				return changePosition(lastMove.getTo(),adjustForColor(piece.color, 1), 0);
			}
		}
		return null;
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
	
	private boolean performMoveAddition(ArrayList<String> moves, String position) {
		if(piece.isPositionTakable(position)) {
			moves.add(position);
			try {
				if(board.getPiece(position) != null && board.getPiece(position).color != piece.color)
					return false;
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
}
