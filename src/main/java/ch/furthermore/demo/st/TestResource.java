package ch.furthermore.demo.st;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/testendpoint")
public class TestResource {
	@Autowired
	private DataAccess dataAccess;
	
	@GET
    @Path("/test1")
	@Produces(MediaType.APPLICATION_JSON)
    public Collection<GeocodedResults> pointsAroundMe() {
		return dataAccess.getOpportunities(6.6666f,	7.7777f, 100f); //6.6666	7.7777
	}
}
