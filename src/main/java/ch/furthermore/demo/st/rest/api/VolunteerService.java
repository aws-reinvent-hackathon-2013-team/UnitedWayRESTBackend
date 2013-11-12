package ch.furthermore.demo.st.rest.api;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.DataAccess;
import ch.furthermore.demo.st.GeocodedResults;
import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Location;
import ch.furthermore.demo.st.rest.model.Opportunity;

@Component
public class VolunteerService {
	private static final float LOOKUP_RADIUS_METER = 100f;
	private static Map<String,Category> categories = new HashMap<String,Category>();
	
	static {
		categories.put( "INCOME", new Category().setId("INCOME").setName("Income") );
		categories.put( "BASIC_NEEDS", new Category().setId("BASIC_NEEDS").setName("Basic Needs") );
		categories.put( "EDUCATION", new Category().setId("EDUCATION").setName("Education") );
	}
	
	@Autowired
	private DataAccess dataAccess;

	public Collection<Category> getCategories() {
		return categories.values();
	}
	
	public Category getCategory( String id ) {
		return categories.get( id );
	}

	public Collection<Opportunity> getOpportunities(float latitude, float longitude) {
		Collection<Opportunity> result = new LinkedList<Opportunity>();
		for (GeocodedResults flatOp : dataAccess.getOpportunities(latitude, longitude, LOOKUP_RADIUS_METER)) {
			Location location = new Location();
			location.setAddress1(flatOp.getAgencyAddress());
			location.setAddress2(flatOp.getAgencyAddress());
			location.setCity(flatOp.getAgencyCity());
			location.setState(flatOp.getAgencyState());
			location.setZip(flatOp.getAgencyZip());
			
			Agency agency = new Agency();
			agency.setEmail(flatOp.getEmail());
			agency.setId("" + flatOp.getNeedId());
			agency.setLocation(location);
			agency.setName(flatOp.getAgencyName());
			agency.setPhone(flatOp.getPhone());
			agency.setUrl("http://fixme.com");
			
			Opportunity op = new Opportunity();
			op.setId("" + flatOp.getNeedId());
			op.setAgency(agency);
			op.setDescription(flatOp.getDescription());
			op.setLocation(location);
			op.setTimeEnd(new Date());
			op.setTimeStart(new Date());
			op.setTitle(flatOp.getNeedTitle());
			
			result.add(op);
		}
		
		//FIXME sort by distance
		
		return result;
	}
}
