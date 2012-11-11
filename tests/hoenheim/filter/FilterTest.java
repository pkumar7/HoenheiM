package hoenheim.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilterTest extends Filter {

	@Test
	public void testFilterUsername() {
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterUsername("<>asdf~!@@#@#%^&*()_=;:'\"''+-/?.,~`\\///asdf234"), "asdfasdf234");
	}
	
	public void testAlphabetsOnlyFilter()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.alphabetsOnlyFilter("<>asdf~!@@#@#%^&*()_=;:'\"''+-/?.,~`\\///asdf234"), "asdfasdf");
		assertEquals(filterInstance.alphabetsOnlyFilter("234234234"), "");
		assertEquals(filterInstance.alphabetsOnlyFilter("!#@$%!#$%!#$%<>;"), "");
		assertEquals(filterInstance.alphabetsOnlyFilter("asdf asdf"), "asdfasdf");
	}
	
	public void testFilterPhoneNumber()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterPhonenumber("+999111"), "+999111");
		assertEquals(filterInstance.filterPhonenumber("<script>999onenumber</script>"), "999");
	}
	
	public void testContentDispositionHeaderFilter()
	{
		// tests for Latin strings
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf0asdf", true), "asdfasdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf\\asdf", true), "asdfasdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf;asdf", true), "asdfasdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf\"asdf", true), "asdfasdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf'asdf", true), "asdfasdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf-asdf", true), "asdf-asdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf_asdf", true), "asdf_asdf");
		assertEquals(filterInstance.contentDispositionHeaderFilter("asdf.asdf", true), "asdf.asdf");
		
		// tests for non-Latin strings
		assertEquals(filterInstance.contentDispositionHeaderFilter("我们", false), "我们");
	}
	
	
	// CSS related method tests
	public void testFilterForCSSValues()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterForCSSValues("\\<>{}"), "");
	}
	
}
