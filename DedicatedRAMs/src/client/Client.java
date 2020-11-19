package client;

import clientUI.GameUI;
import clientUI.LoginUI;
import clientUI.StartUI;
import gameLogic.Game;

import java.io.*;
import java.net.Socket;


public class Client 
{ 
	private final String serverName;
	private final int serverPort;
	private Socket socket;
	private BufferedReader bufferedIn;
	private OutputStream serverOut;
	public User user = new User(null, null, null);
	private Game game;
	protected LoginUI loginUI;
	protected StartUI startUI;
	protected GameUI gameUI;

	
    public Client(String address, int port) 
    { 
    	this.serverName = address;
    	this.serverPort = port;
    	this.loginUI = new LoginUI(this);
//    	this.startUI = new StartUI(this);
    }
    
    public User getUser() {
    	return user;
    }

    public void request(String request) throws IOException {
    	serverOut.write(request.getBytes());
    }
    
	public boolean connect() {
		try {
			this.socket = new Socket(getServerName(), getServerPort());
			InputStream serverIn = socket.getInputStream();
			this.serverOut = socket.getOutputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			startResponseReader();
			return true;
		} catch (IOException e) {
			System.out.println("Connection Refused!");
		}
		return false;
	}

	private void startResponseReader() {
		Thread t = new Thread(this::readResponsesLoop);
		t.start();
	}

	private void readResponsesLoop() {
		String response;
		try {
			while( (response = bufferedIn.readLine()) != null) {
				handleResponse(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void handleResponse(String response) {
		Response r;
		System.out.println(response);
		String responseType = response.split(" ")[0];
		switch(responseType) {
			case "login": r = new LoginResponse(response, user, this);
				break;
			case "register": r = new RegistrationResponse(response, this);
				break;
			case "invite": r = new InviteResponse(response, user, this);
				break;
			case "game": r = new GameResponse(response, user, this);
				break;
			case "move": r = new MoveResponse(response, user, gameUI);
				break;
			default:
				return;
		}
		r.handleResponse();
	}


	public String getServerName() {
		return serverName;
	}
	
	public int getServerPort() {
		return serverPort;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
} 