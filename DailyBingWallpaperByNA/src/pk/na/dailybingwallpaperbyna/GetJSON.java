package pk.na.dailybingwallpaperbyna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import pk.na.dailybingwallpaperbyna.DTO.Image;
import pk.na.dailybingwallpaperbyna.util.ProcessJSON;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class GetJSON {

	Image images=null;
	private GetWallpaperJSON getWallpaperJSON;
	private String sourceURL;

	public GetJSON(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public Image doGetJSON() throws InterruptedException {
		getWallpaperJSON = new GetWallpaperJSON();
		getWallpaperJSON.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				sourceURL);
		while (images ==null);
		return images;
	}

	private class GetWallpaperJSON extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			try {
				Looper.prepare();
				String result = connect(arg0[0]);
				Log.i("The JSON", result);
				images = ProcessJSON.ConvertJSONToWallpaper(result);
				Log.i("The JSON", images.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static String connect(String url) {

		HttpClient httpclient = new DefaultHttpClient();
		String result = null;
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("Praeda", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				// now you have the string representation of the HTML
				// request
				Log.i("result from json is", result);
				instream.close();
			} else {
				Log.i("Returned --", "null");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public Image getImages() {
		return images;
	}
}