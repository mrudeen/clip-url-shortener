package clip.listener;

import clip.exception.ClipUrlShortenerException;
import clip.output.Output;
import clip.shortener.UrlShortener;


public class ConsoleListener implements Listener {

	private Output output;
	private UrlShortener urlShortener;
	
	public ConsoleListener(Output output, UrlShortener urlShortener) {
		this.output = output;
		this.urlShortener = urlShortener;
	}
	
	public void start() {
		while (true) {
			String requestUrl = System.console().readLine();
			try {
				String responseUrl = urlShortener.processRequest(requestUrl);
				output.sendResponse(responseUrl, null);
			} catch (ClipUrlShortenerException e) {
				output.error(e);
			}
		}
	}
}
