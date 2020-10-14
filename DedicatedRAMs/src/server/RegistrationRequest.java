package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationRequest {
	
	private String nickname;
	private String email;
	private String password;
	private List<Map<String, String>> users = new ArrayList<>();
	
	public RegistrationRequest(String command, String nickname, String email, String password) {
		
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		users.add(new HashMap<>());
		users.add(new HashMap<>());
		users.add(new HashMap<>());
	}
	
	public void buildResponse() {
		String url = "jdbc:mysql://localhost:8088/dedicatedrams?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "najyfaou";
		String password = "831931241";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			try(Connection connection = DriverManager.getConnection(url, user, password);
					Statement stCount = connection.createStatement();
					Statement stQuery = connection.createStatement();
					ResultSet rsCount = stCount.executeQuery("select count(nickname) from registration;");
					ResultSet rsQuery = stQuery.executeQuery("select nickname, email, password from registration;")){
				fillUsers(rsCount, rsQuery);
			}
		}catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}

	private void fillUsers(ResultSet count, ResultSet query) throws SQLException {
		
		while(query.next()) {
			String nickname = query.getString("nickname");
			String email = query.getString("email");
			String password = query.getString("password");
			users.get(0).put("nickname", nickname);
			users.get(1).put("email", email);
			users.get(2).put("password", password);
			System.out.println(nickname);
		}
	}
	
	
}