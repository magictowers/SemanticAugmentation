package storage.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import api.tracking.ItemVisit;
import storage.PaaStorage;

public class MySQLStorage implements PaaStorage {

	private static Properties storageProperties;
	private static String jdbcDriverClassName;
	private static String jdbcHost;
	private static String jdbcDatabase;
	private static String jdbcSqlUser;
	private static String jdbcSqlPass;
	
	protected Connection connect = null;
	
	// STATEMENTS PART 1: PREPARED STATEMENTS DEFINITION
	private PreparedStatement preparedStatementItemVisit = null;
	private String preparedStatementItemVisitString = "INSERT INTO ItemVisit VALUES (default, ?, ?, ?, ?)";
	private PreparedStatement preparedStatementGetVisits = null;
	private String preparedStatementGetVisitsString = "SELECT id,url,timestamp FROM ItemVisit WHERE user = ? AND url LIKE ?";
	private PreparedStatement preparedStatementGetUserId = null;
	private String preparedStatementGetUserIdString = "SELECT id FROM User WHERE username = ?";

	private static final Logger logger = LogManager.getLogger(MySQLStorage.class);
	
	@Override
	public void init() {
		
		storageProperties = new Properties();
		try {
			storageProperties.load(MySQLStorage.class.getClassLoader().getResourceAsStream("at.jku.cis.wisch.paa.storage.properties"));
			
			jdbcDriverClassName = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.driverClassName");
			jdbcHost = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.host");
			jdbcDatabase = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.database");
			jdbcSqlUser = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.username");
			jdbcSqlPass = storageProperties.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.password");

			
			try {
				// This will load the MySQL driver, each DB has its own driver
				Class.forName(jdbcDriverClassName);
				// Setup the connection with the DB
				
				logger.info("Connecting to DB: "+"jdbc:mysql://" + jdbcHost + "/"
						+ jdbcDatabase + "?" + "user=" + jdbcSqlUser + "&password="
						+ "*****");
				
				connect = DriverManager.getConnection("jdbc:mysql://" + jdbcHost + "/"
						+ jdbcDatabase + "?" + "user=" + jdbcSqlUser + "&password="
						+ jdbcSqlPass);
				logger.info("Successfully connected to database.");
				
				// STATEMENTS PART 2: PREPARED STATEMENTS PREPARATION
				preparedStatementItemVisit = connect.prepareStatement(preparedStatementItemVisitString);
				preparedStatementGetVisits = connect.prepareStatement(preparedStatementGetVisitsString);
				preparedStatementGetUserId = connect.prepareStatement(preparedStatementGetUserIdString);

				logger.info("Successfully created PreparedStatements.");
				
			} catch (Exception e) {
				logger.error("Database connection error.", e);
				e.printStackTrace();
			} finally {

			}

			
			
			
		} catch (IOException e) {
			logger.error("Could not load properties file!", e);
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void close() {
		logger.info("Closing connection to database...");
		try {
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	
	
	
	// STATEMENTS PART 3: PREPARED STATEMENTS USAGE
	
	private void logRawVisit(ItemVisit visit)
			throws SQLException {
		// log the visit
		preparedStatementItemVisit.setString(1, visit.getUser());
		preparedStatementItemVisit.setString(2, visit.getUrl());
		preparedStatementItemVisit.setTimestamp(3,
				new java.sql.Timestamp(visit.getTimestampms()));
		preparedStatementItemVisit.setLong(4, visit.getTimestampms());
		preparedStatementItemVisit.executeUpdate();
	}

	private void logVisitEvent(ItemVisit visit) 
			throws SQLException {
		//TODO log the visit to the DB
		
	}

	
	// insert itemVisit to DB
	@Override
	public boolean storeRawItemVisit(ItemVisit visit) {
		try {
			logRawVisit(visit);
			return true;
		} catch (SQLException e) {
			logger.error("Error while writing to database.", e);
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public boolean storeItemVisitEvent(ItemVisit visit) {
		try {
			logVisitEvent(visit);
			return true;
		} catch (SQLException e) {
			logger.error("Error while writing to database.", e);
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public Set<String> getURLsMatchingPattern(String user, String urlPattern) {

		try {
			
			preparedStatementGetVisits.setString(1, user);
			preparedStatementGetVisits.setString(2, "%"+urlPattern+"%");
			
			//println(preparedStatementGetVisits);
			
			preparedStatementGetVisits.execute();
			ResultSet rs = preparedStatementGetVisits.getResultSet();
			
			Set<String> visits = new HashSet<String>();

			while (rs.next()) {
				visits.add(rs.getString("url"));
			}
			
			rs.close();
						
			return visits;
			

		} catch (SQLException e) {
			logger.error("Error while reading from database.", e);
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int getUserId(String username) {

		int userId = -1;

		try {
			
			preparedStatementGetUserId.setString(1, username);
			
			preparedStatementGetUserId.execute();
			
			ResultSet rs = preparedStatementGetUserId.getResultSet();
			
			
			if (rs.next()) {
				userId = rs.getInt(1);
			} else {
				// TODO handle error with no result
			}
			
			if (rs.next()) {
				// TODO handle error with more than one result
			}
			
			rs.close();
						
						

		} catch (SQLException e) {
			logger.error("Error while reading from database.", e);
			e.printStackTrace();
		}
		
		return userId;
	
	
	
	}


	
}
