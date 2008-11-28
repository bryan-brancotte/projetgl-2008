package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class GraphNetwork {

	/**
	 * Liste des routes présente dans le GraphNetwork
	 */
	private LinkedList<Route> routes;

	/**
	 * Liste des services présente dans le GraphNetwork
	 */
	private LinkedList<Service> services;

	/**
	 * Liste des stations présente dans le GraphNetwork
	 */
	private LinkedList<Station> stations;

	/**
	 * Construteur d'un objet GraphNetwork
	 */
	protected GraphNetwork() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * retourne le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
	 * 
	 * @param kind
	 *            le type de la ligne
	 * @return le coût pour y accéder
	 */
	public float getEntryCost(KindRoute kind) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder déjà initialisé avec
	 * un nouveau trajet.
	 * 
	 * @return une nouvelle instance de PathInGraph
	 */
	public PathInGraphBuilder getInstancePathInGraphBuilder() {
		return new PathInGraphBuilder(this);
	}

	/**
	 * Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder initialisé avec le
	 * trajet passé en paramètre.
	 * 
	 * @return une nouvelle instance de PathInGraph
	 */
	public PathInGraphBuilder getInstancePathInGraphBuilder(PathInGraph path) {
		PathInGraphBuilder b =  new PathInGraphBuilder(this);
		b.setActualPathInGraph(path);
		return b;
	}

	/**
	 * Retourne les changements présente entre les deux stations passé en paramètres.
	 * 
	 * @param stationA
	 *            la première station
	 * @param stationB
	 *            la seconde station
	 * @return un iterateur sur les changements existant
	 */
	public Iterator<Junction> getJunctions(Station stationA, Station stationB) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Obtient l'objet représentant le type de route dont le nom est passé en paramètre
	 * 
	 * @param kindOf
	 *            le nom du type de route
	 * @return le type de route, ou null s'il n'existe aps
	 */
	public KindRoute getKindFromString(String kindOf) {
		return KindRoute.getKindFromString(kindOf);
	}

	/**
	 * retourne les types de route existants dans le réseau
	 * 
	 * @return un iterateur sur les types de route
	 */
	public Iterator<KindRoute> getKinds() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * retourne la route dont on connait l'identifiant
	 * 
	 * @param id
	 *            le nom/identifiant de la route
	 * @return la route ou null si elle n'existe pas
	 */
	public Route getRoute(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * retourne les routes existantes dans le réseau
	 * 
	 * @return un iterateur sur les routes
	 */
	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}

	/**
	 * retourne le service dont on connait l'identifiant
	 * 
	 * @param id
	 *            l'identifiant de la route
	 * @return le service ou null s'il n'existe pas
	 */
	public Service getService(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * retourne les services existants dans le réseau
	 * 
	 * @return un iterateur sur les services
	 */
	public Iterator<Service> getServices() {
		return services.iterator();
	}

	/**
	 * retourne la station dont on connait l'identifiant
	 * 
	 * @param id
	 *            l'identifiant de la station
	 * @return la station ou null si elle n'existe pas
	 */
	public Station getStation(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * retourne les stations existants dans le réseau
	 * 
	 * @return un iterateur sur les stations
	 */
	public Iterator<Station> getStations() {

		return stations.iterator();
	}

	/**
	 * remet les composants du réseau comme actif.
	 */
	public void resetEnables() {
		// TODO Auto-generated method stub

	}
}
