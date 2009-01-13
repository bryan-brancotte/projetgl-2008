package streamInFolder;

import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import streamInFolder.graphReaderFolder.GraphNetworkReceiverFolder;

public class GraphNetworkReceiverFolderTest {

	GraphNetworkReceiverFolder folder;
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest(){
		folder = new GraphNetworkReceiverFolder("network");
	}
	
	
	/**
	 * Test du getter network
	 */
	@Test
	public void networkGetter() {
		assertTrue(folder.getAvaibleNetwork()!=null);
	}
}
