package ihm.smartPhone.statePanels;

import graphNetwork.Route;
import graphNetwork.Station;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;
import ihm.smartPhone.libPT.PTArea;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class TravelArrayDisplayPanel extends TravelDisplayPanel {
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * Barre de défilement
	 */
	protected PTArea areaDrawn;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;
	/**
	 * L'image des changements
	 */
	protected ImageIcon imageChange;

	public TravelArrayDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar, travelForDisplayPanel);
		scrollBar = makeScrollBar();
		areaDrawn = makeArea();
	}

	private static final long serialVersionUID = 1L;

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
		super.paint(g);
	}

	protected void draw() {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		// int decalageDemi = (decalage >> 1);
		// int decalage2 = (decalage << 1);
		int ordonne = decalage - deroullement;
		SectionOfTravel section;
		Iterator<SectionOfTravel> iterTravel = travel.getTravelDone();
		boolean firstPasseDone = false;
		int ordonnee = decalage;

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
		 * Station d'origine
		 */
		ordonnee = draw(this.travel.getOrigineStation(), ordonne, decalage, 50, 70, getWidth() - (decalage << 1));

		do {
			while (iterTravel.hasNext()) {
				ordonnee = draw((section = iterTravel.next()).getRoute(), ordonne, decalage, 50, 70, getWidth()
						- (decalage << 1));
				ordonnee = draw(section.getChangement(), ordonne, decalage, 50, 70, getWidth() - (decalage << 1));
			}
			if (firstPasseDone)
				break;
			firstPasseDone = true;
			iterTravel = travel.getTravelToDo();
		} while (true);

		// scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
		// .getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
		// father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		// shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		// deroullement = scrollBar.getDeroullement();
	}

	/**
	 * Dessine de decalage à width-2decalage
	 * 
	 * @param ordonnee
	 * @param station
	 * @return
	 */
	protected int draw(Station station, int ordonnee, int left, int inLeft, int inRigth, int rigth) {
		return 0;
	}

	protected int draw(Route station, int ordonnee, int left, int inLeft, int inRigth, int rigth) {
		return 0;
	}

	@Override
	protected void actionToDoWhenChangeStateIsClicked() {
		father.setConfig("GRAPHIC_OR_ARRAY_MODE", IhmReceivingStates.GRAPHIC_MODE.toString());
		father.setCurrentState(IhmReceivingStates.GRAPHIC_MODE.mergeState(actualState));
	}

	@Override
	protected String getMessageChangeState() {
		return father.lg("GoToGraphicMode");
	}
}
