package ar.edu.unlp.info.lifia.api.url;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/url.getSemanticDescription")
public class SemanticDescription {
	 
	@GET
	  @Produces(MediaType.TEXT_HTML)
	  public String htmlHome() {
	    
		  return "<html> " + "<title>" + "Semantic Description" + "</title>"
				  	+ "<body><h1>" + "SEMANTIC API URL" + "</h1></body>" + ""
	        		+ "</html> ";
	  }

}
