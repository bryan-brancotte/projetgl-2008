package graphNetworkOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphNetworkBuilderRobustesse {
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GraphNetworkBuilderRobustesse.class);
	}

	/**
	 * Test de refus d'ajout d'une route avec id null
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test
	public void addRoute1() throws ViolationOfUnicityInIdentificationException, NullPointerException {
		assertTrue(null == bob.addRoute(null, "ee"));
	}

	/**
	 * Test de refus d'ajout d'une route avec kind null
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test
	public void addRoute2() throws ViolationOfUnicityInIdentificationException, NullPointerException {
		assertTrue(null == bob.addRoute("ee", null));
	}

	/**
	 * Test de refus d'ajout de 2 Route avec le même id
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test(expected = ViolationOfUnicityInIdentificationException.class)
	public void addRoute3() throws ViolationOfUnicityInIdentificationException, NullPointerException {
		bob.addRoute("ee", "r");
		bob.addRoute("ee", "r");
	}

	/**
	 * Test de refus d'ajout de 2 Service avec le même id
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test(expected = ViolationOfUnicityInIdentificationException.class)
	public void addService1() throws ViolationOfUnicityInIdentificationException {
		bob.addService(-1, "name");
		bob.addService(-1, "tt");
	}

	/**
	 * Test de refus d'ajout d'un Service avec kind null
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test
	public void addService2() throws NullPointerException, ViolationOfUnicityInIdentificationException {
		assertTrue(bob.addStation(1, null) == null);
	}

	/**
	 * Test d'innactivité en cas d'ajout d'un Service avec nom null
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test
	public void addStation1() throws ViolationOfUnicityInIdentificationException, NullPointerException {
		assertTrue(bob.addStation(-1, null) == null);
	}

	/**
	 * Test de refus d'ajout de 2 Station avec le même id
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 */
	@Test(expected = ViolationOfUnicityInIdentificationException.class)
	public void addStation2() throws ViolationOfUnicityInIdentificationException {
		bob.addStation(-1, "f");
		bob.addStation(-1, "e");
	}

	/**
	 * Test de d'innactivité en cas d'ajout de station sur une route avec l'un des deux voir les deux à null
	 * 
	 * @throws ImpossibleValueException
	 */
	@Test
	public void addStationToRoute1() throws ViolationOfUnicityInIdentificationException, ImpossibleValueException {
		bob.addStation(1, "t");
		bob.addRoute("RerA", "rere");
		bob.addStationToRoute(null, sncf.getStation(1), 3);
		bob.addStationToRoute(sncf.getRoute("RerA"), null, 3);
		bob.addStationToRoute(null, null, 3);
		assertTrue("Aucune ajout de station n'aurait dû être fait sur la route RerA", !sncf.getRoute("RerA")
				.getStations().hasNext());
		assertTrue("Aucune ajout de route n'aurait dû être fait sur la station 1", !sncf.getStation(1).getRoutes()
				.hasNext());
		bob.addStationToRoute(sncf.getRoute("RerA"), sncf.getStation(1), 0);
		assertTrue(true);
	}

	/**
	 * Test de refus d'ajout avec un temps négatif
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 */
	@Test(expected = ImpossibleValueException.class)
	public void addStationToRoute2() throws ImpossibleValueException, ViolationOfUnicityInIdentificationException {
		bob.addStation(1, "t");
		bob.addRoute("RerA", "rere");
		bob.addStationToRoute(sncf.getRoute("RerA"), sncf.getStation(1), -3);
	}

	/**
	 * Test de d'innactivité en cas d'ajout de service sur une station avec l'un des deux voir les deux à null
	 */
	@Test
	public void addServiceToStation1() throws ViolationOfUnicityInIdentificationException {
		bob.addStation(1, "t");
		bob.addService(1, "rr");
		bob.addServiceToStation(null, null);
		bob.addServiceToStation(sncf.getStation(1), null);
		bob.addServiceToStation(null, sncf.getService(1));
		assertTrue("Aucune ajout de service n'aurait dû être fait sur la station 1", !sncf.getStation(1).getServices()
				.hasNext());

	}
}
