package clip.test;

import clip.listener.Listener;
import clip.output.ConsoleOutput;
import clip.output.Output;
import clip.persistence.VolatilePersistenceManager;
import clip.shortener.UrlShortener;

public class ApplicationTest {

	public static void main(String[] args) {

		VolatilePersistenceManager mgr = new VolatilePersistenceManager();
		UrlShortener urlShortener = new UrlShortener(mgr);
		Output output = new ConsoleOutput();
		Listener listener = new TestListener(output, urlShortener, mgr);
		listener.start();
	}

}
