package MovieTracker;
import java.awt.Color;


public class Theme {
	private static Theme instance;

	public Color buttonSuccessBG;
	public Color buttonSuccessPress;
	public Color buttonSuccessHover;
	
	public Color applicationBG;
	public Color applicationFG;
	public Color seperator;
	public Color cardBG;
	public Color cardFG;
	public Color cardHover;
	public Color cardSecondaryFG;

	public Color movieStar;
	
	public static Theme getInstance() {
		if (Theme.instance == null) {
			Theme.instance = new DarkTheme();
		}
		return Theme.instance;
	}
	protected static void setInstance(Theme instance) {
		Theme.instance = instance;
	}
}

abstract class ThemeColors {
	public Color applicationBackground;
}



class DarkTheme extends Theme {
	public static Color z900 = new Color(24, 24, 27);
	public static Color z800 = new Color(39, 39, 42);
	public static Color z700 = new Color(63, 63, 70);
	public static Color z500 = new Color(113, 113, 122);
	public static Color z200 = new Color(228, 228, 231);

	public static Color g600 = new Color(22, 163, 74);
	public static Color g500 = new Color(34, 197, 94);
	public static Color g400 = new Color(74, 222, 128);

	DarkTheme() {
		Theme.setInstance(this);
		
		buttonSuccessBG = g500;
		buttonSuccessPress = g400;
		buttonSuccessHover = g600;
		
		applicationBG = z900;
		applicationFG = Color.WHITE;
		seperator = z500;
		cardBG = z800;
		cardFG = new Color(250, 250, 250);
		cardHover = z700;
		cardSecondaryFG = z200;
		
		movieStar = new Color(222, 222, 128);
	}
}