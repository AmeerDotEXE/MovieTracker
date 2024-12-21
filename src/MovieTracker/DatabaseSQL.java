package MovieTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import MovieCard.MovieInfo;

public class DatabaseSQL implements Database {
	
	private static final String DB_URL = "jdbc:h2:./data";
	private static final String USER = "demo";
	private static final String PASSWORD = "password";
	
	private Connection conn;
	
	private LinkedList<MovieInfo> movies = new LinkedList<MovieInfo>();
	
	DatabaseSQL() {
		try {
			connectDB();
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: "+DB_URL+" as "+USER);
			e.printStackTrace();
		}
	}
	
	private void connectDB() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		initilizeTable();
	}
	
	private void initilizeTable() throws SQLException {
	    String sqlCreate = """
			CREATE TABLE IF NOT EXISTS movies (
				name           VARCHAR(60),
				release_year   SMALLINT,
				rate           TINYINT
	    	);
	    """;

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	

	public LinkedList<MovieInfo> getMovies() {
		return movies;
	}

	public void setMovies(LinkedList<MovieInfo> movies) {
		// not implemented yet
	}
	
}
