package pk.na.dailybingwallpaperbyna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class DownloadFile {
	private boolean done = false;
	public DownloadFile(String uRLToDownload, String filename) {
		URLToDownload = uRLToDownload;
		pathToDownloadedFile = Environment.getExternalStorageDirectory()
				.getPath() + "/Bing Wallpapers by NA/" + filename;
	}

	private boolean setupDirectory() {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Bing Wallpapers by NA");
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		return success;
	}

	public String doDownload() {
		if (setupDirectory()) {
			download = new Download();
			download.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					URLToDownload);
			while (!done);
			return pathToDownloadedFile;

		} else {
			return HttpURLConnection.HTTP_UNAVAILABLE + "";
		}
	}

	private String URLToDownload;
	private String pathToDownloadedFile;
	private Download download;

	private class Download extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... sUrl) {
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				URL url = new URL(sUrl[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP "
							+ connection.getResponseCode() + " "
							+ connection.getResponseMessage();
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();
				output = new FileOutputStream(pathToDownloadedFile);

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					output.write(data, 0, count);
					Log.i("Downloading", total + "");
				}
				Log.i("Downloading", "Downloading complete. File saved to: "
						+ pathToDownloadedFile);
				
			} catch (Exception e) {
				return e.toString();
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
					ignored.printStackTrace();
				}

				if (connection != null)
					connection.disconnect();
			}
			done = true;
			return "Downloading complete. File saved to: "
					+ pathToDownloadedFile;
		}
	}

	@Override
	public String toString() {
		return "DownloadFile [URLToDownload=" + URLToDownload
				+ ", pathToDownloadedFile=" + pathToDownloadedFile + "]";
	}
}