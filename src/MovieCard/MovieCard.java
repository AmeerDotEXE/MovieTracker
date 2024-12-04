package MovieCard;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import Components.RoundedPanel;


public class MovieCard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MovieInfo info;

	/**
	 * Create the panel.
	 */
	public MovieCard(MovieInfo info) {
		this.info = info;
		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(new Color(39, 39, 42));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel image = new JPanel();
		image.setPreferredSize(new Dimension(10, 60));
		image.setMinimumSize(new Dimension(10, 60));
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
		
		JLabel titleText = new JLabel(info.getName());
		titleText.setFont(new Font("Arial", Font.BOLD, 16));
		titleText.setForeground(new Color(250, 250, 250));
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
		yearText.setForeground(new Color(228, 228, 231));
		yearLine.add(yearText);
	}

	protected void createDuration() {
		JPanel durationLine = new JPanel();
		FlowLayout fl_duration = (FlowLayout) durationLine.getLayout();
		fl_duration.setVgap(0);
		fl_duration.setAlignment(FlowLayout.LEADING);
		durationLine.setMaximumSize(new Dimension(32767, 10));
		durationLine.setOpaque(false);
		this.add(durationLine);
		
		JLabel durationText = new JLabel(info.getDuration());
		durationText.setFont(new Font("Arial", Font.BOLD, 14));
		durationText.setForeground(new Color(228, 228, 231));
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
		roundedPanel.setBackground(new Color(22, 163, 74));
		FlowLayout flowLayout_1 = (FlowLayout) roundedPanel.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignOnBaseline(true);
		status.add(roundedPanel);

		RoundedPanel statusColor = new RoundedPanel(10);
		statusColor.setBackground(new Color(74, 222, 128));
		roundedPanel.add(statusColor);
		
		JLabel lblNewLabel_1 = new JLabel("Watched");
		lblNewLabel_1.setBorder(new EmptyBorder(0, 2, 0, 2));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		roundedPanel.add(lblNewLabel_1);
	}
	
	protected void createRating() {
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
		star.setBackground(new Color(222, 222, 128));
		rating.add(star);
		
		RoundedPanel star_1 = new RoundedPanel(10);
		star_1.setBackground(new Color(222, 222, 128));
		rating.add(star_1);

		RoundedPanel star_2 = new RoundedPanel(10);
		star_2.setBackground(new Color(222, 222, 128));
		rating.add(star_2);

		RoundedPanel star_3 = new RoundedPanel(10);
		star_3.setBackground(new Color(222, 222, 128));
		rating.add(star_3);
	}

}
