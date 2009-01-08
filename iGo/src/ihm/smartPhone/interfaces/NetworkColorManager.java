package ihm.smartPhone.interfaces;

import graphNetwork.Route;
import graphNetwork.Service;

import java.awt.Color;

/**
 * Manager retournant pour un station donnée un unique couleur
 * 
 * @author iGo
 * 
 */
public interface NetworkColorManager {

	/**
	 * Retourne la couleur correspondant à la ligne passée en paramètre
	 * @param r la ligne
	 * @return
	 */
	public Color getColor(Route r);

	/**
	 * Retourne la couleur correspondant au service passé en paramètre
	 * @param s la station
	 * @return
	 */
	public Color getColor(Service s);
}
