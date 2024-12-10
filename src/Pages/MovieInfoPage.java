package Pages;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import Components.ImagePanel;
import Components.RoundedPanel;
import Components.StatusPanel;
import MovieTracker.Theme;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import MovieCard.MovieCard;
import MovieCard.MovieInfo;
import MovieCard.MovieStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;

public class MovieInfoPage extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Theme theme; // for easy access
	private MovieCard movieCard;
	private MovieInfo movie;
	private JTextField titleField;
	private JTextField durationField;
	private JTextField yearField;
	private JTextField txtFirstWatch;
	private JTextField txtLastWatch;
	private JTextField txtGerne;
	private JTextField txtCast;
	private ImagePanel image;
	private JSlider slider;
	private ImagePanel favBtn;
	private ImagePanel[] stars;
	private StatusPanel status;
	boolean isFavorited = false;

	/**
	 * Create the panel.
	 */
 	public MovieInfoPage() {
		theme = Theme.getInstance();
		
		setBackground(theme.applicationBG);
		setBorder(null);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel row1 = new JPanel();
		row1.setMaximumSize(new Dimension(32767, 119));
		row1.setOpaque(false);
		add(row1);
		
		JPanel imageField = createImageField();
		row1.add(imageField);
		
		JPanel metadata = createMetadataArea();
		row1.add(metadata);
		
		JPanel row2 = new JPanel();
		row2.setMaximumSize(new Dimension(32767, 54));
		row2.setOpaque(false);
		add(row2);
		
		Box infoArea = createInfoArea();
		row2.add(infoArea);
		
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(2, 40));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(theme.seperator);
		row2.add(separator);
		
		Box dateArea = createDateArea();
		row2.add(dateArea);
		
		JPanel row3 = new JPanel();
		row3.setVisible(false);
		row3.setOpaque(false);
		add(row3);
		
		txtGerne = new JTextField();
		txtGerne.setText("Gerne");
		txtGerne.setPreferredSize(new Dimension(128, 18));
		txtGerne.setOpaque(false);
		txtGerne.setMaximumSize(new Dimension(9999, 18));
		txtGerne.setForeground(Color.WHITE);
		txtGerne.setFont(new Font("Arial", Font.BOLD, 16));
		txtGerne.setColumns(10);
		txtGerne.setBorder(null);
		row3.add(txtGerne);
		
		txtCast = new JTextField();
		txtCast.setText("Cast");
		txtCast.setPreferredSize(new Dimension(128, 18));
		txtCast.setOpaque(false);
		txtCast.setMaximumSize(new Dimension(9999, 18));
		txtCast.setForeground(Color.WHITE);
		txtCast.setFont(new Font("Arial", Font.BOLD, 16));
		txtCast.setColumns(10);
		txtCast.setBorder(null);
		row3.add(txtCast);
		
		Component verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		JPanel navPanel = new JPanel();
		navPanel.setMaximumSize(new Dimension(32767, 2));
		FlowLayout flowLayout = (FlowLayout) navPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		navPanel.setOpaque(false);
		add(navPanel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonBG);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonPress);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnNewButton.setBackground(theme.buttonBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				movieCard.updateMovieData();
				Mainframe.showMoviePage();
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(theme.buttonBG);
		btnNewButton.setForeground(theme.buttonFG);
		navPanel.add(btnNewButton);
	}
	
	public void selectMovie(MovieCard movieCard) {
		movie = movieCard.getMovie();
		this.movieCard = movieCard;
		
		// update input fields
		titleField.setText(movie.getName());
		status.updateStatus(movie.getStatus());
		if (movie.isFavorite()) favBtn.setImage(theme.favoriteOn);
		else favBtn.setImage(theme.favoriteOff);
		updateStarSelector(movie.getRate());
		durationField.setText(movie.getDuration());
		yearField.setText(movie.getYear()+"");
		image.setImage(movie.getImagePath());
		slider.setValue(movie.getImagePosition());
//		txtFirstWatch.setText(movie.getName());
//		txtLastWatch.setText(movie.getName());
	}
	
	
	
 	JPanel createMetadataArea() {
		JPanel metadata = new JPanel();
		metadata.setBorder(new EmptyBorder(8, 0, 0, 0));
		metadata.setPreferredSize(new Dimension(200, 99));
		metadata.setOpaque(false);
		// row1.add(metadata);
		metadata.setLayout(new BoxLayout(metadata, BoxLayout.Y_AXIS));
//		metadata.setLayout(new BoxLayout(metadata, BoxLayout.Y_AXIS));
		
		titleField = new JTextField();
		textFieldStyle(titleField);
		titleField.setText("Terminator");
		metadata.add(titleField);


		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBackground(theme.cardBG);
        LineBorder border = new LineBorder(theme.cardHover);
		popupMenu.setBorder(border);
		
		status = new StatusPanel(MovieStatus.Want_to_Rewatch);
		status.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(status, status.getX(), status.getY());
			}
		});
		status.setBorder(new EmptyBorder(8, 0, 4, 0));
		metadata.add(status);
		
		JPanel statusContainer = new JPanel();
		statusContainer.setLayout(new BoxLayout(statusContainer, BoxLayout.Y_AXIS));
		statusContainer.setBackground(theme.cardBG);
		statusContainer.setBorder(new EmptyBorder(4, 4, 4, 4));
		popupMenu.add(statusContainer);
		
		MovieStatus[] allStatuses = new MovieStatus[5];
		allStatuses[0] = MovieStatus.Needs_Review;
		allStatuses[1] = MovieStatus.Might_Watch;
		allStatuses[2] = MovieStatus.Want_to_Watch;
		allStatuses[3] = MovieStatus.Want_to_Rewatch;
		allStatuses[4] = MovieStatus.Watched;

		for (int i = 0; i < allStatuses.length; i++) {
			MovieStatus selectedStatus = allStatuses[i];
			
			StatusPanel statusOption = new StatusPanel(selectedStatus);
			statusOption.setBorder(new EmptyBorder(2, 2, 2, 2));
			statusOption.setMaximumSize(new Dimension(200, 80));
			
			statusOption.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					movie.setStatus(selectedStatus);
					status.updateStatus(selectedStatus);
					popupMenu.setVisible(false);
				}
			});
			
			statusContainer.add(statusOption);
		}

		Box scoreStuff = Box.createHorizontalBox();
		scoreStuff.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JPanel favButton = createFavButton();
		scoreStuff.add(favButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(8);
		scoreStuff.add(horizontalStrut);
		
		JPanel rating = createStarSelector();
		scoreStuff.add(rating);
		
		metadata.add(scoreStuff);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		scoreStuff.add(horizontalGlue);
		
		Component verticalGlue = Box.createVerticalGlue();
		metadata.add(verticalGlue);
		
		
		
		titleField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = titleField.getText();
				if (newName == null) return;
				
				newName = newName.replaceAll(",", ".");
				movie.setName(titleField.getText());
				titleField.setText(newName);
			}
		});
		
		return metadata;
	}

	JPanel createImageField() {
		JPanel imageHolder = new JPanel();
		imageHolder.setBorder(null);
		// row1.add(imageHolder);
		imageHolder.setOpaque(false);
		
		slider = new JSlider();
		slider.setInverted(true);
		slider.setFocusable(false);
		slider.setOpaque(false);
		slider.setPreferredSize(new Dimension(8, 9*11 + 4));
		slider.setOrientation(SwingConstants.VERTICAL);
		imageHolder.add(slider);
		
		image = new ImagePanel("movie-images/terminator.jpg");
		image.setBackground(theme.cardBG);
		image.setPreferredSize(new Dimension(16*11, 9*11));
		imageHolder.add(image);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				image.setImagePosition(slider.getValue());
				movie.setImagePosition(slider.getValue());
			}
		});
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					movie.setImagePosition(50);
					movie.setImagePath(null);
					image.resetImage();
					slider.setValue(50);
					return;
				}
 				
				JFileChooser j = new JFileChooser();
				j.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith(".jpg");
					}

					@Override
					public String getDescription() {
						return "Images Only";
					}
				});
				j.setAcceptAllFileFilterUsed(false);
	            int r = j.showOpenDialog(null);

	            // if the user selects a file
	            if (r != JFileChooser.APPROVE_OPTION) return;
	            String filePath = "movie-Images\\"+movie.getName()+".jpg";
	            File newFile = new File(filePath);
	            for (int i = 2; newFile.exists(); i++) {
	            	filePath = "movie-Images\\"+movie.getName()+i+".jpg";
	            	newFile = new File(filePath);
	            }
	            
	            try {
					Files.copy(j.getSelectedFile().toPath(), newFile.toPath());
					movie.setImagePosition(50);
					movie.setImagePath(filePath);
					slider.setValue(50);
					image.setImagePosition(50);
					image.setImage(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		return imageHolder;
	}

	JPanel createFavButton() {
		RoundedPanel favContainer = new RoundedPanel(8);
		favContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) favContainer.getLayout();
		flowLayout.setHgap(4);
		flowLayout.setVgap(4);
		favContainer.setMaximumSize(new Dimension(23, 23));
		favContainer.setBackground(theme.cardBG);
	    
		favBtn = new ImagePanel(theme.favoriteOff);
		favBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		favBtn.setPreferredSize(new Dimension(15, 15));

		favBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				movie.setFavorite(!movie.isFavorite());
				if (movie.isFavorite()) {
					favBtn.setImage(theme.favoriteOn);
				} else favBtn.setImage(theme.favoriteOff);
			}
		});
		favContainer.add(favBtn);
		
		return favContainer;
	}

	JPanel createStarSelector() {
		RoundedPanel rating = new RoundedPanel(8);
		rating.setAlignmentX(Component.RIGHT_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) rating.getLayout();
		flowLayout.setHgap(4);
		flowLayout.setVgap(4);
		rating.setMaximumSize(new Dimension(118, 23));
//		rating.setOpaque(false);
		rating.setBackground(theme.cardBG);
//		add(rating);
	    
		stars = new ImagePanel[6];
		for (int i = 0; i < stars.length; i++) {
			ImagePanel star = new ImagePanel(theme.starEmpty);
			star.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			star.setPreferredSize(new Dimension(15, 15));
			star.setImage(theme.starEmpty);
			
			int num = i+1;

			star.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						updateStarSelector(0);
						movie.setRate(0);
						return;
					}
					
					updateStarSelector(num);
					movie.setRate(num);
				}
			});
			
			stars[i] = star;
			rating.add(star);
		}
		
		return rating;
	}

	void updateStarSelector(int starsSelected) {
		for (int i = 0; i < stars.length; i++) {
			ImagePanel star = stars[i];
			if (i >= starsSelected) {
				star.setImage(theme.starEmpty);
			} else if (i == 5) {
				star.setImage(theme.starSuper);
			} else star.setImage(theme.star);
		}
	}
	
	
	Box createInfoArea() {
		Box infoArea = Box.createVerticalBox();
		infoArea.setOpaque(false);
		
		durationField = new JTextField();
		textFieldStyle(durationField);
		durationField.setHorizontalAlignment(SwingConstants.CENTER);
		durationField.setText("2h 0m");
		infoArea.add(durationField);
		
		Component verticalStrut = Box.createVerticalStrut(8);
		infoArea.add(verticalStrut);
		
		yearField = new JTextField();
		textFieldStyle(yearField);
		yearField.setHorizontalAlignment(SwingConstants.CENTER);
		yearField.setText("2000");
		infoArea.add(yearField);
		

		durationField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDuration = durationField.getText();
				if (newDuration == null) return;

				movie.setDuration(newDuration);
				durationField.setText(movie.getDuration());
			}
		});
		yearField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newYear = yearField.getText();
				if (newYear == null) return;
				
				int year = 0;
				try {
					year = Integer.parseInt(newYear);
				} catch (Exception ex) {}
				movie.setYear(year);
				yearField.setText(year+"");
			}
		});
		
		return infoArea;
	}
	
	Box createDateArea() {
		Box dateArea = Box.createVerticalBox();
		dateArea.setOpaque(false);
		
		txtFirstWatch = new JTextField();
		textFieldStyle(txtFirstWatch);
		txtFirstWatch.setHorizontalAlignment(SwingConstants.CENTER);
		txtFirstWatch.setText("First Watch");
		dateArea.add(txtFirstWatch);
		
		Component verticalStrut = Box.createVerticalStrut(8);
		dateArea.add(verticalStrut);
		
		txtLastWatch = new JTextField();
		textFieldStyle(txtLastWatch);
		txtLastWatch.setHorizontalAlignment(SwingConstants.CENTER);
		txtLastWatch.setText("Last Watch");
		dateArea.add(txtLastWatch);
		
		return dateArea;
	}
	
	void textFieldStyle(JTextField txtField) {
		txtField.setCaretColor(Color.WHITE);
		txtField.setMaximumSize(new Dimension(9999, 18));
		txtField.setOpaque(false);
		txtField.setForeground(theme.applicationFG);
		txtField.setBorder(null);
		txtField.setPreferredSize(new Dimension(16*11, 18));
		txtField.setFont(new Font("Arial", Font.BOLD, 16));
		txtField.setColumns(10);
	}
	
}
