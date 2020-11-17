package clientUI;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The DeleteUserUI class is a simple UI for deleting users from system. This
 * class to send requests to the server.
 * 
 * @author DedicatedRAMs Team
 *
 */
public class DeleteUserUI {
	public JFrame frame;
	private JLabel title;
	private JTextField nicknameEntry;
	private JPasswordField passwordEntry;
	private JLabel confirm;
	private JTextField confirmEntry;
	private JButton delete;
	private JButton cancel;
	private JLabel nickname;
	private JLabel password;

	private Client client;

	/**
	 * This constructor sets up the UI and the Client that is passed as a parameter.
	 * 
	 * @param client - Client passed to LoginUI.
	 */
	public DeleteUserUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * addDeleteActionListener() sets up the action listener for the Delete button.
	 */
	private void addDeleteActionListener() {
		delete.addActionListener(e -> {
			int opt = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete your information?\nThis action cannot be undone.",
					"Delete User", JOptionPane.YES_NO_OPTION);
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
					showMessageDialog(frame,
							"Invalid password or confirmation of DELETE.",
							"Delete User", JOptionPane.ERROR_MESSAGE);
			} else if (opt == JOptionPane.NO_OPTION) {
				
			} else {

			}
		});
	}

	/**
	 * addCancelActionListener() sets up the action listener for the Cancel button.
	 * When clicked, disposes the frame.
	 */
	private void addCancelActionListener() {
		cancel.addActionListener(e -> {
			frame.dispose();
		});
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
		frame = new JFrame("Account Settings " + client.user.getNickname());
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
	 * setUpFrameContent() adds all the appropriate fields for the delete user page.
	 */
	private void setUpFrameContent() {
		title = new JLabel("X-Game: Plunder Chess");
		title.setSize(400, 30);
		title.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		nickname = new JLabel("Nickname:");
		nickname.setFont(new Font("TimesRoman", Font.BOLD, 16));
		nickname.setBounds(75, 60, 100, 25);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		password = new JLabel("Password:");
		password.setFont(new Font("TimesRoman", Font.BOLD, 16));
		password.setBounds(75, 90, 100, 25);
		passwordEntry = new JPasswordField();
		passwordEntry.setBounds(175, 90, 150, 25);
		confirm = new JLabel("Type DELETE to confirm deletion of account");
		confirm.setFont(new Font("TimesRoman", Font.BOLD, 12));
		confirm.setBounds(75, 120, 300, 20);
		confirmEntry = new JTextField();
		confirmEntry.setFont(new Font("TimesRoman", Font.BOLD, 16));
		confirmEntry.setBounds(175, 150, 150, 25);
		delete = new JButton("Delete");
		delete.setBounds(80, 180, 100, 25);
		cancel = new JButton("Cancel");
		cancel.setBounds(220, 180, 100, 25);
		addDeleteActionListener();
		addCancelActionListener();
		frame.add(title);
		frame.add(nickname);
		nicknameEntry.setText(client.user.getNickname());
		//nicknameEntry.setEditable(false);
		nicknameEntry.setBackground(Color.LIGHT_GRAY);
		frame.add(nicknameEntry);
		frame.add(password);
		frame.add(passwordEntry);
		frame.add(confirm);
		frame.add(confirmEntry);
		frame.add(delete);
		frame.add(cancel);
	}

}