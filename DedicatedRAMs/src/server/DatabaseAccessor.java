package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The DatabaseAccessor class is a class that connects and queries from the dedicatedrams, MySQL database. The url, user, and password for the database are shared and unchangable between DatabaseAccessors. The query results are stored in an ArrayList.
 * @author DedicatedRAMs Team
 *
 */
public class DatabaseAccessor {
	
	private static final String url = "jdbc:mysql://localhost:8088/dedicatedrams?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String databaseUser = "najyfaou";
	private static final String databasePassword = "831931241";
	private ArrayList<String> queryResults = new ArrayList<>();
	
	/**
	 * The default constructor for DatabaseAccessor simply clears the queryResults ArrayList.
	 */
	public DatabaseAccessor(){
		queryResults.clear();
	}

	/**
	 * queryFromDatabase() queries attempts to query from the database with the query given. First it attempts to connect, then creates a query statement, and finally executes the query. After execution, the result is passed on to the helper method,
	 * fillQueryResults() to fill the queryResults ArrayList.
	 * @param query - SQL style query to be executed.
	 * @return ArrayList<String> - a list of all the results from the query.
	 * @throws ClassNotFoundException - throws if the connection cannot be established.
	 */
	public ArrayList<String> queryFromDatabase(String query) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			try(Connection databaseConnection = DriverManager.getConnection(url, databaseUser, databasePassword);
					Statement stQuery = databaseConnection.createStatement();
					ResultSet rsQuery = stQuery.executeQuery(query)){
				fillQueryResults(rsQuery);
				return queryResults;
			} 
		}catch(SQLException e) {
			return queryResults;
		}
	}

	/**
	 * getQueryResults() fills the ArrayList, queryResults with all of the resulting values in the ResultSet. If there are multiple columns, for each row, it adds each column value to the ArrayList.
	 * @param query - the ResultSet from the query that contains the resulting rows and columns from the query.
	 * @throws SQLException - throws if the query acts unexpectedly.
	 */
	private void fillQueryResults(ResultSet query) throws SQLException  {
		ResultSetMetaData metadata = query.getMetaData();
		int numberOfColumns = metadata.getColumnCount();
		while(query.next()) {
			for(int i = 1; i <= numberOfColumns; i++) {
				queryResults.add(query.getString(i));
			}
		}
	}
	
}
