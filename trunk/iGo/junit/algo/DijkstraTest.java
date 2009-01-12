package algo;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;


import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.Service;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo;
import iGoMaster.Algo.CriteriousForLowerPath;
import iGoMaster.exception.NoRouteForStationException;

import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;
import algorithm.exception.NonValidOriginException;

public class DijkstraTest {

	private Algo algo;
	private PathInGraphConstraintBuilder constraintBuilder;
	private PathInGraphCollectionBuilder collectionBuilder;
	private GraphNetworkBuilder networkBuilder;
	
	private Station s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14;
	private Route r0, r1, r2, r3;
	private Service sv0, sv1;
	
	boolean algoRunning = false;
	
	@Before
	public void setUp() 
	{
		algo = new Dijkstra();
		
		networkBuilder = new GraphNetworkBuilder();
		
		try 
		{
			
			r0 = networkBuilder.addRoute("r0","rer");
			r1 = networkBuilder.addRoute("r1","rer");
			r2 = networkBuilder.addRoute("r2","rer");
			r3 = networkBuilder.addRoute("r3","rer");

			s0 = networkBuilder.addStation(0, "s0");
			s1 = networkBuilder.addStation(1, "s1");
			s2 = networkBuilder.addStation(2, "s2");
			s3 = networkBuilder.addStation(3, "s3");
			s4 = networkBuilder.addStation(4, "s4");
			s5 = networkBuilder.addStation(5, "s5");
			s6 = networkBuilder.addStation(6, "s6");
			s7 = networkBuilder.addStation(7, "s7");
			s8 = networkBuilder.addStation(8, "s8");
			s9 = networkBuilder.addStation(9, "s9");
			s10 = networkBuilder.addStation(10, "s10");
			s11 = networkBuilder.addStation(11, "s11");
			s12 = networkBuilder.addStation(12, "s12");
			s13 = networkBuilder.addStation(13, "s13");
			s14 = networkBuilder.addStation(14, "s14");
			
			sv0 = networkBuilder.addService(0, "cirage2chaussure");
			sv1 = networkBuilder.addService(1, "photomaton");
			
			networkBuilder.addServiceToStation(s0, sv0);
			networkBuilder.addServiceToStation(s4, sv0);
			networkBuilder.addServiceToStation(s6, sv1);
			networkBuilder.addServiceToStation(s7, sv0);
			networkBuilder.addServiceToStation(s8, sv0);
			networkBuilder.addServiceToStation(s10, sv0);
			networkBuilder.addServiceToStation(s11, sv0);
			networkBuilder.addServiceToStation(s13, sv0);
			
			networkBuilder.addStationToRoute(r0, s0, 1);
			networkBuilder.addStationToRoute(r0, s1, 1);
			networkBuilder.addStationToRoute(r0, s2, 1);
			networkBuilder.addStationToRoute(r0, s3, 1);
			networkBuilder.addStationToRoute(r0, s4, 1);
			
			networkBuilder.addStationToRoute(r1, s5, 1);
			networkBuilder.addStationToRoute(r1, s6, 1);
			networkBuilder.addStationToRoute(r1, s7, 1);
			
			networkBuilder.addStationToRoute(r2, s8, 1);
			networkBuilder.addStationToRoute(r2, s9, 1);
			networkBuilder.addStationToRoute(r2, s10, 1);
			
			networkBuilder.addStationToRoute(r3, s11, 1);
			networkBuilder.addStationToRoute(r3, s12, 1);
			networkBuilder.addStationToRoute(r3, s13, 1);
			
			collectionBuilder = networkBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();
			constraintBuilder = collectionBuilder.getPathInGraphConstraintBuilder();		
		} 
		catch (NullPointerException e) {e.printStackTrace();} 
		catch (ViolationOfUnicityInIdentificationException e) {e.printStackTrace();}
		catch (ImpossibleValueException e) {e.printStackTrace();} 
	}
	
	@Test
	/**
	 * Si l'origine et la destination sont les mêmes
	 */
	public void test0FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s0);
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			fail ("Les stations de départ et d'arrivée ne doivent pas être les mêmes :" +
			"une exception aurait du etre levée");
		}
		catch (Exception e){}
	}

	@Test
	/**
	 * Si on passe une station sans route à algo
	 */
	public void test1FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s14);
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			fail ("l'algorithme n'aurait pas du terminer");
		}
		catch (NoRouteForStationException e){}
		catch (Exception e){fail ("Erreur mal traitée");}
	}
	
	@Test
	/**
	 * Si on passe un resultBuilder null à algo
	 */
	public void test2FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s1);
			algo.findPath(null);
			fail ("l'algorithme n'aurait pas du terminer");
		}
		catch (NullPointerException e){fail ("erreur non traitée");}
		catch (Exception e){}
	}
	

	@Test
	/**
	 * PathInGraph sans origine 
	 */
	public void test3FindPath() {
		
		try
		{
			constraintBuilder.setDestination(s1);
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			fail ("l'algorithme n'aurait pas du terminer");
		}
		catch (NullPointerException e){fail ("erreur non traitée");}
		catch (Exception e){}
	}
	
	@Test
	/**
	 * PathInGraph sans dest
	 */
	public void test4FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s0);
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			fail ("l'algorithme n'aurait pas du terminer");
		}
		catch (NullPointerException e){fail ("erreur non traitée");}
		catch (NoRouteForStationException e){}
		catch (Exception e){fail ("Erreur mal traitée");}
	}
	
	@Test
	/**
	 * PathInGraph sans criterious
	 */
	public void test5FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s2);
			constraintBuilder.setDestination(s1);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
		}
		catch (NoSuchElementException e){fail ("Pas de traitement adapté à l'exception");}
		catch (Exception e){}
	}
	
	@Test
	/**
	 * Test chemin entre deux stations
	 */
	public void test6FindPath() {
		
		try
		{
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.CHANGE);
			
			constraintBuilder.setOrigin(s2);
			constraintBuilder.setDestination(s1);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			
			assertEquals(collectionBuilder.getPathInGraph().getJunctionsCount(), 1);
			assertEquals (collectionBuilder.getPathInGraph().getJunctions().next().getOtherStation(s2),s1);
			
		}
		catch (Exception e){}
	}
	
	@Test
	/**
	 * Test du chemin avec le moins de changement
	 */
	public void test7FindPath() {
		
		try
		{

			networkBuilder.linkStationBidirectional(r0, s0, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s5, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r0, s4, r2, s10, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.CHANGE);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s8);
		
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertEquals(7, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	@Test
	/**
	 * Test du chemin le plus rapide
	 */
	public void test8FindPath() {
		
		try
		{

			networkBuilder.linkStationBidirectional(r0, s0, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s5, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r0, s4, r2, s10, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.CHANGE);
			
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s8);
		
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertEquals(2, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	@Test
	/**
	 * 
	 * Test du chemin le moins couteux
	 */
	public void test9FindPath() {
		
		try
		{

			networkBuilder.linkStationBidirectional(r0, s0, r1, s5, 8, 0, true);
			networkBuilder.linkStationBidirectional(r0, s4, r1, s7, 1, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.COST);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s5);
		
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertEquals(7, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	

	@Test
	/**
	 * 
	 * Service partout
	 */
	public void test10FindPath() {
		
		try
		{

			networkBuilder.linkStationBidirectional(r0, s4, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s7, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r2, s10, r3, s11, 0, 0, true);
			networkBuilder.linkStationBidirectional(r3, s13, r0, s0, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.COST);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s4);
			constraintBuilder.setDestination(s7);
			
			constraintBuilder.addSeviceAlways(sv1);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			fail ("Exception non traitée");
			}
		catch(NonValidOriginException e){}
		catch (Exception e){fail ("Exception non traitée");};
	}
	
	@Test
	/**
	 * 
	 * Service à un endroit
	 */
	public void test11FindPath() {
		
		try
		{
			networkBuilder.linkStationBidirectional(r0, s4, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s7, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r2, s10, r3, s11, 0, 0, true);
			networkBuilder.linkStationBidirectional(r3, s13, r0, s0, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.COST);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s12);
			constraintBuilder.setDestination(s10);
			
			constraintBuilder.addSeviceOnce(sv1);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertNotSame(2, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	@Test
	/**
	 * 
	 * Step station
	 */
	public void test12FindPath() {
		
		try
		{
			networkBuilder.linkStationBidirectional(r0, s3, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s7, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r2, s10, r3, s11, 0, 0, true);
			networkBuilder.linkStationBidirectional(r3, s13, r0, s0, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.COST);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s2);
			constraintBuilder.setDestination(s5);
			
			constraintBuilder.addStepStations(s4);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertNotSame(2, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	
	@Test
	/**
	 * Avoid station
	 */
	public void test13FindPath() {
		
		try
		{
			networkBuilder.linkStationBidirectional(r0, s4, r1, s5, 0, 0, true);
			networkBuilder.linkStationBidirectional(r1, s7, r2, s8, 0, 0, true);
			networkBuilder.linkStationBidirectional(r2, s10, r3, s11, 0, 0, true);
			networkBuilder.linkStationBidirectional(r3, s13, r0, s0, 0, 0, true);
			
			constraintBuilder.setMainCriterious(CriteriousForLowerPath.COST);
			constraintBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
			
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s4);
			
			constraintBuilder.addAvoidStations(s3);
			
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
			assertEquals(10, collectionBuilder.getPathInGraph().getJunctionsCount());
		}
		catch (Exception e){e.printStackTrace();}
	}

	
	@Test
	public void testAbort() 
	{
		constraintBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
		constraintBuilder.setMinorCriterious(CriteriousForLowerPath.CHANGE);
		
		constraintBuilder.setOrigin(s0);
		constraintBuilder.setDestination(s1);
		
		new Thread() 
		{
			public void run() 
			{
				algoRunning = true;
				
				try {algo.findPath(collectionBuilder.getPathInGraphResultBuilder());} 
				catch (Exception e) {e.printStackTrace();}	
				algoRunning = false;
			}
		}.start();
		
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		algo.abort();
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		
		if (algoRunning) fail("La méthode abort n'est pas efficace");
	}

}
