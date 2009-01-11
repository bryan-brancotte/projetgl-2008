package algo;

import static org.junit.Assert.*;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.Service;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Algo;

import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;

public class DijkstraTest {

	private Algo algo;
	private PathInGraphConstraintBuilder constraintBuilder;
	private PathInGraphCollectionBuilder collectionBuilder;
	private GraphNetworkBuilder networkBuilder;
	
	private Station s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13;
	private Route r0, r1, r2, r3, r4;
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
			r4 = networkBuilder.addRoute("r4","bus");
			
			s0 = networkBuilder.addStation(0, "s0");
			s1 = networkBuilder.addStation(1, "s1");
			s2 = networkBuilder.addStation(2, "s2");
			s3 = networkBuilder.addStation(3, "s3");
			s4 = networkBuilder.addStation(4, "s4");
			
			sv0 = networkBuilder.addService(0, "cirage2chaussure");
			sv1 = networkBuilder.addService(1, "photomaton");
			
			networkBuilder.addServiceToStation(s0, sv0);
			networkBuilder.addServiceToStation(s1, sv1);
			networkBuilder.addServiceToStation(s1, sv0);
			networkBuilder.addServiceToStation(s2, sv1);
			networkBuilder.addServiceToStation(s3, sv0);
			
			networkBuilder.addStationToRoute(r0, s0, 20);
			networkBuilder.addStationToRoute(r0, s1, 10);
			networkBuilder.addStationToRoute(r1, s2, 30);
			
			networkBuilder.linkStationBidirectional(r1, s2, r0, s1, 0, 10, false);
			
			collectionBuilder = networkBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();
			constraintBuilder = collectionBuilder.getPathInGraphConstraintBuilder();		
		} 
		catch (NullPointerException e) {e.printStackTrace();} 
		catch (ViolationOfUnicityInIdentificationException e) {e.printStackTrace();}
		catch (ImpossibleValueException e) {e.printStackTrace();} 
		catch (StationNotOnRoadException e) {e.printStackTrace();}	
	}
	
	@Test
	public void test0FindPath() {
		
		try
		{
			constraintBuilder.setOrigin(s0);
			constraintBuilder.setDestination(s0);
			algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
		}
		catch (Exception e){}
		
		fail ("Les stations de départ et d'arrivée ne doivent pas être les mêmes :" +
				"une exception aurait du etre levée");
	}
	
	
	

	@Test
	public void testAbort() 
	{
		
		constraintBuilder.setOrigin(s0);
		constraintBuilder.setDestination(s1);
		
		new Thread() 
		{
			public void run() 
			{
				System.out.print("in");
				algoRunning = true;
				
				try {algo.findPath(collectionBuilder.getPathInGraphResultBuilder());} 
				catch (Exception e) {}	
				algoRunning = false;
			}
		}.start();
		
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		algo.abort();
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		
		if (algoRunning) fail("La méthode abort n'est pas efficace");
	}

}
