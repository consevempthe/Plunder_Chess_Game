package client;

public class InviteResponse implements Response {
	private User user;
	private String[] responseContent;
	private Client client;
	
	/**
	 * Constructor for InviteResponse, responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 */
	public InviteResponse(String response, User user, Client client) {
		this.user = user;
		this.responseContent = response.split(" ");
		this.client = client;
	}

	@Override
	public void handleResponse() {
		//invite sent
		if(responseContent[1].equals("failed")) {
			System.out.println("Invitation not sent.");
			return;
		}
		client.startUI.responseLbl.setText("Invitation sent.");
		
	}

}
