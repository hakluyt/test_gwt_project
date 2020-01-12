package projectVisualiser.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private String dbUrl;
	private String username;
	private String password;
	
	public DBConnector() {
		this.dbUrl = "jdbc:postgresql://localhost:5432/unit_projects";
		this.username = "postgres";
		this.password = "0000";
	}
	
	public DBConnector(String dbUrl, String username, String password) {
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}


	public String getDbUrl() {
		return dbUrl;
	}


	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public Connection getConnection() {

		try {
			Connection	connection = DriverManager.getConnection(dbUrl, username, password);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}




 