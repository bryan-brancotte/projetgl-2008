package masterTest;

import static org.junit.Assert.assertTrue;
import iGoMaster.IGoMaster;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import iGoMaster.exception.NoNetworkException;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Mastest {
	protected IGoMaster master;

	@After
	public void epilogueDateTest() {
		master.stop();
	}

	@Before
	public void prologueDateTest() {
		master = new IGoMaster("", "");
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(Mastest.class);
	}

	/**
	 * Test de bon fonctionnement des events
	 * 
	 */
	@Test
	public void askForATravel() {
		assertTrue("Une demande de résolution d'un chemin null ne doit pas marcher",
				master.askForATravel(null) == false);
	}

	@Test
	public void config() {
		assertTrue("un param de config doit toujours être non-null", master.config("") != null);
		assertTrue("un param de config doit toujours être non-null", master.config(null) != null);
		assertTrue("un param de config doit toujours être non-null", master.getConfig("") != null);
		assertTrue("un param de config doit toujours être non-null", master.getConfig(null) != null);
		String s = System.nanoTime() + "789£";
		int i = 0;
		while (!s.contains(i + ""))
			i++;
		s = s.replace(i + "", "a");
		i = 0;
		while (!s.contains(i + ""))
			i++;
		s = s.replace(i + "", "%");
		i = 0;
		while (!s.contains(i + ""))
			i++;
		s = s.replace(i + "", "E");
		master.setConfig("tte", s);
		assertTrue("un param de config doit toujours être non-null", master.getConfig("tte") != null);
		assertTrue("la valeur doit être enregistrer", master.getConfig("tte").compareTo(s) == 0);
		assertTrue("la case de la valeur est importante et doit être respecté", (master.getConfig("tte").compareTo(
				s.toLowerCase()) != 0));
	}

	@Test
	public void favAndRecent() {
		try {
			master.delete(null);
		} catch (Exception e) {
			assertTrue("master.delete(null); ne doit pas planter", false);
		}
		try {
			master.delete(master.getPathInGraphConstraintBuilder().getCurrentPathInGraph());
		} catch (Exception e) {
			assertTrue(
					"master.delete(master.getPathInGraphConstraintBuilder().getCurrentPathInGraph()); ne doit pas planter",
					false);
		}
		assertTrue("l'iterateur ne doit jamais être null", master.getNewEventInfos() != null);
		assertTrue("l'iterateur ne doit jamais être null", master.getNewEventInfos().hasNext() == master
				.hasNewEventInfos());
	}

	@Test
	public void favAndRecents() {
		master.
	}
}