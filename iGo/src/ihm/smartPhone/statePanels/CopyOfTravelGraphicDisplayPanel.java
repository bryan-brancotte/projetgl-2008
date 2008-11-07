package ihm.smartPhone.statePanels;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
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

public class CopyOfTravelGraphicDisplayPanel extends PanelState {

	protected Dimension preferredSize = new Dimension(10, 10);

	protected GraphicsViewPort buffer;

	protected int image;

	@Override
	public Dimension getPreferredSize() {
		return super.getPreferredSize();
	}

	private static final long serialVersionUID = 1L;

	protected CopyOfTravelGraphicDisplayPanel me = this;
	protected TravelForDisplayPanel travel = null;
	protected IhmReceivingStates actualState = IhmReceivingStates.PREVISU_TRAVEL;

	protected Polygon polygon = new Polygon();
	protected OvalToDraw ovalToDrawDelayed = new OvalToDraw(null, 0, 0, 0, 0);
	protected LinkedList<Color> colorList;

	// protected float xImg;
	// protected float yImg;
	// /**
	// * La hauteur de l'image représentant le réseau
	// */
	// protected int heightImage;
	// /**
	// * La largueur de l'image représentant le réseau
	// */
	// protected int widthImage;
	// /**
	// * L'échelle de l'image
	// */
	// protected float scallImg = 0.25F;
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

	public CopyOfTravelGraphicDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
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
				if (e.getWheelRotation() > 0)
					buffer.increasScallImg(1.05F);
				else if (e.getWheelRotation() < 0)
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
					keyCode = KeyEvent.VK_PLUS;
				} else if (e.getKeyChar() == '-') {
					keyCode = KeyEvent.VK_MINUS;
				}
				switch (keyCode) {
				case KeyEvent.VK_PLUS:
					buffer.increasScallImg(1.05F);
					break;
				case KeyEvent.VK_MINUS:
					buffer.decreasScallImg(1.05F);
					break;
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
					buffer.move(0, getWidth() / 20);
					break;
				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
					buffer.move(0, -getWidth() / 20);
					break;
				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
					buffer.move(getWidth() / 20, 0);
					break;
				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
					buffer.move(-getWidth() / 20, 0);
					// TODO
					// tmp = (getWidth() - widthImage);
					// if (xImg < tmp)
					// xImg = tmp;
					break;
				default:
					return;
				}
				// if (xImg > 0)
				// xImg = 0;
				// if (yImg > 0)
				// yImg = 0;
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
//		if (!buffer.isNeededRepaint())
//			return;
		if (!renderingClamp.tryAcquire()) {
			System.out.println("acquire OFF");
			return;
		}
		// on demande un reconstruction de l'image
		buildImage();
		// on efface l'écran puis on dessine cette image
		//g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(buffer.getImage(), 0, 0, null);
		buffer.hasBeenDrawn();
		// g.drawString("qefzer", 10, 10);
		renderingClamp.release();
	}

	public void buildImage() {
		// on définit les tailles étalons, et on les met à l'échelle
		// sizeLarge = (int) (44 * scallImg);
		// sizeQuadLarge = (int) (176 * scallImg);
		// sizeDemiLine = (int) (12 * scallImg);
		sizeLarge = (int) (father.getSizeAdapteur().getSizeLargeFont() * 4 * buffer.getScallImg());
		sizeQuadLarge = 4 * sizeLarge;
		sizeDemiLine = (int) (father.getSizeAdapteur().getSizeSmallFont() * 3 / 2 * buffer.getScallImg());

		/***************************************************************************************************************
		 * On regarde si la taille de l'image a changer (via un zoom par exemple) et on reconstruit l'image.
		 */
		if ((buffer.getWidthViewPort() != getWidth()) || (buffer.getHeigthViewPort() != getHeight())) {
			buffer.setSizeViewPort(getWidth(), getHeight());
		}
		if ((buffer.getWidthImage() != sizeQuadLarge * 5)
				|| (buffer.getHeigthImage() != travel.getTotalTime() * sizeLarge / 4)) {
			buffer.setSizeImage(sizeQuadLarge * 5, travel.getTotalTime() * sizeLarge / 4);
		} else if (!buffer.isNeededRepaint()) {
			return;
		}

		SectionOfTravel section;
		Iterator<SectionOfTravel> iterTravel = travel.getTravel();

		int orientation = 0;
		int idToModify;
		int idToKeep;
		int length;
		int hypo;
		Color lightColor;
		Point center = new Point();
		Iterator<Color> iterColor = colorList.iterator();
		int heightImageDrawn = buffer.getY();
		polygon.reset();
		center.setLocation(sizeLarge / 2 + sizeDemiLine + buffer.getX(), sizeLarge / 2 + sizeDemiLine + buffer.getY());
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(0, 0);
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(center.x, center.y);
		if (heightImageDrawn * 4 / 5 <= buffer.getHeigthViewPort()) {
			buffer.setFont(new Font("AdaptedSmallFont", Font.PLAIN,
					(int) (father.getSizeAdapteur().getSizeSmallFont() * 4 * buffer.getScallImg())));
			buffer.setColor(father.getSkin().getColorInside());
			buffer.clearRect(0, 0, getWidth(), getHeight());
			drawDelayedOval(buffer, center.x - sizeLarge / 2 - 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
		}
		while (iterTravel.hasNext()) {
			if (!iterColor.hasNext())
				iterColor = colorList.iterator();
			section = iterTravel.next();
			lightColor = iterColor.next();
			buffer.setColor(lightColor);
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
			if (heightImageDrawn * 4 / 5 <= buffer.getHeigthViewPort()) {
				buffer.fillPolygon(polygon);
				buffer.setColor(iterColor.next());
				buffer.drawPolygon(polygon);
				buffer.setColor(lightColor);
				drawInformationsRoute(buffer, (polygon.xpoints[0] + polygon.xpoints[2]) / 2,
						(polygon.ypoints[0] + polygon.ypoints[2]) / 2, section);
				drawDelayedOval(buffer, center.x - sizeLarge / 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
			}
			// System.out.println(heightImage + " " + (center.y - sizeLarge / 2)
			// + (heightImage > (center.y - sizeLarge / 2)));
			// buffer.drawLine(50, center.y - sizeLarge / 2, 250, center.y - sizeLarge / 2);
			buffer.setColor(father.getSkin().getColorLetter());
			if (heightImageDrawn > (center.y - sizeLarge / 2)) {
				buffer.drawLine(center.x, center.y, center.x + sizeLarge * 2, heightImageDrawn + sizeLarge / 4);
				heightImageDrawn = sizeDemiLine
						+ drawInformationsStation(buffer, center.x + sizeLarge * 2, heightImageDrawn, section);
			} else {
				buffer.drawLine(center.x, center.y, center.x + sizeLarge * 2, center.y - sizeLarge / 4);
				heightImageDrawn = sizeDemiLine
						+ drawInformationsStation(buffer, center.x + sizeLarge * 2, center.y - sizeLarge / 2, section);
			}
			// buffer.drawLine(0, heightImageDrawn, 200, heightImageDrawn);
			// System.out.println(section.getNameChangement());
			orientation = (++orientation % 6);
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
//		buffer.drawLine(xRec, yRec - 20, xRec, yRec + 20);
//		buffer.drawLine(xRec - 20, yRec, xRec + 20, yRec);
		String[] words = new String[] {
				section.getNameRoute(),
				decomposeMinutesIntoHourMinutes(section.getTimeSection(), father.lg("LetterForHour"), father
						.lg("LetterForMinute")) };
		int i = this.getWidthString(words[0], g.getImage().getGraphics(), g.getFont()) / 2;
		int j = this.getHeigthString(words[0], g.getImage().getGraphics(), g.getFont()) / 2;
		int[] xs = new int[2];
		int[] ys = new int[2];
		Color colorActu = g.getColor();
		xs[0] = xRec - i;
		ys[0] = yRec + j;
		xs[1] = xRec + i + sizeDemiLine*2;
		ys[1] = ys[0];
		// g.drawRect(xs[0], ys[0]-2*j, 2*i, 2*j);
		if (i < j)
			i = j;
		i = (int) (i * 1.3F);
		g.setColor(father.getSkin().getColorLetter());
		g.fillOval(xRec - i - father.getSizeAdapteur().getSizeLine(),
				yRec - i - father.getSizeAdapteur().getSizeLine(), i * 2 + 2 * father.getSizeAdapteur().getSizeLine(),
				i * 2 + 2 * father.getSizeAdapteur().getSizeLine());
		g.setColor(colorActu);
		g.fillOval(xRec - i, yRec - i, i * 2, i * 2);
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
		// xs[0] = xRec + sizeDemiLine / 2;
		// ys[0] = yRec + sizeDemiLine / 2 + this.getHeigthString(words[0], g.getImage().getGraphics(), g.getFont());
		// xs[1] = xs[0] + this.getWidthString(words[0], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine / 2;
		// ys[1] = ys[0];
		// xEnder = xs[1] + this.getWidthString(words[1], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine;
		// g.setColor(father.getSkin().getColorSubAreaInside());
		// g.fillRect(xRec, yRec, xEnder - xRec, ys[1] + sizeDemiLine - yRec);
		// // g.fillRoundRect(xs[0], yRec + sizeDemiLine / 2, xs[1] - xs[0], xs[1] - xs[0],);
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
		int nextX, i, xEnder;
		String[] words = new String[5];
		int[] xs = new int[5];
		int[] ys = new int[5];
		g.setColor(father.getSkin().getColorLetter());
		// g.drawRect(xRec, yRec, sizeQuadLarge, sizeLarge);
		words[0] = section.getNameChangement();
		xs[0] = xRec + sizeDemiLine * 2;
		ys[0] = yRec + sizeDemiLine / 2 + this.getHeigthString("A", g.getImage().getGraphics(), g.getFont());
		// buffer.setFont(father.getSizeAdapteur().getSmallFont());
		nextX = 0;

		words[1] = father.lg("Cost") + " : ";
		words[2] = father.lg("Time") + " : ";

		xs[1] = xs[0];
		ys[1] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[1], g.getImage().getGraphics(), g.getFont());
		xs[2] = xs[1];
		ys[2] = ys[1] + sizeDemiLine / 2 + this.getHeigthString(words[2], g.getImage().getGraphics(), g.getFont());
		nextX = xs[2] + this.getWidthString(words[1], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine / 2;
		i = xs[2] + this.getWidthString(words[2], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine / 2;
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
		ys[3] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[3], g.getImage().getGraphics(), g.getFont());
		xs[4] = xs[3];
		ys[4] = ys[3] + sizeDemiLine / 2 + this.getHeigthString(words[4], g.getImage().getGraphics(), g.getFont());
		nextX = xs[4] + this.getWidthString(words[3], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine * 2;
		i = xs[4] + this.getWidthString(words[4], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine * 2;
		if (i > nextX)
			nextX = i;
		i = xRec + sizeDemiLine * 2
				+ this.getWidthString(section.getNameChangement(), g.getImage().getGraphics(), g.getFont())
				+ sizeDemiLine * 2;
		if (i > nextX)
			xEnder = i;
		else
			xEnder = nextX;
		if (xRec * 4 / 5 <= buffer.getHeigthViewPort()) {
			g.setColor(father.getSkin().getColorSubAreaInside());
			g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			g.setColor(father.getSkin().getColorLetter());
			g.drawRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			for (i = 0; i < 5; i++)
				g.drawString(words[i], xs[i], ys[i]);
		}
		buffer.extendsHeight(ys[4] + sizeDemiLine * 2 - buffer.getY());
		buffer.extendsWidth(xEnder - buffer.getX() + sizeDemiLine);
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
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + father.getSizeAdapteur().getSizeLine(),
					ovalToDrawDelayed.y + father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.width - 2
							* father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.height - 2
							* father.getSizeAdapteur().getSizeLine());
		}
		ovalToDrawDelayed.g = g;
		ovalToDrawDelayed.x = x;
		ovalToDrawDelayed.y = y;
		ovalToDrawDelayed.width = width;
		ovalToDrawDelayed.height = height;
	}

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
			// TODO
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

			this.buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			this.buffer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}

		public void setSizeImage(int width, int height) {
			if (width < 10)
				width = 10;
			if (height < 10)
				height = 10;
			neededRepaint = true;
			this.heightImage = 0;
			this.widthImage = 0;
			// this.heightImage = height;
			// this.widthImage = width;
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
			buffer.drawRect(x, y, width, height);
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
	}
}