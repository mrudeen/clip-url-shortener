package clip.shortener;

import clip.exception.ClipUrlShortenerException;
import clip.exception.ShortUrlNotFoundException;
import clip.persistence.PersistenceManager;

/*
 * TODO:
 * -Generate non-predictable URLs
 * -Re-use deleted URLs
 */

public class UrlShortener {
	
	private final String SHORTENED_URL_PREFIX = "http://cl.ip/";
	
	private PersistenceManager persistenceManager;
	
	public UrlShortener(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	public String processRequest(String url)
			throws ClipUrlShortenerException {
		
		// Retrieving a long URL from a short URL
		if (url.startsWith(SHORTENED_URL_PREFIX)) {
			ShortUrl shortUrl = new ShortUrl(url.substring(SHORTENED_URL_PREFIX.length()));
			
			String longUrl = persistenceManager.getLongUrl(shortUrl);
			if (longUrl == null) {
				throw new ShortUrlNotFoundException(url);
			}
			
			return longUrl;
		}
		
		// Requesting a short URL for a long URL
		return SHORTENED_URL_PREFIX + persistenceManager.saveUrl(url).toString();
	}
}
