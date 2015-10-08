package storage.tdb;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.tdb.TDBFactory;

import storage.mysql.MySQLStorage;

public class PaaTdbStorage {
	
	private Properties storageProperties;
	private String jenaTdbDirectory;
	
	private static final Logger logger = LogManager.getLogger(PaaTdbStorage.class);
	
	public void init() {
		storageProperties = new Properties();
		try {
			storageProperties.load(MySQLStorage.class.getClassLoader().getResourceAsStream("at.jku.cis.wisch.paa.storage.properties"));
			jenaTdbDirectory = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.tdb.directory");
		} catch (IOException e) {
			logger.error("Could not load properties file!", e);
			e.printStackTrace();
		}
		
	}
	
	public Dataset getJenaTdbDataset() {
		return getJenaTdbDataSetByLocation(jenaTdbDirectory) ;
	}
	
	public Dataset getNamedJenaTdbDataset(String name) {
		//TODO check String length and name
		return getJenaTdbDataSetByLocation(jenaTdbDirectory+name+"/") ;
	}

	private static Dataset getJenaTdbDataSetByLocation(String location) {
		logger.info("creating TDB dataset at "+location);
		Dataset d = TDBFactory.createDataset(location);
		return d;
	}

}
