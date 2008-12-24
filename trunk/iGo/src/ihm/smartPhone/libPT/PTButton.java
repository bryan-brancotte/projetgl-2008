package ihm.smartPhone.libPT;

import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea.AreaAndCodeExecutor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PTButton extends PTComponent {

	/**
	 * Constructeur par défaut d'un PTButton, on se doit de fournir un objet Rectangle qui représentera la zone où il
	 * sera dessiné. De part se nature c'est aussi la zone où qui sera cliquable.
	 * 
	 * @param father
	 *            le panel qui héberge le PTButton
	 * @param area
	 *            sa zone
	 */
	protected PTButton(PanelTooled father, AreaAndCodeExecutor area) {
		super(father, area);
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
	 * @return sa zone, mise à jour.
	 */
	public Rectangle update(Graphics g, int x, int y, ImageIcon image) {
		if (!enable)
			return null;
		prepareArea(g, x, y, image);
		draw(g, image);
		return area;
	}

	@Override
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.setColor(colorLetter);
		g.drawString(text, area.x, area.y);
	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!enable)
			return null;
		if (text != null)
			this.text = text;
		area.setBounds(x, y, PanelDoubleBufferingSoftwear.getWidthString(text, g, font), PanelDoubleBufferingSoftwear
				.getHeightString(text, g, font));
		return area;
	}

	/**
	 * Dessine l'image dans la zone qu'on a prépare avec prepareArea(g,x,y,image);
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner l'image
	 * @param image
	 *            l'image à dessiner
	 */
	public void draw(Graphics g, ImageIcon image) {
		if (!enable)
			return;
		g.drawImage(image.getImage(), area.x, area.y, null);
	}

	/**
	 * Prépare la zone où on va dessiner l'image représentant le bouton
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner l'image
	 * @param x
	 *            l'abcisse
	 * @param y
	 *            l'ordonnée
	 * @param image
	 *            l'image à dessiner
	 * @return la zone mise à jour
	 */
	public Rectangle prepareArea(Graphics g, int x, int y, ImageIcon image) {
		if (!enable)
			return null;
		area.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
		return area;
	}
}
