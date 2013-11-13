package ch.furthermore.demo.st.rest.api;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Opportunity;

@Component
public class VolunteerAPIImpl implements VolunteerAPI {
	@Autowired
	private VolunteerService volunteerService;
	
	@Context // Added so that we can get access to the HTTP headers for X-United-Way-Volunteer
	private HttpHeaders requestHeaders;

	@Context // Added so that we can get access to the HTTP headers for X-United-Way-Volunteer
	private HttpServletResponse servletResponse;

	@Override
	public Collection<Agency> getAgencies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Agency> getAgency(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Category> getCategories() {
		return volunteerService.getCategories();
	}

	@Override
	public Category getCategory(String id) {
		return volunteerService.getCategory( id );
	}

	@Override
	public Collection<Opportunity> getOpportunities(float latitude, float longitude, String zipcode) {
		if( getVolunteerId() == null ) {
			createVolunteerId(); // stamp them
		}
		if( zipcode == null || zipcode.isEmpty() ) {
			return volunteerService.getOpportunities(latitude, longitude, getVolunteerId() );
		}
		return volunteerService.getOpportunities( zipcode, getVolunteerId() );
	}

	@Override
	public Opportunity getOpportunity( String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Opportunity register( String id) {
		return getOpportunity( volunteerService.registerForOpportunity(id,getVolunteerId() ) );
	}

	private String getVolunteerId() {
		List<String> headers = requestHeaders.getRequestHeader(USER_HEADER);
		return headers == null || headers.isEmpty() ? null : headers.get(0); // should be none or one
	}
	
	private String createVolunteerId() {
		String volunteerId = UUID.randomUUID().toString();
		// TODO - save this stamped ID to be associated with the donorId
		if( servletResponse != null ) {
			servletResponse.setHeader( USER_HEADER, volunteerId );
		}
		return volunteerId;
	}
}
