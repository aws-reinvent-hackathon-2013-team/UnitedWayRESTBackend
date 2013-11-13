package ch.furthermore.demo.st;

import com.csvreader.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class Importer {
	@Autowired
	private DataAccess dataAccess;
	
	interface CsvRowProcessor {
		public void process(Map<String,String> row);
	}
	
	public void parseCsv(BufferedReader in, CsvRowProcessor processor) throws IOException {
		CsvReader reader = new CsvReader(in, ';');
		reader.readHeaders();
		
		while (reader.readRecord()) {
			Map<String,String> row = new HashMap<String, String>();
			for (int i = 0; i < reader.getColumnCount(); i++) {
				row.put(reader.getHeader(i), reader.get(i));
			}
			processor.process(row);
		}
	}
	
	static LatLong[] fakeLatLongs = new LatLong[]{
		new LatLong((float)33.909460, (float)-118.366920),
		new LatLong((float)33.923635, (float)-118.352634),
		new LatLong((float)33.916426, (float)-118.335223),
		new LatLong((float)33.910183, (float)-118.345882),
		new LatLong((float)33.914289, (float)-118.358771),
		new LatLong((float)33.901887, (float)-118.343298),
		new LatLong((float)33.891231, (float)-118.378618),
	};  
	
	public void importData() throws IOException {
		final ZipToLatLong zipLookup = ZipToLatLong.getInstance();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("UnitedWay-Test+Data.csv")));
			new Importer().parseCsv(in, new CsvRowProcessor() { 
				@Override
				public void process(Map<String, String> row) {
					GeocodedResults r = new GeocodedResults();
					
					r.setAgencyAddress(row.get("agencyaddress"));
					r.setAgencyCity(row.get("agencycity"));
					r.setAgencyIdBit(Long.parseLong(row.get("Agencyid")));
					r.setAgencyName(row.get("AgencyName"));
					r.setAgencyState(row.get("agencystate"));
					r.setAgencyZip(Integer.parseInt(row.get("agencyzip")));
					r.setCategory(row.get("category"));
					r.setDescription(row.get("Description"));
					r.setEmail(row.get("email"));
					r.setPhone(row.get("phone"));
					r.setNeedId(Long.parseLong(row.get("needid")));
					
					//very dirty hack below to support opportunity locations which are different than organzation locations
					//"90250","Hawthorne","CA","33.914614","-118.35092","-8","1"
					LatLong latLong;
					if (r.getAgencyZip() == 90250) {
						latLong = fakeLatLongs[(int)(r.getNeedId() % fakeLatLongs.length)];
						
						System.out.println("Added fake lat/long");
					}
					else {
						//end of dirty hack
						latLong = zipLookup.getLatLongForZip((long)r.getAgencyZip());
					}
					
					if (latLong == null) {
						System.out.println("Zipcode not found for: " + row); //DEBUG
					}
					else {
						r.setLatitude(latLong.getLatitude()); 
						r.setLongitude(latLong.getLongitude());
					}
						
					r.setNeedTitle(row.get("NeedTitle"));
						
					dataAccess.insertgetOpportunity(r);
						
					System.out.println(row); //TODO remove DEBUG code
				}
			});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public void   ImportDonationData ()
    {
        dataAccess.importDonationData();

    }
}
