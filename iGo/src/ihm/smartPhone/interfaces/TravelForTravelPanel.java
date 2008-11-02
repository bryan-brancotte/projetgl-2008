package ihm.smartPhone.interfaces;

/**
 * Interface fournissant des informations au sujet d'un trajet.
 * 
 * @author Brancotte Bryan
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
	 * Retourne le nom du trajet
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Redéfinit le nom du trajet
	 * 
	 * @param name
	 *            le nouveau nom
	 */
	public void setName(String name);

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
	 * Demande un mise à jours du contenue.
	 * 
	 * @return true si un changement à été effectué
	 */
	public boolean update();

}
