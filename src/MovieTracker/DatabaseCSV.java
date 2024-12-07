package MovieTracker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import MovieCard.MovieInfo;

public class DatabaseCSV implements Database {
	private File dataFile;
	private ArrayList<MovieInfo> movies = new ArrayList<MovieInfo>();
	
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
		
		if (fileReader.hasNextLine()) fileReader.nextLine(); // skip header
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			if (line == "") continue;
			String[] cells = parseCSVLine(line);

			if (cells.length != 12) continue;
			String name = cells[1];
			MovieInfo movie;
			if (cells[4] != "") {
				int year = Integer.parseInt(cells[4]);
				int totalMins = MovieInfo.durationToMins(cells[3]);
				movie = new MovieInfo(name, totalMins, year);
			} else {
				movie = new MovieInfo(name);
			}
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

	public ArrayList<MovieInfo> getMovies() {
		return movies;
	}
	
}
