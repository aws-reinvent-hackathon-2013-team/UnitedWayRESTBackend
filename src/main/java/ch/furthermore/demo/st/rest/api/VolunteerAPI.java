package ch.furthermore.demo.st.rest.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Opportunity;

@Component
@Path(VolunteerAPI.Paths.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface VolunteerAPI {
	
	public static interface Paths {
		public static final String ROOT = "/";
		public static final String CATEGORIES = "categories";
		public static final String CATEGORY = "category";
		public static final String AGENCIES = "agencies";
		public static final String AGENCY = "agency";
		public static final String OPPORTUNITIES = "opportunities";
		public static final String OPPORTUNITY = "opportunity";
		public static final String REGISTER = "register";
	}
	
	
	@GET
	@Path(Paths.AGENCIES)
	public Collection<Agency> getAgencies();
	
	@GET
	@Path(Paths.AGENCY + "/{id}")
	public Collection<Agency> getAgency( String id );
	
	@GET
	@Path(Paths.CATEGORIES)
	public Collection<Category> getCategories();
	
	@GET
	@Path(Paths.CATEGORY + "/{id}")
	public Collection<Category> getCategory( String id );
	
	@GET
	@Path(Paths.OPPORTUNITIES)
	public Collection<Opportunity> getOpportunities( Integer latitude, Integer longitude );
	
	@GET
	@Path(Paths.OPPORTUNITIES)
	public Collection<Opportunity> getOpportunities( Integer zipcode );
	
	@GET
	@Path(Paths.OPPORTUNITY + "/{id}")
	public Opportunity getOpportunity( @PathParam("id") String id );
	
	@POST
	@Path(Paths.OPPORTUNITY + "/{id}/" + Paths.REGISTER)
	public Opportunity register( @PathParam("id") String id );
}
