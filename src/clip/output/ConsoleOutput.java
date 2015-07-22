package clip.output;

import java.util.Map;

public class ConsoleOutput implements Output {

	@Override
	public void sendResponse(String url, Map<String, Object> args) {
		System.out.println(url);
	}

	@Override
	public void error(Exception exception) {
		System.out.println(exception.getMessage());		
	}

}
