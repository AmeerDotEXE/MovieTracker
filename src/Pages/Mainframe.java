package Pages;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MovieTracker.Theme;

import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.CardLayout;

public class Mainframe extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cd;
	Theme theme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Mainframe frame = new Mainframe();
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
		setTitle("Movie Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 360);
        setMinimumSize(new Dimension(480, 360));
        setMaximumSize(new Dimension(1056, 792));
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(theme.applicationBG);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		cd = new CardLayout(0, 0);
		contentPane.setLayout(cd);
		
		JPanel moviesPane = new MoviesPage();
		contentPane.add(moviesPane, "moviesList");
		
		JPanel movieInfoPane = new MovieInfoPage();
		contentPane.add(movieInfoPane, "movieInfoPane");

		cd.show(contentPane, "movieInfoPane");
	}
}
