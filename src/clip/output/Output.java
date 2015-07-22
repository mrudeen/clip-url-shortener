package clip.output;

import java.util.Map;

public interface Output {

	public void sendResponse(String url, Map<String, Object> args);
	
	public void error(Exception exception);
}
