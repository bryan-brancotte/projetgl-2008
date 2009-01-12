package ihm.smartPhone.statePanels;

import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.SettingsKey;
import iGoMaster.SettingsValue;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;
import ihm.smartPhone.libPT.PTArea;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class TravelArrayDisplayPanel extends TravelDisplayPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Barre de défilement
	 */
	protected PTArea areaDrawn;
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * variation d'Y pour le drag
	 */
	protected int dyDrag;
	/**
	 * L'image des changements
	 */
	protected ImageIcon imageChange;
	/**
	 * boolean permetant de savoir qu'on a appuyé sur suivant, et qu'on doit mettre en haut la station atteinte
	 */
	protected boolean putStationUp = false;
	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;
	/**
	 * Thread du scroll doux
	 */
	protected SlowScroll slowScroll;
	/**
	 * Y d'origine pour le drag
	 */
	protected int yDrag;

	public TravelArrayDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar, travelForDisplayPanel);
		scrollBar = makeScrollBar();
		areaDrawn = makeArea();
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				scrollBar.setDeroullement(scrollBar.getDeroullement() + (dyDrag = yDrag - e.getY()));
				yDrag = e.getY();
				deroullement = scrollBar.getDeroullement();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (slowScroll != null)
					slowScroll.killMe();
				dyDrag = 0;
				yDrag = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (slowScroll != null)
					slowScroll.killMe();
				slowScroll = new SlowScroll(dyDrag >> 2);
			}
		});
	}

	@Override
	protected void actionToDoWhenChangeStateIsClicked() {
		father.setConfig("GRAPHIC_OR_ARRAY_MODE", IhmReceivingStates.GRAPHIC_MODE.toString());
		father.setCurrentState(IhmReceivingStates.GRAPHIC_MODE.mergeState(currentState));
	}

	protected void draw() {
		int decalage;
		// int decalage2 = (decalage << 1);
		int ordonnee;
		SectionOfTravel section;
		Iterator<SectionOfTravel> iterTravel = travel.getTravelDone();
		boolean firstPasseDone = false;
		int inLeft;
		int inRight;
		int height;
		int i, j;
		String s;
		String letterForHour = father.lg("LetterForHour");
		String letterForMinute = father.lg("LetterForMinute");
		String miniLetterForMinute = father.lg("MiniLetterForMinute");
		/***
		 * Gestion du buffer mémoire
		 */
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
			imageChange = null; 
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			// buffer.setColor(father.getSkin().getColorLetter());
			if (imageChange == null || imageChange.getIconHeight() != father.getSizeAdapteur().getSizeSmallFont()) {
				imageChange = ImageLoader.getRessourcesImageIcone("echange", father.getSizeAdapteur()
						.getSizeSmallFont(), father.getSizeAdapteur().getSizeSmallFont());
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		/**
		 * préparation des constantes
		 */

		decalage = father.getSizeAdapteur().getSizeSmallFont();
		ordonnee = decalage - deroullement;
		inLeft = getWidthString(father.lg("Departure"), buffer);
		i = getWidthString(father.lg("Arrival"), buffer);
		if (inLeft < i)
			inLeft = i;
		inLeft += (decalage << 1);

		inRight = getWidthString(father.lg("Cost") + " : ", buffer);
		i = getWidthString(father.lg("Time") + " : ", buffer);
		if (inRight < i)
			inRight = i;

		inRight = 0;
		j = getWidthString("888" + father.lg("Money"), buffer);
		i = getWidthString(decomposeMinutesIntoHourMinutes(711, letterForHour, letterForMinute, miniLetterForMinute),
				buffer);
		if (j < i)
			j = i;
		i = getWidthString(father.lg("Free"), buffer);
		if (j < i)
			j = i;
		inRight = getWidth() - (decalage << 1) - decalage - inRight - j;

		/**
		 * Station d'origine
		 */
		height = drawStation(this.travel.getOrigineStation(), this.travel.getEntryCost(), 0, decalage, ordonnee,
				this.currentState == IhmReceivingStates.PREVISU_TRAVEL, decalage, inLeft, inRight, getWidth()
						- (decalage << 1));
		buffer.drawString(s = father.lg("Departure"), decalage + (inLeft - decalage - getWidthString(s, buffer) >> 1),
				ordonnee + (height + getHeightString(s, buffer) >> 1));
		ordonnee += decalage + height;

		do {
			while (iterTravel.hasNext()) {
				ordonnee += decalage
						+ drawRoute((section = iterTravel.next()).getRoute(), section.getTimeSection(), section
								.getDirection(), decalage, ordonnee, firstPasseDone, decalage, inLeft, inRight,
								getWidth() - (decalage << 1));
				height = drawStation(section.getChangement(), section.getEnddingChangementCost(), section
						.getEnddingChangementTime(), decalage, ordonnee, firstPasseDone, decalage, inLeft, inRight,
						getWidth() - (decalage << 1));
				if ((firstPasseDone || !travel.hasNext()) && !iterTravel.hasNext())
					buffer.drawString(s = father.lg("Arrival"), decalage
							+ (inLeft - decalage - getWidthString(s, buffer) >> 1), ordonnee
							+ (height + getHeightString(s, buffer) >> 1));
				else
					buffer.drawImage(imageChange.getImage(), decalage
							+ (inLeft - decalage - imageChange.getIconWidth() >> 1), ordonnee
							+ (height - imageChange.getIconHeight() >> 1), null);
				ordonnee += decalage + height;
			}
			if (firstPasseDone)
				break;
			else if (putStationUp) {
				putStationUp = false;
				slowScroll = new SlowScroll(decalage - ordonnee);

			}
			firstPasseDone = true;
			iterTravel = travel.getTravelToDo();
		} while (true);

		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonnee + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();
	}

	protected int drawRoute(Route route, int time, Station direction, int decalage, int ordonnee,
			boolean hasBeenTraveled, int left, int inLeft, int inRigth, int rigth) {
		int taille;
		String s;
		int height = (taille = (int) ((getWidthString(route.getId(), buffer)) * 1.3F)) + decalage;
		// int
		// choix de la couleur : couleur vivant si à faire, couleur d'arrière plan si déja franchit.
		if (hasBeenTraveled)
			buffer.setColor(father.getSkin().getColorSubAreaInside());
		else
			buffer.setColor(father.getSkin().getColorInside());
		// dessin du cartouche pour une route
		buffer.fillRect(left, ordonnee, rigth - left, height);
		buffer.setColor(father.getSkin().getColorLine());
		buffer.drawLine(inLeft, ordonnee, inLeft, ordonnee + height);
		buffer.drawLine(inRigth, ordonnee, inRigth, ordonnee + height);
		buffer.setColor(father.getSkin().getColorLetter());
		buffer.drawRect(left, ordonnee, rigth - left, height);
		// dessine le logo de la ligne
		buffer.setColor(father.getNetworkColorManager().getColor(route));
		buffer.fillRect(left + inLeft - taille >> 1, ordonnee + (height - taille >> 1), taille, taille);
		buffer.setColor(father.getSkin().getColorLetter());
		buffer.drawRect(left + inLeft - taille >> 1, ordonnee + (height - taille >> 1), taille, taille);
		buffer.drawString(route.getId(), left + inLeft - getWidthString(route.getId(), buffer) >> 1, ordonnee
				+ (height + getHeightString(route.getId(), buffer) - (decalage >> 2) >> 1));
		// la direction
		buffer.drawString(father.lg("DirectionInArrayMode") + direction.getName(), inLeft + (decalage >> 1), ordonnee
				+ (height + getHeightString(route.getId(), buffer) >> 1));
		// le temps
		buffer.drawString(s = decomposeMinutesIntoHourMinutes(time, father.lg("LetterForHour"), father
				.lg("LetterForMinute"), father.lg("MiniLetterForMinute")), inRigth + (decalage >> 1), ordonnee
				+ (height + getHeightString(s, buffer) >> 1));

		return height;
	}

	/**
	 * Dessine de decalage à width-2decalage
	 * 
	 * @param ordonnee
	 * @param station
	 * @return
	 */
	protected int drawStation(Station station, float cost, int time, int decalage, int ordonnee,
			boolean hasBeenTraveled, int left, int inLeft, int inRigth, int rigth) {
		int taille;
		int height;
		Service service;
		String s;
		int xService;
		int yService;
		Iterator<Service> itService;

		/**
		 * Variable
		 */
		height = buffer.getFont().getSize() + decalage;
		if ((itService = station.getServices()).hasNext())
			height += (int) ((taille = buffer.getFont().getSize()) * 1.3F);
		else
			height += buffer.getFont().getSize();
		taille = (int) (buffer.getFont().getSize() * 1.3F);
		xService = inLeft + (decalage >> 1);
		yService = ordonnee + (decalage >> 1) + buffer.getFont().getSize();
		buffer.setFont(father.getSizeAdapteur().getSmallFont());

		/**
		 * Cadre
		 */
		if (hasBeenTraveled)
			buffer.setColor(father.getSkin().getColorSubAreaInside());
		else
			buffer.setColor(father.getSkin().getColorInside());
		buffer.fillRect(left, ordonnee, rigth - left, height);
		buffer.setColor(father.getSkin().getColorLine());
		buffer.drawLine(inLeft, ordonnee, inLeft, ordonnee + height);
		buffer.drawLine(inRigth, ordonnee, inRigth, ordonnee + height);
		buffer.setColor(father.getSkin().getColorLetter());
		buffer.drawRect(left, ordonnee, rigth - left, height);

		/**
		 * Station et service
		 */
		if (itService.hasNext()) {
			buffer.drawString(station.getName(), xService, ordonnee + buffer.getFont().getSize());

			while (itService.hasNext()) {
				service = itService.next();
				s = service.getName().substring(0, 1);
				buffer.setColor(father.getNetworkColorManager().getColor(service));
				buffer.fillOval(xService - 1, yService - 1, taille + 2, taille + 2);
				buffer.setColor(father.getSkin().getColorLetter());
				buffer.drawOval(xService - 1, yService - 1, taille + 2, taille + 2);
				buffer.drawString(s, xService + (taille >> 1)
						- (PanelDoubleBufferingSoftwear.getWidthString(s, buffer) >> 1), yService + (taille >> 1)
						+ (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
				xService += taille + (decalage >> 1);
			}
		} else {
			buffer.drawString(station.getName(), xService, ordonnee
					+ (height + getHeightString(station.getName(), buffer) - (decalage >> 2) >> 1));
		}

		/**
		 * Coût et temps : nom
		 */
		xService = inRigth + (decalage >> 1);
		if (this.currentState == IhmReceivingStates.PREVISU_TRAVEL) {

			/**
			 * Temps
			 */
			if (cost == 0)
				s = father.lg("Free");
			else
				s = cost + " " + father.lg("Money");
			buffer.drawString(s, xService, ordonnee + (int) (height * 0.75)
					+ (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
			buffer.drawString(decomposeMinutesIntoHourMinutes(time, father.lg("LetterForHour"), father
					.lg("LetterForMinute"), father.lg("MiniLetterForMinute")), xService, ordonnee + (height >> 2)
					+ (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
		} else {
			buffer.drawString(s = decomposeMinutesIntoHourMinutes(time, father.lg("LetterForHour"), father
					.lg("LetterForMinute"), father.lg("MiniLetterForMinute")), xService, ordonnee
					+ (height + getHeightString(s, buffer) >> 1));
		}
		return height;
	}

	@Override
	protected String getMessageChangeState() {
		return father.lg("GoToGraphicMode");
	}

	@Override
	public void giveControle() {
		if (currentState == IhmReceivingStates.EXPERIMENT_TRAVEL)
			putStationUp = true;
		super.giveControle();
	}

	@Override
	protected void nextStationDone() {
		putStationUp = true;
	}

	@Override
	public void paint(Graphics g) {
		draw();
		if (shouldDoubleRepaint) {
			shouldDoubleRepaint = false;
			draw();
		}
		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
		super.paint(buffer);
		g.drawImage(image, 0, 0, null);
	}

	@Override
	protected void startStationDone() {
		new SlowScroll(-deroullement);
	}

	/**
	 * Classe permettant un défilement doux du tableau
	 * 
	 * @author "iGo"
	 * 
	 */
	protected class SlowScroll extends Thread {

		int deroulement;

		byte step = 2;

		public SlowScroll(int deroulement) {
			super();
			this.deroulement = deroulement >> 1;
			if (father.getConfig(SettingsKey.AUTO_SCROLL.toString()).compareTo(SettingsValue.DISABLE.toString()) != 0)
				this.start();
		}

		public void killMe() {
			deroulement = 0;
		}

		@Override
		public void run() {
			int delta = 0;
			switch (PanelDoubleBufferingSoftwear.getQuality()) {
			case TEXT_ANTI_ANTIALIASING:
				delta += 10;
			case FULL_ANTI_ANTIALIASING:
				delta += 10;
			case HIGHER_QUALITY:
				delta += 10;
				break;
			default:
				delta = deroulement >> 2;
				if (deroulement < 0)
					delta = -delta;
				if (delta < 35)
					delta = 35;
				break;
			}
			for (int i = 0; i > deroulement; deroulement += delta) {
				for (int j = 0; j < step; j++) {
					try {
						Thread.sleep(delta << 1);
					} catch (InterruptedException e) {
					}
					if (deroulement > -delta) {
						delta = -deroulement;
					}
					if (!scrollBar.setDeroullement(scrollBar.getDeroullement() + delta)) {
						repaint();
						return;
					}
					repaint();
				}
			}
			for (int i = 0; i < deroulement; deroulement -= delta) {
				for (int j = 0; j < step; j++) {
					try {
						Thread.sleep(delta << 1);
					} catch (InterruptedException e) {
					}
					if (deroulement < +delta) {
						delta = deroulement;
					}
					if (!scrollBar.setDeroullement(scrollBar.getDeroullement() - delta)) {
						repaint();
						return;
					}
					repaint();
				}
			}
		}
	}

	@Override
	protected String getIconeChangeState() {
		return "mode_graphic";
	}
}
