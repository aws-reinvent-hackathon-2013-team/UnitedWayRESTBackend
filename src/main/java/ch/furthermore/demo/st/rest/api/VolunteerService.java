package ch.furthermore.demo.st.rest.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.furthermore.demo.st.rest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.DataAccess;
import ch.furthermore.demo.st.GeocodedResults;
import ch.furthermore.demo.st.LatLong;
import ch.furthermore.demo.st.ZipToLatLong;

import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Donor;
import ch.furthermore.demo.st.rest.model.Location;
import ch.furthermore.demo.st.rest.model.Opportunity;
import ch.furthermore.demo.st.rest.model.Registration;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

@Component
public class VolunteerService {
	private static final float LOOKUP_RADIUS_METER = 2500f;
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
		return categories.get( id ) != null
				? categories.get( id )
				: new Category(id); //FIXME (!!!) remove this last-minute-dirty hack (currently, some code bases on category name and other code bases on category id)
	}

	public Collection<Opportunity> getOpportunities(float latitude, float longitude, String volunteerId ) {
		return getOpportunities(latitude, longitude, volunteerId, volunteerPreferredCategories(volunteerId)); //FIXME
	}
	
	private List<String> volunteerPreferredCategories(String volunteerId) {
		List<String> categories = new LinkedList<String>();
		for (Donor donor : dataAccess.getDonorHistory(volunteerId)) {
			categories.add(donor.getCategory());
		}
	
		for (Registration registration : dataAccess.getRegistrations(volunteerId)) {
			categories.add(registration.getCategory());
		}
		
		return categories;
	}

	public Collection<Opportunity> getOpportunities(float latitude, float longitude, String volunteerId, List<String> preferredCategories) {
		List<Opportunity> result = new LinkedList<Opportunity>();
		for (GeocodedResults flatOp : dataAccess.getOpportunities(latitude, longitude, LOOKUP_RADIUS_METER)) {
			Location location = new Location();
			location.setAddress1(flatOp.getAgencyAddress());
			location.setAddress2(flatOp.getAgencyAddress());
			location.setCity(flatOp.getAgencyCity());
			location.setState(flatOp.getAgencyState());
			location.setZip(flatOp.getAgencyZip());
			location.setLatitude(flatOp.getLatitude());
			location.setLongitude(flatOp.getLongitude());
			
			
			Agency agency = new Agency();
			agency.setEmail(flatOp.getEmail());
			agency.setId("" + flatOp.getNeedId());
			agency.setLocation(location);
			agency.setName(flatOp.getAgencyName());
			agency.setPhone(flatOp.getPhone());
			agency.setUrl("http://giphy.com/search/" + flatOp.getCategory());
			
			Opportunity op = new Opportunity();
			op.setId("" + flatOp.getNeedId());
			op.setAgency(agency);
			op.setDescription(flatOp.getDescription());
			op.setLocation(location);
			op.setTimeEnd(new Date());
			op.setTimeStart(new Date());
			op.setTitle(flatOp.getNeedTitle());
			op.setCategory( getCategory(flatOp.getCategory()) );
			
			result.add(op);
		}
		
		sortDistance(latitude, longitude, preferredCategories, result);
		
		return result;
	}

	void sortDistance(float latitude, float longitude, final List<String> preferredCategories, final List<Opportunity> result) {
		System.out.println("Soring by " + preferredCategories + " and distance"); //TODO remove DEBUG code
		
		final LatLong givenPos = new LatLong(latitude, longitude);
		Collections.sort(result, new Comparator<Opportunity>() {
			@Override
			public int compare(Opportunity o1, Opportunity o2) { 
				int result = comparePreferredCategory(preferredCategories, o1, o2);
				if (result == 0) {
					return compareDistance(givenPos, o1, o2);
				}
				else {
					return result;
				}
			}
		});
	}
	
	int comparePreferredCategory(List<String> preferredCategories, Opportunity o1, Opportunity o2) {
		boolean o1Preferred = preferredCategories.contains(o1.getCategory().getName());
		boolean o2Preferred = preferredCategories.contains(o2.getCategory().getName());
		if (o1Preferred && o2Preferred) {
			return 0;
		}
		else if (o1Preferred && !o2Preferred) {
			return -1;
		}
		else if (!o1Preferred && o2Preferred) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	int compareDistance(LatLong givenPos, Opportunity o1, Opportunity o2) { 
		final LatLong o1Pos = new LatLong((float)o1.getLocation().getLatitude(),(float) o1.getLocation().getLongitude());
		final LatLong o2Pos = new LatLong((float)o2.getLocation().getLatitude(),(float)o2.getLocation().getLongitude());
		
		double dist1 = 100. * ZipToLatLong.getInstance().distance(givenPos, o1Pos);
		double dist2 = 100. * ZipToLatLong.getInstance().distance(givenPos, o2Pos);
		int result = (int)(dist1 - dist2);
		
		System.out.println("compare " + o1Pos.getLatitude() + "/" + o1Pos.getLongitude() + "("+dist1+")" + " with " + o2Pos.getLatitude() + "/" + o2Pos.getLongitude() + "("+dist2+") => " + result); //TODO remove DEBUG code 
		
		return result; 
	}
	
	public Collection<Opportunity> getOpportunities( String zipcode, String volunteerId ) {
		LatLong ll = getLatLongZip( zipcode );
		return getOpportunities( ll.getLatitude(), ll.getLongitude(), volunteerId );
	}
	
	public String registerForOpportunity( String opportunityId, String volunteerId ) {

        Registration r = new Registration();
        r.setDonorUUID(volunteerId);
        r.setOpportunityId(opportunityId);
        r.setCategory("dummy");

        dataAccess.AddRegistration(r);

		return opportunityId;
	}
	
	private LatLong getLatLongZip( String zipcode ) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest request = new GeocoderRequestBuilder().setAddress(zipcode).setLanguage("en").getGeocoderRequest();
		GeocodeResponse response = geocoder.geocode(request);
		LatLong ll = null;
		if( response != null && response.getStatus() == GeocoderStatus.OK ) {
			for( GeocoderResult geoResult : response.getResults() ) {
				LatLng googLatLong = geoResult.getGeometry().getLocation();
				ll = new LatLong( googLatLong.getLat().floatValue(), googLatLong.getLng().floatValue() );
			}
		}
		
		return ll;
	}
}
