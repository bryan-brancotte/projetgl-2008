package ihm.smartPhone.statePanels;

import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.statePanels.component.TravelPanel;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	protected LinkedList<TravelForTravelPanel> paths;
	/**
	 * L'image OK pour ajouter une station
	 */
	protected ImageIcon imageEdit;
	/**
	 * L'image OK pour retirer une station
	 */
	protected ImageIcon imageDel;

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
			IhmReceivingStates actualState, LinkedList<TravelForTravelPanel> paths) {
		super(ihm, upperBar, lowerBar);
		this.paths = paths;
		if (actualState == IhmReceivingStates.FAVORITES)
			this.actualState = IhmReceivingStates.FAVORITES;
		else if (actualState == IhmReceivingStates.LOAD_TRAVEL)
			this.actualState = IhmReceivingStates.LOAD_TRAVEL;
		buildInterface();
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
		int ordonne = decalage - deroullement;

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
			if (imageEdit == null || imageEdit.getIconHeight() != father.getSizeAdapteur().getSizeSmallFont()) {
				imageEdit = ImageLoader.getRessourcesImageIcone("button_config", father.getSizeAdapteur()
						.getSizeSmallFont(), father.getSizeAdapteur().getSizeSmallFont());
				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}
		/***************************************************************************************************************
		 * Dessins des chemins
		 */

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();
	}

	/**
	 * Construction du contenu
	 */
	protected void buildInterface() {
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
	}
}
