package pathInGraphOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphBuilder;
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

public class PIGRobustesse {
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;
	protected PathInGraphBuilder pigB;
	protected PathInGraph pig;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		constructionDUnReseauSansProbleme();
		// TODO REMI getInstancePathInGraphBuilder mal fait
		pigB = sncf.getInstancePathInGraphBuilder();
		pig = pigB.getCurrentPathInGraph();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(PIGRobustesse.class);
	}

	/**
	 * Construction du réseau sans lever d'exception
	 */
	@Test
	public void constructionDUnReseauSansProbleme() {
		// TODO REMI nettoyer code
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
	public void getCurrentPathInGraph() {
		assertTrue("le graph actuelle de PIGB doit être non null", pig != null);
	}

	@Test
	public void getInstancePathInGraphBuilder() {
		assertTrue("le graph actuelle de PIGB doit être non null", pig != null);
		assertTrue("Le graph du pathInGraph doit être le graph du GNB c'est à dire ici sncf", pigB
				.getCurrentPathInGraph().getGraph() == sncf);
	}

	@Test
	public void getInstancePathInGraphBuilderAvecParam() {
		assertTrue("le graph actuelle de PIGB doit être non null", pig != null);
		pigB = sncf.getInstancePathInGraphBuilder(pigB.getCurrentPathInGraph());
		assertTrue("le nouveau PIGB doit être non null", pigB != null);
		assertTrue("le PIG du nouveau PIGB doit être non l'ancien d'après ce que j'ai mit dans ce test", pigB
				.getCurrentPathInGraph() == pig);
	}

	@Test
	public void getInstancePathInGraphBuilderAvecParam2() {
		pigB = sncf.getInstancePathInGraphBuilder(null);
		// TODO passé setActualPathInGraph à protected
		assertTrue("le nouveau PIGB doit être null : le param est null", pigB == null);
	}

	@Test
	public void addFront() {
		try {
			pigB.addFront(null);
			assertTrue("LE chemin doit être vide : on a ajouté un null", pig.getJunctions().hasNext());
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void addLast() {
		try {
			pigB.addLast(null);
			assertTrue("LE chemin doit être vide : on a ajouté un null", pig.getJunctions().hasNext());
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void equals() {
		try {
			assertTrue("pigB n'est pas null, sinon ca aurait planter", !pigB.equals(null));
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}

	@Test
	public void setActualPathInGraph() {
		try {
			//TODO passé cette méthode en proteced?
			pigB.setCurrentPathInGraph(null);
			assertTrue("Spéficie si setActualPathInGraph(null) met le actual  null ou ne fait rien.", false);
			//SI ca met à null suprimer cette ligne
			assertTrue("Le path actuelle ne doit pas avoir changé : un appelle à setActualPathInGraph(null) ", pigB.getCurrentPathInGraph()==pig);
			//Si ca fait rien supprime cette ligne
			assertTrue("Le path actuelle doit maintenant être nin null", pigB.getCurrentPathInGraph()==null);
		} catch (Exception e) {
			assertTrue("Ce ne doit pas planter", false);
			System.err.println(e);
		}
	}
}