package ch.furthermore.demo.st.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import ch.furthermore.demo.st.LatLong;
import ch.furthermore.demo.st.rest.model.Category;
import ch.furthermore.demo.st.rest.model.Location;
import ch.furthermore.demo.st.rest.model.Opportunity;

public class VolunteerServiceTest {

	@Test
	public void testSortDistancePrefferedCategoryPrio1CloseProximityPrioriat2Sort() {
		VolunteerService vs = new VolunteerService();
		Opportunity op = loc(0.12f, 0.13f, "catA");
		Opportunity op2 = loc(0.12f, 0.35f, "catA");
		Opportunity op3 = loc(0.12f, 0.24f, "catB");
		Opportunity op4 = loc(0.12f, 0.13f, "catB");
		Opportunity op5 = loc(0.12f, 0.35f, "catB");
		Opportunity op6 = loc(0.12f, 0.24f, "catA");
		List<Opportunity> ops = Arrays.asList(op, op2, op3, op4, op5, op6);
		vs.sortDistance(0.12f, 0.13f, Arrays.asList("catB"), ops);  
		
		assertEquals(op4, ops.get(0));
		assertEquals(op3, ops.get(1));
		assertEquals(op5, ops.get(2));
	}
	
	@Test
	public void testSortPrefferedCategoryFirst() {
		final VolunteerService vs = new VolunteerService();
		Opportunity op = loc(0.12f, 0.13f, "catA");
		Opportunity op2 = loc(0.12f, 0.35f, "catB");
		Opportunity op3 = loc(0.12f, 0.24f, "catA");
		List<Opportunity> ops = Arrays.asList(op, op2, op3);
		
		Collections.sort(ops, new Comparator<Opportunity>() {
			@Override
			public int compare(Opportunity o1, Opportunity o2) {
				return vs.comparePreferredCategory(Arrays.asList("catA"), o1, o2);
			}
		});
		
		assertEquals("catA", ops.get(0).getCategory().getName());
		assertEquals("catA", ops.get(1).getCategory().getName());
		assertEquals("catB", ops.get(2).getCategory().getName());
	}
	
	@Test
	public void testSortCloseProximityFirst() {
		final VolunteerService vs = new VolunteerService();
		Opportunity op = loc(0.12f, 0.13f, "catA");
		Opportunity op2 = loc(0.12f, 0.35f, "catA");
		Opportunity op3 = loc(0.12f, 0.24f, "catB");
		List<Opportunity> ops = Arrays.asList(op, op2, op3);
		
		Collections.sort(ops, new Comparator<Opportunity>() {
			@Override
			public int compare(Opportunity o1, Opportunity o2) {
				return vs.compareDistance(new LatLong(0.12f, 0.13f) , o1, o2);
			}
		});
		
		assertEquals(op, ops.get(0));
		assertEquals(op3, ops.get(1));
		assertEquals(op2, ops.get(2));
	}

	private Opportunity loc(float latitude, float longitude, String category) {
		Opportunity op = new Opportunity();
		Location loc = new Location();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		op.setLocation(loc);
		Category cat = new Category();
		cat.setName(category);
		op.setCategory(cat);
		return op;
	}
}
