package tools.xmlFeature;

import iGoMaster.Configuration;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigurationXML implements Configuration {

	protected static final String PATH_TO_CONFIG_HOME_DIR = "/.iGo/";

	protected static final String CONFIG_FILE = "config.xml";

	protected HashMap<String, String> conf;

	protected boolean toSave;

	public ConfigurationXML() {
		reload();
	}

	@Override
	public String getLanguage() {
		return this.getValue("Language");
	}

	@Override
	public Collection<String> getParamsKey() {
		return conf.keySet();
	}

	@Override
	public String getValue(String key) {
		String ret = conf.get(key);
		if (ret == null)
			return "";
		return ret;
	}
 
	public void reload() {
		File ficConf = new File(System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + CONFIG_FILE);
		if (!ficConf.exists()) {
			try {
				System.out.println("Missing config file, creating a new one");
				makeConfigDefaut();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Cannot creat a new configuration file\n"
						+ System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + CONFIG_FILE, "Killing error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		} else {
			try {
				Document doc;
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ficConf);
				loadParam(doc);
			} catch (Exception e) {
				try {
					System.out.println("Error config file, creating a new one");
					makeConfigDefaut();
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot creat a new configuration file\n"
							+ System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + CONFIG_FILE, "Killing error",
							JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		}
		toSave = false;

	}

	@Override
	public void save() {
		if (!toSave) {
			return;
		}
		try {
			Document document;
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Element root = document.createElement("config");
			document.appendChild(root);

			Element elt;
			for (String key : this.conf.keySet()) {
				elt = document.createElement(key);
				elt.setAttribute("value", conf.get(key));
				root.appendChild(elt);
			}

			Source source = new DOMSource(document);
			// Cration du fichier de sortie
			Result resultat = new StreamResult(new File(System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + CONFIG_FILE));

			// Configuration du transformer
			TransformerFactory fabrique = TransformerFactory.newInstance();
			Transformer transformer = fabrique.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			// Transformation
			transformer.transform(source, resultat);
			toSave = false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setValue(String key, String value) {
		toSave = true;
		this.conf.put(key, value);
	}

	protected void makeConfigDefaut() throws Exception {
		File conf = new File(System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR );
		conf.mkdir();
		conf = new File(System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + CONFIG_FILE);
		conf.createNewFile();
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root, elt;

		root = document.createElement("config");
		document.appendChild(root);

		elt = document.createElement("Language");
		elt.setAttribute("value", (new Locale(System.getProperty("user.language"))).getDisplayLanguage());
		root.appendChild(elt);

		elt = document.createElement("DEPTH_DISPLAYED");
		elt.setAttribute("value", "2");
		root.appendChild(elt);

		elt = document.createElement("QTE_CORE");
		try {
			elt.setAttribute("value", Runtime.getRuntime().availableProcessors() + "");
		} catch (Exception e) {
			elt.setAttribute("value", "2");
		}
		root.appendChild(elt);

		elt = document.createElement("DISPLAY_VARIABLE_NAME");
		elt.setAttribute("value", "false");
		root.appendChild(elt);

		elt = document.createElement("DISPLAY_VARIABLE_ICONE");
		elt.setAttribute("value", "true");
		root.appendChild(elt);

		Source source = new DOMSource(document);
		// Cration du fichier de sortie
		Result resultat = new StreamResult(conf);

		// Configuration du transformer
		TransformerFactory fabrique = TransformerFactory.newInstance();
		Transformer transformer = fabrique.newTransformer();
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		// Transformation
		transformer.transform(source, resultat);
		loadParam(document);
	}

	protected void loadParam(Document doc) {
		conf = new HashMap<String, String>();
		NodeList nodes = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (!nodes.item(i).getNodeName().startsWith("#text")) {
				conf.put(nodes.item(i).getNodeName(), nodes.item(i).getAttributes().getNamedItem("value")
						.getNodeValue());
			}
		}
	}
}
