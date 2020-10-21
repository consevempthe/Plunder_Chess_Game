package client;

public class GamesResponse implements Response{
	
	private String[] responseContent;
	
	/**
	 * Constructor for GamesResponse, responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 */
	public GamesResponse(String response) {
		this.responseContent = response.split(" ");
	}
	
	/**
	 * handleResponse() is overridden from the interface.
	 * Either "games failed" or "games success [game ids as strings separated by spaces]"  
	 */
	@Override
	public void handleResponse() {
		if(!responseContent[1].equals("success")) {
			System.out.println("Unable to retrieve games at this time.");
			return;
		}
		//do something with the games returned, spaces between game_ids
	}

}
