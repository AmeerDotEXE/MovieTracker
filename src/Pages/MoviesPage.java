package Pages;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import MovieCard.MovieInfo;
import MovieTracker.MovieManager;
import MovieTracker.Theme;

public class MoviesPage extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MoviesPage() {
		Theme theme = Theme.getInstance();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(theme.applicationBG);
		setBorder(null);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setMaximumSize(new Dimension(32767, 32));
		add(panel);
		
		JButton btnNewButton = new JButton("Add Movie");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonSuccessHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonSuccessBG);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonSuccessPress);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonSuccessBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				MovieManager.getInstance().addMovie(new MovieInfo("No Name"));
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(theme.buttonSuccessBG);
		panel.add(btnNewButton);
		
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel_1.setBackground(theme.applicationBG);
		scrollPane.setViewportView(panel_1);
		panel_1.setMaximumSize(scrollPane.getSize());
		panel_1.setLayout(new GridLayout(0, 3, 4, 4));
		
		MovieManager.getInstance().generateMovieCards(panel_1);
		
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
				panel_1.setLayout(new GridLayout(0, columns, 4, 4));
			}
		});
	}

}
