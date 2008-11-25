package ihm.smartPhone.statePanels;

import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.LinkedList;

public class TravelGraphicDisplayPanel extends TravelDisplayPanel {

	protected GraphicsViewPort buffer;

	protected int image;

	private static final long serialVersionUID = 1L;

	protected Polygon polygon = new Polygon();
	protected OvalToDraw ovalToDrawDelayed = new OvalToDraw(null, 0, 0, 0, 0);
	protected LinkedList<Color> colorList;

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
	 * Sémaphore du rendu, permet de ne pas lancer plusieur repaint en paralèlle
	 */
	// protected Semaphore renderingClamp = new Semaphore(1);
	public TravelGraphicDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar, travelForDisplayPanel);
		colorList = new LinkedList<Color>();
		// TODO couleur correcte...
		addColorAndItsLighted(new Color(242, 130, 38));// Orange
		addColorAndItsLighted(new Color(73, 12, 139));// pourpre foncé
		addColorAndItsLighted(new Color(133, 242, 38));// Vert jeune pousse
		addColorAndItsLighted(new Color(114, 159, 220));// Bleu clair
		addColorAndItsLighted(new Color(208, 38, 242));// rose clair
		addColorAndItsLighted(new Color(12, 52, 139));// Bleu marrine
		addColorAndItsLighted(Color.cyan);
		addColorAndItsLighted(new Color(139, 69, 12));// marront
		addColorAndItsLighted(new Color(12, 128, 139));// Bleu turquoise
		addColorAndItsLighted(new Color(254, 170, 52));// Orange pastel
		addColorAndItsLighted(new Color(189, 107, 247));// violet pastel
		addColorAndItsLighted(new Color(139, 12, 65));// bordeau
		addColorAndItsLighted(new Color(242, 239, 38));// Jaune
		addColorAndItsLighted(new Color(141, 207, 80));// Vert clair
		addColorAndItsLighted(new Color(137, 12, 139));// violet foncé
		addColorAndItsLighted(new Color(52, 52, 254));// Bleu foncé
		addColorAndItsLighted(new Color(80, 139, 12));// Vert foncé
		addColorAndItsLighted(new Color(38, 116, 224));// Bleu
		addColorAndItsLighted(new Color(137, 38, 242));// pourpre
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

	protected void addColorAndItsLighted(Color org) {
		int r = org.getRed(), g = org.getGreen(), b = org.getBlue();
		r *= 1.2;
		if (r > 255)
			r = 255;
		if (r < 25)
			r = 25;
		g *= 1.2;
		if (g > 255)
			g = 255;
		if (g < 25)
			g = 25;
		b *= 1.2;
		if (b > 255)
			b = 255;
		if (b < 25)
			b = 25;
		colorList.add(new Color(r, g, b));
		colorList.add(org);
	}

	@Override
	protected void actionToDoWhenChangeStateIsClicked() {
		father.setActualState(IhmReceivingStates.ARRAY_MODE.mergeState(actualState));
	}

	@Override
	protected String getMessageChangeState() {
		return father.lg("GoToArrayMode");
	}

	@Override
	public void paint(Graphics g) {
		// Un mutex est placé afin de ne pas lancer plusieur paint en même temps (et gagner du temps). Ce mutex a
		// autrefois éviter des execution inutils, mais ne semble plus être utils désormais. Il est laissé an attendant
		// un optimisation
		// if (!buffer.isNeededRepaint())
		// return;
		// TODO redering mutex retiré
		// if (!renderingClamp.tryAcquire()) {
		// System.out.println("acquire OFF");
		// return;
		// }
		// on demande un reconstruction de l'image
		buildImage();
		// on efface l'écran puis on dessine cette image
		// g.clearRect(0, 0, getWidth(), getHeight());
		super.paint(buffer.getBuffer());
		g.drawImage(buffer.getImage(), 0, 0, null);
		buffer.hasBeenDrawn();
		// g.drawString("qefzer", 10, 10);
		// renderingClamp.release();
	}

	public void buildImage() {
		// on définit les tailles étalons, et on les met à l'échelle
		// sizeLarge = (int) (44 * scallImg);
		// sizeQuadLarge = (int) (176 * scallImg);
		// sizeDemiLine = (int) (12 * scallImg);
		sizeLarge = (int) (father.getSizeAdapteur().getSizeLargeFont() * 4 * buffer.getScallImg());
		sizeQuadLarge = 4 * sizeLarge;
		sizeDemiLine = (int) (father.getSizeAdapteur().getSizeSmallFont() * 3 / 2 * buffer.getScallImg());
		sizeLine = (int) (father.getSizeAdapteur().getSizeLine() * buffer.getScallImg() * 2.5F);
		if (sizeLine < 1)
			sizeLine = 1;

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
		// on définit le début du dessin
		center.setLocation(sizeLarge / 2 + sizeDemiLine + buffer.getX(), sizeLarge / 2 + sizeDemiLine + buffer.getY());
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(0, 0);
		polygon.addPoint(center.x, center.y);
		polygon.addPoint(center.x, center.y);

		// System.out.println("\n\n\n\n\n\n\n\n");
		// si le dessin bien au dessus du bas de l'image
		buffer.setFont(new Font("AdaptedSmallFont", Font.PLAIN,
				(int) (father.getSizeAdapteur().getSizeSmallFont() * 4 * buffer.getScallImg())));
		buffer.setColor(father.getSkin().getColorInside());
		buffer.clearRect(0, 0, getWidth(), getHeight());
		if (heightImageDrawn >= -buffer.getHeigthViewPort()) {
			drawDelayedOval(buffer, center.x - sizeLarge / 2 - 1, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
		}
		// in parcout les étapes du trajet
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
			polygon.xpoints[idToKeep] = center.x + 1;
			polygon.ypoints[idToKeep] = center.y;
			polygon.xpoints[idToKeep + 1] = center.x + 1;
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
			// on vérifie que l'on veut dessiner dans la zone. on a modifier les limites car le dessin se fait par
			// groupemement assez séparé.
			// if ((heightImageDrawn * 4 / 5 > buffer.getHeigthViewPort())) {
			if (polygon.ypoints[0] > buffer.getHeigthViewPort() + sizeLarge
					&& polygon.ypoints[2] > buffer.getHeigthViewPort() + sizeLarge) {
				// au dela de la zone
				int i = 1;
				while (iterTravel.hasNext()) {
					iterTravel.next();i++;
				}
				System.out.println("Avoided : "+ i);
				// buffer.setColor(iterColor.next());
				// TODO a améliorer
			} else if (polygon.ypoints[0] < -sizeLarge && polygon.ypoints[2] < -sizeLarge) {
				buffer.setColor(iterColor.next());
			} else {
				// la zone
				// System.out.println("dessin de " + section.getNameChangement());
				buffer.fillPolygon(polygon);
				buffer.setColor(iterColor.next());
				// TODO garder le contour des lignes?
				buffer.drawPolygon(polygon);
				buffer.setColor(lightColor);
				drawInformationsRoute(buffer, (polygon.xpoints[0] + polygon.xpoints[2]) / 2,
						(polygon.ypoints[0] + polygon.ypoints[2]) / 2, section);
				drawDelayedOval(buffer, center.x - sizeLarge / 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
			}
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
		// buffer.drawLine(xRec, yRec - 20, xRec, yRec + 20);
		// buffer.drawLine(xRec - 20, yRec, xRec + 20, yRec);
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
		xs[1] = xRec + i + sizeDemiLine * 2;
		ys[1] = ys[0];
		g.setColor(Color.red);
		g.drawLine(xs[0], ys[0], 2 * xRec - xs[0], 2 * yRec - ys[0]);
		g.drawRect(xs[0], ys[0] - 2 * j, 2 * i, 2 * j);
		if (i < j)
			i = j;
		i = (int) (i * 1.3F);
		g.setColor(father.getSkin().getColorLetter());
		g.fillOval(xRec - i - sizeLine, yRec - i - sizeLine, i * 2 + 2 * sizeLine, i * 2 + 2 * sizeLine);
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

		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
			words[1] = father.lg("Cost") + " : ";
		words[2] = father.lg("Time") + " : ";

		xs[1] = xs[0];
		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
			ys[1] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[1], g.getImage().getGraphics(), g.getFont());
		else
			ys[1] = ys[0];
		xs[2] = xs[1];
		ys[2] = ys[1] + sizeDemiLine / 2 + this.getHeigthString(words[2], g.getImage().getGraphics(), g.getFont());
		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
			nextX = xs[2] + this.getWidthString(words[1], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine / 2;
		i = xs[2] + this.getWidthString(words[2], g.getImage().getGraphics(), g.getFont()) + sizeDemiLine / 2;
		if (i > nextX)
			xs[3] = i;
		else
			xs[3] = nextX;

		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
			if (section.getEnddingChangementCost() == 0)
				words[3] = father.lg("Free");
			else
				words[3] = section.getEnddingChangementCost() + " " + father.lg("Money");
		words[4] = decomposeMinutesIntoHourMinutes(section.getEnddingChangementTime(), father.lg("LetterForHour"),
				father.lg("LetterForMinute"));

		// xs[3]=x;
		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
			ys[3] = ys[0] + sizeDemiLine / 2 + this.getHeigthString(words[3], g.getImage().getGraphics(), g.getFont());
		else
			ys[3] = ys[0];
		xs[4] = xs[3];
		ys[4] = ys[3] + sizeDemiLine / 2 + this.getHeigthString(words[4], g.getImage().getGraphics(), g.getFont());
		if (this.actualState == IhmReceivingStates.PREVISU_TRAVEL)
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
			// TODO dessin de rectangle avec grandes bordures
			// ligne de 1
			// g.setColor(father.getSkin().getColorSubAreaInside());
			// g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			// g.setColor(father.getSkin().getColorLetter());
			// g.drawRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);

			// ligne de sizeLine avec 2 fillRect
			// g.setColor(father.getSkin().getColorLetter());
			// g.fillRect(xRec, yRec, xEnder - xRec, ys[4] + sizeDemiLine - yRec);
			// g.setColor(father.getSkin().getColorSubAreaInside());
			// g.fillRect(xRec + sizeLine, yRec + sizeLine, xEnder - xRec - sizeLine * 2, ys[4] + sizeDemiLine - yRec
			// - sizeLine * 2);
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
			// g.drawLine(xRec + sizeLine * 2, yRec + sizeLine * 2, xEnder - sizeLine * 2, ys[4] + sizeDemiLine -
			// sizeLine * 2);
			for (i = 0; i < 5; i++)
				if (words[i] != null)
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
			// ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + father.getSizeAdapteur().getSizeLine(),
			// ovalToDrawDelayed.y + father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.width - 2
			// * father.getSizeAdapteur().getSizeLine(), ovalToDrawDelayed.height - 2
			// * father.getSizeAdapteur().getSizeLine());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x + sizeLine, ovalToDrawDelayed.y + sizeLine,
					ovalToDrawDelayed.width - 2 * sizeLine, ovalToDrawDelayed.height - 2 * sizeLine);
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

			if (me.getQuality().getValue() >= IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
				this.buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				if (me.getQuality().getValue() >= IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue()) {
					this.buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					if (me.getQuality().getValue() >= IHMGraphicQuality.HIGHER_QUALITY.getValue()) {
						this.buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						this.buffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
								RenderingHints.VALUE_COLOR_RENDER_QUALITY);
						this.buffer.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
								RenderingHints.VALUE_FRACTIONALMETRICS_ON);
						this.buffer.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
					}
				}
			} else {
				/** Désactivation de l'anti-aliasing */
				this.buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				this.buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				/** Demande de rendu rapide */
				this.buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
				this.buffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
						RenderingHints.VALUE_COLOR_RENDER_SPEED);
				this.buffer.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
				this.buffer.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			}
			// this.buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
	}
}