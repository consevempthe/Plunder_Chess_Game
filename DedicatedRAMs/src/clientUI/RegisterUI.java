package clientUI;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;

/**
 * RegisterUI is the class that sets up the Registration UI opened from the Login screen. 
 * @author DedicatedRAMs Team
 *
 */
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
	
	/**
	 * RegisterUI constructor sets the client, frame, and frame contents.
	 * @param client
	 */
	public RegisterUI(Client client) {
		this.client = client;
		setUpFrame();
		setUpFrameContent();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Sets up and centers the Register frame.
	 * Calls centerFrame()
	 */
	private void setUpFrame() {
		frame = new JFrame("Register");
		frame.setSize(400, 300);
		frame.setLayout(null);
		centerFrame();
	}

	/**
	 * Centers frame on system window
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
	 * Sets up all components in the frame.
	 */
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
		email = new JLabel("Email:");
		email.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		email.setBounds(75, 90, 100, 25);
		emailEntry = new JTextField();
		emailEntry.setBounds(175, 90, 150, 25);
		password = new JLabel("Password:");
		password.setFont(new Font ("TimesRoman", Font.BOLD, 16));
		password.setBounds(75, 120, 100, 25);
		passwordEntry = new JPasswordField();
		passwordEntry.setBounds(175, 120, 150, 25);
		register = new JButton("Register");
		register.setBounds(80, 150, 100, 25);
		cancel = new JButton("Cancel");
		cancel.setBounds(220, 150, 100, 25);
		addRegisterActionListener();
		addCancelActionListener();
		frame.add(title);
		frame.add(nickname);
		frame.add(nicknameEntry);
		frame.add(password);
		frame.add(passwordEntry);
		frame.add(email);
		frame.add(emailEntry);
		frame.add(register);
		frame.add(cancel);
	}

	/**
	 * Provides action for the cancel button.
	 */
	private void addCancelActionListener() {
		
	}

	/**
	 * Provides action for the register button.
	 */
	private void addRegisterActionListener() {
		register.addActionListener(e -> {
			if(isNicknameValid() && isEmailValid() && isPasswordValid()) {
				try {
				String registerRequest = "register " + nicknameEntry.getText() + " " + emailEntry.getText() + " " + new String(passwordEntry.getPassword()) + "\n";
				  client.request(registerRequest);
			  } catch (IOException e1) {
				  e1.printStackTrace();
			}
			}
			else
				showMessageDialog(frame, "Invalid nickname, email, or password.\nPlease only use alphanumeric characters for nickname,\n be sure your email is valid,\n and be sure your password only contains a-zA-Z0-9!@#$%^&*()", "Invalid Registration", 0);
		});
	}
	
	/**
	 * isEmailValid() checks to make sure that the email entered at least appears to be like an email.
	 * @return - true if valid email and false if invalid.
	 */
	private boolean isEmailValid() {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$";  
		Pattern pat = Pattern.compile(emailRegex); 
		if (emailEntry.getText() == null) 
            return false; 
        return pat.matcher(emailEntry.getText()).matches(); 
	}

	/**
	 * isPasswordValid() checks to make sure that the password entered is not blank and has the correct characters to avoid SQL problems. (isDuplicate)
	 * @return - true if password only contains letters, numbers, and a few certain special characters. a-zA-Z0-9!@#$%^&*(), false otherwise.
	 */
	private boolean isPasswordValid() {
		if(passwordEntry.getPassword() == null)
			return false;
		String password = new String(passwordEntry.getPassword());
		return password.matches("[a-zA-Z0-9!@#$%^&*()]*");
	}

	/**
	 * isNicknameValid() checks to make sure that the nickname entered is not blank and has only alphanumeric values. (isDuplicate)
	 * @return - true if nickname only contains letters and numbers, false otherwise.
	 */
	private boolean isNicknameValid() {
		String text;
		if(nicknameEntry.getText() == null)
			return false;
		
		text = nicknameEntry.getText();
		return text.matches("[a-zA-Z0-9]*");
	}
	
	
}