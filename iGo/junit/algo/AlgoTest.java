package algo;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo;

import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;

public class AlgoTest {

	protected GraphNetworkBuilder gnb;
	protected GraphNetwork sncf;
	protected PathInGraph pig;
	protected Algo bob;


	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AlgoTest.class);
	}

	@Before
	public void constructionDUnReseauSansProbleme() {
		gnb = new GraphNetworkBuilder();
		sncf = gnb.getCurrentGraphNetwork();
		bob = new Dijkstra();
		gnb.reset();
		Route rerB;
		Station massyPal;
		Route rerC;
		try {
			rerB = gnb.addRoute("RerB", "RER");
			rerC = gnb.addRoute("RerC", "RER");
			gnb.addRoute("Atlantique", "TGV");
			gnb.addStationToRoute(rerB, gnb.addStation(1, "Paris"), 0);
			gnb.addStationToRoute(rerB, gnb.addStation(2, "Antony"), 12);
			gnb.addStationToRoute(rerB, gnb.addStation(3, "Croix de berny"), 1);
			gnb.addStationToRoute(rerB, massyPal = gnb.addStation(10,
					"Massy Palaiseau"), 10);
			gnb.addStationToRoute(rerB, gnb.addStation(4, "Le Guichet"), 9);
			gnb.addStationToRoute(rerB, gnb.addStation(5, "Orsay Ville"), 1);

			gnb.addStationToRoute(rerC, gnb.addStation(9, "Paris Austerlitz"),
					0);
			gnb.addStationToRoute(rerC, gnb.addStation(6, "Orly"), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(7, "Choisy"), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(8, "Juvisy"), 3);
			gnb.addStationToRoute(rerC, massyPal, 25);

			gnb.defineEntryCost(sncf.getKindFromString("RER"), 4);

			gnb.linkStation(rerC, massyPal, rerB, massyPal, 0, 3, false);
			gnb.linkStation(rerC, sncf.getStation(9), rerB, sncf.getStation(1),
					2, 9, true);

			gnb.addServiceToStation(sncf.getStation(9), gnb.addService(1,
					"Journaux"));
			gnb.addServiceToStation(sncf.getStation(9), gnb.addService(2,
					"Cafe"));
			gnb.addServiceToStation(sncf.getStation(9), gnb.addService(3,
					"Handi"));
			gnb.addServiceToStation(sncf.getStation(2), sncf.getService(3));
			gnb.addServiceToStation(sncf.getStation(10), sncf.getService(3));
			gnb.addServiceToStation(sncf.getStation(4), sncf.getService(3));
			gnb.addServiceToStation(sncf.getStation(5), sncf.getService(3));
			gnb.addServiceToStation(sncf.getStation(1), sncf.getService(1));
			gnb.addServiceToStation(sncf.getStation(8), sncf.getService(3));
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
	
	@Test
	public void CalculPlusCourtChemin() {
		
		
	}
	
}
