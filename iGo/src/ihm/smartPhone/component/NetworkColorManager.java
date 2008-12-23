package ihm.smartPhone.component;

import graphNetwork.Route;
import graphNetwork.Service;

import java.awt.Color;

public interface NetworkColorManager {

	public Color getColor(Route r);

	public Color getColor(Service s);
}
