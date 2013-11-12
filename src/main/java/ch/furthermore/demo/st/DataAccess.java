package ch.furthermore.demo.st;

import java.util.Collection;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAccess {
	@Autowired
	private DynamoGeoServiceFactory dynamoGeoServiceFactory;
	private DynamoGeoService geoService;
	
	@PostConstruct
	public void init() {
		String tableName = "geocodedResults4"; //FIXME System.getProperty("PARAM2", "geocodedResults");
		geoService = dynamoGeoServiceFactory.createDynamoGeoService(tableName);
	}
	
	public void insertgetOpportunity(GeocodedResults r) {
		PointData pd = new PointData();
		pd.setRangeKey(r.getRangeKey());
		pd.setLatitude(r.getLatitude());
		pd.setLongitude(r.getLongitude());
		pd.withKeyValue("needTitle", r.getNeedTitle());
		pd.withKeyValue("description", r.getDescription());
		pd.withKeyValue("agencyIdBit", "" + r.getAgencyIdBit());
		pd.withKeyValue("agencyName", r.getAgencyName());
		pd.withKeyValue("category", r.getCategory());
		pd.withKeyValue("agencyAddress", r.getAgencyAddress());
		pd.withKeyValue("agencyCity", r.getAgencyCity());
		pd.withKeyValue("agencyState", r.getAgencyState());
		pd.withKeyValue("agencyZip", "" + r.getAgencyZip());
		pd.withKeyValue("phone", r.getPhone());
		pd.withKeyValue("email", r.getEmail());
		
		//System.out.println(r.getLatitude() + "/" + r.getLongitude()); //TODO remove DEBUG code
		
		geoService.putPoint(pd);
	}
	
	public Collection<GeocodedResults> getOpportunities( float lat, float longitude, float radius ) {
		Collection<GeocodedResults> results = new LinkedList<GeocodedResults>();
		for (PointData pd : geoService.getPointsWithinRadius(lat,  longitude, radius,
				"needTitle",
				"description",
				"agencyIdBit",
				"agencyName",
				"category",
				"agencyAddress",
				"agencyCity",
				"agencyState",
				"agencyZip",
				"phone",
				"email")) 
		{
			GeocodedResults r = new GeocodedResults();
			r.setRangeKey(pd.getRangeKey());
			r.setLatitude(pd.getLatitude());
			r.setLongitude(pd.getLongitude());
			
			r.setNeedTitle(pd.getData().get("needTitle"));
			r.setDescription(pd.getData().get("description"));
			r.setAgencyIdBit(Long.parseLong(pd.getData().get("agencyIdBit")));
			r.setAgencyName(pd.getData().get("agencyName"));
			r.setCategory(pd.getData().get("category"));
			r.setAgencyAddress(pd.getData().get("agencyAddress"));
			r.setAgencyCity(pd.getData().get("agencyCity"));
			r.setAgencyState(pd.getData().get("agencyState"));
			r.setAgencyZip(Integer.parseInt(pd.getData().get("agencyZip")));
			r.setPhone(pd.getData().get("phone"));
			r.setEmail(pd.getData().get("email"));
		}
		return results; 
	}
}
