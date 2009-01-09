package ihm.smartPhone.statePanels;

import iGoMaster.EventInfo;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public class ListingEvent extends PanelState {

	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;
	/**
	 * Y d'origine pour le drag
	 */
	protected int yDrag;
	/**
	 * Utilitaire de défilement doux
	 */
	protected SlowScroll slowScroll;
	/**
	 * variation d'Y pour le drag
	 */
	protected int dyDrag;
	/**
	 * Action à effectuer quand on quitte cet état
	 */
	protected CodeExecutor cancelEndingAction = null;
	/**
	 * Images pour les types d'évenement
	 */
	protected Image imageOther = null;
	protected Image imagePb = null;
	protected Image imageSol = null;

	public ListingEvent(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar,
			CodeExecutor nvCancelEndingAction) {
		super(father, upperBar, lowerBar);
		scrollBar = makeScrollBar(60);
		this.cancelEndingAction = nvCancelEndingAction;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void giveControle() {
		lowerBar.clearMessage();
		lowerBar.repaint();
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("RecentsEvents"));
		upperBar.setLeftCmd(father.lg("Back"), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cancelEndingAction == null)
					father.setCurrentState(IhmReceivingStates.UNKNOWN);
				else
					cancelEndingAction.execute();
			}
		});
		upperBar.repaint();
		this.requestFocus();
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

	/**
	 * Dessin en arrière plan de la fenetre
	 */
	public void draw() {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int decalageDemi = (decalage >> 1);
		int decalage2 = (decalage << 1);
		int ordonnee = decalage - deroullement;
		int roundRect = father.getSizeAdapteur().getSizeSmallFont() >> 1;

		/***
		 * Gestion du buffer mémoire
		 */
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
			imageOther = null;
			imagePb = null;
			imageSol = null;
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			if (imageOther == null || imageOther.getHeight(null) != father.getSizeAdapteur().getSizeLargeFont()) {
				imageOther = ImageLoader.getRessourcesImageIcone("warning",
						father.getSizeAdapteur().getSizeLargeFont(), father.getSizeAdapteur().getSizeLargeFont(), true)
						.getImage();
				imagePb = ImageLoader.getRessourcesImageIcone("button_cancel",
						father.getSizeAdapteur().getSizeLargeFont(), father.getSizeAdapteur().getSizeLargeFont(), true)
						.getImage();
				imageSol = ImageLoader.getRessourcesImageIcone("button_ok",
						father.getSizeAdapteur().getSizeLargeFont(), father.getSizeAdapteur().getSizeLargeFont(), true)
						.getImage();
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		Iterator<EventInfo> itE;
		itE = father.getEvent();
		while (itE.hasNext()) {
			ordonnee = drawEventInfo(itE.next(), ordonnee, decalage, decalageDemi, decalage2, roundRect);
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

	private int drawEventInfo(EventInfo ev, int ordonnee, final int decalage, final int decalageDemi,
			final int decalage2, final int roundRec) {
		int left;
		int height;
		int width;
		String[] title;
		String[] msg;
		left = decalage + imageOther.getWidth(null) + decalageDemi;
		width = getWidth() - left - decalage2 - decalage;
		buffer.setFont(father.getSizeAdapteur().getIntermediateFont());
		if (ev.getIdRoute() != null && ev.getIdStation() != 0) {
			title = decoupeChaine(father.lg("EventRouteAndStation") + " :\n" + father.lg("Route") + " "
					+ ev.getIdRoute() + " , " + father.lg("Station") + " " + ev.getIdStation(), buffer, width);
		} else if (ev.getIdRoute() != null) {
			title = decoupeChaine(father.lg("EventRoute") + " :\n" + father.lg("Route") + " " + ev.getIdRoute(),
					buffer, width);
		} else if (ev.getIdStation() != 0) {
			title = decoupeChaine(father.lg("EventStation") + " :\n" + father.lg("Station") + " " + ev.getIdStation(),
					buffer, width);
		} else {
			title = decoupeChaine(father.lg("UnknownEvent"), buffer, getWidth() - decalage - decalage2 - left);
		}
		msg = decoupeChaine(ev.getMessage(), buffer, width);
		height = decalage + title.length * (father.getSizeAdapteur().getSizeIntermediateFont() + 3) + msg.length
				* (father.getSizeAdapteur().getSizeSmallFont() + 3);
		buffer.setColor(father.getSkin().getColorSubAreaInside());
		buffer.fillRoundRect(decalage, ordonnee, getWidth() - decalage - decalage2, height, roundRec, roundRec);
		buffer.setColor(father.getSkin().getColorLetter());
		buffer.drawRoundRect(decalage, ordonnee, getWidth() - decalage - decalage2, height, roundRec, roundRec);
		// buffer.drawImage(imageCadre, decalage, ordonnee, null);
		switch (ev.getKindEventInfoNetwork()) {
		case PROBLEM:
			buffer.drawImage(imagePb, decalage, ordonnee + (height - father.getSizeAdapteur().getSizeLargeFont() >> 1),
					null);
			break;
		case SOLUTION:
			buffer.drawImage(imageSol, decalage,
					ordonnee + (height - father.getSizeAdapteur().getSizeLargeFont() >> 1), null);
			break;
		case OTHER:
		default:
			buffer.drawImage(imageOther, decalage, ordonnee
					+ (height - father.getSizeAdapteur().getSizeLargeFont() >> 1), null);
			break;
		}
		ordonnee += decalageDemi >> 1;
		ordonnee += getHeightString(ev.getMessage(), buffer);
		for (String tmp : title) {
			buffer.drawString(tmp, 40, ordonnee);
			ordonnee += getHeightString(tmp, buffer) + 3;
		}
		buffer.setFont(father.getSizeAdapteur().getSmallFont());
		ordonnee += decalageDemi >> 1;
		for (String tmp : msg) {
			buffer.drawString(tmp, 40, ordonnee);
			ordonnee += getHeightString(tmp, buffer) + 3;
		}
		return ordonnee;
	}

	/**
	 * Classe permettant un défillement doux des composants dans le panel
	 * 
	 * @author iGo
	 * 
	 */
	protected class SlowScroll extends Thread {

		public SlowScroll(int deroulement) {
			super();
			this.deroulement = deroulement;
			this.start();
		}

		int deroulement;

		byte step = 2;

		public void killMe() {
			deroulement = 0;
		}

		@Override
		public void run() {
			int delta = 2;

			for (int i = 0; i < deroulement; deroulement -= delta) {
				for (int j = 0; j < step; j++) {
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
					}
					if (deroulement == 0)
						return;
					scrollBar.setDeroullement(scrollBar.getDeroullement() + deroulement);
					repaint();
				}
			}
			for (int i = 0; i > deroulement; deroulement += delta) {
				for (int j = 0; j < step; j++) {
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
					}
					if (deroulement == 0)
						return;
					scrollBar.setDeroullement(scrollBar.getDeroullement() + deroulement);
					repaint();
				}
			}
		}
	}

}
