package server;

import java.io.IOException;

public class ServerMain {

    public static void main(String args[]) throws IOException, InterruptedException 
    { 
    	String env = System.getenv("ENVIRONMENT");
    	int port = 8818;
    	if(env != null && env.equals("production"))
    		port = 8000;
    	System.out.println("Attempting to connect to port: " + port);
    	Server server = new Server(port);
    	server.start();
    } 
	
	
}
    
