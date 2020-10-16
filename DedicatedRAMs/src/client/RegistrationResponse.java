package client;

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
			System.out.println("Registration nickname or email unavailable! Try a different nickname or email.");
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
