package at.jku.cis.wisch.paa.storage;

import java.util.Set;

import at.jku.cis.wisch.paa.api.tracking.ItemVisit;

public interface PaaStorage {
	
	public void init();
	
	public void close();

	public boolean storeRawItemVisit(ItemVisit itemVisit);
	
	public boolean storeItemVisitEvent(ItemVisit itemVisit);
	
	public Set<String> getURLsMatchingPattern(String user, String urlPattern);
	
	public int getUserId(String username);

}
