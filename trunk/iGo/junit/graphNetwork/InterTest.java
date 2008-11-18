package graphNetwork;

import static org.junit.Assert.assertTrue;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InterTest {

	protected GraphNetworkBuilder graph;

	protected Station stationA;
	protected Station stationB;
	protected Station stationC;
	protected Route routeA;
	protected Route routeB;
	protected Inter inter;

	@After
	public void epilogueDateTest() {
		stationA = null;
		stationB = null;
		stationC = null;
		routeA = null;
		routeB = null;
		graph = null;
		inter = null;
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetwork();
		try {
			stationA = graph.addStation(1, "stationA");
			stationB = graph.addStation(2, "stationB");
			stationC = graph.addStation(3, "stationC");
			routeA = graph.addRoute("A", "Tram");
			routeB = graph.addRoute("B", "TGV");
			graph.addStationToRoute(routeA, stationA, 0);
			graph.addStationToRoute(routeB, stationB, 0);
			inter = new Inter(stationA, routeA, stationB, routeB);
		} catch (ViolationOfUnicityInIdentificationException e) {
		}
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(InterTest.class);
	}

	/**
	 * Test de la méthode getOtherStation(...)
	 */
	@Test
	public void getOtherStationCorrect() {
		assertTrue(inter.getOtherStation(stationA) != null);
		assertTrue(inter.getOtherStation(stationB) != null);
		assertTrue(inter.getOtherStation(stationC) == null);
		assertTrue(inter.getOtherStation(stationB).getId() == stationA.getId());
		assertTrue(inter.getOtherStation(stationA).getId() == stationB.getId());
		assertTrue(inter.getOtherRoute(stationB).getId().compareTo(routeA.getId()) == 0);
		assertTrue(inter.getOtherRoute(stationA).getId().compareTo(routeB.getId()) == 0);
	}

	/**
	 * Test de la méthode getOtherRoute(...)
	 */
	@Test
	public void getOtherRouteCorrect() {
		assertTrue(inter.getOtherRoute(stationB).getId().compareTo(routeA.getId()) == 0);
		assertTrue(inter.getOtherRoute(stationA).getId().compareTo(routeB.getId()) == 0);
	}

	@Test
	public void setEnableAndisEnable() {
		assertTrue(inter.isEnable());
		inter.setEnable(false);
		assertTrue(!inter.isEnable());
		inter.setEnable(true);
		assertTrue(inter.isEnable());
	}

	@Test
	public void setPedestrianAndisPedestrian() {
		assertTrue(!inter.isPedestrian());
		inter.setPedestrian(true);
		assertTrue(inter.isPedestrian());
		inter.setPedestrian(false);
		assertTrue(!inter.isPedestrian());
	}

	@Test
	public void setRouteLinkAndisRouteLink() {
		assertTrue(!inter.isRouteLink());
		inter.setRouteLink(true);
		assertTrue(inter.isRouteLink());
		inter.setRouteLink(false);
		assertTrue(!inter.isRouteLink());
	}

	@Test
	public void setCostAndgetCost() {
		assertTrue(inter.getCost() == 0);
		inter.setCost(56.64F);
		assertTrue(inter.getCost() == 56.64F);
		inter.setCost(-0.786364F);
		assertTrue(inter.getCost() == -0.786364F);
	}

	@Test
	public void setTimeAndgetTime1() {
		assertTrue(inter.getTimeBetweenStations() == 0);
		inter.setTimeBetweenStations(43);
		assertTrue(inter.getTimeBetweenStations() == 43);
	}

	@Test(expected = NumberFormatException.class)
	public void setTimeAndgetTime2() {
		assertTrue(inter.getTimeBetweenStations() == 0);
		inter.setTimeBetweenStations(-43);
	}

	@Test
	public void getStationAB() {
		assertTrue((inter.getStationA().getId() == stationA.getId())
				&& (inter.getStationB().getId() == stationB.getId())
				|| (inter.getStationA().getId() == stationB.getId())
				&& (inter.getStationB().getId() == stationA.getId()));
	}

	@Test
	public void getRouteAB() {
		assertTrue((inter.getRouteA().getId().compareTo(routeA.getId()) == 0)
				&& (inter.getRouteB().getId().compareTo(routeB.getId()) == 0)
				|| (inter.getRouteA().getId().compareTo(routeB.getId()) == 0)
				&& (inter.getRouteB().getId().compareTo(routeA.getId()) == 0));
	}
}
