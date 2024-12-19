package MovieCard;

import java.util.ArrayList;

public class MovieInfo {
	private String name = "No Name";
	private int durationMins;
	private int year;
	private boolean favorite = false;
	private MovieStatus status = MovieStatus.Needs_Review;
	private int rate;
	private String lastWatched = null;
	private ArrayList<String> genre = new ArrayList<String>();
	private ArrayList<String> cast = new ArrayList<String>();
	private String firstWatched = null;
	private String imagePath;
	private int imagePosition = 50;

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
	public String getLastWatched() { return lastWatched; }
	public ArrayList<String> getGenre() { return genre; }
	public ArrayList<String> getCast() { return cast; }
	public String getFirstWatched() { return firstWatched; }
	public String getImagePath() { return imagePath; }
	public int getImagePosition() { return imagePosition; }


	public void setDuration(String duration) {
		try {
			int mins = durationToMins(duration);
			this.durationMins = mins;
		} catch (Exception e) {
			System.out.println("Couldn't set duration for movie: "+name);
		}
	}
	public String getDuration() {
		int mins = durationMins % 60;
		int hours = durationMins / 60;
		return hours+"h "+mins+"m";
	}
	
	public static int durationToMins(String duration) {
		if (duration == "") return 0;
		
		int mins = 0;
		
		if (duration.contains("h")) {
			String hours = duration.split("h")[0];
			if (hours != "") mins += 60 * Integer.parseInt(hours);
		}
		if (duration.contains("m")) {
			if (duration.contains("h")) {
				duration = duration.split("h")[1];
			}
			if (duration.startsWith(" ")) duration = duration.substring(1);
			String minutes = duration.split("m")[0];
			if (minutes != "") mins += Integer.parseInt(minutes);
		}
		
		return mins;
	}

	public void setFavorite(boolean favorite) { this.favorite = favorite; }
	public void setName(String name) { this.name = name; }
	public void setYear(int year) { this.year = year; }
	public void setStatus(MovieStatus status) { this.status = status; }
	public void setRate(int rate) { this.rate = rate; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }
	public void setImagePosition(int imagePosition) { this.imagePosition = imagePosition; }
	public void setLastWatched(String lastWatched) { this.lastWatched = lastWatched; }
	public void setFirstWatched(String firstWatched) { this.firstWatched = firstWatched; }
}
