package ihm.smartPhone.statePanels;

import graphNetwork.Route;
import graphNetwork.Station;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.libPT.PTButton;
import ihm.smartPhone.libPT.PTCollapsableArea;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ListingPanel extends PanelState {

	public String getStationSelected() {
		return stationSelected.getName();
	}

	protected HashMap<Route, PTCollapsableArea> hashRouteCollapse;
	protected HashMap<Route, LinkedList<PTButton>> hashRouteStation;
	protected LinkedList<Route> routes;

	protected Station stationSelected = null;

	protected CodeExecutor okEndingAction = null;
	protected CodeExecutor cancelEndingAction = null;

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

	public ListingPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar,
			CodeExecutor okEndingAction, CodeExecutor cancelEndingAction) {
		super(father, upperBar, lowerBar);
		if (hashRouteStation == null) {
			hashRouteStation = new HashMap<Route, LinkedList<PTButton>>();
			hashRouteCollapse = new HashMap<Route, PTCollapsableArea>();
			routes = new LinkedList<Route>();
			Iterator<Route> itR;
			Route route;
			Iterator<Station> itS = father.getStations();
			Station station;
			LinkedList<PTButton> lstStation;
			PTButton button;
			PTCollapsableArea collapsable;
			while (itS.hasNext()) {
				itR = (station = itS.next()).getRoutes();
				while (itR.hasNext()) {
					lstStation = hashRouteStation.get(route = itR.next());
					if (lstStation == null) {
						hashRouteStation.put(route, lstStation = new LinkedList<PTButton>());
						hashRouteCollapse.put(route, collapsable = makeCollapsableArea());
					} else {
						collapsable = hashRouteCollapse.get(route);
					}
					lstStation.add(button = makeButton(new CodeExecutor1P<Station>(station) {
						@Override
						public void execute() {
							stationSelected = this.origine;
						}
					}));
					collapsable.addComponent(button);
				}
			}
			routes.addAll(hashRouteStation.keySet());
			Collections.sort(routes, new Comparator<Route>() {
				@Override
				public int compare(Route o1, Route o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
		}
		this.cancelEndingAction = cancelEndingAction;
		this.okEndingAction = okEndingAction;

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar = makeScrollBar();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3801758712891550847L;

	@Override
	public void giveControle() {
		lowerBar.clearMessage();
		lowerBar.repaint();
		upperBar.clearMessage();
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
		int ordonne = decalage - deroullement;
		PTButton button;
		PTCollapsableArea collapsable;
		
		
		
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			// buffer.setColor(father.getSkin().getColorLetter());
//			if (imageOk == null || imageOk.getIconHeight() != father.getSizeAdapteur().getSizeLargeFont()) {
//				imageOk = ImageLoader.getRessourcesImageIcone("button_ok", father.getSizeAdapteur().getSizeLargeFont(),
//						father.getSizeAdapteur().getSizeLargeFont());
//				imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
//						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
//				imageFind = ImageLoader.getRessourcesImageIcone("logo", father.getSizeAdapteur()
//						.getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
//			}
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}
		
		
		
		
		
		for (Route route : routes) {
			(collapsable = hashRouteCollapse.get(route)).prepareArea(buffer, decalage, ordonne, route.getId(), father
					.getSizeAdapteur().getIntermediateFont());
			collapsable.draw(buffer, 
					father.getSizeAdapteur().getIntermediateFont(), 
					father.getSkin()
					.getColorSubAreaInside(), 
					father.getSkin().getColorLetter());
			ordonne = collapsable.getArea().y + collapsable.getArea().height + decalage;

		}
		


		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();

	}
}
