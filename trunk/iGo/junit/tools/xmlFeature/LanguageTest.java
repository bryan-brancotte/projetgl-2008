package tools.xmlFeature;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import iGoMaster.Language;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LanguageTest {

	protected Language lang = null;

	protected Language langDejaInit = new LanguageXML();

	@After
	public void epilogueDateTest() {
	}

	@Before
	public void prologueDateTest() {
	}

	/**
	 * Test si l'objet est bien mit avec la langue du système si elle est présente, où dans la langue par défaut le cas
	 * échéant
	 */
	@Test
	public void langueSystemOuParDefautAuDemarrage() {
		lang = new LanguageXML();
		boolean existingLang = false;
		String sysLang = (new Locale(System.getProperty("user.language"))).getDisplayLanguage();
		for (String s : lang.getLanguages())
			existingLang = existingLang || (s.compareToIgnoreCase(sysLang) == 0);
		if (existingLang)
			assertTrue(lang.getLanguage().compareToIgnoreCase(sysLang) == 0);
		else
			assertTrue(lang.getLanguage().compareTo(Language.DEFAULT_LANGUAGE) == 0);
	}

	/**
	 * Test si la langue par défaut es bien mise si on utilise une langue inconnue
	 */
	@Test
	public void langueParDefautQuandLangueInconnue() {
		lang = new LanguageXML("tagada");
		assertTrue(lang.getLanguage().compareTo(Language.DEFAULT_LANGUAGE) == 0);
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(LanguageTest.class);
	}
}
