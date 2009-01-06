package ihm.smartPhone.interfaces;

import java.awt.Color;
import java.util.Iterator;

/**
 * Interface fournissant des informations au sujet d'un trajet.
 * 
 * @author iGo
 * 
 */
public interface TravelForTravelPanel {

	/**
	 * Retourne le nom de la station d'origine.
	 * 
	 * @return
	 */
	public String getOrigine();

	/**
	 * Retourne le nom de la sation de destination.
	 * 
	 * @return
	 */
	public String getDestination();

	/**
	 * Retourne une concatenation des stations intermédiaire séparé pas ", " et terminé par "."
	 * 
	 * @return
	 */
	public String getIntermediateStation();

	/**
	 * Retourne le nombre de mintues necessaire à l'accomplissement du trajet.
	 * 
	 * @return
	 */
	public int getTotalTime();

	/**
	 * Retourne le coût total du trajet.
	 * 
	 * @return
	 */
	public float getTotalCost();

	/**
	 * Permet de savoir si le trajet est un trajet favorit.
	 * 
	 * @return true si le trajet est un favorit
	 */
	public boolean isFavorite();

	/**
	 * Définit si le trajet est un trajet favorit.
	 * 
	 * @param isFav
	 *            true si le trajet est un favorit.
	 */
	public void setFavorite(boolean isFav);

	/**
	 * Demande le parcourt du chemin existant
	 */
	public void start();

	/**
	 * Demande la suppression du chemin représenté par cette objet
	 */
	public void delete();

	/**
	 * Demande l'édition du chemin représenté par cette objet
	 */
	public void edit();

	public Iterator<ServiceForTravelPanel> getServiceOnce();

	public Iterator<ServiceForTravelPanel> getServiceAlways();

	public interface ServiceForTravelPanel {
		
		public String getLetter();
		
		public Color getColor();

	}

}
