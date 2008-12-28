package ihm.smartPhone.component;

import ihm.smartPhone.tools.SizeAdapteur;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * Title: Class IGoFlowLayout
 * </p>
 * <p>
 * Description: Cette classe permet de placer les 3 composant de l'interface d'iGo selon les bonnes tailles et ratio.
 * </p>
 * 
 * @author iGo
 * @version 1.0
 */
public class IGoFlowLayout extends FlowLayout implements SizeAdapteur {

	private static final long serialVersionUID = 1L;
	protected static final int maxHeightForScalling = 640;

	// public static final int DEFAULT_HEIGTH = 220;public static final int DEFAULT_WIDTH = 140;
	public static final int DEFAULT_HEIGTH = 320; public static final int DEFAULT_WIDTH = 200;
	// public static final int DEFAULT_HEIGTH = 320; public static final int DEFAULT_WIDTH = 240;
	//public static final int DEFAULT_HEIGTH = 558; public static final int DEFAULT_WIDTH = 406;
	// public static final int DEFAULT_HEIGTH = 1200; public static final int DEFAULT_WIDTH = 1600;

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

	public static final int screenHeigth = Toolkit.getDefaultToolkit().getScreenSize().height;

	protected int height;
	protected int width;
	protected int sizeLargeFont;
	protected int sizeIntermediateFont;
	protected int sizeSmallFont;
	protected Font largeFont;
	protected Font intermediateFont;
	protected Font smallFont;
	protected int sizeUpperBar;
	protected int sizeLowerBar;
	protected int sizeLine;
	protected boolean fullScreen = false;
	protected static float corectionOfFontSize = (float) 1.6;

	protected Semaphore lookForALook = new Semaphore(1);
	protected boolean keepLook = false;

	/**
	 * Constructeur avec une taille par défaut(DEFAULT_HEIGTH par DEFAULT_WIDTH).
	 * 
	 * @param enableSizeLine
	 *            true si on veux les afficher.
	 */
	public IGoFlowLayout() {
		this(DEFAULT_HEIGTH, DEFAULT_WIDTH);
	}

	/**
	 * Constructeur où on définit la taille de l'écran.
	 * 
	 * @param height
	 *            la hauteur
	 * @param width
	 *            la largueur
	 * @param enableSizeLine
	 *            true si on veux les afficher.
	 */
	public IGoFlowLayout(int height, int width) {
		super();
		boolean sign = false;
		if (height > screenHeigth) {
			this.height = screenHeigth;
			sign = true;
		} else {
			this.height = height;
		}
		if (width > screenWidth) {
			this.width = screenWidth;
			if (sign) {
				fullScreen = true;
			}
		} else {
			this.width = width;
		}
		largeFont = new Font("Large", Font.PLAIN, 1);
		intermediateFont = largeFont;
		smallFont = largeFont;
		recalculate(height);
	}

	/**
	 * Recalcule les tailles en fonction de la nouvelle hauteur passé en paramètre
	 * 
	 * @param val
	 *            la nouvelle hauteur passé en paramètre
	 */
	protected void recalculate(int val) {
		// on determine le ratio actuelle est inférieur au ratio recommandé. Dans ce cas on bride la hauteur étalon.
		if (val * VERTICAL_RATIO > width)
			val = (int) (width / VERTICAL_RATIO);
		// On bride la hauteur étalon afin de ne pas avoir des menu gigantesque sur de grand écran
		if (val > maxHeightForScalling)
			val = maxHeightForScalling;
		this.sizeLargeFont = (int) (val * corectionOfFontSize * 0.032);
		// TODO refaire les paramètrage de font
		if (this.sizeLargeFont < 11)
			this.sizeLargeFont = 11;
		this.sizeIntermediateFont = (int) (val * corectionOfFontSize * 0.0195);// 195);
		if (this.sizeIntermediateFont < 9)
			this.sizeIntermediateFont = 9;
		this.sizeSmallFont = (int) (val * corectionOfFontSize * 0.013);
		if (this.sizeSmallFont < 7)
			this.sizeSmallFont = 7;
		this.sizeUpperBar = (int) (val * 0.13);
		this.sizeLowerBar = (int) (val * 0.07);// 0.07
		if (this.sizeLowerBar < 24)
			this.sizeLowerBar = 24;
		this.sizeLine = (int) (val * 0.0042);
		if (this.sizeLine == 0)
			this.sizeLine = 1;
		if (largeFont.getSize() != sizeLargeFont)
			largeFont = new Font("Large", Font.PLAIN, sizeLargeFont);
		if (intermediateFont.getSize() != sizeIntermediateFont)
			intermediateFont = new Font("Intermediat", Font.PLAIN, sizeIntermediateFont);
		if (smallFont.getSize() != sizeSmallFont)
			smallFont = new Font("Small", Font.PLAIN, sizeSmallFont);
		// System.out.println(this);
	}

	/**
	 * Surcharge de la méthode afin d'avoir un descriptif des variables internes.
	 */
	public String toString() {
		String msg = "IGoFlowLayout\n";
		msg += "size : " + height + "&" + width + "\n";
		msg += "Fonts : " + sizeLargeFont + "," + sizeIntermediateFont + "," + sizeSmallFont + "\n";
		msg += "Bars : " + sizeUpperBar + "," + sizeLowerBar + "\n";
		msg += "Line : " + sizeLine + "\n";
		msg += "fullScreen : " + fullScreen + "\n";
		return msg;
	}

	/**
	 * Définit les coordonnée et la taille de l'objet passé en paramètre au valeur passé en paramètre
	 * 
	 * @param target
	 *            l'objet placer
	 * @param x
	 *            son abscisse
	 * @param y
	 *            son ordonnée
	 * @param width
	 *            sa largueur
	 * @param height
	 *            sa hauteur
	 */
	private void placethem(Component target, int x, int y, int width, int height) {
		target.setSize(width, height);
		target.setLocation(x, y);
	}

	/**
	 * Méthode surcharger qui (re)place les objets à chaque appelle
	 * 
	 * @param Container
	 *            le conteneur où se trouve les objets.
	 */
	@Override
	public void layoutContainer(Container target) {
		this.width = target.getWidth();
		this.height = target.getHeight();
		recalculate(height);
		int insideWidth = width - target.getInsets().left - target.getInsets().right;

		for (Component c : target.getComponents()) {
			if (c instanceof UpperBar) {
				placethem(c, target.getInsets().left, target.getInsets().top, insideWidth, sizeUpperBar);
			} else if (c instanceof LowerBar) {
				placethem(c, target.getInsets().left, height - target.getInsets().bottom - sizeLowerBar, insideWidth,
						sizeLowerBar);
			} else if (target.getComponentCount() == 3) {
				placethem(c, target.getInsets().left, target.getInsets().top + getSizeLine() + sizeUpperBar,
						insideWidth, height - sizeLowerBar - 2 * getSizeLine() - target.getInsets().top
								- target.getInsets().bottom - sizeUpperBar);
			}
		}
	}

	/**
	 * Getteur de la taille de ligne
	 * 
	 * @return le taille des ligne, ou 0 si elle ne sont pas affichées
	 */
	public int getSizeLine() {
		return sizeLine;
	}

	/**
	 * Getteur de la hauteur
	 * 
	 * @return le hauteur du panel contenant
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Getteur de la largueur
	 * 
	 * @return la largueur du panel contenant
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Permet de savoir si on est en plein écran
	 * 
	 * @return true si on est en plein écran.
	 */
	public boolean isFullScreen() {
		return fullScreen;
	}

	/**
	 * Getteur de la largueur de l'écran
	 * 
	 * @return la largueur de l'écran
	 */
	public static int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Getteur de la hauteur de l'écran
	 * 
	 * @return la hauteur de l'écran
	 */
	public static int getScreenHeigth() {
		return screenHeigth;
	}

	/**
	 * Getteur de la grande taille de police de caractère.
	 * 
	 * @return la taille de la grandes police.
	 */
	public int getSizeLargeFont() {
		return sizeLargeFont;
	}

	/**
	 * Getteur de la grande de police de caractère.
	 * 
	 * @return la grandes police.
	 */
	public Font getLargeFont() {
		return largeFont;
	}

	/**
	 * Getteur de la taille intermédiaire de police de caractère.
	 * 
	 * @return la taille de la grandes police.
	 */
	public int getSizeIntermediateFont() {
		return sizeIntermediateFont;
	}

	/**
	 * Getteur de la police intermédiaire de caractère.
	 * 
	 * @return la grandes police.
	 */
	public Font getIntermediateFont() {
		return intermediateFont;
	}

	/**
	 * Getteur de la petite taille de police de caractère.
	 * 
	 * @return la taille de la petite police.
	 */
	public int getSizeSmallFont() {
		return sizeSmallFont;
	}

	/**
	 * Getteur de la petite police de caractère.
	 * 
	 * @return la petite police.
	 */
	public Font getSmallFont() {
		return smallFont;
	}

	/**
	 * Getteur de la taille de la barre supérieur
	 * 
	 * @return le taille
	 */
	public int getSizeUpperBar() {
		return sizeUpperBar;
	}

	/**
	 * Getteur de la taille de la barre inférieur
	 * 
	 * @return le taille
	 */
	public int getSizeLowerBar() {
		return sizeLowerBar;
	}

	/**
	 * Setteur permettant de définir si on est en plein écran.
	 * 
	 * @param fullScreen
	 *            true si on est en pleine écran
	 */
	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}
}
