package clip.exception;

public class ShortUrlLimitReachedException extends ClipUrlShortenerException {

	private static final long serialVersionUID = 1L;
	
	private int maxLength;
	
	public ShortUrlLimitReachedException(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getMessage() {
		return "Cannot generate new short URLs because the URL length limit (" + maxLength + ") has been reached and there are no more available short URLs.";
	}

}
