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
	private JTable statsTable;
	private JTable historyTable;
	private JTable otherStatsTable;
	private DefaultTableModel userStatsModel;
	private DefaultTableModel historyModel;
	private DefaultTableModel lookupStatsModel;
	private String[] statsColumnNames = { "Wins", "Losses", "Draws" };
    private String[] historyColumnNames = { "GameID", "Player1", "Player2", "Win", "Loss", "Draw" };
    protected Object[][] history;
    private Object[][] userStats;
    private Object[][] otherStats;

	/**
	 * This constructor sets up the UI and the Client that is passed as a parameter.
	 * 
	 * @param client - Client passed to LoginUI.
	 */
	public ProfileUI(Client client) {
		this.client = client;
		this.client.profileUI = this;

		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.client.request("searchuserstats " + client.user.getNickname() +"\n");
			this.client.request("matchhistory " + client.user.getNickname() +"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		otherStats = new Object[][] {{"", "", ""}};
        userStatsModel = new DefaultTableModel(userStats, statsColumnNames);
        historyModel = new DefaultTableModel(history, historyColumnNames);
        lookupStatsModel = new DefaultTableModel(otherStats, statsColumnNames);
        
		frame.add(createBoundedJLabel("Your Stats", 16, 75, 120, 150, 25));
		statsTable = new JTable(userStatsModel);
        statsTable.setFillsViewportHeight(true);
        frame.add(statsTable.getTableHeader());
        JScrollPane statsPane = new JScrollPane(statsTable);
        statsPane.setBounds(75, 150, 500, 50); 
        frame.add(statsPane);

		frame.add(createBoundedJLabel("Match History", 16, 75, 210, 150, 25));
		historyTable = new JTable(historyModel);
        historyTable.setFillsViewportHeight(true);
        frame.add(historyTable.getTableHeader());
        JScrollPane historyPane = new JScrollPane(historyTable);
        historyPane.setBounds(75, 240, 500, 100); 
        frame.add(historyPane);
        
		nicknameEntry.setBounds(330, 350, 150, 25);
		frame.add(createBoundedJLabel("Lookup Player Stats by Nickname:", 16, 75, 350, 300, 25));
        frame.add(nicknameEntry);
        
        otherStatsTable = new JTable(lookupStatsModel);
        otherStatsTable.setFillsViewportHeight(true);
        frame.add(otherStatsTable.getTableHeader());
        JScrollPane otherStatsPane = new JScrollPane(otherStatsTable);
        otherStatsPane.setBounds(75, 380, 500, 50); 
        frame.add(otherStatsPane);
	}

	public void setUserStats(Object [][] stats) {
		this.userStats = stats;
		userStatsModel.setRowCount(0);
		userStatsModel.addRow(stats[0]);
		userStatsModel.fireTableDataChanged();
	}

	public void setHistory(Object [][] hist) {
		this.history = hist;
		historyModel.setRowCount(0);
		for(int i = 0; i < hist.length; i++ ){

			historyModel.addRow(hist[i]);
		}
		historyModel.fireTableDataChanged();
	}

	public void setOtherStats(Object [][] stats) {
		this.otherStats = stats;
		lookupStatsModel.setRowCount(0);
		lookupStatsModel.addRow(stats[0]);
		lookupStatsModel.fireTableDataChanged();
	}
}
