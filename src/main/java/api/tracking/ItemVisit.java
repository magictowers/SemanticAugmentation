package api.tracking;

public class ItemVisit {
	
	private String user;
	private String url;
	private String metainfo;
	private long timestampms;
	
	private int userId;
	private int itemId;
	
	boolean raw;
	
	public ItemVisit(String user, String url, String metainfo,
			long timestampms) {
		this.user = user;
		this.url = url;
		this.metainfo = metainfo;
		this.timestampms = timestampms;
		
		raw = true;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getMetainfo() {
		return metainfo;
	}
	
	public long getTimestampms() {
		return timestampms;
	}
	
	public void setIDs(int userId, int itemId) {
		this.userId = userId;
		this.itemId = itemId;
		
		raw = false;
	}
	
	public boolean isRaw() {
		return raw;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
}
