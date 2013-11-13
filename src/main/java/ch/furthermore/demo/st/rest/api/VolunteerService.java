package ch.furthermore.demo.st.rest.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.furthermore.demo.st.DataAccess;
import ch.furthermore.demo.st.GeocodedResults;
import ch.furthermore.demo.st.LatLong;
import ch.furthermore.demo.st.ZipToLatLong;
import ch.furthermore.demo.st.rest.model.Agency;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Location;
import ch.furthermore.demo.st.rest.model.Opportunity;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

@Component
public class VolunteerService {
	private static final float LOOKUP_RADIUS_METER = 1000f;
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
		
		sortDistance(latitude, longitude, result);
		
		return result;
	}

	void sortDistance(float latitude, float longitude, List<Opportunity> result) {
		final LatLong givenPos = new LatLong(latitude, longitude);
		Collections.sort(result, new Comparator<Opportunity>() {
			@Override
			public int compare(Opportunity o1, Opportunity o2) { //FIXME verify correctness ;-)
				final LatLong o1Pos = new LatLong((float)o1.getLocation().getLatitude(),(float) o1.getLocation().getLongitude());
				final LatLong o2Pos = new LatLong((float)o2.getLocation().getLatitude(),(float)o2.getLocation().getLongitude());
				
				double dist1 = 100. * ZipToLatLong.getInstance().distance(givenPos, o1Pos);
				double dist2 = 100. * ZipToLatLong.getInstance().distance(givenPos, o2Pos);
				int result = (int)(dist1 - dist2);
				
				System.out.println("compare " + o1Pos.getLatitude() + "/" + o1Pos.getLongitude() + "("+dist1+")" + " with " + o2Pos.getLatitude() + "/" + o2Pos.getLongitude() + "("+dist2+") => " + result); //TODO remove DEBUG code 
				
				return result; 
			}
		});
	}
	
	public Collection<Opportunity> getOpportunities( String zipcode ) {
		LatLong ll = getLatLongZip( zipcode );
		return getOpportunities( ll.getLatitude(), ll.getLatitude() );
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
