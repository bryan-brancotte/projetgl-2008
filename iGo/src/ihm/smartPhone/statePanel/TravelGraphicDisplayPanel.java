package ihm.smartPhone.statePanel;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	protected Semaphore renderingLockDown = new Semaphore(1);

	int x = 0;

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
		colorList.add(Color.lightGray);
		colorList.add(Color.red);
		colorList.add(Color.blue);
		// colorList.add(Color.green);
		// colorList.add(Color.orange);
	}

	@Override
	public void paint(Graphics g) {
		
		if (!renderingLockDown.tryAcquire())
			return;
		/***************************************************************************************************************
		 * On regarde si la taille à changer, dans ce cas on modifie le buffer en conséquence
		 */
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
		}

		SectionOfTravel section;
		Iterator<SectionOfTravel> iterTravel = travel.getTravel();

		int orientation = 0;
		int idToModify;
		int idToKeep;
		int length;
		polygon.reset();
		polygon.addPoint(getWidth() / 2, father.getSizeAdapteur().getSizeLargeFont());
		polygon.addPoint(0, 0);
		polygon.addPoint(polygon.xpoints[1], polygon.ypoints[1]);
		polygon.addPoint(polygon.xpoints[0], polygon.ypoints[0]);

		buffer.setFont(father.getSizeAdapteur().getSmallFont());

		buffer.setColor(father.getSkin().getColorInside());
		drawDelayedOval(buffer, getWidth() / 4 - father.getSizeAdapteur().getSizeLargeFont() / 2, 0, father
				.getSizeAdapteur().getSizeLargeFont(), father.getSizeAdapteur().getSizeLargeFont());
		buffer.drawString(travel.getOrigine(), getWidth() / 4 + father.getSizeAdapteur().getSizeLargeFont(), +father
				.getSizeAdapteur().getSizeLargeFont()
				/ 2 - getHeigthString(travel.getOrigine(), buffer, father.getSizeAdapteur().getSmallFont()) / 2);
		while (iterTravel.hasNext()) {
			section = iterTravel.next();
			buffer.setColor(colorList.getFirst());
			colorList.addLast(colorList.removeFirst());
			length = section.getTimeSection() * father.getSizeAdapteur().getSizeSmallFont()/3 ;
			if (orientation % 2 == 0) {
				idToKeep = 2;
				idToModify = 0;
			} else {
				idToKeep = 0;
				idToModify = 2;
			}
			polygon.xpoints[idToKeep] = polygon.xpoints[idToKeep + 1] / 2 + polygon.xpoints[idToKeep] / 2;
			polygon.ypoints[idToKeep] = polygon.ypoints[idToKeep + 1] / 2 + polygon.ypoints[idToKeep] / 2;
			polygon.xpoints[idToKeep + 1] = polygon.xpoints[idToKeep];
			polygon.ypoints[idToKeep + 1] = polygon.ypoints[idToKeep];
			int hypo;
			int sizeDemiInter = father.getSizeAdapteur().getSizeIntermediateFont() / 2;
			hypo = (father.getSizeAdapteur().getSizeLargeFont() * 4);
			hypo *= hypo;
			hypo += length * length;
			hypo = (int) Math.sqrt(hypo);
			double cos, sin;

			cos = 0;
			sin = 1;
			switch (orientation % 3) {
			case 0:
				polygon.xpoints[idToKeep] += -sizeDemiInter /* sin */;
				// polygon.ypoints[idToKeep] += sizeDemiInter * cos;
				polygon.xpoints[idToKeep + 1] += sizeDemiInter /* sin */;
				// polygon.ypoints[idToKeep + 1] += sizeDemiInter * cos;
				break;
			case 1:
				cos = (double) father.getSizeAdapteur().getSizeLargeFont() * 4.0 / (double) hypo;
				sin = (double) length / (double) hypo;
				polygon.xpoints[idToKeep] += -sizeDemiInter * sin;
				polygon.ypoints[idToKeep] += sizeDemiInter * cos;
				polygon.xpoints[idToKeep + 1] += sizeDemiInter * sin;
				polygon.ypoints[idToKeep + 1] += -sizeDemiInter * cos;
				break;
			case 2:
				cos = -(double) father.getSizeAdapteur().getSizeLargeFont() * 4.0 / (double) hypo;
				sin = (double) length / (double) hypo;
				polygon.xpoints[idToKeep] += -sizeDemiInter * sin;
				polygon.ypoints[idToKeep] += sizeDemiInter * cos;
				polygon.xpoints[idToKeep + 1] += sizeDemiInter * sin;
				polygon.ypoints[idToKeep + 1] += -sizeDemiInter * cos;
				break;
			}

			switch (orientation % 3) {
			case 0:
				polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1];
				polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
				polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep];
				polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
				break;
			case 1:
				polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1]
						+ father.getSizeAdapteur().getSizeLargeFont() * 4;
				polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
				polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep]
						+ father.getSizeAdapteur().getSizeLargeFont() * 4;
				polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
				break;
			case 2:
				polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1]
						- father.getSizeAdapteur().getSizeLargeFont() * 4;
				polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
				polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep]
						- father.getSizeAdapteur().getSizeLargeFont() * 4;
				polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
				break;
			}
			// switch (orientation % 3) {
			// case 0:
			// polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1];
			// polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
			// polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep];
			// polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
			// break;
			// case 1:
			// polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1]
			// + father.getSizeAdapteur().getSizeLargeFont() * 4;
			// polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
			// polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep]
			// + father.getSizeAdapteur().getSizeLargeFont() * 4;
			// polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
			// break;
			// case 2:
			// polygon.xpoints[idToModify] = polygon.xpoints[idToKeep + 1]
			// - father.getSizeAdapteur().getSizeLargeFont() * 4;
			// polygon.ypoints[idToModify] = polygon.ypoints[idToKeep + 1] + length;
			// polygon.xpoints[idToModify + 1] = polygon.xpoints[idToKeep]
			// - father.getSizeAdapteur().getSizeLargeFont() * 4;
			// polygon.ypoints[idToModify + 1] = polygon.ypoints[idToKeep] + length;
			// break;
			// }
			buffer.fillPolygon(polygon);
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawPolygon(polygon);
			drawDelayedOval(buffer, polygon.xpoints[idToModify] / 2 + polygon.xpoints[idToModify + 1] / 2
					- father.getSizeAdapteur().getSizeLargeFont() / 2, polygon.ypoints[idToModify] / 2
					+ polygon.ypoints[idToModify + 1] / 2 - father.getSizeAdapteur().getSizeLargeFont() / 2, father
					.getSizeAdapteur().getSizeLargeFont(), father.getSizeAdapteur().getSizeLargeFont());
			// System.out.println(section.getNameChangement());
			orientation = (++orientation % 6);
		}

		drawDelayedOval(null, 0, 0, 0, 0);
		/***************************************************************************************************************
		 * dessin du nouvelle affichage
		 */
		g.drawImage(image, 0, 0, this);
		renderingLockDown.release();
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
			lowerBar.setLeftTitle(father.lg("RemainingCost"));
			lowerBar.setRightTitle(father.lg("RemainingTime"));
			lowerBar.setLeftValue(travel.getRemainingCost() + father.lg("Money"));
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
}
