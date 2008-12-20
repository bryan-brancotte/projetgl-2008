package algo;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

public class AlgoTest {

	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;


	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AlgoTest.class);
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
	}
	
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
			bob.addStationToRoute(rerB, massyPal = bob.addStation(10,
					"Massy Palaiseau"), 10);
			bob.addStationToRoute(rerB, bob.addStation(4, "Le Guichet"), 9);
			bob.addStationToRoute(rerB, bob.addStation(5, "Orsay Ville"), 1);

			bob.addStationToRoute(rerC, bob.addStation(9, "Paris Austerlitz"),
					0);
			bob.addStationToRoute(rerC, bob.addStation(6, "Orly"), 15);
			bob.addStationToRoute(rerC, bob.addStation(7, "Choisy"), 15);
			bob.addStationToRoute(rerC, bob.addStation(8, "Juvisy"), 3);
			bob.addStationToRoute(rerC, massyPal, 25);

			bob.defineEntryCost(sncf.getKindFromString("RER"), 4);

			bob.linkStation(rerC, massyPal, rerB, massyPal, 0, 3, false);
			bob.linkStation(rerC, sncf.getStation(9), rerB, sncf.getStation(1),
					2, 9, true);

			bob.addServiceToStation(sncf.getStation(9), bob.addService(1,
					"Journaux"));
			bob.addServiceToStation(sncf.getStation(9), bob.addService(2,
					"Cafe"));
			bob.addServiceToStation(sncf.getStation(9), bob.addService(3,
					"Handi"));
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
			assertTrue(
					"Une station ne semble pas avoir été mise sur la ligne, or on l'a demandé",
					false);
		} catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		}
		assertTrue("Construction sans problème", true);
	}
}
