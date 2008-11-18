package graphNetwork;

import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StationAndStationReaderTest {

	protected GraphNetworkBuilder graph;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		graph = new GraphNetwork();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(StationAndStationReaderTest.class);
	}

	/**
	 * 
	 */
	@Test
	public void initialisationCorrect(){
		assertTrue(graph.getKinds().length==0);
	}
}
