package ihm.smartPhone.statePanels;

import graphNetwork.Service;
import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;

import sun.java2d.loops.DrawLine;

public class TravelGraphicDisplayPanel extends TravelDisplayPanel {

	protected boolean affichageDroite = false;

	protected GraphicsViewPort buffer;

	protected int image;

	private static final long serialVersionUID = 1L;

	protected Polygon polygon = new Polygon();
	protected OvalToDraw ovalToDrawDelayed = new OvalToDraw(null, 0, 0, 0, 0);

	/**
	 * La dernière abscisse du pointeur
	 */
	protected int xLastPointeur;
	/**
	 * La dernière ordonnée du pointeur
	 */
	protected int yLastPointeur;
	/**
	 * La plus petite des quatre valeurs étalons du réseau : elle définit la largueur de certains traits.
	 */
	protected int sizeLine;
	/**
	 * L'une des trois valeurs de base utilisées pour dessiner le réseau. C'est la valeur d'une demi largueur de ligne
	 */
	protected int sizeDemiLine;
	/**
	 * L'une des trois valeurs de base utilisées pour dessiner le réseau. C'est le quadruple de la grande largueur.
	 */
	protected int sizeQuadLarge;
	/**
	 * L'une des trois valeurs de base utilisées pour dessiner le réseau. C'est la grande largueur.
	 */
	protected int sizeLarge;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = false;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean firstRepaint = true;
	/**
	 * boolean permetant de savoir qu'on a appuyé sur suivant, et qu'on doit mettre en haut la station atteinte
	 */
	protected boolean putStationUp = false;

	public TravelGraphicDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar, travelForDisplayPanel);
		affichageDroite = (father.getConfig(IhmReceivingStates.GRAPHIC_MODE.toString()).compareTo("true") == 0);
		// colorList = new LinkedList<Color>();
		// colorList.add(new Color(242, 130, 38));// Orange
		// colorList.add(new Color(73, 12, 139));// pourpre foncé
		// colorList.add(new Color(133, 242, 38));// Vert jeune pousse
		// colorList.add(new Color(114, 159, 220));// Bleu clair
		// colorList.add(new Color(208, 38, 242));// rose clair
		// colorList.add(new Color(12, 52, 139));// Bleu marrine
		// colorList.add(Color.cyan);
		// colorList.add(new Color(139, 69, 12));// marront
		// colorList.add(new Color(12, 128, 139));// Bleu turquoise
		// colorList.add(new Color(254, 170, 52));// Orange pastel
		// colorList.add(new Color(189, 107, 247));// violet pastel
		// colorList.add(new Color(139, 12, 65));// bordeau
		// colorList.add(new Color(242, 239, 38));// Jaune
		// colorList.add(new Color(141, 207, 80));// Vert clair
		// colorList.add(new Color(137, 12, 139));// violet foncé
		// colorList.add(new Color(52, 52, 254));// Bleu foncé
		// colorList.add(new Color(80, 139, 12));// Vert foncé
		// colorList.add(new Color(38, 116, 224));// Bleu
		// colorList.add(new Color(137, 38, 242));// pourpre
		/***************************************************************************************************************
		 * Création de l'image
		 */
		buffer = new GraphicsViewPort();
		/***************************************************************************************************************
		 * Listener de déplacement de la sourirs
		 */
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int dx, dy;
				dx = e.getX() - xLastPointeur;
				dy = e.getY() - yLastPointeur;
				if (buffer.getWidthImage() - buffer.getWidthViewPort() < 0)
					dx = -dx;
				if (buffer.getHeigthImage() - buffer.getHeigthViewPort() < 0)
					dy = -dy;
				buffer.move(dx, dy);
				xLastPointeur = e.getX();
				yLastPointeur = e.getY();
				me.repaint();
			}
		});
		/***************************************************************************************************************
		 * Listener de clic de la souris
		 */
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				xLastPointeur = e.getX();
				yLastPointeur = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		/***************************************************************************************************************
		 * Listener de molette souris
		 */
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0)
					buffer.increasScallImg(1.05F);
				else if (e.getWheelRotation() > 0)
					buffer.decreasScallImg(1.05F);
				else
					return;
				buffer.move(0, 0);
				repaint();
			}
		});
		/***************************************************************************************************************
		 * Listener de clavier
		 */
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// int tmp;
				int keyCode = e.getKeyCode();
				if (e.getKeyChar() == '+') {
					keyCode = KeyEvent.VK_ADD;
				} else if (e.getKeyChar() == '-') {
					keyCode = KeyEvent.VK_MINUS;
				}
				switch (keyCode) {
				case KeyEvent.VK_ADD:
					buffer.increasScallImg(1.05F);
					break;
				case KeyEvent.VK_MINUS:
					buffer.decreasScallImg(1.05F);
					break;
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
					buffer.move(0, getWidth() >> 5);
					break;
				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
					buffer.move(0, -getWidth() >> 5);
					break;
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					buffer.move(getWidth() >> 5, 0);
					break;
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					buffer.move(-getWidth() >> 5, 0);
					break;
				default:
					return;
				}
				buffer.move(0, 0);
				repaint();
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
	protected void actionToDoWhenChangeStateIsClicked() {
		if (!affichageDroite) {
			affichageDroite = true;
			father.setConfig(IhmReceivingStates.GRAPHIC_MODE.toString(), "true");
			father.setCurrentState(IhmReceivingStates.GRAPHIC_MODE.mergeState(currentState));
			buffer.move(1, 0);
			buffer.move(-1, 0);
			repaint();
		} else {
			affichageDroite = false;
			buffer.move(1, 0);
			buffer.move(-1, 0);
			father.setConfig("GRAPHIC_OR_ARRAY_MODE", IhmReceivingStates.ARRAY_MODE.toString());
			father.setConfig(IhmReceivingStates.GRAPHIC_MODE.toString(), "false");
			father.setCurrentState(IhmReceivingStates.ARRAY_MODE.mergeState(currentState));
		}
	}

	@Override
	protected String getMessageChangeState() {
		if (!affichageDroite) {
			return father.lg("GoToGraphicModeSecondDisplay");
		} else {
			return father.lg("GoToArrayMode");
		}
	}

	@Override
	public void paint(Graphics g) {
		buildImage();
		if (firstRepaint) {
			if (buffer.getHeigthImage() < buffer.getHeigthViewPort()) {
				buffer.increasScallImg(buffer.getWidthViewPort() / buffer.getWidthImage());
				buildImage();
			}
			buffer.move(0, -(buffer.getHeigthViewPort() - buffer.getHeigthImage() >> 1));
			buffer.move(-(buffer.getWidthViewPort() - buffer.getWidthImage() >> 1), 0);
			firstRepaint = false;
			buildImage();
		}
		if (shouldDoubleRepaint) {
			buildImage();
			shouldDoubleRepaint = false;
		}
		super.paint(buffer.getBuffer());
		g.drawImage(buffer.getImage(), 0, 0, null);
		buffer.hasBeenDrawn();
	}

	public void buildImage() {
		// on définit les tailles étalons, et on les met à l'échelle
		// sizeLarge = (int) (44 * scallImg);
		// sizeQuadLarge = (int) (176 * scallImg);
		// sizeDemiLine = (int) (12 * scallImg);
		sizeLarge = (int) (father.getSizeAdapteur().getSizeLargeFont() * 4 * buffer.getScallImg());
		sizeQuadLarge = sizeLarge << 2;
		int sizeDemiLarge = sizeLarge >> 1;
		int sizeQuartLarge = sizeLarge >> 2;
		// sizeQuadLarge = sizeLarge *4;
		sizeDemiLine = (int) (father.getSizeAdapteur().getSizeSmallFont() * 3 * buffer.getScallImg()) >> 1;
		sizeDemiLine = (sizeDemiLine > 0) ? sizeDemiLine : 1;
		sizeLine = (int) (father.getSizeAdapteur().getSizeLine() * buffer.getScallImg() * 2.5F);
		sizeLine = sizeLine < 1 ? 1 : sizeLine;
		// if (sizeLine < 1)
		// sizeLine = 1;

		/***************************************************************************************************************
		 * On regarde si la taille de l'image a changé (via un zoom par exemple) et on reconstruit l'image.
		 */
		if ((currentQuality != PanelDoubleBufferingSoftwear.getQuality()) || (buffer.getWidthViewPort() != getWidth())
				|| (buffer.getHeigthViewPort() != getHeight())) {
			buffer.setSizeViewPort(getWidth(), getHeight());
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
		}
		if ((buffer.getWidthImage() != sizeQuadLarge * 5)
				|| (buffer.getHeigthImage() != travel.getTotalTime() * sizeQuartLarge)) {
			buffer.setSizeImage(sizeQuadLarge * 5, travel.getTotalTime() * sizeQuartLarge);
		} else if (!buffer.isNeededRepaint()) {
			return;
		}

		SectionOfTravel section = null;
		Iterator<SectionOfTravel> iterTravel;

		int orientation = 0;
		int idToModify;
		int idToKeep;
		int length;
		int hypo;
		boolean firstPasseDone = false;
		Point center = new Point();
		// Iterator<Color> iterColor = colorList.iterator();
		int heightImageDrawn = buffer.getY();
		polygon.reset();
		// on définit le début du dessin
		center.setLocation(sizeDemiLarge + sizeDemiLine + buffer.getX(), sizeDemiLarge + sizeDemiLine + buffer.getY());
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(0, 0);
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(center.x, center.y);

		// System.out.println("\n\n\n\n\n\n\n\n");
		// si le dessin bien au dessus du bas de l'image
		buffer.setFont(new Font("AdaptedSmallFont", Font.PLAIN,
				(int) (father.getSizeAdapteur().getSizeSmallFont() * 4 * buffer.getScallImg())));
		buffer.setColor(father.getSkin().getColorInside());
		buffer.fillRect(0, 0, getWidth(), getHeight());
		if (heightImageDrawn >= -buffer.getHeigthViewPort()) {
			drawDelayedOval(buffer, center.x - sizeDemiLarge - 1, center.y - sizeDemiLarge, sizeLarge, sizeLarge);
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawLine(center.x + (affichageDroite ? sizeQuadLarge : 0), center.y, center.x
					+ (affichageDroite ? sizeQuadLarge : 0) + (sizeLarge << 1) + sizeLarge, heightImageDrawn
					+ sizeDemiLine + sizeQuartLarge);
			heightImageDrawn = sizeDemiLine
					+ drawInformationsStation(buffer, center.x + (affichageDroite ? sizeQuadLarge : 0)
							+ (sizeLarge << 1) + sizeLarge, heightImageDrawn + sizeDemiLine, travel.getOrigineStation()
							.getServices(), travel.getOrigine(), travel.getEntryCost(), 0);
		}
		// in parcout les étapes du trajet
		iterTravel = travel.getTravelDone();
		do {
			while (iterTravel.hasNext()) {
				section = iterTravel.next();
				if (section.getTimeSection() < 16
						&& (father.getSizeAdapteur().getSizeSmallFont() < 12 || affichageDroite))
					length = (sizeQuartLarge << 4);
				else if (section.getTimeSection() < 8)
					length = (sizeQuartLarge << 3);
				else if (section.getTimeSection() > 64)
					length = (sizeQuartLarge << 6);
				else
					length = section.getTimeSection() * sizeQuartLarge;
				if (orientation % 2 == 0) {
					idToKeep = 2;
					idToModify = 0;
					if (affichageDroite)
						for (int i = 0; i < 4; i++)
							polygon.xpoints[i] += sizeQuadLarge;
				} else {
					idToKeep = 0;
					idToModify = 2;
					if (affichageDroite)
						for (int i = 0; i < 4; i++)
							polygon.xpoints[i] -= sizeQuadLarge;
				}
				center.setLocation(polygon.xpoints[idToKeep + 1] + polygon.xpoints[idToKeep] >> 1,
						polygon.ypoints[idToKeep + 1] + polygon.ypoints[idToKeep] >> 1);

				if (affichageDroite) {
					buffer.setColor(father.getSkin().getColorSubAreaInside());
					buffer.fillRect(center.x + ((orientation % 2 == 0) ? -sizeQuadLarge : 0), center.y - sizeDemiLine,
							sizeQuadLarge, sizeDemiLine << 1);
					drawDelayedOval(buffer, center.x - sizeDemiLarge, center.y - sizeDemiLarge, sizeLarge, sizeLarge);
				}

				if (firstPasseDone)
					buffer.setColor(father.getNetworkColorManager().getColor(section.getRoute()));
				else
					buffer.setColor(father.getSkin().getColorSubAreaInside());

				polygon.xpoints[idToKeep] = center.x + 1;
				polygon.ypoints[idToKeep] = center.y;
				polygon.xpoints[idToKeep + 1] = center.x + 1;
				polygon.ypoints[idToKeep + 1] = center.y;

				hypo = sizeQuadLarge * sizeQuadLarge;
				hypo += length * length;
				hypo = (int) Math.sqrt(hypo);
				float cos, sin;

				if (affichageDroite) {
					polygon.xpoints[idToKeep] -= sizeDemiLine;
					polygon.xpoints[idToKeep + 1] += sizeDemiLine;
					switch (orientation % 2) {
					case 0:
						polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1] + 0;
						polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
						polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep] + 0;
						polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
						center.setLocation(center.x, center.y + length);
						break;
					case 1:
						polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1] - 0;
						polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
						polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep] - 0;
						polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
						center.setLocation(center.x, center.y + length);
						break;
					}
				} else {
					switch (orientation % 3) {
					case 0:
						polygon.xpoints[idToKeep] += -sizeDemiLine /* sin */;
						// polygon.ypoints[idToKeep] += sizeDemiLine * cos;
						polygon.xpoints[idToKeep + 1] += sizeDemiLine /* sin */;
						// polygon.ypoints[idToKeep + 1] += sizeDemiLine * cos;
						break;
					case 1:
						cos = (float) sizeQuadLarge / (float) hypo;
						sin = (float) length / (float) hypo;
						polygon.xpoints[idToKeep] += -sizeDemiLine * sin;
						polygon.ypoints[idToKeep] += sizeDemiLine * cos;
						polygon.xpoints[idToKeep + 1] += sizeDemiLine * sin;
						polygon.ypoints[idToKeep + 1] += -sizeDemiLine * cos;
						break;
					case 2:
						cos = -(float) sizeQuadLarge / (float) hypo;
						sin = (float) length / (float) hypo;
						polygon.xpoints[idToKeep] += -sizeDemiLine * sin;
						polygon.ypoints[idToKeep] += sizeDemiLine * cos;
						polygon.xpoints[idToKeep + 1] += sizeDemiLine * sin;
						polygon.ypoints[idToKeep + 1] += -sizeDemiLine * cos;
						break;
					}

					switch (orientation % 3) {
					case 0:
						polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1];
						polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
						polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep];
						polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
						center.setLocation(center.x, center.y + length);
						break;
					case 1:
						polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1] + sizeQuadLarge;
						polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
						polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep] + sizeQuadLarge;
						polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
						center.setLocation(center.x + sizeQuadLarge, center.y + length);
						break;
					case 2:
						polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1] - sizeQuadLarge;
						polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
						polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep] - sizeQuadLarge;
						polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
						center.setLocation(center.x - sizeQuadLarge, center.y + length);
						break;
					}
				}
				if (!firstPasseDone && !iterTravel.hasNext() && putStationUp) {
					putStationUp = false;
					int val;
					if ((val = polygon.ypoints[idToModify + 1] - sizeLarge) > ((buffer.getHeigthViewPort() >> 1) + (buffer
							.getHeigthViewPort() >> 2))) {
						new SlowMove(-val);
						// else {
						// buffer.move(0, -val);
						// shouldDoubleRepaint=true;
						// // drawDelayedOval(null, 0, 0, 0, 0);
						// repaint();
						// }
						// TODO
					}
				}

				// buffer.drawLine(0, , 1000, polygon.ypoints[idToModify + 1]-sizeLarge);
				// on vérifie que l'on veut dessiner dans la zone. on a modifier les limites car le dessin se fait par
				// groupemement assez séparé.
				if (polygon.ypoints[0] > buffer.getHeigthViewPort() + sizeLarge
						&& polygon.ypoints[2] > buffer.getHeigthViewPort() + sizeLarge) {
					// au dela de la zone
					if (!putStationUp)
						while (iterTravel.hasNext()) {
							iterTravel.next();
						}
				} else if (polygon.ypoints[0] < -sizeLarge && polygon.ypoints[2] < -sizeLarge) {
					// buffer.setColor(iterColor.next());
				} else {
					// la zone
					buffer.fillPolygon(polygon);
					if ((length = section.getStationInSection()) > 0) {
						float x = ((polygon.xpoints[idToModify] + polygon.xpoints[idToModify + 1]) >> 1)
								- ((polygon.xpoints[idToKeep] + polygon.xpoints[idToKeep + 1]) >> 1);
						float y = ((polygon.ypoints[idToModify] + polygon.ypoints[idToModify + 1]) >> 1)
								- ((polygon.ypoints[idToKeep] + polygon.ypoints[idToKeep + 1]) >> 1);
						x /= length;
						y /= length;
						for (int i = length - 1; i > 0; i--) {
							buffer.setColor(father.getSkin().getColorInside());
							buffer.fillOval(center.x - (int) (x * i) - (sizeQuartLarge >> 1), center.y - (int) (y * i)
									- (sizeQuartLarge >> 1), sizeQuartLarge + 1, sizeQuartLarge + 1);
							if (firstPasseDone)
								buffer.setColor(father.getSkin().getColorLine());
							buffer.drawOval(center.x - (int) (x * i) - (sizeQuartLarge >> 1), center.y - (int) (y * i)
									- (sizeQuartLarge >> 1), sizeQuartLarge, sizeQuartLarge);
						}
					}
					if (firstPasseDone)
						buffer.setColor(father.getNetworkColorManager().getColor(section.getRoute()));
					else
						buffer.setColor(father.getSkin().getColorSubAreaInside());

					drawInformationsRoute(buffer, (polygon.xpoints[0] + polygon.xpoints[2]) >> 1,
							(polygon.ypoints[0] + polygon.ypoints[2]) >> 1, section);
					drawDelayedOval(buffer, center.x - sizeDemiLarge, center.y - sizeDemiLarge, sizeLarge, sizeLarge);
				}
				buffer.setColor(father.getSkin().getColorLetter());

				if (heightImageDrawn > (center.y - sizeDemiLarge)) {
					buffer.drawLine(center.x + ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0),
							center.y, center.x + ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0)
									+ (sizeLarge << 1) + sizeLarge, heightImageDrawn + sizeQuartLarge);
					heightImageDrawn = sizeDemiLine
							+ drawInformationsStation(buffer, center.x
									+ ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0)
									+ (sizeLarge << 1) + sizeLarge, heightImageDrawn, section);
				} else {
					buffer.drawLine(center.x + ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0),
							center.y, center.x + ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0)
									+ (sizeLarge << 1) + sizeLarge, center.y - sizeQuartLarge);
					heightImageDrawn = sizeDemiLine
							+ drawInformationsStation(buffer, center.x
									+ ((affichageDroite && orientation % 2 == 1) ? sizeQuadLarge : 0)
									+ (sizeLarge << 1) + sizeLarge, center.y - sizeDemiLarge, section);
				}
				// System.out.println(section.getNameChangement());
				orientation = (++orientation % 6);
			}
			if (firstPasseDone)
				break;
			firstPasseDone = true;
			iterTravel = travel.getTravelToDo();
		} while (true);
		if (affichageDroite && orientation % 2 == 0) {
			buffer.setColor(father.getSkin().getColorSubAreaInside());
			buffer.fillRect(center.x, center.y - sizeDemiLine, sizeQuadLarge, sizeDemiLine << 1);
			drawDelayedOval(buffer, center.x + sizeQuadLarge - sizeDemiLarge, center.y - sizeDemiLarge, sizeLarge,
					sizeLarge);
		}
		drawDelayedOval(null, 0, 0, 0, 0);
	}

	/**
	 * Fonction permettant de dessiner le cadre décrivant le station du changement. Il retourne ensuite l'abscisse
	 * jusqu'a laquelle on a dessiné.
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner le rectangle.
	 * @param x
	 *            l'abscisse.
	 * @param y
	 *            l'ordonné.
	 * @param section
	 *            la section que l'on achève.
	 * @return l'abscisse où l'on a finit de dessiner.
	 */
	protected void drawInformationsRoute(GraphicsViewPort g, int xRec, int yRec, SectionOfTravel section) {
		// buffer.drawLine(xRec, yRec - 20, xRec, yRec + 20);
		// buffer.drawLine(xRec - 20, yRec, xRec + 20, yRec);
		String[] words = new String[] {
				section.getNameRoute(),
				decomposeMinutesIntoHourMinutes(section.getTimeSection(), father.lg("LetterForHour"), father
						.lg("LetterForMinute")) };
		int i = PanelDoubleBufferingSoftwear.getWidthString(words[0], g.getImage().getGraphics(), g.getFont()) >> 1;
		int j = PanelDoubleBufferingSoftwear.getHeightString(words[0], g.getImage().getGraphics(), g.getFont()) >> 1;
		int[] xs = new int[2];
		int[] ys = new int[2];
		Color colorActu = g.getColor();
		xs[0] = xRec - i;
		ys[0] = yRec + j;
		xs[1] = xRec + i + (sizeDemiLine << 1);
		ys[1] = ys[0];
		g.setColor(Color.red);
		// g.drawLine(xs[0], ys[0], 2 * xRec - xs[0], 2 * yRec - ys[0]);
		// g.drawLine(xs[0], ys[0], (xRec << 1) - xs[0], (yRec << 1) - ys[0]);
		// g.drawRect(xs[0], ys[0] - 2 * j, 2 * i, 2 * j);
		// g.drawRect(xs[0], ys[0] - (j << 1), i << 1, j << 1);
		if (i < j)
			i = j;
		i = (int) (i * 1.3F);
		g.setColor(father.getSkin().getColorLetter());
		g.fillOval(xRec - i - sizeLine, yRec - i - sizeLine, (i << 1) + (sizeLine << 1), (i << 1) + (sizeLine << 1));
		g.setColor(colorActu);
		g.fillOval(xRec - i, yRec - i, i << 1, i << 1);
		g.setColor(father.getSkin().getColorLetter());
		// xRec += sizeDemiLine * 4;
		// yRec -= sizeDemiLine * 3;
		// String[] words = new String[] {
		// " " + section.getNameRoute() + " :",
		// decomposeMinutesIntoHourMinutes(section.getTimeSection(), father.lg("LetterForHour"), father
		// .lg("LetterForMinute")) };
		// int[] xs = new int[2];
		// int[] ys = new int[2];
		// int xEnder;
		// xs[0] = xRec + (sizeDemiLine >>1 );
		// ys[0] = yRec + (sizeDemiLine >>1 ) + PanelDoubleBufferingSoftwear.getHeightString(words[0],
		// g.getImage().getGraphics(), g.getFont());
		// xs[1] = xs[0] + PanelDoubleBufferingSoftwear.getWidthString(words[0], g.getImage().getGraphics(),
		// g.getFont()) + (sizeDemiLine >>1 );
		// ys[1] = ys[0];
		// xEnder = xs[1] + PanelDoubleBufferingSoftwear.getWidthString(words[1], g.getImage().getGraphics(),
		// g.getFont()) + sizeDemiLine;
		// g.setColor(father.getSkin().getColorSubAreaInside());
		// g.fillRect(xRec, yRec, xEnder - xRec, ys[1] + sizeDemiLine - yRec);
		// // g.fillRoundRect(xs[0], yRec + (sizeDemiLine >>1 ), xs[1] - xs[0], xs[1] - xs[0],);
		// g.drawRect(xRec, yRec, xEnder - xRec, ys[1] + sizeDemiLine - yRec);
		for (i = 0; i < 2; i++)
			g.drawString(words[i], xs[i], ys[i]);
	}

	/**
	 * Fonction permettant de dessiner le cadre décrivant le station du changement. Il retourne ensuite l'abscisse
	 * jusqu'a laquelle on a dessiné.
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner le rectangle.
	 * @param x
	 *            l'abscisse.
	 * @param y
	 *            l'ordonné.
	 * @param section
	 *            la section que l'on achève.
	 * @return l'abscisse où l'on a finit de dessiner.
	 */
	protected int drawInformationsStation(GraphicsViewPort g, int xRec, int yRec, SectionOfTravel section) {
		return drawInformationsStation(g, xRec, yRec, section.getEnddingChangementServices(), section
				.getNameChangement(), section.getEnddingChangementCost(), section.getEnddingChangementTime());
	}

	/**
	 * Fonction permettant de dessiner le cadre décrivant le station du changement. Il retourne ensuite l'abscisse
	 * jusqu'a laquelle on a dessiné.
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner le rectangle.
	 * @param x
	 *            l'abscisse.
	 * @param y
	 *            l'ordonné.
	 * @param section
	 *            la section que l'on achève.
	 * @return l'abscisse où l'on a finit de dessiner.
	 */
	protected int drawInformationsStation(GraphicsViewPort g, int xRec, int yRec, Iterator<Service> itService,
			String nameChangement, float costChangement, int timeChangement) {
		int nextX, i, xEnder;
		String[] words = new String[5];
		int[] xs = new int[5];
		int[] ys = new int[5];
		int xService, yService;
		Service service;
		int taille = (itService.hasNext()) ? (int) (g.getFont().getSize() * 1.3F) : 0;

		g.setColor(father.getSkin().getColorLetter());
		// le titre
		words[0] = nameChangement;
		xs[0] = (xService = xRec + (sizeDemiLine << 1));
		ys[0] = yRec + (sizeDemiLine >> 1)
				+ PanelDoubleBufferingSoftwear.getHeightString("A", g.getImage().getGraphics(), g.getFont());
		nextX = 0;

		// les services
		yService = ys[0] + (sizeDemiLine >> 1);

		// colone gauche
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)
			words[1] = father.lg("Cost") + " : ";
		words[2] = father.lg("Time") + " : ";

		xs[1] = xs[0];
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)
			ys[1] = yService + (sizeDemiLine >> 1) + taille
					+ PanelDoubleBufferingSoftwear.getHeightString(words[1], g.getImage().getGraphics(), g.getFont());
		else
			ys[1] = yService + taille;

		xs[2] = xs[1];
		ys[2] = ys[1] + (sizeDemiLine >> 1)
				+ PanelDoubleBufferingSoftwear.getHeightString(words[2], g.getImage().getGraphics(), g.getFont());
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)
			nextX = xs[2]
					+ PanelDoubleBufferingSoftwear.getWidthString(words[1], g.getImage().getGraphics(), g.getFont())
					+ (sizeDemiLine >> 1);
		i = xs[2] + PanelDoubleBufferingSoftwear.getWidthString(words[2], g.getImage().getGraphics(), g.getFont())
				+ (sizeDemiLine >> 1);
		if (i > nextX)
			xs[3] = i;
		else
			xs[3] = nextX;

		// colonne droite
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)
			if (costChangement == 0)
				words[3] = father.lg("Free");
			else
				words[3] = costChangement + " " + father.lg("Money");
		words[4] = decomposeMinutesIntoHourMinutes(timeChangement, father.lg("LetterForHour"), father
				.lg("LetterForMinute"));

		// xs[3]=x;
		// if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
		// ys[3] = ys[0] + (sizeDemiLine >> 1)
		// + PanelDoubleBufferingSoftwear.getHeightString(words[3], g.getImage().getGraphics(), g.getFont());
		// else
		// ys[3] = ys[0];
		ys[3] = ys[1];
		xs[4] = xs[3];
		ys[4] = ys[3] + (sizeDemiLine >> 1)
				+ PanelDoubleBufferingSoftwear.getHeightString(words[4], g.getImage().getGraphics(), g.getFont());
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)
			nextX = xs[4]
					+ PanelDoubleBufferingSoftwear.getWidthString(words[3], g.getImage().getGraphics(), g.getFont())
					+ (sizeDemiLine << 1);
		i = xs[4] + PanelDoubleBufferingSoftwear.getWidthString(words[4], g.getImage().getGraphics(), g.getFont())
				+ (sizeDemiLine << 1);
		if (i > nextX)
			nextX = i;
		i = xRec + (sizeDemiLine << 1)
				+ PanelDoubleBufferingSoftwear.getWidthString(nameChangement, g.getImage().getGraphics(), g.getFont())
				+ (sizeDemiLine << 1);
		if (i > nextX)
			xEnder = i;
		else
			xEnder = nextX;
		if (xRec <= buffer.getWidthViewPort()) {
			// dessin de rectangle avec grandes bordures
			// ligne de 1 pxl
			// g.setColor(father.getSkin().getColorSubAreaInside());
			// g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			// g.setColor(father.getSkin().getColorLetter());
			// g.drawRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);

			// ligne de sizeLine avec 2 fillRect (tto much time)
			// g.setColor(father.getSkin().getColorLetter());
			// g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			// g.setColor(father.getSkin().getColorSubAreaInside());
			// g.fillRect(xRec + sizeLine, yRec + sizeLine, xEnder - xRec - (sizeLine <<1), ys[4] + sizeDemiLine - yRec
			// -( sizeLine <<1));
			// g.setColor(father.getSkin().getColorLetter());

			// Ligne de sizeLine avec 1 fill et dessin des lignes.
			g.setColor(father.getSkin().getColorSubAreaInside());
			g.fillRect(xRec + sizeLine, yRec + sizeLine, xEnder - xRec - sizeLine, ys[4] + sizeDemiLine - yRec
					- sizeLine);
			g.setColor(father.getSkin().getColorLetter());
			for (i = 0; i < sizeLine; i++) {
				g.drawLine(xRec + i, yRec, xRec + i, ys[4] + sizeDemiLine);
				g.drawLine(xEnder - i, yRec, xEnder - i, ys[4] + sizeDemiLine);
				g.drawLine(xRec, yRec + i, xEnder, yRec + i);
				g.drawLine(xRec, ys[4] - i + sizeDemiLine, xEnder, ys[4] - i + sizeDemiLine);
			}
			// g.setColor(Color.red);
			// g.drawLine(xRec + (sizeLine <<1), yRec + (sizeLine <<1), xEnder -( sizeLine <<1), ys[4] + sizeDemiLine -
			// (sizeLine <<1));
			for (i = 0; i < 5; i++)
				if (words[i] != null)
					g.drawString(words[i], xs[i], ys[i]);

			while (itService.hasNext()) {
				service = itService.next();
				words[0] = service.getName().substring(0, 1);
				g.setColor(father.getNetworkColorManager().getColor(service));
				// sttt.setBounds(xService - 1, yService - 1, taille + 2, taille + 2);
				g.fillOval(xService - 1, yService - 1, taille + 2, taille + 2);
				g.setColor(father.getSkin().getColorLetter());
				g.drawOval(xService - 1, yService - 1, taille + 2, taille + 2);
				g.drawString(words[0], xService + (taille >> 1)
						- (PanelDoubleBufferingSoftwear.getWidthString(words[0], g.getBuffer()) >> 1), yService
						+ (taille >> 1) + (PanelDoubleBufferingSoftwear.getHeightString(words[0], g.getBuffer()) >> 1));
				xService += taille + sizeDemiLine;
			}
		}
		g.extendsHeight(ys[4] + (sizeDemiLine << 1) - buffer.getY());
		g.extendsWidth(xEnder - buffer.getX() + sizeDemiLine);
		return ys[4] + sizeDemiLine;
	}

	/**
	 * Procédure permettant de dessiner un oval, avec un appelle de retard : lorsqu'on appelle la fonction, l'oval
	 * effectivement dessiné est celui du prédédent appelle.
	 * 
	 * @param g
	 *            le Graphics où l'on va dessiner l'oval, s'il est null, on dessine tout de même le précédent oval
	 * @param x
	 *            l'abscisse de l'oval
	 * @param y
	 *            l'ordonnée de l'oval
	 * @param width
	 *            la largueur de l'oval
	 * @param height
	 *            la hauteur de l'oval
	 */
	protected void drawDelayedOval(GraphicsViewPort g, int x, int y, int width, int height) {
		if (ovalToDrawDelayed.g != null) {
			// ovalToDrawDelayed.g.setColor(father.getSkin().getColorInside());
			// ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
			// ovalToDrawDelayed.height);
			// ovalToDrawDelayed.g.setColor(father.getSkin().getColorLetter());
			// ovalToDrawDelayed.g.drawOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
			// ovalToDrawDelayed.height);
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorLetter());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
					ovalToDrawDelayed.height);
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorInside());
			// ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + father.getSizeAdapteur().getSizeLine(),
			// ovalToDrawDelayed.y + father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.width - 2
			// * father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.height - 2
			// * father.getSizeAdapteur().getSizeLine());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + sizeLine, ovalToDrawDelayed.y + sizeLine,
					ovalToDrawDelayed.width - (sizeLine << 1), ovalToDrawDelayed.height - (sizeLine << 1));
			// System.out.println(buffer.getScallImg()*5);
		}
		ovalToDrawDelayed.g = g;
		ovalToDrawDelayed.x = x;
		ovalToDrawDelayed.y = y;
		ovalToDrawDelayed.width = width;
		ovalToDrawDelayed.height = height;
	}

	/**
	 * Classe équivalent à un structure en C/C++, on permet de stocker de façon regroupé plusieurs variable relative au
	 * dessin d'un futur oval
	 * 
	 * @author iGo
	 * 
	 */
	protected class OvalToDraw {
		public GraphicsViewPort g;
		public int x;
		public int y;
		public int width;
		public int height;

		public OvalToDraw(GraphicsViewPort g, int x, int y, int width, int height) {
			super();
			this.g = g;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * 
	 * @author "iGo"
	 * 
	 */
	public class GraphicsViewPort {

		/**
		 * Le buffer de l'image
		 */
		protected Graphics2D buffer = null;

		/**
		 * L'image en elle même
		 */
		protected Image image = null;

		/**
		 * l'abscisse
		 */
		protected int x;

		/**
		 * L'ordonnée
		 */
		protected int y;

		/**
		 * La hauteur
		 */
		protected int heightViewPort;

		/**
		 * La largueur
		 */
		protected int widthViewPort;

		/**
		 * La hauteur du dessin
		 */
		protected int heightImage;

		/**
		 * La largueur du dessin
		 */
		protected int widthImage;

		/**
		 * Si un nouvel affchage de l'image est nécessaire
		 */
		protected boolean neededRepaint;
		/**
		 * L'échelle de l'image
		 */
		protected float scallImg = 0.2F;

		public int getHeigthImage() {
			return heightImage;
		}

		public int getWidthImage() {
			return widthImage;
		}

		public float getScallImg() {
			return scallImg;
		}

		public GraphicsViewPort() {
			super();
			this.neededRepaint = true;
		}

		/**
		 * retourne un boolean indiquant si un réaffochage est nécessaire.
		 * 
		 * @return true si on doit de nouveau déssiner l'image.
		 */
		public boolean isNeededRepaint() {
			return neededRepaint;
		}

		/**
		 * Modifie le coefficient de zoom de la valeur indiquée : si le coef est de 2 et que vous appellez
		 * increaseScallImg(3) le coefficient sera ensuite à 6;
		 * 
		 * @param coef
		 *            le coefficient par lequel le coefficient actuelle va être multiplié
		 */
		public void increasScallImg(float coef) {
			scallImg *= coef;
			neededRepaint = true;
		}

		/**
		 * Modifie le coefficient de zoom de la valeur indiquée : si le coef est de 6 et que vous appellez
		 * decreaseScallImg(3) le coefficient sera ensuite à 2;
		 * 
		 * @param coef
		 *            le coefficient par lequel le coefficient actuelle va être divisé
		 */
		public void decreasScallImg(float coef) {
			if (((widthViewPort - widthImage) > 0) && ((heightViewPort - heightImage) > 0))
				return;
			scallImg /= coef;
			neededRepaint = true;
		}

		public Graphics getBuffer() {
			return buffer;
		}

		public Image getImage() {
			return image;
		}

		public void move(int dx, int dy) {
			// neededRepaint = true;
			int tmp = widthViewPort - widthImage;
			int xOrg = x, yOrg = y;
			if (tmp > 0) {
				x -= dx;
				if (x < 0)
					x = 0;
				if (x > tmp)
					x = tmp;
			} else {
				x += dx;
				if (x < tmp)
					x = tmp;
				if (x > 0)
					x = 0;
			}
			tmp = heightViewPort - heightImage;
			if (tmp > 0) {
				y -= dy;
				if (y < 0)
					y = 0;
				if (y > tmp)
					y = tmp;
			} else {
				y += dy;
				if (y < tmp)
					y = tmp;
				if (y > 0)
					y = 0;
			}
			neededRepaint |= ((xOrg != x) || (yOrg != y));
		}

		public void setSizeViewPort(int width, int height) {
			neededRepaint = true;
			if (width < 10)
				width = 10;
			if (height < 10)
				height = 10;
			this.heightViewPort = height;
			this.widthViewPort = width;
			this.image = createImage(this.widthViewPort, this.heightViewPort);
			this.buffer = (Graphics2D) this.image.getGraphics();
			this.buffer.setBackground(father.getSkin().getColorInside());
			graphicsTunning(this.buffer);
		}

		public void setSizeImage(int width, int height) {
			if (width < 10)
				width = 10;
			if (height < 10)
				height = 10;
			neededRepaint = true;
			this.heightImage = height;
			this.widthImage = width;
		}

		public int getHeigthViewPort() {
			return heightViewPort;
		}

		public int getWidthViewPort() {
			return widthViewPort;
		}

		public void fillPolygon(Polygon polygon) {
			buffer.fillPolygon(polygon);
		}

		public void setColor(Color color) {
			buffer.setColor(color);
		}

		public Color getColor() {
			return buffer.getColor();
		}

		public void drawPolygon(Polygon polygon) {
			buffer.drawPolygon(polygon);
		}

		public void setFont(Font font) {
			buffer.setFont(font);
		}

		public Font getFont() {
			return buffer.getFont();
		}

		public void drawLine(int x1, int y1, int x2, int y2) {
			buffer.drawLine(x1, y1, x2, y2);
		}

		public void fillRect(int x, int y, int width, int height) {
			buffer.fillRect(x, y, width, height);
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void drawRect(int x, int y, int width, int height) {
			buffer.drawRect(x, y, width, height);
		}

		public void drawString(String str, int x, int y) {
			buffer.drawString(str, x, y);
		}

		public void fillOval(int x, int y, int width, int height) {
			buffer.fillOval(x, y, width, height);
		}

		public void drawOval(int x, int y, int width, int height) {
			buffer.drawOval(x, y, width, height);
		}

		public void clearRect(int x, int y, int width, int height) {
			buffer.clearRect(x, y, width, height);
		}

		public void extendsWidth(int width) {
			if (widthImage < width)
				widthImage = width;
		}

		public void extendsHeight(int height) {
			if (heightImage < height)
				heightImage = height;

		}

		public void hasBeenDrawn() {
			this.neededRepaint = false;
		}

		public void hasBeenChanged() {
			this.neededRepaint = true;
		}
	}

	@Override
	protected void nextStationDone() {
		putStationUp = true;
		buffer.hasBeenChanged();
	}

	protected class SlowMove extends Thread {

		public SlowMove(int deroulement) {
			super();
			this.deroulement = deroulement;
			this.start();
		}

		int deroulement;

		@Override
		public void run() {
			int delta = 0;
			switch (PanelDoubleBufferingSoftwear.getQuality()) {
			case TEXT_ANTI_ANTIALIASING:
				delta += 20;
			case FULL_ANTI_ANTIALIASING:
				delta += 20;
			case HIGHER_QUALITY:
				delta += 10;
				break;
			default:
				delta = deroulement >> 2;
				if (deroulement < 0)
					delta = -delta;
				break;
			}

			for (int i = 0; i < deroulement; i += delta) {
				try {
					Thread.sleep(delta);
				} catch (InterruptedException e) {
				}
				buffer.move(0, delta);
				repaint();
			}
			for (int i = deroulement; i < 0; i += delta) {
				try {
					Thread.sleep(delta);
				} catch (InterruptedException e) {
				}
				buffer.move(0, -delta);
				repaint();
			}
		}
	}
}