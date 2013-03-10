package pagescrapper;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import util.StringHandler;
import util.Utility;

import httpHandler.HttpGetHandler;

public class Scrapper {

	public static void main(String[] args) throws Exception {

		HttpResponse response = HttpGetHandler
				.getResponse("http://api.simplyhired.com/a/jobs-api/xml-v2/q-title%3A%28Data%20Scientist%29?pshid=48970&ssty=2&cflg=r&jbd=testsite1.jobamatic.com&clip=67.167.203.246");

		try {
			String content = Utility.convertInputStreamAsString(response.getEntity().getContent());
			List<String> hrefs = StringHandler.getStringBetweenPattern(content, "url=\"", "\"");
			for (String href : hrefs) {
				if (!href.isEmpty()) {
					if (!href.contains("http")) {
						href = "http://www.simplyhired.com/" + href;
					}
					String lastRedirectUrl = HttpGetHandler.getLastRedirectUrl(href);
					HttpResponse responseNew = HttpGetHandler.getResponse(lastRedirectUrl);

					long systime = System.currentTimeMillis();
					Utility.writeToFile(Utility.convertInputStreamAsString(responseNew.getEntity().getContent()),
							"jobposts/file-" + systime + ".html");
					
					Utility.appendToFile(systime+".html = "+href+"\n","jobposts/urlmap.txt");
				}
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
