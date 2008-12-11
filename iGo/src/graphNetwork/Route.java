package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Route {

	private boolean enable = true;//@uml.property name="enable"
	private String id = "";//@uml.property name="id"
	private KindRoute kindRoute;//@uml.property name="kindRoute"
	private LinkedList<Station> stations;//@uml.property name="stations"

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
		this.id = id;
		this.kindRoute = kindRoute;
		this.stations = new LinkedList<Station>();
		this.stationsDisabled = new LinkedList<Station>();
	}

	public String getId() {
		return id;
	}

	/**
	 * Getter of the property <tt>kindRoute</tt>
	 * 
	 * @return Returns the kindRoute.
	 * @uml.property name="kindRoute"
	 */
	public KindRoute getKindRoute() {
		return kindRoute;
	}

	/**
	 * Getter of the property <tt>stations</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="stations"
	 */
	public Iterator<Station> getStations() {
		return stations.iterator();
	}

	public boolean isEnable() {
		return enable;
	}

	/**
	 * Setter of the property <tt>enable</tt>
	 * 
	 * @param enable
	 *            The enable to set.
	 * @uml.property name="enable"
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter of the property <tt>kindRoute</tt>
	 * 
	 * @param kindRoute
	 *            The kindRoute to set.
	 * @uml.property name="kindRoute"
	 */
	protected void setKindRoute(KindRoute kindRoute) {
		this.kindRoute = kindRoute;
	}

	/**
	 * Ajoute un station à cette ligne. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et
	 * de façon sûr. Ajouter une Station manuellement à votre risque et péril.
	 * 
	 * @param station
	 */
	public void addStation(Station station) {
		this.stations.add(station);
	}

	/**
	 * Set the station who have the id given in parameter to the status stationEnable if the staion is on this route
	 * 
	 * @param station
	 *            the station to enable/disable
	 * @param stationEnable
	 *            enable or disable the station
	 */
	public void setStationEnable(int idStation, boolean stationEnable) {
	}

	/**
	 * @uml.property name="stationsDisabled"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="route:graphNetwork.Station"
	 */
	private LinkedList<Station> stationsDisabled;

	public boolean isStationEnable(int idStation) {
		// TODO Auto-generated method stub
		if (stationsDisabled == null)
			stationsDisabled = null;
		return false;
	}

}
