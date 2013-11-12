package ch.furthermore.demo.st;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/management")
public class ManagementResource { //FIXME authentication and authorization!!!
	@Autowired
	private Importer importer;
	
	@GET
    @Path("/startImport")
	@Produces(MediaType.APPLICATION_JSON)
    public String startImport() {
		try {
			importer.importData();
			return "done ;-)";
		}
		catch (Exception e) {
			return "Sorry: "  + e;
		}
	}
}
