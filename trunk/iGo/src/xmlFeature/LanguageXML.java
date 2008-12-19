package xmlFeature;

import iGoMaster.Language;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LanguageXML implements Language {

	protected static final String PATH_LANGUAGE = "/languages/languages.xml";

	protected HashMap<String, String> correspondanceKeyWord;

	protected HashMap<String, String> langues;

	protected String langue = "";

	protected boolean nvLangue = false;

	public LanguageXML() {
		this((new Locale(System.getProperty("user.language"))).getDisplayLanguage());
	}

	public LanguageXML(String lang) {
		super();
		correspondanceKeyWord = new HashMap<String, String>();
		langues = new HashMap<String, String>();
		LoadLanguages();
		setLanguage(lang);
	}

	public LanguageXML(String lang, boolean forceChoixLangue) {
		super();
		correspondanceKeyWord = new HashMap<String, String>();
		langues = new HashMap<String, String>();
		LoadLanguages();
		if (!setLanguage(lang) && forceChoixLangue) {
			nvLangue = true;
			langue = lang;
			LoadLanguage();
		}
	}

	@Override
	public String getLanguage() {
		return langue;
	}

	@Override
	public String lg(String key) {
		String ret = correspondanceKeyWord.get(key);
		if (ret == null) {
			ret = correspondanceKeyWord.get("KeyUnknown") + ":" + key;
		}
		return ret;
	}

	@Override
	public boolean setLanguage(String name) {
		if (langues.containsKey(name)) {
			langue = name;
			LoadLanguage();
			return true;
		}
		String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		if (langues.containsKey(nameCap)) {
			langue = nameCap;
			LoadLanguage();
			return true;
		}
		nameCap = null;
		boolean alreadyMatch = false;
		for (String s : langues.keySet()) {
			if(s.length() >= name.length()) 
			if (s.length() >= name.length() && s.substring(0, name.length()).compareToIgnoreCase(name) == 0)
				if (nameCap != null)
					alreadyMatch = true;
				else
					nameCap = s;
		} 
		if (nameCap != null && !alreadyMatch) {
			langue = nameCap;
			LoadLanguage();
			return true;
		}
		if (langue.compareTo(NOT_YET_A_LANGUAGE) == 0)
			langue = DEFAULT_LANGUAGE;
		return false;
	}

	protected boolean LoadLanguages() {
		langue = "Loading...";
		NodeList nodesLangue = null;
		int i;
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					getClass().getResourceAsStream(PATH_LANGUAGE));
			/***********************************************************************************************************
			 * Chargement des langues
			 */
			i = 0;
			while ((nodesLangue == null) && (i < doc.getFirstChild().getChildNodes().getLength())) {
				if (doc.getFirstChild().getChildNodes().item(i).getNodeName().compareTo("languages") == 0) {
					nodesLangue = doc.getFirstChild().getChildNodes().item(i).getChildNodes();
				}
				i++;
			}
			for (i = 0; i < nodesLangue.getLength(); i++) {
				if (nodesLangue.item(i).getNodeName().compareTo("#text") != 0) {
					if (!nodesLangue.item(i).getNodeName().startsWith("#"))
						langues.put(nodesLangue.item(i).getAttributes().getNamedItem("value").getNodeValue(),
								nodesLangue.item(i).getAttributes().getNamedItem("balise").getNodeValue());
				}
			}
		} catch (SAXException e) {
			System.err.println("Err ds LoadLanguages");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Err ds LoadLanguages");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.println("Err ds LoadLanguages");
			e.printStackTrace();
		}
		return true;
	}

	protected boolean LoadLanguage() {
		NodeList nodesLangue = null;
		Node node = null;
		correspondanceKeyWord.clear();
		boolean ok;
		int i;
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					getClass().getResourceAsStream(PATH_LANGUAGE));
			i = 0;
			while ((nodesLangue == null) && (i < doc.getFirstChild().getChildNodes().getLength())) {
				if (doc.getFirstChild().getChildNodes().item(i).getNodeName().compareTo("fields") == 0) {
					nodesLangue = doc.getFirstChild().getChildNodes().item(i).getChildNodes();
				}
				i++;
			}
			for (i = 0; i < nodesLangue.getLength(); i++) {
				node = nodesLangue.item(i);
				if (node.getNodeName().compareTo("#text") != 0) {
					ok = false;
					for (int j = 0; j < node.getChildNodes().getLength(); j++) {
						if (node.getChildNodes().item(j).getNodeName().compareTo(langues.get(langue)) == 0) {
							correspondanceKeyWord.put(node.getNodeName(), node.getChildNodes().item(j).getAttributes()
									.getNamedItem("value").getNodeValue());
							ok = true;
						}
					}
					if (!ok) {
						correspondanceKeyWord.put(node.getNodeName(), "UnknownKey:" + node.getNodeName());
					}
				}
			}
		} catch (SAXException e) {
			System.err.println("Err ds LoadLanguage");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Err ds LoadLanguage");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.println("Err ds LoadLanguage");
			e.printStackTrace();
		}
		return true;
	}

	public Set<String> getCles() {
		return correspondanceKeyWord.keySet();
	}

	public boolean isNvLangue() {
		return nvLangue;
	}

	public boolean containsLanguageKey(String key) {
		return langues.containsKey(key);
	}

	@Override
	public Collection<String> getLanguages() {
		return langues.keySet();
	}

}
