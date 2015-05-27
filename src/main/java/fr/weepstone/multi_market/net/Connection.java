package fr.weepstone.multi_market.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

@Service
public class Connection {

	private CloseableHttpClient newHttpClient(){
		return HttpClients.custom().setProxy(new HttpHost("proxy.intranet-adsn.fr", 8080)).build();
	}

	public List<String> connection(String url, HashMap<String, String> params, List<String> cookies) throws NoSuchAlgorithmException, KeyManagementException, IOException {

		// Example send http request
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		for(String param : params.keySet()){
			paramList.add(new BasicNameValuePair(param, params.get(param)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(paramList));

		final CloseableHttpClient httpClient = newHttpClient();

		HttpResponse response = httpClient.execute(httpPost);

		List<String> cookiesValue = new ArrayList<String>();

		for (Header header : response.getAllHeaders()) {
			if (header.getName().equals("Set-Cookie")) {
				String value = header.getValue();
				for (String cookie : cookies) {
					if (value.startsWith(cookie)) {
						cookiesValue.add(value.substring(0, value.indexOf(";")));
					}
				}
			}
		}
		httpClient.close();

		return cookiesValue;
	}

	public String getPageHtml(String url, List<String> cookies) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		for (String cookie : cookies) {
			httpGet.addHeader("Cookie", cookie);
		}

		final CloseableHttpClient httpClient = newHttpClient();
		HttpResponse response2 = httpClient.execute(httpGet);

		BufferedReader rd2 = new BufferedReader(
				new InputStreamReader(response2.getEntity().getContent()));

		StringBuffer result2 = new StringBuffer();
		String line2 = "";
		while ((line2 = rd2.readLine()) != null) {
			result2.append(line2);
		}
		httpClient.close();
		return result2.toString();
	}
}
