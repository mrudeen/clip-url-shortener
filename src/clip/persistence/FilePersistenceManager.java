package clip.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import clip.shortener.ShortUrl;

public class FilePersistenceManager extends VolatilePersistenceManager {

	private final String URL_DELIMITER = " "; // Space is used for delimiter because URLs cannot contain spaces
	
	private PrintWriter printWriter;
	
	// TODO: Validate file, assuming valid file
	public FilePersistenceManager(String filename) throws IOException {
		super();

		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			
			String[] splitLine = line.split(URL_DELIMITER);
			insertShortUrl(new ShortUrl(splitLine[0]), splitLine[1]);
		}
		br.close();
		printWriter = new PrintWriter(new FileOutputStream(file, true), true); // Append mode and automatic line flushing (crash resilience)
	}
	
	@Override
	protected void writeToDisk(ShortUrl shortUrl, String longUrl) {
		
		// Guard clause to prevent trying to write to file when loading existing short URLs at startup
		if (printWriter == null) {
			return;
		}
		
		printWriter.println(shortUrl + URL_DELIMITER + longUrl);
	}

}
