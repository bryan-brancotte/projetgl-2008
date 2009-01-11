package masterTest;

import static org.junit.Assert.assertTrue;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.IGoMaster;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Mastest {
	protected IGoMaster master;

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
		master = new IGoMaster("", "");
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(Mastest.class);
	}

	/**
	 * Test de refus d'ajout d'une route avec id null
	 * 
	 * @throws ViolationOfUnicityInIdentificationException
	 * @throws NullPointerException
	 */
	@Test
	public void addRoute1() throws ViolationOfUnicityInIdentificationException, NullPointerException {
		assertTrue("l'iterateur ne doit jamais être null", master.getNewEventInfos()!=null);
	}
}