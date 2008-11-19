package graphNetwork;

import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RouteAndRouteReaderTest {

	protected GraphNetworkBuilder graph;
	protected Route routeA;
	protected Station stationA;
	protected Station stationB;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetwork();
		graph.addKind("TGV");
		routeA = new Route("routeA", graph.getKindFromString("TGV"));// , );
		KindRoute.addKind("Tram");
		routeA.setKindRoute(KindRoute.getKindFromString("Tram"));
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RouteAndRouteReaderTest.class);
	}

	/**
	 * vérifie que le constrcuteur marche, et mémorise bien les paramètres fournit.
	 */
	@Test
	public void initialisationCorrect() {
		assertTrue(routeA.getId().compareTo("routeA") == 0);
		String s = Math.random() * Integer.MAX_VALUE + "";
		graph.addKind("kind" + s);
		routeA = new Route(s, graph.getKindFromString("kind" + s));
		stationA = new Station(43,"Station A");
		stationB = new Station(32,"Station B");
		assertTrue(routeA.getId().compareTo(s) == 0);
		assertTrue(routeA.getKindRoute().getKindOf().compareTo("kind" + s) == 0);
		assertTrue(routeA.getKindRouteR().getKindOf().compareTo("kind" + s) == 0);
	}

	/**
	 * Vérifie que le constructeur refuse de marche si on lui fournit une valeur null
	 */
	@Test(expected = NullPointerException.class)
	public void initilisationIncorrect1() {
		routeA = new Route(null, graph.getKindFromString("TGV"));
	}

	/**
	 * Vérifie que le constructeur refuse de marche si on lui fournit une valeur null
	 */
	@Test(expected = NullPointerException.class)
	public void initilisationIncorrect2() {
		routeA = new Route("toto", null);
	}

	/**
	 * Vérifie que le constructeur refuse de marche si on lui fournit une valeur null
	 */
	@Test(expected = NullPointerException.class)
	public void initilisationIncorrect3() {
		routeA = new Route(null, null);
	}
	
	/**
	 * Vérification de l'ajout de station
	 */
	@Test
	public void accesseurTest(){
		assertTrue(routeA.getStations().size()==routeA.getStationsR().length);
		assertTrue(routeA.getStations().size()==0);
		routeA.addStation(stationA);
		assertTrue(routeA.getStations().size()==routeA.getStationsR().length);
		assertTrue(routeA.getStations().size()==1);
		routeA.addStation(stationB);
		assertTrue(routeA.getStations().size()==routeA.getStationsR().length);
		assertTrue(routeA.getStations().size()==2);
		//TODO vérifier le comportement de addStation.
		routeA.addStation(stationA);
		assertTrue(routeA.getStations().size()==routeA.getStationsR().length);
		assertTrue(routeA.getStations().size()==2);
	}
	
	public void enableDisableStationTest(){
		routeA.addStation(stationA);
		routeA.addStation(stationB);
		assertTrue(routeA.isStationEnable(stationA.getId()));
		routeA.setStationEnable(stationA.getId(), false);
		assertTrue(!routeA.isStationEnable(stationA.getId()));
		routeA.setStationEnable(stationA.getId(), true);
		assertTrue(routeA.isStationEnable(stationA.getId()));

		assertTrue(routeA.isStationEnable(stationB.getId()));
		routeA.setStationEnable(stationB.getId(), false);
		assertTrue(!routeA.isStationEnable(stationB.getId()));
		routeA.setStationEnable(stationB.getId(), true);
		assertTrue(routeA.isStationEnable(stationB.getId()));
		
		stationA.setEnable(false);
		assertTrue(!routeA.isStationEnable(stationA.getId()));
		stationA.setEnable(true);
		assertTrue(routeA.isStationEnable(stationA.getId()));
	}
}
