package Pages;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Components.ImagePanel;
import Components.RoundedPanel;
import MovieTracker.Theme;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import MovieCard.MovieCard;
import MovieCard.MovieStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovieInfoPage extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Theme theme; // for easy access

	/**
	 * Create the panel.
	 */
	public MovieInfoPage() {
		theme = Theme.getInstance();
		
		setBackground(theme.applicationBG);
		setBorder(null);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel row1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) row1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		row1.setOpaque(false);
		add(row1);
		
		JPanel imageHolder = new JPanel();
		imageHolder.setBorder(null);
		row1.add(imageHolder);
		imageHolder.setOpaque(false);
		
		JSlider slider = new JSlider();
		slider.setInverted(true);
		slider.setFocusable(false);
		slider.setOpaque(false);
		slider.setPreferredSize(new Dimension(8, 9*11 + 4));
		slider.setOrientation(SwingConstants.VERTICAL);
		imageHolder.add(slider);
		
		ImagePanel image = new ImagePanel("movie-images/terminator.jpg");
		image.setPreferredSize(new Dimension(16*11, 9*11));
		imageHolder.add(image);
		
		JPanel metadata = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) metadata.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		metadata.setBorder(new EmptyBorder(8, 0, 0, 0));
		metadata.setPreferredSize(new Dimension(200, 99));
		metadata.setOpaque(false);
		row1.add(metadata);
//		metadata.setLayout(new BoxLayout(metadata, BoxLayout.Y_AXIS));
		
		JTextField titleField = new JTextField();
		titleField.setMaximumSize(new Dimension(9999, 18));
		titleField.setOpaque(false);
		titleField.setForeground(theme.applicationFG);
		titleField.setText("Terminator");
//		titleField.setBorder(new EmptyBorder(4, 0, 4, 0));
		titleField.setBorder(null);
		titleField.setPreferredSize(new Dimension(16*8, 18));
		titleField.setFont(new Font("Arial", Font.BOLD, 16));
		metadata.add(titleField);
		titleField.setColumns(10);
		
		JPanel status = MovieCard.createStatus(MovieStatus.Want_to_Rewatch);
		status.setBorder(new EmptyBorder(0, 0, 4, 0));
		metadata.add(status);

		JPanel rating = createStarSelector(3);
		metadata.add(rating);
		
//		Component verticalGlue = Box.createVerticalGlue();
//		metadata.add(verticalGlue);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				image.setImagePosition(slider.getValue());
				image.repaint();
			}
		});
	}
	
	JPanel createStarSelector(int starsSelected) {
		RoundedPanel rating = new RoundedPanel(8);
		FlowLayout flowLayout = (FlowLayout) rating.getLayout();
		flowLayout.setHgap(4);
		flowLayout.setVgap(4);
		flowLayout.setAlignment(FlowLayout.LEADING);
		rating.setMaximumSize(new Dimension(32767, 10));
//		rating.setOpaque(false);
		rating.setBackground(theme.cardBG);
//		add(rating);
	    
		ImagePanel[] stars = new ImagePanel[6];
		for (int i = 0; i < stars.length; i++) {
			ImagePanel star = new ImagePanel(theme.starEmpty);
			star.setCursor(new Cursor(Cursor.HAND_CURSOR));
			star.setPreferredSize(new Dimension(15, 15));
			if (i >= starsSelected) {
				star.setImage(theme.starEmpty);
			} else if (i == 5) {
				star.setImage(theme.starSuper);
			} else star.setImage(theme.star);
			
			int num = i+1;

			star.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < stars.length; i++) {
						ImagePanel star = stars[i];
						if (i >= num) {
							star.setImage(theme.starEmpty);
						} else if (i == 5) {
							star.setImage(theme.starSuper);
						} else star.setImage(theme.star);
					}
				}
			});
			
			stars[i] = star;
			rating.add(star);
		}
		
		return rating;
	}

}
