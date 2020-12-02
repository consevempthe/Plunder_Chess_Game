package clientUI;

import client.Client;

import javax.swing.*;
import java.io.IOException;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * RegisterUI is the class that sets up the Registration UI opened from the Login screen. 
 * @author DedicatedRAMs Team
 *
 */
public class RegisterUI extends FrameUI {
	private JFrame frame;
	private JTextField nicknameEntry = new JTextField();
	private JTextField emailEntry = new JTextField();
	private JPasswordField passwordEntry = new JPasswordField();
	private JButton register;
	private JButton cancel;
	private Client client;
	
	/**
	 * RegisterUI constructor sets the client, frame, and frame contents.
	 * @param client - the client creating this object
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
		centerFrame(frame);
	}
	
	/**
	 * Sets up all components in the frame.
	 */
	private void setUpFrameContent() {
		nicknameEntry.setBounds(175, 60, 150, 25);
		emailEntry.setBounds(175, 90, 150, 25);
		passwordEntry.setBounds(175, 120, 150, 25);
		register = createButton("Register", 80, 150, 100, 25);
		cancel = createButton("Cancel", 220, 150, 100, 25);

		addRegisterActionListener();
		addCancelActionListener();
		frame.add(createTitleJLabel("Registration"));
		frame.add(createBoundedJLabel("Nickname:", 16, 75, 60, 100, 25));
		frame.add(nicknameEntry);
		frame.add(createBoundedJLabel("Password:", 16,75, 120, 100, 25));
		frame.add(passwordEntry);
		frame.add(createBoundedJLabel("Email:", 16,75, 90, 100, 25));
		frame.add(emailEntry);
		frame.add(register);
		frame.add(cancel);
	}

	/**
	 * Provides action for the cancel button.
	 */
	private void addCancelActionListener() {
		cancel.addActionListener(e -> frame.dispose());
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
				showMessageDialog(frame, "Invalid nickname, email, or password.\nPlease only use alphanumeric characters for nickname,\n be sure your email is valid,\n and be sure your password only contains a-zA-Z0-9!@#$%^&*()", "Invalid Registration", JOptionPane.ERROR_MESSAGE);
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