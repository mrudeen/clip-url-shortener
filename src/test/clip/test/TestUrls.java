package clip.test;

public enum TestUrls {
	TEST1("test1.com", "http://cl.ip/0", null),
	TEST2("test2.co.uk", "http://cl.ip/1", null),
	TEST3("www.test3.jp", "http://cl.ip/2", "8"),
	TEST4("www13.test4.mobi", "http://cl.ip/9", null),
	TEST5("http://test5.info", "http://cl.ip/A", null),
	TEST6("https://www.test6.ca", "http://cl.ip/B", "Y"),
	TEST7("irc://test7.net", "http://cl.ip/Z", null),
	TEST8("ftp://test8.cn/stuff", "http://cl.ip/a", null),
	TEST9("http://test9.com/stuff.php", "http://cl.ip/b", "y"),
	TEST10("http://www3.test10.co.uk/", "http://cl.ip/z", null),
	TEST11("http://www.test11.nl/stuff/morestuff/", "http://cl.ip/00", "Zzzy"),
	TEST12("test12.ca/file.extension", "http://cl.ip/Zzzz", null),
	TEST13("test13.com/", "http://cl.ip/a000", "zzzzzzzzzy"),
	TEST14("test14.fr", "http://cl.ip/zzzzzzzzzz", null);

	private String longUrl;
	private String shortUrl;
	private String keyBeforeNextTest;
	
	private TestUrls(String longUrl, String shortUrl, String keyBeforeNextTest) {
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
		this.keyBeforeNextTest = keyBeforeNextTest;
	}
	
	public String getLongUrl() {
		return longUrl;
	}
	
	public String getShortUrl() {
		return shortUrl;
	}
	
	public String getKeyBeforeNextTest() {
		return keyBeforeNextTest;
	}
}
