package graphNetwork;

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
	private LinkedList<Station> stationsDisabled;

	/**
	 * Constructeur d'un nouvelle objet avec un id spécifique et un type spécifique. L'unicité de l'id auprès des autre
	 * instance n'est pas vérifié.
	 * 
	 * @param id
	 *            l'identifiant de cette ligne
	 * @param kindRoute
	 *            le type de cette ligne
	 * @throws NullPointerException
	 *             une exception est jetté si un des deux paramètre est null
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
	 *            nouvel etat a appliquer a la route
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * réactive toute les stations qui était désativer dans la ligne
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
	 * ajoute une station a la route. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de
	 * façon sûr. Ajouter une Station manuellement à votre risque et péril.
	 * 
	 * @param station
	 *            la station a ajouter
	 */
	protected boolean addStation(Station station) {
		return addStation(station, 0);
	}

	/**
	 * Ajoute une station a la route. Attention, on autorise l'ajout multiple d'une station à une route
	 * 
	 * @param station
	 *            la station a ajouter
	 * @return true si l'ajout à bien eu lieu
	 */
	protected boolean addStation(Station station, int time) {
		// TODO time pas utilisé????
		return this.stations.add(station);
	}

	/**
	 * modifie l'etat enable d'une station
	 * 
	 * @param id
	 *            id de la station a modifier
	 * @param stationEnable
	 *            nouvel etat enable de la station
	 */
	public void setStationEnable(int idStation, boolean stationEnable) {
		// activation
		if (stationEnable) {
			stationsDisabled.remove(this.getStation(idStation));
			return;
		}
		// desactivation
		if (!isStationEnable(idStation))
			return;
		Station s;
		if ((s = this.getStation(idStation)) != null)
			stationsDisabled.add(s);
	}

	/**
	 * Retourne la station dont on vient passer l'identifiant si cette dernière est bien dans la route.
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
	 * retourne l'etat enable d'une station
	 * 
	 * @param id
	 *            id de la station recherchee
	 * @return etat false si la station est dans la route ET desactiver : si vous passer un id de station qui n'est pas
	 *         sur la route, nous la considererons comme active sur la ligne
	 */
	public boolean isStationEnable(int idStation) {
		if (!this.getStation(idStation).isEnable())
			return false;
		Iterator<Station> s1 = stationsDisabled.iterator();
		while (s1.hasNext())
			if ((s1.next()).getId() == idStation)
				return false;
		return true;
	}

}
