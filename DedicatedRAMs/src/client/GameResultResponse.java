package client;

public class GameResultResponse implements Response {

    private String[] responseContent;

    /**
     * Constructor for GamesResponse, responseContent contains the response but split up by spacing.
     * @param response - response from Server.
     */
    public GameResultResponse(String response) {
        this.responseContent = response.split(" ");
    }

    /**
     * handleResponse() is overridden from the interface.
     * Either "gameresult failed" or "gameresult success"
     */
    @Override
    public void handleResponse() {
        if(!responseContent[1].equals("success")) {
            System.out.println("Unable to write result to games at this time.");
        }
        //do something with the games returned, spaces between game_ids

    }

}
