package MovieCard;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import Components.RoundedPanel;
import MovieTracker.Theme;


public class MovieCard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MovieInfo info;
	Theme theme; // for easy access

	/**
	 * Create the panel.
	 */
	public MovieCard(MovieInfo info) {
		this.info = info;
		
		theme = Theme.getInstance();
		
		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(theme.cardBG);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel image = new JPanel();
		image.setPreferredSize(new Dimension(10, 60));
		image.setMinimumSize(new Dimension(10, 60));
		image.setBackground(theme.applicationBG);
		add(image);

		createTitle();
		
		createStatus();
		createRating();

		createYear();
		createDuration();
	}

	protected void createTitle() {
		JPanel titleLine = new JPanel();
		FlowLayout fl_title = (FlowLayout) titleLine.getLayout();
		fl_title.setVgap(4);
		fl_title.setAlignment(FlowLayout.LEADING);
		titleLine.setMaximumSize(new Dimension(32767, 10));
		titleLine.setOpaque(false);
		this.add(titleLine);
		
		String filteredTitle = info.getName();
		filteredTitle = filteredTitle.substring(0, Math.min(10, filteredTitle.length()));
		JLabel titleText = new JLabel(filteredTitle);
		titleText.setFont(new Font("Arial", Font.BOLD, 16));
		titleText.setForeground(theme.cardFG);
		titleLine.add(titleText);
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
		status.setBorder(new EmptyBorder(0, 0, 6, 0));
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
