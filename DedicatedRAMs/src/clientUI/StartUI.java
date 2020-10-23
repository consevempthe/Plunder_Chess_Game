package clientUI;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;
import client.IllegalMoveException;
import client.IllegalPositionException;

public class StartUI {
	private JFrame frame;
	private JLabel titleLbl;
	private JTextField nicknameEntry;
	private JLabel nicknameLbl;
	private JTextField gameIDEntry;
	private JLabel gameIDLbl;
	private JLabel inviteLbl;
	private JButton inviteBtn;
	private JLabel responseLbl;
	private JButton startBtn;
	private JButton quitBtn;
	private String inviteStatus;
	private String opponentNickname;
	private String gameID;

	private Client client;
	
	public StartUI() {
		//this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	/**
	 * setUpFrame() sets up the window to have fixed size and be centered on the users' computer screen.
	 */
	private void setUpFrame() {
		frame = new JFrame("Start Menu");
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
	 * Includes field to enter nickname of player to invite, invite button, and start button
	 */
	private void setUpFrameContent() {
		titleLbl = new JLabel("X-Game: Plunder Chess");
		titleLbl.setSize(400, 30);
		titleLbl.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		titleLbl.setVerticalAlignment(SwingConstants.TOP);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nicknameLbl = new JLabel("Nickname");
		nicknameLbl.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		nicknameLbl.setBounds(75, 60, 100, 25);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		gameIDLbl = new JLabel("Game ID");
		gameIDLbl.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		gameIDLbl.setBounds(75, 90, 100, 25);
		gameIDEntry = new JTextField();
		gameIDEntry.setBounds(175, 90, 150, 25);
		inviteLbl = new JLabel("To invite someone to play, enter a nickname and ID. Click the Invite button");
		inviteLbl.setFont(new Font ("TimesRoman", Font.BOLD, 12));
		inviteLbl.setBounds(10, 30, 375, 25);
		inviteBtn = new JButton("Invite");
		inviteBtn.setBounds(125, 120, 150, 25);
		responseLbl = new JLabel("Waiting for player to take action...");
		responseLbl.setFont(new Font ("TimesRoman", Font.ITALIC, 12));
		responseLbl.setBounds(10, 150, 370, 25);
		startBtn = new JButton("Start Game");
		startBtn.setBounds(125, 180, 150, 25);
		quitBtn = new JButton("Quit");
		quitBtn.setBounds(125, 210, 150, 25);
		addInviteActionListener();
		addQuitActionListener();
		addStartActionListener();
		frame.add(titleLbl);
		frame.add(nicknameLbl);
		frame.add(nicknameEntry);
		frame.add(gameIDLbl);
		frame.add(gameIDEntry);
		frame.add(inviteLbl);
		frame.add(inviteBtn);
		frame.add(responseLbl);
		frame.add(startBtn);
		frame.add(quitBtn);
	}
	
	private void addStartActionListener() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Server protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]
	 */
	private void addInviteActionListener() {
		inviteBtn.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
				  if(true) {
					  inviteStatus = "accepted";
					  opponentNickname = nicknameEntry.getText();
					  gameID = gameIDEntry.getText();
					  nicknameEntry.setText("");
					  gameIDEntry.setText("");

					  responseLbl.setText("Challenge " + inviteStatus + " by " + opponentNickname + " (ID: " + gameID + "). Click Start Game to begin.");
//					  try {
//						  String loginRequest = "invite add " + client.user.getNickname() + " " + opponentNickname + "\n";
//						client.request(loginRequest);
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
				  }
				  else
					  showMessageDialog(frame, "Invalid nickname or password.\nPlease make sure your login information was entered correctly!", "Invalid Login", 0);
			  }
			});
	}
	/**
	 * addQuitActionListener() sets up the action listener for the Quit button.
	 * When clicked, it disconnects the client and exits the system.
	 */
	private void addQuitActionListener() {
		quitBtn.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  try {
				client.request("quit\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			  System.exit(0);
		  }
		});
	}
	
	public static void main(String[] args) {
		StartUI s = new StartUI();
	}
	
}
