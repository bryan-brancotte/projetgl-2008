package graphNetwork;

import static org.junit.Assert.assertTrue;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.MissingResourceException;

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
			graph.addStationToRoute(routeB, stationC, 0);
			inter = new Inter(routeA, stationA, routeB, stationB, 1.23F, 412, false, false);
			// graph.linkStation(routeA, stationA, routeB, stationB, 1.23F, 12, false);
		} catch (ViolationOfUnicityInIdentificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StationNotOnRoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImpossibleValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(InterTest.class);
	}

	/**
	 * vérifier que l'objet refuse de s'initialiser si les station et route sont incorrects.
	 * 
	 * @throws StationNotOnRoadException
	 * @throws ImpossibleValueException
	 */
	@Test(expected = StationNotOnRoadException.class)
	public void initialisationIncorrect1() throws StationNotOnRoadException, ImpossibleValueException {
		new Inter(routeA, stationA, routeA, stationB, 0, 0, true, false);
	}

	/**
	 * vérifier que l'objet refuse de s'initialiser si le coût est incorrect.
	 * 
	 * @throws StationNotOnRoadException
	 * @throws ImpossibleValueException
	 */
	@Test(expected = ImpossibleValueException.class)
	public void initialisationIncorrect2() throws StationNotOnRoadException, ImpossibleValueException {
		new Inter(routeA, stationA, routeB, stationB, -1.5F, 0, true, false);
	}

	/**
	 * vérifier que l'objet refuse de s'initialiser si le temps est incorrect.
	 * 
	 * @throws StationNotOnRoadException
	 * @throws ImpossibleValueException
	 */
	@Test(expected = ImpossibleValueException.class)
	public void initialisationIncorrect3() throws StationNotOnRoadException, ImpossibleValueException {
		new Inter(routeA, stationA, routeB, stationB, 0, -1, true, false);
	}

	/**
	 * vérifier que l'objet s'initialise correctement.
	 * 
	 * @throws StationNotOnRoadException
	 * @throws ImpossibleValueException
	 */
	@Test
	public void initialisationCorrect2() throws StationNotOnRoadException, ImpossibleValueException {
		new Inter(routeB, stationC, routeB, stationB, 0, 0, true, false);
		assertTrue(inter.getRouteA() == routeB);
		assertTrue(inter.getRouteA() == inter.getRouteB());
		assertTrue((inter.getStationA() == stationC) && (inter.getStationB() == stationB));
		assertTrue(inter.getCost() == 1.23F);
		assertTrue(inter.getTimeBetweenStations() == 412);
		assertTrue(inter.isRouteLink() == true);
		assertTrue(inter.isPedestrian() == false);
		assertTrue(inter.isEnable() == true);
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
	public void setCostAndgetCost1() throws ImpossibleValueException {
		assertTrue(inter.getCost() ==  1.23F);
		inter.setCost(56.64F);
		assertTrue(inter.getCost() == 56.64F);
	}

	@Test(expected = ImpossibleValueException.class)
	public void setCostAndgetCost2() throws ImpossibleValueException {
		inter.setCost(-56.64F);
	}

	@Test
	public void setTimeAndgetTime1() throws ImpossibleValueException {
		assertTrue(inter.getTimeBetweenStations() == 412);
		inter.setTimeBetweenStations(43);
		assertTrue(inter.getTimeBetweenStations() == 43);
	}

	@Test(expected = ImpossibleValueException.class)
	public void setTimeAndgetTime2() throws ImpossibleValueException {
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
