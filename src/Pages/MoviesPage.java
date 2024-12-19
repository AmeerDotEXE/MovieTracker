package Pages;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.function.Predicate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import MovieCard.MovieCard;
import MovieCard.MovieInfo;
import MovieCard.MovieStatus;
import MovieTracker.Language;
import MovieTracker.MovieManager;
import MovieTracker.Theme;

public class MoviesPage extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Theme theme;
	private JPanel moviesContainer;
	private boolean isFiltering = false;
	private LinkedList<MovieCard> movieCards = new LinkedList<MovieCard>();

	/**
	 * Create the panel.
	 */
	public MoviesPage() {
		theme = Theme.getInstance();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(theme.applicationBG);
		setBorder(null);
		
		Box controlBar = createControlBar();
		add(controlBar);
		
		Component verticalStrut_1_1 = Box.createVerticalStrut(4);
		add(verticalStrut_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(theme.seperator);
		separator.setMaximumSize(new Dimension(32767, 2));
		add(separator);
		
		Component verticalStrut_1 = Box.createVerticalStrut(8);
		add(verticalStrut_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(1056, 32767));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setBackground(theme.applicationBG);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);
		
		moviesContainer = new JPanel() {
			private static final long serialVersionUID = -1055015007625188194L;
			
			@Override
			public Component add(Component comp) {
				if (comp instanceof MovieCard && !isFiltering) movieCards.add((MovieCard)comp);
				return super.add(comp);
			}
			@Override
			public Component add(Component comp, int index) {
				if (comp instanceof MovieCard && !isFiltering) movieCards.add(index, (MovieCard)comp);
				return super.add(comp, index);
			}
		};
		moviesContainer.setBorder(new EmptyBorder(4, 4, 4, 4));
		moviesContainer.setBackground(theme.applicationBG);
		scrollPane.setViewportView(moviesContainer);
		moviesContainer.setMaximumSize(scrollPane.getSize());
		moviesContainer.setLayout(new GridLayout(0, 3, 4, 4));
		
		MovieManager.getInstance().generateMovieCards(moviesContainer);
		
		JPanel footer = new JPanel();
		footer.setBorder(new EmptyBorder(4, 0, 0, 0));
		FlowLayout flowLayout_1 = (FlowLayout) footer.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		footer.setMaximumSize(new Dimension(32767, 28));
		footer.setOpaque(false);
		add(footer);
		
		JLabel lblNewLabel = new JLabel("Created by Ameer");
		lblNewLabel.setForeground(theme.applicationFG);
		footer.add(lblNewLabel);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int columns = 3;
				// 1 element min width 140, max width 188
				// 3 Elements width 420 with spaces is 428
				//   with minimum total 480
				// but total width for 4 elements is 624 and 5 is 768 and 6 is 912
				// which means maximum total should be width+(48*columns)
				if (getWidth() > 912) columns = 6;
				else if (getWidth() > 768) columns = 5;
				else if (getWidth() > 624) columns = 4;
				moviesContainer.setLayout(new GridLayout(0, columns, 4, 4));
			}
		});
	}
	
	Box createControlBar() {
		Box controlBar = Box.createHorizontalBox();
		controlBar.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton watchNextButton = new JButton(Language.tabWatchNext);
		watchNextButton.setBorder(new EmptyBorder(4, 8, 0, 8));
		watchNextButton.setForeground(theme.applicationSecondaryFG);
		watchNextButton.setOpaque(false);
		watchNextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		watchNextButton.setFocusable(false);
		watchNextButton.setBorderPainted(false);
		watchNextButton.setFont(new Font("Arial", Font.BOLD, 16));
		controlBar.add(watchNextButton);
		
		JButton listAllButton = new JButton(Language.tabAllMovies);
		watchNextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		listAllButton.setOpaque(false);
		listAllButton.setForeground(theme.applicationFG);
		listAllButton.setFont(new Font("Arial", Font.BOLD, 16));
		listAllButton.setFocusable(false);
		listAllButton.setBorderPainted(false);
		listAllButton.setBorder(new EmptyBorder(4, 8, 0, 8));
		controlBar.add(listAllButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		controlBar.add(horizontalGlue);
		
		JButton addMovieButton = new JButton(Language.buttonAddMovie);
		addMovieButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMovieButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addMovieButton.setBackground(theme.buttonSuccessHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				addMovieButton.setBackground(theme.buttonSuccessBG);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				addMovieButton.setBackground(theme.buttonSuccessPress);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				addMovieButton.setBackground(theme.buttonSuccessBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				MovieManager.getInstance().addMovie(new MovieInfo("No Name"));
			}
		});
		addMovieButton.setFocusable(false);
		addMovieButton.setBorderPainted(false);
		addMovieButton.setBackground(theme.buttonSuccessBG);
		controlBar.add(addMovieButton);
		
		listAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listAllButton.setForeground(theme.applicationFG);
				watchNextButton.setForeground(theme.applicationSecondaryFG);
				
				updateFilter(m -> true);
			}
		});
		watchNextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				watchNextButton.setForeground(theme.applicationFG);
				listAllButton.setForeground(theme.applicationSecondaryFG);
				
				updateFilter(m -> 
					m.getMovie().getStatus() == MovieStatus.Might_Watch ||
					m.getMovie().getStatus() == MovieStatus.Want_to_Watch ||
					m.getMovie().getStatus() == MovieStatus.Want_to_Rewatch
				);
			}
		});
		
		return controlBar;
	}
	
	void updateFilter(Predicate<? super MovieCard> filter) {
		isFiltering = true;
		moviesContainer.removeAll();
		for (MovieCard movieCard : movieCards) {
			if (filter.test(movieCard)) moviesContainer.add(movieCard);
		}
		isFiltering = false;
		moviesContainer.revalidate();
	}

}
