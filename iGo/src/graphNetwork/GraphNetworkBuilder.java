package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Iterator;
import java.util.MissingResourceException;

/**
 * 
 * Classe suivant les design patterns Factory (création controlé d'objet) et Builder (modification controlé d'objet).
 * 
 * @author "iGo"
 * 
 */
public class GraphNetworkBuilder {

	/**
	 * 
	 * le GraphNetwork courant, celui où on est actuellement en travail
	 * 
	 */
	protected GraphNetwork currentGraphNetwork = null;

	/**
	 * Constructeur du monteur de GraphNetwork
	 */
	public GraphNetworkBuilder() {
		super();
		currentGraphNetwork = new GraphNetwork();
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé une nouvelle route et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant de la route, c'est aussi son nom. s'il est à null ou vide rien ne se passera.
	 * @param kinfOf
	 *            son type. s'il est à null ou vide rien ne se passera.
	 * @return la nouvelle route créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Route addRoute(String id, String kindOf) throws ViolationOfUnicityInIdentificationException,
			NullPointerException {
		if (id == null)
			return null;
		if (kindOf == null)
			return null;
		if (id.isEmpty())
			return null;
		if (kindOf.isEmpty())
			return null;

		for (int i = 0; i < currentGraphNetwork.routes.size(); i++)
			if (currentGraphNetwork.routes.get(i).getId().compareTo(id) == 0)
				throw new ViolationOfUnicityInIdentificationException();

		Route route;
		currentGraphNetwork.routes.add(route = new Route(id, KindRoute.addKind(kindOf)));
		return route;

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
		return addService(id, name, null);
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé un nouveau service et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @param description
	 *            description rapide du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name, String description)
			throws ViolationOfUnicityInIdentificationException {
		if (name == null)
			return null;
		if (name.isEmpty())
			return null;

		for (int i = 0; i < currentGraphNetwork.services.size(); i++)
			if (currentGraphNetwork.services.get(i).getId() == id)
				throw new ViolationOfUnicityInIdentificationException();

		Service service;
		currentGraphNetwork.services.add(service = new Service(id, name, description));
		return service;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajout un service à un station. Si le service est déja présent, on
	 * ne fait rien.
	 * 
	 * @param station
	 *            la station en question
	 * @param serviceToAdd
	 *            le service en question
	 * @return true si le service n'était pas présent, et l'est désormais. false si un des paramètre est null, ou que le
	 *         paramètre était déjà présent
	 */
	public boolean addServiceToStation(Station station, Service serviceToAdd) {
		if (station == null)
			return false;
		if (serviceToAdd == null)
			return false;
		return station.addService(serviceToAdd);
		// if (station == null || serviceToAdd == null || !currentGraphNetwork.stations.contains(station))
		// return false;
		//
		// Iterator<Station> it = currentGraphNetwork.stations.iterator();
		// while (it.hasNext()) {
		// Station temp = it.next();
		// if (temp.equals(station)) { // pour la station en question
		// Iterator<Service> itS = temp.getServices();
		// while (itS.hasNext()) {
		// if (itS.next().equals(serviceToAdd)) // recherche si le
		// // service existe
		// // deja
		// return true;
		// }
		// temp.addService(serviceToAdd); // ajout si il n'existe pas
		// return true;
		// }
		// }
		// return false;
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
		if (name == null)
			return null;
		if (name.isEmpty())
			return null;

		for (int i = 0; i < currentGraphNetwork.stations.size(); i++)
			if (currentGraphNetwork.stations.get(i).getId() == id)
				throw new ViolationOfUnicityInIdentificationException();

		Station s;
		currentGraphNetwork.stations.add(s = new Station(id, name));
		return s;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajout une station à un route. Si la station est déja présente, on
	 * ne fait rien.
	 * 
	 * @param route
	 *            la route en question
	 * @param stationToAdd
	 *            la station en question
	 * @param time
	 *            le temps entre la précédente station et celle ci
	 * @return true si la station n'était pas présent, et l'est désormais.
	 * @throws ImpossibleValueException
	 */
	public boolean addStationToRoute(Route route, Station stationToAdd, int time) throws ImpossibleValueException {
		// TODO que faire avec time? + ajouter jonction station pcecedente -
		// stationtoadd
		if (time < 0)
			throw new ImpossibleValueException();
		if (route == null)
			return false;
		if (stationToAdd == null)
			return false;

		return route.addStation(stationToAdd, time);

		// Iterator<Route> it = currentGraphNetwork.routes.iterator();
		// while (it.hasNext()) {
		// Route temp = it.next();
		// if (temp.equals(route)) { // pour la route en question
		// Iterator<Station> itS = temp.getStations();
		// while (itS.hasNext()) {
		// Station temp3 = itS.next();
		// if (temp3.equals(stationToAdd)) { // recherche si la station
		// // existe deja
		// temp3.addRoute(route);
		// return true;
		// }
		// }
		// stationToAdd.addRoute(route);
		// temp.addStation(stationToAdd); // ajout si elle n'existe pas

		/*
		 * bloc ajouté pour creer route dans la station. Si pas necessaire, il faudra creer une nouvelle methode
		 * addRouteToStation
		 */
		// Iterator<Station> itt =
		// currentGraphNetwork.stations.iterator();
		// while(itt.hasNext()){
		// Station temp2=itt.next();
		// if(temp2.equals(stationToAdd)){
		// Iterator<Station> itR=temp2.g();
		// while(itR.hasNext())
		// temp2.addRoute(route);
		// }
		// }
		/* fin bloc */
		// return true;
		// }
		// }
		//
		// return false;
	}

	/**
	 * Définit le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
	 * 
	 * @param kind
	 *            le type de la ligne
	 * @param cost
	 *            le coût pour y accéder
	 * @throws ImpossibleValueException
	 */
	public void defineEntryCost(KindRoute kind, float cost) throws ImpossibleValueException {
		if (kind == null)
			return;
		if (cost < 0)
			throw new ImpossibleValueException();
		kind.setKindCost(cost);
	}

	/**
	 * @deprecated Use getCurrentGraphNetwork()
	 */
	@Deprecated
	public GraphNetwork getActualGraphNetwork() {
		return currentGraphNetwork;
	}

	/**
	 * retourne l'actuelle GraphNetwork.
	 * 
	 * @return le GraphNetwork courant ou null si'il n'y en a pas.
	 */
	public GraphNetwork getCurrentGraphNetwork() {
		return currentGraphNetwork;
	}

	/**
	 * Fonction abandonné a cause d'un nom pas assez explicite.
	 * 
	 * @return rien
	 */
	@Deprecated
	public GraphNetwork getInstance() {
		return new GraphNetwork();
	}

	/**
	 * 
	 * En travaillant sur le GraphNetwork courant, on lie deux station entre elles par un lien à faire à pied. On
	 * spécifie sur quelles routes sont les stations. On spécifie le cout monétaire et en temps pour emprunter le
	 * changement. On spécifie de plus si le lien est "long" c'est à dire qu'il fait resortir de la première station
	 * pour rejoindre la seconde, sans pour autant impliquer une surtaxe. <br/><B>La jonction est monodirectionel</B><br/>SI
	 * jamais la jonction existe déja on la modifie et la retourne.
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
	 * @return
	 * @throws StationNotOnRoadException
	 *             jetée si une station n'est pas sur la route que l'on suppose.
	 * @throws NullPointerException
	 *             jetée si un des composant est null.
	 * @throws ImpossibleValueException
	 *             jetée si une valeur est incorrect.
	 */
	public Junction linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			NullPointerException, ImpossibleValueException {

		// boolean sameStation;
		// boolean sameRoute;
		if (routeOrigin == null)
			throw new NullPointerException();
		if (stationOrigin == null)
			throw new NullPointerException();
		if (routeDestination == null)
			throw new NullPointerException();
		if (stationDestination == null)
			throw new NullPointerException();

		if (!routeOrigin.stations.contains(stationOrigin))
			throw new StationNotOnRoadException();
		if (!routeDestination.stations.contains(stationDestination))
			throw new StationNotOnRoadException();

		if (cost < 0)
			throw new ImpossibleValueException();
		if (time < 0)
			throw new ImpossibleValueException();
		//
		// sameStation = stationOrigin == stationDestination;
		// if ((sameRoute = (routeOrigin == routeDestination)) && sameStation)
		// throw new ImpossibleValueException();

		Iterator<Junction> itJ = stationOrigin.junctions.iterator();
		Junction j;
		Junction ret = null;
		// TODO .......work
		while (itJ.hasNext()) {
			j = itJ.next();
			//               A    A        B    B
			// on a pour j:Massy RerC => Massy RerB
			// les params :Massy RerB => Palaiseau RerB
			if (j.getOtherStation(stationOrigin) == stationDestination
					&& (j.isRouteLink() || j.getOtherRoute(stationOrigin) == routeDestination))
				ret = j;
			// if (sameStation) {
			// if (j.isSameStation()) {
			// }
			// } else {
			// if (j.getOtherStation(stationOrigin) == stationDestination) {
			// }
			// }
			// if (sameRoute) {
			// if (j.isSameRoute()) {
			// }
			// } else {
			// if (j.getOtherRoute(stationOrigin) == routeDestination) {
			// }
			// }

		}
		return null;
		// boolean done = false;
		// Iterator<Route> itR = currentGraphNetwork.routes.iterator();
		//
		// Junction jOrigine = new Junction(routeOrigin, stationOrigin, routeDestination, stationDestination, cost,
		// time,
		// true, pedestrian);
		// Junction jDestination = new Junction(routeDestination, stationDestination, routeOrigin, stationOrigin, cost,
		// time, true, pedestrian);
		// Junction jRetour = null;
		//
		// while (itR.hasNext()) {
		// Route temp = itR.next();
		// if (temp.equals(routeOrigin)) { // dans la route origine
		// Iterator<Station> itS = temp.getStations();
		// while (itS.hasNext()) {
		// Station temp2 = itS.next();
		// if (temp2.equals(stationOrigin)) {// pour la station origine
		// temp2.addJunction(jOrigine);// ajout d'une jonction vers
		// jRetour = jOrigine; // la station dest
		// done = true;
		// }
		// }
		// }
		// if (temp.equals(routeDestination)) { // dans la route destination
		// Iterator<Station> itS = temp.getStations();
		// while (itS.hasNext()) {
		// Station temp2 = itS.next();
		// if (temp2.equals(stationDestination)) {// pour la station
		// // destination
		// temp2.addJunction(jDestination);// ajout d'une jonction
		// jRetour = jDestination; // vers la station
		// // origine
		// done = true;
		// }
		// }
		// }
		// }
		// if (!done)
		// throw new ImpossibleValueException();
		//
		// return jRetour;
	}

	/**
	 * Réinitialise le contenue de l'objet courant : on vide l'ensemble des conteneurs
	 */
	public void reset() {
		currentGraphNetwork.routes.clear();
		currentGraphNetwork.stations.clear();
		currentGraphNetwork.services.clear();
		KindRoute.reset();
	}

	/**
	 * @deprecated Use setCurrentGraphNetwork()
	 */
	protected void setActualGraphNetwork(GraphNetwork currentGraphNetwork) {
		this.currentGraphNetwork = currentGraphNetwork;
	}

	/**
	 * définit le GraphNetwork passé en paramètre comme le GraphNetwork courant.
	 * 
	 * @param currentGraphNetwork
	 *            le futur GraphNetwork courant.
	 */
	protected void setCurrentGraphNetwork(GraphNetwork currentGraphNetwork) {
		this.currentGraphNetwork = currentGraphNetwork;
	}

	/* test perso a virer */
	public Station getStationTest(int id) {
		Iterator<Station> it = currentGraphNetwork.getStations();

		while (it.hasNext()) {
			Station temp = it.next();
			if (temp.getId() == id)
				return temp;
		}
		return null;

	}

	/* test perso a virer */
	public Service getServiceTest(int id) {
		Iterator<Service> it = currentGraphNetwork.getServices();

		while (it.hasNext()) {
			Service temp = it.next();
			if (temp.getId() == id)
				return temp;
		}
		return null;

	}
}
