package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 
 * Classe qui utilise les design patterns Factory (création controlée d'objets) et Builder (modification controlée
 * d'objets).
 * 
 * @author "iGo"
 * 
 */
public class GraphNetworkBuilder {

	/**
	 * 
	 * le GraphNetwork courant
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
	 * En travaillant sur le GraphNetwork courant, on crée une nouvelle route et on l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé en paramètre existe déjà on renvoie une exception.
	 * 
	 * @param id
	 *            l'identifiant de la route, c'est aussi son nom. s'il est à null ou vide rien ne se passera.
	 * @param kinfOf
	 *            son type. s'il est à null ou vide rien ne se passera.
	 * @return la nouvelle route créée
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception renvoyée si l'identifiant est déja utilisé.
	 */
	public Route addRoute(String id, String kindOf) throws ViolationOfUnicityInIdentificationException,
			NullPointerException {
		if (id == null)
			return null;
		if (kindOf == null)
			return null;
		if (id.compareTo("") == 0)
			return null;
		if (kindOf.compareTo("") == 0)
			return null;

		for (int i = 0; i < currentGraphNetwork.routes.size(); i++)
			if (currentGraphNetwork.routes.get(i).getId().compareTo(id) == 0)
				throw new ViolationOfUnicityInIdentificationException("The road " + id + "already existe");

		Route route;
		currentGraphNetwork.routes.add(route = new Route(id, KindRoute.addKind(kindOf)));
		return route;

	}

	/**
	 * En travaillant sur le GraphNetwork courant, on crée un nouveau service et on l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on renvoie une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception renvoyée si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException {
		return addService(id, name, null);
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé un nouveau service et on l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on renvoie une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @param description
	 *            description rapide du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception renvoyé si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name, String description)
			throws ViolationOfUnicityInIdentificationException {
		if (name == null)
			return null;
		if (name.compareTo("") == 0)
			return null;

		for (int i = 0; i < currentGraphNetwork.services.size(); i++)
			if (currentGraphNetwork.services.get(i).getId() == id)
				throw new ViolationOfUnicityInIdentificationException("The service " + id + "already existe");

		Service service;
		currentGraphNetwork.services.add(service = new Service(id, name, description));
		return service;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajoute un service à un station. Si le service est déja présent, on
	 * ne fait rien.
	 * 
	 * @param station
	 *            la station en question
	 * @param serviceToAdd
	 *            le service en question
	 * @return true si le service n'était pas présent, mais l'est désormais. false si un des paramètres est null, ou si
	 *         le paramètre était déjà présent
	 */
	public boolean addServiceToStation(Station station, Service serviceToAdd) {
		if (station == null)
			return false;
		if (serviceToAdd == null)
			return false;
		return station.addService(serviceToAdd);
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on crée une nouvelle station et l'ajoute au GraphNetwork courant. Si
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
		if (name.compareTo("") == 0)
			return null;

		for (int i = 0; i < currentGraphNetwork.stations.size(); i++)
			if (currentGraphNetwork.stations.get(i).getId() == id)
				throw new ViolationOfUnicityInIdentificationException("The station " + id + "already existe");

		Station s;
		currentGraphNetwork.stations.add(s = new Station(id, name));
		return s;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajoute une station à un route. Si la station est déja présente, on
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
		// stationtoadd
		if (time < 0)
			throw new ImpossibleValueException("Adding " + stationToAdd + " to " + route + " : time should >=0 (here="
					+ time);
		if (route == null)
			return false;
		if (stationToAdd == null)
			return false;

		Station lastStation = null;
		if (!route.stations.isEmpty())
			lastStation = route.stations.getLast();
		route.addStation(stationToAdd);
		stationToAdd.addRoute(route);

		if (lastStation != null) {
			Junction j;
			try {
				j = new Junction(route, lastStation, route, stationToAdd, 0, time, true, false);
			} catch (StationNotOnRoadException e) {
				e.printStackTrace();
				return false;
			}
			lastStation.addJunction(j);
			stationToAdd.addJunction(j);
		}
		return true;
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
			throw new ImpossibleValueException("Defining entry cost for " + kind + " should be with a cost >=0 (here="
					+ cost);
		kind.setKindCost(cost);
	}

	/**
	 * retourne le GraphNetwork courant.
	 * 
	 * @return le GraphNetwork courant ou null s'il n'y en a pas.
	 */
	public GraphNetwork getCurrentGraphNetwork() {
		return currentGraphNetwork;
	}

	/**
	 * 
	 * En travaillant sur le GraphNetwork courant, on lie deux stations entre elles par un lien à faire à pied. On
	 * spécifie sur quelles routes sont les stations. On spécifie le cout monétaire et en temps pour emprunter le
	 * changement. On spécifie de plus si le lien est "long" c'est à dire qu'il faut resortir de la première station
	 * pour rejoindre la seconde, sans pour autant impliquer une surtaxe. <br/>
	 * <B>La jonction est monodirectionel</B><br/>
	 * SI jamais la jonction existe déja on la modifie et la retourne.
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
			Station stationDestination, float cost, int timeBetweenStations, boolean pedestrian)
			throws StationNotOnRoadException, NullPointerException, ImpossibleValueException {

		if (routeOrigin == null)
			throw new NullPointerException("routeOrigin is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (stationOrigin == null)
			throw new NullPointerException("stationOrigin is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (routeDestination == null)
			throw new NullPointerException("routeDestination is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (stationDestination == null)
			throw new NullPointerException("stationDestination is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);

		if (!routeOrigin.stations.contains(stationOrigin))
			throw new StationNotOnRoadException("Route " + routeOrigin + " does not contains " + stationOrigin);
		if (!routeDestination.stations.contains(stationDestination))
			throw new StationNotOnRoadException("Route " + routeDestination + " does not contains "
					+ stationDestination);

		if (cost < 0)
			throw new ImpossibleValueException("Linking " + stationOrigin + " and " + stationDestination
					+ " : cost should >=0 (here=" + cost);
		if (timeBetweenStations < 0)
			throw new ImpossibleValueException("Linking " + stationOrigin + " and " + stationDestination
					+ " : timeBetweenStations should >=0 (here=" + timeBetweenStations);

		Iterator<Junction> itJ = stationOrigin.junctions.iterator();
		Junction j;
		while (itJ.hasNext()) {
			j = itJ.next();
			if (j.equals(routeOrigin, stationOrigin, routeDestination, stationDestination)) {
				j.setCost(cost);
				j.setPedestrian(pedestrian);
				j.setTimeBetweenStations(timeBetweenStations);
				return j;
			}
		}
		j = new Junction(routeOrigin, stationOrigin, routeDestination, stationDestination, cost, timeBetweenStations,
				routeDestination == routeOrigin, pedestrian);
		stationOrigin.addJunction(j);
		return j;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on lie deux stations entre elles par un lien à faire à pied. On
	 * spécifie sur quelles routes sont les stations. On spécifie le cout monétaire et en temps pour emprunter le
	 * changement. On spécifie de plus si le lien est "long" c'est à dire qu'il fait resortir de la première station
	 * pour rejoindre la seconde, sans pour autant impliquer une surtaxe. <br/>
	 * <B>Les jonctions sont monodirectionelles, mais on en crée 2!</B><br/>
	 * Si dans un sens comme dans l'autre on trouve une jonction correspondant au critère, on la met à jour plutôt que
	 * d'en créer une nouvelle.
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
	 *             jetée si un des composants est null.
	 * @throws ImpossibleValueException
	 *             jetée si une valeur est incorrect.
	 */
	public void linkStationBidirectional(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int timeBetweenStations, boolean pedestrian)
			throws StationNotOnRoadException, NullPointerException, ImpossibleValueException {

		if (routeOrigin == null)
			throw new NullPointerException("routeOrigin is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (stationOrigin == null)
			throw new NullPointerException("stationOrigin is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (routeDestination == null)
			throw new NullPointerException("routeDestination is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);
		if (stationDestination == null)
			throw new NullPointerException("stationDestination is null (cost is " + cost + "$ and time is"
					+ timeBetweenStations);

		if (!routeOrigin.stations.contains(stationOrigin))
			throw new StationNotOnRoadException("Route " + routeOrigin + " does not contains " + stationOrigin);
		if (!routeDestination.stations.contains(stationDestination))
			throw new StationNotOnRoadException("Route " + routeDestination + " does not contains "
					+ stationDestination);

		if (cost < 0)
			throw new ImpossibleValueException("Linking " + stationOrigin + " and " + stationDestination
					+ " : cost should >=0 (here=" + cost);
		if (timeBetweenStations < 0)
			throw new ImpossibleValueException("Linking " + stationOrigin + " and " + stationDestination
					+ " : timeBetweenStations should >=0 (here=" + timeBetweenStations);

		Iterator<Junction> itJ;
		Junction jOD = null;
		Junction jDO = null;

		// Jonction de Origine vers Destination
		itJ = stationOrigin.junctions.iterator();
		while (jOD == null && itJ.hasNext()) {
			jOD = itJ.next();
			if (jOD.equals(routeOrigin, stationOrigin, routeDestination, stationDestination)) {
				jOD.setCost(cost);
				jOD.setPedestrian(pedestrian);
				jOD.setTimeBetweenStations(timeBetweenStations);
			} else
				jOD = null;
		}
		if (jOD == null) {
			jOD = new Junction(routeOrigin, stationOrigin, routeDestination, stationDestination, cost,
					timeBetweenStations, routeDestination == routeOrigin, pedestrian);
			stationOrigin.addJunction(jOD);
		}

		// Jonction de Destination vers Origine
		itJ = stationDestination.junctions.iterator();
		while (jDO == null && itJ.hasNext()) {
			jDO = itJ.next();
			if (jDO.equals(routeDestination, stationDestination, routeOrigin, stationOrigin)) {
				jDO.setCost(cost);
				jDO.setPedestrian(pedestrian);
				jDO.setTimeBetweenStations(timeBetweenStations);
				return;
			}
		}
		jDO = new Junction(routeDestination, stationDestination, routeOrigin, stationOrigin, cost, timeBetweenStations,
				routeDestination == routeOrigin, pedestrian);
		stationDestination.addJunction(jDO);
	}

	/**
	 * Réinitialise le contenu de l'objet courant : on vide l'ensemble des conteneurs
	 */
	public void reset() {
		currentGraphNetwork.routes.clear();
		currentGraphNetwork.stations.clear();
		currentGraphNetwork.services.clear();
		KindRoute.reset();
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

	/**
	 * setter de l'etat enable d'une station
	 * 
	 * @param station
	 *            la station à modifier.
	 * @param enable
	 *            etat à definir
	 * @return void
	 */
	public void setEnable(Station station, boolean enable) {
		if (station != null)
			station.setToEnable(enable);
	}

	/**
	 * setter de l'etat enable d'une station
	 * 
	 * @param station
	 *            l'id de la station à modifier.
	 * @param enable
	 *            etat à definir
	 * @return void
	 */
	public void setEnable(int idStation, boolean enable) {
		this.setEnable(this.currentGraphNetwork.getStation(idStation), enable);
	}

	/**
	 * modifie l'etat enable d'une station
	 * 
	 * @param route
	 *            la route sur laquelle est la station.
	 * @param station
	 *            la station à modifier.
	 * @param stationEnable
	 *            etat à definir
	 */
	public void setEnable(Route route, Station station, boolean enable) {
		if (route != null)
			route.setStationEnable(station, enable);
	}

	/**
	 * modifie l'etat enable d'une station
	 * 
	 * @param route
	 *            la route sur laquelle est la station.
	 * @param station
	 *            la station à modifier.
	 * @param stationEnable
	 *            etat à definir
	 */
	public void setEnable(String idRoute, int idStation, boolean enable) {
		this.setEnable(currentGraphNetwork.getRoute(idRoute), currentGraphNetwork.getStation(idStation), enable);
	}

	/**
	 * setter de l'etat enable d'une route
	 * 
	 * @param route
	 *            la route à modifier.
	 * @param enable
	 *            etat à definir
	 * @return void
	 */
	public void setEnable(Junction junction, boolean enable) {
		if (junction != null)
			junction.setToEnable(enable);
	}

	/**
	 * setter de l'etat enable d'une route
	 * 
	 * @param route
	 *            la route à modifier.
	 * @param enable
	 *            etat à definir
	 * @return void
	 */
	public void setEnable(Route route, boolean enable) {
		if (route != null)
			route.setToEnable(enable);
	}

	/**
	 * setter de l'etat enable d'une route
	 * 
	 * @param route
	 *            l'id de la route à modifier.
	 * @param enable
	 *            etat à definir
	 * @return void
	 */
	public void setEnable(String idRoute, boolean enable) {
		if (this.currentGraphNetwork.getRoute(idRoute) == null)
			System.err.println("Route unkown : " + idRoute);
		else
			this.setEnable(this.currentGraphNetwork.getRoute(idRoute), enable);
	}

	/**
	 * Trouve la/les jonction()s entre les deux stations sur leurs routes respectives et leur applique l'état passé en
	 * paramètre
	 * 
	 * @param stationOrigin
	 *            la première station
	 * @param routeOrigin
	 *            la route de la première station
	 * @param stationDestination
	 *            la seconde station
	 * @param routeDestination
	 *            la route de la seconde station
	 * @param enable
	 *            etat à definir
	 * @throws StationNotOnRoadException
	 *             jetée si une station n'est pas sur la route que l'on suppose.
	 * @throws NullPointerException
	 *             jetée si un des composants est null.
	 */
	public void setEnableJunctionsBetween(String idRouteOrigin, int idStationOrigin, String idRouteDestination,
			int idStationDestination, boolean enable) throws StationNotOnRoadException {
		this.setEnableJunctionsBetween(this.currentGraphNetwork.getRoute(idRouteOrigin), this.currentGraphNetwork
				.getStation(idStationOrigin), this.currentGraphNetwork.getRoute(idRouteDestination),
				this.currentGraphNetwork.getStation(idStationDestination), enable);
	}

	/**
	 * Trouve la/les jonction()s entre les deux stations sur leurs routes respectives et leur applique l'état passé en
	 * paramètre
	 * 
	 * @param stationOrigin
	 *            la première station
	 * @param routeOrigin
	 *            la route de la première station
	 * @param stationDestination
	 *            la seconde station
	 * @param routeDestination
	 *            la route de la seconde station
	 * @param enable
	 *            etat à definir
	 * @throws StationNotOnRoadException
	 *             jetée si une station n'est pas sur la route que l'on suppose.
	 * @throws NullPointerException
	 *             jetée si un des composants est null.
	 */
	public void setEnableJunctionsBetween(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, boolean enable) throws StationNotOnRoadException {

		if (routeOrigin == null)
			throw new NullPointerException("routeOrigin is null");
		if (stationOrigin == null)
			throw new NullPointerException("stationOrigin is null");
		if (routeDestination == null)
			throw new NullPointerException("routeDestination is null");
		if (stationDestination == null)
			throw new NullPointerException("stationDestination is null");

		if (!routeOrigin.stations.contains(stationOrigin))
			throw new StationNotOnRoadException("Route " + routeOrigin + " does not contains " + stationOrigin);
		if (!routeDestination.stations.contains(stationDestination))
			throw new StationNotOnRoadException("Route " + routeDestination + " does not contains "
					+ stationDestination);

		Iterator<Junction> itJ = stationOrigin.getJunctions(routeOrigin);
		Junction junction;
		while (itJ.hasNext()) {
			if ((junction = itJ.next()).haveOnASide(routeDestination, stationDestination))
				junction.setToEnable(enable);
		}
	}

	/**
	 * Remet les composants du réseau comme actif : on réactive les routes, les stations, les stations relatives aux
	 * routes, aux jonctions.
	 */
	public void resetEnables() {
		Iterator<Route> itRoute = currentGraphNetwork.routes.iterator();
		Iterator<Station> itStation = currentGraphNetwork.stations.iterator();
		Route route;
		Station station;
		Iterator<Junction> itJunction;

		while (itRoute.hasNext()) {
			(route = itRoute.next()).setToEnable(true);
			route.resetDisabledStation();
		}

		while (itStation.hasNext()) {
			itJunction = (station = itStation.next()).getJunctions();
			while (itJunction.hasNext())
				itJunction.next().setToEnable(true);
			station.setToEnable(true);
		}
	}

	/**
	 * Trie tous les objets (routes, stations,...) contenus par ordre alphabétique.
	 */
	public void sortMembers() {
		// routes services stations;
		Collections.sort(currentGraphNetwork.routes, new Comparator<Route>() {
			@Override
			public int compare(Route r1, Route r2) {
				return r1.getId().compareTo(r2.getId());
			}
		});
		Collections.sort(currentGraphNetwork.services, new Comparator<Service>() {
			@Override
			public int compare(Service s1, Service s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
		Collections.sort(currentGraphNetwork.stations, new Comparator<Station>() {
			@Override
			public int compare(Station s1, Station s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
	}
}
