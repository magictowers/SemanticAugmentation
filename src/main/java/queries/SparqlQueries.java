package queries;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import storage.ConnectionManager;

@Path("/sparqlQuery")
public class SparqlQueries {

	private static final String ERROR_MESSAGE_4_WRONG_TYPE = "You tried to GET something here, but the format is not supported --- only JSON is...";
	private static final String ERROR_MESSAGE_4_MISSING_PARAMS = "You tried to POST something here, but you did not correctly specify what (wrong or missing parameters)...";
	
//	private static Dataset dataset;
	
//	public SparqlQueries() {
//		dataset = ConnectionManager.getJenaTdbDataset();
//	}
	
	private static final Logger logger = LogManager.getLogger(SparqlQueries.class);
	
	// answer POST requests with queries
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String userQuery(@FormParam(value = "user") String user, @FormParam(value = "sparql") String sparql) {
		
		logger.debug("query ("+user+"/SPRING:"+SecurityContextHolder.getContext().getAuthentication().getName()+"): "+sparql);
		
		if (user == null || sparql == null) {
			return "{ 'user and SPARQL query must be supplied (i.e. not be null)', '"+ ERROR_MESSAGE_4_MISSING_PARAMS + "' }";
		} else {
			
			Dataset dataset = ConnectionManager.getPaaTdbStorageInstance().getNamedJenaTdbDataset(user);
			
			String baseDIR = "e:/rdf/";
			  
			  dataset.begin(ReadWrite.WRITE) ;
			  Model model = dataset.getDefaultModel() ;
			  model.read(baseDIR+"foaf.rdf", null);
			  model.read(baseDIR+"instances.rdf", null);
			  
			  dataset.commit(); // !!!
			  
			  QueryExecution qe = QueryExecutionFactory.create(sparql, model);
				ResultSet rs = qe.execSelect();
				String result = "";
				while (rs.hasNext()) {
					result += "Person: "+rs.next()+" ";
				}
			  dataset.end() ;
			  return result;
			
			
			
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