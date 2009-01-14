package ihm.smartPhone;

import graphNetwork.GraphNetwork;
import graphNetwork.KindRoute;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;
import iGoMaster.AlgoKindOfException;
import iGoMaster.AlgoKindOfRelaxation;
import iGoMaster.EventInfo;
import iGoMaster.IHM;
import iGoMaster.IHMGraphicQuality;
import iGoMaster.Master;
import iGoMaster.SettingsKey;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import iGoMaster.exception.NoNetworkException;
import ihm.smartPhone.component.IGoFlowLayout;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.NetworkColorManagerPseudoRandom;
import ihm.smartPhone.component.TravelForDisplayPanelImplPathInGraph;
import ihm.smartPhone.component.TravelForTravelPanelImplPathInGraph;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.component.iGoSmartPhoneSkin;
import ihm.smartPhone.interfaces.NetworkColorManager;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.libPT.PanelTooled;
import ihm.smartPhone.listener.MyWindowStateListener;
import ihm.smartPhone.statePanels.ErrorPanel;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.statePanels.LoadTravelPanel;
import ihm.smartPhone.statePanels.MainPanel;
import ihm.smartPhone.statePanels.NewTravelPanel;
import ihm.smartPhone.statePanels.PanelState;
import ihm.smartPhone.statePanels.SettingsPanel;
import ihm.smartPhone.statePanels.SplashScreenPanel;
import ihm.smartPhone.statePanels.TravelArrayDisplayPanel;
import ihm.smartPhone.statePanels.TravelDisplayPanel;
import ihm.smartPhone.statePanels.TravelGraphicDisplayPanel;
import ihm.smartPhone.statePanels.VoidPanel;
import ihm.smartPhone.statePanels.NewTravelPanel.NewTravelPanelState;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IGoIhmSmartPhone extends Frame implements IHM, IhmReceivingPanelState {

	private static final long serialVersionUID = 1L;

	public static final IHMGraphicQuality defaultQualityForIhm = IHMGraphicQuality.FULL_ANTI_ANTIALIASING;

	/**
	 * Les composants et variables de l'ihm
	 */
	protected IGoFlowLayout sizeAdapteur = null;
	protected iGoSmartPhoneSkin skin;
	protected LinkedList<iGoSmartPhoneSkin> skins;
	protected IhmReceivingStates actualState = IhmReceivingStates.UNKNOWN;
	protected NetworkColorManager networkColorManager;
	protected TravelForDisplayPanel travel;
	protected VoidPanel computingPanel;
	protected final Lock verrou = new ReentrantLock();

	/**
	 * Les 3 zones de l'IHM
	 */
	protected UpperBar upperBar;
	protected Panel centerPanel;
	protected LowerBar lowerBar;
	protected int oldSizeLine = -1;
	/**
	 * etat préféré pour la prévisualisation et l'expérimentation d'un trajet : IhmReceivingStates.GRAPHIC_MODE ou
	 * IhmReceivingStates.ARRAY_MODE
	 */
	protected IhmReceivingStates preferedState;
	protected SplashScreenPanel splashScreenPanel = null;
	protected MainPanel mainPanel = null;
	protected SettingsPanel settingsPanel = null;
	protected NewTravelPanel newTravelPanel = null;
	protected TravelDisplayPanel travelGraphicPanel = null;
	protected TravelDisplayPanel travelArrayPanel = null;
	protected ErrorPanel errorPanel = null;

	// protected boolean quitMessage = false;

	/**
	 * Constructeur de l'interface. Le master est requit car il sert imédiatement.
	 * 
	 * @param master
	 *            le master, celui qui fournira entre autre les traductions
	 */
	public IGoIhmSmartPhone(Master master) {
		this(master, null);
	}

	public IGoIhmSmartPhone(Master master, iGoSmartPhoneSkin skin) {
		super(master.lg("ProgName"));
		sizeAdapteur = new IGoFlowLayout();
		networkColorManager = new NetworkColorManagerPseudoRandom(master); 
		this.setLayout(sizeAdapteur);
		this.master = master;
		this.skin = iGoSmartPhoneSkin.White;
		setIconImage(ImageLoader.getRessourcesImageIcone("logo", 32, 32, true).getImage());
		// récupération des preférence système pour la skin
		if (skin == null) {
			Iterator<iGoSmartPhoneSkin> itS = this.getSkins();
			String s = this.master.getConfig(SettingsKey.SKIN.toString());
			if (s.compareTo("") != 0) {
				while (itS.hasNext())
					if ((skin = itS.next()).toString().compareTo(s) == 0) {
						this.skin = skin;
						break;
					}
			} else {
				this.master.setConfig(SettingsKey.SKIN.toString(), this.skin.toString());
			}
		}
		// on définit le background par le couleur des lignes, en effet les lignes sont en faite des espace laissé sans
		// objet
		this.setBackground(this.skin.getColorLine());
		// on vérifie que la config est cohérente : si le main ou minor criteria ne sont pas définit, on les définit
		// nous même. S'ils sont les mêmes on définit le second différament du premier
		if (this.master.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo("") == 0)
			this.master.setConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString(), Algo.CriteriousForLowerPath.COST
					.toString());
		if (this.master.getConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString()).compareTo("") == 0)
			if (this.master.getConfig(SettingsKey.MAIN_TRAVEL_CRITERIA.toString()).compareTo(
					Algo.CriteriousForLowerPath.TIME.toString()) != 0)
				this.master.setConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString(), Algo.CriteriousForLowerPath.TIME
						.toString());
			else
				this.master.setConfig(SettingsKey.MINOR_TRAVEL_CRITERIA.toString(), Algo.CriteriousForLowerPath.COST
						.toString());

		// lecture de la qualité graphique
		int i = 0;
		try {
			i = Integer.parseInt(this.master.getConfig(SettingsKey.GRAPHICAL_QUALITY.toString()));
		} catch (NumberFormatException e) {
		}

		if (i == IHMGraphicQuality.AS_FAST_AS_WE_CAN.getValue()) {
			PanelDoubleBufferingSoftwear.setStaticQuality(IHMGraphicQuality.AS_FAST_AS_WE_CAN);
		} else if (i == IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
			PanelDoubleBufferingSoftwear.setStaticQuality(IHMGraphicQuality.TEXT_ANTI_ANTIALIASING);
		} else if (i == IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue()) {
			PanelDoubleBufferingSoftwear.setStaticQuality(IHMGraphicQuality.FULL_ANTI_ANTIALIASING);
		} else if (i == IHMGraphicQuality.HIGHER_QUALITY.getValue()) {
			PanelDoubleBufferingSoftwear.setStaticQuality(IHMGraphicQuality.HIGHER_QUALITY);
		} else {
			this.master.setConfig("GRAPHICAL_QUALITY", defaultQualityForIhm.getValue() + "");
			PanelDoubleBufferingSoftwear.setStaticQuality(defaultQualityForIhm);
		}

		this.setSize(sizeAdapteur.getWidth(), sizeAdapteur.getHeight());
		this.setLocation((int) ((SizeAdapteur.screenWidth - sizeAdapteur.getWidth()) * 0.333),
				(int) ((SizeAdapteur.screenHeigth - sizeAdapteur.getHeight()) * 0.333));
		if (sizeAdapteur.isFullScreen()) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}
		String tmp = this.master.getConfig("GRAPHIC_OR_ARRAY_MODE");
		if ((tmp == null) || (tmp.compareTo(IhmReceivingStates.ARRAY_MODE.toString()) == 0))
			this.preferedState = IhmReceivingStates.ARRAY_MODE;
		else
			this.preferedState = IhmReceivingStates.GRAPHIC_MODE;

		/***************************************************************************************************************
		 * Préparation des trois zones de données
		 * 
		 * Barre supérieure
		 */
		upperBar = new UpperBar(this, false);
		upperBar.setBackground(this.skin.getColorInside());
		this.add(upperBar);

		/***************************************************************************************************************
		 * Barre inférieure
		 */
		lowerBar = new LowerBar(this, false);
		lowerBar.setBackground(this.skin.getColorInside());
		this.add(lowerBar);

		/***************************************************************************************************************
		 * Zone principale
		 */
		centerPanel = new Panel(new BorderLayout(0, 0));
		centerPanel.setBackground(this.skin.getColorInside());
		centerPanel.add(new VoidPanel(this));
		this.add(centerPanel);

		/***************************************************************************************************************
		 * Procédure d'arret en cas de pression de la croix rouge.
		 */
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				((IGoIhmSmartPhone) e.getSource()).stop();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		this.addWindowStateListener(new MyWindowStateListener<IGoIhmSmartPhone>(this) {

			@Override
			public void windowStateChanged(WindowEvent e) {
				sizeAdapteur.setFullScreen(e.getNewState() == Frame.MAXIMIZED_BOTH);
			}
		});
	}

	/**
	 * Le constructeur par défaut est déclaré en private afin d'empécher son utilisation par des classes extérieurs, ou
	 * héritantes.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private IGoIhmSmartPhone() {
	}

	/**
	 * @uml.property name="master"
	 * @uml.associationEnd inverse="iGoIhmSmartPhone:iGoMaster.Master"
	 */
	private Master master;

	/**
	 * Getter of the property <tt>master</tt>
	 * 
	 * @return Returns the master.
	 * @uml.property name="master"
	 */
	public Master getMaster() {
		return master;
	}

	/**
	 * Setter of the property <tt>master</tt>
	 * 
	 * @param master
	 *            The master to set.
	 * @uml.property name="master"
	 */
	public void setMaster(Master master) {
		this.master = master;
	}

	/**
	 * @uml.property name="reseau"
	 * @uml.associationEnd inverse="iGoIhmSmartPhone:ihm.network.Network"
	 */
	private GraphNetwork reseau;

	/**
	 * Getter of the property <tt>reseau</tt>
	 * 
	 * @return Returns the reseau.
	 * @uml.property name="reseau"
	 */
	public GraphNetwork getReseau() {
		return reseau;
	}

	/**
	 * Setter of the property <tt>reseau</tt>
	 * 
	 * @param reseau
	 *            The reseau to set.
	 * @uml.property name="reseau"
	 */
	public void setReseau(GraphNetwork reseau) {
		this.reseau = reseau;
	}

	@Override
	public void start(boolean bySplashScreen, int step) {
		if (checkSplashScreenPanel())
			splashScreenPanel.setMaxStepInSplashScreen(step);
		if (bySplashScreen)
			this.setCurrentState(IhmReceivingStates.SPLASH_SCREEN);
		else
			this.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
		this.setVisible(true);
	}

	@Override
	public void start(boolean bySplashScreen) {
		start(bySplashScreen, -1);
	}

	@Override
	public String lg(String key) {
		return master.lg(key);
	}

	@Override
	public void showMessageSplashScreen(String message) {
		if (actualState == IhmReceivingStates.SPLASH_SCREEN) {
			if (checkSplashScreenPanel())
				splashScreenPanel.incrementStepInSplashScreen();
			lowerBar.setMainTitle(message, FontSizeKind.INTERMEDIATE);
			lowerBar.repaint();
		}
	}

	@Override
	public void endSplashScreen() {
		if (actualState == IhmReceivingStates.SPLASH_SCREEN) {
			setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
		}
	}

	@Override
	public int getMaxStepInSplashScreen() {
		if (checkSplashScreenPanel())
			return splashScreenPanel.getMaxStepInSplashScreen();
		return -1;
	}

	@Override
	public int getStepInSplashScreen() {
		if (checkSplashScreenPanel())
			return splashScreenPanel.getStepInSplashScreen();
		return -1;
	}

	@Override
	public void setMaxStepInSplashScreen(int step) {
		if (checkSplashScreenPanel())
			splashScreenPanel.setMaxStepInSplashScreen(step);
	}

	@Override
	public void setStepInSplashScreen(int step) {
		if (checkSplashScreenPanel())
			splashScreenPanel.setStepInSplashScreen(step);
	}

	/**
	 * Vérifie le SplashScreenPanel, et si ce dernier est null, on le crée.
	 * 
	 * @return true si on a bien pu créé le SplashScreenPanel
	 */
	protected boolean checkSplashScreenPanel() {
		if (splashScreenPanel != null)
			return true;
		if ((actualState == IhmReceivingStates.SPLASH_SCREEN) || (actualState == IhmReceivingStates.UNKNOWN)) {
			splashScreenPanel = new SplashScreenPanel(this, upperBar, lowerBar);
			return true;
		}
		return false;
	}

	/**
	 * Vérifie si le panel de l'écran principal est bien créé, et si ce dernier est null, on le crée.
	 */
	protected void checkMainPanel() {
		if (mainPanel == null)
			mainPanel = new MainPanel(this, upperBar, lowerBar);
	}

	/**
	 * Vérifie si le panel de l'écran de chargement des trajets récents est bien créé, et si ce dernier est null, on le
	 * crée.
	 */
	protected LoadTravelPanel checkLoadTravelPanel() {
		LinkedList<TravelForTravelPanel> lst = new LinkedList<TravelForTravelPanel>();
		Iterator<PathInGraphCollectionBuilder> itP = master.getRecentsPaths();
		PathInGraphCollectionBuilder pigCol;
		while (itP.hasNext()) {
			lst.add(new TravelForTravelPanelImplPathInGraph((pigCol = itP.next()).getPathInGraphConstraintBuilder(),
					networkColorManager, master.isFavoritesPaths(pigCol.getPathInGraph())) {

				@Override
				public void delete() {
					master.delete(path.getCurrentPathInGraph());
				}

				@Override
				public void edit() {
					setCurrentState(IhmReceivingStates.EDIT_TRAVEL, path.getCurrentPathInGraph());
				}

				@Override
				public void start() {
					setCurrentState(IhmReceivingStates.COMPUT_TRAVEL, path);
				}

				@Override
				public void setFavorite(boolean isFav) {
					if (isFav) {
						master.markAsFavorite(path.getCurrentPathInGraph());
					} else {
						master.removeFromFavorites(path.getCurrentPathInGraph());
					}
					this.isFav = isFav;
				}
			});
		}
		return new LoadTravelPanel(this, upperBar, lowerBar, IhmReceivingStates.LOAD_TRAVEL, lst);
	}

	/**
	 * Vérifie si le panel de l'écran de chargement des trajets favoris est bien créé, et si ce dernier est null, on le
	 * crée.
	 */
	protected LoadTravelPanel checkFavoritesPanel() {
		LinkedList<TravelForTravelPanel> lst = new LinkedList<TravelForTravelPanel>();
		Iterator<PathInGraphCollectionBuilder> itP = master.getFavoritesPaths();
		while (itP.hasNext()) {
			lst.add(new TravelForTravelPanelImplPathInGraph(itP.next().getPathInGraphConstraintBuilder(),
					networkColorManager, true) {

				@Override
				public void delete() {
					master.delete(path.getCurrentPathInGraph());
				}

				@Override
				public void edit() {
					setCurrentState(IhmReceivingStates.EDIT_TRAVEL, path.getCurrentPathInGraph());
				}

				@Override
				public void start() {
					setCurrentState(IhmReceivingStates.COMPUT_TRAVEL, path);
				}

				@Override
				public void setFavorite(boolean isFav) {
					if (isFav) {
						master.markAsFavorite(path.getCurrentPathInGraph());
					} else {
						master.removeFromFavorites(path.getCurrentPathInGraph());
					}
					this.isFav = isFav;
				}
			});
		}
		return new LoadTravelPanel(this, upperBar, lowerBar, IhmReceivingStates.FAVORITES, lst);

	}

	/**
	 * Vérifie si le panel de l'écran des paramètres est bien créé, et si ce dernier est null, on le crée.
	 */
	protected void checkSettingsPanel() {
		if (settingsPanel == null)
			settingsPanel = new SettingsPanel(this, upperBar, lowerBar);
	}

	/**
	 * Vérifie si le panel de visualisation graphique d'un trajet est bien créé, et si ce dernier est null, on le crée.
	 */
	protected void checkTravelGraphicDisplayPanel() {
		if (travelGraphicPanel == null)
			travelGraphicPanel = new TravelGraphicDisplayPanel(this, upperBar, lowerBar, travel);
	}

	/**
	 * Vérifie si le panel de visualisation sous forme de tableau d'un trajet est bien créé, et si ce dernier est null,
	 * on le crée.
	 */
	protected void checkTravelArrayDisplayPanel() {
		if (travelArrayPanel == null)
			travelArrayPanel = new TravelArrayDisplayPanel(this, upperBar, lowerBar, travel);
	}

	/**
	 * Vérifie si le panel de préparation d'un nouveau trajet est bien créé, et si ce dernier est null, on le crée.
	 */
	protected void checkNewTravelPanel() {
		if (newTravelPanel == null)
			newTravelPanel = new NewTravelPanel(this, upperBar, lowerBar);
	}

	/**
	 * Vérifie si le panel d'affichage d'erreur est bien créé, et si ce dernier est null, on le crée.
	 */
	protected void checkErrorPanel() {
		if (errorPanel == null)
			errorPanel = new ErrorPanel(this, upperBar, lowerBar);
	}

	@Override
	public void stop() {
		this.endInterface();
	}

	/**
	 * Fonction de fermeture de l'ihm,
	 * 
	 * @return
	 */
	protected boolean endInterface() {
		this.setVisible(false);
		this.dispose();
		this.master.stop();
		return true;
	}

	@Override
	public iGoSmartPhoneSkin getSkin() {
		return skin;
	}

	@Override
	public SizeAdapteur getSizeAdapteur() {
		return sizeAdapteur;
	}

	@Override
	public IhmReceivingStates getCurrentState() {
		return actualState;
	}

	@Override
	public boolean setCurrentState(IhmReceivingStates ihmReceivingStates) {
		return setCurrentState(ihmReceivingStates, null, null);
	}

	@Override
	public boolean setCurrentState(IhmReceivingStates ihmReceivingStates, PathInGraph path) {
		return setCurrentState(ihmReceivingStates, null, path);
	}

	@Override
	public boolean setCurrentState(IhmReceivingStates ihmReceivingStates, PathInGraphConstraintBuilder pathBuilder) {
		return setCurrentState(ihmReceivingStates, pathBuilder, null);
	}

	@Override
	public boolean setCurrentState(IhmReceivingStates ihmReceivingStates, PathInGraphConstraintBuilder pathBuilder,
			PathInGraph path) {
		// on verrouille l'utilisation de la fonction, car celle ci le fait que certain variable sont ou ne sont pas à
		// null, en cas d'appelle multithread, on pourrais ce retrouver dans des état non-désirés.
		verrou.lock();
		boolean b = false;
		try {
			b = setCurrentStateUnSynchronized(ihmReceivingStates, pathBuilder, path);
		} finally {
			verrou.unlock();
		}
		return b;
	};

	/**
	 * Définit l'état courant de l'ihm de façon non multi-thread safe.
	 * 
	 * @param actualState
	 *            le nouvel état
	 * @param pathBuilder
	 *            un builder relatif à ce nouvelle état
	 * @param path
	 *            un path relatif à ce nouvelle état
	 * @return true si cela à bien marché
	 */
	public boolean setCurrentStateUnSynchronized(IhmReceivingStates actualState,
			PathInGraphConstraintBuilder pathBuilder, PathInGraph path) {
		computingPanel = null;
		if (actualState == this.actualState)
			return true;
		if (actualState == IhmReceivingStates.UNKNOWN)
			actualState = this.actualState;
		if (actualState != IhmReceivingStates.SPLASH_SCREEN) {
			splashScreenPanel = null;
		}
		/***************************************************************************************************
		 */
		if (actualState == IhmReceivingStates.SPLASH_SCREEN) {
			this.actualState = IhmReceivingStates.SPLASH_SCREEN;
			centerPanel.removeAll();
			try {
				checkSplashScreenPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkSplashScreenPanel();
			}
			centerPanel.add(splashScreenPanel);
			splashScreenPanel.giveControle();
			centerPanel.validate();
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.MAIN_INTERFACE) {
			// System.out.println(this.actualState);
			if (this.actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_ARRAY_MODE
					|| this.actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE
					|| this.actualState == IhmReceivingStates.PREVISU_TRAVEL_ARRAY_MODE
					|| this.actualState == IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE
					|| this.actualState == IhmReceivingStates.NEW_TRAVEL
					|| this.actualState == IhmReceivingStates.EDIT_TRAVEL
					|| this.actualState == IhmReceivingStates.LOST_IN_TRAVEL)
				cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.MAIN_INTERFACE;
			centerPanel.removeAll();
			try {
				checkMainPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkMainPanel();
			}
			centerPanel.add(mainPanel);
			mainPanel.giveControle();
			centerPanel.validate();
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.NEW_TRAVEL || actualState == IhmReceivingStates.EDIT_TRAVEL
				|| actualState == IhmReceivingStates.LOST_IN_TRAVEL) {
			// if (actualState == IhmReceivingStates.LOST_IN_TRAVEL && path == null)
			// return false;
			this.actualState = actualState;
			centerPanel.removeAll();
			newTravelPanel = null;
			try {
				checkNewTravelPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkNewTravelPanel();
			}
			try {
				switch (actualState) {
				case NEW_TRAVEL:
					pathBuilder = master.getPathInGraphConstraintBuilder();
					newTravelPanel.setPathInGraphConstraintBuilder(pathBuilder, NewTravelPanelState.NEW_TRAVEL);
					break;
				case LOST_IN_TRAVEL:
					if (pathBuilder == null) {
						pathBuilder = master.getPathInGraphConstraintBuilder();
						pathBuilder.importPath(path);
					}
					newTravelPanel.setPathInGraphConstraintBuilder(pathBuilder, NewTravelPanelState.LOST_TRAVEL);
					break;
				case EDIT_TRAVEL:
					// Si on demande l'édition d'un trajet en lecture seul, on le recopie dans un nouveau builder, et on
					// l'édit
					if (pathBuilder == null) {
						pathBuilder = master.getPathInGraphConstraintBuilder();
						pathBuilder.importPath(path);
					}
					newTravelPanel.setPathInGraphConstraintBuilder(pathBuilder, NewTravelPanelState.EDIT_TRAVEL);
					break;
				}
				centerPanel.add(newTravelPanel);
				newTravelPanel.giveControle();
				centerPanel.validate();
				return true;
			} catch (NoNetworkException e) {
				this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_ReturnNoNetworkException"));
				return false;
			} catch (GraphReceptionException e) {
				this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_NetworkReception"));
				return false;
			} catch (GraphConstructionException e) {
				this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_NetworkConstruction"));
				return false;
			}
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.LOAD_TRAVEL) {
			this.actualState = IhmReceivingStates.LOAD_TRAVEL;
			centerPanel.removeAll();
			LoadTravelPanel loadTravelPanel = null;
			try {
				loadTravelPanel = checkLoadTravelPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				loadTravelPanel = checkLoadTravelPanel();
			}
			centerPanel.add(loadTravelPanel);
			loadTravelPanel.giveControle();
			centerPanel.validate();
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.FAVORITES) {
			this.actualState = IhmReceivingStates.FAVORITES;
			centerPanel.removeAll();
			LoadTravelPanel favoritesPanel = null;
			try {
				favoritesPanel = checkFavoritesPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				favoritesPanel = checkFavoritesPanel();
			}
			centerPanel.add(favoritesPanel);
			favoritesPanel.giveControle();
			centerPanel.validate();
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.SETTINGS) {
			this.actualState = IhmReceivingStates.SETTINGS;
			centerPanel.removeAll();
			try {
				checkSettingsPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkSettingsPanel();
			}
			centerPanel.add(settingsPanel);
			settingsPanel.giveControle();
			centerPanel.validate();
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.COMPUT_TRAVEL) {
			cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.COMPUT_TRAVEL;
			addToCenterPanel(computingPanel = new VoidPanel(this, upperBar, lowerBar, master.lg("ComputingANewPath")));
			if (pathBuilder != null && master.askForATravel(pathBuilder))
				return true;
			if (path != null)
				try {
					pathBuilder = master.getPathInGraphConstraintBuilder();
					pathBuilder.importPath(path);
					if (master.askForATravel(pathBuilder))
						return true;
				} catch (NoNetworkException e) {
					this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_ReturnNoNetworkException"));
					return false;
				} catch (GraphReceptionException e) {
					this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_NetworkReception"));
					return false;
				} catch (GraphConstructionException e) {
					this.setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_NetworkConstruction"));
					return false;
				}
			setErrorState(this.lg("ERROR_Impossible"), this.lg("ERROR_UnknownException"));
			return false;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			actualState = IhmReceivingStates.PREVISU_TRAVEL.mergeState(preferedState);
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) {
			actualState = IhmReceivingStates.EXPERIMENT_TRAVEL.mergeState(preferedState);
		}
		/***************************************************************************************************
		 */
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE) {
			this.actualState = IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE;
			try {
				checkTravelGraphicDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelGraphicDisplayPanel();
			}
			travelGraphicPanel.setCurrentState(IhmReceivingStates.PREVISU_TRAVEL);
			addToCenterPanel(travelGraphicPanel);
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE) {
			this.actualState = IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE;
			try {
				checkTravelGraphicDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelGraphicDisplayPanel();
			}
			travelGraphicPanel.setCurrentState(IhmReceivingStates.EXPERIMENT_TRAVEL);
			addToCenterPanel(travelGraphicPanel);
			return true;
			/***************************************************************************************************
			 */
		} else /**/if (actualState == IhmReceivingStates.PREVISU_TRAVEL_ARRAY_MODE) {
			this.actualState = IhmReceivingStates.PREVISU_TRAVEL_ARRAY_MODE;
			try {
				checkTravelArrayDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelArrayDisplayPanel();
			}
			travelArrayPanel.setCurrentState(IhmReceivingStates.PREVISU_TRAVEL);
			addToCenterPanel(travelArrayPanel);
			return true;
			/***************************************************************************************************
			 */
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_ARRAY_MODE) {
			this.actualState = IhmReceivingStates.EXPERIMENT_TRAVEL_ARRAY_MODE;
			centerPanel.removeAll();
			try {
				checkTravelArrayDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelArrayDisplayPanel();
			}
			travelArrayPanel.setCurrentState(IhmReceivingStates.EXPERIMENT_TRAVEL);
			addToCenterPanel(travelArrayPanel);
			return true;
		}
		return false;
	}

	/**
	 * Procédure d'ajout d'un panel (=un état) au panel centrale qui est le receptacle des panel-états
	 * 
	 * @param statePanel
	 */
	protected void addToCenterPanel(PanelState statePanel) {
		centerPanel.removeAll();
		centerPanel.add(statePanel);
		statePanel.giveControle();
		centerPanel.validate();
	}

	/**
	 * effectue un libération des ressources : on casse les lien entre les objets afin de permetre au GC de les effacer
	 * si besoin est.
	 * 
	 * @param dueToAnError
	 *            true si c'est suite à un erreur de mémoire. dans ce cas on réduit la qualité général de l'application
	 *            a fin d'éviter que cela ce reproduise.
	 */
	protected void cleanPanelsStates(boolean dueToAnError) {
		if (dueToAnError)
			PanelTooled.setStaticQuality(IHMGraphicQuality.AS_FAST_AS_WE_CAN);
		splashScreenPanel = null;
		settingsPanel = null;
		newTravelPanel = null;
		mainPanel = null;
		travelGraphicPanel = null;
		travelArrayPanel = null;
		travel = null;
	}

	/**
	 * Définit l'état préféré pour l'affichage d'un trajet. On a le choix entre IhmReceivingStates.GRAPHIC_MODE et
	 * IhmReceivingStates.ARRAY_MODE.
	 * 
	 * @param actualState
	 *            le nouvelle état préféré. Si on fournit un état qui n'est pas géré, l'appelle est sans effet
	 */
	public void setPreferedStates(IhmReceivingStates actualState) {
		if (actualState == IhmReceivingStates.GRAPHIC_MODE)
			this.actualState = IhmReceivingStates.GRAPHIC_MODE;
		else if (actualState == IhmReceivingStates.ARRAY_MODE)
			this.actualState = IhmReceivingStates.ARRAY_MODE;
	}

	@Override
	public boolean returnPathAsked(PathInGraphConstraintBuilder path, AlgoKindOfException algoKindOfException,
			Service service, Route route, Station station, KindRoute kindRoute) {
		verrou.lock();
		try {
			if (actualState != IhmReceivingStates.COMPUT_TRAVEL)
				return false;
			String message;
			switch (algoKindOfException) {
			case EverythingFine:
				// ba c'est bon quoi.
				if (path == null) {
					setErrorState(this.lg("ERROR_Problem"), this.lg("ERROR_ReturnNullTravelDetails"));
					return false;
				}
				try {
					// on tente la construction
					PathInGraphConstraintBuilder pathClone = master.getPathInGraphConstraintBuilder();
					pathClone.importPath(path.getCurrentPathInGraph());
					travel = new TravelForDisplayPanelImplPathInGraph(path, pathClone, master.isFavoritesPaths(path
							.getCurrentPathInGraph())) {
						@Override
						public void setFavorite(boolean isFav) {
							if (isFav) {
								master.markAsFavorite(path.getCurrentPathInGraph());
							} else {
								master.removeFromFavorites(path.getCurrentPathInGraph());
							}
							this.isFav = isFav;
						};
					};
					// ca à marché on dirait
				} catch (NoNetworkException e) {
					setErrorState(this.lg("ERROR_Problem"), this.lg("ERROR_ReturnNoNetworkException"));
					e.printStackTrace();
				} catch (GraphReceptionException e) {
					setErrorState(this.lg("ERROR_Problem"), this.lg("ERROR_GraphReceptionException"));
					e.printStackTrace();
				} catch (GraphConstructionException e) {
					setErrorState(this.lg("ERROR_Problem"), this.lg("ERROR_GraphConstructionException"));
					e.printStackTrace();
				} catch (Exception e) { 
					setErrorState(this.lg("ERROR_Problem"), this.lg("ERROR_BuildingTravelFromResult"));
					e.printStackTrace();
					return false;
				}
				// On passe en visu.
				this.setCurrentState(IhmReceivingStates.PREVISU_TRAVEL);
				return true;
			case NonValidDestination:// ba c'est pas bon =:-(
				message = this.lg("ERROR_ERROR_NonValidDestinationException");
				break;
			case NoSolution:
				message = this.lg("ERROR_NoSolution");
				break;
			case RoutesNotAccessible:
				message = this.lg("ERROR_RoutesNotAccessible");
				break;
			case ServiceNotAccessible:
				message = this.lg("ERROR_ServiceNotAccessibleException");
				break;
			case StationNotAccessible:
				message = this.lg("ERROR_StationNotAccessibleException");
				break;
			case StationNotOnGraphNetworkRoad:
				message = this.lg("ERROR_StationNotOnGraphNetworkRoad");
				break;
			case UndefinedError:
				message = this.lg("ERROR_UnknownException");
				break;
			case NonValidOrigin:
				message = this.lg("ERROR_NonValidOrigin");
				break;
			default:
				message = this.lg("ERROR_Unknown") + " : " + algoKindOfException;
				break;
			}
			if (service != null)
				message += "\n" + master.lg("Service") + " : " + service;
			if (route != null)
				message += "\n" + master.lg("Route") + " : " + route;
			if (station != null)
				message += "\n" + master.lg("Station") + " : " + station;
			if (kindRoute != null)
				message += "\n" + master.lg("KindRoute") + " : " + kindRoute;
			setErrorState(this.lg("ERROR_Impossible"), message);
			return false;
		} finally {
			// Quoiqu'il arrive on execute ce code en fin de fonction
			verrou.unlock();
		}
	}

	@Override
	public void cancel() {
		if (actualState != IhmReceivingStates.COMPUT_TRAVEL)
			return;
		this.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
	}

	@Override
	public String getConfig(String key) {
		return master.getConfig(key);
	}

	@Override
	public Iterator<KindRoute> getKindRoutes() {
		return master.getKindRoutes();
	}

	@Override
	public Iterator<Service> getServices() {
		return master.getServices();
	}

	@Override
	public boolean setConfig(String key, String value) {
		if (!master.setConfig(key, value))
			return false;
		if (key.compareTo(SettingsKey.LANGUAGE.toString()) == 0) {
			sortSkin();
			this.setTitle(master.lg("ProgName"));
		}
		if (key.compareTo("GRAPHIC_OR_ARRAY_MODE") == 0)
			if (value.compareTo(IhmReceivingStates.ARRAY_MODE.toString()) == 0)
				this.preferedState = IhmReceivingStates.ARRAY_MODE;
			else
				this.preferedState = IhmReceivingStates.GRAPHIC_MODE;
		return true;
		// if (key.compareTo(SettingsKey.LANGUAGE.toString()) == 0)
		// sortSkin();
		//
		// if (key.compareTo("GRAPHIC_OR_ARRAY_MODE") == 0)
		// if (value.compareTo(IhmReceivingStates.ARRAY_MODE.toString()) == 0)
		// this.preferedState = IhmReceivingStates.ARRAY_MODE;
		// else
		// this.preferedState = IhmReceivingStates.GRAPHIC_MODE;
		// return master.setConfig(key, value);
	}

	@Override
	public Iterator<Station> getStations() {
		return master.getStations();
	}

	@Override
	public Station getStation(int idStation) {
		Iterator<Station> itS = master.getStations();
		while (itS.hasNext()) {
			Station station = (Station) itS.next();
			if (station.getId() == idStation)
				return station;
		}
		return null;
	}

	@Override
	public NetworkColorManager getNetworkColorManager() {
		return networkColorManager;
	}

	@Override
	public Iterator<String> getLanguages() {
		return master.getLanguages();
	}

	@Override
	public void setSkin(iGoSmartPhoneSkin skin) {
		this.skin = skin;
		this.setBackground(skin.getColorLine());

		if (splashScreenPanel != null)
			splashScreenPanel.setBackground(skin.getColorInside());
		if (mainPanel != null)
			mainPanel.setBackground(skin.getColorInside());
		if (settingsPanel != null)
			settingsPanel.setBackground(skin.getColorInside());
		if (newTravelPanel != null)
			newTravelPanel.setBackground(skin.getColorInside());
		if (travelGraphicPanel != null)
			travelGraphicPanel.setBackground(skin.getColorInside());
		if (travelArrayPanel != null)
			travelArrayPanel.setBackground(skin.getColorInside());
		upperBar.setBackground(skin.getColorInside());
		lowerBar.setBackground(skin.getColorInside());
	}

	@Override
	public Iterator<iGoSmartPhoneSkin> getSkins() {
		if (skins == null) {
			skins = new LinkedList<iGoSmartPhoneSkin>();
			skins.add(iGoSmartPhoneSkin.Black);
			skins.add(iGoSmartPhoneSkin.Blue);
			skins.add(iGoSmartPhoneSkin.Cherry);
			skins.add(iGoSmartPhoneSkin.Green);
			skins.add(iGoSmartPhoneSkin.Orange);
			skins.add(iGoSmartPhoneSkin.Christmas);
			skins.add(iGoSmartPhoneSkin.Pink);
			skins.add(iGoSmartPhoneSkin.PinkPurple);
			skins.add(iGoSmartPhoneSkin.Purple);
			skins.add(iGoSmartPhoneSkin.Sand);
			skins.add(iGoSmartPhoneSkin.YellowGreen);
			skins.add(iGoSmartPhoneSkin.Water);
			skins.add(iGoSmartPhoneSkin.WaterPool);
			skins.add(iGoSmartPhoneSkin.White);
			sortSkin();
		}
		return skins.iterator();
	}

	protected void sortSkin() {
		Collections.sort(skins, new Comparator<iGoSmartPhoneSkin>() {
			@Override
			public int compare(iGoSmartPhoneSkin o1, iGoSmartPhoneSkin o2) {
				return master.lg(o1.toString()).compareTo(master.lg(o2.toString()));
			}
		});

	}

	@Override
	public boolean updateNetwork() {
		synchronized (verrou) {
			if (travel != null)
				if (!travel.isValideFromWhereIAm()) {
					CodeExecutor codeEx = new CodeExecutor() {
						public void execute() {
							if (travel.prepareToSolveAsBestAsICan())
								setCurrentState(IhmReceivingStates.COMPUT_TRAVEL, travel.getPathClone());
							else {
								setCurrentState(IhmReceivingStates.LOST_IN_TRAVEL, travel.getPathClone());
							}
						};
					};
					if (travelArrayPanel != null)
						travelArrayPanel.displayPopUpMessage(master.lg("UpdateNetworkTitle"), master
								.lg("UpdateNetworkMessage"), codeEx);
					if (travelGraphicPanel != null)
						travelGraphicPanel.displayPopUpMessage(master.lg("UpdateNetworkTitle"), master
								.lg("UpdateNetworkMessage"), codeEx);
					return true;
				}
			return false;
		}
	}

	@Override
	public void setErrorState(String title, String message) {
		this.actualState = IhmReceivingStates.ERROR_STATE;
		centerPanel.removeAll();
		try {
			checkErrorPanel();
		} catch (OutOfMemoryError e) {
			cleanPanelsStates(true);
			checkErrorPanel();
		}
		centerPanel.add(errorPanel);
		errorPanel.setMessage(message);
		errorPanel.setTitle(title);
		errorPanel.giveControle();
		centerPanel.validate();
	}

	@Override
	public boolean infoPathAsked(AlgoKindOfRelaxation algoKindOfRelaxation, Service service, Route route,
			Station station, KindRoute kindRoute) {
		if (computingPanel == null)
			return false;
		switch (algoKindOfRelaxation) {
		case ServiceRelaxation:
			computingPanel.addMessage(master.lg("INFO_ServiceRelaxation"));
			computingPanel.addMessage(service.getName());
			break;
		case EverythingFine:
			computingPanel.addMessage(master.lg("INFO_EverythingFine"));
			break;
		default:
			computingPanel.addMessage(master.lg("ERROR_Unknown") + " : " + algoKindOfRelaxation + "\n"
					+ master.lg("Service") + " : " + service + master.lg("Route") + " : " + route
					+ master.lg("Station") + " : " + station + master.lg("KindRoute") + " : " + kindRoute);
			break;
		}
		return true;
	}

	@Override
	public Iterator<EventInfo> getEvent() {
		return master.getNewEventInfos();
	}

	@Override
	public boolean hasEvent() {
		return master.hasNewEventInfos();
	}
}
