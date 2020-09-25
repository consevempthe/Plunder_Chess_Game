package client;

import java.net.*;
import java.io.*; 


public class Client 
{ 
	private final String serverName;
	private final int serverPort;  
	private Socket socket; 
	private InputStream serverIn;
	private BufferedReader bufferedIn;
	private OutputStream serverOut;

    public Client(String address, int port) 
    { 
    	this.serverName = address;
    	this.serverPort = port;
    }

    public void request(String request) throws IOException {
    	serverOut.write(request.getBytes());
    }

    public String response() throws IOException {
    	String response = bufferedIn.readLine();
    	return response;
    }
    
	public boolean connect() {
		try {
			this.socket = new Socket(getServerName(), getServerPort());
			this.serverIn = socket.getInputStream();
			this.serverOut = socket.getOutputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected String getServerName() {
		return serverName;
	}
	
	protected int getServerPort() {
		return serverPort;
	}
	
} 