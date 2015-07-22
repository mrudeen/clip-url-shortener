package clip.test;

import clip.exception.ClipUrlShortenerException;
import clip.listener.Listener;
import clip.output.Output;
import clip.persistence.VolatilePersistenceManager;
import clip.shortener.ShortUrl;
import clip.shortener.UrlShortener;

public class TestListener implements Listener {

	private Output output;
	private UrlShortener urlShortener;
	private VolatilePersistenceManager mgr;
	
	// TODO Remove the hack for inserting arbitrary short URLs as well as the dependency on VolatilePersistenceManager
	public TestListener(Output output, UrlShortener urlShortener, VolatilePersistenceManager mgr) {
		this.output = output;
		this.urlShortener = urlShortener;
		this.mgr = mgr;
	}
	
	@Override
	public void start() {
		for (TestUrls test : TestUrls.values()) {
			try {
				String response = urlShortener.processRequest(test.getShortUrl());
				output.sendResponse("ERROR: Got unexpected response for " + test.getShortUrl() + " - " + response, null);
			} catch (ClipUrlShortenerException e) {
				output.sendResponse("OK: Got expected error for " + test.getShortUrl(), null);
				output.error(e);
			}
			
			try {
				String response = urlShortener.processRequest(test.getLongUrl());
				if (response.equals(test.getShortUrl())) {
					output.sendResponse("OK: Got expected response for " + test.getLongUrl() + " - " + response, null);
				} else {
					output.sendResponse("ERROR: Got unexpected response for " + test.getLongUrl() + " - expected: " + test.getShortUrl() + " but got: " + response, null);
				}
			} catch (ClipUrlShortenerException e) {
				output.sendResponse("ERROR: Got unexpected error for " + test.getLongUrl(), null);
				output.error(e);
			}
			
			try {
				String response = urlShortener.processRequest(test.getShortUrl());
				if (response.equals(test.getLongUrl())) {
					output.sendResponse("OK: Got expected response for " + test.getShortUrl() + " - " + response, null);
				} else {
					output.sendResponse("ERROR: Got unexpected response for " + test.getShortUrl() + " - expected: " + test.getLongUrl() + " but got: " + response, null);
				}
			} catch (ClipUrlShortenerException e) {
				output.sendResponse("ERROR: Got unexpected error for " + test.getShortUrl(), null);
				output.error(e);
			}
			
			try {
				String response = urlShortener.processRequest(test.getLongUrl());
				if (response.equals(test.getShortUrl())) {
					output.sendResponse("OK: Duplicate: Got expected response for " + test.getLongUrl() + " - " + response, null);
				} else {
					output.sendResponse("ERROR: Duplicate: Got unexpected response for " + test.getLongUrl() + " - expected: " + test.getShortUrl() + " but got: " + response, null);
				}
			} catch (ClipUrlShortenerException e) {
				output.sendResponse("ERROR: Duplicate: Got unexpected error for " + test.getLongUrl(), null);
				output.error(e);
			}
			
			if (test.getKeyBeforeNextTest() != null) {
				hackKey(new ShortUrl(test.getKeyBeforeNextTest()), test.getLongUrl() + "/fake");
			}
		}
		
		try {
			String response = urlShortener.processRequest("toomanyurls.com");
			output.sendResponse("ERROR: Got unexpected response for too many URLs - " + response, null);
		} catch (ClipUrlShortenerException e) {
			output.sendResponse("OK: Got expected error for too many URLs", null);
			output.error(e);
		}
	}
	
	private void hackKey(ShortUrl shortUrl, String longUrl) {
		mgr.insertShortUrl(shortUrl, longUrl);
	}

}
