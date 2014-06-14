package pk.na.dailybingwallpaperbyna.DTO;

import java.util.Arrays;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Wallpaper {
	Date startdate;
	Date enddate;
	String urlbase;
	String copyright;
	String copyrightlink;
	Boolean wp;
	String hsh;
	Integer drk;
	Integer top;
	Integer bot;
	@SerializedName("hs")
	HotSpot[] hotSpots;
	public Wallpaper(Date startdate, Date enddate, String urlbase,
			String copyright, String copyrightlink, Boolean wp, String hsh,
			Integer drk, Integer top, Integer bot, HotSpot[] hotSpots) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
		this.urlbase = urlbase;
		this.copyright = copyright;
		this.copyrightlink = copyrightlink;
		this.wp = wp;
		this.hsh = hsh;
		this.drk = drk;
		this.top = top;
		this.bot = bot;
		this.hotSpots = hotSpots;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getUrlbase() {
		return urlbase;
	}
	public void setUrlbase(String urlbase) {
		this.urlbase = urlbase;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCopyrightlink() {
		return copyrightlink;
	}
	public void setCopyrightlink(String copyrightlink) {
		this.copyrightlink = copyrightlink;
	}
	public Boolean getWp() {
		return wp;
	}
	public void setWp(Boolean wp) {
		this.wp = wp;
	}
	public String getHsh() {
		return hsh;
	}
	public void setHsh(String hsh) {
		this.hsh = hsh;
	}
	public Integer getDrk() {
		return drk;
	}
	public void setDrk(Integer drk) {
		this.drk = drk;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getBot() {
		return bot;
	}
	public void setBot(Integer bot) {
		this.bot = bot;
	}
	public HotSpot[] getHotSpot() {
		return hotSpots;
	}
	public void setHotSpot(HotSpot[] hotSpots) {
		this.hotSpots = hotSpots;
	}
	@Override
	public String toString() {
		return "Wallpaper [startdate=" + startdate + ", enddate=" + enddate
				+ ", urlbase=" + urlbase + ", copyright=" + copyright
				+ ", copyrightlink=" + copyrightlink + ", wp=" + wp + ", hsh="
				+ hsh + ", drk=" + drk + ", top=" + top + ", bot=" + bot
				+ ", hotSpots=" + Arrays.toString(hotSpots) + "]";
	}
	
}
