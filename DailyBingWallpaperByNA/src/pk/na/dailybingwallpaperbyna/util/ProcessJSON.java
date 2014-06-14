package pk.na.dailybingwallpaperbyna.util;

import pk.na.dailybingwallpaperbyna.DTO.Image;

import com.google.gson.GsonBuilder;

public class ProcessJSON {
	public static Image ConvertJSONToWallpaper(String jsonString) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyyMMdd");
		Image images = gsonBuilder.create().fromJson(jsonString, Image.class);
		return images;

	}
}
