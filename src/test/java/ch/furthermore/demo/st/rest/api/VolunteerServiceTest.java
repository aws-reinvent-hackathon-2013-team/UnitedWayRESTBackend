package ch.furthermore.demo.st.rest.api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ch.furthermore.demo.st.rest.model.Location;
import ch.furthermore.demo.st.rest.model.Opportunity;

public class VolunteerServiceTest {

	@Test
	public void testSortDistance() {
		VolunteerService vs = new VolunteerService();
		Opportunity op = loc(0.12f, 0.13f);
		Opportunity op2 = loc(0.12f, 0.35f);
		Opportunity op3 = loc(0.12f, 0.24f);
		List<Opportunity> ops = Arrays.asList(op, op2, op3);
		vs.sortDistance(0.12f, 0.13f, ops);  
		
		assertEquals(op, ops.get(0));
		assertEquals(op3, ops.get(1));
		assertEquals(op2, ops.get(2));
	}

	private Opportunity loc(float latitude, float longitude) {
		Opportunity op = new Opportunity();
		Location loc = new Location();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		op.setLocation(loc);
		return op;
	}
}
