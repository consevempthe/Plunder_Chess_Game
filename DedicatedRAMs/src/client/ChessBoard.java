package client;

import client.ChessPiece.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard {

	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();
	private King whiteKing = new King(this, Color.WHITE);
	private King blackKing = new King(this, Color.BLACK);

	private Scanner sc = new Scanner(System.in);

	public ChessBoard() {
		board = new ChessPiece[8][8];
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

	public MoveHistory getHistory() {
		return history;
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
//			else if (getPiece(position) != null && !getPiece(position).getColor().equals(piece.getColor())) {
//				history.setCapturedPieceInMove(getPiece(position));
//				this.captureAndReplace(piece, position); // capture the piece does this need to be added to some sort of
//			}											// list
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

		try {
			pieceToMove = getPiece(currentPos);
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException();
		}

		if(pieceToMove != null) {
			boolean moveIsLegal = pieceToMove.legalMoves(true, true).contains(newPos);

			if(moveIsLegal && pieceToMove instanceof King && !pieceToMove.hasMoved &&
					(newPos.equals("c1") || newPos.equals("g1") || newPos.equals("g8") || newPos.equals("c8"))) {

				castleMove(pieceToMove, newPos);

			} else if(moveIsLegal) {
				if(pieceToMove.getVest() != null) {
					System.out.print("Use Vest for this move? (y/n)");
					char response = sc.nextLine().charAt(0);

					if (response == 'y') {
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

	public void captureAndReplace(ChessPiece piece, String position) throws IllegalPositionException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Would you like to plunder the piece at " + position + "? (y/n)");
		String response = scanner.nextLine();
		if (response.equals("y"))
		{
			ChessPiece vestPiece = this.getPiece(position);
			ChessPiece vest = vestPiece;
			if (vestPiece.vest != null) {
				if (!piece.vestTypes.contains(vestPiece.getClass())
						&& piece.vestTypes.contains(vestPiece.vest.getType().getClass())) {
					System.out.println("The captured isn't a plunderable type, only it's vest of type "
							+ vestPiece.vest.getType().getClass().toString() + " can be applied");
					piece.setVest(vestPiece.vest.getType());
					
				} else if (piece.vestTypes.contains(vestPiece.getClass())
						&& !piece.vestTypes.contains(vestPiece.vest.getType().getClass())) {
					System.out.println("The captured piece's vest isn't a plunderable type, only it's piece of type "
							+ vestPiece.getClass().toString() + " can be applied");
					piece.setVest(vestPiece);
					
				} else if (piece.vestTypes.contains(vestPiece.getClass())
						&& piece.vestTypes.contains(vestPiece.vest.getType().getClass())) {
					
					System.out.println(vestPiece.getClass().toString() + " has a vest of type "
							+ vestPiece.vest.getType().getClass().toString());
					System.out.print("Plunder " + vestPiece.getClass().toString() + " (1)  or "
							+ vestPiece.vest.getType().getClass().toString() + " (2)?");

					while (!response.equals("1") && !response.equals("2")) {
						response = scanner.nextLine();
					}

					if (response.equals("2")) {
						vest = vestPiece.vest.getType();
					}
					

					piece.setVest(vest);
				} else {
					System.out.println("No plunderable piece can be applied");
				}
			} else {
				if (piece.vestTypes.contains(vestPiece.getClass())) {
					piece.setVest(vest);
				} else {
					System.out.println("No plunderable piece can be applied");
				}
			}

		}

		this.replacePiece(piece, position);
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
					if(pieceCapturesKing && !(piece instanceof King) && !piece.color.equals(currentColor)) {
						System.out.println("Check - " + piece.getPosition() + " - " + piece.toString());
						return true;
					}
				}
			}
		}
		King otherKing =  currentColor == Color.BLACK ?  whiteKing : blackKing;

		return otherKing.legalMoves(true, false).contains(currentKing.getPosition());
	}

	/**
	 *  precondition: board has a king of the given color on the board
	 * @param currentColor - the current color of the King piece
	 * @return - true if it is checkMate and false if it isn't
	 */
	public boolean isCheckMate(Color currentColor) {

		ArrayList<String> opponentsMoves = new ArrayList<>();
		ArrayList<ChessPiece> opponentsPieces = new ArrayList<>();
		ArrayList<String> opponentsPositions = new ArrayList<>();

		King king = currentColor == Color.WHITE ? whiteKing : blackKing;
		String currentPosition;

		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {

				ChessPiece piece = this.board[row][col];
				if(piece != null && piece.getColor() != currentColor) {
					opponentsPieces.add(piece);
					opponentsPositions.add(piece.getPosition());
				}
				if(piece instanceof King && piece.getColor() == currentColor) {
					king = (King)piece;
				}

			}
		}

		currentPosition = king.getPosition();

		for(ChessPiece piece : opponentsPieces) {
			opponentsMoves.addAll(piece.legalMoves(true, false));
		}

		this.removePiece(currentPosition); // remove king temporarily

		for(ChessPiece piece : opponentsPieces) {
			opponentsMoves.addAll(piece.legalMoves(true, false));
		}

		this.placePiece(king, currentPosition); // replace the king
		ArrayList<String> kingsMoves = king.legalMoves(false, false);

		if(kingsMoves.size() != 0) {
			for (String move : opponentsMoves) {
				kingsMoves.remove(move);
			}
			if(kingsMoves.size() != 0) {
				testRemainingMoves(kingsMoves, king);
			}
			return kingsMoves.size() == 0;
		} else {
			return false;
		}
	}

	// method: testRemainingMoves(ArrayList<String>, King);
	// parameter 1: ArrayList<String> kingMoves - list of king's remaining moves to test legality
	// parameter 2: King king - a reference to the king in question of check mate
	// precondition: kingMoves.size() != 0
	// postcondition: filters out legal king moves from parameter 1
	// return_type: void - this function manipulates kingMoves by reference
	private void testRemainingMoves(ArrayList<String> kingMoves, King king) {

		ArrayList<String> enemyMoves = new ArrayList<>();
		String kingLocation = king.getPosition();
		String enemyLocation = "";

		if(kingMoves.size() == 0) {
			return;
		}

		for(String move : kingMoves) {

			ChessPiece otherPiece = null; // other piece is the piece at the king's valid moves
			ChessPiece boardPiece; // board piece is a temp variable for the boards other pieces

			try {
				otherPiece = getPiece(move); // see if a piece exists at king's moves
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}

			if(otherPiece != null && otherPiece.getColor() != king.getColor()) {
				enemyLocation = move;
				this.removePiece(kingLocation);
				this.removePiece(enemyLocation); // simulate the king taking the piece
				this.placePiece(king, enemyLocation);
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						boardPiece = this.board[i][j];
						if(boardPiece != null && boardPiece.getColor() != king.getColor()) {
							enemyMoves.addAll(boardPiece.legalMoves(true, false));
						}
					}
				}
			} else {
				this.placePiece(king, enemyLocation);
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						boardPiece = this.board[i][j];
						if(boardPiece != null && boardPiece.getColor() != king.getColor()) {
							enemyMoves.addAll(boardPiece.legalMoves(true, false));
						}
					}
				}
			}

			this.removePiece(move);
			if(otherPiece != null) {
				this.placePiece(otherPiece, enemyLocation);
			}
			this.placePiece(king, kingLocation);

		}

		for(String enemyMove : enemyMoves) {
			kingMoves.remove(enemyMove);
		}

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
					midLine.append(verticalLine).append("\u3000");
				} else {
					midLine.append(verticalLine).append(" ").append(board[row][col]).append(" ");
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

}
