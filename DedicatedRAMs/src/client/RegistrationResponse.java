package client;

public class RegistrationResponse implements Response {
	
	private User user;
	private String[] responseContent;
	
	public RegistrationResponse(String response, User user) {
		this.responseContent = response.split(" ");
		this.user = user;
	}

	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			System.out.println("Registration nickname or email unavailable! Try a different nickname or email.");
			return;
		}
		String response = responseContent[0] + " " + responseContent[1] + " " + responseContent[2] + " " + responseContent[3] + " " + responseContent[4];
		LoginResponse login = new LoginResponse(response, user);
		login.handleResponse();
	}

}
