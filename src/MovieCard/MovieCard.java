package MovieCard;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import Components.ImagePanel;
import Components.StatusPanel;
import MovieTracker.MovieManager;
import MovieTracker.Theme;
import Pages.Mainframe;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MovieCard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MovieInfo info;

	Theme theme; // for easy access
	private JLabel titleText;
	private StatusPanel status;
	private JPanel rating;
	private JPanel yearLine;
	private JLabel yearText;
	private JPanel durationLine;
	private JLabel durationText;
	private ImagePanel[] stars;
	private ImagePanel image;
	private ImagePanel favoriteIcon;

	/**
	 * @wbp.eval.method.parameter info new MovieInfo("Back to the Future")
	 * @wbp.eval.method.parameter info2 new MovieInfo("The Terminator", 95, 2025)
	 * @wbp.eval.method.parameter info3 new MovieInfo("Pirates of the Caribbean: The Curse of the Black Pearl", 95, 2025)
	 */
	public MovieCard(MovieInfo info) {
		this.info = info;
		
		theme = Theme.getInstance();
		
		MovieCard card = this;
		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(theme.cardBG);
		Dimension cardSize = new Dimension((16*8)+12, 176+12);
		setPreferredSize(cardSize);
		setMaximumSize(new Dimension(176+12, 204+12));
		setMinimumSize(new Dimension(128+12, 176+12));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		image = new ImagePanel(info.getImagePath());
		image.setImagePosition(info.getImagePosition());
		Dimension imgSize = new Dimension(16*8, 9*8);
		image.setPreferredSize(imgSize);
		image.setMaximumSize(new Dimension(16*11, 9*11));
		image.setMinimumSize(new Dimension(16*8, 9*8));
		image.setBackground(theme.applicationBG);
		add(image);

		createTitle();
		
		status = new StatusPanel(info.getStatus());
		add(status);
		createRating();

		createYear();
		createDuration();

		Component verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(theme.cardHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(theme.cardBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Mainframe.showMovieInfo(card);
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int width = getMinimumSize().width;
				float sizeFactor = ((float)getWidth() - (float)width) / 48.0f;
				int height = (int)(28 * sizeFactor) + getMinimumSize().height;
				setPreferredSize(new Dimension(width, height));

				int imgWidth = image.getWidth();
				image.setPreferredSize(new Dimension(imgWidth, (int) (((float)(imgWidth) * 9) / 16.0)));
			}
		});
	}

	protected void createTitle() {
		JPanel titleLine = new JPanel();
		FlowLayout fl_title = (FlowLayout) titleLine.getLayout();
		fl_title.setVgap(6);
		fl_title.setHgap(6);
		titleLine.setMaximumSize(new Dimension(32767, 28));
		titleLine.setPreferredSize(new Dimension(32767, 28));
		titleLine.setOpaque(false);
		this.add(titleLine);
		
		String originalTitle = info.getName();

		titleText = new JLabel(originalTitle);
		titleText.setHorizontalAlignment(SwingConstants.CENTER);
		titleText.setVerticalAlignment(SwingConstants.TOP);
		titleText.setPreferredSize(new Dimension(16*8, 18));
		titleText.setFont(new Font("Arial", Font.BOLD, 16));
		titleText.setForeground(theme.cardFG);
		titleLine.add(titleText);
		
		titleLine.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				titleText.setPreferredSize(new Dimension(titleLine.getWidth()-12, 18));
			}
		});
		
//		for (int i = 0; i < 3; i++) {
//			int charsCut = (i+1)*14;
//			if (charsCut > originalTitle.length()) charsCut = originalTitle.length();
//			String filteredTitle = originalTitle.substring(i*14, charsCut) + "\n";
//			
//			
//			if (charsCut == originalTitle.length()) break;
//		}
	}

	protected void createYear() {
		yearLine = new JPanel();
		FlowLayout fl_yearLine = (FlowLayout) yearLine.getLayout();
		fl_yearLine.setVgap(0);
		fl_yearLine.setAlignment(FlowLayout.LEADING);
		yearLine.setMaximumSize(new Dimension(32767, 10));
		yearLine.setOpaque(false);
		this.add(yearLine);
		
		yearText = new JLabel(info.getYear()+"");
		yearText.setFont(new Font("Arial", Font.BOLD, 14));
		yearText.setForeground(theme.cardSecondaryFG);
		yearLine.add(yearText);

		if (info.getYear() == 0) {
			yearLine.setVisible(false);
		}
	}

	protected void createDuration() {
		durationLine = new JPanel();
		FlowLayout fl_duration = (FlowLayout) durationLine.getLayout();
		fl_duration.setVgap(0);
		fl_duration.setAlignment(FlowLayout.LEADING);
		durationLine.setMaximumSize(new Dimension(32767, 10));
		durationLine.setOpaque(false);
		this.add(durationLine);
		
		durationText = new JLabel(info.getDuration());
		durationText.setFont(new Font("Arial", Font.BOLD, 14));
		durationText.setForeground(theme.cardSecondaryFG);
		durationLine.add(durationText);

		if (info.getDurationMins() == 0) {
			durationLine.setVisible(false);
		}
	}
	
	protected void createRating() {
		rating = new JPanel();
		rating.setBorder(new EmptyBorder(0, 0, 3, 0));
		FlowLayout fl_rating = (FlowLayout) rating.getLayout();
		fl_rating.setHgap(4);
		fl_rating.setVgap(0);
		fl_rating.setAlignment(FlowLayout.LEADING);
		rating.setMaximumSize(new Dimension(32767, 10));
		rating.setOpaque(false);
		add(rating);

		favoriteIcon = new ImagePanel(theme.favoriteOn);
		favoriteIcon.setPreferredSize(new Dimension(10, 10));
		if (!info.isFavorite()) favoriteIcon.setVisible(false);
		rating.add(favoriteIcon);

		stars = new ImagePanel[6];

		updateRating(info.getRate());

		if (!info.isFavorite() && info.getRate() == 0) {
			rating.setVisible(false);
		}
	}

	void updateRating(int starsSelected) {
		for (int i = 0; i < stars.length; i++) {
			ImagePanel star = stars[i];
			
			if (i >= starsSelected) {
				if (star != null) {
					rating.remove(star);
					stars[i] = null;
				}
				continue;
			}

			if (star == null) {
				star = new ImagePanel(theme.star);
				if (i == 5) {
					star.setImage(theme.starSuper);
				}

				stars[i] = star;
				rating.add(star);
			}
		}

		if (!info.isFavorite() && starsSelected == 0) {
			rating.setVisible(false);
		} else rating.setVisible(true);
	}
	

	
	public void updateMovieData() {
		image.setImage(info.getImagePath());
		image.setImagePosition(info.getImagePosition());
		titleText.setText(info.getName());
		status.updateStatus(info.getStatus());
		if (info.isFavorite()) favoriteIcon.setVisible(true);
		else favoriteIcon.setVisible(false);
		updateRating(info.getRate());
		if (info.getYear() == 0) {
			yearLine.setVisible(false);
		} else {
			yearLine.setVisible(true);
			yearText.setText(info.getYear()+"");
		}
		if (info.getDurationMins() == 0) {
			durationLine.setVisible(false);
		} else {
			durationLine.setVisible(true);
			durationText.setText(info.getDuration());
		}
		
		MovieManager.getInstance().saveMovies();
	}
	

	public MovieInfo getMovie() {
		return info;
	}
}
