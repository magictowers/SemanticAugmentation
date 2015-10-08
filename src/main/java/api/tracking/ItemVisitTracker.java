package api.tracking;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import storage.ConnectionManager;
import storage.PaaStorage;

@Path("/itemVisit")
public class ItemVisitTracker {

	private static final String ERROR_MESSAGE_4_GET = "You tried to GET something here, but you really should only PUT stuff here...";
	
	private static PaaStorage storage;
	private static ItemVisitArchive archive;
	
	private static final Logger logger = LogManager.getLogger(ItemVisitTracker.class);
	
	public ItemVisitTracker() {
		storage = ConnectionManager.getPaaStorageInstance();
		archive = new ItemVisitArchive(storage);
	}
	

	// receives PUT requests to store visited URLs on the server
	@PUT
	public String trackItemVisit(@FormParam(value = "user") String user,
			@FormParam(value = "url") String url,
			@FormParam(value = "metainfo") String metainfo) {
		
		logger.info("tracked: "+user+" @ "+url+" SPRING: "+SecurityContextHolder.getContext().getAuthentication().getName());
		
		boolean success = false;
		
		
		
		if (user != null && url != null) { // TODO make more checks here

			ItemVisit visit = new ItemVisit(user, url, metainfo, System.currentTimeMillis());
			
			success = storage.storeRawItemVisit(visit);
			
			if (success) {
				int userId = getUserId(visit.getUser());
				int itemId = updateItemAndGetId(visit.getUrl(), visit.getMetainfo());
				visit.setIDs(userId, itemId);
				
				success = storage.storeItemVisitEvent(visit);
			}

		}
		
		return "tracked "+success;
	}

	private int getUserId(String username) {
		return archive.getUserId(username);
	}

	private int updateItemAndGetId(String url, String metainfo) {
		// check if item already exits
					//TODO check and get ID

					// if not, create new item
		
		// anyway: add (new) metainfo
					//TODO and return ID
		return 0;
	}


    
	
	
	// -------------------------------------------------------------------------------------------------
	
	// This method is called if TEXT_PLAIN is requested
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextError() {
		return ERROR_MESSAGE_4_GET;
	}

	// This method is called if XML is requested
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLError() {
		return "<?xml version=\"1.0\"?>" + "<hello>" + ERROR_MESSAGE_4_GET
				+ "</hello>";
	}

	// This method is called if HTML is requested
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlError() {
		return "<html> " + "<title>" + ERROR_MESSAGE_4_GET + "</title>"
				+ "<body><h1>" + ERROR_MESSAGE_4_GET + "</body></h1>"
				+ "</html> ";
	}
	
}