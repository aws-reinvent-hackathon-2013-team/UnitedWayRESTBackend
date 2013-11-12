package ch.furthermore.demo.st;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csvreader.CsvReader;

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
					
					LatLong latLong = zipLookup.getLatLongForZip((long)r.getAgencyZip());
					r.setLatitude(latLong.getLatitude()); 
					r.setLongitude(latLong.getLongitude());
					
					r.setNeedId(Long.parseLong(row.get("needid")));
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
}
