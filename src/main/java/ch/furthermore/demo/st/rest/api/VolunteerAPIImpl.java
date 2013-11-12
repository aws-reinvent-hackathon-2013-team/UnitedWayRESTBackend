package ch.furthermore.demo.st.rest.api;

import java.util.Collection;

import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Opportunity;

@Component
public class VolunteerAPIImpl implements VolunteerAPI {

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
		return VolunteerService.getCategories();
	}

	@Override
	public Category getCategory(String id) {
		return VolunteerService.getCategory( id );
	}

	@Override
	public Collection<Opportunity> getOpportunities(Integer latitude, Integer longitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Opportunity> getOpportunities(Integer zipcode) {
		// TODO Auto-generated method stub
		return null;
	}

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
