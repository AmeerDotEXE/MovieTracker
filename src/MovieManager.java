import javax.swing.JPanel;

import MovieCard.MovieCard;
import MovieCard.MovieInfo;

public class MovieManager {
	private static MovieManager instance = new MovieManager();
	public static MovieManager getInstance() { return instance; }
	
	Database db; 
	
	MovieManager() {
		db = new DatabaseCSV();
	}
	
	public void generateMovieCards(JPanel parent) {
		
		for (MovieInfo movie : db.getMovies()) {
			parent.add(new MovieCard(movie));
		}
		
	}
	public void generateMovieCardsOld(JPanel parent) {
		parent.add(new MovieCard(new MovieInfo("test1")));
		parent.add(new MovieCard(new MovieInfo("test2")));
		parent.add(new MovieCard(new MovieInfo("test3")));
		parent.add(new MovieCard(new MovieInfo("test4")));
		parent.add(new MovieCard(new MovieInfo("test5")));
		
		for (int i = 0; i < 10; i++) {
			MovieCard movieCard = new MovieCard(new MovieInfo("test"+(i+6), i*30, 2024));
			parent.add(movieCard);
		}
	}
}