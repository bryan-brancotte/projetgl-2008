package recentsAndFavoritesGraphsTest;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.IGoMaster;
import iGoMaster.RecentsAndFavoritesPathsInGraph;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReceiver;
import streamInFolder.recentsAndFavoritesGraphs.RecentsAndFavoritesPathsInGraphReaderInFolder;

public class RafGraph {

	RecentsAndFavoritesPathsInGraph raf;
	GraphNetworkBuilder gnb;
	GraphNetwork gn;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		gnb = (new IGoMaster("", "")).getGraphNetworkFactory();
		gn = gnb.getCurrentGraphNetwork();
		raf = new RecentsAndFavoritesPathsInGraphReceiver(gnb, new RecentsAndFavoritesPathsInGraphReaderInFolder());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RafGraph.class);
	}

	@Test
	public void testDeBase() {
		assertTrue("l'iterateur ne doit jamais être null", raf.getRecentsPaths() != null);
		assertTrue("l'iterateur ne doit jamais être null", raf.getFavoritesPaths() != null);
		try {
			raf.addAsRecent(null);
		} catch (Exception e) {
			assertTrue("Passer un param null doit marcher", false);
		}
		try {
			assertTrue(!raf.isFavorite(null));
		} catch (Exception e) {
			assertTrue("Passer un param null doit marcher", false);
		}
		try {
			raf.markAsFavorite(null);
		} catch (Exception e) {
			assertTrue("Passer un param null doit marcher", false);
		}
		try {
			raf.removeFromFavorites(null);
		} catch (Exception e) {
			assertTrue("Passer un param null doit marcher", false);
		}
		try {
			raf.removeFromRecents(null);
		} catch (Exception e) {
			assertTrue("Passer un param null doit marcher", false);
		}
	}

	@Test
	public void testDajout() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été ajouté fans les récents, il devrait y être", b);
		it = raf.getFavoritesPaths();
		b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été ajouté fans les récents, il ne devrait pas être déja dans les favoris", !b);
	}
}
