package MovieTracker;

public class Language {
	private static Language instance = new Language();

	public static String applicationTitle;
	
	public static String statusReview;
	public static String statusMightWatch;
	public static String statusWantToWatch;
	public static String statusWantToRewatch;
	public static String statusWatched;
	
	public static String tabWatchNext;
	public static String tabAllMovies;
	
	public static String buttonAddMovie;
	public static String buttonDelete;
	public static String buttonDone;
	
	public static String inputDuration;
	public static String inputYear;
	public static String inputFirstWatchDate;
	public static String inputLastWatchDate;
	public static String inputGenre;
	public static String inputCast;

	Language() {
		if (Language.instance != null) return;
		Language.instance = this;
		Language.instance = new EnLanguage();
	}
	public static void setLanguageCode(String languageCode) {
		switch (languageCode) {
			case "en":
				Language.instance = new EnLanguage();
				break;
		}
	}
	
}

class EnLanguage extends Language {
	EnLanguage() {
		applicationTitle = "Movie Tracker";
		
		statusReview = "Needs Review";
		statusMightWatch = "Might Watch";
		statusWantToWatch = "Want to Watch";
		statusWantToRewatch = "Want to Rewatch";
		statusWatched = "Watched";
		
		tabWatchNext = "Watch Next";
		tabAllMovies = "All Movies";
		
		buttonAddMovie = "Add Movie";
		buttonDelete = "Delete";
		buttonDone = "Done";
		
		inputDuration = "Duration";
		inputYear = "Year";
		inputFirstWatchDate = "First Watch Date";
		inputLastWatchDate = "Last Watch Date";
		inputGenre = "Genre";
		inputCast = "Cast";
	}
}
