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
		sncf = bob.getCurrentGraphNetwork();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GraphNetworkRobustesse.class);
	}

	@Test
	public void getJunction() throws ViolationOfUnicityInIdentificationException {
		assertTrue("Doit retourner null car une des stations est null", sncf.getJunctions(null, null) == null);
		bob.addStation(1, "e");
		assertTrue("Doit retourner null car une des stations est null",
				sncf.getJunctions(sncf.getStation(1), null) == null);
		assertTrue("Doit retourner null car une des stations est null",
				sncf.getJunctions(null, sncf.getStation(1)) == null);
		bob.addStation(2, "e");
		assertTrue("Doit retourner un iterateur sans element car il n'y a aucun lien entre les stations", !(sncf
				.getJunctions(sncf.getStation(2), sncf.getStation(1)).hasNext()));
	}

	@Test
	public void getEntryCost() {
		assertTrue("Doit retourner Float.Nan car une le kind est null", Float.isNaN(sncf.getEntryCost(null)));
	}

	@Test
	public void getInstancePathInGraphBuilder() {
		assertTrue("On retourne forcement un GCB", sncf.getInstancePathInGraphCollectionBuilder() != null);
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
