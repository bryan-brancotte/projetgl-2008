package ihm.smartPhone.statePanels;

import graphNetwork.KindRoute;
import graphNetwork.PathInGraph;
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
import ihm.smartPhone.libPT.PTArea;
import ihm.smartPhone.libPT.PTAutoCompletionTextBox;
import ihm.smartPhone.libPT.PTButton;
import ihm.smartPhone.libPT.PTCheckBox;
import ihm.smartPhone.libPT.PTCollapsableArea;
import ihm.smartPhone.libPT.PTRadioBox;
import ihm.smartPhone.libPT.PTRadioBoxGroup;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.libPT.PanelTooled;
import ihm.smartPhone.statePanels.component.PairPTCheckBox;
import ihm.smartPhone.statePanels.component.PairPTRadioBoxs;
import ihm.smartPhone.statePanels.component.ServiceToolTipText;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.CodeExecutor2P;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	public enum NewTravelPanelState {
		NEW_TRAVEL, EDIT_TRAVEL, LOST_TRAVEL, BUILDING;
	};

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
	 * L'image OK pour ajouter une station
	 */
	protected ImageIcon imageFind;
	/**
	 * Etat de l'objet
	 */
	NewTravelPanelState mode = NewTravelPanelState.BUILDING;
	/**
	 * Boolean permetant de savoir si l'utilisateur à déja essayé de lancer le calcul sans dep ou arr
	 */
	protected boolean redNeeded = false;
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
	protected LinkedList<PairPTRadioBoxs> servicesRadioBoxs;
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
	protected PTButton intermediatesStationsFind;

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
	protected PTButton avoidsStationsFind;

	/**
	 * Variables pour la zone de texte de la station de départ
	 */
	protected boolean departureStationChanged;
	protected PTArea departureStationArea;
	protected PTAutoCompletionTextBox departureStationTextBox;
	protected PTCollapsableArea departureStationCollapsableArea;
	protected PTButton departureStationFind;

	/**
	 * Variables pour la zone de texte de la station de arrivé
	 */
	protected boolean arrivalStationChanged;
	protected PTArea arrivalStationNew;
	protected PTAutoCompletionTextBox arrivalStationTextBox;
	protected PTCollapsableArea arrivalStationCollapsableArea;
	protected PTButton arrivalStationFind;

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

			protected ServiceToolTipText overed = null;

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				for (ServiceToolTipText s : serviceDisplayed)
					if (s.contains(e.getX(), e.getY())) {
						if (overed != s) {
							s.maybeOvered(e.getX(), e.getY());
							overed = s;
						}
						me.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						return;
					}
				if (overed == null)
					return;
				lowerBar.setLeftTitle("");
				lowerBar.setLeftValue("");
				lowerBar.repaint();
				overed = null;
				me.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!servicesCollapsableArea.isCollapsed())
					return;
				boolean changed = false;
				for (ServiceToolTipText s : serviceDisplayed)
					changed = s.contains(e.getX(), e.getY()) || changed;
				if (!changed)
					return;
				servicesCollapsableArea.changeCollapseState();
				repaint();
			}
		});
		buildInterface();
	}

	/**
	 * Construction du contenu
	 */
	protected void buildInterface() {
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
		travelCriteriaRadioBoxs[1] = makeRadioButton(new PTRadioBoxGroup[] { grp, grpTrans[1] }, ex);// Faster
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
		travelCriteriaRadioBoxs[0].setClicked((s = father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()))
				.compareTo(Algo.CriteriousForLowerPath.COST.toString()) == 0);
		travelCriteriaRadioBoxs[1].setClicked(s.compareTo(Algo.CriteriousForLowerPath.TIME.toString()) == 0);
		travelCriteriaRadioBoxs[2].setClicked(s.compareTo(Algo.CriteriousForLowerPath.CHANGE.toString()) == 0);
		travelCriteriaRadioBoxs[3].setClicked((s = father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()))
				.compareTo(Algo.CriteriousForLowerPath.COST.toString()) == 0);
		travelCriteriaRadioBoxs[4].setClicked(s.compareTo(Algo.CriteriousForLowerPath.TIME.toString()) == 0);
		travelCriteriaRadioBoxs[5].setClicked(s.compareTo(Algo.CriteriousForLowerPath.CHANGE.toString()) == 0);
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
		servicesRadioBoxs = new LinkedList<PairPTRadioBoxs>();
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
			servicesRadioBoxs.add(new PairPTRadioBoxs(rbs, ser, s));
		}

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
		departureStationFind = makeButton(new CodeExecutor1P<NewTravelPanel>(this) {
			@Override
			public void execute() {
				Container c = this.origine.getParent();
				c.removeAll();
				ListingPanel l;
				c.add(l = new ListingPanel(father, upperBar, lowerBar, new CodeExecutor2P<Container, NewTravelPanel>(c,
						this.origine) {
					public void execute() {
						departureStationTextBox.setText(((ListingPanel) this.origineA.getComponent(0))
								.getStationSelected());
						departureStationChanged = true;
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}, new CodeExecutor2P<Container, NewTravelPanel>(c, this.origine) {
					public void execute() {
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}));
				l.giveControle();
				c.validate();
			}
		});
		departureStationTextBox = makeAutoCompletionTextBox(stations, new CodeExecutor() {

			@Override
			public void execute() {
				departureStationChanged = true;
			}
		});
		departureStationCollapsableArea.addComponent(departureStationTextBox);
		departureStationCollapsableArea.addComponent(departureStationArea);
		departureStationCollapsableArea.addComponent(departureStationFind);

		/***************************************************************************************************************
		 * arrivalStation
		 */
		arrivalStationCollapsableArea = makeCollapsableArea();
		arrivalStationNew = makeArea();
		arrivalStationFind = makeButton(new CodeExecutor1P<NewTravelPanel>(this) {
			@Override
			public void execute() {
				Container c = this.origine.getParent();
				c.removeAll();
				ListingPanel l;
				c.add(l = new ListingPanel(father, upperBar, lowerBar, new CodeExecutor2P<Container, NewTravelPanel>(c,
						this.origine) {
					public void execute() {
						arrivalStationTextBox.setText(((ListingPanel) this.origineA.getComponent(0))
								.getStationSelected());
						arrivalStationChanged = true;
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}, new CodeExecutor2P<Container, NewTravelPanel>(c, this.origine) {
					public void execute() {
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}));
				l.giveControle();
				c.validate();
			}
		});
		arrivalStationTextBox = makeAutoCompletionTextBox(stations, new CodeExecutor() {

			@Override
			public void execute() {
				arrivalStationChanged = true;
			}
		});
		arrivalStationCollapsableArea.addComponent(arrivalStationTextBox);
		arrivalStationCollapsableArea.addComponent(arrivalStationNew);
		arrivalStationCollapsableArea.addComponent(arrivalStationFind);

		/***************************************************************************************************************
		 * Station inter
		 */
		intermediatesStationsCollapsableArea = makeCollapsableArea();
		intermediatesStationsCollapsableArea.changeCollapseState();
		intermediatesStationsArea = makeArea();
		intermediatesStationsFind = makeButton(new CodeExecutor1P<NewTravelPanel>(this) {
			@Override
			public void execute() {
				Container c = this.origine.getParent();
				c.removeAll();
				ListingPanel l;
				c.add(l = new ListingPanel(father, upperBar, lowerBar, new CodeExecutor2P<Container, NewTravelPanel>(c,
						this.origine) {
					public void execute() {
						// intermediatesStationsTextBox.setText(((ListingPanel) this.origineA.getComponent(0))
						// .getStationSelected());
						recordChangedSetting(intermediatesStationsAdd, ((ListingPanel) this.origineA.getComponent(0))
								.getStationSelected());
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}, new CodeExecutor2P<Container, NewTravelPanel>(c, this.origine) {
					public void execute() {
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}));
				l.giveControle();
				c.validate();
			}
		});
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
		intermediatesStationsCollapsableArea.addComponent(intermediatesStationsFind);
		intermediatesStationsDel = new Hashtable<Integer, PTButton>();

		// TODO z_make
		/***************************************************************************************************************
		 * Station avoid
		 */
		avoidsStationsCollapsableArea = makeCollapsableArea();
		avoidsStationsCollapsableArea.changeCollapseState();
		avoidsStationsArea = makeArea();
		avoidsStationsFind = makeButton(new CodeExecutor1P<NewTravelPanel>(this) {
			@Override
			public void execute() {
				Container c = this.origine.getParent();
				c.removeAll();
				ListingPanel l;
				c.add(l = new ListingPanel(father, upperBar, lowerBar, new CodeExecutor2P<Container, NewTravelPanel>(c,
						this.origine) {
					public void execute() {
						// avoidsStationsTextBox.setText(((ListingPanel) this.origineA.getComponent(0))
						// .getStationSelected());
						recordChangedSetting(avoidsStationsAdd, ((ListingPanel) this.origineA.getComponent(0))
								.getStationSelected());
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle(); 
					};
				}, new CodeExecutor2P<Container, NewTravelPanel>(c, this.origine) {
					public void execute() {
						this.origineA.removeAll();
						this.origineA.add(this.origineB);
						this.origineA.validate();
						this.origineB.giveControle();
					};
				}));
				l.giveControle();
				c.validate();
			}
		});
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
		avoidsStationsCollapsableArea.addComponent(avoidsStationsFind);
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
		case minorTravelCriteria:
			if (travelCriteriaRadioBoxs[0].isClicked())
				pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.COST);
			else if (travelCriteriaRadioBoxs[1].isClicked())
				pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.TIME);
			else if (travelCriteriaRadioBoxs[2].isClicked())
				pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.CHANGE);
			else if (travelCriteriaRadioBoxs[3].isClicked()) {
				travelCriteriaRadioBoxs[1].setClicked(true);
				pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.TIME);
			} else {
				travelCriteriaRadioBoxs[0].setClicked(true);
				pathBuilder.setMainCriterious(Algo.CriteriousForLowerPath.COST);
			}

			if (travelCriteriaRadioBoxs[3].isClicked())
				pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.COST);
			else if (travelCriteriaRadioBoxs[4].isClicked())
				pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.TIME);
			else if (travelCriteriaRadioBoxs[5].isClicked())
				pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.CHANGE);
			else if (travelCriteriaRadioBoxs[0].isClicked()) {
				travelCriteriaRadioBoxs[4].setClicked(true);
				pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.TIME);
			} else {
				travelCriteriaRadioBoxs[3].setClicked(true);
				pathBuilder.setMinorCriterious(Algo.CriteriousForLowerPath.COST);
			}

			return;
		case services:
			for (PairPTRadioBoxs p : servicesRadioBoxs) {
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
			// System.out.println("intermediatesStationsAdd" + s + ":" + station);
			if (station != null
					&& (mode == NewTravelPanelState.BUILDING || !pathBuilder.getCurrentPathInGraph().containsSteps(
							station))) {
				intermediatesStationsDel.put(station.getId(), makeButton(new CodeExecutor2P<PanelTooled, String>(this,
						s) {
					@Override
					public void execute() {
						recordChangedSetting(intermediatesStationsRemove, this.origineB);
						this.origineA.repaint();
					}
				}));
				if (mode != NewTravelPanelState.BUILDING)
					pathBuilder.addStepStations(station);
				// shouldDoubleRepaint = true;
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
			// System.out.println("avoidsStationsAdd" + s + ":" + station);
			if (station != null
					&& (mode == NewTravelPanelState.BUILDING || !pathBuilder.getCurrentPathInGraph()
							.containsAvoidStation(station))) {
				avoidsStationsDel.put(station.getId(), makeButton(new CodeExecutor2P<PanelTooled, String>(this, s) {
					@Override
					public void execute() {
						recordChangedSetting(avoidsStationsRemove, this.origineB);
						this.origineA.repaint();
					}
				}));
				if (mode != NewTravelPanelState.BUILDING)
					pathBuilder.addAvoidStations(station);
				// shouldDoubleRepaint = true;
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
			PTCollapsableArea stationCollapsableArea, PTButton stationFind, String title, int ordonne, int decalage,
			int decalage2) {
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
			if (stationFind != null)
				stationFind.prepareArea(buffer, stationTextBox.getArea().x + stationTextBox.getArea().width
						+ (decalage >> 1), stationTextBox.getArea().y
						+ (stationTextBox.getArea().height - imageFind.getIconHeight() >> 1), imageFind);
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
			PTCollapsableArea stationCollapsableArea, PTButton stationFind, int ordonne, int decalage, int decalage2,
			int taille, boolean shouldFillTheField) {
		if (stationCollapsableArea.isCollapsed())
			return null;
		stationTextBox.draw(buffer, father.getSkin().getColorInside(), father.getSkin().getColorLetter());
		Station station = this.stationsHash.get(stationTextBox.getText());
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		if (stationFind != null)
			stationFind.draw(buffer, imageFind);
		if (station != null) {
			int xActu, yActu;
			xActu = stationTextBox.getArea().x;
			yActu = stationTextBox.getArea().y + stationTextBox.getArea().height + (decalage >> 1);
			drawRoutesAndServices(xActu + (decalage >> 1), yActu, decalage, taille, station);
			return station;
		}
		if (!shouldFillTheField)
			return null;
		if (stationTextBox.getText().compareTo("") == 0) {
			if (redNeeded)
				buffer.setColor(Color.red);
			buffer.drawString(father.lg("FillThisField"), stationTextBox.getArea().x, stationTextBox.getArea().y
					+ stationTextBox.getArea().height + (decalage >> 1)
					+ PanelDoubleBufferingSoftwear.getHeightString(father.lg("InvalideStation"), buffer));
			return null;
		}
		buffer.setColor(Color.red);
		buffer.drawString(father.lg("InvalideStation"), stationTextBox.getArea().x, stationTextBox.getArea().y
				+ stationTextBox.getArea().height + (decalage >> 1)
				+ PanelDoubleBufferingSoftwear.getHeightString(father.lg("InvalideStation"), buffer));
		redNeeded = false;
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
		int w,h;
		while (itRoute.hasNext()) {
				buffer.setColor(father.getNetworkColorManager().getColor(route=itRoute.next()));
				w = PanelDoubleBufferingSoftwear.getWidthString(route.getId(), buffer);
				h = PanelDoubleBufferingSoftwear.getHeightString(route.getId(), buffer);
				buffer.fillRect(xActu + (taille - w >> 1)-1, yActu + (taille - h >> 1), w+2, h+2);
				buffer.setColor(father.getSkin().getColorLetter());
				buffer.drawRect(xActu + (taille - w >> 1)-1, yActu + (taille - h >> 1), w+2, h+2);
				buffer.drawString(route.getId(), xActu + (taille - w >> 1), yActu + (taille + h >> 1));
				xActu += taille + (decalage >> 1);
		}
//		while (itRoute.hasNext()) {
//			route = itRoute.next();
//			s += route.getId();
//			if (itRoute.hasNext())
//				s += ", ";
//		}
//		buffer.drawString(s, xActu, yActu + PanelDoubleBufferingSoftwear.getHeightString(s, buffer));
//		xActu += PanelDoubleBufferingSoftwear.getWidthString(s, buffer) + (decalage >> 1);

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
		// System.out.println("NewTravelPanel.paint("+shouldDoubleRepaint+")");
		if (pathBuilder == null) {
			father.setErrorState(father.lg("ERROR_Impossible"), father.lg("ERROR_ReturnNullConstraintBuilder"));
			return;
		}

		draw();
		if (shouldDoubleRepaint) {
			shouldDoubleRepaint = false;
			draw();
			// repaint();
			// return;
		}
		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
		// System.out.println("NewTravelPanel.paintING");
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
			imageFind = null;
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			// buffer.setColor(father.getSkin().getColorLetter());
			if (imageOk == null || imageOk.getIconHeight() != father.getSizeAdapteur().getSizeLargeFont()) {
				imageOk = ImageLoader.getRessourcesImageIcone("button_ok", father.getSizeAdapteur().getSizeLargeFont(),
						father.getSizeAdapteur().getSizeLargeFont());
				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont(), true);
				imageFind = ImageLoader.getRessourcesImageIcone("loupe", father.getSizeAdapteur().getSizeLargeFont(),
						father.getSizeAdapteur().getSizeLargeFont());
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		/*****
		 * Swap des liste de zone de service
		 */
		servicePooled.addAll(serviceDisplayed);
		serviceDisplayed.clear();

		/***************************************************************************************************************
		 * Departure
		 */
		if (this.mode == NewTravelPanelState.LOST_TRAVEL)
			s = father.lg("WhereYouAre");
		else
			s = father.lg("Departure");
		taille = prepareAutoCompletionStationTextBox(departureStationArea, departureStationTextBox,
				departureStationCollapsableArea, departureStationFind, s, ordonne, decalage, decalage2);
		departureStationCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
				.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		station = drawAutoCompletionStationTextBox(departureStationArea, departureStationTextBox,
				departureStationCollapsableArea, departureStationFind, ordonne, decalage, decalage2, taille, true);
		if (departureStationChanged)
			pathBuilder.setOrigin(station);
		ordonne = departureStationCollapsableArea.getArea().y + departureStationCollapsableArea.getArea().height
				+ decalage;

		/***************************************************************************************************************
		 * Arrival
		 */
		if (this.mode != NewTravelPanelState.LOST_TRAVEL) {
			s = father.lg("Arrival");
			taille = prepareAutoCompletionStationTextBox(arrivalStationNew, arrivalStationTextBox,
					arrivalStationCollapsableArea, arrivalStationFind, s, ordonne, decalage, decalage2);
			arrivalStationCollapsableArea
					.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
							.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			station = drawAutoCompletionStationTextBox(arrivalStationNew, arrivalStationTextBox,
					arrivalStationCollapsableArea, arrivalStationFind, ordonne, decalage, decalage2, taille, true);
			if (arrivalStationChanged)
				pathBuilder.setDestination(station);
			ordonne = arrivalStationCollapsableArea.getArea().y + arrivalStationCollapsableArea.getArea().height
					+ decalage;
		}

		/***************************************************************************************************************
		 * Travel criteria
		 */
		if (this.mode != NewTravelPanelState.LOST_TRAVEL) {
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
						+ travelCriteriaRadioBoxs[0].getArea().width + decalageDemi, travelCriteriaRadioBoxs[0]
						.getArea().y, father.lg("Faster"), father.getSizeAdapteur().getSmallFont());
				// si ca sort du cadre, retour à la ligne
				if ((travelCriteriaRadioBoxs[1].getArea().x + travelCriteriaRadioBoxs[1].getArea().width) > width)
					travelCriteriaRadioBoxs[1].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[1].getArea().y
							+ travelCriteriaRadioBoxs[1].getArea().height + decalageDemi, father.lg("Faster"), father
							.getSizeAdapteur().getSmallFont());
				/***********************************************************************************************************
				 * main changes
				 */
				travelCriteriaRadioBoxs[2].prepareArea(buffer, travelCriteriaRadioBoxs[1].getArea().x
						+ travelCriteriaRadioBoxs[1].getArea().width + decalageDemi, travelCriteriaRadioBoxs[1]
						.getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur().getSmallFont());
				// si ca sort du cadre, retour à la ligne
				if ((travelCriteriaRadioBoxs[2].getArea().x + travelCriteriaRadioBoxs[2].getArea().width) > width)
					travelCriteriaRadioBoxs[2].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[2].getArea().y
							+ travelCriteriaRadioBoxs[2].getArea().height + decalageDemi, father.lg("FewerChanges"),
							father.getSizeAdapteur().getSmallFont());
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
						+ travelCriteriaRadioBoxs[3].getArea().width + decalageDemi, travelCriteriaRadioBoxs[3]
						.getArea().y, father.lg("Faster"), father.getSizeAdapteur().getSmallFont());
				// si ca sort du cadre, retour à la ligne
				if ((travelCriteriaRadioBoxs[4].getArea().x + travelCriteriaRadioBoxs[4].getArea().width) > width)
					travelCriteriaRadioBoxs[4].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[4].getArea().y
							+ travelCriteriaRadioBoxs[4].getArea().height + decalageDemi, father.lg("Faster"), father
							.getSizeAdapteur().getSmallFont());
				/***********************************************************************************************************
				 * minor changes
				 */
				travelCriteriaRadioBoxs[5].prepareArea(buffer, travelCriteriaRadioBoxs[4].getArea().x
						+ travelCriteriaRadioBoxs[4].getArea().width + decalageDemi, travelCriteriaRadioBoxs[4]
						.getArea().y, father.lg("FewerChanges"), father.getSizeAdapteur().getSmallFont());
				// si ca sort du cadre, retour à la ligne
				if ((travelCriteriaRadioBoxs[5].getArea().x + travelCriteriaRadioBoxs[5].getArea().width) > width)
					travelCriteriaRadioBoxs[5].prepareArea(buffer, tmp, travelCriteriaRadioBoxs[5].getArea().y
							+ travelCriteriaRadioBoxs[5].getArea().height + decalageDemi, father.lg("FewerChanges"),
							father.getSizeAdapteur().getSmallFont());

			}
			travelCriteriaCollapsableArea
					.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
							.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			if (!travelCriteriaCollapsableArea.isCollapsed()) {
				buffer.setFont(father.getSizeAdapteur().getSmallFont());
				s = father.lg("FirstTravelCriteria");
				buffer.drawString(s, decalage + decalageDemi, travelCriteriaRadioBoxs[0].getArea().y
						+ getHeightString(s, buffer));
				s = father.lg("SecondTravelCriteria");
				buffer.drawString(s, decalage + decalageDemi, travelCriteriaRadioBoxs[3].getArea().y
						+ getHeightString(s, buffer));
				for (PTRadioBox t : travelCriteriaRadioBoxs)
					t.draw(buffer, father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			}
			ordonne = travelCriteriaCollapsableArea.getArea().y + travelCriteriaCollapsableArea.getArea().height
					+ decalage;
		}

		/***************************************************************************************************************
		 * Travel mode
		 */
		if (this.mode != NewTravelPanelState.LOST_TRAVEL) {
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
			travelModeCollapsableArea
					.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
							.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			if (!travelModeCollapsableArea.isCollapsed())
				for (PairPTCheckBox p : travelModeCheckBoxs)
					p.chk.draw(buffer, father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			ordonne = travelModeCollapsableArea.getArea().y + travelModeCollapsableArea.getArea().height + decalage;
		}

		/***************************************************************************************************************
		 * Services
		 */
		if (this.mode != NewTravelPanelState.LOST_TRAVEL) {
			s = father.lg("Services");
			if (!servicesCollapsableArea.isCollapsed() && !servicesRadioBoxs.isEmpty()) {
				pos = new int[3];
				width = 0;
				cpt = 0;
				// on trouve la largueur de la colonne des services
				ord = new byte[servicesRadioBoxs.size()];
				for (PairPTRadioBoxs p : servicesRadioBoxs) {
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
				for (PairPTRadioBoxs p : servicesRadioBoxs) {
					tmp += width;
					if (ord[++cpt]-- >= 1)
						tmp += width * ord[cpt] >> 1;
					for (int i = 0; i < pos.length; i++) {
						p.rbs[i].prepareArea(buffer, pos[i], tmp, "", father.getSizeAdapteur().getSmallFont(), true,
								false);
					}
					if (ord[cpt] >= 1)
						tmp += width * ord[cpt] >> 1;
				}
				servicesCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
						.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin()
						.getColorLetter());
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
				for (PairPTRadioBoxs p : servicesRadioBoxs) {
					tmp += width;
					p.rbs[0].draw(buffer, father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
					p.rbs[1].draw(buffer, father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
					p.rbs[2].draw(buffer, father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
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
					buffer.fillOval(decalage + (decalage >> 2) - 1, p.rbs[0].getArea().y + (decalage >> 3), father
							.getSizeAdapteur().getSizeIntermediateFont() >> 1, father.getSizeAdapteur()
							.getSizeIntermediateFont() >> 1);
					buffer.setColor(father.getSkin().getColorLetter());
					buffer.drawOval(decalage + (decalage >> 2) - 1, p.rbs[0].getArea().y + (decalage >> 3), father
							.getSizeAdapteur().getSizeIntermediateFont() >> 1, father.getSizeAdapteur()
							.getSizeIntermediateFont() >> 1);
				}
			} else
				servicesCollapsableArea.update(buffer, decalage, ordonne, s, father.getSizeAdapteur()
						.getIntermediateFont(), father.getSkin().getColorSubAreaInside(), father.getSkin()
						.getColorLetter());
			ordonne = servicesCollapsableArea.getArea().y + servicesCollapsableArea.getArea().height + decalage;
		}

		/***************************************************************************************************************
		 * Station intermediaire
		 */
		if (true || this.mode != NewTravelPanelState.LOST_TRAVEL) {
			intermediatesStationsButton.setEnable(true);
			intermediatesStationsFind.setEnable(true);
			s = father.lg("IntermediatesStations");
			taille = prepareAutoCompletionStationTextBox(intermediatesStationsArea, intermediatesStationsTextBox,
					intermediatesStationsCollapsableArea, null, s, ordonne, decalage, decalage2);
			if (!intermediatesStationsCollapsableArea.isCollapsed()) {
				intermediatesStationsButton.prepareArea(buffer, intermediatesStationsTextBox.getArea().x
						+ intermediatesStationsTextBox.getArea().width + (decalage >> 1), intermediatesStationsTextBox
						.getArea().y
						+ (intermediatesStationsTextBox.getArea().height - imageOk.getIconHeight() >> 1), imageOk);
				rec.setBounds(intermediatesStationsTextBox.getArea().x, intermediatesStationsArea.getArea().height
						+ intermediatesStationsArea.getArea().y, intermediatesStationsTextBox.getArea().width, 0);
				intermediatesStationsArea.getArea().height += pathBuilder.getCurrentPathInGraph().getStepsCount()
						* (decalageDemi + father.getSizeAdapteur().getSizeIntermediateFont() + taille + decalage);
			}
			// TODO z_paint
			intermediatesStationsCollapsableArea
					.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
							.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			station = drawAutoCompletionStationTextBox(intermediatesStationsArea, intermediatesStationsTextBox,
					intermediatesStationsCollapsableArea, null, ordonne, decalage, decalage2, taille, false);
			if (!intermediatesStationsCollapsableArea.isCollapsed()) {
				if (station == null || pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)
						|| pathBuilder.getCurrentPathInGraph().containsSteps(station)
						|| pathBuilder.getCurrentPathInGraph().getDestination() == station
						|| pathBuilder.getCurrentPathInGraph().getOrigin() == station) {
					intermediatesStationsFind.update(buffer, intermediatesStationsButton.getArea().x,
							intermediatesStationsButton.getArea().y
									+ (imageOk.getIconHeight() - imageFind.getIconHeight() >> 1), imageFind);
					// intermediatesStationsButton.prepareArea(buffer, getWidth(), getHeight(), imageOk);
					intermediatesStationsButton.setEnable(false);
				} else {
					intermediatesStationsButton.draw(buffer, imageOk);
					// intermediatesStationsFind.prepareArea(buffer, getWidth(), getHeight(), imageOk);
					intermediatesStationsFind.setEnable(false);
				}
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
		}

		/***************************************************************************************************************
		 * Station à éviter
		 */
		if (this.mode != NewTravelPanelState.LOST_TRAVEL) {
			avoidsStationsButton.setEnable(true);
			avoidsStationsFind.setEnable(true);
			s = father.lg("AvoidsStations");
			taille = prepareAutoCompletionStationTextBox(avoidsStationsArea, avoidsStationsTextBox,
					avoidsStationsCollapsableArea, null, s, ordonne, decalage, decalage2);
			if (!avoidsStationsCollapsableArea.isCollapsed()) {
				avoidsStationsButton.prepareArea(buffer, avoidsStationsTextBox.getArea().x
						+ avoidsStationsTextBox.getArea().width + (decalage >> 1), avoidsStationsTextBox.getArea().y
						+ (avoidsStationsTextBox.getArea().height - imageOk.getIconHeight() >> 1), imageOk);
				// "↲", father.getSizeAdapteur().getLargeFont());
				rec.setBounds(avoidsStationsTextBox.getArea().x, avoidsStationsArea.getArea().height
						+ avoidsStationsArea.getArea().y, avoidsStationsTextBox.getArea().width, 0);
				avoidsStationsArea.getArea().height += pathBuilder.getCurrentPathInGraph().getAvoidStationsCount()
						* (decalageDemi + father.getSizeAdapteur().getSizeIntermediateFont() + taille + decalage);
			}
			avoidsStationsCollapsableArea
					.update(buffer, decalage, ordonne, s, father.getSizeAdapteur().getIntermediateFont(), father
							.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
			station = drawAutoCompletionStationTextBox(avoidsStationsArea, avoidsStationsTextBox,
					avoidsStationsCollapsableArea, null, ordonne, decalage, decalage2, taille, false);
			if (!avoidsStationsCollapsableArea.isCollapsed()) {
				if (station == null || pathBuilder.getCurrentPathInGraph().containsAvoidStation(station)
						|| pathBuilder.getCurrentPathInGraph().containsSteps(station)
						|| pathBuilder.getCurrentPathInGraph().getDestination() == station
						|| pathBuilder.getCurrentPathInGraph().getOrigin() == station) {
					avoidsStationsFind.update(buffer, avoidsStationsButton.getArea().x,
							avoidsStationsButton.getArea().y
									+ (imageOk.getIconHeight() - imageFind.getIconHeight() >> 1), imageFind);
					avoidsStationsButton.setEnable(false);
				} else {
					avoidsStationsButton.draw(buffer, imageOk);
					// father.getSkin().getColorLetter());
					avoidsStationsFind.setEnable(false);
				}
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
			ordonne = avoidsStationsCollapsableArea.getArea().y + avoidsStationsCollapsableArea.getArea().height
					+ decalage;
		}

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint |= (deroullement != scrollBar.getDeroullement());
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
		switch (mode) {
		case EDIT_TRAVEL:
			upperBar.setMainTitle(father.lg("EditTravel"));
			break;
		case LOST_TRAVEL:
			upperBar.setMainTitle(father.lg("LostTravel"));
			break;
		case NEW_TRAVEL:
			upperBar.setMainTitle(father.lg("NewTravel"));
			break;
		default:
			break;
		}
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		if (pathBuilder != null)
			lowerBar.setRightCmd((this.mode != NewTravelPanelState.LOST_TRAVEL) ? father.lg("FindAPath") : father
					.lg("CatchUpYourPath"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (pathBuilder.isValideForSolving()) {
						father.setCurrentState(IhmReceivingStates.COMPUT_TRAVEL, pathBuilder);
					} else {
						redNeeded = true;
						repaint();
					}
				}
			});
		lowerBar.repaint();
		this.requestFocus();
	}

	/**
	 * Définit le trajet courant qui sera utiliser pour définir les paramètres de l'utilisateur. On considère qu'il est
	 * vierge. Si l'objet est null, on affichera qu'on peut rien faire, et on ne proposera que de retourné au menu
	 * principale
	 * 
	 * @param pathBuilder
	 */
	public void setPathInGraphConstraintBuilder(PathInGraphConstraintBuilder pathBuilder,
			NewTravelPanelState newTravelPanelState) {
		this.pathBuilder = pathBuilder;
		mode = NewTravelPanelState.BUILDING;
		// if (newTravelPanelState == NewTravelPanelState.EDIT_TRAVEL
		// || newTravelPanelState == NewTravelPanelState.LOST_TRAVEL)
		// readPathInGraphConstraintBuilder();
		// else if (newTravelPanelState == NewTravelPanelState.NEW_TRAVEL)
		// initPathInGraphConstraintBuilder();
		switch (newTravelPanelState) {
		case EDIT_TRAVEL:
			readPathInGraphConstraintBuilder();
			break;
		case LOST_TRAVEL:
			readPathInGraphConstraintBuilder();
			departureStationTextBox.setText("");
			break;
		case NEW_TRAVEL:
			initPathInGraphConstraintBuilder();
			break;
		default:
			break;
		}
		mode = newTravelPanelState;
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
	protected void readPathInGraphConstraintBuilder() {
		PathInGraph p = pathBuilder.getCurrentPathInGraph();

		this.departureStationTextBox.setText(p.getOrigin().getName());
		this.arrivalStationTextBox.setText(p.getDestination().getName());
		/***************************************************************************************************************
		 * Travel Criteria
		 */
		travelCriteriaRadioBoxs[0].setClicked(p.getMainCriterious() == Algo.CriteriousForLowerPath.COST);
		travelCriteriaRadioBoxs[1].setClicked(p.getMainCriterious() == Algo.CriteriousForLowerPath.TIME);
		travelCriteriaRadioBoxs[2].setClicked(p.getMainCriterious() == Algo.CriteriousForLowerPath.CHANGE);
		travelCriteriaRadioBoxs[3].setClicked(p.getMinorCriterious() == Algo.CriteriousForLowerPath.COST);
		travelCriteriaRadioBoxs[4].setClicked(p.getMinorCriterious() == Algo.CriteriousForLowerPath.TIME);
		travelCriteriaRadioBoxs[5].setClicked(p.getMinorCriterious() == Algo.CriteriousForLowerPath.CHANGE);

		/***************************************************************************************************************
		 * Travel Mode
		 */
		Iterator<KindRoute> itRefusedKindRoute = p.getRefusedKindRouteIter();
		KindRoute kindRoute;
		for (PairPTCheckBox pChk : travelModeCheckBoxs)
			pChk.chk.setClicked(true);
		while (itRefusedKindRoute.hasNext()) {
			kindRoute = itRefusedKindRoute.next();
			for (PairPTCheckBox pChk : travelModeCheckBoxs) {
				if (pChk.name.compareTo(kindRoute.getKindOf()) == 0) {
					pChk.chk.setClicked(false);
					break;
				}
			}
		}

		/***************************************************************************************************************
		 * Services
		 */
		Iterator<Service> itService;
		Service service;
		for (PairPTRadioBoxs pRbx : servicesRadioBoxs)
			pRbx.rbs[0].setClicked(true);
		/***************************************************************************************************************
		 * Always
		 */
		itService = p.getServicesAlwaysIter();
		while (itService.hasNext()) {
			service = itService.next();
			for (PairPTRadioBoxs pRbx : servicesRadioBoxs) {
				if (pRbx.name.compareTo(SettingsKey.SERVICES_.toString() + service.getId()) == 0) {
					pRbx.rbs[0].setClicked(false);
					pRbx.rbs[1].setClicked(false);
					pRbx.rbs[2].setClicked(true);
					break;
				}
			}
		}
		/***************************************************************************************************************
		 * Once
		 */
		itService = p.getServicesOnceIter();
		while (itService.hasNext()) {
			service = itService.next();
			for (PairPTRadioBoxs pRbx : servicesRadioBoxs) {
				if (pRbx.name.compareTo(SettingsKey.SERVICES_.toString() + service.getId()) == 0) {
					pRbx.rbs[0].setClicked(false);
					pRbx.rbs[1].setClicked(true);
					pRbx.rbs[2].setClicked(false);
					break;
				}
			}
		}

		/***************************************************************************************************************
		 * Station
		 */
		Iterator<Station> itStation;
		/***************************************************************************************************************
		 * Avoid
		 */
		itStation = p.getAvoidStationsIter();
		while (itStation.hasNext())
			recordChangedSetting(avoidsStationsAdd, itStation.next().getName());
		/***************************************************************************************************************
		 * Step
		 */
		itStation = p.getStepsIter();
		Station station;
		while (itStation.hasNext()) {
			station = itStation.next();
			recordChangedSetting(intermediatesStationsAdd, station.getName());
		}
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

		if ((s = father.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()))
				.compareTo(Algo.CriteriousForLowerPath.COST.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.COST);
		else if (s.compareTo(Algo.CriteriousForLowerPath.TIME.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.TIME);
		else if (s.compareTo(Algo.CriteriousForLowerPath.CHANGE.toString()) == 0)
			pathBuilder.setMainCriterious(CriteriousForLowerPath.CHANGE);

		if ((s = father.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()))
				.compareTo(Algo.CriteriousForLowerPath.COST.toString()) == 0)
			pathBuilder.setMinorCriterious(CriteriousForLowerPath.COST);
		else if (s.compareTo(Algo.CriteriousForLowerPath.TIME.toString()) == 0)
			pathBuilder.setMinorCriterious(CriteriousForLowerPath.TIME);
		else if (s.compareTo(Algo.CriteriousForLowerPath.CHANGE.toString()) == 0)
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
