package client;

import gameLogic.Game;

public class LoadResponse implements Response {
	private String[] responseContent;
	private User user;
	private Client client;
	
	public LoadResponse(String response, User user, Client client) {
		this.responseContent = response.split(" ");
		this.user = user;
		this.client = client;
	}

	@Override
	public void handleResponse() {
		Game gameToLoad = user.getGame(responseContent[2]);
		for(int i = 3; i <= this.responseContent.length - 3; i=i+4)
		{
			//System.out.println(responseContent[i+1] + " " + responseContent[i+2] + " " + responseContent[i+3]);
			gameToLoad.move(responseContent[i+1], responseContent[i+2], responseContent[i+3]);
		}
		client.startUI.addGame(gameToLoad);
	}
}
