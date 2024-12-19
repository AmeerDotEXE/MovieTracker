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
			case "tr":
				Language.instance = new TrLanguage();
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

class TrLanguage extends Language {
	TrLanguage() {
		applicationTitle = "Film Arşivi";
		
		statusReview = "İnceleme Gerekli";
		statusMightWatch = "İzlenebilir";
		statusWantToWatch = "İzlemek İstiyorum";
		statusWantToRewatch = "Tekrar İzle";
		statusWatched = "İzlendi";
		
		tabWatchNext = "Sonrakini İzle";
		tabAllMovies = "Tüm Filmler";
		
		buttonAddMovie = "Film Ekle";
		buttonDelete = "Sil";
		buttonDone = "Bitti";
		
		inputDuration = "Süre";
		inputYear = "Yıl";
		inputFirstWatchDate = "İlk İzleme Tarihi";
		inputLastWatchDate = "Son İzleme Tarihi";
		inputGenre = "Tür";
		inputCast = "Aktörler";
	}
}
