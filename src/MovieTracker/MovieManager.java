package MovieTracker;

import java.io.File;
import java.util.LinkedList;
import javax.swing.JPanel;

import MovieCard.MovieCard;
import MovieCard.MovieInfo;

public class MovieManager {
	private static MovieManager instance = new MovieManager();
	public static MovieManager getInstance() { return instance; }
	
	private JPanel cardsPage;
	private Database db;
	private LinkedList<MovieInfo> movies;
	
	MovieManager() {
		db = new DatabaseCSV();
		movies = db.getMovies();
	}
	
	public void generateMovieCards(JPanel parent) {
		cardsPage = parent;
		
		for (MovieInfo movie : movies) {
			cardsPage.add(new MovieCard(movie));
		}

		for (MovieInfo movie : movies) {
			cardsPage.add(new MovieCard(movie));
		}
		
	}
	public void saveMovies() {
		db.setMovies(movies);
	}
	public void removeMovie(MovieCard movieCard) {
		MovieInfo movie = movieCard.getMovie();
		movies.remove(movie);
		cardsPage.remove(movieCard);

        String oldFilePath = movie.getImagePath();
		
		if (oldFilePath == null || oldFilePath == "") return;
		File oldImage = new File(oldFilePath);
		if (oldImage.exists()) oldImage.delete();
	}
}
