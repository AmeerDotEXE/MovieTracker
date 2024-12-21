package MovieTracker;

import java.io.File;
import java.util.LinkedList;
import javax.swing.JPanel;

import MovieCard.MovieCard;
import MovieCard.MovieInfo;
import Pages.Mainframe;

public class MovieManager {
	private static MovieManager instance = new MovieManager();
	public static MovieManager getInstance() { return instance; }
	
	private JPanel cardsPage;
	private Database db;
	private LinkedList<MovieInfo> movies;
	
	MovieManager() {
		db = new DatabaseSQL();
		movies = db.getMovies();
	}
	
	public void generateMovieCards(JPanel parent) {
		cardsPage = parent;
		
		for (MovieInfo movie : movies) {
			cardsPage.add(new MovieCard(movie));
		}

		// for debugging purposes
//		for (MovieInfo movie : movies) {
//			cardsPage.add(new MovieCard(movie));
//		}
		
	}
	public void addMovie(MovieInfo movie) {
		movies.addFirst(movie);
		MovieCard movieCard = new MovieCard(movie);
		cardsPage.add(movieCard, 0);
		Mainframe.showMovieInfo(movieCard);
	}
	public void updateMovie(MovieInfo movie) {
		db.updateMovie(movie);
	}
	public void saveMovies() {
		db.setMovies(movies);
	}
	public void removeMovie(MovieCard movieCard) {
		
		// TODO: trigger save
		
		MovieInfo movie = movieCard.getMovie();
		movies.remove(movie);
		cardsPage.remove(movieCard);
		
		db.deleteMovie(movie);

        String oldFilePath = movie.getImagePath();
		
		if (oldFilePath == null || oldFilePath == "") return;
		File oldImage = new File(oldFilePath);
		if (oldImage.exists()) oldImage.delete();
	}
}
