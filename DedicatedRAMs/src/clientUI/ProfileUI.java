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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import client.Client;

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
	private JPasswordField passwordEntry = new JPasswordField();
	private JTextField confirmEntry = new JTextField();
	private JButton search;
	private JButton cancel;

	private Client client;

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
	}

	/**
	 * addSearchActionListener() sets up the action listener for the Search button.
	 */
	private void addSearchActionListener() {
		search.addActionListener(e -> {
			int opt = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete your information?\nThis action cannot be undone.", "Delete User",
					JOptionPane.YES_NO_OPTION);
			if (opt == JOptionPane.YES_OPTION) {
				if (confirmEntry.getText().equals("DELETE") && isNicknameValid() && isPasswordValid()) {
					try {
						String deleteUserRequest = "deleteuser " + nicknameEntry.getText() + " "
								+ new String(passwordEntry.getPassword()) + "\n";
						client.request(deleteUserRequest);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else
					showMessageDialog(frame, "Invalid password or confirmation of DELETE.", "Delete User",
							JOptionPane.ERROR_MESSAGE);
			}
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
	 * isPasswordValid() checks to make sure that the password entered is not blank
	 * and has the correct characters to avoid SQL problems.
	 * 
	 * @return - true if password only contains letters, numbers, and a few certain
	 *         special characters. a-zA-Z0-9!@#$%^&*(), false otherwise.
	 */
	private boolean isPasswordValid() {
		if (passwordEntry.getPassword() == null)
			return false;
		String password = new String(passwordEntry.getPassword());
		return password.matches("[a-zA-Z0-9!@#$%^&*()]*");
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
		frame = new JFrame("User Stats " + client.user.getNickname());
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
		cancel = createButton("Cancel", 280, 450, 100, 25);
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
		
        String[][] stats = { 
                { "100", "20", "1" }
        }; 
        
        String[][] nostats = { 
                {"", "", ""}
        }; 
        
        
        String[][] history = { 
                { "1", "bat", "cat", "bat", "cat", "N"  },
                { "23", "bat", "jimmy", "bat", "jimmy", "N" },
                { "100", "harriet", "bat", "bat", "harriet", "N"  },
                { "12", "bat", "ben" , "bat", "ben", "N" },
                { "12", "ben", "bat" , "ben", "bat", "N" },
                { "50", "gregorythegreatestplayer", "bat", "gregorythegreatestplayer", "bat", "N"  },
                { "55", "gregorythegreatestplayer2", "bat", "", "", "Y" },
                { "56", "gregorythegreatestplayer2", "bat", "", "", "" },
                { "1000", "gregorythegreatestplayer2", "bat", "", "", "" }
        };
        
        String[] statsColumnNames = { "Wins", "Losses", "Draws" };
        String[] historyColumnNames = { "GameID", "Player1", "Player2", "Win", "Loss", "Draw" };

		frame.add(createBoundedJLabel("Your Stats", 16, 75, 120, 150, 25));

        JTable statsTable = new JTable(stats, statsColumnNames); 
        statsTable.setFillsViewportHeight(true);
        frame.add(statsTable.getTableHeader());
        JScrollPane statsPane = new JScrollPane(statsTable);
        statsPane.setBounds(75, 150, 500, 50); 
        frame.add(statsPane);

		frame.add(createBoundedJLabel("Match History", 16, 75, 210, 150, 25));
        JTable historyTable = new JTable(history, historyColumnNames); 
        historyTable.setFillsViewportHeight(true);
        frame.add(historyTable.getTableHeader());
        JScrollPane historyPane = new JScrollPane(historyTable);
        historyPane.setBounds(75, 240, 500, 100); 
        frame.add(historyPane);
        
		nicknameEntry.setBounds(330, 350, 150, 25);
		frame.add(createBoundedJLabel("Lookup Player Stats by Nickname:", 16, 75, 350, 300, 25));
        frame.add(nicknameEntry);
        
        JTable otherStatsTable = new JTable(nostats, statsColumnNames); 
        otherStatsTable.setFillsViewportHeight(true);
        frame.add(otherStatsTable.getTableHeader());
        JScrollPane otherStatsPane = new JScrollPane(otherStatsTable);
        otherStatsPane.setBounds(75, 380, 500, 50); 
        frame.add(otherStatsPane);
	}

	public static void main(String[] args) {
		Client client = new Client("localhost", 8822);
		client.user.setNickname("bat");
		client.user.setEmail("fake@fakefakefake.com");

		new ProfileUI(client);

	}
}
