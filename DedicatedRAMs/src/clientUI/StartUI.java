package clientUI;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import client.Client;

public class StartUI {
	private JFrame frame;
	public JTextField nicknameEntry;
	public JTextField gameIDEntry;
	private JButton inviteButton;
	public JLabel responseLabel;
	public JButton startButton;
	private JButton quitButton;
	public JButton acceptInviteBtn;
	public JButton rejectInviteBtn;
	private String opponentNickname;
	private String gameID;
	private final String START_TEXT = "Waiting for inputs...";

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
		centerFrame();
	}

	/**
	 * centerFrame() centers the frame on the users' screen.
	 */
	private void centerFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		frame.setLocation(x, y);
	}

	/**
	 * setUpFrameContent() adds all the appropriate fields for the start menu.
	 * Includes field to enter nickname of player to invite, invite button, and
	 * start button
	 */
	private void setUpFrameContent() {
		JLabel titleLbl = new JLabel("X-Game: Plunder Chess");
		titleLbl.setSize(400, 30);
		titleLbl.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		titleLbl.setVerticalAlignment(SwingConstants.TOP);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel nicknameLbl = new JLabel("Nickname");
		nicknameLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));
		nicknameLbl.setBounds(75, 60, 100, 25);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		JLabel gameIDLbl = new JLabel("Game ID");
		gameIDLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));
		gameIDLbl.setBounds(75, 90, 100, 25);
		gameIDEntry = new JTextField();
		gameIDEntry.setBounds(175, 90, 150, 25);
		JLabel inviteLbl = new JLabel("Enter a nickname and ID.");
		inviteLbl.setFont(new Font("TimesRoman", Font.BOLD, 12));
		inviteLbl.setBounds(10, 30, 375, 25);
		inviteButton = new JButton("Invite");
		inviteButton.setBounds(125, 120, 150, 25);
		startButton = new JButton("Start Game");
		startButton.setBounds(125, 150, 150, 25);

		startButton.setEnabled(false);
		acceptInviteBtn = new JButton("Accept");
		acceptInviteBtn.setBounds(240, 180, 75, 25);
		acceptInviteBtn.setVisible(false);
		rejectInviteBtn = new JButton("Reject");
		rejectInviteBtn.setBounds(315, 180, 75, 25);
		rejectInviteBtn.setVisible(false);

		responseLabel = new JLabel(START_TEXT);
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 180, 370, 25);
		quitButton = new JButton("Quit");
		quitButton.setBounds(125, 210, 150, 25);
		addInviteActionListener();
		addStartActionListener();
		addQuitActionListener();
		addAcceptInviteActionListener();
		addRejectInviteActionListener();
		frame.add(titleLbl);
		frame.add(nicknameLbl);
		frame.add(nicknameEntry);
		frame.add(gameIDLbl);
		frame.add(gameIDEntry);
		frame.add(inviteLbl);
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
		frame.add(quitButton);
	}

	/**
	 * Server protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addInviteActionListener() {
		inviteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			} 
		});
	}

	/**
	 * Server protocol: invite accept [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addAcceptInviteActionListener() {
		acceptInviteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
	}

	/**
	 * Server protocol: invite reject [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addRejectInviteActionListener() {
		rejectInviteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
}
