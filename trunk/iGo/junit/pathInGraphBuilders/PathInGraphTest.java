package pathInGraphBuilders;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo.CriteriousForLowerPath;

import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PathInGraphTest {
	protected GraphNetworkBuilder gnb;
	protected GraphNetwork gn;
	protected PathInGraphCollectionBuilder pcb;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		gnb = new GraphNetworkBuilder();
		gn = gnb.getCurrentGraphNetwork();
		pcb = gn.getInstancePathInGraphCollectionBuilder();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(PathInGraphTest.class);
	}

	/**
	 * Construction du réseau sans lever d'exception
	 */
	@Test
	public void constructionDUnReseauSansProbleme() {
		gnb.reset();
		gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder().getPathInGraphResultBuilder();
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
			gnb.addStationToRoute(rerB, massyPal = gnb.addStation(10, "Massy Palaiseau"), 10);
			gnb.addStationToRoute(rerB, gnb.addStation(4, "Le Guichet"), 9);
			gnb.addStationToRoute(rerB, gnb.addStation(5, "Orsay Ville"), 1);

			gnb.addStationToRoute(rerC, gnb.addStation(9, "Paris Austerlitz"), 0);
			gnb.addStationToRoute(rerC, gnb.addStation(6, "Orly"), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(7, "Choisy"), 15);
			gnb.addStationToRoute(rerC, gnb.addStation(8, "Juvisy"), 3);
			gnb.addStationToRoute(rerC, massyPal, 25);

			gnb.defineEntryCost(gn.getKindFromString("RER"), 4);

			gnb.linkStation(rerC, massyPal, rerB, massyPal, 0, 3, false);
			gnb.linkStation(rerB, massyPal, rerC, massyPal, 0, 3, false);
			gnb.linkStation(rerC, gn.getStation(9), rerB, gn.getStation(1), 2, 9, true);
			gnb.linkStation(rerB, gn.getStation(1), rerC, gn.getStation(9), 5, 3, false);

			gnb.addServiceToStation(gn.getStation(9), gnb.addService(1, "Journaux"));
			gnb.addServiceToStation(gn.getStation(9), gnb.addService(2, "Cafe"));
			gnb.addServiceToStation(gn.getStation(9), gnb.addService(3, "Handi"));
			gnb.addServiceToStation(gn.getStation(2), gn.getService(3));
			gnb.addServiceToStation(gn.getStation(10), gn.getService(3));
			gnb.addServiceToStation(gn.getStation(4), gn.getService(3));
			gnb.addServiceToStation(gn.getStation(5), gn.getService(3));
			gnb.addServiceToStation(gn.getStation(1), gn.getService(1));
			gnb.addServiceToStation(gn.getStation(8), gn.getService(3));
		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		} catch (MissingResourceException e) {
			assertTrue("un objet est null dans linkstation", false);
		} catch (StationNotOnRoadException e) {
			assertTrue("Une station ne semble pas avoir été mise sur la ligne, or on l'a demandé", false);
		} catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		}
		// System.out.println("Construction sans problème");
	}

	/**
	 * 
	 */
	@Test
	public void constructionDUnCheminSansProbleme() {
		constructionDUnReseauSansProbleme();
		assertTrue("getInstancePathInGraphCollectionBuilder ne devrait pas retourner null", pcb != null);
		PathInGraphConstraintBuilder constraintBuilder = pcb.getPathInGraphConstraintBuilder();
		constraintBuilder.setDestination(gn.getStation(3));
		constraintBuilder.setOrigin(gn.getStation(8));
		constraintBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
		constraintBuilder.setMinorCriterious(CriteriousForLowerPath.CHANGE);
		constraintBuilder.addAvoidStations(gn.getStation(1));
		constraintBuilder.addStepStations(gn.getStation(7));
		constraintBuilder.addSeviceOnce(gn.getService(1));
		constraintBuilder.addSeviceAlways(gn.getService(2));
	}

	/**
	 * 
	 */
	@Test
	public void constructionDUnCheminCorrectementFait() {
		constructionDUnCheminSansProbleme();
		testDuCheminCorrectementFait();
	}

	public void testDuCheminCorrectementFait() {
		assertTrue("getInstancePathInGraphCollectionBuilder ne devrait pas retourner null", pcb != null);
		PathInGraph pig;
		pig = pcb.getPathInGraphConstraintBuilder().getCurrentPathInGraph();
		assertTrue("arrivé est station 3", pig.getDestination() == gn.getStation(3));
		assertTrue("départ est station 8", pig.getOrigin() == gn.getStation(8));
		assertTrue("MainCriterious est TIME", pig.getMainCriterious() == CriteriousForLowerPath.TIME);
		assertTrue("MinorCriterious est CHANGE", pig.getMinorCriterious() == CriteriousForLowerPath.CHANGE);
		assertTrue("Il n'y a qu'un station à éviter", pig.getAvoidStationsArray().length == 1);
		assertTrue("La station à éviter est 1", pig.getAvoidStationsArray()[0].getId()==1);
		assertTrue("Il n'y a qu'un station à emprunter", pig.getStepsArray().length == 1);
		assertTrue("La station à éviter est 7", pig.getStepsArray()[0].getId()==7);
		assertTrue("Il n'y a qu'un service obligatoire", pig.getServicesAlwaysArray().length == 1);
		assertTrue("Le service toujours présent est 2", pig.getServicesAlwaysArray()[0].getId()==2);
		assertTrue("Il n'y a qu'un service \"au moins un\"", pig.getServicesOnceArray().length == 1);
		assertTrue("Le service \"au moins un\" est 1", pig.getServicesOnceArray()[0].getId()==1);
	}

	@Test
	public void exportPath() {
		constructionDUnCheminSansProbleme();
		String org = pcb.getPathInGraph().exportPath();
		System.out.println(pcb.getPathInGraph().exportPath());
		System.out.println((pcb = gn.getInstancePathInGraphCollectionBuilder(pcb.getPathInGraph().exportPath()))
				.getPathInGraph().exportPath());
		testDuCheminCorrectementFait();
		assertTrue("Un va et vient doit être identique", org.compareTo(gn.getInstancePathInGraphCollectionBuilder(
				pcb.getPathInGraph().exportPath()).getPathInGraph().exportPath()) == 0);
	}
}
