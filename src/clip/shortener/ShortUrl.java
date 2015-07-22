package clip.shortener;

import java.util.Arrays;
import java.util.List;

import clip.exception.ShortUrlLimitReachedException;

public class ShortUrl implements Comparable<ShortUrl> {
	
	public static final ShortUrl FIRST = new ShortUrl("0");

	private final int MAX_LENGTH = 10;

	private final List<Character> CHARACTERS = Arrays.asList(
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	
	private String value;
	
	public ShortUrl(String value) {		
		this.value = value;
	}
	
	public ShortUrl increment()
			throws ShortUrlLimitReachedException {
		
		String nextValue = null;
		String trailingZeroes = "";
		
		// Read the current value backward character by character
		for (int i = value.length() - 1; i >= 0; i--) {
			char currentCharacter = value.charAt(i);
			
			// Find the list index of the character
			int index = CHARACTERS.indexOf(currentCharacter);
			
			// If the character is anything but 'z' (the last value), we increment the current character and append the trailing zeroes
			if (index < CHARACTERS.size() - 1) {
				// The prefix preceding the current character is kept as is
				String remainingValue = i > 0 ? value.substring(0, i) : "";
				
				nextValue = remainingValue + CHARACTERS.get(index + 1) + trailingZeroes;
				break;
			}
			
			// Each trailing 'z' character is replaced by a '0'
			trailingZeroes = trailingZeroes + '0';
			
			// Every character is 'z', we must add another '0' to extend the length of the URL
			if (i == 0) {
				nextValue = trailingZeroes + '0';
			}
		}
		
		if (nextValue.length() > MAX_LENGTH) {
			throw new ShortUrlLimitReachedException(MAX_LENGTH);
		}
		
		return new ShortUrl(nextValue);
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	// Comparison methods are necessary for the use of this object as a key in a TreeMap (equals is necessary in all Map implementations)
	
	@Override
	public boolean equals(Object shortUrl) {
		return value.equals(shortUrl.toString());
	}

	@Override
	public int compareTo(ShortUrl shortUrl) {
		int comparison = value.length() - shortUrl.toString().length();
		
		if (comparison == 0) {
			comparison = value.compareTo(shortUrl.toString());
		}
		
		return comparison;
	}
}
