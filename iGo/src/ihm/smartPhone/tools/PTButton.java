package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PTButton extends PTComponent {

	protected PTButton(PanelTooled father,Rectangle area) {
		super(father,area);
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
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		g.setColor(colorLetter);
		g.drawString(text, x, y);
		area.setBounds(x, y, father.getWidthString(text, g, font), father.getHeigthString(text, g, font));
		return null;
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
	 * @param image
	 *            l'image
	 * @param itsArea
	 *            sa zone
	 * @return sa zone, mise à jour.
	 */
	public void update(Graphics g, int x, int y, ImageIcon image) {
		g.drawImage(image.getImage(), x, y, null);
		area.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
	}
}
