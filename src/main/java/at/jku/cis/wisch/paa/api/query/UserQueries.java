package at.jku.cis.wisch.paa.api.query;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import at.jku.cis.wisch.paa.api.query.wikipedia.WikipediaVisits;
import at.jku.cis.wisch.paa.storage.ConnectionManager;
import at.jku.cis.wisch.paa.storage.PaaStorage;

@Path("/userQuery")
public class UserQueries {

	private static final String ERROR_MESSAGE_4_WRONG_TYPE = "You tried to GET something here, but the format is not supported --- only JSON is...";
	private static final String ERROR_MESSAGE_4_MISSING_PARAMS = "You tried to POST something here, but you did not correctly specify what (wrong or missing parameters)...";
	
	private static PaaStorage storage;
	
	private static final Logger logger = LogManager.getLogger(UserQueries.class);
	
	public UserQueries() {
		storage = ConnectionManager.getPaaStorageInstance();
	}
	
	// answer POST requests with queries
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String userQuery(@FormParam(value = "user") String user, @FormParam(value = "query") String query) {
		
		logger.debug("query ("+user+"/SPRING:"+SecurityContextHolder.getContext().getAuthentication().getName()+"): "+query);
		
		// TODO redesign query mechanism --- now it is all very specific and not reusable
		
		if (user == null || query == null) {
			return "{ 'user and query must be supplied (i.e. not be null)', '"+ ERROR_MESSAGE_4_MISSING_PARAMS + "' }";
		} else if (query.equals("languages")){
			
			// TODO check if query asks for languages based on Wikipedia page visits + set minimum threshold?
			//int threshold = 3;
			String langs = "";
			if (true) {
				langs = WikipediaVisits.getVisitedLanguages(storage, user);
			}
			
			//TODO
			return "{ '" + langs + "' }";
		} else {
			return "{ 'query not supported', '"+ ERROR_MESSAGE_4_MISSING_PARAMS + "' }";
		}
	}

	
	
	
    // -------------------------------------------------------------------------------------------------
	
	// This method is called if TEXT_PLAIN is requested
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextError() {
		logger.debug("text requested");
		return ERROR_MESSAGE_4_WRONG_TYPE;
	}

	// This method is called if XML is requested
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLError() {
		logger.debug("XML requested");
		return "<?xml version=\"1.0\"?>" + "<hello>" + ERROR_MESSAGE_4_WRONG_TYPE
				+ "</hello>";
	}

	// This method is called if HTML is requested
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlError() {
		logger.debug("HTML requested");
		return "<html> " + "<title>" + ERROR_MESSAGE_4_WRONG_TYPE + "</title>"
				+ "<body><h1>" + ERROR_MESSAGE_4_WRONG_TYPE + "</body></h1>"
				+ "</html> ";
	}
	
}