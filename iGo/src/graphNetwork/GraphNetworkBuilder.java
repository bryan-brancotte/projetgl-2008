package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.MissingResourceException;

/**
 * Classe suivant les design patterns Factory (création controlé d'objet) et Builder (modification controlé d'objet).
 * 
 * @author "iGo"
 * 
 */
public class GraphNetworkBuilder {

	/**
	 * le GraphNetwork courant, celui où on est actuellement en travail
	 */
	protected GraphNetwork actualGraphNetwork = null;

	/**
	 * constructeur du monteur de GraphNetwork
	 */
	public GraphNetworkBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé une nouvelle route et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant de la route, c'est aussi son nom
	 * @param kinfOf
	 *            sont type
	 * @return la nouvelle route créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Route addRoute(String id, String kinfOf) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé un nouveau service et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajout un service à un station. Si le service est déja présent, on ne fait
	 * rien.
	 * 
	 * @param station
	 *            la station en question
	 * @param serviceToAdd
	 *            le service en question
	 * @return true si le service n'était pas présent, et l'est désormais.
	 */
	public boolean addServiceToStation(Station station, Service serviceToAdd) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé une nouvelle station et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant de la station
	 * @param name
	 *            le nom de la station
	 * @return la station route créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Station addStation(int id, String name) throws ViolationOfUnicityInIdentificationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajout une station à un route. Si la station est déja présente, on ne fait
	 * rien.
	 * 
	 * @param route
	 *            la route en question
	 * @param stationToAdd
	 *            la station en question
	 * @param time
	 *            le temps entre la précédente station et celle ci
	 * @return true si la station n'était pas présent, et l'est désormais.
	 */
	public boolean addStationToRoute(Route route, Station stationToAdd, int time) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Définit le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
	 * 
	 * @param kind
	 *            le type de la ligne
	 * @param cost
	 *            le coût pour y accéder
	 */
	public void defineEntryCost(KindRoute kind, int cost) {
		// TODO Auto-generated method stub
	}

	/**
	 * retourne l'actuelle GraphNetwork.
	 * 
	 * @return le GraphNetwork courant ou null si'il n'y en a pas.
	 */
	protected GraphNetwork getActualGraphNetwork() {
		return actualGraphNetwork;
	}

	/**
	 * Créé initialise et retourne une instance de GraphNetwork.
	 * 
	 * @return une nouvelle instance de GraphNetwork
	 */
	public GraphNetwork getInstance() {
		// TODO Auto-generated method stub
		return new GraphNetwork();
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on lie deux station entre elles par un lien à faire à pied. On spécifie sur
	 * quelles routes sont les stations. On spécifie le cout monétaire et en temps pour emprunter le changement. On
	 * spécifie de plus si le lien est "long" c'est à dire fait resortir de la station pour rejoindre la seconde, sans
	 * pour autant impliquer une surtaxe.
	 * 
	 * @param routeOrigin
	 *            la route origine
	 * @param stationOrigin
	 *            la station origine
	 * @param routeDestination
	 *            la route destination
	 * @param stationDestination
	 *            la station destination
	 * @param cost
	 *            le coût pour emprunter le changement (positif ou null)
	 * @param time
	 *            le temps necessaire pour parcourir le chemin (positif ou null)
	 * @param pedestrian
	 *            ce lien est-il long?
	 * @throws StationNotOnRoadException
	 *             jeté si une station n'est pas sur la route que l'on suppose.
	 * @throws MissingResourceException
	 *             jeté si un des objet est null, ou si une valeur est incorrect.
	 */
	public void linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			MissingResourceException {
		// TODO Auto-generated method stub

	}

	/**
	 * Réinitialise le contenue de l'objet courant
	 */
	public void reset() {
		// TODO Auto-generated method stub

	}

	/**
	 * définit le GraphNetwork passé en paramètre comme le GraphNetwork courant.
	 * 
	 * @param actualGraphNetwork
	 *            le futur GraphNetwork courant.
	 */
	protected void setActualGraphNetwork(GraphNetwork actualGraphNetwork) {
		this.actualGraphNetwork = actualGraphNetwork;
	}
}
