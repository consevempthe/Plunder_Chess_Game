package client;

public class InviteResponse implements Response {
	private User user;
	private String[] responseContent;
	private Client client;

	/**
	 * Constructor for InviteResponse, responseContent contains the response but
	 * split up by spacing.
	 * 
	 * @param response - response from Server.
	 */
	public InviteResponse(String response, User user, Client client) {
		this.user = user;
		this.responseContent = response.split(" ");
		this.client = client;
	}

	@Override
	public void handleResponse() {
		boolean enabled = false;
		boolean inviteButtonVis = false;
		String gameId = "";
		String nicknameId = "";

		// invite add/success nicknameTx nicknameRx gameid
		if (responseContent[1].equals("failed")) {
			System.out.println("Invitation not sent.");
			client.startUI.responseLabel.setText("Problem with invite, please try again later.");
		} else if (responseContent[1].equals("accepted")) {
			System.out.println("Invitation accepted.");
			client.startUI.responseLabel.setText(responseContent[2] + " accepted the invite! Review fields and click 'Start Game' to begin");	
			gameId = responseContent[4];
			nicknameId = responseContent[2];
			enabled = true;
		} else if (responseContent[1].equals("sentAccepted")) {
			System.out.println("Invitation accepted.");
			client.startUI.responseLabel.setText("Invite from " + responseContent[2] + " accepted! Review fields and click 'Start Game' to begin");
			gameId = responseContent[4];
			nicknameId = responseContent[2];
			enabled = true;
		} else if (responseContent[1].equals("rejected")) {
			System.out.println("Invitation rejected.");
			client.startUI.responseLabel.setText(responseContent[2] + " rejected the invite.");
		} else if (responseContent[1].equals("sentRejected")) {
			System.out.println("Invitation rejected.");
			client.startUI.responseLabel.setText("Invite from " + responseContent[2] + " rejected.");
		} else if (responseContent[1].equals("sent")) {
			System.out.println("Invitation sent.");
			client.startUI.responseLabel.setText("Invited " + responseContent[2] + ", awaiting response.");
		} else if (responseContent[1].equals("add")) {
			System.out.println("Invitation recieved.");
			client.startUI.responseLabel.setText("Invite from, " + responseContent[2] + ", " + responseContent[4]);
			inviteButtonVis = true;
		}

		client.startUI.gameIDEntry.setText(gameId);
		client.startUI.nicknameEntry.setText(nicknameId);
		client.startUI.acceptInviteBtn.setVisible(inviteButtonVis);
		client.startUI.rejectInviteBtn.setVisible(inviteButtonVis);
		client.startUI.startButton.setEnabled(enabled);
	}

}
