package client;

public class User {
	
	public enum Status {HAPPY, SAD, MONDAY}; 
	private String nickname;
	private String email;
	private String password;
	private Status status;
	
	public User(String nickname, String email, String password) {
		this.setNickname(nickname);
		this.setEmail(email);
		this.setPassword(password);
		this.setStatus(Status.HAPPY);
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
	
	//getMatchHistory()
	
	//addGame()
	
	//updatePassword()
	
	//updateEmail()
}
