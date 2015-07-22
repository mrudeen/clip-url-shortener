package clip.application;

import java.io.IOException;

import clip.listener.ConsoleListener;
import clip.listener.Listener;
import clip.output.ConsoleOutput;
import clip.output.Output;
import clip.persistence.FilePersistenceManager;
import clip.shortener.UrlShortener;

public class Application {

	public static void main(String[] args) {

		Output output = new ConsoleOutput();

		UrlShortener urlShortener = null;
		try { 
			urlShortener = new UrlShortener(new FilePersistenceManager(args[0]));
		} catch (IOException e) {
			output.error(e);
			System.exit(1);
		}
		
		Listener listener = new ConsoleListener(output, urlShortener);
		listener.start();
	}

}
