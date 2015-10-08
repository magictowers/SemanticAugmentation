package api.tracking;

import java.util.HashMap;
import java.util.Map;

import storage.PaaStorage;

public class ItemVisitArchive {
	
	private PaaStorage storage;
	
	private Map<String,Integer> users;
	
	protected ItemVisitArchive(PaaStorage storage) {
		this.storage = storage;
		users = new HashMap<String,Integer>();
	}

	protected int getUserId(String username) {
		int id = -1;
		Integer idInteger = users.get(username);
		if (idInteger != null) {
			id = idInteger.intValue();
		} else {
			//read from DB and, if successful, store to hashmap
			int idFromDB = storage.getUserId(username);
			
			if (idFromDB >= 1) {
				users.put(username, id);
				id = idFromDB;
			} else {
				//TODO handle error of missing userId				
			}
		}
		return id;
	}

}
