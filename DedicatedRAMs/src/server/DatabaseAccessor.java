package server;

import java.sql.*;
import java.util.ArrayList;

/**
 * The DatabaseAccessor class is a class that connects and queries from the dedicatedrams, MySQL database. The url, user, and password for the database are shared and unchangeable between DatabaseAccessors. The query results are stored in an ArrayList.
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
	 * fillQueryResults() to fill the queryResults ArrayList. queryFromDatabase should be used specifically for Select... SQL statement or any that return a ResultSet.
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
	 * changeDatabase() allows the database to be changed. In this case, the connection is established, then the query is executed. Will return true if the query executed with no problems. Otherwise it returns false or throws an exception.
	 * @param query - SQL style query to be executed. Strictly speaking, it should be an insert, delete, or create type query.
	 * @return boolean - true if the change was successful. false if not.
	 * @throws ClassNotFoundException - throws if database connection could not be set up.
	 */
	public boolean changeDatabase(String query) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			try(Connection databaseConnection = DriverManager.getConnection(url, databaseUser, databasePassword);
					PreparedStatement stQuery = databaseConnection.prepareStatement(query)){
				int a = stQuery.executeUpdate();
				return true;
			} 
		}catch(SQLException e) {
			return false;
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

	public ArrayList<Object> queryFillTable(String query) throws ClassNotFoundException {
		ArrayList<Object> tblresult = new ArrayList<Object>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection databaseConnection = DriverManager.getConnection(url, databaseUser, databasePassword);
				Statement stQuery = databaseConnection.createStatement();
				ResultSet rsQuery = stQuery.executeQuery(query)){
				ResultSetMetaData metadata = rsQuery.getMetaData();
				int numberOfColumns = metadata.getColumnCount();
				while(rsQuery.next()) {
					for(int i = 1; i <= numberOfColumns; i++) {
						tblresult.add(rsQuery.getObject(i));
					}
				}
				return tblresult;
			}
		}catch(SQLException e) {
			return tblresult;
		}
	}
	
}
