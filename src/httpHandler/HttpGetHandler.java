package httpHandler;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class HttpGetHandler {


	public static HttpResponse getResponse(String url) {
		return getResponse(url,null);
	}

	public static HttpResponse getResponse(String url,
			String cookie) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		/*Use the following header to set some header spec*/
		//request.setHeader("accessKey","");
		if(cookie != null)
			request.setHeader("cookie", cookie);
		HttpResponse response =  null;		
		try {
			response = client.execute(request);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String getLastRedirectUrl(String url) {
		HttpGet httpget = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		AbstractHttpClient httpClient = new DefaultHttpClient();
		
		String currentUrl = null;
		try {
			HttpResponse response = httpClient.execute(httpget, context);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new IOException(response.getStatusLine().toString());
			HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost
					.toURI() + currentReq.getURI());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return currentUrl;
	}
}
