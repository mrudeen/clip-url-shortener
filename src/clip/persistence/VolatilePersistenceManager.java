package clip.persistence;

import java.util.TreeMap;

import clip.exception.ShortUrlLimitReachedException;
import clip.shortener.ShortUrl;

/**
 * While not technically a persistence manager because this class does not implement
 * a way to save data to a storage device, VolatilePersistenceManager implements
 * the save and accessor methods of the PersistenceManager interface
 * and provides a method to be overridden by inheriting managers to save to disk.
 * 
 * It also provides a simple implementation of PersistenceManager for use in testing.
 */
public class VolatilePersistenceManager implements PersistenceManager {
	
	private TreeMap<ShortUrl, String> urlMap;
	
	public VolatilePersistenceManager() {
		urlMap = new TreeMap<ShortUrl, String>();
	}
	
	@Override
	public String getLongUrl(ShortUrl shortUrl) {
		return urlMap.get(shortUrl);
	}

	// TODO Better reverse lookup algorithm
	// O(n log(n)) is passable; is there a way to reduce to O(log(n)) or at least O(n)?
	@Override
	public ShortUrl getShortUrl(String longUrl) {
		
		for (ShortUrl shortUrl : urlMap.keySet()) {
			String url = urlMap.get(shortUrl);
			if (url.equals(longUrl)) {
				return shortUrl;
			}
		}
		
		return null;
	}
	
	// Synchronized because we want to ensure that:
	//  A) the same long URL is not being written twice
	//  B) two different long URLs aren't given the same short URL
	@Override
	public synchronized ShortUrl saveUrl(String longUrl)
			throws ShortUrlLimitReachedException {
		
		if (urlMap.containsValue(longUrl)) {
			return getShortUrl(longUrl);
		}
		ShortUrl shortUrl;
		if (urlMap.isEmpty()) {
			shortUrl = ShortUrl.FIRST;
		} else {
			shortUrl = urlMap.lastKey().increment();
		}
		// Write to disk before memory in case the application crashes
		writeToDisk(shortUrl, longUrl);
		urlMap.put(shortUrl, longUrl);
		
		return shortUrl;
	}
	
	@Override
	public synchronized boolean insertShortUrl(ShortUrl shortUrl, String longUrl) {
		if (urlMap.containsKey(shortUrl) || urlMap.containsValue(longUrl)) {
			return false;
		}
		
		writeToDisk(shortUrl, longUrl);
		urlMap.put(shortUrl, longUrl);
		
		return true;
	}
	
	// Method to be overridden by inheriting classes
	protected void writeToDisk(ShortUrl shortUrl, String longUrl) {}

}
