package algo;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo;
import iGoMaster.Algo.CriteriousForLowerPath;

import java.util.Iterator;
import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;

public class AlgoTest {

	protected GraphNetworkBuilder gnb;
	protected GraphNetwork sncf;
	protected PathInGraphResultBuilder pig;
	protected Algo bob;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AlgoTest.class);
	}

	@Before
	public void constructionDUnReseauSansProbleme() {
		gnb = new GraphNetworkBuilder();
		sncf = gnb.getCurrentGraphNetwork();
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
			gnb.addStationToRoute(rerB, massyPal = gnb.addStation(10,"Massy Palaiseau"), 10);
			gnb.addStationToRoute(rerB, gnb.addStation(4, "Le Guichet"), 9);
			gnb.addStationToRoute(rerB, gnb.addStation(5, "Orsay Ville"), 1);

			gnb.addStationToRoute(rerC, gnb.addStation(9, "Paris Austerlitz"),0);
			gnb.addStationToRoute(rerC, gnb.addStation(6, "Orly"), 15);
			gnb.addStationToRoute(rerC, gnb.getStationTest(10), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(7, "Choisy"), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(8, "Juvisy"), 3);
			gnb.addStationToRoute(rerC, massyPal, 25);

			gnb.defineEntryCost(sncf.getKindFromString("RER"), 4);

			gnb.linkStationBidirectional(rerC, massyPal, rerB, massyPal, 0, 3, false);
			gnb.linkStationBidirectional(rerC, sncf.getStation(9), rerB, sncf.getStation(1),2, 9, true);

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
	public void verifGraphOriente () {
		Station massy = sncf.getStation(10);
		Iterator<Junction> it = massy.getJunctions();
		int nbJunctions = 0;
		while (it.hasNext()) {
			it.next();
			nbJunctions++;
		}
		/*
		 * Massy Palaiseau(RerB)<=>Le Guichet(RerB) : 0.0$ in 9 minutes
		 * Massy Palaiseau(RerC)<=>Choisy(RerC) : 0.0$ in 15 minutes
		 * Massy Palaiseau(RerC)=>Massy Palaiseau(RerB) : 0.0$ in 3 minutes
		 * Massy Palaiseau(RerB)=>Massy Palaiseau(RerC) : 0.0$ in 3 minutes
		 * Juvisy(RerC)<=>Massy Palaiseau(RerC) : 0.0$ in 25 minutes
		 * Massy Palaiseau(RerB)<=>Croix de Berny(RerB)
		 * Massy Palaiseau(RerC)<=>Orly(RerC)
		 * 
		 * Manque :
		 * 
		 * Plus rien ! 
		 */
		assertTrue("mauvais nombre de jonctions, ici "+nbJunctions+" et normalement 7",nbJunctions==7);
		
	}
	
	@Test
	public void CréationGraph() {
		GraphNetwork gn = gnb.getCurrentGraphNetwork();
		PathInGraphCollectionBuilder pc = gn
				.getInstancePathInGraphCollectionBuilder();
		PathInGraphConstraintBuilder pcb = pc.getPathInGraphConstraintBuilder();

		pcb.setMainCriterious(CriteriousForLowerPath.TIME);
		pcb.setMinorCriterious(CriteriousForLowerPath.CHANGE);
		pcb.setOrigin(gn.getStation(3));
		pcb.setDestination(gn.getStation(8));

		PathInGraphResultBuilder prb = pc.getPathInGraphResultBuilder();
		
		bob = new Dijkstra();

		PathInGraph p = bob.findPath(prb);

		Iterator<Junction> it = p.getJunctions();
		int time=0;
		int changes=0;
		while (it.hasNext()) {
			Junction j=it.next();
			time+=j.getTimeBetweenStations();
			if (!j.isRouteLink()) changes++;
			System.out.println(j.toString());
		}
		System.out.println("---------------------------------------");
		System.out.println("Time : "+time+" minutes");
		System.out.println(changes+" changements");
	}

	@Test
	public void CalculPlusCourtChemin() {

	}

}
