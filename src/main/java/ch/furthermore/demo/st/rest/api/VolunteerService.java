package ch.furthermore.demo.st.rest.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ch.furthermore.demo.st.rest.model.Category;

public class VolunteerService {
	
	private static Map<String,Category> categories = new HashMap<String,Category>();
	
	static {
		categories.put( "INCOME", new Category().setId("INCOME").setName("Income") );
		categories.put( "BASIC_NEEDS", new Category().setId("BASIC_NEEDS").setName("Basic Needs") );
		categories.put( "EDUCATION", new Category().setId("EDUCATION").setName("Education") );
	}

	public static Collection<Category> getCategories() {
		return categories.values();
	}
	
	public static Category getCategory( String id ) {
		return categories.get( id );
	}
}
