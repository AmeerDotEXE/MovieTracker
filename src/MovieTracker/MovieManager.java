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

		// move old database to new one
//		Database oldDB = new DatabaseCSV();
//		for (int i = oldDB.getMovies().size() - 1; i >= 0; i--) {
//			MovieInfo movie = oldDB.getMovies().get(i);
//			movies.addFirst(movie);
//			db.addMovie(movie);
//		}
		
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
		db.addMovie(movie);
	}
	public void updateMovie(MovieInfo movie) {
		db.updateMovie(movie);
	}
	public void renameMovies(String oldName, MovieInfo movie) {
		db.renameMovie(oldName, movie);
	}
	public void removeMovie(MovieCard movieCard) {
		
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
