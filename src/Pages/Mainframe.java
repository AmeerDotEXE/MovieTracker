package Pages;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MovieCard.MovieCard;
import MovieTracker.Language;
import MovieTracker.Theme;

import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.CardLayout;

public class Mainframe extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Mainframe instance;
	
	Theme theme;
	private JPanel contentPane;
	private CardLayout cl;
	private MovieInfoPage movieInfoPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			
			if (arg.equalsIgnoreCase("--lang")) {
				i++;
				Language.setLanguageCode(args[i].toLowerCase());
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Mainframe frame = new Mainframe();
					Mainframe.instance = frame;
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Mainframe() {
		theme = Theme.getInstance();
		
		setBackground(theme.applicationBG);
		setTitle(Language.applicationTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 360);
        setMinimumSize(new Dimension(480, 360));
        setMaximumSize(new Dimension(1056, 792));
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(theme.applicationBG);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);
		
		JPanel moviesPane = new MoviesPage();
		contentPane.add(moviesPane, "moviesList");
		
		movieInfoPane = new MovieInfoPage();
		contentPane.add(movieInfoPane, "movieInfoPane");
	}
	
	public static void showMovieInfo(MovieCard movieCard) {
		Mainframe frame = Mainframe.instance;
		frame.movieInfoPane.selectMovie(movieCard);
		frame.cl.show(frame.contentPane, "movieInfoPane");
	}
	
	public static void showMoviePage() {
		Mainframe frame = Mainframe.instance;
		frame.cl.show(frame.contentPane, "moviesList");
	}
}
