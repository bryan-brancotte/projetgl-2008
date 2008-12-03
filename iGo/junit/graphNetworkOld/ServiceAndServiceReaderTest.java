package graphNetworkOld;

import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServiceAndServiceReaderTest {

	protected GraphNetworkBuilderTest2 graph;
	protected Service serviceCoffe;
	protected Service serviceNews;


	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ServiceAndServiceReaderTest.class);
	}
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetworkBuilderTest2();
		serviceCoffe = new Service("Coffe", 3);
		serviceNews = new Service("News", 4);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationCorrect() {
		assertTrue(serviceCoffe.getShortDescription().compareTo("Coffe") == 0);
		assertTrue(serviceCoffe.getShortDescription().compareTo("coffe") != 0);
		assertTrue(serviceCoffe.getId() == 3);
		assertTrue(serviceNews.getShortDescription().compareTo("News") == 0);
		assertTrue(serviceNews.getShortDescription().compareTo("NewS") != 0);
		assertTrue(serviceNews.getId() == 4);
	}

	@Test
	public void getterAndSetterTest() {
		assertTrue(serviceCoffe.getShortDescription().compareTo("Coffe") == 0);
		serviceCoffe.setShortDescription("totati");
		assertTrue(serviceCoffe.getShortDescription().compareTo("Coffe") != 0);
		assertTrue(serviceCoffe.getShortDescription().compareTo("totati") == 0);
		serviceCoffe.setShortDescription("coffe");
		assertTrue(serviceCoffe.getShortDescription().compareTo("coffe") == 0);

		assertTrue(serviceCoffe.getId() == 3);
		serviceCoffe.setId(32);
		assertTrue(serviceCoffe.getId() == 32);
		serviceCoffe.setId(-532);
		assertTrue(serviceCoffe.getId() == -532);
		serviceCoffe.setId(0);
		assertTrue(serviceCoffe.getId() == 0);
	}
	
	
}
