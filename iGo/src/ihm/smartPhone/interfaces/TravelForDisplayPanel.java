package ihm.smartPhone.interfaces;

import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;

import java.util.Iterator;

public interface TravelForDisplayPanel {

	/**
	 * Retourne le nom de la station d'origine.
	 * 
	 * @return
	 */
	public String getOrigine();

	/**
	 * Retourne le nom de la station d'origine.
	 * 
	 * @return
	 */
	public Station getOrigineStation();

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
	 * Permet de savoir s'il reste des section à parcourir
	 */
	public boolean hasNext();

	/**
	 * Permet de savoir si le chemin est encore praticable à partir d'où on est actuellement
	 * 
	 * @return
	 */
	public boolean isValideFromWhereIAm();

	/**
	 * Retourne un iterateur sur la décomposition du trajet. Le premier element doit être la section en cours de
	 * parcourt, ou à parcourir.
	 * 
	 * @return
	 */
	public Iterator<SectionOfTravel> getTravelToDo();

	/**
	 * Retourne un iterateur sur la décomposition du trajet. la dernière section doit être celle qu'on vient de
	 * terminer.
	 * 
	 * @return
	 */
	public Iterator<SectionOfTravel> getTravelDone();

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
		 * Retourne le nombre de station dans la section.
		 * 
		 * @return
		 */
		public int getStationInSection();

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
		 * Retourne la listes des services
		 * 
		 * @return
		 */
		public Iterator<Service> getEnddingChangementServices();

		/**
		 * Retourne le nom du la station qui sert de changement
		 * 
		 * @return
		 */
		public String getNameChangement();

		/**
		 * Retourne la station qui sert de changement
		 * 
		 * @return
		 */
		public Station getChangement();
	}

}
