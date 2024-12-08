package MovieTracker;

import java.util.LinkedList;

import MovieCard.MovieInfo;

public interface Database {
	public LinkedList<MovieInfo> getMovies();
}
