package ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
public class Register {

	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String htmlHome() {
	    
		  return "<html> " + "<title>" + "PAA Registration" + "</title>"
	        + "<body><h1>" + "TODO User registration" + "</h1></body>" + "</html> ";
	  }

	} 