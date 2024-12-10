package MovieTracker;

import java.util.LinkedList;
import javax.swing.JPanel;

import MovieCard.MovieCard;
import MovieCard.MovieInfo;

public class MovieManager {
	private static MovieManager instance = new MovieManager();
	public static MovieManager getInstance() { return instance; }
	
	Database db;
	LinkedList<MovieInfo> movies;
	
	MovieManager() {
		db = new DatabaseCSV();
		movies = db.getMovies();
	}
	
	public void generateMovieCards(JPanel parent) {
		
		for (MovieInfo movie : movies) {
			parent.add(new MovieCard(movie));
		}

		for (MovieInfo movie : movies) {
			parent.add(new MovieCard(movie));
		}
		
	}
	public void saveMovies() {
		db.setMovies(movies);
	}
}
