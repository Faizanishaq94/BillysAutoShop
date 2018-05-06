import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class BillyTest {

	@Test
	public void TestBillyInput() {
		
		ArrayList<Appointment> appt = new ArrayList<Appointment>();
		
		appt.add(0, new Inspection(0, "Kyle", "11:00", "MON", true, "man1", "mod1"));
		assertEquals("Inspection -- Kyle | BLYAUTSHP1 | MON @ 11:00 | Oil Change - true | Vehicle: man1 mod1 | Cost - $100.0", appt.get(0).toString());
		
		appt.add(1, new Inspection(1, "Eric", "12:00", "TUES", false, "man2", "mod2"));
		assertEquals("Inspection -- Eric | BLYAUTSHP2 | TUES @ 12:00 | Oil Change - false | Vehicle: man2 mod2 | Cost - $100.0", appt.get(1).toString());
		
		appt.add(2, new Inspection(1, "Kenny", "13:00", "WED", true, "man3", "mod3"));
		assertEquals("Inspection -- Kenny | BLYAUTSHP3 | WED @ 13:00 | Oil Change - true | Vehicle: man3 mod3 | Cost - $150.0", appt.get(2).toString());
		
		appt.add(3, new Inspection(0, "Stan", "14:00", "THUR", false, "man4", "mod4"));
		assertEquals("Inspection -- Stan | BLYAUTSHP4 | THUR @ 14:00 | Oil Change - false | Vehicle: man4 mod4 | Cost - $50.0", appt.get(3).toString());
		
		appt.add(4, new Maintenance(4, 0, 4.0, "Mimsy", "15:00", "FRI", true, "man5", "mod5"));
		assertEquals("Maintenance -- Mimsy | BLYAUTSHP5 | FRI @ 15:00 | Oil Change - true | Vehicle: man5 mod5 | Cost - $450.0", appt.get(4).toString());
		
		appt.add(5, new Maintenance(5, 1, 5.0, "Timmeh", "16:00", "FRI", false, "man6", "mod6"));
		assertEquals("Maintenance -- Timmeh | BLYAUTSHP6 | FRI @ 16:00 | Oil Change - false | Vehicle: man6 mod6 | Cost - $750.0", appt.get(5).toString());
		
		appt.add(6, new Maintenance(6, 1, 6.0, "Jimmy", "17:00", "FRI", true, "man7", "mod7"));
		assertEquals("Maintenance -- Jimmy | BLYAUTSHP7 | FRI @ 17:00 | Oil Change - true | Vehicle: man7 mod7 | Cost - $950.0", appt.get(6).toString());
		
		appt.add(7, new Maintenance(7, 0, 7.0, "Nathan", "18:00", "FRI", false, "man8", "mod8"));
		assertEquals("Maintenance -- Nathan | BLYAUTSHP8 | FRI @ 18:00 | Oil Change - false | Vehicle: man8 mod8 | Cost - $700.0", appt.get(7).toString());
		
		
		/*String exp = "[Inspection -- Kyle | BLYAUTSHP1 | MON @ 11:00 | Oil Change - true | Vehicle: man1 mod1 | Cost - $100.0, "
				+ "Inspection -- Eric | BLYAUTSHP2 | TUES @ 12:00 | Oil Change - false | Vehicle: man2 mod2 | Cost - $100.0, "
				+ "Inspection -- Kenny | BLYAUTSHP3 | WED @ 13:00 | Oil Change - true | Vehicle: man3 mod3 | Cost - $150.0, "
				+ "Inspection -- Stan | BLYAUTSHP4 | THUR @ 14:00 | Oil Change - false | Vehicle: man4 mod4 | Cost - $50.0, "
				+ "Maintenance -- Mimsy | BLYAUTSHP5 | FRI @ 15:00 | Oil Change - true | Vehicle: man5 mod5 | Cost - $450.0, "
				+ "Maintenance -- Timmeh | BLYAUTSHP6 | FRI @ 16:00 | Oil Change - false | Vehicle: man6 mod6 | Cost - $750.0, "
				+ "Maintenance -- Jimmy | BLYAUTSHP7 | FRI @ 17:00 | Oil Change - true | Vehicle: man7 mod7 | Cost - $950.0, "
				+ "Maintenance -- Nathan | BLYAUTSHP8 | FRI @ 18:00 | Oil Change - false | Vehicle: man8 mod8 | Cost - $700.0]";*/
		
		//assertEquals(exp, appt.toString());
		//System.out.println(appt.toString());
		
	}
}
