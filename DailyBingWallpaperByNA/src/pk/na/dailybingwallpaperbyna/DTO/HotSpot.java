package pk.na.dailybingwallpaperbyna.DTO;

public class HotSpot {
	private String desc;
	private String link;
	private String query;
	private Integer locx;
	private Integer locy;
	public HotSpot(String desc, String link, String query, Integer locx,
			Integer locy) {
		super();
		this.desc = desc;
		this.link = link;
		this.query = query;
		this.locx = locx;
		this.locy = locy;
	}
	public String getDesc() {
		return desc;
	}
	@Override
	public String toString() {
		return "SearchPointer [desc=" + desc + ", link=" + link + ", query="
				+ query + ", locx=" + locx + ", locy=" + locy + "]";
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Integer getLocx() {
		return locx;
	}
	public void setLocx(Integer locx) {
		this.locx = locx;
	}
	public Integer getLocy() {
		return locy;
	}
	public void setLocy(Integer locy) {
		this.locy = locy;
	}
	
}
