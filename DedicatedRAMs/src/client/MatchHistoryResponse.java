package client;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class MatchHistoryResponse implements Response {

    private String[] responseContent;
    private Client client;

    /**
     * The constructor for LoginResponse takes the response and a user to instantiate. responseContent contains the response but split up by spacing.
     * @param response - response from Server.
     */
    public MatchHistoryResponse(String response, User user, Client client) {
        this.responseContent = response.split(" ");
        this.client = client;
    }


    /**
     * handleResponse() is overridden from the interface, Response. It acts on the login response created.
     * If the response comes back with the "failed" key response, it will the UI will display an error message box informing the user that the login was not excepted.
     * Otherwise, the user's data will be set given the response information.
     */
    @Override
    public void handleResponse() {
        if(responseContent[1].equals("failed")) {
            showMessageDialog(null, "Invalid nickname", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < responseContent.length; i++) {

        }
        String id = responseContent[2];
        String p1 = responseContent[3];
        String p2 = responseContent[4];
        String win = responseContent[5];
        String loss = responseContent[6];
        String draw = responseContent[7];

        Object [][] match = new Object[][] {{id, p1, p2, win, loss, draw}};
        client.user.setHistory(match);
    }
}
