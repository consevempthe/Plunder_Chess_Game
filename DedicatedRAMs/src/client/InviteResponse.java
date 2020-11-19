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
			client.startUI.responseLabel.setText("Problem with invite, please try again later.");
			return;
		}
		
		//invite add/success nicknameTx nicknameRx gameid
		client.startUI.gameIDEntry.setText(responseContent[4]);
		client.startUI.nicknameEntry.setText(responseContent[2]);
		client.startUI.responseLabel.setText("Invited! Review fields and click 'Start Game' to begin");
		
	}

}
