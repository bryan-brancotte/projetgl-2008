package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Graphics;
import java.awt.Image;

public class SplashScreenPanel2 extends PanelState2 {

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
	public SplashScreenPanel2(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
	}

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public void paint(Graphics g) {
		if(logo==null)
			this.displayableAreaResized(g);
		// on l'efface
		g.clearRect(0, 0, getWidth(), getHeight());

		g.drawImage(logo, this.getWidth() - logo.getWidth(null) >> 1, (this.getHeight() - logo.getHeight(null) >> 1)
				- father.getSizeAdapteur().getSizeSmallFont(), null);
		// On affiche ensuite la barre de progression
		if (maxStepInSplashScreen >= 0) {
			g.drawRoundRect((this.getWidth() - logo.getWidth(null) >> 1) - 2,
					(this.getHeight() + logo.getHeight(null) >> 1) - 2, 4 + logo.getWidth(null), 4 + father
							.getSizeAdapteur().getSizeSmallFont(), 6, 6);
			int progression;
			if (stepInSplashScreen < maxStepInSplashScreen)
				progression = (int) (logo.getWidth(null) * ((float) stepInSplashScreen / maxStepInSplashScreen));
			else
				progression = logo.getWidth(null);
			g.fillPolygon(new int[] { this.getWidth() - logo.getWidth(null) >> 1,
					(this.getWidth() - logo.getWidth(null) >> 1) + progression + 1,
					(this.getWidth() - logo.getWidth(null) >> 1) + progression + 1,
					this.getWidth() - logo.getWidth(null) >> 1 }, new int[] {
					this.getHeight() + logo.getHeight(null) >> 1, this.getHeight() + logo.getHeight(null) >> 1,
					(this.getHeight() + logo.getHeight(null) >> 1) + father.getSizeAdapteur().getSizeSmallFont() + 1,
					(this.getHeight() + logo.getHeight(null) >> 1) + father.getSizeAdapteur().getSizeSmallFont() + 1 },
					4);
		}
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

	@Override
	public void displayableAreaResized(Graphics g) {
		// on charge aussi le logo en fonction de la taille du panel.
		logo = ImageLoader.getImageIcone(getClass().getResource("/images/iGo.256.png"), (int) (this.getWidth() * 0.8),
				(int) (this.getHeight() * 0.8)).getImage();
		g.setColor(father.getSkin().getColorOutside());
		g.drawImage(logo, this.getWidth() - logo.getWidth(null) >> 1,
				(this.getHeight() - logo.getHeight(null)) >> 1 - father.getSizeAdapteur().getSizeSmallFont(), null);
	}
}
