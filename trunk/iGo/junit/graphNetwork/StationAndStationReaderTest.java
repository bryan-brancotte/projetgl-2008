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

public class StationAndStationReaderTest {

	protected GraphNetworkBuilder graph;
	protected Station stationA;
	protected Station stationB;
	protected Station stationC;
	protected Route routeA;
	protected Route routeB;
	protected Inter interZ;
	protected Inter interY;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {

		graph = new GraphNetwork();
		try {
			stationA = new Station(1, "stationA");
			graph.addStation(stationA);
			stationB = new Station(2, "stationB");
			graph.addStation(stationB);
			stationC = new Station(3, "stationC");
			graph.addStation(stationC);
			routeA = graph.addRoute("A", "Tram");
			routeB = graph.addRoute("B", "TGV");
			stationA.addRoute(routeA);
			stationB.addRoute(routeB);
			stationC.addRoute(routeB);
			routeA.addStation(stationA);
			routeB.addStation(stationB);
			routeB.addStation(stationC);
			interY = new Inter(routeA, stationA, routeB, stationB, 1.23F, 412, false, false);
			interZ = new Inter(routeB, stationC, routeB, stationB, 0F, 412, false, false);
			stationA.addInter(interY);
			stationB.addInter(interY);
			stationB.addInter(interZ);
			stationC.addInter(interZ);
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
		return new JUnit4TestAdapter(StationAndStationReaderTest.class);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationInterCorrect() {
		assertTrue(stationB.getInter().size() == stationB.getInterR().length);
		assertTrue(stationB.getInter().size() == 2);
		assertTrue((stationB.getInterR()[0].getOtherStationR(stationB).getId() == stationC.getId())
				&& (stationB.getInterR()[1].getOtherStationR(stationB).getId() == stationA.getId())
				|| (stationB.getInterR()[1].getOtherStationR(stationB).getId() == stationC.getId())
				&& (stationB.getInterR()[0].getOtherStationR(stationB).getId() == stationA.getId()));
	}

	/**
	 * 
	 */
	@Test
	public void initialisationRouteCorrect() {
		assertTrue(stationB.getRoutes().size() == stationB.getRoutesR().length);
		assertTrue(stationB.getRoutes().size() == 2);
		assertTrue(stationA.getRoutes().size() == stationA.getRoutesR().length);
		assertTrue(stationA.getRoutes().size() == 1);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationIdAndNameCorrect() {
		assertTrue(stationA.getId() == 1);
		assertTrue(stationA.getName().compareTo("stationA") == 0);
		assertTrue(stationB.getId() == 2);
		assertTrue(stationB.getName().compareTo("stationB") == 0);
	}

	@Test
	public void enableDisable() {
		stationA.setEnable(false);
		assertTrue(!routeA.isStationEnable(stationA.getId()));
		stationA.setEnable(true);
		assertTrue(routeA.isStationEnable(stationA.getId()));

		stationB.setEnable(false);
		assertTrue(!routeA.isStationEnable(stationB.getId()));
		stationB.setEnable(true);
		assertTrue(routeA.isStationEnable(stationB.getId()));
	}
}
