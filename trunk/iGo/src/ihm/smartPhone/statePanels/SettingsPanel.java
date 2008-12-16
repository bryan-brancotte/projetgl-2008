package ihm.smartPhone.statePanels;

import graphNetwork.KindRoute;
import graphNetwork.Service;
import iGoMaster.IHMGraphicQuality;
import iGoMaster.SettingsKey;
import iGoMaster.SettingsValue;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.PTCheckBox;
import ihm.smartPhone.tools.PTCollapsableArea;
import ihm.smartPhone.tools.PTRadioBox;
import ihm.smartPhone.tools.PTRadioBoxGroup;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

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

	protected class PairPTCheckBox {
		public PTCheckBox chk;
		public String name;

		protected PairPTCheckBox(PTCheckBox chk, String name) {
			super();
			this.chk = chk;
			this.name = name;
		}
	}

	protected class PairPTRadioBox {
		public PTRadioBox[] rbs;
		public String name;

		protected PairPTRadioBox(PTRadioBox[] rbs, String name) {
			super();
			this.rbs = rbs;
			this.name = name;
		}
	}

	private static final long serialVersionUID = 1L;

	@Deprecated
	protected Document settings = null;

	protected final int travelModeCheckBox = 1;
	protected LinkedList<PairPTCheckBox> travelModeCheckBoxs;
	protected PTCollapsableArea travelModeCollapsableArea;

	protected final int travelCriteriaRadioBox = 2;
	protected PTRadioBox[] travelCriteriaRadioBoxs;
	protected PTCollapsableArea travelCriteriaCollapsableArea;

	protected final int servicesRadioBox = 3;
	protected LinkedList<PairPTRadioBox> ServicesRadioBoxs;
	protected PTCollapsableArea servicesCollapsableArea;

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
		String s, valS;
		PTRadioBoxGroup grp;
		CodeExecutor ex;

		/***************************************************************************************************************
		 * Travel criteria
		 */
		travelCriteriaCollapsableArea = makeCollapsableArea();
		grp = new PTRadioBoxGroup(3);
		ex = new CodeExecutor() {
			@Override
			public void execute() {
				recordChangedSetting(travelCriteriaRadioBox, SettingsKey.TRAVEL_CRITERIA.getValue());
			}
		};
		travelCriteriaRadioBoxs = new PTRadioBox[3];
		travelCriteriaRadioBoxs[0] = makeRadioButton(grp, ex);// Cheaper
		travelCriteriaRadioBoxs[1] = makeRadioButton(grp, ex);// Faster
		travelCriteriaRadioBoxs[2] = makeRadioButton(grp, ex);// Fewer Changes
		travelCriteriaRadioBoxs[0].setClicked(father.getConfig("TRAVEL_CRITERIA").compareTo("0") == 0);
		travelCriteriaRadioBoxs[1].setClicked(father.getConfig("TRAVEL_CRITERIA").compareTo("1") == 0);
		travelCriteriaRadioBoxs[2].setClicked(father.getConfig("TRAVEL_CRITERIA").compareTo("2") == 0);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[0]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[1]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[2]);

		/***************************************************************************************************************
		 * Travel Mode
		 */
		travelModeCollapsableArea = makeCollapsableArea();
		travelModeCheckBoxs = new LinkedList<PairPTCheckBox>();
		Iterator<KindRoute> itR = father.getKindRoutes();
		PTCheckBox chk;
		while (itR.hasNext()) {
			chk = makeCheckBox(new CodeExecutor1P<String>(s = (itR.next().getKindOf())) {
				@Override
				public void execute() {
					recordChangedSetting(travelModeCheckBox, this.origine);
				}
			});
			chk.setClicked(father.getConfig(SettingsKey.TRAVEL_MODE + s).compareTo("1") == 0);
			travelModeCollapsableArea.addComponent(chk);
			travelModeCheckBoxs.add(new PairPTCheckBox(chk, s));
		}

		/***************************************************************************************************************
		 * Services
		 */
		servicesCollapsableArea = makeCollapsableArea();
		servicesCollapsableArea.changeCollapseState();
		ServicesRadioBoxs = new LinkedList<PairPTRadioBox>();
		Iterator<Service> itS = father.getServices();
		PTRadioBox[] rbs;
		while (itS.hasNext()) {
			rbs = new PTRadioBox[3];
			grp = new PTRadioBoxGroup(rbs.length);
			ex = new CodeExecutor1P<String>(s = (itS.next().getName())) {
				@Override
				public void execute() {
					recordChangedSetting(servicesRadioBox, this.origine);
				}
			};
			for (int i = 0; i < rbs.length; i++) {
				rbs[i] = makeRadioButton(grp, ex);
				servicesCollapsableArea.addComponent(rbs[i]);
			}
			valS = father.getConfig(SettingsKey.SERVICES + s);
			if (valS.compareTo("1") == 0)
				rbs[1].setClicked(true);
			else if (valS.compareTo("2") == 0)
				rbs[2].setClicked(true);
			else
				rbs[0].setClicked(true);
			ServicesRadioBoxs.add(new PairPTRadioBox(rbs, s));
		}
	}

	protected void recordChangedSetting(int familly, String s) {
		switch (familly) {
		case travelModeCheckBox:
			for (PairPTCheckBox p : travelModeCheckBoxs)
				if (p.name.compareTo(s) == 0) {
					if (p.chk.isClicked())
						father.setConfig(SettingsKey.TRAVEL_MODE + s, SettingsValue.ENABLE.getStringValue());
					else
						father.setConfig(SettingsKey.TRAVEL_MODE + s, SettingsValue.DISABLE.getStringValue());
					return;
				}
			break;
		case travelCriteriaRadioBox:
			if (travelCriteriaRadioBoxs[0].isClicked()) {
				father.setConfig(s, SettingsValue.CHEAPER.getStringValue());
				return;
			}
			if (travelCriteriaRadioBoxs[1].isClicked()) {
				father.setConfig(s, SettingsValue.FASTER.getStringValue());
				return;
			}
			if (travelCriteriaRadioBoxs[2].isClicked()) {
				father.setConfig(s, SettingsValue.FEWER_CHANGES.getStringValue());
				return;
			}
			break;
		case servicesRadioBox:
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				if (s.compareTo(p.name) == 0) {
					if (p.rbs[0].isClicked()) {
						father.setConfig(SettingsKey.SERVICES + s, SettingsValue.Idle.getStringValue());
						return;
					}
					if (p.rbs[1].isClicked()) {
						father.setConfig(SettingsKey.SERVICES + s, SettingsValue.Once.getStringValue());
						return;
					}
					if (p.rbs[2].isClicked()) {
						father.setConfig(SettingsKey.SERVICES + s, SettingsValue.Always.getStringValue());
						return;
					}
				}
			}
		default:
			System.out.println("Not Handeled : " + familly + " : " + s);
			break;
		}
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
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int ordonne = decalage;
		int width;
		int[] pos;
		int tmp;
		// int heigth;
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
		} else {
			buffer.clearRect(0, 0, getWidth(), getHeight());
		}

		/***************************************************************************************************************
		 * Travel criteria
		 */
		s = father.lg("TravelCriteria");
		if (!travelCriteriaCollapsableArea.isCollapsed()) {
			travelCriteriaRadioBoxs[0].prepareArea(buffer, decalage + (decalage >> 1),
					travelCriteriaCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
							.getSizeAdapteur().getLargeFont()), father.lg("Cheaper"), father.getSizeAdapteur()
							.getSmallFont());
			travelCriteriaRadioBoxs[1].prepareArea(buffer, travelCriteriaRadioBoxs[0].getArea().x
					+ travelCriteriaRadioBoxs[0].getArea().width + (decalage >> 1), travelCriteriaRadioBoxs[0]
					.getArea().y, father.lg("Faster"), father.getSizeAdapteur().getSmallFont());
			travelCriteriaRadioBoxs[2].prepareArea(buffer, travelCriteriaRadioBoxs[1].getArea().x
					+ travelCriteriaRadioBoxs[1].getArea().width + (decalage >> 1), travelCriteriaRadioBoxs[0]
					.getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur().getSmallFont());
		}
		travelCriteriaCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getLargeFont(),
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		travelCriteriaRadioBoxs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
				.getColorSubAreaInside(), father.getSkin().getColorLetter());
		travelCriteriaRadioBoxs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
				.getColorSubAreaInside(), father.getSkin().getColorLetter());
		travelCriteriaRadioBoxs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
				.getColorSubAreaInside(), father.getSkin().getColorLetter());

		/***************************************************************************************************************
		 * Travel mode
		 */
		ordonne = travelCriteriaCollapsableArea.getArea().y + travelCriteriaCollapsableArea.getArea().height
				+ (decalage << 1);
		s = father.lg("TravelMode");
		PTCheckBox chk = null;
		if (!travelModeCollapsableArea.isCollapsed()) {
			for (PairPTCheckBox p : travelModeCheckBoxs) {
				p.chk.prepareArea(buffer, (chk == null) ? decalage + (decalage >> 1) : chk.getArea().x
						+ chk.getArea().width + (decalage >> 1), (chk == null) ? travelCriteriaCollapsableArea
						.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
								.getLargeFont()) : chk.getArea().y, p.name, father.getSizeAdapteur().getSmallFont());
				if ((p.chk.getArea().x + p.chk.getArea().width) > (getWidth() - decalage)) {
					p.chk.prepareArea(buffer, decalage + (decalage >> 1), p.chk.getArea().y + p.chk.getArea().height
							+ (decalage >> 1), p.name, father.getSizeAdapteur().getSmallFont());
				}
				chk = p.chk;
			}
		}
		travelModeCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getLargeFont(), father
				.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!travelModeCollapsableArea.isCollapsed())
			for (PairPTCheckBox p : travelModeCheckBoxs)
				p.chk.draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin().getColorSubAreaInside(),
						father.getSkin().getColorLetter());

		/***************************************************************************************************************
		 * Services
		 */
		ordonne = travelModeCollapsableArea.getArea().y + travelModeCollapsableArea.getArea().height + (decalage << 1);
		s = father.lg("Services");
		if (!servicesCollapsableArea.isCollapsed()) {
			pos = new int[3];
			width = 0;
			// on trouve la largueur de la colonne des services
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp = getWidthString(p.name, buffer, father.getSizeAdapteur().getSmallFont());
				if (tmp > width)
					width = tmp;
			}
			// on calcul les positions des 3 colone de valehbur des services
			tmp = (((getWidth() - (decalage << 1) - width) / 3) >> 1);
			pos[0] = decalage + width + tmp;
			pos[1] = pos[0] + (tmp << 1);
			pos[2] = pos[1] + (tmp << 1);
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getLargeFont());
			width = getHeigthString("", buffer, father.getSizeAdapteur().getSmallFont()) + (decalage >> 1);
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp += width;
				for (int i = 0; i < pos.length; i++) {
					p.rbs[i].prepareArea(buffer, pos[i], tmp, "", father.getSizeAdapteur().getSmallFont(), true, false);
				}
			}
			servicesCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getLargeFont(),
					father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			// ____________Idle_Once_Always
			// __Coffre_____O_____O____X___
			// __Handi______O_____X____O___
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getLargeFont());
			buffer.setFont(father.getSizeAdapteur().getSmallFont());
			buffer.drawString(SettingsValue.Idle.toString(), pos[0]
					- (getWidthString(SettingsValue.Idle.toString(), buffer) >> 1), tmp);
			buffer.drawString(SettingsValue.Once.toString(), pos[1]
					- (getWidthString(SettingsValue.Once.toString(), buffer) >> 1), tmp);
			buffer.drawString(SettingsValue.Always.toString(), pos[2]
					- (getWidthString(SettingsValue.Always.toString(), buffer) >> 1), tmp);
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getLargeFont());
			width = getHeigthString("", buffer, father.getSizeAdapteur().getSmallFont());
			tmp += width;
			width += (decalage >> 1);
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp += width;
				buffer.setFont(father.getSizeAdapteur().getSmallFont());
				buffer.drawString(p.name, decalage << 1, tmp);
				p.rbs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			}
			// p.chk.getArea().y + p.chk.getArea().height + (decalage >> 1)
			// TODO ...........taf en cours....................
		} else {
			servicesCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getLargeFont(),
					father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		}

		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
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
