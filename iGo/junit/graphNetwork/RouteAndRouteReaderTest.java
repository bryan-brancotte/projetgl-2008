package graphNetwork;

import static org.junit.Assert.assertTrue;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RouteAndRouteReaderTest {

	protected GraphNetworkBuilder graph;
	protected Route routeA;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetwork();
		routeA = new Route("routeA");//, );
		KindRoute.addKind("Tram");
		routeA.setKindRoute(KindRoute.getKindFromString("Tram"));
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RouteAndRouteReaderTest.class);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationCorrect() {
		routeA.getId().compareTo("routeA");
	}
}
