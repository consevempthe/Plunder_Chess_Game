package clientUI;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The LoginUI class is a simple UI for logging into Plunder Chess. It has many components to show in the UI and interacts with the Client
 * class to send requests to the server.
 * @author DedicatedRAMs Team
 *
 */
public class LoginUI extends FrameUI {
	public JFrame frame;
	private JTextField nicknameEntry;
	private JPasswordField passwordEntry;
	private JLabel register;
	private JButton login;
	private JButton quit;

	private Client client;
	/**
	 * This constructor sets up the UI and the Client that is passed as a parameter.
	 * @param client - Client passed to LoginUI.
	 */
	public LoginUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * addLoginActionListener() sets up the action listener for the Login button.
	 * If the login information is appropriate as in the fields are not blank and the nickname and password are potentially valid,
	 * it sends a request to login the user. If it is blank or unreasonable, it displays a pop-up telling the user.
	 */
	private void addLoginActionListener() {
		login.addActionListener(e -> {
			if(isNicknameValid() && isPasswordValid()) {
				try {
					String loginRequest = "login " + nicknameEntry.getText() + " " + new String(passwordEntry.getPassword()) + "\n";
				  client.request(loginRequest);
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
			}
			else
				showMessageDialog(frame, "Invalid nickname or password.\nPlease make sure your login information was entered correctly!", "Invalid Login", JOptionPane.ERROR_MESSAGE);
		});
	}
	
	/**
	 * addQuitActionListener() sets up the action listener for the Quit button.
	 * When clicked, it disconnects the client and exits the system.
	 */
	private void addQuitActionListener() {
		quit.addActionListener(e -> {
			try {
			  client.request("quit\n");
		  } catch (IOException e1) {
			  e1.printStackTrace();
		  }
			System.exit(0);
		});
	}
	/**
	 * addRegisterActionListener() sets up the mouse listener for the Need to register label.
	 * When clicked, it opens another UI on top of the LoginUI with the registration fields.
	 */
	private void addRegisterActionListener() {
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unused")
				RegisterUI registrationPage = new RegisterUI(client);
			}
		});
	}

	/**
	 * isPasswordValid() checks to make sure that the password entered is not blank and has the correct characters to avoid SQL problems.
	 * @return - true if password only contains letters, numbers, and a few certain special characters. a-zA-Z0-9!@#$%^&*(), false otherwise.
	 */
	private boolean isPasswordValid() {
		if(passwordEntry.getPassword() == null)
			return false;
		String password = new String(passwordEntry.getPassword());
		return password.matches("[a-zA-Z0-9!@#$%^&*()]*");
	}

	/**
	 * isNicknameValid() checks to make sure that the nickname entered is not blank and has only alphanumeric values.
	 * @return - true if nickname only contains letters and numbers, false otherwise.
	 */
	private boolean isNicknameValid() {
		String text;
		if(nicknameEntry.getText() == null)
			return false;
		
		text = nicknameEntry.getText();
		return text.matches("[a-zA-Z0-9]*");
	}

	/**
	 * setUpFrame() sets up the Login Screen to have fixed size and be centered on the users' computer screen.
	 */
	private void setUpFrame() {
		frame = new JFrame("Login Screen");
		frame.setSize(400, 300);
		frame.setMaximumSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(400, 300));
		frame.setResizable(false);
		frame.setLayout(null);
		centerFrame(frame);
	}

	/**
	 * setUpFrameContent() adds all the appropriate fields for the login page.
	 * title, nickname, password, and register labels. Along with login and quit buttons.
	 * Also sets actionListeners or mouseListeners for register, login, and quit.
	 */
	private void setUpFrameContent() {
		nicknameEntry = new JTextField();
		nicknameEntry.setBounds(175, 60, 150, 25);
		passwordEntry = new JPasswordField();
		passwordEntry.setBounds(175, 90, 150, 25);
		register = new JLabel("Need to Register?");
		register.setFont(new Font ("TimesRoman", Font.ITALIC, 14));
		register.setBounds(225, 120, 100, 20);
		login = new JButton("Login");
		login.setBounds(80, 150, 100, 25);
		quit = new JButton("Quit");
		quit.setBounds(220, 150, 100, 25);
		addLoginActionListener();
		addQuitActionListener();
		addRegisterActionListener();
		frame.add(createTitleJLabel("X-Game: Plunder Chess"));
		frame.add(createBoundedJLabel("Nickname:", 16,75, 60, 100, 25));
		frame.add(nicknameEntry);
		frame.add(createBoundedJLabel("Password:", 16,75, 90, 100, 25));
		frame.add(passwordEntry);
		frame.add(register);
		frame.add(login);
		frame.add(quit);
	}
	
}