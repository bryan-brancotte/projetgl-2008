package ihm.smartPhone.component;

import graphNetwork.Route;
import graphNetwork.Service;

import iGoMaster.Master;

import java.awt.Color;
import java.util.HashMap;

public class NetworkColorManagerPseudoRandom implements NetworkColorManager {
	Master master;
	HashMap<Route, Color> colorRoutes;
	HashMap<Service, Color> colorServices;

	public NetworkColorManagerPseudoRandom(Master master) {
		super();
	}

	@Override
	public Color getColorForRoute(Route r) {
		return Color.blue;
	}

	@Override
	public Color getColorForService(Service r) {
		return Color.red;
	}

}
