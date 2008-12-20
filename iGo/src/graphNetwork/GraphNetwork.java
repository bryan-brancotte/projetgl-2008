package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/**
 * 
 * @author iGo
 */
public class GraphNetwork {

	/**
	 * Liste des routes présente dans le GraphNetwork
	 */
	protected Vector<Route> routes;
	/**
	 * Liste des services présente dans le GraphNetwork
	 */
	protected Vector<Service> services;
	/**
	 * Liste des stations présente dans le GraphNetwork
	 */
	protected Vector<Station> stations;

	/**
	 * Construteur d'un objet GraphNetwork
	 */
	protected GraphNetwork() {
		super();
		routes = new Vector<Route>();
		services = new Vector<Service>();
		stations = new Vector<Station>();
		if (KindRoute.kinds != null)
			KindRoute.kinds.clear();
	}

	/**
	 * retourne le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
	 * 
	 * @param kind
	 * @return
	 * @throws NullPointerException
	 */
	public float getEntryCost(KindRoute kind) throws NullPointerException {
		if (kind == null) {
			return Float.NaN;
		}
		return kind.cost;
	}

	/**
	 * Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder déjà initialisé avec
	 * un nouveau trajet.
	 * 
	 * @return
	 */
	public PathInGraphBuilder getInstancePathInGraphBuilder() {
		// TODO PathInGraphBuilder à evoluer
		return new PathInGraphBuilder(this);
	}

	/**
	 * 
	 * Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder initialisé avec le
	 * trajet passé en paramètre.
	 * 
	 * @param path
	 *            le path à mettre dans le monteur.
	 * @return le monteur, ou null si le path est null.
	 */
	public PathInGraphBuilder getInstancePathInGraphBuilder(PathInGraph path) {
		if (path == null)
			return null;
		PathInGraphBuilder b = new PathInGraphBuilder(this,path);
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
		// Check by bryan
		if (stationA == null || stationB == null)
			return null;
		Iterator<Junction> it1 = stationA.getJunctions();
		Junction temp;
		LinkedList<Junction> jonction = new LinkedList<Junction>();
		while (it1.hasNext()) {
			temp = it1.next();
//			System.out.println(temp);
			if (temp.getOtherStation(stationA).equals(stationB))
				jonction.add(temp);
		}
		return jonction.iterator();
	}

	/**
	 * Obtient l'objet représentant le type de route dont le nom est passé en paramètre
	 * 
	 * @param kindOf
	 *            le nom du type de route
	 * @return le type de route, ou null s'il n'existe aps
	 */
	public KindRoute getKindFromString(String kindOf) {
		if (kindOf == null)
			return null;
		return KindRoute.getKindFromString(kindOf);
	}

	/**
	 * retourne les types de route existants dans le réseau
	 * 
	 * @return un iterateur sur les types de route
	 */
	public Iterator<KindRoute> getKinds() {
		return KindRoute.getKinds();
	}

	/**
	 * Retourne la route dont on connait l'identifiant ou null si elle n'existe pas
	 * 
	 * @param id
	 * @return
	 */
	public Route getRoute(String id) {
		// Iterator<Route> it = routes.iterator();
		// while (it.hasNext()) {
		// Route temp = it.next();
		// if (temp.getId().compareTo(id) == 0) {
		// return temp;
		// }
		// }
		// return null;
		for (int i = 0; i < routes.size(); i++)
			if (routes.get(i).getId().compareTo(id) == 0)
				return routes.get(i);
		return null;
	}

	/**
	 * Retourne un iterateur sur les routes existantes dans le réseau
	 * 
	 * @return
	 */
	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}

	/**
	 * Retourne le service dont on connait l'identifiant ou null s'il n'existe pas
	 * 
	 * @param id
	 * @return
	 */
	public Service getService(int id) {
		// Iterator<Service> it = services.iterator();
		// while (it.hasNext()) {
		// Service s1 = it.next();
		// if (s1.getId() == id) {
		// return s1;
		// }
		// }
		// return null;
		for (int i = 0; i < services.size(); i++)
			if (services.get(i).getId() == id)
				return services.get(i);
		return null;
	}

	/**
	 * retourne un iterateur sur les services
	 * 
	 * @return un iterateur sur les services existants dans le reseau
	 */
	public Iterator<Service> getServices() {// 
		return services.iterator();
	}

	/**
	 * retourne la station dont on connait l'identifiant, null sinon
	 * 
	 * @param id
	 *            l'identifiant de la sation
	 * @return
	 */
	public Station getStation(int id) {// 
		// Iterator<Station> it = stations.iterator();
		// while (it.hasNext()) {
		// Station temp = it.next();
		// if (temp.getId() == id) {
		// return temp;
		// }
		// }
		// return null;
		for (int i = 0; i < stations.size(); i++)
			if (stations.get(i).getId() == id)
				return stations.get(i);
		return null;
	}

	/**
	 * retourne les stations existants dans le réseau
	 * 
	 * @return
	 */
	public Iterator<Station> getStations() {// 
		return stations.iterator();
	}

	/**
	 * remet les composants du réseau comme actif : on réactive les routes, les stations, les stations relative au
	 * route, les jonctions.
	 */
	public void resetEnables() {
		Iterator<Route> itRoute = routes.iterator();
		Iterator<Station> itStation = stations.iterator();
		Route route;
		Station station;
		Iterator<Junction> itJunction;

		while (itRoute.hasNext()) {
			(route = itRoute.next()).setEnable(true);
			route.resetDisabledStation();
		}

		while (itStation.hasNext()) {
			itJunction = (station = itStation.next()).getJunctions();
			while (itJunction.hasNext())
				itJunction.next().setEnable(true);
			station.setEnable(true);
		}
	}
}
