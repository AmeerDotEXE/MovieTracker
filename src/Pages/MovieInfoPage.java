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
import Components.TextField;
import MovieTracker.Language;
import MovieTracker.MovieManager;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

public class MovieInfoPage extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Theme theme; // for easy access
	private MovieCard movieCard;
	private MovieInfo movie;
	private JTextField titleField;
	private TextField durationField;
	private TextField yearField;
	private TextField firstWatch;
	private TextField lastWatch;
	private JPanel panelGenre;
	private JPanel panelCast;
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
		
		Box metadata = createMetadataArea();
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
		
		Box row3 = createTagsRow();
		add(row3);
		
		Component verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		Box navPanel = createNavigaionButtons();
		add(navPanel);
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
		if (movie.getDurationMins() == 0) {					
			durationField.setText("");
		} else durationField.setText(movie.getDuration());
		if (movie.getYear() == 0) {					
			yearField.setText("");
		} else yearField.setText(movie.getYear()+"");
		image.setImage(movie.getImagePath());
		slider.setValue(movie.getImagePosition());
		
		while (panelGenre.getComponentCount() > 1) {
			panelGenre.remove(1);
		}
		while (panelCast.getComponentCount() > 1) {
			panelCast.remove(1);
		}
		for (String tagTxt : movie.getGenre()) {
			createTag(tagTxt, true);
		}
		for (String tagTxt : movie.getCast()) {
			createTag(tagTxt, false);
		}


		SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM d, yyyy");
		if (movie.getFirstWatched() != null) {
			try {
				Date date = targetFormat.parse(movie.getFirstWatched());
				String originalDate = originalFormat.format(date);
				firstWatch.setText(originalDate);
			} catch (ParseException e1) {}
		} else firstWatch.setText("");
		if (movie.getLastWatched() != null) {
			try {
				Date date = targetFormat.parse(movie.getLastWatched());
				String originalDate = originalFormat.format(date);
				lastWatch.setText(originalDate);
			} catch (ParseException e1) {}
		} else lastWatch.setText("");
	}
	
	
	
 	Box createMetadataArea() {
//		JPanel metadata = new JPanel();
		Box metadata = Box.createVerticalBox();
		metadata.setBorder(new EmptyBorder(8, 0, 0, 0));
		metadata.setPreferredSize(new Dimension(200, 99));
//		metadata.setOpaque(false);
		// row1.add(metadata);
//		metadata.setLayout(new BoxLayout(metadata, BoxLayout.Y_AXIS));
		
		titleField = new JTextField();
		textFieldStyle(titleField);
		titleField.setText("No Name");
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
		scoreStuff.setMinimumSize(new Dimension(300, 0));
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
	            String oldFilePath = movie.getImagePath();
	            String filePath = "movie-images/"+movie.getName()+".jpg";
	            File newFile = new File(filePath);
	            for (int i = 2; newFile.exists(); i++) {
	            	filePath = "movie-images/"+movie.getName()+i+".jpg";
	            	newFile = new File(filePath);
	            }
	            
	            try {
					Files.copy(j.getSelectedFile().toPath(), newFile.toPath());
					movie.setImagePosition(50);
					movie.setImagePath(filePath);
					slider.setValue(50);
					image.setImagePosition(50);
					image.setImage(filePath);
					
					if (oldFilePath == null || oldFilePath == "") return;
					File oldImage = new File(oldFilePath);
					oldImage.delete();
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
		
		durationField = new TextField();
		durationField.setPlaceHolder(Language.inputDuration);
		durationField.setPlaceHolderColor(theme.applicationSecondaryFG);
		durationField.setFont(new Font("Arial", Font.BOLD, 16));
		durationField.setText("");
		infoArea.add(durationField);
		
		Component verticalStrut = Box.createVerticalStrut(8);
		infoArea.add(verticalStrut);
		
		yearField = new TextField();
		yearField.setPlaceHolder(Language.inputYear);
		yearField.setPlaceHolderColor(theme.applicationSecondaryFG);
		yearField.setFont(new Font("Arial", Font.BOLD, 16));
		yearField.setText("");
		infoArea.add(yearField);
		

		durationField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDuration = durationField.getText();
				if (newDuration == null) return;

				movie.setDuration(newDuration);
				if (movie.getDurationMins() == 0) {					
					durationField.setText("");
				} else durationField.setText(movie.getDuration());
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
				if (movie.getYear() == 0) {					
					yearField.setText("");
				} else yearField.setText(year+"");
			}
		});
		
		return infoArea;
	}
	
	Box createDateArea() {
		Box dateArea = Box.createVerticalBox();
		dateArea.setOpaque(false);
		
		firstWatch = new TextField();
		firstWatch.setPlaceHolder(Language.inputFirstWatchDate);
		firstWatch.setPlaceHolderColor(theme.applicationSecondaryFG);
		firstWatch.setFont(new Font("Arial", Font.BOLD, 16));
		firstWatch.setText("");
		dateArea.add(firstWatch);
		
		Component verticalStrut = Box.createVerticalStrut(8);
		dateArea.add(verticalStrut);
		
		lastWatch = new TextField();
		lastWatch.setPlaceHolder(Language.inputLastWatchDate);
		lastWatch.setPlaceHolderColor(theme.applicationSecondaryFG);
		lastWatch.setFont(new Font("Arial", Font.BOLD, 16));
		lastWatch.setText("");
		dateArea.add(lastWatch);
		

		firstWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDateTxt = firstWatch.getText();
				if (newDateTxt == null) return;
				
				if (newDateTxt.equals("")) {
					movie.setFirstWatched(null);
					firstWatch.setText("");
					return;
				}

				SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
				SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM d, yyyy");
				Date date;
				try {
					date = originalFormat.parse(newDateTxt);
				} catch (ParseException e1) {
					date = new Date();
				}
				String formattedDate = targetFormat.format(date);
				String originalDate = originalFormat.format(date);
				movie.setFirstWatched(formattedDate);
				firstWatch.setText(originalDate);
			}
		});
		lastWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDateTxt = lastWatch.getText();
				if (newDateTxt == null) return;
				
				if (newDateTxt.equals("")) {
					movie.setLastWatched(null);
					lastWatch.setText("");
					return;
				}

				SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
				SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM d, yyyy");
				Date date;
				try {
					date = originalFormat.parse(newDateTxt);
				} catch (ParseException e1) {
					date = new Date();
				}
				String formattedDate = targetFormat.format(date);
				String originalDate = originalFormat.format(date);
				movie.setLastWatched(formattedDate);
				lastWatch.setText(originalDate);
			}
		});
		
		return dateArea;
	}
	
	
	Box createTagsRow() {
		Box row3 = Box.createVerticalBox();
		row3.setBorder(new EmptyBorder(8, 8, 0, 4));
//		row3.setPreferredSize(new Dimension(999, 2));
		
		panelGenre = new JPanel();
		panelGenre.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelGenre.setMinimumSize(new Dimension(10, 25));
		panelGenre.setMaximumSize(new Dimension(9999, 50));
		panelGenre.setOpaque(false);
		row3.add(panelGenre);
		
		TextField txtGenre = new TextField();
		txtGenre.setPlaceHolder(Language.inputGenre);
		txtGenre.setPlaceHolderColor(theme.applicationSecondaryFG);
		txtGenre.setHorizontalAlignment(SwingConstants.LEFT);
		txtGenre.setFont(new Font("Arial", Font.BOLD, 16));
		txtGenre.setColumns(5);
		txtGenre.setText("");
		panelGenre.add(txtGenre);
		

		panelCast = new JPanel();
		panelCast.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelCast.setMinimumSize(new Dimension(10, 25));
		panelCast.setMaximumSize(new Dimension(9999, 50));
//		panelCast.setPreferredSize(new Dimension(10, 50));
		panelCast.setOpaque(false);
		row3.add(panelCast);
		
		TextField txtCast = new TextField();
		txtCast.setPlaceHolder(Language.inputCast);
		txtCast.setPlaceHolderColor(theme.applicationSecondaryFG);
		txtCast.setHorizontalAlignment(SwingConstants.LEFT);
		txtCast.setFont(new Font("Arial", Font.BOLD, 16));
		txtCast.setColumns(5);
		txtCast.setText("");
		panelCast.add(txtCast);

		txtGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagTxt = txtGenre.getText();
				if (tagTxt == null || tagTxt.equals("")) return;
				if (movie.getGenre().size() >= 4) return;
				
				movie.getGenre().add(tagTxt);
				createTag(tagTxt, true);
				txtGenre.setText("");
				txtGenre.requestFocus();
			}
		});
		txtCast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagTxt = txtCast.getText();
				if (tagTxt == null || tagTxt.equals("")) return;
				if (movie.getCast().size() >= 6) return;
				
				movie.getCast().add(tagTxt);
				createTag(tagTxt, false);
				txtCast.setText("");
				txtCast.requestFocus();
			}
		});
		
		return row3;
	}
	
	void createTag(String text, boolean isGenre) {
		RoundedPanel tagContainer = new RoundedPanel(8);
		tagContainer.setBackground(theme.buttonBG);
		FlowLayout flowLayout_1 = (FlowLayout) tagContainer.getLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		
		JLabel tagText = new JLabel(text);
		tagText.setBorder(new EmptyBorder(0, 2, 0, 2));
		tagText.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tagText.setForeground(theme.buttonFG);
		tagContainer.add(tagText);
		
		tagContainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON3) return;

				if (isGenre) {
					panelGenre.remove(tagContainer);
					movie.getGenre().remove(text);
					panelGenre.revalidate();
				} else {
					panelCast.remove(tagContainer);
					movie.getCast().remove(text);
					panelCast.revalidate();
				}
			}
		});
		
		if (isGenre) {
			panelGenre.add(tagContainer);
			panelGenre.revalidate();
		} else {
			panelCast.add(tagContainer);
			panelCast.revalidate();
		}
	}
	
	
	Box createNavigaionButtons() {
		Box navPanel = Box.createHorizontalBox();
		navPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton deleteButton = new JButton(Language.buttonDelete);
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setBackground(theme.buttonDangerHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				deleteButton.setBackground(theme.buttonDangerBG);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				deleteButton.setBackground(theme.buttonDangerPress);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				deleteButton.setBackground(theme.buttonDangerBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				MovieManager.getInstance().removeMovie(movieCard);
				MovieManager.getInstance().saveMovies();
				Mainframe.showMoviePage();
			}
		});
		deleteButton.setFocusable(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setBackground(theme.buttonDangerBG);
//		deleteButton.setForeground(theme.buttonFG);
		navPanel.add(deleteButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		navPanel.add(horizontalGlue);
		
		JButton doneButton = new JButton(Language.buttonDone);
		doneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				doneButton.setBackground(theme.buttonHover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				doneButton.setBackground(theme.buttonBG);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				doneButton.setBackground(theme.buttonPress);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				doneButton.setBackground(theme.buttonBG);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				MovieManager.getInstance().saveMovies();
				movieCard.updateMovieData();
				Mainframe.showMoviePage();
			}
		});
		doneButton.setFocusable(false);
		doneButton.setBorderPainted(false);
		doneButton.setBackground(theme.buttonBG);
		doneButton.setForeground(theme.buttonFG);
		navPanel.add(doneButton);
		
		return navPanel;
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
