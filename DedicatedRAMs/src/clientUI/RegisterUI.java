package clientUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;

public class RegisterUI {
	private JFrame frame;
	private JLabel title;
	private JTextField nicknameEntry;
	private JTextField emailEntry;
	private JPasswordField passwordEntry;
	private JLabel nickname;
	private JLabel password;
	private JLabel email;
	private JButton register;
	private JButton cancel;
	private Client client;
	
	public RegisterUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	private void setUpFrame() {
		frame = new JFrame("Register");
		frame.setSize(400, 300);
		frame.setLayout(null);
		centerFrame();
	}

	private void centerFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
	    frame.setLocation(x, y);
	}
	
	private void setUpFrameContent() {
		title = new JLabel("Registration");
		title.setSize(400, 30);
		title.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		nickname = new JLabel("Nickname:");
		nickname.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		nickname.setBounds(75, 60, 100, 25);
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		password = new JLabel("Password:");
		password.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		password.setBounds(75, 90, 100, 25);
		passwordEntry = new JPasswordField();
		passwordEntry.setBounds(175, 90, 150, 25);
		frame.add(title);
		frame.add(nickname);
		frame.add(nicknameEntry);
		frame.add(password);
		frame.add(passwordEntry);
	}
	
	
}