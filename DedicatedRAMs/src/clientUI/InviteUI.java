package clientUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import client.Client;
import server.DatabaseAccessor;

public class InviteUI {

	// Necessary Frame Components
	private JFrame frame;
	private JTabbedPane tabbedPane;
	
	// Components for Invites List
	private JPanel invites;
	private JLabel nicknameLabel;
	private JTextField nicknameEntry;
	private JButton sendInviteBtn;
	private JButton refreshBtn;
	private JButton acceptInviteBtn;
	private JButton declineInviteBtn;
	private JTable inviteTable;
	private JLabel responseLabel;
	private int selectedInvite = -1;
	private DefaultTableModel tableModel = new DefaultTableModel(0,0) {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	JScrollPane inviteScrollPane;
	private Object[][] finalInvites;
	
	// Constants
	private static final String[] COLUMNS = new String[]{"Nickname", "Game ID", "Type"};
	private static final int PANE_WIDTH  = 400;
	private static final int PANE_HEIGHT = 600;
	
	// Other
	private Client client;
	private DatabaseAccessor db;
	private String opponentNickname;
	private String gameID;
	
	
	// Constructor for InviteUI
	// Opens a new frame to display the user's invites.
	public InviteUI(Client client) {
		this.client = client;
		this.db = new DatabaseAccessor();
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	// Sets up content of the frame
	private void setUpFrameContent() {
		invites = new JPanel();
		
		// Invites Component Setup
		invites.setSize(new Dimension(PANE_WIDTH, PANE_HEIGHT));
		invites.setLayout(null);
		nicknameLabel = new JLabel("Friend's Nickname:");
		nicknameLabel.setBounds(10, 478, 500, 100);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(130, 520, 150, 20);
		sendInviteBtn = new JButton("Send Invite");
		sendInviteBtn.setBounds(290, 520, 100, 20);
		refreshBtn = new JButton("Refresh!");
		refreshBtn.setBounds(10, 350, 370, 25);
		acceptInviteBtn = new JButton("Accept Invite");
		acceptInviteBtn.setBounds(10, 390, 120, 25);
		acceptInviteBtn.setEnabled(false);
		declineInviteBtn = new JButton("Decline Invite");
		declineInviteBtn.setBounds(255, 390, 120, 25);
		declineInviteBtn.setEnabled(false);
		setupInviteTable();
		addInviteActionListener();
		addAcceptInviteActionListener();
		addDeclineInviteActionListener();
		addRefreshActionListener();
		responseLabel = new JLabel("Enter a opponent nickname to invite...");
		responseLabel.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		responseLabel.setBounds(10, 500, 370, 25);
		invites.add(acceptInviteBtn);
		invites.add(declineInviteBtn);
		invites.add(refreshBtn);
		invites.add(sendInviteBtn);
		invites.add(nicknameLabel);
		invites.add(nicknameEntry);
		invites.add(responseLabel);
		
		inviteTable = new JTable();
		tableModel.setColumnIdentifiers(COLUMNS);
		inviteTable.setModel(tableModel);
		this.inviteTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					
					selectedInvite = inviteTable.getSelectedRow();
					
					if (selectedInvite == -1) {
						// No selection, disable fire button
						acceptInviteBtn.setEnabled(false);
						declineInviteBtn.setEnabled(false);
					} else {
						// Selection, enable the fire button.
						if((tableModel.getValueAt(selectedInvite, 2).equals("Accept or Decline"))){
							acceptInviteBtn.setEnabled(true);
							declineInviteBtn.setEnabled(true);
						} else {
							acceptInviteBtn.setEnabled(false);
							declineInviteBtn.setEnabled(false);
						}
					}
				}
			}
		});
		
		inviteScrollPane = new JScrollPane(inviteTable);
		inviteScrollPane.setSize(new Dimension(PANE_WIDTH, PANE_HEIGHT - 250));
		invites.add(inviteScrollPane);
		
		for(Object[] invite : finalInvites) {
			tableModel.addRow(invite);
		}
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 5, 400, 600);
		tabbedPane.add("Invites", invites);
		
		frame.add(tabbedPane);
	}
	
	// Helper method to validate user's inputs.
	private boolean processInputs() {
		Random random = new Random();
		ArrayList<String> queryResults = new ArrayList<String>();
		opponentNickname = nicknameEntry.getText();
		if(opponentNickname.length() == 0) {
			responseLabel.setText("The text box cannot be left blank.");
			return false;
		}
		this.db.clearQueryResults();
		try {
			queryResults = this.db.queryFromDatabase("select * from registration where nickname='" + opponentNickname + "';");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(queryResults.size() == 0) {
			responseLabel.setText("The nickname you entered does not exist!");
			return false;
		}
		gameID = "GAME" + random.nextInt(10000) + 1;
		boolean o = !opponentNickname.matches("[a-zA-Z0-9!@#$%^&*()]*");
		boolean g = !gameID.matches("[a-zA-Z0-9!@#$%^&*()]*");
		if(opponentNickname.isEmpty()) {
			responseLabel.setText("Please enter an opponent nickname.");
			return false;
		} else if (opponentNickname.equals(client.user.getNickname())) {
			responseLabel.setText("Please enter an opponent nickname.");
			return false;
		}  else if (o || g) {
			responseLabel.setText("Unusual characters, please check and try again.");
			return false;
		} else {
			return true;
		}
	}
	
	// Adds action listener for invite button
	private void addInviteActionListener() {
		this.sendInviteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (processInputs()) {
						String inviteRequest = "invite add " + client.user.getNickname() + " " + opponentNickname + " "
								+ gameID + "\n";
						client.request(inviteRequest);
						responseLabel.setText("Invite sent!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLabel.setText("Error sending invitation.");
				}
			}
		});
	}
	
	// Adds action listener for refresh button
	private void addRefreshActionListener() {
		this.refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tableModel.setRowCount(0);
				setupInviteTable();
				for(Object[] invite : finalInvites) {
					tableModel.addRow(invite);
				}
			}
		});
	}
	
	// adds action listener for accept invite button
	private void addAcceptInviteActionListener() {
		acceptInviteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opponent = ((String) tableModel.getValueAt(selectedInvite, 0)).trim();
				String game = ((String) tableModel.getValueAt(selectedInvite, 1)).trim();
				try {
					String inviteRequest = "invite accepted " + client.user.getNickname() + " " + opponent + " " + game
							+ "\n";
					client.request(inviteRequest);
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLabel.setText("Error with invitation.");
				}
				String gameRequest = "game " + client.user.getNickname() + " " + opponent + " " + game
						+ "\n";
				try {
					client.request(gameRequest);
				} catch (IOException e1) {
					e1.printStackTrace();
					responseLabel.setText("Error accepting invitation.");
				}
				declineInviteBtn.doClick();
			}
		});
	}
	
	// adds action listener for decline invite button
	private void addDeclineInviteActionListener() {
		declineInviteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String opponent = ((String) tableModel.getValueAt(selectedInvite, 0)).trim();
				String game = ((String) tableModel.getValueAt(selectedInvite, 1)).trim();
				
				tableModel.removeRow(selectedInvite);
				db.clearQueryResults();
				try {
					db.changeDatabase("delete from invitations where nicknameRx='" + opponent + "' AND gameID='" + game + "';");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
	}
	
	// gets data from database tables and populates it into the frame.
	private void setupInviteTable() {
		
		Object[][] myInvites;
		Object[][] theirInvites;
		
		ArrayList<String> myInviteResults = new ArrayList<String>();
		ArrayList<String> theirInviteResults = new ArrayList<String>();
		
		try {
			myInviteResults = this.db.queryFromDatabase("select * from invitations where nicknameTx='" + this.client.user.getNickname() + "';");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(myInviteResults.size() == 0) {
			myInvites = new Object[][] {{"-", "[NO INVITES YET]", "-"}};
		} else {
			myInvites = parseInvites(myInviteResults, "MINE");
		}
		
		this.db.clearQueryResults();
		
		try {
			theirInviteResults = this.db.queryFromDatabase("select * from invitations where nicknameRx='" + this.client.user.getNickname() + "';");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(theirInviteResults.size() == 0) {
			theirInvites = new Object[][] {{"-", "[NO INVITES SENT]", "-"}};
		} else {
			theirInvites = parseInvites(theirInviteResults, "OTHER");
		}
		
		this.db.clearQueryResults();
		
		finalInvites = combineArrays(myInvites, theirInvites);
        
	}
	
	// helper method for combining data after getting both sent and received invites
	private Object[][] combineArrays(Object[][] arr1, Object[][] arr2) {
		
		Object[][] retArray = new Object[arr1.length + arr2.length][3];
		
		int indexer = 0;
		for(int i = 0; i < arr1.length; i++, indexer++) {
			retArray[indexer][0] = arr1[i][0];
			retArray[indexer][1] = arr1[i][1];
			retArray[indexer][2] = arr1[i][2];
		}
		for(int i = 0; i < arr2.length; i++, indexer++) {
			retArray[indexer][0] = arr2[i][0];
			retArray[indexer][1] = arr2[i][1];
			retArray[indexer][2] = arr2[i][2];
		}
		
		return retArray;
		
	}
	
	// Parses only necessary information from database invitation tables
	private Object[][] parseInvites(ArrayList<String> queryResults, String mode) {
		// these indexes are from the database where 1 is the nickname who sent the invite
		// and 3 is the index of the game ID
		
		int fromIndex = 0;
		int gameIdIndex = 3;
		String typeText = "";
		
		if(mode.equals("MINE")) {
			fromIndex = 1;
			typeText = "Accept or Decline";
		} else {
			fromIndex = 2;
			typeText = "Pending Decision...";
		}
		Object[][] invites = new Object[queryResults.size() / 4][3];
		
		for(int i = 0; i < queryResults.size() / 4; i++) {
			
			invites[i][0] = queryResults.get(fromIndex);
			invites[i][1] = queryResults.get(gameIdIndex);
			invites[i][2] = typeText;
			fromIndex += 4;
			gameIdIndex += 4;
			
		}
		
		return invites;
		
	}
	
	// helps set up the JFrame
	private void setUpFrame () {
		frame = new JFrame("Invite Your Friends to Play Plunderchess!");
		frame.setSize(PANE_WIDTH, PANE_HEIGHT);
		frame.setMaximumSize(new Dimension(PANE_WIDTH, PANE_HEIGHT));
		frame.setMinimumSize(new Dimension(PANE_WIDTH, PANE_HEIGHT));
		frame.setResizable(false);
		frame.setLayout(null);
		centerFrame();
	}
	
	// centers frame to the screen
	private void centerFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		frame.setLocation(x, y);
	}
	
}
