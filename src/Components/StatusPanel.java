package Components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MovieCard.MovieStatus;
import MovieTracker.Language;
import MovieTracker.Theme;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RoundedPanel statusContainer;
	private RoundedPanel statusDot;
	private JLabel statusText;
	Theme theme;

	/**
	 * Create the panel.
	 */
	public StatusPanel(MovieStatus movieStatus) {
		theme = Theme.getInstance();
		
		setBorder(new EmptyBorder(2, 0, 6, 0));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEADING);
		setMaximumSize(new Dimension(32767, 10));
		setOpaque(false);
//			add(status);
		
		statusContainer = new RoundedPanel(8);
		statusContainer.setBorder(new EmptyBorder(0, 2, 0, 2));
		FlowLayout flowLayout_1 = (FlowLayout) statusContainer.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignOnBaseline(true);
		add(statusContainer);

		statusDot = new RoundedPanel(10);
		statusContainer.add(statusDot);
		
		statusText = new JLabel(movieStatus.toString());
		statusText.setBorder(new EmptyBorder(0, 2, 0, 2));
		statusText.setFont(new Font("Segoe UI", Font.BOLD, 12));
		statusContainer.add(statusText);
		
		updateStatus(movieStatus);
	}
	
	public void updateStatus(MovieStatus movieStatus) {
//		statusText.setText(movieStatus.toString());
		
		switch (movieStatus) {
			default:
				return;
			case Needs_Review:
				statusText.setText(Language.statusReview);
				statusContainer.setBackground(theme.statusReview);
				statusDot.setBackground(theme.statusReviewDot);
				break;
			case Might_Watch:
				statusText.setText(Language.statusMightWatch);
				statusContainer.setBackground(theme.statusMightWatch);
				statusDot.setBackground(theme.statusMightWatchDot);
				break;
			case Want_to_Watch:
				statusText.setText(Language.statusWantToWatch);
				statusContainer.setBackground(theme.statusWantToWatch);
				statusDot.setBackground(theme.statusWantToWatchDot);
				break;
			case Want_to_Rewatch:
				statusText.setText(Language.statusWantToRewatch);
				statusContainer.setBackground(theme.statusWantToRewatch);
				statusDot.setBackground(theme.statusWantToRewatchDot);
				break;
			case Watched:
				statusText.setText(Language.statusWatched);
				statusContainer.setBackground(theme.statusWatched);
				statusDot.setBackground(theme.statusWatchedDot);
				break;
		}
	}
	
	@Override
	public synchronized void addMouseListener(MouseListener l) {
		statusContainer.addMouseListener(l);
	}

}
