package xmlFeature;

import static org.junit.Assert.assertTrue;
import iGoMaster.Language;

import java.util.Locale;

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
	 * Test si la langue par défaut (préférence système, puis anglais) est bien mise si on utilise une langue inconnue
	 */
	@Test
	public void langueParDefautQuandLangueInconnue() {
		lang = new LanguageXML("tagada");
		LanguageXML l2 = new LanguageXML();
		assertTrue(lang.getLanguage().compareTo(l2.getLanguage()) == 0);
	}

	/**
	 * Test si la langue par défaut es bien mise si on utilise une langue inconnue
	 */
	@Test
	public void setLanguage() {
		lang = new LanguageXML("english");
		lang.setLanguage("eee");
		assertTrue(lang.getLanguage().compareTo("English") == 0);
		lang.setLanguage("aze");
		lang.setLanguage("Français");
		assertTrue(lang.getLanguage().compareTo("Français") == 0);
		lang.setLanguage("azeaze");
		lang.setLanguage("english");
		assertTrue(lang.getLanguage().compareTo("English") == 0);
		lang.setLanguage("azeazeaze");
		lang.setLanguage("Fr");
		assertTrue(lang.getLanguage().compareTo("Français") == 0);
		lang.setLanguage("azeaze");
		lang.setLanguage("english");
		assertTrue(lang.getLanguage().compareTo("English") == 0);
		lang.setLanguage("zze");
		lang.setLanguage("fr");
		assertTrue(lang.getLanguage().compareTo("Français") == 0);
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(LanguageTest.class);
	}
}
