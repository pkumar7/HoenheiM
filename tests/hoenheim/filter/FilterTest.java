package hoenheim.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilterTest extends Filter {

	@Test
	public void testFilterUsername() {
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterUsername("<>asdfasdf234"), "asdfasdf234");
	}
}
