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

import streamInFolder.event.EventInfoStationOnARoute;;

public class EventInfoStationOnARouteTest {

	protected EventInfoStationOnARoute info;
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;
	protected KindEventInfoNetwork kind;
	
	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		kind = KindEventInfoNetwork.OTHER;
		info = new EventInfoStationOnARoute(1,"RerB","message",2,kind);
		
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		
		bob.reset();
		bob.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder().getPathInGraphResultBuilder();
		Route rerB;
		try {
			rerB = bob.addRoute("RerB", "RER");
			bob.addStationToRoute(rerB, bob.addStation(1, "Paris"), 0);
			bob.addStationToRoute(rerB, bob.addStation(2, "Antony"), 12);
		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		}
		catch (ImpossibleValueException e) {
			assertTrue("linkStation ne supporte pas un valeur normal", false);
		}
		
	}
	
	
	/**
	 * Test du getter des id
	 */
	@Test
	public void idsGetter() {
		assertTrue(info.getIdStation()==1);
		assertTrue(info.getId()==2);
		assertTrue(info.getIdRoute().compareTo("RerB")==0);
	}
	
	/**
	 * Test du getter du kindEvent
	 */
	@Test
	public void kindEventGetter() {
		assertTrue(info.getKindEventInfoNetwork().compareTo(kind)==0);
		kind = KindEventInfoNetwork.SOLUTION;
	}
	
	/**
	 * Test du getter du message
	 */
	@Test
	public void messageGetter() {
		assertTrue(info.getMessage().compareTo("message")==0);
	}
	
	/**
	 * Test du applied
	 */
	@Test
	public void appliedTested() {
		assertTrue(!info.isApplied());
	}
	
	/**
	 * Test du applyInfo
	 */
	@Test
	public void applyInfoTester() {
		kind = KindEventInfoNetwork.PROBLEM;
		info.applyInfo(bob);
		assertTrue(sncf.getRoute("RerB").getStation(1).isEnable());
		assertTrue(info.isApplied());
		
		kind = KindEventInfoNetwork.SOLUTION;
		info.applyInfo(bob);
		assertTrue(sncf.getRoute("RerB").getStation(1).isEnable());
		
		
	}
}
