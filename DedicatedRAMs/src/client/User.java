package client;

import gameLogic.*;

public class User {
	
	public enum Status {HAPPY, SAD, MONDAY}; 
	private String nickname;
	private String email;
	private String password;
	private Status status;
	private MatchHistory matches = new MatchHistory();
	private boolean isReady = true;
	
	public User(String nickname, String email, String password) {
		this.setNickname(nickname);
		this.setEmail(email);
		this.setPassword(password);
		this.setStatus(Status.HAPPY);
	}

	public void createGame(String gameID) {
		Game creation = new Game(gameID, this);
		matches.addGame(creation);
	}
	
	public Game getGame(String gameID) {
		return matches.getGame(gameID);
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public boolean isReady() {
		return isReady;
	}
}
