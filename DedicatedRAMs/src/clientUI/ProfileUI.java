package clientUI;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Client;
import server.RemoteSSHConnector;

/**
 * The ProfileUI class is a simple UI for players to view their stats and
 * details.
 * 
 * @author DedicatedRAMs Team
 *
 */
public class ProfileUI extends FrameUI {
	public JFrame frame;
	private JTextField nicknameEntry = new JTextField();
	private JButton search;
	private JButton cancel;
	private Client client;
	private DefaultTableModel userStatsModel;
	private DefaultTableModel historyModel;
	private DefaultTableModel lookupStatsModel;
	private String[] statsColumnNames = { "Wins", "Losses", "Draws" };
    private String[] historyColumnNames = { "GameID", "Player1", "Player2", "Win", "Loss", "Draw" };
    private Object[][] history;
    private Object[][] userStats;
    private Object[][] otherStats;

	/**
	 * This constructor sets up the UI and the Client that is passed as a parameter.
	 * 
	 * @param client - Client passed to LoginUI.
	 */
	public ProfileUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userStats = client.user.getUserStats();
	}

	/**
	 * addSearchActionListener() sets up the action listener for the Search button.
	 */
	private void addSearchActionListener() {
		search.addActionListener(e -> {

				if (isNicknameValid()) {
					try {
						String searchUserRequest = "searchuserstats " + nicknameEntry.getText() + "\n";
						client.request(searchUserRequest);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else
					showMessageDialog(frame, "Invalid nickname, please try again.", "Lookup User Stats",
							JOptionPane.ERROR_MESSAGE);
		});
	}

	/**
	 * addCancelActionListener() sets up the action listener for the Cancel button.
	 * When clicked, disposes the frame.
	 */
	private void addCancelActionListener() {
		cancel.addActionListener(e -> frame.dispose());
	}

	/**
	 * isNicknameValid() checks to make sure that the nickname entered is not blank
	 * and has only alphanumeric values.
	 * 
	 * @return - true if nickname only contains letters and numbers, false
	 *         otherwise.
	 */
	private boolean isNicknameValid() {
		String text;
		if (nicknameEntry.getText() == null)
			return false;

		text = nicknameEntry.getText();
		return text.matches("[a-zA-Z0-9]*");
	}

	/**
	 * setUpFrame() sets up the Login Screen to have fixed size and be centered on
	 * the users' computer screen.
	 */
	private void setUpFrame() {
		frame = new JFrame("User Profile " + client.user.getNickname());
		frame.setSize(650, 600);
		frame.setMaximumSize(new Dimension(650, 600));
		frame.setMinimumSize(new Dimension(650, 600));
		frame.setResizable(false);
		frame.pack();
		frame.setLayout(null);
		centerFrame(frame);
	}

	/**
	 * setUpFrameContent() adds all the appropriate fields for the delete user page.
	 */
	private void setUpFrameContent() {
		cancel = createButton("Back", 280, 450, 100, 25);
		addCancelActionListener();
		frame.add(cancel);
		
		search = createButton("Search", 480, 350, 100, 25);
		addSearchActionListener();
		frame.add(search);
		
		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(createBoundedJLabel("Nickname: ", 16, 75, 60, 100, 25));
		frame.add(createBoundedJLabel(client.user.getNickname(), 16, 175, 60, 100, 25));
		frame.add(createBoundedJLabel("Email:", 16, 75, 90, 100, 25));
		frame.add(createBoundedJLabel(client.user.getEmail(), 12, 175, 90, 150, 25));
		
//        String[][] nostats = { 
//                {"1", "2", "3"}
//        }; 
//
//        String[][] history = { 
//                { "1", "bat", "cat", "bat", "cat", "N"  },
//                { "23", "bat", "jimmy", "bat", "jimmy", "N" },
//                { "100", "harriet", "bat", "bat", "harriet", "N"  },
//                { "12", "bat", "ben" , "bat", "ben", "N" },
//                { "12", "ben", "bat" , "ben", "bat", "N" },
//                { "50", "gregorythegreatestplayer", "bat", "gregorythegreatestplayer", "bat", "N"  },
//                { "55", "gregorythegreatestplayer2", "bat", "", "", "Y" },
//                { "56", "gregorythegreatestplayer2", "bat", "", "", "" },
//                { "1000", "gregorythegreatestplayer2", "bat", "", "", "" }
//        };
		
		otherStats = new Object[][] {{"", "", ""}};
        userStatsModel = new DefaultTableModel(client.user.getUserStats(), statsColumnNames);
        historyModel = new DefaultTableModel(history, historyColumnNames); 
        lookupStatsModel = new DefaultTableModel(otherStats, statsColumnNames);
        
		frame.add(createBoundedJLabel("Your Stats", 16, 75, 120, 150, 25));
        JTable statsTable = new JTable(userStatsModel); 
        statsTable.setFillsViewportHeight(true);
        frame.add(statsTable.getTableHeader());
        JScrollPane statsPane = new JScrollPane(statsTable);
        statsPane.setBounds(75, 150, 500, 50); 
        frame.add(statsPane);

		frame.add(createBoundedJLabel("Match History", 16, 75, 210, 150, 25));
        JTable historyTable = new JTable(historyModel); 
        historyTable.setFillsViewportHeight(true);
        frame.add(historyTable.getTableHeader());
        JScrollPane historyPane = new JScrollPane(historyTable);
        historyPane.setBounds(75, 240, 500, 100); 
        frame.add(historyPane);
        
		nicknameEntry.setBounds(330, 350, 150, 25);
		frame.add(createBoundedJLabel("Lookup Player Stats by Nickname:", 16, 75, 350, 300, 25));
        frame.add(nicknameEntry);
        
        JTable otherStatsTable = new JTable(lookupStatsModel); 
        otherStatsTable.setFillsViewportHeight(true);
        frame.add(otherStatsTable.getTableHeader());
        JScrollPane otherStatsPane = new JScrollPane(otherStatsTable);
        otherStatsPane.setBounds(75, 380, 500, 50); 
        frame.add(otherStatsPane);
	}

	public static void main(String[] args) {
	  	RemoteSSHConnector connector = new RemoteSSHConnector(8818, 8000, "concord.cs.colostate.edu", "localhost");
        connector.connect();
		Client client = new Client("localhost", 8818);

		
		//Client client = new Client("localhost", 8822);
		client.user.setNickname("cat");
		client.user.setEmail("fake@fakefakefake.com");

		new ProfileUI(client);

	}


}
