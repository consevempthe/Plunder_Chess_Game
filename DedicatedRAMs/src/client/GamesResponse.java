package client;

public class GamesResponse implements Response{
	
	private String[] responseContent;
	
	public GamesResponse(String response) {
		this.responseContent = response.split(" ");
	}

	@Override
	public void handleResponse() {
		if(!responseContent[1].equals("success")) {
			System.out.println("Unable to retrieve games at this time.");
			return;
		}
		//do something with the games returned, spaces between game_ids
	}

}
