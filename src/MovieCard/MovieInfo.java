package MovieCard;

public class MovieInfo {
	private String name = "No Name";
	private int durationMins;
	private int year;
	private boolean favorite = false;
	private MovieStatus status = MovieStatus.Needs_Review;
	private int rate;
	private int lastWatched;
	private String[] gerne = new String[4];
	private String[] cast = new String[6];
	private int firstWatched;

	public MovieInfo(String name) {
		this.name = name;
	}
	public MovieInfo(String name, int durationMins, int year) {
		this.name = name;
		this.durationMins = durationMins;
		this.year = year;
	}

	public String getName() { return name; }
	public int getYear() { return year; }
	public int getDurationMins() { return durationMins; }
	public boolean isFavorite() { return favorite; }
	public MovieStatus getStatus() { return status; }
	public int getRate() { return rate; }
	public int getLastWatched() { return lastWatched; }
	public String[] getGerne() { return gerne; }
	public String[] getCast() { return cast; }
	public int getFirstWatched() { return firstWatched; }

	public String getDuration() {
		int mins = durationMins % 60;
		int hours = durationMins / 60;
		return hours+"h "+mins+"m";
	}
}
