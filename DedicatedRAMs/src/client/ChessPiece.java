package client;

import client.Player.Color;

import javax.swing.*;
import java.util.ArrayList;

public abstract class ChessPiece {

	protected ChessBoard board;
	protected int row;
	protected int column;
	protected Color color;
	protected Vest vest = null;
	protected boolean hasMoved = false;
	protected ArrayList<Class<?>> vestTypes;

	public ChessPiece (ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
		this.vestTypes = new ArrayList<>();
	}

	/**
	 * Getter Method: returns the list of ChessPiece types a Piece can plunder
	 * @return ArrayList of ChessPiece classes corresponding with plunderable types. i.e. (knight, queen, etc).
	 */
	public ArrayList<Class<?>> getVestTypes() {
		return vestTypes;
	}
	
	public String getName() {
		String typeString = this.getClass().toString();
		return typeString.substring(typeString.lastIndexOf(".") + 1);
	}

	/**
	 * Getter Method: returns Color of piece.
	 * @return Color - Can be either BLACK, or WHITE.
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * Getter Method: Returns the string position of a piece depending on the int row/column that piece is in
	 * @return string position - i.e. "a1".
	 */
	public String getPosition() {
		char c = (char) ('a' + this.column);
		char r = (char)('1' + this.row);
		return c + "" + r;
	}

	/**
	 * @return returns hasMoved variable
	 */
	public boolean getHasMoved() {
		return this.hasMoved;
	}

	/**
	 * Getter Method: returns the Vest worn by piece
	 * @return returns the Vest - i.e. a Pawn could have captured a Knight and would obtain the Knight vest and moves
	 */
	public Vest getVest()
	{
		return this.vest;
	}

	/**
	 * Determines that a piece has moved. Should not be called to set it to false.
	 * Only called by ChessBoard move method after the piece has been moved.
	 * @param hasMoved - boolean stating the piece has moved
	 */
	public void setHasMoved(Boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * Setter Method: Given a string position this method sets the int row/column for the piece to the that position
	 *
	 * If the piece has a vest it will change the vests position as well
	 *
	 * @param position - 2 character string - i.e. "a2"
	 * @throws IllegalPositionException - if given an incorrect string - i.e. "hi"
	 */
	public void setPosition(String position) throws IllegalPositionException {
		if(!board.isPositionOnBoard(position))
			throw new IllegalPositionException("Tried to set ChessPiece position off of the board.");
		this.row = position.charAt(1) - '1';
		this.column = position.charAt(0) - 'a';
		
		// if the vest exists set the position to the same position as the parent piece
		if(this.vest != null)
		{
			this.vest.setVestPosition(position);
		}
	}

	/**
	 * Setter Method: sets the vest of piece to a type
	 * @param type - the Class of ChessPiece that the current piece has plundered
	 * @throws IllegalPositionException - because it calls getPosition()
	 */
	public void setVest(ChessPiece type) throws IllegalPositionException
	{
		if(type instanceof Pawn) {
			Pawn p = new Pawn(this.board, this.getColor());
			this.vest = new Vest(p);
		} else {
			this.vest = type != null ? new Vest(type) : null;
		}

		// if the vest exists set the position to the same position as the parent piece
		if(this.vest != null)
			this.vest.setVestPosition(this.getPosition());
	}

	/**
	 * Helper Method: Checks to make sure that a square is empty or doesn't contain a piece of the same color.
	 *
	 * This method is invoked in PieceMovement class by pawnCapture(), kingCircleOfMoves(), performMoveAddition(),
	 * and knightJumpMoves().
	 *
	 * @param position - the position of the square in question
	 * @return - true if the position null or an enemy piece, and false otherwise.
	 */
	public boolean isPositionTakable(String position) {
		try {
			ChessPiece piece = board.getPiece(position);
			if(piece != null && piece.color.equals(this.color))
				return false;
		} catch (IllegalPositionException e) {
			return false;
		}
		return true;
	}

	public boolean moveIsLegal(String position, boolean includeVest) {
		return this.legalMoves(includeVest, true).contains(position);
	}

	/**
	 * Helper Method: Iterates through a list of legalMoves for a chessPiece and simulates moves to determine if that
	 * move would cause check incidentally. If the move does cause check it is removed from the list of legal moves.
	 *
	 * Since this method is actually moving the pieces the remaining code is used to return pieces to their original
	 * spaces after the simulated move.
	 *
	 * This method calls -- ChessBoard isCheck() -- to determine if the move causes a check.
	 *
	 * @param legalMoves - array of the potential legal moves
	 * @return array of legal moves without any move that might cause check
	 */
	public ArrayList<String> illegalMovesDueToCheck(ArrayList<String> legalMoves) {
		Vest currentVest = this.vest;
		ArrayList<String> newLegalMoves = new ArrayList<>(legalMoves);
		for(String newPos: legalMoves) {
			String currentPos = this.getPosition();
			board.simulateMove(this, currentPos, newPos);

			if(board.isCheck(this.color)) {
				newLegalMoves.remove(newPos);
			}

			board.simulateMove(this, newPos, currentPos);						//Returns the piece back to its position if it did not check
			board.getHistory().removeEnd();

			if(board.getHistory().getLastMove() != null && board.getHistory().getLastMove().getCaptured() != null) {
				board.placePiece(board.getHistory().getLastMove().getCaptured(), newPos, false);	// If the simulated move captures a piece return that piece to the board.
			}

			this.vest = currentVest;
			board.getHistory().removeEnd();
		}
		return newLegalMoves;
	}

	/**
	 * Helper Method - for testing purposes mainly, this returns the unicode character of the given piece.
	 * @return - returns unicode character depending on ChessPiece
	 */
	abstract public String toString();

	/**
	 * Determines the legal moves of a ChessPiece which is obtained by calling PieceMovement methods to determine the
	 * legal moves depending on the Piece and any special moves that piece might because of plundering.
	 * @param includeVest - boolean - true means to include plundered moves, false will not include that movement.
	 * @param turn - boolean - this is used for determining illegalMovesDueToCheck().
	 * @return list of String positions that are legal moves.
	 */
	abstract public ArrayList<String> legalMoves(boolean includeVest, boolean turn);

	/**
	 * UI method - to get the image for the given piece.
	 * @return - Image for the given ChessPiece.
	 */
	abstract public ImageIcon toImage();
	
}