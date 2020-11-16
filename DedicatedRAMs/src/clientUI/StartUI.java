package clientUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.*;

import client.Client;

public class StartUI {
	private JFrame frame;
	private JLabel titleLbl;
	public JTextField nicknameEntry;
	private JLabel nicknameLbl;
	public JTextField gameIDEntry;
	private JLabel gameIDLbl;
	private JLabel inviteLbl;
	private JButton inviteBtn;
	public JLabel responseLbl;
	public JButton acceptInviteBtn;
	public JButton rejectInviteBtn;
	public JButton startBtn;
	private JButton quitBtn;
	private String opponentNickname;
	private String gameID;
	private String startText = "Waiting for inputs...";

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
		titleLbl = new JLabel("X-Game: Plunder Chess");
		titleLbl.setSize(400, 30);
		titleLbl.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		titleLbl.setVerticalAlignment(SwingConstants.TOP);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nicknameLbl = new JLabel("Nickname");
		nicknameLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));
		nicknameLbl.setBounds(75, 60, 100, 25);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		gameIDLbl = new JLabel("Game ID");
		gameIDLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));
		gameIDLbl.setBounds(75, 90, 100, 25);
		gameIDEntry = new JTextField();
		gameIDEntry.setBounds(175, 90, 150, 25);
		inviteLbl = new JLabel("Enter a nickname and ID.");
		inviteLbl.setFont(new Font("TimesRoman", Font.BOLD, 12));
		inviteLbl.setBounds(10, 30, 375, 25);
		inviteBtn = new JButton("Invite");
		inviteBtn.setBounds(125, 120, 150, 25);
		startBtn = new JButton("Start Game");
		startBtn.setBounds(125, 150, 150, 25);
		startBtn.setEnabled(false);
		acceptInviteBtn = new JButton("Accept");
		acceptInviteBtn.setBounds(240, 180, 75, 25);
		acceptInviteBtn.setVisible(false);
		rejectInviteBtn = new JButton("Reject");
		rejectInviteBtn.setBounds(315, 180, 75, 25);
		rejectInviteBtn.setVisible(false);
		responseLbl = new JLabel(startText);
		responseLbl.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLbl.setBounds(10, 180, 370, 25);
		quitBtn = new JButton("Quit");
		quitBtn.setBounds(125, 210, 150, 25);
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
		frame.add(inviteBtn);

		JPanel invitePanel = new JPanel();
		invitePanel.setLayout(new BoxLayout(invitePanel, BoxLayout.LINE_AXIS));
		invitePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		invitePanel.add(Box.createHorizontalGlue());
		invitePanel.add(responseLbl);
		invitePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		invitePanel.add(acceptInviteBtn);
		invitePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		invitePanel.add(rejectInviteBtn);

		frame.add(responseLbl);
		frame.add(acceptInviteBtn);
		frame.add(rejectInviteBtn);

		frame.add(startBtn);
		frame.add(quitBtn);
	}

	/**
	 * Server protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addInviteActionListener() {
		inviteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (processInputs()) {
						String inviteRequest = "invite add " + client.user.getNickname() + " " + opponentNickname + " "
								+ gameID + "\n";
						client.request(inviteRequest);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLbl.setText("Error with invitation.");
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
					String opponent = responseLbl.getText().split(",")[1].trim();
					String game = responseLbl.getText().split(",")[2].trim();
					String inviteRequest = "invite accepted " + client.user.getNickname() + " " + opponent + " " + game
							+ "\n";
					client.request(inviteRequest);
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLbl.setText("Error with invitation.");
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
					String opponent = responseLbl.getText().split(",")[1].trim();
					String game = responseLbl.getText().split(",")[2].trim();
					String inviteRequest = "invite rejected " + client.user.getNickname() + " " + opponent + " " + game
							+ "\n";
					client.request(inviteRequest);
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLbl.setText("Error with invitation.");
				}
			}
		});
	}

	/**
	 * addStartGameActionListener() sets up the action listener for the starting a
	 * game. Protocol: game player1nickname player2nickname gameID
	 */
	private void addStartActionListener() {
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (processInputs()) {
						String gameRequest = "game " + client.user.getNickname() + " " + opponentNickname + " " + gameID
								+ "\n";
						client.request(gameRequest);
					}

				} catch (IOException e1) {
					e1.printStackTrace();
					responseLbl.setText("Error with starting game.");
				}
			}
		});
	}

	/**
	 * addQuitActionListener() sets up the action listener for the Quit button. When
	 * clicked, it disconnects the client and exits the system.
	 */
	private void addQuitActionListener() {
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.request("quit\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
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
		if (opponentNickname.isEmpty()) {
			responseLbl.setText("Please enter an opponent nickname.");
			return false;
		} else if (opponentNickname.equals(client.user.getNickname())) {
			responseLbl.setText("Please enter an opponent nickname.");
			return false;
		} else if (gameID.isEmpty()) {
			responseLbl.setText("Please enter a game ID.");
			return false;
		} else if (o || g) {
			responseLbl.setText("Unusual characters, please check and try again.");
			return false;
		} else {
			return true;
		}
	}

	public void clearFields() {
		nicknameEntry.setText("");
		gameIDEntry.setText("");
		responseLbl.setText(startText);
	}
}
