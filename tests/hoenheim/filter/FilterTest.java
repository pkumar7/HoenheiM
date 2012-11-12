package hoenheim.filter;

import static org.junit.Assert.*;
import java.util.Random;

import org.junit.Test;

public class FilterTest extends Filter {

	@Test
	public void testFilterUsername() {
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterUsername("<>asdf~!@@#@#%^&*()_=;:'\"''+-/?.,~`\\///asdf234"), "asdfasdf234");
	}
	
	@Test
	public void testAlphabetsOnlyFilter()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.alphabetsOnlyFilter("<>asdf~!@@#@#%^&*()_=;:'\"''+-/?.,~`\\///asdf234"), "asdfasdf");
		assertEquals(filterInstance.alphabetsOnlyFilter("234234234"), "");
		assertEquals(filterInstance.alphabetsOnlyFilter("!#@$%!#$%!#$%<>;"), "");
		assertEquals(filterInstance.alphabetsOnlyFilter("asdf asdf"), "asdfasdf");
	}
	
	@Test
	public void testFilterPhoneNumber()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterPhonenumber("+999111"), "+999111");
		assertEquals(filterInstance.filterPhonenumber("<script>999onenumber</script>"), "999");
	}
	
	@Test
	public void testFilterAddress() {
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterAddress("<>Some~!@@ #@ran#d%o^&m*()_ =;A:'d\"d''r+-/?.e,~`s\\//s/-f234"), "Some random A/dd//r-/.e,s//s/-f234");
	}

	@Test
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
	
	@Test
	public void testAlphanumericsOnlyFilter()
	{
		// improve the test method, probably by generating random values and
		// comparing it with the alphanumeric regex.
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.alphanumericsOnlyFilter("<>asdf~!@@#@#%^&*()_=;:'\"''+-/?.,~`\\///asdf234"), "asdfasdf234");
		assertEquals(filterInstance.alphanumericsOnlyFilter("234234234"), "234234234");
		assertEquals(filterInstance.alphanumericsOnlyFilter("!#@$%!#$%!#$%<>;"), "");
		assertEquals(filterInstance.alphanumericsOnlyFilter("asdf asdf 1234"), "asdfasdf1234");
	}
	
	// CSS related method tests
	@Test
	public void testFilterForCSSValues()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterForCSSValues("\\<>{}"), "");
	}
	
	@Test
	public void testhtmlToPlaintext()
	{
		//Test is getting failed right now.
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.htmlToPlaintext("<script>v&al>ue<script>"), "v&amp;al&gt;ue");
	}
	
	@Test
	public void testUrlFilter()
	{
		Filter filterInstance = new Filter();
		// when everything is given properly in url
		String url = "https://hostname.com/somepath/somemorepath?query#fragid";
		String expected_url = "https://hostname.com/somepath/somemorepath?query#fragid";
		assertEquals(filterInstance.filterUrl(url), expected_url);
		// when scheme is not recognised
		url = "httpss://hostname.com/somepath/somemorepath?query#fragid";
		expected_url = "";
		assertEquals(filterInstance.filterUrl(url), expected_url);
		// when scheme is not followed by "//"
		url = "https:/#hostname.com/somepath/somemorepath?query#fragid";
		expected_url = "";
		assertEquals(filterInstance.filterUrl(url), expected_url);
		//url with undesired values 
		url = "https://hostna*)(<>me.com/some$^path/som!,.emorepath?que<>ry#fragid";
		expected_url = "https://hostname.com/somepath/somemorepath?query#fragid";
		assertEquals(filterInstance.filterUrl(url), expected_url);
		// when any authority delimiters occur inside host name 
		url = "https://host?name.com/somepath/somemorepath?query#fragid";
		expected_url = "https://host?name.comsomepathsomemorepathquery#fragid";
		assertEquals(filterInstance.filterUrl(url), expected_url);
		// need to identify values valid for fragment identifier.
		// using just alphanumerics to be on the safe side.
		url = "https://host#name.com/somepath/somemorepath?query#fragid";
		expected_url = "https://host#namecomsomepathsomemorepathqueryfragid";
		assertEquals(filterInstance.filterUrl(url), expected_url);
	}

	@Test
	public void testFilterForStyleParamater()
	{
		//test failing
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterForStyleParamater("/\\$%&@{1234}val"), "$%&@1234val");
	}

	@Test
	public void testFilterCSSAttributes()
	{
		Filter filterInstance = new Filter();
		assertEquals(filterInstance.filterCSSAttributes("some@#$%/\\/junk./&-=+value"), "somejunkvalue");
	}
	
}
