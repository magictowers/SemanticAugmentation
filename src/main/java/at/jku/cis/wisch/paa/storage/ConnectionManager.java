package at.jku.cis.wisch.paa.storage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.jku.cis.wisch.paa.storage.mysql.MySQLStorage;
import at.jku.cis.wisch.paa.storage.tdb.PaaTdbStorage;

public class ConnectionManager {
	
	private static PaaStorage storage = null;
	private static PaaTdbStorage tdbStorage = null;
	
	private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
	
	//	public ConnectionManager() {
	//		logger.info("ConnectionManager initialzing...");
	//	}
	
	
	/**
	 * provides PaaStorage instances
	 * 
	 * @return PaaStorage implementation instance
	 */
	public static PaaStorage getPaaStorageInstance(){
		
		if (storage == null) {
			logger.info("Creating new MySQLStorage...");
			storage = new MySQLStorage();
			logger.info("MySQLStorage initalizing...");
			storage.init();
		}
		return storage;
		
	}
	
	public static PaaTdbStorage getPaaTdbStorageInstance(){
		
		if (tdbStorage == null) {
			logger.info("Creating new PaaTdbStorage...");
			tdbStorage = new PaaTdbStorage();
			logger.info("PaaTdbStorage initalizing...");
			tdbStorage.init();
		}
		return tdbStorage;
		
	}

}
