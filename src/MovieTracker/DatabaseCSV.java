package MovieTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import MovieCard.MovieInfo;
import MovieCard.MovieStatus;

public class DatabaseCSV implements Database {
	private File dataFile;
	private LinkedList<MovieInfo> movies = new LinkedList<MovieInfo>();
	
	DatabaseCSV() {
		File csvFile = new File("data.csv");
		if (!csvFile.exists()) {
			try {
				csvFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.dataFile = csvFile;
		loadMovies();
	}
	
	private void loadMovies() {
		if (dataFile == null) return;
		if (!dataFile.canRead()) return;
		if (!dataFile.exists()) return;
		
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(dataFile);
		} catch (IOException e) {}
		if (fileReader == null) return;
		movies.clear();
		
		if (fileReader.hasNextLine()) fileReader.nextLine(); // skip header
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			if (line == "") continue;
			String[] cells = parseCSVLine(line);

			if (cells.length != 12) {
				if (cells.length > 0)
					System.out.println("Corrupted line: "+cells[0]);
				continue;
			}
			String name = cells[1];
			MovieInfo movie = new MovieInfo(name);
			if (cells[0].equalsIgnoreCase("yes")) movie.setFavorite(true);
			if (cells[2] != "") movie.setStatus(MovieStatus.fromString(cells[2]));
			if (cells[3] != "") movie.setDuration(cells[3]);
			if (cells[4] != "") movie.setYear(Integer.parseInt(cells[4]));
			if (cells[5] != "") movie.setRate(cells[5].length());
			if (cells[6] != "") movie.setLastWatched(cells[6].substring(1, cells[6].length()-1));
			if (cells[7] != "") {
				if (!cells[7].startsWith("\"")) movie.getGenre().add(cells[7]);
				else {
					for (String tag : cells[7].substring(1, cells[7].length()-1).split(", ")) {
						movie.getGenre().add(tag);
					}
				}
			}
			if (cells[8] != "") {
				if (!cells[8].startsWith("\"")) movie.getCast().add(cells[8]);
				else {
					for (String tag : cells[8].substring(1, cells[8].length()-1).split(", ")) {
						movie.getCast().add(tag);
					}
				}
			}
			if (cells[9] != "") movie.setFirstWatched(cells[9].substring(1, cells[9].length()-1));
			if (cells[10] != "") movie.setImagePath("movie-images/"+cells[10]);
			if (cells[11] != "") movie.setImagePosition(Integer.parseInt(cells[11]));
			movies.add(movie);
		}
		
		fileReader.close();
	}
	
	private String[] parseCSVLine(String line) {
		LinkedList<String> cells = new LinkedList<String>();
		
		boolean isInString = false;
		String cell = "";

		for (int i = 0; i < line.length(); i++) {
			char character = line.charAt(i);
			if (isInString || character == '"') {
				if (character == '"') isInString = !isInString;
				cell += character;
				continue;
			}
			if (character == ',') {
				cells.add(cell);
				cell = "";
				continue;
			}
			cell += character;
		}
		cells.add(cell);
		
		return cells.toArray(String[]::new);
	}

	public LinkedList<MovieInfo> getMovies() {
		return movies;
	}
	

	public void addMovie(MovieInfo movie) {
		try {
			saveMovies(movies);
		} catch (IOException e) {
			System.out.println("Couldn't save in CSV format.");
			e.printStackTrace();
		}
	}
	public void updateMovie(MovieInfo movie) {
		try {
			saveMovies(movies);
		} catch (IOException e) {
			System.out.println("Couldn't save in CSV format.");
			e.printStackTrace();
		}
	}
	public void deleteMovie(MovieInfo movie) {
		try {
			saveMovies(movies);
		} catch (IOException e) {
			System.out.println("Couldn't save in CSV format.");
			e.printStackTrace();
		}
	}
	public void renameMovie(String oldName, MovieInfo movie) {
		try {
			saveMovies(movies);
		} catch (IOException e) {
			System.out.println("Couldn't save in CSV format.");
			e.printStackTrace();
		}
	}

	private void saveMovies(LinkedList<MovieInfo> movies) throws IOException {
		FileWriter fileWriter = new FileWriter(this.dataFile, false);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		

		// write header
		bufferedWriter.write("Favorite,Name,Movie Status,Run Time,Year,Rate,Movie Last Watched,Movie Genre,Movie Cast,Started Date,Image File,Image Position");
		
		for (MovieInfo movie : movies) {
			String[] cells = new String[12];
			
			cells[0] = movie.isFavorite() ? "Yes" : "No";
			cells[1] = movie.getName();
			cells[2] = movie.getStatus().toString();
			cells[3] = movie.getDuration();
			cells[4] = movie.getYear()+"";
			cells[5] = "⭐⭐⭐⭐⭐⭐".substring(0, movie.getRate());
			cells[6] = movie.getLastWatched();
			cells[7] = ""; // Genre
			cells[8] = ""; // Cast
			cells[9] = movie.getFirstWatched();
			cells[10] = movie.getImagePath();
			cells[11] = movie.getImagePosition()+"";

			if (movie.getDurationMins() == 0) cells[3] = "";
			
			if (cells[6] == null) cells[6] = "";
			else cells[6] = '"'+cells[6]+'"';

			if (movie.getGenre().size() > 0) {
				cells[7] = movie.getGenre().get(0);
				if (movie.getGenre().size() > 1) {
					cells[7] = '"'+String.join(", ", movie.getGenre())+'"';
				}
			}
			if (movie.getCast().size() > 0) {
				cells[8] = movie.getCast().get(0);
				if (movie.getCast().size() > 1) {
					cells[8] = '"'+String.join(", ", movie.getCast())+'"';
				}
			}

			if (cells[9] == null) cells[9] = "";
			else cells[9] = '"'+cells[9]+'"';
			
			if (cells[10] == null) cells[10] = "";
			else if (cells[10].startsWith("movie-images")) cells[10] = cells[10].substring(13);
			
			if (movie.getImagePosition() == 50) cells[11] = "";
			
			bufferedWriter.write("\n"+String.join(",", cells));
		}
		
				
		bufferedWriter.close();
		fileWriter.close();
	}
	
}
