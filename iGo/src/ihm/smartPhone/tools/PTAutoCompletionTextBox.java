package ihm.smartPhone.tools;

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
import java.util.ArrayList;

public class PTAutoCompletionTextBox extends PTComponent {

	protected String[] fields;
	protected ArrayList<Integer> fieldsMatching;
	protected int fieldMatched = 0;
	protected String currentStringLeft;
	protected String currentStringSelected;
	protected String currentStringRight;
	protected boolean isInMe = false;
	protected boolean isSelecting = false;
	protected boolean key_char = false;
	protected byte selectingWay = 0;
	protected Font lastFont;
	protected CodeExecutor actionOnChange;
	protected CodeExecutor actionOnEnter;

	protected PTAutoCompletionTextBox(PanelTooled nvfather, Rectangle nvArea, String[] nvFields,
			CodeExecutor theActionOnChange, CodeExecutor theActionOnEnter) {
		super(nvfather, nvArea);
		this.fields = nvFields;
		this.actionOnChange = theActionOnChange;
		this.actionOnEnter = theActionOnEnter;
		this.fieldsMatching = new ArrayList<Integer>();
		this.currentStringLeft = "";
		this.currentStringSelected = "";
		this.currentStringRight = "";
		this.father.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isInMe)
					return;
				int size;
				key_char = false;
				switch (e.getKeyCode()) {
				// The void key :
				case KeyEvent.VK_CONTROL:
				case KeyEvent.VK_ALT:
				case KeyEvent.VK_ALT_GRAPH:
				case KeyEvent.VK_CONTEXT_MENU:
					return;
					// the usefull keys :
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
							if (currentStringSelected.isEmpty())
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
							if (currentStringSelected.isEmpty())
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
					fieldMatched = 0;
					if (currentStringSelected.length() > 0) {
						currentStringSelected = "";
					} else {
						if (currentStringRight.length() < 1)
							return;
						currentStringRight = currentStringRight.substring(1);
					}
					break;
				case KeyEvent.VK_BACK_SPACE:
					fieldMatched = 0;
					if (currentStringSelected.length() > 0) {
						currentStringSelected = "";
					} else {
						if (currentStringLeft.length() < 1)
							return;
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
				case KeyEvent.VK_ENTER:
					if (actionOnEnter != null)
						actionOnEnter.execute();
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_KP_DOWN:
					fieldMatched++;
					autoCompletion();
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_KP_UP:
					fieldMatched--;
					autoCompletion();
					break;
				default:
					if (key_char = !e.isActionKey()) {
						fieldMatched = 0;
						currentStringLeft += e.getKeyChar();
						currentStringSelected = "";
						autoCompletion();
					} else
						return;
				}
				if (actionOnChange != null)
					actionOnChange.execute();
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
					currentStringLeft = currentStringLeft + currentStringSelected + currentStringRight;
					currentStringSelected = "";
					currentStringRight = "";
					int i = 0;
					while ((i < currentStringLeft.length())
							&& PanelDoubleBufferingSoftwear.getWidthString(currentStringLeft.substring(0, i),
									father.buffer, lastFont) < (e.getX() - area.x - (lastFont.getSize() >> 1))) {
						i++;
					}
					if (--i < 0)
						i = 0;
					if (i < currentStringLeft.length()) {
						currentStringRight = currentStringLeft.substring(i);
						currentStringLeft = currentStringLeft.substring(0, i);
					}
					isInMe = true;
					father.repaint();
				} else {
					if (isInMe) {
						currentStringLeft = currentStringLeft + currentStringSelected + currentStringRight;
						currentStringSelected = "";
						currentStringRight = "";
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

	protected void autoCompletion() {
		if ((currentStringRight.isEmpty())/* && (currentStringLeft.length() > 0)/* */) {
			int cpt;

			if (key_char || currentStringLeft.isEmpty()) {
				fieldsMatching.clear();
				for (cpt = 0; cpt < fields.length; cpt++) {
					if (fields[cpt].toLowerCase().startsWith(currentStringLeft.toLowerCase())) {
						fieldsMatching.add(cpt);
					}
				}
			}
			if (fieldsMatching.size() > 0) {
				if (fieldMatched < 0)
					fieldMatched += fieldsMatching.size();
				fieldMatched = fieldMatched % fieldsMatching.size();
				currentStringLeft = fields[fieldsMatching.get(fieldMatched)].substring(0, cpt = currentStringLeft
						.length());
				currentStringSelected = fields[fieldsMatching.get(fieldMatched)].substring(cpt);
			}
		}
	}

	/**
	 * Retourne le contenue intégrale de la zone de texte.
	 * 
	 * @return la chaine représentant le contenue peu importe la selection et position du pointeur
	 */
	public String getText() {
		return currentStringLeft + currentStringSelected + currentStringRight;
	}

	/**
	 * Définit le contenue de la zone de texte à la valeur passé en paramètre. Le pointeur est mit au début de la chaine
	 * 
	 * @param la
	 *            chaine représentant le contenue peu importe la selection et position du pointeur
	 */
	public void setText(String s) {
		currentStringLeft = "";
		currentStringSelected = "";
		currentStringRight = s;
	}

	@Override
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		g.setColor(colorInside);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, area.width, area.height);
		g.drawString(currentStringLeft + currentStringSelected + currentStringRight, area.x + (font.getSize() >> 1),
				area.y + area.height - (font.getSize() >> 2));
		if (currentStringSelected.length() > 0) {
			int x = area.x + PanelTooled.getWidthString(currentStringLeft, g) + (font.getSize() >> 1);
			g.fillRect(x, area.y, PanelTooled.getWidthString(currentStringSelected, g), area.height);
			g.setColor(colorInside);
			g.drawString(currentStringSelected, x, area.y + area.height - (font.getSize() >> 2));
		}
		if (isInMe) {
			int x = area.x + (font.getSize() >> 1) + PanelTooled.getWidthString(currentStringLeft, g);
			g.setColor(colorLetter);
			g.drawLine(x, area.y + 1, x, area.y + area.height - 2);
		}
		// System.out.println(currentStringLeft + "[" + currentStringSelected + "]" + currentStringRight);
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
