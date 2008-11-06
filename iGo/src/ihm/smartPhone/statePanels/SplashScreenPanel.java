package ihm.smartPhone.statePanels;

import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Graphics;
import java.awt.Image;

public class SplashScreenPanel extends PanelState {

	private static final long serialVersionUID = 1L;
	protected int maxStepInSplashScreen = -1;
	protected int stepInSplashScreen = 0;
	protected Image logo = null;

	/**
	 * L'unique constructeur de cette classe. On a besoin l'ihm qui l'heberge afin d'afficher les bonnes couleurs via le
	 * getSkin() et les bonnes tailles via le getSizeAdapteur().
	 * 
	 * @param ihm
	 *            l'ihm réceptrice
	 * @param upperBar
	 *            la barre supérieur
	 * @param lowerBar
	 *            la barre inférieur
	 */
	public SplashScreenPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
	}

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public void paint(Graphics g) {
		// System.out.println("SplashScreenPanel.paint(...)");
		// Si le buffer n'existe pas, ou s'il n'est pas adapté à la taille du panel, on ne redéfinit
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			// on charge aussi le logo en fonction de la taille du panel.
			logo = ImageLoader.getImageIcone(getClass().getResource("/images/iGo.256.png"), (int) (this.getWidth() * 0.8),
					(int) (this.getHeight() * 0.8)).getImage();
			buffer.setColor(father.getSkin().getColorOutside());
			buffer.drawImage(logo, this.getWidth() / 2 - logo.getWidth(null) / 2, this.getHeight() / 2
					- logo.getHeight(null) / 2 - father.getSizeAdapteur().getSizeSmallFont(), null);
		}
		// si le logo n'est pas définit on le charge en mémoire. On n'a besoin de le charger que si la taille
		// destination à changé.
		if (logo == null) {
			System.out.println("logo");
			logo = ImageLoader.getImageIcone(getClass().getResource("/images/iGo.png"), (int) (this.getWidth() * 0.8),
					(int) (this.getHeight() * 0.8)).getImage();
		}
		// on l'efface
		buffer.clearRect(0, 0, getWidth(), getHeight());

		buffer.drawImage(logo, this.getWidth() / 2 - logo.getWidth(null) / 2, this.getHeight() / 2
				- logo.getHeight(null) / 2 - father.getSizeAdapteur().getSizeSmallFont(), null);
		// On affiche ensuite la barre de progression
		if (maxStepInSplashScreen >= 0) {
			buffer.drawRoundRect(this.getWidth() / 2 - 2 - logo.getWidth(null) / 2, this.getHeight() / 2 - 2
					+ logo.getHeight(null) / 2, 4 + logo.getWidth(null), 4 + father.getSizeAdapteur()
					.getSizeSmallFont(), 6, 6);
			int progression;
			if (stepInSplashScreen < maxStepInSplashScreen)
				progression = (int) (logo.getWidth(null) * ((float) stepInSplashScreen / maxStepInSplashScreen));
			else
				progression = logo.getWidth(null);
			buffer.fillPolygon(new int[] { this.getWidth() / 2 - logo.getWidth(null) / 2,
					this.getWidth() / 2 + 1 - logo.getWidth(null) / 2 + progression,
					this.getWidth() / 2 + 1 - logo.getWidth(null) / 2 + progression,
					this.getWidth() / 2 - logo.getWidth(null) / 2 },
					new int[] {
							this.getHeight() / 2 + logo.getHeight(null) / 2,
							this.getHeight() / 2 + logo.getHeight(null) / 2,
							this.getHeight() / 2 + logo.getHeight(null) / 2
									+ father.getSizeAdapteur().getSizeSmallFont() + 1,
							this.getHeight() / 2 + logo.getHeight(null) / 2
									+ father.getSizeAdapteur().getSizeSmallFont() + 1 }, 4);
		}
		g.drawImage(image, 0, 0, this);
	}

	/**
	 * getteur du maximume de pas dans l'écran de chargement/
	 * 
	 * @return le nombre maximume de pas.
	 */
	public int getMaxStepInSplashScreen() {
		return maxStepInSplashScreen;
	}

	/**
	 * setteur du maximume de pas dans l'écran de chargement. Un valeur négative aura pour effet de masquer la barre de
	 * chargement
	 * 
	 * @param maxStepInSplashScreen
	 *            le nombre maximume de pas.
	 */
	public void setMaxStepInSplashScreen(int maxStepInSplashScreen) {
		this.maxStepInSplashScreen = maxStepInSplashScreen;
		this.repaint();
	}

	/**
	 * 
	 * @return
	 */
	public int getStepInSplashScreen() {
		return stepInSplashScreen;
	}

	/**
	 * 
	 * @param stepInSplashScreen
	 */
	public void setStepInSplashScreen(int stepInSplashScreen) {
		this.stepInSplashScreen = stepInSplashScreen;
		this.repaint();
	}

	/**
	 * 
	 */
	public void incrementStepInSplashScreen() {
		this.stepInSplashScreen++;
		if (stepInSplashScreen > maxStepInSplashScreen)
			return;
		this.repaint();
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("SplashScreenUpperTitle"), FontSizeKind.INTERMEDIATE);
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setRightTitle(father.lg("iGoVersion"));
		lowerBar.repaint();
	}
}
