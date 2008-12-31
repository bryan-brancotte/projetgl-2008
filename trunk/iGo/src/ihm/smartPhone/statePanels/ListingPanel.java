package ihm.smartPhone.statePanels;

import graphNetwork.Route;
import graphNetwork.Station;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.libPT.PTButton;
import ihm.smartPhone.tools.CodeExecutor1P;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ListingPanel extends PanelState {

	protected static HashMap<Route, LinkedList<PTButton>> hashRouteStation;
	protected static LinkedList<Route> routes;

	protected Station stationSelected = null;

	public ListingPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar) {
		super(father, upperBar, lowerBar);
		if (hashRouteStation == null) {
			hashRouteStation = new HashMap<Route, LinkedList<PTButton>>();
			routes = new LinkedList<Route>();
			Iterator<Route> itR;
			Route route;
			Iterator<Station> itS = father.getStations();
			Station station;
			LinkedList<PTButton> lstStation;
			while (itS.hasNext()) {
				itR = (station = itS.next()).getRoutes();
				while (itR.hasNext()) {
					lstStation = hashRouteStation.get(route = itR.next());
					if (lstStation == null)
						hashRouteStation.put(route, lstStation = new LinkedList<PTButton>());
					lstStation.add(makeButton(new CodeExecutor1P<Station>(station) {
						@Override
						public void execute() {
							stationSelected = this.origine;
						}
					}));
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
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3801758712891550847L;

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		lowerBar.clearMessage();
		upperBar.setLeftCmd(father.lg("Edit"), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// father.setCurrentState(IhmReceivingStates.EDIT_TRAVEL, travel.getPath());
			}
		});
		upperBar.repaint();
		lowerBar.repaint();
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		for(Route r:routes){
			System.out.println(r);
		}
	}
}
