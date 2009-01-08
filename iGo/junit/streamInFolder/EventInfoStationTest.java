package streamInFolder;

import static org.junit.Assert.assertTrue;

import java.util.MissingResourceException;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.KindEventInfoNetwork;
import iGoMaster.exception.ImpossibleStartingException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import streamInFolder.event.EventInfoStation;
import streamInFolder.event.EventInfoStationOnARoute;

public class EventInfoStationTest {

	protected EventInfoStation info;
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;
	protected KindEventInfoNetwork kind;
	
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		kind = KindEventInfoNetwork.OTHER;
		info = new EventInfoStation(1,"message",2,kind);
		
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		
		bob.reset();
		bob.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder().getPathInGraphResultBuilder();
		Route rerB;
		Station massyPal;
		Route rerC;
		try {
			rerB = bob.addRoute("RerB", "RER");
//			rerC = bob.addRoute("RerC", "RER");
//			bob.addRoute("Atlantique", "TGV");
			bob.addStationToRoute(rerB, bob.addStation(1, "Paris"), 0);
			bob.addStationToRoute(rerB, bob.addStation(2, "Antony"), 12);
//			bob.addStationToRoute(rerB, bob.addStation(3, "Croix de berny"), 1);
//			bob.addStationToRoute(rerB, massyPal = bob.addStation(10, "Massy Palaiseau"), 10);
//			bob.addStationToRoute(rerB, bob.addStation(4, "Le Guichet"), 9);
//			bob.addStationToRoute(rerB, bob.addStation(5, "Orsay Ville"), 1);
//
//			bob.addStationToRoute(rerC, bob.addStation(9, "Paris Austerlitz"), 0);
//			bob.addStationToRoute(rerC, bob.addStation(6, "Orly"), 15);
//			bob.addStationToRoute(rerC, bob.addStation(7, "Choisy"), 15);
//			bob.addStationToRoute(rerC, bob.addStation(8, "Juvisy"), 3);
//			bob.addStationToRoute(rerC, massyPal, 25);
//
//			bob.defineEntryCost(sncf.getKindFromString("RER"), 4);
//
//			bob.linkStationBidirectional(rerC, massyPal, rerB, massyPal, 0, 3, false);
//			bob.linkStation(rerC, sncf.getStation(9), rerB, sncf.getStation(1), 2, 9, true);
//			bob.linkStation(rerB, sncf.getStation(1), rerC, sncf.getStation(9), 5, 3, false);
//
//			bob.addServiceToStation(sncf.getStation(9), bob.addService(1, "Journaux"));
//			bob.addServiceToStation(sncf.getStation(9), bob.addService(2, "Cafe"));
//			bob.addServiceToStation(sncf.getStation(9), bob.addService(3, "Handi"));
//			bob.addServiceToStation(sncf.getStation(2), sncf.getService(3));
//			bob.addServiceToStation(sncf.getStation(10), sncf.getService(3));
//			bob.addServiceToStation(sncf.getStation(4), sncf.getService(3));
//			bob.addServiceToStation(sncf.getStation(5), sncf.getService(3));
//			bob.addServiceToStation(sncf.getStation(1), sncf.getService(1));
//			bob.addServiceToStation(sncf.getStation(8), sncf.getService(3));
		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		}
//		catch (MissingResourceException e) {
//			assertTrue("un objet est null dans linkstation", false);
//		} catch (StationNotOnRoadException e) {
//			assertTrue("Une station ne semble pas avoir été mise sur la ligne, or on l'a demandé", false);
		catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		}
		
	}
	
	/**
	 * Test du getter de l'id
	 */
	@Test
	public void idGetter() {
		assertTrue(info.getId()==1);
		info.setId(3);
		assertTrue(info.getId()==3);
	}
	
	/**
	 * Test du getter du kind
	 */
	@Test
	public void kindGetter() {
		assertTrue(info.getKindEventInfoNetwork().compareTo(kind)==0);
		kind = KindEventInfoNetwork.SOLUTION;
		info.setKindEventInfoNetwork(kind);
		assertTrue(info.getKindEventInfoNetwork().compareTo(kind)==0);
	}
	
	/**
	 * Test du getter du message
	 */
	@Test
	public void messageGetter() {
		assertTrue(info.getMessage().compareTo("message")==0);
		info.setMessage("newMessage");
		assertTrue(info.getMessage().compareTo("newMessage")==0);
	}
	
	/**
	 * Test du applied
	 */
	@Test
	public void appliedTested() {
		assertTrue(!info.isApplied());
		info.setApplied(true);
		assertTrue(info.isApplied());
	}
	
	/**
	 * Test du applyInfo
	 */
	@Test
	public void applyInfoTester() {
		kind = KindEventInfoNetwork.PROBLEM;
		info.setKindEventInfoNetwork(kind);
		info.applyInfo(bob);
		assertTrue(!sncf.getStation(1).isEnable());
		assertTrue(info.isApplied());
		
		kind = KindEventInfoNetwork.SOLUTION;
		info.setKindEventInfoNetwork(kind);
		info.applyInfo(bob);
		assertTrue(sncf.getStation(1).isEnable());
		
		
	}
}
