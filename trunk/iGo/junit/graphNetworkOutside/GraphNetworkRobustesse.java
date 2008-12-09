package graphNetworkOutside;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphNetworkRobustesse {
	protected GraphNetworkBuilder bob;
	protected GraphNetwork sncf;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		bob = new GraphNetworkBuilder();
		sncf = bob.getInstance();
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(GraphNetworkRobustesse.class);
	}
	
	@Test
	public void getJunction(){
		
	}
}
