package ihm.smartPhone.statePanels;

import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.statePanels.component.TravelPanel;
import ihm.smartPhone.tools.VerticalFlowLayout;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * 
 * @author "iGo"
 * 
 */
public class LoadTravelPanel extends PanelState {

	private static final long serialVersionUID = 1L;

	protected Panel inside;

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
		ScrollPane sp = new ScrollPane();
		inside = new Panel();
		inside.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, true, false));
		makeInside(paths);
		sp.add(inside);
		if (actualState == IhmReceivingStates.FAVORITES)
			this.actualState = IhmReceivingStates.FAVORITES;
		else if (actualState == IhmReceivingStates.LOAD_TRAVEL)
			this.actualState = IhmReceivingStates.LOAD_TRAVEL;
		else
			throw new NoSuchFieldError("the kind " + actualState + " isn't allowed in a LoadTravelPanel");
		this.setLayout(new BorderLayout());
		this.add(sp);
	}

	@Override
	public void paint(Graphics g) {
		// g.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
	}

	protected void makeInside(LinkedList<TravelForTravelPanel> paths) {
		if (paths == null)
			return;
		inside.removeAll();
		if (this.getQuality().getValue() >= IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
			for (TravelForTravelPanel t : paths)
				inside.add(new TravelPanel(t, father,true));
		} else {
			for (TravelForTravelPanel t : paths)
				inside.add(new TravelPanel(t, father,false));
		}
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
				father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
	}

	/**
	 * Donne le contrôle en spécifiant une nouvelle liste de chemin.
	 * 
	 * @param paths
	 *            la nouvelle liste de chemin
	 */
	public void giveControle(LinkedList<TravelForTravelPanel> paths) {
		makeInside(paths);
	}
}
