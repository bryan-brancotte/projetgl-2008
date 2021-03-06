package pathInGraphOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PIGResultBuilderRobustesse {
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;
	protected PathInGraphCollectionBuilder pigColB;
	protected PathInGraph pig;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		constructionDUnReseauSansProbleme();
		pigColB = sncf.getInstancePathInGraphCollectionBuilder();
		pig = pigColB.getPathInGraph();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(PIGResultBuilderRobustesse.class);
	}

	/**
	 * Construction du réseau sans lever d'exception
	 */
	@Test
	public void constructionDUnReseauSansProbleme() {
		bob.reset();
		Route rerB;
		Station massyPal;
		Route rerC;
		try {
			rerB = bob.addRoute("RerB", "RER");
			rerC = bob.addRoute("RerC", "RER");
			bob.addRoute("Atlantique", "TGV");
			bob.addStationToRoute(rerB, bob.addStation(1, "Paris"), 0);
			bob.addStationToRoute(rerB, bob.addStation(2, "Antony"), 12);
			bob.addStationToRoute(rerB, bob.addStation(3, "Croix de berny"), 1);
			bob.addStationToRoute(rerB, massyPal = bob.addStation(10, "Massy Palaiseau"), 10);
			bob.addStationToRoute(rerB, bob.addStation(4, "Le Guichet"), 9);
			bob.addStationToRoute(rerB, bob.addStation(5, "Orsay Ville"), 1);

			bob.addStationToRoute(rerC, bob.addStation(9, "Paris Austerlitz"), 0);
			bob.addStationToRoute(rerC, bob.addStation(6, "Orly"), 15);
			bob.addStationToRoute(rerC, bob.addStation(7, "Choisy"), 15);
			bob.addStationToRoute(rerC, bob.addStation(8, "Juvisy"), 3);
			bob.addStationToRoute(rerC, massyPal, 25);

			bob.defineEntryCost(sncf.getKindFromString("RER"), 4);

			bob.linkStation(rerC, massyPal, rerB, massyPal, 0, 3, false);
			bob.linkStation(rerC, sncf.getStation(9), rerB, sncf.getStation(1), 2, 9, true);

			bob.addServiceToStation(sncf.getStation(9), bob.addService(1, "Journaux"));
			bob.addServiceToStation(sncf.getStation(9), bob.addService(2, "Cafe"));
			bob.addServiceToStation(sncf.getStation(9), bob.addService(3, "Handi"));
			bob.addServiceToStation(sncf.getStation(2), sncf.getService(3));
			bob.addServiceToStation(sncf.getStation(10), sncf.getService(3));
			bob.addServiceToStation(sncf.getStation(4), sncf.getService(3));
			bob.addServiceToStation(sncf.getStation(5), sncf.getService(3));
			bob.addServiceToStation(sncf.getStation(1), sncf.getService(1));
			bob.addServiceToStation(sncf.getStation(8), sncf.getService(3));
		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		} catch (MissingResourceException e) {
			assertTrue("un objet est null dans linkstation", false);
		} catch (StationNotOnRoadException e) {
			assertTrue("Une station ne semble pas avoir été mise sur la ligne, or on l'a demandé", false);
		} catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		}
		assertTrue("Construction sans problème", true);
	}

	@Test
	public void getPathInGraph() {
		assertTrue("le graph actuelle de PIGB doit être non null", pig != null);
	}

	@Test
	public void getInstancePathInGraphBuilder() {
		assertTrue("le graph actuelle de PIGB doit être non null", pig != null);
		assertTrue("Le graph du pathInGraph doit être le graph du GNB c'est à dire ici sncf", pigColB.getPathInGraph()
				.getGraph() == sncf);
	}

	@Test
	public void addFront() {
		try {
			pigColB.getPathInGraphResultBuilder().addFront(null);
			assertTrue("LE chemin doit être vide : on a ajouté un null", pig.getJunctions().hasNext());
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void addLast() {
		try {
			pigColB.getPathInGraphResultBuilder().addLast(null);
			assertTrue("LE chemin doit être vide : on a ajouté un null", pig.getJunctions().hasNext());
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void equals() {
		try {
			assertTrue("pigColB n'est pas null, sinon ca aurait planter", !pigColB.equals(null));
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void importPath() {
		try {
			pigColB.getPathInGraphResultBuilder().addFront(
					bob.linkStation(sncf.getRoute("RerB"), sncf.getStation(1), sncf.getRoute("RerC"), sncf
							.getStation(9), 2, 20, true));
			pigColB.getPathInGraphConstraintBuilder().importPath((String)null);
			assertTrue("Le path actuelle doit maintenant être vide", pigColB.getPathInGraph().getJunctions().hasNext());
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}
}