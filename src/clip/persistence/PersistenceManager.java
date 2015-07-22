package clip.persistence;

import clip.exception.ShortUrlLimitReachedException;
import clip.shortener.ShortUrl;

public interface PersistenceManager {
	
	/**
	 * Attributes the next available short URL to the provided long URL and saves the key-value pair.
	 * 
	 * A long URL that has already been saved should simply return its existing short URL.
	 * 
	 * @param longUrl The long URL to save
	 * @return The shortened URL
	 */
	public ShortUrl saveUrl(String longUrl) throws ShortUrlLimitReachedException;
	
	public String getLongUrl(ShortUrl shortUrl);
	
	public ShortUrl getShortUrl(String longUrl);
	
	/**
	 *  Utility method for manually inserting values.
	 *  
	 * @param shortUrl
	 * @param longUrl
	 * @return true if the insertion succeeded, false otherwise
	 */
	public boolean insertShortUrl(ShortUrl shortUrl, String longUrl);
}
