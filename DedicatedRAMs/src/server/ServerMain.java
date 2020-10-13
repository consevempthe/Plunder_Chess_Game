package server;

import java.io.IOException;

public class ServerMain {

    public static void main(String args[]) throws IOException, InterruptedException 
    { 
    	int port = 8818;
    		Server server = new Server(port);
    		server.start();
    		RegistrationRequest reg = new RegistrationRequest("reg", "NStrike", "temp", "ttt");
    		reg.buildResponse();
    } 
	
	
}
