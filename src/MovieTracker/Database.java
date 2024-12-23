package MovieTracker;

import java.util.LinkedList;

import MovieCard.MovieInfo;

public interface Database {
	public LinkedList<MovieInfo> getMovies();
	public void updateMovie(MovieInfo movie);
	public void deleteMovie(MovieInfo movie);
	public void renameMovie(String oldName, MovieInfo movie);
}
