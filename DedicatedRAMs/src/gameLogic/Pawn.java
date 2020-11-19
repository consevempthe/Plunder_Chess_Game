package gameLogic;

import gameLogic.Player.Color;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

	private ImageIcon icon;
	private String enPassant = "";

	public Pawn(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Rook.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Knight.class);
		this.setInitialImage();
	}
	
	private void setInitialImage() {
		icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whitePawn.png"))
				: new ImageIcon(getClass().getResource("/images/blackPawn.png"));
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(50, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		
		this.icon = new ImageIcon(newimg);
	}

	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2659" : "\u265F";
	}

	@Override
	public ImageIcon toImage() {
		return this.icon;
	}

	/**
	 * Promotion for Pawn - where if a pawn reaches the end of the board the player can upgrade the piece
	 * @param playersChoice - the String containing what piece the player wants.
	 */
	public void promote(String playersChoice) {
		switch (playersChoice.toUpperCase()) {
		case "BISHOP":
			this.board.replacePiece(new Bishop(this.board, this.color), this.getPosition());
			this.icon = (new Bishop(null, this.color)).toImage(); // set new image to Bishop before it gets rendered.
			break;
		case "KNIGHT":
			this.board.replacePiece(new Knight(this.board, this.color), this.getPosition());
			this.icon = (new Knight(null, this.color)).toImage();
			break;
		case "ROOK":
			this.board.replacePiece(new Rook(this.board, this.color), this.getPosition());
			this.icon = (new Rook(null, this.color)).toImage();
			break;
		case "QUEEN": // nothing here, falls through.
		default:
			this.board.replacePiece(new Queen(this.board, this.color), this.getPosition());
			this.icon = (new Queen(null, this.color)).toImage();
			break;
		}
	}

	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		ArrayList<String> moves = new ArrayList<>();
		PieceMovement movement = new PieceMovement(board, this);

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
			setEnPassant(move);
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

	/**
	 * @param pos - Position of enPassant move
	 */
	public void setEnPassant(String pos) {
		this.enPassant = pos;
	}

	/**
	 * @return - EnPassant move
	 */
	public String getEnPassant() {
		return this.enPassant;
	}

	/**
	 * @return - true if the Pawn can enPassant
	 */
	public boolean hasEnPassant() {
		return !this.enPassant.isEmpty();
	}

}
