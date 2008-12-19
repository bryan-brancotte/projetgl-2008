package ihm.smartPhone.component;

import graphNetwork.Route;
import graphNetwork.Service;

import java.awt.Color;

public interface NetworkColorManager {

	public Color getColorForRoute(Route r);

	public Color getColorForService(Service s);
}
