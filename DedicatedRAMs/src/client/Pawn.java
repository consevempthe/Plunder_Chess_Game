package client;

import client.Player.Color;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

	public Pawn(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Rook.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Knight.class);
	}

	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2659" : "\u265F";
	}

	@Override
	public ImageIcon toImage() {
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whitePawn.png"))
				: new ImageIcon(getClass().getResource("/images/blackPawn.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(50, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newimg);
	}

	// Promotion in chess is a rule
	// that requires a pawn that reaches
	// the eighth rank to be replaced by the player's choice of a
	// bishop, knight, rook, or queen of the same color.
	// source: wikipedia
	public void promote(String playersChoice) {
		switch (playersChoice.toUpperCase()) {
		case "BISHOP":
			this.board.replacePiece(new Bishop(this.board, this.color), this.getPosition());
			break;
		case "KNIGHT":
			this.board.replacePiece(new Knight(this.board, this.color), this.getPosition());
			break;
		case "ROOK":
			this.board.replacePiece(new Rook(this.board, this.color), this.getPosition());
			break;
			default:
			this.board.replacePiece(new Queen(this.board, this.color), this.getPosition());
			break;
		}
	}

	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		ArrayList<String> moves = new ArrayList<>();

		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		String move = movement.pawnPlusOne();
		if (move != null) {
			moves.add(move);
		}
		move = movement.pawnPlusTwo();
		if (move != null) {
			moves.add(move);

		}
		move = movement.enPassantMove();
		if (move != null) {
			moves.add(move);
		}
		moves.addAll(movement.pawnCapture());

		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}
		if (turn) {
			moves = illegalMovesDueToCheck(moves);
		}

		return moves;
	}

}
