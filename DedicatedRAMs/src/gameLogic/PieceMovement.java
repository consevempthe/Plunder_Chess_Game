package gameLogic;

import gameLogic.Player.Color;
import exceptions.*;

import java.util.ArrayList;

public class PieceMovement {

	private ChessBoard board;
	private ChessPiece piece;

	public PieceMovement(ChessBoard board, ChessPiece piece) {
		this.board = board;
		this.piece = piece;
	}

	/**
	 * Standard Pawn Move: single horizontal movement, which calls adjustForColor(Color, int) depending on the color
	 * of the piece (i.e. moving up or down the board).
	 * @return String with move. ("a1", "b2", etc.)
	 */
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

	/**
	 * Special Pawn Move: Double horizontal movement, which is dependent on if the pawn has not moved yet. Is also
	 * adjusted for Color
	 * @return String with the move. ("a1", "b2", etc.)
	 */
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

	/**
	 * Standard Pawn Capture: Pawn captures diagonally forward one square to the left or right.
	 * @return ArrayList of Strings with move. Ex. {"a3", "b3"}
	 *
	 */
	public ArrayList<String> pawnCapture(){
		ArrayList<String> moves = new ArrayList<>();
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

	/**
	 * Special Pawn Capture: Only occurs immediately after a pawn makes a move of two squares from its starting square,
	 * and it could have been captured by an enemy pawn if it had moved one square.
	 *
	 * Method finds the last move in the move history and determines if the piece moved is a pawn of the opposite
	 * color that has moved twice.
	 * @return String with the move. ("a1", "b2", etc.)
	 */
	public String enPassantMove() {
		if(board.getMoveHistorySize() == 0)
			return null;

		Move lastMove = board.getLastMoveInHistory();

		boolean isPawn = lastMove.getPieceMovedClass() == Pawn.class;
		boolean oppositeColor = piece.color != lastMove.getPieceMovedColor();
		boolean isDoubleMove = Math.abs(lastMove.getCurrentPos().charAt(1) - lastMove.getNewPos().charAt(1)) == 2;

		if(isPawn && oppositeColor && isDoubleMove) {
			if(lastMove.getNewPos().charAt(1) == piece.getPosition().charAt(1)
				&& (Math.abs(lastMove.getNewPos().charAt(0) - piece.getPosition().charAt(0)) == 1 )) {
				return changePosition(lastMove.getNewPos(), adjustForColor(piece.color, 1), 0);
			}
		}
		return null;
	}

	/**
	 * King moves: King can move one space in any direction
	 * @return ArrayList of Strings with possible moves.
	 */
	public ArrayList<String> kingCircleOfMoves(){
		ArrayList<String> moves = new ArrayList<>();
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

	/**
	 * Special King Move: King castles with either Rook as long as neither of the pieces have moved and there are
	 * no pieces in between them
	 * @return ArrayList of Strings with the king's desired movement. i.e. "c1" is the space the king will end up
	 * @throws IllegalPositionException - if a piece isn't on the board etc.
	 */
	public ArrayList<String> kingCastles() throws IllegalPositionException {
		ArrayList<String> moves = new ArrayList<>();

		String kingPos = piece.getPosition();
		ChessPiece rook_A = null;
		ChessPiece rook_H = null;

		if(board.isPositionOnBoard(changePosition(kingPos, 0, -4)))
			rook_A = board.getPiece(changePosition(kingPos, 0, -4));
		if(board.isPositionOnBoard(changePosition(kingPos, 0, 3)))
			rook_H = board.getPiece(changePosition(kingPos, 0, 3));

		if(rook_A != null && rook_H != null) {
			if(rook_A instanceof Rook && !rook_A.hasMoved && rook_A.color.equals(piece.color)) {
				boolean emptySpaces = false;
				for(int i = 1; i < 4; i++) {
					String squarePos = changePosition(kingPos, 0, -i);
					emptySpaces = checkEmpty(squarePos);
				}
				if(emptySpaces) {
					String castle_left = changePosition(kingPos, 0, -2);
					moves.add(castle_left);
				}
			}

			if(rook_H instanceof Rook && !rook_H.hasMoved && rook_H.color.equals(piece.color)) {
				boolean empty = false;
				for(int i = 1; i < 3; i++) {
					String squarePos = changePosition(kingPos, 0, i);
					empty = checkEmpty(squarePos);
				}

				if(empty) {
					String castle_right = changePosition(kingPos, 0, 2);
					moves.add(castle_right);
				}
			}
		}

		return moves;
	}

	/**
	 * Knight moves: Knight can move 2 spaces in one direction and 1 space to the left of or right of that direction.
	 * @return ArrayList of Strings with possible moves.
	 */
	public ArrayList<String> knightJumpMove(){
		ArrayList<String> moves = new ArrayList<>();
	    int[][] moveSet = { {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2} };
	    for (int[] move : moveSet) {
	        String to = changePosition(piece.getPosition(), move[0], move[1]);
	        if(piece.isPositionTakable(to)) {
	        	moves.add(to);
			}
		}
	    return moves;
	}

	/**
	 * Moves for Queen, Rook, Bishop: The Queen can move horizontally and diagonally, while the rook can move horizontally
	 * and the bishop can move diagonally.
	 * @param type - can be either horizontal or diagonal, which will be called depending on the type of piece.
	 * @return ArrayList of String with possible moves.
	 */
	public ArrayList<String> longRangeMoves(String type){
		ArrayList<String> moves = new ArrayList<>();
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

	/**
	 * Helper Method (general): returns a new position in order to find new moves.
	 * @param position - the starting string position of the piece. i.e. "a2"
	 * @param row - the row of the new move
	 * @param col - the column of the new move
	 * @return String with new position.
	 */
	private String changePosition(String position, int row, int col) {
		char c = position.charAt(0);
		char r = position.charAt(1);
		return (char)(c + col) + "" + (char)(r+row);
	}

	/**
	 * Helper Method for pawn movement: returns a negative or positive number to aid in pawn movement depending on
	 * color. If the Color is black the number is negated, otherwise it doesn't change.
	 * @param pieceColor - Color of the piece
	 * @param numToAdjust - number being adjusted
	 * @return int adjusted number.
	 */
	private int adjustForColor(Color pieceColor, int numToAdjust) {
		if(pieceColor == Color.BLACK)
			return -numToAdjust;
		else 
			return numToAdjust;
	}

	/**
	 * Helper Method - for pawn movement: determines if a pawn is in its starting position for double moves.
	 * @return boolean - true if pawn hasn't moved, false otherwise.
	 */
	private boolean isPawnStartMove() {
		return (piece.color.equals(Color.WHITE) && piece.row == 1) || (piece.color.equals(Color.BLACK) && piece.row == 6);
	}

	/**
	 * HelperMethod for longRangeMoves(): determines if the move should be added to moves list.
	 * @param moves - the list of moves
	 * @param position - position to be added.
	 * @return - true if the move was added, false otherwise.
	 */
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

	/**
	 * Helper Method for kingCastles(): checks if a space is empty
	 * @param position - String position that is being checked.
	 * @return boolean - true if its empty, false if it isn't
	 * @throws IllegalPositionException - if the position isn't legal
	 */
	private boolean checkEmpty(String position) throws IllegalPositionException {
		return board.getPiece(position) == null;
	}
	
}
