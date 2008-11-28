package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;

/**
 * 
 * @author iGo
 */
public class Junction {

	private float cost = 0F;

	private boolean enable = true;

	private boolean pedestrian = false;
	
	private Route routeA;

	private Route routeB;

	private Station stationB;

	private Station stationA;

	private int timeBetweenStations = 0;

	protected void setCost(float cost) {
		this.cost = cost;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	public float getCost() {
		return cost;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isPedestrian() {
		return pedestrian;
	}

	public int getTimeBetweenStations() {
		return timeBetweenStations;
	}

	/**
	 * return the route of the station for this junction.
	 * 
	 * @param me
	 *            the station you know in the junction
	 * @return the route of other station, or null of the specified station isn't one of the two station
	 */
	public Route getRoute(Station me) {
		if (me.getId() == this.getStationA().getId())
			return this.getRouteA();
		if (me.getId() == this.getStationB().getId())
			return this.getRouteB();
		return null;
	}
	
	/**
	 * return the route of other station of an junction. You have to give one station to give the other
	 * 
	 * @param me
	 *            the station you know in the junction
	 * @return the route of other station, or null of the specified station isn't one of the two station
	 */
	public Route getOtherRoute(Station me) {
		if (me.getId() == this.getStationA().getId())
			return this.getRouteB();
		if (me.getId() == this.getStationB().getId())
			return this.getRouteA();
		return null;
	}

	/**
	 * return the other station of an junction. You have to give one station to give the other
	 * 
	 * @param me
	 *            the station you know in the junction
	 * @return the other station, or null of the specified station isn't one of the two station
	 */
	public Station getOtherStation(Station me) {
		if (me.getId() == this.getStationA().getId())
			return this.getStationB();
		if (me.getId() == this.getStationB().getId())
			return this.getStationA();
		return null;
	}

	/**
	 * Getter of the property <tt>routeA</tt>
	 * 
	 * @return Returns the routeA.
	 * @uml.property name="routeA"
	 */
	public Route getRouteA() {
		return routeA;
	}

	/**
	 * Getter of the property <tt>routeB</tt>
	 * 
	 * @return Returns the routeB.
	 * @uml.property name="routeB"
	 */
	public Route getRouteB()

	{
		return routeB;
	}

	/**
	 * Getter of the property <tt>stationA</tt>
	 * 
	 * @return Returns the stationA.
	 * @uml.property name="stationA"
	 */
	public Station getStationA() {
		return stationA;
	}

	/**
	 * Getter of the property <tt>stationB</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="stationB"
	 */
	public Station getStationB() {
		return stationB;
	}

	/**
	 * Mutateur de routeA. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr.
	 * Définisez la Route manuellement à votre risque et péril.
	 * 
	 * @param routeA
	 *            The routeA to set.
	 * @uml.property name="routeA"
	 */
	protected void setRouteA(Route routeA) {
		this.routeA = routeA;
	}

	/**
	 * Mutateur de routeB. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr.
	 * Définisez la Route manuellement à votre risque et péril.
	 * 
	 * @param routeB
	 *            The routeB to set.
	 * @uml.property name="routeB"
	 */
	protected void setRouteB(Route routeB) {
		this.routeB = routeB;
	}

	/**
	 * Mutateur de stationA. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon
	 * sûr. Définisez la Station manuellement à votre risque et péril.
	 * 
	 * @param stationA
	 *            The stationA to set.
	 * @uml.property name="stationA"
	 */
	protected void setStationA(Station stationA) {
		this.stationA = stationA;
	}

	/**
	 * Mutateur de stationB. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon
	 * sûr. Définisez la Station manuellement à votre risque et péril.
	 * 
	 * @param stationB
	 *            The station to set.
	 * @uml.property name="stationB"
	 */
	protected void setStationB(Station stationB) {
		this.stationB = stationB;
	}

	/**
	 * Setter of the property <tt>timeBetweenStations</tt>
	 * 
	 * @param timeBetweenStations
	 *            The timeBetweenStations to set. It must be positive or equal to zero
	 * @throws ImpossibleValueException
	 *             si la valeur fournit est incohérente.
	 * @uml.property name="timeBetweenStations"
	 */
	protected void setTimeBetweenStations(int timeBetweenStations) throws ImpossibleValueException {
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * a variable to know if the junction is rail between two station or an junction were you have to step down from you
	 * train/subway/...
	 */
	private boolean routeLink = false;

	public boolean isRouteLink() {
		return routeLink;
	}

	/**
	 * Mutateur de routeLink. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon
	 * sûr. Définisez son etat routeLink manuellement à votre risque et péril.
	 * 
	 */
	protected void setRouteLink(boolean routeLink) {
		this.routeLink = routeLink;
	}

	/**
	 * Construcuteur d'un jonction, il propose de fournir l'ensemble des informations au sujet de la jonction en
	 * une seul fois. La jonction est par défaut active (the Junction.isEnable()==true).
	 * 
	 * @param routeA
	 *            la première route
	 * @param stationA
	 *            la station sur la première route
	 * @param routeB
	 *            la seconde route
	 * @param stationB
	 *            la station sur la second route
	 * @param cost
	 *            le coût pour emprunter cette jonction
	 * @param timeBetweenStations
	 *            le temps nécessaire pour franchir cette jonction
	 * @param routeLink
	 *            est-ce un lien entre deux station de la même ligne, c'est à dire que l'on reste dans le wagon
	 * @param pedestrian
	 *            si le lien n'est routeLink, on doit déscendre du wagon, mais devont nous aussi sortir de la sation
	 *            pour aller à une autre?
	 * @throws StationNotOnRoadException
	 *             si une des station fournit n'est pas sur la ligne où elle devrait être
	 */
	protected Junction(Route routeA, Station stationA, Route routeB, Station stationB, float cost,
			int timeBetweenStations, boolean routeLink, boolean pedestrian) throws StationNotOnRoadException,
			ImpossibleValueException {
		super();
		this.cost = cost;
		this.pedestrian = pedestrian;
		this.routeA = routeA;
		this.routeB = routeB;
		this.stationB = stationB;
		this.stationA = stationA;
		this.timeBetweenStations = timeBetweenStations;
		this.routeLink = routeLink;
	}

}
