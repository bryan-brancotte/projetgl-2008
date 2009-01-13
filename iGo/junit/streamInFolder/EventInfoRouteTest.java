package streamInFolder;

import static org.junit.Assert.assertTrue;


import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Route;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.KindEventInfoNetwork;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import streamInFolder.event.EventInfoRoute;

public class EventInfoRouteTest {
	
	protected EventInfoRoute info;
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;
	protected KindEventInfoNetwork kind;
	
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		kind = KindEventInfoNetwork.OTHER;
		info = new EventInfoRoute("RerB","message1",1,kind);
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		
		bob.reset();
		bob.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder().getPathInGraphResultBuilder();
		Route rerB;
		try {
			rerB = bob.addRoute("RerB", "RER");
			bob.addStationToRoute(rerB, bob.addStation(1, "Paris"), 0);

		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		}
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
	}

	/**
	 * Test du getter de l'id de la route
	 */
	@Test
	public void idRGetter() {
		assertTrue(info.getIdRoute().compareTo("RerB")==0);
	}
	
	/**
	 * Test du getter du kind
	 */
	@Test
	public void kindGetter() {
		assertTrue(info.getKindEventInfoNetwork()!=null);
		assertTrue(info.getKindEventInfoNetwork().compareTo(kind)==0);
	}
	
	/**
	 * Test du getter du message
	 */
	@Test
	public void messageGetter() {
		assertTrue(info.getMessage().compareTo("message1")==0);
	}
	
	/**
	 * Test du applyInfo
	 */
	@Test
	public void applyInfoTester() {
		kind = KindEventInfoNetwork.PROBLEM;
		info.applyInfo(bob);
		assertTrue(sncf.getRoute("RerB").isEnable());
		assertTrue(info.isApplied());
		
		kind = KindEventInfoNetwork.SOLUTION;
		info.applyInfo(bob);
		assertTrue(sncf.getRoute("RerB").isEnable());
		
		
	}
}
