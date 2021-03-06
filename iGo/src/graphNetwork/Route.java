package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Route {

	private boolean enable = true;// @uml.property name="enable"
	private String id = "";// @uml.property name="id"
	private KindRoute kindRoute;// @uml.property name="kindRoute"
	protected LinkedList<Station> stations;// @uml.property name="stations"
	protected LinkedList<Station> stationsDisabled;

	/**
	 * Constructeur d'un nouvel objet avec un id spécifique et un type spécifique. L'unicité de l'id auprès des autres
	 * instances n'est pas vérifié.
	 * 
	 * @param id
	 *            l'identifiant de cette ligne
	 * @param kindRoute
	 *            le type de cette ligne
	 * @throws NullPointerException
	 *             une exception est jetée si un des deux paramètres est null
	 */
	protected Route(String id, KindRoute kindRoute) throws NullPointerException {
		super();
		if (id == null || kindRoute == null)
			throw new NullPointerException();
		this.id = id;
		this.kindRoute = kindRoute;
		this.stations = new LinkedList<Station>();
		this.stationsDisabled = new LinkedList<Station>();
	}

	/**
	 * retourne l'id de la route
	 * 
	 * @return l'id de la route
	 */
	public String getId() {
		return id;
	}

	/**
	 * retourne le type de la route
	 * 
	 * @return le type de la route
	 */
	public KindRoute getKindRoute() {
		return kindRoute;
	}

	/**
	 * retourne les stations d'une route
	 * 
	 * @return un iterateur sur les station de la route
	 */
	public Iterator<Station> getStations() {
		return stations.iterator();
	}

	/**
	 * retourne l'etat enable d'une route
	 * 
	 * @return etat enable de la route
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * setter de l'etat enable d'une route
	 * 
	 * @param enable
	 *            nouvel état a appliquer a la route
	 */
	public void setToEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * réactive toutes les stations qui était désativées dans la ligne
	 */
	public void resetDisabledStation() {
		this.stationsDisabled.clear();
	}

	/**
	 * setter de l'id d'une route
	 * 
	 * @param id
	 *            le service a jouter
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * setter du type de route
	 * 
	 * @param kindRoute
	 *            type de la route
	 */
	protected void setKindRoute(KindRoute kindRoute) {
		this.kindRoute = kindRoute;
	}

	/**
	 * Ajoute une station a la route. Attention, on autorise l'ajout multiple d'une station à une route
	 * 
	 * @param station
	 *            la station a ajouter
	 * @return true si l'ajout à bien eu lieu
	 */
	protected boolean addStation(Station station) {
		return this.stations.add(station);
	}

	/**
	 * Modifie l'etat enable d'une station
	 * 
	 * @param station
	 *            la station a modifier
	 * @param stationEnable
	 *            nouvel etat enable de la station
	 */
	protected void setStationEnable(Station station, boolean stationEnable) {
		// activation
		if (stationEnable) {
			stationsDisabled.remove(station);
			return;
		}
		// desactivation
		if (!isStationEnable(station))
			return;
		if (station != null)
			stationsDisabled.add(station);
	}

	/**
	 * Modifie l'etat enable d'une station
	 * 
	 * @param id
	 *            id de la station a modifier
	 * @param stationEnable
	 *            nouvel etat enable de la station
	 */
	protected void setStationToEnable(int idStation, boolean stationEnable) {
		setStationEnable(this.getStation(idStation), stationEnable);
	}

	/**
	 * Retourne la station dont on vient de passer l'identifiant si cette dernière est bien dans la route.
	 * 
	 * @param idStation
	 *            l'identifiant de la sation
	 * @return la station ou null si elle n'est pas dans la route
	 */
	public Station getStation(int idStation) {
		Iterator<Station> s1 = stations.iterator();
		Station s;
		while (s1.hasNext())
			if ((s = s1.next()).getId() == idStation)
				return s;
		return null;
	}

	/**
	 * Retourne l'etat enable d'une station
	 * 
	 * @param id
	 *            la station recherchée
	 * @return etat false si la station est desactivée, ou si elle est dans la route désactivée pour cette route.
	 */
	public boolean isStationEnable(Station station) {
		if (!station.isEnable())
			return false;
		Iterator<Station> s1 = stationsDisabled.iterator();
		while (s1.hasNext())
			if ((s1.next()) == station)
				return false;
		return true;
	}

	/**
	 * retourne l'etat enable d'une station
	 * 
	 * @param id
	 *            id de la station recherchée
	 * @return etat false si la station est dans la route ET desactivée : si vous passer un id de station qui n'est pas
	 *         sur la route, nous la considererons comme active sur la ligne
	 */
	public boolean isStationEnable(int idStation) {
		Station s;
		if ((s = this.getStation(idStation)) != null)
			return isStationEnable(s);
		return true;
	}

	public String toString() {
		return "Route : " + id;
	}

	/**
	 * Retourne la station de fin sur la ligne courante dans le sens stationOrigin vers stationDestination
	 * 
	 * @param stationA
	 * @param stationB
	 * @return
	 * @throws StationNotOnRoadException
	 */
	public Station getDirection(Station stationOrigin, Station stationDestination) throws StationNotOnRoadException {
		if (!this.stations.contains(stationOrigin))
			throw new StationNotOnRoadException("Route " + this + " does not contains " + stationOrigin);
		if (!this.stations.contains(stationDestination))
			throw new StationNotOnRoadException("Route " + this + " does not contains " + stationDestination);
		Iterator<Station> itS = this.stations.iterator();
		boolean thisWay = true;
		Station station = null;
		while (itS.hasNext()) {
			if ((station = itS.next()) == stationOrigin) {
				break;
			} else if (station == stationDestination) {
				thisWay = false;
			}
		}
		if (thisWay)
			return stations.getLast();
		else
			return stations.getFirst();
	}
}
