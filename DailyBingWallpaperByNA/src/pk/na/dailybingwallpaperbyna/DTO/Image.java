package pk.na.dailybingwallpaperbyna.DTO;

import java.util.Arrays;

public class Image {
	Wallpaper[] images;
	public static final String HD = "_1920X1080.jpg";
	public static final String SemiHD = "_1366X768.jpg";

	@Override
	public String toString() {
		return "Image [images=" + Arrays.toString(images) + "]";
	}

	public Wallpaper[] getImages() {
		return images;
	}

	public void setImages(Wallpaper[] images) {
		this.images = images;
	}

	public Image(Wallpaper[] images) {
		super();
		this.images = images;
	}

}
