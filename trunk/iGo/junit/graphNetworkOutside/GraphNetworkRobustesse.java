package graphNetworkOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphNetworkRobustesse {
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getActualGraphNetwork ();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GraphNetworkRobustesse.class);
	}

	@Test
	public void getJunction() throws ViolationOfUnicityInIdentificationException {
		assertTrue("Doit retourné null car une des stations est null", sncf.getJunctions(null, null) == null);
		bob.addService(1, "e");
		assertTrue("Doit retourné null car une des stations est null",
				sncf.getJunctions(sncf.getStation(1), null) == null);
		assertTrue("Doit retourné null car une des stations est null",
				sncf.getJunctions(null, sncf.getStation(1)) == null);
		bob.addService(2, "e");
		assertTrue("Doit retourné un iterateur sans element car aucun lien entre les stations", !sncf.getJunctions(
				sncf.getStation(2), sncf.getStation(1)).hasNext());
	}

	@Test
	public void getEntryCost() {
		assertTrue("Doit retourné Float.Nan car une le kind est null", sncf.getEntryCost(null) == Float.NaN);
	}

	@Test
	public void getInstancePathInGraphBuilder() {
		assertTrue("On ne peut construire un PathInGraphBuilder pour un path null", sncf
				.getInstancePathInGraphBuilder(null) == null);
	}

	@Test
	public void getKindFromString() {
		assertTrue("le kindString est null, le retour doit aussi l'être", null == sncf.getKindFromString(null));
	}

	@Test
	public void getRoute() {
		assertTrue("le nom est null, le retour doit l'être", null == sncf.getRoute(null));
		assertTrue("le nom de Route est inconnu, le retour doit être null", null == sncf.getRoute("ee"));
	}

	@Test
	public void getService() {
		assertTrue("le num de Service est incounn, le retour doit être null", null == sncf.getService(-23));
	}

	@Test
	public void getStation() {
		assertTrue("le num de Station est incounn, le retour doit être null", null == sncf.getStation(-42));
	}

	@Test
	public void getKinds() {
		assertTrue("l'iterateur doit être vide", !sncf.getKinds().hasNext());
	}

	@Test
	public void getRoutes() {
		assertTrue("l'iterateur doit être vide", !sncf.getRoutes().hasNext());
	}

	@Test
	public void getServices() {
		assertTrue("l'iterateur doit être vide", !sncf.getServices().hasNext());
	}

	@Test
	public void getStations() {
		assertTrue("l'iterateur doit être vide", !sncf.getStations().hasNext());
	}
}
