package at.jku.cis.wisch.paa.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/urlgetSemanticDescription")

public class SemanticDescription {
	@GET
	  @Produces(MediaType.TEXT_HTML)
	  public String htmlHome() {
	    
		  return "<html> " + "<title>" + "Semantic Description IN PAA APP" + "</title>"
				  	+ "<body><h1>" + "Semantic Descriptino" + "</h1></body>" + ""
	        		+ "<p>Welcome to SEMANTIC DESCRIPTION in PAA APP!</p>"
	        		+ "<p><a href='./myProfile'>My Profile</a></p>"
	        		+ "<p><a href='./register'>Register</a></p>"
	        		+ "<p><a href='./../downloads/my-paa.xpi'>Download the Firefox-Plugin</a></p>"
	        		+ "</html> ";
	  }

}
