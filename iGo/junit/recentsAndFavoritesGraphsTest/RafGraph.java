package recentsAndFavoritesGraphsTest;

import static org.junit.Assert.assertTrue;
import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.IGoMaster;
import iGoMaster.RecentsAndFavoritesPathsInGraph;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReceiver;
import streamInFolder.recentsAndFavoritesGraphs.RecentsAndFavoritesPathsInGraphReaderInFolder;

public class RafGraph {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RafGraph.class);
	}
	GraphNetwork gn;
	GraphNetworkBuilder gnb;

	RecentsAndFavoritesPathsInGraph raf;

	@After
	public void epilogueDateTest() {
	}

	@Test
	public void favAndRecentOrdered() {
		Iterator<PathInGraphCollectionBuilder> it;
		PathInGraphCollectionBuilder p1 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p2 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p3 = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(p1);
		raf.addAsRecent(p2);
		raf.addAsRecent(p3);
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
		raf.markAsFavorite(p2.getPathInGraph());
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
	}

	@Test
	public void keepOrder() {
		Iterator<PathInGraphCollectionBuilder> it;
		PathInGraphCollectionBuilder p1 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p2 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p3 = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(p1);
		raf.addAsRecent(p2);
		raf.addAsRecent(p3);
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
		raf.markAsFavorite(p2.getPathInGraph());
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
		raf.removeFromFavorites(p2.getPathInGraph());
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
	}

	@Test
	public void masiveRemove() {
		int size = RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS / 4;
		Vector<PathInGraphCollectionBuilder> addPath = new Vector<PathInGraphCollectionBuilder>();
		PathInGraphCollectionBuilder p1 = gn.getInstancePathInGraphCollectionBuilder();
		Iterator<PathInGraphCollectionBuilder> it;
		boolean b;
		for (int i = 0; i < size; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		raf.addAsRecent(p1);
		for (int i = 0; i < size; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.removeFromRecents(p.getPathInGraph());
		}
		it = raf.getRecentsPaths();
		b = false;
		while (!b && it.hasNext()) {
			b |= p1 == it.next();
		}
		assertTrue("Nous venons d'ajouter/supprimer plusieur chemin, mais p1 devrait toujours être là", b);
	}

	@Test
	public void maxCapacity() {
		Vector<PathInGraphCollectionBuilder> addPath = new Vector<PathInGraphCollectionBuilder>();
		for (int i = 0; i < RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		while (it.hasNext())
			addPath.remove(it.next());
		assertTrue("Nous avons ajouter " + RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS
				+ " trajet, il devrait tous être dans les récent", addPath.isEmpty());
	}

	@Test
	public void mutliAddOneRemove() {
		int size = RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS / 4;
		Vector<PathInGraphCollectionBuilder> addPath = new Vector<PathInGraphCollectionBuilder>();
		Vector<PathInGraphCollectionBuilder> addPathClone = new Vector<PathInGraphCollectionBuilder>();
		PathInGraphCollectionBuilder p1 = gn.getInstancePathInGraphCollectionBuilder();
		Iterator<PathInGraphCollectionBuilder> it;
		for (int i = 0; i < size; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		raf.addAsRecent(p1);
		for (int i = 0; i < size; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		for (PathInGraphCollectionBuilder p : addPath) {
			addPathClone.add(p);
			raf.removeFromRecents(p.getPathInGraph());
		}
		it = raf.getRecentsPaths();
		while (it.hasNext())
			addPathClone.remove(it.next());
		assertTrue(
				"Nous venons d'ajouter plusieur fois les même chemin, il devrait pourtant disparaitre dès la première supression",
				addPathClone.size() == size);
	}

	@Test
	public void orderedAdding() {
		int nbAdd = RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS / 2;
		boolean b;
		LinkedList<PathInGraphCollectionBuilder> addPath = new LinkedList<PathInGraphCollectionBuilder>();
		Iterator<PathInGraphCollectionBuilder> it;
		// on ajout les notre
		for (int i = 0; i < nbAdd; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPath)
			raf.addAsRecent(p);

		it = raf.getRecentsPaths();
		b = true;
		for (int i = 0; i < nbAdd; i++) {
			b &= it.next() == addPath.removeLast();
		}
		assertTrue("Les " + nbAdd
				+ " premier trajet, il devrait être dans l'ordre d'ajout du pls récent au plus ancien", b);
	}

	@Test
	public void orderedDeleting() {
		Iterator<PathInGraphCollectionBuilder> it;
		PathInGraphCollectionBuilder p1 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p2 = gn.getInstancePathInGraphCollectionBuilder();
		PathInGraphCollectionBuilder p3 = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(p1);
		raf.addAsRecent(p2);
		raf.addAsRecent(p3);
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p2", it.next() == p2);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
		raf.removeFromRecents(p2.getPathInGraph());
		it = raf.getRecentsPaths();
		assertTrue("Le chemin le plus récent est le p3", it.next() == p3);
		assertTrue("Ensuite c'est le p1", it.next() == p1);
	}

	@Test
	public void overFlowMaxCapacity() {
		int OVERFLOW = 35;
		Vector<PathInGraphCollectionBuilder> addPath = new Vector<PathInGraphCollectionBuilder>();
		Vector<PathInGraphCollectionBuilder> addPathOver = new Vector<PathInGraphCollectionBuilder>();
		for (int i = 0; i < RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS; i++)
			addPath.add(gn.getInstancePathInGraphCollectionBuilder());
		for (int i = 0; i < OVERFLOW; i++)
			addPathOver.add(gn.getInstancePathInGraphCollectionBuilder());
		for (PathInGraphCollectionBuilder p : addPathOver) {
			raf.addAsRecent(p);
		}
		for (PathInGraphCollectionBuilder p : addPath) {
			raf.addAsRecent(p);
		}
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		while (it.hasNext()) {
			PathInGraphCollectionBuilder p;
			addPath.remove(p = it.next());
			addPathOver.remove(p);
		}
		assertTrue("Nous avons ajouter " + (RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS + OVERFLOW)
				+ " trajet, il devrait en rester les " + OVERFLOW + " premier ajouté dans les récent", addPathOver
				.size() == OVERFLOW);
		assertTrue("Nous avons ajouter " + (RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS + OVERFLOW)
				+ " trajet, les " + RecentsAndFavoritesPathsInGraph.MAX_RECENTS_PATHS
				+ " devrait avoir disparut (rest " + addPath.size() + ")", addPath.isEmpty());
	}

	@Before
	public void prologueDateTest() {
		gnb = (new IGoMaster("", "")).getGraphNetworkFactory();
		gn = gnb.getCurrentGraphNetwork();
		raf = new RecentsAndFavoritesPathsInGraphReceiver(gnb, new RecentsAndFavoritesPathsInGraphReaderInFolder());
	}

	@Test
	public void Robustesse() {
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
	public void testAddDelInFav() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		raf.markAsFavorite(pigColl.getPathInGraph());
		raf.removeFromFavorites(pigColl.getPathInGraph());
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getFavoritesPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été retiré des favoris, il ne devrait plus y être dans les récents", !b);
		assertTrue("le trajet n'est pourtant plus un favoris", !raf.isFavorite(pigColl.getPathInGraph()));
	}

	@Test
	public void testAddDelInRecent() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		raf.removeFromRecents(pigColl.getPathInGraph());
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été retiré, il devrait plus  être dans les récents", !b);
	}

	@Test
	public void testDAjout1() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été ajouté dans les récents, il devrait y être", b);
		it = raf.getFavoritesPaths();
		b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été ajouté dans les récents, il ne devrait pas être déja dans les favoris", !b);
		raf.addAsRecent(pigColl);
	}

	@Test
	public void testDAjoutFav() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		raf.markAsFavorite(pigColl.getPathInGraph());
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue(
				"le trajet à été ajouté dansles récents puis les favoris, il devrait toujours être dans les récents", b);
		it = raf.getFavoritesPaths();
		b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été ajouté dans les récents puis les favoris, il devrait être donc dans les favoris", b);
		assertTrue("le trajet est pourtant un favoris", raf.isFavorite(pigColl.getPathInGraph()));
	}

	@Test
	public void testDeBase() {
		assertTrue("l'iterateur ne doit jamais être null", raf.getRecentsPaths() != null);
		assertTrue("l'iterateur ne doit jamais être null", raf.getFavoritesPaths() != null);
	}

	@Test
	public void testDelInFav() {
		PathInGraphCollectionBuilder pigColl = gn.getInstancePathInGraphCollectionBuilder();
		raf.addAsRecent(pigColl);
		raf.markAsFavorite(pigColl.getPathInGraph());
		raf.removeFromRecents(pigColl.getPathInGraph());
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getFavoritesPaths();
		boolean b = false;
		while (!b && it.hasNext()) {
			b |= it.next() == pigColl;
		}
		assertTrue("le trajet à été retiré des favoris, il ne devrait plus y être dans les favoris", !b);
		assertTrue("le trajet n'est pourtant plus un favoris", !raf.isFavorite(pigColl.getPathInGraph()));
	}

	@Test
	public void keepFolderClear() {
		Iterator<PathInGraphCollectionBuilder> it;
		it = raf.getRecentsPaths();
		int nbFile = 0;
		while (it.hasNext()) {
			nbFile++;
			it.next();
		}
		String path = (System.getProperty("user.home")
				+ RecentsAndFavoritesPathsInGraphReaderInFolder.PATH_TO_CONFIG_HOME_DIR + RecentsAndFavoritesPathsInGraphReaderInFolder.PIG_DIR)
				.replace("\\", "/");
		File folder = new File(path);
		assertTrue("Le nombre de fichier doit être le nombre de trajet, pas de fantome svp. ici "
				+ folder.listFiles().length + " or on ne devrais en avoir que " + nbFile,
				folder.listFiles().length == nbFile);
	}

}
