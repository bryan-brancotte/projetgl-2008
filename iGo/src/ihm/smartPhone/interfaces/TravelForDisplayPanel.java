package ihm.smartPhone.interfaces;

import graphNetwork.Route;

import java.util.Iterator;

public interface TravelForDisplayPanel {

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
	 * Retourne le nombre de mintues restante pour ce trajet.
	 * 
	 * @return
	 */
	public int getRemainingTime();

	/**
	 * Demande un mise à jours du contenue.
	 * 
	 * @return true si un changement à été effectué
	 */
	public boolean update();

	/**
	 * Retourne le prochain point de passage
	 * 
	 * @return
	 */
	public String getNextStop();

	/**
	 * Passe à la prochaine section
	 */
	public void next();

	/**
	 * Retourne un iterateur sur la décomposition du trajet. Le premier element doit être une section de temps nulle qui
	 * à pour changenemt la station de départ.
	 * 
	 * @return
	 */
	public Iterator<SectionOfTravel> getTravel();

	/**
	 * Interface décrivant un portion du trajet, un protion du trajet est la partie du trajet qui est sur la même ligne,
	 * elle commence quand on arrive sur cette ligne et se termine quand on en ressort.
	 * 
	 * @author iGo
	 * 
	 */
	public interface SectionOfTravel {

		/**
		 * Retourne le temps nécessaire pour franchir cette partie du trajet. On ne compte pas ici le temps ensuite
		 * utilisé pour traversé le changement de ligne.
		 * 
		 * @return
		 */
		public int getTimeSection();

		/**
		 * Retourne le coût nécessaire pour franchir le changement terminant cette section.
		 * 
		 * @return le coût, il est positif ou null.
		 */
		public float getEnddingChangementCost();

		/**
		 * Retourne le nécessaire pour franchir le changement terminant cette section.
		 * 
		 * @return le temps en minutes
		 */
		public int getEnddingChangementTime();

		/**
		 * Retourne le nom de la ligne
		 * 
		 * @return
		 */
		public String getNameRoute();

		/**
		 * Retourne la route
		 * 
		 * @return
		 */
		public Route getRoute();

		/**
		 * Retourne le nom du la station qui sert de changement
		 * 
		 * @return
		 */
		public String getNameChangement();
	}

}
