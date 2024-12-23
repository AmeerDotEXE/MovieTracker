package MovieTracker;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import MovieCard.MovieInfo;
import MovieCard.MovieStatus;

public class DatabaseSQL implements Database {
	
	private static final String DB_URL = "jdbc:h2:./data";
	private static final String USER = "demo";
	private static final String PASSWORD = "password";
	
	private Connection conn;
	
	private LinkedList<MovieInfo> movies = new LinkedList<MovieInfo>();
	
	DatabaseSQL() {
		try {
			connectDB();
			loadMovies();
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
				rate           TINYINT,
				last_watch     VARCHAR(25),
				genre          VARCHAR(120) ARRAY[4],
				movie_cast     VARCHAR(120) ARRAY[6],
				first_watch    VARCHAR(25),
				image_file     VARCHAR(120),
				image_position TINYINT
			);
	    """;

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private void loadMovies() throws SQLException {
		Statement st = conn.createStatement();
		
		String query = """
			SELECT * FROM movies
		""";
		
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			MovieInfo movie = new MovieInfo(rs.getString("name"));
			movie.setFavorite(rs.getBoolean("favorite"));
			movie.setStatus(MovieStatus.fromString(rs.getString("status")));
			movie.setDurationMins(rs.getInt("run_time"));
			movie.setYear(rs.getInt("release_year"));
			movie.setRate(rs.getInt("rate"));
			movie.setLastWatched(rs.getString("last_watch"));
			for (Object element : (Object[])rs.getArray("genre").getArray()) {
				movie.getGenre().add((String)element);
			}
			for (Object element : (Object[])rs.getArray("movie_cast").getArray()) {
				movie.getCast().add((String)element);
			}
			movie.setFirstWatched(rs.getString("first_watch"));
			if (rs.getString("image_file") != "") movie.setImagePath("movie-images/"+rs.getString("image_file"));
			movie.setImagePosition(rs.getInt("image_position"));
			movies.add(movie);
		}
	}
	
	private void insertMovie(MovieInfo movie) throws SQLException {

		String query = """
			INSERT INTO movies (favorite, name, status, run_time, release_year, rate, last_watch, genre, movie_cast, first_watch, image_file, image_position) 
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
		""";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setBoolean(1, movie.isFavorite());
        preparedStmt.setString(2, movie.getName().substring(0, Math.min(120, movie.getName().length())));
        preparedStmt.setString(3, movie.getStatus().toString());
        preparedStmt.setInt(4, movie.getDurationMins());
        preparedStmt.setInt(5, movie.getYear());
        preparedStmt.setInt(6, movie.getRate());
        preparedStmt.setString(7, movie.getLastWatched());

        Array genreArray = conn.createArrayOf("VARCHAR", movie.getGenre().toArray());
        preparedStmt.setArray(8, genreArray);
        Array castArray = conn.createArrayOf("VARCHAR", movie.getCast().toArray());
        preparedStmt.setArray(9, castArray);
        
        preparedStmt.setString(10, movie.getFirstWatched());

        String imagePath = movie.getImagePath();
		if (imagePath == null) imagePath = "";
		else if (imagePath.startsWith("movie-images")) imagePath = imagePath.substring(13);
        preparedStmt.setString(11, imagePath);

        preparedStmt.setInt(12, movie.getImagePosition());
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
