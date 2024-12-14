package MovieTracker;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Theme {
	private static Theme instance;
	
	public BufferedImage star;
	public BufferedImage starEmpty;
	public BufferedImage starSuper;

	public BufferedImage favoriteOn;
	public BufferedImage favoriteOff;

	public Color buttonSuccessBG;
	public Color buttonSuccessPress;
	public Color buttonSuccessHover;

	public Color buttonBG;
	public Color buttonFG;
	public Color buttonPress;
	public Color buttonHover;
	
	public Color applicationBG;
	public Color applicationFG;
	public Color applicationSecondaryFG;
	public Color seperator;
	public Color cardBG;
	public Color cardFG;
	public Color cardHover;
	public Color cardSecondaryFG;

	public Color statusReview;
	public Color statusReviewDot;
	public Color statusMightWatch;
	public Color statusMightWatchDot;
	public Color statusWantToWatch;
	public Color statusWantToWatchDot;
	public Color statusWantToRewatch;
	public Color statusWantToRewatchDot;
	public Color statusWatched;
	public Color statusWatchedDot;
	
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



class DarkTheme extends Theme {
	public static Color z900 = new Color(24, 24, 27);
	public static Color z800 = new Color(39, 39, 42);
	public static Color z700 = new Color(63, 63, 70);
	public static Color z600 = new Color(82, 82, 91);
	public static Color z500 = new Color(113, 113, 122);
	public static Color z300 = new Color(212, 212, 216);
	public static Color z200 = new Color(228, 228, 231);
	public static Color z100 = new Color(244, 244, 245);

	public static Color g700 = new Color(21, 128, 61);
	public static Color g600 = new Color(22, 163, 74);
	public static Color g500 = new Color(34, 197, 94);
	public static Color g400 = new Color(74, 222, 128);
	
	public static Color r700 = new Color(185, 28, 28);
	public static Color r500 = new Color(239, 68, 68);
	
	public static Color b700 = new Color(29, 78, 216);
	public static Color b500 = new Color(59, 130, 246);

	DarkTheme() {
		Theme.setInstance(this);
		try {
			File imageFile = new File("assets/star-dark.jpg");
			BufferedImage image = ImageIO.read(imageFile);
			star = image;
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Star image not found.");
		}
		try {
			File imageFile = new File("assets/star-dark-empty.jpg");
			BufferedImage image = ImageIO.read(imageFile);
			starEmpty = image;
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Empty Star image not found.");
		}
		try {
			File imageFile = new File("assets/star-dark-super.jpg");
			BufferedImage image = ImageIO.read(imageFile);
			starSuper = image;
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Super Star image not found.");
		}
		try {
			File imageFile = new File("assets/fav-dark-on.jpg");
			BufferedImage image = ImageIO.read(imageFile);
			favoriteOn = image;
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Favorite On image not found.");
		}
		try {
			File imageFile = new File("assets/fav-dark-off.jpg");
			BufferedImage image = ImageIO.read(imageFile);
			favoriteOff = image;
		} catch (IOException ex) {
			// handle exception...
			System.out.print("Favorite Off image not found.");
		}
		
		buttonSuccessBG = g500;
		buttonSuccessPress = g400;
		buttonSuccessHover = g600;

		buttonBG = z700;
		buttonFG = z200;
		buttonPress = z600;
		buttonHover = z800;
		
		applicationBG = z900;
		applicationFG = Color.WHITE;
		applicationSecondaryFG = new Color(128, 128, 128);
		seperator = z500;
		cardBG = z800;
		cardFG = z100;
		cardHover = z700;
		cardSecondaryFG = z300;

		statusReview = g600;
		statusReviewDot = g400;
		statusMightWatch = g600;
		statusMightWatchDot = g400;
		statusWantToWatch = b500;
		statusWantToWatchDot = b700;
		statusWantToRewatch = b500;
		statusWantToRewatchDot = b700;
		statusWatched = r500;
		statusWatchedDot = r700;
	}
}