package ihm.smartPhone.statePanels;

import graphNetwork.KindRoute;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;
import iGoMaster.SettingsKey;
import iGoMaster.SettingsValue;
import iGoMaster.Algo.CriteriousForLowerPath;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.statePanels.component.PairPTCheckBox;
import ihm.smartPhone.statePanels.component.PairPTRadioBoxs;
import ihm.smartPhone.statePanels.component.ServiceToolTipText;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.CodeExecutor2P;
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class NewTravelPanel extends PanelState {

	/**
	 * Le hash des stations
	 */
	protected HashMap<String, Station> stationsHash;
	/**
	 * ...
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Le chemin qu'on va paramètrer
	 */
	protected PathInGraphConstraintBuilder pathBuilder;
	/**
	 * Les nom des stations sous forme de tableau. Elles sont alphanumériquement triées
	 */
	protected String[] stations;
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * L'image OK pour ajouter une station
	 */
	protected ImageIcon imageOk;
	/**
	 * L'image OK pour retirer une station
	 */
	protected ImageIcon imageDel;
	/**
	 * Conteneurs pour les zone d'aide des services
	 */
	protected LinkedList<ServiceToolTipText> serviceDisplayed;
	protected LinkedList<ServiceToolTipText> servicePooled;
	/**
	 * Variables pour les type de transport autorisé
	 */
	protected final int travelMode = 1;
	protected LinkedList<PairPTCheckBox> travelModeCheckBoxs;
	protected PTCollapsableArea travelModeCollapsableArea;

	/**
	 * Variables pour les critères de calcul
	 */
	protected final int minorTravelCriteria = 2;
	protected final int mainTravelCriteria = 4;
	protected PTRadioBox[] travelCriteriaRadioBoxs;
	protected PTCollapsableArea travelCriteriaCollapsableArea;

	/**
	 * Variables pour les services
	 */
	protected final int services = 3;
	protected LinkedList<PairPTRadioBoxs> ServicesRadioBoxs;
	protected PTCollapsableArea servicesCollapsableArea;

	/**
	 * Variables pour les stations intermédiaires
	 */
	protected final int intermediatesStationsAdd = 5;
	protected final int intermediatesStationsRemove = 6;
	protected PTArea intermediatesStationsArea;
	protected PTAutoCompletionTextBox intermediatesStationsTextBox;
	protected PTCollapsableArea intermediatesStationsCollapsableArea;
	protected PTButton intermediatesStationsButton;
	protected Hashtable<Integer, PTButton> intermediatesStationsDel;

	/**
	 * Variables pour les stations à éviter
	 */
	protected final int avoidsStationsAdd = 7;
	protected final int avoidsStationsRemove = 8;
	protected PTArea avoidsStationsArea;
	protected PTAutoCompletionTextBox avoidsStationsTextBox;
	protected PTCollapsableArea avoidsStationsCollapsableArea;
	protected PTButton avoidsStationsButton;
	protected Hashtable<Integer, PTButton> avoidsStationsDel;

	/**
	 * Variables pour la zone de texte de la station de départ
	 */
	protected boolean departureStationChanged;
	protected PTArea departureStationArea;
	protected PTAutoCompletionTextBox departureStationTextBox;
	protected PTCollapsableArea departureStationCollapsableArea;

	/**
	 * Variables pour la zone de texte de la station de arrivé
	 */
	protected boolean arrivalStationChanged;
	protected PTArea arrivalStationNew;
	protected PTAutoCompletionTextBox arrivalStationTextBox;
	protected PTCollapsableArea arrivalStationCollapsableArea;

	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;

	/**
	 * Unique constructeur surchargeant le constructeur de PanelState. On y commande la construction de l'ihm
	 * 
	 * @param ihm
	 * @param upperBar
	 * @param lowerBar
	 */
	public NewTravelPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar newlowerBar) {
		super(ihm, upperBar, newlowerBar);
		deroullement = 0;
		departureStationChanged = true;
		arrivalStationChanged = true;
		serviceDisplayed = new LinkedList<ServiceToolTipText>();
		servicePooled = new LinkedList<ServiceToolTipText>();
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				boolean changed = false;
				for (ServiceToolTipText s : serviceDisplayed)
					changed = s.maybeOvered(e.getX(), e.getY()) || changed;
				if (!changed) {
					lowerBar.setLeftTitle("");
					lowerBar.setLeftValue("");
					lowerBar.repaint();
				}
			}
		});
		buildInterfaceFromDomDocument();
	}

	/**
	 * Construction du contenu
	 */
	protected void buildInterfaceFromDomDocument() {
		String s, valS;
		PTRadioBoxGroup grp;
		PTRadioBoxGroup[] grpTrans;
		CodeExecutor ex;
		Iterator<Station> it;
		Iterator<KindRoute> itR;
		Iterator<Service> itS;
		Station st;
		Service ser;

		/***************************************************************************************************************
		 * Travel criteria
		 */
		travelCriteriaCollapsableArea = makeCollapsableArea();
		travelCriteriaCollapsableArea.changeCollapseState();
		travelCriteriaRadioBoxs = new PTRadioBox[6];
		grp = new PTRadioBoxGroup(3);
		grpTrans = new PTRadioBoxGroup[3];
		grpTrans[0] = new PTRadioBoxGroup(2);
		grpTrans[1] = new PTRadioBoxGroup(2);
		grpTrans[2] = new PTRadioBoxGroup(2);
		ex = new CodeExecutor() {
			@Override
			public void execute() {
				recordChangedSetting(mainTravelCriteria, SettingsKey.MAIN_TRAVEL_CRITERIA.toString());
			}
		};
		travelCriteriaRadioBoxs[0] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[0] }, ex);// Cheaper
		travelCriteriaRadioBoxs[1] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[1] });// Faster
		travelCriteriaRadioBoxs[2] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[2] }, ex);// Fewer Changes
		grp = new PTRadioBoxGroup(3);
		ex = new CodeExecutor() {
			@Override
			public void execute() {
				recordChangedSetting(minorTravelCriteria, SettingsKey.MAIN_TRAVEL_CRITERIA.toString());
			}
		};
		travelCriteriaRadioBoxs[3] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[0] }, ex);// Cheaper
		travelCriteriaRadioBoxs[4] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[1] }, ex);// Faster
		travelCriteriaRadioBoxs[5] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[2] }, ex);// Fewer Changes
		travelCriteriaRadioBoxs[0].setClicked(father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.COST.toString()) == 0);
		travelCriteriaRadioBoxs[1].setClicked(father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.TIME.toString()) == 0);
		travelCriteriaRadioBoxs[2].setClicked(father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.CHANGE.toString()) == 0);
		travelCriteriaRadioBoxs[3].setClicked(father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.COST.toString()) == 0);
		travelCriteriaRadioBoxs[4].setClicked(father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.TIME.toString()) == 0);
		travelCriteriaRadioBoxs[5].setClicked(father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.CHANGE.toString()) == 0);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[0]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[1]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[2]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[3]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[4]);
		travelCriteriaCollapsableArea.addComponent(travelCriteriaRadioBoxs[5]);

		/***************************************************************************************************************
		 * Travel Mode
		 */
		travelModeCollapsableArea = makeCollapsableArea();
		travelModeCollapsableArea.changeCollapseState();
		travelModeCheckBoxs = new LinkedList<PairPTCheckBox>();
		itR = father.getKindRoutes();
		PTCheckBox chk;
		while (itR.hasNext()) {
			chk = makeCheckBox(new CodeExecutor1P<String>(s = (itR.next().getKindOf())) {
				@Override
				public void execute() {
					recordChangedSetting(travelMode, this.origine);
				}
			});
			chk
					.setClicked(!(father.getConfig(SettingsKey.TRAVEL_MODE_ + s).compareTo(
							SettingsValue.DISABLE.toString()) == 0));
			travelModeCollapsableArea.addComponent(chk);
			travelModeCheckBoxs.add(new PairPTCheckBox(chk, s));
		}

		/***************************************************************************************************************
		 * Services
		 */
		servicesCollapsableArea = makeCollapsableArea();
		servicesCollapsableArea.changeCollapseState();
		ServicesRadioBoxs = new LinkedList<PairPTRadioBoxs>();
		itS = father.getServices();
		PTRadioBox[] rbs;
		while (itS.hasNext()) {
			rbs = new PTRadioBox[3];
			grp = new PTRadioBoxGroup(rbs.length);
			ex = new CodeExecutor1P<String>(s = (SettingsKey.SERVICES_.toString() + ((ser = itS.next()).getId()))) {
				@Override
				public void execute() {
					recordChangedSetting(services, this.origine);
				}
			};
			for (int i = 0; i < rbs.length; i++) {
				rbs[i] = makeRadioButton(grp, ex);
				servicesCollapsableArea.addComponent(rbs[i]);
			}
			valS = father.getConfig(s);
			if (valS.compareTo(SettingsValue.Once.toString()) == 0)
				rbs[1].setClicked(true);
			else if (valS.compareTo(SettingsValue.Always.toString()) == 0)
				rbs[2].setClicked(true);
			else
				rbs[0].setClicked(true);
			ServicesRadioBoxs.add(new PairPTRadioBoxs(rbs, ser, s));
		}

		/***************************************************************************************************************
		 * Quality
		 */
		// qualityCollapsableArea = makeCollapsableArea();
		// qualityCollapsableArea.changeCollapseState();
		// grp = new PTRadioBoxGroup(4);
		// ex = new CodeExecutor1P<PanelTooled>(this) {
		// @Override
		// public void execute() {
		// recordChangedSetting(quality, "GRAPHICAL_QUALITY");
		// // this.origine.;
		// }
		// };
		// qualityRadioBoxs = new PTRadioBox[4];
		// qualityRadioBoxs[0] = makeRadioButton(grp, ex);// AS_FAST_AS_WE_CAN
		// qualityRadioBoxs[1] = makeRadioButton(grp, ex);// TEXT_ANTI_ANTIALIASING
		// qualityRadioBoxs[2] = makeRadioButton(grp, ex);// FULL_ANTI_ANTIALIASING
		// qualityRadioBoxs[3] = makeRadioButton(grp, ex);// HIGHER_QUALITY
		// int i = IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue();
		// try {
		// i = Integer.parseInt(father.getConfig("GRAPHICAL_QUALITY"));
		// } catch (NumberFormatException e) {
		// father.setConfig("GRAPHICAL_QUALITY", i + "");
		// this.setQuality(IHMGraphicQuality.FULL_ANTI_ANTIALIASING);
		// }
		// qualityRadioBoxs[0].setClicked(IHMGraphicQuality.AS_FAST_AS_WE_CAN.getValue() == i);
		// qualityRadioBoxs[1].setClicked(IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue() == i);
		// qualityRadioBoxs[2].setClicked(IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue() == i);
		// qualityRadioBoxs[3].setClicked(IHMGraphicQuality.HIGHER_QUALITY.getValue() == i);
		// qualityCollapsableArea.addComponent(qualityRadioBoxs[0]);
		// qualityCollapsableArea.addComponent(qualityRadioBoxs[1]);
		// qualityCollapsableArea.addComponent(qualityRadioBoxs[2]);
		// qualityCollapsableArea.addComponent(qualityRadioBoxs[3]);
		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar = makeScrollBar();

		/***************************************************************************************************************
		 * stations array
		 */
		// long l = System.nanoTime();
		stationsHash = new HashMap<String, Station>();
		Vector<String> v = new Vector<String>();
		it = father.getStations();
		while (it.hasNext()) {
			st = it.next();
			stationsHash.put(st.getName(), st);
			v.add(st.getName());
		}
		Collections.sort(v, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		stations = v.toArray(new String[0]);
		// System.out.println((System.nanoTime() - l) * 1e-6 + " pour trier "+ stations.length);
		/***************************************************************************************************************
		 * departureStation
		 */
		departureStationCollapsableArea = makeCollapsableArea();
		departureStationArea = makeArea();
		departureStationTextBox = makeAutoCompletionTextBox(stations, new CodeExecutor() {

			@Override
			public void execute() {
				departureStationChanged = true;
			}
		});
		departureStationCollapsableArea.addComponent(departureStationTextBox);
		departureStationCollapsableArea.addComponent(departureStationArea);

		/***************************************************************************************************************
		 * arrivalStation
		 */
		arrivalStationCollapsableArea = makeCollapsableArea();
		arrivalStationNew = makeArea();
		arrivalStationTextBox = makeAutoCompletionTextBox(stations, new CodeExecutor() {

			@Override
			public void execute() {
				arrivalStationChanged = true;
			}
		});
		arrivalStationCollapsableArea.addComponent(arrivalStationTextBox);
		arrivalStationCollapsableArea.addComponent(arrivalStationNew);

		/***************************************************************************************************************
		 * Station inter
		 */
		intermediatesStationsCollapsableArea = makeCollapsableArea();
		intermediatesStationsCollapsableArea.changeCollapseState();
		intermediatesStationsArea = makeArea();
		intermediatesStationsButton = makeButton(ex = new CodeExecutor1P<PanelTooled>(this) {
			@Override
			public void execute() {
				recordChangedSetting(intermediatesStationsAdd, intermediatesStationsTextBox.getText());
				this.origine.repaint();
			}
		});
		intermediatesStationsTextBox = makeAutoCompletionTextBox(stations, null, ex);
		intermediatesStationsCollapsableArea.addComponent(intermediatesStationsArea);
		intermediatesStationsCollapsableArea.addComponent(intermediatesStationsTextBox);
		intermediatesStationsDel = new Hashtable<Integer, PTButton>();

		// TODO z_make
		/***************************************************************************************************************
		 * Station avoid
		 */
		avoidsStationsCollapsableArea = makeCollapsableArea();
		avoidsStationsCollapsableArea.changeCollapseState();
		avoidsStationsArea = makeArea();
		avoidsStationsButton = makeButton(ex = new CodeExecutor1P<PanelTooled>(this) {
			@Override
			public void execute() {
				recordChangedSetting(avoidsStationsAdd, avoidsStationsTextBox.getText());
				this.origine.repaint();
			}
		});
		avoidsStationsTextBox = makeAutoCompletionTextBox(stations, null, ex);
		avoidsStationsCollapsableArea.addComponent(avoidsStationsArea);
		avoidsStationsCollapsableArea.addComponent(avoidsStationsTextBox);
		avoidsStationsDel = new Hashtable<Integer, PTButton>();
	}

	/**
	 * Fonction enregistrant les modifications. Chaque modif est définit pas un entier étant sa famille, le string qui
	 * suit est une information suplémentaire au sujet de la modification
	 * 
	 * @param familly
	 * @param s
	 */
	// TODO z_recordChangedSetting
	protected void recordChangedSetting(int familly, String s) {
		Station station;
		switch (familly) {
		case travelMode:
			for (PairPTCheckBox p : travelModeCheckBoxs)
				if (p.name.compareTo(s) == 0) {
					Iterator<KindRoute> itR = father.getKindRoutes();
					KindRoute kind;
					while (itR.hasNext())
						if ((kind = itR.next()).getKindOf().compareTo(s) == 0)
							if (p.chk.isClicked())
								pathBuilder.removeRefusedKindRoute(kind);
							else
								pathBuilder.addRefusedKindRoute(kind);
					return;
				}
			break;
		case mainTravelCriteria:
			if (travelCriteriaRadioBoxs[0].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.COST);
				return;
			}
			if (travelCriteriaRadioBoxs[1].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.TIME);
				return;
			}
			if (travelCriteriaRadioBoxs[2].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.CHANGE);
				return;
			}
			break;
		case minorTravelCriteria:
			if (travelCriteriaRadioBoxs[3].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.COST);
				return;
			}
			if (travelCriteriaRadioBoxs[4].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.TIME);
				return;
			}
			if (travelCriteriaRadioBoxs[5].isClicked()) {
				if (pathBuilder != null)
					pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.CHANGE);
				return;
			}
			break;
		case services:
			for (PairPTRadioBoxs p : ServicesRadioBoxs) {
				if (s.compareTo(p.name) == 0) {
					Iterator<Service> itS = father.getServices();
					Service service;
					if (p.rbs[0].isClicked()) {
						while (itS.hasNext()) {
							if ((SettingsKey.SERVICES_.toString() + (service = itS.next()).getId()).compareTo(s) == 0) {
								pathBuilder.removeSeviceOnce(service);
								pathBuilder.removeSeviceAlways(service);
								return;
							}
						}
						return;
					}
					if (p.rbs[1].isClicked()) {
						while (itS.hasNext()) {
							if ((SettingsKey.SERVICES_.toString() + (service = itS.next()).getId()).compareTo(s) == 0) {
								if (pathBuilder.getCurrentPathInGraph().containsServicesOnce(service))
									return;
								pathBuilder.removeSeviceAlways(service);
								pathBuilder.addSeviceOnce(service);
								return;
							}
						}
						return;
					}
					if (p.rbs[2].isClicked()) {
						while (itS.hasNext()) {
							if ((SettingsKey.SERVICES_.toString() + (service = itS.next()).getId()).compareTo(s) == 0) {
								if (pathBuilder.getCurrentPathInGraph().containsServicesAlways(service))
									return;
								pathBuilder.addSeviceAlways(service);
								pathBuilder.removeSeviceOnce(service);
								return;
							}
						}
						return;
					}
				}
			}
		case intermediatesStationsAdd:
			station = stationsHash.get(s);
			if (station != null && !pathBuilder.getCurrentPathInGraph().containsSteps(station)) {
				intermediatesStationsDel.put(station.getId(), makeButton(new CodeExecutor2P<PanelTooled, String>(this,
						s) {
					@Override
					public void execute() {
						recordChangedSetting(intermediatesStationsRemove, this.origineB);
						this.origineA.repaint();
					}
				}));
				pathBuilder.addStepStations(station);
				intermediatesStationsTextBox.setText("");
			}
			break;
		case intermediatesStationsRemove:
			station = stationsHash.get(s);
			if (station != null && pathBuilder.getCurrentPathInGraph().containsSteps(station)) {
				intermediatesStationsDel.remove(station.getId());
				pathBuilder.removeStepStations(station);
			}
			break;
		case avoidsStationsAdd:
			station = stationsHash.get(s);
			if (station != null && !pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)) {
				avoidsStationsDel.put(station.getId(), makeButton(new CodeExecutor2P<PanelTooled, String>(this, s) {
					@Override
					public void execute() {
						recordChangedSetting(avoidsStationsRemove, this.origineB);
						this.origineA.repaint();
					}
				}));
				pathBuilder.addAvoidStations(station);
				avoidsStationsTextBox.setText("");
			}
			break;
		case avoidsStationsRemove:
			station = stationsHash.get(s);
			if (station != null && pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)) {
				avoidsStationsDel.remove(station.getId());
				pathBuilder.removeAvoidStations(station);
			}
			break;
		default:
			System.err.println("NewTravel : record (" + familly + "," + s + " not handeled.");
			break;
		}
	}

	/**
	 * Prépare une zone d'autocomplétion
	 * 
	 * @param stationNew
	 *            la zone étant la surface de la PTAutoCompletionTextBox
	 * @param stationTextBox
	 *            la PTAutoCompletionTextBox en elle même
	 * @param stationCollapsableArea
	 *            la zone qui contient la PTAutoCompletionTextBox et la zone étandu
	 * @param title
	 *            le titre de la PTCollapsableArea
	 * @param ordonne
	 *            l'ordonnée à partir de laquelle il faut dessiner
	 * @param decalage
	 *            le decalage
	 * @param decalage2
	 *            le decalage fois 2
	 * @return la taille des icones
	 */
	protected int prepareAutoCompletionStationTextBox(PTArea stationNew, PTAutoCompletionTextBox stationTextBox,
			PTCollapsableArea stationCollapsableArea, String title, int ordonne, int decalage, int decalage2) {
		int taille = 0;
		if (!stationCollapsableArea.isCollapsed()) {
			int width = getWidth() - decalage2 - decalage;
			buffer.setFont(father.getSizeAdapteur().getSmallFont());
			taille = (int) (buffer.getFont().getSize() * 1.3F);
			stationTextBox.prepareArea(buffer, decalage2, stationCollapsableArea.getFirstOrdonneForComponents(buffer,
					decalage, ordonne, title, father.getSizeAdapteur().getIntermediateFont()), width - decalage2
					- decalage - father.getSizeAdapteur().getSizeSmallFont(), father.getSizeAdapteur().getSmallFont());
			stationNew.update(buffer, stationTextBox.getArea().x, stationTextBox.getArea().y,
					stationTextBox.getArea().height + (decalage >> 1) + taille, stationCollapsableArea.getArea().width,
					null, null);
		}
		return taille;
	}

	/**
	 * Dessin la zone d'autocomplétion qu'on a préalablement préparé
	 * 
	 * @param stationNew
	 * @param stationTextBox
	 * @param stationCollapsableArea
	 * @param ordonne
	 * @param decalage
	 * @param decalage2
	 * @param taille
	 * @param shouldFillTheField
	 * @return
	 */
	protected Station drawAutoCompletionStationTextBox(PTArea stationNew, PTAutoCompletionTextBox stationTextBox,
			PTCollapsableArea stationCollapsableArea, int ordonne, int decalage, int decalage2, int taille,
			boolean shouldFillTheField) {
		if (stationCollapsableArea.isCollapsed())
			return null;
		stationTextBox.draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin().getColorInside(), father
				.getSkin().getColorLetter());
		Station station = this.stationsHash.get(stationTextBox.getText());
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		if (station != null) {
			// buffer.setFont(father.getSizeAdapteur().getSmallFont());
			// buffer.setColor(father.getSkin().getColorLetter());
			int xActu, yActu;
			// Iterator<Route> itRoute = station.getRoutes();
			// Route route;
			// Iterator<Service> itService = station.getServices();
			// Service service;
			xActu = stationTextBox.getArea().x;
			yActu = stationTextBox.getArea().y + stationTextBox.getArea().height + (decalage >> 1);
			// String s = "";
			// while (itRoute.hasNext()) {
			// route = itRoute.next();
			// s += route.getId();
			// if (itRoute.hasNext())
			// s += ", ";
			// }
			// buffer.drawString(s, xActu, yActu + PanelDoubleBufferingSoftwear.getHeightString(s, buffer));
			// xActu += PanelDoubleBufferingSoftwear.getWidthString(s, buffer) + decalageDemi;
			//
			// while (itService.hasNext()) {
			// service = itService.next();
			// s = service.getName().substring(0, 1);
			// buffer.setColor(father.getNetworkColorManager().getColorForService(service));
			// buffer.fillOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
			// buffer.setColor(father.getSkin().getColorLetter());
			// buffer.drawOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
			// buffer.drawString(s, xActu + (taille >> 1)
			// - (PanelDoubleBufferingSoftwear.getWidthString(s, buffer) >> 1), yActu + (taille >> 1)
			// + (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
			// xActu += taille + decalageDemi;
			// }
			drawRoutesAndServices(xActu + (decalage >> 1), yActu, decalage, taille, station);
			return station;
		}
		if (!shouldFillTheField)
			return null;
		if (stationTextBox.getText().isEmpty()) {
			buffer.drawString(father.lg("FillThisField"), stationTextBox.getArea().x, stationTextBox.getArea().y
					+ stationTextBox.getArea().height + (decalage >> 1)
					+ PanelDoubleBufferingSoftwear.getHeightString(father.lg("InvalideStation"), buffer));
			return null;
		}
		buffer.setColor(Color.red);
		buffer.drawString(father.lg("InvalideStation"), stationTextBox.getArea().x, stationTextBox.getArea().y
				+ stationTextBox.getArea().height + (decalage >> 1)
				+ PanelDoubleBufferingSoftwear.getHeightString(father.lg("InvalideStation"), buffer));
		return null;
	}

	/**
	 * Dessine pour une station données ses routes, puis ces services
	 * 
	 * @param xActu
	 * @param yActu
	 * @param decalage
	 * @param taille
	 * @param station
	 */
	protected void drawRoutesAndServices(int xActu, int yActu, int decalage, int taille, Station station) {
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		buffer.setColor(father.getSkin().getColorLetter());
		Iterator<Route> itRoute = station.getRoutes();
		ServiceToolTipText sttt;
		Route route;
		Iterator<Service> itService = station.getServices();
		Service service;
		String s = "";
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
			if (servicePooled.isEmpty())
				sttt = new ServiceToolTipText(new Rectangle(), lowerBar);
			else
				sttt = servicePooled.remove();
			serviceDisplayed.add(sttt);
			sttt.init(service);
			buffer.setColor(father.getNetworkColorManager().getColor(service));
			sttt.setBounds(xActu - 1, yActu - 1, taille + 2, taille + 2);
			buffer.fillOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
			buffer.drawString(s, xActu + (taille >> 1) - (PanelDoubleBufferingSoftwear.getWidthString(s, buffer) >> 1),
					yActu + (taille >> 1) + (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
			xActu += taille + (decalage >> 1);
		}
	}

	@Override
	public void paint(Graphics g) {
		int tmp;
		String s;
		if (pathBuilder == null) {
			g.setFont(father.getSizeAdapteur().getLargeFont());
			g.setColor(Color.red);
			s = "Impossible de construire";
			g.drawString(s, getWidth() - getWidthString(s, g) >> 1,
					tmp = (getHeight() - getHeightString(s, g) * 7 >> 1));
			s = "un nouveau trajet :";
			g.drawString(s, getWidth() - getWidthString(s, g) >> 1, tmp = (tmp + (getHeightString(s, g) << 1)));
			s = "le master à passé";
			g.drawString(s, getWidth() - getWidthString(s, g) >> 1, tmp = (tmp + (getHeightString(s, g) << 1)));
			s = "un constructeur vide";
			g.drawString(s, getWidth() - getWidthString(s, g) >> 1, tmp = (tmp + (getHeightString(s, g) << 1)));
			return;
		}

		draw();
		if (shouldDoubleRepaint) {
			shouldDoubleRepaint = false;
			draw();
		}
		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Dessin en arrière plan de la fenetre
	 */
	public void draw() {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int decalageDemi = (decalage >> 1);
		int decalage2 = (decalage << 1);
		int ordonne = decalage - deroullement;
		int width;
		int taille;
		int[] pos;
		byte[] ord;
		int tmp;
		int cpt;
		Rectangle rec = new Rectangle();
		// PTRadioBox rb;
		String s;
		PTCheckBox chk;
		Station station;
		Iterator<Station> itS;

		/***
		 * Gestion du buffer mémoire
		 */
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
			// buffer.setColor(father.getSkin().getColorLetter());
			if (imageOk == null || imageOk.getIconHeight() != father.getSizeAdapteur().getSizeSmallFont()) {
				imageOk = ImageLoader.getRessourcesImageIcone("button_ok", father.getSizeAdapteur().getSizeSmallFont(),
						father.getSizeAdapteur().getSizeSmallFont());
				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		/*****
		 * Swap des liste de zone de service
		 */
		LinkedList<ServiceToolTipText> serviceSwap = servicePooled;
		servicePooled = serviceDisplayed;
		serviceDisplayed = serviceSwap;

		/***************************************************************************************************************
		 * Departure
		 */
		s = father.lg("Departure");
		taille = prepareAutoCompletionStationTextBox(departureStationArea, departureStationTextBox,
				departureStationCollapsableArea, s, ordonne, decalage, decalage2);
		departureStationCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		station = drawAutoCompletionStationTextBox(departureStationArea, departureStationTextBox,
				departureStationCollapsableArea, ordonne, decalage, decalage2, taille, true);
		if (departureStationChanged)
			pathBuilder.setOrigin(station);
		ordonne = departureStationCollapsableArea.getArea().y + departureStationCollapsableArea.getArea().height
				+ decalage;

		/***************************************************************************************************************
		 * Arrival
		 */
		s = father.lg("Arrival");
		taille = prepareAutoCompletionStationTextBox(arrivalStationNew, arrivalStationTextBox,
				arrivalStationCollapsableArea, s, ordonne, decalage, decalage2);
		arrivalStationCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		station = drawAutoCompletionStationTextBox(arrivalStationNew, arrivalStationTextBox,
				arrivalStationCollapsableArea, ordonne, decalage, decalage2, taille, true);
		if (arrivalStationChanged)
			pathBuilder.setDestination(station);
		ordonne = arrivalStationCollapsableArea.getArea().y + arrivalStationCollapsableArea.getArea().height + decalage;

		/***************************************************************************************************************
		 * Travel criteria
		 */
		s = father.lg("TravelCriteria");
		if (!travelCriteriaCollapsableArea.isCollapsed()) {
			width = getWidth() - decalage2;
			tmp = getWidthString(father.lg("FirstTravelCriteria"), buffer, father.getSizeAdapteur().getSmallFont());
			if ((cpt = getWidthString(father.lg("SecondTravelCriteria"), buffer, father.getSizeAdapteur()
					.getSmallFont())) > tmp)
				tmp = cpt;
			tmp += decalage + decalageDemi + (decalage >> 2);
			/***********************************************************************************************************
			 * main Cheaper
			 */
			travelCriteriaRadioBoxs[0].prepareArea(buffer, tmp, travelCriteriaCollapsableArea
					.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
							.getIntermediateFont()), father.lg("Cheaper"), father.getSizeAdapteur().getSmallFont());
			/***********************************************************************************************************
			 * main faster
			 */
			travelCriteriaRadioBoxs[1].prepareArea(buffer, travelCriteriaRadioBoxs[0].getArea().x
					+ travelCriteriaRadioBoxs[0].getArea().width + decalageDemi,
					travelCriteriaRadioBoxs[0].getArea().y, father.lg("Faster"), father.getSizeAdapteur()
							.getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((travelCriteriaRadioBoxs[1].getArea().x + travelCriteriaRadioBoxs[1].getArea().width) > width)
				travelCriteriaRadioBoxs[1].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[1].getArea().y
						+ travelCriteriaRadioBoxs[1].getArea().height + decalageDemi, father.lg("Faster"), father
						.getSizeAdapteur().getSmallFont());
			/***********************************************************************************************************
			 * main changes
			 */
			travelCriteriaRadioBoxs[2].prepareArea(buffer, travelCriteriaRadioBoxs[1].getArea().x
					+ travelCriteriaRadioBoxs[1].getArea().width + decalageDemi,
					travelCriteriaRadioBoxs[1].getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur()
							.getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((travelCriteriaRadioBoxs[2].getArea().x + travelCriteriaRadioBoxs[2].getArea().width) > width)
				travelCriteriaRadioBoxs[2].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[2].getArea().y
						+ travelCriteriaRadioBoxs[2].getArea().height + decalageDemi, father.lg("FewerChanges"), father
						.getSizeAdapteur().getSmallFont());
			/***********************************************************************************************************
			 * minor Cheaper
			 */
			travelCriteriaRadioBoxs[3].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[2].getArea().y
					+ travelCriteriaRadioBoxs[2].getArea().height + decalageDemi, father.lg("Cheaper"), father
					.getSizeAdapteur().getSmallFont());
			/***********************************************************************************************************
			 * minor faster
			 */
			travelCriteriaRadioBoxs[4].prepareArea(buffer, travelCriteriaRadioBoxs[3].getArea().x
					+ travelCriteriaRadioBoxs[3].getArea().width + decalageDemi,
					travelCriteriaRadioBoxs[3].getArea().y, father.lg("Faster"), father.getSizeAdapteur()
							.getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((travelCriteriaRadioBoxs[4].getArea().x + travelCriteriaRadioBoxs[4].getArea().width) > width)
				travelCriteriaRadioBoxs[4].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[4].getArea().y
						+ travelCriteriaRadioBoxs[4].getArea().height + decalageDemi, father.lg("Faster"), father
						.getSizeAdapteur().getSmallFont());
			/***********************************************************************************************************
			 * minor changes
			 */
			travelCriteriaRadioBoxs[5].prepareArea(buffer, travelCriteriaRadioBoxs[4].getArea().x
					+ travelCriteriaRadioBoxs[4].getArea().width + decalageDemi,
					travelCriteriaRadioBoxs[4].getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur()
							.getSmallFont());
			// si ca sort du cadre, retour à la ligne
			if ((travelCriteriaRadioBoxs[5].getArea().x + travelCriteriaRadioBoxs[5].getArea().width) > width)
				travelCriteriaRadioBoxs[5].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[5].getArea().y
						+ travelCriteriaRadioBoxs[5].getArea().height + decalageDemi, father.lg("FewerChanges"), father
						.getSizeAdapteur().getSmallFont());

		}
		travelCriteriaCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		if (!travelCriteriaCollapsableArea.isCollapsed()) {
			buffer.setFont(father.getSizeAdapteur().getSmallFont());
			s = father.lg("FirstTravelCriteria");
			buffer.drawString(s, decalage + decalageDemi, travelCriteriaRadioBoxs[0].getArea().y
					+ getHeightString(s, buffer));
			s = father.lg("SecondTravelCriteria");
			buffer.drawString(s, decalage + decalageDemi, travelCriteriaRadioBoxs[3].getArea().y
					+ getHeightString(s, buffer));
			for (PTRadioBox t : travelCriteriaRadioBoxs)
				t.draw(buffer, father.getSizeAdapteur().getSmallFont(), father.getSkin().getColorSubAreaInside(),
						father.getSkin().getColorLetter());
		}
		ordonne = travelCriteriaCollapsableArea.getArea().y + travelCriteriaCollapsableArea.getArea().height + decalage;

		/***************************************************************************************************************
		 * Travel mode
		 */
		chk = null;
		s = father.lg("TravelMode");
		if (!travelModeCollapsableArea.isCollapsed()) {
			for (PairPTCheckBox p : travelModeCheckBoxs) {
				p.chk.prepareArea(buffer, (chk == null) ? decalage + decalageDemi : chk.getArea().x
						+ chk.getArea().width + decalageDemi, (chk == null) ? travelCriteriaCollapsableArea
						.getFirstOrdonneForComponents(buffer, decalage, ordonne, s, father.getSizeAdapteur()
								.getIntermediateFont()) : chk.getArea().y, p.name, father.getSizeAdapteur()
						.getSmallFont());
				if ((p.chk.getArea().x + p.chk.getArea().width) > (getWidth() - decalage)) {
					p.chk.prepareArea(buffer, decalage + decalageDemi, p.chk.getArea().y + p.chk.getArea().height
							+ decalageDemi, p.name, father.getSizeAdapteur().getSmallFont());
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
		ordonne = travelModeCollapsableArea.getArea().y + travelModeCollapsableArea.getArea().height + decalage;

		/***************************************************************************************************************
		 * Services
		 */
		s = father.lg("Services");
		if (!servicesCollapsableArea.isCollapsed() && !ServicesRadioBoxs.isEmpty()) {
			pos = new int[3];
			width = 0;
			cpt = 0;
			// on trouve la largueur de la colonne des services
			ord = new byte[ServicesRadioBoxs.size()];
			for (PairPTRadioBoxs p : ServicesRadioBoxs) {
				tmp = getWidthString(p.service.getName(), buffer, father.getSizeAdapteur().getSmallFont());
				if (tmp > (getWidth() - decalage2 - decalage >> 1)) {
					ord[cpt] = 0;
					tmp = 0;
					String[] splited = p.service.getName().split(" ");
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
			width = getHeightString("", buffer, father.getSizeAdapteur().getSmallFont()) + decalageDemi;
			cpt = -1;
			for (PairPTRadioBoxs p : ServicesRadioBoxs) {
				tmp += width;
				if (ord[++cpt]-- >= 1)
					tmp += width * ord[cpt] >> 1;
				for (int i = 0; i < pos.length; i++) {
					p.rbs[i].prepareArea(buffer, pos[i], tmp, "", father.getSizeAdapteur().getSmallFont(), true, false);
				}
				if (ord[cpt] >= 1)
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
			width += decalageDemi;
			for (PairPTRadioBoxs p : ServicesRadioBoxs) {
				tmp += width;
				p.rbs[0].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[1].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				p.rbs[2].draw(buffer, father.getSizeAdapteur().getSmallFont(),
						father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
				buffer.setFont(father.getSizeAdapteur().getSmallFont());
				if (getWidthString(p.service.getName(), buffer, father.getSizeAdapteur().getSmallFont()) > (servicesCollapsableArea
						.getArea().width >> 1)) {
					String[] splited = p.service.getName().split(" ");
					tmp -= width;
					for (String miniS : splited) {
						tmp += width;
						buffer.drawString(miniS, decalage << 1, tmp);
					}
				} else {
					buffer.drawString(p.service.getName(), decalage << 1, tmp);
				}
				buffer.setColor(father.getNetworkColorManager().getColor(p.service));
				buffer.fillOval(decalage + (decalage >> 2), p.rbs[0].getArea().y + (decalage >> 3) + (decalage >> 3),
						father.getSizeAdapteur().getSizeSmallFont() >> 1,
						father.getSizeAdapteur().getSizeSmallFont() >> 1);
				buffer.setColor(father.getSkin().getColorLetter());
				buffer.drawOval(decalage + (decalage >> 2), p.rbs[0].getArea().y + (decalage >> 3) + (decalage >> 3),
						father.getSizeAdapteur().getSizeSmallFont() >> 1,
						father.getSizeAdapteur().getSizeSmallFont() >> 1);
			}
		} else
			servicesCollapsableArea.update(buffer, decalage, ordonne, s,
					father.getSizeAdapteur().getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father
							.getSkin().getColorLetter());
		ordonne = servicesCollapsableArea.getArea().y + servicesCollapsableArea.getArea().height + decalage;

		/***************************************************************************************************************
		 * Station intermediaire
		 */
		s = father.lg("IntermediatesStations");
		taille = prepareAutoCompletionStationTextBox(intermediatesStationsArea, intermediatesStationsTextBox,
				intermediatesStationsCollapsableArea, s, ordonne, decalage, decalage2);
		if (!intermediatesStationsCollapsableArea.isCollapsed()) {
			intermediatesStationsButton.prepareArea(buffer, intermediatesStationsTextBox.getArea().x
					+ intermediatesStationsTextBox.getArea().width + decalage,
					intermediatesStationsTextBox.getArea().y, imageOk);
			rec.setBounds(intermediatesStationsTextBox.getArea().x, intermediatesStationsArea.getArea().height
					+ intermediatesStationsArea.getArea().y, intermediatesStationsTextBox.getArea().width, 0);
			intermediatesStationsArea.getArea().height += pathBuilder.getCurrentPathInGraph().getStepsCount()
					* (decalageDemi + father.getSizeAdapteur().getSizeIntermediateFont() + taille + decalage);
		}
		intermediatesStationsCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		station = drawAutoCompletionStationTextBox(intermediatesStationsArea, intermediatesStationsTextBox,
				intermediatesStationsCollapsableArea, ordonne, decalage, decalage2, taille, false);
		if (!intermediatesStationsCollapsableArea.isCollapsed()) {
			if (station == null || pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)
					|| pathBuilder.getCurrentPathInGraph().containsSteps(station)
					|| pathBuilder.getCurrentPathInGraph().getDestination() == station
					|| pathBuilder.getCurrentPathInGraph().getOrigin() == station) {
				intermediatesStationsButton.prepareArea(buffer, getWidth(), getHeight(), imageOk);
			} else
				intermediatesStationsButton.draw(buffer, imageOk);
			itS = pathBuilder.getCurrentPathInGraph().getStepsIter();
			// x = intermediatesStationsTextBox.getArea().x;
			// y = intermediatesStationsTextBox.getArea().y + intermediatesStationsTextBox.getArea().height
			// + decalageDemi + taille;
			// rec.height += decalageDemi;
			while (itS.hasNext()) {
				station = itS.next();
				rec.y += rec.height + decalageDemi;
				rec.height = 0;
				buffer.setFont(father.getSizeAdapteur().getIntermediateFont());
				rec.height += getHeightString(station.getName(), buffer);
				buffer.drawString(station.getName(), rec.x + decalageDemi, rec.y + rec.height);
				// buffer.setFont(father.getSizeAdapteur().getIntermediateFont());
				drawRoutesAndServices(rec.x + decalageDemi, rec.y + rec.height + decalageDemi, decalage, taille,
						station);
				rec.height += taille + decalage;
				buffer.drawRect(rec.x, rec.y, rec.width, rec.height);
				intermediatesStationsDel.get(station.getId()).update(buffer,
						rec.x + rec.width - decalage - imageDel.getIconWidth(),
						rec.y + (rec.height - imageDel.getIconHeight() >> 1), imageDel);
			}
		}
		// dessins des station déja rentré
		ordonne = intermediatesStationsCollapsableArea.getArea().y
				+ intermediatesStationsCollapsableArea.getArea().height + decalage;

		/***************************************************************************************************************
		 * Station à éviter
		 */
		s = father.lg("AvoidsStations");
		taille = prepareAutoCompletionStationTextBox(avoidsStationsArea, avoidsStationsTextBox,
				avoidsStationsCollapsableArea, s, ordonne, decalage, decalage2);
		if (!avoidsStationsCollapsableArea.isCollapsed()) {
			avoidsStationsButton.prepareArea(buffer, avoidsStationsTextBox.getArea().x
					+ avoidsStationsTextBox.getArea().width + decalage, avoidsStationsTextBox.getArea().y, imageOk);
			rec.setBounds(avoidsStationsTextBox.getArea().x, avoidsStationsArea.getArea().height
					+ avoidsStationsArea.getArea().y, avoidsStationsTextBox.getArea().width, 0);
			avoidsStationsArea.getArea().height += pathBuilder.getCurrentPathInGraph().getAvoidStationsCount()
					* (decalageDemi + father.getSizeAdapteur().getSizeIntermediateFont() + taille + decalage);
		}
		// TODO z_paint
		avoidsStationsCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		station = drawAutoCompletionStationTextBox(avoidsStationsArea, avoidsStationsTextBox,
				avoidsStationsCollapsableArea, ordonne, decalage, decalage2, taille, false);
		if (!avoidsStationsCollapsableArea.isCollapsed()) {
			if (station == null || pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)
					|| pathBuilder.getCurrentPathInGraph().containsSteps(station)
					|| pathBuilder.getCurrentPathInGraph().getDestination() == station
					|| pathBuilder.getCurrentPathInGraph().getOrigin() == station) {
				avoidsStationsButton.prepareArea(buffer, getWidth(), getHeight(), imageOk);
			} else
				avoidsStationsButton.draw(buffer, imageOk);
			itS = pathBuilder.getCurrentPathInGraph().getAvoidStationsIter();
			// x = avoidsStationsTextBox.getArea().x;
			// y = avoidsStationsTextBox.getArea().y + avoidsStationsTextBox.getArea().height
			// + decalageDemi + taille;
			// rec.height += decalageDemi;
			while (itS.hasNext()) {
				station = itS.next();
				rec.y += rec.height + decalageDemi;
				rec.height = 0;
				buffer.setFont(father.getSizeAdapteur().getIntermediateFont());
				rec.height += getHeightString(station.getName(), buffer);
				buffer.drawString(station.getName(), rec.x + decalageDemi, rec.y + rec.height);
				// buffer.setFont(father.getSizeAdapteur().getIntermediateFont());
				drawRoutesAndServices(rec.x + decalageDemi, rec.y + rec.height + decalageDemi, decalage, taille,
						station);
				rec.height += taille + decalage;
				buffer.drawRect(rec.x, rec.y, rec.width, rec.height);
				avoidsStationsDel.get(station.getId()).update(buffer,
						rec.x + rec.width - decalage - imageDel.getIconWidth(),
						rec.y + (rec.height - imageDel.getIconHeight() >> 1), imageDel);
			}
		}
		// dessins des station déja rentré
		ordonne = avoidsStationsCollapsableArea.getArea().y + avoidsStationsCollapsableArea.getArea().height + decalage;
		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();

		/***************************************************************************************************************
		 * on met le flag de modification de départ/arrvié à false
		 */
		departureStationChanged = false;
		arrivalStationChanged = false;

	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("NewTravel"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		if (pathBuilder != null)
			lowerBar.setRightCmd(father.lg("FindAPath"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(pathBuilder.getCurrentPathInGraph());
					if (pathBuilder.isValideForSolving()) {
						father.setCurrentState(IhmReceivingStates.COMPUT_TRAVEL);
					}
				}
			});
		lowerBar.repaint();
	}

	/**
	 * Définit le trajet courant qui sera utiliser pour définir les paramètres de l'utilisateur. On considère qu'il est
	 * vierge. Si l'objet est null, on affichera qu'on peut rien faire, et on ne proposera que de retourné au menu
	 * principale
	 * 
	 * @param pathBuilder
	 */
	public void setPathInGraphConstraintBuilder(PathInGraphConstraintBuilder pathBuilder) {
		this.pathBuilder = pathBuilder;
		initPathInGraphConstraintBuilder();
	}

	/**
	 * Retourne le trajet sur lequel on travaillait
	 * 
	 * @return
	 */
	public PathInGraphConstraintBuilder getPathInGraphConstraintBuilder() {
		return this.pathBuilder;
	}

	/**
	 * Initialise le trajet en fonction des parmètres systèmes
	 */
	protected void initPathInGraphConstraintBuilder() {
		if (pathBuilder == null)
			return;

		Iterator<KindRoute> itR;
		KindRoute kind;
		Iterator<Service> itS;
		Service service;
		String s;

		if (father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.COST.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.COST);
		else if (father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.TIME.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
		else if (father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.CHANGE.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.CHANGE);

		if (father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.COST.toString()) == 0)
			pathBuilder.setMinorCriterious(CriteriousForLowerPath.COST);
		else if (father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.TIME.toString()) == 0)
			pathBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
		else if (father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo(
				Algo.CriteriousForLowerPath.CHANGE.toString()) == 0)
			pathBuilder.setMinorCriterious(CriteriousForLowerPath.CHANGE);

		itR = father.getKindRoutes();
		while (itR.hasNext())
			if (father.getConfig(SettingsKey.TRAVEL_MODE_ + (kind = itR.next()).getKindOf()).compareTo(
					SettingsValue.DISABLE.toString()) == 0)
				pathBuilder.addRefusedKindRoute(kind);

		itS = father.getServices();
		while (itS.hasNext()) {
			if ((father.getConfig(s = (SettingsKey.SERVICES_.toString() + (service = itS.next()).getId())))
					.compareTo(SettingsValue.Always.toString()) == 0)
				pathBuilder.addSeviceAlways(service);
			else if (father.getConfig(s).compareTo(SettingsValue.Once.toString()) == 0)
				pathBuilder.addSeviceOnce(service);
		}
	}
}
