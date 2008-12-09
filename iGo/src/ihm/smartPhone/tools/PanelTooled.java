package ihm.smartPhone.tools;

import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class PanelTooled extends PanelDoubleBufferingSoftwear {
	protected MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray;

	public PanelTooled() {
		super();
		MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray = new MouseListenerClickAndMoveInArea(this);
		this.addMouseListener(clickAndMoveWarningAndArray);
		this.addMouseMotionListener(clickAndMoveWarningAndArray);
	}

	public Rectangle drawCheckBox(Graphics g, int x, int y, String text, boolean value, Font font, Color colorInside,
			Color colorLetter, CodeExecutor action) {
		return updateCheckBox(g, x, y, text, value, font, colorInside, colorLetter, clickAndMoveWarningAndArray
				.addInteractiveArea(new Rectangle(), action));
	}

	public Rectangle updateCheckBox(Graphics g, int x, int y, String text, boolean value, Font font, Color colorInside,
			Color colorLetter, Rectangle itsArea) {
		// TODO
		return null;
	}

	public Rectangle drawRadioBox(Graphics g, int x, int y, String text, boolean value, Font font, Color colorInside,
			Color colorLetter, CodeExecutor action) {
		return updateRadioBox(g, x, y, text, value, font, colorInside, colorLetter, clickAndMoveWarningAndArray
				.addInteractiveArea(new Rectangle(), action));

	}

	public Rectangle updateRadioBox(Graphics g, int x, int y, String text, boolean value, Font font, Color colorInside,
			Color colorLetter, Rectangle itsArea) {
		// TODO
		return null;

	}

	/**
	 * Crée le bouton, on lui passe en paramètre le code à exécuter lorsqu'il est cliqué
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
	 * @param action
	 *            l'action à déclancher lorsque l'on clic dessus
	 * @return la zone ou sera le rectangle.
	 */
	public Rectangle drawButton(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter,
			CodeExecutor action) {
		return updateButton(g, x, y, text, font, colorInside, colorLetter, clickAndMoveWarningAndArray
				.addInteractiveArea(new Rectangle(), action));
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
	 * @param itsArea
	 *            sa zone
	 * @return sa zone, mise à jour.
	 */
	public Rectangle updateButton(Graphics g, int x, int y, String text, Font font, Color colorInside,
			Color colorLetter, Rectangle itsArea) {
		// TODO
		return null;
	}

	/**
	 * Crée le bouton, on lui passe en paramètre le code à exécuter lorsqu'il est cliqué
	 * 
	 * @param g
	 *            le graphic om l'on dessine
	 * @param x
	 *            l'abscisse
	 * @param y
	 *            l'ordonnée
	 * @param image
	 *            l'image
	 * @param action
	 *            l'action à déclancher lorsque l'on clic dessus
	 * @return la zone ou sera le rectangle.
	 */
	public Rectangle drawButton(Graphics g, int x, int y, ImageIcon image, CodeExecutor action) {
		return updateButton(g, x, y, image, clickAndMoveWarningAndArray.addInteractiveArea(new Rectangle(), action));
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
	public Rectangle updateButton(Graphics g, int x, int y, ImageIcon image, Rectangle itsArea) {
		itsArea.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
		g.drawImage(image.getImage(), x, y, null);
		return itsArea;
	}
	// TODO champs avec completion

	// TODO fentre des choix comme chrome?

	// TODO combo box ou pas?

}
