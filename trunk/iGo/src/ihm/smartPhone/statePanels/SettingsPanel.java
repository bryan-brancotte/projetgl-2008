package ihm.smartPhone.statePanels;

import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.PTCollapsableArea;
import ihm.smartPhone.tools.PTRadioBox;
import ihm.smartPhone.tools.PTRadioBoxGroup;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

public class SettingsPanel extends PanelState {

	private static final long serialVersionUID = 1L;

	protected Document settings = null;

	protected PTRadioBox cheapper;
	protected PTRadioBox faster;
	protected PTRadioBox fewer;
	protected PTCollapsableArea travelCriteria;

	// protected Panel insidePanel;

	public SettingsPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar, Document settings) {
		super(ihm, upperBar, lowerBar);
		try {
			if (settings == null)
				this.settings = buildExempleSettingXML();
			else
				this.settings = settings;
		} catch (ParserConfigurationException e) {
			return;
		}
		/*
		 * this.setLayout(new BorderLayout()); insidePanel = new Panel(new VerticalFlowLayout()); ScrollPane scrollPane =
		 * new ScrollPane(); scrollPane.add(insidePanel); this.add(scrollPane);
		 */
		buildInterfaceFromDomDocument();
	}

	protected void buildInterfaceFromDomDocument() {
		PTRadioBoxGroup grp = new PTRadioBoxGroup(3);
		cheapper = makeRadioButton(grp);
		faster = makeRadioButton(grp);
		fewer = makeRadioButton(grp);
		travelCriteria = makeCollapsableArea();
		travelCriteria.addComponent(cheapper);
		travelCriteria.addComponent(fewer);
		travelCriteria.addComponent(faster);
	}

	protected Document buildExempleSettingXML() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root, elt, child;

		root = document.createElement("Settings");
		document.appendChild(root);

		elt = document.createElement("Setting");
		elt.setAttribute("text", "TravelCriteria");
		elt.setAttribute("type", "radiobox");
		elt.setAttribute("value", "1");
		child = document.createElement("enum");
		child.setAttribute("text", "Cheaper");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child = document.createElement("enum");
		child.setAttribute("text", "Faster");
		child.setAttribute("value", "2");
		elt.appendChild(child);
		child = document.createElement("enum");
		child.setAttribute("text", "FewerChanges");
		child.setAttribute("value", "3");
		elt.appendChild(child);
		root.appendChild(elt);

		elt = document.createElement("Setting");
		elt.setAttribute("text", "TravelMode");
		elt.setAttribute("type", "checkbox");
		elt.setAttribute("value", "");
		child = document.createElement("enum");
		child.setAttribute("text", "Trolley");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child = document.createElement("enum");
		child.setAttribute("text", "Subway");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child = document.createElement("enum");
		child.setAttribute("text", "Train");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		root.appendChild(elt);

		// TODO retirer l'export fichier des paramètres
		try {
			Source source = new DOMSource(document);
			// Cration du fichier de sortie
			Result resultat = new StreamResult(new File("c:\\ExempleSettingXML.xml"));

			// Configuration du transformer
			TransformerFactory fabrique = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = fabrique.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			// Transformation
			transformer.transform(source, resultat);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return document;
	}

	@Override
	public void paint(Graphics g) {
		int decalage = father.getSizeAdapteur().getSizeLargeFont();
		int ordonne = decalage;
		//int heigth;
		String s;
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			if (this.getQuality().getValue() >= IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
				buffer = (Graphics2D) /* */image.getGraphics();
				((Graphics2D) buffer).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			} else {
				buffer = image.getGraphics();
			}
			buffer.setColor(father.getSkin().getColorLetter());
		}
		// buffer.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
		s = father.lg("TravelCriteria");
		travelCriteria.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
				.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("Settings"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
	}

}
