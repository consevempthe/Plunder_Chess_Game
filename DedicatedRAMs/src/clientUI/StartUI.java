package clientUI;

import client.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import client.GamesResponse.*;

public class StartUI {
	private JFrame frame;
	public JTextField nicknameEntry;
	public JTextField gameIDEntry;
	private JButton inviteButton;
	public JLabel responseLabel = new JLabel();
	public JButton startButton;
	private JButton quitButton;
	private JButton accountButton;
	public JButton acceptInviteBtn;
	public JButton rejectInviteBtn;
	public JLabel gamesLabel;
	private JList<String> gameList;
	private DefaultListModel<String> games = new DefaultListModel<>();
	private String opponentNickname;
	private String gameID;
	private final String START_TEXT = "Waiting for inputs...";
	private DeleteUserUI deleteUserUI;
	private JButton startGameButton;
	private ArrayList<Game> activeGames = new ArrayList<Game>();

	private Client client;

	public StartUI() {
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public StartUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * setUpFrame() sets up the window to have fixed size and be centered on the
	 * users' computer screen.
	 */
	private void setUpFrame() {
		frame = new JFrame("Start Menu - " + client.user.getNickname());
		frame.setSize(400, 300);
		frame.setMaximumSize(new Dimension(400, 425));
		frame.setMinimumSize(new Dimension(400, 425));
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

		inviteButton = createButton("Invite", 125, 120, 150, 25);
		startButton = createButton("Start Game", 125, 150, 150, 25);
		startButton.setEnabled(false);
		accountButton = createButton("Account Settings", 125, 210, 150, 25);
		acceptInviteBtn = createButton("Accept", 240, 180, 75, 25);
		acceptInviteBtn.setVisible(false);
		rejectInviteBtn = createButton("Reject", 315, 180, 75, 25);
		rejectInviteBtn.setVisible(false);
		quitButton = createButton("Quit", 125, 240, 150, 25);


		responseLabel = new JLabel(START_TEXT);
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 180, 370, 25);
		quitButton = new JButton("Quit");
		quitButton.setBounds(125, 210, 150, 25);

		this.gameList = new JList<String>(games);
		this.gameList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.gameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.gameList.setVisibleRowCount(-1);
		this.gameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (gameList.getSelectedIndex() == -1) {
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
		listScroller.setBounds(125, 275, 150, 75);

		gamesLabel = new JLabel("Games:");
		gamesLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		gamesLabel.setBounds(10, 250, 75, 25);
		
		startGameButton = new JButton("Resume Game");
		startGameButton.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		startGameButton.setBounds(125, 360, 150, 25);
		startGameButton.setEnabled(false);

		addInviteActionListener();
		addStartActionListener();
		addUserAccountActionListener();
		addStartGameActionListener();
		addQuitActionListener();
		addAcceptInviteActionListener();
		addRejectInviteActionListener();

		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(createBoundedJLabel("Nickname",16, 75, 60, 100, 25));
		getUserGames();
		frame.add(titleLbl);
		frame.add(nicknameLbl);
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
		invitePanel.add(acceptInviteBtn);
		invitePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		invitePanel.add(rejectInviteBtn);

		frame.add(responseLabel);
		frame.add(acceptInviteBtn);
		frame.add(rejectInviteBtn);

		frame.add(startButton);
		frame.add(accountButton);
		frame.add(quitButton);
		frame.add(gamesLabel);
		frame.add(listScroller);
		frame.add(startGameButton);
	}

	/**
	 * Server protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addInviteActionListener() {
		inviteButton.addActionListener(e -> {
			try {
				if (processInputs()) {
					String inviteRequest = "invite add " + client.user.getNickname() + " " + opponentNickname + " "
							+ gameID + "\n";
					client.request(inviteRequest);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with invitation.");
			}
		});
	}

	private void getUserGames() {
		try {
			String gamesRequest = "games " + this.client.user.getNickname() + "\n";
			client.request(gamesRequest);
		} catch (IOException e1) {
			e1.printStackTrace();
			responseLabel.setText("Error with games requst.");
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
			try {
				Game game = this.activeGames.get(this.gameList.getSelectedIndex());
				String nickname = client.user.getNickname();
				String opponent = game.player1 == nickname ? game.player2 : game.player1;
				//if (this.activeGames.contains(this.gameList.getSelectedValue())) {
					String gameRequest = "game " + nickname + " " + opponent + " " + game.gameId
							+ "\n";
					client.request(gameRequest);
				//}

			} catch (IOException e1) {
				e1.printStackTrace();
				responseLabel.setText("Error with starting game.");
			}
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
	 * addUserAccountActionListener() sets up the action listener for the Quit button. When
	 * clicked, it disconnects the client and exits the system.
	 */
	private void addUserAccountActionListener(){
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

	public void addGame(Game game)
	{
		this.games.addElement(game.gameId);
		this.activeGames.add(game);
	}
}
