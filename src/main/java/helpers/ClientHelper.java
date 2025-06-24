package helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class ClientHelper {
	
	private static HttpClient client = HttpClient.newHttpClient();
	
	public static HttpResponse<String> GET(String url) throws IOException, InterruptedException  {
		    HttpRequest request = HttpRequest.newBuilder()
		    	      .uri(URI.create(url))
		    	      .GET()
		    	      .build();
		    
		    HttpResponse<String> apiResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		    return apiResponse;
	}
}