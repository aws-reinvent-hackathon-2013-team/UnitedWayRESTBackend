package ch.furthermore.demo.st;

import java.util.Properties;

import com.amazonaws.regions.Regions;


public class Configuration {

	private Properties props = new Properties();
	private static Configuration singleton = null;
	
	public static interface Keys {
		public static final String DYNAMO_REGION = "dynamoRegion";
		public static final String DYNAMO_DBGEO_ACCESS_KEY = "dynamoDBGeoAccessKey";
		public static final String DYNAMO_DBGEO_SECRET_KEY = "dynamoDBGeoSecretKey";
	}
	
	private Configuration() {
		props.put( Keys.DYNAMO_REGION, System.getProperty(Keys.DYNAMO_REGION,Regions.US_WEST_2.getName()) );
		props.put( Keys.DYNAMO_DBGEO_ACCESS_KEY, System.getProperty(Keys.DYNAMO_DBGEO_ACCESS_KEY,"AKIAJBHDBH4JIW5R5KEQ" ) );
		props.put( Keys.DYNAMO_DBGEO_SECRET_KEY, System.getProperty(Keys.DYNAMO_DBGEO_SECRET_KEY,"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" ) );
	}
	
	public static Configuration instance() {
		if( singleton == null ) {
			singleton = new Configuration();
		}
		return singleton;
	}
	
	public String getProperty( String key ) {
		return props.getProperty( key );
	}
	
	public String getProperty( String key, String defaultValue ) {
		return props.getProperty( key, defaultValue );
	}
}
