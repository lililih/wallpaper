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
import android.os.Looper;
import android.util.Log;

public class DownloadFile {
	private boolean done = false;
	private final static String FOLDER_NAME = "/Bing Wallpapers by NA";

	public DownloadFile(String uRLToDownload, String filename) {
		URLToDownload = uRLToDownload;
		pathToDownloadedFile = Environment.getExternalStorageDirectory()
				.getPath() + FOLDER_NAME + "/" + filename;
	}

	private boolean setupDirectory() {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ FOLDER_NAME);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		return success;
	}

	public String doDownload() {
		if (setupDirectory()) {
			if (new File(pathToDownloadedFile).exists()) {
				return pathToDownloadedFile;
			}
			download = new Download();
			download.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					URLToDownload);
			while (!done)
				;
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
			Looper.prepare();
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				URL url = new URL(sUrl[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP "
							+ connection.getResponseCode() + " "
							+ connection.getResponseMessage();
				}
				// int fileLength = connection.getContentLength();

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