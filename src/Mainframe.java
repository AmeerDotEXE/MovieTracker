import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MovieTracker.MovieManager;
import MovieTracker.Theme;

import javax.swing.UIManager;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.JSeparator;

public class Mainframe extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		Theme theme = Theme.getInstance();
		
		setBackground(theme.applicationBG);
		setTitle("Movie Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 360);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(theme.applicationBG);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setMaximumSize(new Dimension(32767, 10));
		contentPane.add(panel);
		
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
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(theme.buttonSuccessBG);
		panel.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(theme.seperator);
		separator.setMaximumSize(new Dimension(32767, 2));
		contentPane.add(separator);
		
		Component verticalStrut_1 = Box.createVerticalStrut(8);
		contentPane.add(verticalStrut_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setBackground(theme.applicationBG);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.add(scrollPane);
		
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
		footer.setMaximumSize(new Dimension(32767, 10));
		footer.setOpaque(false);
		contentPane.add(footer);
		
		JLabel lblNewLabel = new JLabel("Created by Ameer");
		lblNewLabel.setForeground(theme.applicationFG);
		footer.add(lblNewLabel);
	}

}
