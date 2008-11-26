package ihm.smartPhone;

import graphNetwork.PathInGraphReader;
import iGoMaster.IHM;
import iGoMaster.Master;
import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.listener.MyWindowStateListener;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.statePanels.LoadTravelPanel;
import ihm.smartPhone.statePanels.MainPanel;
import ihm.smartPhone.statePanels.NewTravelPanel;
import ihm.smartPhone.statePanels.SettingsPanel;
import ihm.smartPhone.statePanels.SplashScreenPanel;
import ihm.smartPhone.statePanels.TravelArrayDisplayPanel;
import ihm.smartPhone.statePanels.TravelDisplayPanel;
import ihm.smartPhone.statePanels.TravelGraphicDisplayPanel;
import ihm.smartPhone.statePanels.VoidPanel;
import ihm.smartPhone.tools.IGoFlowLayout;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur;
import ihm.smartPhone.tools.iGoSmartPhoneSkin;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.Observer;

import javax.swing.JOptionPane;

import streamInFolder.graphReaderFolder.Network;

public class IGoIhmSmartPhone extends Frame implements IHM, IhmReceivingPanelState {

	private static final long serialVersionUID = 1L;

	protected IGoFlowLayout sizeAdapteur = null;

	protected UpperBar upperBar;
	protected int oldSizeLine = -1;
	protected Panel centerPanel;
	protected LowerBar lowerBar;
	protected IhmReceivingStates actualState = IhmReceivingStates.UNKNOWN;
	/**
	 * etat préféré pour la prévisualisation et l'expérimentation d'un trajet : IhmReceivingStates.GRAPHIC_MODE ou
	 * IhmReceivingStates.ARRAY_MODE
	 */
	protected IhmReceivingStates preferedState;
	protected SplashScreenPanel splashScreenPanel = null;
	protected MainPanel mainPanel = null;
	protected LoadTravelPanel loadTravelPanel = null;
	protected LoadTravelPanel favoritesPanel = null;
	protected SettingsPanel settingsPanel = null;
	protected NewTravelPanel newTravelPanel = null;
	protected TravelDisplayPanel travelGraphicPanel = null;
	protected TravelDisplayPanel travelArrayPanel = null;
	protected boolean quitMessage = false;
	protected iGoSmartPhoneSkin skin;

	/**
	 * Constructeur de l'interface. Le master est requit car il sert imédiatement.
	 * 
	 * @param master
	 *            le master, celui qui fournira entre autre les traductions
	 */
	public IGoIhmSmartPhone(Master master) {
		this(master, iGoSmartPhoneSkin.WHITE_WITH_LINE);
	}

	public IGoIhmSmartPhone(Master master, iGoSmartPhoneSkin skin) {
		super(master.lg("ProgName"));
		this.skin = skin;
		sizeAdapteur = new IGoFlowLayout(skin.isDisplayLine());
		System.out.println(sizeAdapteur);
		this.setLayout(sizeAdapteur);

		this.setBackground(skin.getColorLine());
		this.master = master;
		this.setSize(sizeAdapteur.getWidth(), sizeAdapteur.getHeight());
		this.setLocation((int) ((SizeAdapteur.screenWidth - sizeAdapteur.getWidth())*0.333),
				(int) ((SizeAdapteur.screenHeigth - sizeAdapteur.getHeight())*0.333));
		if (sizeAdapteur.isFullScreen()) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}
		String tmp = this.master.config("GRAPHIC_OR_ARRAY_MODE");
		if ((tmp == null) || (tmp.compareTo(IhmReceivingStates.GRAPHIC_MODE.toString()) == 0))
			this.preferedState = IhmReceivingStates.GRAPHIC_MODE;
		else
			this.preferedState = IhmReceivingStates.ARRAY_MODE;

		/***************************************************************************************************************
		 * Préparation des trois zones de données
		 * 
		 * Barre supérieure
		 */
		upperBar = new UpperBar(this, false);
		upperBar.setBackground(skin.getColorInside());
		this.add(upperBar);

		/***************************************************************************************************************
		 * Barre inférieure
		 */
		lowerBar = new LowerBar(this, false);
		lowerBar.setBackground(skin.getColorInside());
		this.add(lowerBar);

		/***************************************************************************************************************
		 * Zone principale
		 */
		centerPanel = new Panel(new BorderLayout(0, 0));
		centerPanel.setBackground(skin.getColorInside());
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
	private Network reseau;

	/**
	 * Getter of the property <tt>reseau</tt>
	 * 
	 * @return Returns the reseau.
	 * @uml.property name="reseau"
	 */
	public Network getReseau() {
		return reseau;
	}

	/**
	 * Setter of the property <tt>reseau</tt>
	 * 
	 * @param reseau
	 *            The reseau to set.
	 * @uml.property name="reseau"
	 */
	public void setReseau(Network reseau) {
		this.reseau = reseau;
	}

	@Override
	public void start(boolean bySplashScreen, int step) {
		if (checkSplashScreenPanel())
			splashScreenPanel.setMaxStepInSplashScreen(step);
		if (bySplashScreen)
			this.setActualState(IhmReceivingStates.SPLASH_SCREEN);
		else
			this.setActualState(IhmReceivingStates.MAIN_INTERFACE);
		this.setVisible(true);
	}

	public void start(boolean bySplashScreen) {
		start(bySplashScreen, -1);
	}

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
			setActualState(IhmReceivingStates.MAIN_INTERFACE);
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

	protected boolean checkSplashScreenPanel() {
		if (splashScreenPanel != null)
			return true;
		if ((actualState == IhmReceivingStates.SPLASH_SCREEN) || (actualState == IhmReceivingStates.UNKNOWN)) {
			splashScreenPanel = new SplashScreenPanel(this, upperBar, lowerBar);
			return true;
		}
		return false;
	}

	protected void checkMainPanel() {
		if (mainPanel == null)
			mainPanel = new MainPanel(this, upperBar, lowerBar);
	}

	protected void checkLoadTravelPanel() {
		if (loadTravelPanel == null) {
			LinkedList<TravelForTravelPanel> lst = new LinkedList<TravelForTravelPanel>();
			for (int i = 0; i < 5; i++)
				lst.add(new TravelForTravelPanelExemple());
			loadTravelPanel = new LoadTravelPanel(this, upperBar, lowerBar, IhmReceivingStates.LOAD_TRAVEL, lst);
		}
	}

	protected void checkFavoritesPanel() {
		if (favoritesPanel == null) {
			LinkedList<TravelForTravelPanel> lst = new LinkedList<TravelForTravelPanel>();
			TravelForTravelPanel t = new TravelForTravelPanelExemple();
			for (int i = 0; i < 5; t = new TravelForTravelPanelExemple()) {
				if (t.isFavorite()) {
					lst.add(t);
					i++;
				}
			}
			favoritesPanel = new LoadTravelPanel(this, upperBar, lowerBar, IhmReceivingStates.FAVORITES, lst);
		}
	}

	protected void checkSettingsPanel() {
		if (settingsPanel == null)
			settingsPanel = new SettingsPanel(this, upperBar, lowerBar, null);
	}

	protected void checkTravelGraphicDisplayPanel() {
		if (travelGraphicPanel == null)
			travelGraphicPanel = new TravelGraphicDisplayPanel(this, upperBar, lowerBar, null);
	}

	protected void checkTravelArrayDisplayPanel() {
		if (travelArrayPanel == null)
			travelArrayPanel = new TravelArrayDisplayPanel(this, upperBar, lowerBar, null);
	}

	protected void checkNewTravelPanel() {
		if (newTravelPanel == null)
			newTravelPanel = new NewTravelPanel(this, upperBar, lowerBar);
	}

	@Override
	public void stop() {
		this.endInterface();
	}

	protected boolean endInterface() {
		if (quitMessage && (JOptionPane.showConfirmDialog(this, master.lg("DoYouWantToQuit")) == JOptionPane.NO_OPTION))
			return false;
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
	public IhmReceivingStates getActualState() {
		return actualState;
	}

	@Override
	public boolean setActualState(IhmReceivingStates actualState) {
		if (actualState == this.actualState)
			return true;
		if (actualState != IhmReceivingStates.SPLASH_SCREEN) {
			splashScreenPanel = null;
		}

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
		} else if (actualState == IhmReceivingStates.MAIN_INTERFACE) {
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
		} else if (actualState == IhmReceivingStates.NEW_TRAVEL) {
			this.actualState = IhmReceivingStates.NEW_TRAVEL;
			centerPanel.removeAll();
			try {
				checkNewTravelPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkNewTravelPanel();
			}
			centerPanel.add(newTravelPanel);
			newTravelPanel.giveControle();
			centerPanel.validate();
			return true;
		} else if (actualState == IhmReceivingStates.LOAD_TRAVEL) {
			this.actualState = IhmReceivingStates.LOAD_TRAVEL;
			centerPanel.removeAll();
			try {
				checkLoadTravelPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkLoadTravelPanel();
			}
			centerPanel.add(loadTravelPanel);
			loadTravelPanel.giveControle();
			centerPanel.validate();
			return true;
		} else if (actualState == IhmReceivingStates.FAVORITES) {
			this.actualState = IhmReceivingStates.FAVORITES;
			centerPanel.removeAll();
			try {
				checkFavoritesPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkFavoritesPanel();
			}
			centerPanel.add(favoritesPanel);
			favoritesPanel.giveControle();
			centerPanel.validate();
			return true;
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
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			actualState = IhmReceivingStates.PREVISU_TRAVEL.mergeState(preferedState);
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) {
			actualState = IhmReceivingStates.EXPERIMENT_TRAVEL.mergeState(preferedState);
		}
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE) {
			cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE;
			centerPanel.removeAll();
			try {
				checkTravelGraphicDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelGraphicDisplayPanel();
			}
			centerPanel.add(travelGraphicPanel);
			travelGraphicPanel.setActualState(IhmReceivingStates.PREVISU_TRAVEL);
			travelGraphicPanel.giveControle();
			centerPanel.validate();
			return true;
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE) {
			cleanPanelsStates(false);
			if (this.actualState != IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE) {
				centerPanel.removeAll();
				try {
					checkTravelGraphicDisplayPanel();
				} catch (OutOfMemoryError e) {
					cleanPanelsStates(true);
					checkTravelGraphicDisplayPanel();
				}
				centerPanel.add(travelGraphicPanel);
			}
			travelGraphicPanel.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
			travelGraphicPanel.giveControle();
			centerPanel.validate();
			this.actualState = IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE;
			return true;
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL_ARRAY_MODE) {
			cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.PREVISU_TRAVEL_ARRAY_MODE;
			centerPanel.removeAll();
			try {
				checkTravelArrayDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelArrayDisplayPanel();
			}
			centerPanel.add(travelArrayPanel);
			travelArrayPanel.setActualState(IhmReceivingStates.PREVISU_TRAVEL);
			travelArrayPanel.giveControle();
			travelArrayPanel.displayPopUpMessage("Info", "En cours de création, merci.", null);
			centerPanel.validate();
			return true;
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_ARRAY_MODE) {
			cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.EXPERIMENT_TRAVEL_ARRAY_MODE;
			centerPanel.removeAll();
			try {
				checkTravelArrayDisplayPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkTravelArrayDisplayPanel();
			}
			centerPanel.add(travelArrayPanel);
			travelArrayPanel.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
			travelArrayPanel.giveControle();
			travelArrayPanel.displayPopUpMessage("Info", "En cours de création, merci.", null);
			centerPanel.validate();
			return true;
		}
		return false;
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
			ImageLoader.setFastLoadingOfImages(false);
		splashScreenPanel = null;
		loadTravelPanel = null;
		settingsPanel = null;
		mainPanel = null;
		travelGraphicPanel = null;
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

	/**
	 */
	public void setChanged() {
	}

	/**
	 */
	public void addObserver(Observer o) {
	}

	/**
	 */
	public void notifyObservers() {
	}

	@Override
	public boolean returnPathAsked(PathInGraphReader path, String message) {
		// TODO Auto-generated method stub
		return false;
	}
}
