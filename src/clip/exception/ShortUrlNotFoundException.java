package clip.exception;

public class ShortUrlNotFoundException extends ClipUrlShortenerException {

	private static final long serialVersionUID = 1L;
	
	private String url;
	
	public ShortUrlNotFoundException(String url) {
		this.url = url;
	}
	
	@Override
	public String getMessage() {
		return "The requested short URL \"" + url + "\" could not be found.";
	}
}
