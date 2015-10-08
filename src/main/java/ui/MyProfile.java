package ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/myProfile")
public class MyProfile {
	
	
	// This method is called if TEXT_PLAIN is requested
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String plainTextProfile() {
	    return "TODO show user profile";
	  }

	  // This method is called if XML is requested
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public String xmlProfile() {
	    return "<?xml version=\"1.0\"?>" + "<hello>"+"TODO show user profile" + "</hello>";
	  }

	  // This method is called if HTML is requested
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String htmlProfile() {
	    
		  return "<html> " + "<title>" + "PAA MyProfile" + "</title>"
	        + "<body><h1>" + "TODO show user profile" + "</h1></body>" 
				  + "<p>bla bla</p>"
				  + "</html> ";
	  }

}
