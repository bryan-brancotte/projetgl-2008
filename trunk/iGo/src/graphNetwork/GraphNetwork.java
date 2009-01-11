package graphNetwork;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * 
 * @author iGo
 */
public class GraphNetwork {

	/**
	 * Liste des routes présentes dans le GraphNetwork
	 */
	protected Vector<Route> routes;
	/**
	 * Liste des services présents dans le GraphNetwork
	 */
	protected Vector<Service> services;
	/**
	 * Liste des stations présentes dans le GraphNetwork
	 */
	protected Vector<Station> stations;

	/**
	 * Constructeur d'un objet GraphNetwork
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
	 * Créé un nouvel objet PathInGraphCollectionBuilder. Ce dernier contient les deux builders pour définir un chemin à
	 * construire et pour le contruire. Chacun de ces builders possède une variable interne pathInGraph lui aussi
	 * nouvellement créé
	 * 
	 * @return l'instance de PathInGraphCollectionBuilder
	 */
	public PathInGraphCollectionBuilder getInstancePathInGraphCollectionBuilder() {
		PathInGraph pathInGraph = new PathInGraph(this);
		PathInGraphConstraintBuilder pathInGraphConstraintBuilder = new PathInGraphConstraintBuilder(pathInGraph);
		PathInGraphResultBuilder pathInGraphResultBuilder = new PathInGraphResultBuilder(pathInGraph);
		return new PathInGraphCollectionBuilder(pathInGraphConstraintBuilder, pathInGraphResultBuilder);
	}

	/**
	 * Créé un nouvel objet PathInGraphCollectionBuilder. Ce dernier contient les deux builders pour définir un chemin à
	 * construire et pour le contruire. Chacun de ces builders a comme variable interne un pathInGraph lui aussi
	 * nouvellement créé
	 * 
	 * @param pathInString
	 *            passe un PathInGraph sous forme XML
	 * @return l'instance de PathInGraphCollectionBuilder
	 */
	public PathInGraphCollectionBuilder getInstancePathInGraphCollectionBuilder(String pathInString) {
		PathInGraph pathInGraph = new PathInGraph(this);
		PathInGraphConstraintBuilder pathInGraphConstraintBuilder = new PathInGraphConstraintBuilder(pathInGraph);
		PathInGraphResultBuilder pathInGraphResultBuilder = new PathInGraphResultBuilder(pathInGraph);
		pathInGraph.importPath(pathInString);
		return new PathInGraphCollectionBuilder(pathInGraphConstraintBuilder, pathInGraphResultBuilder);
	}

	/**
	 * Créé un nouvel objet PathInGraphCollectionBuilder. Ce dernier contient les deux builders pour définir un chemin à
	 * construire et pour le contruire. Chacun de ces builders a comme variable interne un pathInGraph lui aussi
	 * nouvellement créé. On ne capture aucune exception en cas de problème.
	 * 
	 * @param pathInString
	 *            passe un PathInGraph sous forme XML
	 * @return l'instance de PathInGraphCollectionBuilder
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public PathInGraphCollectionBuilder getInstancePathInGraphCollectionBuilderWithoutCatch(String pathInString)
			throws SAXException, IOException, ParserConfigurationException {
		PathInGraph pathInGraph = new PathInGraph(this);
		PathInGraphConstraintBuilder pathInGraphConstraintBuilder = new PathInGraphConstraintBuilder(pathInGraph);
		PathInGraphResultBuilder pathInGraphResultBuilder = new PathInGraphResultBuilder(pathInGraph);
		pathInGraph.importPathWithoutCatch(pathInString);
		return new PathInGraphCollectionBuilder(pathInGraphConstraintBuilder, pathInGraphResultBuilder);
	}

	/**
	 * Retourne les changements présents entre les deux stations passées en paramètre.
	 * 
	 * @param stationA
	 *            la première station
	 * @param stationB
	 *            la seconde station
	 * @return un iterateur sur les changements existants
	 */
	public Iterator<Junction> getJunctions(Station stationA, Station stationB) {
		if (stationA == null || stationB == null)
			return null;
		Iterator<Junction> it1 = stationA.getJunctions();
		Junction temp;
		LinkedList<Junction> jonction = new LinkedList<Junction>();
		while (it1.hasNext()) {
			temp = it1.next();
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
	 * @return le type de route, ou null s'il n'existe pas
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
		for (Route route : routes)
			if (route.getId().compareTo(id) == 0)
				return route;
		return null;
	}

	/**
	 * Retourne un iterateur sur les routes existant dans le réseau
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
		for (Service service : services)
			if (service.getId() == id)
				return service;
		return null;
	}

	/**
	 * retourne un iterateur sur les services
	 * 
	 * @return un iterateur sur les services existants dans le reseau
	 */
	public Iterator<Service> getServices() {
		return services.iterator();
	}

	/**
	 * retourne la station dont on connait l'identifiant, null sinon
	 * 
	 * @param id
	 *            l'identifiant de la sation
	 * @return
	 */
	public Station getStation(int id) {
		for (Station station : stations)
			if (station.getId() == id)
				return station;
		return null;
	}

	/**
	 * retourne les stations existant dans le réseau
	 * 
	 * @return
	 */
	public Iterator<Station> getStations() { 
		return stations.iterator();
	}
}
