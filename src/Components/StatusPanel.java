package Components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MovieCard.MovieStatus;
import MovieTracker.Theme;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel statusText;

	/**
	 * Create the panel.
	 */
	public StatusPanel(MovieStatus movieStatus) {
		Theme theme = Theme.getInstance();
		
		setBorder(new EmptyBorder(2, 0, 6, 0));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEADING);
		setMaximumSize(new Dimension(32767, 10));
		setOpaque(false);
//			add(status);
		
		RoundedPanel roundedPanel = new RoundedPanel(8);
		roundedPanel.setBorder(new EmptyBorder(0, 2, 0, 2));
		roundedPanel.setBackground(theme.buttonSuccessHover);
		FlowLayout flowLayout_1 = (FlowLayout) roundedPanel.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignOnBaseline(true);
		add(roundedPanel);

		RoundedPanel statusColor = new RoundedPanel(10);
		statusColor.setBackground(theme.buttonSuccessPress);
		roundedPanel.add(statusColor);
		
		statusText = new JLabel(movieStatus.toString());
		statusText.setBorder(new EmptyBorder(0, 2, 0, 2));
		statusText.setFont(new Font("Segoe UI", Font.BOLD, 12));
		roundedPanel.add(statusText);
	}
	
	public void updateStatus(MovieStatus movieStatus) {
		statusText.setText(movieStatus.toString());
	}

}
