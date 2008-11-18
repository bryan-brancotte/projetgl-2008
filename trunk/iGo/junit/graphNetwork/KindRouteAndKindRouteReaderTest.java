package graphNetwork;

import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KindRouteAndKindRouteReaderTest {

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {

	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(KindRouteAndKindRouteReaderTest.class);
	}

	@Test
	public void resetTest() {
		KindRoute.reset();
		assertTrue(KindRoute.getKinds().size() == 0);
		KindRoute.addKind("toto");
		KindRoute.reset();
		assertTrue(KindRoute.getKinds().size() == 0);
	}

	@Test
	public void addTest() {
		assertTrue(KindRoute.getKindFromString("toto") == null);
		KindRoute.addKind("toto");
		assertTrue(KindRoute.getKindFromString("toto") != null);
		assertTrue(KindRoute.getKindFromString("toto").getKindOf().compareTo("Toto") != 0);
		KindRoute.addKind("tata");
		assertTrue(KindRoute.getKindFromString("toto") != null);
		assertTrue(KindRoute.getKindFromString("tata") != null);
		assertTrue(KindRoute.getKinds().size()==2);
	}

	@Test
	public void getKindFromStringTest() {
		KindRoute.addKind("toto");
		assertTrue(KindRoute.getKindFromString("toto") != null);
		assertTrue(KindRoute.getKindFromString("toto").getKindOf().compareTo("toto") == 0);
	}

	@Test
	public void getKindFromStringInKindRouteReaderTest() {
		KindRoute.addKind("toto");
		KindRoute.addKind("tata");
		assertTrue(KindRouteReader.getKindFromString("toto") != null);
		assertTrue(KindRouteReader.getKindFromString("tata") != null);
		assertTrue(KindRoute.getKinds().size()==2);
	}

	@Test
	public void getKindsTest() {
		KindRoute.addKind("toto");
		KindRoute.addKind("tata");
		assertTrue(KindRoute.getKinds().size()==2);
		assertTrue(KindRouteReader.getKindsR().length==KindRoute.getKinds().size());
		for(KindRouteReader k : KindRouteReader.getKindsR()){
			assertTrue(KindRoute.getKindFromString(k.getKindOf())!=null);
		}
	}
	
	@Test
	public void equalTest(){
		KindRoute.addKind("toto");
		KindRoute.addKind("tata");
		KindRoute toto = KindRoute.getKindFromString("toto");
		KindRoute toto2 = KindRoute.getKindFromString("toto");
		KindRouteReader totoReader = KindRoute.getKindFromString("toto");
		KindRoute tata = KindRoute.getKindFromString("toto");		
		assertTrue("inégalité entre 2 kind différent",toto!=tata);
		assertTrue("inégalité entre kind et un kindReader différent",totoReader!=tata);
		assertTrue("égalité entre 2 kind",toto==toto);
		assertTrue("égalité entre Kind et KindReader",toto==totoReader);
		assertTrue("égalité entre 2 kind",toto2==toto);
		assertTrue("égalité entre Kind et KindReader",toto2==totoReader);
	}
}
