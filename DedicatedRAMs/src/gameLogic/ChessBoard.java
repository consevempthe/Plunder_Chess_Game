package gameLogic;

import gameLogic.Player.Color;
import exceptions.*;
import clientUI.GameEventHandlers;
import clientUI.PawnPromoteUI;

import java.util.ArrayList;

public class ChessBoard {

	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();
	private King whiteKing = new King(this, Color.WHITE);
	private King blackKing = new King(this, Color.BLACK);
	private boolean turnWhite = true;

	private ArrayList<GameEventHandlers> listeners = new ArrayList<>();

	public ChessBoard() {
		board = new ChessPiece[8][8];
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

	public void addListener(GameEventHandlers toAdd) {
		listeners.add(toAdd);
	}

	/**
	 * Getter Method: Takes a string and converts it to a 0-based int and returns
	 * the ChessPiece at that location on the chessboard.
	 *
	 * This Method is called in the following classes: ChessBoard by placePiece(),
	 * move(), castleMove(), capture(), tryPawnPromote() ChessPiece by
	 * isPositionTakable() PieceMovement by pawnPlusOne(), pawnPlusTwo(),
	 * pawnCapture(), kingCastles(), performMoveAddition(), checkEmpty()
	 *
	 * @param position - string where index 0 is char a-h and index 1 is number 1-8
	 * @return The ChessPiece at the given position on the ChessBoard.
	 * @throws IllegalPositionException - if the string position is not in that
	 *                                  character range and therefore not on the
	 *                                  board.
	 */
	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int col = position.charAt(0) - 'a';
		int row = position.charAt(1) - '1';
		return board[row][col];
	}

	/**
	 * Getter Method: Instead of a position get the Piece via row and column on the
	 * board
	 * 
	 * @param row - int row
	 * @param col - int col
	 * @return - piece at that row/col
	 */
	public ChessPiece getPiece(int row, int col) {
		return board[row][col];
	}

	/**
	 * Setter Method: This method is used to place a ChessPiece onto the board and
	 * is used to place pieces when the board is initialized and when a piece is
	 * being moved.
	 *
	 *
	 * The ChessPiece class uses this method to return a simulated captured
	 * ChessPiece back to the board when it is trying to determine if there is
	 * illegal movement due to Check.
	 *
	 * @param movingPiece - the given ChessPiece object that is going to be placed
	 * @param newPos      - the String position for where to place the piece
	 * @return - true on success, false otherwise
	 */
	public boolean placePiece(ChessPiece movingPiece, String newPos) {
		try {
			if (getPiece(newPos) != null && getPiece(newPos).getColor().equals(movingPiece.getColor()))
				return false;
			else if (getPiece(newPos) != null && !getPiece(newPos).getColor().equals(movingPiece.getColor())) {
				history.setCapturedPieceInMove(getPiece(newPos));
			}
			movingPiece.setPosition(newPos);
		} catch (IllegalPositionException e) {
			return false;
		}
		int i1 = newPos.charAt(0) - 'a';
		int i2 = newPos.charAt(1) - '1';
		board[i2][i1] = movingPiece;
		return true;
	}

	/**
	 * Method to move pieces: Given two positions move the piece from its currentPos
	 * to the newPos.
	 *
	 * This method checks to see if the move is a castle and subsequently calls
	 * castleMove(), otherwise it will ask for the players input on whether they
	 * want to use a vested move.
	 *
	 * The method adds the move to the ChessBoard's move history, places the
	 * ChessPiece, sets the ChessPiece to hasMoved and the removes that piece from
	 * its current position and then attempts to promote that piece if it is a pawn.
	 *
	 * @param currentPos - the current position of the piece being moved
	 * @param newPos     - the new position that the piece is being moved to
	 * @param plunderOption 
	 * @throws IllegalMoveException     - if the newPos is not a legal move
	 * @throws IllegalPositionException - if the currentPos was not a legal position
	 */
	public void move(String currentPos, String newPos, String plunderOption) throws IllegalMoveException, IllegalPositionException {
		ChessPiece pieceToMove;
		pieceToMove = getPiece(currentPos);

		boolean moveIsLegal = pieceToMove != null && pieceToMove.moveIsLegal(newPos, true);
		if (moveIsLegal) {
			boolean isIncorrectColor = (turnWhite && pieceToMove.getColor() == Color.BLACK)
					|| (!turnWhite && pieceToMove.getColor() == Color.WHITE);
			if (isIncorrectColor)
				throw new IllegalMoveException();

			if (pieceToMove instanceof King && !pieceToMove.hasMoved
					&& (newPos.equals("c1") || newPos.equals("g1") || newPos.equals("g8") || newPos.equals("c8"))) {

				castleMove((King) pieceToMove, newPos);

			} else if (pieceToMove instanceof Pawn
					&& newPos.equals(((Pawn) pieceToMove).getEnPassant())) {

				enPassantMove(pieceToMove, newPos, plunderOption);

			} else {
				if (pieceToMove.hasVest()) {
					// if the move is in vest and not the parent piece it's a vest move
					if (pieceToMove.isVestMoveLegal(newPos)
							&& !pieceToMove.moveIsLegal(newPos, false)) {
						pieceToMove.setVest(null);
					}
				}

				makeMove(currentPos, newPos, pieceToMove, plunderOption);
				if (pieceToMove instanceof Pawn)
					tryPawnPromote(newPos);

			}
		} else {
			throw new IllegalMoveException();
		}

	}

	private void makeMove(String currentPos, String newPos, ChessPiece pieceToMove, String plunderOption) {
		history.addMoveToMoveHistory(new Move(pieceToMove, currentPos, newPos));
		if(plunderOption.contains("yes"))
			try {
				plunder(pieceToMove, getPiece(newPos), plunderOption.charAt(plunderOption.length()-1));
			} catch (IllegalPositionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			placePiece(pieceToMove, newPos);
		}
		pieceToMove.setHasMoved(true);
		removePiece(currentPos);
	}

	private void plunder(ChessPiece pieceToMove, ChessPiece defendingPiece, char plunderValue) throws IllegalPositionException {
		placePiece(pieceToMove, defendingPiece.getPosition());
		if(plunderValue == '0') {
			pieceToMove.setVest(defendingPiece);
			pieceToMove.setVestPieceColor(pieceToMove.getColor());
		}
		else if(plunderValue == '1') {
			pieceToMove.setVest(defendingPiece.getVest());
			pieceToMove.setVestPieceColor(pieceToMove.getColor());
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
	public void castleMove(King pieceToMove, String newPos) throws IllegalPositionException {
		String kingPosition = pieceToMove.getPosition();
		String currentRookPos = "";
		String newRookPos = "";

		if (newPos.equals("c1") || newPos.equals("c8")) {
			currentRookPos = (char) (-4 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char) (-1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
		} else if (newPos.equals("g1") || newPos.equals("g8")) {
			currentRookPos = (char) (3 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char) (1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
		}

		makeMove(kingPosition, newPos, pieceToMove, "no");
		pieceToMove.setRookCastlingPositions(currentRookPos, newRookPos);

		ChessPiece rook = this.getPiece(currentRookPos);
		makeMove(currentRookPos, newRookPos, rook, "no");

	}

	public void enPassantMove(ChessPiece pawnEnPassant, String newPos, String plunderOption) {
		String pawnLocation = pawnEnPassant.getPosition();
		String pawnCapture;

		if (pawnEnPassant.getColor().equals(Color.WHITE)) {
			pawnCapture = (newPos.charAt(0)) + "" + (char) (-1 + newPos.charAt(1));
		} else {
			pawnCapture = (newPos.charAt(0)) + "" + (char) (1 + newPos.charAt(1));
		}

		makeMove(pawnLocation, newPos, pawnEnPassant, plunderOption);
		removePiece(pawnCapture);
	}

	/**
	 * Helper Method: Removes a piece from the board - either because it was
	 * simulated for Check, or because move() was called and successful.
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
	 * This method is called by capture() and called in the Pawn class in order to
	 * upgrade a piece.
	 *
	 * @param newPiece - the piece that is going to replace whatever is at the
	 *                 position
	 * @param position - The String position to be replaced.
	 */
	public void replacePiece(ChessPiece newPiece, String position) {
		removePiece(position);
		placePiece(newPiece, position);
	}
	
	/**
	 * Determines whether a piece has the capability to plunder another piece by the legality of the move and the legality of plundering.
	 * @param attackingPiece
	 * @param defendingPiece
	 * @return - true if plunder if possible, false otherwise.
	 */
	public boolean isPlunderable(ChessPiece attackingPiece, ChessPiece defendingPiece) {
		boolean pieceCaptured = defendingPiece != null && attackingPiece.moveIsLegal(defendingPiece.getPosition(), true);
		boolean canPlunder = false;
		if(pieceCaptured) {
			 canPlunder = (attackingPiece.hasVestType(defendingPiece) || (defendingPiece.hasVest()
					&& attackingPiece.hasVestType(defendingPiece.getVest())));
		}
		return canPlunder;
	}

	/**
	 * Helper Method called by move(): Promotes a pawn that has reached the other
	 * end of the board.
	 * 
	 * @param position - the position being moved too.
	 */
	private void tryPawnPromote(String position) {
		ChessPiece piece = null;
		try {
			piece = getPiece(position);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		if (piece instanceof Pawn && ((position.charAt(1) == '1' && piece.color == Color.BLACK)
				|| (position.charAt(1) == '8' && piece.color == Color.WHITE))) {
			Pawn pawn = (Pawn) piece;
			PawnPromoteUI ui = new PawnPromoteUI(pawn.getColor());
			String userDecision = ui.showDialog();
			pawn.promote(userDecision);
		}
	}

	/**
	 * precondition: board has a king of the given color on the board
	 * 
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is checkMate and false if it isn't
	 */
	public boolean isCheckMate(Color currentColor) {
		return isCheck(currentColor) && !hasAnyMoves(currentColor);
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
		//check the three types of draw, stalemate, threefold repetition, fifty-move rule 
		return this.checkStalemate(currentColor) || this.history.checkFiftyMoveRule() || this.history.checkThreefoldRepetition();
		
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

			return moves.size() == 0;
		}

		return false;
	}

	/**
	 * Setter Method: used to test the King Class
	 * 
	 * @param whiteKing - Set the White King
	 */
	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	/**
	 * Setter Method: used to test the King Class
	 * 
	 * @param blackKing - set the Black King
	 */
	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	/**
	 * Getter Method: returns the move history of the ChessBoard
	 *
	 * ONLY USED IN TESTING
	 * 
	 * @return Move History object, which is an array of Moves
	 */
	public MoveHistory getMoveHistory() {
		return history;
	}

	/**
	 * gets the size of the MoveHistory
	 * @return int size
	 */
	public int getMoveHistorySize() { return history.moveHistorySize(); }

	/**
	 * returns the last move in the history
	 * @return Move object - last move taken
	 */
	public Move getLastMoveInHistory() { return history.getLastMove(); }

	/**
	 * returns the last piece that was captured in the history
	 * @return ChessPiece object - last one captured
	 */
	public ChessPiece getLastCapturedPiece() {
		return history.getLastMove().getCaptured();
	}

	/**
	 * removes the last move - done in simulating
	 */
	public void removeLastMoveInHistory() {
		history.removeEnd();
	}

	/**
	 * Helper Method used by ChessPiece method illegalMovesDueToCheck() to simulate
	 * and reset movement on the ChessBoard in order to determine whether a
	 * ChessPiece's move is illegal because it will place that Color's King into
	 * check.
	 * 
	 * @param pieceToMove - The ChessPiece Object being moved.
	 * @param currentPos  - The current position of the move.
	 * @param newPos      - the position that the piece is being moved too.
	 */
	public void simulateMove(ChessPiece pieceToMove, String currentPos, String newPos) {
		history.simulateMoveHistoryAddition(new Move(pieceToMove, currentPos, newPos));
		placePiece(pieceToMove, newPos);
		removePiece(currentPos);
	}

	/**
	 * Helper Method that ChessPiece calls to determine if a position is legal
	 * 
	 * @param position - The position in question.
	 * @return True if the position is on the board false otherwise.
	 */
	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h'
				&& position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}

	/**
	 * Helper Method being used by Game() to increment the turn.
	 * 
	 * @param turnWhite - True if it's Whites turn, false otherwise.
	 */
	public void setTurnWhite(boolean turnWhite) {
		this.turnWhite = turnWhite;
	}

}
