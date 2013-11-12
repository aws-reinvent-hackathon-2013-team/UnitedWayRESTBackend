package ch.furthermore.demo.st.rest.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Opportunity;

@Component
public class VolunteerAPIImpl implements VolunteerAPI {
	@Autowired
	private VolunteerService volunteerService;

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
	public Collection<Opportunity> getOpportunities(float latitude, float longitude) {
		return volunteerService.getOpportunities(latitude, longitude);
	}

	//@Override
	//public Collection<Opportunity> getOpportunities(Integer zipcode) {
	//	// TODO Auto-generated method stub
	//	return null;
	//}

	@Override
	public Opportunity getOpportunity( String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Opportunity register( String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
