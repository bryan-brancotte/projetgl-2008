package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PTAutoCompletionTextBox extends PTComponent {

	protected String[] fields;
	protected String currentStringLeft;
	protected String currentStringRight;
	protected boolean isInMe = true;

	protected PTAutoCompletionTextBox(PanelTooled nvfather, Rectangle area, String[] fields) {
		super(nvfather, area);
		this.fields = fields;
		currentStringLeft = "";
		currentStringRight = "";
		// TODO ........taf.........................
		// father.addMouseListener(l)
		father.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isInMe)
					return;
				int size;
				boolean shouldRepaint = true;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					size = currentStringLeft.length() - 1;
					currentStringRight = currentStringLeft.charAt(size) + currentStringRight;
					currentStringLeft = currentStringLeft.substring(0, size);
					break;
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					currentStringLeft += currentStringRight.charAt(0);
					currentStringRight = currentStringRight.substring(1);
					break;
				case KeyEvent.VK_DELETE:
					currentStringRight = currentStringRight.substring(1);
					break;
				case KeyEvent.VK_BACK_SPACE:
					currentStringLeft = currentStringLeft.substring(0, currentStringLeft.length() - 1);
					break;
				default:
					size = currentStringLeft.length();
					currentStringLeft += e.getKeyChar();
					if (size == currentStringLeft.length())
						shouldRepaint = false;
					return;
				}
				if (shouldRepaint)
					father.repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	@Override
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		g.setColor(colorInside);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, area.width, area.height);
	}

	/**
	 * Prepare l'objet pour être dessiné, on citera par exemple le calcul de la zone où il sera affiché
	 * 
	 * @param g
	 *            le graphic où l'on dessine
	 * @param x
	 *            l'abscisse
	 * @param y
	 *            l'ordonnée
	 * @param width
	 *            la largueur de l'objet
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
	public Rectangle prepareArea(Graphics g, int x, int y, int width, Font font) {
		area.setBounds(x, y, width, font.getSize());
		return null;
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
	 * @param width
	 *            la largueur de l'objet
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
	public Rectangle update(Graphics g, int x, int y, int width, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return null;
		prepareArea(g, x, y, width, font);
		draw(g, font, colorInside, colorLetter);
		return area;
	}

	@Deprecated
	@Override
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		return null;
	}

	@Override
	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		return null;
	}

	@Override
	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font, boolean horizontalCentered,
			boolean verticalCentered) {
		return null;
	}
}
