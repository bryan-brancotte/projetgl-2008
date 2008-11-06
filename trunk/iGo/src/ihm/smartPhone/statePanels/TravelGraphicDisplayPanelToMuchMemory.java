package ihm.smartPhone.statePanels;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class TravelGraphicDisplayPanelToMuchMemory extends PanelState {

	protected Dimension preferredSize = new Dimension(10, 10);

	@Override
	public Dimension getPreferredSize() {
		return super.getPreferredSize();
	}

	private static final long serialVersionUID = 1L;

	protected TravelGraphicDisplayPanelToMuchMemory me = this;
	protected TravelForDisplayPanel travel = null;
	protected IhmReceivingStates actualState = IhmReceivingStates.PREVISU_TRAVEL;

	protected Polygon polygon = new Polygon();
	protected OvalToDraw ovalToDrawDelayed = new OvalToDraw(null, 0, 0, 0, 0);
	protected LinkedList<Color> colorList;

	protected float xImg;
	protected float yImg;
	/**
	 * La hauteur de l'image représentant le réseau
	 */
	protected int heigthImage;
	/**
	 * La largueur de l'image représentant le réseau
	 */
	protected int widthImage;
	/**
	 * L'échelle de l'image
	 */
	protected float scallImg = 0.25F;
	/**
	 * La dernière abscisse du pointeur
	 */
	protected int xLastPointeur;
	/**
	 * La dernière ordonnée du pointeur
	 */
	protected int yLastPointeur;

	protected int sizeDemiLine;
	protected int sizeQuadLarge;
	protected int sizeLarge;

	protected Semaphore renderingClamp = new Semaphore(1);

	public TravelGraphicDisplayPanelToMuchMemory(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar);
		this.travel = travelForDisplayPanel;
		if (travel == null)
			travel = new TravelForTravelPanelExemple();
		colorList = new LinkedList<Color>();
		addColorAndItsLighted(Color.cyan);
		addColorAndItsLighted(Color.magenta);
		addColorAndItsLighted(Color.yellow);
		addColorAndItsLighted(Color.red);
		addColorAndItsLighted(Color.blue);
		addColorAndItsLighted(Color.green);
		addColorAndItsLighted(Color.orange);
		/***************************************************************************************************************
		 * Listener de déplacement de la sourirs
		 */
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int tmp;
				xImg += e.getX() - xLastPointeur;
				yImg += e.getY() - yLastPointeur;
				xLastPointeur = e.getX();
				yLastPointeur = e.getY();

				tmp = (getHeight() - heigthImage);
				if (yImg < tmp) {
					yImg = tmp;
				}
				tmp = (getWidth() - widthImage);
				if (xImg < tmp) {
					xImg = tmp;
				}
				if (xImg > 0) {
					xImg = 0;
				}
				if (yImg > 0) {
					yImg = 0;
				}
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
				if (e.getWheelRotation() > 0)
					scallImg *= 1.05;
				else if ((e.getWheelRotation() < 0) && ((widthImage > getWidth()) || (heigthImage > getHeight())))
					scallImg /= 1.05;
				else
					return;
				repaint();
			}
		});
		/***************************************************************************************************************
		 * Listener de clavier
		 */
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int tmp;
				int keyCode = e.getKeyCode();
				if (e.getKeyChar() == '+') {
					keyCode = KeyEvent.VK_PLUS;
				} else if (e.getKeyChar() == '-') {
					keyCode = KeyEvent.VK_MINUS;
				}
				switch (keyCode) {
				case KeyEvent.VK_PLUS:
					scallImg *= 1.05;
					break;
				case KeyEvent.VK_MINUS:
					scallImg /= 1.05;
					break;
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
					yImg += getWidth() / 20;
					break;
				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
					yImg -= getWidth() / 20;
					tmp = (getHeight() - heigthImage);
					if (yImg < tmp)
						yImg = tmp;
					break;
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					xImg += getWidth() / 20;
					break;
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					xImg -= getWidth() / 20;
					tmp = (getWidth() - widthImage);
					if (xImg < tmp)
						xImg = tmp;
					break;
				default:
					return;
				}
				if (xImg > 0)
					xImg = 0;
				if (yImg > 0)
					yImg = 0;
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

	protected void addColorAndItsLighted(Color org) {
		colorList.add(org);
		int r = org.getRed(), g = org.getGreen(), b = org.getBlue();
		r *= .2;
		if (r > 255)
			r = 255;
		g *= .2;
		if (g > 255)
			g = 255;
		b *= .2;
		if (b > 255)
			b = 255;
		colorList.add(new Color(r, g, b));
	}

	@Override
	public void paint(Graphics g) {
		// Un mutex est placé afin de ne pas lancer plusieur paint en même temps (et gagner du temps). Ce mutex a
		// autrefois éviter des execution inutils, mais ne semble plus être utils désormais. Il est laissé an attendant
		// un optimisation
		if (!renderingClamp.tryAcquire()) {
			System.out.println("acquire OFF");
			return;
		}
		// on demande un reconstruction de l'image
		buildImage();
		// on efface l'écran puis on dessine cette image
		// g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, (int) xImg, (int) yImg, null);
		renderingClamp.release();
	}

	public void buildImage() {
		// on définit les tailles étalons, et on les met à l'échelle
		// sizeLarge = (int) (44 * scallImg);
		// sizeQuadLarge = (int) (176 * scallImg);
		// sizeDemiLine = (int) (12 * scallImg);
		sizeLarge = (int) (father.getSizeAdapteur().getSizeLargeFont() * 4 * scallImg);
		sizeQuadLarge = 4 * sizeLarge;
		sizeDemiLine = (int) (father.getSizeAdapteur().getSizeSmallFont() * 3 / 2 * scallImg);

		/***************************************************************************************************************
		 * On regarde si la taille de l'image a changer (via un zoom par exemple) et on reconstruit l'image.
		 */
		if ((buffer == null) || (image.getWidth(null) != sizeQuadLarge * 5)
				|| (image.getHeight(null) != travel.getTotalTime() * sizeLarge / 4)) {
			if (image != null) {
				xImg += image.getWidth(this) / 2;
				yImg += image.getHeight(this) / 2;
			}
			try {
				image = createImage(sizeQuadLarge * 5, travel.getTotalTime() * sizeLarge / 4);
			} catch (OutOfMemoryError e) {
				scallImg /= 1.05;
				xImg -= image.getWidth(this) / 2;
				yImg -= image.getHeight(this) / 2;
				return;
			}
			if (buffer != null) {
				xImg -= image.getWidth(this) / 2;
				yImg -= image.getHeight(this) / 2;
			}
			buffer = image.getGraphics();
		} else {
			return;
		}

		SectionOfTravel section;
		Iterator<SectionOfTravel> iterTravel = travel.getTravel();

		int orientation = 0;
		int idToModify;
		int idToKeep;
		int length;
		int hypo;
		Point center = new Point();
		Iterator<Color> iterColor = colorList.iterator();
		heigthImage = 0;
		polygon.reset();
		center.setLocation(sizeLarge / 2 + 10, sizeLarge / 2 + 10);
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(0, 0);
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(center.x, center.y);

		buffer.setFont(father.getSizeAdapteur().getSmallFont());

		drawDelayedOval(buffer, center.x - sizeLarge / 2 - 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
		while (iterTravel.hasNext()) {
			if (!iterColor.hasNext())
				iterColor = colorList.iterator();
			section = iterTravel.next();
			buffer.setColor(iterColor.next());
			length = section.getTimeSection() * sizeLarge / 5;
			// System.out.println(section.getTimeSection());
			if (orientation % 2 == 0) {
				idToKeep = 2;
				idToModify = 0;
			} else {
				idToKeep = 0;
				idToModify = 2;
			}
			center.setLocation(polygon.xpoints[idToKeep + 1] / 2 + polygon.xpoints[idToKeep] / 2,
					polygon.ypoints[idToKeep + 1] / 2 + polygon.ypoints[idToKeep] / 2);
			polygon.xpoints[idToKeep] = center.x;
			polygon.ypoints[idToKeep] = center.y;
			polygon.xpoints[idToKeep + 1] = center.x;
			polygon.ypoints[idToKeep + 1] = center.y;
			hypo = sizeQuadLarge * sizeQuadLarge;
			hypo += length * length;
			hypo = (int) Math.sqrt(hypo);
			float cos, sin;

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
			buffer.fillPolygon(polygon);
			buffer.setColor(iterColor.next());
			buffer.drawPolygon(polygon);
			drawInformationsRoute(buffer, (polygon.xpoints[0] + polygon.xpoints[2]) / 2,
					(polygon.ypoints[0] + polygon.ypoints[2]) / 2, section);
			drawDelayedOval(buffer, center.x - sizeLarge / 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
			// System.out.println(heigthImage + " " + (center.y - sizeLarge / 2)
			// + (heigthImage > (center.y - sizeLarge / 2)));
			// buffer.drawLine(0, heigthImage, 200, heigthImage);
			// buffer.drawLine(50, center.y - sizeLarge / 2, 250, center.y - sizeLarge / 2);
			buffer.setColor(father.getSkin().getColorLetter());
			if (heigthImage > (center.y - sizeLarge / 2)) {
				buffer.drawLine(center.x, center.y, center.x + sizeLarge * 2, heigthImage + sizeLarge / 4);
				heigthImage = father.getSizeAdapteur().getSizeSmallFont()
						+ drawInformationsStation(buffer, center.x + sizeLarge * 2, heigthImage, section);
			} else {
				buffer.drawLine(center.x, center.y, center.x + sizeLarge * 2, center.y - sizeLarge / 4);
				heigthImage = father.getSizeAdapteur().getSizeSmallFont()
						+ drawInformationsStation(buffer, center.x + sizeLarge * 2, center.y - sizeLarge / 2, section);
			}
			// System.out.println(section.getNameChangement());
			orientation = (++orientation % 6);
		}

		drawDelayedOval(null, 0, 0, 0, 0);
		/***************************************************************************************************************
		 * Définition de la taille réel de l'image.
		 */
		hypo = center.y + sizeLarge / 2;
		if (hypo > heigthImage)
			heigthImage = hypo + 10;
		heigthImage += 10;
		widthImage = image.getWidth(null);

		hypo = (getHeight() - heigthImage);
		if (yImg < hypo) {
			yImg = hypo;
		}
		hypo = (getWidth() - widthImage);
		if (xImg < hypo) {
			xImg = hypo;
		}
		if (xImg > 0) {
			xImg = 0;
		}
		if (yImg > 0) {
			yImg = 0;
		}
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
	protected void drawInformationsRoute(Graphics g, int xRec, int yRec, SectionOfTravel section) {
		// buffer.drawLine(xRec, yRec - 20, xRec, yRec + 20);
		// buffer.drawLine(xRec - 20, yRec, xRec + 20, yRec);
		xRec += sizeDemiLine * 4;
		yRec -= sizeDemiLine * 3;
		String[] words = new String[] {
				" " + section.getNameRoute() + " :",
				decomposeMinutesIntoHourMinutes(section.getTimeSection(), father.lg("LetterForHour"), father
						.lg("LetterForMinute")) };
		int[] xs = new int[2];
		int[] ys = new int[2];
		int xEnder;
		xs[0] = xRec + sizeDemiLine / 2;
		ys[0] = yRec + sizeDemiLine / 2 + this.getHeigthString(words[0], g, g.getFont());
		xs[1] = xs[0] + this.getWidthString(words[0], g, g.getFont()) + sizeDemiLine / 2;
		ys[1] = ys[0];
		xEnder = xs[1] + this.getWidthString(words[1], g, g.getFont()) + sizeDemiLine;
		g.setColor(father.getSkin().getColorSubAreaInside());
		g.fillRect(xRec, yRec, xEnder - xRec, ys[1] + sizeDemiLine - yRec);
		// g.setColor(colorActu);
		// g.fillRoundRect(xs[0], yRec + sizeDemiLine / 2, xs[1] - xs[0], xs[1] - xs[0],);
		g.setColor(father.getSkin().getColorLetter());
		g.drawRect(xRec, yRec, xEnder - xRec, ys[1] + sizeDemiLine - yRec);
		for (xEnder = 0; xEnder < 2; xEnder++)
			g.drawString(words[xEnder], xs[xEnder], ys[xEnder]);
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
	protected int drawInformationsStation(Graphics g, int xRec, int yRec, SectionOfTravel section) {
		int nextX, i, xEnder;
		String[] words = new String[5];
		int[] xs = new int[5];
		int[] ys = new int[5];
		// g.setColor(father.getSkin().getColorLetter());
		// g.drawRect(xRec, yRec, sizeQuadLarge, sizeLarge);
		words[0] = section.getNameChangement();
		xs[0] = xRec + sizeDemiLine * 2;
		ys[0] = yRec + sizeDemiLine / 2 + this.getHeigthString("A", g, g.getFont());
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		nextX = 0;

		words[1] = father.lg("Cost") + " : ";
		words[2] = father.lg("Time") + " : ";

		xs[1] = xs[0];
		ys[1] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[1], g, g.getFont());
		xs[2] = xs[1];
		ys[2] = ys[1] + sizeDemiLine / 2 + this.getHeigthString(words[2], g, g.getFont());
		nextX = xs[2] + this.getWidthString(words[1], g, g.getFont()) + sizeDemiLine / 2;
		i = xs[2] + this.getWidthString(words[2], g, g.getFont()) + sizeDemiLine / 2;
		if (i > nextX)
			xs[3] = i;
		else
			xs[3] = nextX;

		if (section.getEnddingChangementCost() == 0)
			words[3] = father.lg("Free");
		else
			words[3] = section.getEnddingChangementCost() + " " + father.lg("Money");
		words[4] = decomposeMinutesIntoHourMinutes(section.getEnddingChangementTime(), father.lg("LetterForHour"),
				father.lg("LetterForMinute"));

		// xs[3]=x;
		ys[3] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[3], g, g.getFont());
		xs[4] = xs[3];
		ys[4] = ys[3] + sizeDemiLine / 2 + this.getHeigthString(words[4], g, g.getFont());
		nextX = xs[4] + this.getWidthString(words[3], g, g.getFont()) + sizeDemiLine * 2;
		i = xs[4] + this.getWidthString(words[4], g, g.getFont()) + sizeDemiLine * 2;
		if (i > nextX)
			nextX = i;
		i = xRec + sizeDemiLine * 2 + this.getWidthString(section.getNameChangement(), g, g.getFont()) + sizeDemiLine
				* 2;
		if (i > nextX)
			xEnder = i;
		else
			xEnder = nextX;
		g.setColor(father.getSkin().getColorSubAreaInside());
		g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
		g.setColor(father.getSkin().getColorLetter());
		g.drawRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
		for (i = 0; i < 5; i++)
			g.drawString(words[i], xs[i], ys[i]);
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
	 * @param heigth
	 *            la hauteur de l'oval
	 */
	protected void drawDelayedOval(Graphics g, int x, int y, int width, int heigth) {
		if (ovalToDrawDelayed.g != null) {
			// ovalToDrawDelayed.g.setColor(father.getSkin().getColorInside());
			// ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
			// ovalToDrawDelayed.heigth);
			// ovalToDrawDelayed.g.setColor(father.getSkin().getColorLetter());
			// ovalToDrawDelayed.g.drawOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
			// ovalToDrawDelayed.heigth);
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorLetter());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
					ovalToDrawDelayed.heigth);
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorInside());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + father.getSizeAdapteur().getSizeLine(),
					ovalToDrawDelayed.y + father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.width - 2
							* father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.heigth - 2
							* father.getSizeAdapteur().getSizeLine());
		}
		ovalToDrawDelayed.g = g;
		ovalToDrawDelayed.x = x;
		ovalToDrawDelayed.y = y;
		ovalToDrawDelayed.width = width;
		ovalToDrawDelayed.heigth = heigth;
	}

	// TODO
	public void setActualState(IhmReceivingStates actualState) {
		if ((actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) || (actualState == IhmReceivingStates.PREVISU_TRAVEL)) {
			this.actualState = actualState;
			giveControle();
		}
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			upperBar.setLeftCmd(father.lg("Edit"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Edit soon avaible...");
				}
			});
			upperBar.setRightCmd(father.lg("Start"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					me.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
					// father.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
				}
			});
			upperBar.setUpperTitle(father.lg("Destination"));
			upperBar.setMainTitle(travel.getDestination());
		} else {
			upperBar.setLeftCmd(father.lg("Lost"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Lost soon avaible...");
				}
			});
			upperBar.setRightCmd(father.lg("Next"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Next soon avaible...");
				}
			});
			upperBar.setUpperTitle(father.lg("NextStop"));
			upperBar.setMainTitle(travel.getNextStop());
		}
		upperBar.repaint();

		lowerBar.clearMessage();
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			lowerBar.setCenterIcone("button_save", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Saving soon avaible...");
				}
			});
			lowerBar.setLeftTitle(father.lg("TotalCost"));
			lowerBar.setRightTitle(father.lg("TotalTime"));
			lowerBar.setLeftValue(travel.getTotalCost() + father.lg("Money"));
			lowerBar.setRightValue(decomposeMinutesIntoHourMinutes(travel.getTotalTime(), father.lg("LetterForHour"),
					father.lg("LetterForMinute")));
		} else {
			lowerBar.setCenterIcone("home", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (true || JOptionPane.showConfirmDialog(me, father.lg("DoYouWantToQuitYourActualTravel"), father
							.lg("ProgName"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
				}
			});
			lowerBar.setRightTitle(father.lg("RemainingTime"));
			lowerBar.setRightValue(decomposeMinutesIntoHourMinutes(travel.getRemainingTime(), father
					.lg("LetterForHour"), father.lg("LetterForMinute")));
		}
		lowerBar.repaint();
	}

	/**
	 * Classe équivalent à un structure en C/C++, on permet de stocker de façon regroupé plusieurs variable relative au
	 * dessin d'un futur oval
	 * 
	 * @author iGo
	 * 
	 */
	protected class OvalToDraw {
		public Graphics g;
		public int x;
		public int y;
		public int width;
		public int heigth;

		public OvalToDraw(Graphics g, int x, int y, int width, int heigth) {
			super();
			this.g = g;
			this.x = x;
			this.y = y;
			this.width = width;
			this.heigth = heigth;
		}
	}

	class RenderingThread extends Thread {
		/**
		 * Ce thread appelle le rafraichissement de notre fen�tre toutes les 10 milli-secondes
		 */
		public void run() {
			while (true) {
				try {
					repaint();
					sleep(100);
				} catch (Exception e) {
				}
			}
		}
	}
}
