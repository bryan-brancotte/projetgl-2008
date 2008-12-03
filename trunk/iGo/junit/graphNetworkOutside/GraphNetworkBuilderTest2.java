package graphNetworkOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Iterator;
import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphNetworkBuilderTest2 {

	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		bob.setActualGraphNetwork(sncf = bob.getInstance());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GraphNetworkBuilderTest2.class);
	}

	/**
	 * Construction du réseau sans lever d'exception
	 */
	@Test
	public void constructionDUnReseauSansProbleme() {
		Route rerB;
		Station massyPal;
		Route rerC;
		try {
			rerB = bob.addRoute("RerB", "RER");
			rerC = bob.addRoute("RerC", "RER");
			bob.addStationToRoute(rerB, bob.addStation(1, "Paris"), 0);
			bob.addStationToRoute(rerB, bob.addStation(2, "Antony"), 12);
			bob.addStationToRoute(rerB, bob.addStation(3, "Croix de berny"), 1);
			bob.addStationToRoute(rerB, massyPal = bob.addStation(9, "Massy Palaiseau"), 9);
			bob.addStationToRoute(rerB, bob.addStation(4, "Le Guichet"), 10);
			bob.addStationToRoute(rerB, bob.addStation(5, "Orsay Ville"), 1);

			bob.addStationToRoute(rerC, bob.addStation(10, "Paris Austerlitz"), 0);
			bob.addStationToRoute(rerC, bob.addStation(6, "Orly"), 15);
			bob.addStationToRoute(rerC, bob.addStation(7, "Choisy"), 15);
			bob.addStationToRoute(rerC, bob.addStation(8, "Juvisy"), 3);
			bob.addStationToRoute(rerC, massyPal, 25);

			bob.defineEntryCost(sncf.getKindFromString("RER"), 4);

			// TODO linkStation est dans un sens ou les deux sens?
			bob.linkStation(rerC, massyPal, rerB, massyPal, 0, 3, false);
			bob.linkStation(rerC, massyPal, rerB, massyPal, 2, 10, true);

			bob.addServiceToStation(sncf.getStation(10), bob.addService(1, "Journaux"));
			bob.addServiceToStation(sncf.getStation(10), bob.addService(2, "Cafe"));
			bob.addServiceToStation(sncf.getStation(10), bob.addService(3, "Handi"));
			bob.addServiceToStation(sncf.getStation(2), sncf.getService(3));
			bob.addServiceToStation(sncf.getStation(9), sncf.getService(3));
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
		}
		assertTrue("Construction sans problème", true);
	}

	/**
	 * Vérifie que toutes les stations sont présentes, et avec leurs noms.
	 */
	@Test
	public void initialisationStation() {
		constructionDUnReseauSansProbleme();
		Iterator<Station> itSta = sncf.getStations();
		Station[] stations = new Station[10];
		Station station;
		String[] nom = { "Paris", "Antony", "Croix de berny", "Le Guichet", "Orsay Ville", "Orly", "Choisy", "Juvisy",
				"Massy Palaiseau", "Paris Austerlitz" };
		while (itSta.hasNext()) {
			station = itSta.next();
			stations[station.getId() - 1] = station;
		}
		for (int i = 0; i < 10; i++) {
			assertTrue("Station " + (i + 1) + " absente du réseau", stations[i] != null);
			assertTrue("Station " + (i + 1) + " n'a pas son vrai nom", stations[i].getName().compareTo(nom[i]) == 0);
		}
	}

	/**
	 * Vérifie que toutes les stations sont présentes, et avec leurs noms.
	 */
	@Test
	public void initialisationService() {
		constructionDUnReseauSansProbleme();
		Iterator<Service> itSer = sncf.getServices();
		int cpt = 0;
		while (itSer.hasNext()) {
			itSer.next();
			cpt++;
		}
		assertTrue("Il y a 3 service, ce qui ne semble pas être le cas ici (" + cpt + ")", cpt == 3);
		assertTrue(sncf.getService(1).getShortDescription().compareTo("Journaux") == 0);
		assertTrue(sncf.getService(2).getShortDescription().compareTo("Cafe") == 0);
		assertTrue(sncf.getService(3).getShortDescription().compareTo("Handi") == 0);
	}

	/**
	 * Vérifie que toutes les stations sont présentes, et avec leurs noms.
	 */
	@Test
	public void initialisationServiceOnStation() {
		Station[] stations = new Station[] { sncf.getStation(10),/* les 3 services */
		sncf.getStation(1), /* le service 1 */
		sncf.getStation(2), /* le service 3 */
		sncf.getStation(9), /* le service 3 */
		sncf.getStation(4), /* le service 3 */
		sncf.getStation(5), /* le service 3 */
		sncf.getStation(8) /* le service 3 */
		};
		constructionDUnReseauSansProbleme();
		Iterator<Service> itSer;
		Service[] services = new Service[3];
		Service service;

		itSer = stations[0].getServices();
		while (itSer.hasNext()) {
			service = itSer.next();
			services[service.getId() - 1] = service;
		}
		for (int i = 0; i < services.length; i++) {
			assertTrue("Le service " + i + " n'est pas présent dans la station " + stations[0].getId(),
					services[1] != null);
		}
		itSer = stations[1].getServices();
		assertTrue("La station " + stations[1].getId() + " n'a pas de service, or elle devrait", itSer.hasNext());
		assertTrue("La station " + stations[1].getId() + " n'a pas le service 1 , or elle devrait", itSer.next()
				.getId() == 1);
		assertTrue("La station " + stations[1].getId() + " a plus d'un service, or elle ne devrait pas", itSer
				.hasNext());
		for (int i = 2; i < stations.length; i++) {
			itSer = stations[1].getServices();
			assertTrue("La station " + stations[i].getId() + " n'a pas de service, or elle devrait", itSer.hasNext());
			assertTrue("La station " + stations[i].getId() + " n'a pas le service 3 , or elle devrait", itSer.next()
					.getId() == 3);
			assertTrue("La station " + stations[i].getId() + " a plus d'un service, or elle ne devrait pas", itSer
					.hasNext());
		}
	}
	
	@Test
	public void initialisationStationOnRoute(){
		assertTrue("Test à fAire",false);
	}
	
	@Test
	public void initialisationInter(){
		assertTrue("Test à fAire",false);
	}
}
