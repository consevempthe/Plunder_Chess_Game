package clientUI;

import client.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import gameLogic.Game;

public class StartUI extends FrameUI {
	public JFrame frame;
	public JTextField nicknameEntry = new JTextField();
	public JTextField gameIDEntry = new JTextField();
	private JButton inviteButton;
	public JLabel responseLabel = new JLabel();
	private JButton quitButton;
	private JButton accountButton;
	public JLabel gamesLabel;
	private final String START_TEXT = "Waiting for inputs...";
	private DeleteUserUI deleteUserUI;
	private JButton startGameButton;
	private ArrayList<Game> activeGames = new ArrayList<Game>();
	private String[] columnNames = { "Game ID", "Opponent", "Turn", "Check" };
	private JTable gameList;
	DefaultTableModel games = new DefaultTableModel(0, 0);
	private JButton refreshGamesButton;

	private Client client;

	public StartUI() {
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public StartUI(Client client, boolean show) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(show);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * setUpFrame() sets up the window to have fixed size and be centered on the
	 * users' computer screen.
	 */
	private void setUpFrame() {
		frame = new JFrame("Start Menu - " + client.user.getNickname());
		frame.setSize(400, 300);
		frame.setMaximumSize(new Dimension(400, 550));
		frame.setMinimumSize(new Dimension(400, 550));
		frame.setResizable(false);
		frame.setLayout(null);
		centerFrame(frame);
	}

	/**
	 * setUpFrameContent() adds all the appropriate fields for the start menu.
	 * Includes field to enter nickname of player to invite, invite button, and
	 * start button
	 */
	private void setUpFrameContent() {
		nicknameEntry.setBounds(175, 60, 150, 25);
		gameIDEntry.setBounds(175, 90, 150, 25);

		inviteButton = createButton("Invites", 125, 120, 150, 25);
		accountButton = createButton("Account Settings", 125, 210, 150, 25);
		quitButton = createButton("Quit", 125, 240, 150, 25);

		responseLabel = new JLabel(START_TEXT);
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 180, 370, 25);
		quitButton = new JButton("Quit");
		quitButton.setBounds(130, 210, 150, 25);

		this.gameList = new JTable();
		this.games.setColumnIdentifiers(this.columnNames);
		this.gameList.setModel(games);
		this.gameList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (gameList.getSelectedRow() == -1) {
						// No selection, disable fire button.
						startGameButton.setEnabled(false);

					} else {
						// Selection, enable the fire button.
						startGameButton.setEnabled(true);
					}
				}
			}
		});

		JScrollPane listScroller = new JScrollPane(this.gameList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(25, 275, 350, 75);

		gamesLabel = new JLabel("Games:");
		gamesLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		gamesLabel.setBounds(10, 250, 75, 25);

		startGameButton = new JButton("Resume Game");
		startGameButton.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		startGameButton.setBounds(125, 360, 150, 25);
		startGameButton.setEnabled(false);
		
		refreshGamesButton = new JButton("Refresh Games");
		refreshGamesButton.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		refreshGamesButton.setBounds(125, 390, 150, 25);

		addInviteActionListener();
		addUserAccountActionListener();
		addStartGameActionListener();
		addRefreshGamesActionListener();
		addQuitActionListener();
		getUserGames();

		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(createBoundedJLabel("Nickname", 16, 75, 60, 100, 25));
		frame.add(nicknameEntry);
		frame.add(createBoundedJLabel("Game ID", 16, 75, 90, 100, 25));
		frame.add(gameIDEntry);
		frame.add(createBoundedJLabel("Enter a nickname and ID.", 12, 10, 30, 375, 25));
		frame.add(inviteButton);

		JPanel invitePanel = new JPanel();
		invitePanel.setLayout(new BoxLayout(invitePanel, BoxLayout.LINE_AXIS));
		invitePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		invitePanel.add(Box.createHorizontalGlue());
		invitePanel.add(responseLabel);
		invitePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		invitePanel.add(Box.createRigidArea(new Dimension(10, 0)));

		frame.add(responseLabel);

		frame.add(accountButton);
		frame.add(quitButton);
		frame.add(gamesLabel);
		frame.add(listScroller);
		frame.add(startGameButton);
		frame.add(refreshGamesButton);
	}

	/**
	 * Server protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addInviteActionListener() {
		inviteButton.addActionListener(e -> {
			InviteUI invite = new InviteUI(client);
		});
	}

	public void getUserGames() {
		if (this.client.user.getNickname() != null) {
			try {
				String gamesRequest = "games " + this.client.user.getNickname() + "\n";
				client.request(gamesRequest);
			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with games requst.");
			}
		}
	}

	/**
	 * Server protocol: invite accept [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addAcceptInviteActionListener() {
		acceptInviteBtn.addActionListener(e -> {
			try {
				String opponent = responseLabel.getText().split(",")[1].trim();
				String game = responseLabel.getText().split(",")[2].trim();
				String inviteRequest = "invite accepted " + client.user.getNickname() + " " + opponent + " " + game
						+ "\n";
				client.request(inviteRequest);
			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with invitation.");
			}
		});
	}

	/**
	 * Server protocol: invite reject [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addRejectInviteActionListener() {
		rejectInviteBtn.addActionListener(e -> {
			try {
				String opponent = responseLabel.getText().split(",")[1].trim();
				String game = responseLabel.getText().split(",")[2].trim();
				String inviteRequest = "invite rejected " + client.user.getNickname() + " " + opponent + " " + game
						+ "\n";
				client.request(inviteRequest);
			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with invitation.");
			}
		});
	}

	/**
	 * addStartGameActionListener() sets up the action listener for the starting a
	 * game. Protocol: game player1nickname player2nickname gameID
	 */
	private void addStartActionListener() {
		startButton.addActionListener(e -> {
			try {
				if (processInputs()) {
					String gameRequest = "game " + client.user.getNickname() + " " + opponentNickname + " " + gameID
							+ "\n";
					client.request(gameRequest);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with starting game.");
			}
		});
	}

	private void addStartGameActionListener() {
		this.startGameButton.addActionListener(e -> {
			Game game = this.activeGames.get(this.gameList.getSelectedRow());
			this.client.openGame(game);
		});
	}
	
	private void addRefreshGamesActionListener() {
		this.refreshGamesButton.addActionListener(e -> {
			this.getUserGames();
		});
	}

	/**
	 * addQuitActionListener() sets up the action listener for the Quit button. When
	 * clicked, it disconnects the client and exits the system.
	 */
	private void addQuitActionListener() {
		quitButton.addActionListener(e -> {
			try {
				client.request("quit\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
	}

	/**
	 * addUserAccountActionListener() sets up the action listener for the Quit
	 * button. When clicked, it disconnects the client and exits the system.
	 */
	private void addUserAccountActionListener() {
		accountButton.addActionListener(e -> deleteUserUI = new DeleteUserUI(client));
	}

	/**
	 * Helper function that returns some basic error messages to the UI.
	 */
	private boolean processInputs() {
		opponentNickname = nicknameEntry.getText();
		gameID = gameIDEntry.getText();
		boolean o = !opponentNickname.matches("[a-zA-Z0-9!@#$%^&*()]*");
		boolean g = !gameID.matches("[a-zA-Z0-9!@#$%^&*()]*");
		if (opponentNickname.isEmpty()) {
			responseLabel.setText("Please enter an opponent nickname.");
			return false;
		} else if (opponentNickname.equals(client.user.getNickname())) {
			responseLabel.setText("Please enter an opponent nickname.");
			return false;
		} else if (gameID.isEmpty()) {
			responseLabel.setText("Please enter a game ID.");
			return false;
		} else if (o || g) {
			responseLabel.setText("Unusual characters, please check and try again.");
			return false;
		} else {
			return true;
		}
	}

	public void clearFields() {
		nicknameEntry.setText("");
		gameIDEntry.setText("");
		responseLabel.setText(START_TEXT);
	}

	public void removeDeleteUserFrame() {
		deleteUserUI.frame.dispose();
	}

	/**
	 * Helper function that adds a game to the UI.
	 */
	public void addGame(Game game) {
		String turn = game.isPlayersTurn() ? this.client.getUser().getNickname() : game.getOpponent();
		this.games.addRow(new Object[] { game.getGameID(), game.getOpponent(), turn, game.isCheck(game.getPlayerColor()) });
		this.activeGames.add(game);
	}

	/**
	 * Helper function that clears the games from the UI.
	 */
	public void clearGames() {
		DefaultTableModel model = (DefaultTableModel) this.gameList.getModel();
		model.setRowCount(0);
		this.activeGames = new ArrayList<Game>();
	}
s}
