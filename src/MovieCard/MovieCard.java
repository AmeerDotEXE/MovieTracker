package MovieCard;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import Components.ImagePanel;
import Components.RoundedPanel;
import MovieTracker.Theme;
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

	/**
	 * @wbp.eval.method.parameter info new MovieInfo("Back to the Future")
	 * @wbp.eval.method.parameter info2 new MovieInfo("The Terminator", 95, 2025)
	 * @wbp.eval.method.parameter info3 new MovieInfo("Pirates of the Caribbean: The Curse of the Black Pearl", 95, 2025)
	 */
	public MovieCard(MovieInfo info) {
		this.info = info;
		
		theme = Theme.getInstance();
		
		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(theme.cardBG);
		Dimension cardSize = new Dimension((16*8)+12, 176+12);
		setPreferredSize(cardSize);
		setMaximumSize(new Dimension(176+12, 204+12));
		setMinimumSize(new Dimension(128+12, 176+12));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		ImagePanel image = new ImagePanel(info.getImagePath());
		image.setImagePosition(info.getImagePosition());
		Dimension imgSize = new Dimension(16*8, 9*8);
		image.setPreferredSize(imgSize);
		image.setMaximumSize(new Dimension(16*11, 9*11));
		image.setMinimumSize(new Dimension(16*8, 9*8));
		image.setBackground(theme.applicationBG);
		add(image);

		createTitle();
		
		createStatus();
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

		JLabel titleText = new JLabel(originalTitle);
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
		if (info.getYear() == 0) return;
		JPanel yearLine = new JPanel();
		FlowLayout fl_year = (FlowLayout) yearLine.getLayout();
		fl_year.setVgap(0);
		fl_year.setAlignment(FlowLayout.LEADING);
		yearLine.setMaximumSize(new Dimension(32767, 10));
		yearLine.setOpaque(false);
		this.add(yearLine);
		
		JLabel yearText = new JLabel(info.getYear()+"");
		yearText.setFont(new Font("Arial", Font.BOLD, 14));
		yearText.setForeground(theme.cardSecondaryFG);
		yearLine.add(yearText);
	}

	protected void createDuration() {
		if (info.getDurationMins() == 0) return;
		JPanel durationLine = new JPanel();
		FlowLayout fl_duration = (FlowLayout) durationLine.getLayout();
		fl_duration.setVgap(0);
		fl_duration.setAlignment(FlowLayout.LEADING);
		durationLine.setMaximumSize(new Dimension(32767, 10));
		durationLine.setOpaque(false);
		this.add(durationLine);
		
		JLabel durationText = new JLabel(info.getDuration());
		durationText.setFont(new Font("Arial", Font.BOLD, 14));
		durationText.setForeground(theme.cardSecondaryFG);
		durationLine.add(durationText);
	}
	
	protected void createStatus() {
		JPanel status = new JPanel();
		status.setBorder(new EmptyBorder(2, 0, 6, 0));
		FlowLayout flowLayout = (FlowLayout) status.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEADING);
		status.setMaximumSize(new Dimension(32767, 10));
		status.setOpaque(false);
		add(status);
		
		RoundedPanel roundedPanel = new RoundedPanel(8);
		roundedPanel.setBorder(new EmptyBorder(0, 2, 0, 2));
		roundedPanel.setBackground(theme.buttonSuccessHover);
		FlowLayout flowLayout_1 = (FlowLayout) roundedPanel.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignOnBaseline(true);
		status.add(roundedPanel);

		RoundedPanel statusColor = new RoundedPanel(10);
		statusColor.setBackground(theme.buttonSuccessPress);
		roundedPanel.add(statusColor);
		
		JLabel lblNewLabel_1 = new JLabel("Watched");
		lblNewLabel_1.setBorder(new EmptyBorder(0, 2, 0, 2));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		roundedPanel.add(lblNewLabel_1);
	}
	
	protected void createRating() {
		if (info.getRate() == 0) return;
		JPanel rating = new JPanel();
		rating.setBorder(new EmptyBorder(0, 0, 3, 0));
		FlowLayout flowLayout = (FlowLayout) rating.getLayout();
		flowLayout.setHgap(4);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEADING);
		rating.setMaximumSize(new Dimension(32767, 10));
		rating.setOpaque(false);
		add(rating);

		RoundedPanel star = new RoundedPanel(10);
		star.setBackground(theme.movieStar);
		rating.add(star);
		
		RoundedPanel star_1 = new RoundedPanel(10);
		star_1.setBackground(theme.movieStar);
		rating.add(star_1);

		RoundedPanel star_2 = new RoundedPanel(10);
		star_2.setBackground(theme.movieStar);
		rating.add(star_2);

		RoundedPanel star_3 = new RoundedPanel(10);
		star_3.setBackground(theme.movieStar);
		rating.add(star_3);
	}

}
