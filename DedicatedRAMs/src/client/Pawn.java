package client;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

	public Pawn(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2659" : "\u265F";
	}
	
	// Promotion in chess is a rule
	// that requires a pawn that reaches 
	// the eighth rank to be replaced by the player's choice of a 
	// bishop, knight, rook, or queen of the same color.
	// source: wikipedia
	public void promote (String playersChoice) {
		switch (playersChoice.toUpperCase()) {
		case "BISHOP":
			this.board.replacePiece(this.getPosition(), new Bishop(this.board, this.color));
			break;
		case "KNIGHT":
			this.board.replacePiece(this.getPosition(), new Knight(this.board, this.color));
			break;
		case "ROOK":
			this.board.replacePiece(this.getPosition(), new Rook(this.board, this.color));
			break;
		case "QUEEN":
			this.board.replacePiece(this.getPosition(), new Queen(this.board, this.color));
			break;
		}
	}

	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<>();
		
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		String move = movement.pawnPlusOne();
		if(move != null)
			moves.add(move);
		move = movement.pawnPlusTwo();
		if(move != null)
			moves.add(move);
		move = movement.enPassantMove();
		if(move != null)
			moves.add(move);
		moves.addAll(movement.pawnCapture());

		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}

		return moves;
	}
}
