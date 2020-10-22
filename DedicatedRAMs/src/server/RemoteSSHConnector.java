package server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RemoteSSHConnector {
	
	private static final String username = "najyfaou";
	private static final String password = "831931241";
	private int localPort;
	private int remotePort;
	private String remoteHost;
	private String host;
	private JSch jsch = new JSch();
	private Session session = null;
	
	public RemoteSSHConnector(int localPort, int remotePort, String remoteHost, String host) {
		this.localPort = localPort;
		this.remotePort = remotePort;
		this.remoteHost = remoteHost;
		this.host = host;
	}
	

	public boolean connect() {
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		try {
			session = jsch.getSession(username, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			session.setPortForwardingL(localPort, remoteHost, remotePort);
			System.out.println("Port Fowarded!");
		} catch (JSchException e) {
			System.out.println("Port Connected Already!");
			return false;
		}
		return true;
	}
	
	public void disconnect() {
		session.disconnect();
	}
	
}
