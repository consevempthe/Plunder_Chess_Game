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
	private JButton inviteButton;
	public JLabel responseLabel = new JLabel();
	private JButton quitButton;
	private JButton accountButton;
	private JButton profileButton;
	public JLabel gamesLabel;
	private final String START_TEXT = "";
	private DeleteUserUI deleteUserUI;
	private ProfileUI profileUI;
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
		frame.setMaximumSize(new Dimension(400, 400));
		frame.setMinimumSize(new Dimension(400, 400));
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

		inviteButton = createButton("Invites", 125, 45, 150, 25);
		accountButton = createButton("Settings", 60, 330, 90, 25);
		profileButton = createButton("Profile", 150, 330, 90, 25);
		quitButton = createButton("Quit", 240, 330, 90, 25);

		gamesLabel = new JLabel("Games:");
		gamesLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		gamesLabel.setBounds(10, 120, 75, 25);

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
		listScroller.setBounds(25, 145, 350, 100);

		startGameButton = new JButton("Resume Game");
		startGameButton.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		startGameButton.setBounds(125, 270, 150, 25);
		startGameButton.setEnabled(false);
		
		refreshGamesButton = new JButton("Refresh Games");
		refreshGamesButton.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		refreshGamesButton.setBounds(125, 300, 150, 25);
		
		responseLabel = new JLabel(START_TEXT);
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 250, 240, 25);

		addInviteActionListener();
		addUserAccountActionListener();
		addProfileActionListener();
		addStartGameActionListener();
		addRefreshGamesActionListener();
		addQuitActionListener();
		getUserGames();

		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(inviteButton);

		frame.add(responseLabel);

		frame.add(accountButton);
		frame.add(quitButton);
		frame.add(gamesLabel);
		frame.add(listScroller);
		frame.add(startGameButton);
		frame.add(profileButton);
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
	 * addProfileActionListener() sets up the action listener for the Quit button. When
	 * clicked, it disconnects the client and exits the system.
	 */
	private void addProfileActionListener(){
		profileButton.addActionListener(e -> setupProfile());
	}

	private void setupProfile() {
		profileUI = new ProfileUI(client);
		client.profileUI = profileUI;
	}

	public void clearFields() {
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
}
