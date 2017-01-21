package org.topicquests.support;

import org.topicquests.support.api.IResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpClient {
	public SimpleHttpClient() {
	}

	public IResult put(String url, String queryString) {
		return this.handle(url, queryString, "POST");
	}

	public IResult get(String url, String queryString) {
		return this.handle(url, queryString, "GET");
	}

	private IResult handle(String url, String queryString, String mode) {
		IResult result = new ResultPojo();
		BufferedReader rd = null;
		HttpURLConnection con = null;

		try {
			URL urx = new URL(url + queryString);
			con = (HttpURLConnection) urx.openConnection();
			con.setReadTimeout(1000);
			con.setRequestMethod(mode);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder buf = new StringBuilder();

			String line;
			while ((line = rd.readLine()) != null) {
				buf.append(line + '\n');
			}

			result.setResultObject(buf.toString());
		} catch (Exception var18) {
			var18.printStackTrace();
			result.addErrorString(var18.getMessage());
		} finally {
			try {
				if (rd != null) {
					rd.close();
				}

				if (con != null) {
					con.disconnect();
				}
			} catch (Exception var17) {
				var17.printStackTrace();
				result.addErrorString(var17.getMessage());
			}

		}

		return result;
	}
}
