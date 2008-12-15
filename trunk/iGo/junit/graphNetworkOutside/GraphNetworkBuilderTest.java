package graphNetworkOutside;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Junction;
import graphNetwork.KindRoute;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Iterator;
import java.util.MissingResourceException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphNetworkBuilderTest {

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
		return new JUnit4TestAdapter(GraphNetworkBuilderTest.class);
	}

	@Test
	public void newGraphNetworkBuilderSansProbleme() {
		assertTrue(sncf != null);
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
			rerC = bob.addRoute("Atlantique", "TGV");
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

			// TODO linkStation est dans un sens ou les deux sens?
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

	/**
	 * Vérifie que toutes les Routes sont présentes, et avec leur noms.
	 */
	@Test
	public void initialisationRoute() {
		constructionDUnReseauSansProbleme();
		Iterator<Route> it = sncf.getRoutes();
		Route r;
		int cpt = 0;
		boolean rerB_OK = false;
		boolean rerC_OK = false;
		boolean atlantique_OK = false;
		while (it.hasNext()) {
			r = it.next();
			cpt++;
			rerB_OK |= r.getId().compareTo("RerB") == 0;
			rerC_OK |= r.getId().compareTo("RerC") == 0;
			atlantique_OK |= r.getId().compareTo("Atlantique") == 0;
		}
		assertTrue("Normalement il y a 3 route, pas ici.", cpt == 3);
		assertTrue("La route RerB n'a pas été trouvé", rerB_OK);
		assertTrue("La route RerC n'a pas été trouvé", rerC_OK);
		assertTrue("La route Atlantique n'a pas été trouvé", atlantique_OK);
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
		
		String[] nom = { 
				"Paris",
				"Antony", 
				"Croix de berny", 
				"Le Guichet", 
				"Orsay Ville", 
				"Orly", 
				"Choisy", 
				"Juvisy", 
				"Paris Austerlitz",
				"Massy Palaiseau" };
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
		assertTrue(sncf.getService(1).getName().compareTo("Journaux") == 0);
		assertTrue(sncf.getService(2).getName().compareTo("Cafe") == 0);
		assertTrue(sncf.getService(3).getName().compareTo("Handi") == 0);
	}

	/**
	 * Vérifie que toutes les stations sont présentes, et avec leurs noms.
	 */
	@Test
	public void initialisationServiceOnStation() {
		constructionDUnReseauSansProbleme();
		Station[] stations = new Station[] { sncf.getStation(9),/* les 3 services */
		sncf.getStation(1), /* le service 1 */
		sncf.getStation(2), /* le service 3 */
		sncf.getStation(10), /* le service 3 */
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
	public void initialisationStationOnRoute() {
		constructionDUnReseauSansProbleme();
		Route[] routes;
		Iterator<Route> it;
		assertTrue("Test à fAire", false);
		for (int i = 1; i <= 5; i++) {
			it = sncf.getStation(i).getRoutes();
			assertTrue("La station " + i + " n'est pas sur une Route, or elle devrait", it.hasNext());
			assertTrue("La station " + i + " n'est pas sur la Route RerB , or elle devrait", it.next().getId()
					.compareTo("RerB") == 0);
			assertTrue("La station " + i + " est sur plus d'une Route, or elle ne devrait pas", it.hasNext());

		}
		for (int i = 6; i <= 9; i++) {
			it = sncf.getStation(i).getRoutes();
			assertTrue("La station " + i + " n'est pas sur une Route, or elle devrait", it.hasNext());
			assertTrue("La station " + i + " n'est pas sur la Route RerC , or elle devrait", it.next().getId()
					.compareTo("RerC") == 0);
			assertTrue("La station " + i + " est sur plus d'une Route, or elle ne devrait pas", it.hasNext());

		}

		it = sncf.getStation(10).getRoutes();
		assertTrue("La station 10 n'est pas sur aucune Route, or elle devrait", it.hasNext());
		routes = new Route[2];
		routes[0] = it.next();
		assertTrue("La station 10 n'est pas sur deux Route, or elle devrait", it.hasNext());
		routes[1] = it.next();
		assertTrue("La station 10 est sur plus de deux Route, or elle ne devrait pas", it.hasNext());

		if (routes[0].getId().compareTo("RerC") == 0)
			assertTrue("La station 10 n'est pas sur la Route RerC , or elle devrait", routes[1].getId().compareTo(
					"RerB") == 0);
		else
			assertTrue("La station 10 n'est pas sur la Route RerC , or elle devrait", (routes[0].getId().compareTo(
					"RerB") == 0)
					&& (routes[1].getId().compareTo("RerC") == 0));
	}

	@Test
	public void initialisationInter() {
		constructionDUnReseauSansProbleme();
		Iterator<Junction> it;
		Junction j;
		it = sncf.getJunctions(sncf.getStation(9), sncf.getStation(1));
		it = sncf.getJunctions(sncf.getStation(10), sncf.getStation(10));

		assertTrue("Les stations 10 et 10 ne sont pas relier entre elles, or elles devraient", it.hasNext());
		j = it.next();
		assertTrue("Le temps entre les stations 10 et 10 est faux", j.getTimeBetweenStations() == 3);
		assertTrue("Le coût entre les stations 10 et 10 est faux", j.getCost() == 0);
		assertTrue("Le lien entre les stations 10 et 10 est pedestrian alors qu'il ne devrait pas l'être", !j
				.isPedestrian());
		assertTrue(
				"Le lien entre les stations 10 et 10 est marquer comme sur une ligne alors qu'il ne devrait pas l'être",
				j.isRouteLink());
		assertTrue("Les stations 10 et 10 sont relier entre elles plus d'un fois, or elles ne devraient pas", it
				.hasNext());

		assertTrue("Les stations 9 et 1 ne sont pas relier entre elles, or elles devraient", it.hasNext());
		j = it.next();
		assertTrue("Le temps entre les stations 9 et 1 est faux", j.getTimeBetweenStations() == 9);
		assertTrue("Le coût entre les stations 9 et 1 est faux", j.getCost() == 2);
		assertTrue("Le lien entre les stations 9 et 1 n'est pas pedestrian alors qu'il devrait l'être", j
				.isPedestrian());
		assertTrue(
				"Le lien entre les stations 9 et 1 est marquer comme sur une ligne alors qu'il ne devrait pas l'être",
				j.isRouteLink());
		assertTrue("Les stations 9 et 1 sont relier entre elles plus d'un fois, or elles ne devraient pas", it
				.hasNext());
	}

	@Test
	public void initialisationEnableAndUseOfEnable() {
		constructionDUnReseauSansProbleme();
		Iterator<Junction> itJ;
		Junction j;
		Iterator<Station> itS = sncf.getStations();
		Station station;
		while (itS.hasNext()) {
			station = itS.next();
			assertTrue("Toute les Stations doivent être enable", station.isEnable());
			station.setEnable(false);
			assertTrue("La Station " + station.getId() + " devrait maintenant être disable", !station.isEnable());
			station.setEnable(true);
			assertTrue("La Station " + station.getId() + " devrait maintenant être enable", !station.isEnable());
			itJ = station.getJunctions();
			while (itJ.hasNext()) {
				j = itJ.next();
				assertTrue("Toute les Jonctions de la Station " + station.getId() + " doivent être enable", j
						.isEnable());
				j.setEnable(false);
				assertTrue("La Jonction " + j + " devrait maintenant être disable", !j.isEnable());
				j.setEnable(true);
				assertTrue("La Jonction " + j + " devrait maintenant être enable", !j.isEnable());

			}
		}
		Iterator<Route> itR = sncf.getRoutes();
		Route r;
		while (itR.hasNext()) {
			r = itR.next();
			assertTrue("Toute les Routes doivent être enable", r.isEnable());
			r.setEnable(false);
			assertTrue("La Jonction " + r + " devrait maintenant être disable", !r.isEnable());
			r.setEnable(true);
			assertTrue("La Jonction " + r + " devrait maintenant être enable", !r.isEnable());
		}
	}

	@Test
	public void useOfEnableAndReset() {
		constructionDUnReseauSansProbleme();
		sncf.getStation(4).setEnable(false);
		sncf.getStation(7).setEnable(false);
		sncf.getRoute("RerB").setEnable(false);
		sncf.getRoute("RerC").setEnable(false);
		sncf.getStation(10).getJunctions().next().setEnable(false);
		sncf.getRoute("RerB").setStationEnable(10, false);
		assertTrue(!sncf.getRoute("RerB").isStationEnable(10));
		sncf.resetEnables();
		assertTrue(sncf.getStation(4).isEnable());
		assertTrue(sncf.getStation(7).isEnable());
		assertTrue(sncf.getRoute("RerB").isEnable());
		assertTrue(sncf.getRoute("RerC").isEnable());
		assertTrue(sncf.getStation(10).getJunctions().next().isEnable());
		assertTrue(sncf.getRoute("RerB").isStationEnable(10));
	}

	@Test
	public void entryCost() {
		constructionDUnReseauSansProbleme();
		assertTrue(sncf.getEntryCost(sncf.getKindFromString("RER")) == 4);
		try {
			sncf.getEntryCost(null);
			assertTrue("Définir le comportement d'un getEntryCost(null)", false);
		} catch (Exception e) {
		}
	}

	@Test
	public void initialisationKind() {
		assertTrue(sncf.getKindFromString("RER") != null);
		assertTrue(sncf.getKindFromString("RER").getKindOf().compareTo("RER") == 0);
		assertTrue(sncf.getKindFromString("TGV") != sncf.getKindFromString("RER"));
		assertTrue(sncf.getKindFromString("tagada") == null);
		Iterator<KindRoute> it = sncf.getKinds();
		int cpt = 0;
		while (it.hasNext()) {
			it.next();
			cpt++;
		}
		assertTrue("mauvais nombre de kind", cpt == 2);
	}

}
