package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class PTComponent {

	protected Rectangle area;

	protected String text = "Nothing";

	protected PanelTooled father;

	protected boolean enable = true;

	public Rectangle getArea() {
		if (!this.isEnable())
			return null;
		return area;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected PTComponent(PanelTooled father, Rectangle area) {
		super();
		this.area = area;
		this.father = father;
	}

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
	 * @return sa zone, mise à jour.
	 */
	public abstract Rectangle prepareArea(Graphics g, int x, int y, String text, Font font);

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
		area.x -= (area.width >> 1);
		area.y -= (area.height >> 1);
		return area;
	}

	/**
	 * Execute succesivement prepareArea et draw.
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
	public abstract void draw(Graphics g, Font font, Color colorInside, Color colorLetter);

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
	 * @return sa zone, mise à jour.
	 */
	public final Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return null;
		prepareArea(g, x, y, text, font);
		draw(g, font, colorInside, colorLetter);
		return area;
	}
}
