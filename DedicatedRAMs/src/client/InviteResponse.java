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
		switch (responseContent[1]) {
			case "failed":
				System.out.println("Invitation not sent.");
				client.startUI.responseLabel.setText("Problem with invite, please try again later.");
				break;
			case "accepted":
				System.out.println("Invitation accepted.");
				client.startUI.responseLabel.setText(responseContent[2] + " accepted the invite! Review fields and click 'Start Game' to begin");
				gameId = responseContent[4];
				nicknameId = responseContent[2];
				enabled = true;
				break;
			case "sentAccepted":
				System.out.println("Invitation accepted.");
				client.startUI.responseLabel.setText("Invite from " + responseContent[2] + " accepted! Review fields and click 'Start Game' to begin");
				gameId = responseContent[4];
				nicknameId = responseContent[2];
				enabled = true;
				break;
			case "rejected":
				System.out.println("Invitation rejected.");
				client.startUI.responseLabel.setText(responseContent[2] + " rejected the invite.");
				break;
			case "sentRejected":
				System.out.println("Invitation rejected.");
				client.startUI.responseLabel.setText("Invite from " + responseContent[2] + " rejected.");
				break;
			case "sent":
				System.out.println("Invitation sent.");
				client.startUI.responseLabel.setText("Invited " + responseContent[2] + ", awaiting response.");
				break;
			case "add":
				System.out.println("Invitation recieved.");
				client.startUI.responseLabel.setText("Invite from, " + responseContent[2] + ", " + responseContent[4]);
				inviteButtonVis = true;
				break;
		}

		client.startUI.gameIDEntry.setText(gameId);
		client.startUI.nicknameEntry.setText(nicknameId);
		client.startUI.acceptInviteBtn.setVisible(inviteButtonVis);
		client.startUI.rejectInviteBtn.setVisible(inviteButtonVis);
		client.startUI.startButton.setEnabled(enabled);
	}

}
