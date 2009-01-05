package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.interfaces.TravelForTravelPanel.ServiceForTravelPanel;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.listener.MouseMotionListenerSimplificated;
import ihm.smartPhone.statePanels.component.TravelPanelPT;
import ihm.smartPhone.tools.CodeExecutor2P;
import ihm.smartPhone.tools.CodeExecutor3P;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;

/**
 * 
 * @author "iGo"
 * 
 */
public class LoadTravelPanel extends PanelState {

	private static final long serialVersionUID = 1L;
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;
	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * Barre de défilement
	 */
	protected LinkedList<TravelPanelPT> travelPanels;
	/**
	 * L'image pour editer le trajet
	 */
	protected ImageIcon imageEdit;
	/**
	 * L'image pour supprimer le trajet
	 */
	protected ImageIcon imageDel;
	/**
	 * L'image d'un trajet favorit
	 */
	protected ImageIcon imageFav = null;
	/**
	 * L'image d'un trajet non-favorit
	 */
	protected ImageIcon imageNoFav = null;

	protected IhmReceivingStates actualState = IhmReceivingStates.UNKNOWN;

	/**
	 * 
	 * @param ihm
	 * @param upperBar
	 * @param lowerBar
	 * @param actualState
	 *            est soit IhmReceivingStates.FAVORITES soit IhmReceivingStates.LOAD_TRAVEL, leve une exception
	 *            NoSuchFieldError si on spécfie un autre type.
	 * @param paths
	 */
	public LoadTravelPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			IhmReceivingStates actualState, LinkedList<TravelForTravelPanel> travels) {
		super(ihm, upperBar, lowerBar);
		travelPanels = new LinkedList<TravelPanelPT>();
		if (actualState == IhmReceivingStates.FAVORITES)
			this.actualState = IhmReceivingStates.FAVORITES;
		else if (actualState == IhmReceivingStates.LOAD_TRAVEL)
			this.actualState = IhmReceivingStates.LOAD_TRAVEL;
		buildInterface(travels);
		this.addMouseMotionListener(new MouseMotionListenerSimplificated<PanelState>(this) {
			@Override
			public void mouseMoved(MouseEvent e) {
				boolean repaint = false;
				boolean b;
				for (TravelPanelPT t : travelPanels) {
					if (t.isInMe ^ (b = t.area.contains(e.getX(), e.getY()))) {
						t.isInMe = b;
						repaint = true;
					}
				}
				if (repaint)
					this.origin.repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
		this.addMouseListener(new MouseListener() {

			protected TravelPanelPT toStart;

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
				for (TravelPanelPT t : travelPanels)
					if (t.isInMe)
						toStart = t;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				for (TravelPanelPT t : travelPanels) {
					if (t.isInMe) {
						if (t != toStart)
							return;
						if (!t.cmdDel.getArea().contains(e.getX(), e.getY())
								&& !t.cmdEdit.getArea().contains(e.getX(), e.getY())
								&& !t.cmdFav.getArea().contains(e.getX(), e.getY()))
							toStart.pathBuilder.start();
					}
				}
			}
		});
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
		g.drawImage(image, 0, 0, null);
	}

	public void draw() {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int decalageDemi = (decalage >> 1);
		int decalage2 = (decalage << 1);
		int ordonnee = decalage - deroullement;

		/***
		 * Gestion du buffer mémoire
		 */
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
			imageEdit = null;
			imageDel = null;
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			// buffer.setColor(father.getSkin().getColorLetter());
			if (imageEdit == null || imageEdit.getIconHeight() != father.getSizeAdapteur().getSizeIntermediateFont()) {
				imageEdit = ImageLoader.getRessourcesImageIcone("button_config", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
				imageFav = ImageLoader.getRessourcesImageIcone("fav", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
				imageNoFav = ImageLoader.getRessourcesImageIcone("fav-no", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}
		/***************************************************************************************************************
		 * Dessins des chemins
		 */
		for (TravelPanelPT travelPanelPT : travelPanels) {
			drawPathLikeInTheDoc(travelPanelPT, ordonnee, decalage, decalageDemi, decalage2);
			ordonnee += travelPanelPT.area.height + decalage;
		}

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonnee + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();
	}

	protected void drawPathWithMoreInfo(TravelPanelPT travelPanelPT, int ordonnee, int decalage, int decalageDemi,
			int decalage2) {
	}

	protected void drawPathLikeInTheDoc(TravelPanelPT travelPanelPT, int ordonnee, int decalage, int decalageDemi,
			int decalage2) {
		int x;
		int y;
		int nextX;
		int i;
		int endRight;
		String tmp1, tmp2;
		String[] stationInter = null;
		if (travelPanelPT.pathBuilder.getIntermediateStation().length() == 0)
			stationInter = new String[0];
		else
			stationInter = decoupeChaine(father.lg("IntermediatesStationsLittle") + " : "
					+ travelPanelPT.pathBuilder.getIntermediateStation(), buffer, getWidth() - decalage2 - decalageDemi);
		travelPanelPT.area.setBounds(decalage, ordonnee, getWidth() - decalage2 - decalage, decalage2
				+ father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() << 1));
		if (stationInter.length > 0) {
			travelPanelPT.area.height += (father.getSizeAdapteur().getSizeSmallFont() + decalageDemi)
					* stationInter.length;
		}
		buffer.setColor(father.getSkin().getColorSubAreaInside());
		buffer
				.fillRect(travelPanelPT.area.x, travelPanelPT.area.y, travelPanelPT.area.width,
						travelPanelPT.area.height);
		buffer.setColor(father.getSkin().getColorLetter());
		buffer
				.drawRect(travelPanelPT.area.x, travelPanelPT.area.y, travelPanelPT.area.width,
						travelPanelPT.area.height);
		if (travelPanelPT.isInMe)
			buffer.drawRect(travelPanelPT.area.x + 1, travelPanelPT.area.y + 1, travelPanelPT.area.width - 2,
					travelPanelPT.area.height - 2);
		x = travelPanelPT.area.x + decalageDemi;
		y = travelPanelPT.area.y + decalageDemi;
		if (travelPanelPT.pathBuilder.isFavorite())
			travelPanelPT.cmdFav.update(buffer, x, y, imageFav);
		else
			travelPanelPT.cmdFav.update(buffer, x, y, imageNoFav);
		travelPanelPT.cmdDel.update(buffer,
				i = (travelPanelPT.area.x + travelPanelPT.area.width - decalageDemi - imageDel.getIconWidth()), y,
				imageDel);
		travelPanelPT.cmdEdit.update(buffer, i - decalageDemi - imageEdit.getIconWidth(), y, imageEdit);
		buffer.setFont(father.getSizeAdapteur().getIntermediateFont());

		x += (decalageDemi >> 1) + imageFav.getIconWidth();
		tmp1 = "";
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		Iterator<ServiceForTravelPanel> itS;
		ServiceForTravelPanel serviceForTravelPanel;
		i = PanelDoubleBufferingSoftwear.getHeightString("H", buffer);
		y += imageFav.getIconHeight() - i >> 1;
		i += (i >> 2) + (i >> 3);

		itS = travelPanelPT.pathBuilder.getServiceAlways();
		if (!itS.hasNext())
			x -= decalage;
		while (itS.hasNext()) {
			serviceForTravelPanel = itS.next();
			buffer.setColor(serviceForTravelPanel.getColor());
			buffer.fillOval(x - 1, y - 1, i + 2, i + 2);
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawOval(x - 1, y - 1, i + 2, i + 2);
			buffer.drawString(serviceForTravelPanel.getLetter(), x + (i >> 1)
					- (PanelDoubleBufferingSoftwear.getWidthString(serviceForTravelPanel.getLetter(), buffer) >> 1), y
					+ (i >> 1)
					+ (PanelDoubleBufferingSoftwear.getHeightString(serviceForTravelPanel.getLetter(), buffer) >> 1));
			x += i + decalageDemi;
			;
		}
		x += decalage;
		itS = travelPanelPT.pathBuilder.getServiceOnce();
		while (itS.hasNext()) {
			serviceForTravelPanel = itS.next();
			buffer.setColor(serviceForTravelPanel.getColor());
			buffer.fillOval(x - 1, y - 1, i + 2, i + 2);
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawOval(x - 1, y - 1, i + 2, i + 2);
			buffer.drawString(serviceForTravelPanel.getLetter(), x + (i >> 1)
					- (PanelDoubleBufferingSoftwear.getWidthString(serviceForTravelPanel.getLetter(), buffer) >> 1), y
					+ (i >> 1)
					+ (PanelDoubleBufferingSoftwear.getHeightString(serviceForTravelPanel.getLetter(), buffer) >> 1));
			x += i + (decalage >> 1);
		}
		y = travelPanelPT.area.y + decalageDemi + getHeightString("e", buffer);

		/***************************************************************************************************************
		 * calcul du from to
		 */
		x = travelPanelPT.area.x + decalageDemi + (decalageDemi >> 2);
		tmp1 = father.lg("FromAndTwoDot") + " ";
		tmp2 = father.lg("ToAndTwoDot") + " ";
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		nextX = PanelDoubleBufferingSoftwear.getWidthString(tmp1, buffer) + x;
		i = PanelDoubleBufferingSoftwear.getWidthString(tmp2, buffer) + x;
		if (nextX < i)
			nextX = i;

		/***************************************************************************************************************
		 * Dessin du from to
		 */
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		y += getHeightString(tmp1, buffer) + decalageDemi;
		buffer.drawString(tmp1, x, y);
		buffer.drawString(tmp2, x, y + decalageDemi + getHeightString(tmp2, buffer));

		/***************************************************************************************************************
		 * Dessin des gares de départ et d'arrivée
		 */
		x = nextX;
		tmp1 = travelPanelPT.pathBuilder.getOrigine();
		tmp2 = travelPanelPT.pathBuilder.getDestination();
		buffer.drawString(tmp1, x, y);
		buffer.drawString(tmp2, x, y + getHeightString(tmp2, buffer) + decalageDemi);
		endRight = getWidthString(tmp1, buffer);
		i = getWidthString(tmp2, buffer);
		if (i > endRight)
			endRight = i;
		endRight += x;

		//
		//
		/***************************************************************************************************************
		 * calcul des valeurs
		 */
		tmp1 = travelPanelPT.pathBuilder.getTotalCost() + " " + father.lg("Money");
		tmp2 = decomposeMinutesIntoHourMinutes(travelPanelPT.pathBuilder.getTotalTime(), father.lg("LetterForHour"),
				father.lg("LetterForMinute"), father.lg("MiniLetterForMinute"));
		nextX = travelPanelPT.area.width + travelPanelPT.area.x
				- PanelDoubleBufferingSoftwear.getWidthString(tmp1, buffer);
		i = travelPanelPT.area.width + travelPanelPT.area.x - PanelDoubleBufferingSoftwear.getWidthString(tmp2, buffer);
		if (nextX > i)
			nextX = i;

		/***************************************************************************************************************
		 * Dessin des valeurs
		 */
		x = nextX - decalageDemi;
		buffer.drawString(tmp1, x, y);
		buffer.drawString(tmp2, x, y + decalageDemi + getHeightString(tmp2, buffer));

		/***************************************************************************************************************
		 * Dessin du coût et du temps
		 */
		tmp1 = father.lg("Cost") + " : ";
		tmp2 = father.lg("Time") + " : ";
		nextX = x - PanelDoubleBufferingSoftwear.getWidthString(tmp1, buffer);
		i = x - PanelDoubleBufferingSoftwear.getWidthString(tmp2, buffer);
		if (nextX > i)
			nextX = i;
		if (nextX > endRight) {
			x = nextX;
			buffer.drawString(tmp1, x, y);
			buffer.drawString(tmp2, x, y + decalageDemi + getHeightString(tmp2, buffer));
		}

		/***************************************************************************************************************
		 * Dessin des stations intermédiaires
		 */
		x = travelPanelPT.area.x + decalageDemi + (decalageDemi >> 2);
		y += decalageDemi + getHeightString(tmp2, buffer);
		for (String tmp : stationInter)
			buffer.drawString(tmp, x, y += getHeightString(tmp, buffer) + decalageDemi);
	}

	/**
	 * Construction du contenu
	 */
	protected void buildInterface(LinkedList<TravelForTravelPanel> travels) {
		TravelPanelPT travelPanel;
		for (TravelForTravelPanel t : travels) {
			this.travelPanels.add(travelPanel = new TravelPanelPT(null, null, null, new Rectangle(), t));
			// la zone général
			travelPanel.area = new Rectangle();
			// la supression de l'obj
			travelPanel.cmdDel = makeButton(new CodeExecutor3P<TravelForTravelPanel, PanelState, TravelPanelPT>(t,
					this, travelPanel) {
				@Override
				public void execute() {
					this.origineC.cmdDel.terminate();
					this.origineC.cmdEdit.terminate();
					this.origineC.cmdFav.terminate();
					travelPanels.remove(this.origineC);
					this.origineA.delete();
					this.origineB.repaint();

				}
			});
			// sa mise en favorit
			travelPanel.cmdFav = makeButton(new CodeExecutor2P<TravelForTravelPanel, PanelState>(t, this) {
				@Override
				public void execute() {
					this.origineA.setFavorite(!this.origineA.isFavorite());
					this.origineB.repaint();
				}
			});
			// son édition
			travelPanel.cmdEdit = makeButton(new CodeExecutor2P<TravelForTravelPanel, PanelState>(t, this) {
				@Override
				public void execute() {
					this.origineA.edit();
					this.origineB.repaint();
				}
			});
		}
		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar = makeScrollBar();
	}

	/**
	 * Donne le controle en concervant la liste actuelle des chemins possibles
	 */
	@Override
	public void giveControle() {
		upperBar.clearMessage();
		if (actualState == IhmReceivingStates.FAVORITES)
			upperBar.setMainTitle(father.lg("Favorites"));
		else
			upperBar.setMainTitle(father.lg("LoadTravel"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
		this.requestFocus();
	}
}
