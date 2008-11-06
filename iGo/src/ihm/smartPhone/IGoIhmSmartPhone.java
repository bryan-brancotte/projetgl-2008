package ihm.smartPhone;

import iGoMaster.Master;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.listener.MyWindowStateListener;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.statePanels.LoadTravelPanel;
import ihm.smartPhone.statePanels.MainPanel;
import ihm.smartPhone.statePanels.NewTravelPanel;
import ihm.smartPhone.statePanels.SettingsPanel;
import ihm.smartPhone.statePanels.SplashScreenPanel;
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
	protected SplashScreenPanel splashScreenPanel = null;
	protected MainPanel mainPanel = null;
	protected LoadTravelPanel loadTravelPanel = null;
	protected SettingsPanel settingsPanel = null;
	protected NewTravelPanel newTravelPanel = null;
	protected TravelGraphicDisplayPanel travelGraphicPanel = null;
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
		this.setLocation((SizeAdapteur.screenWidth - sizeAdapteur.getWidth()) / 3,
				(SizeAdapteur.screenHeigth - sizeAdapteur.getHeight()) / 3);
		if (sizeAdapteur.isFullScreen()) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}

		/***************************************************************************************************************
		 * Préparation des trois zones de données
		 * 
		 * Barre supérieure
		 */
		upperBar = new UpperBar(this, false);
		upperBar.setBackground(skin.getColorInside());
		this.add(upperBar);

		/***************************************************************************************************************
		 * Zone principale
		 */
		centerPanel = new Panel(new BorderLayout(0, 0));
		centerPanel.setBackground(skin.getColorInside());
		centerPanel.add(new VoidPanel(this));
		this.add(centerPanel);

		/***************************************************************************************************************
		 * Barre inférieure
		 */
		lowerBar = new LowerBar(this, false);
		lowerBar.setBackground(skin.getColorInside());
		this.add(lowerBar);

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
		if (loadTravelPanel == null)
			loadTravelPanel = new LoadTravelPanel(this, upperBar, lowerBar, null);
	}

	protected void checkSettingsPanel() {
		if (settingsPanel == null)
			settingsPanel = new SettingsPanel(this, upperBar, lowerBar, null);
	}

	protected void checkGraphicPanel() {
		if (travelGraphicPanel == null)
			travelGraphicPanel = new TravelGraphicDisplayPanel(this, upperBar, lowerBar,null);
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
	public void setActualState(IhmReceivingStates actualState) {
		if (actualState == this.actualState)
			return;
		if (actualState != IhmReceivingStates.SPLASH_SCREEN){
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
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			cleanPanelsStates(false);
			this.actualState = IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE;
			centerPanel.removeAll();
			try {
				checkGraphicPanel();
			} catch (OutOfMemoryError e) {
				cleanPanelsStates(true);
				checkGraphicPanel();
			}
			centerPanel.add(travelGraphicPanel);
			travelGraphicPanel.giveControle();
			centerPanel.validate();
		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) {
			cleanPanelsStates(false);
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL_GRAPHIC_MODE) {

		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_GRAPHIC_MODE) {
			cleanPanelsStates(false);
		} else if (actualState == IhmReceivingStates.PREVISU_TRAVEL_TEXT_MODE) {

		} else if (actualState == IhmReceivingStates.EXPERIMENT_TRAVEL_TEXT_MODE) {
			cleanPanelsStates(false);
		}
	}

	protected void cleanPanelsStates(boolean dueToAnError) {
		// TODO trouver un façon d'invoquer le GC
		if (dueToAnError)
			ImageLoader.setFastLoadingOfImages(false);
		splashScreenPanel = null;
		loadTravelPanel = null;
		settingsPanel = null;
		mainPanel = null;
		travelGraphicPanel=null;
	}
}
