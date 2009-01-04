package ihm.smartPhone.libPT;

import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea.AreaAndCodeExecutor;
import ihm.smartPhone.listener.MouseListenerSimplificated;
import ihm.smartPhone.listener.MouseMotionListenerSimplificated;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class PTTextBox extends PTComponent {

	protected String currentStringLeft;
	protected String currentStringSelected;
	protected String currentStringRight;
	protected boolean isInMe = false;
	protected boolean isSelecting = false;
	protected byte selectingWay = 0;

	protected PTTextBox(PanelTooled nvfather, AreaAndCodeExecutor nvArea) {
		super(nvfather, nvArea);
		currentStringLeft = "";
		currentStringSelected = "";
		currentStringRight = "";
		// father.addMouseListener(l)
		father.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isInMe)
					return;
				int size;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					if (isSelecting) {
						if (selectingWay <= 0) {
							size = currentStringLeft.length() - 1;
							if (size < 0)
								return;
							selectingWay = -1;
							currentStringSelected = currentStringLeft.charAt(size) + currentStringSelected;
							currentStringLeft = currentStringLeft.substring(0, size);
						} else {
							size = currentStringSelected.length() - 1;
							if (size < 0)
								return;
							currentStringRight = currentStringSelected.charAt(size) + currentStringRight;
							currentStringSelected = currentStringSelected.substring(0, size);
							if (currentStringSelected.compareTo("") == 0)
								selectingWay = 0;
						}
					} else if (currentStringSelected.length() > 0) {
						currentStringRight = currentStringSelected + currentStringRight;
						currentStringSelected = "";
					} else {
						size = currentStringLeft.length() - 1;
						if (size < 0)
							return;
						currentStringRight = currentStringLeft.charAt(size) + currentStringRight;
						currentStringLeft = currentStringLeft.substring(0, size);
					}
					break;
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					if (isSelecting) {
						if (selectingWay >= 0) {
							if (currentStringRight.length() < 1)
								return;
							selectingWay = 1;
							currentStringSelected += currentStringRight.charAt(0);
							currentStringRight = currentStringRight.substring(1);
						} else {
							if (currentStringSelected.length() < 1)
								return;
							currentStringLeft += currentStringSelected.charAt(0);
							currentStringSelected = currentStringSelected.substring(1);
							if (currentStringSelected.compareTo("") == 0)
								selectingWay = 0;
						}
					} else if (currentStringSelected.length() > 0) {
						currentStringLeft += currentStringSelected;
						currentStringSelected = "";
					} else {
						if (currentStringRight.length() < 1)
							return;
						currentStringLeft += currentStringRight.charAt(0);
						currentStringRight = currentStringRight.substring(1);
					}
					break;
				case KeyEvent.VK_DELETE:
					if (currentStringRight.length() < 1)
						return;
					if (currentStringSelected.length() > 0) {
						currentStringSelected = "";
					} else {
						currentStringRight = currentStringRight.substring(1);
					}
					break;
				case KeyEvent.VK_BACK_SPACE:
					if (currentStringLeft.length() < 1)
						return;
					if (currentStringSelected.length() > 0) {
						currentStringSelected = "";
					} else {
						currentStringLeft = currentStringLeft.substring(0, currentStringLeft.length() - 1);
					}
					break;
				case KeyEvent.VK_HOME:
					if (isSelecting) {
						currentStringSelected = currentStringLeft + currentStringSelected;
						currentStringLeft = "";
						selectingWay = -1;
					} else {
						currentStringRight = currentStringLeft + currentStringSelected + currentStringRight;
						currentStringSelected = "";
						currentStringLeft = "";
					}
					break;
				case KeyEvent.VK_END:
					if (isSelecting) {
						currentStringSelected += currentStringRight;
						currentStringRight = "";
						selectingWay = 1;
					} else {
						currentStringLeft = currentStringLeft + currentStringSelected + currentStringRight;
						currentStringSelected = "";
						currentStringRight = "";
					}
					break;
				case KeyEvent.VK_SHIFT:
					isSelecting = true;
					break;
				case KeyEvent.VK_CONTROL:
				case KeyEvent.VK_ALT:
				case KeyEvent.VK_ALT_GRAPH:
				case KeyEvent.VK_CONTEXT_MENU:
					return;
				default:
					if (!e.isActionKey()) {
						currentStringLeft += e.getKeyChar();
						currentStringSelected = "";
					} else
						return;
				}
				father.repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// System.out.println("keyReleased");
				if (!isInMe)
					return;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SHIFT:
					// if (isSelecting) {
					// currentStringLeft += currentStringSelected;
					// currentStringSelected = "";
					isSelecting = false;
					selectingWay = 0;
					// father.repaint();
					// }
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// System.out.println("keyTyped");
			}
		});
		father.addMouseMotionListener(new MouseMotionListenerSimplificated<PanelTooled>(father) {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (area.contains(e.getPoint()))
					this.origin.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			}
		});
		father.addMouseListener(new MouseListenerSimplificated<PanelTooled>(father) {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (area.contains(e.getPoint())) {
					int i = 0;
					while ((i < currentStringLeft.length())
							&& PanelDoubleBufferingSoftwear.getWidthString(currentStringLeft.substring(0, i),
									father.buffer, lastFont) < (e.getX() - area.x - (lastFont.getSize() >> 1))) {
						i++;
					}
					if (i < 0)
						i = 0;
					if (i < currentStringLeft.length()) {
						currentStringRight = currentStringLeft.substring(i);
						currentStringLeft = currentStringLeft.substring(0, i);
					}
					isInMe = true;
					father.repaint();
				} else {
					if (isInMe) {
						currentStringLeft = currentStringLeft + currentStringRight;
						isInMe = false;
						father.repaint();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
	}

	public String getText() {
		return currentStringLeft + currentStringSelected + currentStringRight;
	}

	@Override
	public void draw(Graphics g, Color colorInside, Color colorLetter) {
		g.setColor(colorInside);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, area.width, area.height);
		g.drawString(currentStringLeft + currentStringSelected + currentStringRight, area.x + (lastFont.getSize() >> 1),
				area.y + area.height - (lastFont.getSize() >> 2));
		if (currentStringSelected.length() > 0) {
			int x = area.x + PanelTooled.getWidthString(currentStringLeft, g) + (lastFont.getSize() >> 1);
			g.fillRect(x, area.y, PanelTooled.getWidthString(currentStringSelected, g), area.height);
			g.setColor(colorInside);
			g.drawString(currentStringSelected, x, area.y + area.height - (lastFont.getSize() >> 2));
		}
		if (isInMe) {
			int x = area.x + (lastFont.getSize() >> 1) + PanelTooled.getWidthString(currentStringLeft, g);
			g.setColor(colorLetter);
			g.drawLine(x, area.y + 1, x, area.y + area.height - 2);
		}
		System.out.println(currentStringLeft + "[" + currentStringSelected + "]" + currentStringRight);
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
		lastFont = font;
		area.setBounds(x, y, width, font.getSize() + (font.getSize() >> 1));
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
		draw(g, colorInside, colorLetter);
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
