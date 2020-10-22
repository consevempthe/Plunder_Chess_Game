package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import client.ChessPiece;
import client.Game;
import client.ChessPiece.Color;

public class ChessBoardUI {

	private Game game;
	private final JPanel window = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private JPanel chessBoard;
	private static final String COLS = "ABCDEFGH";
	private Color perspectiveColor = Color.BLACK; // this should be coming from the player...

	/**
	 * Creates a new instance of the ChessBoardUI class, which takes a game instance.
	 */
	public ChessBoardUI(Game game) {
		this.game = game;
		this.initializeGui();
		this.fillInPieces();
	}

	/**
	 * Initializes the GUI, this method creates a base blank board in gray scale
	 * All the square are buttons we can add a call back to do make game moves
	 */
	private void initializeGui() {
		window.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		window.add(tools, BorderLayout.PAGE_START);
		tools.addSeparator();

		chessBoard = new JPanel(new GridLayout(0, 9));
		chessBoard.setBorder(new LineBorder(java.awt.Color.BLACK));
		window.add(chessBoard);

		for (int r = 7; r >= 0; r--) {
			for (int c = 0; c < 8; c++) {
				JButton b = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.setOpaque(true);
				b.setBorderPainted(false); // For Mac and java 8
				chessBoardSquares[r][c] = b;
			}
		}

		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				JButton b = chessBoardSquares[r][c];
				if ((r % 2 == 1 && c % 2 == 1) || (r % 2 == 0 && c % 2 == 0)) {
					b.setBackground(java.awt.Color.lightGray);
				} else {
					b.setBackground(java.awt.Color.gray);
				}
			}
		}
	}

	/**
	 * This method fills in the pieces on the board based on the perspective color,
	 * The black board is a mirror image of the white, with the row and column labels backwards as well
	 */
	private void fillInPieces() {
		chessBoard.add(new JLabel(""));
		if (this.perspectiveColor == Color.WHITE) {
			for (int c = 0; c < 8; c++) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 7; r >= 0; r--)
				for (int c = 0; c < 8; c++) {
					{
						switch (c) {
						case 0:
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						default:
							chessBoard.add(chessBoardSquares[r][c]);
							ChessPiece piece = this.game.getGameBoard().getPieces()[r][c];
							if (piece != null) {
								int i1 = piece.getPosition().charAt(0) - 'a';
								int i2 = piece.getPosition().charAt(1) - '1';

								JButton button = chessBoardSquares[i2][i1];
								ImageIcon icon = piece.toImage();
								button.setIcon(icon);
								button.setOpaque(true);
								button.setBorderPainted(false);
							}
						}
					}
				}
		} else {
			for (int c = 7; c >= 0; c--) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 0; r < 8; r++)
				for (int c = 7; c >= 0; c--) {
					{
						switch (c) {
						case 7:
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						default:
							chessBoard.add(chessBoardSquares[r][c]);
							ChessPiece piece = this.game.getGameBoard().getPieces()[r][c];
							if (piece != null) {
								int i1 = piece.getPosition().charAt(0) - 'a';
								int i2 = piece.getPosition().charAt(1) - '1';

								JButton button = chessBoardSquares[i2][i1];
								ImageIcon icon = piece.toImage();
								button.setIcon(icon);
								button.setOpaque(true);
								button.setBorderPainted(false);
							}
						}
					}
				}
		}

	}

	/**
	 * Gets the window so it can be rendered in the UI thread.
	 * @returns the board as a JComponent
	 */
	public JComponent getGui() {
		return window;
	}

}
