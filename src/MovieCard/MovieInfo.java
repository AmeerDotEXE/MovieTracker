package MovieCard;

public class MovieInfo {
	private String name;
	private String duration;
	private int year;

	public MovieInfo(String name) {
		this.name = name;
		this.duration = "0h 0m";
		this.year = 0;
	}
	public MovieInfo(String name, String duration, int year) {
		this.name = name;
		this.duration = duration;
		this.year = year;
	}

	public String getName() { return name; }
	public String getDuration() { return duration; }
	public int getYear() { return year; }
}
