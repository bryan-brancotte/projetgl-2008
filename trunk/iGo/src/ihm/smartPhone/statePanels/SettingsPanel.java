package ihm.smartPhone.statePanels;

import graphNetwork.KindRoute;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.IHMGraphicQuality;
import iGoMaster.SettingsKey;
import iGoMaster.SettingsValue;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.PTArea;
import ihm.smartPhone.tools.PTAutoCompletionTextBox;
import ihm.smartPhone.tools.PTButton;
import ihm.smartPhone.tools.PTCheckBox;
import ihm.smartPhone.tools.PTCollapsableArea;
import ihm.smartPhone.tools.PTRadioBox;
import ihm.smartPhone.tools.PTRadioBoxGroup;
import ihm.smartPhone.tools.PTScrollBar;
import ihm.smartPhone.tools.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.PanelTooled;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;

public class SettingsPanel extends PanelState {

	protected HashMap<String, Station> sationsHash;

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

	protected String[] stations;
	protected int deroullement;

	protected ImageIcon imageOk;
	protected ImageIcon imageDel;

	protected final int travelMode = 1;
	protected LinkedList<PairPTCheckBox> travelModeCheckBoxs;
	protected PTCollapsableArea travelModeCollapsableArea;

	protected final int travelCriteria = 2;
	protected PTRadioBox[] travelCriteriaRadioBoxs;
	protected PTCollapsableArea travelCriteriaCollapsableArea;

	protected final int services = 3;
	protected LinkedList<PairPTRadioBox> ServicesRadioBoxs;
	protected PTCollapsableArea servicesCollapsableArea;

	protected final int quality = 4;
	protected PTRadioBox[] qualityRadioBoxs;
	protected PTCollapsableArea qualityCollapsableArea;

	protected final int intermediatesStations = 5;
	protected PTArea intermediatesStationsNew;
	protected PTAutoCompletionTextBox intermediatesStationsTextBox;
	protected PTCollapsableArea intermediatesStationsCollapsableArea;
	protected PTButton intermediatesStationsButton;
	protected PTScrollBar scrollBar;

	public SettingsPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
		deroullement = 0;
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
				recordChangedSetting(travelCriteria, SettingsKey.TRAVEL_CRITERIA.toString());
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
					recordChangedSetting(travelMode, this.origine);
				}
			});
			chk.setClicked(father.getConfig(SettingsKey.TRAVEL_MODE_ + s).compareTo("1") == 0);
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
					recordChangedSetting(services, this.origine);
				}
			};
			for (int i = 0; i < rbs.length; i++) {
				rbs[i] = makeRadioButton(grp, ex);
				servicesCollapsableArea.addComponent(rbs[i]);
			}
			valS = father.getConfig(SettingsKey.SERVICES_ + s);
			if (valS.compareTo("1") == 0)
				rbs[1].setClicked(true);
			else if (valS.compareTo("2") == 0)
				rbs[2].setClicked(true);
			else
				rbs[0].setClicked(true);
			ServicesRadioBoxs.add(new PairPTRadioBox(rbs, s));
		}

		/***************************************************************************************************************
		 * Quality
		 */
		qualityCollapsableArea = makeCollapsableArea();
		qualityCollapsableArea.changeCollapseState();
		grp = new PTRadioBoxGroup(4);
		ex = new CodeExecutor1P<PanelTooled>(this) {
			@Override
			public void execute() {
				recordChangedSetting(quality, "GRAPHICAL_QUALITY");
				// this.origine.;
			}
		};
		qualityRadioBoxs = new PTRadioBox[4];
		qualityRadioBoxs[0] = makeRadioButton(grp, ex);// AS_FAST_AS_WE_CAN
		qualityRadioBoxs[1] = makeRadioButton(grp, ex);// TEXT_ANTI_ANTIALIASING
		qualityRadioBoxs[2] = makeRadioButton(grp, ex);// FULL_ANTI_ANTIALIASING
		qualityRadioBoxs[3] = makeRadioButton(grp, ex);// HIGHER_QUALITY
		int i = IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue();
		try {
			i = Integer.parseInt(father.getConfig("GRAPHICAL_QUALITY"));
		} catch (NumberFormatException e) {
			father.setConfig("GRAPHICAL_QUALITY", i + "");
			this.setQuality(IHMGraphicQuality.FULL_ANTI_ANTIALIASING);
		}
		qualityRadioBoxs[0].setClicked(IHMGraphicQuality.AS_FAST_AS_WE_CAN.getValue() == i);
		qualityRadioBoxs[1].setClicked(IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue() == i);
		qualityRadioBoxs[2].setClicked(IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue() == i);
		qualityRadioBoxs[3].setClicked(IHMGraphicQuality.HIGHER_QUALITY.getValue() == i);
		qualityCollapsableArea.addComponent(qualityRadioBoxs[0]);
		qualityCollapsableArea.addComponent(qualityRadioBoxs[1]);
		qualityCollapsableArea.addComponent(qualityRadioBoxs[2]);
		qualityCollapsableArea.addComponent(qualityRadioBoxs[3]);

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar = makeScrollBar();

		/***************************************************************************************************************
		 * AutoCompletionTextBox : intermediatesStationsTextBox
		 */
		intermediatesStationsCollapsableArea = makeCollapsableArea();
		intermediatesStationsNew = makeArea();
		Iterator<Station> it = father.getStations();
		sationsHash = new HashMap<String, Station>();
		Station st;
		while (it.hasNext()) {
			st = it.next();
			sationsHash.put(st.getName(), st);
		}
		intermediatesStationsTextBox = makeAutoCompletionTextBox(sationsHash.keySet().toArray(new String[0]));
		intermediatesStationsButton = makeButton(new CodeExecutor1P<PanelTooled>(this) {
			@Override
			public void execute() {
				recordChangedSetting(intermediatesStations, null);
				// this.origine.;
			}
		});
		intermediatesStationsCollapsableArea.addComponent(intermediatesStationsNew);
		intermediatesStationsCollapsableArea.addComponent(intermediatesStationsTextBox);
	}

	protected void recordChangedSetting(int familly, String s) {
		switch (familly) {
		case travelMode:
			for (PairPTCheckBox p : travelModeCheckBoxs)
				if (p.name.compareTo(s) == 0) {
					if (p.chk.isClicked())
						father.setConfig(SettingsKey.TRAVEL_MODE_ + s, SettingsValue.ENABLE.getStringValue());
					else
						father.setConfig(SettingsKey.TRAVEL_MODE_ + s, SettingsValue.DISABLE.getStringValue());
					return;
				}
			break;
		case travelCriteria:
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
		case services:
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				if (s.compareTo(p.name) == 0) {
					if (p.rbs[0].isClicked()) {
						father.setConfig(SettingsKey.SERVICES_ + s, SettingsValue.Idle.getStringValue());
						return;
					}
					if (p.rbs[1].isClicked()) {
						father.setConfig(SettingsKey.SERVICES_ + s, SettingsValue.Once.getStringValue());
						return;
					}
					if (p.rbs[2].isClicked()) {
						father.setConfig(SettingsKey.SERVICES_ + s, SettingsValue.Always.getStringValue());
						return;
					}
				}
			}
		case quality:
			if (qualityRadioBoxs[0].isClicked()) {
				father.setConfig(s, IHMGraphicQuality.AS_FAST_AS_WE_CAN.getValue() + "");
				this.setQuality(IHMGraphicQuality.AS_FAST_AS_WE_CAN);
				return;
			}
			if (qualityRadioBoxs[1].isClicked()) {
				father.setConfig(s, IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue() + "");
				this.setQuality(IHMGraphicQuality.TEXT_ANTI_ANTIALIASING);
				return;
			}
			if (qualityRadioBoxs[2].isClicked()) {
				father.setConfig(s, IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue() + "");
				this.setQuality(IHMGraphicQuality.FULL_ANTI_ANTIALIASING);
				return;
			}
			if (qualityRadioBoxs[3].isClicked()) {
				father.setConfig(s, IHMGraphicQuality.HIGHER_QUALITY.getValue() + "");
				this.setQuality(IHMGraphicQuality.HIGHER_QUALITY);
				return;
			}
			break;
		case intermediatesStations:
			System.out.println("Intermediates stations not handeled yet");
			break;
		default:
			System.out.println("Not handeled : " + familly + " : " + s);
			break;
		}
	}

	@Override
	public void paint(Graphics g) {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int decalage2 = (decalage << 1);
		int ordonne = decalage - deroullement;
		int width;
		int[] pos;
		byte[] ord;
		int tmp;
		int cpt;
		// int heigth;
		String s;
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
			imageOk = null;
			imageDel = null;
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			buffer.setColor(father.getSkin().getColorLetter());
			if (imageOk == null || imageOk.getIconHeight() != father.getSizeAdapteur().getSizeSmallFont()) {
				imageOk = ImageLoader.getRessourcesImageIcone("button_ok", father.getSizeAdapteur().getSizeSmallFont(),
						father.getSizeAdapteur().getSizeSmallFont());
				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
						.getSizeSmallFont(), father.getSizeAdapteur().getSizeSmallFont());
			}
		} else {
			buffer.clearRect(0, 0, getWidth(), getHeight());
		}

		/***************************************************************************************************************
		 * Travel criteria
		 */
		s = father.lg("TravelCriteria");
		if (!travelCriteriaCollapsableArea.isCollapsed()) {
			width = getWidth() - decalage2;
			travelCriteriaRadioBoxs[0].prepareArea(buffer, decalage + (decalage >> 1), travelCriteriaCollapsableArea
					.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
							.getIntermediateFont()), father.lg("Cheaper"), father.getSizeAdapteur().getSmallFont());

			travelCriteriaRadioBoxs[1].prepareArea(buffer, travelCriteriaRadioBoxs[0].getArea().x
					+ travelCriteriaRadioBoxs[0].getArea().width + (decalage >> 1), travelCriteriaRadioBoxs[0]
					.getArea().y, father.lg("Faster"), father.getSizeAdapteur().getSmallFont());
			if ((travelCriteriaRadioBoxs[1].getArea().x + travelCriteriaRadioBoxs[1].getArea().width) > width)
				travelCriteriaRadioBoxs[1].prepareArea(buffer, decalage + (decalage >> 1), travelCriteriaRadioBoxs[1]
						.getArea().y
						+ travelCriteriaRadioBoxs[1].getArea().height + (decalage >> 1), father.lg("Faster"), father
						.getSizeAdapteur().getSmallFont());

			travelCriteriaRadioBoxs[2].prepareArea(buffer, travelCriteriaRadioBoxs[1].getArea().x
					+ travelCriteriaRadioBoxs[1].getArea().width + (decalage >> 1), travelCriteriaRadioBoxs[1]
					.getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur().getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((travelCriteriaRadioBoxs[2].getArea().x + travelCriteriaRadioBoxs[2].getArea().width) > width)
				travelCriteriaRadioBoxs[2].prepareArea(buffer, decalage + (decalage >> 1), travelCriteriaRadioBoxs[2]
						.getArea().y
						+ travelCriteriaRadioBoxs[2].getArea().height + (decalage >> 1), father.lg("FewerChanges"),
						father.getSizeAdapteur().getSmallFont());

		}
		travelCriteriaCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!travelCriteriaCollapsableArea.isCollapsed()) {
			travelCriteriaRadioBoxs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
			travelCriteriaRadioBoxs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
			travelCriteriaRadioBoxs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
		}
		ordonne = travelCriteriaCollapsableArea.getArea().y + travelCriteriaCollapsableArea.getArea().height
				+ decalage2;

		/***************************************************************************************************************
		 * Travel mode
		 */
		s = father.lg("TravelMode");
		PTCheckBox chk = null;
		if (!travelModeCollapsableArea.isCollapsed()) {
			for (PairPTCheckBox p : travelModeCheckBoxs) {
				p.chk.prepareArea(buffer, (chk == null) ? decalage + (decalage >> 1) : chk.getArea().x
						+ chk.getArea().width + (decalage >> 1), (chk == null) ? travelCriteriaCollapsableArea
						.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
								.getIntermediateFont()) : chk.getArea().y, p.name, father.getSizeAdapteur()
						.getSmallFont());
				if ((p.chk.getArea().x + p.chk.getArea().width) > (getWidth() - decalage)) {
					p.chk.prepareArea(buffer, decalage + (decalage >> 1), p.chk.getArea().y + p.chk.getArea().height
							+ (decalage >> 1), p.name, father.getSizeAdapteur().getSmallFont());
				}
				chk = p.chk;
			}
		}
		travelModeCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(),
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!travelModeCollapsableArea.isCollapsed())
			for (PairPTCheckBox p : travelModeCheckBoxs)
				p.chk.draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin().getColorSubAreaInside(),
						father.getSkin().getColorLetter());
		ordonne = travelModeCollapsableArea.getArea().y + travelModeCollapsableArea.getArea().height + decalage2;

		/***************************************************************************************************************
		 * Services
		 */
		s = father.lg("Services");
		if (!servicesCollapsableArea.isCollapsed()) {
			pos = new int[3];
			width = 0;
			cpt = 0;
			// on trouve la largueur de la colonne des services
			ord = new byte[ServicesRadioBoxs.size()];
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp = getWidthString(p.name, buffer, father.getSizeAdapteur().getSmallFont());
				if (tmp > (getWidth() - decalage2 - decalage >> 1)) {
					ord[cpt] = 0;
					tmp = 0;
					String[] splited = p.name.split(" ");
					int myTmp;
					for (String miniS : splited) {
						ord[cpt]++;
						myTmp = getWidthString(miniS, buffer, father.getSizeAdapteur().getSmallFont());
						if (myTmp > tmp)
							tmp = myTmp;
					}
				} else {
					ord[cpt] = 1;
				}
				cpt++;
				if (tmp > width)
					width = tmp;
			}
			// on calcul les positions des 3 colone de valehbur des services
			tmp = (((getWidth() - decalage2 - decalage - width) / 3) >> 1);
			pos[0] = decalage + width + tmp;
			pos[1] = pos[0] + (tmp << 1);
			pos[2] = pos[1] + (tmp << 1);
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getIntermediateFont());
			width = getHeightString("", buffer, father.getSizeAdapteur().getSmallFont()) + (decalage >> 1);
			cpt = -1;
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp += width;
				if (ord[++cpt]-- > 1)
					tmp += width * ord[cpt] >> 1;
				for (int i = 0; i < pos.length; i++) {
					p.rbs[i].prepareArea(buffer, pos[i], tmp, "", father.getSizeAdapteur().getSmallFont(), true, false);
				}
				if (ord[cpt] > 1)
					tmp += width * ord[cpt] >> 1;
			}
			servicesCollapsableArea.update(buffer, decalage, ordonne, s,
					father.getSizeAdapteur().getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father
							.getSkin().getColorLetter());
			// ____________Idle_Once_Always
			// __Coffre_____O_____O____X___
			// __Handi______O_____X____O___
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getIntermediateFont());
			buffer.setFont(father.getSizeAdapteur().getSmallFont());
			buffer.drawString(father.lg(SettingsValue.Idle.toString()), pos[0]
					- (getWidthString(father.lg(SettingsValue.Idle.toString()), buffer) >> 1), tmp);
			buffer.drawString(father.lg(SettingsValue.Once.toString()), pos[1]
					- (getWidthString(father.lg(SettingsValue.Once.toString()), buffer) >> 1), tmp);
			buffer.drawString(father.lg(SettingsValue.Always.toString()), pos[2]
					- (getWidthString(father.lg(SettingsValue.Always.toString()), buffer) >> 1), tmp);
			tmp = servicesCollapsableArea.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father
					.getSizeAdapteur().getIntermediateFont());
			width = getHeightString("", buffer, father.getSizeAdapteur().getSmallFont());
			tmp += width;
			width += (decalage >> 1);
			for (PairPTRadioBox p : ServicesRadioBoxs) {
				tmp += width;
				p.rbs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				buffer.setFont(father.getSizeAdapteur().getSmallFont());
				if (getWidthString(p.name, buffer, father.getSizeAdapteur().getSmallFont()) > (servicesCollapsableArea
						.getArea().width >> 1)) {
					String[] splited = p.name.split(" ");
					tmp -= width;
					for (String miniS : splited) {
						tmp += width;
						buffer.drawString(miniS, decalage << 1, tmp);
					}
				} else {
					buffer.drawString(p.name, decalage << 1, tmp);
				}
//				buffer.setColor(father.getNetworkColorManager().getColorForService(p.))
				buffer.drawOval(decalage + (decalage >> 2), p.rbs[0].getArea().y ,
						father.getSizeAdapteur().getSizeSmallFont() >> 1,
						father.getSizeAdapteur().getSizeSmallFont() >> 1);
			}
		} else
			servicesCollapsableArea.update(buffer, decalage, ordonne, s,
					father.getSizeAdapteur().getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father
							.getSkin().getColorLetter());
		ordonne = servicesCollapsableArea.getArea().y + servicesCollapsableArea.getArea().height + decalage2;

		/***************************************************************************************************************
		 * Quality
		 */
		s = father.lg("GraphicalQuality");
		if (!qualityCollapsableArea.isCollapsed()) {
			width = getWidth() - decalage2;

			qualityRadioBoxs[0].prepareArea(buffer, decalage + (decalage >> 1), qualityCollapsableArea
					.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
							.getIntermediateFont()), father.lg("Minimal"), father.getSizeAdapteur().getSmallFont());

			qualityRadioBoxs[1].prepareArea(buffer, qualityRadioBoxs[0].getArea().x
					+ qualityRadioBoxs[0].getArea().width + (decalage >> 1), qualityRadioBoxs[0].getArea().y, father
					.lg("Low"), father.getSizeAdapteur().getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((qualityRadioBoxs[1].getArea().x + qualityRadioBoxs[1].getArea().width) > width)
				qualityRadioBoxs[1].prepareArea(buffer, decalage + (decalage >> 1), qualityRadioBoxs[1].getArea().y
						+ qualityRadioBoxs[1].getArea().height + (decalage >> 1), father.lg("Low"), father
						.getSizeAdapteur().getSmallFont());

			qualityRadioBoxs[2].prepareArea(buffer, qualityRadioBoxs[1].getArea().x
					+ qualityRadioBoxs[1].getArea().width + (decalage >> 1), qualityRadioBoxs[1].getArea().y, father
					.lg("Medium"), father.getSizeAdapteur().getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((qualityRadioBoxs[2].getArea().x + qualityRadioBoxs[2].getArea().width) > width)
				qualityRadioBoxs[2].prepareArea(buffer, decalage + (decalage >> 1), qualityRadioBoxs[2].getArea().y
						+ qualityRadioBoxs[2].getArea().height + (decalage >> 1), father.lg("Medium"), father
						.getSizeAdapteur().getSmallFont());

			qualityRadioBoxs[3].prepareArea(buffer, qualityRadioBoxs[2].getArea().x
					+ qualityRadioBoxs[2].getArea().width + (decalage >> 1), qualityRadioBoxs[2].getArea().y, father
					.lg("High"), father.getSizeAdapteur().getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((qualityRadioBoxs[3].getArea().x + qualityRadioBoxs[3].getArea().width) > width)
				qualityRadioBoxs[3].prepareArea(buffer, decalage + (decalage >> 1), qualityRadioBoxs[3].getArea().y
						+ qualityRadioBoxs[3].getArea().height + (decalage >> 1), father.lg("High"), father
						.getSizeAdapteur().getSmallFont());
		}
		qualityCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(),
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!qualityCollapsableArea.isCollapsed()) {
			qualityRadioBoxs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
			qualityRadioBoxs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
			qualityRadioBoxs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
			qualityRadioBoxs[3].draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorSubAreaInside(), father.getSkin().getColorLetter());
		}
		ordonne = qualityCollapsableArea.getArea().y + qualityCollapsableArea.getArea().height + decalage2;

		/***************************************************************************************************************
		 * AutoCompletionTextArea
		 */
		s = father.lg("IntermediatesStations");
		if (!intermediatesStationsCollapsableArea.isCollapsed()) {
			width = getWidth() - decalage2 - decalage;
			// intermediatesStationsNew.prepareArea(buffer, x, y, height, width)
			intermediatesStationsTextBox.prepareArea(buffer, decalage << 1, intermediatesStationsCollapsableArea
					.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
							.getIntermediateFont()), width - decalage2 - decalage
					- father.getSizeAdapteur().getSizeSmallFont(), father.getSizeAdapteur().getSmallFont());
			intermediatesStationsButton.prepareArea(buffer, intermediatesStationsTextBox.getArea().x
					+ intermediatesStationsTextBox.getArea().width + decalage,
					intermediatesStationsTextBox.getArea().y, imageOk);
			intermediatesStationsNew.update(buffer, intermediatesStationsTextBox.getArea().x,
					intermediatesStationsTextBox.getArea().y, 40, intermediatesStationsTextBox.getArea().width, null,
					null);
		}
		intermediatesStationsCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!intermediatesStationsCollapsableArea.isCollapsed()) {
			intermediatesStationsTextBox.draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin()
					.getColorInside(), father.getSkin().getColorLetter());
			Station station = this.sationsHash.get(intermediatesStationsTextBox.getText());
			if (station != null) {
				intermediatesStationsButton.draw(buffer, imageOk);
				buffer.setFont(father.getSizeAdapteur().getSmallFont());
				buffer.setColor(father.getSkin().getColorLetter());
				int xActu, yActu, taille;
				Iterator<Route> itRoute = station.getRoutes();
				Route route;
				Iterator<Service> itService = station.getServices();
				Service service;
				xActu = intermediatesStationsTextBox.getArea().x;
				yActu = intermediatesStationsTextBox.getArea().y + intermediatesStationsTextBox.getArea().height
						+ (decalage >> 1);
				s = "";
				while (itRoute.hasNext()) {
					route = itRoute.next();
					s += route.getId();
					if (itRoute.hasNext())
						s += ", ";
				}
				buffer.drawString(s, xActu, yActu + PanelDoubleBufferingSoftwear.getHeightString(s, buffer));
				xActu += PanelDoubleBufferingSoftwear.getWidthString(s, buffer) + (decalage >> 1);

				while (itService.hasNext()) {
					service = itService.next();
					s = service.getName().substring(0, 1);
					taille = (int) (buffer.getFont().getSize() * 1.3F);
					buffer.setColor(father.getNetworkColorManager().getColorForService(service));
					buffer.fillOval(xActu, yActu, taille + 1, taille + 1);
					buffer.setColor(father.getSkin().getColorLetter());
					buffer.drawOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
					buffer.drawString(s, xActu + (taille >> 1)
							- (PanelDoubleBufferingSoftwear.getWidthString(s, buffer) >> 1), yActu + (taille >> 1)
							+ (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
					xActu += taille + (decalage >> 1);
				}
			}
			// les stations déja choisie

		}
		ordonne = intermediatesStationsCollapsableArea.getArea().y
				+ intermediatesStationsCollapsableArea.getArea().height + decalage2;
		// imageOk;imageDel;

		// TODO ........mk AutoComplet°........................
		// TODO ........taf.........................

		// TODO ........Language
		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		deroullement = scrollBar.getDeroullement();
		// TODO améliorer le scroll actuelle il utimise des donnée du passé pour le presente. bug maximisation fenetre.

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
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
		this.requestFocus();
	}

}
