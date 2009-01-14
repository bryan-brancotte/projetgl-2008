package master;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import iGoMaster.Algo;
import iGoMaster.IGoMaster;
import iGoMaster.IHM;
import ihm.smartPhone.IGoIhmSmartPhone;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithm.Dijkstra;

public class MasterTest {
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
		return new JUnit4TestAdapter(MasterTest.class);
	}

	/**
	 * Test de bon fonctionnement des events
	 * 
	 */
	@Test
	public void askForATravel() {
		assertTrue(
				"Une demande de résolution d'un chemin null ne doit pas marcher",
				master.askForATravel(null) == false);
	}

	@Test
	/*
	 * Si le master accepte un argument vide ou null
	 */
	public void config() {
		assertTrue("un param de config doit toujours être non-null", master
				.config("") != null);
		assertTrue("un param de config doit toujours être non-null", master
				.config(null) != null);
		assertTrue("un param de config doit toujours être non-null", master
				.getConfig("") != null);
		assertTrue("un param de config doit toujours être non-null", master
				.getConfig(null) != null);
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
		assertTrue("un param de config doit toujours être non-null", master
				.getConfig("tte") != null);
		assertTrue("la valeur doit être enregistrer", master.getConfig("tte")
				.compareTo(s) == 0);
		assertTrue("la case de la valeur est importante et doit être respecté",
				(master.getConfig("tte").compareTo(s.toLowerCase()) != 0));
	}

	/**
	 * Si l'ajout et les liens de l'algo sont corrects
	 */
	@Test
	public void algo() {
		try {
			master.getAlgo().getIGoMaster();
		}
		catch (NullPointerException e){fail ("erreur non traitée");}
		Algo a = new Dijkstra();
		master.setAlgo(a);
		assertTrue("mauvais lien",master.getAlgo()==a);
		assertTrue("lien vers iGoMaster non valide",master.getAlgo().getIGoMaster()==master);
	}

	/**
	 * Si l'ajout et les liens de l'ihm sont corrects
	 */
	@Test
	public void ihm () {
		master.setIhm(null);
		IHM i = new IGoIhmSmartPhone(master);
		master.setIhm(i);
		assertTrue("mauvais lien",master.getIhm()==i);
		
	}

	/**
	 * Si l'algo s'arrete bien
	 */
	@Test
	public void stop() {
		master.stop();
	}
}