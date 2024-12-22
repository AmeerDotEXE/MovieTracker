package MovieTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		//"Favorite,Name,Movie Status,Run Time,Year,Rate,Movie Last Watched,Movie Genre,Movie Cast,Started Date,Image File,Image Position"
	    String sqlCreate = """
			DROP TABLE IF EXISTS movies;
			CREATE TABLE IF NOT EXISTS movies (
				favorite       BOOLEAN,
				name           VARCHAR(120),
				status         ENUM(
					'Review',
					'Might Watch',
					'Want to Watch',
					'Want to Rewatch',
					'Watched'
				),
				run_time       SMALLINT,
				release_year   SMALLINT,
				rate           TINYINT
			);
	    """;

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private void insertMovie(MovieInfo movie) throws SQLException {

		String query = """
			INSERT INTO movies (favorite, name, status, run_time, release_year, rate) 
            VALUES (?, ?, ?, ?, ?, ?);
		""";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setBoolean(1, movie.isFavorite());
        preparedStmt.setString(2, movie.getName().substring(0, Math.min(120, movie.getName().length())));
        preparedStmt.setString(3, movie.getStatus().toString());
        preparedStmt.setInt(4, movie.getDurationMins());
        preparedStmt.setInt(5, movie.getYear());
        preparedStmt.setInt(6, movie.getRate());
        preparedStmt.executeUpdate();
	}
	

	public LinkedList<MovieInfo> getMovies() {
		return movies;
	}
	public void updateMovie(MovieInfo movie) {
		try {
			insertMovie(movie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteMovie(MovieInfo movie) {
		// not implemented
	}

	public void setMovies(LinkedList<MovieInfo> movies) {
		// not implemented yet
	}
	
}
