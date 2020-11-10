package gameLogic;

import gameLogic.Player.Color;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bishop extends ChessPiece {

	public Bishop(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Rook.class);
		this.vestTypes.add(Pawn.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Knight.class);
	}
	
	@Override
	public ImageIcon toImage() {
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whiteBishop.png"))
				: new ImageIcon(getClass().getResource("/images/blackBishop.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		PieceMovement movement = new PieceMovement(board, this);
		ArrayList<String> moves = new ArrayList<>(movement.longRangeMoves("Diagonal"));
		// include the vest moves if it exists
		if (includeVest && this.vest != null) {
			System.out.println("i can plunder");
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}
		if(turn) {
			moves = illegalMovesDueToCheck(moves);

		}

		return moves;
	}

}
