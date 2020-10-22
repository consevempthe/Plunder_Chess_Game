package client;

import client.ChessPiece.Color;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.InputStream;

public class ChessBoard {

	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();
	private King whiteKing = new King(this, Color.WHITE);
	private King blackKing = new King(this, Color.BLACK);
	private boolean turnWhite = true;

	private Scanner sc;

	public ChessBoard(InputStream inputStream) {
		board = new ChessPiece[8][8];
		this.sc = new Scanner(inputStream);
	}

	 /**
	 * Sets up the pieces of the board.
	 */
	public void initialize() {
		for (int i = 0; i < 8; i++) {
			placePiece(new Pawn(this, Color.WHITE), (char) ('a' + i) + "" + (char) ('1' + 1));
			placePiece(new Pawn(this, Color.BLACK), (char) ('a' + i) + "" + (char) ('1' + 6));
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
		placePiece(whiteKing, "e1");
		placePiece(blackKing, "e8");
	}
	
	/**
	 * getPieces() gets the board pieces
	 * 
	 * @return a two dimensional array of pieces
	 */
	public ChessPiece[][] getPieces()
	{
		return this.board;
	}


	/**
	 * Getter Method: Takes a string and converts it to a 0-based int and returns the ChessPiece at that location on the
	 * chessboard.
	 *
	 * This Method is called in the following classes:
	 * ChessBoard by placePiece(), move(), castleMove(), capture(), tryPawnPromote()
	 * ChessPiece by isPositionTakable()
	 * PieceMovement by pawnPlusOne(), pawnPlusTwo(), pawnCapture(), kingCastles(), performMoveAddition(), checkEmpty()
	 *
	 * @param position - string where index 0 is char a-h and index 1 is number 1-8
	 * @return The ChessPiece at the given position on the ChessBoard.
	 * @throws IllegalPositionException - if the string position is not in that character range and therefore not on
	 * the board.
	 */
	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		return board[i2][i1];
	}

	/**
	 * Setter Method: This method is used to place a ChessPiece onto the board and is used to place pieces when the
	 * board is initialized and when a piece is being moved.
	 *
	 * This method calls capture()
	 *
	 * The ChessPiece class uses this method to return a simulated captured ChessPiece back to the board when it
	 * is trying to determine if there is illegal movement due to Check.
	 *
	 * @param piece - the given ChessPiece object that is going to be placed
	 * @param position - the String position for where to place the piece
	 * @return - true on success, false otherwise
	 */
	public boolean placePiece(ChessPiece piece, String position) {
		try {
			if (getPiece(position) != null && getPiece(position).getColor().equals(piece.getColor()))
				return false;
			else if (getPiece(position) != null && !getPiece(position).getColor().equals(piece.getColor())) {
				history.setCapturedPieceInMove(getPiece(position));
				capture(piece, position);
			}

			piece.setPosition(position);
		} catch (IllegalPositionException e) {
			return false;
		}
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		board[i2][i1] = piece;

		return true;
	}

	/**
	 * Method to move pieces: Given two positions move the piece from its currentPos to the newPos.
	 *
	 * This method checks to see if the move is a castle and subsequently calls castleMove(), otherwise it will ask
	 * for the players input on whether they want to use a vested move.
	 *
	 * The method adds the move to the ChessBoard's move history, places the ChessPiece, sets the ChessPiece to hasMoved
	 * and the removes that piece from its current position and then attempts to promote that piece if it is a pawn.
	 *
	 * @param currentPos - the current position of the piece being moved
	 * @param newPos - the new position that the piece is being moved to
	 * @throws IllegalMoveException - if the newPos is not a legal move
	 * @throws IllegalPositionException - if the currentPos was not a legal position
	 */
	public void move(String currentPos, String newPos) throws IllegalMoveException, IllegalPositionException {
		ChessPiece pieceToMove;
		pieceToMove = getPiece(currentPos);

		boolean isIncorrectColor = (turnWhite && pieceToMove.getColor() == Color.BLACK)
				|| (!turnWhite && pieceToMove.getColor() == Color.WHITE);
		if (isIncorrectColor)
			throw new IllegalMoveException();

		if (pieceToMove != null) {
			boolean moveIsLegal = pieceToMove.legalMoves(true, true).contains(newPos);

			if (moveIsLegal && pieceToMove instanceof King && !pieceToMove.hasMoved
					&& (newPos.equals("c1") || newPos.equals("g1") || newPos.equals("g8") || newPos.equals("c8"))) {

				castleMove(pieceToMove, newPos);

			} else if (moveIsLegal) {
				if (pieceToMove.getVest() != null) {
					System.out.println("Use Vest for this move? (y/n)");
					if (sc.nextLine().equals("y")) {
						// if the move is in vest and not the parent piece it's a vest move
						if (pieceToMove.getVest().getType().legalMoves(false, true).contains(newPos)) {
							pieceToMove.setVest(null);
						} else {
							System.out.print("Invalid move for vest, regular move applied.");
						}
					}
				}

				history.addMoveToMoveHistory(new Move(pieceToMove, currentPos, newPos, null));
				placePiece(pieceToMove, newPos);
				pieceToMove.setHasMoved(true);
				removePiece(currentPos);
				tryPawnPromote(newPos);

			} else {
				throw new IllegalMoveException();
			}
		} else {
			throw new IllegalMoveException();
		}

	}
	
	public boolean getTurnWhite() {
		return this.turnWhite;
	}

	/**
	 * Helper Method for Move: This method is only called when piece being move is a
	 * King that hasn't moved and it is making a castling move determined by the
	 * piece it is going to, which is either c1, c8, g1, g8.
	 *
	 * By this point in time we know based on the King's legalMoves() that there are
	 * no spaces in between the King and Rook and that the Rook is in the position
	 * it needs to be.
	 *
	 * This method determines the Rook position based on the King position and then
	 * moves both of those pieces to the position that will go to.
	 *
	 * @param pieceToMove - Is a King ChessPiece Object
	 * @param newPos      - The Castling move
	 * @throws IllegalPositionException - since it calls getPosition it must throw
	 *                                  an illegal position.
	 */
	public void castleMove(ChessPiece pieceToMove, String newPos) throws IllegalPositionException {
		String kingPosition = pieceToMove.getPosition();
		String rookPosition = "";
		String newRookPos = "";

		if (newPos.equals("c1") || newPos.equals("c8")) {
			rookPosition = (char) (-4 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char) (-1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
		} else if (newPos.equals("g1") || newPos.equals("g8")) {
			rookPosition = (char) (3 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char) (1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
		}

		history.addMoveToMoveHistory(new Move(pieceToMove, kingPosition, newPos, null));
		placePiece(pieceToMove, newPos);
		removePiece(kingPosition);
		pieceToMove.setHasMoved(true);

		ChessPiece rook = this.getPiece(rookPosition);
		history.addMoveToMoveHistory(new Move(rook, rookPosition, newRookPos, null));
		placePiece(rook, newRookPos);
		removePiece(rookPosition);
		rook.setHasMoved(true);
	}

	public void simulateMove(ChessPiece pieceToMove, String from, String to) {
		history.addMoveToMoveHistory(new Move(pieceToMove, from, to, null));
		placePiece(pieceToMove, to);
		removePiece(from);
	}

	/**
	 * Helper Method: Removes a piece from the board - either because it was simulated for Check, or because move()
	 * was called and successful.
	 *
	 * @param position - String position of the ChessPiece to be removed.
	 */
	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}

	/**
	 * Helper Method : Replaces a piece on the board
	 *
	 * This method is called by capture() and called in the Pawn class in order to upgrade a piece.
	 *
	 * @param newPiece - the piece that is going to replace whatever is at the position
	 * @param position - The String position to be replaced.
	 */
	public void replacePiece(ChessPiece newPiece, String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		this.board[row][col] = null;
		placePiece(newPiece, position);
	}

	/**
	 * Method called by move(): When a ChessPiece is being captured that this method will call plunder() allowing a player
	 * to plunder the movement of the captured ChessPiece.
	 *
	 * This method is called in placePiece()
	 *
	 * @param attackingPiece - The ChessPiece that is capturing
	 * @param position - the String position of the ChessPiece being captured.
	 */
	public void capture(ChessPiece attackingPiece, String position) {
		ChessPiece capturedPiece;
		try {
			capturedPiece = getPiece(position);
			plunder(attackingPiece, capturedPiece);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		replacePiece(attackingPiece, position);
	}

	/**
	 * Method called by capture(): Allows a player to choose to plunder movement from a captured ChessPiece.
	 * @param attackingPiece - The ChessPiece that is capturing
	 * @param capturedPiece - The ChessPiece being captured
	 * @throws IllegalPositionException - calls the setVest which throws IllegalPositionException
	 */
	private void plunder(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException {
		ArrayList<Class<?>> vestTypes = attackingPiece.getVestTypes();
		boolean isPlunderable = (vestTypes.contains(capturedPiece.getClass()) || (capturedPiece.getVest() != null
				&& vestTypes.contains(capturedPiece.getVest().getType().getClass())));
		if (!isPlunderable)
			return;
		boolean pieceIsPlunderable = vestTypes.contains(capturedPiece.getClass());
		boolean vestIsPlunderable = (capturedPiece.getVest() != null
				&& vestTypes.contains(capturedPiece.getVest().getType().getClass()));
		boolean attackerPrivileged = attackingPiece.getVest() != null;
		System.out.println("Would you like to plunder? (y/n)");
		if (sc.nextLine().equals("y")) {
			System.out.println("You may plunder: ");
			if (pieceIsPlunderable && vestIsPlunderable && attackerPrivileged) {
				System.out.println("Would you like to remove your vest? (y/n)");
				if (sc.nextLine().equals("y"))
					attackingPiece.setVest(null);
			}
			System.out.println("You may obtain the following vests: ");

			if (pieceIsPlunderable)
				System.out.print(capturedPiece.getClass().toString() + " (1)");

			if (vestIsPlunderable)
				System.out.print(", " + capturedPiece.getVest().getType().getClass().toString() + " (2)");

			String reply = sc.nextLine();
			if (reply.equals("1") && pieceIsPlunderable)
				attackingPiece.setVest(capturedPiece);
			else if (reply.equals("2") && vestIsPlunderable)
				attackingPiece.setVest(capturedPiece.getVest().getType());
		}
	}

	/**
	 * Helper Method called by move(): Promotes a pawn that has reached the other end of the board.
	 * @param position - the position being moved too.
	 */
	// this function is called by move();
	private void tryPawnPromote(String position) {

		// TODO: Note that pawn just gets promoted to QUEEN right now
		// However, it can also get promoted to Bishop, Knight, and Rook,
		// depending on the user.
		ChessPiece piece = null;
		try {
			piece = getPiece(position);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		if (piece instanceof Pawn && ((position.charAt(1) == '1' && piece.color == Color.BLACK)
				|| (position.charAt(1) == '8' && piece.color == Color.WHITE))) {
			Pawn pawn = (Pawn) piece;
			pawn.promote("QUEEN");
		}
	}

	/**
	 *  precondition: board has a king of the given color on the board
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is checkMate and false if it isn't
	 */
	public boolean isCheckMate(Color currentColor) {
		return isCheck(currentColor) && !hasAnyMoves(currentColor);
	}

	/**
	 * hasAnyMoves() checks if a side currently has any moves based on the color.
	 * @param currentColor - the color of the team you check the moves for.
	 * @return - true if they do have moves and false if they don't have moves.
	 */
	private boolean hasAnyMoves(Color currentColor) {
		ArrayList<String> totalMoves = new ArrayList<>();

		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				ChessPiece piece = this.board[row][col];
				if(piece != null && piece.getColor() == currentColor) {
					totalMoves.addAll(piece.legalMoves(true, true));
				}
			}
		}
		return !(totalMoves.size() == 0);
	}

	public boolean isCheck(Color currentColor) {
		King currentKing = currentColor == Color.WHITE ? whiteKing : blackKing;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				ChessPiece piece = null;

				try {
					piece = board[row][col];
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

				if (piece != null) {
					boolean pieceCapturesKing = piece.legalMoves(true, false).contains(currentKing.getPosition());
					if (pieceCapturesKing && !piece.color.equals(currentColor)) {
						System.out.println("Check - " + piece.getPosition() + " - " + piece.toString());
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * isDraw() checks if the game is a draw based on one of three draw scenerios,
	 * stalemate, threefold repetition, or fifty-move rule
	 * 
	 * @return a value indicating whether or not in a draw state
	 */
	public boolean isDraw(Color currentColor) {
		// check the three types of draw, stalemate, threefold repetition, fifty-move
		// rule
		return this.checkStalemate(currentColor) && this.history.checkFiftyMoveRule()
				&& this.history.checkThreefoldRepetition();

	}

	/**
	 * checkStalemate: checks if the game is in a stalemate, this occurs when the
	 * not in check and there are no legal moves
	 * 
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is a stalemate and false if it isn't
	 */
	private boolean checkStalemate(Color currentColor) {
		if (!this.isCheck(currentColor)) {
			ArrayList<String> moves = new ArrayList<>();
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					ChessPiece piece = null;
					try {
						piece = board[row][col];
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					if (piece != null && piece.color != currentColor) {
						moves.addAll(piece.legalMoves(true, false));
					}
				}
			}

			if (moves.size() == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * precondition: board has a king of the given color on the board
	 * 
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is checkMate and false if it isn't
	 */
	public boolean isCheckMate(Color currentColor) {
		if (!isCheck(currentColor) || hasAnyMoves(currentColor))
			return false;
		return true;
	}

	/**
	 * hasAnyMoves() checks if a side currently has any moves based on the color.
	 * 
	 * @param currentColor - the color of the team you check the moves for.
	 * @return - true if they do have moves and false if they don't have moves.
	 */
	private boolean hasAnyMoves(Color currentColor) {
		ArrayList<String> totalMoves = new ArrayList<>();

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				ChessPiece piece = this.board[row][col];
				if (piece != null && piece.getColor() == currentColor) {
					totalMoves.addAll(piece.legalMoves(true, true));
				}
			}
		}
		return !(totalMoves.size() == 0);
	}

	// This method is just for testing, remove when UI is implemented
	public String toString() {
		StringBuilder chess = new StringBuilder();
		String upperLeft = "\u250C";
		String upperRight = "\u2510";
		String horizontalLine = "\u2500";
		String horizontal3 = horizontalLine + "\u3000" + horizontalLine;
		String verticalLine = "\u2502";
		String upperT = "\u252C";
		String bottomLeft = "\u2514";
		String bottomRight = "\u2518";
		String bottomT = "\u2534";
		String plus = "\u253C";
		String leftT = "\u251C";
		String rightT = "\u2524";

		StringBuilder topLine = new StringBuilder(upperLeft);
		for (int i = 0; i < 7; i++) {
			topLine.append(horizontal3).append(upperT);
		}
		topLine.append(horizontal3).append(upperRight);

		StringBuilder bottomLine = new StringBuilder(bottomLeft);
		for (int i = 0; i < 7; i++) {
			bottomLine.append(horizontal3).append(bottomT);
		}
		bottomLine.append(horizontal3).append(bottomRight);
		chess.append(topLine).append("\n");

		for (int row = 7; row >= 0; row--) {
			StringBuilder midLine = new StringBuilder();
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == null) {
					midLine.append(verticalLine).append("-\u3000-");
				} else {
					midLine.append(verticalLine).append("-").append(board[row][col]).append("-");
				}
			}
			midLine.append(verticalLine);
			StringBuilder midLine2 = new StringBuilder(leftT);
			for (int i = 0; i < 7; i++) {
				midLine2.append(horizontal3).append(plus);
			}
			midLine2.append(horizontal3).append(rightT);
			chess.append(midLine).append("\n");
			if (row >= 1)
				chess.append(midLine2).append("\n");
		}

		chess.append(bottomLine);
		return chess.toString();
	}

	/**
	 * Setter Method: used to test the King Class
	 * @param whiteKing - Set the White King
	 */
	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	/**
	 * Setter Method: used to test the King Class
	 * @param blackKing - set the Black King
	 */
	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	/**
	 * Getter Method: returns the move history of the ChessBoard
	 * @return Move History object, which is an array of Moves
	 */
	public MoveHistory getHistory() {
		return history;
	}

	/**
	 * Helper Method used by ChessPiece method illegalMovesDueToCheck() to simulate and reset movement on the ChessBoard
	 * in order to determine whether a ChessPiece's move is illegal because it will place that Color's King into check.
	 * @param pieceToMove - The ChessPiece Object being moved.
	 * @param currentPos - The current position of the move.
	 * @param newPos - the position that the piece is being moved too.
	 */
	public void simulateMove(ChessPiece pieceToMove, String currentPos, String newPos) {
		history.addMoveToMoveHistory(new Move(pieceToMove, currentPos, newPos, null));
		placePiece(pieceToMove, newPos);
		removePiece(currentPos);
	}

	/**
	 * Helper Method that ChessPiece calls to determine if a position is legal
	 * @param position - The position in question.
	 * @return True if the position is on the board false otherwise.
	 */
	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h'
				&& position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}

	/**
	 * Helper Method being used by Game() to increment the turn.
	 * @param turnWhite - True if it's Whites turn, false otherwise.
	 */
	public void setTurnWhite(boolean turnWhite) {
		this.turnWhite = turnWhite;
	}

}
