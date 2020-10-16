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

	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		return board[i2][i1];
	}

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

	public void move(String currentPos, String newPos) throws IllegalMoveException, IllegalPositionException {
		ChessPiece pieceToMove;
		pieceToMove = getPiece(currentPos);
		
		boolean isIncorrectColor = (turnWhite && pieceToMove.getColor() == Color.BLACK) || (!turnWhite && pieceToMove.getColor() == Color.WHITE);
		if(isIncorrectColor)
			throw new IllegalMoveException();
		
		if(pieceToMove != null) {
			boolean moveIsLegal = pieceToMove.legalMoves(true, true).contains(newPos);

			if(moveIsLegal && pieceToMove instanceof King && !pieceToMove.hasMoved &&
					(newPos.equals("c1") || newPos.equals("g1") || newPos.equals("g8") || newPos.equals("c8"))) {

				castleMove(pieceToMove, newPos);

			} else if(moveIsLegal) {
				if(pieceToMove.getVest() != null) {
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
		} else
			throw new IllegalMoveException();
	}

	public void setTurnWhite(boolean turnWhite) {
		this.turnWhite = turnWhite;
	}

	/**
	 * Helper Method for Move: This method is only called when piece being move is a King that hasn't moved and it is
	 * making a castling move determined by the piece it is going to, which is either c1, c8, g1, g8.
	 *
	 * By this point in time we know based on the King's legalMoves() that there are no spaces in between the King
	 * and Rook and that the Rook is in the position it needs to be.
	 *
	 * This method determines the Rook position based on the King position and then moves both of those pieces to the
	 * position that will go to.
	 *
	 * @param pieceToMove - Is a King ChessPiece Object
	 * @param newPos - The Castling move
	 * @throws IllegalPositionException - since it calls getPosition it must throw an illegal position.
	 */
	public void castleMove(ChessPiece pieceToMove, String newPos) throws IllegalPositionException {
		String kingPosition = pieceToMove.getPosition();
		String rookPosition = "";
		String newRookPos = "";

		if(newPos.equals("c1") || newPos.equals("c8")) {
			rookPosition = (char)(-4 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char)(-1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
		} else if(newPos.equals("g1") || newPos.equals("g8")) {
			rookPosition = (char)(3 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
			newRookPos = (char)(1 + kingPosition.charAt(0)) + "" + kingPosition.charAt(1);
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

	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}

	public void replacePiece(ChessPiece newPiece, String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		this.board[row][col] = null;
		placePiece(newPiece, position);
	}
	
	public void capture(ChessPiece attackingPiece, String position) {
		ChessPiece capturedPiece = null;
		try {
			capturedPiece = getPiece(position);
			plunder(attackingPiece, capturedPiece);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		replacePiece(attackingPiece, position);
	}

	private void plunder(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException {
		ArrayList<Class<?>> vestTypes = attackingPiece.getVestTypes();
		boolean isPlunderable = (vestTypes.contains(capturedPiece.getClass()) || (capturedPiece.getVest() != null && vestTypes.contains(capturedPiece.getVest().getType().getClass())));
		if(!isPlunderable)
			return;
		boolean pieceIsPlunderable  = vestTypes.contains(capturedPiece.getClass());
		boolean vestIsPlunderable = (capturedPiece.getVest() != null && vestTypes.contains(capturedPiece.getVest().getType().getClass()));
		boolean attackerPrivileged = attackingPiece.getVest() != null;
		System.out.println("Would you like to plunder? (y/n)");
		if(sc.nextLine().equals("y")) {
			System.out.println("You may plunder: ");
			if(pieceIsPlunderable && vestIsPlunderable && attackerPrivileged) {
				System.out.println("Would you like to remove your vest? (y/n)");
				if(sc.nextLine().equals("y"))
					attackingPiece.setVest(null);
			}
			System.out.println("You may obtain the following vests: ");
			
			if(pieceIsPlunderable)
				System.out.print(capturedPiece.getClass().toString() + " (1)");
			
			if(vestIsPlunderable)
				System.out.print(", " + capturedPiece.getVest().getType().getClass().toString() + " (2)");
			
			String reply = sc.nextLine();
			if (reply.equals("1") && pieceIsPlunderable) 
				attackingPiece.setVest(capturedPiece);
			else if(reply.equals("2") && vestIsPlunderable) 
				attackingPiece.setVest(capturedPiece.getVest().getType());
		}
	}
	
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

	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h'
				&& position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}
	
	public boolean isCheck(Color currentColor) {
		King currentKing =  currentColor == Color.WHITE ?  whiteKing : blackKing;

		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				ChessPiece piece = null;

				try {
					piece = board[row][col];
				}catch(NullPointerException e) {
					e.printStackTrace();
				}

				if(piece != null) {
					boolean pieceCapturesKing = piece.legalMoves(true, false).contains(currentKing.getPosition());
					if(pieceCapturesKing && !piece.color.equals(currentColor)) {
						System.out.println("Check - " + piece.getPosition() + " - " + piece.toString());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * isDraw() checks if the game is a draw based on one of three draw scenerios, stalemate, threefold repetition, or fifty-move rule
	 * @return a value indicating whether or not in a draw state
	 */
	public boolean isDraw(Color currentColor)
	{
		//check the three types of draw, stalemate, threefold repetition, fifty-move rule 
		return this.checkStalemate(currentColor) && this.history.checkFiftyMoveRule() && this.history.checkThreefoldRepetition();
		
	}
	
	/**
	 * checkStalemate: checks if the game is in a stalemate, this occurs when the not in check and there are no legal moves
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is a stalemate and false if it isn't
	 */
	private boolean checkStalemate(Color currentColor)
	{
		if(!this.isCheck(currentColor))
		{
			ArrayList<String> moves = new ArrayList<>();
			for(int row = 0; row < 8; row++) {
				for(int col = 0; col < 8; col++) {
					ChessPiece piece = null;
					try {
						piece = board[row][col];
					}catch(NullPointerException e) {
						e.printStackTrace();
					}
					if(piece != null && piece.color != currentColor) {
						moves.addAll(piece.legalMoves(true, false));
					}
				}
			}
			
			if(moves.size() == 0)
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 *  precondition: board has a king of the given color on the board
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is checkMate and false if it isn't
	 */
	public boolean isCheckMate(Color currentColor) {
		if(!isCheck(currentColor) || hasAnyMoves(currentColor))
			return false;
		return true;
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
	
	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}
	
	public MoveHistory getHistory() {
		return history;
	}

}
