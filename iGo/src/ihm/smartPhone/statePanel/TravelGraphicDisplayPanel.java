package ihm.smartPhone.statePanel;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;
import ihm.smartPhone.listener.MouseListenerSimplificated;

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
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class TravelGraphicDisplayPanel extends PanelState {

	protected Dimension preferredSize = new Dimension(10, 10);

	@Override
	public Dimension getPreferredSize() {
		return super.getPreferredSize();
	}

	private static final long serialVersionUID = 1L;

	protected TravelGraphicDisplayPanel me = this;
	protected TravelForDisplayPanel travel = null;
	protected IhmReceivingStates actualState = IhmReceivingStates.PREVISU_TRAVEL;

	protected Polygon polygon = new Polygon();
	protected OvalToDraw ovalToDrawDelayed = new OvalToDraw(null, 0, 0, 0, 0);
	protected LinkedList<Color> colorList;

	protected float xImg;
	protected float yImg;
	protected int heigthImage;
	protected int widthImage;
	protected float scallImg = 0.25F;

	protected int xLastPointeur;
	protected int yLastPointeur;

	protected int sizeDemiLine;
	protected int sizeQuadLarge;
	protected int sizeLarge;

	protected Semaphore renderingClamp = new Semaphore(1);

	public TravelGraphicDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar);
		this.travel = travelForDisplayPanel;
		if (travel == null)
			travel = new TravelForTravelPanelExemple();
		colorList = new LinkedList<Color>();
		colorList.add(Color.cyan);
		colorList.add(Color.magenta);
		colorList.add(Color.yellow);
		colorList.add(Color.red);
		colorList.add(Color.blue);
		colorList.add(Color.green);
		colorList.add(Color.orange);
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
		this.addMouseListener(new MouseListenerSimplificated<TravelGraphicDisplayPanel>(this) {

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
		});
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() > 0)
					scallImg *= 1.02;
				else if ((e.getWheelRotation() < 0) && ((widthImage > getWidth()) || (heigthImage > getHeight())))
					scallImg /= 1.02;
				else
					return;
				repaint(); 
			}
		});
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
		// this.setEnableRenderingClamp(true);
	}

	@Override
	public void paint(Graphics g) {
		if (!renderingClamp.tryAcquire()) {
			System.out.println("acquire OFF");
			return;
		}
		buildImage();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, (int) xImg, (int) yImg, null);
		renderingClamp.release();
	}

	public void buildImage() {
		sizeLarge = 44;
		sizeQuadLarge = 176;
		sizeDemiLine = 12;
		sizeLarge *= scallImg;
		sizeQuadLarge *= scallImg;
		sizeDemiLine *= scallImg;

		/***************************************************************************************************************
		 * On regarde si la taille à changer, dans ce cas on modifie le buffer en conséquence
		 */
		if ((buffer == null) || (image.getWidth(null) != sizeQuadLarge * 4)
				|| (image.getHeight(null) != travel.getTotalTime() * sizeLarge / 3)) {
			if (image != null) {
				xImg += image.getWidth(this) / 2;
				yImg += image.getHeight(this) / 2;
			}
			image = createImage(sizeQuadLarge * 4, travel.getTotalTime() * sizeLarge / 3);
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
			length = section.getTimeSection() * sizeLarge / 3;
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
			drawDelayedOval(buffer, center.x - sizeLarge / 2, center.y - sizeLarge / 2, sizeLarge, sizeLarge);
			buffer.drawLine(center.x, center.y, center.x + sizeQuadLarge, center.y);
			// System.out.println(section.getNameChangement());
			orientation = (++orientation % 6);
		}

		drawDelayedOval(null, 0, 0, 0, 0);
		/***************************************************************************************************************
		 * Définition de la taille réel de l'image.
		 */
		heigthImage = center.y + sizeLarge / 2 + 10;
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

	protected void drawDelayedOval(Graphics g, int x, int y, int width, int heigth) {
		if (ovalToDrawDelayed.g != null) {
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorInside());
			ovalToDrawDelayed.g.fillOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
					ovalToDrawDelayed.heigth);
			ovalToDrawDelayed.g.setColor(father.getSkin().getColorLetter());
			ovalToDrawDelayed.g.drawOval(ovalToDrawDelayed.x, ovalToDrawDelayed.y, ovalToDrawDelayed.width,
					ovalToDrawDelayed.heigth);
		}
		ovalToDrawDelayed.g = g;
		ovalToDrawDelayed.x = x;
		ovalToDrawDelayed.y = y;
		ovalToDrawDelayed.width = width;
		ovalToDrawDelayed.heigth = heigth;
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
