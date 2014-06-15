package pk.na.dailybingwallpaperbyna;

import java.io.IOException;
import java.net.HttpURLConnection;

import pk.na.dailybingwallpaperbyna.DTO.Image;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String BASEURL = "http://www.bing.com";
	private static final String URL = "/HPImageArchive.aspx?format=js&idx=0&n=1&nc=1397809837851&pid=hp";
	String filePath = null;
	String fetchDate = null;
	String imageCopyright = null;
	Button fetchButton = null;
	Button setWallpaperButton;
	SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPreferences = getSharedPreferences("BingWallpaper",
				Context.MODE_PRIVATE);
		if (sharedPreferences.contains("lastImage")) {
			filePath = sharedPreferences.getString("lastImage", null);
			setWallpaperShowcase();
		}
		if (sharedPreferences.contains("fetchDate")) {
			fetchDate = sharedPreferences.getString("fetchDate", null);
		}
		if (sharedPreferences.contains("imageCopyright")) {
			imageCopyright = sharedPreferences.getString("imageCopyright", null);
		}
		fetchWallpaperListener();
		setWallpaperListener();
	}

	public void setWallpaperListener() {
		setWallpaperButton = (Button) findViewById(R.id.button2);
		setWallpaperButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setWallpaper();
			}
		});
	}
	public void fetchWallpaperListener() {
		fetchButton = (Button) findViewById(R.id.button1);
		fetchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fetchWallpaper();
			}
		});
	}
	
	private void fetchWallpaper(){
		GetJSON gettingJson = new GetJSON(BASEURL + URL);
		
		try {
			Image imagesList;
			imagesList = gettingJson.doGetJSON();
			String downloadLink;
			String downloadFileName;
			if (imagesList != null) {
				downloadLink = BASEURL
						+ imagesList.getImages()[0].getUrlbase()
						+ Image.HD;
				downloadFileName = imagesList.getImages()[0].getHsh()
						+ Image.HD;
				DownloadFile downloadFile = new DownloadFile(
						downloadLink, downloadFileName);
				filePath = downloadFile.doDownload();
				Log.i("Download Link", downloadLink);
				Log.i("Download File Path", filePath);
				Editor editor = sharedPreferences.edit();
				editor.putString("lastImage", filePath);
				editor.putString("fetchDate", imagesList.getImages()[0].getStartdate().toString());
				editor.putString("imageCopyright", imagesList.getImages()[0].getCopyright());
				editor.commit();
				setWallpaperShowcase();
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
	private void setWallpaper(){
		if (filePath != null){
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			try {
				getApplicationContext().setWallpaper(bitmap);
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "Could not load file from memory! Please download again!", Toast.LENGTH_LONG).show();
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "There are awesome wallpapers available with Bing! Press Fetch to fetch the latest wallpaper!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void setWallpaperShowcase() {
		if (filePath != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ImageView img = (ImageView) findViewById(R.id.imageView1);
			img.setImageBitmap(bitmap);
			TextView tv = (TextView) findViewById(R.id.textView3);
			if (fetchDate != null)
				tv.setText(fetchDate);
			tv=(TextView) findViewById(R.id.textView4);
			if (imageCopyright != null)
				tv.setText(imageCopyright);
		}
	}

	// TODO: Nomi please make this function run in a cron job
	public void runCronically(){
		fetchWallpaper();
		setWallpaper();
	}
}
