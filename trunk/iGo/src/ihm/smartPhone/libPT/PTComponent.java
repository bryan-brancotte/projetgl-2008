package ihm.smartPhone.libPT;

import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea.AreaAndCodeExecutor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class PTComponent {

	protected static Color colorRound = new Color(141, 207, 80);

	protected Rectangle area;

	protected AreaAndCodeExecutor areaCodEx;

	protected String text = "Nothing";

	protected Font lastFont;

	protected PanelTooled father;

	protected boolean enable = true;

	/**
	 * Retourne la zone courante du dessin si ce dernier est actif, sinon on retourne null
	 * 
	 * @return la zone active si l'objet est actif, null dans le cas contraire
	 */
	public Rectangle getArea() {
		if (!this.isEnable())
			return null;
		if (areaCodEx != null)
			return areaCodEx.area;
		return area;
	}

	/**
	 * Demande à l'objet de se terminer. Il devient alors inactif et invisible.
	 */
	public void terminate() {
		if (areaCodEx != null)
			areaCodEx.terminate = true;
		this.setEnable(false);

	}

	/**
	 * Accesseur de l'état de l'objet, s'il est actif si sera dessiné, sinon il ne le sera pas
	 * 
	 * @return true s'il est activé, false dans le cas contraire
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * Mutateur de l'état de l'objet, s'il est actif si sera dessiné, sinon il ne le sera pas
	 * 
	 * @param enable
	 *            true pour l'activer
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
		if (!enable)
			this.area.setBounds(-1, -1, 0, 0);
	}

	protected PTComponent(PanelTooled father, AreaAndCodeExecutor areaCodEx) {
		super();
		this.areaCodEx = areaCodEx;
		if (areaCodEx != null)
			this.area = areaCodEx.area;
		this.father = father;
	}

	protected PTComponent(PanelTooled father, Rectangle area) {
		super();
		this.area = area;
		this.father = father;
	}

	/**
	 * Prepare l'objet pour être dessiné, on citera par exemple le calcul de la zone où il sera affiché. Si l'objet est
	 * desactivé il ne doit normalement y avoir aucun travail
	 * 
	 * @param g
	 *            le graphic où l'on dessine
	 * @param x
	 *            l'abscisse
	 * @param y
	 *            l'ordonnée
	 * @param text
	 *            le texte du bouton
	 * @param font
	 *            la police utilisé
	 * @return sa zone mise à jour, ou null si l'objet n'est pas actif
	 */
	public abstract Rectangle prepareArea(Graphics g, int x, int y, String text, Font font);

	/**
	 * Prepare l'objet pour être dessiné, on citera par exemple le calcul de la zone où il sera affiché
	 * 
	 * @param g
	 *            le graphic où l'on dessine
	 * @param x
	 *            l'abscisse
	 * @param y
	 *            l'ordonnée
	 * @param text
	 *            le texte du bouton
	 * @param font
	 *            la police utilisé
	 * @param horizontalCentered
	 *            true pour centré horizontalement l'objet sur la coordonnée x
	 * @param verticalCentered
	 *            true pour centré verticalement l'objet sur la coordonnée y
	 * @return sa zone, mise à jour.
	 */
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font, boolean horizontalCentered,
			boolean verticalCentered) {
		if (!enable)
			return null;
		prepareArea(g, x, y, text, font);
		if (!verticalCentered && !horizontalCentered)
			return area;
		if (horizontalCentered)
			area.x -= (area.width >> 1);
		if (verticalCentered)
			area.y -= (area.height >> 1);
		return area;
	}

	/**
	 * dessine l'objet sur la zone qu'on lui avait demandé de préparer
	 * 
	 * @param g
	 *            le graphic où l'on dessine
	 * @param font
	 *            la police utilisé
	 * @param colorInside
	 *            la couleur du fond
	 * @param colorLetter
	 *            la couleur du texte
	 */
	public abstract void draw(Graphics g, Color colorInside, Color colorLetter);

	/**
	 * Execute succesivement prepareArea et draw.
	 * 
	 * @param g
	 *            le graphic om l'on dessine
	 * @param x
	 *            l'abscisse
	 * @param y
	 *            l'ordonnée
	 * @param text
	 *            le texte du bouton
	 * @param font
	 *            la police utilisé
	 * @param colorInside
	 *            la couleur du fond
	 * @param colorLetter
	 *            la couleur du texte
	 * @return sa zone mise à jour, ou null si l'objet n'est pas actif
	 */
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return null;
		prepareArea(g, x, y, text, font);
		draw(g, colorInside, colorLetter);
		return area;
	}
}
