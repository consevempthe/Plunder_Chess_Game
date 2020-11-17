package clientUI;

import client.*;
import gameLogic.*;
import exceptions.IllegalPositionException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameUI implements GameEventHandlers {

	private Game game;
	private Client client;
	private final JPanel window = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private JPanel chessBoard;
	private static final String COLS = "ABCDEFGH";
	private JButton selectedButton;
	private Object[] plunderOptions = { "Yes", "No" };
	private Object[] confirmOptions = { "Continue", "Cancel" };
	private boolean isCheckMate = false;
	private boolean isDraw = false;

	/**
	 * Creates a new instance of the GameUI class, which takes a game
	 * instance.
	 * @param client - the game client
	 */
	public GameUI(Game game, Client client) {
		this.client = client;
		this.game = game;
		this.initializeGui();
		this.fillInPieces();
		this.game.addListener(this);
	}

	/**
	 * Gets the window so it can be rendered in the UI thread.
	 * 
	 * @return the ChessBoard UI
	 */
	public JComponent getGui() {
		return window;
	}

	@Override
	public void checkMateEvent(Player.Color winningColor) {
		isCheckMate = true;
		if(winningColor == Player.Color.WHITE)
			winningColor = Player.Color.BLACK;
		else
			winningColor = Player.Color.WHITE;
		JOptionPane.showMessageDialog(window, "Checkmate! " + winningColor + " Wins!");
	}

	@Override
	public void drawEvent() {
		isDraw = true;
		JOptionPane.showMessageDialog(window, "Draw!");
	}
	
	@Override
	public void checkEvent(gameLogic.Player.Color checkedColor, String white_player, String black_player) {
		this.highlightKingInCheck(checkedColor);
		new CheckUI(checkedColor, white_player, black_player);
	}
	
	private void highlightKingInCheck(Player.Color colorInCheck) {
		
		int whiteRow = 0, whiteCol = 0, blackRow = 0, blackCol = 0;
			ChessPiece piece;

			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					piece = game.getGameBoard().getPiece(i, j);
					if(piece instanceof King) {
						if(piece.getColor() == Player.Color.WHITE) {
							whiteRow = i;
							whiteCol = j;
						} else {
							blackRow = i;
							blackCol = j;
						}
					}
				}
			}

			if(colorInCheck == Player.Color.WHITE && this.game.getPlayerColor() == Player.Color.WHITE) {
				chessBoardSquares[whiteRow][whiteCol].setBackground(Color.MAGENTA);
			} else {
				chessBoardSquares[blackRow][blackCol].setBackground(Color.MAGENTA);
			}
		
	}

	/**
	 * The plunder event that updates the UI when plunder happens on the back end.
	 * 
	 * @param attackingPiece the attacking piece
	 * @param capturedPiece  the captured piece
	 */
	@Override
	public String plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) {
		boolean canPlunderPiece = attackingPiece.hasVestType(capturedPiece);
		boolean canPlunderVest = (capturedPiece.hasVest() && attackingPiece.hasVestType(capturedPiece.getVest()));
		boolean attackerHasVest = attackingPiece.hasVest();

		int plunderResponse = JOptionPane.showOptionDialog(window, "Would you like to plunder?", "Plunder",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, plunderOptions, plunderOptions[1]);

		if (plunderResponse == JOptionPane.YES_OPTION) {
			if ((canPlunderPiece || canPlunderVest) && attackerHasVest) {
				int confirmResponse = JOptionPane.showOptionDialog(window,
						"Plundering will remove current vest of " + attackingPiece.getVestName() + " continue?",
						"Confirm Plunder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						confirmOptions, confirmOptions[1]);
				if(confirmResponse == JOptionPane.NO_OPTION)
					return "no";
			}

			Object[] vestOptions = new Object[1];
			if (canPlunderPiece && !canPlunderVest) {
				vestOptions[0] = capturedPiece.getName();
			}

			if (canPlunderPiece && canPlunderVest) {
				vestOptions = new Object[2];
				vestOptions[0] = capturedPiece.getName();
				vestOptions[1] = capturedPiece.getVestName();
			}

			int vestChoice = JOptionPane.showOptionDialog(window, "Select a vest from the following: ",
					"Confirm Plunder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, vestOptions,
					vestOptions.length == 2 ? vestOptions[1] : vestOptions[0]);

			return "yes_" + vestChoice;
		}
		return "no";
	}

	/**
	 * Initializes the GUI, this method creates a base blank board in gray scale All
	 * the square are buttons we can add a call back to make game moves
	 */
	private void initializeGui() {
		window.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		window.add(tools, BorderLayout.PAGE_START);
		tools.addSeparator();

		chessBoard = new JPanel(new GridLayout(0, 9));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		window.add(chessBoard);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton square = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				square.setIcon(icon);
				square.setOpaque(true);
				chessBoardSquares[row][col] = square;

				if ((row + col) % 2 == 0) {
					square.setBackground(Color.LIGHT_GRAY);
					square.putClientProperty("color", Color.LIGHT_GRAY);
				} else {
					square.setBackground(Color.GRAY);
					square.putClientProperty("color", Color.GRAY);
				}

				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}
	}

	public void updateGUI() {
		if(selectedButton != null) {
			selectedButton.setIcon(null);
			selectedButton.setText(null);
			selectedButton.removeAll();
			selectedButton = null;
		}
		
		for(int r = 0; r< 8; r++) {
			for(int c = 0; c < 8; c++) {
				addPieceToBoard(r, c);
			}
		}
		window.repaint();
	}
	
	
	/**
	 * This method fills in the pieces on the board based on the perspective color,
	 * The black board is a mirror image of the white, with the row and column
	 * labels backwards as well
	 */
	private void fillInPieces() {
		chessBoard.add(new JLabel(""));

		if (game.getPlayerColor().equals(Player.Color.WHITE)) {
			for (int c = 0; c < 8; c++) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 7; r >= 0; r--)
				for (int c = 0; c < 8; c++) {
					{
						if (c == 0) {
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						}

						addPieceToBoard(r, c);
						chessBoard.add(chessBoardSquares[r][c]);
					}
				}
		} else {
			for (int c = 7; c >= 0; c--) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 0; r < 8; r++)
				for (int c = 7; c >= 0; c--) {
					{
						if (c == 7) {
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						}

						addPieceToBoard(r, c);
						chessBoard.add(chessBoardSquares[r][c]);
					}
				}
		}
	}

	/**
	 * Helper Method for fillInPieces(): adds the image of ChessPiece to any
	 * location that has a ChessPiece to the board. Adds an actionListener to the
	 * square to allow user input.
	 *
	 * @param row - of the piece
	 * @param col - of the piece
	 */
	private void addPieceToBoard(int row, int col) {
		JButton currentSquare = chessBoardSquares[row][col];
		currentSquare.setOpaque(true);

		ChessPiece currentPiece = this.game.getPieceAtLocation(row, col);
		if (currentPiece != null) {
			currentSquare.setIcon(currentPiece.toImage());
			if (currentPiece.hasVest()) {
				currentSquare.setFont(new Font(currentSquare.getFont().getName(), Font.BOLD, currentSquare.getFont().getSize()));
				currentSquare.setHorizontalTextPosition(SwingConstants.CENTER);
				currentSquare.setVerticalTextPosition(SwingConstants.BOTTOM);
				currentSquare.setIconTextGap(-15);
				currentSquare.setText(currentPiece.getVestName());
				currentSquare.setForeground(currentPiece.getVestColor());
			}
		}
		else {
			currentSquare.setIcon(null);
			currentSquare.setText(null);
		}

		currentSquare.putClientProperty("SquareLoc", new Square(row, col));
		currentSquare.addActionListener(new SelectSquare());
	}

	/**
	 * Given a boolean statement, if false set the board tiles to their original
	 * color, if true highlight movement for the piece.
	 * 
	 * @param select - true to highlight movement, false for normal board.
	 */
	protected void highlightPieceMovement(boolean select) {
		Color tile_color;
		if (select) {
			tile_color = Color.ORANGE;
		} else {
			tile_color = (Color) selectedButton.getClientProperty("color");
		}

		selectedButton.setBackground(tile_color);

		Square s = (Square) selectedButton.getClientProperty("SquareLoc");
		ChessPiece selectedPiece = game.getPieceByPosition(s.getPosition());

		for (String move : selectedPiece.legalMoves(true, true)) {
			int row = move.charAt(1) - '1';
			int col = move.charAt(0) - 'a';
			JButton square = chessBoardSquares[row][col];
			if (select) {
				if (selectedPiece.moveIsLegal(move, true)) {
					tile_color = Color.CYAN;

					// Always default to the chess piece color before the vest color (if the move is
					// solely a vest move color accordingly)
					if (selectedPiece.hasVest() && selectedPiece.isVestMoveLegal(move)
							&& !selectedPiece.moveIsLegal(move, false)) {
						tile_color = selectedPiece.getVestColor();
					}
				}
			} else {
				tile_color = (Color) square.getClientProperty("color");
			}
			square.setBackground(tile_color);
		}
	}

	/**
	 * Helper class used to get Positions of empty squares for movement on the board
	 * UI.
	 */
	private static class Square {
		private int row, col;

		public Square(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public String getPosition() {
			char row = (char) ('1' + this.row);
			char col = (char) ('a' + this.col);

			return col + "" + row;
		}

		public String toString() {
			return this.row + " " + this.col;
		}
	}

	/**
	 * This Class implements ActionListener to override the actionPerformed()
	 * method. When a user clicks on a ChessPiece or empty square the
	 * actionPerformed() method will select the piece or move that piece.
	 */
	private class SelectSquare implements ActionListener {
		/**
		 * Allows the user to select or move a piece.
		 * 
		 * @param e - the MouseClick
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton square = (JButton) e.getSource();
			Square selection = (Square) square.getClientProperty("SquareLoc");
			ChessPiece selectedPiece = game.getPieceByPosition(selection.getPosition());

			if (selectedPiece != null && selectedPiece.getColor() == game.getPlayerColor() && game.isPlayersTurn()) {
				selectPiece(square);
				return;
			}

			if (selectedButton != null) {
				movePiece(square);
			}
			
			
		}

		/**
		 * Move is only called when the user has selected a piece. This method is where
		 * the rest of our Chess Logic should go.
		 * 
		 * @param selectedSquare - the square selected via the actionPerformed class
		 */
		private void movePiece(JButton selectedSquare) {
			Square current = (Square) selectedButton.getClientProperty("SquareLoc");
			ChessPiece currentPiece = game.getPieceByPosition(current.getPosition());
			String currentPos = currentPiece.getPosition();

			Square selectedMove = (Square) selectedSquare.getClientProperty("SquareLoc");
			String newPos = selectedMove.getPosition();

			highlightPieceMovement(false);
			ChessBoard currentBoard = game.getGameBoard();
			ChessPiece attackingPiece = null;
			ChessPiece capturedPiece = null;
			try {
				attackingPiece = currentBoard.getPiece(currentPos);
				capturedPiece = currentBoard.getPiece(newPos);
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}
			String plunderOption = "no";
			if(currentBoard.isPlunderable(attackingPiece, capturedPiece))
					plunderOption = plunderEvent(attackingPiece, capturedPiece);
			
			if(game.move(currentPos, newPos, plunderOption)) {
			String request = "move " + currentPos + " " + newPos + " " + game.getGameID() + " " + game.getOpponent() + " " + plunderOption + "\n";
			try {
				client.request(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
			updateGUI();
			
			}
			
		}

		/**
		 * If a player is selecting a piece for the first time highlight the movement,
		 * the board will not have any highlighted movement
		 * 
		 * @param selectedSquare - the square being selected by the user
		 */
		private void selectPiece(JButton selectedSquare) {
			if (!isCheckMate && !isDraw) {
				if (selectedButton != null) {
					highlightPieceMovement(false);
				}

				selectedButton = selectedSquare;
				highlightPieceMovement(true);
			}
		}
	}

}