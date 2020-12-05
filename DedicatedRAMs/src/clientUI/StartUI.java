package clientUI;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StartUI extends FrameUI {
	public JFrame frame;
	public JTextField nicknameEntry = new JTextField();
	public JTextField gameIDEntry = new JTextField();
	private JButton inviteButton;
	public JLabel responseLabel = new JLabel();
	public JButton startButton;
	private JButton quitButton;
	private JButton accountButton;
	public JButton acceptInviteBtn;
	public JButton rejectInviteBtn;
	private JButton profileButton;
	private String opponentNickname;
	private String gameID;
	private final String START_TEXT = "Waiting for inputs...";
	private DeleteUserUI deleteUserUI;
	private ProfileUI profileUI;
	

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
		frame.setMaximumSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(400, 300));
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
		accountButton = createButton("Account Settings", 50, 210, 150, 25);
		acceptInviteBtn = createButton("Accept", 240, 180, 75, 25);
		acceptInviteBtn.setVisible(false);
		rejectInviteBtn = createButton("Reject", 315, 180, 75, 25);
		rejectInviteBtn.setVisible(false);
		quitButton = createButton("Quit", 125, 240, 150, 25);
		profileButton = createButton("Profile", 200, 210, 150, 25);

		responseLabel = new JLabel(START_TEXT);
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 180, 370, 25);

		addInviteActionListener();
		addStartActionListener();
		addUserAccountActionListener();
		addQuitActionListener();
		addAcceptInviteActionListener();
		addRejectInviteActionListener();
		addProfileActionListener();


		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(createBoundedJLabel("Nickname",16, 75, 60, 100, 25));
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
		frame.add(profileButton);

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
				if(processInputs()) {
					String gameRequest = "game " + client.user.getNickname() + " " + opponentNickname + " " + gameID +  "\n";
					client.request(gameRequest);
				}

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
	 * addProfileActionListener() sets up the action listener for the Quit button. When
	 * clicked, it disconnects the client and exits the system.
	 */
	private void addProfileActionListener(){
		profileButton.addActionListener(e -> profileUI = new ProfileUI(client));
	}
	
	/**
	 * Helper function that returns some basic error messages to the UI.
	 */
	private boolean processInputs() {
		opponentNickname = nicknameEntry.getText();
		gameID = gameIDEntry.getText();
		boolean o = !opponentNickname.matches("[a-zA-Z0-9!@#$%^&*()]*");
		boolean g = !gameID.matches("[a-zA-Z0-9!@#$%^&*()]*");
		if(opponentNickname.isEmpty()) {
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

}
