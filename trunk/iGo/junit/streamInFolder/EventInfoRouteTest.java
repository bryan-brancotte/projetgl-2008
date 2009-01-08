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
		info.setId(2);
		bob = new GraphNetworkBuilder();
		sncf = bob.getCurrentGraphNetwork();
		
		bob.reset();
		bob.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder().getPathInGraphResultBuilder();
		Route rerB;
		Station massyPal;
		Route rerC;
		try {
			rerB = bob.addRoute("RerB", "RER");

		} catch (ViolationOfUnicityInIdentificationException e) {
			assertTrue("Erreur dans la vérification des id unique", false);
		}
	}

	/**
	 * Test du getter de l'id
	 */
	@Test
	public void idGetter() {
		assertTrue(info.getId()==2);
		info.setId(3);
		assertTrue(info.getId()==3);
	}
	
	/**
	 * Test du getter du kind
	 */
	@Test
	public void kindGetter() {
		assertTrue(info.getKindEventInfoNetwork()!=null);
		assertTrue(info.getKindEventInfoNetwork().compareTo(kind)==0);
		//TODO voir comment initialiser le kindEventInfoNetwork
	}
	
	/**
	 * Test du getter du message
	 */
	@Test
	public void messageGetter() {
		assertTrue(info.getMessage().compareTo("message1")==0);
		info.setMessage("message2");
		assertTrue(info.getMessage().compareTo("message2")==0);
	}
	
	/**
	 * Test du apply
	 */
	@Test
	public void applyTester() {
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
		assertTrue(!sncf.getRoute("RerB").isEnable());
		assertTrue(info.isApplied());
		
		kind = KindEventInfoNetwork.SOLUTION;
		info.setKindEventInfoNetwork(kind);
		info.applyInfo(bob);
		assertTrue(sncf.getRoute("RerB").isEnable());
		
		
	}
}
