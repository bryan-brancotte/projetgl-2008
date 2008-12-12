package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class PTComponent {

	protected Rectangle area;

	protected PanelTooled father;

	protected boolean enable=true;

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
	 * Met à jour le bouton, on lui passe ne paramètre la zone qui lui à été attribué.
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
	public abstract Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside,
			Color colorLetter);
}
