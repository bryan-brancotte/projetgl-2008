package graphNetwork;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import streamInFolder.EventInfoNetworkWatcherInFolderTest;

public class GraphNetworkBuilderTest {

	protected GraphNetworkBuilder graph;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetwork();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(EventInfoNetworkWatcherInFolderTest.class);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationCorrect(){
	}
}
