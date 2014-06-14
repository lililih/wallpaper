package pk.na.dailybingwallpaperbyna;

import java.net.HttpURLConnection;

import pk.na.dailybingwallpaperbyna.DTO.Image;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String BASEURL = "http://www.bing.com";
	private static final String URL = "/HPImageArchive.aspx?format=js&idx=0&n=1&nc=1397809837851&pid=hp";
	Button fetchButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fetchWallpaperListener();
	}

	public void fetchWallpaperListener() {
		fetchButton = (Button) findViewById(R.id.button1);
		fetchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				GetJSON gettingJson = new GetJSON(BASEURL + URL);
				Image imagesList;
				try {
					imagesList = gettingJson.doGetJSON();

					String filePath = null;
					String downloadLink;
					String downloadFilePath;
					if (imagesList != null) {
						downloadLink = imagesList.getImages()[0].getUrlbase()
								+ Image.SemiHD;
						downloadFilePath = imagesList.getImages()[0].getHsh()
								+ Image.SemiHD;
						DownloadFile downloadFile = new DownloadFile(
								downloadLink, downloadFilePath);
						filePath = downloadFile.doDownload();
						Log.i("Download Link",downloadLink);
						Log.i("Download File Path", filePath);
					}
					if ((HttpURLConnection.HTTP_UNAVAILABLE + "")
							.equals(filePath) || imagesList == null) {
						Toast.makeText(getApplicationContext(),
								"Something went wrong, cannot download file",
								Toast.LENGTH_LONG).show();
					}
				} catch (InterruptedException e) {
					Toast.makeText(getApplicationContext(),
							"Something went wrong, cannot download file",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
	}
}
