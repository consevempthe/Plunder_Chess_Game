package client;

import server.RemoteSSHConnector;

public class ClientMain {
  public static void main(String[] args)
    {
	  	RemoteSSHConnector connector = new RemoteSSHConnector(8818, 8000, "concord.cs.colostate.edu", "localhost");
        connector.connect();
		Client client = new Client("localhost", 8818);


        if(!client.connect())
        	System.err.println("Connection Failed.");
        else
        	System.out.println("Connection Succeeded.");
	}
}
