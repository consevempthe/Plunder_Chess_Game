package client;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.IOException;

public class RegistrationResponse implements Response {
	
	private Client client;
	private String[] responseContent;
	
	public RegistrationResponse(String response, Client client) {
		this.responseContent = response.split(" ");
		this.client = client;
	}

	@Override
	public void handleResponse() {
		if(!responseContent[1].equals("success")) {
			showMessageDialog(null, "The username or email has already been used\n Try a different one!", "Invalid Registration.", 0);
			return;
		}
		String request = "login " + responseContent[2] + " " + responseContent[4];
		try {
			client.request(request + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
